package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrolluk.*;
import com.xero.models.payrolluk.Employee.GenderEnum;

import org.threeten.bp.*;
import java.io.IOException;
import java.util.UUID;

public class PayrollUkApiEmployeeTest {

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
		defaultClient = new ApiClient("http://127.0.0.1:4015",null,null,null,null);
        payrollUkApi = PayrollUkApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollUkApi = null;
        defaultClient = null;
	}

	@Test
    public void getEmployeesTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeesTest");
       
        String filter = "";
        Employees response =  payrollUkApi.getEmployees(accessToken, xeroTenantId, filter,1);
        
        assertThat(response.getEmployees().get(0).getEmployeeID(), is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
        assertThat(response.getEmployees().get(0).getFirstName(), is(equalTo("Jack")));
        assertThat(response.getEmployees().get(0).getLastName(), is(equalTo("Allan")));
        assertThat(response.getEmployees().get(0).getDateOfBirth(), is(equalTo(LocalDate.of(1987, 12, 23))));
        assertThat(response.getEmployees().get(0).getAddress().getAddressLine1(), is(equalTo("171 Midsummer Boulevard")));
        assertThat(response.getEmployees().get(0).getAddress().getCity(), is(equalTo("Milton Keynes")));
        assertThat(response.getEmployees().get(0).getAddress().getPostCode(), is(equalTo("MK9 1EB")));
        assertThat(response.getEmployees().get(0).getAddress().getCountryName(), is(equalTo("UNITED KINGDOM")));
        assertThat(response.getEmployees().get(0).getGender() , is(equalTo(com.xero.models.payrolluk.Employee.GenderEnum.M)));
        assertThat(response.getEmployees().get(0).getStartDate() , is(equalTo(LocalDate.of(2020, 02, 03))));
        assertThat(response.getEmployees().get(0).getPayrollCalendarID(), is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));        
        assertThat(response.getEmployees().get(0).getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 02, 13, 16, 23, 31) )));  
        assertThat(response.getEmployees().get(0).getCreatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 02, 10, 10, 00, 24) )));  
        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeTest() throws IOException {
        System.out.println("@Test UK Payroll - createEmployeeTest");
        
        Employee employee = new Employee();
        Address address = new Address();
        address.setAddressLine1("101 Green St");
        address.setCity("Rangiora");
        address.setPostCode("SW6 6EY");
        address.setCountryName("UNITED KINGDOM");
        employee.setAddress(address);
        employee.setEmployeeID(UUID.randomUUID());
        employee.setFirstName("Adam");
        employee.setLastName("Adamson");
        employee.setDateOfBirth(LocalDate.of(2019, 7, 12));
        employee.setTitle("Mrs");
        employee.setGender(GenderEnum.M);
        employee.email("test@test.com");
        employee.setPhoneNumber("415-555-1212");
        employee.setStartDate(LocalDate.of(2023, 7, 12));
        employee.setEndDate(LocalDate.of(2024, 7, 12));
        System.out.println(employee);
        EmployeeObject response = payrollUkApi.createEmployee(accessToken, xeroTenantId, employee, null);
        
        assertThat(response.getEmployee().getEmployeeID(), is(equalTo(UUID.fromString("316146c7-26a4-4065-b9bd-346d0557ea96"))));
        assertThat(response.getEmployee().getTitle(), is(equalTo("Mr")));
        assertThat(response.getEmployee().getFirstName(), is(equalTo("Mike")));
        assertThat(response.getEmployee().getLastName(), is(equalTo("Fancy")));
        assertThat(response.getEmployee().getDateOfBirth(), is(equalTo(LocalDate.of(1999, 01, 01))));
        assertThat(response.getEmployee().getAddress().getAddressLine1(), is(equalTo("101 Green St")));
        assertThat(response.getEmployee().getAddress().getCity(), is(equalTo("San Francisco")));
        assertThat(response.getEmployee().getAddress().getPostCode(), is(equalTo("6TGR4F")));
        assertThat(response.getEmployee().getEmail(), is(equalTo("mike@starkindustries.com")));
        assertThat(response.getEmployee().getGender() , is(equalTo(com.xero.models.payrolluk.Employee.GenderEnum.M)));
        assertThat(response.getEmployee().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 03, 25, 03, 12, 10) )));  
        assertThat(response.getEmployee().getCreatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 03, 25, 03, 12, 10) )));  
      
        //System.out.println(response.toString());
    }

    @Test
    public void getEmployeeTest() throws IOException {
        System.out.println("@Test UK Payroll - getEmployeeTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeObject response = payrollUkApi.getEmployee(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getEmployee().getEmployeeID(), is(equalTo(UUID.fromString("d17e008e-3381-45c0-b50c-2fab7757e503"))));
        assertThat(response.getEmployee().getTitle(), is(equalTo("Mr.")));
        assertThat(response.getEmployee().getFirstName(), is(equalTo("Edgar")));
        assertThat(response.getEmployee().getLastName(), is(equalTo("Allan Po")));
        assertThat(response.getEmployee().getDateOfBirth(), is(equalTo(LocalDate.of(1985, 03, 24))));
        assertThat(response.getEmployee().getAddress().getAddressLine1(), is(equalTo("171 Midsummer")));
        assertThat(response.getEmployee().getAddress().getCity(), is(equalTo("Milton Keyness")));
        assertThat(response.getEmployee().getAddress().getPostCode(), is(equalTo("MK9 1EB")));
        assertThat(response.getEmployee().getAddress().getCountryName(), is(equalTo("UNITED KINGDOM")));
        assertThat(response.getEmployee().getGender() , is(equalTo(com.xero.models.payrolluk.Employee.GenderEnum.M)));
        assertThat(response.getEmployee().getPayrollCalendarID(), is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
        assertThat(response.getEmployee().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2017, 05, 12, 10, 00 ,24) )));  
        assertThat(response.getEmployee().getCreatedDateUTC(), is(equalTo(LocalDateTime.of(2017, 05, 12, 10, 00, 24) )));  
        assertThat(response.getEmployee().getNationalInsuranceNumber(), is(equalTo("AB123456C")));
        //System.out.println(response.toString());
    }

    @Test
    public void updateEmployeeTest() throws IOException {
        System.out.println("@Test UK Payroll - updateEmployeeTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        Employee employee = new Employee();
        employee.setEmployeeID(UUID.randomUUID());
        employee.setFirstName("Adam");
        employee.setLastName("Adamson");
        employee.setDateOfBirth(LocalDate.of(2019, 7, 12));
        employee.setTitle("Mrs");
        employee.setGender(GenderEnum.M);
        employee.email("test@test.com");
        employee.setPhoneNumber("415-555-1212");
        employee.setStartDate(LocalDate.of(2023, 7, 12));
        employee.setEndDate(LocalDate.of(2024, 7, 12));
        Address address = new Address();
        address.setAddressLine1("101 Green St");
        address.setCity("Rangiora");
        address.setPostCode("SW6 6EY");
        address.setCountryName("UNITED KINGDOM");
        employee.setAddress(address);

        EmployeeObject response = payrollUkApi.updateEmployee(accessToken, xeroTenantId, employeeId, employee, null);
        
        assertThat(response.getEmployee().getEmployeeID(), is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
        assertThat(response.getEmployee().getTitle(), is(equalTo("Mr")));
        assertThat(response.getEmployee().getFirstName(), is(equalTo("Mike")));
        assertThat(response.getEmployee().getLastName(), is(equalTo("Johnllsbkrhwopson")));
        assertThat(response.getEmployee().getDateOfBirth(), is(equalTo(LocalDate.of(1999, 01, 01))));
        assertThat(response.getEmployee().getAddress().getAddressLine1(), is(equalTo("101 Green St")));
        assertThat(response.getEmployee().getAddress().getCity(), is(equalTo("San Francisco")));
        assertThat(response.getEmployee().getAddress().getPostCode(), is(equalTo("6TGR4F")));
        assertThat(response.getEmployee().getEmail(), is(equalTo("84044@starkindustries.com")));
        assertThat(response.getEmployee().getGender() , is(equalTo(com.xero.models.payrolluk.Employee.GenderEnum.M)));
        assertThat(response.getEmployee().getStartDate(), is(equalTo(LocalDate.of(2020, 02, 03))));
        assertThat(response.getEmployee().getPayrollCalendarID(), is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));  
        assertThat(response.getEmployee().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 03, 25, 17, 03, 50) )));  
        assertThat(response.getEmployee().getCreatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 02, 10, 10, 00, 24) )));  
        //System.out.println(response.toString());
    }
}
