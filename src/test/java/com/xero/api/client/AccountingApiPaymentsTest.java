package com.xero.api.client;


import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.xero.api.ApiClient;
import com.xero.models.accounting.*;



import org.threeten.bp.*;
import java.io.IOException;

import java.io.IOException;


import java.util.UUID;

public class AccountingApiPaymentsTest {

  ApiClient defaultClient;
  AccountingApi accountingApi;
  String accessToken;
  String xeroTenantId;

  @Before
  public void setUp() {
    // Set Access Token and Tenant Id
    accessToken = "123";
    xeroTenantId = "xyz";

    // Init AccountingApi client
    // NEW Sandbox for API Mocking
    defaultClient =
        new ApiClient(
            "https://xero-accounting.getsandbox.com:443/api.xro/2.0", null, null, null, null);
    accountingApi = AccountingApi.getInstance(defaultClient);
  }

  public void tearDown() {
    accountingApi = null;
    defaultClient = null;
  }

  @Test
  public void createPaymentsTest() throws IOException {
    System.out.println("@Test - createPayments");
    Payments payments = new Payments();
    Payments response = accountingApi.createPayments(accessToken, xeroTenantId, payments, true);

    assertThat(
        response.getPayments().get(0).getInvoice().getInvoiceNumber(), is(equalTo("INV-0004")));
    assertThat(response.getPayments().get(0).getAccount().getCode(), is(equalTo("970")));
    assertThat(
        response.getPayments().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 12))));
    assertThat(response.getPayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getPayments().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getPayments().get(0).getAmount(), is(equalTo(1.0)));
    assertThat(response.getPayments().get(0).getAmount().toString(), is(equalTo("1.0")));
    assertThat(response.getPayments().get(0).getIsReconciled(), is(equalTo(false)));
    assertThat(
        response.getPayments().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.Payment.StatusEnum.AUTHORISED)));
    assertThat(
        response.getPayments().get(0).getPaymentType(),
        is(equalTo(com.xero.models.accounting.Payment.PaymentTypeEnum.ACCRECPAYMENT)));
    assertThat(
        response.getPayments().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-12T23:10:38.623Z"))));
    assertThat(
        response.getPayments().get(0).getPaymentID(),
        is(equalTo(UUID.fromString("61ed71fc-01bf-4eb8-8419-8a18789ff45f"))));
    assertThat(response.getPayments().get(0).getHasAccount(), is(equalTo(true)));
    assertThat(response.getPayments().get(0).getHasValidationErrors(), is(equalTo(true)));
    assertThat(
        response.getPayments().get(0).getValidationErrors().get(0).getMessage(),
        is(equalTo("Payment amount exceeds the amount outstanding on this document")));
    // System.out.println(response.getPayments().get(0).toString());
  }

  @Test
  public void deletePaymentTest() throws IOException {
    System.out.println("@Test - deletePayment");
    UUID paymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    PaymentDelete paymentDelete = new PaymentDelete();
    Payments response =
        accountingApi.deletePayment(accessToken, xeroTenantId, paymentID, paymentDelete);

    assertThat(response.getPayments().get(0).getAccount().getCode(), is(equalTo("033")));
    assertThat(
        response.getPayments().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 9, 02))));
    assertThat(response.getPayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getPayments().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getPayments().get(0).getAmount(), is(equalTo(2.0)));
    assertThat(response.getPayments().get(0).getAmount().toString(), is(equalTo("2.0")));
    assertThat(response.getPayments().get(0).getReference(), is(equalTo("foobar")));
    assertThat(response.getPayments().get(0).getIsReconciled(), is(equalTo(false)));
    assertThat(
        response.getPayments().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.Payment.StatusEnum.DELETED)));
    assertThat(
        response.getPayments().get(0).getPaymentType(),
        is(equalTo(com.xero.models.accounting.Payment.PaymentTypeEnum.APCREDITPAYMENT)));
    assertThat(
        response.getPayments().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2020-03-11T16:57:32.373Z"))));
    assertThat(
        response.getPayments().get(0).getPaymentID(),
        is(equalTo(UUID.fromString("c94c996b-1ab3-4ff3-ad19-1cdc77f30817"))));
    assertThat(response.getPayments().get(0).getHasAccount(), is(equalTo(true)));
    assertThat(response.getPayments().get(0).getHasValidationErrors(), is(equalTo(false)));
    // System.out.println(response.getPayments().get(0).toString());
  }

  @Test
  public void getPaymentTest() throws IOException {
    System.out.println("@Test - getPayment");
    UUID paymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Payments response = accountingApi.getPayment(accessToken, xeroTenantId, paymentID);

    assertThat(
        response.getPayments().get(0).getInvoice().getInvoiceNumber(), is(equalTo("INV-0002")));
    assertThat(response.getPayments().get(0).getAccount().getCode(), is(equalTo("970")));
    assertThat(
        response.getPayments().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2018, 11, 29))));
    assertThat(response.getPayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getPayments().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getPayments().get(0).getAmount(), is(equalTo(46.0)));
    assertThat(response.getPayments().get(0).getAmount().toString(), is(equalTo("46.0")));
    assertThat(response.getPayments().get(0).getIsReconciled(), is(equalTo(false)));
    assertThat(
        response.getPayments().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.Payment.StatusEnum.AUTHORISED)));
    assertThat(
        response.getPayments().get(0).getPaymentType(),
        is(equalTo(com.xero.models.accounting.Payment.PaymentTypeEnum.ACCRECPAYMENT)));
    assertThat(
        response.getPayments().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2018-11-02T16:36:32.690Z"))));
    assertThat(
        response.getPayments().get(0).getPaymentID(),
        is(equalTo(UUID.fromString("99ea7f6b-c513-4066-bc27-b7c65dcd76c2"))));
    assertThat(response.getPayments().get(0).getHasAccount(), is(equalTo(true)));
    assertThat(response.getPayments().get(0).getHasValidationErrors(), is(equalTo(false)));
    // System.out.println(response.getPayments().get(0).toString());
  }

  @Test
  public void getPaymentsTest() throws IOException {
    System.out.println("@Test - getPayments");
    OffsetDateTime ifModifiedSince = null;
    String where = null;
    String order = null;
    Payments response =
        accountingApi.getPayments(accessToken, xeroTenantId, ifModifiedSince, where, order, 1);

    assertThat(
        response.getPayments().get(0).getInvoice().getInvoiceNumber(), is(equalTo("INV-0002")));
    assertThat(response.getPayments().get(0).getAccount().getCode(), is(equalTo("970")));
    assertThat(
        response.getPayments().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2018, 11, 29))));
    assertThat(response.getPayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getPayments().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getPayments().get(0).getAmount(), is(equalTo(46.0)));
    assertThat(response.getPayments().get(0).getAmount().toString(), is(equalTo("46.0")));
    assertThat(response.getPayments().get(0).getIsReconciled(), is(equalTo(false)));
    assertThat(
        response.getPayments().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.Payment.StatusEnum.AUTHORISED)));
    assertThat(
        response.getPayments().get(0).getPaymentType(),
        is(equalTo(com.xero.models.accounting.Payment.PaymentTypeEnum.ACCRECPAYMENT)));
    assertThat(
        response.getPayments().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2018-11-02T16:36:32.690Z"))));
    assertThat(
        response.getPayments().get(0).getPaymentID(),
        is(equalTo(UUID.fromString("99ea7f6b-c513-4066-bc27-b7c65dcd76c2"))));
    assertThat(response.getPayments().get(0).getHasAccount(), is(equalTo(true)));
    assertThat(response.getPayments().get(0).getHasValidationErrors(), is(equalTo(false)));
    // System.out.println(response.getPayments().get(0).toString());
  }
}
