package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrollnz.*;
import com.xero.models.payrollnz.SalaryAndWage.PaymentTypeEnum;
import com.xero.models.payrollnz.SalaryAndWage.StatusEnum;

import org.threeten.bp.*;
import java.io.IOException;
import java.util.*;

public class PayrollNzApiSalaryAndWagesTest {

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
    public void getSalaryAndWagesTest() throws IOException {
        System.out.println("@Test NZ Payroll - getSalaryAndWagesTest");
       
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        SalaryAndWages response =  payrollNzApi.getEmployeeSalaryAndWages(accessToken, xeroTenantId, employeeId, 1);
        
        assertThat(response.getSalaryAndWages().get(0).getSalaryAndWagesID(), is(equalTo(UUID.fromString("b0026f5a-fa8e-43aa-95e4-cec3c0972e39"))));
        assertThat(response.getSalaryAndWages().get(0).getEarningsRateID(), is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getSalaryAndWages().get(0).getNumberOfUnitsPerWeek(), is(equalTo(0.0)));
        assertThat(response.getSalaryAndWages().get(0).getRatePerUnit(), is(equalTo(25.0)));
        assertThat(response.getSalaryAndWages().get(0).getNumberOfUnitsPerDay(), is(equalTo(0.0)));
        assertThat(response.getSalaryAndWages().get(0).getDaysPerWeek(), is(equalTo(0.0)));
        assertThat(response.getSalaryAndWages().get(0).getEffectiveFrom(), is(equalTo(LocalDate.of(2019, 02, 07))));        
        assertThat(response.getSalaryAndWages().get(0).getAnnualSalary(), is(equalTo(25.0)));
        assertThat(response.getSalaryAndWages().get(0).getStatus() , is(equalTo(com.xero.models.payrollnz.SalaryAndWage.StatusEnum.ACTIVE)));
        assertThat(response.getSalaryAndWages().get(0).getPaymentType() , is(equalTo(com.xero.models.payrollnz.SalaryAndWage.PaymentTypeEnum.HOURLY)));

        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeSalaryAndWageTest() throws IOException {
        System.out.println("@Test NZ Payroll - createEmployeeSalaryAndWageTest");
        
        SalaryAndWage salaryAndWage = new SalaryAndWage();  
        salaryAndWage.setEarningsRateID(UUID.randomUUID());
        salaryAndWage.setNumberOfUnitsPerWeek(1.00);
        salaryAndWage.setNumberOfUnitsPerDay(1.00);
        salaryAndWage.setEffectiveFrom(LocalDate.now());
        salaryAndWage.setAnnualSalary(2.00);
        salaryAndWage.setStatus(StatusEnum.ACTIVE);
        salaryAndWage  .setPaymentType(PaymentTypeEnum.HOURLY);      
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        SalaryAndWageObject response =  payrollNzApi.createEmployeeSalaryAndWage(accessToken, xeroTenantId, employeeId, salaryAndWage, null);
        
        assertThat(response.getSalaryAndWages().getSalaryAndWagesID(), is(equalTo(UUID.fromString("0211c70c-93d5-4da1-a570-b66d8df2ca15"))));
        assertThat(response.getSalaryAndWages().getEarningsRateID(), is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getSalaryAndWages().getNumberOfUnitsPerWeek(), is(equalTo(2.0)));
        assertThat(response.getSalaryAndWages().getNumberOfUnitsPerDay(), is(equalTo(2.0)));
        assertThat(response.getSalaryAndWages().getDaysPerWeek(), is(equalTo(1.0)));
        assertThat(response.getSalaryAndWages().getEffectiveFrom(), is(equalTo(LocalDate.of(2020, 05, 01))));        
        assertThat(response.getSalaryAndWages().getAnnualSalary(), is(equalTo(100.0)));
        assertThat(response.getSalaryAndWages().getStatus() , is(equalTo(com.xero.models.payrollnz.SalaryAndWage.StatusEnum.ACTIVE)));
        assertThat(response.getSalaryAndWages().getPaymentType() , is(equalTo(com.xero.models.payrollnz.SalaryAndWage.PaymentTypeEnum.SALARY)));

        //System.out.println(response.toString());
    }

    @Test
    public void getEmployeeSalaryAndWageTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEmployeeSalaryAndWageTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        UUID salaryAndWagesId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        SalaryAndWages response =  payrollNzApi.getEmployeeSalaryAndWage(accessToken, xeroTenantId, employeeId, salaryAndWagesId);
        
        assertThat(response.getSalaryAndWages().get(0).getSalaryAndWagesID(), is(equalTo(UUID.fromString("b0026f5a-fa8e-43aa-95e4-cec3c0972e39"))));
        assertThat(response.getSalaryAndWages().get(0).getEarningsRateID(), is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getSalaryAndWages().get(0).getNumberOfUnitsPerWeek(), is(equalTo(0.0)));
        assertThat(response.getSalaryAndWages().get(0).getRatePerUnit(), is(equalTo(25.0)));
        assertThat(response.getSalaryAndWages().get(0).getNumberOfUnitsPerDay(), is(equalTo(0.0)));
        assertThat(response.getSalaryAndWages().get(0).getDaysPerWeek(), is(equalTo(0.0)));
        assertThat(response.getSalaryAndWages().get(0).getEffectiveFrom(), is(equalTo(LocalDate.of(2019, 02, 07))));        
        assertThat(response.getSalaryAndWages().get(0).getAnnualSalary(), is(equalTo(25.0)));
        assertThat(response.getSalaryAndWages().get(0).getStatus() , is(equalTo(com.xero.models.payrollnz.SalaryAndWage.StatusEnum.ACTIVE)));
        assertThat(response.getSalaryAndWages().get(0).getPaymentType() , is(equalTo(com.xero.models.payrollnz.SalaryAndWage.PaymentTypeEnum.HOURLY)));

        //System.out.println(response.toString());
    }

