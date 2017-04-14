package com.xero.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.xero.api.OAuthParameters;

import com.google.api.client.auth.oauth.OAuthSigner;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;

public class OAuthAccessToken {

	private String token = null;
	private String tokenSecret = null;
	private String sessionHandle = null;
	private long tokenTimestamp;
	private boolean isSuccess;
	private String problem = null;
	private String advice = null;
	private Config config;
	private HttpRequest request;
	private GenericUrl Url;
	
	private HttpTransport transport;
	private OAuthSigner signer;
	public String verifier;
	public String tempToken;
	private String tempTokenSecret;
	
	public OAuthAccessToken(Config config)
	{
		this.config = config;
	}
	
	public OAuthAccessToken build(String verifier, String tempToken, String tempTokenSecret) throws IOException
	{
		this.verifier = verifier;
		this.tempToken = tempToken;
		this.tempTokenSecret = tempTokenSecret;
		
		Url = new GenericUrl(config.getAccessTokenUrl());
		
		/*  DEPRECATED ENTRUST CERTIFICATES
		if(c.getAppType().equals("PARTNER"))
		{
			transport = new PartnerHttpClient().getPartnerHttpClient();
		}	else {
			transport = new ApacheHttpTransport();
		}
		*/
		
		transport = new ApacheHttpTransport();
		  
		HttpRequestFactory requestFactory = transport.createRequestFactory();
		request = requestFactory.buildRequest(HttpMethods.GET, Url,null);

		HttpHeaders headers = new HttpHeaders();
		headers.setUserAgent(config.getUserAgent());
		headers.setAccept(config.getAccept());
		request.setHeaders(headers);    
		
		createParameters().intercept(request); 
		
		return this;
	}
	
	public OAuthAccessToken build() throws IOException
	{
		Url = new GenericUrl(config.getAccessTokenUrl());
		
		/*  DEPRECATED ENTRUST CERTIFICATES
		if(c.getAppType().equals("PARTNER"))
		{
			transport = new PartnerHttpClient().getPartnerHttpClient();
		}
		else 
		{
			transport = new ApacheHttpTransport();
		}
		*/
		
		transport = new ApacheHttpTransport();
		  
		HttpRequestFactory requestFactory = transport.createRequestFactory();
		request = requestFactory.buildRequest(HttpMethods.GET, Url,null);

		HttpHeaders headers = new HttpHeaders();
		headers.setUserAgent(config.getUserAgent());
		headers.setAccept(config.getAccept());

		request.setHeaders(headers);    
		createRefreshParameters().intercept(request); 
		
		return this;
	}
	
	public boolean execute() throws IOException
	{
		try {
			HttpResponse response = request.execute();	
			
			isSuccess = response.isSuccessStatusCode();
		
			if (isSuccess)
			{
				Map<String, String> oauthKeys = getQueryMap(response.parseAsString());
				
				this.token = oauthKeys.get("oauth_token");
				this.tokenSecret = oauthKeys.get("oauth_token_secret");
				this.sessionHandle = oauthKeys.get("oauth_session_handle");
				this.tokenTimestamp = System.currentTimeMillis() / 1000l;
				isSuccess = true;
			} 
			else 
			{
				
			}
		} catch (HttpResponseException e) {
			System.out.println("REFRESH EXECPTION");
			
			Map<String, String> oauthError = getQueryMap(e.getMessage());
			this.problem = oauthError.get("oauth_problem");
			this.advice = oauthError.get("oauth_problem_advice");
			//System.out.println("HttpException" + e.toString());
			isSuccess =  false;
		}
		return isSuccess;
	}
	
	public void setToken(String token) 
	{
		this.token = token; 
		if(config.getAppType().equals("PRIVATE")) 
		{
			this.token = config.getConsumerKey();
		}
	}
	
	public String getToken()
	{
		return token;
	}

	public void setTokenSecret(String secret) 
	{
		this.tokenSecret = secret; 
		
	}
	
	public String getTokenSecret()
	{
		return tokenSecret;
	}
	
	public Boolean isSuccess()
	{
		return isSuccess;
	}
	
	public String getProblem()
	{
		return problem;
	}
	
	public String getAdvice()
	{
		return advice;
	}
	
	public void setSessionHandle(String sessionHandle) 
	{
		this.sessionHandle = sessionHandle; 
	}
	
	public String getSessionHandle()
	{
		return sessionHandle;
	}
	
	public String getTokenTimestamp()
	{
		String s = Objects.toString(tokenTimestamp, null);
		return s;
	}
	
	private static Map<String, String> getQueryMap(String query)
	{
	    String[] params = query.split("&");
	    Map<String, String> map = new HashMap<String, String>();
	    for (String param : params)
	    {
	        String name = param.split("=")[0];
	        String value = param.split("=")[1];
	        map.put(name, value);
	    }
	    
	    return map;
	}
	
	public HashMap<String, String> getAll()
	{
		HashMap<String,String> map = new HashMap<String,String>();
	
		map.put("token",getToken());
		map.put("tokenSecret",getTokenSecret());
		map.put("sessionHandle",getSessionHandle());
		map.put("tokenTimestamp",getTokenTimestamp());
		
		return map;
	}
	
	private OAuthParameters createParameters() 
	{
		if(config.getAppType().equals("PUBLIC"))
		{
			signer = new HmacSigner(config).createHmacSigner(tempTokenSecret);
		}	else {
			signer = new RsaSigner(config).createRsaSigner();
		}
		  
		
		OAuthParameters result = new OAuthParameters();
		result.consumerKey = config.getConsumerKey();
		result.token = tempToken;
		result.verifier = verifier;
		result.signer = signer;
		return result;
	}
	
	private OAuthParameters createRefreshParameters() 
	{
		signer = new RsaSigner(config).createRsaSigner();
		  
		OAuthParameters result = new OAuthParameters();
		result.consumerKey = config.getConsumerKey();
		result.token = this.token;
		result.sessionHandle = this.sessionHandle;
		result.signer = signer;
		return result;
	}
	
	public boolean isStale(String timestamp) 
	{
		boolean bool = false;
		
		if (timestamp == null || timestamp.isEmpty()) {
			bool = false;
		} else {
		
			long currentTime = System.currentTimeMillis() / 1000l;
			 
			long tokenTimestamp = Long.parseLong(timestamp);
			long secondsElapsed = (currentTime - tokenTimestamp);
			
			if (secondsElapsed >= 1800) 
			{
				bool = true;
			}
		}
		
		return bool;
	}

}