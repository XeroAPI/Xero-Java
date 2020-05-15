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

public class PayrollAuApiPayrollCalendarTest {

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
    public void getPayrollCalendarsTest() throws IOException {
        System.out.println("@Test - getPayrollCalendarsTest");
        
        PayrollCalendars response = payrollAuApi.getPayrollCalendars(accessToken, xeroTenantId, null, null, null, null);
        
        assertThat(response.getPayrollCalendars().get(0).getName(), is(equalTo("Sid Weekly")));
        assertThat(response.getPayrollCalendars().get(0).getCalendarType(), is(equalTo(com.xero.models.payrollau.CalendarType.WEEKLY)));
        assertThat(response.getPayrollCalendars().get(0).getStartDateAsDate(), is(equalTo(LocalDate.of(2019,11,8))));
        assertThat(response.getPayrollCalendars().get(0).getPaymentDateAsDate(), is(equalTo(LocalDate.of(2019,11,15))));
        assertThat(response.getPayrollCalendars().get(0).getPayrollCalendarID() , is(equalTo(UUID.fromString("78bb86b9-e1ea-47ac-b75d-f087a81931de"))));
        assertThat(response.getPayrollCalendars().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-06T22:01:27Z"))));
        //System.out.println(response.toString());
    }

    @Test
    public void createPayrollCalendarTest() throws IOException {
        System.out.println("@Test - createPayrollCalendarTest");
        
        List<PayrollCalendar> payrollCalendars = new ArrayList<>();   
        PayrollCalendars response = payrollAuApi.createPayrollCalendar(accessToken, xeroTenantId, payrollCalendars);
        
        assertThat(response.getPayrollCalendars().get(0).getName(), is(equalTo("MyCal37127")));
        assertThat(response.getPayrollCalendars().get(0).getCalendarType(), is(equalTo(com.xero.models.payrollau.CalendarType.WEEKLY)));
        assertThat(response.getPayrollCalendars().get(0).getStartDateAsDate(), is(equalTo(LocalDate.of(2019,11,6))));
        assertThat(response.getPayrollCalendars().get(0).getPaymentDateAsDate(), is(equalTo(LocalDate.of(2019,11,12))));
        assertThat(response.getPayrollCalendars().get(0).getPayrollCalendarID() , is(equalTo(UUID.fromString("57accbfe-f729-4be3-b3cb-8c3445c61d3a"))));      
        assertThat(response.getPayrollCalendars().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2020-03-13T18:51:58.633Z"))));
        //System.out.println(response.toString());
    }

    @Test
    public void getPayrollCalendarTest() throws IOException {
        System.out.println("@Test - getPayrollCalendarTest");
        
        UUID payrollCalendarId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        PayrollCalendars response = payrollAuApi.getPayrollCalendar(accessToken, xeroTenantId, payrollCalendarId);
        
        assertThat(response.getPayrollCalendars().get(0).getName(), is(equalTo("Sid Weekly")));
        assertThat(response.getPayrollCalendars().get(0).getCalendarType(), is(equalTo(com.xero.models.payrollau.CalendarType.WEEKLY)));
        assertThat(response.getPayrollCalendars().get(0).getStartDateAsDate(), is(equalTo(LocalDate.of(2019,11,8))));
        assertThat(response.getPayrollCalendars().get(0).getPaymentDateAsDate(), is(equalTo(LocalDate.of(2019,11,15))));
        assertThat(response.getPayrollCalendars().get(0).getPayrollCalendarID() , is(equalTo(UUID.fromString("78bb86b9-e1ea-47ac-b75d-f087a81931de")))); 
        assertThat(response.getPayrollCalendars().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-06T22:01:27Z"))));
        //System.out.println(response.toString());
    }
}
