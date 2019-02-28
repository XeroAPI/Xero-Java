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
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class AccountingApiBankTransactionTest {

	CustomJsonConfig config;
	
	ApiClient apiClientForAccounting; 
	AccountingApi api; 
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
	UUID bankTransactionID;
	@Before
	public void setUp() {
		config = new CustomJsonConfig();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting/2.0.0",null,null,null);
		api = new AccountingApi(config);
		api.setApiClient(apiClientForAccounting);
		api.setOAuthToken(config.getConsumerKey(), config.getConsumerSecret());

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
		bankTransactionID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	
	}


	public void tearDown() {
		api = null;
		apiClientForAccounting = null;
	}

	@Test
	public void testGetBankTransactions() throws Exception {
		System.out.println("@Test - getBankTransactions");

		BankTransactions response = api.getBankTransactions(ifModifiedSince, where, order, null);
		assertThat(response.getBankTransactions().get(0).getBankTransactionID(), is(equalTo(UUID.fromString("db54aab0-ad40-4ced-bcff-0940ba20db2c"))));
		assertThat(response.getBankTransactions().get(0).getStatus(), is(equalTo(com.xero.models.accounting.BankTransaction.StatusEnum.AUTHORISED)));
		assertThat(response.getBankTransactions().get(0).getType(), is(equalTo(com.xero.models.accounting.BankTransaction.TypeEnum.RECEIVE)));
		assertThat(response.getBankTransactions().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.USD)));
		assertThat(response.getBankTransactions().get(0).getIsReconciled(), is(equalTo(false)));
		assertThat(response.getBankTransactions().get(0).getIsReconciled(), is(equalTo(false)));
		assertThat(response.getBankTransactions().get(0).getSubTotal(), is(equalTo(10.0f)));
		assertThat(response.getBankTransactions().get(0).getTotal(), is(equalTo(10.0f)));
		
		//System.out.println(response.getBankTransactions().get(0).toString());
	}


	@Test
	public void testGetBankTransaction() throws Exception {
		System.out.println("@Test - getBankTransaction");

		BankTransactions response = api.getBankTransaction(bankTransactionID);
		assertThat(response.getBankTransactions().get(0).getBankTransactionID(), is(equalTo(UUID.fromString("db54aab0-ad40-4ced-bcff-0940ba20db2c"))));
		assertThat(response.getBankTransactions().get(0).getStatus(), is(equalTo(com.xero.models.accounting.BankTransaction.StatusEnum.AUTHORISED)));
		assertThat(response.getBankTransactions().get(0).getType(), is(equalTo(com.xero.models.accounting.BankTransaction.TypeEnum.RECEIVE)));
		assertThat(response.getBankTransactions().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.USD)));
		assertThat(response.getBankTransactions().get(0).getIsReconciled(), is(equalTo(false)));
		assertThat(response.getBankTransactions().get(0).getSubTotal(), is(equalTo(10.0f)));
		assertThat(response.getBankTransactions().get(0).getTotal(), is(equalTo(10.0f)));
		
		//System.out.println(response.getBankTransactions().get(0).toString());
	}

	@Test
	public void testUpdateBankTransaction() throws Exception {
		System.out.println("@Test - updateBankTransaction");

		BankTransactions newBankTransactions = new BankTransactions();
		BankTransaction newBankTransaction = new BankTransaction();
		newBankTransaction.setSubTotal(null);
		newBankTransaction.setTotal(null);	
		newBankTransaction.setReference("You just updated");
		newBankTransactions.addBankTransactionsItem(newBankTransaction);
		BankTransactions response = api.updateBankTransaction(bankTransactionID,newBankTransactions);
					
		assertThat(response.getBankTransactions().get(0).getBankTransactionID(), is(equalTo(UUID.fromString("1289c190-e46d-434b-9628-463ffdb52f00"))));		
		assertThat(response.getBankTransactions().get(0).getStatus(), is(equalTo(com.xero.models.accounting.BankTransaction.StatusEnum.AUTHORISED)));
		assertThat(response.getBankTransactions().get(0).getType(), is(equalTo(com.xero.models.accounting.BankTransaction.TypeEnum.SPEND)));
		assertThat(response.getBankTransactions().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.USD)));
		assertThat(response.getBankTransactions().get(0).getIsReconciled(), is(equalTo(false)));
		assertThat(response.getBankTransactions().get(0).getSubTotal(), is(equalTo(18.26f)));
		assertThat(response.getBankTransactions().get(0).getTotal(), is(equalTo(20.00f)));
		
		//System.out.println(response.getBankTransactions().get(0).toString());
	}

	@Test
	public void testCreateBankTransaction() throws Exception {
		System.out.println("@Test - createBankTransaction");

		BankTransactions newBankTransactions = new BankTransactions();
		BankTransaction newBankTransaction = new BankTransaction();

		List<LineItem> lineItems = new ArrayList<>();
		LineItem li = new LineItem();
		li.setAccountCode("400");
		li.setDescription("Foobar");
		li.setQuantity(1.0f);
		li.setUnitAmount(20.0f);
		lineItems.add(li);
		newBankTransaction.setLineitems(lineItems);
		newBankTransaction.setType(com.xero.models.accounting.BankTransaction.TypeEnum.SPEND);
		newBankTransactions.addBankTransactionsItem(newBankTransaction);
		BankTransactions response = api.createBankTransaction(newBankTransactions,null);
					
		assertThat(response.getBankTransactions().get(0).getBankTransactionID(), is(equalTo(UUID.fromString("1289c190-e46d-434b-9628-463ffdb52f00"))));		
		assertThat(response.getBankTransactions().get(0).getStatus(), is(equalTo(com.xero.models.accounting.BankTransaction.StatusEnum.AUTHORISED)));
		assertThat(response.getBankTransactions().get(0).getType(), is(equalTo(com.xero.models.accounting.BankTransaction.TypeEnum.SPEND)));
		assertThat(response.getBankTransactions().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.USD)));
		assertThat(response.getBankTransactions().get(0).getIsReconciled(), is(equalTo(false)));
		assertThat(response.getBankTransactions().get(0).getSubTotal(), is(equalTo(18.26f)));
		assertThat(response.getBankTransactions().get(0).getTotal(), is(equalTo(20.00f)));
		
		//System.out.println(response.getBankTransactions().get(0).toString());
	}


    @Test
    public void createBankTransactionAttachmentByFileNameTest() throws IOException {
        InputStream inputStream = CustomJsonConfig.class.getResourceAsStream("/helo-heros.jpg");
		byte[] body = IOUtils.toByteArray(inputStream);
		String fileName = "sample5.jpg";
		
        Attachments response = api.createBankTransactionAttachmentByFileName(bankTransactionID, fileName, body);
		assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("4508a692-e52c-4ad8-a138-2f13e22bf57b"))));
		assertThat(response.getAttachments().get(0).getFileName().toString(), is(equalTo("sample5.jpg")));
		assertThat(response.getAttachments().get(0).getMimeType().toString(), is(equalTo("image/jpg")));
		assertThat(response.getAttachments().get(0).getUrl().toString(), is(equalTo("https://api.xero.com/api.xro/2.0/BankTransactions/db54aab0-ad40-4ced-bcff-0940ba20db2c/Attachments/sample5.jpg")));

		//System.out.println(response.getAttachments().get(0).toString());
    }


    @Test
    public void getBankTransactionAttachmentsTest() throws IOException {
        Attachments response = api.getBankTransactionAttachments(bankTransactionID);

       	assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("4508a692-e52c-4ad8-a138-2f13e22bf57b"))));
		assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("sample5.jpg")));
		assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
		assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/BankTransactions/db54aab0-ad40-4ced-bcff-0940ba20db2c/Attachments/sample5.jpg")));
		assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal(2878711))));
		//System.out.println(response.getAttachments().get(0).toString());
    }

    @Test
    public void updateBankTransactionAttachmentByFileNameTest() throws IOException {
        InputStream inputStream = CustomJsonConfig.class.getResourceAsStream("/helo-heros.jpg");
		byte[] body = IOUtils.toByteArray(inputStream);
		String fileName = "sample5.jpg";
		
        Attachments response = api.updateBankTransactionAttachmentByFileName(bankTransactionID, fileName, body);
		assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("4508a692-e52c-4ad8-a138-2f13e22bf57b"))));
		assertThat(response.getAttachments().get(0).getFileName().toString(), is(equalTo("sample5.jpg")));
		assertThat(response.getAttachments().get(0).getMimeType().toString(), is(equalTo("image/jpg")));
		assertThat(response.getAttachments().get(0).getUrl().toString(), is(equalTo("https://api.xero.com/api.xro/2.0/BankTransactions/db54aab0-ad40-4ced-bcff-0940ba20db2c/Attachments/sample5.jpg")));

    }

	@Test
	public void testGetBankTransactionsHistory() throws Exception {
		System.out.println("@Test - getBankTransactionsHistory");

		HistoryRecords hr = api.getBankTransactionsHistory(bankTransactionID);
		assertThat(hr.getHistoryRecords().get(0).getUser(), is(equalTo("System Generated")));		
		assertThat(hr.getHistoryRecords().get(0).getChanges(), is(equalTo("Attached a file")));		
		//System.out.println(hr.getHistoryRecords().toString());		
	}

	@Test
	public void testCreateBankTransactionHistoryRecord() throws Exception {
		System.out.println("@Test - createBankTransactionHistoryRecord - not implemented");
		
		/*
		HistoryRecords newHistoryRecords = new  HistoryRecords();
		HistoryRecord newHistoryRecord = new  HistoryRecord();
		newHistoryRecord.setDetails("Hello World");
		newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
		HistoryRecords newHistory = api.createBankTransactionHistoryRecord(bankTransactionID,newHistoryRecords);
		//assertThat(newHistory.getHistoryRecords().get(0).getDetails(), is(equalTo("Hello World")));		
		System.out.println(newHistory.getHistoryRecords().toString());	
		*/	
	}
	
}
