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

public class AccountingApiTaxRatesTest {

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
    public void createTaxRatesTest() throws IOException {
        System.out.println("@Test - createTaxRates");
        TaxRates taxRates = new TaxRates();
        TaxRates response = accountingApi.createTaxRates(accessToken,xeroTenantId,taxRates);

        assertThat(response.getTaxRates().get(0).getName(), is(equalTo("SDKTax29067")));
        assertThat(response.getTaxRates().get(0).getTaxType(), is(equalTo("TAX002")));
        assertThat(response.getTaxRates().get(0).getTaxComponents().get(0).getName(), is(equalTo("State Tax")));
        assertThat(response.getTaxRates().get(0).getTaxComponents().get(0).getRate(), is(equalTo(2.25)));
        assertThat(response.getTaxRates().get(0).getTaxComponents().get(0).getIsCompound(), is(equalTo(false)));
        assertThat(response.getTaxRates().get(0).getTaxComponents().get(0).getIsNonRecoverable(), is(equalTo(false)));        
        assertThat(response.getTaxRates().get(0).getStatus(), is(equalTo(com.xero.models.accounting.TaxRate.StatusEnum.ACTIVE)));
        assertThat(response.getTaxRates().get(0).getReportTaxType(), is(equalTo(com.xero.models.accounting.TaxRate.ReportTaxTypeEnum.INPUT)));
        assertThat(response.getTaxRates().get(0).getCanApplyToAssets(), is(equalTo(true)));
        assertThat(response.getTaxRates().get(0).getCanApplyToEquity(), is(equalTo(true)));
        assertThat(response.getTaxRates().get(0).getCanApplyToExpenses(), is(equalTo(true)));
        assertThat(response.getTaxRates().get(0).getCanApplyToLiabilities(), is(equalTo(true)));
        assertThat(response.getTaxRates().get(0).getCanApplyToRevenue(), is(equalTo(false)));
        assertThat(response.getTaxRates().get(0).getDisplayTaxRate(), is(equalTo(2.25)));
        assertThat(response.getTaxRates().get(0).getDisplayTaxRate().toString(), is(equalTo("2.25")));
        assertThat(response.getTaxRates().get(0).getEffectiveRate(), is(equalTo(2.25)));
        assertThat(response.getTaxRates().get(0).getEffectiveRate().toString(), is(equalTo("2.25")));
        //System.out.println(response.getTaxRates().get(0).toString());
    }

    @Test
    public void getTaxRatesTest() throws IOException {
        System.out.println("@Test - getTaxRates");
        String where = null;
        String order = null;
        String taxType = null;
        TaxRates response = accountingApi.getTaxRates(accessToken,xeroTenantId,where, order, taxType);

        assertThat(response.getTaxRates().get(0).getName(), is(equalTo("15% GST on Expenses")));
        assertThat(response.getTaxRates().get(0).getTaxType(), is(equalTo("INPUT2")));
        assertThat(response.getTaxRates().get(0).getTaxComponents().get(0).getName(), is(equalTo("GST")));
        assertThat(response.getTaxRates().get(0).getTaxComponents().get(0).getRate(), is(equalTo(15.0)));
        assertThat(response.getTaxRates().get(0).getTaxComponents().get(0).getIsCompound(), is(equalTo(false)));
        assertThat(response.getTaxRates().get(0).getTaxComponents().get(0).getIsNonRecoverable(), is(equalTo(false)));        
        assertThat(response.getTaxRates().get(0).getStatus(), is(equalTo(com.xero.models.accounting.TaxRate.StatusEnum.ACTIVE)));
        assertThat(response.getTaxRates().get(0).getReportTaxType(), is(equalTo(com.xero.models.accounting.TaxRate.ReportTaxTypeEnum.INPUT)));
        assertThat(response.getTaxRates().get(0).getCanApplyToAssets(), is(equalTo(true)));
        assertThat(response.getTaxRates().get(0).getCanApplyToEquity(), is(equalTo(true)));
        assertThat(response.getTaxRates().get(0).getCanApplyToExpenses(), is(equalTo(true)));
        assertThat(response.getTaxRates().get(0).getCanApplyToLiabilities(), is(equalTo(true)));
        assertThat(response.getTaxRates().get(0).getCanApplyToRevenue(), is(equalTo(false)));
        assertThat(response.getTaxRates().get(0).getDisplayTaxRate(), is(equalTo(15.0)));
        assertThat(response.getTaxRates().get(0).getDisplayTaxRate().toString(), is(equalTo("15.0")));
        assertThat(response.getTaxRates().get(0).getEffectiveRate(), is(equalTo(15.0)));
        assertThat(response.getTaxRates().get(0).getEffectiveRate().toString(), is(equalTo("15.0")));
        //System.out.println(response.getTaxRates().get(0).toString());
    }

    @Test
    public void updateTaxRateTest() throws IOException {
        System.out.println("@Test - updateTaxRate");
        TaxRates taxRates = new TaxRates();
        TaxRates response = accountingApi.updateTaxRate(accessToken,xeroTenantId,taxRates);

        assertThat(response.getTaxRates().get(0).getName(), is(equalTo("SDKTax29067")));
        assertThat(response.getTaxRates().get(0).getTaxType(), is(equalTo("TAX002")));
        assertThat(response.getTaxRates().get(0).getTaxComponents().get(0).getName(), is(equalTo("State Tax")));
        assertThat(response.getTaxRates().get(0).getTaxComponents().get(0).getRate(), is(equalTo(2.25)));
        assertThat(response.getTaxRates().get(0).getTaxComponents().get(0).getIsCompound(), is(equalTo(false)));
        assertThat(response.getTaxRates().get(0).getTaxComponents().get(0).getIsNonRecoverable(), is(equalTo(false)));        
        assertThat(response.getTaxRates().get(0).getStatus(), is(equalTo(com.xero.models.accounting.TaxRate.StatusEnum.DELETED)));
        assertThat(response.getTaxRates().get(0).getReportTaxType(), is(equalTo(com.xero.models.accounting.TaxRate.ReportTaxTypeEnum.INPUT)));
        assertThat(response.getTaxRates().get(0).getCanApplyToAssets(), is(equalTo(true)));
        assertThat(response.getTaxRates().get(0).getCanApplyToEquity(), is(equalTo(true)));
        assertThat(response.getTaxRates().get(0).getCanApplyToExpenses(), is(equalTo(true)));
        assertThat(response.getTaxRates().get(0).getCanApplyToLiabilities(), is(equalTo(true)));
        assertThat(response.getTaxRates().get(0).getCanApplyToRevenue(), is(equalTo(false)));
        assertThat(response.getTaxRates().get(0).getDisplayTaxRate(), is(equalTo(2.25)));
        assertThat(response.getTaxRates().get(0).getDisplayTaxRate().toString(), is(equalTo("2.25")));
        assertThat(response.getTaxRates().get(0).getEffectiveRate(), is(equalTo(2.25)));
        assertThat(response.getTaxRates().get(0).getEffectiveRate().toString(), is(equalTo("2.25")));
        //System.out.println(response.getTaxRates().get(0).toString());
    }
}
