package com.xero.api.client;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import com.xero.api.ApiClient;
import com.xero.models.payrollnz.Employment;
import com.xero.models.payrollnz.EmploymentObject;

public class PayrollNzApiEmploymentTest {

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
    public void createEmploymentTest() throws IOException {
        System.out.println("@Test UK Payroll - createEmploymentTest");
        
        Employment employment = new Employment();
        employment.setPayrollCalendarID(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"));
        employment.setStartDate(LocalDate.now());
        employment.setEngagementType("FixedTerm");
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmploymentObject response = payrollNzApi.createEmployment(accessToken, xeroTenantId, employeeId, employment, null);
        
        assertThat(response.getEmployment().getPayrollCalendarID(), is(equalTo(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"))));
        assertThat(response.getEmployment().getStartDate(), is(equalTo(LocalDate.of(2020, 9, 02))));
    
        //System.out.println(response.toString());
    }
}
