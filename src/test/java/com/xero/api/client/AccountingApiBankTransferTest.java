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
import com.xero.example.CustomJsonConfig;

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

	CustomJsonConfig config;
	
	ApiClient apiClientForAccounting; 
	AccountingApi api; 
	
	@Before
	public void setUp() {
		config = new CustomJsonConfig();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting/2.0.0",null,null,null);
		api = new AccountingApi(config);
		api.setApiClient(apiClientForAccounting);
		api.setOAuthToken(config.getConsumerKey(), config.getConsumerSecret());
	}

	public void tearDown() {
		api = null;
		apiClientForAccounting = null;
	}

	@Test
	public void getBankTransfersTest() throws Exception {
		System.out.println("@Test - getBankTransfers");

        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
    
		BankTransfers response = api.getBankTransfers(ifModifiedSince, where, order);
        assertThat(response.getBankTransfers().get(0).getBankTransferID(), is(equalTo(UUID.fromString("6221458a-ef7a-4d5f-9b1c-1b96ce03833c"))));
        assertThat(response.getBankTransfers().get(0).getFromBankTransactionID(), is(equalTo(UUID.fromString("a3eca480-bc04-4292-9bbd-5c57b8ba12b4"))));
        assertThat(response.getBankTransfers().get(0).getToBankTransactionID(), is(equalTo(UUID.fromString("4ca13f40-f3a0-4530-a442-a600f5696118"))));        
        assertThat(response.getBankTransfers().get(0).getFromBankAccount().getName(), is(equalTo("Business Wells Fargo")));
        assertThat(response.getBankTransfers().get(0).getFromBankAccount().getAccountID(), is(equalTo(UUID.fromString("6f7594f2-f059-4d56-9e67-47ac9733bfe9"))));
        assertThat(response.getBankTransfers().get(0).getToBankAccount().getName(), is(equalTo("My Savings")));
        assertThat(response.getBankTransfers().get(0).getToBankAccount().getAccountID(), is(equalTo(UUID.fromString("7e5e243b-9fcd-4aef-8e3a-c70be1e39bfa"))));
        //System.out.println(response.getBankTransfers().get(0).toString());
	}

   	@Test
    public void getBankTransferTest() throws IOException {
    	System.out.println("@Test - getBankTransfer");

        UUID bankTransferID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");  
        BankTransfers response = api.getBankTransfer(bankTransferID);
        assertThat(response.getBankTransfers().get(0).getBankTransferID(), is(equalTo(UUID.fromString("6221458a-ef7a-4d5f-9b1c-1b96ce03833c"))));
        assertThat(response.getBankTransfers().get(0).getFromBankTransactionID(), is(equalTo(UUID.fromString("a3eca480-bc04-4292-9bbd-5c57b8ba12b4"))));
        assertThat(response.getBankTransfers().get(0).getToBankTransactionID(), is(equalTo(UUID.fromString("4ca13f40-f3a0-4530-a442-a600f5696118"))));        
        assertThat(response.getBankTransfers().get(0).getFromBankAccount().getName(), is(equalTo("Business Wells Fargo")));
        assertThat(response.getBankTransfers().get(0).getFromBankAccount().getAccountID(), is(equalTo(UUID.fromString("6f7594f2-f059-4d56-9e67-47ac9733bfe9"))));
        assertThat(response.getBankTransfers().get(0).getToBankAccount().getName(), is(equalTo("My Savings")));
        assertThat(response.getBankTransfers().get(0).getToBankAccount().getAccountID(), is(equalTo(UUID.fromString("7e5e243b-9fcd-4aef-8e3a-c70be1e39bfa"))));
        //System.out.println(response.getBankTransfers().get(0).toString());
    }
    
	@Test
    public void createBankTransferTest() throws IOException {
    	System.out.println("@Test - createBankTransfer");

        BankTransfers bankTransfers = null;
        BankTransfers response = api.createBankTransfer(bankTransfers);
		assertThat(response.getBankTransfers().get(0).getBankTransferID(), is(equalTo(UUID.fromString("76eea4b6-f026-464c-b6f3-5fb39a196145"))));
		assertThat(response.getBankTransfers().get(0).getFromBankTransactionID(), is(equalTo(UUID.fromString("e4059952-5acb-4a56-b076-53fad85f2930"))));
		assertThat(response.getBankTransfers().get(0).getToBankAccount().getName(), is(equalTo("Business Wells Fargo")));
        assertThat(response.getBankTransfers().get(0).getToBankAccount().getAccountID(), is(equalTo(UUID.fromString("6f7594f2-f059-4d56-9e67-47ac9733bfe9"))));
        assertThat(response.getBankTransfers().get(0).getFromBankAccount().getName(), is(equalTo("My Savings")));
        assertThat(response.getBankTransfers().get(0).getFromBankAccount().getAccountID(), is(equalTo(UUID.fromString("7e5e243b-9fcd-4aef-8e3a-c70be1e39bfa"))));
        //System.out.println(response.getBankTransfers().get(0).toString());
    }
    @Test
    public void getBankTransferHistoryTest() throws IOException {
        System.out.println("@Test - getBankTransferHistoryTest");

        UUID bankTransferID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");  
        HistoryRecords response = api.getBankTransferHistory(bankTransferID);
        assertThat(response.getHistoryRecords().get(0).getUser(), is(equalTo("System Generated")));       
        assertThat(response.getHistoryRecords().get(0).getChanges(), is(equalTo("Attached a file")));     
        assertThat(response.getHistoryRecords().get(0).getDetails(), is(equalTo("Attached the file sample2.jpg through the Xero API using Java Partner Example")));     
        //System.out.println(response.getHistoryRecords().get(0).toString());
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
        System.out.println("@Test - createBankTransferAttachmentByFileNameTest");

        UUID bankTransferID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");  
        InputStream inputStream = CustomJsonConfig.class.getResourceAsStream("/helo-heros.jpg");
        byte[] body = IOUtils.toByteArray(inputStream);
        String fileName = "sample5.jpg";
        
        Attachments response = api.createBankTransferAttachmentByFileName(bankTransferID, fileName, body);
       	assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("9478be4c-c707-48c1-b4a7-83d8eaf442b5"))));
        assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("sample5.jpg")));
        assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/BankTransfers/6221458a-ef7a-4d5f-9b1c-1b96ce03833c/Attachments/sample5.jpg")));
        //System.out.println(response.getAttachments().get(0).toString());	
    }

    @Test
    public void getBankTransferAttachmentsTest() throws IOException {
        System.out.println("@Test - getBankTransferAttachmentsTest");

        UUID bankTransferID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");  
        Attachments response = api.getBankTransferAttachments(bankTransferID);
        assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("e05a6fd8-0e47-47a9-9799-b809c8267260"))));
        assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/BankTransfers/6221458a-ef7a-4d5f-9b1c-1b96ce03833c/Attachments/HelloWorld.jpg")));
        //System.out.println(response.getAttachments().get(0).toString());
    }
    
    @Test
    public void updateBankTransferAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - updateBankTransferAttachmentByFileNameTest");

        UUID bankTransferID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");  
        InputStream inputStream = CustomJsonConfig.class.getResourceAsStream("/helo-heros.jpg");
        byte[] body = IOUtils.toByteArray(inputStream);
        String fileName = "sample5.jpg";
        
        Attachments response = api.updateBankTransferAttachmentByFileName(bankTransferID, fileName, body);
        assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("0851935c-c4c5-4de8-9247-ce22efde6f82"))));
        assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("sample5.jpg")));
        assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/BankTransfers/6221458a-ef7a-4d5f-9b1c-1b96ce03833c/Attachments/sample5.jpg")));
        //System.out.println(response.getAttachments().get(0).toString());
    }	
}
