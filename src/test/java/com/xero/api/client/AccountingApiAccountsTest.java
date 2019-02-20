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
	String accountName;
	UUID accountID;
	UUID accountUpdateID;
	String accountUpdateName;
	String  accountResetName;

	@Before
	public void setUp() {
		config = JsonConfig.getInstance();
		apiClientForAccounting = new ApiClient(config.getApiUrl(),null,null,null);
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
		accountID = UUID.fromString("310add3b-c278-4592-8c60-c6ed3edd2ac0");				
		accountUpdateID = UUID.fromString("1c1dba87-cd66-41d4-8f6a-dad98cf3feb2");				
		accountUpdateName = "HelloWorld";
		accountResetName = "MyAccountNameToUpdate";
	}


	public void tearDown() {
		accountingApi = null;
		apiClientForAccounting = null;
	}

	@Test
	public void testGetAccounts() throws Exception {
		// GET all accounts
		Accounts accounts = accountingApi.getAccounts(null, null, null);

		if (accounts.getAccounts().size() > 0)
		{
			assert(accounts.getAccounts().size() > 0);
			System.out.println("Get a all Accounts - total : " + accounts.getAccounts().size());
		}
	}

	@Test
	public void testGetSingleAccount() throws Exception {
		// GET all accounts
		Accounts accounts = accountingApi.getAccounts(null, null, null);

		if (accounts.getAccounts().size() > 0)
		{
			// GET one account
			//UUID contactID = UUID.fromString("2bdb8d04-2e4d-4f1c-a3aa-a7cd7703b311");
			Accounts oneAccount = accountingApi.getAccount(accountID);
			assertThat(oneAccount.getAccounts().get(0).getName(), is(equalTo("JavaUnitTest")));
			System.out.println("Get a one Account - name : " + oneAccount.getAccounts().get(0).getName());				
		}		
	}

	@Test
	public void testCreateAccount() throws Exception {
		// CREATE account
		Account acct = new Account();
		accountName = "Bye" + SampleData.loadRandomNum();
		acct.setName(accountName);
		acct.setCode("S" + SampleData.loadRandomNum());
		acct.setType(com.xero.models.accounting.AccountType.EXPENSE);
		Accounts newAccount = accountingApi.createAccount(acct);
		
		assertThat(newAccount.getAccounts().get(0).getName(), is(equalTo(accountName)));
		System.out.println("Create a new Account - Name : " + newAccount.getAccounts().get(0).getName());
		
	}

	@Test
	public void testUpdateAccount() throws Exception {
		// Update account
		Account updateAccount = new Account();
		Accounts accts = new Accounts();
		updateAccount.setName(accountUpdateName);
		accts.addAccountsItem(updateAccount);
		Accounts updatedAccount = accountingApi.updateAccount(accountUpdateID, accts);
		
		assertThat(updatedAccount.getAccounts().get(0).getName(), is(equalTo(accountUpdateName)));
		System.out.println("Update Account - Name : " + updatedAccount.getAccounts().get(0).getName());

		// RESET account
		Account resetAccount = new Account();
		Accounts resetAccts = new Accounts();
		resetAccount.setName(accountResetName);
		resetAccts.addAccountsItem(resetAccount);
		Accounts resetedAccount = accountingApi.updateAccount(accountUpdateID, resetAccts);
	}

	@Test
	public void testArchiveAccount() throws Exception {
		// CREATE account
		Account acct2 = new Account();
		acct2.setName("Bye" + SampleData.loadRandomNum());
		acct2.setCode("S" + SampleData.loadRandomNum());
		acct2.setType(com.xero.models.accounting.AccountType.EXPENSE);
		Accounts newAccountToArchive = accountingApi.createAccount(acct2);
		
		// ARCHIVE account
		Accounts archiveAccounts = new Accounts();
		Account archiveAccount = new Account();
		archiveAccount.setStatus(com.xero.models.accounting.Account.StatusEnum.ARCHIVED);
		archiveAccount.setAccountID(newAccountToArchive.getAccounts().get(0).getAccountID());
		archiveAccounts.addAccountsItem(archiveAccount);
		Accounts achivedAccount = accountingApi.updateAccount(newAccountToArchive.getAccounts().get(0).getAccountID(), archiveAccounts);
		
		assertThat(achivedAccount.getAccounts().get(0).getStatus().toString(), is(equalTo("ARCHIVED")));
		System.out.println("Archived Account - Name : " + achivedAccount.getAccounts().get(0).getName() + " Status: " + achivedAccount.getAccounts().get(0).getStatus());
	}

	@Test
	public void testDeleteAccount() throws Exception {
		// CREATE account
		Account acct3 = new Account();
		acct3.setName("Bye" + SampleData.loadRandomNum());
		acct3.setCode("S" + SampleData.loadRandomNum());
		acct3.setType(com.xero.models.accounting.AccountType.EXPENSE);
		Accounts newAccountToDelete = accountingApi.createAccount(acct3);
		
		// DELETE Account
		UUID deleteAccountID = newAccountToDelete.getAccounts().get(0).getAccountID();
		Accounts deleteAccount = accountingApi.deleteAccount(deleteAccountID);

		assertThat(deleteAccount.getAccounts().get(0).getStatus().toString(), is(equalTo("DELETED")));
		System.out.println("Delete account - Status? : " + deleteAccount.getAccounts().get(0).getStatus());	
	}
}