    @Test
    public void updateEmployeeSalaryAndWageTest() throws IOException {
        System.out.println("@Test NZ Payroll - updateEmployeeSalaryAndWageTest");
        
        SalaryAndWage salaryAndWage = new SalaryAndWage();
        salaryAndWage.setEarningsRateID(UUID.randomUUID());
        salaryAndWage.setNumberOfUnitsPerWeek(1.00);
        salaryAndWage.setNumberOfUnitsPerDay(1.00);
        salaryAndWage.setEffectiveFrom(LocalDate.now());
        salaryAndWage.setAnnualSalary(2.00);
        salaryAndWage.setStatus(StatusEnum.ACTIVE);
        salaryAndWage  .setPaymentType(PaymentTypeEnum.HOURLY);     
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        UUID salaryAndWagesId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        SalaryAndWageObject response =  payrollNzApi.updateEmployeeSalaryAndWage(accessToken, xeroTenantId, employeeId, salaryAndWagesId, salaryAndWage, null);
        
        assertThat(response.getSalaryAndWages().getSalaryAndWagesID(), is(equalTo(UUID.fromString("1912d614-99d0-43e6-8d63-5b539dcfe358"))));
        assertThat(response.getSalaryAndWages().getEarningsRateID(), is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getSalaryAndWages().getNumberOfUnitsPerWeek(), is(equalTo(3.0)));
        assertThat(response.getSalaryAndWages().getNumberOfUnitsPerDay(), is(equalTo(3.0)));
        assertThat(response.getSalaryAndWages().getDaysPerWeek(), is(equalTo(1.0)));
        assertThat(response.getSalaryAndWages().getEffectiveFrom(), is(equalTo(LocalDate.of(2020, 05, 15))));        
        assertThat(response.getSalaryAndWages().getAnnualSalary(), is(equalTo(101.0)));
        assertThat(response.getSalaryAndWages().getStatus() , is(equalTo(com.xero.models.payrollnz.SalaryAndWage.StatusEnum.ACTIVE)));
        assertThat(response.getSalaryAndWages().getPaymentType() , is(equalTo(com.xero.models.payrollnz.SalaryAndWage.PaymentTypeEnum.SALARY)));

        //System.out.println(response.toString());
    }
}
