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

public class PayrollUkApiDeductionsTest {

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
  public void getDeductionsTest() throws IOException {
    System.out.println("@Test UK Payroll - getDeductionsTest");

    int page = 1;
    Deductions response = payrollUkApi.getDeductions(accessToken, xeroTenantId, page);

    assertThat(
        response.getDeductions().get(1).getDeductionId(),
        is(equalTo(UUID.fromString("49f301d4-2746-43a6-ba4a-e7001b5b83fc"))));
    assertThat(
        response.getDeductions().get(1).getLiabilityAccountId(),
        is(equalTo(UUID.fromString("d659ebbf-0760-4e07-a1fb-8de6b9ecdff9"))));
    assertThat(response.getDeductions().get(1).getDeductionName(), is(equalTo("Post-Tax Pension")));
    assertThat(
        response.getDeductions().get(1).getDeductionCategory(),
        is(
            equalTo(
                com.xero.models.payrolluk.Deduction.DeductionCategoryEnum
                    .STAKEHOLDERPENSIONPOSTTAX)));
    assertThat(response.getDeductions().get(1).getPercentage(), is(equalTo(0.0)));
    assertThat(
        response.getDeductions().get(1).getCalculationType(),
        is(equalTo(com.xero.models.payrolluk.Deduction.CalculationTypeEnum.PERCENTAGEOFGROSS)));
    assertThat(response.getDeductions().get(1).getCurrentRecord(), is(equalTo(true)));
    assertThat(response.getDeductions().get(1).getApplyToPensionCalculations(), is(equalTo(false)));
    assertThat(response.getDeductions().get(1).getIsPension(), is(equalTo(true)));

    // System.out.println(response.toString());
  }

  @Test
  public void getDeductionTest() throws IOException {
    System.out.println("@Test UK Payroll - getDeductionTest");

    UUID deductionId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    DeductionObject response = payrollUkApi.getDeduction(accessToken, xeroTenantId, deductionId);

    assertThat(
        response.getDeduction().getDeductionId(),
        is(equalTo(UUID.fromString("7c736d59-a624-4584-920b-e9910d2887a0"))));
    assertThat(
        response.getDeduction().getLiabilityAccountId(),
        is(equalTo(UUID.fromString("d659ebbf-0760-4e07-a1fb-8de6b9ecdff9"))));
    assertThat(
        response.getDeduction().getDeductionName(), is(equalTo("Mind The Gap Pensions (NPA)")));
    assertThat(
        response.getDeduction().getDeductionCategory(),
        is(equalTo(com.xero.models.payrolluk.Deduction.DeductionCategoryEnum.STAKEHOLDERPENSION)));
    assertThat(response.getDeduction().getPercentage(), is(equalTo(5.0)));
    assertThat(
        response.getDeduction().getCalculationType(),
        is(equalTo(com.xero.models.payrolluk.Deduction.CalculationTypeEnum.PERCENTAGEOFGROSS)));
    assertThat(response.getDeduction().getCurrentRecord(), is(equalTo(true)));

    // System.out.println(response.toString());
  }

  @Test
  public void createDeductionTest() throws IOException {
    System.out.println("@Test UK Payroll - createDeductionTest");

    Deduction deduction = new Deduction();
    DeductionObject response = payrollUkApi.createDeduction(accessToken, xeroTenantId, deduction);

    assertThat(
        response.getDeduction().getDeductionId(),
        is(equalTo(UUID.fromString("b3695b29-750f-4957-98b4-678e4a529043"))));
    assertThat(
        response.getDeduction().getLiabilityAccountId(),
        is(equalTo(UUID.fromString("e0faa299-ca0d-4b0a-9e32-0dfabdf9179a"))));
    assertThat(response.getDeduction().getDeductionName(), is(equalTo("My new deducation")));
    assertThat(
        response.getDeduction().getDeductionCategory(),
        is(equalTo(com.xero.models.payrolluk.Deduction.DeductionCategoryEnum.SALARYSACRIFICE)));
    assertThat(
        response.getDeduction().getCalculationType(),
        is(equalTo(com.xero.models.payrolluk.Deduction.CalculationTypeEnum.FIXEDAMOUNT)));
    assertThat(response.getDeduction().getCurrentRecord(), is(equalTo(true)));

    // System.out.println(response.toString());
  }
}
