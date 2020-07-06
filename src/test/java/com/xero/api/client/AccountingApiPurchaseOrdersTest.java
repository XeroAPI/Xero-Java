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

public class AccountingApiPurchaseOrdersTest {

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

  /* @Test
  public void createPurchaseOrderTest() throws IOException {
      System.out.println("@Test - createPurchaseOrder");
      PurchaseOrder purchaseOrder = new PurchaseOrder();
      PurchaseOrders response = accountingApi.createPurchaseOrder(accessToken,xeroTenantId,purchaseOrder);

      // TODO: test validations
      assertThat(response.getPurchaseOrders().get(0).getContact().getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
      assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getLineItemID(), is(equalTo(UUID.fromString("792b7e40-b9f2-47f0-8624-b09f4b0166dd"))));
      assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getDescription(), is(equalTo("Foobar")));
      assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
      assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(20.0)));
      assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxType(), is(equalTo("INPUT2")));
      assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("710")));
      assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(3.0)));
      assertThat(response.getPurchaseOrders().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(20.0)));
      assertThat(response.getPurchaseOrders().get(0).getDate(), is(equalTo(LocalDate.of(2019,03,12))));
      assertThat(response.getPurchaseOrders().get(0).getLineAmountTypes(), is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
      assertThat(response.getPurchaseOrders().get(0).getPurchaseOrderNumber(), is(equalTo("PO-0004")));
      assertThat(response.getPurchaseOrders().get(0).getCurrencyCode(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
      assertThat(response.getPurchaseOrders().get(0).getStatus(), is(equalTo(com.xero.models.accounting.PurchaseOrder.StatusEnum.DRAFT)));
      assertThat(response.getPurchaseOrders().get(0).getSentToContact(), is(equalTo(false)));
      assertThat(response.getPurchaseOrders().get(0).getPurchaseOrderID(), is(equalTo(UUID.fromString("56204648-8fbe-46f8-b09c-2125f7939533"))));
      assertThat(response.getPurchaseOrders().get(0).getCurrencyRate(), is(equalTo(1.0)));
      assertThat(response.getPurchaseOrders().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
      assertThat(response.getPurchaseOrders().get(0).getSubTotal(), is(equalTo(20.0)));
      assertThat(response.getPurchaseOrders().get(0).getSubTotal().toString(), is(equalTo("20.0")));
      assertThat(response.getPurchaseOrders().get(0).getTotalTax(), is(equalTo(3.0)));
      assertThat(response.getPurchaseOrders().get(0).getTotalTax().toString(), is(equalTo("3.0")));
      assertThat(response.getPurchaseOrders().get(0).getTotal(), is(equalTo(23.0)));
      assertThat(response.getPurchaseOrders().get(0).getTotal().toString(), is(equalTo("23.0")));
      assertThat(response.getPurchaseOrders().get(0).getTotalDiscount(), is(equalTo(0.0)));
      assertThat(response.getPurchaseOrders().get(0).getTotalDiscount().toString(), is(equalTo("0.0")));
      assertThat(response.getPurchaseOrders().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-13T17:22:26.077-07:00"))));
      assertThat(response.getPurchaseOrders().get(0).getValidationErrors().get(0).getMessage(), is(equalTo("Order number must be unique")));
      assertThat(response.getPurchaseOrders().get(0).getWarnings().get(0).getMessage(), is(equalTo("Only AUTHORISED and BILLED purchase orders may have SentToContact updated.")));
      //System.out.println(response.getPurchaseOrders().get(0).toString());
  }
  */

