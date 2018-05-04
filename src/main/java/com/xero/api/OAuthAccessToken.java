package com.xero.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthSigner;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.UrlEncodedParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class OAuthAccessToken {

  private String token = null;
  private String tokenSecret = null;
  private String sessionHandle = null;
  private long tokenTimestamp;
  private boolean isSuccess;
  private String problem = null;
  private String advice = null;
  private Config config;
  private SignerFactory signerFactory;
  private CloseableHttpClient httpclient;
  public String verifier;
  public String tempToken;
  private String tempTokenSecret;
  private int connectTimeout = 20;
  private int readTimeout = 20;
	
  public OAuthAccessToken(Config config) {
    this(config, new ConfigBasedSignerFactory(config));
  }

  public OAuthAccessToken(Config config, SignerFactory signerFactory) {
    this.config = config;
    this.signerFactory = signerFactory;
  }

  public OAuthAccessToken build(String verifier, String tempToken, String tempTokenSecret) throws IOException {
    this.verifier = verifier;
    this.tempToken = tempToken;
    this.tempTokenSecret = tempTokenSecret;
    this.connectTimeout = config.getConnectTimeout() * 1000;
	this.readTimeout = config.getReadTimeout() * 1000;
   
    	httpclient = new XeroHttpContext(config).getHttpClient();
	
    return this;
  }

  public OAuthAccessToken build() throws IOException {
    httpclient = new XeroHttpContext(config).getHttpClient();
    return this;
  }

  public boolean execute() throws IOException {
	  GenericUrl requestUrl = new GenericUrl(this.config.getAccessTokenUrl());

	  HttpGet httpget = new HttpGet(this.config.getAccessTokenUrl());
	  
	  RequestConfig.Builder requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(readTimeout)
				.setSocketTimeout(connectTimeout);
		
	  //Proxy Service Setup - unable to fully test as we don't have a proxy 
	  // server to test against.
	  if(!"".equals(config.getProxyHost()) && config.getProxyHost() != null) {
		  int port = (int) (config.getProxyPort() == 80 && config.getProxyHttpsEnabled() ? 443 : config.getProxyPort());		
		  HttpHost proxy = new HttpHost(config.getProxyHost(), port, config.getProxyHttpsEnabled() ? "https" : "http");
		  requestConfig.setProxy(proxy);
	  }
	  this.createParameters().intercept(httpget,requestUrl);
	  httpget.setConfig(requestConfig.build());
	
	  try {
		  CloseableHttpResponse response = httpclient.execute(httpget);
		  try {
			  HttpEntity entity = response.getEntity();  
			  String retSrc = EntityUtils.toString(entity);
			  
			  OAuthCredentialsResponse oauthResponse = new OAuthCredentialsResponse();
			  UrlEncodedParser.parse(retSrc, oauthResponse); 
			  
			  if ((oauthResponse.token == null || oauthResponse.token.length() == 0) && (config.getKeyStorePassword() == null || config.getKeyStorePassword().length() == 0))
			  {
				  Map<String, String> oauthError = getQueryMap(retSrc);
				  this.problem = oauthError.get("oauth_problem");
				  this.advice = oauthError.get("oauth_problem_advice");
				  isSuccess = false;   
			  } else {
				  Map<String, String> oauthKeys = getQueryMap(retSrc);
				  this.token = oauthKeys.get("oauth_token");
				  this.tokenSecret = oauthKeys.get("oauth_token_secret");
				  this.sessionHandle = oauthKeys.get("oauth_session_handle");
				  this.tokenTimestamp = System.currentTimeMillis() / 1000l;
				  isSuccess = true;
			  }
		        
			  EntityUtils.consume(entity);
		        
		  } finally {
			  response.close();
		  }
		    
	  } finally {
		  httpclient.close();
	  }
	  return isSuccess;
  }

  public void setToken(String token) {
    this.token = token;
    if (config.getAppType().equals("PRIVATE")) {
      this.token = config.getConsumerKey();
    }
  }

  public String getToken() {
    return token;
  }

  public void setTokenSecret(String secret) {
    this.tokenSecret = secret;
  }

  public String getTokenSecret() {
    return tokenSecret;
  }

  public Boolean isSuccess() {
    return isSuccess;
  }

  public String getProblem() {
    return problem;
  }

  public String getAdvice() {
    return advice;
  }

  public void setSessionHandle(String sessionHandle) {
    this.sessionHandle = sessionHandle;
  }

  public String getSessionHandle() {
    return sessionHandle;
  }

  public String getTokenTimestamp() {
    String s = Objects.toString(tokenTimestamp, null);
    return s;
  }

  private static Map<String, String> getQueryMap(String query) {
    String[] params = query.split("&");
    Map<String, String> map = new HashMap<String, String>();
    for (String param : params) {
      String name = param.split("=")[0];
      String value = param.split("=")[1];
      map.put(name, value);
    }

    return map;
  }

  public HashMap<String, String> getAll() {
    HashMap<String, String> map = new HashMap<String, String>();

    map.put("token", getToken());
    map.put("tokenSecret", getTokenSecret());
    map.put("sessionHandle", getSessionHandle());
    map.put("tokenTimestamp", getTokenTimestamp());

    return map;
  }

  private OAuthParameters createParameters() {
    OAuthSigner signer = signerFactory.createSigner(tempTokenSecret);

    OAuthParameters result = new OAuthParameters();
    result.consumerKey = config.getConsumerKey();
    result.usingAppFirewall = config.isUsingAppFirewall();
    result.appFirewallHostname = config.getAppFirewallHostname();
    result.appFirewallUrlPrefix = config.getAppFirewallUrlPrefix();
    result.token = tempToken;
    result.verifier = verifier;
    result.signer = signer;
    return result;
  }

@SuppressWarnings("unused")
private OAuthParameters createRefreshParameters() {
    OAuthSigner signer = signerFactory.createSigner(null);

    OAuthParameters result = new OAuthParameters();
    result.consumerKey = config.getConsumerKey();
    result.usingAppFirewall = config.isUsingAppFirewall();
    result.appFirewallHostname = config.getAppFirewallHostname();
    result.appFirewallUrlPrefix = config.getAppFirewallUrlPrefix();
    result.token = this.token;
    result.sessionHandle = this.sessionHandle;
    result.signer = signer;
    return result;
  }

  public boolean isStale(String timestamp) {
    boolean bool = false;

    if (timestamp == null || timestamp.isEmpty()) {
      bool = false;
    } else {

      long currentTime = System.currentTimeMillis() / 1000l;

      long tokenTimestamp = Long.parseLong(timestamp);
      long secondsElapsed = (currentTime - tokenTimestamp);

      if (secondsElapsed >= 1800) {
        bool = true;
      }
    }

    return bool;
  }
}
