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

public class PayrollUkApiEmployeeTaxTest {

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
  public void getEmployeeTaxTest() throws IOException {
    System.out.println("@Test UK Payroll - getEmployeeTax");

    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    EmployeeTaxObject response = payrollUkApi.getEmployeeTax(accessToken, xeroTenantId, employeeId);

    assertThat(response.getEmployeeTax().getStarterType(), is(equalTo("New Employee with P45")));
    assertThat(
        response.getEmployeeTax().getStarterDeclaration(),
        is(equalTo("B.) This is currently their only job")));
    assertThat(response.getEmployeeTax().getTaxCode(), is(equalTo("1185L")));
    assertThat(response.getEmployeeTax().getW1M1(), is(equalTo(false)));
    assertThat(response.getEmployeeTax().getPreviousTaxPaid(), is(equalTo(200.0)));
    assertThat(response.getEmployeeTax().getPreviousTaxablePay(), is(equalTo(2000.0)));
    assertThat(response.getEmployeeTax().getHasPostGraduateLoans(), is(equalTo(false)));
    assertThat(response.getEmployeeTax().getIsDirector(), is(equalTo(false)));

    // System.out.println(response.toString());
  }
}
