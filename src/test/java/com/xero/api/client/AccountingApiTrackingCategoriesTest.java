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

public class AccountingApiTrackingCategoriesTest {

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
  public void createTrackingCategoryTest() throws IOException {
    System.out.println("@Test - createTrackingCategory");
    TrackingCategory trackingCategory = new TrackingCategory();
    TrackingCategories response =
        accountingApi.createTrackingCategory(accessToken, xeroTenantId, trackingCategory);

    assertThat(
        response.getTrackingCategories().get(0).getTrackingCategoryID(),
        is(equalTo(UUID.fromString("b1df776b-b093-4730-b6ea-590cca40e723"))));
    assertThat(response.getTrackingCategories().get(0).getName(), is(equalTo("FooBar")));
    assertThat(
        response.getTrackingCategories().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.TrackingCategory.StatusEnum.ACTIVE)));
    // System.out.println(response.getTrackingCategories().get(0).toString());
  }

  @Test
  public void createTrackingOptionsTest() throws IOException {
    System.out.println("@Test - createTrackingOptions");
    UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    TrackingOption trackingOption = new TrackingOption();
    TrackingOptions response =
        accountingApi.createTrackingOptions(
            accessToken, xeroTenantId, trackingCategoryID, trackingOption);

    assertThat(
        response.getOptions().get(0).getTrackingOptionID(),
        is(equalTo(UUID.fromString("34669548-b989-487a-979f-0787d04568a2"))));
    assertThat(response.getOptions().get(0).getName(), is(equalTo("Bar40423")));
    assertThat(
        response.getOptions().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.TrackingOption.StatusEnum.ACTIVE)));
    // System.out.println(response.getOptions().get(0).toString());
  }

  @Test
  public void deleteTrackingCategoryTest() throws IOException {
    System.out.println("@Test - deleteTrackingCategory");
    UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    TrackingCategories response =
        accountingApi.deleteTrackingCategory(accessToken, xeroTenantId, trackingCategoryID);

    assertThat(
        response.getTrackingCategories().get(0).getTrackingCategoryID(),
        is(equalTo(UUID.fromString("0390bdfd-94f2-49d6-b7a0-4a38c46ebf03"))));
    assertThat(response.getTrackingCategories().get(0).getName(), is(equalTo("Foo46189")));
    assertThat(
        response.getTrackingCategories().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.TrackingCategory.StatusEnum.DELETED)));
    // System.out.println(response.getTrackingCategories().get(0).toString());
  }

  @Test
  public void deleteTrackingOptionsTest() throws IOException {
    System.out.println("@Test - deleteTrackingOptions");
    UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    UUID trackingOptionID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    TrackingOptions response =
        accountingApi.deleteTrackingOptions(
            accessToken, xeroTenantId, trackingCategoryID, trackingOptionID);

    assertThat(
        response.getOptions().get(0).getTrackingOptionID(),
        is(equalTo(UUID.fromString("34669548-b989-487a-979f-0787d04568a2"))));
    assertThat(response.getOptions().get(0).getName(), is(equalTo("Bar40423")));
    assertThat(
        response.getOptions().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.TrackingOption.StatusEnum.DELETED)));
    // System.out.println(response.getOptions().toString());
  }

  @Test
  public void getTrackingCategoriesTest() throws IOException {
    System.out.println("@Test - getTrackingCategories");
    String where = null;
    String order = null;
    Boolean includeArchived = null;
    TrackingCategories response =
        accountingApi.getTrackingCategories(
            accessToken, xeroTenantId, where, order, includeArchived);

    assertThat(
        response.getTrackingCategories().get(0).getTrackingCategoryID(),
        is(equalTo(UUID.fromString("22f10184-0deb-44ae-a312-b1f6ea70e51f"))));
    assertThat(response.getTrackingCategories().get(0).getName(), is(equalTo("BarFoo")));
    assertThat(
        response.getTrackingCategories().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.TrackingCategory.StatusEnum.ACTIVE)));
    // System.out.println(response.getTrackingCategories().get(0).toString());
  }

  @Test
  public void getTrackingCategoryTest() throws IOException {
    System.out.println("@Test - getTrackingCategory");
    UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    TrackingCategories response =
        accountingApi.getTrackingCategory(accessToken, xeroTenantId, trackingCategoryID);

    assertThat(
        response.getTrackingCategories().get(0).getTrackingCategoryID(),
        is(equalTo(UUID.fromString("22f10184-0deb-44ae-a312-b1f6ea70e51f"))));
    assertThat(response.getTrackingCategories().get(0).getName(), is(equalTo("Foo41157")));
    assertThat(
        response.getTrackingCategories().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.TrackingCategory.StatusEnum.DELETED)));
    // System.out.println(response.getTrackingCategories().get(0).toString());
  }

  @Test
  public void updateTrackingCategoryTest() throws IOException {
    System.out.println("@Test - updateTrackingCategory");
    UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    TrackingCategory trackingCategory = new TrackingCategory();
    TrackingCategories response =
        accountingApi.updateTrackingCategory(
            accessToken, xeroTenantId, trackingCategoryID, trackingCategory);

    assertThat(
        response.getTrackingCategories().get(0).getTrackingCategoryID(),
        is(equalTo(UUID.fromString("b1df776b-b093-4730-b6ea-590cca40e723"))));
    assertThat(response.getTrackingCategories().get(0).getName(), is(equalTo("BarFoo")));
    assertThat(
        response.getTrackingCategories().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.TrackingCategory.StatusEnum.ACTIVE)));
    // System.out.println(response.getTrackingCategories().get(0).toString());
  }
}