  @Test
  public void getPurchaseOrderTest() throws IOException {
    System.out.println("@Test - getPurchaseOrder");
    UUID purchaseOrderID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    PurchaseOrders response =
        accountingApi.getPurchaseOrder(accessToken, xeroTenantId, purchaseOrderID);

    assertThat(
        response.getPurchaseOrders().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("8a9d3eca-e052-43bc-9b87-221d0648c045"))));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Brand new Fender Strats")));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getUnitAmount(),
        is(equalTo(2500.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getItemCode(),
        is(equalTo("123")));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxType(),
        is(equalTo("INPUT2")));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getAccountCode(),
        is(equalTo("630")));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxAmount(),
        is(equalTo(337.5)));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getLineAmount(),
        is(equalTo(2250.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getTracking().get(0).getName(),
        is(equalTo("Simpsons")));
    assertThat(
        response.getPurchaseOrders().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 26))));
    assertThat(
        response.getPurchaseOrders().get(0).getDeliveryDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 28))));
    assertThat(
        response.getPurchaseOrders().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(
        response.getPurchaseOrders().get(0).getPurchaseOrderNumber(), is(equalTo("PO-0006")));
    assertThat(response.getPurchaseOrders().get(0).getReference(), is(equalTo("foobar")));
    assertThat(
        response.getPurchaseOrders().get(0).getBrandingThemeID(),
        is(equalTo(UUID.fromString("414d4a87-46d6-4cfc-ab42-4e29d22e5076"))));
    assertThat(
        response.getPurchaseOrders().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getPurchaseOrders().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.PurchaseOrder.StatusEnum.DRAFT)));
    assertThat(response.getPurchaseOrders().get(0).getSentToContact(), is(equalTo(false)));
    assertThat(
        response.getPurchaseOrders().get(0).getDeliveryAddress(),
        is(equalTo("101 Grafton Road\nRoseneath\nWellington\n6011\nNew Zealand")));
    assertThat(response.getPurchaseOrders().get(0).getAttentionTo(), is(equalTo("CEO")));
    assertThat(response.getPurchaseOrders().get(0).getTelephone(), is(equalTo("64 123-2222")));
    assertThat(
        response.getPurchaseOrders().get(0).getDeliveryInstructions(),
        is(equalTo("Drop off at front  door")));
    assertThat(
        response.getPurchaseOrders().get(0).getPurchaseOrderID(),
        is(equalTo(UUID.fromString("15369a9f-17b6-4235-83c4-0029256d1c37"))));
    assertThat(response.getPurchaseOrders().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getPurchaseOrders().get(0).getSubTotal(), is(equalTo(2250.0)));
    assertThat(response.getPurchaseOrders().get(0).getSubTotal().toString(), is(equalTo("2250.0")));
    assertThat(response.getPurchaseOrders().get(0).getTotalTax(), is(equalTo(337.5)));
    assertThat(response.getPurchaseOrders().get(0).getTotalTax().toString(), is(equalTo("337.5")));
    assertThat(response.getPurchaseOrders().get(0).getTotal(), is(equalTo(2587.5)));
    assertThat(response.getPurchaseOrders().get(0).getTotal().toString(), is(equalTo("2587.5")));
    assertThat(response.getPurchaseOrders().get(0).getTotalDiscount(), is(equalTo(250.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getTotalDiscount().toString(), is(equalTo("250.0")));
    assertThat(response.getPurchaseOrders().get(0).getHasAttachments(), is(equalTo(true)));
    assertThat(
        response.getPurchaseOrders().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-26T18:47:09.823Z"))));
    assertThat(
        response.getPurchaseOrders().get(0).getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("7d94ccdc-ef7b-4806-87ac-8442f25e593b"))));
    assertThat(
        response.getPurchaseOrders().get(0).getAttachments().get(0).getFileName(),
        is(equalTo("HelloWorld.png")));
    assertThat(
        response.getPurchaseOrders().get(0).getAttachments().get(0).getMimeType(),
        is(equalTo("image/png")));
    assertThat(
        response.getPurchaseOrders().get(0).getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/PurchaseOrders/15369a9f-17b6-4235-83c4-0029256d1c37/Attachments/HelloWorld.png")));
    assertThat(
        response.getPurchaseOrders().get(0).getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal("76091"))));
    assertThat(
        response.getPurchaseOrders().get(0).getAttachments().get(0).getIncludeOnline(),
        is(equalTo(null)));
    // System.out.println(response.getPurchaseOrders().get(0).toString());
  }

  @Test
  public void getPurchaseOrdersTest() throws IOException {
    System.out.println("@Test - getPurchaseOrders");
    OffsetDateTime ifModifiedSince = null;
    String status = null;
    String dateFrom = null;
    String dateTo = null;
    String order = null;
    Integer page = null;
    PurchaseOrders response =
        accountingApi.getPurchaseOrders(
            accessToken, xeroTenantId, ifModifiedSince, status, dateFrom, dateTo, order, page);

    assertThat(
        response.getPurchaseOrders().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("0f7b54b8-bfa4-4c5d-9c22-73dbd5796e54"))));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Foobar")));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getUnitAmount(),
        is(equalTo(20.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(0.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getLineAmount(),
        is(equalTo(20.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 12))));
    assertThat(
        response.getPurchaseOrders().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(
        response.getPurchaseOrders().get(0).getPurchaseOrderNumber(), is(equalTo("PO-0001")));
    assertThat(response.getPurchaseOrders().get(0).getAttentionTo(), is(equalTo("Jimmy")));
    assertThat(
        response.getPurchaseOrders().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getPurchaseOrders().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.PurchaseOrder.StatusEnum.DELETED)));
    assertThat(
        response.getPurchaseOrders().get(0).getPurchaseOrderID(),
        is(equalTo(UUID.fromString("f9627f0d-b715-4039-bb6a-96dc3eae5ec5"))));
    assertThat(response.getPurchaseOrders().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getPurchaseOrders().get(0).getSubTotal(), is(equalTo(20.0)));
    assertThat(response.getPurchaseOrders().get(0).getSubTotal().toString(), is(equalTo("20.0")));
    assertThat(response.getPurchaseOrders().get(0).getTotalTax(), is(equalTo(0.0)));
    assertThat(response.getPurchaseOrders().get(0).getTotalTax().toString(), is(equalTo("0.0")));
    assertThat(response.getPurchaseOrders().get(0).getTotal(), is(equalTo(20.0)));
    assertThat(response.getPurchaseOrders().get(0).getTotal().toString(), is(equalTo("20.0")));
    assertThat(
        response.getPurchaseOrders().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-14T00:18:23.443Z"))));
    // System.out.println(response.getPurchaseOrders().get(0).toString());
  }

  @Test
  public void updatePurchaseOrderTest() throws IOException {
    System.out.println("@Test - updatePurchaseOrder");
    UUID purchaseOrderID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    PurchaseOrders purchaseOrders = new PurchaseOrders();
    PurchaseOrders response =
        accountingApi.updatePurchaseOrder(
            accessToken, xeroTenantId, purchaseOrderID, purchaseOrders);

    assertThat(
        response.getPurchaseOrders().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("d1d9b2cd-c9f2-4445-8d98-0b8096cf4dae"))));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Foobar")));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getUnitAmount(),
        is(equalTo(20.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxType(),
        is(equalTo("INPUT2")));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getAccountCode(),
        is(equalTo("710")));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(3.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getLineItems().get(0).getLineAmount(),
        is(equalTo(20.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 13))));
    assertThat(
        response.getPurchaseOrders().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(
        response.getPurchaseOrders().get(0).getPurchaseOrderNumber(), is(equalTo("PO-0005")));
    assertThat(response.getPurchaseOrders().get(0).getAttentionTo(), is(equalTo("Jimmy")));
    assertThat(
        response.getPurchaseOrders().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getPurchaseOrders().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.PurchaseOrder.StatusEnum.DRAFT)));
    assertThat(response.getPurchaseOrders().get(0).getSentToContact(), is(equalTo(false)));
    assertThat(
        response.getPurchaseOrders().get(0).getPurchaseOrderID(),
        is(equalTo(UUID.fromString("f9fc1120-c937-489e-84bc-e822190cfe9c"))));
    assertThat(response.getPurchaseOrders().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getPurchaseOrders().get(0).getSubTotal(), is(equalTo(20.0)));
    assertThat(response.getPurchaseOrders().get(0).getSubTotal().toString(), is(equalTo("20.0")));
    assertThat(response.getPurchaseOrders().get(0).getTotalTax(), is(equalTo(3.0)));
    assertThat(response.getPurchaseOrders().get(0).getTotalTax().toString(), is(equalTo("3.0")));
    assertThat(response.getPurchaseOrders().get(0).getTotal(), is(equalTo(23.0)));
    assertThat(response.getPurchaseOrders().get(0).getTotal().toString(), is(equalTo("23.0")));
    assertThat(response.getPurchaseOrders().get(0).getTotalDiscount(), is(equalTo(0.0)));
    assertThat(
        response.getPurchaseOrders().get(0).getTotalDiscount().toString(), is(equalTo("0.0")));
    assertThat(
        response.getPurchaseOrders().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-14T00:39:36.853Z"))));
    // System.out.println(response.getPurchaseOrders().get(0).toString());
  }
}
