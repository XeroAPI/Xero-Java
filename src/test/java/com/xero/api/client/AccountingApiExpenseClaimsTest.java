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

public class AccountingApiExpenseClaimsTest {

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
  public void createExpenseClaimTest() throws IOException {
    System.out.println("@Test - createExpenseClaim");
    ExpenseClaims expenseClaims = new ExpenseClaims();
    ExpenseClaims response =
        accountingApi.createExpenseClaims(accessToken, xeroTenantId, expenseClaims);

    assertThat(
        response.getExpenseClaims().get(0).getExpenseClaimID(),
        is(equalTo(UUID.fromString("646b15ab-b874-4e13-82ae-f4385b2ac4b6"))));
    assertThat(
        response.getExpenseClaims().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.ExpenseClaim.StatusEnum.SUBMITTED)));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getUserID(),
        is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
    assertThat(response.getExpenseClaims().get(0).getUser().getFirstName(), is(equalTo("API ")));
    assertThat(response.getExpenseClaims().get(0).getUser().getLastName(), is(equalTo("Team")));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getEmailAddress(),
        is(equalTo("api@xero.com")));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2017-11-29T12:06:19.217Z"))));
    assertThat(response.getExpenseClaims().get(0).getUser().getIsSubscriber(), is(equalTo(true)));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getOrganisationRole(),
        is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
    assertThat(
        response.getExpenseClaims().get(0).getReceipts().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 12))));
    assertThat(
        response.getExpenseClaims().get(0).getReceipts().get(0).getReceiptID(),
        is(equalTo(UUID.fromString("dc1c7f6d-0a4c-402f-acac-551d62ce5816"))));
    assertThat(
        response.getExpenseClaims().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-11T17:37:30.107Z"))));
    assertThat(response.getExpenseClaims().get(0).getTotal(), is(equalTo(40.0)));
    assertThat(response.getExpenseClaims().get(0).getTotal().toString(), is(equalTo("40.0")));
    assertThat(response.getExpenseClaims().get(0).getAmountDue(), is(equalTo(40.0)));
    assertThat(response.getExpenseClaims().get(0).getAmountDue().toString(), is(equalTo("40.0")));
    assertThat(response.getExpenseClaims().get(0).getAmountPaid(), is(equalTo(0.0)));
    assertThat(response.getExpenseClaims().get(0).getAmountPaid().toString(), is(equalTo("0.0")));
    // assertThat(response.getExpenseClaims().get(0).getPayments(),
    // is(equalTo("CONTAINER_PLACEHOLDER")));
    // assertThat(response.getExpenseClaims().get(0).getPaymentDueDate(),
    // is(equalTo(LocalDate.of(YYYY, MM, DD))));
    // assertThat(response.getExpenseClaims().get(0).getReportingDate(),
    // is(equalTo(LocalDate.of(YYYY, MM, DD))));
    // assertThat(response.getExpenseClaims().get(0).getReceiptID(),
    // is(equalTo(UUID.fromString("UUID_PLACEHOLDER"))));
    // System.out.println(response.getExpenseClaims().get(0).toString());
  }

  @Test
  public void getExpenseClaimTest() throws IOException {
    System.out.println("@Test - getExpenseClaim");
    UUID expenseClaimID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    ExpenseClaims response =
        accountingApi.getExpenseClaim(accessToken, xeroTenantId, expenseClaimID);

    assertThat(
        response.getExpenseClaims().get(0).getExpenseClaimID(),
        is(equalTo(UUID.fromString("646b15ab-b874-4e13-82ae-f4385b2ac4b6"))));
    assertThat(
        response.getExpenseClaims().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.ExpenseClaim.StatusEnum.AUTHORISED)));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getUserID(),
        is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
    assertThat(response.getExpenseClaims().get(0).getUser().getFirstName(), is(equalTo("API ")));
    assertThat(response.getExpenseClaims().get(0).getUser().getLastName(), is(equalTo("Team")));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getEmailAddress(),
        is(equalTo("api@xero.com")));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2017-11-29T12:06:19.217Z"))));
    assertThat(response.getExpenseClaims().get(0).getUser().getIsSubscriber(), is(equalTo(true)));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getOrganisationRole(),
        is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
    assertThat(
        response.getExpenseClaims().get(0).getReceipts().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 12))));
    assertThat(
        response.getExpenseClaims().get(0).getReceipts().get(0).getReceiptID(),
        is(equalTo(UUID.fromString("dc1c7f6d-0a4c-402f-acac-551d62ce5816"))));
    assertThat(
        response.getExpenseClaims().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-11T17:37:31.767Z"))));
    assertThat(response.getExpenseClaims().get(0).getTotal(), is(equalTo(40.0)));
    assertThat(response.getExpenseClaims().get(0).getTotal().toString(), is(equalTo("40.0")));
    assertThat(response.getExpenseClaims().get(0).getAmountDue(), is(equalTo(40.0)));
    assertThat(response.getExpenseClaims().get(0).getAmountDue().toString(), is(equalTo("40.0")));
    assertThat(response.getExpenseClaims().get(0).getAmountPaid(), is(equalTo(0.0)));
    assertThat(response.getExpenseClaims().get(0).getAmountPaid().toString(), is(equalTo("0.0")));
    // System.out.println(response.getExpenseClaims().get(0).toString());
  }

  @Test
  public void getExpenseClaimsTest() throws IOException {
    System.out.println("@Test - getExpenseClaims");
    OffsetDateTime ifModifiedSince = null;
    String where = null;
    String order = null;
    ExpenseClaims response =
        accountingApi.getExpenseClaims(accessToken, xeroTenantId, ifModifiedSince, where, order);

    // TODO: test validations
    assertThat(
        response.getExpenseClaims().get(0).getExpenseClaimID(),
        is(equalTo(UUID.fromString("646b15ab-b874-4e13-82ae-f4385b2ac4b6"))));
    assertThat(
        response.getExpenseClaims().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.ExpenseClaim.StatusEnum.AUTHORISED)));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getUserID(),
        is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
    assertThat(response.getExpenseClaims().get(0).getUser().getFirstName(), is(equalTo("API ")));
    assertThat(response.getExpenseClaims().get(0).getUser().getLastName(), is(equalTo("Team")));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getEmailAddress(),
        is(equalTo("api@xero.com")));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2017-11-29T12:06:19.217Z"))));
    assertThat(response.getExpenseClaims().get(0).getUser().getIsSubscriber(), is(equalTo(true)));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getOrganisationRole(),
        is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
    assertThat(
        response.getExpenseClaims().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-11T17:37:31.767Z"))));
    assertThat(response.getExpenseClaims().get(0).getTotal(), is(equalTo(40.0)));
    assertThat(response.getExpenseClaims().get(0).getTotal().toString(), is(equalTo("40.0")));
    assertThat(response.getExpenseClaims().get(0).getAmountDue(), is(equalTo(40.0)));
    assertThat(response.getExpenseClaims().get(0).getAmountDue().toString(), is(equalTo("40.0")));
    assertThat(response.getExpenseClaims().get(0).getAmountPaid(), is(equalTo(0.0)));
    assertThat(response.getExpenseClaims().get(0).getAmountPaid().toString(), is(equalTo("0.0")));
    // System.out.println(response.getExpenseClaims().get(0).toString());
  }

  @Test
  public void updateExpenseClaimTest() throws IOException {
    System.out.println("@Test - updateExpenseClaim");
    UUID expenseClaimID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    ExpenseClaims expenseClaims = new ExpenseClaims();
    ExpenseClaims response =
        accountingApi.updateExpenseClaim(accessToken, xeroTenantId, expenseClaimID, expenseClaims);

    // TODO: test validations
    assertThat(
        response.getExpenseClaims().get(0).getExpenseClaimID(),
        is(equalTo(UUID.fromString("646b15ab-b874-4e13-82ae-f4385b2ac4b6"))));
    assertThat(
        response.getExpenseClaims().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.ExpenseClaim.StatusEnum.AUTHORISED)));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getUserID(),
        is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
    assertThat(response.getExpenseClaims().get(0).getUser().getFirstName(), is(equalTo("API ")));
    assertThat(response.getExpenseClaims().get(0).getUser().getLastName(), is(equalTo("Team")));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getEmailAddress(),
        is(equalTo("api@xero.com")));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2017-11-29T12:06:19.217Z"))));
    assertThat(response.getExpenseClaims().get(0).getUser().getIsSubscriber(), is(equalTo(true)));
    assertThat(
        response.getExpenseClaims().get(0).getUser().getOrganisationRole(),
        is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
    assertThat(
        response.getExpenseClaims().get(0).getReceipts().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 12))));
    assertThat(
        response.getExpenseClaims().get(0).getReceipts().get(0).getReceiptID(),
        is(equalTo(UUID.fromString("dc1c7f6d-0a4c-402f-acac-551d62ce5816"))));
    assertThat(
        response.getExpenseClaims().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-11T17:37:31.767Z"))));
    assertThat(response.getExpenseClaims().get(0).getTotal(), is(equalTo(40.0)));
    assertThat(response.getExpenseClaims().get(0).getTotal().toString(), is(equalTo("40.0")));
    assertThat(response.getExpenseClaims().get(0).getAmountDue(), is(equalTo(40.0)));
    assertThat(response.getExpenseClaims().get(0).getAmountDue().toString(), is(equalTo("40.0")));
    assertThat(response.getExpenseClaims().get(0).getAmountPaid(), is(equalTo(0.0)));
    assertThat(response.getExpenseClaims().get(0).getAmountPaid().toString(), is(equalTo("0.0")));
    // System.out.println(response.getExpenseClaims().get(0).toString());
  }
}
