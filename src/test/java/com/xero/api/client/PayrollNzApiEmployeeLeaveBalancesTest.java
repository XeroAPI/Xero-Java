package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrollnz.*;

import java.io.IOException;
import java.util.UUID;

public class PayrollNzApiEmployeeLeaveBalancesTest {

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
    public void getEmployeeLeaveBalancesTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEmployeeLeaveBalancesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeLeaveBalances response = payrollNzApi.getEmployeeLeaveBalances(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getPagination().getPage() , is(equalTo(1)));
        assertThat(response.getPagination().getPageSize() , is(equalTo(100)));
        assertThat(response.getPagination().getPageCount() , is(equalTo(1)));
        assertThat(response.getPagination().getItemCount() , is(equalTo(4)));
        assertThat(response.getLeaveBalances().get(0).getLeaveTypeID(), is(equalTo(UUID.fromString("0441497f-5dc7-4cd3-a90d-f2e07e21b2a6"))));
        assertThat(response.getLeaveBalances().get(0).getName() , is(equalTo("Holiday Pay")));
        assertThat(response.getLeaveBalances().get(0).getBalance() , is(equalTo(0.0)));
        assertThat(response.getLeaveBalances().get(0).getTypeOfUnits() , is(equalTo("Dollars")));

        //System.out.println(response.toString());
    }
}
