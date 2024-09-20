package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrollnz.*;

import java.io.IOException;
import java.util.UUID;

public class PayrollNzApiEmployeeLeaveSetupTest {

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
		defaultClient = new ApiClient("http://127.0.0.1:4016",null,null,null,null);
        payrollNzApi = PayrollNzApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollNzApi = null;
        defaultClient = null;
	}

    @Test
    public void createEmployeeLeaveSetupTest() throws IOException {
        System.out.println("@Test NZ Payroll - createEmployeeLeaveSetupTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeLeaveSetup employeeLeaveSetup = new EmployeeLeaveSetup();

        EmployeeLeaveSetupObject response = payrollNzApi.createEmployeeLeaveSetup(accessToken, xeroTenantId, employeeId, employeeLeaveSetup, null);
        
        assertThat(response.getLeaveSetup().getHolidayPayOpeningBalance(), is(equalTo(10.0)));
        assertThat(response.getLeaveSetup().getAnnualLeaveOpeningBalance(), is(equalTo(100.0)));
        assertThat(response.getLeaveSetup().getSickLeaveHoursToAccrueAnnually(), is(equalTo(20.0)));
        assertThat(response.getLeaveSetup().getSickLeaveOpeningBalance(), is(equalTo(10.0)));

        //System.out.println(response.toString());
    }
}
