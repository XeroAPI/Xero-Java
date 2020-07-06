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

public class PayrollUkApiPayRunsTest {

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
  public void getPayRunsTest() throws IOException {
    System.out.println("@Test UK Payroll - getPayRunsTest");

    int page = 1;
    PayRuns response = payrollUkApi.getPayRuns(accessToken, xeroTenantId, page, null);

    assertThat(
        response.getPayRuns().get(0).getPayRunID(),
        is(equalTo(UUID.fromString("e0a59d82-6229-4be4-9d66-49891b4d933e"))));
    assertThat(
        response.getPayRuns().get(0).getPayrollCalendarID(),
        is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
    assertThat(
        response.getPayRuns().get(0).getPayRunType(),
        is(equalTo(com.xero.models.payrolluk.PayRun.PayRunTypeEnum.SCHEDULED)));
    assertThat(
        response.getPayRuns().get(0).getPeriodStartDate(), is(equalTo(LocalDate.of(2020, 02, 03))));
    assertThat(
        response.getPayRuns().get(0).getPaymentDate(), is(equalTo(LocalDate.of(2020, 02, 10))));
    assertThat(response.getPayRuns().get(0).getTotalCost(), is(equalTo(490.39)));
    assertThat(response.getPayRuns().get(0).getTotalPay(), is(equalTo(376.92)));
    assertThat(
        response.getPayRuns().get(0).getPayRunStatus(),
        is(equalTo(com.xero.models.payrolluk.PayRun.PayRunStatusEnum.DRAFT)));
    assertThat(
        response.getPayRuns().get(0).getCalendarType(),
        is(equalTo(com.xero.models.payrolluk.PayRun.CalendarTypeEnum.WEEKLY)));

    // System.out.println(response.toString());
  }

  @Test
  public void getPayRunTest() throws IOException {
    System.out.println("@Test UK Payroll - getPayRunTest");

    UUID payRunID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    PayRunObject response = payrollUkApi.getPayRun(accessToken, xeroTenantId, payRunID);

    assertThat(
        response.getPayRun().getPayRunID(),
        is(equalTo(UUID.fromString("e0a59d82-6229-4be4-9d66-49891b4d933e"))));
    assertThat(
        response.getPayRun().getPayrollCalendarID(),
        is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
    assertThat(
        response.getPayRun().getPayRunType(),
        is(equalTo(com.xero.models.payrolluk.PayRun.PayRunTypeEnum.SCHEDULED)));
    assertThat(response.getPayRun().getPeriodStartDate(), is(equalTo(LocalDate.of(2020, 02, 03))));
    assertThat(response.getPayRun().getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 02, 9))));
    assertThat(response.getPayRun().getPaymentDate(), is(equalTo(LocalDate.of(2020, 02, 10))));
    assertThat(response.getPayRun().getTotalCost(), is(equalTo(490.39)));
    assertThat(response.getPayRun().getTotalPay(), is(equalTo(376.92)));
    assertThat(
        response.getPayRun().getPayRunStatus(),
        is(equalTo(com.xero.models.payrolluk.PayRun.PayRunStatusEnum.DRAFT)));
    assertThat(
        response.getPayRun().getCalendarType(),
        is(equalTo(com.xero.models.payrolluk.PayRun.CalendarTypeEnum.WEEKLY)));
    assertThat(
        response.getPayRun().getPaySlips().get(0).getPaySlipID(),
        is(equalTo(UUID.fromString("ed3fb9b3-e9a1-44fa-a224-8be49facbbe8"))));
    assertThat(
        response.getPayRun().getPaySlips().get(0).getEmployeeID(),
        is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
    assertThat(response.getPayRun().getPaySlips().get(0).getFirstName(), is(equalTo("Mike")));
    assertThat(
        response.getPayRun().getPaySlips().get(0).getLastName(), is(equalTo("Johnpitragibigson")));
    assertThat(response.getPayRun().getPaySlips().get(0).getTotalEarnings(), is(equalTo(480.77)));
    assertThat(response.getPayRun().getPaySlips().get(0).getGrossEarnings(), is(equalTo(480.77)));
    assertThat(response.getPayRun().getPaySlips().get(0).getTotalPay(), is(equalTo(386.54)));
    assertThat(response.getPayRun().getPaySlips().get(0).getTotalEmployerTaxes(), is(equalTo(0.0)));
    assertThat(
        response.getPayRun().getPaySlips().get(0).getTotalEmployeeTaxes(), is(equalTo(94.23)));
    assertThat(response.getPayRun().getPaySlips().get(0).getTotalDeductions(), is(equalTo(9.62)));
    assertThat(
        response.getPayRun().getPaySlips().get(0).getTotalReimbursements(), is(equalTo(0.0)));
    assertThat(response.getPayRun().getPaySlips().get(0).getTotalCourtOrders(), is(equalTo(0.0)));
    assertThat(response.getPayRun().getPaySlips().get(0).getTotalBenefits(), is(equalTo(9.62)));

    // System.out.println(response.toString());
  }

