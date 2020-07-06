package com.xero.api.client;


import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.xero.api.ApiClient;
import com.xero.models.payrolluk.*;
import com.xero.models.payrolluk.Benefit.CategoryEnum;



import org.threeten.bp.*;
import java.io.IOException;

import java.io.IOException;


import java.util.UUID;

public class PayrollUkApiEmployerPensionsTest {

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
  public void getBenefitsTest() throws IOException {
    System.out.println("@Test UK Payroll - getBenefitsTest");
    int page = 1;
    Benefits response = payrollUkApi.getBenefits(accessToken, xeroTenantId, page);

    assertThat(
        response.getBenefits().get(0).getId(),
        is(equalTo(UUID.fromString("17ee7e28-9f4c-4675-9590-cc0547c9f7ac"))));
    assertThat(
        response.getBenefits().get(0).getLiabilityAccountId(),
        is(equalTo(UUID.fromString("d659ebbf-0760-4e07-a1fb-8de6b9ecdff9"))));
    assertThat(
        response.getBenefits().get(0).getExpenseAccountId(),
        is(equalTo(UUID.fromString("edda7154-dfc8-4486-a82b-e5e955408eaa"))));
    assertThat(response.getBenefits().get(0).getName(), is(equalTo("Mind The Gap Pensions")));
    assertThat(
        response.getBenefits().get(0).getCategory(), is(equalTo(CategoryEnum.STAKEHOLDERPENSION)));
    assertThat(response.getBenefits().get(0).getPercentage(), is(equalTo(3.0)));
    assertThat(
        response.getBenefits().get(0).getCalculationType(),
        is(equalTo(com.xero.models.payrolluk.Benefit.CalculationTypeEnum.PERCENTAGEOFGROSS)));
    assertThat(response.getBenefits().get(0).getCurrentRecord(), is(equalTo(true)));
    assertThat(response.getBenefits().get(0).getShowBalanceToEmployee(), is(equalTo(false)));

    // System.out.println(response.toString());
  }

  @Test
  public void getBenefitTest() throws IOException {
    System.out.println("@Test UK Payroll - getBenefitTest");

    UUID benefitId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    BenefitObject response = payrollUkApi.getBenefit(accessToken, xeroTenantId, benefitId);

    assertThat(
        response.getBenefit().getId(),
        is(equalTo(UUID.fromString("17ee7e28-9f4c-4675-9590-cc0547c9f7ac"))));
    assertThat(
        response.getBenefit().getLiabilityAccountId(),
        is(equalTo(UUID.fromString("d659ebbf-0760-4e07-a1fb-8de6b9ecdff9"))));
    assertThat(
        response.getBenefit().getExpenseAccountId(),
        is(equalTo(UUID.fromString("edda7154-dfc8-4486-a82b-e5e955408eaa"))));
    assertThat(response.getBenefit().getName(), is(equalTo("Mind The Gap Pensions")));
    assertThat(response.getBenefit().getCategory(), is(equalTo(CategoryEnum.STAKEHOLDERPENSION)));
    assertThat(response.getBenefit().getPercentage(), is(equalTo(3.0)));
    assertThat(
        response.getBenefit().getCalculationType(),
        is(equalTo(com.xero.models.payrolluk.Benefit.CalculationTypeEnum.PERCENTAGEOFGROSS)));
    assertThat(response.getBenefit().getCurrentRecord(), is(equalTo(true)));
    assertThat(response.getBenefit().getShowBalanceToEmployee(), is(equalTo(false)));

    // System.out.println(response.toString());
  }

  @Test
  public void createBenefitTest() throws IOException {
    System.out.println("@Test UK Payroll - createBenefitTest");

    Benefit benefit = new Benefit();
    BenefitObject response = payrollUkApi.createBenefit(accessToken, xeroTenantId, benefit);

    assertThat(
        response.getBenefit().getId(),
        is(equalTo(UUID.fromString("d295bf25-fb61-4f91-9b62-a9ae87633746"))));
    assertThat(
        response.getBenefit().getLiabilityAccountId(),
        is(equalTo(UUID.fromString("e0faa299-ca0d-4b0a-9e32-0dfabdf9179a"))));
    assertThat(
        response.getBenefit().getExpenseAccountId(),
        is(equalTo(UUID.fromString("4b03500d-32fd-4616-8d70-e1e56e0519c6"))));
    assertThat(response.getBenefit().getName(), is(equalTo("My Big Bennie")));
    assertThat(response.getBenefit().getCategory(), is(equalTo(CategoryEnum.STAKEHOLDERPENSION)));
    assertThat(response.getBenefit().getPercentage(), is(equalTo(25.0)));
    assertThat(
        response.getBenefit().getCalculationType(),
        is(equalTo(com.xero.models.payrolluk.Benefit.CalculationTypeEnum.PERCENTAGEOFGROSS)));
    assertThat(response.getBenefit().getCurrentRecord(), is(equalTo(true)));
    assertThat(response.getBenefit().getShowBalanceToEmployee(), is(equalTo(true)));

    // System.out.println(response.toString());
  }
}
