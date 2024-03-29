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
import com.xero.models.payrolluk.*;
import com.xero.models.payrolluk.Benefit.CalculationTypeEnum;
import com.xero.models.payrolluk.Benefit.CategoryEnum;

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

public class PayrollUkApiEarningRatesTest {

	ApiClient defaultClient; 
    PayrollUkApi payrollUkApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        // Init projectApi client
        // NEW Sandbox for API Mocking
		defaultClient = new ApiClient("https://ba3fd247-8fc6-4d7c-bcd1-bdbea4ea1803.mock.pstmn.io/payroll.xro/2.0",null,null,null,null);
        payrollUkApi = PayrollUkApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollUkApi = null;
        defaultClient = null;
	}


    @Test
    public void getEarningsRatesTest() throws IOException {
        System.out.println("@Test UK Payroll - getEarningsRatesTest");
       
        int page = 1;
        EarningsRates response = payrollUkApi.getEarningsRates(accessToken, xeroTenantId, page);
        
        assertThat(response.getEarningsRates().get(0).getEarningsRateID(),is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
        assertThat(response.getEarningsRates().get(0).getExpenseAccountID(), is(equalTo(UUID.fromString("81da553d-c6c6-411e-95df-cc4ac8f7e1c2"))));
        assertThat(response.getEarningsRates().get(0).getName(), is(equalTo("Regular Hours")));
        assertThat(response.getEarningsRates().get(0).getEarningsType(), is(equalTo(com.xero.models.payrolluk.EarningsRate.EarningsTypeEnum.REGULAREARNINGS)));
        assertThat(response.getEarningsRates().get(0).getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getEarningsRates().get(0).getRateType(), is(equalTo(com.xero.models.payrolluk.EarningsRate.RateTypeEnum.RATEPERUNIT)));
        assertThat(response.getEarningsRates().get(0).getTypeOfUnits(), is(equalTo("hours")));
        assertThat(response.getEarningsRates().get(1).getEarningsRateID(),is(equalTo(UUID.fromString("973365f3-66b2-4c33-8ae6-14b75f78f68b"))));
        assertThat(response.getEarningsRates().get(1).getExpenseAccountID(), is(equalTo(UUID.fromString("81da553d-c6c6-411e-95df-cc4ac8f7e1c2"))));
        assertThat(response.getEarningsRates().get(1).getName(), is(equalTo("Overtime Hours")));
        assertThat(response.getEarningsRates().get(1).getEarningsType(), is(equalTo(com.xero.models.payrolluk.EarningsRate.EarningsTypeEnum.OVERTIMEEARNINGS)));
        assertThat(response.getEarningsRates().get(1).getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getEarningsRates().get(1).getRateType(), is(equalTo(com.xero.models.payrolluk.EarningsRate.RateTypeEnum.MULTIPLEOFORDINARYEARNINGSRATE)));
        assertThat(response.getEarningsRates().get(1).getMultipleOfOrdinaryEarningsRate(), is(equalTo(1.5)));

        //System.out.println(response.toString());
    }

    @Test
    public void getEarningsRateTest() throws IOException {
        System.out.println("@Test UK Payroll - getEarningsRateTest");
       
        UUID earningRateId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EarningsRateObject response = payrollUkApi.getEarningsRate(accessToken, xeroTenantId, earningRateId);
        
        assertThat(response.getEarningsRate().getEarningsRateID(),is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
        assertThat(response.getEarningsRate().getExpenseAccountID(), is(equalTo(UUID.fromString("81da553d-c6c6-411e-95df-cc4ac8f7e1c2"))));
        assertThat(response.getEarningsRate().getName(), is(equalTo("Regular Hours")));
        assertThat(response.getEarningsRate().getEarningsType(), is(equalTo(com.xero.models.payrolluk.EarningsRate.EarningsTypeEnum.REGULAREARNINGS)));
        assertThat(response.getEarningsRate().getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getEarningsRate().getRateType(), is(equalTo(com.xero.models.payrolluk.EarningsRate.RateTypeEnum.RATEPERUNIT)));
        assertThat(response.getEarningsRate().getTypeOfUnits(), is(equalTo("hours")));

        //System.out.println(response.toString());
    }

    @Test
    public void createEarningsRateTest() throws IOException {
        System.out.println("@Test UK Payroll - createEarningsRateTest");
       
        EarningsRate earningsRate = new EarningsRate();
        EarningsRateObject response = payrollUkApi.createEarningsRate(accessToken, xeroTenantId, earningsRate, null);
        
        assertThat(response.getEarningsRate().getEarningsRateID(),is(equalTo(UUID.fromString("fcf811a8-3843-4e87-8431-c62e83158aef"))));
        assertThat(response.getEarningsRate().getExpenseAccountID(), is(equalTo(UUID.fromString("4b03500d-32fd-4616-8d70-e1e56e0519c6"))));
        assertThat(response.getEarningsRate().getName(), is(equalTo("My Earnings Rate")));
        assertThat(response.getEarningsRate().getEarningsType(), is(equalTo(com.xero.models.payrolluk.EarningsRate.EarningsTypeEnum.REGULAREARNINGS)));
        assertThat(response.getEarningsRate().getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getEarningsRate().getRateType(), is(equalTo(com.xero.models.payrolluk.EarningsRate.RateTypeEnum.RATEPERUNIT)));
        assertThat(response.getEarningsRate().getTypeOfUnits(), is(equalTo("hours")));

        //System.out.println(response.toString());
    }
}
