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

public class PayrollNzApiPayRunCalendarsTest {

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
    public void getPayRunCalendarsTest() throws IOException {
        System.out.println("@Test NZ Payroll - getPayRunCalendarsTest");
       
        int page = 1;
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        PayRunCalendars response = payrollNzApi.getPayRunCalendars(accessToken, xeroTenantId, page);
        
        assertThat(response.getPayRunCalendars().get(0).getPayrollCalendarID(),is(equalTo(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"))));
        assertThat(response.getPayRunCalendars().get(0).getCalendarType(), is(equalTo(com.xero.models.payrollnz.CalendarType.WEEKLY)));
        assertThat(response.getPayRunCalendars().get(0).getName() , is(equalTo("Weekly")));
        assertThat(response.getPayRunCalendars().get(0).getPeriodStartDate(),  is(equalTo(LocalDate.of(2019, 7, 8))));
        assertThat(response.getPayRunCalendars().get(0).getPeriodEndDate(),  is(equalTo(LocalDate.of(2019, 7, 14))));
        assertThat(response.getPayRunCalendars().get(0).getPaymentDate(),  is(equalTo(LocalDate.of(2019, 7, 16))));
        assertThat(response.getPayRunCalendars().get(0).getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2019, 9, 24, 5, 8 ,45))));  
      
        //System.out.println(response.toString());
    }

    @Test
    public void getPayRunCalendarTest() throws IOException {
        System.out.println("@Test NZ Payroll - getPayRunCalendarTest");
       
        int page = 1;
        UUID payRunCalendarID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        PayRunCalendarObject response = payrollNzApi.getPayRunCalendar(accessToken, xeroTenantId, payRunCalendarID);
        
        assertThat(response.getPayRunCalendar().getPayrollCalendarID(),is(equalTo(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"))));
        assertThat(response.getPayRunCalendar().getCalendarType(), is(equalTo(com.xero.models.payrollnz.CalendarType.WEEKLY)));
        assertThat(response.getPayRunCalendar().getName() , is(equalTo("Weekly")));
        assertThat(response.getPayRunCalendar().getPeriodStartDate(),  is(equalTo(LocalDate.of(2019, 7, 8))));
        assertThat(response.getPayRunCalendar().getPeriodEndDate(),   is(equalTo(LocalDate.of(2019, 7, 14))));
        assertThat(response.getPayRunCalendar().getPaymentDate(),   is(equalTo(LocalDate.of(2019, 7, 16))));
        assertThat(response.getPayRunCalendar().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2019, 9, 24, 5, 8 ,45))));

        //System.out.println(response.toString());
    }

    @Test
    public void createPayRunCalendarTest() throws IOException {
        System.out.println("@Test NZ Payroll - createPayRunCalendarTest");
       
        PayRunCalendar payRunCalendar = new PayRunCalendar();
        payRunCalendar.setName("My Weekly Cal");
        payRunCalendar.setCalendarType(com.xero.models.payrollnz.CalendarType.WEEKLY);
        payRunCalendar.setPeriodStartDate(LocalDate.of(2020, 05, 01));
        payRunCalendar.setPaymentDate(LocalDate.of(2020, 05, 15));
        PayRunCalendarObject response = payrollNzApi.createPayRunCalendar(accessToken, xeroTenantId, payRunCalendar, null);
        
        assertThat(response.getPayRunCalendar().getPayrollCalendarID(),is(equalTo(UUID.fromString("54e9706a-c4e8-45ff-9c63-6fcac7ee7cde"))));
        assertThat(response.getPayRunCalendar().getCalendarType(), is(equalTo(com.xero.models.payrollnz.CalendarType.WEEKLY)));
        assertThat(response.getPayRunCalendar().getName() , is(equalTo("My Weekly Cal")));
        assertThat(response.getPayRunCalendar().getPeriodStartDate(),  is(equalTo(LocalDate.of(2020, 05, 01))));
        assertThat(response.getPayRunCalendar().getPeriodEndDate(),  is(equalTo(LocalDate.of(2020, 05, 07))));
        assertThat(response.getPayRunCalendar().getPaymentDate(),  is(equalTo(LocalDate.of(2020, 05, 15))));
        assertThat(response.getPayRunCalendar().getUpdatedDateUTC(), is(equalTo(LocalDateTime.of(2020, 8, 26, 23, 54, 49).plusNanos(486332200))));  

        //System.out.println(response.toString());
    }
}
