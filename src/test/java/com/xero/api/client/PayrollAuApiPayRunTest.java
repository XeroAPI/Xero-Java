package com.xero.api.client;


import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.xero.api.ApiClient;
import com.xero.models.payrollau.*;



import org.threeten.bp.*;
import java.io.IOException;

import java.io.IOException;


import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class PayrollAuApiPayRunTest {

  ApiClient defaultClient;
  PayrollAuApi payrollAuApi;

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
            "https://xero-payroll-au.getsandbox.com:443/payroll.xro/1.0", null, null, null, null);
    payrollAuApi = PayrollAuApi.getInstance(defaultClient);
  }

  public void tearDown() {
    payrollAuApi = null;
    defaultClient = null;
  }

  @Test
  public void getPayRunsTest() throws IOException {
    System.out.println("@Test - getPayRunsTest");

    PayRuns response = payrollAuApi.getPayRuns(accessToken, xeroTenantId, null, null, null, null);

    assertThat(
        response.getPayRuns().get(0).getPayrollCalendarID(),
        is(equalTo(UUID.fromString("78bb86b9-e1ea-47ac-b75d-f087a81931de"))));
    assertThat(
        response.getPayRuns().get(0).getPayRunID(),
        is(equalTo(UUID.fromString("5de420bb-4ad2-405c-beb1-2610bcc2144e"))));
    assertThat(
        response.getPayRuns().get(0).getPayRunPeriodStartDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 01))));
    assertThat(
        response.getPayRuns().get(0).getPayRunPeriodEndDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 07))));
    assertThat(
        response.getPayRuns().get(0).getPayRunStatus(),
        is(equalTo(com.xero.models.payrollau.PayRunStatus.POSTED)));
    assertThat(
        response.getPayRuns().get(0).getPaymentDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 8))));
    assertThat(
        response.getPayRuns().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-11-13T02:09:30Z"))));
    assertThat(response.getPayRuns().get(0).getWages(), is(equalTo(200.0)));
    assertThat(response.getPayRuns().get(0).getDeductions(), is(equalTo(33.0)));
    assertThat(response.getPayRuns().get(0).getTax(), is(equalTo(78.0)));
    assertThat(response.getPayRuns().get(0).getNetPay(), is(equalTo(89.0)));
    assertThat(response.getPayRuns().get(0).getSuper(), is(equalTo(0.00)));
    assertThat(response.getPayRuns().get(0).getReimbursement(), is(equalTo(22.00)));
    // System.out.println(response.toString());
  }

  @Test
  public void getPayRunTest() throws IOException {
    System.out.println("@Test - getPayRunTest");

    UUID payRunId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    PayRuns response = payrollAuApi.getPayRun(accessToken, xeroTenantId, payRunId);

    assertThat(
        response.getPayRuns().get(0).getPayrollCalendarID(),
        is(equalTo(UUID.fromString("78bb86b9-e1ea-47ac-b75d-f087a81931de"))));
    assertThat(
        response.getPayRuns().get(0).getPayRunID(),
        is(equalTo(UUID.fromString("21d6317b-5319-4b3d-8d78-48904db6b665"))));
    assertThat(
        response.getPayRuns().get(0).getPayRunPeriodStartDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 1))));
    assertThat(
        response.getPayRuns().get(0).getPayRunPeriodEndDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 7))));
    assertThat(
        response.getPayRuns().get(0).getPayRunStatus(),
        is(equalTo(com.xero.models.payrollau.PayRunStatus.POSTED)));
    assertThat(
        response.getPayRuns().get(0).getPaymentDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 8))));
    assertThat(
        response.getPayRuns().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-11-14T00:42:35Z"))));
    assertThat(response.getPayRuns().get(0).getWages(), is(equalTo(205.4)));
    assertThat(response.getPayRuns().get(0).getDeductions(), is(equalTo(37.0)));
    assertThat(response.getPayRuns().get(0).getTax(), is(equalTo(0.0)));
    assertThat(response.getPayRuns().get(0).getSuper(), is(equalTo(0.0)));
    assertThat(response.getPayRuns().get(0).getReimbursement(), is(equalTo(77.0)));
    assertThat(response.getPayRuns().get(0).getNetPay(), is(equalTo(168.4)));
    assertThat(
        response.getPayRuns().get(0).getPayslips().get(0).getEmployeeID(),
        is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
    assertThat(
        response.getPayRuns().get(0).getPayslips().get(0).getPayslipID(),
        is(equalTo(UUID.fromString("c81e8bcc-56b0-4740-b46b-767753a6ee45"))));
    assertThat(
        response.getPayRuns().get(0).getPayslips().get(0).getFirstName(), is(equalTo("Albus")));
    assertThat(
        response.getPayRuns().get(0).getPayslips().get(0).getLastName(), is(equalTo("Dumbledore")));
    assertThat(
        response.getPayRuns().get(0).getPayslips().get(0).getEmployeeGroup(), is(equalTo("foo")));
    assertThat(response.getPayRuns().get(0).getPayslips().get(0).getDeductions(), is(equalTo(4.0)));
    assertThat(response.getPayRuns().get(0).getPayslips().get(0).getTax(), is(equalTo(0.0)));
    assertThat(
        response.getPayRuns().get(0).getPayslips().get(0).getReimbursements(), is(equalTo(55.0)));
    assertThat(response.getPayRuns().get(0).getPayslips().get(0).getNetPay(), is(equalTo(1.4)));
    assertThat(response.getPayRuns().get(0).getPayslips().get(0).getWages(), is(equalTo(5.4)));
    assertThat(
        response.getPayRuns().get(0).getPayslips().get(0).getReimbursements(), is(equalTo(55.0)));
    assertThat(response.getPayRuns().get(0).getPayslips().get(0).getSuper(), is(equalTo(0.0)));
    assertThat(
        response.getPayRuns().get(0).getPayslips().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-11-14T00:42:35Z"))));

    // System.out.println(response.toString());
  }

  @Test
  public void createPayRunTest() throws IOException {
    System.out.println("@Test - createPayRunTest");

    List<PayRun> payRuns = new ArrayList<>();
    PayRuns response = payrollAuApi.createPayRun(accessToken, xeroTenantId, payRuns);

    assertThat(
        response.getPayRuns().get(0).getPayrollCalendarID(),
        is(equalTo(UUID.fromString("78bb86b9-e1ea-47ac-b75d-f087a81931de"))));
    assertThat(
        response.getPayRuns().get(0).getPayRunID(),
        is(equalTo(UUID.fromString("d1348fab-f47a-4697-beea-922ee262407a"))));
    assertThat(
        response.getPayRuns().get(0).getPayRunPeriodStartDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 1))));
    assertThat(
        response.getPayRuns().get(0).getPayRunPeriodEndDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 7))));
    assertThat(
        response.getPayRuns().get(0).getPayRunStatus(),
        is(equalTo(com.xero.models.payrollau.PayRunStatus.DRAFT)));
    assertThat(
        response.getPayRuns().get(0).getPaymentDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 8))));
    assertThat(
        response.getPayRuns().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-11-13T22:56:58.311Z"))));
    // System.out.println(response.toString());
  }

  @Test
  public void updatePayRunTest() throws IOException {
    System.out.println("@Test - updatePayRunTest");

    UUID payRunId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    List<PayRun> payRuns = new ArrayList<>();
    PayRuns response = payrollAuApi.updatePayRun(accessToken, xeroTenantId, payRunId, payRuns);

    assertThat(
        response.getPayRuns().get(0).getPayrollCalendarID(),
        is(equalTo(UUID.fromString("78bb86b9-e1ea-47ac-b75d-f087a81931de"))));
    assertThat(
        response.getPayRuns().get(0).getPayRunID(),
        is(equalTo(UUID.fromString("f8fcda54-643f-4406-902a-d7b020d0a036"))));
    assertThat(
        response.getPayRuns().get(0).getPayRunPeriodStartDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 1))));
    assertThat(
        response.getPayRuns().get(0).getPayRunPeriodEndDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 7))));
    assertThat(
        response.getPayRuns().get(0).getPayRunStatus(),
        is(equalTo(com.xero.models.payrollau.PayRunStatus.POSTED)));
    assertThat(
        response.getPayRuns().get(0).getPaymentDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 8))));
    assertThat(
        response.getPayRuns().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-11-14T00:58:13Z"))));

    // System.out.println(response.toString());
  }
}
