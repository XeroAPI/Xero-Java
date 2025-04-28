package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrolluk.*;
import org.threeten.bp.*;
import java.io.IOException;
import java.util.*;

public class PayrollUkApiLeaveTypesTest {

	ApiClient defaultClient; 
    PayrollUkApi payrollUkApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("payrolluk.api.url"),null,null,null,null);
        payrollUkApi = PayrollUkApi.getInstance(defaultClient); 
	}

	public void tearDown() {
		payrollUkApi = null;
        defaultClient = null;
	}

    @Test
    public void getLeaveTypesTest() throws IOException {
        System.out.println("@Test UK Payroll - getLeaveTypesTest");
       
        int page = 1;
        LeaveTypes response = payrollUkApi.getLeaveTypes(accessToken, xeroTenantId, page, true);
        
        assertThat(response.getLeaveTypes().get(0).getLeaveTypeID(),is(equalTo(UUID.fromString("ed08dffe-788e-4b24-9630-f0fa2f4d164c"))));
        assertThat(response.getLeaveTypes().get(0).getName(), is(equalTo("Holiday")));
        assertThat(response.getLeaveTypes().get(0).getIsPaidLeave(), is(equalTo(true)));
        assertThat(response.getLeaveTypes().get(0).getShowOnPayslip(), is(equalTo(true)));
        assertThat(response.getLeaveTypes().get(0).getIsActive(), is(equalTo(true)));
        assertThat(response.getLeaveTypes().get(0).getIsStatutoryLeave(), is(equalTo(false)));
        assertThat(response.getLeaveTypes().get(0).getUpdatedDateUTC(), is(equalTo(LocalDateTime.parse("2020-02-13T15:56:11"))));

        //System.out.println(response.toString());
    }

    @Test
    public void getLeaveTypeTest() throws IOException {
        System.out.println("@Test UK Payroll - getLeaveTypeTest");
       
        UUID leaveTypeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        LeaveTypeObject response = payrollUkApi.getLeaveType(accessToken, xeroTenantId, leaveTypeId);
        
        assertThat(response.getLeaveType().getLeaveTypeID(),is(equalTo(UUID.fromString("ed08dffe-788e-4b24-9630-f0fa2f4d164c"))));
        assertThat(response.getLeaveType().getName(), is(equalTo("Holiday")));
        assertThat(response.getLeaveType().getIsPaidLeave(), is(equalTo(true)));
        assertThat(response.getLeaveType().getShowOnPayslip(), is(equalTo(true)));
        assertThat(response.getLeaveType().getIsActive(), is(equalTo(true)));
        assertThat(response.getLeaveType().getIsStatutoryLeave(), is(equalTo(false)));
        assertThat(response.getLeaveType().getUpdatedDateUTC(), is(equalTo(LocalDateTime.parse("2020-04-20T20:14:31"))));

        //System.out.println(response.toString());
    }

    @Test
    public void createLeaveTypeTest() throws IOException {
        System.out.println("@Test UK Payroll - createLeaveTypeTest");
       
        LeaveType leaveType = new LeaveType();
        leaveType.setLeaveID(UUID.randomUUID());
        leaveType.setName("My opebvwbfxf Leave");
        leaveType.setIsStatutoryLeave(true);
        leaveType.setIsPaidLeave(true);
        leaveType.isActive(true);
        leaveType.setShowOnPayslip(true);
        LeaveTypeObject response = payrollUkApi.createLeaveType(accessToken, xeroTenantId, leaveType, null);
        
        assertThat(response.getLeaveType().getLeaveTypeID(),is(equalTo(UUID.fromString("4c027a23-6e7b-4547-808b-c34b2b140fef"))));
        assertThat(response.getLeaveType().getName(), is(equalTo("My opebvwbfxf Leave")));
        assertThat(response.getLeaveType().getIsPaidLeave(), is(equalTo(false)));
        assertThat(response.getLeaveType().getShowOnPayslip(), is(equalTo(true)));
        assertThat(response.getLeaveType().getIsActive(), is(equalTo(true)));
        assertThat(response.getLeaveType().getUpdatedDateUTC(), is(equalTo(LocalDateTime.parse("2020-04-21T02:59:35.383124900"))));

        //System.out.println(response.toString());
    }
}
