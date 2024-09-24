package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrollnz.*;

import java.io.IOException;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.io.InputStream;

public class PayrollNzApiEmployeePayTemplatesTest {

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
    public void getEmployeePayTemplateTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEmployeePayTemplateTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeePayTemplates response = payrollNzApi.getEmployeePayTemplates(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getPayTemplate().getEmployeeID(), is(equalTo(UUID.fromString("68342973-c405-4b86-b5d3-d7b877c27995"))));
        assertThat(response.getPayTemplate().getEarningTemplates().get(0).getPayTemplateEarningID(), is(equalTo(UUID.fromString("1527cf6c-93db-41bf-aba2-9d6af1d3c499"))));
        assertThat(response.getPayTemplate().getEarningTemplates().get(0).getEarningsRateID(), is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));        
        assertThat(response.getPayTemplate().getEarningTemplates().get(0).getRatePerUnit(), is(equalTo(20.0)));
        assertThat(response.getPayTemplate().getEarningTemplates().get(0).getNumberOfUnits(), is(equalTo(8.0)));
        assertThat(response.getPayTemplate().getEarningTemplates().get(0).getName(), is(equalTo("Ordinary Time")));

        //System.out.println(response.toString());
    }

    @Test
    public void updateEmployeePayTemplateTest() throws IOException {
        System.out.println("@Test NZ Payroll - updateEmployeePayTemplateTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        UUID payTemplateEarningID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EarningsTemplate earningsTemplate = new EarningsTemplate();
        EarningsTemplateObject response = payrollNzApi.updateEmployeeEarningsTemplate(accessToken, xeroTenantId, employeeId, payTemplateEarningID, earningsTemplate, null);
         
        assertThat(response.getEarningTemplate().getEarningsRateID(), is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getEarningTemplate().getPayTemplateEarningID(), is(equalTo(UUID.fromString("1527cf6c-93db-41bf-aba2-9d6af1d3c499"))));
        assertThat(response.getEarningTemplate().getRatePerUnit(), is(equalTo(25.0)));
        assertThat(response.getEarningTemplate().getNumberOfUnits(), is(equalTo(4.0)));
        assertThat(response.getEarningTemplate().getName(), is(equalTo("Ordinary Time")));

        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeEarningsTemplateTest() throws IOException {
        System.out.println("@Test NZ Payroll - createEmployeeEarningsTemplateTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EarningsTemplate earningsTemplate = new EarningsTemplate();
        EarningsTemplateObject response = payrollNzApi.createEmployeeEarningsTemplate(accessToken, xeroTenantId, employeeId, earningsTemplate, null);
         
        assertThat(response.getEarningTemplate().getPayTemplateEarningID(), is(equalTo(UUID.fromString("11b4e492-5d56-4eac-a9ce-687d7b9a4a84"))));
        assertThat(response.getEarningTemplate().getEarningsRateID(), is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getEarningTemplate().getRatePerUnit(), is(equalTo(20.0)));
        assertThat(response.getEarningTemplate().getNumberOfUnits(), is(equalTo(8.0)));
        assertThat(response.getEarningTemplate().getName(), is(equalTo("Ordinary Time")));
    
        //System.out.println(response.toString());
    }

    @Test
    public void createMultipleEmployeeEarningsTemplateTest() throws IOException {
        System.out.println("@Test NZ Payroll - createMultipleEmployeeEarningsTemplateTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        List<EarningsTemplate> earningsTemplate = new ArrayList<>();
        EmployeeEarningsTemplates response = payrollNzApi.createMultipleEmployeeEarningsTemplate(accessToken, xeroTenantId, employeeId, earningsTemplate, null);
         
        assertThat(response.getEarningTemplates().get(0).getPayTemplateEarningID(), is(equalTo(UUID.fromString("1527cf6c-93db-41bf-aba2-9d6af1d3c499"))));
        assertThat(response.getEarningTemplates().get(0).getEarningsRateID(), is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getEarningTemplates().get(0).getRatePerUnit(), is(equalTo(20.0)));
        assertThat(response.getEarningTemplates().get(0).getNumberOfUnits(), is(equalTo(8.0)));
        assertThat(response.getEarningTemplates().get(0).getName(), is(equalTo("Ordinary Time")));
       
        //System.out.println(response.toString());
    }
}
