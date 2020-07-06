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

public class AccountingApiBankTransferTest {

  ApiClient defaultClient;
  AccountingApi accountingApi;
  String accessToken;
  String xeroTenantId;

  File bytes;

  private static boolean setUpIsDone = false;

  @Before
  public void setUp() {
    // Set Access Token and Tenant Id
    accessToken = "123";
    xeroTenantId = "xyz";

    // NEW Sandbox for API Mocking
    defaultClient =
        new ApiClient(
            "https://xero-accounting.getsandbox.com:443/api.xro/2.0", null, null, null, null);

    accountingApi = AccountingApi.getInstance(defaultClient);

    ClassLoader classLoader = getClass().getClassLoader();
    bytes = new File(classLoader.getResource("helo-heros.jpg").getFile());

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
  public void getBankTransfersTest() throws Exception {
    System.out.println("@Test - getBankTransfers");

    OffsetDateTime ifModifiedSince = null;
    String where = null;
    String order = null;

    BankTransfers response =
        accountingApi.getBankTransfers(accessToken, xeroTenantId, ifModifiedSince, where, order);
    assertThat(
        response.getBankTransfers().get(0).getBankTransferID(),
        is(equalTo(UUID.fromString("6221458a-ef7a-4d5f-9b1c-1b96ce03833c"))));
    assertThat(
        response.getBankTransfers().get(0).getFromBankTransactionID(),
        is(equalTo(UUID.fromString("a3eca480-bc04-4292-9bbd-5c57b8ba12b4"))));
    assertThat(
        response.getBankTransfers().get(0).getToBankTransactionID(),
        is(equalTo(UUID.fromString("4ca13f40-f3a0-4530-a442-a600f5696118"))));
    assertThat(
        response.getBankTransfers().get(0).getFromBankAccount().getName(),
        is(equalTo("Business Wells Fargo")));
    assertThat(
        response.getBankTransfers().get(0).getFromBankAccount().getAccountID(),
        is(equalTo(UUID.fromString("6f7594f2-f059-4d56-9e67-47ac9733bfe9"))));
    assertThat(
        response.getBankTransfers().get(0).getToBankAccount().getName(), is(equalTo("My Savings")));
    assertThat(
        response.getBankTransfers().get(0).getToBankAccount().getAccountID(),
        is(equalTo(UUID.fromString("7e5e243b-9fcd-4aef-8e3a-c70be1e39bfa"))));
    // System.out.println(response.getBankTransfers().get(0).toString());
  }

  @Test
  public void getBankTransferTest() throws IOException {
    System.out.println("@Test - getBankTransfer");

    UUID bankTransferID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
    BankTransfers response =
        accountingApi.getBankTransfer(accessToken, xeroTenantId, bankTransferID);
    assertThat(
        response.getBankTransfers().get(0).getBankTransferID(),
        is(equalTo(UUID.fromString("6221458a-ef7a-4d5f-9b1c-1b96ce03833c"))));
    assertThat(
        response.getBankTransfers().get(0).getFromBankTransactionID(),
        is(equalTo(UUID.fromString("a3eca480-bc04-4292-9bbd-5c57b8ba12b4"))));
    assertThat(
        response.getBankTransfers().get(0).getToBankTransactionID(),
        is(equalTo(UUID.fromString("4ca13f40-f3a0-4530-a442-a600f5696118"))));
    assertThat(
        response.getBankTransfers().get(0).getFromBankAccount().getName(),
        is(equalTo("Business Wells Fargo")));
    assertThat(
        response.getBankTransfers().get(0).getFromBankAccount().getAccountID(),
        is(equalTo(UUID.fromString("6f7594f2-f059-4d56-9e67-47ac9733bfe9"))));
    assertThat(
        response.getBankTransfers().get(0).getToBankAccount().getName(), is(equalTo("My Savings")));
    assertThat(
        response.getBankTransfers().get(0).getToBankAccount().getAccountID(),
        is(equalTo(UUID.fromString("7e5e243b-9fcd-4aef-8e3a-c70be1e39bfa"))));
    // System.out.println(response.getBankTransfers().get(0).toString());
  }

  @Test
  public void createBankTransferTest() throws IOException {
    System.out.println("@Test - createBankTransfer");

    BankTransfers bankTransfers = new BankTransfers();
    BankTransfers response =
        accountingApi.createBankTransfer(accessToken, xeroTenantId, bankTransfers);
    assertThat(
        response.getBankTransfers().get(0).getBankTransferID(),
        is(equalTo(UUID.fromString("76eea4b6-f026-464c-b6f3-5fb39a196145"))));
    assertThat(
        response.getBankTransfers().get(0).getFromBankTransactionID(),
        is(equalTo(UUID.fromString("e4059952-5acb-4a56-b076-53fad85f2930"))));
    assertThat(
        response.getBankTransfers().get(0).getToBankAccount().getName(),
        is(equalTo("Business Wells Fargo")));
    assertThat(
        response.getBankTransfers().get(0).getToBankAccount().getAccountID(),
        is(equalTo(UUID.fromString("6f7594f2-f059-4d56-9e67-47ac9733bfe9"))));
    assertThat(
        response.getBankTransfers().get(0).getFromBankAccount().getName(),
        is(equalTo("My Savings")));
    assertThat(
        response.getBankTransfers().get(0).getFromBankAccount().getAccountID(),
        is(equalTo(UUID.fromString("7e5e243b-9fcd-4aef-8e3a-c70be1e39bfa"))));
    // System.out.println(response.getBankTransfers().get(0).toString());
  }

  @Test
  public void createBankTransferAttachmentByFileNameTest() throws IOException {
    System.out.println("@Test - createBankTransferAttachmentByFileNameTest");

    UUID bankTransferID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
    String fileName = "sample5.jpg";

    Attachments response =
        accountingApi.createBankTransferAttachmentByFileName(
            accessToken, xeroTenantId, bankTransferID, fileName, bytes);
    assertThat(
        response.getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("9478be4c-c707-48c1-b4a7-83d8eaf442b5"))));
    assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("sample5.jpg")));
    assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
    assertThat(
        response.getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/BankTransfers/6221458a-ef7a-4d5f-9b1c-1b96ce03833c/Attachments/sample5.jpg")));
    // System.out.println(response.getAttachments().get(0).toString());
  }

  @Test
  public void getBankTransferAttachmentsTest() throws IOException {
    System.out.println("@Test - getBankTransferAttachmentsTest");

    UUID bankTransferID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
    Attachments response =
        accountingApi.getBankTransferAttachments(accessToken, xeroTenantId, bankTransferID);
    assertThat(
        response.getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("e05a6fd8-0e47-47a9-9799-b809c8267260"))));
    assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
    assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
    assertThat(
        response.getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/BankTransfers/6221458a-ef7a-4d5f-9b1c-1b96ce03833c/Attachments/HelloWorld.jpg")));
    // System.out.println(response.getAttachments().get(0).toString());
  }
  /*
  @Test
  public void updateBankTransferAttachmentByFileNameTest() throws IOException {
      System.out.println("@Test - updateBankTransferAttachmentByFileNameTest");

      UUID bankTransferID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
      String fileName = "sample5.jpg";

      Attachments response = accountingApi.updateBankTransferAttachmentByFileName(bankTransferID, fileName, bytes);
      assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("0851935c-c4c5-4de8-9247-ce22efde6f82"))));
      assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("sample5.jpg")));
      assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
      assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/BankTransfers/6221458a-ef7a-4d5f-9b1c-1b96ce03833c/Attachments/sample5.jpg")));
      //System.out.println(response.getAttachments().get(0).toString());
  }
  */
}
