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
import com.xero.example.CustomJsonConfig;

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

public class AccountingApiTaxRatesTest {

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
    public void createTaxRateTest() throws IOException {
        System.out.println("@Test - createTaxRate");
        TaxRates taxRates = null;
        TaxRates response = api.createTaxRate(taxRates);

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
        TaxRates response = api.getTaxRates(where, order, taxType);

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
        TaxRates taxRates = null;
        TaxRates response = api.updateTaxRate(taxRates);

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