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

public class AccountingApiJournalsTest {

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
  public void getJournalTest() throws IOException {
    System.out.println("@Test - getJournal");
    UUID journalID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Journals response = accountingApi.getJournal(accessToken, xeroTenantId, journalID);

    assertThat(
        response.getJournals().get(0).getJournalID(),
        is(equalTo(UUID.fromString("1b31feeb-aa23-404c-8c19-24c827c53661"))));
    assertThat(
        response.getJournals().get(0).getJournalDateAsDate(),
        is(equalTo(LocalDate.of(2018, 10, 20))));
    assertThat(response.getJournals().get(0).getJournalNumber(), is(equalTo(1)));
    assertThat(
        response.getJournals().get(0).getCreatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2018-11-02T16:30:43.467Z"))));
    assertThat(response.getJournals().get(0).getReference(), is(equalTo("Red Fish, Blue Fish")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getJournalLineID(),
        is(equalTo(UUID.fromString("81e6b1bf-d812-4f87-8dc4-698558ae043e"))));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getAccountID(),
        is(equalTo(UUID.fromString("b94495d0-44ab-4199-a1d0-427a4877e100"))));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getAccountCode(),
        is(equalTo("610")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getAccountType(),
        is(equalTo(com.xero.models.accounting.AccountType.CURRENT)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getAccountName(),
        is(equalTo("Accounts Receivable")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getNetAmount(), is(equalTo(40.0)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getNetAmount().toString(),
        is(equalTo("40.0")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getGrossAmount(), is(equalTo(40.0)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getGrossAmount().toString(),
        is(equalTo("40.0")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getTaxAmount(), is(equalTo(0.0)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getTaxAmount().toString(),
        is(equalTo("0.0")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getJournalLineID(),
        is(equalTo(UUID.fromString("ad221a8c-ebee-4c1b-88ed-d574e27e8803"))));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getAccountID(),
        is(equalTo(UUID.fromString("e0a5d892-9f9f-44f0-a153-5cb7db125170"))));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getAccountCode(),
        is(equalTo("200")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getAccountType(),
        is(equalTo(com.xero.models.accounting.AccountType.REVENUE)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getAccountName(),
        is(equalTo("Sales")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getDescription(),
        is(equalTo("Acme Tires")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getNetAmount(), is(equalTo(-40.0)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getNetAmount().toString(),
        is(equalTo("-40.0")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getGrossAmount(),
        is(equalTo(-40.0)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getGrossAmount().toString(),
        is(equalTo("-40.0")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getTaxAmount(), is(equalTo(0.0)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getTaxAmount().toString(),
        is(equalTo("0.0")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getTaxType(), is(equalTo("NONE")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getTaxName(), is(equalTo("No GST")));
    // System.out.println(response.getJournals().get(0).toString());
  }

  @Test
  public void getJournalsTest() throws IOException {
    System.out.println("@Test - getJournals");
    OffsetDateTime ifModifiedSince = null;
    Integer offset = null;
    Boolean paymentsOnly = null;
    Journals response =
        accountingApi.getJournals(accessToken, xeroTenantId, ifModifiedSince, offset, paymentsOnly);

    assertThat(
        response.getJournals().get(0).getJournalID(),
        is(equalTo(UUID.fromString("1b31feeb-aa23-404c-8c19-24c827c53661"))));
    assertThat(
        response.getJournals().get(0).getJournalDateAsDate(),
        is(equalTo(LocalDate.of(2018, 10, 20))));
    assertThat(response.getJournals().get(0).getJournalNumber(), is(equalTo(1)));
    assertThat(
        response.getJournals().get(0).getCreatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2018-11-02T16:30:43.467Z"))));
    assertThat(response.getJournals().get(0).getReference(), is(equalTo("Red Fish, Blue Fish")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getJournalLineID(),
        is(equalTo(UUID.fromString("81e6b1bf-d812-4f87-8dc4-698558ae043e"))));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getAccountID(),
        is(equalTo(UUID.fromString("b94495d0-44ab-4199-a1d0-427a4877e100"))));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getAccountCode(),
        is(equalTo("610")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getAccountType(),
        is(equalTo(com.xero.models.accounting.AccountType.CURRENT)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getAccountName(),
        is(equalTo("Accounts Receivable")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getNetAmount(), is(equalTo(40.0)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getNetAmount().toString(),
        is(equalTo("40.0")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getGrossAmount(), is(equalTo(40.0)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getGrossAmount().toString(),
        is(equalTo("40.0")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getTaxAmount(), is(equalTo(0.0)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(0).getTaxAmount().toString(),
        is(equalTo("0.0")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getJournalLineID(),
        is(equalTo(UUID.fromString("ad221a8c-ebee-4c1b-88ed-d574e27e8803"))));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getAccountID(),
        is(equalTo(UUID.fromString("e0a5d892-9f9f-44f0-a153-5cb7db125170"))));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getAccountCode(),
        is(equalTo("200")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getAccountType(),
        is(equalTo(com.xero.models.accounting.AccountType.REVENUE)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getAccountName(),
        is(equalTo("Sales")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getDescription(),
        is(equalTo("Acme Tires")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getNetAmount(), is(equalTo(-40.0)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getNetAmount().toString(),
        is(equalTo("-40.0")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getGrossAmount(),
        is(equalTo(-40.0)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getGrossAmount().toString(),
        is(equalTo("-40.0")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getTaxAmount(), is(equalTo(0.0)));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getTaxAmount().toString(),
        is(equalTo("0.0")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getTaxType(), is(equalTo("NONE")));
    assertThat(
        response.getJournals().get(0).getJournalLines().get(1).getTaxName(), is(equalTo("No GST")));
    // System.out.println(response.getJournals().get(0).toString());
  }
}
