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
import com.xero.api.client.*;
import com.xero.models.accounting.*;

import com.xero.example.CustomJsonConfig;
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

	CustomJsonConfig config;
	
	ApiClient apiClientForAccounting; 
	AccountingApi accountingApi; 
	
	private static boolean setUpIsDone = false;

	@Before
	public void setUp() {
		config = new CustomJsonConfig();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting-oauth1/2.0.0",null,null,null);
		accountingApi = new AccountingApi(config);
		accountingApi.setApiClient(apiClientForAccounting);
		accountingApi.setOAuthToken(config.getConsumerKey(), config.getConsumerSecret());

		
		// ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
		if (setUpIsDone) {
        	return;
    	}

    	try {
    		System.out.println("Sleep for 60 seconds");
	    	Thread.sleep(60000);
    	} catch(InterruptedException e) {
    		System.out.println(e);
    	}
    	// do the setup
    	setUpIsDone = true;
	}


	public void tearDown() {
		accountingApi = null;
		apiClientForAccounting = null;
	}

	@Test
	public void testGetAccounts() throws Exception {
		System.out.println("@Test - getAccounts");

		
		OffsetDateTime ifModifiedSince = null;
		String where = null;
		String order = null;
	
		Accounts accounts = accountingApi.getAccounts(ifModifiedSince, where, order);
		assert(accounts.getAccounts().size() == 2);
		assertThat(accounts.getAccounts().get(0).getCode(), is(equalTo("091")));
		assertThat(accounts.getAccounts().get(0).getName(), is(equalTo("Business Savings Account")));
		assertThat(accounts.getAccounts().get(0).getAccountID().toString(), is(equalTo("ebd06280-af70-4bed-97c6-7451a454ad85")));
		assertThat(accounts.getAccounts().get(0).getType(), is(equalTo(com.xero.models.accounting.AccountType.BANK)));
		assertThat(accounts.getAccounts().get(0).getBankAccountNumber(), is(equalTo("0209087654321050")));
		assertThat(accounts.getAccounts().get(0).getBankAccountType(), is(equalTo(com.xero.models.accounting.Account.BankAccountTypeEnum.BANK)));
		assertThat(accounts.getAccounts().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
		assertThat(accounts.getAccounts().get(0).getTaxType(), is(equalTo("NONE")));
	}

	@Test
	public void testGetAccount() throws Exception {
		System.out.println("@Test - getAccount");
		UUID accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	
		
		Accounts oneAccount = accountingApi.getAccount(accountID);
		assertThat(oneAccount.getAccounts().get(0).getName(), is(equalTo("FooBar")));
		assertThat(oneAccount.getAccounts().get(0).getCode(), is(equalTo("123456")));
	}

	@Test
	public void testCreateAccount() throws Exception {
		System.out.println("@Test - createAccount");

		Account acct = null;
		Accounts newAccount = accountingApi.createAccount(acct);
		assertThat(newAccount.getAccounts().get(0).getName(), is(equalTo("Foobar")));		
	}

	@Test
	public void testUpdateAccount() throws Exception {
		System.out.println("@Test - updateAccount");
		
		UUID accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	
		Accounts accts = null;
		Accounts updatedAccount = accountingApi.updateAccount(accountID, accts);
		assertThat(updatedAccount.getAccounts().get(0).getName(), is(equalTo("BarFoo")));
	}

	@Test
	public void testDeleteAccount() throws Exception {		
		System.out.println("@Test - deleteAccount");

		UUID accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
		Accounts deleteAccount = accountingApi.deleteAccount(accountID);
		assertThat(deleteAccount.getAccounts().get(0).getStatus().toString(), is(equalTo("DELETED")));
	}

	@Test
	public void testGetAccountAttachments() throws Exception {		
		System.out.println("@Test - getAccountAttachments");

		UUID accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
		Attachments accountsAttachments = accountingApi.getAccountAttachments(accountID);					
		assertThat(accountsAttachments.getAttachments().get(0).getAttachmentID().toString(), is(equalTo("52a643be-cd5c-489f-9778-53a9fd337756")));
		assertThat(accountsAttachments.getAttachments().get(0).getFileName().toString(), is(equalTo("sample5.jpg")));
		assertThat(accountsAttachments.getAttachments().get(0).getMimeType().toString(), is(equalTo("image/jpg")));
		assertThat(accountsAttachments.getAttachments().get(0).getUrl().toString(), is(equalTo("https://api.xero.com/api.xro/2.0/Accounts/da962997-a8bd-4dff-9616-01cdc199283f/Attachments/sample5.jpg")));
		
	
	}	

	@Test
	public void testCreateAccountAttachmentByFileName() throws Exception {		
		System.out.println("@Test - createAccountAttachmentByFileName");

		UUID accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
		InputStream inputStream = CustomJsonConfig.class.getResourceAsStream("/helo-heros.jpg");
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

		UUID accountID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
		InputStream inputStream = CustomJsonConfig.class.getResourceAsStream("/helo-heros.jpg");
		byte[] bytes = IOUtils.toByteArray(inputStream);
		String newFileName = "sample5.jpg";
		Attachments createAccountsAttachments = accountingApi.updateAccountAttachmentByFileName(accountID, newFileName, bytes);					
		assertThat(createAccountsAttachments.getAttachments().get(0).getAttachmentID().toString(), is(equalTo("3fa85f64-5717-4562-b3fc-2c963f66afa6")));
		assertThat(createAccountsAttachments.getAttachments().get(0).getFileName().toString(), is(equalTo("sample5.jpg")));
		assertThat(createAccountsAttachments.getAttachments().get(0).getMimeType().toString(), is(equalTo("image/jpg")));
		assertThat(createAccountsAttachments.getAttachments().get(0).getUrl().toString(), is(equalTo("https://api.xero.com/api.xro/2.0/Accounts/da962997-a8bd-4dff-9616-01cdc199283f/Attachments/sample5.jpg")));
	}
}
