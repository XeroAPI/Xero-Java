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

public class PayrollNzApiEmployeeLeaveSetupTest {

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
		defaultClient = new ApiClient("https://5d4d8dd7-b3b2-4151-87c6-31841929f349.mock.pstmn.io/payroll.xro/2.0",null,null,null,null);
        payrollNzApi = PayrollNzApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollNzApi = null;
        defaultClient = null;
	}

    @Test
    public void createEmployeeLeaveSetupTest() throws IOException {
        System.out.println("@Test NZ Payroll - createEmployeeLeaveSetupTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeLeaveSetup employeeLeaveSetup = new EmployeeLeaveSetup();

        EmployeeLeaveSetupObject response = payrollNzApi.createEmployeeLeaveSetup(accessToken, xeroTenantId, employeeId, employeeLeaveSetup, null);
        
        assertThat(response.getLeaveSetup().getHolidayPayOpeningBalance(), is(equalTo(10.0)));
        assertThat(response.getLeaveSetup().getAnnualLeaveOpeningBalance(), is(equalTo(100.0)));
        assertThat(response.getLeaveSetup().getSickLeaveHoursToAccrueAnnually(), is(equalTo(20.0)));
        assertThat(response.getLeaveSetup().getSickLeaveOpeningBalance(), is(equalTo(10.0)));

        //System.out.println(response.toString());
    }
}
