package com.xero.api.client;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Every.everyItem;

import com.xero.api.XeroApiException;
import com.xero.api.ApiClient;
import com.xero.api.Config;
import com.xero.api.JsonConfig;
import com.xero.api.client.*;
import com.xero.models.accounting.*;

import com.xero.example.SampleData;

import org.threeten.bp.*;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class AccountingApiAccountsTest {

	Config config;
	
	ApiClient apiClientForAccounting; 
	AccountingApi accountingApi; 
	Map<String, String> params;

	OffsetDateTime ifModifiedSince;
	String where;
	String order;
	boolean summarizeErrors;
	String ids;
	boolean includeArchived;
	String invoiceNumbers;
	String contactIDs;
	String statuses;
	boolean createdByMyApp;
	UUID accountID;
	@Before
	public void setUp() {
		config = JsonConfig.getInstance();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting/2.0.0",null,null,null);
		accountingApi = new AccountingApi(config);
		accountingApi.setApiClient(apiClientForAccounting);
		accountingApi.setOAuthToken(config.getConsumerKey(), config.getConsumerSecret());

		ifModifiedSince = null;
		where = null;
		order = null;
		summarizeErrors = false;
		ids= null;
		includeArchived = false;
		invoiceNumbers = null;
		contactIDs = null;
		statuses = null;
		createdByMyApp = false;
		accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	
	}


	public void tearDown() {
		accountingApi = null;
		apiClientForAccounting = null;
	}

	@Test
	public void testGetAccounts() throws Exception {
		System.out.println("@Test - getAccounts");
		Accounts accounts = accountingApi.getAccounts(null, null, null);
		assert(accounts.getAccounts().size() == 2);
		assertThat(accounts.getAccounts().get(0).getCode(), is(equalTo("091")));
		assertThat(accounts.getAccounts().get(0).getName(), is(equalTo("Business Savings Account")));
		assertThat(accounts.getAccounts().get(0).getAccountID().toString(), is(equalTo("ebd06280-af70-4bed-97c6-7451a454ad85")));
		assertThat(accounts.getAccounts().get(0).getType(), is(equalTo(com.xero.models.accounting.AccountType.BANK)));
		assertThat(accounts.getAccounts().get(0).getBankAccountNumber(), is(equalTo("0209087654321050")));
		assertThat(accounts.getAccounts().get(0).getBankAccountType(), is(equalTo(com.xero.models.accounting.Account.BankAccountTypeEnum.BANK)));
		assertThat(accounts.getAccounts().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
		assertThat(accounts.getAccounts().get(0).getTaxType(), is(equalTo(com.xero.models.accounting.TaxType.NONE)));
	}

	@Test
	public void testGetAccount() throws Exception {
		System.out.println("@Test - getAccount");
		Accounts oneAccount = accountingApi.getAccount(accountID);
		assertThat(oneAccount.getAccounts().get(0).getName(), is(equalTo("FooBar")));
		assertThat(oneAccount.getAccounts().get(0).getCode(), is(equalTo("123456")));
	}

	@Test
	public void testCreateAccount() throws Exception {
		System.out.println("@Test - createAccount");
		Account acct = new Account();
		acct.setName("Foobar");
		acct.setCode("123456");
		acct.setType(com.xero.models.accounting.AccountType.EXPENSE);
		Accounts newAccount = accountingApi.createAccount(acct);
		assertThat(newAccount.getAccounts().get(0).getName(), is(equalTo("Foobar")));		
	}

	@Test
	public void testUpdateAccount() throws Exception {
		System.out.println("@Test - updateAccount");
		
		Account acct = new Account();
		Accounts accts = new Accounts();
		acct.setName("BarFoo");
		accts.addAccountsItem(acct);
		Accounts updatedAccount = accountingApi.updateAccount(accountID, accts);
		assertThat(updatedAccount.getAccounts().get(0).getName(), is(equalTo("BarFoo")));
	}

	@Test
	public void testDeleteAccount() throws Exception {		
		System.out.println("@Test - deleteAccount");

		Accounts deleteAccount = accountingApi.deleteAccount(accountID);
		assertThat(deleteAccount.getAccounts().get(0).getStatus().toString(), is(equalTo("DELETED")));
	}

	@Test
	public void testGetAccountAttachments() throws Exception {		
		System.out.println("@Test - getAccountAttachments");

		Attachments accountsAttachments = accountingApi.getAccountAttachments(accountID);					
		assertThat(accountsAttachments.getAttachments().get(0).getAttachmentID().toString(), is(equalTo("52a643be-cd5c-489f-9778-53a9fd337756")));
		assertThat(accountsAttachments.getAttachments().get(0).getFileName().toString(), is(equalTo("sample5.jpg")));
		assertThat(accountsAttachments.getAttachments().get(0).getMimeType().toString(), is(equalTo("image/jpg")));
		assertThat(accountsAttachments.getAttachments().get(0).getUrl().toString(), is(equalTo("https://api.xero.com/api.xro/2.0/Accounts/da962997-a8bd-4dff-9616-01cdc199283f/Attachments/sample5.jpg")));
		
	
	}	

	@Test
	public void testCreateAccountAttachmentByFileName() throws Exception {		
		System.out.println("@Test - createAccountAttachmentByFileName");

		InputStream inputStream = JsonConfig.class.getResourceAsStream("/helo-heros.jpg");
		byte[] bytes = IOUtils.toByteArray(inputStream);
		String newFileName = "sample5.jpg";
		Attachments createAccountsAttachments = accountingApi.createAccountAttachmentByFileName(accountID, newFileName, bytes);					

		assertThat(createAccountsAttachments.getAttachments().get(0).getAttachmentID().toString(), is(equalTo("ab95b276-9dce-4925-9077-439818ba270f")));
		assertThat(createAccountsAttachments.getAttachments().get(0).getFileName().toString(), is(equalTo("sample5.jpg")));
		assertThat(createAccountsAttachments.getAttachments().get(0).getMimeType().toString(), is(equalTo("image/jpg")));
		assertThat(createAccountsAttachments.getAttachments().get(0).getUrl().toString(), is(equalTo("https://api.xero.com/api.xro/2.0/Accounts/da962997-a8bd-4dff-9616-01cdc199283f/Attachments/sample5.jpg")));
	}

	@Test
	public void testUpdateAccountAttachmentByFileName() throws Exception {		
		System.out.println("@Test - updateAccountAttachmentByFileName");

		InputStream inputStream = JsonConfig.class.getResourceAsStream("/helo-heros.jpg");
		byte[] bytes = IOUtils.toByteArray(inputStream);
		String newFileName = "sample5.jpg";
		Attachments createAccountsAttachments = accountingApi.updateAccountAttachmentByFileName(accountID, newFileName, bytes);					

		assertThat(createAccountsAttachments.getAttachments().get(0).getAttachmentID().toString(), is(equalTo("3fa85f64-5717-4562-b3fc-2c963f66afa6")));
		assertThat(createAccountsAttachments.getAttachments().get(0).getFileName().toString(), is(equalTo("sample5.jpg")));
		assertThat(createAccountsAttachments.getAttachments().get(0).getMimeType().toString(), is(equalTo("image/jpg")));
		assertThat(createAccountsAttachments.getAttachments().get(0).getUrl().toString(), is(equalTo("https://api.xero.com/api.xro/2.0/Accounts/da962997-a8bd-4dff-9616-01cdc199283f/Attachments/sample5.jpg")));
	}
}
