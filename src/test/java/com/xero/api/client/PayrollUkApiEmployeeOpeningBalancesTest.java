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

public class PayrollUkApiEmployeeOpeningBalancesTest {

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
		defaultClient = new ApiClient("http://127.0.0.1:4017",null,null,null,null);
        payrollUkApi = PayrollUkApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollUkApi = null;
        defaultClient = null;
	}

    @Test
    public void getEmployeeOpeningBalancesTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeeOpeningBalancesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeOpeningBalancesObject response = payrollUkApi.getEmployeeOpeningBalances(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getOpeningBalances().getStatutoryAdoptionPay() , is(equalTo(10.0)));
        assertThat(response.getOpeningBalances().getStatutoryMaternityPay() , is(equalTo(10.0)));
        assertThat(response.getOpeningBalances().getStatutoryPaternityPay() , is(equalTo(10.0)));
        assertThat(response.getOpeningBalances().getStatutorySharedParentalPay() , is(equalTo(10.0)));
        assertThat(response.getOpeningBalances().getStatutorySickPay() , is(equalTo(10.0)));
        assertThat(response.getOpeningBalances().getPriorEmployeeNumber() , is(equalTo(10.0)));
        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeOpeningBalancesTest() throws IOException {
        System.out.println("@Test UK Payroll - createEmployeeOpeningBalancesTest - No Test Case - method works, but response NULL");
    }

    @Test
    public void updateEmployeeOpeningBalancesTest() throws IOException {
        System.out.println("@Test UK Payroll - updateEmployeeOpeningBalancesTest No Test Case - method works, but response NULL");
    }
}