  @Test
  public void updatePayRunTest() throws IOException {
    System.out.println("@Test UK Payroll - updatePayRunTest");

    UUID payRunID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    PayRun payRun = new PayRun();
    PayRunObject response = payrollUkApi.updatePayRun(accessToken, xeroTenantId, payRunID, payRun);

    assertThat(
        response.getPayRun().getPayRunID(),
        is(equalTo(UUID.fromString("e0a59d82-6229-4be4-9d66-49891b4d933e"))));
    assertThat(
        response.getPayRun().getPayrollCalendarID(),
        is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
    assertThat(
        response.getPayRun().getPayRunType(),
        is(equalTo(com.xero.models.payrolluk.PayRun.PayRunTypeEnum.SCHEDULED)));
    assertThat(response.getPayRun().getPeriodStartDate(), is(equalTo(LocalDate.of(2020, 02, 03))));
    assertThat(response.getPayRun().getPeriodEndDate(), is(equalTo(LocalDate.of(2020, 02, 9))));
    assertThat(response.getPayRun().getPaymentDate(), is(equalTo(LocalDate.of(2020, 05, 01))));
    assertThat(response.getPayRun().getTotalCost(), is(equalTo(523.79)));
    assertThat(response.getPayRun().getTotalPay(), is(equalTo(445.04)));
    assertThat(
        response.getPayRun().getPayRunStatus(),
        is(equalTo(com.xero.models.payrolluk.PayRun.PayRunStatusEnum.DRAFT)));
    assertThat(
        response.getPayRun().getCalendarType(),
        is(equalTo(com.xero.models.payrolluk.PayRun.CalendarTypeEnum.WEEKLY)));
    assertThat(
        response.getPayRun().getPaySlips().get(0).getPaySlipID(),
        is(equalTo(UUID.fromString("ed3fb9b3-e9a1-44fa-a224-8be49facbbe8"))));
    assertThat(
        response.getPayRun().getPaySlips().get(0).getEmployeeID(),
        is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
    assertThat(response.getPayRun().getPaySlips().get(0).getFirstName(), is(equalTo("Mike")));
    assertThat(
        response.getPayRun().getPaySlips().get(0).getLastName(), is(equalTo("Johncfvhitgezvson")));
    assertThat(response.getPayRun().getPaySlips().get(0).getTotalEarnings(), is(equalTo(480.77)));
    assertThat(response.getPayRun().getPaySlips().get(0).getGrossEarnings(), is(equalTo(480.77)));
    assertThat(response.getPayRun().getPaySlips().get(0).getTotalPay(), is(equalTo(445.04)));
    assertThat(
        response.getPayRun().getPaySlips().get(0).getTotalEmployerTaxes(), is(equalTo(43.02)));
    assertThat(
        response.getPayRun().getPaySlips().get(0).getTotalEmployeeTaxes(), is(equalTo(35.73)));
    assertThat(response.getPayRun().getPaySlips().get(0).getTotalDeductions(), is(equalTo(0.0)));
    assertThat(
        response.getPayRun().getPaySlips().get(0).getTotalReimbursements(), is(equalTo(0.0)));
    assertThat(response.getPayRun().getPaySlips().get(0).getTotalCourtOrders(), is(equalTo(0.0)));
    assertThat(response.getPayRun().getPaySlips().get(0).getTotalBenefits(), is(equalTo(0.0)));
    assertThat(
        response.getPayRun().getPaySlips().get(0).getPaymentMethod(),
        is(equalTo(com.xero.models.payrolluk.Payslip.PaymentMethodEnum.ELECTRONICALLY)));

    // System.out.println(response.toString());
  }
}
