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

public class PayrollUkApiEmployeeStatutoryLeavesSummaryTest {

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
		defaultClient = new ApiClient("https://xero-payroll-uk.getsandbox.com:443/payroll.xro/2.0",null,null,null,null);
        payrollUkApi = PayrollUkApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollUkApi = null;
        defaultClient = null;
	}

    @Test
    public void getEmployeeStatutoryLeavesSummaryTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeeStatutoryLeavesSummaryTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeStatutoryLeavesSummaries response = payrollUkApi.getStatutoryLeaveSummary(accessToken, xeroTenantId, employeeId, false);
        
        assertThat(response.getStatutoryLeaves().get(0).getEmployeeID(),  is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
        assertThat(response.getStatutoryLeaves().get(0).getStatutoryLeaveID(),  is(equalTo(UUID.fromString("17f4c3c7-d65c-4572-9118-03798f26f813"))));
        assertThat(response.getStatutoryLeaves().get(0).getType(), is(equalTo(com.xero.models.payrolluk.EmployeeStatutoryLeaveSummary.TypeEnum.SICK)));
        assertThat(response.getStatutoryLeaves().get(0).getStartDate(), is(equalTo(LocalDate.of(2020, 03, 28))));
        assertThat(response.getStatutoryLeaves().get(0).getEndDate(), is(equalTo(LocalDate.of(2020, 04, 01))));
        assertThat(response.getStatutoryLeaves().get(0).getIsEntitled(), is(equalTo(false)));
        assertThat(response.getStatutoryLeaves().get(0).getStatus(), is(equalTo(com.xero.models.payrolluk.EmployeeStatutoryLeaveSummary.StatusEnum.PENDING)));

        //System.out.println(response.toString());
    }
}
