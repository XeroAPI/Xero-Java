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
import java.util.List;
import java.util.ArrayList;

public class AccountingApiBankTransactionsTest {

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
	UUID bankTransactionID;
	UUID bankTransactionDeleteID;
	UUID newBankTransactionID;
	String accountUpdateName;
	String  accountResetName;
	Accounts accountsWhere;

    private UUID create() throws Exception {
        System.out.println("Create a bank transaction for test");
        UUID newBankTransactionID = null;

        String where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
		Accounts accountsWhere = accountingApi.getAccounts(ifModifiedSince, where, order);
		
		// Maker sure we have at least 1 bank
		if(accountsWhere.getAccounts().size() > 0) {
				
			Account bankAcct = new Account();
			bankAcct.setCode(accountsWhere.getAccounts().get(0).getCode());
				
			where = null;
			Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
			Contact useContact = new Contact();
			useContact.setContactID(contacts.getContacts().get(0).getContactID());
				
			// Create Bank Transaction
			List<LineItem> lineItems = new ArrayList<>();
			LineItem li = new LineItem();
			li.setAccountCode("400");
			li.setDescription("Foobar");
			li.setQuantity(1.0f);
			li.setUnitAmount(20.0f);
			lineItems.add(li);
			BankTransaction bt = new BankTransaction();
			bt.setBankAccount(bankAcct);
			bt.setContact(useContact);
			bt.setLineitems(lineItems);
			bt.setType(com.xero.models.accounting.BankTransaction.TypeEnum.SPEND);
			BankTransactions bts = new BankTransactions();
			bts.addBankTransactionsItem(bt);					
			BankTransactions newBankTransaction = accountingApi.createBankTransaction(bts, summarizeErrors);
	
			newBankTransactionID = newBankTransaction.getBankTransactions().get(0).getBankTransactionID();
			return newBankTransactionID;
		} else {
			return null;
		}

		
    }


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
		bankTransactionID = UUID.fromString("0b1909c2-3102-4512-a1b1-449014f18fe0");				
		accountUpdateName = "HelloWorld";
		accountResetName = "MyAccountNameToUpdate";
	}

	public void tearDown() {
		accountingApi = null;
		apiClientForAccounting = null;
	}

	@Test
	public void testGetBankTransactions() throws Exception {
		// GET all Bank Transaction
		BankTransactions bankTransactions = accountingApi.getBankTransactions(ifModifiedSince, where, order, null);
	
		if (bankTransactions.getBankTransactions().size() > 0)
		{
			assert(bankTransactions.getBankTransactions().size() > 0);
			System.out.println("Get Bank Transactions - total : " + bankTransactions.getBankTransactions().size());	
		}
	}
	
	@Test
	public void testGetSingleBankTransaction() throws Exception {
		// GET all Bank Transaction
		BankTransactions bankTransactions = accountingApi.getBankTransactions(ifModifiedSince, where, order, null);
	
		if (bankTransactions.getBankTransactions().size() > 0)
		{
			// GET one Bank Transaction
			BankTransactions oneBankTransaction = accountingApi.getBankTransaction(bankTransactions.getBankTransactions().get(0).getBankTransactionID());
			System.out.println("Get single Bank Transaction : Total:" + oneBankTransaction.getBankTransactions().get(0).getTotal());				
			assert(oneBankTransaction.getBankTransactions().size() > 0);
		}		
	}

	@Test
	public void testCreateBankTransaction() throws Exception {
		where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
		Accounts accountsWhere = accountingApi.getAccounts(ifModifiedSince, where, order);
		
		// Maker sure we have at least 1 bank
		if(accountsWhere.getAccounts().size() > 0) {
				
			Account bankAcct = new Account();
			bankAcct.setCode(accountsWhere.getAccounts().get(0).getCode());
				
			where = null;
			Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
			Contact useContact = new Contact();
			useContact.setContactID(contacts.getContacts().get(0).getContactID());
				
			// Create Bank Transaction
			List<LineItem> lineItems = new ArrayList<>();
			LineItem li = new LineItem();
			li.setAccountCode("400");
			li.setDescription("Foobar");
			li.setQuantity(1.0f);
			li.setUnitAmount(20.0f);
			lineItems.add(li);
			BankTransaction bt = new BankTransaction();
			bt.setBankAccount(bankAcct);
			bt.setContact(useContact);
			bt.setLineitems(lineItems);
			bt.setType(com.xero.models.accounting.BankTransaction.TypeEnum.SPEND);
			BankTransactions bts = new BankTransactions();
			bts.addBankTransactionsItem(bt);					
			BankTransactions newBankTransaction = accountingApi.createBankTransaction(bts, summarizeErrors);
			System.out.println("Create new BankTransaction : Total:" + newBankTransaction.getBankTransactions().get(0).getTotal());				
			assertThat(newBankTransaction.getBankTransactions().get(0).getTotal(), is(equalTo(20.0f)));	
		}
	}

	@Test
	public void testUpdate() throws Exception {
		// Update Bank Transaction
		BankTransaction toUpdateBankTransaction = new BankTransaction();
		BankTransactions toUpdateBankTransactions = new BankTransactions();
		toUpdateBankTransaction.setSubTotal(null);
		toUpdateBankTransaction.setTotal(null);	
		toUpdateBankTransaction.setReference("foobar");
		toUpdateBankTransactions.addBankTransactionsItem(toUpdateBankTransaction);

		BankTransactions updateBankTransaction = accountingApi.updateBankTransaction(bankTransactionID,toUpdateBankTransactions);
		System.out.println("Update BankTransaction : reference:" + updateBankTransaction.getBankTransactions().get(0).getReference());				
		assertThat(updateBankTransaction.getBankTransactions().get(0).getReference(), is(equalTo("foobar")));	
	}

	@Test
	public void testDelete() throws Exception {
		
		// DELETE Bank Transaction
		BankTransactions toDeleteBankTransactions = new BankTransactions();
		BankTransaction toDeleteBankTransaction = new BankTransaction();
		toDeleteBankTransaction.setStatus(com.xero.models.accounting.BankTransaction.StatusEnum.DELETED);
		toDeleteBankTransactions.addBankTransactionsItem(toDeleteBankTransaction);
System.out.println(create());
//		BankTransactions deletedBankTransaction = accountingApi.updateBankTransaction(create(),toDeleteBankTransactions);
//		System.out.println("Deleted new Bank Transaction : Status:" + deletedBankTransaction.getBankTransactions().get(0).getStatus());					
			//assertThat(deletedBankTransaction.getBankTransactions().get(0).getStatus().toString(), is(equalTo("DELETED")));			
		
	}
}
