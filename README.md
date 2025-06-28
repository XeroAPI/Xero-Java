# Xero-Java
[![Xero-Java](https://maven-badges.herokuapp.com/maven-central/com.github.xeroapi/xero-java/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.github.xeroapi/xero-java)
[![Github forks](https://img.shields.io/github/forks/XeroAPI/Xero-Java.svg)](https://github.com/XeroAPI/Xero-Java/network)
[![Github stars](https://img.shields.io/github/stars/XeroAPI/Xero-Java.svg)](https://github.com/XeroAPI/Xero-Java/stargazers)


The **Xero-Java** SDK makes it easy for developers to access Xero's APIs in their **Java** code, and build robust applications and software using small business & general ledger accounting data.
# Table of Contents
- [API Client documentation](#api-client-documentation)
- [Sample Applications](#sample-applications)
- [Xero Account Requirements](#xero-account-requirements)
- [Installation](#installation)
- [Authentication](#authentication)
- [Custom Connections](#custom-connections)
- [App Store Subscriptions](#app-store-subscriptions)
- [API Clients](#api-clients)
- [Usage Examples](#usage-examples)
- [Running Test(s) in Local](#running-tests-in-local)
- [SDK conventions](#sdk-conventions)
- [Participating in Xero’s developer community](#participating-in-xeros-developoer-community)

<hr>

## API Client documentation
This SDK supports full method coverage for the following Xero API sets:

| API Set | Description |
| --- | --- |
| [`Accounting`](https://xeroapi.github.io/Xero-Java/v4/accounting/index.html) | The Accounting API exposes accounting functions of the main Xero application *(most commonly used)*
| [Assets](https://xeroapi.github.io/Xero-Java/v4/assets/index.html) | The Assets API exposes fixed asset related functions of the Xero Accounting application |
| [Files](https://xeroapi.github.io/Xero-Java/v4/files/index.html) | The Files API provides access to the files, folders, and the association of files within a Xero organisation |
| [Finance](https://xeroapi.github.io/Xero-Java/v4/finance/index.html) | The Finance API exposes finacial functions that may help lenders gain the confidence they need to provide capital |
| [Projects](https://xeroapi.github.io/Xero-Java/v4/projects/index.html) | Xero Projects allows businesses to track time and costs on projects/jobs and report on profitability |
| [Payroll (AU)](https://xeroapi.github.io/Xero-Java/v4/payroll_au/index.html) | The (AU) Payroll API exposes payroll related functions of the payroll Xero application |
| [Payroll (UK)](https://xeroapi.github.io/Xero-Java/v4/payroll_uk/index.html) | The (UK) Payroll API exposes payroll related functions of the payroll Xero application |
| [Payroll (NZ)](https://xeroapi.github.io/Xero-Java/v4/payroll_nz/index.html) | The (NZ) Payroll API exposes payroll related functions of the payroll Xero application |
| [Payroll (NZ)](https://xeroapi.github.io/Xero-Java/v4/bankfeeds/index.html) | The Bankfeeds API exposes Bankfeed functions - this is a restricted API - [Contact us](https://www.xero.com/partner-programs/financialweb/contact/) to get permission to use|

<img src="https://i.imgur.com/cvuZGNN.png" alt="drawing" width="350"/>

<hr>

## Sample Applications
Sample apps can get you started quickly with simple auth flows and advanced usage examples.

| Sample App | Description |
| --- | --- |
| [`starter-app`](https://github.com/XeroAPI/xero-java-oauth2-starter) | Basic getting started code samples
| [`full-app`](https://github.com/XeroAPI/xero-java-oauth2-app) | Complete app with more examples
| [`custom-connections-starter`](https://github.com/XeroAPI/xero-java-custom-connections-starter) | Basic app showing Custom Connections - a Xero [premium option](https://developer.xero.com/documentation/oauth2/custom-connections) for building M2M integrations to a single org

<hr>

## Xero Account Requirements
- Create a [free Xero user account](https://www.xero.com/us/signup/api/)
- Login to your Xero developer [dashboard](https://developer.xero.com/app/manage) and create an API application
- Copy the credentials from your API app and store them using a secure ENV variable strategy
- Decide the [necessary scopes](https://developer.xero.com/documentation/oauth2/scopes) for your app's functionality

## Installation
Add the Xero Java SDK dependency to project via maven, gradle, sbt or other build tools can be found on [maven central](https://search.maven.org/search?q=g:com.github.xeroapi).

```xml
<dependency>
  <groupId>com.github.xeroapi</groupId>
  <artifactId>xero-java</artifactId>
  <version>4.X.X</version>
</dependency>
```

---
## Authentication
All API requests go through Xero's OAuth 2.0 gateway and require a valid `access_token` to be set on the `client` which appends the `access_token` [JWT](https://jwt.io/) to the header of each request.

The code below shows how to perform the OAuth 2 authorization code flow.

1. *Authorization.java*
2. *Callback.java*
3. *TokenStorage.java*
4. *TokenRefresh.java*

Create your [Xero app](https://developer.xero.com/myapps) to obtain your clientId, clientSecret and set your redirectUri. The redirectUri is your server that Xero will send a user back to once authorization is complete (aka callback url).

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
    final String clientId = "--CLIENT-ID--";
    final String clientSecret = "--CLIENT-SECRET--";
    final String redirectURI = "http://localhost:8080/starter/Callback";
    final String TOKEN_SERVER_URL = "https://identity.xero.com/connect/token";
    final String AUTHORIZATION_SERVER_URL = "https://login.xero.com/identity/connect/authorize";
    final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    final JsonFactory JSON_FACTORY = new JacksonFactory();
    final String secretState = "secret" + new Random().nextInt(999_999);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authorization() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        
        // Save your secretState variable and compare in callback to prevent CSRF
        TokenStorage store = new TokenStorage();
        store.saveItem(response, "state", secretState);

        DataStoreFactory DATA_STORE_FACTORY = new MemoryDataStoreFactory();
        AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
                HTTP_TRANSPORT, JSON_FACTORY, new GenericUrl(TOKEN_SERVER_URL),
                new ClientParametersAuthentication(clientId, clientSecret), clientId, AUTHORIZATION_SERVER_URL)
                .setScopes(scopeList).setDataStoreFactory(DATA_STORE_FACTORY).build();

        String url = flow.newAuthorizationUrl().setClientId(clientId).setScopes(scopeList).setState(secretState)
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

import com.auth0.jwt.interfaces.DecodedJWT;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
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
    final String clientId = "--CLIENT-ID--";
    final String clientSecret = "--CLIENT-SECRET--";
    final String redirectURI = "http://localhost:8080/starter/Callback";
    final String TOKEN_SERVER_URL = "https://identity.xero.com/connect/token";
    final String AUTHORIZATION_SERVER_URL = "https://login.xero.com/identity/connect/authorize";
    final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    final JsonFactory JSON_FACTORY = new JacksonFactory();
    final ApiClient defaultClient = new ApiClient();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Callback() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = "123";
        if (request.getParameter("code") != null) {
            code = request.getParameter("code");
        }

        // Retrieve your stored secretState variable 
        TokenStorage store = new TokenStorage();
        String secretState =store.get(request, "state");
 
        // Compare to state prevent CSRF
        if (request.getParameter("state") != null && secretState.equals(request.getParameter("state").toString())) {

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
                    HTTP_TRANSPORT, JSON_FACTORY, new GenericUrl(TOKEN_SERVER_URL),
                    new ClientParametersAuthentication(clientId, clientSecret), clientId, AUTHORIZATION_SERVER_URL)
                    .setScopes(scopeList).setDataStoreFactory(DATA_STORE_FACTORY).build();

            TokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirectURI).execute();


            try {
                DecodedJWT verifiedJWT = defaultClient.verify(tokenResponse.getAccessToken());
                        
                ApiClient defaultIdentityClient = new ApiClient("https://api.xero.com", null, null, null, null);
                IdentityApi idApi = new IdentityApi(defaultIdentityClient);
                List<Connection> connection = idApi.getConnections(tokenResponse.getAccessToken(),null);
                           
                store.saveItem(response, "token_set", tokenResponse.toPrettyString());
                store.saveItem(response, "access_token", verifiedJWT.getToken());
                store.saveItem(response, "refresh_token", tokenResponse.getRefreshToken());
                store.saveItem(response, "expires_in_seconds", tokenResponse.getExpiresInSeconds().toString());
                store.saveItem(response, "xero_tenant_id", connection.get(0).getTenantId().toString());

                response.sendRedirect("./AuthenticatedResource");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid state - possible CSFR");
        }
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

public class TokenStorage {

    public TokenStorage() {
        super();
    }

    public String get(HttpServletRequest request, String key) {
        String item = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(key)) {
                    item = cookies[i].getValue();
                }
            }
        }
        return item;
    }

    public void clear(HttpServletResponse response) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("jwt_token", "");
        map.put("id_token", "");        
        map.put("access_token", "");
        map.put("refresh_token", "");
        map.put("expires_in_seconds", "");
        map.put("xero_tenant_id", "");

        save(response, map);
    }

    public void saveItem(HttpServletResponse response, String key, String value) {
        Cookie t = new Cookie(key, value);
        response.addCookie(t);
    }

    public void save(HttpServletResponse response, HashMap<String, String> map) {
        Set<Entry<String, String>> set = map.entrySet();
        Iterator<Entry<String, String>> iterator = set.iterator();

        while (iterator.hasNext()) {
            Map.Entry<?, ?> mentry = iterator.next();
            String key = (String) mentry.getKey();
            String value = (String) mentry.getValue();

            Cookie t = new Cookie(key, value);
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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
import com.xero.api.ApiClient;

public class TokenRefresh {
    final static Logger logger = LoggerFactory.getLogger(TokenRefresh.class);
    
    final String clientId = "--CLIENT-ID--";
    final String clientSecret = "--CLIENT-SECRET--";
    final String TOKEN_SERVER_URL = "https://identity.xero.com/connect/token";
    final ApiClient defaultClient = new ApiClient();

    public TokenRefresh() {
        super();
    }

    public String checkToken(String accessToken, String refreshToken, HttpServletResponse response) throws IOException {
        String currToken = null;

        try {
            DecodedJWT jwt = JWT.decode(accessToken);

            if (jwt.getExpiresAt().getTime() > System.currentTimeMillis()) {
                System.out.println("Refresh Token : NOT NEEDED - return current token");
                currToken = accessToken;
            } else {
                System.out.println("Refresh Token : BEGIN");
                try {
                    TokenResponse tokenResponse = new RefreshTokenRequest(new NetHttpTransport(), new JacksonFactory(),
                            new GenericUrl(TOKEN_SERVER_URL), refreshToken)
                            .setClientAuthentication(new BasicAuthentication(this.clientId, this.clientSecret))
                            .execute();
                    System.out.println("Refresh Token : SUCCESS");

                    try {
                        DecodedJWT verifiedJWT = defaultClient.verify(tokenResponse.getAccessToken());
                        
                        // DEMO PURPOSE ONLY - You'll need to implement your own token storage solution
                        TokenStorage store = new TokenStorage();
                        store.saveItem(response, "token_set", tokenResponse.toPrettyString());
                        store.saveItem(response, "access_token", verifiedJWT.getToken());
                        store.saveItem(response, "refresh_token", tokenResponse.getRefreshToken());
                        store.saveItem(response, "expires_in_seconds", tokenResponse.getExpiresInSeconds().toString());

                        currToken = verifiedJWT.getToken();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }           
                } catch (TokenResponseException e) {
                    System.out.println("Refresh Token : EXCEPTION");
                    if (e.getDetails() != null) {
                        System.out.println("Error: " + e.getDetails().getError());
                        if (e.getDetails().getErrorDescription() != null) {
                        	System.out.println(e.getDetails().getErrorDescription());
                        }
                        if (e.getDetails().getErrorUri() != null) {
							System.out.println(e.getDetails().getErrorUri());
                        }
                    } else {
                        System.out.println("Refresh Token : EXCEPTION");
                        System.out.println(e.getMessage());
                    }
                }
            }

        } catch (JWTDecodeException exception) {
            System.out.println("Refresh Token : INVALID TOKEN");
            System.out.println(exception.getMessage());
        }

        return currToken;
    }
}
```

It is recommended that you store this token set JSON in a datastore in relation to the user who has authenticated the Xero API connection. Each time you want to call the Xero API, you will need to access the previously generated token set, initialize it on the SDK `client`, and refresh your `access_token` prior to making API calls.

### Token Set
| key | value | description |
| --- | --- | --- |
| id_token: | "xxx.yyy.zzz" | [OpenID Connect](https://openid.net/connect/) token returned if `openid profile email` scopes accepted |
| access_token: | "xxx.yyy.zzz" | [Bearer token](https://oauth.net/2/jwt/) with a 30 minute expiration required for all API calls |
| expires_in: | 1800 | Time in seconds till the token expires - 1800s is 30m |
| refresh_token: | "XXXXXXX" | Alphanumeric string used to obtain a new Token Set w/ a fresh access_token - 60 day expiry |
| scope: | "email profile openid accounting.transactions offline_access" | The Xero permissions that are embedded in the `access_token` |

---
## Custom Connections 

Custom Connections are a Xero [premium option](https://developer.xero.com/documentation/oauth2/custom-connections) used for building M2M integrations to a single organisation. A custom connection uses OAuth 2.0's [`client_credentials`](https://www.oauth.com/oauth2-servers/access-tokens/client-credentials/) grant which eliminates the step of exchanging the temporary code for a token set.

> [Sample Application full code example](https://github.com/XeroAPI/xero-java-custom-connections-starter)

To use this SDK with a Custom Connection:
```java
final String clientId = "--CLIENT-ID--";
final String clientSecret = "--CLIENT-SECRET--";
final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
final JsonFactory JSON_FACTORY = new JacksonFactory();

ArrayList<String> appStoreScopeList = new ArrayList<String>();
appStoreScopeList.add("accounting.transactions");

// client_credentials 
TokenResponse tokenResponse = new ClientCredentialsTokenRequest(HTTP_TRANSPORT, JSON_FACTORY, 
    new GenericUrl("https://identity.xero.com/connect/token"))
    .setScopes(appStoreScopeList)
    .setClientAuthentication( new BasicAuthentication(clientId, clientSecret))
    .execute();
```

Because Custom Connections are only valid for a single organisation you don't need to set the specific `xero-tenant-id` anymore which can now simply be set as an empy string.

---

## App Store Subscriptions
If you are implementing subscriptions to participate in Xero's App Store you will need to setup [App Store subscriptions](https://developer.xero.com/documentation/guides/how-to-guides/xero-app-store-referrals/) endpoints.

When a plan is successfully purchased, the user is redirected back to the URL specified in the setup process. The Xero App Store appends the subscription Id to this URL so you can immediately determine what plan the user has subscribed to through the subscriptions API.

With your app credentials you can create a client via `client_credentials` grant_type with the `marketplace.billing` scope. This unique access_token will allow you to interact with functions in `AppStoreApi`. Client Credentials tokens to query app store endpoints will only work for apps that have completed the App Store on-boarding process.

```java
final String clientId = "--APP-STORE-CLIENT-ID--";
final String clientSecret = "--APP-STORE-CLIENT-SECRET--";
final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
final JsonFactory JSON_FACTORY = new JacksonFactory();

ArrayList<String> appStoreScopeList = new ArrayList<String>();
scopesList.add("marketplace.billing");

// client_credentials 
TokenResponse tokenResponse = new ClientCredentialsTokenRequest(HTTP_TRANSPORT, JSON_FACTORY, 
    new GenericUrl("https://identity.xero.com/connect/token"))
    .setScopes(scopesList)
    .setClientAuthentication( new BasicAuthentication(clientId, clientSecret))
    .execute();

// => /post-purchase-url?subscriptionId=03bc74f2-1237-4477-b782-2dfb1a6d8b21
AppStoreApi appStoreApi = new AppStoreApi(defaultIdentityClient);
Subscription subscription = appStoreApi.getSubscription(tokenResponse.getAccessToken(), "03bc74f2-1237-4477-b782-2dfb1a6d8b21");
```

## API Clients
You can access the different API sets and their available methods through the following:

```java
ApiClient defaultClient = new ApiClient();

// Get Singleton - instance of sub client
accountingApi = AccountingApi.getInstance(defaultClient);
assetApi = AssetApi.getInstance(defaultClient);
bankFeedsApi = BankfeedsApi.getInstance(defaultClient);
filesApi = FilesApi.getInstance(defaultClient);
projectApi = ProjectNzApi.getInstance(defaultClient);
identityApi = IdentityApi.getInstance(defaultClient);
payrollAuApi = PayrollAuApi.getInstance(defaultClient);
payrollUkApi = PayrollUkApi.getInstance(defaultClient);
payrollNzApi = PayrollNzApi.getInstance(defaultClient);
appStoreApi = AppStoreApi.getInstance(defaultClient);
```
---
## Usage Examples

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

### Revoking Token
You can revoke a user's refresh token and remove all their connections to your app by making a request to the revocation endpoint.

We've added a helpful method to the ApiClient class. The code below shows how to pass the id, secret and refresh token to execute the revoke method. Success

```java
try {
    ApiClient apiClient = new ApiClient();
    HttpResponse revokeResponse = apiClient.revoke(clientId, clientSecret, refreshToken);
    System.out.println("Revoke success: " + revokeResponse.getStatusCode());
} catch (Exception e) {
    System.out.println(e.getMessage());
}
```
## Running Test(s) in Local
For Running Test cases PRISM Mock Server needs to be started in the local machine.
Steps to Run Test(s)
* Install PRISM from npm using the command:  **npm install -g @stoplight/prism-cli**
* Verify Installation: **prism --version**
* Navigate to **Xero-Java--> src--> test--> util** folder in the terminal
* Execute the script **./start-prism.sh**
* This will start the PRISM Server in Local
* Run **mvn clean verify -DskipTests=false** to build the Java code along with Test Cases.

---
## SDK conventions

### Working with dates
Both our Accounting and AU Payroll APIs use [Microsoft .NET JSON format](https://developer.xero.com/documentation/api/accounting/requests-and-responses/#JSON) i.e. "/Date(1439434356790)/". Our other APIs use standard date formatting i.e. "2020-03-24T18:43:43.860852". Building our SDKs from OpenAPI specs with such different date formats has been challenging.

For this reason, we've decided dates in MS .NET JSON format will be strings with NO date or date-time format in our OpenAPI specs. This means developers wanting to use our OpenAPI specs with code generators won't run into deserialization issues trying to handle MS .NET JSON format dates.

The side effect is accounting and AU payroll models now have two getter methods. For example, getDateOfBirth() returns the string "/Date(1439434356790)/" while getDateOfBirthAsDate() return a standard date "2020-05-14". Since you can overload methods in Java setDateOfBirth() can accept a String or a LocalDate.

This is a breaking change between version 3.x and 4.x.

### Exception Handling
As we work to expand API coverage in our SDKs through new OpenAPI specs, we've discovered that error messages returned by different Xero API sets can vary greatly. Specifically, we found the format of validation errors differ enough that our current exception handling resulted in details being lost as exceptions bubbled up.


To address this we've refactored exception handling and we are deprecating the general purpose XeroApiException class and replacing it with unique exceptions. Below are the unique exception classes, but will add more as needed.

**This is a breaking change between version 3.x and 4.x.**

| code | class                                  | description                                                                                                                             |
|------|----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| N/A  | XeroException                          | All Xero exceptions extend from XeroException                                                                                           |
| N/A  | XeroAuthenticationException            | XeroUnauthorizedException and XeroUnauthorizedException extend from XeroAuthenticationException                                         |
| 400  | XeroBadRequestException                | A validation exception has occurred - typical cause invalid data.  Look at data returned for error details                              |
| 401  | XeroUnauthorizedException              | Invalid authorization credentials. Extends XeroAuthenticationException                                                                  |
| 403  | XeroForbiddenException                 | Not authorized to access a resource - typical cause is problem with scopes. Extends XeroAuthenticationException                         |
| 404  | XeroNotFoundException                  | The resource you have specified cannot be found                                                                                         |
| 405  | XeroMethodNotAllowedException          | Method not allowed on the organisation - typical cause the API is not  available in the organisation i.e. UK Payroll on Australian org. |
| 429  | XeroRateLimitException                 | All Xero rate limit exceptions extend XeroRateLimitException.                                                                           |
| 429  | XeroAppMinuteRateLimitException        | The API app minute rate limit for your organisation/application pairing has been exceeded.                                              |
| 429  | XeroDailyRateLimitException            | The API daily rate limit for your organisation/application pairing has been exceeded.                                                   |
| 429  | XeroMinuteRateLimitException           | The API minute rate limit for your organisation/application pairing has been exceeded.                                                  |
| 500  | XeroServerErrorException               | An unhandled error with the Xero API                                                                                                    |
| 501  | XeroNotImplementedException            | Method not implemented for the organisation                                                                                             |
| 503  | XeroNotAvailableException              | The organisation temporarily cannot be connected to or API is currently unavailable – typically due to a scheduled outage               |

Below is a try/catch example

```java
class TryCatchExample {
  
  void someOperation() {
    try {
      // Create contact with the same name as an existing contact will generate a validation error.
      Contact contact = new Contact();
      contact.setName("Test user");
      Contacts createContact1 = accountingApi.createContact(accessToken, xeroTenantId, contact);
      Contacts createContact2 = accountingApi.createContact(accessToken, xeroTenantId, contact);
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
      } else if (e.getFieldValidationErrorsElements() != null && e.getFieldValidationErrorsElements().size() > 0) {
        System.out.println("Xero Exception: " + e.getStatusCode());
        for (FieldValidationErrorsElement ele : e.getFieldValidationErrorsElements()) {
          System.out.println("Asset Field Validation Error Msg: " + ele.getDetail());
        }
        // BANKFEEDS - Statement VALIDATION ERROR
      } else if (e.getStatementItems() != null && e.getStatementItems().size() > 0) {
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
      } else if (e.getPayrollUkProblem() != null &&
          ((e.getPayrollUkProblem().getDetail() != null && e.getPayrollUkProblem().getTitle() != null) ||
              (e.getPayrollUkProblem().getInvalidFields() != null &&
                  e.getPayrollUkProblem().getInvalidFields().size() > 0))) {
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
      System.out.println("Exception status code: " + e.getStatusCode());
      System.out.println("Exception message: " + e.getMessage());
    } catch (XeroForbiddenException e) {
      // 403
      System.out.println("Exception status code: " + e.getStatusCode());
      System.out.println("Exception message: " + e.getMessage());
    } catch (XeroNotFoundException e) {
      // 404
      System.out.println("Exception status code: " + e.getStatusCode());
      System.out.println("Exception message: " + e.getMessage());
    } catch (XeroMethodNotAllowedException e) {
      // 405
      if (e.getPayrollUkProblem() != null) {
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
    } catch (XeroAppMinuteRateLimitException | XeroDailyRateLimitException | XeroMinuteRateLimitException e) {
      // 429
      System.out.println("Exception status code: " + e.getStatusCode());
      System.out.println("Exception message: " + e.getMessage());
      System.out.println("Remaining app minute limit: " + e.getAppLimitRemaining());
      System.out.println("Remaining daily limit: " + e.getDayLimitRemaining());
      System.out.println("Remaining minute limit: " + e.getMinuteLimitRemaining());
      System.out.println("Retry after seconds: " + e.getRetryAfterSeconds());
    } catch (XeroRateLimitException e) {
      // 429
      System.out.println("Exception status code: " + e.getStatusCode());
      System.out.println("Exception message: " + e.getMessage());
      System.out.println("Remaining app minute limit: " + e.getAppLimitRemaining());
      System.out.println("Remaining daily limit: " + e.getDayLimitRemaining());
      System.out.println("Remaining minute limit: " + e.getMinuteLimitRemaining());
      System.out.println("Retry after seconds: " + e.getRetryAfterSeconds());
    } catch (XeroServerErrorException e) {
      // 500
      System.out.println("Exception status code: " + e.getStatusCode());
      System.out.println("Exception message: " + e.getMessage());
    } catch (Exception e) {
      // other Exceptions 
    }
  }
}
```

### Logging
We've replace a specific logging plugin (org.apache.logging.log4j) with a logging facade org.slf4j. With version 4.x we'll use SLF4J and allow you to plug in the logging library of your choice at deployment time. This [blog post](https://www.baeldung.com/slf4j-with-log4j2-logback) explains how to add log4j2 for logging. To configure, add a log4j.properties file to the Resources directory.

### Looking for version 3.x of the SDK?
Codebase, samples and setup instructions located in [java-3.x branch](https://github.com/XeroAPI/Xero-Java/tree/java-3.x).

---
## Participating in Xero’s developer community

This SDK is one of a number of SDK’s that the Xero Developer team builds and maintains. We are grateful for all the contributions that the community makes. 

Here are a few things you should be aware of as a contributor:
* Xero has adopted the Contributor Covenant [Code of Conduct](https://github.com/XeroAPI/Xero-Java/blob/master/CODE_OF_CONDUCT.md), we expect all contributors in our community to adhere to it
* If you raise an issue then please make sure to fill out the Github issue template, doing so helps us help you 
* You’re welcome to raise PRs. As our SDKs are generated we may use your code in the core SDK build instead of merging your code
* We have a [contribution guide](https://github.com/XeroAPI/Xero-Java/blob/master/CONTRIBUTING.md) for you to follow when contributing to this SDK
* Curious about how we generate our SDK’s? Have a [read of our process](https://devblog.xero.com/building-sdks-for-the-future-b79ff726dfd6) and have a look at our [OpenAPISpec](https://github.com/XeroAPI/Xero-OpenAPI)
* This software is published under the [MIT License](https://github.com/XeroAPI/Xero-Java/blob/master/LICENSE)

For questions that aren’t related to SDKs please refer to our [developer support page](https://developer.xero.com/support/).

### Contributing
PRs, issues, and discussion are highly appreciated and encouraged. Note that the majority of this project is generated code based on [Xero's OpenAPI specs](https://github.com/XeroAPI/Xero-OpenAPI) - PR's will be evaluated and pre-merge will be incorporated into the root generation templates.
### Versioning
We do our best to keep OS industry `semver` standards, but we can make mistakes! If something is not accurately reflected in a version's release notes please let the team know.
