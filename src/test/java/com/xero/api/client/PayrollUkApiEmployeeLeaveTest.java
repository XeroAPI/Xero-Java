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

public class PayrollUkApiEmployeeLeaveTest {

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
  public void getEmployeeLeavesTest() throws IOException {
    System.out.println("@Test UK Payroll - getEmployeeLeavesTest");

    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    EmployeeLeaves response = payrollUkApi.getEmployeeLeaves(accessToken, xeroTenantId, employeeId);

    assertThat(
        response.getLeave().get(0).getLeaveID(),
        is(equalTo(UUID.fromString("384bf62a-5884-484d-b675-a3ad9c662f8e"))));
    assertThat(
        response.getLeave().get(0).getLeaveTypeID(),
        is(equalTo(UUID.fromString("ed08dffe-788e-4b24-9630-f0fa2f4d164c"))));
    assertThat(response.getLeave().get(0).getDescription(), is(equalTo("Vacation to Majorca")));
    assertThat(response.getLeave().get(0).getStartDate(), is(equalTo(LocalDate.of(2020, 02, 17))));
    assertThat(response.getLeave().get(0).getEndDate(), is(equalTo(LocalDate.of(2020, 02, 21))));
    assertThat(
        response.getLeave().get(0).getPeriods().get(0).getPeriodStartDate(),
        is(equalTo(LocalDate.of(2020, 02, 17))));
    assertThat(
        response.getLeave().get(0).getPeriods().get(0).getPeriodEndDate(),
        is(equalTo(LocalDate.of(2020, 02, 23))));
    assertThat(
        response.getLeave().get(0).getPeriods().get(0).getNumberOfUnits(), is(equalTo(40.0)));
    assertThat(
        response.getLeave().get(0).getPeriods().get(0).getPeriodStatus(),
        is(equalTo(com.xero.models.payrolluk.LeavePeriod.PeriodStatusEnum.APPROVED)));
    assertThat(
        response.getLeave().get(0).getUpdatedDateUTC(),
        is(equalTo(LocalDateTime.of(2020, 02, 10, 10, 15, 53))));

    // System.out.println(response.toString());
  }

  @Test
  public void createEmployeeLeaveTest() throws IOException {
    System.out.println("@Test UK Payroll - createEmployeeLeaveTest");

    EmployeeLeave employeeLeave = new EmployeeLeave();
    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");

    EmployeeLeaveObject response =
        payrollUkApi.createEmployeeLeave(accessToken, xeroTenantId, employeeId, employeeLeave);

    assertThat(
        response.getLeave().getLeaveID(),
        is(equalTo(UUID.fromString("bdfedc6b-363b-4801-8ba6-9a074d400b31"))));
    assertThat(
        response.getLeave().getLeaveTypeID(),
        is(equalTo(UUID.fromString("1d2778ee-86ea-45c0-bbf8-1045485f6b3f"))));
    assertThat(response.getLeave().getDescription(), is(equalTo("Creating a Desription")));
    assertThat(response.getLeave().getStartDate(), is(equalTo(LocalDate.of(2020, 03, 24))));
    assertThat(response.getLeave().getEndDate(), is(equalTo(LocalDate.of(2020, 03, 26))));
    assertThat(
        response.getLeave().getPeriods().get(0).getPeriodStartDate(),
        is(equalTo(LocalDate.of(2020, 03, 23))));
    assertThat(
        response.getLeave().getPeriods().get(0).getPeriodEndDate(),
        is(equalTo(LocalDate.of(2020, 03, 29))));
    assertThat(response.getLeave().getPeriods().get(0).getNumberOfUnits(), is(equalTo(24.0)));
    assertThat(
        response.getLeave().getPeriods().get(0).getPeriodStatus(),
        is(equalTo(com.xero.models.payrolluk.LeavePeriod.PeriodStatusEnum.APPROVED)));
    assertThat(
        response.getLeave().getUpdatedDateUTC(),
        is(equalTo(LocalDateTime.of(2020, 03, 31, 04, 32, 56, 953785700))));

    // System.out.println(response.toString());
  }

