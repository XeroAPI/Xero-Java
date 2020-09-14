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

public class PayrollNzApiEmployeeOpeningBalancesTest {

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
    public void getEmployeeOpeningBalancesTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEmployeeOpeningBalancesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeOpeningBalancesObject response = payrollNzApi.getEmployeeOpeningBalances(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getOpeningBalances().get(0).getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 10, 01))));
        assertThat(response.getOpeningBalances().get(0).getDaysPaid(), is(equalTo(3)));
        assertThat(response.getOpeningBalances().get(0).getUnpaidWeeks(), is(equalTo(2)));
        assertThat(response.getOpeningBalances().get(0).getGrossEarnings(), is(equalTo(40.0)));
        
        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeOpeningBalancesTest() throws IOException {
        System.out.println("@Test NZ Payroll - createEmployeeOpeningBalancesTest");

        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        List<EmployeeOpeningBalance> employeeOpeningBalances = new ArrayList<>();
      
        EmployeeOpeningBalancesObject response = payrollNzApi.createEmployeeOpeningBalances(accessToken, xeroTenantId, employeeId, employeeOpeningBalances);
      
        assertThat(response.getOpeningBalances().get(0).getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 10, 01))));
        assertThat(response.getOpeningBalances().get(0).getDaysPaid(), is(equalTo(3)));
        assertThat(response.getOpeningBalances().get(0).getUnpaidWeeks(), is(equalTo(2)));
        assertThat(response.getOpeningBalances().get(0).getGrossEarnings(), is(equalTo(40.0)));

        //System.out.println(response.toString());
    }
}
