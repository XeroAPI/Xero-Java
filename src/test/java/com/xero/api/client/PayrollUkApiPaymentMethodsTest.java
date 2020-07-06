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

public class PayrollUkApiPaymentMethodsTest {

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
  public void getEmployeePaymentMethodTest() throws IOException {
    System.out.println("@Test UK Payroll - getEmployeePaymentMethodTest");

    int page = 1;
    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    PaymentMethodObject response =
        payrollUkApi.getEmployeePaymentMethod(accessToken, xeroTenantId, employeeId);

    assertThat(
        response.getPaymentMethod().getPaymentMethod(),
        is(equalTo(com.xero.models.payrolluk.PaymentMethod.PaymentMethodEnum.ELECTRONICALLY)));
    assertThat(
        response.getPaymentMethod().getBankAccounts().get(0).getAccountName(),
        is(equalTo("Oliver Furniss - Santander")));
    assertThat(
        response.getPaymentMethod().getBankAccounts().get(0).getAccountNumber(),
        is(equalTo("12345678")));
    assertThat(
        response.getPaymentMethod().getBankAccounts().get(0).getSortCode(), is(equalTo("111111")));

    // System.out.println(response.toString());
  }

  @Test
  public void createEmployeePaymentMethodTest() throws IOException {
    System.out.println("@Test UK Payroll - createEmployeePaymentMethodTest");

    int page = 1;
    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    PaymentMethod paymentMethod = new PaymentMethod();
    PaymentMethodObject response =
        payrollUkApi.createEmployeePaymentMethod(
            accessToken, xeroTenantId, employeeId, paymentMethod);

    assertThat(
        response.getPaymentMethod().getPaymentMethod(),
        is(equalTo(com.xero.models.payrolluk.PaymentMethod.PaymentMethodEnum.ELECTRONICALLY)));
    assertThat(
        response.getPaymentMethod().getBankAccounts().get(0).getAccountName(),
        is(equalTo("Sid BofA")));
    assertThat(
        response.getPaymentMethod().getBankAccounts().get(0).getAccountNumber(),
        is(equalTo("24987654")));
    assertThat(
        response.getPaymentMethod().getBankAccounts().get(0).getSortCode(), is(equalTo("287654")));

    // System.out.println(response.toString());
  }
}
