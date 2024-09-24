package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrollnz.*;

import org.threeten.bp.*;
import java.io.IOException;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.io.InputStream;

public class PayrollNzApiEmployeeOpeningBalancesTest {

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
    public void getEmployeeOpeningBalancesTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEmployeeOpeningBalancesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeOpeningBalancesObject response = payrollNzApi.getEmployeeOpeningBalances(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getOpeningBalances().get(0).getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 10, 01))));
        assertThat(response.getOpeningBalances().get(0).getDaysPaid(), is(equalTo(3)));
        assertThat(response.getOpeningBalances().get(0).getUnpaidWeeks(), is(equalTo(2)));
        assertThat(response.getOpeningBalances().get(0).getGrossEarnings(), is(equalTo(40.0)));
        
        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeOpeningBalancesTest() throws IOException {
        System.out.println("@Test NZ Payroll - createEmployeeOpeningBalancesTest");

        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        List<EmployeeOpeningBalance> employeeOpeningBalances = new ArrayList<>();
      
        EmployeeOpeningBalancesObject response = payrollNzApi.createEmployeeOpeningBalances(accessToken, xeroTenantId, employeeId, employeeOpeningBalances, null);
      
        assertThat(response.getOpeningBalances().get(0).getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 10, 01))));
        assertThat(response.getOpeningBalances().get(0).getDaysPaid(), is(equalTo(3)));
        assertThat(response.getOpeningBalances().get(0).getUnpaidWeeks(), is(equalTo(2)));
        assertThat(response.getOpeningBalances().get(0).getGrossEarnings(), is(equalTo(40.0)));

        //System.out.println(response.toString());
    }
}
