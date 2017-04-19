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
import com.google.api.client.http.*;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.util.Beta;

import java.io.File;
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

	private void init(Config config, String resource, String method, Map<? extends String, ?> params) {
		this.config = config;

		Url = new GenericUrl(config.getApiUrl() + resource);
		this.httpMethod = method;
		if(method.equals("POST") || method.equals("PUT")){
			usePost = true;
		}

		if (params != null) {
			Url.putAll(params);
		}
	}

	public  OAuthRequestResource(Config config, String resource, String method, String body, Map<? extends String, ?> params) {
		init(config, resource, method, params);
		this.body = body;
	}

	public  OAuthRequestResource(Config config, String resource, String method, String contentType, byte[] bytes, Map<? extends String, ?> params) {
		init(config, resource, method, params);
		this.contentType = contentType;
		this.requestBody = new ByteArrayContent(contentType,  bytes);
	}

	public  OAuthRequestResource(Config config, String resource, String method, String contentType, File file, Map<? extends String, ?> params) {
		init(config, resource, method, params);
		this.contentType = contentType;
		this.requestBody = new FileContent(contentType, file);
	}
	
	/**
	 * Executes the HTTP request for a temporary or long-lived token.
	 *
	 * @return OAuth credentials response object
	 */
	public final HttpResponse execute() throws IOException {
		/*  DEPRECATED ENTRUST CERTIFICATES
		if(c.getAppType().equals("PARTNER")){
			transport = new PartnerHttpClient().getPartnerHttpClient();
		}	else {
			transport = new ApacheHttpTransport();
		}
		*/
		
		transport = new ApacheHttpTransport();

		if(usePost && body != null){
			requestBody = ByteArrayContent.fromString(null, body);
		}

		HttpRequestFactory requestFactory = transport.createRequestFactory();
		HttpRequest request = requestFactory.buildRequest(this.httpMethod, Url, requestBody);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setUserAgent(config.getUserAgent());
		headers.setAccept(config.getAccept());
		headers.setContentType(contentType == null ? "application/xml" : contentType);
		if(ifModifiedSince != null) {
			System.out.println("Set Header " + this.ifModifiedSince);
			headers.setIfModifiedSince(this.ifModifiedSince);
			
		}
		request.setHeaders(headers);    
		createParameters().intercept(request);
		HttpResponse response;
		
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
