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

public class AccountingApiItemsTest {

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

  @Test
  public void createItemTest() throws IOException {
    System.out.println("@Test - createItem");
    Items items = new Items();
    Items response = accountingApi.updateOrCreateItems(accessToken, xeroTenantId, items, false, 4);

    assertThat(response.getItems().get(0).getCode(), is(equalTo("abc65591")));
    assertThat(response.getItems().get(0).getName(), is(equalTo("Hello11350")));
    assertThat(response.getItems().get(0).getIsSold(), is(equalTo(true)));
    assertThat(response.getItems().get(0).getIsPurchased(), is(equalTo(true)));
    assertThat(response.getItems().get(0).getDescription(), is(equalTo("foobar")));
    assertThat(response.getItems().get(0).getIsTrackedAsInventory(), is(equalTo(false)));
    assertThat(
        response.getItems().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-11T19:17:51.707Z"))));
    assertThat(
        response.getItems().get(0).getItemID(),
        is(equalTo(UUID.fromString("a4544d51-48f6-441f-a623-99ecbced6ab7"))));
    assertThat(
        response.getItems().get(0).getValidationErrors().get(0).getMessage(),
        is(equalTo("Price List Item with Code 'abc' already exists")));
    // System.out.println(response.getItems().get(0).toString());
  }

  @Test
  public void getItemTest() throws IOException {
    System.out.println("@Test - getItem");
    UUID itemID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Items response = accountingApi.getItem(accessToken, xeroTenantId, itemID, 4);

    assertThat(response.getItems().get(0).getCode(), is(equalTo("123")));
    assertThat(response.getItems().get(0).getInventoryAssetAccountCode(), is(equalTo("630")));
    assertThat(response.getItems().get(0).getName(), is(equalTo("Guitars")));
    assertThat(response.getItems().get(0).getIsSold(), is(equalTo(true)));
    assertThat(response.getItems().get(0).getIsPurchased(), is(equalTo(true)));
    assertThat(response.getItems().get(0).getDescription(), is(equalTo("Guitars Fender Strat")));
    assertThat(
        response.getItems().get(0).getPurchaseDescription(),
        is(equalTo("Brand new Fender Strats")));
    assertThat(response.getItems().get(0).getPurchaseDetails().getUnitPrice(), is(equalTo(2500.0)));
    assertThat(
        response.getItems().get(0).getPurchaseDetails().getCoGSAccountCode(), is(equalTo("310")));
    assertThat(response.getItems().get(0).getPurchaseDetails().getTaxType(), is(equalTo("INPUT2")));
    assertThat(response.getItems().get(0).getSalesDetails().getUnitPrice(), is(equalTo(5000.0)));
    assertThat(response.getItems().get(0).getSalesDetails().getAccountCode(), is(equalTo("200")));
    assertThat(response.getItems().get(0).getSalesDetails().getTaxType(), is(equalTo("OUTPUT2")));
    assertThat(response.getItems().get(0).getIsTrackedAsInventory(), is(equalTo(true)));
    assertThat(response.getItems().get(0).getTotalCostPool(), is(equalTo(25000.0)));
    assertThat(response.getItems().get(0).getTotalCostPool().toString(), is(equalTo("25000.0")));
    assertThat(response.getItems().get(0).getQuantityOnHand(), is(equalTo(10.0)));
    assertThat(response.getItems().get(0).getQuantityOnHand().toString(), is(equalTo("10.0")));
    assertThat(
        response.getItems().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-11T19:41:49.387Z"))));
    assertThat(
        response.getItems().get(0).getItemID(),
        is(equalTo(UUID.fromString("c8c54d65-f3f2-452d-926e-bf450b12fb07"))));
    // System.out.println(response.getItems().get(0).toString());
  }

  @Test
  public void getItemsTest() throws IOException {
    System.out.println("@Test - getItems");
    OffsetDateTime ifModifiedSince = null;
    String where = null;
    String order = null;
    Integer unitdp = null;
    Items response =
        accountingApi.getItems(accessToken, xeroTenantId, ifModifiedSince, where, order, unitdp);

    assertThat(response.getItems().get(0).getCode(), is(equalTo("123")));
    assertThat(response.getItems().get(0).getName(), is(equalTo("Guitars")));
    assertThat(response.getItems().get(0).getIsSold(), is(equalTo(true)));
    assertThat(response.getItems().get(0).getIsPurchased(), is(equalTo(false)));
    assertThat(response.getItems().get(0).getDescription(), is(equalTo("Guitars Fender Strat")));
    assertThat(response.getItems().get(0).getSalesDetails().getUnitPrice(), is(equalTo(5000.0)));
    assertThat(response.getItems().get(0).getSalesDetails().getAccountCode(), is(equalTo("200")));
    assertThat(response.getItems().get(0).getSalesDetails().getTaxType(), is(equalTo("OUTPUT2")));
    assertThat(response.getItems().get(0).getIsTrackedAsInventory(), is(equalTo(false)));
    assertThat(
        response.getItems().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-07T17:57:56.267Z"))));
    assertThat(
        response.getItems().get(0).getItemID(),
        is(equalTo(UUID.fromString("c8c54d65-f3f2-452d-926e-bf450b12fb07"))));
    // System.out.println(response.getItems().get(0).toString());
  }

  @Test
  public void updateItemTest() throws IOException {
    System.out.println("@Test - updateItem");
    UUID itemID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Items items = new Items();
    Items response = accountingApi.updateItem(accessToken, xeroTenantId, itemID, items, 4);

    assertThat(response.getItems().get(0).getCode(), is(equalTo("abc38306")));
    assertThat(response.getItems().get(0).getName(), is(equalTo("Hello8746")));
    assertThat(response.getItems().get(0).getIsSold(), is(equalTo(true)));
    assertThat(response.getItems().get(0).getIsPurchased(), is(equalTo(true)));
    assertThat(response.getItems().get(0).getDescription(), is(equalTo("Hello Xero")));
    assertThat(response.getItems().get(0).getIsTrackedAsInventory(), is(equalTo(false)));
    assertThat(
        response.getItems().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-11T19:29:18.924Z"))));
    assertThat(
        response.getItems().get(0).getItemID(),
        is(equalTo(UUID.fromString("a7e87086-e0ae-4df2-83d7-e26e9a6b7786"))));
    // System.out.println(response.getItems().get(0).toString());
  }

  @Test
  public void deleteItemTest() throws IOException {
    System.out.println("@Test - deleteItem");
    UUID itemID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    accountingApi.deleteItem(accessToken, xeroTenantId, itemID);
  }
}
