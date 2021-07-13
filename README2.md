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
- [Configuration](#configuration)
- [Authentication](#authentication)
- [Custom Connections](#custom-connections)
- [API Clients](#api-clients)
- [Helper Methods](#helper-methods)
- [Usage Examples](#usage-examples)
- [SDK conventions](#sdk-conventions)
- [Participating in Xero’s developer community](#participating-in-xeros-developoer-community)

<hr>

## API Client documentation
This SDK supports full method coverage for the following Xero API sets:

| API Set | Description |
| --- | --- |
| [`Accounting`](https://xeroapi.github.io/Xero-Java/v4/accounting/index.html) | The Accounting API exposes accounting functions of the main Xero application *(most commonly used)*
| [Assets](https://xeroapi.github.io/Xero-Java/assets/index.html) | The Assets API exposes fixed asset related functions of the Xero Accounting application |
| [Files](https://xeroapi.github.io/Xero-Java/files/index.html) | The Files API provides access to the files, folders, and the association of files within a Xero organisation |
| [Projects](https://xeroapi.github.io/Xero-Java/projects/index.html) | Xero Projects allows businesses to track time and costs on projects/jobs and report on profitability |
| [Payroll (AU)](https://xeroapi.github.io/Xero-Java/payroll_au/index.html) | The (AU) Payroll API exposes payroll related functions of the payroll Xero application |
| [Payroll (UK)](https://xeroapi.github.io/Xero-Java/payroll_uk/index.html) | The (UK) Payroll API exposes payroll related functions of the payroll Xero application |
| [Payroll (NZ)](https://xeroapi.github.io/Xero-Java/payroll_nz/index.html) | The (NZ) Payroll API exposes payroll related functions of the payroll Xero application |

**TODO: Image of Docs**

<img src="https://i.imgur.com/0MsvkGB.png" alt="drawing" width="350"/>

<hr>

## Sample Applications
Sample apps can get you started quickly with simple auth flows and advanced usage examples.

**TODO: Replace sample app links screenshots**

| Sample App | Description | Screenshot |
| --- | --- | --- |
| [`starter-app`](https://github.com/XeroAPI/Xero-Java-oauth2-starter) | Basic getting started code samples | <img src="https://i.imgur.com/9H4F98M.png" alt="drawing" width="200"/>
| [`full-app`](https://github.com/XeroAPI/Xero-Java-oauth2-app) | Complete app with more complex examples | <img src="https://i.imgur.com/XsAp9Ww.png" alt="drawing" width="500"/>
| [`custom-connections-starter`](https://github.com/XeroAPI/Xero-Java-custom-connections-starter) | Basic app showing Custom Connections - a Xero [premium option](https://developer.xero.com/documentation/oauth2/custom-connections) for building M2M integrations to a single org | <img src="https://i.imgur.com/YEkScui.png" alt="drawing" width="300"/>

<hr>

## Xero Account Requirements
- Create a [free Xero user account](https://www.xero.com/us/signup/api/)
- Login to your Xero developer [dashboard](https://developer.xero.com/app/manage) and create an API application
- Copy the credentials from your API app and store them using a secure ENV variable strategy
- Decide the [neccesary scopes](https://developer.xero.com/documentation/oauth2/scopes) for your app's functionality

# Installation
To install this SDK in your project:
```
- how to install sdk in language / frameworks
```

---
## Configuration
```
- require library

How to setup:
- 'CLIENT_ID'
- 'CLIENT_SECRET'
- 'REDIRECT_URI'
- 'SCOPES'
- 'STATE'

- client configuration

- Show additional config
```

---
## Authentication
All API requests go through Xero's OAuth 2.0 gateway and require a valid `access_token` to be set on the `client` which appends the `access_token` [JWT](https://jwt.io/) to the header of each request.

If you are making an API call for the first time:

**TODO: detailed steps**
```ruby
- setup client
- send user through auth url
- exchange callback
```

It is recommended that you store this token set JSON in a datastore in relation to the user who has authenticated the Xero API connection. Each time you want to call the Xero API, you will need to access the previously generated token set, initialize it on the SDK `client`, and refresh the `access_token` prior to making API calls.

**TODO: Make sure this is accurate for SDK (Java handles differently)**

### Token Set
| key | value | description |
| --- | --- | --- |
| id_token: | "xxx.yyy.zzz" | [OpenID Connect](https://openid.net/connect/) token returned if `openid profile email` scopes accepted |
| access_token: | "xxx.yyy.zzz" | [Bearer token](https://oauth.net/2/jwt/) with a 30 minute expiration required for all API calls |
| expires_in: | 1800 | Time in seconds till the token expires - 1800s is 30m |
| refresh_token: | "XXXXXXX" | Alphanumeric string used to obtain a new Token Set w/ a fresh access_token - 60 day expiry |
| scope: | "email profile openid accounting.transactions offline_access" | The Xero permissions that are embedded in the `access_token` |

Example Token Set JSON:
```
{
  "id_token": "xxx.yyy.zz",
  "access_token": "xxx.yyy.zzz",
  "expires_in": 1800,
  "token_type": "Bearer",
  "refresh_token": "xxxxxxxxx",
  "scope": "email profile openid accounting.transactions offline_access"
}
```

**TODO: Make sure this is accurate for SDK (DotNet has different names for token)**

---
## Custom Connections 

Custom Connections are a Xero [premium option](https://developer.xero.com/documentation/oauth2/custom-connections) used for building M2M integrations to a single organisation. A custom connection uses OAuth 2.0's [`client_credentials`](https://www.oauth.com/oauth2-servers/access-tokens/client-credentials/) grant which eliminates the step of exchanging the temporary code for a token set.

To use this SDK with a Custom Connections:
```
Show config setup:
- 'CLIENT_ID'
- 'CLIENT_SECRET'
- 'client_credentials'

- client initialization

- show `get client_credentials token` function

- show example API call
- ex: client.accounting_api.get_invoices('').invoices
```

Because Custom Connections are only valid for a single organisation you don't need to pass the xero-tenant-id as the first parameter to every method, or more specifically for this SDK xeroTenantId can be an empty string.

**TODO: Make sure this is accurate for SDK (Java does not require xero-tenant id on ever api call)**

> Because the SDK is generated from the OpenAPI spec the parameter remains. For now you are required to pass an empty string to use this SDK with a Custom Connection.

---
## API Clients
You can access the different API sets and their available methods through the following:

```
- show how to access the supported API sets.
client = ApiClient..

client.accounting_api
client.asset_api
client.project_api
client.files_api
client.payroll_au_api
client.payroll_nz_api
client.payroll_uk_api
```
---
## Helper Methods

Once you have a valid Token Set in your datastore, the next time you want to call the Xero API simply initialize a new `client` and refresh the token set.

```
- client = ApiClient..
 
ex: show basic token refresh and usage
- client.refresh_token_set(token_set)
- user.token_set = @token_set
- client.accounting_api.get_invoices(tenantId).invoices
```

**TODO: describe best practice in setting token set on client and how to refresh prior to usage**


A full list of the SDK client's methods:

**TODO: replace with list of public helper methods that are supported in SDK outside of actual api endpoints**

| method | description |
| --- | --- |
| client.`authorization_url` | returns the authorize URL string to send a new user to for API authorization |
| client.`get_token_set_from_callback`(params) | returns and generates a `token_set` from a temporary code for an `authorization_code` configured client only. Params are the url params. |
| client.`get_client_credentials_token` | returns and generates a `token_set` for a `client_credentials` configured client only  |
| client.`refresh_token_set`(token_set) | returns a refreshed token_set |
| client.`revoke_token`(token_set) | removes all a user's org connections and revokes the refresh_token |
| client.`disconnect`(connection_id) | disconnects an org connection from a user's token permissions |
| client.`connections` | returns an array of the user's currently connected Xero orgs |
---
## Usage Examples
### Accounting API
```
- require client

client.refresh_token_set(user_token_set)
tenant_id = client.connections[0].tenantId

- show a few common endpoints
# Get Accounts
# Create Invoice
# Create History
# Create Attachment
```

---
## SDK conventions

### < TODO SDK Feature Name >
```
  - describe any nuance in the SDK 
  - ex, all currencies are treated as BigDecimal objects..
```

### Querying & Filtering

Describe the support for query options and filtering

```ruby
- opts
- pagination
- if_modified_since
- order
- where
  - type
  - amount_due
  - invoice_number
  - fully_paid_on_date
  - amount_due
  - reference
  - invoice_number
  - contact_id
  - contact_number
  - date
  - status
  - is_customer
  - name
  - email_address
```

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

### < TODO - add section about how to run test suite, or add new unit tests >
### Versioning
We do our best to keep OS industry `semver` standards, but we can make mistakes! If something is not accurately reflected in a version's release notes please let the team know.
