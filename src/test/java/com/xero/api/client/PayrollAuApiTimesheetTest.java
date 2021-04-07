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

public class PayrollAuApiTimesheetTest {

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
    public void getTimesheetsTest() throws IOException {
        System.out.println("@Test - getTimesheetsTest");
     
        List<Double> numOfUnitList = new ArrayList<>();
        numOfUnitList.add(4.0);
        numOfUnitList.add(4.0);
        numOfUnitList.add(4.0);
        numOfUnitList.add(4.0);
        numOfUnitList.add(4.0);
        numOfUnitList.add(4.0);
        numOfUnitList.add(0.0);
        Timesheets response = payrollAuApi.getTimesheets(accessToken, xeroTenantId, null, null, null, null);
        
        assertThat(response.getTimesheets().get(0).getEmployeeID(), is(equalTo(UUID.fromString("b34e89ff-770d-4099-b7e5-f968767118bc"))));       
        assertThat(response.getTimesheets().get(0).getStartDateAsDate() , is(equalTo(LocalDate.of(2019,01,18))));
        assertThat(response.getTimesheets().get(0).getEndDateAsDate() , is(equalTo(LocalDate.of(2019,01,24))));  
        assertThat(response.getTimesheets().get(0).getStatus(), is(equalTo(com.xero.models.payrollau.TimesheetStatus.APPROVED)));
        assertThat(response.getTimesheets().get(0).getHours(), is(equalTo(24.0)));        
        assertThat(response.getTimesheets().get(0).getTimesheetID(), is(equalTo(UUID.fromString("863bbd31-0447-4419-80d5-d733d5e723ba"))));       
        assertThat(response.getTimesheets().get(0).getTimesheetLines().get(0).getEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));       
        assertThat(response.getTimesheets().get(0).getTimesheetLines().get(0).getNumberOfUnits(), is(equalTo(numOfUnitList)));       
        assertThat(response.getTimesheets().get(0).getTimesheetLines().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-05T01:03:47Z"))));   
        assertThat(response.getTimesheets().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-05T01:03:47Z"))));
        //System.out.println(response.toString());
    }

    @Test
    public void getTimesheetTest() throws IOException {
        System.out.println("@Test - getTimesheetTest");
        UUID timesheetId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
       
        List<Double> numOfUnitList = new ArrayList<>();
        numOfUnitList.add(3.0);
        numOfUnitList.add(3.0);
        numOfUnitList.add(3.0);
        numOfUnitList.add(3.0);
        numOfUnitList.add(0.0);
        numOfUnitList.add(3.0);
        numOfUnitList.add(0.0);
        
        TimesheetObject response = payrollAuApi.getTimesheet(accessToken, xeroTenantId, timesheetId);
        
        assertThat(response.getTimesheet().getEmployeeID(), is(equalTo(UUID.fromString("b34e89ff-770d-4099-b7e5-f968767118bc"))));       
        assertThat(response.getTimesheet().getStartDateAsDate() , is(equalTo(LocalDate.of(2019,01,11))));
        assertThat(response.getTimesheet().getEndDateAsDate() , is(equalTo(LocalDate.of(2019,01,17))));  
        assertThat(response.getTimesheet().getStatus(), is(equalTo(com.xero.models.payrollau.TimesheetStatus.APPROVED)));
        assertThat(response.getTimesheet().getHours(), is(equalTo(15.0)));
        assertThat(response.getTimesheet().getTimesheetID(), is(equalTo(UUID.fromString("df954ca3-3a70-47e9-9a3e-80711e7c5f90"))));       
        assertThat(response.getTimesheet().getTimesheetLines().get(0).getEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));       
        assertThat(response.getTimesheet().getTimesheetLines().get(0).getNumberOfUnits(), is(equalTo(numOfUnitList)));       
        assertThat(response.getTimesheet().getTimesheetLines().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-05T01:03:17Z"))));   
        assertThat(response.getTimesheet().getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-05T01:03:17Z"))));   

        //System.out.println(response.toString());
    }

    @Test
    public void createTimesheetTest() throws IOException {
        System.out.println("@Test - createTimesheetTest");
        UUID timesheetId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        List<Timesheet> timesheets = new ArrayList<>();
        List<Double> numOfUnitList = new ArrayList<>();
        numOfUnitList.add(2.0);
        numOfUnitList.add(10.0);
        numOfUnitList.add(0.0);
        numOfUnitList.add(0.0);
        numOfUnitList.add(5.0);
        numOfUnitList.add(0.0);
        numOfUnitList.add(5.0);
        
        Timesheets response = payrollAuApi.createTimesheet(accessToken, xeroTenantId, timesheets);
        
        assertThat(response.getTimesheets().get(0).getEmployeeID(), is(equalTo(UUID.fromString("b34e89ff-770d-4099-b7e5-f968767118bc"))));       
        assertThat(response.getTimesheets().get(0).getStartDateAsDate() , is(equalTo(LocalDate.of(2019,11,8))));
        assertThat(response.getTimesheets().get(0).getEndDateAsDate() , is(equalTo(LocalDate.of(2019,11,14))));  
        assertThat(response.getTimesheets().get(0).getStatus(), is(equalTo(com.xero.models.payrollau.TimesheetStatus.DRAFT)));
        assertThat(response.getTimesheets().get(0).getHours(), is(equalTo(22.0)));        
        assertThat(response.getTimesheets().get(0).getTimesheetID(), is(equalTo(UUID.fromString("a7eb0a79-8511-4ee7-b473-3a25f28abcb9"))));       
        assertThat(response.getTimesheets().get(0).getTimesheetLines().get(0).getEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));       
        assertThat(response.getTimesheets().get(0).getTimesheetLines().get(0).getTrackingItemID(), is(equalTo(UUID.fromString("af5e9ce2-2349-4136-be99-3561b189f473"))));       
        assertThat(response.getTimesheets().get(0).getTimesheetLines().get(0).getNumberOfUnits(), is(equalTo(numOfUnitList)));
        assertThat(response.getTimesheets().get(0).getTimesheetLines().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-11T23:49:45.127Z"))));
        assertThat(response.getTimesheets().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-11T23:49:45.127Z"))));
        //System.out.println(response.toString());
    }

    @Test
    public void updateTimesheetTest() throws IOException {
        System.out.println("@Test - updateTimesheetTest");
        UUID timesheetId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        List<Timesheet> timesheets = new ArrayList<>();
        List<Double> numOfUnitList = new ArrayList<>();
        numOfUnitList.add(2.0);
        numOfUnitList.add(10.0);
        numOfUnitList.add(0.0);
        numOfUnitList.add(0.0);
        numOfUnitList.add(5.0);
        numOfUnitList.add(0.0);
        numOfUnitList.add(5.0);
        
        Timesheets response = payrollAuApi.updateTimesheet(accessToken, xeroTenantId, timesheetId, timesheets);
        
        assertThat(response.getTimesheets().get(0).getEmployeeID(), is(equalTo(UUID.fromString("b34e89ff-770d-4099-b7e5-f968767118bc"))));       
        assertThat(response.getTimesheets().get(0).getStartDateAsDate() , is(equalTo(LocalDate.of(2019,11,8))));
        assertThat(response.getTimesheets().get(0).getEndDateAsDate() , is(equalTo(LocalDate.of(2019,11,14))));  
        assertThat(response.getTimesheets().get(0).getStatus(), is(equalTo(com.xero.models.payrollau.TimesheetStatus.APPROVED)));
        assertThat(response.getTimesheets().get(0).getHours(), is(equalTo(22.0)));        
        assertThat(response.getTimesheets().get(0).getTimesheetID(), is(equalTo(UUID.fromString("a7eb0a79-8511-4ee7-b473-3a25f28abcb9"))));       
        assertThat(response.getTimesheets().get(0).getTimesheetLines().get(0).getEarningsRateID(), is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));       
        assertThat(response.getTimesheets().get(0).getTimesheetLines().get(0).getTrackingItemID(), is(equalTo(UUID.fromString("af5e9ce2-2349-4136-be99-3561b189f473"))));       
        assertThat(response.getTimesheets().get(0).getTimesheetLines().get(0).getNumberOfUnits(), is(equalTo(numOfUnitList)));       
        assertThat(response.getTimesheets().get(0).getTimesheetLines().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-11T23:49:45.227Z"))));
        assertThat(response.getTimesheets().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-11T23:49:45.227Z"))));
        
        //System.out.println(response.toString());
    }
}
