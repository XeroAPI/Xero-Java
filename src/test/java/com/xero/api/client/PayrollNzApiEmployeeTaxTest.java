package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrollnz.*;

import java.io.IOException;
import java.util.UUID;

public class PayrollNzApiEmployeeTaxTest {

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
    public void getEmployeeTaxTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeeTax");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeTaxObject response = payrollNzApi.getEmployeeTax(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getEmployeeTax().getIrdNumber() , is(equalTo("111111111")));
        assertThat(response.getEmployeeTax().getTaxCode() , is(equalTo(com.xero.models.payrollnz.TaxCode.M)));
        assertThat(response.getEmployeeTax().getHasSpecialStudentLoanRate() , is(equalTo(false)));
        assertThat(response.getEmployeeTax().getIsEligibleForKiwiSaver() , is(equalTo(true)));
        assertThat(response.getEmployeeTax().getEsctRatePercentage() , is(equalTo(17.5)));
        assertThat(response.getEmployeeTax().getKiwiSaverContributions() , is(equalTo(com.xero.models.payrollnz.EmployeeTax.KiwiSaverContributionsEnum.MAKECONTRIBUTIONS)));
        assertThat(response.getEmployeeTax().getKiwiSaverEmployeeContributionRatePercentage() , is(equalTo(3.0)));
        assertThat(response.getEmployeeTax().getKiwiSaverEmployerContributionRatePercentage() , is(equalTo(3.0)));
        assertThat(response.getEmployeeTax().getKiwiSaverEmployerSalarySacrificeContributionRatePercentage() , is(equalTo(0.0)));
        assertThat(response.getEmployeeTax().getHasStudentLoanBalance() , is(equalTo(false)));        
        
        //System.out.println(response.toString());
    }

    @Test
    public void updateEmployeeTaxTest() throws IOException {
        System.out.println("@Test NZ Payroll - updateEmployeeTax");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeTax employeeTax = new EmployeeTax();
        EmployeeTaxObject response = payrollNzApi.updateEmployeeTax(accessToken, xeroTenantId, employeeId,employeeTax, null);
        
        // assertThat(response.getEmployeeTax().getIrdNumber() , is(equalTo("111111111")));
        // assertThat(response.getEmployeeTax().getTaxCode() , is(equalTo(com.xero.models.payrollnz.TaxCode.M)));
        // assertThat(response.getEmployeeTax().getHasSpecialStudentLoanRate() , is(equalTo(false)));
        // assertThat(response.getEmployeeTax().getIsEligibleForKiwiSaver() , is(equalTo(true)));
        // assertThat(response.getEmployeeTax().getEsctRatePercentage() , is(equalTo(17.5)));
        // assertThat(response.getEmployeeTax().getKiwiSaverContributions() , is(equalTo(com.xero.models.payrollnz.EmployeeTax.KiwiSaverContributionsEnum.MAKECONTRIBUTIONS)));
        // assertThat(response.getEmployeeTax().getKiwiSaverEmployeeContributionRatePercentage() , is(equalTo(3.0)));
        // assertThat(response.getEmployeeTax().getKiwiSaverEmployerContributionRatePercentage() , is(equalTo(3.0)));
        // assertThat(response.getEmployeeTax().getKiwiSaverEmployerSalarySacrificeContributionRatePercentage() , is(equalTo(0.0)));
        // assertThat(response.getEmployeeTax().getHasStudentLoanBalance() , is(equalTo(false)));        
        
        System.out.println(response.toString());
    }
}
