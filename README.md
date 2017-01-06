# Xero-Java
This is the official Java SDK for the Xero API. Currently, only supports the Accounting API. Additional third party libraries dependencies managed with Maven

### Xero App
You'll need to decide which type of Xero app you'll be building (Public, Private or Partner). Go to [http://app.xero.com](http://app.xero.com) and login with your Xero user account and create an app.

### Downloading
This library is available from a branch of this github repository at the moment.  

Add this dependency to your project POM.xml

    <dependency>
	  <groupId>com.xero</groupId>
	  <artifactId>xero-java-sdk</artifactId>
	  <version>0.0.1</version>
	</dependency>

Add this repository to your project POM.xml

    <repositories>
      <repository>
        <id>xero-java-mvn-repo</id>
	    <url>https://raw.github.com/SidneyAllen/Xero-Java/mvn-repo/</url>
	    <snapshots>
	      <enabled>true</enabled>
	      <updatePolicy>always</updatePolicy>
	    </snapshots>
      </repository>
    </repositories>


### Configure
Create a file called config.json and place it in the src/main/resources directory. Config.java will look in this folder for config.jsonin order to initialize your Xero keys and secrets. 

Below are required attributes for the config.json for each Xero Application Type

**Public Application**
```javascript
{ 
	"AppType" : "PUBLIC",
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
	"ConsumerKey" : "FA6UXXXXXXXXXXXXXXXXXXXXXXRC7",
	"ConsumerSecret" : "7FMXXXXXXXXXXXXXXXXXXXXXXXXXCSA",
	"CallbackBaseUrl" : "http://localhost:8080/myapp",
	"CallbackPath" : "/CallbackServlet",
	"PrivateKeyCert" :  "certs/public_privatekey.pfx",
	"PrivateKeyPassword" :  "1234"
}
```


**Additional Optional Attributes**

UserAgent: for debugging by Xero API team (unique string)
Accept: format of data returned from API  (application/xml or application/json) *default is XML*
ApiBaseUrl: base URL for API calls      *default is https://api.xero.com*
ApiEndpointPath: path for API Calls      *default is /api.xro/2.0/*
RequestTokenPath: path for Request Token      *default it /oauth/RequestToken*
AuthenticateUrl: path for redirect to authorize      *default is /oauth/RequestToken*
AccessTokenPath: path for Access Token         *default is https://api.xero.com/oauth/Authorize*


### Xero Model
We've included a complete set of classes in `com.xero.model`.  These are generated from the  [Xero Schema XSDs](https://github.com/XeroAPI/XeroAPI-Schemas).  You can always download and generate updated classes in the future to replace the ones included in this project.

### Xero Client 
We've included the XeroClient with methods to perform each action supported by endpoints.  Once you instantiate XeroClient, you can begin using classes from the model directory to create, read, update and delete data through Xero's API.

```java
XeroClient client = new XeroClient(request, response);
		
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
System.out.printlin("How many invoices modified in last 24 hours?: " + InvoiceList24hour.size());

```


##License

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