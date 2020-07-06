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

public class PayrollUkApiEmployeeStatutorySickLeaveTest {

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
  public void createEmployeeStatutorySickLeaveTest() throws IOException {
    System.out.println("@Test UK Payroll - createEmployeeStatutorySickLeaveTest");

    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    EmployeeStatutorySickLeave employeeStatutorySickLeave = new EmployeeStatutorySickLeave();
    EmployeeStatutorySickLeaveObject response =
        payrollUkApi.createEmployeeStatutorySickLeave(
            accessToken, xeroTenantId, employeeStatutorySickLeave);

    assertThat(
        response.getStatutorySickLeave().getStatutoryLeaveID(),
        is(equalTo(UUID.fromString("a2b5a1fb-ae21-47b4-876d-0b61fa6b37ab"))));
    assertThat(
        response.getStatutorySickLeave().getEmployeeID(),
        is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
    assertThat(
        response.getStatutorySickLeave().getLeaveTypeID(),
        is(equalTo(UUID.fromString("aab78802-e9d3-4bbd-bc87-df858054988f"))));
    assertThat(
        response.getStatutorySickLeave().getStartDate(), is(equalTo(LocalDate.of(2020, 04, 21))));
    assertThat(
        response.getStatutorySickLeave().getEndDate(), is(equalTo(LocalDate.of(2020, 04, 24))));
    assertThat(response.getStatutorySickLeave().getWorkPattern().get(0), is(equalTo("Monday")));
    assertThat(response.getStatutorySickLeave().getIsPregnancyRelated(), is(equalTo(false)));
    assertThat(response.getStatutorySickLeave().getSufficientNotice(), is(equalTo(true)));
    assertThat(response.getStatutorySickLeave().getIsEntitled(), is(equalTo(false)));
    assertThat(response.getStatutorySickLeave().getEntitlementWeeksRequested(), is(equalTo(0.8)));
    assertThat(response.getStatutorySickLeave().getEntitlementWeeksQualified(), is(equalTo(27.2)));
    assertThat(response.getStatutorySickLeave().getEntitlementWeeksRemaining(), is(equalTo(26.4)));
    assertThat(response.getStatutorySickLeave().getOverlapsWithOtherLeave(), is(equalTo(false)));
    assertThat(
        response.getStatutorySickLeave().getEntitlementFailureReasons().get(0),
        is(
            equalTo(
                com.xero.models.payrolluk.EmployeeStatutorySickLeave.EntitlementFailureReasonsEnum
                    .NOTQUALIFIEDINPREVIOUSPIW)));

    // System.out.println(response.toString());
  }

  @Test
  public void getEmployeeStatutorySickLeaveTest() throws IOException {
    System.out.println("@Test UK Payroll - getEmployeeStatutorySickLeaveTest");

    UUID statutorySickLeaveId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    EmployeeStatutorySickLeaveObject response =
        payrollUkApi.getEmployeeStatutorySickLeave(accessToken, xeroTenantId, statutorySickLeaveId);

    assertThat(
        response.getStatutorySickLeave().getStatutoryLeaveID(),
        is(equalTo(UUID.fromString("17f4c3c7-d65c-4572-9118-03798f26f813"))));
    assertThat(
        response.getStatutorySickLeave().getEmployeeID(),
        is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
    assertThat(
        response.getStatutorySickLeave().getLeaveTypeID(),
        is(equalTo(UUID.fromString("054a2b5e-fe51-4494-9486-3e3130323c69"))));
    assertThat(
        response.getStatutorySickLeave().getStartDate(), is(equalTo(LocalDate.of(2020, 03, 28))));
    assertThat(
        response.getStatutorySickLeave().getEndDate(), is(equalTo(LocalDate.of(2020, 04, 01))));
    assertThat(response.getStatutorySickLeave().getWorkPattern().get(0), is(equalTo("Monday")));
    assertThat(response.getStatutorySickLeave().getIsPregnancyRelated(), is(equalTo(false)));
    assertThat(response.getStatutorySickLeave().getSufficientNotice(), is(equalTo(true)));
    assertThat(response.getStatutorySickLeave().getIsEntitled(), is(equalTo(false)));
    assertThat(response.getStatutorySickLeave().getEntitlementWeeksRequested(), is(equalTo(0.6)));
    assertThat(response.getStatutorySickLeave().getEntitlementWeeksQualified(), is(equalTo(28.0)));
    assertThat(response.getStatutorySickLeave().getEntitlementWeeksRemaining(), is(equalTo(0.0)));
    assertThat(response.getStatutorySickLeave().getOverlapsWithOtherLeave(), is(equalTo(false)));
    assertThat(
        response.getStatutorySickLeave().getEntitlementFailureReasons().get(0),
        is(
            equalTo(
                com.xero.models.payrolluk.EmployeeStatutorySickLeave.EntitlementFailureReasonsEnum
                    .AWELOWERTHANLEL)));
    // System.out.println(response.toString());
  }
}
