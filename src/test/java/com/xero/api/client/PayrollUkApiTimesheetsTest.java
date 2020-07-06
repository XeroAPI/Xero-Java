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

public class PayrollUkApiTimesheetsTest {

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
  public void getTimesheetsTest() throws IOException {
    System.out.println("@Test UK Payroll - getTimesheetsTest");

    int page = 1;
    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    UUID payrollCalendarId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    Timesheets response =
        payrollUkApi.getTimesheets(accessToken, xeroTenantId, page, employeeId, payrollCalendarId);

    assertThat(
        response.getTimesheets().get(0).getTimesheetID(),
        is(equalTo(UUID.fromString("0c94d453-3d8c-4167-8c25-b4025121d18b"))));
    assertThat(
        response.getTimesheets().get(0).getPayrollCalendarID(),
        is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
    assertThat(
        response.getTimesheets().get(0).getEmployeeID(),
        is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
    assertThat(
        response.getTimesheets().get(0).getStartDate(), is(equalTo(LocalDate.of(2020, 04, 13))));
    assertThat(
        response.getTimesheets().get(0).getEndDate(), is(equalTo(LocalDate.of(2020, 04, 19))));
    assertThat(
        response.getTimesheets().get(0).getStatus(),
        is(equalTo(com.xero.models.payrolluk.Timesheet.StatusEnum.APPROVED)));
    assertThat(response.getTimesheets().get(0).getTotalHours(), is(equalTo(16.0)));
    assertThat(
        response.getTimesheets().get(0).getUpdatedDateUTC(),
        is(equalTo(LocalDateTime.of(2020, 04, 13, 21, 53, 39))));

    // System.out.println(response.toString());
  }

  @Test
  public void getTimesheetTest() throws IOException {
    System.out.println("@Test UK Payroll - getTimesheetTest");

    int page = 1;
    UUID timesheetID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    TimesheetObject response = payrollUkApi.getTimesheet(accessToken, xeroTenantId, timesheetID);

    assertThat(
        response.getTimesheet().getTimesheetID(),
        is(equalTo(UUID.fromString("25c59963-5df2-43c3-88f6-25a3caa63084"))));
    assertThat(
        response.getTimesheet().getPayrollCalendarID(),
        is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
    assertThat(
        response.getTimesheet().getEmployeeID(),
        is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
    assertThat(response.getTimesheet().getStartDate(), is(equalTo(LocalDate.of(2020, 04, 13))));
    assertThat(response.getTimesheet().getEndDate(), is(equalTo(LocalDate.of(2020, 04, 19))));
    assertThat(
        response.getTimesheet().getStatus(),
        is(equalTo(com.xero.models.payrolluk.Timesheet.StatusEnum.DRAFT)));
    assertThat(response.getTimesheet().getTotalHours(), is(equalTo(14.0)));
    assertThat(
        response.getTimesheet().getUpdatedDateUTC(),
        is(equalTo(LocalDateTime.of(2020, 04, 14, 16, 31, 35))));
    assertThat(
        response.getTimesheet().getTimesheetLines().get(0).getTimesheetLineID(),
        is(equalTo(UUID.fromString("6cc1e5c3-f080-4dbc-a82b-bea256ef939f"))));
    assertThat(
        response.getTimesheet().getTimesheetLines().get(0).getEarningsRateID(),
        is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
    assertThat(
        response.getTimesheet().getTimesheetLines().get(0).getDate(),
        is(equalTo(LocalDate.of(2020, 04, 13))));
    assertThat(
        response.getTimesheet().getTimesheetLines().get(0).getNumberOfUnits(), is(equalTo(8.0)));

    // System.out.println(response.toString());
  }

  @Test
  public void createTimesheetTest() throws IOException {
    System.out.println("@Test UK Payroll - createTimesheetTest");

    int page = 1;
    UUID timesheetID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    Timesheet timesheet = new Timesheet();
    TimesheetObject response = payrollUkApi.createTimesheet(accessToken, xeroTenantId, timesheet);

    assertThat(
        response.getTimesheet().getTimesheetID(),
        is(equalTo(UUID.fromString("88d2038a-06f7-4b8a-bdab-809804c0aa1d"))));
    assertThat(
        response.getTimesheet().getPayrollCalendarID(),
        is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
    assertThat(
        response.getTimesheet().getEmployeeID(),
        is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
    assertThat(response.getTimesheet().getStartDate(), is(equalTo(LocalDate.of(2020, 04, 13))));
    assertThat(response.getTimesheet().getEndDate(), is(equalTo(LocalDate.of(2020, 04, 19))));
    assertThat(
        response.getTimesheet().getStatus(),
        is(equalTo(com.xero.models.payrolluk.Timesheet.StatusEnum.DRAFT)));
    assertThat(response.getTimesheet().getTotalHours(), is(equalTo(14.0)));
    assertThat(
        response.getTimesheet().getUpdatedDateUTC(),
        is(equalTo(LocalDateTime.of(2020, 04, 21, 03, 07, 36).plusNanos(813283400))));
    assertThat(
        response.getTimesheet().getTimesheetLines().get(0).getTimesheetLineID(),
        is(equalTo(UUID.fromString("03060d0c-b14a-4339-aced-51cb2e5313b7"))));
    assertThat(
        response.getTimesheet().getTimesheetLines().get(0).getEarningsRateID(),
        is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
    assertThat(
        response.getTimesheet().getTimesheetLines().get(0).getDate(),
        is(equalTo(LocalDate.of(2020, 04, 15))));
    assertThat(
        response.getTimesheet().getTimesheetLines().get(0).getNumberOfUnits(), is(equalTo(6.0)));

    // System.out.println(response.toString());
  }

  @Test
  public void createTimesheetLineTest() throws IOException {
    System.out.println("@Test UK Payroll - createTimesheetLineTest");

    UUID timesheetID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    TimesheetLine timesheetLine = new TimesheetLine();
    TimesheetLineObject response =
        payrollUkApi.createTimesheetLine(accessToken, xeroTenantId, timesheetID, timesheetLine);

    assertThat(
        response.getTimesheetLine().getTimesheetLineID(),
        is(equalTo(UUID.fromString("56fce87e-7f0d-4c19-8f74-7f5656651c81"))));
    assertThat(response.getTimesheetLine().getDate(), is(equalTo(LocalDate.of(2020, 04, 14))));
    assertThat(
        response.getTimesheetLine().getEarningsRateID(),
        is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
    assertThat(response.getTimesheetLine().getNumberOfUnits(), is(equalTo(1.0)));

    // System.out.println(response.toString());
  }

  @Test
  public void updateTimesheetLineTest() throws IOException {
    System.out.println("@Test UK Payroll - updateTimesheetLineTest");

    UUID timesheetID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    UUID timesheetLineID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    TimesheetLine timesheetLine = new TimesheetLine();
    TimesheetLineObject response =
        payrollUkApi.updateTimesheetLine(
            accessToken, xeroTenantId, timesheetID, timesheetLineID, timesheetLine);

    assertThat(
        response.getTimesheetLine().getTimesheetLineID(),
        is(equalTo(UUID.fromString("c88edcad-af32-4536-a682-9a4772c21c8d"))));
    assertThat(response.getTimesheetLine().getDate(), is(equalTo(LocalDate.of(2020, 04, 14))));
    assertThat(
        response.getTimesheetLine().getEarningsRateID(),
        is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
    assertThat(response.getTimesheetLine().getNumberOfUnits(), is(equalTo(2.0)));

    // System.out.println(response.toString());
  }

  /* Can't test approve or revert - they are POST with no body and sandbox API expects body to not be empty.
      @Test
      public void approveTimesheetTest() throws IOException {
          System.out.println("@Test UK Payroll - approveTimesheetTest");

          UUID timesheetID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
          //TimesheetObject response = payrollUkApi.approveTimesheet(accessToken, xeroTenantId, timesheetID);

          // assertThat(response.getTimesheetLine().getTimesheetLineID(),is(equalTo(UUID.fromString("56fce87e-7f0d-4c19-8f74-7f5656651c81"))));
          // assertThat(response.getTimesheetLine().getDate(), is(equalTo(LocalDate.of(2020, 04, 14))));
          // assertThat(response.getTimesheetLine().getEarningsRateID(),is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
          // assertThat(response.getTimesheetLine().getNumberOfUnits(), is(equalTo(1.0)));

          //System.out.println(response.toString());
      }

      @Test
      public void revertTimesheetTest() throws IOException {
          System.out.println("@Test UK Payroll - revertTimesheetTest");

          UUID timesheetID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
          TimesheetObject response = payrollUkApi.revertTimesheet(accessToken, xeroTenantId, timesheetID);

          // assertThat(response.getTimesheetLine().getTimesheetLineID(),is(equalTo(UUID.fromString("56fce87e-7f0d-4c19-8f74-7f5656651c81"))));
          // assertThat(response.getTimesheetLine().getDate(), is(equalTo(LocalDate.of(2020, 04, 14))));
          // assertThat(response.getTimesheetLine().getEarningsRateID(),is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
          // assertThat(response.getTimesheetLine().getNumberOfUnits(), is(equalTo(1.0)));
          //System.out.println(response.toString());
      }
  */

}
