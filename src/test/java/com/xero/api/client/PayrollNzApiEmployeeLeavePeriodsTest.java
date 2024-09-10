package com.xero.api.client;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;

import com.xero.api.ApiClient;
import com.xero.models.payrollnz.LeavePeriods;

public class PayrollNzApiEmployeeLeavePeriodsTest {

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
    public void getEmployeeLeavePeriodsTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEmployeeLeavePeriodsTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        LocalDate startDate = LocalDate.of(2020, Month.MARCH, 30);
        LocalDate endDate = LocalDate.of(2020, Month.MARCH, 30);
        LeavePeriods response = payrollNzApi.getEmployeeLeavePeriods(accessToken, xeroTenantId, employeeId, startDate, endDate);
        
        assertThat(response.getPeriods().get(0).getPeriodStartDate(),  is(equalTo(LocalDate.of(2020, 02, 24))));
        assertThat(response.getPeriods().get(0).getPeriodEndDate(),  is(equalTo(LocalDate.of(2020, 03, 01))));
        assertThat(response.getPeriods().get(0).getNumberOfUnits() , is(equalTo(24.0)));
        
        //System.out.println(response.toString());
    }
}
