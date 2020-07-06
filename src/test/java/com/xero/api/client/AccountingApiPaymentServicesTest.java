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

public class AccountingApiPaymentServicesTest {

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
  public void createPaymentServiceTest() throws IOException {
    System.out.println("@Test - createPaymentService");
    PaymentServices paymentServices = new PaymentServices();
    PaymentServices response =
        accountingApi.createPaymentService(accessToken, xeroTenantId, paymentServices);

    assertThat(
        response.getPaymentServices().get(0).getPaymentServiceID(),
        is(equalTo(UUID.fromString("54b3b4f6-0443-4fba-bcd1-61ec0c35ca55"))));
    assertThat(
        response.getPaymentServices().get(0).getPaymentServiceName(), is(equalTo("PayUpNow")));
    assertThat(
        response.getPaymentServices().get(0).getPaymentServiceUrl(),
        is(equalTo("https://www.payupnow.com/")));
    assertThat(response.getPaymentServices().get(0).getPayNowText(), is(equalTo("Time To Pay")));
    assertThat(response.getPaymentServices().get(0).getPaymentServiceType(), is(equalTo("Custom")));
    assertThat(
        response.getPaymentServices().get(0).getValidationErrors().get(0).getMessage(),
        is(equalTo("Payment service could not be found")));
    // System.out.println(response.getPaymentServices().get(0).toString());
  }

  @Test
  public void getPaymentServicesTest() throws IOException {
    System.out.println("@Test - getPaymentServices");
    PaymentServices response = accountingApi.getPaymentServices(accessToken, xeroTenantId);

    assertThat(
        response.getPaymentServices().get(0).getPaymentServiceID(),
        is(equalTo(UUID.fromString("54b3b4f6-0443-4fba-bcd1-61ec0c35ca55"))));
    assertThat(
        response.getPaymentServices().get(0).getPaymentServiceName(), is(equalTo("PayUpNow")));
    assertThat(
        response.getPaymentServices().get(0).getPaymentServiceUrl(),
        is(equalTo("https://www.payupnow.com/")));
    assertThat(response.getPaymentServices().get(0).getPayNowText(), is(equalTo("Time To Pay")));
    assertThat(response.getPaymentServices().get(0).getPaymentServiceType(), is(equalTo("Custom")));
    // System.out.println(response.getPaymentServices().get(0).toString());
  }
}
