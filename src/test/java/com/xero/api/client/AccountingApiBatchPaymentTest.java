package com.xero.api.client;

import static org.junit.Assert.assertTrue;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Every.everyItem;

import com.xero.api.ApiClient;
import com.xero.api.client.*;
import com.xero.models.accounting.*;

import java.io.File;
import java.net.URL;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import org.threeten.bp.*;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

public class AccountingApiBatchPaymentTest {
	ApiClient defaultClient; 
    AccountingApi accountingApi; 
	String accessToken;
    String xeroTenantId; 
     
    File bytes;

	private static boolean setUpIsDone = false;

	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        // Init AccountingApi client
        // NEW Sandbox for API Mocking
		defaultClient = new ApiClient("https://xero-accounting.getsandbox.com:443/api.xro/2.0",null,null,null,null);
        
        accountingApi = AccountingApi.getInstance(defaultClient);	
        
        ClassLoader classLoader = getClass().getClassLoader();
        bytes = new File(classLoader.getResource("helo-heros.jpg").getFile());
      
		// ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
		if (setUpIsDone) {
        	return;
    	}

    	try {
    		System.out.println("Sleep for 60 seconds");
	    	Thread.sleep(60);
    	} catch(InterruptedException e) {
    		System.out.println(e);
    	}
    	// do the setup
    	setUpIsDone = true;
	}

	public void tearDown() {
		accountingApi = null;
        defaultClient = null;
	}

	@Test
	public void testGetBatchPayment() throws Exception {
		System.out.println("@Test - getBatchPayment");
		OffsetDateTime ifModifiedSince = null;
		String where = null;
		String order = null;
		BatchPayments batchPayments = accountingApi.getBatchPayments(accessToken,xeroTenantId,ifModifiedSince, where, order);
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
		BatchPayments newBatchPayments = accountingApi.createBatchPayment(accessToken,xeroTenantId,createBatchPayments, false);
		assertThat(newBatchPayments.getBatchPayments().get(0).getBatchPaymentID().toString(), is(equalTo("d318c343-208e-49fe-b04a-45642349bcf1")));	
		assertThat(newBatchPayments.getBatchPayments().get(0).getReference(), is(equalTo("Foobar123")));	
		//System.out.println(newBatchPayments.getBatchPayments().toString());
	}

	@Test
	public void testGetBatchPaymentsHistory() throws Exception {
		System.out.println("@Test - getBatchPaymentHistory");
		UUID batchPaymentID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	

		HistoryRecords hr = accountingApi.getBatchPaymentHistory(accessToken,xeroTenantId,batchPaymentID);
		assertThat(hr.getHistoryRecords().get(0).getUser(), is(equalTo("Buzz Lightyear")));		
		assertThat(hr.getHistoryRecords().get(0).getChanges(), is(equalTo("Approved")));		
		//System.out.println(hr.getHistoryRecords().toString());		
	}

	@Test
	public void testcreateBatchPaymentHistoryRecord() throws Exception {
		System.out.println("@Test - createBatchPaymentHistoryRecord");
		UUID batchPaymentID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	

		HistoryRecords newHistoryRecords = new  HistoryRecords();
		HistoryRecord newHistoryRecord = new  HistoryRecord();
		newHistoryRecord.setDetails("Hello World");
		newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
		HistoryRecords newHistory = accountingApi.createBatchPaymentHistoryRecord(accessToken,xeroTenantId,batchPaymentID,newHistoryRecords);
		assertThat(newHistory.getHistoryRecords().get(0).getDetails(), is(equalTo("Hello World")));		
		//System.out.println(newHistory.getHistoryRecords().toString());		
	}
}
