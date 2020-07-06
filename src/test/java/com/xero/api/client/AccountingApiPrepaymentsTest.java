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
import java.math.BigDecimal;

public class AccountingApiPrepaymentsTest {

  ApiClient defaultClient;
  AccountingApi accountingApi;
  String accessToken;
  String xeroTenantId;

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

    // ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
    if (setUpIsDone) {
      return;
    }

    try {
      System.out.println("Sleep for 30 seconds");
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
  public void createPrepaymentAllocationTest() throws IOException {
    System.out.println("@Test - createPrepaymentAllocation");
    UUID prepaymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Allocations allocations = new Allocations();
    Allocations response =
        accountingApi.createPrepaymentAllocations(
            accessToken, xeroTenantId, prepaymentID, allocations, false);

    assertThat(response.getAllocations().get(0).getAmount(), is(equalTo(1.0)));
    assertThat(
        response.getAllocations().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 13))));
    assertThat(
        response.getAllocations().get(0).getInvoice().getInvoiceID(),
        is(equalTo(UUID.fromString("c7c37b83-ac95-45ea-88ba-8ad83a5f22fe"))));
    // System.out.println(response.getAllocations().get(0).toString());
  }

  @Test
  public void getPrepaymentTest() throws IOException {
    System.out.println("@Test - getPrepayment");
    UUID prepaymentID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Prepayments response = accountingApi.getPrepayment(accessToken, xeroTenantId, prepaymentID);

    assertThat(
        response.getPrepayments().get(0).getType().toString(), is(equalTo("RECEIVE-PREPAYMENT")));
    assertThat(
        response.getPrepayments().get(0).getContact().getName(), is(equalTo("Luke Skywalker")));
    assertThat(
        response.getPrepayments().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 13))));
    assertThat(
        response.getPrepayments().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.Prepayment.StatusEnum.AUTHORISED)));
    assertThat(
        response.getPrepayments().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(
        response.getPrepayments().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Light Speeder")));
    assertThat(
        response.getPrepayments().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
    assertThat(
        response.getPrepayments().get(0).getLineItems().get(0).getUnitAmount(),
        is(equalTo(3000.0)));
    assertThat(
        response.getPrepayments().get(0).getLineItems().get(0).getTaxType(),
        is(equalTo("OUTPUT2")));
    assertThat(
        response.getPrepayments().get(0).getLineItems().get(0).getAccountCode(),
        is(equalTo("200")));
    assertThat(
        response.getPrepayments().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(450.0)));
    assertThat(
        response.getPrepayments().get(0).getLineItems().get(0).getLineAmount(),
        is(equalTo(3000.0)));
    assertThat(response.getPrepayments().get(0).getSubTotal(), is(equalTo(3000.0)));
    assertThat(response.getPrepayments().get(0).getSubTotal().toString(), is(equalTo("3000.0")));
    assertThat(response.getPrepayments().get(0).getTotalTax(), is(equalTo(450.0)));
    assertThat(response.getPrepayments().get(0).getTotalTax().toString(), is(equalTo("450.0")));
    assertThat(response.getPrepayments().get(0).getTotal(), is(equalTo(3450.0)));
    assertThat(response.getPrepayments().get(0).getTotal().toString(), is(equalTo("3450.0")));
    assertThat(
        response.getPrepayments().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-14T00:13:44.850Z"))));
    assertThat(
        response.getPrepayments().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getPrepayments().get(0).getPrepaymentID(),
        is(equalTo(UUID.fromString("ce0cddef-cf5a-4e59-b638-f225679115a7"))));
    assertThat(response.getPrepayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getPrepayments().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getPrepayments().get(0).getRemainingCredit(), is(equalTo(3449.0)));
    assertThat(
        response.getPrepayments().get(0).getRemainingCredit().toString(), is(equalTo("3449.0")));
    assertThat(response.getPrepayments().get(0).getHasAttachments(), is(equalTo(true)));
    assertThat(
        response.getPrepayments().get(0).getAllocations().get(0).getAmount(), is(equalTo(1.0)));
    assertThat(
        response.getPrepayments().get(0).getAllocations().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 13))));
    assertThat(
        response.getPrepayments().get(0).getAllocations().get(0).getInvoice().getInvoiceNumber(),
        is(equalTo("INV-0004")));
    assertThat(
        response.getPrepayments().get(0).getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("2ca06aa0-3629-474a-9401-553d4b7fa9b0"))));
    assertThat(
        response.getPrepayments().get(0).getAttachments().get(0).getFileName(),
        is(equalTo("giphy.gif")));
    assertThat(
        response.getPrepayments().get(0).getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/banktransaction/ce0cddef-cf5a-4e59-b638-f225679115a7/Attachments/giphy.gif")));
    assertThat(
        response.getPrepayments().get(0).getAttachments().get(0).getMimeType(),
        is(equalTo("image/gif")));
    assertThat(
        response.getPrepayments().get(0).getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal(495727))));
    // System.out.println(response.getPrepayments().get(0).toString());
  }

  @Test
  public void getPrepaymentsTest() throws IOException {
    System.out.println("@Test - getPrepayments");
    OffsetDateTime ifModifiedSince = null;
    String where = null;
    String order = null;
    Integer page = null;
    Integer unitdp = null;
    Prepayments response =
        accountingApi.getPrepayments(
            accessToken, xeroTenantId, ifModifiedSince, where, order, page, unitdp);

    assertThat(
        response.getPrepayments().get(0).getType().toString(), is(equalTo("RECEIVE-PREPAYMENT")));
    assertThat(
        response.getPrepayments().get(0).getContact().getName(), is(equalTo("Luke Skywalker")));
    assertThat(
        response.getPrepayments().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 13))));
    assertThat(
        response.getPrepayments().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.Prepayment.StatusEnum.AUTHORISED)));
    assertThat(
        response.getPrepayments().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(response.getPrepayments().get(0).getSubTotal(), is(equalTo(3000.0)));
    assertThat(response.getPrepayments().get(0).getSubTotal().toString(), is(equalTo("3000.0")));
    assertThat(response.getPrepayments().get(0).getTotalTax(), is(equalTo(450.0)));
    assertThat(response.getPrepayments().get(0).getTotalTax().toString(), is(equalTo("450.0")));
    assertThat(response.getPrepayments().get(0).getTotal(), is(equalTo(3450.0)));
    assertThat(response.getPrepayments().get(0).getTotal().toString(), is(equalTo("3450.0")));
    assertThat(
        response.getPrepayments().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-13T14:59:47.730Z"))));
    assertThat(
        response.getPrepayments().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getPrepayments().get(0).getPrepaymentID(),
        is(equalTo(UUID.fromString("ce0cddef-cf5a-4e59-b638-f225679115a7"))));
    assertThat(response.getPrepayments().get(0).getRemainingCredit(), is(equalTo(3450.0)));
    assertThat(
        response.getPrepayments().get(0).getRemainingCredit().toString(), is(equalTo("3450.0")));
    assertThat(response.getPrepayments().get(0).getHasAttachments(), is(equalTo(true)));
    // System.out.println(response.getPrepayments().get(0).toString());
  }
}
