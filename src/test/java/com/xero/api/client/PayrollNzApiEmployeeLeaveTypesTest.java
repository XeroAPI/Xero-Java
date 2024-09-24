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

public class PayrollNzApiEmployeeLeaveTypesTest {

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
    public void getEmployeeLeaveTypesTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEmployeeLeaveTypesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        LocalDate startDate = LocalDate.of(2020, Month.MARCH, 30);
        LocalDate endDate = LocalDate.of(2020, Month.MARCH, 30);
        EmployeeLeaveTypes response = payrollNzApi.getEmployeeLeaveTypes(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getLeaveTypes().get(0).getLeaveTypeID(), is(equalTo(UUID.fromString("0441497f-5dc7-4cd3-a90d-f2e07e21b2a6"))));
        assertThat(response.getLeaveTypes().get(0).getScheduleOfAccrual(), is(equalTo(com.xero.models.payrollnz.EmployeeLeaveType.ScheduleOfAccrualEnum.PERCENTAGEOFGROSSEARNINGS)));
        assertThat(response.getLeaveTypes().get(0).getHoursAccruedAnnually(), is(equalTo(0.0)));
        assertThat(response.getLeaveTypes().get(0).getMaximumToAccrue(), is(equalTo(0.0)));
        assertThat(response.getLeaveTypes().get(0).getOpeningBalance(), is(equalTo(0.0)));
        assertThat(response.getLeaveTypes().get(0).getPercentageOfGrossEarnings(), is(equalTo(8.0)));
        assertThat(response.getLeaveTypes().get(0).getIncludeHolidayPayEveryPay(), is(equalTo(true)));

        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeeLeaveTypesTest() throws IOException {
        System.out.println("@Test NZ Payroll - createEmployeeLeaveTypesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeLeaveType employeeLeaveType  = new EmployeeLeaveType();
        EmployeeLeaveTypeObject response = payrollNzApi.createEmployeeLeaveType(accessToken, xeroTenantId, employeeId, employeeLeaveType, null);
        
        assertThat(response.getLeaveType().getLeaveTypeID(), is(equalTo(UUID.fromString("35da97ae-05b9-427f-9a98-69157ba42cec"))));
        assertThat(response.getLeaveType().getScheduleOfAccrual(), is(equalTo(com.xero.models.payrollnz.EmployeeLeaveType.ScheduleOfAccrualEnum.ANNUALLYAFTER6MONTHS)));
        assertThat(response.getLeaveType().getHoursAccruedAnnually(), is(equalTo(10.0)));
        assertThat(response.getLeaveType().getMaximumToAccrue(), is(equalTo(80.0)));
        assertThat(response.getLeaveType().getOpeningBalance(), is(equalTo(100.0)));
        assertThat(response.getLeaveType().getPercentageOfGrossEarnings(), is(equalTo(0.0)));

        //System.out.println(response.toString());
    }
}
