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

public class AccountingApiPurchaseOrdersTest {

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
            Thread.sleep(30000);
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
    public void createPurchaseOrderTest() throws IOException {
        System.out.println("@Test - createPurchaseOrder");
        PurchaseOrders purchaseOrders = null;
        Boolean summarizeErrors = null;
        PurchaseOrders response = api.createPurchaseOrder(purchaseOrders, summarizeErrors);

        // TODO: test validations
        assertThat(response.getPurchaseOrders().get(0).getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getLineItemID(), is(equalTo(UUID.fromString("792b7e40-b9f2-47f0-8624-b09f4b0166dd"))));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getDescription(), is(equalTo("Foobar")));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxType(), is(equalTo("INPUT2")));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("710")));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(3.0)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,12))));  
        assertThat(response.getPurchaseOrders().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getPurchaseOrders().get(0).getPurchaseOrderNumber(), is(equalTo("PO-0004")));
        assertThat(response.getPurchaseOrders().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getPurchaseOrders().get(0).getStatus(), is(equalTo(com.xero.models.accounting.PurchaseOrder.StatusEnum.DRAFT)));
        assertThat(response.getPurchaseOrders().get(0).getSentToContact(), is(equalTo(false)));
        assertThat(response.getPurchaseOrders().get(0).getPurchaseOrderID(), is(equalTo(UUID.fromString("56204648-8fbe-46f8-b09c-2125f7939533"))));
        assertThat(response.getPurchaseOrders().get(0).getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPurchaseOrders().get(0).getSubTotal(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().get(0).getSubTotal().toString(), is(equalTo("20.0")));
        assertThat(response.getPurchaseOrders().get(0).getTotalTax(), is(equalTo(3.0)));
        assertThat(response.getPurchaseOrders().get(0).getTotalTax().toString(), is(equalTo("3.0")));
        assertThat(response.getPurchaseOrders().get(0).getTotal(), is(equalTo(23.0)));
        assertThat(response.getPurchaseOrders().get(0).getTotal().toString(), is(equalTo("23.0")));
        assertThat(response.getPurchaseOrders().get(0).getTotalDiscount(), is(equalTo(0.0)));
        assertThat(response.getPurchaseOrders().get(0).getTotalDiscount().toString(), is(equalTo("0.0")));
        assertThat(response.getPurchaseOrders().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-13T17:22:26.077-07:00"))));  
        assertThat(response.getPurchaseOrders().get(0).getValidationErrors().get(0).getMessage(), is(equalTo("Order number must be unique")));
        assertThat(response.getPurchaseOrders().get(0).getWarnings().get(0).getMessage(), is(equalTo("Only AUTHORISED and BILLED purchase orders may have SentToContact updated.")));
        //System.out.println(response.getPurchaseOrders().get(0).toString());
    }
    
    @Test
    public void createPurchaseOrderHistoryTest() throws IOException {
        System.out.println("@Test - createPurchaseOrderHistory");
        UUID purchaseOrderID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords historyRecords = null;
        HistoryRecords response = api.createPurchaseOrderHistory(purchaseOrderID, historyRecords);

        assertThat(response.getHistoryRecords().get(0).getDetails(), is(equalTo("Hello World")));     
        assertThat(response.getHistoryRecords().get(0).getDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-13T17:39:39.354-07:00"))));  
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }

    @Test
    public void getPurchaseOrderTest() throws IOException {
        System.out.println("@Test - getPurchaseOrder");
        UUID purchaseOrderID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        PurchaseOrders response = api.getPurchaseOrder(purchaseOrderID);

        assertThat(response.getPurchaseOrders().get(0).getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getLineItemID(), is(equalTo(UUID.fromString("8a9d3eca-e052-43bc-9b87-221d0648c045"))));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getDescription(), is(equalTo("Brand new Fender Strats")));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(2500.0)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getItemCode(), is(equalTo("123")));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxType(), is(equalTo("INPUT2")));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("630")));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(337.5)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(2250.0)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getTracking().get(0).getName(), is(equalTo("Simpsons")));
        assertThat(response.getPurchaseOrders().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,25))));  
        assertThat(response.getPurchaseOrders().get(0).getDeliveryDate(), is(equalTo(LocalDate.of(2019,03,27))));  
        assertThat(response.getPurchaseOrders().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getPurchaseOrders().get(0).getPurchaseOrderNumber(), is(equalTo("PO-0006")));
        assertThat(response.getPurchaseOrders().get(0).getReference(), is(equalTo("foobar")));
        assertThat(response.getPurchaseOrders().get(0).getBrandingThemeID(), is(equalTo(UUID.fromString("414d4a87-46d6-4cfc-ab42-4e29d22e5076"))));
        assertThat(response.getPurchaseOrders().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getPurchaseOrders().get(0).getStatus(), is(equalTo(com.xero.models.accounting.PurchaseOrder.StatusEnum.DRAFT)));
        assertThat(response.getPurchaseOrders().get(0).getSentToContact(), is(equalTo(false)));
        assertThat(response.getPurchaseOrders().get(0).getDeliveryAddress(), is(equalTo("101 Grafton Road\nRoseneath\nWellington\n6011\nNew Zealand")));
        assertThat(response.getPurchaseOrders().get(0).getAttentionTo(), is(equalTo("CEO")));
        assertThat(response.getPurchaseOrders().get(0).getTelephone(), is(equalTo("64 123-2222")));
        assertThat(response.getPurchaseOrders().get(0).getDeliveryInstructions(), is(equalTo("Drop off at front  door")));
        assertThat(response.getPurchaseOrders().get(0).getPurchaseOrderID(), is(equalTo(UUID.fromString("15369a9f-17b6-4235-83c4-0029256d1c37"))));
        assertThat(response.getPurchaseOrders().get(0).getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPurchaseOrders().get(0).getSubTotal(), is(equalTo(2250.0)));
        assertThat(response.getPurchaseOrders().get(0).getSubTotal().toString(), is(equalTo("2250.0")));
        assertThat(response.getPurchaseOrders().get(0).getTotalTax(), is(equalTo(337.5)));
        assertThat(response.getPurchaseOrders().get(0).getTotalTax().toString(), is(equalTo("337.5")));
        assertThat(response.getPurchaseOrders().get(0).getTotal(), is(equalTo(2587.5)));
        assertThat(response.getPurchaseOrders().get(0).getTotal().toString(), is(equalTo("2587.5")));
        assertThat(response.getPurchaseOrders().get(0).getTotalDiscount(), is(equalTo(250.0)));
        assertThat(response.getPurchaseOrders().get(0).getTotalDiscount().toString(), is(equalTo("250.0")));
        assertThat(response.getPurchaseOrders().get(0).getHasAttachments(), is(equalTo(true)));
        assertThat(response.getPurchaseOrders().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-26T11:47:09.823-07:00"))));  
        assertThat(response.getPurchaseOrders().get(0).getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("7d94ccdc-ef7b-4806-87ac-8442f25e593b"))));
        assertThat(response.getPurchaseOrders().get(0).getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.png")));
        assertThat(response.getPurchaseOrders().get(0).getAttachments().get(0).getMimeType(), is(equalTo("image/png")));
        assertThat(response.getPurchaseOrders().get(0).getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/PurchaseOrders/15369a9f-17b6-4235-83c4-0029256d1c37/Attachments/HelloWorld.png")));
        assertThat(response.getPurchaseOrders().get(0).getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("76091"))));
        assertThat(response.getPurchaseOrders().get(0).getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));        
        //System.out.println(response.getPurchaseOrders().get(0).toString());
    }
    
    @Test
    public void getPurchaseOrderHistoryTest() throws IOException {
        System.out.println("@Test - getPurchaseOrderHistory");
        UUID purchaseOrderID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords response = api.getPurchaseOrderHistory(purchaseOrderID);

        assertThat(response.getHistoryRecords().get(0).getUser(), is(equalTo("System Generated")));       
        assertThat(response.getHistoryRecords().get(0).getChanges(), is(equalTo("Note")));     
        assertThat(response.getHistoryRecords().get(0).getDetails(), is(equalTo("Hello World")));     
        assertThat(response.getHistoryRecords().get(0).getDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-13T17:22:28.670-07:00"))));  
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }
    
    @Test
    public void getPurchaseOrdersTest() throws IOException {
        System.out.println("@Test - getPurchaseOrders");
        OffsetDateTime ifModifiedSince = null;
        String status = null;
        String dateFrom = null;
        String dateTo = null;
        String order = null;
        Integer page = null;
        PurchaseOrders response = api.getPurchaseOrders(ifModifiedSince, status, dateFrom, dateTo, order, page);

        assertThat(response.getPurchaseOrders().get(0).getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getLineItemID(), is(equalTo(UUID.fromString("0f7b54b8-bfa4-4c5d-9c22-73dbd5796e54"))));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getDescription(), is(equalTo("Foobar")));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(0.0)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,11))));  
        assertThat(response.getPurchaseOrders().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getPurchaseOrders().get(0).getPurchaseOrderNumber(), is(equalTo("PO-0001")));
        assertThat(response.getPurchaseOrders().get(0).getAttentionTo(), is(equalTo("Jimmy")));
        assertThat(response.getPurchaseOrders().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getPurchaseOrders().get(0).getStatus(), is(equalTo(com.xero.models.accounting.PurchaseOrder.StatusEnum.DELETED)));
        assertThat(response.getPurchaseOrders().get(0).getPurchaseOrderID(), is(equalTo(UUID.fromString("f9627f0d-b715-4039-bb6a-96dc3eae5ec5"))));
        assertThat(response.getPurchaseOrders().get(0).getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPurchaseOrders().get(0).getSubTotal(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().get(0).getSubTotal().toString(), is(equalTo("20.0")));
        assertThat(response.getPurchaseOrders().get(0).getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getPurchaseOrders().get(0).getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getPurchaseOrders().get(0).getTotal(), is(equalTo(20.0)));       
        assertThat(response.getPurchaseOrders().get(0).getTotal().toString(), is(equalTo("20.0")));
        assertThat(response.getPurchaseOrders().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-13T17:18:23.443-07:00"))));  
        //System.out.println(response.getPurchaseOrders().get(0).toString());
    }
    
    @Test
    public void updatePurchaseOrderTest() throws IOException {
        System.out.println("@Test - updatePurchaseOrder");
        UUID purchaseOrderID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        PurchaseOrders purchaseOrders = null;
        PurchaseOrders response = api.updatePurchaseOrder(purchaseOrderID, purchaseOrders);

        assertThat(response.getPurchaseOrders().get(0).getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getLineItemID(), is(equalTo(UUID.fromString("d1d9b2cd-c9f2-4445-8d98-0b8096cf4dae"))));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getDescription(), is(equalTo("Foobar")));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxType(), is(equalTo("INPUT2")));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("710")));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(3.0)));
        assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,12))));  
        assertThat(response.getPurchaseOrders().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
        assertThat(response.getPurchaseOrders().get(0).getPurchaseOrderNumber(), is(equalTo("PO-0005")));
        assertThat(response.getPurchaseOrders().get(0).getAttentionTo(), is(equalTo("Jimmy")));
        assertThat(response.getPurchaseOrders().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getPurchaseOrders().get(0).getStatus(), is(equalTo(com.xero.models.accounting.PurchaseOrder.StatusEnum.DRAFT)));
        assertThat(response.getPurchaseOrders().get(0).getSentToContact(), is(equalTo(false)));
        assertThat(response.getPurchaseOrders().get(0).getPurchaseOrderID(), is(equalTo(UUID.fromString("f9fc1120-c937-489e-84bc-e822190cfe9c"))));
        assertThat(response.getPurchaseOrders().get(0).getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPurchaseOrders().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPurchaseOrders().get(0).getSubTotal(), is(equalTo(20.0)));
        assertThat(response.getPurchaseOrders().get(0).getSubTotal().toString(), is(equalTo("20.0")));
        assertThat(response.getPurchaseOrders().get(0).getTotalTax(), is(equalTo(3.0)));
        assertThat(response.getPurchaseOrders().get(0).getTotalTax().toString(), is(equalTo("3.0")));
        assertThat(response.getPurchaseOrders().get(0).getTotal(), is(equalTo(23.0)));       
        assertThat(response.getPurchaseOrders().get(0).getTotal().toString(), is(equalTo("23.0")));
        assertThat(response.getPurchaseOrders().get(0).getTotalDiscount(), is(equalTo(0.0)));
        assertThat(response.getPurchaseOrders().get(0).getTotalDiscount().toString(), is(equalTo("0.0")));
        assertThat(response.getPurchaseOrders().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-13T17:39:36.853-07:00"))));  
        //System.out.println(response.getPurchaseOrders().get(0).toString());
    }
}
