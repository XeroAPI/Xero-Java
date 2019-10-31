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

import com.xero.example.SampleData;
import com.xero.example.CustomJsonConfig;

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

public class AccountingApiBatchPaymentTest {
	CustomJsonConfig config;
	
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
	UUID batchPaymentID;
	private static boolean setUpIsDone = false;

	@Before
	public void setUp() {
		config = new CustomJsonConfig();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting-oauth1/2.0.0",null,null,null);
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
		batchPaymentID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	

		// ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
		if (setUpIsDone) {
        	return;
    	}

    	try {
    		System.out.println("Sleep for 30 seconds");
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
	public void testGetBatchPayment() throws Exception {
		System.out.println("@Test - getBatchPayment");
		BatchPayments batchPayments = accountingApi.getBatchPayments(ifModifiedSince, where, order);
    	assertThat(batchPayments.getBatchPayments().get(0).getBatchPaymentID().toString(), is(equalTo("d0e9bbbf-5b8a-48b6-906a-035591fcb061")));	
		assertThat(batchPayments.getBatchPayments().get(0).getReference(), is(equalTo("Hello World")));	
		//System.out.println(batchPayments.getBatchPayments().toString());
	}

	@Test
	public void testCreateBatchPayment() throws Exception {
		System.out.println("@Test - createBatchPayment");
		
		BatchPayments createBatchPayments = new BatchPayments();
		BatchPayment createBatchPayment = new BatchPayment();
		createBatchPayments.addBatchPaymentsItem(createBatchPayment);		
		BatchPayments newBatchPayments = accountingApi.createBatchPayment(createBatchPayments);
		assertThat(newBatchPayments.getBatchPayments().get(0).getBatchPaymentID().toString(), is(equalTo("d318c343-208e-49fe-b04a-45642349bcf1")));	
		assertThat(newBatchPayments.getBatchPayments().get(0).getReference(), is(equalTo("Foobar123")));	
		//System.out.println(newBatchPayments.getBatchPayments().toString());
	}

	@Test
	public void testGetBatchPaymentsHistory() throws Exception {
		System.out.println("@Test - getBatchPaymentHistory");
		
		HistoryRecords hr = accountingApi.getBatchPaymentHistory(batchPaymentID);
		assertThat(hr.getHistoryRecords().get(0).getUser(), is(equalTo("Sidney Maestre")));		
		assertThat(hr.getHistoryRecords().get(0).getChanges(), is(equalTo("Approved")));		
		//System.out.println(hr.getHistoryRecords().toString());		
	}

	@Test
	public void testcreateBatchPaymentHistoryRecord() throws Exception {
		System.out.println("@Test - createBatchPaymentHistoryRecord");
		
		HistoryRecords newHistoryRecords = new  HistoryRecords();
		HistoryRecord newHistoryRecord = new  HistoryRecord();
		newHistoryRecord.setDetails("Hello World");
		newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
		HistoryRecords newHistory = accountingApi.createBatchPaymentHistoryRecord(batchPaymentID,newHistoryRecords);
		assertThat(newHistory.getHistoryRecords().get(0).getDetails(), is(equalTo("Hello World")));		
		//System.out.println(newHistory.getHistoryRecords().toString());		
	}
}
