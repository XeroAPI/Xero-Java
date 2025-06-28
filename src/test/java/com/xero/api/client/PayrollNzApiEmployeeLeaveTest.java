package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrollnz.*;

import org.threeten.bp.*;
import java.io.IOException;
import java.util.UUID;

public class PayrollNzApiEmployeeLeaveTest {

	ApiClient defaultClient; 
    PayrollNzApi payrollNzApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("payrollnz.api.url"),null,null,null,null);
        payrollNzApi = PayrollNzApi.getInstance(defaultClient);     
	}

	public void tearDown() {
		payrollNzApi = null;
        defaultClient = null;
	}

	@Test
    public void getEmployeeLeavesTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEmployeeLeavesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeLeaves response =  payrollNzApi.getEmployeeLeaves(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getLeave().get(0).getLeaveID(), is(equalTo(UUID.fromString("e311d45f-122e-4fbf-b068-4e3f92dd2729"))));
        assertThat(response.getLeave().get(0).getLeaveTypeID(), is(equalTo(UUID.fromString("0441497f-5dc7-4cd3-a90d-f2e07e21b2a6"))));
        assertThat(response.getLeave().get(0).getDescription(), is(equalTo("Holiday Pay Payout")));
        assertThat(response.getLeave().get(0).getStartDate(), is(equalTo(LocalDate.of(2019, 07, 01))));
        assertThat(response.getLeave().get(0).getEndDate(), is(equalTo(LocalDate.of(2019, 07, 07))));
        assertThat(response.getLeave().get(0).getPeriods().get(0).getPeriodStartDate(), is(equalTo(LocalDate.of(2019, 07, 01))));
        assertThat(response.getLeave().get(0).getPeriods().get(0).getPeriodEndDate(), is(equalTo(LocalDate.of(2019, 07, 07))));
        assertThat(response.getLeave().get(0).getPeriods().get(0).getNumberOfUnits(), is(equalTo(36.0)));
        assertThat(response.getLeave().get(0).getPeriods().get(0).getPeriodStatus(), is(equalTo(com.xero.models.payrollnz.LeavePeriod.PeriodStatusEnum.COMPLETED)));
        assertThat(response.getLeave().get(0).getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2019, 9, 24, 05, 8, 44) )));  
        
        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeLeaveTest() throws IOException {
        System.out.println("@Test NZ Payroll - createEmployeeLeaveTest");
        
        EmployeeLeave employeeLeave = new EmployeeLeave();
        employeeLeave.setLeaveTypeID(UUID.fromString("b0b1b79e-2a25-46c2-ad08-ca25ef48d7e4"));
        employeeLeave.setDescription("Creating a Description");
        employeeLeave.setStartDate(LocalDate.of(2020, 04, 24));
        employeeLeave.setEndDate(LocalDate.of(2020, 04, 26));
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        
        EmployeeLeaveObject response = payrollNzApi.createEmployeeLeave(accessToken, xeroTenantId, employeeId, employeeLeave, null);
        
        assertThat(response.getLeave().getLeaveID(), is(equalTo(UUID.fromString("82a04ba6-a5cc-4e7d-86d4-b9f381a494e8"))));
        assertThat(response.getLeave().getLeaveTypeID(), is(equalTo(UUID.fromString("b0b1b79e-2a25-46c2-ad08-ca25ef48d7e4"))));
        assertThat(response.getLeave().getDescription(), is(equalTo("Creating a Description")));
        assertThat(response.getLeave().getStartDate(), is(equalTo(LocalDate.of(2020, 04, 24))));
        assertThat(response.getLeave().getEndDate(), is(equalTo(LocalDate.of(2020, 04, 26))));
        assertThat(response.getLeave().getPeriods().get(0).getPeriodStartDate(), is(equalTo(LocalDate.of(2020, 04, 20))));
        assertThat(response.getLeave().getPeriods().get(0).getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 04, 26))));
        assertThat(response.getLeave().getPeriods().get(0).getNumberOfUnits(), is(equalTo(0.0)));
        assertThat(response.getLeave().getPeriods().get(0).getPeriodStatus(), is(equalTo(com.xero.models.payrollnz.LeavePeriod.PeriodStatusEnum.APPROVED)));
        assertThat(response.getLeave().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 8, 26, 20, 29, 55, 215156100) )));  
       
        //System.out.println(response.toString());
    }

    @Test
    public void updateEmployeeLeaveTest() throws IOException {
        System.out.println("@Test NZ Payroll - updateEmployeeLeaveTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        UUID leaveId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        
        EmployeeLeave employeeLeave = new EmployeeLeave();
        employeeLeave.setLeaveTypeID(UUID.fromString("b0b1b79e-2a25-46c2-ad08-ca25ef48d7e4"));
        employeeLeave.setDescription("Creating a Description");
        employeeLeave.setStartDate(LocalDate.of(2020, 04, 24));
        employeeLeave.setEndDate(LocalDate.of(2020, 04, 26));
        EmployeeLeaveObject response = payrollNzApi.updateEmployeeLeave(accessToken, xeroTenantId, employeeId, leaveId, employeeLeave, null);
        
        assertThat(response.getLeave().getLeaveID(), is(equalTo(UUID.fromString("82a04ba6-a5cc-4e7d-86d4-b9f381a494e8"))));
        assertThat(response.getLeave().getLeaveTypeID(), is(equalTo(UUID.fromString("b0b1b79e-2a25-46c2-ad08-ca25ef48d7e4"))));
        assertThat(response.getLeave().getDescription(), is(equalTo("Creating a Description")));
        assertThat(response.getLeave().getStartDate(), is(equalTo(LocalDate.of(2020, 04, 24))));
        assertThat(response.getLeave().getEndDate(), is(equalTo(LocalDate.of(2020, 04, 26))));
        assertThat(response.getLeave().getPeriods().get(0).getPeriodStartDate(), is(equalTo(LocalDate.of(2020, 04, 20))));
        assertThat(response.getLeave().getPeriods().get(0).getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 04, 26))));
        assertThat(response.getLeave().getPeriods().get(0).getNumberOfUnits(), is(equalTo(1.0)));
        assertThat(response.getLeave().getPeriods().get(0).getPeriodStatus(), is(equalTo(com.xero.models.payrollnz.LeavePeriod.PeriodStatusEnum.APPROVED)));
        assertThat(response.getLeave().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 8, 26, 20, 29, 55, 885453600) )));  
       
        //System.out.println(response.toString());
    }
}
