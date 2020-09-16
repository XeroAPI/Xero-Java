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
import org.threeten.bp.temporal.ChronoUnit;
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

public class PayrollNzApiTimesheetsTest {

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
    public void getTimesheetsTest() throws IOException {
        System.out.println("@Test NZ Payroll - getTimesheetsTest");
       
        int page = 1;
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        UUID payrollCalendarId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        Timesheets response = payrollNzApi.getTimesheets(accessToken, xeroTenantId, page, employeeId, payrollCalendarId);
        
        assertThat(response.getTimesheets().get(0).getTimesheetID(),is(equalTo(UUID.fromString("f2a2aff0-9944-411e-bc58-44f22304188d"))));
        assertThat(response.getTimesheets().get(0).getPayrollCalendarID(),is(equalTo(UUID.fromString("f6931b89-d9c8-4f25-b4fa-268b5fd24197"))));
        assertThat(response.getTimesheets().get(0).getEmployeeID(),is(equalTo(UUID.fromString("272a7559-1b8f-4d3a-977b-d05578cb9546"))));
        assertThat(response.getTimesheets().get(0).getStartDate(), is(equalTo(LocalDate.of(2020, 8, 01))));
        assertThat(response.getTimesheets().get(0).getEndDate(), is(equalTo(LocalDate.of(2020, 8, 31))));
        assertThat(response.getTimesheets().get(0).getStatus(), is(equalTo(com.xero.models.payrollnz.Timesheet.StatusEnum.DRAFT)));
        assertThat(response.getTimesheets().get(0).getTotalHours(), is(equalTo(17.0)));
        assertThat(response.getTimesheets().get(0).getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 8, 28, 20, 50 ,40) )));  

        //System.out.println(response.toString());
    }

    @Test
    public void getTimesheetTest() throws IOException {
        System.out.println("@Test NZ Payroll - getTimesheetTest");
       
        int page = 1;
        UUID timesheetID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        TimesheetObject response = payrollNzApi.getTimesheet(accessToken, xeroTenantId, timesheetID);
        
        assertThat(response.getTimesheet().getTimesheetID(),is(equalTo(UUID.fromString("f2a2aff0-9944-411e-bc58-44f22304188d"))));
        assertThat(response.getTimesheet().getPayrollCalendarID(),is(equalTo(UUID.fromString("f6931b89-d9c8-4f25-b4fa-268b5fd24197"))));
        assertThat(response.getTimesheet().getEmployeeID(),is(equalTo(UUID.fromString("272a7559-1b8f-4d3a-977b-d05578cb9546"))));
        assertThat(response.getTimesheet().getStartDate(), is(equalTo(LocalDate.of(2020, 8, 01))));
        assertThat(response.getTimesheet().getEndDate(), is(equalTo(LocalDate.of(2020, 8, 31))));
        assertThat(response.getTimesheet().getStatus(), is(equalTo(com.xero.models.payrollnz.Timesheet.StatusEnum.DRAFT)));
        assertThat(response.getTimesheet().getTotalHours(), is(equalTo(17.0)));
        assertThat(response.getTimesheet().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 8, 28, 20, 50 ,40) )));  
        assertThat(response.getTimesheet().getTimesheetLines().get(0).getTimesheetLineID(),is(equalTo(UUID.fromString("3397aab1-6cac-4804-a72b-00f396b04a08"))));
        assertThat(response.getTimesheet().getTimesheetLines().get(0).getEarningsRateID(),is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getTimesheet().getTimesheetLines().get(0).getDate(),  is(equalTo(LocalDate.of(2020, 8, 01))));
        assertThat(response.getTimesheet().getTimesheetLines().get(0).getNumberOfUnits(),  is(equalTo(7.0)));

        //System.out.println(response.toString());
    }

    @Test
    public void createTimesheetTest() throws IOException {
        System.out.println("@Test NZ Payroll - createTimesheetTest");
       
        int page = 1;
        UUID timesheetID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        Timesheet timesheet = new Timesheet();
        TimesheetObject response = payrollNzApi.createTimesheet(accessToken, xeroTenantId, timesheet);
        
        assertThat(response.getTimesheet().getTimesheetID(),is(equalTo(UUID.fromString("d227445a-4188-453a-a196-48163a38188c"))));
        assertThat(response.getTimesheet().getPayrollCalendarID(),is(equalTo(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"))));
        assertThat(response.getTimesheet().getEmployeeID(),is(equalTo(UUID.fromString("68342973-c405-4b86-b5d3-d7b877c27995"))));
        assertThat(response.getTimesheet().getStartDate(), is(equalTo(LocalDate.of(2020, 04, 13))));
        assertThat(response.getTimesheet().getEndDate(), is(equalTo(LocalDate.of(2020, 04, 19))));
        assertThat(response.getTimesheet().getStatus(), is(equalTo(com.xero.models.payrollnz.Timesheet.StatusEnum.DRAFT)));
        assertThat(response.getTimesheet().getTotalHours(), is(equalTo(14.0)));
        assertThat(response.getTimesheet().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 8, 28, 21, 07 ,41).plusNanos(967621800) )));  
        assertThat(response.getTimesheet().getTimesheetLines().get(0).getTimesheetLineID(),is(equalTo(UUID.fromString("ebeda184-6f42-4c44-a19c-3c0308578153"))));
        assertThat(response.getTimesheet().getTimesheetLines().get(0).getEarningsRateID(),is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getTimesheet().getTimesheetLines().get(0).getDate(),  is(equalTo(LocalDate.of(2020, 04, 15))));
        assertThat(response.getTimesheet().getTimesheetLines().get(0).getNumberOfUnits(),  is(equalTo(6.0)));

        //System.out.println(response.toString());
    }

    @Test
    public void createTimesheetLineTest() throws IOException {
        System.out.println("@Test NZ Payroll - createTimesheetLineTest");
       
        UUID timesheetID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        TimesheetLine timesheetLine = new TimesheetLine();
        TimesheetLineObject response = payrollNzApi.createTimesheetLine(accessToken, xeroTenantId, timesheetID, timesheetLine);
        
        assertThat(response.getTimesheetLine().getTimesheetLineID(),is(equalTo(UUID.fromString("10c3c63e-6cd0-4630-861f-08a2baa657fa"))));
        assertThat(response.getTimesheetLine().getDate(), is(equalTo(LocalDate.of(2020, 8, 03))));
        assertThat(response.getTimesheetLine().getEarningsRateID(),is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getTimesheetLine().getNumberOfUnits(), is(equalTo(1.0)));

        //System.out.println(response.toString());
    }

    @Test
    public void updateTimesheetLineTest() throws IOException {
        System.out.println("@Test NZ Payroll - updateTimesheetLineTest");
       
        UUID timesheetID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        UUID timesheetLineID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        TimesheetLine timesheetLine = new TimesheetLine();
        TimesheetLineObject response = payrollNzApi.updateTimesheetLine(accessToken, xeroTenantId, timesheetID, timesheetLineID, timesheetLine);
        
        assertThat(response.getTimesheetLine().getTimesheetLineID(),is(equalTo(UUID.fromString("3397aab1-6cac-4804-a72b-00f396b04a08"))));
        assertThat(response.getTimesheetLine().getDate(), is(equalTo(LocalDate.of(2020, 8, 04))));
        assertThat(response.getTimesheetLine().getEarningsRateID(),is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getTimesheetLine().getNumberOfUnits(), is(equalTo(2.0)));

        //System.out.println(response.toString());
    }

