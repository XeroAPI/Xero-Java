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

public class AccountingApiOrganisationsTest {

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
  public void getOrganisationsTest() throws IOException {
    System.out.println("@Test - getOrganisations");
    Organisations response = accountingApi.getOrganisations(accessToken, xeroTenantId);

    assertThat(
        response.getOrganisations().get(0).getOrganisationID(),
        is(equalTo(UUID.fromString("b2c885a9-4bb9-4a00-9b6e-6c2bf60b1a2b"))));
    assertThat(
        response.getOrganisations().get(0).getApIKey(),
        is(equalTo("CTJ60UH519MXQIXEJSDPDALS3EOZ5Y")));
    assertThat(
        response.getOrganisations().get(0).getName(),
        is(equalTo("Dev Evangelist - Sid Test 3 (NZ-2016-02)")));
    assertThat(
        response.getOrganisations().get(0).getLegalName(),
        is(equalTo("Dev Evangelist - Sid Test 3 (NZ-2016-02)")));
    assertThat(response.getOrganisations().get(0).getPaysTax(), is(equalTo(true)));
    assertThat(
        response.getOrganisations().get(0).getVersion(),
        is(equalTo(com.xero.models.accounting.Organisation.VersionEnum.NZ)));
    assertThat(
        response.getOrganisations().get(0).getOrganisationType(),
        is(equalTo(com.xero.models.accounting.Organisation.OrganisationTypeEnum.COMPANY)));
    assertThat(
        response.getOrganisations().get(0).getBaseCurrency(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getOrganisations().get(0).getCountryCode(),
        is(equalTo(com.xero.models.accounting.CountryCode.NZ)));
    assertThat(response.getOrganisations().get(0).getIsDemoCompany(), is(equalTo(false)));
    assertThat(response.getOrganisations().get(0).getOrganisationStatus(), is(equalTo("ACTIVE")));
    assertThat(response.getOrganisations().get(0).getFinancialYearEndDay(), is(equalTo(31)));
    assertThat(response.getOrganisations().get(0).getFinancialYearEndMonth(), is(equalTo(3)));
    assertThat(response.getOrganisations().get(0).getTaxNumber(), is(equalTo("071-138-054")));
    assertThat(
        response.getOrganisations().get(0).getSalesTaxBasis(),
        is(equalTo(com.xero.models.accounting.Organisation.SalesTaxBasisEnum.PAYMENTS)));
    assertThat(
        response.getOrganisations().get(0).getSalesTaxPeriod(),
        is(equalTo(com.xero.models.accounting.Organisation.SalesTaxPeriodEnum.TWOMONTHS)));
    assertThat(
        response.getOrganisations().get(0).getDefaultSalesTax(), is(equalTo("Tax Exclusive")));
    assertThat(
        response.getOrganisations().get(0).getDefaultPurchasesTax(), is(equalTo("Tax Exclusive")));
    assertThat(
        response.getOrganisations().get(0).getPeriodLockDateAsDate(),
        is(equalTo(LocalDate.of(2018, 12, 31))));
    assertThat(
        response.getOrganisations().get(0).getEndOfYearLockDateAsDate(),
        is(equalTo(LocalDate.of(2018, 12, 31))));
    assertThat(
        response.getOrganisations().get(0).getCreatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2016-02-18T20:29:53Z"))));
    assertThat(
        response.getOrganisations().get(0).getTimezone(),
        is(equalTo(com.xero.models.accounting.TimeZone.NEWZEALANDSTANDARDTIME)));
    assertThat(
        response.getOrganisations().get(0).getOrganisationEntityType(),
        is(equalTo(com.xero.models.accounting.Organisation.OrganisationEntityTypeEnum.COMPANY)));
    assertThat(response.getOrganisations().get(0).getShortCode(), is(equalTo("!mBdtL")));
    assertThat(
        response.getOrganisations().get(0).getPropertyClass(),
        is(equalTo(com.xero.models.accounting.Organisation.PropertyClassEnum.PREMIUM)));
    assertThat(
        response.getOrganisations().get(0).getEdition(),
        is(equalTo(com.xero.models.accounting.Organisation.EditionEnum.BUSINESS)));
    // assertThat(response.getOrganisations().get(0).getRegistrationNumber(),
    // is(equalTo("STRING_ONLY_PLACEHOLDER")));
    // assertThat(response.getOrganisations().get(0).getLineOfBusiness(),
    // is(equalTo("STRING_ONLY_PLACEHOLDER")));
    // assertThat(response.getOrganisations().get(0).getAddresses(),
    // is(equalTo("CONTAINER_PLACEHOLDER")));
    // assertThat(response.getOrganisations().get(0).getPhones(),
    // is(equalTo("CONTAINER_PLACEHOLDER")));
    // assertThat(response.getOrganisations().get(0).getExternalLinks(),
    // is(equalTo("CONTAINER_PLACEHOLDER")));
    // assertThat(response.getOrganisations().get(0).getPaymentTerms(),
    // is(equalTo("MODEL_PLACEHOLDER")));
    // System.out.println(response.getOrganisations().get(0).toString());
  }
}
