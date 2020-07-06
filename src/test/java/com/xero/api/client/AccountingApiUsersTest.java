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

public class AccountingApiUsersTest {

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
  public void getUserTest() throws IOException {
    System.out.println("@Test - getUser");
    UUID userID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Users response = accountingApi.getUser(accessToken, xeroTenantId, userID);

    assertThat(
        response.getUsers().get(0).getUserID(),
        is(equalTo(UUID.fromString("3c37ef1d-cd49-4589-9787-3c418ed8b6ac"))));
    assertThat(response.getUsers().get(0).getEmailAddress(), is(equalTo("sid.maestre@xero.com")));
    assertThat(response.getUsers().get(0).getFirstName(), is(equalTo("Sidney")));
    assertThat(response.getUsers().get(0).getLastName(), is(equalTo("Maestre")));
    assertThat(
        response.getUsers().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2017-10-20T18:14:21.613Z"))));
    assertThat(response.getUsers().get(0).getIsSubscriber(), is(equalTo(false)));
    assertThat(
        response.getUsers().get(0).getOrganisationRole(),
        is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
    // System.out.println(response.getUsers().get(0).toString());
  }

  @Test
  public void getUsersTest() throws IOException {
    System.out.println("@Test - getUsers");
    OffsetDateTime ifModifiedSince = null;
    String where = null;
    String order = null;
    Users response =
        accountingApi.getUsers(accessToken, xeroTenantId, ifModifiedSince, where, order);

    assertThat(
        response.getUsers().get(0).getUserID(),
        is(equalTo(UUID.fromString("3c37ef1d-cd49-4589-9787-3c418ed8b6ac"))));
    assertThat(response.getUsers().get(0).getEmailAddress(), is(equalTo("sid.maestre@xero.com")));
    assertThat(response.getUsers().get(0).getFirstName(), is(equalTo("Sidney")));
    assertThat(response.getUsers().get(0).getLastName(), is(equalTo("Maestre")));
    assertThat(
        response.getUsers().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2017-10-20T18:14:21.613Z"))));
    assertThat(response.getUsers().get(0).getIsSubscriber(), is(equalTo(false)));
    assertThat(
        response.getUsers().get(0).getOrganisationRole(),
        is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
    // System.out.println(response.getUsers().toString());
  }
}