  @Test
  public void getEmployeeLeaveTest() throws IOException {
    System.out.println("@Test UK Payroll - getEmployeeLeaveTest");

    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    UUID leaveId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    EmployeeLeaveObject response =
        payrollUkApi.getEmployeeLeave(accessToken, xeroTenantId, employeeId, leaveId);

    assertThat(
        response.getLeave().getLeaveID(),
        is(equalTo(UUID.fromString("384bf62a-5884-484d-b675-a3ad9c662f8e"))));
    assertThat(
        response.getLeave().getLeaveTypeID(),
        is(equalTo(UUID.fromString("ed08dffe-788e-4b24-9630-f0fa2f4d164c"))));
    assertThat(response.getLeave().getDescription(), is(equalTo("Vacation to Majorca")));
    assertThat(response.getLeave().getStartDate(), is(equalTo(LocalDate.of(2020, 02, 17))));
    assertThat(response.getLeave().getEndDate(), is(equalTo(LocalDate.of(2020, 02, 21))));
    assertThat(
        response.getLeave().getPeriods().get(0).getPeriodStartDate(),
        is(equalTo(LocalDate.of(2020, 02, 17))));
    assertThat(
        response.getLeave().getPeriods().get(0).getPeriodEndDate(),
        is(equalTo(LocalDate.of(2020, 02, 23))));
    assertThat(response.getLeave().getPeriods().get(0).getNumberOfUnits(), is(equalTo(40.0)));
    assertThat(
        response.getLeave().getPeriods().get(0).getPeriodStatus(),
        is(equalTo(com.xero.models.payrolluk.LeavePeriod.PeriodStatusEnum.APPROVED)));
    assertThat(
        response.getLeave().getUpdatedDateUTC(),
        is(equalTo(LocalDateTime.of(2020, 02, 10, 10, 15, 53))));

    // System.out.println(response.toString());
  }

  @Test
  public void updateEmployeeLeaveTest() throws IOException {
    System.out.println("@Test UK Payroll - updateEmployeeLeaveTest");

    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    UUID leaveId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");

    EmployeeLeave employeeLeave = new EmployeeLeave();
    EmployeeLeaveObject response =
        payrollUkApi.updateEmployeeLeave(
            accessToken, xeroTenantId, employeeId, leaveId, employeeLeave);

    assertThat(
        response.getLeave().getLeaveID(),
        is(equalTo(UUID.fromString("8340b795-50c1-428e-9fda-90badf081ab4"))));
    assertThat(
        response.getLeave().getLeaveTypeID(),
        is(equalTo(UUID.fromString("ed08dffe-788e-4b24-9630-f0fa2f4d164c"))));
    assertThat(response.getLeave().getDescription(), is(equalTo("Creating a Description")));
    assertThat(response.getLeave().getStartDate(), is(equalTo(LocalDate.of(2020, 04, 24))));
    assertThat(response.getLeave().getEndDate(), is(equalTo(LocalDate.of(2020, 04, 26))));
    assertThat(
        response.getLeave().getPeriods().get(0).getPeriodStartDate(),
        is(equalTo(LocalDate.of(2020, 04, 20))));
    assertThat(
        response.getLeave().getPeriods().get(0).getPeriodEndDate(),
        is(equalTo(LocalDate.of(2020, 04, 26))));
    assertThat(response.getLeave().getPeriods().get(0).getNumberOfUnits(), is(equalTo(1.0)));
    assertThat(
        response.getLeave().getPeriods().get(0).getPeriodStatus(),
        is(equalTo(com.xero.models.payrolluk.LeavePeriod.PeriodStatusEnum.APPROVED)));
    assertThat(
        response.getLeave().getUpdatedDateUTC(),
        is(equalTo(LocalDateTime.of(2020, 04, 17, 18, 18, 30).plusNanos(614537000))));

    // System.out.println(response.toString());
  }
}
