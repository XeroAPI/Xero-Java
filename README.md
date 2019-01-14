# Xero-Java

[![Maven Central](https://img.shields.io/maven-central/v/com.github.xeroapi/xero-java.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.xeroapi%22%20AND%20a:%22xero-java%22)

This is the official Java SDK for Xero's API. It supports accounting, fixed asset and bank feed API endpoints. All third party libraries dependencies managed with Maven.


## Migrating from version 1.0 to 2.0 of SDK
We've made some big changes to our Java SDK with version 2.0.  All code examples in this README are for version 2.0.  We've archived [code samples for version 1.0 here](https://github.com/XeroAPI/Xero-Java/tree/master/example).

2.0 implements requests and responses for accounting API endpoints using JSON only.  Don't worry we won't be removing any of the existing methods for XML, but will mark them as deprecated in favor of JSON.

Our XSD schema files will also be deprecated in favor of [OpenAPI spec 3.0 files now available on Github](https://github.com/XeroAPI/Xero-OpenAPI).

Lastly, our trusty XeroClient class that holds methods for interacting with each endpoint will be deprecated in favor of clients for each major API group at Xero.  See below. 

### Initializing Client

1.0 Client (deprecated)
```java
XeroClient client = new XeroClient();
client.setOAuthToken(accessToken.getToken(), accessToken.getTokenSecret());				
```

2.0 Client
```java
// Accounting API endpoints
ApiClient apiClientForAccounting = new ApiClient(config.getApiUrl(),null,null,null);
AccountingApi accountingApi = new AccountingApi(apiClientForAccounting);
accountingApi.setOAuthToken(token, tokenSecret);

// Fixed Assets API endpoints
ApiClient apiClientForAssets = new ApiClient(config.getAssetsUrl(),null,null,null);
AssetApi assetApi = new AssetApi(apiClientForAssets);
assetApi.setOAuthToken(token, tokenSecret);

// BankFeeds API endpoints (for approved Partners)
ApiClient apiClientForBankFeeds = new ApiClient(config.getBankFeedsUrl(),null,null,null);
BankFeedsApi bankFeedsApi = new BankFeedsApi(apiClientForBankFeeds);
bankFeedsApi.setOAuthToken(token, tokenSecret);
```

### Making API calls will change as well.

1.0 Example GET
```java
List<Organisation> organisations = client.getOrganisations();
System.out.println("Org Name : " + organisations.get(0).getName());
```

2.0 Example GET
```java
Organisations organisations = accountingApi.getOrganisations();
System.out.println("Org Name : " + organisations.getOrganisations().get(0).getName());
```

### Models are moving

1.0 models where imported from com.xero.model.*

2.0 models are separated into major API groups under *com.xero.models* 
i.e. com.xero.models.accounting.*


## Getting Started

### Xero App Type
Start by deciding which type of Xero app you'll be building [Private](http://developer.xero.com/documentation/auth-and-limits/private-applications/), [Public](http://developer.xero.com/documentation/auth-and-limits/public-applications/), or [Partner](http://developer.xero.com/documentation/auth-and-limits/partner-applications/). Go to [http://app.xero.com](http://app.xero.com) and login with your Xero user account to create a Private or Public app (Public apps can be upgraded to Partner).

### Add Xero-Java Dependency

Add the dependency to your pom.xml.  Gradle, sbt and other build tools can be found on [maven central](https://search.maven.org/search?q=g:com.github.xeroapi).

    <dependency>
      <groupId>com.github.xeroapi</groupId>
      <artifactId>xero-java</artifactId>
      <version>2.2.6</version>
	</dependency>


### Default Configuration
The SDK uses a config.json file to manage API keys along with other configuration values.  The SDK will look for a file *config.json* in a source folder called *resources*.

To create you config.json file, login to app.xero.com. Create a Xero App, if you have not already. Under the *SDK Configuration* heading, select Java to see your config.json and copy it.  Create a new file named *config.json* paste your configuration information and save the file in a source folder named *resources*.

You can confirm your config.json file is loading properly by running the code below in your project. If successful, in the debugging console you'll see your User Agent string displayed.

```java
try {
	Config config = JsonConfig.getInstance();		
	System.out.println("Your user agent is: " + config.getUserAgent());			
} catch(Exception e) {
	System.out.println(e.getMessage());
}
```

Above is the default configuration method.  You also have the option to [customize your configuration](#custom-configuration).

#### Config.json example
You should get the minimum config.json from app.xero.com. 

Here are examples of the minimum config.json for different Xero App Types.

**Public**
```javascript
{ 
	"AppType" : "PUBLIC",
	"UserAgent": "YourAppName",
	"ConsumerKey" : "WTCXXXXXXXXXXXXXXXXXXXXXXKG",
	"ConsumerSecret" : "GJ2XXXXXXXXXXXXXXXXXXXXXXXXWZ",
	"CallbackBaseUrl" : "http://localhost:8080/myapp",
	"CallbackPath" : "/CallbackServlet"
}
```

**Private**
```javascript
{ 
	"AppType" : "PRIVATE",
	"UserAgent": "YourAppName",
	"ConsumerKey" : "CW1XXXXXXXXXXXXXXXXXXXXXXXXYG",
	"ConsumerSecret" : "SRJXXXXXXXXXXXXXXXXXXXXXXXZEA6",
	"PrivateKeyCert" :  "/certs/public_privatekey.pfx",
	"PrivateKeyPassword" :  "1234"
}
```

**Partner**
```javascript
{ 
	"AppType" : "PARTNER",
	"UserAgent": "YourAppName",
	"ConsumerKey" : "FA6UXXXXXXXXXXXXXXXXXXXXXXRC7",
	"ConsumerSecret" : "7FMXXXXXXXXXXXXXXXXXXXXXXXXXCSA",
	"CallbackBaseUrl" : "http://localhost:8080/myapp",
	"CallbackPath" : "/CallbackServlet",
	"PrivateKeyCert" :  "/certs/public_privatekey.pfx",
	"PrivateKeyPassword" :  "1234"
}
```

**Optional Attributes**

* Accept: format of data returned from API  (application/xml or application/json) *default is XML*
* ApiBaseUrl: base URL for API calls      *default is https://api.xero.com*
* ApiEndpointPath: path for API Calls      *default is /api.xro/2.0/*
* RequestTokenPath: path for Request Token      *default it /oauth/RequestToken*
* AuthenticateUrl: path for redirect to authorize      *default is /oauth/RequestToken*
* AccessTokenPath: path for Access Token         *default it /oauth/AccessToken*
* KeyStorePath: Path to your cacerts is typically inside your $JAVA_HOME/jre/lib/security/cacerts 
* KeyStorePassword: your password

### Custom Configuration

You have the option to implement your own Config class and pass it as an argument to the OAuthRequestToken, OAuthAccessToken and Api Clients (AccountingApi, AssetsApi, etc). 

An example of how you might implement Config can be found in the `/src/main/java/com/xero/example` folder named `CustomJsonConfig.java`.

```java
try {
	config = new CustomJsonConfig();
	System.out.println("Your user agent is: " + config.getUserAgent());			
} catch(Exception e) {
	System.out.println(e.getMessage());
}

ApiClient apiClientForAccounting = new ApiClient(config.getApiUrl(),null,null,null);

AccountingApi accountingApi = new AccountingApi(config);
accountingApi.setApiClient(apiClientForAccounting);
accountingApi.setOAuthToken(token, tokenSecret);
```

### Spring Framework based Configuration

An alternative method of configuring the Xero Java SDK can be found in the `example-spring/src/main/java` folder named `SpringConfig.java`.
 
This class reads the configuration from the spring `Environment` backed by the `application.properties`. This handy way of configuring the SDK
allows spring profiles to control your production and development environments.

This code should be updated and replace XeroClient with new Clients (AccountingApi, AssetsApi, etc)
```java
    @Bean
    public XeroClient xeroClient(Environment environment) {
        SpringConfig config = new SpringConfig("xero.", environment);
        XeroClient client = new XeroClient(config);
        client.setOAuthToken(config.getConsumerKey(), config.getConsumerSecret());
        return client;
    }
```

Application.properties 
```
xero.AppType=PRIVATE
xero.UserAgent=Your App Name
xero.ConsumerKey=FA6UXXXXXXXXXXXXXXXXXXXXXXRC7
xero.ConsumerSecret=7FMXXXXXXXXXXXXXXXXXXXXXXXXXCSA
xero.PrivateKeyCert=/certs/public_privatekey.pfx
xero.PrivateKeyPassword=
```

## Customize Request Signing

You can provide your own signing mechanism by using the `public AccountingApi(Config config, SignerFactory signerFactory)` constructor. Simply implement the `SignerFactory` interface with your implementation.

You can also provide a `RsaSignerFactory` using the `public RsaSignerFactory(InputStream privateKeyInputStream, String privateKeyPassword)` constructor to fetch keys from any InputStream.


## Logging
The SDK uses log4j2.  To configure, add a log4j.properties file to the Resources directory.


## How to use the Xero-Java SDK

### Example App
We've created an example app with code examples for each endpoint.  To build the example app as a WAR file, **update the config.json in example/src/main/resources directory** and from the terminal run 

```javascript
mvn clean compile war:war
```

Then deploy the Xero-Java-SDK.war found in the target directory to your Java server.


### Step by Step Video
We've created a video walking through how to create a new Eclipse project, add your dependencies and make your first API call.
[Watch this video](https://youtu.be/F3upynnpztc). 

### Hello Organization

This is the code we used in our video.

For Public & Partner Apps, you'll implement 3 legged oAuth - Private Apps can skip down to the Data Endpoints (your Consumer Key is your long lived  Access Token)

*RequestTokenServlet.java*
```java
package com.xero.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xero.api.Config;
import com.xero.api.JsonConfig;
import com.xero.api.OAuthAuthorizeToken;
import com.xero.api.OAuthRequestToken;

@WebServlet("/RequestTokenServlet")
public class RequestTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public RequestTokenServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Config config = JsonConfig.getInstance();
			
			OAuthRequestToken requestToken = new OAuthRequestToken(config);
			requestToken.execute();	
			
			TokenStorage storage = new TokenStorage();
			storage.save(response,requestToken.getAll());

			OAuthAuthorizeToken authToken = new OAuthAuthorizeToken(config, requestToken.getTempToken());
			response.sendRedirect(authToken.getAuthUrl());	
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
```

In your callback Servlet you'll read the query params and swap your temporary for your 30 min access token. 

*CallbackServlet.java*
```java
package com.xero.example;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xero.api.ApiClient;
import com.xero.api.OAuthAccessToken;
import com.xero.api.client.AccountingApi;
import com.xero.models.accounting.Organisations;
import com.xero.api.Config;
import com.xero.api.JsonConfig;

@WebServlet("/CallbackServlet")
public class CallbackServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public CallbackServlet() 
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		TokenStorage storage = new TokenStorage();
		String verifier = request.getParameter("oauth_verifier");

		try {
			Config config = JsonConfig.getInstance();
			
			OAuthAccessToken accessToken = new OAuthAccessToken(config);
			
			accessToken.build(verifier,storage.get(request,"tempToken"),storage.get(request,"tempTokenSecret")).execute();
			
			if(!accessToken.isSuccess()) {
				storage.clear(response);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				storage.save(response,accessToken.getAll());			
				
				ApiClient apiClientForAccounting = new ApiClient(config.getApiUrl(),null,null,null);
				AccountingApi accountingApi = new AccountingApi(apiClientForAccounting);
				accountingApi.setOAuthToken(accessToken.getToken(), accessToken.getTokenSecret());
		
				Organisations organisations = accountingApi.getOrganisations();
				System.out.println("Get a Organisation - Name : " + organisations.getOrganisations().get(0).getName());
				
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}	
}		
```

The TokenStorage class uses cookies to store your temporary token & secret so they can be swapped for 30 min access token & secret.  Of course, you'd want to create your own implementation to store this user information in a database.  This class is merely for demo purposes so you can trying out the SDK.

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
	
	public boolean tokenIsNull(String token) {
		if (token != null && !token.isEmpty()) { 
			return false;
		} else {
			return true;
		}
	}

	public void clear(HttpServletResponse response)
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("tempToken","");
		map.put("tempTokenSecret","");
		map.put("sessionHandle","");
		map.put("tokenTimestamp","");

		save(response,map);
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


**Data Endpoints**

The Xero Java SDK contains Client classes (AccountingApi, AssetsApi, etc) which have helper methods to perform (Create, Read, Update and Delete) actions on each endpoints.  Once you instantiate a Client class, you'll use Xero API models to interact with Java Objects.

```java
import com.xero.api.*;
import com.xero.api.ApiClient;
import com.xero.api.client.AccountingApi;
import com.xero.models.accounting.*;

// Get Xero API Resource - DEMONSTRATION ONLY get token from Cookie
TokenStorage storage = new TokenStorage();
String token = storage.get(request,"token");
String tokenSecret = storage.get(request,"tokenSecret");

// For Private Apps the token is your consumerKey and the tokenSecret is your consumerSecret
// You can get these values out of the config object above
ApiClient apiClientForAccounting = new ApiClient(config.getApiUrl(),null,null,null);
AccountingApi accountingApi = new AccountingApi(apiClientForAccounting);
accountingApi.setOAuthToken(token, tokenSecret);
		
// Get All Contacts
Contacts contactList = accountingApi.getContacts(null, null, null, null, null, null);
System.out.println("How many contacts did we find: " + contactList.getContacts().size());
				
/* CREATE ACCOUNT */
Account newAccount = new Account();
newAccount.setName("Office Expense");
newAccount.setCode("66000"));
newAccount.setType(Account.TypeEnum.EXPENSE);
Accounts newAccount = accountingApi.createAccount(newAccount);
messages.add("Create a new Account - Name : " + newAccount.getAccounts().get(0).getName());
			
/* READ ACCOUNT using a WHERE clause */
where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
Accounts accountsWhere = accountingApi.getAccounts(null, where, null);

/* READ ACCOUNT using the ID */
Accounts accountList = accountingApi.getAccounts(null, null, null);
UUID accountID = accountList.getAccounts().get(0).getAccountID();
Accounts oneAccount = accountingApi.getAccount(accountID);
							
/* UPDATE ACCOUNT */
UUID newAccountID = newAccount.getAccounts().get(0).getAccountID();
newAccount.getAccounts().get(0).setDescription("Monsters Inc.");
newAccount.getAccounts().get(0).setStatus(null);
Accounts updateAccount = accountingApi.updateAccount(newAccountID, newAccount);

/* DELETE ACCOUNT */
UUID deleteAccountID = newAccount.getAccounts().get(0).getAccountID();
Accounts deleteAccount = accountingApi.deleteAccount(deleteAccountID);
String status = deleteAccount.getAccounts().get(0).getStatus();

// GET INVOICE MODIFIED in LAST 24 HOURS
OffsetDateTime invModified = OffsetDateTime.now();
invModified.minusDays(1);	
Invoices InvoiceList24hour = accountingApi.getInvoices(invModified, null, null, null, null, null, null, null, null, null);
System.out.println("How many invoices modified in last 24 hours?: " + InvoiceList24hour.getInvoices().size());
```

**BankFeed Endpoints**

Currently, BankFeed endpoints (FeedConnection & Statements) is limited beta financial institutions who are engaged with Xero.  Once these endpoints have been enabled for your Xero Partner App, use the following pattern to make API calls.

```java
import com.xero.api.*;
import com.xero.api.ApiClient;
import com.xero.models.bankfeeds.*;
import com.xero.models.bankfeeds.Statements;
import com.xero.models.bankfeeds.FeedConnection.AccountTypeEnum;

import com.fasterxml.jackson.core.type.TypeReference;
import org.threeten.bp.LocalDate;

// Get Xero API Resource - DEMONSTRATION ONLY get token from Cookie
TokenStorage storage = new TokenStorage();
String token = storage.get(request,"token");
String tokenSecret = storage.get(request,"tokenSecret");

// Initialize the BankFeedApi object and set the token & secret
ApiClient apiClientForBankFeeds = new ApiClient(config.getBankFeedsUrl(),null,null,null);
BankFeedsApi bankFeedsApi = new BankFeedsApi(apiClientForBankFeeds);
bankFeedsApi.setOAuthToken(token, tokenSecret);
Map<String, String> params = null;

// Get ALL Feed Connections
try {
	FeedConnections fc = bankFeedsApi.getFeedConnections(null);
	System.out.println("Total Banks found: " + fc.getItems().size());
} catch (Exception e) {
	System.out.println(e.toString());
}

// Get one Feed Connection
try {
	FeedConnections fc = bankFeedsApi.getFeedConnections(null);
	FeedConnection oneFC = bankFeedsApi.getFeedConnection("123456789",null);
	System.out.println("One Bank: " + oneFC.getAccountName());
} catch (Exception e) {
	System.out.println(e.toString());
}

try {
	FeedConnection newBank = new FeedConnection();
	newBank.setAccountName("SDK Bank " + SampleData.loadRandomNum());
	newBank.setAccountNumber("1234" + SampleData.loadRandomNum());
	newBank.setAccountType(AccountTypeEnum.BANK);
	newBank.setAccountToken("foobar" + SampleData.loadRandomNum());
	newBank.setCurrency("GBP");
	
	FeedConnections arrayFeedConnections = new FeedConnections();
	arrayFeedConnections.addItemsItem(newBank);
	
	FeedConnections fc1 = bankFeedsApi.createFeedConnections(arrayFeedConnections, null);
	System.out.println("New Bank with status: " + fc1.getItems().get(0).getStatus());
} catch (Exception e) {
	System.out.println(e.toString());
}


// Create Bank Statement
// Create One Statement
try {
	Statements arrayOfStatements = new Statements();
	Statement newStatement = new Statement();
	LocalDate stDate = LocalDate.of(2018, 9, 01);
	newStatement.setStartDate(stDate);
	LocalDate endDate = LocalDate.of(2018, 9, 15);
	newStatement.endDate(endDate);
	StartBalance stBalance = new StartBalance();
	stBalance.setAmount("100");
	stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
	newStatement.setStartBalance(stBalance);
	
	EndBalance endBalance = new EndBalance();
	endBalance.setAmount("300");
	endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
	newStatement.endBalance(endBalance);
	
	FeedConnections fc = bankFeedsApi.getFeedConnections(null);
	newStatement.setFeedConnectionId(fc.getItems().get(0).getId().toString());
	
	StatementLine newStatementLine = new StatementLine();
	newStatementLine.setAmount("50");
	newStatementLine.setChequeNumber("123");
	newStatementLine.setDescription("My new line");
	newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
	newStatementLine.setReference("Foobar");
	newStatementLine.setPayeeName("StarLord");
	newStatementLine.setTransactionId("1234");
	LocalDate postedDate = LocalDate.of(2017, 9, 05);
	newStatementLine.setPostedDate(postedDate);

	StatementLines arrayStatementLines = new StatementLines();
	arrayStatementLines.add(newStatementLine);
	
	newStatement.setStatementLines(arrayStatementLines);
	arrayOfStatements.addItemsItem(newStatement);
	Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements, params);
	
	System.out.println("New Bank Statement Status: " + rStatements.getItems().get(0).getStatus());
				
} catch (Exception e) {
	// Error throw is of type Statements - it contains an array of Errors.
	TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
	Statements statementErrors =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);
	System.out.println(statementErrors.getItems().get(0).getErrors().get(0).getDetail());
}

```

**Exception Handling**

Below is an example of how how to handle errors.


```java
import com.xero.api.*;
import com.xero.api.ApiClient;
import com.xero.api.client.AccountingApi;
import com.xero.models.accounting.*;

try {
	// BAD invoice data
		
} catch (XeroApiException e) {
	System.out.println("Response Code: " + e.getResponseCode());
	System.out.println("Error Type: " + e.getError().getType());
	System.out.println("Error Number: " + e.getError().getErrorNumber());
	System.out.println("Error Message: " + e.getError().getMessage());
	if (e.getResponseCode() == 400) {
		System.out.println("Validation Message: " + e.getError().getElements().get(0).getValidationErrors().get(0).getMessage());
	}
}

```


## TLS 1.0 deprecation
As of June 30, 2018, Xero's API will remove support for TLS 1.0.  

The easiest way to force TLS 1.2 is to set the Runtime Environment for your server (Tomcat, etc) to Java 1.8 which defaults to TLS 1.2.

Those using Java 1.7 or 1.6 will need to add two attributes to the config.json file.

* KeyStorePath: Path to your cacerts is typically inside your $JAVA_HOME/jre/lib/security/cacerts 
* KeyStorePassword: your password

On a Mac your KeyStorePath value would look something like this ... 
*/Library/Java/JavaVirtualMachines/jdk1.7.0_67.jdk/Contents/Home/jre/lib/security/cacerts*

Example config.json with optional keystore attributes

```javascript
{ 
	"AppType" : "PUBLIC",
	"UserAgent": "YourAppName",
	"ConsumerKey" : "WTCXXXXXXXXXXXXXXXXXXXXXXKG",
	"ConsumerSecret" : "GJ2XXXXXXXXXXXXXXXXXXXXXXXXWZ",
	"CallbackBaseUrl" : "http://localhost:8080/myapp",
	"CallbackPath" : "/CallbackServlet",
	"KeyStorePath" : "/Library/Java/JavaVirtualMachines/jdk1.7.0_67.jdk/Contents/Home/jre/lib/security/cacerts",
	"KeyStorePassword" : "changeit"
}
```



## Acknowledgement

Special thanks to [Connectifier](https://github.com/connectifier) and [Ben Mccann](https://github.com/benmccann).  Marshalling and Unmarshalling in XeroClient was derived and extended from [Xero-Java-Client](https://github.com/connectifier/xero-java-client)
  

## License

This software is published under the [MIT License](http://en.wikipedia.org/wiki/MIT_License).

	Copyright (c) 2016 Xero Limited

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
