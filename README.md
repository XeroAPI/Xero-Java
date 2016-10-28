# Xero-Java
This is the official Java SDK for the Xero API. Currently, supports Accounting API. All third party libraries dependencies managed with Maven

### Xero App
You'll need to decide which type of Xero app you'll be building (Public, Private or Partner). Go to [http://app.xero.com](http://app.xero.com) and login with your Xero user account and create an app.

### config.json 
Located in src/main/resources is the config.json file.  There are examples for public, private and partner - but the Config.java will look in this folder at the config.json file in order to initialize your Java code. 

Here is an example of config.json for a Partner App.

```javascript
{ 
	"AppType" : "PARTNER",
	"UserAgent" : "Xero-Java",
	"Accept" : "application/xml", 
	"SignatureMethod" : "RSA-SHA1",
	"ConsumerKey" : "Z7DLBXSOMSQI9GMUHZB8RY6ZXHTTYC",
	"ConsumerSecret" : "71K7QL8TKH7CKDVE6TLY02CTQOX36S",
	"ApiBaseUrl" : "https://api-partner.network.xero.com",
	"ApiEndpointPath" : "/api.xro/2.0/",
	"RequestTokenPath": "/oauth/RequestToken",
	"AuthenticateUrl" : "https://api.xero.com/oauth/Authorize",
	"AccessTokenPath"  : "/oauth/AccessToken",
	"CallbackBaseUrl" : "https://tranquil-falls-53784.herokuapp.com",
	"CallbackPath" : "/CallbackServlet",
	"PrivateKeyCert" :  "certs/public_privatekey.pfx",
	"PrivateKeyPassword" :  "1234",
	"EntrustCert" : "certs/xero-entrust-20170513.p12",
	"EntrustCertPassword" : "123456"
}
```

Below are the possible attributes for each App Type. 

| App Type			    | Attribute             | Purpose                               | Valid Options 
| --------------------- | --------------------- |---------------------------------------| -------------
| ALL				    | AppType               |  Defines your app type                | PUBLIC or PRIVATE or PARTNER  
| ALL					| UserAgent             |  for debugging by Xero API ssues      | unique string
| ALL					| Accept                |  format of data returned from API     | application/xml or application/json
| ALL		    		| ConsumerKey           |  for oAuth Signature                  | App Key created at app.xero.com
| ALL					| ConsumerSecret        |  for oAuth Signature       			| App Secret created at app.xero.com
| ALL					| ApiBaseUrl            |  base URL for API calls               | https://api.xero.com or https://api-partner.network.xero.com
| ALL					| ApiEndpointPath       |  path for API Calls                   | /api.xro/2.0/
| Public or Partner		| RequestTokenPath      |  path for Request Token               | /oauth/RequestToken
| Public or Partner 	| AuthenticateUrl       |  path for redirect to authorize       | /oauth/RequestToken
| Public or Partner 	| AccessTokenPath       |  path for Access Token                | https://api.xero.com/oauth/Authorize
| Public or Partner 	| CallbackBaseUrl       |  base URL for Callback url            | unique string
| Public or Partner 	| CallbackPath          |  path for Callback url                | unique string
| Private or Partner	| PrivateKeyCert        |  path to [Private Key Certificate](https://developer.xero.com/documentation/advanced-docs/public-private-keypair/)      | unique string
| Private or Partner	| PrivateKeyPassword    |  password for Private key             | unique string
| Partner				| EntrustCert           |  path to [Entrust Certificate](https://developer.xero.com/documentation/getting-started/partner-applications/#certificates)    | unique string
| Partner				| EntrustCertPassword   |  password for Entrust certificate     | unique string


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

```

### Maven Dependencies 

The pom.xml file contains two library dependencies.

```xml
<dependency>
	<groupId>com.google.oauth-client</groupId>
	<artifactId>google-oauth-client</artifactId>
	<version>1.20.0</version>
</dependency>
<dependency>
	<groupId>com.googlecode.json-simple</groupId>
	<artifactId>json-simple</artifactId>
	<version>1.1.1</version>
</dependency>
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