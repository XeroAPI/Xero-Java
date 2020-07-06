package com.xero.api.client;


import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.xero.api.ApiClient;
import com.xero.models.payrolluk.*;



import org.threeten.bp.*;
import java.io.IOException;

import java.io.IOException;


import java.util.UUID;

public class PayrollUkApiPayRunCalendarsTest {

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
    defaultClient =
        new ApiClient(
            "https://xero-payroll-uk.getsandbox.com:443/payroll.xro/2.0", null, null, null, null);
    payrollUkApi = PayrollUkApi.getInstance(defaultClient);
  }

  public void tearDown() {
    payrollUkApi = null;
    defaultClient = null;
  }

  @Test
  public void getPayRunCalendarsTest() throws IOException {
    System.out.println("@Test UK Payroll - getPayRunCalendarsTest");

    int page = 1;
    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    PayRunCalendars response = payrollUkApi.getPayRunCalendars(accessToken, xeroTenantId, page);

    assertThat(
        response.getPayRunCalendars().get(0).getPayrollCalendarID(),
        is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
    assertThat(
        response.getPayRunCalendars().get(0).getCalendarType(),
        is(equalTo(com.xero.models.payrolluk.PayRunCalendar.CalendarTypeEnum.WEEKLY)));
    assertThat(response.getPayRunCalendars().get(0).getName(), is(equalTo("Weekly ")));
    assertThat(
        response.getPayRunCalendars().get(0).getPeriodStartDate(),
        is(equalTo(LocalDate.of(2020, 02, 10))));
    assertThat(
        response.getPayRunCalendars().get(0).getPeriodEndDate(),
        is(equalTo(LocalDate.of(2020, 02, 16))));
    assertThat(
        response.getPayRunCalendars().get(0).getPaymentDate(),
        is(equalTo(LocalDate.of(2020, 02, 17))));
    assertThat(
        response.getPayRunCalendars().get(0).getUpdatedDateUTC(),
        is(equalTo(LocalDateTime.of(2020, 02, 13, 16, 53, 54))));

    // System.out.println(response.toString());
  }

  @Test
  public void getPayRunCalendarTest() throws IOException {
    System.out.println("@Test UK Payroll - getPayRunCalendarTest");

    int page = 1;
    UUID payRunCalendarID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    PayRunCalendarObject response =
        payrollUkApi.getPayRunCalendar(accessToken, xeroTenantId, payRunCalendarID);

    assertThat(
        response.getPayRunCalendar().getPayrollCalendarID(),
        is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
    assertThat(
        response.getPayRunCalendar().getCalendarType(),
        is(equalTo(com.xero.models.payrolluk.PayRunCalendar.CalendarTypeEnum.WEEKLY)));
    assertThat(response.getPayRunCalendar().getName(), is(equalTo("Weekly ")));
    assertThat(
        response.getPayRunCalendar().getPeriodStartDate(), is(equalTo(LocalDate.of(2020, 02, 10))));
    assertThat(
        response.getPayRunCalendar().getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 02, 16))));
    assertThat(
        response.getPayRunCalendar().getPaymentDate(), is(equalTo(LocalDate.of(2020, 02, 17))));
    assertThat(
        response.getPayRunCalendar().getUpdatedDateUTC(),
        is(equalTo(LocalDateTime.of(2020, 02, 13, 16, 53, 54))));

    // System.out.println(response.toString());
  }

  @Test
  public void createPayRunCalendarTest() throws IOException {
    System.out.println("@Test UK Payroll - createPayRunCalendarTest");

    PayRunCalendar payRunCalendar = new PayRunCalendar();
    PayRunCalendarObject response =
        payrollUkApi.createPayRunCalendar(accessToken, xeroTenantId, payRunCalendar);

    assertThat(
        response.getPayRunCalendar().getPayrollCalendarID(),
        is(equalTo(UUID.fromString("5f29322d-9123-49be-bef0-9b14c35653d1"))));
    assertThat(
        response.getPayRunCalendar().getCalendarType(),
        is(equalTo(com.xero.models.payrolluk.PayRunCalendar.CalendarTypeEnum.WEEKLY)));
    assertThat(response.getPayRunCalendar().getName(), is(equalTo("My Weekly Cal")));
    assertThat(
        response.getPayRunCalendar().getPeriodStartDate(), is(equalTo(LocalDate.of(2020, 05, 01))));
    assertThat(
        response.getPayRunCalendar().getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 05, 07))));
    assertThat(
        response.getPayRunCalendar().getPaymentDate(), is(equalTo(LocalDate.of(2020, 05, 15))));
    assertThat(
        response.getPayRunCalendar().getUpdatedDateUTC(),
        is(equalTo(LocalDateTime.of(2020, 04, 13, 17, 01, 06).plusNanos(494433100))));

    // System.out.println(response.toString());
  }
}
