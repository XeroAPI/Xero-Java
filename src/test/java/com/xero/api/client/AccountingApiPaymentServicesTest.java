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

public class AccountingApiPaymentServicesTest {

	CustomJsonConfig config;
	ApiClient apiClientForAccounting; 
	AccountingApi api; 

    private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
		config = new CustomJsonConfig();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting-oauth1/2.0.0",null,null,null);
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
    public void createPaymentServiceTest() throws IOException {
        System.out.println("@Test - createPaymentService");
        PaymentServices paymentServices = null;
        PaymentServices response = api.createPaymentService(paymentServices);

        assertThat(response.getPaymentServices().get(0).getPaymentServiceID(), is(equalTo(UUID.fromString("54b3b4f6-0443-4fba-bcd1-61ec0c35ca55"))));
        assertThat(response.getPaymentServices().get(0).getPaymentServiceName(), is(equalTo("PayUpNow")));
        assertThat(response.getPaymentServices().get(0).getPaymentServiceUrl(), is(equalTo("https://www.payupnow.com/")));
        assertThat(response.getPaymentServices().get(0).getPayNowText(), is(equalTo("Time To Pay")));
        assertThat(response.getPaymentServices().get(0).getPaymentServiceType(), is(equalTo("Custom")));
        assertThat(response.getPaymentServices().get(0).getValidationErrors().get(0).getMessage(), is(equalTo("Payment service could not be found")));
        //System.out.println(response.getPaymentServices().get(0).toString());
    }

    @Test
    public void getPaymentServicesTest() throws IOException {
        System.out.println("@Test - getPaymentServices");
        PaymentServices response = api.getPaymentServices();

        assertThat(response.getPaymentServices().get(0).getPaymentServiceID(), is(equalTo(UUID.fromString("54b3b4f6-0443-4fba-bcd1-61ec0c35ca55"))));
        assertThat(response.getPaymentServices().get(0).getPaymentServiceName(), is(equalTo("PayUpNow")));
        assertThat(response.getPaymentServices().get(0).getPaymentServiceUrl(), is(equalTo("https://www.payupnow.com/")));
        assertThat(response.getPaymentServices().get(0).getPayNowText(), is(equalTo("Time To Pay")));
        assertThat(response.getPaymentServices().get(0).getPaymentServiceType(), is(equalTo("Custom")));
        //System.out.println(response.getPaymentServices().get(0).toString());
    }
}