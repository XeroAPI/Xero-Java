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
	  <version>1.0.0</version>
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
The Xero Java SDK is configured using a config.json file to provide API Keys and other values unique to your Application. This is the default configuration method, however you can implement the `Config` interface and pass it to the `XeroClient`.  


### Example App
To build the example app as a WAR file, **update the config.json in example/src/main/resources directory** and from the terminal run 

```javascript
mvn clean compile war:war
```
Then deploy the Xero-Java-SDK.war found in the target directory to your Java server.


### How to Create the config.json file
In a text editor, create a file called config.json (examples are below)  Refer to Xero Developer Center [Getting Started](http://developer.xero.com/documentation/getting-started/getting-started-guide/) when you are ready to create a Xero App - this is how you'll create a Consumer Key and Secret. Private and Partner apps require a [public/private key pair](http://developer.xero.com/documentation/api-guides/create-publicprivate-key/) you'll create using OpenSSL.  The private key should be exported as a pfx file and in our example we create a "certs" folder inside the resources folder and place it there.

**Public Application**
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

**Private Application**
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
**Partner Application**
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

**Optionals Attributes**

* UserAgent: for debugging by Xero API team (unique string)
* Accept: format of data returned from API  (application/xml or application/json) *default is XML*
* ApiBaseUrl: base URL for API calls      *default is https://api.xero.com*
* ApiEndpointPath: path for API Calls      *default is /api.xro/2.0/*
* RequestTokenPath: path for Request Token      *default it /oauth/RequestToken*
* AuthenticateUrl: path for redirect to authorize      *default is /oauth/RequestToken*
* AccessTokenPath: path for Access Token         *default is https://api.xero.com/oauth/Authorize*
* KeyStorePath: Path to your cacerts is typically inside your $JAVA_HOME/jre/lib/security/cacerts 
* KeyStorePassword: your password

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


## Custom Request Signing

You can provide your own signing mechanism by using the `public XeroClient(Config config, SignerFactory signerFactory)` constructor. Simply implement the `SignerFactory` interface with your implementation.

You can also provide a `RsaSignerFactory` using the `public RsaSignerFactory(InputStream privateKeyInputStream, String privateKeyPassword)` constructor to fetch keys from any InputStream.

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
xero.PrivateKeyCert=/certs/public_privatekey.pfx
xero.PrivateKeyPassword=
```


## Logging
The SDK includes a log4j.  This [post gives you the basics](https://www.mkyong.com/logging/log4j-hello-world-example/) on log4j.

To configure, add a log4j.properties file to the Resources directory.

* Update log4j.rootLogger attribute to change the level of detail and where you want the data output.
* Update log4j.appender.file.File attribute to change where you want the log file saved on your local filesystem.

log4j.properties
```
# Root logger option
log4j.rootLogger=DEBUG, stdout, file

# Redirect log messages to console
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

# Redirect log messages to a log file, support file rolling.
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=/Users/sid.maestre/logs/log4j-application.log
log4j.appender.file.MaxFileSize=5MB
log4j.appender.file.MaxBackupIndex=10
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n
```


## oAuth Flow

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
OAuthAuthorizeToken authToken = new OAuthAuthorizeToken(config,requestToken.getTempToken());
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
