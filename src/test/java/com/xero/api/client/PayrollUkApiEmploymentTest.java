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

public class PayrollUkApiEmploymentTest {

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
  public void createEmploymentTest() throws IOException {
    System.out.println("@Test UK Payroll - createEmploymentTest");

    Employment employment = new Employment();
    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    EmploymentObject response =
        payrollUkApi.createEmployment(accessToken, xeroTenantId, employeeId, employment);

    assertThat(
        response.getEmployment().getPayrollCalendarID(),
        is(equalTo(UUID.fromString("216d80e6-af55-47b1-b718-9457c3f5d2fe"))));
    assertThat(response.getEmployment().getStartDate(), is(equalTo(LocalDate.of(2020, 04, 01))));
    assertThat(response.getEmployment().getEmployeeNumber(), is(equalTo("123ABC")));
    assertThat(
        response.getEmployment().getNiCategory(),
        is(equalTo(com.xero.models.payrolluk.Employment.NiCategoryEnum.A)));
    // System.out.println(response.toString());
  }
}
