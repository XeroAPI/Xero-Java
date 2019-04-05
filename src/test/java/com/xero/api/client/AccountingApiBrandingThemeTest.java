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

public class AccountingApiBrandingThemeTest {

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
	UUID brandingThemeId;
	UUID paymentServiceId;
	private static boolean setUpIsDone = false;

	@Before
	public void setUp() {
		config = new CustomJsonConfig();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting/2.0.0",null,null,null);
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
		brandingThemeId = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	
		paymentServiceId = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");

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
		accountingApi = null;
		apiClientForAccounting = null;
	}

	@Test
	public void testGetBrandingThemes() throws Exception {
		System.out.println("@Test - getBrandingThemes");

		BrandingThemes bt = accountingApi.getBrandingThemes();
		assertThat(bt.getBrandingThemes().get(0).getBrandingThemeID().toString(), is(equalTo("dabc7637-62c1-4941-8a6e-ee44fa5090e7")));
		assertThat(bt.getBrandingThemes().get(0).getName(), is(equalTo("Standard")));
		//System.out.println(bt.toString());
	}

	@Test
	public void testGetBrandingTheme() throws Exception {
		System.out.println("@Test - getBrandingTheme");
		
		BrandingThemes oneBrandingTheme = accountingApi.getBrandingTheme(brandingThemeId);	
		assertThat(oneBrandingTheme.getBrandingThemes().get(0).getBrandingThemeID().toString(), is(equalTo("dabc7637-62c1-4941-8a6e-ee44fa5090e7")));
		assertThat(oneBrandingTheme.getBrandingThemes().get(0).getName(), is(equalTo("Standard")));
		//System.out.println(bt.toString());
	}

	@Test
	public void testGetBrandingThemePaymentServices() throws Exception {
		System.out.println("@Test - getBrandingThemePaymentServices");

		PaymentServices paymentServicesForBrandingTheme = accountingApi.getBrandingThemePaymentServices(brandingThemeId);	
		assertThat(paymentServicesForBrandingTheme.getPaymentServices().get(1).getPaymentServiceID().toString(), is(equalTo("dede7858-14e3-4a46-bf95-4d4cc491e645")));
		assertThat(paymentServicesForBrandingTheme.getPaymentServices().get(1).getPaymentServiceName(), is(equalTo("ACME Payment")));
		//System.out.println(paymentServicesForBrandingTheme.toString());
	}

	@Test
	public void testCreateBrandingThemePaymentServices() throws Exception {
		System.out.println("@Test - createBrandingThemePaymentServices");

		PaymentService btPaymentService = new PaymentService();
		btPaymentService.setPaymentServiceID(paymentServiceId);
		btPaymentService.setPaymentServiceName("ACME Payments");
		btPaymentService.setPaymentServiceUrl("http://www.mydomain.com/paymentservice");
		btPaymentService.setPayNowText("Pay Now");
		PaymentServices response = accountingApi.createBrandingThemePaymentServices(brandingThemeId, btPaymentService);	
		assertThat(response.getPaymentServices().get(0).getPaymentServiceID().toString(), is(equalTo("dede7858-14e3-4a46-bf95-4d4cc491e645")));
		assertThat(response.getPaymentServices().get(0).getPaymentServiceName(), is(equalTo("ACME Payments")));
		assertThat(response.getPaymentServices().get(0).getPaymentServiceUrl(), is(equalTo("https://www.payupnow.com/")));
		assertThat(response.getPaymentServices().get(0).getPayNowText(), is(equalTo("Pay Now")));
	
		//System.out.println(response.toString());
	}
}
