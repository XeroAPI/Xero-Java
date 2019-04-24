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

public class AccountingApiReceiptsTest {

	CustomJsonConfig config;
	ApiClient apiClientForAccounting; 
	AccountingApi api; 

    private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
		config = new CustomJsonConfig();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting/2.0.0",null,null,null);
		api = new AccountingApi(config);
		api.setApiClient(apiClientForAccounting);
		api.setOAuthToken(config.getConsumerKey(), config.getConsumerSecret());

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
		api = null;
		apiClientForAccounting = null;
	}

     @Test
    public void createReceiptTest() throws IOException {
        System.out.println("@Test - createReceipt");
        Receipts receipts = null;
        Receipts response = api.createReceipt(receipts);

        assertThat(response.getReceipts().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,13))));  
        assertThat(response.getReceipts().get(0).getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getReceipts().get(0).getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getReceipts().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getReceipts().get(0).getSubTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().get(0).getSubTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().get(0).getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getReceipts().get(0).getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getReceipts().get(0).getTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().get(0).getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().get(0).getReceiptID(), is(equalTo(UUID.fromString("a44fd147-af4e-4fe8-a09a-55332df74162"))));
        assertThat(response.getReceipts().get(0).getStatus(), is(equalTo(com.xero.models.accounting.Receipt.StatusEnum.DRAFT)));
        assertThat(response.getReceipts().get(0).getReceiptNumber(), is(equalTo("1")));
        assertThat(response.getReceipts().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-13T17:49:43.367-07:00"))));  
        assertThat(response.getReceipts().get(0).getHasAttachments(), is(equalTo(false)));
        assertThat(response.getReceipts().get(0).getValidationErrors().get(0).getMessage(), is(equalTo("A valid user should be identified using the UserID.")));
        //System.out.println(response.getReceipts().get(0).toString());
    }
    
    @Test
    public void createReceiptAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - createReceiptAttachmentByFileName");
        UUID receiptID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
        InputStream inputStream = CustomJsonConfig.class.getResourceAsStream("/helo-heros.jpg");
        byte[] body = IOUtils.toByteArray(inputStream);
        Attachments response = api.createReceiptAttachmentByFileName(receiptID, fileName, body);

        assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("3451e34c-66a6-42b0-91e2-88618bdc169b"))));
        assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("foobar.jpg")));
        assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Receipts/a44fd147-af4e-4fe8-a09a-55332df74162/Attachments/foobar.jpg")));
        assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().get(0).toString());
    }
    
   
    @Test
    public void createReceiptHistoryTest() throws IOException {
        System.out.println("@Test - createReceiptHistory - not implemented");
        UUID receiptID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords historyRecords = null;
        //HistoryRecords response = api.createReceiptHistory(receiptID, historyRecords);
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }

    @Test
    public void getReceiptTest() throws IOException {
        System.out.println("@Test - getReceipt");
        UUID receiptID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Receipts response = api.getReceipt(receiptID);

        assertThat(response.getReceipts().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,12))));  
        assertThat(response.getReceipts().get(0).getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getReceipts().get(0).getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getReceipts().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getReceipts().get(0).getSubTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().get(0).getSubTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().get(0).getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getReceipts().get(0).getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getReceipts().get(0).getTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().get(0).getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().get(0).getReceiptID(), is(equalTo(UUID.fromString("a44fd147-af4e-4fe8-a09a-55332df74162"))));
        assertThat(response.getReceipts().get(0).getStatus(), is(equalTo(com.xero.models.accounting.Receipt.StatusEnum.DRAFT)));
        assertThat(response.getReceipts().get(0).getReceiptNumber(), is(equalTo("1")));
        assertThat(response.getReceipts().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-13T17:49:43.983-07:00"))));  
        assertThat(response.getReceipts().get(0).getHasAttachments(), is(equalTo(true)));
        assertThat(response.getReceipts().get(0).getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("e02a84f6-b83a-4983-b3b9-35cd8880c7bc"))));
        assertThat(response.getReceipts().get(0).getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getReceipts().get(0).getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getReceipts().get(0).getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/receipts/a44fd147-af4e-4fe8-a09a-55332df74162/Attachments/HelloWorld.jpg")));
        assertThat(response.getReceipts().get(0).getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getReceipts().get(0).getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getReceipts().get(0).toString());
    }
    
    @Test
    public void getReceiptAttachmentsTest() throws IOException {
        System.out.println("@Test - getReceiptAttachments");
        UUID receiptID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Attachments response = api.getReceiptAttachments(receiptID);

        assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("11e5ca6b-d38c-42ab-a29f-c1710d171aa1"))));
        assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("giphy.gif")));
        assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/gif")));
        assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Receipts/7923c00d-163d-404c-a608-af3de333db29/Attachments/giphy.gif")));
        assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("495727"))));
        assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().get(0).toString());
    }
    
    @Test
    public void getReceiptHistoryTest() throws IOException {
        System.out.println("@Test - getReceiptHistory");
        UUID receiptID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords response = api.getReceiptHistory(receiptID);

        assertThat(response.getHistoryRecords().get(0).getUser(), is(equalTo("System Generated")));       
        assertThat(response.getHistoryRecords().get(0).getChanges(), is(equalTo("Edited")));     
        assertThat(response.getHistoryRecords().get(0).getDetails(), is(equalTo("Received through the Xero API from Java Partner Example")));     
        assertThat(response.getHistoryRecords().get(0).getDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-13T17:49:43.983-07:00"))));  
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }
    
    @Test
    public void getReceiptsTest() throws IOException {
        System.out.println("@Test - getReceipts");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        Integer unitdp = null;
        Receipts response = api.getReceipts(ifModifiedSince, where, order,unitdp);

        assertThat(response.getReceipts().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,12))));  
        assertThat(response.getReceipts().get(0).getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getReceipts().get(0).getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getReceipts().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getReceipts().get(0).getSubTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().get(0).getSubTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().get(0).getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getReceipts().get(0).getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getReceipts().get(0).getTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().get(0).getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().get(0).getReceiptID(), is(equalTo(UUID.fromString("a44fd147-af4e-4fe8-a09a-55332df74162"))));
        assertThat(response.getReceipts().get(0).getStatus(), is(equalTo(com.xero.models.accounting.Receipt.StatusEnum.DRAFT)));
        assertThat(response.getReceipts().get(0).getReceiptNumber(), is(equalTo("1")));
        assertThat(response.getReceipts().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-13T17:49:43.983-07:00"))));  
        assertThat(response.getReceipts().get(0).getHasAttachments(), is(equalTo(false)));
        //System.out.println(response.getReceipts().get(0).toString());
    }
    
    @Test
    public void updateReceiptTest() throws IOException {
        System.out.println("@Test - updateReceipt");
        UUID receiptID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Receipts receipts = null;
        Receipts response = api.updateReceipt(receiptID, receipts);

        assertThat(response.getReceipts().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,15))));  
        assertThat(response.getReceipts().get(0).getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getReceipts().get(0).getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getReceipts().get(0).getReference(), is(equalTo("Foobar")));
        assertThat(response.getReceipts().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getReceipts().get(0).getSubTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().get(0).getSubTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().get(0).getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getReceipts().get(0).getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getReceipts().get(0).getTotal(), is(equalTo(40.0)));
        assertThat(response.getReceipts().get(0).getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getReceipts().get(0).getReceiptID(), is(equalTo(UUID.fromString("e3686fdc-c661-4581-b9df-cbb20782ea66"))));
        assertThat(response.getReceipts().get(0).getStatus(), is(equalTo(com.xero.models.accounting.Receipt.StatusEnum.DRAFT)));
        assertThat(response.getReceipts().get(0).getReceiptNumber(), is(equalTo("2")));
        assertThat(response.getReceipts().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-15T11:45:56.927-07:00"))));  
        assertThat(response.getReceipts().get(0).getHasAttachments(), is(equalTo(false)));
        //System.out.println(response.getReceipts().get(0).toString());
    }
    
    @Test
    public void updateReceiptAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - updateReceiptAttachmentByFileName");
        UUID receiptID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
        InputStream inputStream = CustomJsonConfig.class.getResourceAsStream("/helo-heros.jpg");
        byte[] body = IOUtils.toByteArray(inputStream);
        Attachments response = api.updateReceiptAttachmentByFileName(receiptID, fileName, body);

        assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("e02a84f6-b83a-4983-b3b9-35cd8880c7bc"))));
        assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Receipts/a44fd147-af4e-4fe8-a09a-55332df74162/Attachments/HelloWorld.jpg")));
        assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().get(0).toString());
    }
}
