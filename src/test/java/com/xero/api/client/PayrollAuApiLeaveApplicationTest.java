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

public class PayrollAuApiLeaveApplicationTest {

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
		defaultClient = new ApiClient("https://5f9f95f1-25c8-40dd-8b10-8192c658dd79.mock.pstmn.io/payroll.xro/1.0",null,null,null,null);
        payrollAuApi = PayrollAuApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollAuApi = null;
        defaultClient = null;
	}

	@Test
    public void getLeaveApplicationsTest() throws IOException {
        System.out.println("@Test - getLeaveApplicationsTest");
        
        LeaveApplications response = payrollAuApi.getLeaveApplications(accessToken, xeroTenantId, null, null, null, null);
        
        assertThat(response.getLeaveApplications().get(0).getTitle(), is(equalTo("vacation")));
        assertThat(response.getLeaveApplications().get(0).getLeaveApplicationID() , is(equalTo(UUID.fromString("1d4cd583-0107-4386-936b-672eb3d1f624"))));
        assertThat(response.getLeaveApplications().get(0).getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getLeaveApplications().get(0).getLeaveTypeID() , is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
        assertThat(response.getLeaveApplications().get(0).getStartDateAsDate(), is(equalTo(LocalDate.of(2019,11,12))));
        assertThat(response.getLeaveApplications().get(0).getEndDateAsDate(), is(equalTo(LocalDate.of(2019,11,12))));
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getPayPeriodStartDateAsDate() , is(equalTo(LocalDate.of(2019,11,8))));
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getPayPeriodEndDateAsDate() , is(equalTo(LocalDate.of(2019,11,14))));        
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getNumberOfUnits(), is(equalTo(0.0)));
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getLeavePeriodStatus(), is(equalTo(com.xero.models.payrollau.LeavePeriodStatus.SCHEDULED)));
        assertThat(response.getLeaveApplications().get(0).getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getLeaveApplications().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T05:30:08Z"))));  
        //System.out.println(response.toString());
    }

    @Test
    public void getLeaveApplicationTest() throws IOException {
        System.out.println("@Test - getLeaveApplicationTest");
        
        UUID leaveApplicationId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        LeaveApplications response = payrollAuApi.getLeaveApplication(accessToken, xeroTenantId, leaveApplicationId);
        
        assertThat(response.getLeaveApplications().get(0).getTitle(), is(equalTo("vacation")));
        assertThat(response.getLeaveApplications().get(0).getLeaveApplicationID() , is(equalTo(UUID.fromString("1d4cd583-0107-4386-936b-672eb3d1f624"))));
        assertThat(response.getLeaveApplications().get(0).getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getLeaveApplications().get(0).getLeaveTypeID() , is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
        assertThat(response.getLeaveApplications().get(0).getStartDateAsDate(), is(equalTo(LocalDate.of(2019,11,12))));
        assertThat(response.getLeaveApplications().get(0).getEndDateAsDate(), is(equalTo(LocalDate.of(2019,11,12))));
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getPayPeriodStartDateAsDate() , is(equalTo(LocalDate.of(2019,11,8))));
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getPayPeriodEndDateAsDate() , is(equalTo(LocalDate.of(2019,11,14))));        
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getNumberOfUnits(), is(equalTo(0.0)));
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getLeavePeriodStatus(), is(equalTo(com.xero.models.payrollau.LeavePeriodStatus.SCHEDULED)));
        assertThat(response.getLeaveApplications().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T05:30:08Z"))));  
        //System.out.println(response.toString());
    }

    @Test
    public void createLeaveApplicationTest() throws IOException {
        System.out.println("@Test - createLeaveApplicationTest");
        
        List<LeaveApplication> leaveApplications = new ArrayList<>();        
        LeaveApplications response = payrollAuApi.createLeaveApplication(accessToken, xeroTenantId, leaveApplications, null);
        
        assertThat(response.getLeaveApplications().get(0).getTitle(), is(equalTo("Hello World")));
        assertThat(response.getLeaveApplications().get(0).getLeaveApplicationID() , is(equalTo(UUID.fromString("5f7097e4-51f2-46cc-921b-45bc73ea7831"))));
        assertThat(response.getLeaveApplications().get(0).getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getLeaveApplications().get(0).getLeaveTypeID() , is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
        assertThat(response.getLeaveApplications().get(0).getStartDateAsDate(), is(equalTo(LocalDate.of(2019,10,31))));
        assertThat(response.getLeaveApplications().get(0).getEndDateAsDate(), is(equalTo(LocalDate.of(2019,11,01))));
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getPayPeriodStartDateAsDate() , is(equalTo(LocalDate.of(2019,11,1))));
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getPayPeriodEndDateAsDate() , is(equalTo(LocalDate.of(2019,11,07))));        
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getNumberOfUnits(), is(equalTo(0.6)));
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getLeavePeriodStatus(), is(equalTo(com.xero.models.payrollau.LeavePeriodStatus.SCHEDULED)));
        assertThat(response.getLeaveApplications().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T21:16:31.897Z"))));  
        //System.out.println(response.toString());
    }

    @Test
    public void updateLeaveApplicationTest() throws IOException {
        System.out.println("@Test - updateLeaveApplicationTest");
        
        UUID leaveApplicationId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        List<LeaveApplication> leaveApplications = new ArrayList<>();
        LeaveApplications response = payrollAuApi.updateLeaveApplication(accessToken, xeroTenantId, leaveApplicationId, leaveApplications, null);
        
        assertThat(response.getLeaveApplications().get(0).getTitle(), is(equalTo("vacation")));
        assertThat(response.getLeaveApplications().get(0).getLeaveApplicationID() , is(equalTo(UUID.fromString("1d4cd583-0107-4386-936b-672eb3d1f624"))));
        assertThat(response.getLeaveApplications().get(0).getEmployeeID(), is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
        assertThat(response.getLeaveApplications().get(0).getLeaveTypeID() , is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
        assertThat(response.getLeaveApplications().get(0).getStartDateAsDate(), is(equalTo(LocalDate.of(2019,10,31))));
        assertThat(response.getLeaveApplications().get(0).getEndDateAsDate(), is(equalTo(LocalDate.of(2019,11,01))));
        assertThat(response.getLeaveApplications().get(0).getDescription(), is(equalTo("My updated Description")));        
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getPayPeriodStartDateAsDate() , is(equalTo(LocalDate.of(2019,11,01))));
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getPayPeriodEndDateAsDate() , is(equalTo(LocalDate.of(2019,11,07))));        
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getNumberOfUnits(), is(equalTo(0.6)));
        assertThat(response.getLeaveApplications().get(0).getLeavePeriods().get(0).getLeavePeriodStatus(), is(equalTo(com.xero.models.payrollau.LeavePeriodStatus.SCHEDULED)));
        assertThat(response.getLeaveApplications().get(0).getUpdatedDateUTCAsDate(), is(equalTo(OffsetDateTime.parse("2019-11-13T21:16:32.293Z"))));  
        //System.out.println(response.toString());
    }
}
