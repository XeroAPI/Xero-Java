# Xero-Java

This is the Java SDK for the Xero API. Currently, supports Accounting API. All third party libraries dependencies managed with Maven

## Getting Started

### Xero App Type
Start by deciding which type of Xero app you'll be building [Private](http://developer.xero.com/documentation/auth-and-limits/private-applications/), [Public](http://developer.xero.com/documentation/auth-and-limits/public-applications/), or [Partner](http://developer.xero.com/documentation/auth-and-limits/partner-applications/). Go to [http://app.xero.com](http://app.xero.com) and login with your Xero user account to create a Private or Public app (Public apps can be upgraded to Partner).

### Add Xero-Java Dependency

For those using maven, add the dependency and repository to your pom.xml

    <dependency>
	  <groupId>com.xero</groupId>
	  <artifactId>xero-java-sdk</artifactId>
	  <version>1.0.9</version>
	</dependency>

    <repositories>
      <repository>
        <id>xero-java-mvn-repo</id>
	    <url>https://raw.github.com/XeroAPI/Xero-Java/mvn-repo/</url>
	    <snapshots>
	      <enabled>true</enabled>
	      <updatePolicy>always</updatePolicy>
	    </snapshots>
      </repository>
    </repositories>

### Where are the jars?

You can download the latest build as a jar file from the *mvn-repo* branch of this project.

### Working with sbt?
We have a build.sbt file defined in the root of this project.

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

Here is an examples of the minimum config.json for different Xero App Types.

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

You have the option to implement your own Config class and pass it as an argument to the OAuthRequestToken, OAuthAccessToken and XeroClient constructors. 

An example of how you might implement Config can be found in the `/src/main/java/com/xero/example` folder named `CustomJsonConfig.java`.

### Spring Framework based Configuration

An alternative method of configuring the Xero Java SDK can be found in the `example-spring/src/main/java` folder named `SpringConfig.java`.
 
This class reads the configuration from the spring `Environment` backed by the `application.properties`. This handy way of configuring the SDK
allows spring profiles to control your production and development environments.

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

You can provide your own signing mechanism by using the `public XeroClient(Config config, SignerFactory signerFactory)` constructor. Simply implement the `SignerFactory` interface with your implementation.

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
[Watch this video](https://youtu.be/19UBlTDLxVc). New Video is development

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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xero.api.OAuthAccessToken;
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
				
				XeroClient client = new XeroClient();
				client.setOAuthToken(accessToken.getToken(), accessToken.getTokenSecret());
				
				List<Organisation> newOrganisation = client.getOrganisations();
				System.out.println("Get a Organisation - Name : " + newOrganisation.get(0).getName());		
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}	
}		
```

The TokenStorage class uses cookies to store your temporary token & secret so they can be swapped for 30 min access token & secret.  Of course, you'd want to create your own implmentation to store this user information in a database.  This class is merely for demo purposes so you can trying out the SDK.

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

The Xero Java SDK contains XeroClient which has helper methods to perform (Create, Read, Update and Delete) actions on each endpoints.  Once you instantiate XeroClient, you'll use Xero API schema classes to interact with Java Objects.

```java
import com.xero.api.*;
import com.xero.model.*;

// Get Xero API Resource - DEMONSTRATION ONLY get token from Cookie
TokenStorage storage = new TokenStorage();
String token = storage.get(request,"token");
String tokenSecret = storage.get(request,"tokenSecret");

// For Private Apps the token is your consumerKey and the tokenSecret is your consumerSecret
// You can get these values out of the config object above
XeroClient client = new XeroClient();
client.setOAuthToken(token, tokenSecret);

// Get All Contacts
List<Contact> contactList = client.getContacts();
System.out.println("How many contacts did we find: " + contactList.size());
				
/* CREATE ACCOUNT */
ArrayOfAccount accountArray = new ArrayOfAccount();
Account account = new Account();
account.setCode("66000");
account.setName("Office Expense");
account.setType(AccountType.EXPENSE);
accountArray.getAccount().add(account);
List<Account> newAccount = client.createAccounts(accountArray);
			
/* READ ACCOUNT using a WHERE clause */
List<Account> accountWhere = client.getAccounts(null,"Type==\"BANK\"",null);

/* READ ACCOUNT using the ID */
List<Account> accountList = client.getAccounts();
Account accountOne = client.getAccount(accountList.get(0).getAccountID());
			
/* UPDATE ACCOUNT */
newAccount.get(0).setName("Entertainment");
newAccount.get(0).setStatus(null);
List<Account> updateAccount = client.updateAccount(newAccount);

/* DELETE ACCOUNT */
String status = client.deleteAccount(newAccount.get(0).getAccountID());

// GET INVOICE MODIFIED in LAST 24 HOURS
Date date = new Date();
Calendar cal = Calendar.getInstance();
cal.setTime(date);
cal.add(Calendar.DAY_OF_MONTH, -1);
		    
List<Invoice> InvoiceList24hour = client.getInvoices(cal.getTime(),null,null);
System.out.println("How many invoices modified in last 24 hours?: " + InvoiceList24hour.size());
```

**Exception Handling**

Below is an example of how how to handle errors.

```java
import com.xero.api.*;
import com.xero.model.*;

// FORCE a 404 Error - there is no contact wtih ID 1234	
try {
	Contact ContactOne = client.getContact("1234");
	System.out.println("Get a single Contact - ID : " + ContactOne.getContactID());
} catch (XeroApiException e) {
	System.out.println(e.getResponseCode());
	System.out.println(e.getMessage());	
}	


// FORCE a 503 Error - try to make more than 60 API calls in a minute to trigger rate limit error.
List<Contact> ContactList = client.getContacts();
int num = SampleData.findRandomNum(ContactList.size());			
try {
	for(int i=65; i>1; i--){
		Contact ContactOne = client.getContact(ContactList.get(num).getContactID());
	}
	System.out.println("Congrats - you made over 60 calls without hitting rate limit");
} catch (XeroApiException e) {
	System.out.println(e.getResponseCode());
	System.out.println(e.getMessage());  
}		

```

Version 0.6.2 adds improved ApiException handling if you are creating multiple Invoices and pass the SummarizeError=true parameter - Thanks Lance Reid (lancedfr)


```java
import com.xero.api.*;
import com.xero.model.*;

try {
	List<Invoice> listOfInvoices = new ArrayList<Invoice>();
	listOfInvoices.add(SampleData.loadBadInvoice().getInvoice().get(0));
	listOfInvoices.add(SampleData.loadBadInvoice2().getInvoice().get(0));
	
	List<Invoice> newInvoice = client.createInvoices(listOfInvoices,null,true);
	System.out.println("Create a new Invoice ID : " + newInvoice.get(0).getInvoiceID() + " - Reference : " +newInvoice.get(0).getReference());
		
} catch (XeroApiException e) {
	System.out.println(e.getResponseCode());

	List<Elements> elements = e.getApiException().getElements();
	Elements element = elements.get(0);
 	List<Object> dataContractBase = element.getDataContractBase();
	for (Object dataContract : dataContractBase) {
		Invoice failedInvoice = (Invoice) dataContract;
		ArrayOfValidationError validationErrors = failedInvoice.getValidationErrors();
            
		System.out.println("Failure message : " + errors.get(0).getMessage());
		System.out.println("Failure invoice Num : " + failedInvoice.getInvoiceNumber());
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
