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

import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.auth.oauth.OAuthSigner;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.util.Beta;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Generic OAuth 1.0a URL to request API resource from server.
 *
 * @since 1.0
 * @author Sidney Maestre
 */
@Beta
public class OAuthRequestResource extends GenericUrl {

	/**
	 * HTTP transport required for executing request in {@link #execute()}.
	 *
	 * @since 1.3
	 */
	public HttpTransport transport;

	/**
	 * Required identifier portion of the client credentials (equivalent to a username).
	 */
	//private String consumerKey;

	/**
	 * Required access token - obtained from user through oAuth process.
	 */
	private String token;
	
	/**
	 * Required access token - obtained from user through oAuth process.
	 */
	private String tokenSecret;

	/** Required OAuth signature algorithm. */
	public OAuthSigner signer;

	/** Required Generic Url. */
	private GenericUrl Url;

	public String contentType = null;
	public String ifModifiedSince = null;
	public String accept = "application/json";
	private String body = null;
	private HttpContent requestBody = null;
	private String httpMethod = "GET";
	
	private Config config;

	/** {@code true} for POST request or the default {@code false} for GET request. */
	protected boolean usePost;

	public  OAuthRequestResource(Config config, String resource, String method, String body, Map<? extends String, ?> params) {
		this.config = config;
		
		Url = new GenericUrl(config.getApiUrl() + resource);
		this.httpMethod = method;
		if(method.equals("POST") || method.equals("PUT")){
			usePost = true;
		} 
		
		if (params != null) {
			 Url.putAll(params);
	    }
		
		this.body = body;
	}
	
	/**
	 * Executes the HTTP request for a temporary or long-lived token.
	 *
	 * @throws IOException 
	 */
	
	public final HttpResponse execute() throws IOException  {
		
		transport = new ApacheHttpTransport();

		if(usePost){
			requestBody = ByteArrayContent.fromString(null, body);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setUserAgent(config.getUserAgent());
		headers.setAccept(config.getAccept());
		headers.setContentType("application/xml");
		if(ifModifiedSince != null) {
			System.out.println("Set Header " + this.ifModifiedSince);
			headers.setIfModifiedSince(this.ifModifiedSince);	
		}

		HttpRequestFactory requestFactory = transport.createRequestFactory();
		HttpRequest request;
		HttpResponse response = null;
		request = requestFactory.buildRequest(this.httpMethod, Url, requestBody);
		request.setHeaders(headers);    
		createParameters().intercept(request);
		
		response = request.execute();
		response.setContentLoggingLimit(0);

		return response;
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
	
	public void setIfModifiedSince(Date modifiedAfter) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		this.ifModifiedSince = formatter.format(modifiedAfter); 
	}
	
	public OAuthParameters createParameters() 
	{
		if(config.getAppType().equals("PUBLIC")){
			signer = new HmacSigner(config).createHmacSigner(this.tokenSecret);
		}	else {
			signer = new RsaSigner(config).createRsaSigner();
		}

		OAuthParameters result = new OAuthParameters();
		result.consumerKey = config.getConsumerKey();;
		result.token = token;
		result.signer = signer;
		return result;
	}
}
