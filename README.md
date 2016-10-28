# Xero-Java
A skinny Java wrapper of the Xero API. Supports Accounting API. All third party libraries dependencies managed with Maven

### Xero App
You'll need to decide which type of Xero app you'll be building (Public, Private or Partner). Go to http://app.xero.com to login and create your app.

### config.json 
Located in src/main/resources is the config.json file.  There are examples for public, private and partner - but the Config.java will look in this folder at the config.json file in order to initialize you Java code. 

Below are the unique values you'll set in config.json for each type of Xero app. 

**Public Xero App**

* Copy and paste, Consumer Key & Secret from app.xero.com
* Set your callback url at app.xero.com to match CallbackBaseUrl
* Set your CallbackPath - this is appended to the CallbackBaseUrl


**Private Xero App**

* Copy and paste, Consumer Key & Secret from app.xero.com
* Upload the public.cer file at app.xero.com
* Copy the public_privatekey.pfx file created with OpenSSL in the certs folder
[Public Private Key Docs](https://developer.xero.com/documentation/advanced-docs/public-private-keypair/)
* Set the private key password


**Partner Xero App**

* Copy and paste, Consumer Key & Secret from app.xero.com
* Set your callback url at app.xero.com to match CallbackBaseUrl
* Set your CallbackPath - this is appended to the CallbackBaseUrl
* Upload the public.cer file at app.xero.com
* Copy the public_privatekey.pfx file created with OpenSSL in the certs folder
[Public Private Key Docs](https://developer.xero.com/documentation/advanced-docs/public-private-keypair/)
* Set the Private key password
* Copy the xero-entrus.p12 file set to you by Xero API team.
[Entrust Certificates Docs](https://developer.xero.com/documentation/getting-started/partner-applications/#certificates)
* Set the Entrust Certifcate password

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