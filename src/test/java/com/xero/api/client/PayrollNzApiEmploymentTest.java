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
        
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("payrollnz.api.url"),null,null,null,null);
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
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        employment.setPayrollCalendarID(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"));
        employment.setStartDate(LocalDate.now());
        employment.setEngagementType("FixedTerm");
        EmploymentObject response = payrollNzApi.createEmployment(accessToken, xeroTenantId, employeeId, employment, null);
        
        assertThat(response.getEmployment().getPayrollCalendarID(), is(equalTo(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"))));
        assertThat(response.getEmployment().getStartDate(), is(equalTo(LocalDate.of(2020, 9, 02))));
    
        //System.out.println(response.toString());
    }
}
