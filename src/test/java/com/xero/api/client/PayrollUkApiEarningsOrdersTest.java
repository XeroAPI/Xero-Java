package com.xero.api.client;


import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.xero.api.ApiClient;
import com.xero.models.payrolluk.*;



import org.threeten.bp.*;
import java.io.IOException;

import java.io.IOException;


import java.util.UUID;

public class PayrollUkApiEarningsOrdersTest {

  ApiClient defaultClient;
  PayrollUkApi payrollUkApi;

  String accessToken;
  String xeroTenantId;

  @Before
  public void setUp() {
    // Set Access Token and Tenant Id
    accessToken = "123";
    xeroTenantId = "xyz";

    // Init projectApi client
    // NEW Sandbox for API Mocking
    defaultClient =
        new ApiClient(
            "https://xero-payroll-uk.getsandbox.com:443/payroll.xro/2.0", null, null, null, null);
    payrollUkApi = PayrollUkApi.getInstance(defaultClient);
  }

  public void tearDown() {
    payrollUkApi = null;
    defaultClient = null;
  }

  @Test
  public void getEarningsOrdersTest() throws IOException {
    System.out.println("@Test UK Payroll - getEarningsOrdersTest");

    UUID deductionId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    int page = 1;
    EarningsOrders response = payrollUkApi.getEarningsOrders(accessToken, xeroTenantId, page);

    assertThat(
        response.getStatutoryDeductions().get(0).getId(),
        is(equalTo(UUID.fromString("d00d0128-500b-4685-8332-8fc8743a395c"))));
    assertThat(
        response.getStatutoryDeductions().get(0).getLiabilityAccountId(),
        is(equalTo(UUID.fromString("921237ae-76ff-4f4c-bf70-5c3c177b149d"))));
    assertThat(
        response.getStatutoryDeductions().get(0).getName(), is(equalTo("AEO (maintenance)")));
    assertThat(
        response.getStatutoryDeductions().get(0).getStatutoryDeductionCategory(),
        is(equalTo(com.xero.models.payrolluk.StatutoryDeductionCategory.PRIORITYORDER)));
    assertThat(response.getStatutoryDeductions().get(0).getCurrentRecord(), is(equalTo(true)));

    // System.out.println(response.toString());
  }

  @Test
  public void getEarningsOrderTest() throws IOException {
    System.out.println("@Test UK Payroll - getEarningsOrderTest");

    UUID id = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    int page = 1;
    EarningsOrderObject response = payrollUkApi.getEarningsOrder(accessToken, xeroTenantId, id);

    assertThat(
        response.getStatutoryDeduction().getId(),
        is(equalTo(UUID.fromString("d00d0128-500b-4685-8332-8fc8743a395c"))));
    assertThat(
        response.getStatutoryDeduction().getLiabilityAccountId(),
        is(equalTo(UUID.fromString("921237ae-76ff-4f4c-bf70-5c3c177b149d"))));
    assertThat(response.getStatutoryDeduction().getName(), is(equalTo("AEO (maintenance)")));
    assertThat(
        response.getStatutoryDeduction().getStatutoryDeductionCategory(),
        is(equalTo(com.xero.models.payrolluk.StatutoryDeductionCategory.PRIORITYORDER)));
    assertThat(response.getStatutoryDeduction().getCurrentRecord(), is(equalTo(true)));

    // System.out.println(response.toString());
  }
}
