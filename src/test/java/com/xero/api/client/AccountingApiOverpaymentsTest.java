package com.xero.api.client;


import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.xero.api.ApiClient;
import com.xero.models.accounting.*;

import java.io.File;


import org.threeten.bp.*;
import java.io.IOException;

import java.io.File;
import java.io.IOException;


import java.util.UUID;
import java.math.BigDecimal;

public class AccountingApiOverpaymentsTest {

  ApiClient defaultClient;
  AccountingApi accountingApi;
  String accessToken;
  String xeroTenantId;

  File body;

  private static boolean setUpIsDone = false;

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

    ClassLoader classLoader = getClass().getClassLoader();
    body = new File(classLoader.getResource("helo-heros.jpg").getFile());

    // ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
    if (setUpIsDone) {
      return;
    }

    try {
      System.out.println("Sleep for 60 seconds");
      Thread.sleep(60);
    } catch (InterruptedException e) {
      System.out.println(e);
    }
    // do the setup
    setUpIsDone = true;
  }

  public void tearDown() {
    accountingApi = null;
    defaultClient = null;
  }

  @Test
  public void createOverpaymentAllocationsTest() throws IOException {
    System.out.println("@Test - createOverpaymentAllocations");
    UUID overpaymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Allocations allocations = new Allocations();
    Allocations response =
        accountingApi.createOverpaymentAllocations(
            accessToken, xeroTenantId, overpaymentID, allocations, false);

    // TODO: test validations
    assertThat(
        response.getAllocations().get(0).getInvoice().getInvoiceID(),
        is(equalTo(UUID.fromString("c45720a1-ade3-4a38-a064-d15489be6841"))));
    assertThat(response.getAllocations().get(0).getAmount(), is(equalTo(1.0)));
    assertThat(response.getAllocations().get(0).getAmount().toString(), is(equalTo("1.0")));
    assertThat(
        response.getAllocations().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 12))));
    // System.out.println(response.getAllocations().get(0).toString());
  }

  @Test
  public void getOverpaymentTest() throws IOException {
    System.out.println("@Test - getOverpayment");
    UUID overpaymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Overpayments response = accountingApi.getOverpayment(accessToken, xeroTenantId, overpaymentID);

    // assertThat(response.getOverpayments().get(0).getType(),
    // is(equalTo(com.xero.models.accounting.Overpayment.TypeEnum.SPEND-OVERPAYMENT)));
    assertThat(
        response.getOverpayments().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 12))));
    assertThat(
        response.getOverpayments().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.Overpayment.StatusEnum.AUTHORISED)));
    assertThat(
        response.getOverpayments().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
    assertThat(response.getOverpayments().get(0).getSubTotal(), is(equalTo(3000.0)));
    assertThat(response.getOverpayments().get(0).getSubTotal().toString(), is(equalTo("3000.0")));
    assertThat(response.getOverpayments().get(0).getTotalTax(), is(equalTo(0.0)));
    assertThat(response.getOverpayments().get(0).getTotalTax().toString(), is(equalTo("0.0")));
    assertThat(response.getOverpayments().get(0).getTotal(), is(equalTo(3000.0)));
    assertThat(response.getOverpayments().get(0).getTotal().toString(), is(equalTo("3000.0")));
    assertThat(
        response.getOverpayments().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-12T22:15:52.890Z"))));
    assertThat(
        response.getOverpayments().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getOverpayments().get(0).getOverpaymentID(),
        is(equalTo(UUID.fromString("ed7f6041-c915-4667-bd1d-54c48e92161e"))));
    assertThat(response.getOverpayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getOverpayments().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getOverpayments().get(0).getRemainingCredit(), is(equalTo(2999.0)));
    assertThat(
        response.getOverpayments().get(0).getRemainingCredit().toString(), is(equalTo("2999.0")));
    assertThat(
        response.getOverpayments().get(0).getAllocations().get(0).getAmount(), is(equalTo(1.0)));
    assertThat(
        response.getOverpayments().get(0).getAllocations().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 12))));
    assertThat(
        response.getOverpayments().get(0).getAllocations().get(0).getInvoice().getInvoiceID(),
        is(equalTo(UUID.fromString("c45720a1-ade3-4a38-a064-d15489be6841"))));
    assertThat(response.getOverpayments().get(0).getHasAttachments(), is(equalTo(true)));
    assertThat(
        response.getOverpayments().get(0).getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("247dd942-5245-47a7-adb1-4d9ea075b431"))));
    assertThat(
        response.getOverpayments().get(0).getAttachments().get(0).getFileName(),
        is(equalTo("giphy.gif")));
    assertThat(
        response.getOverpayments().get(0).getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/banktransaction/ed7f6041-c915-4667-bd1d-54c48e92161e/Attachments/giphy.gif")));
    assertThat(
        response.getOverpayments().get(0).getAttachments().get(0).getMimeType(),
        is(equalTo("image/gif")));
    assertThat(
        response.getOverpayments().get(0).getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal("495727"))));
    assertThat(
        response.getOverpayments().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Broken TV deposit")));
    assertThat(
        response.getOverpayments().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
    assertThat(
        response.getOverpayments().get(0).getLineItems().get(0).getUnitAmount(),
        is(equalTo(3000.0)));
    assertThat(
        response.getOverpayments().get(0).getLineItems().get(0).getAccountCode(),
        is(equalTo("800")));
    assertThat(
        response.getOverpayments().get(0).getLineItems().get(0).getTaxType(), is(equalTo("NONE")));
    assertThat(
        response.getOverpayments().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(0.0)));
    assertThat(
        response.getOverpayments().get(0).getLineItems().get(0).getLineAmount(),
        is(equalTo(3000.0)));
    // System.out.println(response.getOverpayments().get(0).toString());
  }

  @Test
  public void getOverpaymentsTest() throws IOException {
    System.out.println("@Test - getOverpayments");
    OffsetDateTime ifModifiedSince = null;
    String where = null;
    String order = null;
    Integer page = null;
    Integer unitdp = null;
    Overpayments response =
        accountingApi.getOverpayments(
            accessToken, xeroTenantId, ifModifiedSince, where, order, page, unitdp);

    assertThat(
        response.getOverpayments().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 12))));
    assertThat(
        response.getOverpayments().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.Overpayment.StatusEnum.AUTHORISED)));
    assertThat(
        response.getOverpayments().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
    assertThat(response.getOverpayments().get(0).getSubTotal(), is(equalTo(500.0)));
    assertThat(response.getOverpayments().get(0).getSubTotal().toString(), is(equalTo("500.0")));
    assertThat(response.getOverpayments().get(0).getTotalTax(), is(equalTo(0.0)));
    assertThat(response.getOverpayments().get(0).getTotalTax().toString(), is(equalTo("0.0")));
    assertThat(response.getOverpayments().get(0).getTotal(), is(equalTo(500.0)));
    assertThat(response.getOverpayments().get(0).getTotal().toString(), is(equalTo("500.0")));
    assertThat(
        response.getOverpayments().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-12T22:08:55.123Z"))));
    assertThat(
        response.getOverpayments().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getOverpayments().get(0).getOverpaymentID(),
        is(equalTo(UUID.fromString("098b4dcb-5622-4699-87f8-9d40c4ccceb3"))));
    assertThat(response.getOverpayments().get(0).getRemainingCredit(), is(equalTo(500.0)));
    assertThat(
        response.getOverpayments().get(0).getRemainingCredit().toString(), is(equalTo("500.0")));
    assertThat(response.getOverpayments().get(0).getHasAttachments(), is(equalTo(false)));
    // System.out.println(response.getOverpayments().get(0).toString());
  }
}
