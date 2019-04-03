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

public class AccountingApiRepeatingInvoicesTest {

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
            Thread.sleep(60);
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
    public void createRepeatingInvoiceAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - createRepeatingInvoiceAttachmentByFileName");
        UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
        InputStream inputStream = CustomJsonConfig.class.getResourceAsStream("/helo-heros.jpg");
        byte[] body = IOUtils.toByteArray(inputStream);
        Attachments response = api.createRepeatingInvoiceAttachmentByFileName(repeatingInvoiceID, fileName, body);

        assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("e078e56c-9a2b-4f6c-a1fa-5d19b0dab611"))));
        assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("foobar.jpg")));
        assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/RepeatingInvoices/428c0d75-909f-4b04-8403-a48dc27283b0/Attachments/foobar.jpg")));
        assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().get(0).toString());
    }
    
    @Test
    public void createRepeatingInvoiceHistoryTest() throws IOException {
        System.out.println("@Test - createRepeatingInvoiceHistory - not implmented");
        UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords historyRecords = null;
        //HistoryRecords response = api.createRepeatingInvoiceHistory(repeatingInvoiceID, historyRecords);
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }

     @Test
    public void getRepeatingInvoiceTest() throws IOException {
        System.out.println("@Test - getRepeatingInvoice");
        UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        RepeatingInvoices response = api.getRepeatingInvoice(repeatingInvoiceID);

        assertThat(response.getRepeatingInvoices().get(0).getType(), is(equalTo(com.xero.models.accounting.RepeatingInvoice.TypeEnum.ACCREC)));
        assertThat(response.getRepeatingInvoices().get(0).getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getPeriod(), is(equalTo(1)));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getUnit(), is(equalTo(com.xero.models.accounting.Schedule.UnitEnum.MONTHLY)));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getDueDate(), is(equalTo(10)));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getDueDateType(), is(equalTo(com.xero.models.accounting.Schedule.DueDateTypeEnum.OFFOLLOWINGMONTH)));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getStartDate(), is(equalTo(LocalDate.of(2019,04,14))));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getNextScheduledDate(), is(equalTo(LocalDate.of(2019,04,14))));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getEndDate(), is(equalTo(LocalDate.of(2019,9,29))));         
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getLineItemID(), is(equalTo(UUID.fromString("13a8353c-d2af-4d5b-920c-438449f08900"))));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getDescription(), is(equalTo("Guitars Fender Strat")));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(5000.0)));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getTaxType(), is(equalTo("OUTPUT2")));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("200")));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(750.0)));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(5000.0)));
        assertThat(response.getRepeatingInvoices().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getRepeatingInvoices().get(0).getReference(), is(equalTo("[Week]")));
        assertThat(response.getRepeatingInvoices().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getRepeatingInvoices().get(0).getStatus(), is(equalTo(com.xero.models.accounting.RepeatingInvoice.StatusEnum.AUTHORISED)));
        assertThat(response.getRepeatingInvoices().get(0).getSubTotal(), is(equalTo(5000.0)));
        assertThat(response.getRepeatingInvoices().get(0).getSubTotal().toString(), is(equalTo("5000.0")));
        assertThat(response.getRepeatingInvoices().get(0).getTotalTax(), is(equalTo(750.0)));
        assertThat(response.getRepeatingInvoices().get(0).getTotalTax().toString(), is(equalTo("750.0")));
        assertThat(response.getRepeatingInvoices().get(0).getTotal(), is(equalTo(5750.0)));
        assertThat(response.getRepeatingInvoices().get(0).getTotal().toString(), is(equalTo("5750.0")));
        assertThat(response.getRepeatingInvoices().get(0).getRepeatingInvoiceID(), is(equalTo(UUID.fromString("428c0d75-909f-4b04-8403-a48dc27283b0"))));
        assertThat(response.getRepeatingInvoices().get(0).getID(), is(equalTo(UUID.fromString("428c0d75-909f-4b04-8403-a48dc27283b0"))));
        assertThat(response.getRepeatingInvoices().get(0).getHasAttachments(), is(equalTo(true)));
        assertThat(response.getRepeatingInvoices().get(0).getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("2a488b0f-3966-4b6e-a7e1-b6d3286351f2"))));
        assertThat(response.getRepeatingInvoices().get(0).getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getRepeatingInvoices().get(0).getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getRepeatingInvoices().get(0).getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Invoices/428c0d75-909f-4b04-8403-a48dc27283b0/Attachments/HelloWorld.jpg")));
        assertThat(response.getRepeatingInvoices().get(0).getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getRepeatingInvoices().get(0).getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getRepeatingInvoices().get(0).toString());
    }
    
    @Test
    public void getRepeatingInvoiceAttachmentsTest() throws IOException {
        System.out.println("@Test - getRepeatingInvoiceAttachments");
        UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Attachments response = api.getRepeatingInvoiceAttachments(repeatingInvoiceID);

        assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("2a488b0f-3966-4b6e-a7e1-b6d3286351f2"))));
        assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/RepeatingInvoices/428c0d75-909f-4b04-8403-a48dc27283b0/Attachments/HelloWorld.jpg")));
        assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().get(0).toString());
    }
    
    @Test
    public void getRepeatingInvoiceHistoryTest() throws IOException {
        System.out.println("@Test - getRepeatingInvoiceHistory");
        UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords response = api.getRepeatingInvoiceHistory(repeatingInvoiceID);

        assertThat(response.getHistoryRecords().get(0).getUser(), is(equalTo("System Generated")));       
        assertThat(response.getHistoryRecords().get(0).getChanges(), is(equalTo("Attached a file")));     
        assertThat(response.getHistoryRecords().get(0).getDetails(), is(equalTo("Attached the file foobar.jpg through the Xero API using Java Partner Example")));     
        assertThat(response.getHistoryRecords().get(0).getDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-15T15:07:28.587-07:00"))));  
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }
    
    @Test
    public void getRepeatingInvoicesTest() throws IOException {
        System.out.println("@Test - getRepeatingInvoices");
        String where = null;
        String order = null;
        RepeatingInvoices response = api.getRepeatingInvoices(where, order);

        assertThat(response.getRepeatingInvoices().get(0).getType(), is(equalTo(com.xero.models.accounting.RepeatingInvoice.TypeEnum.ACCREC)));
        assertThat(response.getRepeatingInvoices().get(0).getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getPeriod(), is(equalTo(1)));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getUnit(), is(equalTo(com.xero.models.accounting.Schedule.UnitEnum.MONTHLY)));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getDueDate(), is(equalTo(10)));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getDueDateType(), is(equalTo(com.xero.models.accounting.Schedule.DueDateTypeEnum.OFFOLLOWINGMONTH)));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getStartDate(), is(equalTo(LocalDate.of(2019,04,14))));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getNextScheduledDate(), is(equalTo(LocalDate.of(2019,04,14))));
        assertThat(response.getRepeatingInvoices().get(0).getSchedule().getEndDate(), is(equalTo(LocalDate.of(2019,9,29))));         
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getLineItemID(), is(equalTo(UUID.fromString("13a8353c-d2af-4d5b-920c-438449f08900"))));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getDescription(), is(equalTo("Guitars Fender Strat")));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(5000.0)));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getTaxType(), is(equalTo("OUTPUT2")));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("200")));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(750.0)));
        assertThat(response.getRepeatingInvoices().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(5000.0)));
        assertThat(response.getRepeatingInvoices().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getRepeatingInvoices().get(0).getReference(), is(equalTo("[Week]")));
        assertThat(response.getRepeatingInvoices().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getRepeatingInvoices().get(0).getStatus(), is(equalTo(com.xero.models.accounting.RepeatingInvoice.StatusEnum.AUTHORISED)));
        assertThat(response.getRepeatingInvoices().get(0).getSubTotal(), is(equalTo(5000.0)));
        assertThat(response.getRepeatingInvoices().get(0).getSubTotal().toString(), is(equalTo("5000.0")));
        assertThat(response.getRepeatingInvoices().get(0).getTotalTax(), is(equalTo(750.0)));
        assertThat(response.getRepeatingInvoices().get(0).getTotalTax().toString(), is(equalTo("750.0")));
        assertThat(response.getRepeatingInvoices().get(0).getTotal(), is(equalTo(5750.0)));
        assertThat(response.getRepeatingInvoices().get(0).getTotal().toString(), is(equalTo("5750.0")));
        assertThat(response.getRepeatingInvoices().get(0).getRepeatingInvoiceID(), is(equalTo(UUID.fromString("428c0d75-909f-4b04-8403-a48dc27283b0"))));
        assertThat(response.getRepeatingInvoices().get(0).getID(), is(equalTo(UUID.fromString("428c0d75-909f-4b04-8403-a48dc27283b0"))));
        assertThat(response.getRepeatingInvoices().get(0).getHasAttachments(), is(equalTo(true)));
        //System.out.println(response.getRepeatingInvoices().get(0).toString());
    }

    @Test
    public void updateRepeatingInvoiceAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - updateRepeatingInvoiceAttachmentByFileName");
        UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        String fileName = "sample5.jpg";
        InputStream inputStream = CustomJsonConfig.class.getResourceAsStream("/helo-heros.jpg");
        byte[] body = IOUtils.toByteArray(inputStream);
        Attachments response = api.updateRepeatingInvoiceAttachmentByFileName(repeatingInvoiceID, fileName, body);

        assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("d086d5f4-9c3d-4edc-a87e-906248eeb652"))));
        assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
        assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/RepeatingInvoices/428c0d75-909f-4b04-8403-a48dc27283b0/Attachments/HelloWorld.jpg")));
        assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().get(0).toString());
    }
}
