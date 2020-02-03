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

public class AccountingApiPaymentsTest {

	ApiClient defaultClient; 
    AccountingApi accountingApi; 
	String accessToken;
    String xeroTenantId; 
     
    
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
    public void createPaymentsTest() throws IOException {
        System.out.println("@Test - createPayments");
        Payments payments = new Payments();
        Payments response = accountingApi.createPayments(accessToken,xeroTenantId,payments);

        assertThat(response.getPayments().get(0).getInvoice().getInvoiceNumber(), is(equalTo("INV-0004")));
        assertThat(response.getPayments().get(0).getAccount().getCode(), is(equalTo("970")));
        assertThat(response.getPayments().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,11))));  
        assertThat(response.getPayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPayments().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPayments().get(0).getAmount(), is(equalTo(1.0)));
        assertThat(response.getPayments().get(0).getAmount().toString(), is(equalTo("1.0")));
        assertThat(response.getPayments().get(0).getIsReconciled(), is(equalTo(false)));
        assertThat(response.getPayments().get(0).getStatus(), is(equalTo(com.xero.models.accounting.Payment.StatusEnum.AUTHORISED)));
        assertThat(response.getPayments().get(0).getPaymentType(), is(equalTo(com.xero.models.accounting.Payment.PaymentTypeEnum.ACCRECPAYMENT)));
        assertThat(response.getPayments().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-12T15:10:38.623-08:00"))));
        assertThat(response.getPayments().get(0).getPaymentID(), is(equalTo(UUID.fromString("61ed71fc-01bf-4eb8-8419-8a18789ff45f"))));
        assertThat(response.getPayments().get(0).getHasAccount(), is(equalTo(true)));
        assertThat(response.getPayments().get(0).getHasValidationErrors(), is(equalTo(true)));
        assertThat(response.getPayments().get(0).getValidationErrors().get(0).getMessage(), is(equalTo("Payment amount exceeds the amount outstanding on this document")));
        //System.out.println(response.getPayments().get(0).toString());
    }
    
    @Test
    public void createPaymentHistoryTest() throws IOException {
        System.out.println("@Test - createPaymentHistory");
        UUID paymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords historyRecords = new HistoryRecords();
        //HistoryRecords response = accountingApi.createPaymentHistory(paymentID, historyRecords);
        // TODO: test validations
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }
    
    @Test
    public void deletePaymentTest() throws IOException {
        System.out.println("@Test - deletePayment");
        UUID paymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Payments payments = new Payments();
        Payments response = accountingApi.deletePayment(accessToken,xeroTenantId,paymentID, payments);

        assertThat(response.getPayments().get(0).getInvoice().getInvoiceNumber(), is(equalTo("INV-0006")));
        assertThat(response.getPayments().get(0).getAccount().getCode(), is(equalTo("980")));
        assertThat(response.getPayments().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,18))));  
        assertThat(response.getPayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPayments().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPayments().get(0).getAmount(), is(equalTo(148062.76)));
        assertThat(response.getPayments().get(0).getAmount().toString(), is(equalTo("148062.76")));
        assertThat(response.getPayments().get(0).getReference(), is(equalTo("Yahoo")));
        assertThat(response.getPayments().get(0).getIsReconciled(), is(equalTo(false)));
        assertThat(response.getPayments().get(0).getStatus(), is(equalTo(com.xero.models.accounting.Payment.StatusEnum.DELETED)));
        assertThat(response.getPayments().get(0).getPaymentType(), is(equalTo(com.xero.models.accounting.Payment.PaymentTypeEnum.ACCRECPAYMENT)));
        assertThat(response.getPayments().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-12T15:17:21.940-08:00"))));
        assertThat(response.getPayments().get(0).getPaymentID(), is(equalTo(UUID.fromString("38928000-e9a0-420c-8884-f624bab2a351"))));
        assertThat(response.getPayments().get(0).getHasAccount(), is(equalTo(true)));
        assertThat(response.getPayments().get(0).getHasValidationErrors(), is(equalTo(false)));
        //System.out.println(response.getPayments().get(0).toString());
    }

     @Test
    public void getPaymentTest() throws IOException {
        System.out.println("@Test - getPayment");
        UUID paymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Payments response = accountingApi.getPayment(accessToken,xeroTenantId,paymentID);

        assertThat(response.getPayments().get(0).getInvoice().getInvoiceNumber(), is(equalTo("INV-0002")));
        assertThat(response.getPayments().get(0).getAccount().getCode(), is(equalTo("970")));
        assertThat(response.getPayments().get(0).getDate(), is(equalTo(LocalDate.of(2018,11,28))));  
        assertThat(response.getPayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPayments().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPayments().get(0).getAmount(), is(equalTo(46.0)));
        assertThat(response.getPayments().get(0).getAmount().toString(), is(equalTo("46.0")));
        assertThat(response.getPayments().get(0).getIsReconciled(), is(equalTo(false)));
        assertThat(response.getPayments().get(0).getStatus(), is(equalTo(com.xero.models.accounting.Payment.StatusEnum.AUTHORISED)));
        assertThat(response.getPayments().get(0).getPaymentType(), is(equalTo(com.xero.models.accounting.Payment.PaymentTypeEnum.ACCRECPAYMENT)));
        assertThat(response.getPayments().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2018-11-02T08:36:32.690-08:00"))));
        assertThat(response.getPayments().get(0).getPaymentID(), is(equalTo(UUID.fromString("99ea7f6b-c513-4066-bc27-b7c65dcd76c2"))));
        assertThat(response.getPayments().get(0).getHasAccount(), is(equalTo(true)));
        assertThat(response.getPayments().get(0).getHasValidationErrors(), is(equalTo(false)));
        //System.out.println(response.getPayments().get(0).toString());
    }
   
    @Test
    public void getPaymentHistoryTest() throws IOException {
        System.out.println("@Test - getPaymentHistory");
        UUID paymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords response = accountingApi.getPaymentHistory(accessToken,xeroTenantId,paymentID);

        assertThat(response.getHistoryRecords().get(0).getUser(), is(equalTo("Sidney Maestre")));       
        assertThat(response.getHistoryRecords().get(0).getChanges(), is(equalTo("Created")));     
        assertThat(response.getHistoryRecords().get(0).getDateUTC(), is(equalTo(OffsetDateTime.parse("2018-11-02T08:36:32.690-08:00"))));
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }
    
    @Test
    public void getPaymentsTest() throws IOException {
        System.out.println("@Test - getPayments");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        Payments response = accountingApi.getPayments(accessToken,xeroTenantId,ifModifiedSince, where, order);

        assertThat(response.getPayments().get(0).getInvoice().getInvoiceNumber(), is(equalTo("INV-0002")));
        assertThat(response.getPayments().get(0).getAccount().getCode(), is(equalTo("970")));
        assertThat(response.getPayments().get(0).getDate(), is(equalTo(LocalDate.of(2018,11,28))));  
        assertThat(response.getPayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
        assertThat(response.getPayments().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
        assertThat(response.getPayments().get(0).getAmount(), is(equalTo(46.0)));
        assertThat(response.getPayments().get(0).getAmount().toString(), is(equalTo("46.0")));
        assertThat(response.getPayments().get(0).getIsReconciled(), is(equalTo(false)));
        assertThat(response.getPayments().get(0).getStatus(), is(equalTo(com.xero.models.accounting.Payment.StatusEnum.AUTHORISED)));
        assertThat(response.getPayments().get(0).getPaymentType(), is(equalTo(com.xero.models.accounting.Payment.PaymentTypeEnum.ACCRECPAYMENT)));
        assertThat(response.getPayments().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2018-11-02T08:36:32.690-08:00"))));
        assertThat(response.getPayments().get(0).getPaymentID(), is(equalTo(UUID.fromString("99ea7f6b-c513-4066-bc27-b7c65dcd76c2"))));
        assertThat(response.getPayments().get(0).getHasAccount(), is(equalTo(true)));
        assertThat(response.getPayments().get(0).getHasValidationErrors(), is(equalTo(false)));
        //System.out.println(response.getPayments().get(0).toString());
    }
}
