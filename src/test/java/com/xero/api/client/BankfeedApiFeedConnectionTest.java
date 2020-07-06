package com.xero.api.client;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import com.xero.api.ApiClient;
import com.xero.models.bankfeeds.FeedConnection;
import com.xero.models.bankfeeds.FeedConnections;

import org.junit.Before;
import org.junit.Test;

public class BankfeedApiFeedConnectionTest {

  ApiClient defaultClient;
  BankFeedsApi bankfeedsApi;
  String accessToken;
  String xeroTenantId;

  private static boolean setUpIsDone = false;

  @Before
  public void setUp() {

    // Set Access Token and Tenant Id
    accessToken = "123";
    xeroTenantId = "xyz";

    // Init AccountingApi client
    // defaultClient = new
    // ApiClient("https://virtserver.swaggerhub.com/Xero/bankfeeds/1.0.0",null,null,null,null);
    defaultClient =
        new ApiClient(
            "https://xero-bank-feeds.getsandbox.com:443/bankfeeds.xro/1.0", null, null, null, null);
    bankfeedsApi = BankFeedsApi.getInstance(defaultClient);

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
    bankfeedsApi = null;
    defaultClient = null;
  }

  @Test
  public void testCreateFeedConnections() throws Exception {
    System.out.println("@Test - createFeedConnections");

    FeedConnections feedConnections = new FeedConnections();
    FeedConnections response =
        bankfeedsApi.createFeedConnections(accessToken, xeroTenantId, feedConnections);
    assertThat(
        response.getItems().get(0).getId().toString(),
        (equalTo("2a19d46c-2a92-4e50-9401-dcf2cb895be7")));
    assertThat(response.getItems().get(0).getAccountToken(), (equalTo("foobar71760")));
    assertThat(
        response.getItems().get(0).getStatus(),
        is(equalTo(com.xero.models.bankfeeds.FeedConnection.StatusEnum.PENDING)));
  }

  @Test
  public void testGetFeedConnections() throws Exception {
    System.out.println("@Test - getFeedConnections");
    FeedConnections response =
        bankfeedsApi.getFeedConnections(accessToken, xeroTenantId, null, null);

    assertThat(response.getPagination().getItemCount().toString(), (equalTo("39")));
    assertThat(response.getPagination().getPageCount().toString(), (equalTo("1")));
    assertThat(response.getPagination().getPage().toString(), (equalTo("1")));
    assertThat(response.getPagination().getPageSize().toString(), (equalTo("87654321")));
    assertThat(
        response.getItems().get(0).getId().toString(),
        (equalTo("c0eb97b5-4f97-465a-8268-276513c14396")));
    assertThat(response.getItems().get(0).getAccountToken(), (equalTo("foobar31306")));
    assertThat(response.getItems().get(0).getAccountNumber(), (equalTo("123496842")));
    assertThat(response.getItems().get(0).getAccountName(), (equalTo("SDK Bank 95921")));
    assertThat(
        response.getItems().get(0).getAccountId().toString(),
        (equalTo("aefbf6be-4285-4ca5-bf39-0f486c8515c7")));
    assertThat(
        response.getItems().get(0).getAccountType(),
        is(equalTo(com.xero.models.bankfeeds.FeedConnection.AccountTypeEnum.BANK)));
    assertThat(
        response.getItems().get(0).getCurrency(),
        is(equalTo(com.xero.models.bankfeeds.CurrencyCode.GBP)));
    assertThat(
        response.getItems().get(0).getCountry(),
        is(equalTo(com.xero.models.bankfeeds.CountryCode.GB)));
  }

  @Test
  public void testGetFeedConnection() throws Exception {
    System.out.println("@Test - getFeedConnection");
    UUID feedConnectionId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    FeedConnection response =
        bankfeedsApi.getFeedConnection(accessToken, xeroTenantId, feedConnectionId);

    assertThat(response.getId().toString(), (equalTo("b58b685a-1bee-4904-91f1-fee30bb6ea52")));
    assertThat(response.getAccountToken(), (equalTo("foobar84778")));
    assertThat(response.getAccountNumber(), (equalTo("123434859")));
    assertThat(response.getAccountName(), (equalTo("SDK Bank 5517")));
    assertThat(
        response.getAccountId().toString(), (equalTo("f4c4d595-da94-493b-999a-19d1ae1f508a")));
    assertThat(
        response.getAccountType(),
        is(equalTo(com.xero.models.bankfeeds.FeedConnection.AccountTypeEnum.BANK)));
    assertThat(response.getCurrency(), is(equalTo(com.xero.models.bankfeeds.CurrencyCode.GBP)));
    assertThat(response.getCountry(), is(equalTo(com.xero.models.bankfeeds.CountryCode.GB)));
  }

  @Test
  public void testDeleteFeedConnection() throws Exception {
    System.out.println("@Test - deleteFeedConnection");

    FeedConnections deleteFeedConnections = new FeedConnections();
    FeedConnections response =
        bankfeedsApi.deleteFeedConnections(accessToken, xeroTenantId, deleteFeedConnections);
    assertThat(
        response.getItems().get(0).getId().toString(),
        (equalTo("b4cc693b-24d9-42ec-a6d4-2943d253ff63")));
    assertThat(
        response.getItems().get(0).getStatus(),
        is(equalTo(com.xero.models.bankfeeds.FeedConnection.StatusEnum.PENDING)));
    assertThat(response.getItems().get(1).getAccountToken(), (equalTo("10000125")));
    assertThat(
        response.getItems().get(1).getStatus(),
        is(equalTo(com.xero.models.bankfeeds.FeedConnection.StatusEnum.REJECTED)));
    assertThat(
        response.getItems().get(1).getError().getDetail(),
        (equalTo(
            "The AccountToken is connected to another Xero Bank Account associated with this bank."
                + " This Xero Bank Account belongs to a different Xero Organisation.")));
    assertThat(
        response.getItems().get(1).getError().getTitle(),
        (equalTo("Feed connected in different organisation")));
  }
}
