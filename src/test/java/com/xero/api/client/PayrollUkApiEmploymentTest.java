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

public class PayrollUkApiEmploymentTest {

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
    public void createEmploymentTest() throws IOException {
        System.out.println("@Test UK Payroll - createEmploymentTest");
        
        Employment employment = new Employment();
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmploymentObject response = payrollUkApi.createEmployment(accessToken, xeroTenantId, employeeId, employment, null);
        
        assertThat(response.getEmployment().getPayrollCalendarID(), is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
        assertThat(response.getEmployment().getStartDate(), is(equalTo(LocalDate.of(2020, 04, 01))));
        assertThat(response.getEmployment().getEmployeeNumber(), is(equalTo("123ABC")));
        assertThat(response.getEmployment().getNiCategory(), is(equalTo(com.xero.models.payrolluk.NICategoryLetter.A)));
        //System.out.println(response.toString());
    }
}
