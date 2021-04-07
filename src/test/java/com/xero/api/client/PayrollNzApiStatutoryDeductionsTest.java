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

public class PayrollNzApiStatutoryDeductionsTest {

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
    public void getStatutoryDeductionsTest() throws IOException {
        System.out.println("@Test NZ Payroll - getStatutoryDeductionsTest");
       
        int page = 1;
        StatutoryDeductions response = payrollNzApi.getStatutoryDeductions(accessToken, xeroTenantId, page);
        
        assertThat(response.getStatutoryDeductions().get(0).getId(), is(equalTo(UUID.fromString("e3731829-9801-4809-a1a1-bbe40cd18b7e"))));
        assertThat(response.getStatutoryDeductions().get(0).getLiabilityAccountId(), is(equalTo(UUID.fromString("fa5cdc43-643b-4ad8-b4ac-3ffe0d0f4488"))));
        assertThat(response.getStatutoryDeductions().get(0).getName(), is(equalTo("Child Support")));
        assertThat(response.getStatutoryDeductions().get(0).getStatutoryDeductionCategory(), is(equalTo(com.xero.models.payrollnz.StatutoryDeductionCategory.CHILDSUPPORT)));
        assertThat(response.getStatutoryDeductions().get(0).getCurrentRecord(), is(equalTo(true)));

        //System.out.println(response.toString());
    }

    @Test
    public void getStatutoryDeductionTest() throws IOException {
        System.out.println("@Test NZ Payroll - getStatutoryDeductionTest");
       
        UUID deductionId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        StatutoryDeductionObject response = payrollNzApi.getStatutoryDeduction(accessToken, xeroTenantId, deductionId);
        
        assertThat(response.getStatutoryDeduction().getId(), is(equalTo(UUID.fromString("e3731829-9801-4809-a1a1-bbe40cd18b7e"))));
        assertThat(response.getStatutoryDeduction().getLiabilityAccountId(), is(equalTo(UUID.fromString("fa5cdc43-643b-4ad8-b4ac-3ffe0d0f4488"))));
        assertThat(response.getStatutoryDeduction().getName(), is(equalTo("Child Support")));
        assertThat(response.getStatutoryDeduction().getStatutoryDeductionCategory(), is(equalTo(com.xero.models.payrollnz.StatutoryDeductionCategory.CHILDSUPPORT)));
        assertThat(response.getStatutoryDeduction().getCurrentRecord(), is(equalTo(true)));

        //System.out.println(response.toString());
    }
}
