# Xero-Java

[![Maven Central](https://img.shields.io/maven-central/v/com.github.xeroapi/xero-java.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.xeroapi%22%20AND%20a:%22xero-java%22)

## Current release of SDK with oAuth 2 support
Version 3.x of Xero Java SDK only supports oAuth2 authentication and the following API sets.
* accounting

Coming soon
* fixed asset 
* bank feeds 
* files 
* payroll
* projects
* xero hq

All third party libraries dependencies managed with Maven.

## Looking for version 2.x of the SDK with oAuth 1.0a support?
Codebase, samples and setup instructions located in [oauth1 branch](https://github.com/XeroAPI/Xero-Java/tree/oauth1).

## Getting Started

### Create a Xero App
Follow these steps to create your Xero app

* Create a free Xero user account (if you don't have one)
* Use this URL for beta access to oAuth2 [https://developer.xero.com/myapps?code=oauth2create](https://developer.xero.com/myapps?code=oauth2create)
* Click "or Try oAuth2" link
* Enter your App name, company url, privacy policy url, and redirect URI (this is your callback url - localhost, etc)
* Agree to terms and condition and click "Create App".
* Click "Generate a secret" button.
* Copy your client id and client secret and save for use later.
* Click the "Save" button. You secret is now hidden.

### Add Xero-Java Dependency

Add the Xero Java SDK dependency to project via maven, gradle, sbt or other build tools can be found on [maven central](https://search.maven.org/search?q=g:com.github.xeroapi).

```xml
<dependency>
  <groupId>com.github.xeroapi</groupId>
  <artifactId>xero-java</artifactId>
  <version>3.X.X</version>
</dependency>
```
## How to use the Xero-Java SDK

### Step by Step Video
We've created a video walking through how to create a new Eclipse project, add your dependencies and make your first API call.
Watch this video - TODO. 

Below are the code snippets used in the video

### Authorization
Create your [Xero app](https://developer.xero.com/myapps) to obtain your clientId, clientSecret and set your redirectUri.  The redirectUri is your server that Xero will send a user back to once authorization is complete (aka callback url).

You can add or remove items from the scopeList for your integration.

Lastly, you'll generate an authorization URL and redirect the user to Xero for authorization.

*Authorization.java*
```java
package com.xero.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;

@WebServlet("/Authorization")
public class Authorization extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String clientId = "--YOUR_CLIENT_ID--";
	final String clientSecret = "--YOUR_CLIENT_SECRET--";
	final String redirectURI = "--YOUR_REDIRECT_URI--";
    final String TOKEN_SERVER_URL = "https://identity.xero.com/connect/token";
    final String AUTHORIZATION_SERVER_URL = "https://login.xero.com/identity/connect/authorize";
	final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    final JsonFactory JSON_FACTORY = new JacksonFactory();
    final String secretState = "secret" + new Random().nextInt(999_999);
       
    public Authorization() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> scopeList = new ArrayList<String>();
        scopeList.add("openid");
        scopeList.add("email");
        scopeList.add("profile");
        scopeList.add("offline_access");
        scopeList.add("accounting.settings");
        scopeList.add("accounting.transactions");
        scopeList.add("accounting.contacts");
        scopeList.add("accounting.journals.read");
        scopeList.add("accounting.reports.read");
        scopeList.add("accounting.attachments");
        
        DataStoreFactory DATA_STORE_FACTORY = new MemoryDataStoreFactory();		
        AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(), 
        		HTTP_TRANSPORT, 
        		JSON_FACTORY, 
        		new GenericUrl(TOKEN_SERVER_URL), 
        		new ClientParametersAuthentication(clientId, clientSecret), clientId, AUTHORIZATION_SERVER_URL)
        			.setScopes(scopeList)
        			.setDataStoreFactory(DATA_STORE_FACTORY)
        			.build();
       
        String url = flow.newAuthorizationUrl()
        	.setClientId(clientId)
        	.setScopes(scopeList)
        	.setState(secretState)
            .setRedirectUri(redirectURI).build();
        
         response.sendRedirect(url);
	}
}
```

After the user has selected an organisation to authorise, they will be returned to your application specified in the redirectUri.  Below is an example Callback servlet.  You'll get a *code* from callback url query string and use it to request you access token.

An access token can be associate with one or more Xero orgs, so you'll need to call Xero's identity service (https://api.xero.com/Connections).  You'll receive an array of xero-tenant-id's (that identify the organisation(s) authorized). Use both the access token and the tenant id to access resources via the API.

Lastly, we save the access token, refresh token and Xero tenant id.  We've mocked up a TokenStorage class for this demo.

*Callback.java*
```java
package com.xero.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.xero.api.ApiClient;
import com.xero.api.client.IdentityApi;
import com.xero.models.identity.Connection;

@WebServlet("/Callback")
public class Callback extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String clientId = "--YOUR_CLIENT_ID--";
	final String clientSecret = "--YOUR_CLIENT_SECRET--";
	final String redirectURI = "--YOUR_REDIRECT_URI--";
    final String TOKEN_SERVER_URL = "https://identity.xero.com/connect/token";
    final String AUTHORIZATION_SERVER_URL = "https://login.xero.com/identity/connect/authorize";
	final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    final JsonFactory JSON_FACTORY = new JacksonFactory();

    public Callback() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = "123";
		if (request.getParameter("code") != null) {   
			code = request.getParameter("code");
		}
	
	    ArrayList<String> scopeList = new ArrayList<String>();
	    scopeList.add("openid");
	    scopeList.add("email");
	    scopeList.add("profile");
	    scopeList.add("offline_access");
	    scopeList.add("accounting.settings");
	    scopeList.add("accounting.transactions");
	    scopeList.add("accounting.contacts");
	    scopeList.add("accounting.journals.read");
	    scopeList.add("accounting.reports.read");
	    scopeList.add("accounting.attachments");
	    
	    DataStoreFactory DATA_STORE_FACTORY = new MemoryDataStoreFactory();		
	    
	    AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(), 
	    		HTTP_TRANSPORT, 
	    		JSON_FACTORY, 
	    		new GenericUrl(TOKEN_SERVER_URL), 
	    		new ClientParametersAuthentication(clientId, clientSecret), clientId, AUTHORIZATION_SERVER_URL).setScopes(scopeList).setDataStoreFactory(DATA_STORE_FACTORY).build();
	    
	    TokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirectURI).execute();
	   
	    HttpTransport httpTransport = new NetHttpTransport();
	    JsonFactory jsonFactory = new JacksonFactory();
	    GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory).setClientSecrets(clientId, clientSecret).build();
	    credential.setAccessToken(tokenResponse.getAccessToken());
	    credential.setRefreshToken(tokenResponse.getRefreshToken());
	    credential.setExpiresInSeconds(tokenResponse.getExpiresInSeconds());
	
	    // Create requestFactory with credentials
	    HttpTransport transport = new NetHttpTransport();        
	    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
	
	    // Init IdentityApi client
	    ApiClient defaultClient = new ApiClient("https://api.xero.com",null,null,null,requestFactory);
	    IdentityApi idApi = new IdentityApi(defaultClient);
	    List<Connection> connection = idApi.getConnections();
	
	    TokenStorage store = new TokenStorage();
	    store.saveItem(response, "access_token", tokenResponse.getAccessToken());
	    store.saveItem(response, "refresh_token", tokenResponse.getRefreshToken());
	    store.saveItem(response, "xero_tenant_id", connection.get(0).getTenantId().toString());
	
	    response.sendRedirect("./AuthenticatedResource");
	}
}		
```

TokenStorage class uses cookies to store your access token, refresh token and Xero tenant id.  Of course, you'd want to create your own implementation of Token Storage to store information in a database.  This class is merely for demo purposes so you can trying out the SDK.

*TokenStorage.java*
```java
package com.xero.example;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenStorage 
{
	
	public  TokenStorage() 
	{
		super();
	}

	public String get(HttpServletRequest request,String key)
	{
		String item = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) 
			{
				if (cookies[i].getName().equals(key)) 
				{
					item = cookies[i].getValue();
				}
			}
		}
		return item;
	}

	public void clear(HttpServletResponse response)
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("access_token","");
		map.put("refresh_token","");
		map.put("xero_tenant_id","");

		save(response,map);
	}
	
	public void saveItem(HttpServletResponse response,String key, String value)
	{
		Cookie t = new Cookie(key,value);
		response.addCookie(t);
	}

	public void save(HttpServletResponse response,HashMap<String,String> map)
	{
		Set<Entry<String, String>> set = map.entrySet();
		Iterator<Entry<String, String>> iterator = set.iterator();
	
		while(iterator.hasNext()) {
			Map.Entry<?, ?> mentry = iterator.next();
			String key = (String)mentry.getKey();
			String value = (String)mentry.getValue();

			Cookie t = new Cookie(key,value);
			response.addCookie(t);
		}
	}
}
```

TokenRefresh class is an example of how you can check if your access token is expired and perform a token refresh if needed. This example uses the TokenStorage class to persist you new access token and refresh token when performing a refresh.  You are welcome to modify or replace this class to suit your needs.

*TokenRefresh.java*
```java
package com.xero.example;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class TokenRefresh {
	final static Logger logger = LogManager.getLogger(AuthenticatedResource.class);
	final String clientId = "--YOUR_CLIENT_ID--";
	final String clientSecret = "--YOUR_CLIENT_SECRET--";
	final String redirectURI = "--YOUR_REDIRECT_URI--";
	final String TOKEN_SERVER_URL = "https://identity.xero.com/connect/token";
	
	public  TokenRefresh() 
	{
		super();
	}
	
	public String checkToken(String accessToken, String refreshToken, HttpServletResponse response) throws IOException 
	{
		String currToken =null;

		try {
			DecodedJWT jwt = JWT.decode(accessToken);

			if (jwt.getExpiresAt().getTime() > System.currentTimeMillis()) {
				currToken = accessToken;
			} else {
				try {
					TokenResponse tokenResponse = new RefreshTokenRequest(new NetHttpTransport(), new JacksonFactory(), new GenericUrl(
		    	        		  TOKEN_SERVER_URL), refreshToken)
		    	          		.setClientAuthentication(new BasicAuthentication(this.clientId, this.clientSecret)).execute();
		    	      
					// DEMO PURPOSE ONLY - You'll need to implement your own token storage solution
					TokenStorage store = new TokenStorage();
					store.saveItem(response, "jwt_token", tokenResponse.toPrettyString());
					store.saveItem(response, "access_token", tokenResponse.getAccessToken());
					store.saveItem(response, "refresh_token", tokenResponse.getRefreshToken());
					store.saveItem(response, "expires_in_seconds", tokenResponse.getExpiresInSeconds().toString());
		    	      
					currToken = tokenResponse.getAccessToken();
				} catch (TokenResponseException e) {			
					if (e.getDetails() != null) {
						System.out.println("Error: " + e.getDetails().getError());
						if (e.getDetails().getErrorDescription() != null) {
							System.out.println(e.getDetails().getErrorDescription());
						}
						if (e.getDetails().getErrorUri() != null) {
							System.out.println(e.getDetails().getErrorUri());
						}
					} else {
						System.out.println(e.getMessage());	
		   			}
		   		}
		    }
		} catch (JWTDecodeException exception){
			System.out.println(exception.getMessage());
		}
		return currToken;
	}
}
```

**Data Endpoints**

The Xero Java SDK contains Client classes (AccountingApi, etc) which have helper methods to perform (Create, Read, Update and Delete) actions on each endpoints.  AccountingApi is designed as a Singleton. Use the getInstance method of the class class and use with API models to interact with Java Objects.

*Token expiration should be checked prior to making API calls*

*AuthenticatedResource.java*
```java
package com.xero.example;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.threeten.bp.OffsetDateTime;

import com.xero.api.ApiClient;
import com.xero.api.XeroApiException;
import com.xero.api.client.AccountingApi;
import com.xero.models.accounting.*;

@WebServlet("/AuthenticatedResource")
public class AuthenticatedResource extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountingApi accountingApi;
       
    public AuthenticatedResource() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get Tokens and Xero Tenant Id from Storage
		TokenStorage store = new TokenStorage();
		String savedAccessToken =store.get(request, "access_token");
		String savedRefreshToken = store.get(request, "refresh_token");
		String xeroTenantId = store.get(request, "xero_tenant_id");	

		// Check expiration of token and refresh if necessary
		// This should be done prior to each API call to ensure your accessToken is valid
		String accessToken = new TokenRefresh().checkToken(savedAccessToken,savedRefreshToken,response);

		// Init AccountingApi client
		ApiClient defaultClient = new ApiClient();

		// Get Singleton - instance of accounting client
		accountingApi = AccountingApi.getInstance(defaultClient);	
		
		try {
			// Get All Contacts
			Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,null, null, null, null, null, null);
			System.out.println("How many contacts did we find: " + contacts.getContacts().size());
			
			/* CREATE ACCOUNT */
			Account acct = new Account();
			acct.setName("Office Expense for Me");
			acct.setCode("66000");
			acct.setType(com.xero.models.accounting.AccountType.EXPENSE);
			Accounts newAccount = accountingApi.createAccount(accessToken,xeroTenantId,acct);
			System.out.println("New account created: " + newAccount.getAccounts().get(0).getName());

			/* READ ACCOUNT using a WHERE clause */
			String where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
			Accounts accountsWhere = accountingApi.getAccounts(accessToken,xeroTenantId,null, where, null);

			/* READ ACCOUNT using the ID */
			Accounts accounts = accountingApi.getAccounts(accessToken,xeroTenantId,null, null, null);
			UUID accountID = accounts.getAccounts().get(0).getAccountID();
			Accounts oneAccount = accountingApi.getAccount(accessToken,xeroTenantId,accountID);
										
			/* UPDATE ACCOUNT */
			UUID newAccountID = newAccount.getAccounts().get(0).getAccountID();
			newAccount.getAccounts().get(0).setDescription("Monsters Inc.");
			newAccount.getAccounts().get(0).setStatus(null);
			Accounts updateAccount = accountingApi.updateAccount(accessToken,xeroTenantId,newAccountID, newAccount);

			/* DELETE ACCOUNT */
			UUID deleteAccountID = newAccount.getAccounts().get(0).getAccountID();
			Accounts deleteAccount = accountingApi.deleteAccount(accessToken,xeroTenantId,deleteAccountID);
			System.out.println("Delete account - Status? : " + deleteAccount.getAccounts().get(0).getStatus());

			// GET INVOICE MODIFIED in LAST 24 HOURS
			OffsetDateTime invModified = OffsetDateTime.now();
			invModified.minusDays(1);	
			Invoices InvoiceList24hour = accountingApi.getInvoices(accessToken,xeroTenantId,invModified, null, null, null, null, null, null, null, null, null, null);
			System.out.println("How many invoices modified in last 24 hours?: " + InvoiceList24hour.getInvoices().size());
		

			response.getWriter().append("API calls completed at: ").append(request.getContextPath());
		} catch (XeroApiException xe) {
			System.out.println("Xero Exception: " + xe.getResponseCode());	
			for(Element item : xe.getError().getElements()){
				for(ValidationError err : item.getValidationErrors()){
					System.out.println("Error Msg: " + err.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());	
		}
	}
}
```

**Exception Handling**

Utilize the XeroApiException class for exceptions related to Xero's API.  By wrapping your APIs in a try/catch block you can read  XeroApiExceptions and determine the appropriate action.

Validation errors  are common and contain a response code of "400", a list of elements and validation errors will be included in the caught exception.

```java
try {
	// Create contact with the same name as an existing contact will generate a validation error.
	Contact contact = new Contact();
	contact.setName("Sidney Maestre");
	Contacts createContact1 = accountingApi.createContact(accessToken,xeroTenantId, contact);
	Contacts createContact2 = accountingApi.createContact(accessToken,xeroTenantId, contact);
} catch (XeroApiException xe) {
	System.out.println("Xero Exception: " + xe.getResponseCode());	
	for(Element item : xe.getError().getElements()){
		for(ValidationError err : item.getValidationErrors()){
			System.out.println("Error Msg: " + err.getMessage());
		}
	}
} catch (Exception e) {
	System.out.println(e.getMessage());	
}
```

## Logging
The SDK uses log4j2.  To configure, add a log4j2.xml file to the src/resources directory in your project.

log4j2.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="DEBUG">
	<Appenders>
		<Console name="Console" target="SYSTEM_OUT">
			<PatternLayout pattern="%d{YYYY-MM-dd HH:mm:ss} [%t] %-5p %c{1}:%L - %msg%n" />
		</Console>
 
		<RollingFile name="RollingFile" filename="log/XeroJavaSDK.log"
			filepattern="${logPath}/%d{YYYYMMddHHmmss}-fargo.log">
			<PatternLayout pattern="%d{YYYY-MM-dd HH:mm:ss} [%t] %-5p %c{1}:%L - %msg%n" />
			<Policies>
				<SizeBasedTriggeringPolicy size="100 MB" />
			</Policies>
			<DefaultRolloverStrategy max="20" />
		</RollingFile>
 
	</Appenders>
	<Loggers>
        <Root level="debug" additivity="false">
			<AppenderRef ref="Console" />
			<AppenderRef ref="RollingFile" />
		</Root>
	</Loggers>
</Configuration>
```

## TLS 1.0 deprecation
As of June 30, 2018, Xero's API will remove support for TLS 1.0.  

The easiest way to force TLS 1.2 is to set the Runtime Environment for your server (Tomcat, etc) to Java 1.8 which defaults to TLS 1.2.

## License

This software is published under the [MIT License](http://en.wikipedia.org/wiki/MIT_License).

	Copyright (c) 2019 Xero Limited

	Permission is hereby granted, free of charge, to any person
	obtaining a copy of this software and associated documentation
	files (the "Software"), to deal in the Software without
	restriction, including without limitation the rights to use,
	copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the
	Software is furnished to do so, subject to the following
	conditions:

	The above copyright notice and this permission notice shall be
	included in all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
	EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
	OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
	NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
	HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
	WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
	OTHER DEALINGS IN THE SOFTWARE.
