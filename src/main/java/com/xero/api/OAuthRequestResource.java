/*
 * Copyright (c) 2015 Xero Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.xero.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth.OAuthSigner;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.xero.api.exception.XeroExceptionHandler;
import com.xero.models.bankfeeds.Statements;

public class OAuthRequestResource {
	private String token;
	private String tokenSecret;
	private GenericUrl url;
	private byte[] requestBody = null;
	
	public String contentType = null;
	public String ifModifiedSince = null;
	public String accept = null;
	private String body = null;

	private String httpMethod = "GET";
	private String resource;
	private int connectTimeout = 20;
	private int readTimeout = 20;
	private Map<? extends String, ?> params = null;
	
	private Config config;
	private SignerFactory signerFactory;
	private String fileName;
	final static Logger logger = LogManager.getLogger(OAuthRequestResource.class);

	private OAuthRequestResource(Config config, SignerFactory signerFactory, String resource, String method, Map<? extends String, ?> params) {
		this.config = config;
		this.signerFactory = signerFactory;
		this.resource = resource;
		this.httpMethod = method;
		this.params = params;
		this.connectTimeout = config.getConnectTimeout() * 1000;
		this.readTimeout = config.getReadTimeout() * 1000;
	}

	public  OAuthRequestResource(Config config, SignerFactory signerFactory, String resource, String method, String body, Map<? extends String, ?> params) {
		this(config, signerFactory, resource, method, params);
		this.body = body;
	}
	
	public  OAuthRequestResource(Config config, SignerFactory signerFactory, String resource, String method, String body, Map<? extends String, ?> params, String accept, String contentType) {
		this(config, signerFactory, resource, method, params);
		this.accept = accept;
		this.contentType = contentType;
		this.body = body;
	}

	public  OAuthRequestResource(Config config, SignerFactory signerFactory, String resource, String method, String body, Map<? extends String, ?> params, String accept) {
		this(config, signerFactory, resource, method, params);
		this.accept = accept;
		this.body = body;
	}
	
	public  OAuthRequestResource(Config config, SignerFactory signerFactory, String resource, String method, String contentType, byte[] bytes, Map<? extends String, ?> params) {
		this(config, signerFactory, resource, method, params);
		this.contentType = contentType;
		this.requestBody = bytes;
	}
	
	public  OAuthRequestResource(Config config, SignerFactory signerFactory, String resource, String method, String contentType, byte[] bytes, Map<? extends String, ?> params, String accept) {
		this(config, signerFactory, resource, method, params);
		this.contentType = contentType;
		this.requestBody = bytes;
		this.accept = accept;
	}

	public OAuthRequestResource(Config config, SignerFactory signerFactory, String resource, String method, String contentType, File file, Map<? extends String, ?> params) {
		this(config, signerFactory, resource, method, params);
		this.contentType = contentType;
	}
	
	public final ByteArrayInputStream executefile() throws UnsupportedOperationException, IOException {
		CloseableHttpClient httpclient =null;
		httpclient = new XeroHttpContext(config,this.accept,this.ifModifiedSince).getHttpClient();

		if(this.resource.indexOf("https") ==-1) {
			this.resource = config.getApiUrl() + this.resource;
		}
		
		url = new GenericUrl(this.resource);
		if (this.params != null) {
			url.putAll(this.params);
		}
		
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

		HttpGet httpget = new HttpGet(url.toString());
		if (httpMethod == "GET") {
			this.createParameters().intercept(httpget,url);
			httpget.setConfig(requestConfig.build());
		}
		
		HttpPost httppost = new HttpPost(url.toString());
		if (httpMethod == "POST") {
			httppost.setEntity(new StringEntity(this.body, "utf-8"));
			httppost.addHeader("Content-Type", this.contentType);
		    this.createParameters().intercept(httppost,url);
		    httppost.setConfig(requestConfig.build());
		}
		
		HttpPut httpput = new HttpPut(url.toString());
		if (httpMethod == "PUT") {
			httpput.setEntity(new StringEntity(this.body, "utf-8"));
			httpput.addHeader("Content-Type", this.contentType);

		    this.createParameters().intercept(httpput,url);
		    httpput.setConfig(requestConfig.build());
		}
		
		HttpEntity entity;
		try {
			CloseableHttpResponse response = null;
			  
			if (httpMethod == "GET") {
				response = httpclient.execute(httpget);
			}
			
			if (httpMethod == "POST") {
				response = httpclient.execute(httppost);
			}
			
			if (httpMethod == "PUT") {
				response = httpclient.execute(httpput);
			}
			
		    try {		    
		    		entity = response.getEntity();
		    	
		    		List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());        
		    	    for (Header header : httpHeaders) {
		    	    		if (header.getName() == "Content-Disposition") {
		    	    			this.fileName = parseFileName(header.getValue());
		    	    		}
		    	        //System.out.println("Headers.. name,value:"+header.getName() + "," + header.getValue());
		    	    }
		    		
		        InputStream is = entity.getContent();		       
	            byte[] bytes = IOUtils.toByteArray(is);

	            is.close();
	            return new ByteArrayInputStream(bytes);
		       
		    } finally {
		        response.close();
		    }
		} finally {
		    httpclient.close();
		}
	}
	
	private String parseFileName(String param)
	{
		String fileName = null; 
		Pattern regex = Pattern.compile("(?<=filename=\").*?(?=\")");
        Matcher regexMatcher = regex.matcher(param);
        if (regexMatcher.find()) {
        		fileName = regexMatcher.group();
        }
        return fileName;
	}
	
	public String getFileName() 
	{
		return this.fileName;
	}
	
	public final Map<String, String> execute() throws IOException,XeroApiException  {
		CloseableHttpClient httpclient =null;
		
		httpclient = new XeroHttpContext(config,this.accept,this.ifModifiedSince).getHttpClient();
		
		if(this.resource.indexOf("https") ==-1) {
			this.resource = config.getApiUrl() + this.resource;
		}
		
		//params.forEach((k,v)->System.out.println("Key : " + k + " Value : " + v));
		
		url = new GenericUrl(this.resource);
		if (this.params != null) {
			url.putAll(this.params);
		}
	   
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
		
		HttpGet httpget = new HttpGet(url.toString());
		if (httpMethod == "GET") {
			this.createParameters().intercept(httpget,url);
			httpget.setConfig(requestConfig.build());
			if(logger.isInfoEnabled()){
				logger.info("------------------ GET : URL -------------------");
				logger.info(url.toString());
			}
		}
		
		HttpPost httppost = new HttpPost(url.toString());
		if (httpMethod == "POST") {
			if(logger.isInfoEnabled()){
				logger.info("------------------ POST: BODY  -------------------");
				logger.info(this.body);	
			}
			if(this.requestBody != null) {
				httppost.setEntity(new ByteArrayEntity(this.requestBody));				
			} else {
				httppost.setEntity(new StringEntity(this.body, "utf-8"));
			}
			
			httppost.addHeader("Content-Type", this.contentType);
		    this.createParameters().intercept(httppost,url);
		  	httppost.setConfig(requestConfig.build());
		}
		
		HttpPut httpput = new HttpPut(url.toString());
		if (httpMethod == "PUT") {
			if(logger.isInfoEnabled()){
				logger.info("------------------ PUT : BODY  -------------------");
				logger.info(this.body);
			}
			if(this.requestBody != null) {
				httpput.setEntity(new ByteArrayEntity(this.requestBody));				
			} else {
				httpput.setEntity(new StringEntity(this.body, "utf-8"));
			}
			httpput.addHeader("Content-Type", this.contentType);
			this.createParameters().intercept(httpput,url);
		    httpput.setConfig(requestConfig.build());
		}
		
		HttpDelete httpdelete = new HttpDelete(url.toString());
		if (httpMethod == "DELETE") {
			this.createParameters().intercept(httpdelete,url);
			httpdelete.setConfig(requestConfig.build());
			if(logger.isInfoEnabled()){
				logger.info("------------------ DELTE : URL -------------------");
				logger.info(url.toString());
			}
		}
	
		HttpEntity entity;
		try {
			CloseableHttpResponse response = null;
			  
			if (httpMethod == "GET") {
				response = httpclient.execute(httpget);
			}
			
			if (httpMethod == "POST") {
				response = httpclient.execute(httppost);
			}
			
			if (httpMethod == "PUT") {
				response = httpclient.execute(httpput);
			}
			
			if (httpMethod == "DELETE") {
				response = httpclient.execute(httpdelete);
			}
			
		    try {	
				String content = "";
		        entity = response.getEntity();
		        if(entity != null) {
		        	content = EntityUtils.toString(entity);
			    }
	        	
		        int code = response.getStatusLine().getStatusCode();
		        
		        if (code == 204) {
		        	if (this.contentType == "application/json") {
						content = "{\"Status\": \"DELETED\" }";
		        	} else {
						content = "<Response><Status>DELETED</Status></Response>";		        		
		        	}
		        }
		        if (code != 200 && code != 201 && code != 202 && code != 204) {
		            Header rateHeader = response.getFirstHeader("x-rate-limit-problem");
		            if (rateHeader != null) {
		                content += "&rate=" + rateHeader.getValue().toLowerCase();
                    }
		            
		          	XeroApiException e = new XeroApiException(code,content);
		        		throw e;
		        }
		        
		        Map<String, String> responseMap = new HashMap<>();
		        addToMapIfNotNull(responseMap, "content", content);
		        addToMapIfNotNull(responseMap, "code", code);
		        
		        // TODO: ADD LOGGING of Code & Content
		        if(entity != null) {
			     	EntityUtils.consume(entity);
		        }
		        return responseMap;
		    } finally {
		        response.close();
		    }
		} finally {
		    httpclient.close();
		}
	}
	
	protected void addToMapIfNotNull(Map<String, String> map, String key, Object value) {
        if (value != null) {
            map.put(key, value.toString());
        }
    }
	
	public void setMethod(String method) {
		this.httpMethod = method; 
	}
	
	public void setToken(String token) {
		this.token = token; 
		if(config.getAppType().equals("PRIVATE")) {
			this.token = config.getConsumerKey();
		}
	}
	public void setTokenSecret(String secret) {
		this.tokenSecret = secret; 
	}
	
	public void setIfModifiedSince(OffsetDateTime modifiedAfter) {
		this.ifModifiedSince = modifiedAfter.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
	}
	
	public void setIfModifiedSince(Date modifiedAfter) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		this.ifModifiedSince = formatter.format(modifiedAfter); 
	}
	
	public OAuthParameters createParameters() 
	{
		OAuthSigner signer = signerFactory.createSigner(tokenSecret);

		OAuthParameters result = new OAuthParameters();
		result.consumerKey = config.getConsumerKey();
		result.usingAppFirewall = config.isUsingAppFirewall();
		result.appFirewallHostname = config.getAppFirewallHostname();
		result.appFirewallUrlPrefix = config.getAppFirewallUrlPrefix();
		result.token = token;
		result.signer = signer;
		return result;
	}
	
	@Deprecated
	public void setProxy(String host, int port, boolean httpsEnabled) {

	}
	
	public static boolean isJSONValid(String jsonInString ) {
    	try {
    		final ObjectMapper mapper = new ObjectMapper();
    		mapper.readTree(jsonInString);
    		return true;
    	} catch (IOException e) {
    		return false;
    	}
    } 
}
