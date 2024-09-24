package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrollnz.*;

import org.threeten.bp.*;
import java.io.IOException;
import java.util.UUID;
import java.util.*;
import java.io.InputStream;

public class PayrollNzApiLeaveTypesTest {

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
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")){
            properties.load(input);
            defaultClient = new ApiClient(properties.getProperty("payrollnz.api.url"),null,null,null,null);
            payrollNzApi = PayrollNzApi.getInstance(defaultClient); 

        } catch (IOException ex) {
            ex.printStackTrace();
        } 
       
	}

	public void tearDown() {
		payrollNzApi = null;
        defaultClient = null;
	}

    @Test
    public void getLeaveTypesTest() throws IOException {
        System.out.println("@Test NZ Payroll - getLeaveTypesTest");
       
        int page = 1;
        LeaveTypes response = payrollNzApi.getLeaveTypes(accessToken, xeroTenantId, page, true);
        
        assertThat(response.getLeaveTypes().get(0).getLeaveTypeID(),is(equalTo(UUID.fromString("b0b1b79e-2a25-46c2-ad08-ca25ef48d7e4"))));
        assertThat(response.getLeaveTypes().get(0).getName(), is(equalTo("Annual Leave")));
        assertThat(response.getLeaveTypes().get(0).getIsPaidLeave(), is(equalTo(true)));
        assertThat(response.getLeaveTypes().get(0).getShowOnPayslip(), is(equalTo(true)));
        assertThat(response.getLeaveTypes().get(0).getIsActive(), is(equalTo(true)));
        assertThat(response.getLeaveTypes().get(0).getUpdatedDateUTC(), is(equalTo(LocalDateTime.parse("2019-09-22T23:09:32"))));

        //System.out.println(response.toString());
    }

    @Test
    public void getLeaveTypeTest() throws IOException {
        System.out.println("@Test NZ Payroll - getLeaveTypeTest");
       
        UUID leaveTypeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        LeaveTypeObject response = payrollNzApi.getLeaveType(accessToken, xeroTenantId, leaveTypeId);
        
        assertThat(response.getLeaveType().getLeaveTypeID(),is(equalTo(UUID.fromString("b0b1b79e-2a25-46c2-ad08-ca25ef48d7e4"))));
        assertThat(response.getLeaveType().getName(), is(equalTo("Annual Leave")));
        assertThat(response.getLeaveType().getIsPaidLeave(), is(equalTo(true)));
        assertThat(response.getLeaveType().getShowOnPayslip(), is(equalTo(true)));
        assertThat(response.getLeaveType().getIsActive(), is(equalTo(true)));
        assertThat(response.getLeaveType().getUpdatedDateUTC(), is(equalTo(LocalDateTime.parse("2019-09-22T23:09:32"))));

        //System.out.println(response.toString());
    }

    @Test
    public void createLeaveTypeTest() throws IOException {
        System.out.println("@Test NZ Payroll - createLeaveTypeTest");
       
        LeaveType leaveType = new LeaveType();
        leaveType.setLeaveTypeID(UUID.randomUUID());
        leaveType.setName("My opebvwbfxf Leave");
        leaveType.setIsPaidLeave(true);
        leaveType.isActive(true);
        leaveType.setShowOnPayslip(true);
        LeaveTypeObject response = payrollNzApi.createLeaveType(accessToken, xeroTenantId, leaveType, null);
        
        assertThat(response.getLeaveType().getLeaveTypeID(),is(equalTo(UUID.fromString("80464f55-b5c9-4d05-84c7-219d98baa3e2"))));
        assertThat(response.getLeaveType().getName(), is(equalTo("My wqwhhiktun Leave")));
        assertThat(response.getLeaveType().getIsPaidLeave(), is(equalTo(false)));
        assertThat(response.getLeaveType().getShowOnPayslip(), is(equalTo(true)));
        assertThat(response.getLeaveType().getIsActive(), is(equalTo(true)));
        assertThat(response.getLeaveType().getUpdatedDateUTC(), is(equalTo(LocalDateTime.parse("2020-08-27T20:49:59.831561900"))));

        //System.out.println(response.toString());
    }
}
