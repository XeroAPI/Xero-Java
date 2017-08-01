# Xero-Java

This is the Xero Java SDK for the Xero API. Currently, supports Accounting API. All third party libraries dependencies managed with Maven

### Xero App
You'll need to decide which type of Xero app you'll be building [Private](http://developer.xero.com/documentation/auth-and-limits/private-applications/), [Public](http://developer.xero.com/documentation/auth-and-limits/public-applications/), or [Partner](http://developer.xero.com/documentation/auth-and-limits/partner-applications/). Go to [http://app.xero.com](http://app.xero.com) and login with your Xero user account to create an app.

### Questions?
[Watch this video](https://youtu.be/19UBlTDLxVc) walkthrough of how to setup Xero Java SDK in Eclipse.


### Download Xero Java SDK

Add this dependency and repository to your POM.xml

    <dependency>
	  <groupId>com.xero</groupId>
	  <artifactId>xero-java-sdk</artifactId>
	  <version>0.4.7</version>
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

#### Working with sbt?
We have a build.sbt file defined in the root.


### Configure
The Xero Java SDK is easily configured using an external JSON file to configure values unique to your Application. This is the default configuration method, however you can implement the `Config` interface and pass it to the `XeroClient`.

We include an Example App (in this repo) built using Eclipse.  We started with a Maven Project and selected the maven-archetype-webapp with the setup Wizard.   This created a web application structure good for use with Servlets & JSPs.  By default a src/main/resources directory is added to the project.  **Place the config.json file you create in the resources directory**. 

The Xero Java SDK JsonConfig.java class parses the JSON file from the resources directory using the following bit of code.

```java
final ClassLoader loader = Config.class.getClassLoader();
URL path = loader.getResource("config.json");
File f = new File(path.getFile());
```

### How to Create the config.json file
In a text editor, create a file called config.json (examples are below)  Refer to Xero Developer Center [Getting Started](http://developer.xero.com/documentation/getting-started/getting-started-guide/) when you are ready to create a Xero App - this is how you'll create a Consumer Key and Secret. Private and Partner apps require a [public/private key pair](http://developer.xero.com/documentation/api-guides/create-publicprivate-key/) you'll create using OpenSSL.  The private key should be exported as a pfx file and in our example we create a "certs" folder inside the resources folder and place it there.

**Public Application**
```javascript
{ 
	"AppType" : "PUBLIC",
	"UserAgent": "Your App Name",
	"ConsumerKey" : "WTCXXXXXXXXXXXXXXXXXXXXXXKG",
	"ConsumerSecret" : "GJ2XXXXXXXXXXXXXXXXXXXXXXXXWZ",
	"CallbackBaseUrl" : "http://localhost:8080/myapp",
	"CallbackPath" : "/CallbackServlet"
}
```

**Private Application**
```javascript
{ 
	"AppType" : "PRIVATE",
	"UserAgent": "Your App Name",
	"ConsumerKey" : "CW1XXXXXXXXXXXXXXXXXXXXXXXXYG",
	"ConsumerSecret" : "SRJXXXXXXXXXXXXXXXXXXXXXXXZEA6",
	"PrivateKeyCert" :  "certs/public_privatekey.pfx",
	"PrivateKeyPassword" :  "1234"
}
```
**Partner Application**
```javascript
{ 
	"AppType" : "PARTNER",
	"UserAgent": "Your App Name",
	"ConsumerKey" : "FA6UXXXXXXXXXXXXXXXXXXXXXXRC7",
	"ConsumerSecret" : "7FMXXXXXXXXXXXXXXXXXXXXXXXXXCSA",
	"CallbackBaseUrl" : "http://localhost:8080/myapp",
	"CallbackPath" : "/CallbackServlet",
	"PrivateKeyCert" :  "certs/public_privatekey.pfx",
	"PrivateKeyPassword" :  "1234"
}
```

**Optionals Attributes**

* UserAgent: for debugging by Xero API team (unique string)
* Accept: format of data returned from API  (application/xml or application/json) *default is XML*
* ApiBaseUrl: base URL for API calls      *default is https://api.xero.com*
* ApiEndpointPath: path for API Calls      *default is /api.xro/2.0/*
* RequestTokenPath: path for Request Token      *default it /oauth/RequestToken*
* AuthenticateUrl: path for redirect to authorize      *default is /oauth/RequestToken*
* AccessTokenPath: path for Access Token         *default is https://api.xero.com/oauth/Authorize*

### Spring Framework based Config

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
xero.PrivateKeyCert=certs/public_privatekey.pfx
xero.PrivateKeyPassword=
```

### Example App 
This repo includes an Example App mentioned above.  The file structure mirrors that of an Eclipse Maven Project with the maven-archetype-webapp

* src/main/java contains the com.xero.example package and the servlets for handling oAuth and sample API calls
* src/main/resource contains examples of config.json files
* src/main/webapp contains index and callback JSP files along with web.xml mappings for Servlets


**oAuth Flow**

For Public & Partner Apps, you'll implement 3 legged oAuth - Private Apps can skip down to the Data Endpoints (your Consumer Key will act as your permenent Access Token)

```java
private Config config = JsonConfig.getInstance();

// Start by requesting a temporary token from Xero
OAuthRequestToken requestToken = new OAuthRequestToken(config);
requestToken.execute();

// DEMONSTRATION ONLY - Store in Cookie - you can extend TokenStorage
// and implement the save() method for your database
TokenStorage storage = new TokenStorage();
storage.save(response,requestToken.getAll());

//Build the Authorization URL and redirect User
OAuthAuthorizeToken authToken = new OAuthAuthorizeToken(requestToken.getTempToken());
response.sendRedirect(authToken.getAuthUrl());	
```

In your callback Servlet you'll read the query params and swap your temporary for your 30 min access token.  In our example, we forward the user to the callback.jsp if successful.

```java
private Config config = JsonConfig.getInstance(); 


// DEMONSTRATION ONLY - retrieve TempToken from Cookie
TokenStorage storage = new TokenStorage();

// retrieve OAuth verifier code from callback URL param
String verifier = request.getParameter("oauth_verifier");

// Swap your temp token for 30 oauth token
OAuthAccessToken accessToken = new OAuthAccessToken(config);
accessToken.build(verifier,storage.get(request,"tempToken"),storage.get(request,"tempTokenSecret")).execute();

// Check if your Access Token call successful
if(!accessToken.isSuccess())
{
	storage.clear(response);
	request.getRequestDispatcher("index.jsp").forward(request, response);
}
else 
{
	// DEMONSTRATION ONLY - Store in Cookie - you can extend TokenStorage
	// and implement the save() method for your database
	storage.save(response,accessToken.getAll());			
	request.getRequestDispatcher("callback.jsp").forward(request, response);			
}		
```


In your callback.jsp, you can have a link to access some data resources.


**Data Endpoints**

The Xero Java SDK contains XeroClient which has helper methods to perform (Create, Read, Update and Delete) actions on each endpoints.  Once you instantiate XeroClient, you'll use Xero API schema classes to interact with Java Objects.

```java
import com.xero.api.*;
import com.xero.model.*;

private Config config = JsonConfig.getInstance();

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

We've added better support for exception handling when errors are returned from the API.  We've tested 400, 401, 404, 500 and 503 errors.  This is still underdevelopment - if your find ways to improve error handling, please submit a pull request or file an issue with details around your suggestion.  Below is an example of how how to handle error.

```java
import com.xero.api.*;
import com.xero.model.*;

// FORCE a 404 Error - there is no contact wtih ID 1234	
try {
	Contact ContactOne = client.getContact("1234");
	messages.add("Get a single Contact - ID : " + ContactOne.getContactID());
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