/* Can't test approve or revert - they are POST with no body and sandbox API expects body to not be empty.
    @Test
    public void approveTimesheetTest() throws IOException {
        System.out.println("@Test NZ Payroll - approveTimesheetTest");
       
        UUID timesheetID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        //TimesheetObject response = payrollNzApi.approveTimesheet(accessToken, xeroTenantId, timesheetID);
        
        // assertThat(response.getTimesheetLine().getTimesheetLineID(),is(equalTo(UUID.fromString("56fce87e-7f0d-4c19-8f74-7f5656651c81"))));
        // assertThat(response.getTimesheetLine().getDate(), is(equalTo(LocalDate.of(2020, 04, 14))));
        // assertThat(response.getTimesheetLine().getEarningsRateID(),is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
        // assertThat(response.getTimesheetLine().getNumberOfUnits(), is(equalTo(1.0)));

        //System.out.println(response.toString());
    }

    @Test
    public void revertTimesheetTest() throws IOException {
        System.out.println("@Test NZ Payroll - revertTimesheetTest");
       
        UUID timesheetID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        TimesheetObject response = payrollNzApi.revertTimesheet(accessToken, xeroTenantId, timesheetID);
        
        // assertThat(response.getTimesheetLine().getTimesheetLineID(),is(equalTo(UUID.fromString("56fce87e-7f0d-4c19-8f74-7f5656651c81"))));
        // assertThat(response.getTimesheetLine().getDate(), is(equalTo(LocalDate.of(2020, 04, 14))));
        // assertThat(response.getTimesheetLine().getEarningsRateID(),is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
        // assertThat(response.getTimesheetLine().getNumberOfUnits(), is(equalTo(1.0)));
        //System.out.println(response.toString());
    }
*/

}
