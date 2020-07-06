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
import java.util.List;
import java.util.ArrayList;

public class PayrollUkApiEmployeePayTemplatesTest {

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
  public void getEmployeePayTemplateTest() throws IOException {
    System.out.println("@Test UK Payroll - getEmployeePayTemplateTest");

    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    EmployeePayTemplateObject response =
        payrollUkApi.getEmployeePayTemplate(accessToken, xeroTenantId, employeeId);

    assertThat(
        response.getPayTemplate().getEmployeeID(),
        is(equalTo(UUID.fromString("aad6b292-7b94-408b-93f6-e489867e3fb0"))));
    assertThat(
        response.getPayTemplate().getEarningTemplates().get(0).getPayTemplateEarningID(),
        is(equalTo(UUID.fromString("4e2a0753-56b9-423d-8068-624473bd1c00"))));
    assertThat(
        response.getPayTemplate().getEarningTemplates().get(0).getEarningsRateID(),
        is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
    assertThat(
        response.getPayTemplate().getEarningTemplates().get(0).getRatePerUnit(), is(equalTo(25.0)));
    assertThat(
        response.getPayTemplate().getEarningTemplates().get(0).getNumberOfUnits(),
        is(equalTo(10.0)));
    assertThat(
        response.getPayTemplate().getEarningTemplates().get(0).getName(),
        is(equalTo("Regular Hours")));

    // System.out.println(response.toString());
  }

  @Test
  public void updateEmployeePayTemplateTest() throws IOException {
    System.out.println("@Test UK Payroll - updateEmployeePayTemplateTest");

    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    UUID payTemplateEarningID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    EarningsTemplate earningsTemplate = new EarningsTemplate();
    EarningsTemplateObject response =
        payrollUkApi.updateEmployeeEarningsTemplate(
            accessToken, xeroTenantId, employeeId, payTemplateEarningID, earningsTemplate);

    assertThat(
        response.getEarningTemplate().getEarningsRateID(),
        is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
    assertThat(
        response.getEarningTemplate().getPayTemplateEarningID(),
        is(equalTo(UUID.fromString("4e2a0753-56b9-423d-8068-624473bd1c00"))));
    assertThat(response.getEarningTemplate().getRatePerUnit(), is(equalTo(30.0)));
    assertThat(response.getEarningTemplate().getNumberOfUnits(), is(equalTo(4.0)));
    assertThat(response.getEarningTemplate().getName(), is(equalTo("Regular Hours")));

    // System.out.println(response.toString());
  }

  @Test
  public void createMultipleEmployeeEarningsTemplateTest() throws IOException {
    System.out.println("@Test UK Payroll - createMultipleEmployeeEarningsTemplateTest");

    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    List<EarningsTemplate> earningsTemplate = new ArrayList<>();
    EmployeePayTemplates response =
        payrollUkApi.createMultipleEmployeeEarningsTemplate(
            accessToken, xeroTenantId, employeeId, earningsTemplate);

    assertThat(
        response.getEarningTemplates().get(0).getPayTemplateEarningID(),
        is(equalTo(UUID.fromString("6b32533d-1111-4a92-ac52-7641315a1719"))));
    assertThat(
        response.getEarningTemplates().get(0).getEarningsRateID(),
        is(equalTo(UUID.fromString("87f5b43a-cf51-4b74-92de-94c819e82d27"))));
    assertThat(response.getEarningTemplates().get(0).getRatePerUnit(), is(equalTo(20.0)));
    assertThat(response.getEarningTemplates().get(0).getNumberOfUnits(), is(equalTo(8.0)));
    assertThat(response.getEarningTemplates().get(0).getName(), is(equalTo("Regular Hours")));
    assertThat(
        response.getEarningTemplates().get(1).getPayTemplateEarningID(),
        is(equalTo(UUID.fromString("b8b16366-6a60-4b72-97c1-57af555da375"))));
    assertThat(
        response.getEarningTemplates().get(1).getEarningsRateID(),
        is(equalTo(UUID.fromString("973365f3-66b2-4c33-8ae6-14b75f78f68b"))));
    assertThat(response.getEarningTemplates().get(1).getRatePerUnit(), is(equalTo(20.0)));
    assertThat(response.getEarningTemplates().get(1).getNumberOfUnits(), is(equalTo(8.0)));
    assertThat(response.getEarningTemplates().get(1).getName(), is(equalTo("Overtime Hours")));

    // System.out.println(response.toString());
  }
}
