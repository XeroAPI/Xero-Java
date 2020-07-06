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

public class AccountingApiLinkedTransactionsTest {

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
  public void createLinkedTransactionTest() throws IOException {
    System.out.println("@Test - createLinkedTransaction");
    LinkedTransaction linkedTransaction = new LinkedTransaction();
    LinkedTransactions response =
        accountingApi.createLinkedTransaction(accessToken, xeroTenantId, linkedTransaction);

    assertThat(
        response.getLinkedTransactions().get(0).getSourceTransactionID(),
        is(equalTo(UUID.fromString("a848644a-f20f-4630-98c3-386bd7505631"))));
    assertThat(
        response.getLinkedTransactions().get(0).getSourceLineItemID(),
        is(equalTo(UUID.fromString("b0df260d-3cc8-4ced-9bd6-41924f624ed3"))));
    assertThat(
        response.getLinkedTransactions().get(0).getLinkedTransactionID(),
        is(equalTo(UUID.fromString("e9684b6c-4df9-45a0-917b-85cc29857008"))));
    assertThat(
        response.getLinkedTransactions().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.LinkedTransaction.StatusEnum.DRAFT)));
    assertThat(
        response.getLinkedTransactions().get(0).getType(),
        is(equalTo(com.xero.models.accounting.LinkedTransaction.TypeEnum.BILLABLEEXPENSE)));
    assertThat(
        response.getLinkedTransactions().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-12T00:37:35Z"))));
    assertThat(
        response.getLinkedTransactions().get(0).getSourceTransactionTypeCode(),
        is(
            equalTo(
                com.xero.models.accounting.LinkedTransaction.SourceTransactionTypeCodeEnum
                    .ACCPAY)));
    assertThat(
        response.getLinkedTransactions().get(0).getValidationErrors().get(0).getMessage(),
        is(equalTo("The SourceLineItemID and SourceTransactionID do not match")));
    // System.out.println(response.getLinkedTransactions().get(0).toString());
  }

  @Test
  public void deleteLinkedTransactionTest() throws IOException {
    System.out.println("@Test - deleteLinkedTransaction");
    UUID linkedTransactionID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    accountingApi.deleteLinkedTransaction(accessToken, xeroTenantId, linkedTransactionID);
  }

  @Test
  public void getLinkedTransactionTest() throws IOException {
    System.out.println("@Test - getLinkedTransaction");
    UUID linkedTransactionID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    LinkedTransactions response =
        accountingApi.getLinkedTransaction(accessToken, xeroTenantId, linkedTransactionID);

    assertThat(
        response.getLinkedTransactions().get(0).getSourceTransactionID(),
        is(equalTo(UUID.fromString("aec416dd-38ea-40dc-9f0b-813c8c71f87f"))));
    assertThat(
        response.getLinkedTransactions().get(0).getSourceLineItemID(),
        is(equalTo(UUID.fromString("77e0b23b-5b79-4f4b-ae20-c9031d41442f"))));
    assertThat(
        response.getLinkedTransactions().get(0).getContactID(),
        is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
    assertThat(
        response.getLinkedTransactions().get(0).getTargetTransactionID(),
        is(equalTo(UUID.fromString("83693fc1-5b05-4807-b190-109d4a85dd5f"))));
    assertThat(
        response.getLinkedTransactions().get(0).getTargetLineItemID(),
        is(equalTo(UUID.fromString("d5128ff1-0f39-4d2a-a6d5-46dfaf5f075c"))));
    assertThat(
        response.getLinkedTransactions().get(0).getLinkedTransactionID(),
        is(equalTo(UUID.fromString("5cf7d9c0-b9a7-4433-a2dc-ae3c11bba39b"))));
    assertThat(
        response.getLinkedTransactions().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.LinkedTransaction.StatusEnum.ONDRAFT)));
    assertThat(
        response.getLinkedTransactions().get(0).getType(),
        is(equalTo(com.xero.models.accounting.LinkedTransaction.TypeEnum.BILLABLEEXPENSE)));
    assertThat(
        response.getLinkedTransactions().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-11T23:46:31Z"))));
    assertThat(
        response.getLinkedTransactions().get(0).getSourceTransactionTypeCode(),
        is(
            equalTo(
                com.xero.models.accounting.LinkedTransaction.SourceTransactionTypeCodeEnum
                    .ACCPAY)));
    // System.out.println(response.getLinkedTransactions().get(0).toString());
  }

  @Test
  public void getLinkedTransactionsTest() throws IOException {
    System.out.println("@Test - getLinkedTransactions");
    Integer page = null;
    String linkedTransactionID = null;
    String sourceTransactionID = null;
    String contactID = null;
    String status = null;
    String targetTransactionID = null;
    LinkedTransactions response =
        accountingApi.getLinkedTransactions(
            accessToken,
            xeroTenantId,
            page,
            linkedTransactionID,
            sourceTransactionID,
            contactID,
            status,
            targetTransactionID);

    assertThat(
        response.getLinkedTransactions().get(0).getSourceTransactionID(),
        is(equalTo(UUID.fromString("aec416dd-38ea-40dc-9f0b-813c8c71f87f"))));
    assertThat(
        response.getLinkedTransactions().get(0).getSourceLineItemID(),
        is(equalTo(UUID.fromString("77e0b23b-5b79-4f4b-ae20-c9031d41442f"))));
    assertThat(
        response.getLinkedTransactions().get(0).getContactID(),
        is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
    assertThat(
        response.getLinkedTransactions().get(0).getTargetTransactionID(),
        is(equalTo(UUID.fromString("83693fc1-5b05-4807-b190-109d4a85dd5f"))));
    assertThat(
        response.getLinkedTransactions().get(0).getTargetLineItemID(),
        is(equalTo(UUID.fromString("d5128ff1-0f39-4d2a-a6d5-46dfaf5f075c"))));
    assertThat(
        response.getLinkedTransactions().get(0).getLinkedTransactionID(),
        is(equalTo(UUID.fromString("5cf7d9c0-b9a7-4433-a2dc-ae3c11bba39b"))));
    assertThat(
        response.getLinkedTransactions().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.LinkedTransaction.StatusEnum.ONDRAFT)));
    assertThat(
        response.getLinkedTransactions().get(0).getType(),
        is(equalTo(com.xero.models.accounting.LinkedTransaction.TypeEnum.BILLABLEEXPENSE)));
    assertThat(
        response.getLinkedTransactions().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-11T23:46:31Z"))));
    assertThat(
        response.getLinkedTransactions().get(0).getSourceTransactionTypeCode(),
        is(
            equalTo(
                com.xero.models.accounting.LinkedTransaction.SourceTransactionTypeCodeEnum
                    .ACCPAY)));
    // System.out.println(response.getLinkedTransactions().get(0).toString());
  }

  @Test
  public void updateLinkedTransactionTest() throws IOException {
    System.out.println("@Test - updateLinkedTransaction");
    UUID linkedTransactionID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    LinkedTransactions linkedTransactions = new LinkedTransactions();
    LinkedTransactions response =
        accountingApi.updateLinkedTransaction(
            accessToken, xeroTenantId, linkedTransactionID, linkedTransactions);

    assertThat(
        response.getLinkedTransactions().get(0).getSourceTransactionID(),
        is(equalTo(UUID.fromString("a848644a-f20f-4630-98c3-386bd7505631"))));
    assertThat(
        response.getLinkedTransactions().get(0).getSourceLineItemID(),
        is(equalTo(UUID.fromString("b0df260d-3cc8-4ced-9bd6-41924f624ed3"))));
    assertThat(
        response.getLinkedTransactions().get(0).getContactID(),
        is(equalTo(UUID.fromString("4e1753b9-018a-4775-b6aa-1bc7871cfee3"))));
    assertThat(
        response.getLinkedTransactions().get(0).getLinkedTransactionID(),
        is(equalTo(UUID.fromString("e9684b6c-4df9-45a0-917b-85cc29857008"))));
    assertThat(
        response.getLinkedTransactions().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.LinkedTransaction.StatusEnum.DRAFT)));
    assertThat(
        response.getLinkedTransactions().get(0).getType(),
        is(equalTo(com.xero.models.accounting.LinkedTransaction.TypeEnum.BILLABLEEXPENSE)));
    assertThat(
        response.getLinkedTransactions().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-12T00:37:35Z"))));
    assertThat(
        response.getLinkedTransactions().get(0).getSourceTransactionTypeCode(),
        is(
            equalTo(
                com.xero.models.accounting.LinkedTransaction.SourceTransactionTypeCodeEnum
                    .ACCPAY)));
    // System.out.println(response.getLinkedTransactions().get(0).toString());
  }
}
