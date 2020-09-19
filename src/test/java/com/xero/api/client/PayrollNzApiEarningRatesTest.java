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
import com.xero.models.payrollnz.*;
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

public class PayrollNzApiEarningRatesTest {

	ApiClient defaultClient; 
    PayrollNzApi payrollNzApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        // Init projectApi client
        // NEW Sandbox for API Mocking
		defaultClient = new ApiClient("https://xero-payroll-nz.getsandbox.com:443/payroll.xro/2.0",null,null,null,null);
        payrollNzApi = PayrollNzApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollNzApi = null;
        defaultClient = null;
	}

    @Test
    public void getEarningsRatesTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEarningsRatesTest");
       
        int page = 1;
        EarningsRates response = payrollNzApi.getEarningsRates(accessToken, xeroTenantId, page);
        
        assertThat(response.getEarningsRates().get(0).getEarningsRateID(),is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getEarningsRates().get(0).getExpenseAccountID(), is(equalTo(UUID.fromString("1c91e520-a12b-45cc-8194-99950858e5bf"))));
        assertThat(response.getEarningsRates().get(0).getName(), is(equalTo("Ordinary Time")));
        assertThat(response.getEarningsRates().get(0).getEarningsType(), is(equalTo(com.xero.models.payrollnz.EarningsRate.EarningsTypeEnum.REGULAREARNINGS)));
        assertThat(response.getEarningsRates().get(0).getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getEarningsRates().get(0).getRateType(), is(equalTo(com.xero.models.payrollnz.EarningsRate.RateTypeEnum.RATEPERUNIT)));
        assertThat(response.getEarningsRates().get(0).getTypeOfUnits(), is(equalTo("hours")));
        assertThat(response.getEarningsRates().get(2).getEarningsRateID(),is(equalTo(UUID.fromString("6e5a4cf2-aa38-4558-a46e-f6998dad239b"))));
        assertThat(response.getEarningsRates().get(2).getExpenseAccountID(), is(equalTo(UUID.fromString("1c91e520-a12b-45cc-8194-99950858e5bf"))));
        assertThat(response.getEarningsRates().get(2).getName(), is(equalTo("Directors Fees")));
        assertThat(response.getEarningsRates().get(2).getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getEarningsRates().get(2).getRateType(), is(equalTo(com.xero.models.payrollnz.EarningsRate.RateTypeEnum.FIXEDAMOUNT)));
        assertThat(response.getEarningsRates().get(2).getFixedAmount(), is(equalTo(1000.0)));

        //System.out.println(response.toString());
    }

    @Test
    public void getEarningsRateTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEarningsRateTest");
       
        UUID earningRateId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EarningsRateObject response = payrollNzApi.getEarningsRate(accessToken, xeroTenantId, earningRateId);
        
        assertThat(response.getEarningsRate().getEarningsRateID(),is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getEarningsRate().getExpenseAccountID(), is(equalTo(UUID.fromString("1c91e520-a12b-45cc-8194-99950858e5bf"))));
        assertThat(response.getEarningsRate().getName(), is(equalTo("Ordinary Time")));
        assertThat(response.getEarningsRate().getEarningsType(), is(equalTo(com.xero.models.payrollnz.EarningsRate.EarningsTypeEnum.REGULAREARNINGS)));
        assertThat(response.getEarningsRate().getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getEarningsRate().getRateType(), is(equalTo(com.xero.models.payrollnz.EarningsRate.RateTypeEnum.RATEPERUNIT)));
        assertThat(response.getEarningsRate().getTypeOfUnits(), is(equalTo("hours")));

        //System.out.println(response.toString());
    }

    @Test
    public void createEarningsRateTest() throws IOException {
        System.out.println("@Test NZ Payroll - createEarningsRateTest");
       
        EarningsRate earningsRate = new EarningsRate();
        EarningsRateObject response = payrollNzApi.createEarningsRate(accessToken, xeroTenantId, earningsRate);
        
        assertThat(response.getEarningsRate().getEarningsRateID(),is(equalTo(UUID.fromString("4369b0ef-a64d-42e1-bb6d-f2fc984de133"))));
        assertThat(response.getEarningsRate().getExpenseAccountID(), is(equalTo(UUID.fromString("e4eb36f6-97e3-4427-a394-dd4e1b355c2e"))));
        assertThat(response.getEarningsRate().getName(), is(equalTo("My Earnings Rate")));
        assertThat(response.getEarningsRate().getEarningsType(), is(equalTo(com.xero.models.payrollnz.EarningsRate.EarningsTypeEnum.REGULAREARNINGS)));
        assertThat(response.getEarningsRate().getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getEarningsRate().getRateType(), is(equalTo(com.xero.models.payrollnz.EarningsRate.RateTypeEnum.RATEPERUNIT)));
        assertThat(response.getEarningsRate().getTypeOfUnits(), is(equalTo("hours")));

        //System.out.println(response.toString());
    }
}
