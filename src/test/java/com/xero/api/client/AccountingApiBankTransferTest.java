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
import java.math.BigDecimal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class AccountingApiBankTransferTest {

	Config config;
	
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
	UUID bankTransferID;
	@Before
	public void setUp() {
		config = JsonConfig.getInstance();
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
		bankTransferID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	
	}


	public void tearDown() {
		api = null;
		apiClientForAccounting = null;
	}

	@Test
	public void getBankTransfersTest() throws Exception {
		System.out.println("@Test - getBankTransfers");

		BankTransfers response = api.getBankTransfers(ifModifiedSince, where, order);
		/*
		assertThat(response.getBankTransfers().get(0).getBankTransactionID(), is(equalTo(UUID.fromString("db54aab0-ad40-4ced-bcff-0940ba20db2c"))));
		assertThat(response.getBankTransfers().get(0).getStatus(), is(equalTo(com.xero.models.accounting.BankTransaction.StatusEnum.AUTHORISED)));
		assertThat(response.getBankTransfers().get(0).getType(), is(equalTo(com.xero.models.accounting.BankTransaction.TypeEnum.RECEIVE)));
		assertThat(response.getBankTransfers().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.USD)));
		assertThat(response.getBankTransfers().get(0).getIsReconciled(), is(equalTo(false)));
		assertThat(response.getBankTransfers().get(0).getIsReconciled(), is(equalTo(false)));
		assertThat(response.getBankTransfers().get(0).getSubTotal(), is(equalTo(10.0f)));
		assertThat(response.getBankTransfers().get(0).getTotal(), is(equalTo(10.0f)));
		*/
		//System.out.println(response.getBankTransfers().get(0).toString());
	}

   	@Test
    public void getBankTransferTest() throws IOException {
    	System.out.println("@Test - getBankTransfer");
        BankTransfers response = api.getBankTransfer(bankTransferID);

        // TODO: test validations
        //System.out.println(response.getBankTransfers().get(0).toString());
    }
    

	@Test
    public void createBankTransferTest() throws IOException {
    	System.out.println("@Test - createBankTransfer");
        BankTransfers bankTransfers = null;
        BankTransfers response = api.createBankTransfer(bankTransfers);

       	
		assertThat(response.getBankTransfers().get(0).getBankTransferID(), is(equalTo(UUID.fromString("76eea4b6-f026-464c-b6f3-5fb39a196145"))));
		assertThat(response.getBankTransfers().get(0).getFromBankTransactionID(), is(equalTo(UUID.fromString("e4059952-5acb-4a56-b076-53fad85f2930"))));
		assertThat(response.getBankTransfers().get(0).getFromBankAccount().getName(), is(equalTo("My Savings")));
		
        //System.out.println(response.getBankTransfers().get(0).toString());
    }
    /*
    @Test
    public void getBankTransferHistoryTest() throws IOException {
        UUID bankTransferID = null;
        HistoryRecords response = api.getBankTransferHistory(bankTransferID);

        // TODO: test validations
       // System.out.println(response.getHistoryRecords().get(0).toString());
    }

    @Test
    public void createBankTransferHistoryRecordTest() throws IOException {
    	System.out.println("@Test - createBankTransferHistoryRecordTest - not implemented at this time");
        
        //HistoryRecords historyRecords = null;
        //HistoryRecords response = api.createBankTransferHistoryRecord(bankTransferID, historyRecords);

        // TODO: test validations
    }


    

    @Test
    public void createBankTransferAttachmentByFileNameTest() throws IOException {
        String fileName = null;
        byte[] body = null;
        Attachments response = api.createBankTransferAttachmentByFileName(bankTransferID, fileName, body);

        // TODO: test validations
       	System.out.println(response.getAttachments().get(0).toString());
	
    }

    @Test
    public void getBankTransferAttachmentByFileNameTest() throws IOException {
        UUID bankTransferID = null;
        String fileName = null;
        String contentType = null;
        Attachments response = api.getBankTransferAttachmentByFileName(bankTransferID, fileName, contentType);

        // TODO: test validations
    }
    

    @Test
    public void getBankTransferAttachmentByIdTest() throws IOException {
        UUID bankTransferID = null;
        UUID attachmentID = null;
        String contentType = null;
        File response = api.getBankTransferAttachmentById(bankTransferID, attachmentID, contentType);

        // TODO: test validations
    }
    

    @Test
    public void getBankTransferAttachmentsTest() throws IOException {
        UUID bankTransferID = null;
        Attachments response = api.getBankTransferAttachments(bankTransferID);

        // TODO: test validations
    }
    

    @Test
    public void getBankTransferHistoryTest() throws IOException {
        UUID bankTransferID = null;
        HistoryRecords response = api.getBankTransferHistory(bankTransferID);

        // TODO: test validations
    }
    



    @Test
    public void updateBankTransferAttachmentByFileNameTest() throws IOException {
        String fileName = null;
        byte[] body = null;
        Attachments response = api.updateBankTransferAttachmentByFileName(bankTransferID, fileName, body);

        // TODO: test validations
    }
   */
	
}
