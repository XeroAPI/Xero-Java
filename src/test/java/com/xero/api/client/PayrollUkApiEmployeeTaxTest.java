package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrolluk.*;

import java.io.IOException;
import java.util.*;
import java.io.InputStream;

public class PayrollUkApiEmployeeTaxTest {

	ApiClient defaultClient; 
    PayrollUkApi payrollUkApi;
     
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
            defaultClient = new ApiClient(properties.getProperty("payrolluk.api.url"),null,null,null,null);
            payrollUkApi = PayrollUkApi.getInstance(defaultClient); 

        } catch (IOException ex) {
            ex.printStackTrace();
        }
       
	}

	public void tearDown() {
		payrollUkApi = null;
        defaultClient = null;
	}

    @Test
    public void getEmployeeTaxTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeeTax");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeTaxObject response = payrollUkApi.getEmployeeTax(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getEmployeeTax().getStarterType() , is(equalTo("New Employee with P45")));
        assertThat(response.getEmployeeTax().getStarterDeclaration() , is(equalTo("B.) This is currently their only job")));
        assertThat(response.getEmployeeTax().getTaxCode() , is(equalTo("1185L")));
        assertThat(response.getEmployeeTax().getW1M1() , is(equalTo(false)));
        assertThat(response.getEmployeeTax().getPreviousTaxPaid() , is(equalTo(200.0)));
        assertThat(response.getEmployeeTax().getPreviousTaxablePay() , is(equalTo(2000.0)));
        assertThat(response.getEmployeeTax().getHasPostGraduateLoans() , is(equalTo(false)));
        assertThat(response.getEmployeeTax().getIsDirector() , is(equalTo(false)));
        
        //System.out.println(response.toString());
    }
}
