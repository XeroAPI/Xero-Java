package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrollnz.*;

import org.threeten.bp.*;
import java.io.IOException;
import java.util.UUID;

public class PayrollNzApiEmployeeTest {

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
    public void getEmployeesTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeesTest");
       
        Employees response =  payrollNzApi.getEmployees(accessToken, xeroTenantId, null,1);
        
        assertThat(response.getEmployees().get(0).getEmployeeID(), is(equalTo(UUID.fromString("68342973-c405-4b86-b5d3-d7b877c27995"))));
        assertThat(response.getEmployees().get(0).getFirstName(), is(equalTo("Casual")));
        assertThat(response.getEmployees().get(0).getLastName(), is(equalTo("Worker")));
        assertThat(response.getEmployees().get(0).getDateOfBirth(), is(equalTo(LocalDate.of(1990, 12, 01))));
        assertThat(response.getEmployees().get(0).getAddress().getAddressLine1(), is(equalTo("30 King ST")));
        assertThat(response.getEmployees().get(0).getAddress().getCity(), is(equalTo("Rangiora")));
        assertThat(response.getEmployees().get(0).getAddress().getPostCode(), is(equalTo("7400")));
        assertThat(response.getEmployees().get(0).getAddress().getCountryName(), is(equalTo("NEW ZEALAND")));
        assertThat(response.getEmployees().get(0).getGender() , is(equalTo(com.xero.models.payrollnz.Employee.GenderEnum.M)));
        assertThat(response.getEmployees().get(0).getStartDate() , is(equalTo(LocalDate.of(2019, 02, 07))));
        assertThat(response.getEmployees().get(0).getPayrollCalendarID(), is(equalTo(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"))));        
        assertThat(response.getEmployees().get(0).getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2019, 9, 24, 05, 8, 45) )));  
        assertThat(response.getEmployees().get(0).getCreatedDateUTC(), is(equalTo(LocalDateTime.of(2019, 9, 22, 23, 58, 23) )));  
        
        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeTest() throws IOException {
        System.out.println("@Test UK Payroll - createEmployeeTest");
        
         Employee employee = new Employee();
         Address address = new Address();
         address.setAddressLine1("101 Green St");
         address.setCity("San Francisco");
         address.setPostCode("4351");
         employee.setAddress(address);
         employee.setFirstName("Mike");
         employee.setLastName("Johntzxzpxhmkgson");
         employee.setDateOfBirth(LocalDate.now());
         EmployeeObject response = payrollNzApi.createEmployee(accessToken, xeroTenantId, employee, null);
        
         assertThat(response.getEmployee().getEmployeeID(), is(equalTo(UUID.fromString("658be485-3feb-402e-9e77-ac17623aad42"))));
         assertThat(response.getEmployee().getFirstName(), is(equalTo("Mike")));
         assertThat(response.getEmployee().getLastName(), is(equalTo("Johntzxzpxhmkgson")));
         assertThat(response.getEmployee().getDateOfBirth(), is(equalTo(LocalDate.of(2000, 01, 01))));
         assertThat(response.getEmployee().getAddress().getAddressLine1(), is(equalTo("101 Green St")));
         assertThat(response.getEmployee().getAddress().getCity(), is(equalTo("San Francisco")));
         assertThat(response.getEmployee().getAddress().getPostCode(), is(equalTo("4351")));
         assertThat(response.getEmployee().getEmail(), is(equalTo("83139@starkindustries.com")));
         assertThat(response.getEmployee().getGender() , is(equalTo(com.xero.models.payrollnz.Employee.GenderEnum.M)));
         assertThat(response.getEmployee().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 8, 24, 20, 27, 22) )));  
         assertThat(response.getEmployee().getCreatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 8, 24, 20, 27, 22) )));  
   
        //System.out.println(response.toString());
    }

    @Test
    public void getEmployeeTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeeTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeObject response = payrollNzApi.getEmployee(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getEmployee().getEmployeeID(), is(equalTo(UUID.fromString("68342973-c405-4b86-b5d3-d7b877c27995"))));
        assertThat(response.getEmployee().getFirstName(), is(equalTo("Casual")));
        assertThat(response.getEmployee().getLastName(), is(equalTo("Worker")));
        assertThat(response.getEmployee().getDateOfBirth(), is(equalTo(LocalDate.of(1990, 12, 01))));
        assertThat(response.getEmployee().getAddress().getAddressLine1(), is(equalTo("30 King ST")));
        assertThat(response.getEmployee().getAddress().getCity(), is(equalTo("Rangiora")));
        assertThat(response.getEmployee().getAddress().getPostCode(), is(equalTo("7400")));
        assertThat(response.getEmployee().getAddress().getCountryName(), is(equalTo("NEW ZEALAND")));
        assertThat(response.getEmployee().getGender() , is(equalTo(com.xero.models.payrollnz.Employee.GenderEnum.M)));
        assertThat(response.getEmployee().getStartDate() , is(equalTo(LocalDate.of(2019, 02, 07))));
        assertThat(response.getEmployee().getPayrollCalendarID(), is(equalTo(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"))));        
        assertThat(response.getEmployee().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2019, 9, 24, 05, 8, 45) )));  
        assertThat(response.getEmployee().getCreatedDateUTC(), is(equalTo(LocalDateTime.of(2019, 9, 22, 23, 58, 23) )));  
       
        //System.out.println(response.toString());
    }

    @Test
    public void updateEmployeeTest() throws IOException {
        System.out.println("@Test UK Payroll - updateEmployeeTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        Employee employee = new Employee();
        Address address = new Address();
        address.setAddressLine1("101 Green St");
        address.setCity("San Francisco");
        address.setPostCode("4351");
        employee.setAddress(address);
        employee.setFirstName("Mike");
        employee.setLastName("Johntzxzpxhmkgson");
        employee.setDateOfBirth(LocalDate.now());
        EmployeeObject response = payrollNzApi.updateEmployee(accessToken, xeroTenantId, employeeId, employee, null);
        
         assertThat(response.getEmployee().getEmployeeID(), is(equalTo(UUID.fromString("68342973-c405-4b86-b5d3-d7b877c27995"))));
         assertThat(response.getEmployee().getFirstName(), is(equalTo("Tony")));
         assertThat(response.getEmployee().getLastName(), is(equalTo("Starkgtrzgquusrson")));
         assertThat(response.getEmployee().getDateOfBirth(), is(equalTo(LocalDate.of(1999, 01, 01))));
         assertThat(response.getEmployee().getAddress().getAddressLine1(), is(equalTo("101 Green St")));
         assertThat(response.getEmployee().getAddress().getCity(), is(equalTo("San Francisco")));
         assertThat(response.getEmployee().getAddress().getPostCode(), is(equalTo("4432")));
         assertThat(response.getEmployee().getEmail(), is(equalTo("58315@starkindustries.com")));
         assertThat(response.getEmployee().getGender() , is(equalTo(com.xero.models.payrollnz.Employee.GenderEnum.M)));
         assertThat(response.getEmployee().getStartDate(), is(equalTo(LocalDate.of(2019, 02, 07))));
         assertThat(response.getEmployee().getPayrollCalendarID(), is(equalTo(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"))));  
         assertThat(response.getEmployee().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 8, 24, 20, 29, 43) )));  
         assertThat(response.getEmployee().getCreatedDateUTC(), is(equalTo(LocalDateTime.of(2019, 9, 22, 23, 58, 23) )));  
    
         //System.out.println(response.toString());
    }
}
