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
