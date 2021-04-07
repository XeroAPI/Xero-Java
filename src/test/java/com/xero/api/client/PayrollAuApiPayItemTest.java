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
import com.xero.models.payrollau.*;

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

public class PayrollAuApiPayItemTest {

	ApiClient defaultClient; 
    PayrollAuApi payrollAuApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        // Init projectApi client
        // NEW Sandbox for API Mocking
		defaultClient = new ApiClient("https://xero-payroll-au.getsandbox.com:443/payroll.xro/1.0",null,null,null,null);
        payrollAuApi = PayrollAuApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollAuApi = null;
        defaultClient = null;
	}

	@Test
    public void getPayItemsTest() throws IOException {
        System.out.println("@Test - getPayItemsTest");
        
        PayItems response = payrollAuApi.getPayItems(accessToken, xeroTenantId, null, null, null, null);

        assertThat(response.getPayItems().getEarningsRates().get(0).getName(), is(equalTo("Ordinary Hours")));
        assertThat(response.getPayItems().getEarningsRates().get(0).getAccountCode(), is(equalTo("477")));
        assertThat(response.getPayItems().getEarningsRates().get(0).getTypeOfUnits()  , is(equalTo("Hours")));
        assertThat(response.getPayItems().getEarningsRates().get(0).getIsExemptFromTax() , is(equalTo(true)));
        assertThat(response.getPayItems().getEarningsRates().get(0).getIsExemptFromSuper() , is(equalTo(true)));
        assertThat(response.getPayItems().getEarningsRates().get(0).getIsReportableAsW1() , is(equalTo(true)));        
        assertThat(response.getPayItems().getEarningsRates().get(0).getEarningsType(), is(equalTo(com.xero.models.payrollau.EarningsType.ORDINARYTIMEEARNINGS)));
        assertThat(response.getPayItems().getEarningsRates().get(0).getEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
        assertThat(response.getPayItems().getEarningsRates().get(0).getRatePerUnit() , is(equalTo("3")));        
        assertThat(response.getPayItems().getEarningsRates().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T04:53:41Z"))));  
        assertThat(response.getPayItems().getEarningsRates().get(0).getRateType(), is(equalTo(com.xero.models.payrollau.RateType.RATEPERUNIT)));
        assertThat(response.getPayItems().getEarningsRates().get(0).getCurrentRecord() , is(equalTo(true))); 
        assertThat(response.getPayItems().getDeductionTypes().get(0).getDeductionTypeID(), is(equalTo(UUID.fromString("727af5e8-b347-4ae7-85fc-9b82266d0aec"))));
        assertThat(response.getPayItems().getDeductionTypes().get(0).getName(), is(equalTo("Union Fees/Subscriptions")));        
        assertThat(response.getPayItems().getDeductionTypes().get(0).getAccountCode(), is(equalTo("850")));        
        assertThat(response.getPayItems().getDeductionTypes().get(0).getReducesTax(), is(equalTo(false)));        
        assertThat(response.getPayItems().getDeductionTypes().get(0).getReducesSuper(), is(equalTo(false)));        
        assertThat(response.getPayItems().getDeductionTypes().get(0).getIsExemptFromW1(), is(equalTo(false)));        
        assertThat(response.getPayItems().getDeductionTypes().get(0).getDeductionCategory(), is(equalTo(com.xero.models.payrollau.DeductionType.DeductionCategoryEnum.UNIONFEES)));  
        assertThat(response.getPayItems().getDeductionTypes().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-01-14T21:12:10Z"))));       
        assertThat(response.getPayItems().getDeductionTypes().get(0).getCurrentRecord(), is(equalTo(true)));      
        assertThat(response.getPayItems().getReimbursementTypes().get(0).getReimbursementTypeID(), is(equalTo(UUID.fromString("98ba33b2-db5b-4204-bcac-5ddd98d63524"))));
        assertThat(response.getPayItems().getReimbursementTypes().get(0).getName(), is(equalTo("Travel Costs")));        
        assertThat(response.getPayItems().getReimbursementTypes().get(0).getAccountCode(), is(equalTo("850")));        
        assertThat(response.getPayItems().getReimbursementTypes().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-01-14T21:12:10Z"))));
        assertThat(response.getPayItems().getReimbursementTypes().get(0).getCurrentRecord(), is(equalTo(true)));      
        assertThat(response.getPayItems().getLeaveTypes().get(0).getLeaveTypeID(), is(equalTo(UUID.fromString("fbcc9dab-6238-43d9-a3f4-d768423fdcfa"))));
        assertThat(response.getPayItems().getLeaveTypes().get(0).getName(), is(equalTo("Annual Leave")));        
        assertThat(response.getPayItems().getLeaveTypes().get(0).getTypeOfUnits(), is(equalTo("Hours")));
        assertThat(response.getPayItems().getLeaveTypes().get(0).getNormalEntitlement(), is(equalTo(152.00)));
        assertThat(response.getPayItems().getLeaveTypes().get(0).getLeaveLoadingRate(),  is(equalTo(1.0)));
        assertThat(response.getPayItems().getLeaveTypes().get(0).getIsPaidLeave(), is(equalTo(true)));
        assertThat(response.getPayItems().getLeaveTypes().get(0).getShowOnPayslip(), is(equalTo(true)));
        assertThat(response.getPayItems().getLeaveTypes().get(0).getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getPayItems().getLeaveTypes().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T04:54:13Z"))));
        // System.out.println(response.toString());
    }

    @Test
    public void createPayItemTest() throws IOException {
        System.out.println("@Test - createPayItemTest");
        
        PayItem payItems = new PayItem();        
        //PayItems response = payrollAuApi.createPayItem(accessToken, xeroTenantId,payItems);
        
        // CAN NOT TEST response from createPayItem 
        // because API returns EMPTY JSON object
        //System.out.println(response.toString());
    }
}
