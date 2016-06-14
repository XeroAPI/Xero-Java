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
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.util.Beta;

import java.io.IOException;

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

	private Config c = Config.getInstance();

	/** {@code true} for POST request or the default {@code false} for GET request. */
	protected boolean usePost;

	/**
	 * @param authorizationServerUrl encoded authorization server URL
	 */
	public  OAuthRequestResource(String resource, String method, String body) {
		Url = new GenericUrl(c.getApiUrl() + resource);
		if(method.equals("POST")){
			usePost = true;
		}
		this.body = body;
	}

	/**
	 * Executes the HTTP request for a temporary or long-lived token.
	 *
	 * @return OAuth credentials response object
	 */
	public final HttpResponse execute() throws IOException {
		if(c.getAppType().equals("PARTNER")){
			transport = new PartnerHttpClient().getPartnerHttpClient();
		}	else {
			transport = new ApacheHttpTransport();
		}

		if(usePost){
			requestBody = ByteArrayContent.fromString(null, body);
		}

		HttpRequestFactory requestFactory = transport.createRequestFactory();
		HttpRequest request = requestFactory.buildRequest(usePost ? HttpMethods.POST : HttpMethods.GET, Url, requestBody);

		HttpHeaders headers = new HttpHeaders();
		headers.setUserAgent(c.getUserAgent());
		headers.setAccept(c.getAccept());
		headers.setContentType("application/xml");
		//headers.setIfModifiedSince(ifModifiedSince);
		request.setHeaders(headers);    
		
		createParameters().intercept(request);

		HttpResponse response = request.execute();
		response.setContentLoggingLimit(0);

		return response;
	}

	public void setToken(String token) {
		this.token = token; 
		if(c.getAppType().equals("PRIVATE")) {
			this.token = c.getConsumerKey();
		}
	}
	public void setTokenSecret(String secret) {
		this.tokenSecret = secret; 
	}

	public OAuthParameters createParameters() 
	{

		if(c.getAppType().equals("PUBLIC")){
			signer = new HmacSigner().createHmacSigner(this.tokenSecret);
		}	else {
			signer = new RsaSigner().createRsaSigner();
		}

		OAuthParameters result = new OAuthParameters();
		result.consumerKey = c.getConsumerKey();;
		result.token = token;
		result.signer = signer;
		return result;
	}
}
