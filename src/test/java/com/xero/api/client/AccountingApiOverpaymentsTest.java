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
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class AccountingApiOverpaymentsTest {

	ApiClient defaultClient; 
    AccountingApi accountingApi; 
    String xeroTenantId = "3697c2dc5-cc47-4afd-8ec8-74990b8761e9";  
    File body;

    private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
		// Set Access Token from Storage
        String accessToken = "123";
        Credential credential =  new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        
        // Create requestFactory with credentials
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);

        // Init AccountingApi client
        defaultClient = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting/2.0.0",null,null,null,requestFactory);
        accountingApi = new AccountingApi(defaultClient);
       
        ClassLoader classLoader = getClass().getClassLoader();
        body = new File(classLoader.getResource("helo-heros.jpg").getFile());
       
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
    public void createOverpaymentAllocationTest() throws IOException {
        System.out.println("@Test - createOverpaymentAllocation");
        UUID overpaymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Allocations allocations = new Allocations();
        Allocations response = accountingApi.createOverpaymentAllocation(xeroTenantId, overpaymentID, allocations);

        // TODO: test validations
        assertThat(response.getAllocations().get(0).getInvoice().getInvoiceID(), is(equalTo(UUID.fromString("c45720a1-ade3-4a38-a064-d15489be6841"))));
        assertThat(response.getAllocations().get(0).getAmount(), is(equalTo(1.0)));
        assertThat(response.getAllocations().get(0).getAmount().toString(), is(equalTo("1.0")));
        assertThat(response.getAllocations().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,11))));  
        //System.out.println(response.getAllocations().get(0).toString());
    }

    @Test
    public void createOverpaymentHistoryTest() throws IOException {
        System.out.println("@Test - createOverpaymentHistory - this is not implemented");
        UUID overpaymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords historyRecords = new HistoryRecords();
        //HistoryRecords response = accountingApi.createOverpaymentHistory(overpaymentID, historyRecords);
        // TODO: test validations
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }
    
    @Test
    public void getOverpaymentTest() throws IOException {
        System.out.println("@Test - getOverpayment");
        UUID overpaymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Overpayments response = accountingApi.getOverpayment(xeroTenantId, overpaymentID);

        //assertThat(response.getOverpayments().get(0).getType(), is(equalTo(com.xero.models.accounting.Overpayment.TypeEnum.SPEND-OVERPAYMENT)));
        assertThat(response.getOverpayments().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,11))));  
        assertThat(response.getOverpayments().get(0).getStatus(), is(equalTo(com.xero.models.accounting.Overpayment.StatusEnum.AUTHORISED)));
        assertThat(response.getOverpayments().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getOverpayments().get(0).getSubTotal(), is(equalTo(3000.0)));
        assertThat(response.getOverpayments().get(0).getSubTotal().toString(), is(equalTo("3000.0")));
        assertThat(response.getOverpayments().get(0).getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getOverpayments().get(0).getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getOverpayments().get(0).getTotal(), is(equalTo(3000.0)));
        assertThat(response.getOverpayments().get(0).getTotal().toString(), is(equalTo("3000.0")));
        assertThat(response.getOverpayments().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-12T15:15:52.890-07:00"))));  
        assertThat(response.getOverpayments().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getOverpayments().get(0).getOverpaymentID(), is(equalTo(UUID.fromString("ed7f6041-c915-4667-bd1d-54c48e92161e"))));
        assertThat(response.getOverpayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getOverpayments().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getOverpayments().get(0).getRemainingCredit(), is(equalTo(2999.0)));
        assertThat(response.getOverpayments().get(0).getRemainingCredit().toString(), is(equalTo("2999.0")));        
        assertThat(response.getOverpayments().get(0).getAllocations().get(0).getAmount(), is(equalTo(1.0)));
        assertThat(response.getOverpayments().get(0).getAllocations().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,11))));
        assertThat(response.getOverpayments().get(0).getAllocations().get(0).getInvoice().getInvoiceID(), is(equalTo(UUID.fromString("c45720a1-ade3-4a38-a064-d15489be6841"))));
        assertThat(response.getOverpayments().get(0).getHasAttachments(), is(equalTo(true)));
        assertThat(response.getOverpayments().get(0).getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("247dd942-5245-47a7-adb1-4d9ea075b431"))));
        assertThat(response.getOverpayments().get(0).getAttachments().get(0).getFileName(), is(equalTo("giphy.gif")));
        assertThat(response.getOverpayments().get(0).getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/banktransaction/ed7f6041-c915-4667-bd1d-54c48e92161e/Attachments/giphy.gif")));
        assertThat(response.getOverpayments().get(0).getAttachments().get(0).getMimeType(), is(equalTo("image/gif")));
        assertThat(response.getOverpayments().get(0).getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("495727"))));
        assertThat(response.getOverpayments().get(0).getLineItems().get(0).getDescription(), is(equalTo("Broken TV deposit")));
        assertThat(response.getOverpayments().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
        assertThat(response.getOverpayments().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(3000.0)));
        assertThat(response.getOverpayments().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("800")));
        assertThat(response.getOverpayments().get(0).getLineItems().get(0).getTaxType(), is(equalTo("NONE")));
        assertThat(response.getOverpayments().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(0.0)));
        assertThat(response.getOverpayments().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(3000.0)));
        //System.out.println(response.getOverpayments().get(0).toString());
    }
    
    @Test
    public void getOverpaymentHistoryTest() throws IOException {
        System.out.println("@Test - getOverpaymentHistory");
        UUID overpaymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords response = accountingApi.getOverpaymentHistory(xeroTenantId, overpaymentID);

        assertThat(response.getHistoryRecords().get(0).getUser(), is(equalTo("System Generated")));       
        assertThat(response.getHistoryRecords().get(0).getChanges(), is(equalTo("Applied")));     
        assertThat(response.getHistoryRecords().get(0).getDetails(), is(equalTo("Credit applied on 12 March 2019 for 1.00.")));     
        assertThat(response.getHistoryRecords().get(0).getDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-12T15:15:52.877-07:00"))));  
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }
    
    @Test
    public void getOverpaymentsTest() throws IOException {
        System.out.println("@Test - getOverpayments");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        Integer page = null;
        Integer unitdp = null;
        Overpayments response = accountingApi.getOverpayments(xeroTenantId, ifModifiedSince, where, order, page,unitdp);

        assertThat(response.getOverpayments().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,11))));  
        assertThat(response.getOverpayments().get(0).getStatus(), is(equalTo(com.xero.models.accounting.Overpayment.StatusEnum.AUTHORISED)));
        assertThat(response.getOverpayments().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
        assertThat(response.getOverpayments().get(0).getSubTotal(), is(equalTo(500.0)));
        assertThat(response.getOverpayments().get(0).getSubTotal().toString(), is(equalTo("500.0")));
        assertThat(response.getOverpayments().get(0).getTotalTax(), is(equalTo(0.0)));
        assertThat(response.getOverpayments().get(0).getTotalTax().toString(), is(equalTo("0.0")));
        assertThat(response.getOverpayments().get(0).getTotal(), is(equalTo(500.0)));
        assertThat(response.getOverpayments().get(0).getTotal().toString(), is(equalTo("500.0")));
        assertThat(response.getOverpayments().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-12T15:08:55.123-07:00"))));  
        assertThat(response.getOverpayments().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getOverpayments().get(0).getOverpaymentID(), is(equalTo(UUID.fromString("098b4dcb-5622-4699-87f8-9d40c4ccceb3"))));
        assertThat(response.getOverpayments().get(0).getRemainingCredit(), is(equalTo(500.0)));
        assertThat(response.getOverpayments().get(0).getRemainingCredit().toString(), is(equalTo("500.0")));        
        assertThat(response.getOverpayments().get(0).getHasAttachments(), is(equalTo(false)));
        //System.out.println(response.getOverpayments().get(0).toString());
    }
}