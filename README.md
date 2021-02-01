# Xero-Java OAuth 2.0

[![Maven Central](https://img.shields.io/maven-central/v/com.github.xeroapi/xero-java.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.xeroapi%22%20AND%20a:%22xero-java%22)

## Current release of SDK with oAuth 2 support
Version 4.x and higher of Xero Java SDK only supports OAuth2 authentication and the following API sets.
* accounting 
* identity
* bank feeds
* fixed asset 
* projects
* payroll au
* payroll uk
* payroll nz

Coming soon 
* files 

## SDK Documentation
We've begun generating SDK documentation with code examples for each method.

* [Accounting](http://xeroapi.github.io/Xero-Java/docs/v4/accounting/index.html) 

Coming Soon
* identity
* bank feeds
* fixed asset 
* projects
* payroll au
* payroll uk
* payroll nz
* files


All third party libraries dependencies managed with Maven.

## Changes in version 4.x

### Methods to access Dates have changed 
Both our Accounting and AU Payroll APIs use [Microsoft .NET JSON format](https://developer.xero.com/documentation/api/requests-and-responses#JSON) i.e. "\/Date(1439434356790)\/". Our other APIs use standard date formatting i.e. "2020-03-24T18:43:43.860852". Building our SDKs from OpenAPI specs with such different date formats has been challenging.

For this reason, we've decided dates in MS .NET JSON format will be  strings with NO date or date-time format in our OpenAPI specs. This means developers wanting to use our OpenAPI specs with code generators won't run into deserialization issues trying to handle MS .NET JSON format dates.

The side effect is accounting and AU payroll models now have two getter methods. For example, getDateOfBirth() returns the string "\/Date(1439434356790)\/" while getDateOfBirthAsDate() return a standard date "2020-05-14". Since you can overload methods in Java setDateOfBirth() can accept a String or a LocalDate. 

**This is a breaking change between version 3.x and 4.x.**

### Exception Handling
As we work to expand API coverage in our SDKs through new OpenAPI specs, we've discovered that error messages returned by different Xero API sets can vary greatly. Specifically, we found the format of validation errors differ enough that our current exception handling resulted in details being lost as exceptions bubbled up.


To address this we've refactored exception handling and we are deprecating the general purpose XeroApiException class and replacing it with unique exceptions. Below are the unique exception classes, but will add more as needed.

**This is a breaking change between version 3.x and 4.x.**

| code | class                         | description                                                                                                                             |
|------|-------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| N/A  | XeroException                 | All Xero exceptions extend from XeroException                                                                                           |
| N/A  | XeroAuthenticationException   | XeroUnauthorizedException and XeroUnauthorizedException extend from XeroAuthenticationException                                         |
| 400  | XeroBadRequestException       | A validation exception has occurred - typical cause invalid data.  Look at data returned for error details                              |
| 401  | XeroUnauthorizedException     | Invalid authorization credentials. Extends XeroAuthenticationException                                                                    |
| 403  | XeroForbiddenException        | Not authorized to access a resource - typical cause is problem with scopes. Extends XeroAuthenticationException                          |
| 404  | XeroNotFoundException         | The resource you have specified cannot be found                                                                                         |
| 405  | XeroMethodNotAllowedException | Method not allowed on the organisation - typical cause the API is not  available in the organisation i.e. UK Payroll on Australian org. |
| 429  | XeroRateLimitException        | The API rate limit for your organisation/application pairing has been exceeded.                                                         |
| 500  | XeroServerErrorException      | An unhandled error with the Xero API                                                                                                    |

Below is a try/catch example

```java
try {
  // Create contact with the same name as an existing contact will generate a validation error.
  Contact contact = new Contact();
  contact.setName("Sidney Maestre");
  Contacts createContact1 = accountingApi.createContact(accessToken,xeroTenantId, contact);
  Contacts createContact2 = accountingApi.createContact(accessToken,xeroTenantId, contact);
} catch (XeroBadRequestException e) {
  // 400
  // ACCOUNTING VALIDATION ERROR
  if (e.getElements() != null && e.getElements().size() > 0) {
    System.out.println("Xero Exception: " + e.getStatusCode());
    for (Element item : e.getElements()) {
      for (ValidationError err : item.getValidationErrors()) {
        System.out.println("Accounting Validation Error Msg: " + err.getMessage());
      }
    }
  // FIXED ASSETS VALIDATION ERROR
  } else if (e.getFieldValidationErrorsElements()  != null && e.getFieldValidationErrorsElements().size() > 0) {
    System.out.println("Xero Exception: " + e.getStatusCode());
    for (FieldValidationErrorsElement ele : e.getFieldValidationErrorsElements()) {
      System.out.println("Asset Field Validation Error Msg: " + ele.getDetail());
    }
  // BANKFEEDS - Statement VALIDATION ERROR
  } else if (e.getStatementItems()  != null && e.getStatementItems().size() > 0) {
    System.out.println("Xero Exception: " + e.getStatusCode());
    for (Statement statement : e.getStatementItems()) {
      System.out.println("Bank Feed - Statement Msg: " + statement.getFeedConnectionId());
      for (com.xero.models.bankfeeds.Error statementError : statement.getErrors()) {
        System.out.println("Bank Feed - Statement Error Msg: " + statementError.getDetail());
      }
    }
  // AU PAYROLL - Employee VALIDATION ERROR
  } else if (e.getEmployeeItems() != null && e.getEmployeeItems().size() > 0) {             
    System.out.println("Xero Exception: " + e.getStatusCode());
    for (com.xero.models.payrollau.Employee emp : e.getEmployeeItems()) {
      for (com.xero.models.payrollau.ValidationError err : emp.getValidationErrors()) {
        System.out.println("Payroll AU Employee Validation Error Msg: " + err.getMessage());
      }
    }
  // AU PAYROLL - Payroll Calendar VALIDATION ERROR
  } else if (e.getPayrollCalendarItems() != null && e.getPayrollCalendarItems().size() > 0) {             
    System.out.println("Xero Exception: " + e.getStatusCode());
    for (com.xero.models.payrollau.PayrollCalendar item : e.getPayrollCalendarItems()) {
      for (com.xero.models.payrollau.ValidationError err : item.getValidationErrors()) {
        System.out.println("Payroll AU Payroll Calendar Validation Error Msg: " + err.getMessage());
      }
    }
  // AU PAYROLL - PayRun VALIDATION ERROR
  } else if (e.getPayRunItems() != null && e.getPayRunItems().size() > 0) {             
    System.out.println("Xero Exception: " + e.getStatusCode());
    for (com.xero.models.payrollau.PayRun item : e.getPayRunItems()) {
      for (com.xero.models.payrollau.ValidationError err : item.getValidationErrors()) {
        System.out.println("Payroll AU Payroll Calendar Validation Error Msg: " + err.getMessage());
      }
    }
  // AU PAYROLL - SuperFund VALIDATION ERROR
  } else if (e.getSuperFundItems() != null && e.getSuperFundItems().size() > 0) {             
    System.out.println("Xero Exception: " + e.getStatusCode());
    for (com.xero.models.payrollau.SuperFund item : e.getSuperFundItems()) {
      for (com.xero.models.payrollau.ValidationError err : item.getValidationErrors()) {
        System.out.println("Payroll AU SuperFund Validation Error Msg: " + err.getMessage());
      }
    }
  // AU PAYROLL - Timesheet VALIDATION ERROR
  } else if (e.getTimesheetItems() != null && e.getTimesheetItems().size() > 0) {             
    System.out.println("Xero Exception: " + e.getStatusCode());
    for (com.xero.models.payrollau.Timesheet item : e.getTimesheetItems()) {
      for (com.xero.models.payrollau.ValidationError err : item.getValidationErrors()) {
        System.out.println("Payroll AU Timesheet Validation Error Msg: " + err.getMessage());
      }
    }
  // UK PAYROLL - PROBLEM ERROR
  } else if (e.getPayrollUkProblem() != null && ((e.getPayrollUkProblem().getDetail() != null && e.getPayrollUkProblem().getTitle() != null) || (e.getPayrollUkProblem().getInvalidFields() != null && e.getPayrollUkProblem().getInvalidFields().size() > 0)) ) {
    System.out.println("Xero Exception: " + e.getStatusCode());
    System.out.println("Problem title: " + e.getPayrollUkProblem().getTitle());
    System.out.println("Problem detail: " + e.getPayrollUkProblem().getDetail());
    if (e.getPayrollUkProblem().getInvalidFields() != null && e.getPayrollUkProblem().getInvalidFields().size() > 0) {
      for (com.xero.models.payrolluk.InvalidField field : e.getPayrollUkProblem().getInvalidFields()) {
        System.out.println("Invalid Field name: " + field.getName());
        System.out.println("Invalid Field reason: " + field.getReason());
      }
    }
  } else {
    System.out.println("Error Msg: " + e.getMessage());
  }
} catch (XeroUnauthorizedException e) {
  // 401
  System.out.println("Exception status code: " + e.getStatusCode() );
  System.out.println("Exception message: " + e.getMessage());           
} catch (XeroForbiddenException e) {
  // 403
  System.out.println("Exception status code: " + e.getStatusCode() );
  System.out.println("Exception message: " + e.getMessage());           
} catch (XeroNotFoundException e) {
  // 404
  System.out.println("Exception status code: " + e.getStatusCode() );
  System.out.println("Exception message: " + e.getMessage());           
} catch (XeroMethodNotAllowedException e) {
  // 405
  if (e.getPayrollUkProblem()  != null) {
    System.out.println("Xero Exception: " + e.getStatusCode());
    System.out.println("Problem title: " + e.getPayrollUkProblem().getTitle());
    System.out.println("Problem detail: " + e.getPayrollUkProblem().getDetail());
    if (e.getPayrollUkProblem().getInvalidFields() != null && e.getPayrollUkProblem().getInvalidFields().size() > 0) {
      for (com.xero.models.payrolluk.InvalidField field : e.getPayrollUkProblem().getInvalidFields()) {
        System.out.println("Invalid Field name: " + field.getName());
        System.out.println("Invalid Field reason: " + field.getReason());
      }
    }
  }
} catch (XeroRateLimitException e) {
  // 429
  System.out.println("Exception status code: " + e.getStatusCode() );
  System.out.println("Exception message: " + e.getMessage());             
} catch (XeroServerErrorException e) {
  // 500
  System.out.println("Exception status code: " + e.getStatusCode() );
  System.out.println("Exception message: " + e.getMessage());           
} catch (Exception e) {
  // other Exceptions 
}
```

### Logging
We've replace a specific logging plugin (org.apache.logging.log4j) with a logging facade org.slf4j. With version 4.x we'll use SLF4J and allow you to plug in the logging library of your choice at deployment time. This [blog post](https://www.baeldung.com/slf4j-with-log4j2-logback) explains how to add log4j2 for logging. To configure, add a log4j.properties file to the Resources directory.


## Looking for version 3.x of the SDK?
Codebase, samples and setup instructions located in [java-3.x branch](https://github.com/XeroAPI/Xero-Java/tree/java-3.x).

## Looking for version 2.x of the SDK with OAuth 1.0a support?
Codebase, samples and setup instructions located in [oauth1 branch](https://github.com/XeroAPI/Xero-Java/tree/oauth1).

## Getting Started

### Create a Xero App
Follow these steps to create your Xero app

* Create a [free Xero user account](https://www.xero.com/us/signup/api/) (if you don't have one)
* Login to [Xero developer center](https://developer.xero.com/myapps)
* Click "New app" button
* Enter your App name, company url, privacy policy url.
* Enter the redirect URI (this is your callback url - localhost, etc)
* Agree to terms and condition and click "Create App".
* Click "Generate a secret" button.
* Copy your client id and client secret and save for use later.
* Click the "Save" button. Your secret is now hidden.

### Add Xero-Java Dependency

Add the Xero Java SDK dependency to project via maven, gradle, sbt or other build tools can be found on [maven central](https://search.maven.org/search?q=g:com.github.xeroapi).

```xml
<dependency>
  <groupId>com.github.xeroapi</groupId>
  <artifactId>xero-java</artifactId>
  <version>4.X.X</version>
</dependency>
```

## How to use the Xero-Java SDK

Below are the code to perform the OAuth 2 flow.

### Authorization
Create your [Xero app](https://developer.xero.com/myapps) to obtain your clientId, clientSecret and set your redirectUri.  The redirectUri is your server that Xero will send a user back to once authorization is complete (aka callback url).

You can add or remove resources from the scopeList for your integration. We have a [list of all available scopes](https://developer.xero.com/documentation/oauth2/scopes).

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
	    List<Connection> connection = idApi.getConnections(tokenResponse.getAccessToken(),null);
	
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
		
		} catch (XeroBadRequestException e) {
			// 400
			// ACCOUNTING VALIDATION ERROR
			if (e.getElements() != null && e.getElements().size() > 0) {
				System.out.println("Xero Exception: " + e.getStatusCode());
				for (Element item : e.getElements()) {
					for (ValidationError err : item.getValidationErrors()) {
						System.out.println("Accounting Validation Error Msg: " + err.getMessage());
					}
				}
			}
		} catch (XeroUnauthorizedException e) {
			// 401
			System.out.println("Exception message: " + e.getMessage());
		} catch (XeroForbiddenException e) {
			// 403
			System.out.println("Exception message: " + e.getMessage());
		} catch (XeroNotFoundException e) {
			// 404
			System.out.println("Exception message: " + e.getMessage());
		} catch (XeroMethodNotAllowedException e) {
			// 405
			System.out.println("Exception message: " + e.getMessage());
		} catch (XeroRateLimitException e) {
			// 429
			System.out.println("Exception message: " + e.getMessage());             
		} catch (XeroServerErrorException e) {
			// 500
			System.out.println("Exception message: " + e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}  
	}
}
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
