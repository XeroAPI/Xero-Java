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

public class PayrollUkApiEmployeeLeaveBalancesTest {

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
  public void getEmployeeLeaveBalancesTest() throws IOException {
    System.out.println("@Test UK Payroll - getEmployeeLeaveBalancesTest");

    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    EmployeeLeaveBalances response =
        payrollUkApi.getEmployeeLeaveBalances(accessToken, xeroTenantId, employeeId);

    assertThat(response.getPagination().getPage(), is(equalTo(1)));
    assertThat(response.getPagination().getPageSize(), is(equalTo(100)));
    assertThat(response.getPagination().getPageCount(), is(equalTo(1)));
    assertThat(response.getPagination().getItemCount(), is(equalTo(2)));
    assertThat(
        response.getLeaveBalances().get(0).getLeaveTypeID(),
        is(equalTo(UUID.fromString("ed08dffe-788e-4b24-9630-f0fa2f4d164c"))));
    assertThat(response.getLeaveBalances().get(0).getName(), is(equalTo("Holiday")));
    assertThat(response.getLeaveBalances().get(0).getBalance(), is(equalTo(32.0)));
    assertThat(response.getLeaveBalances().get(0).getTypeOfUnits(), is(equalTo("Hours")));

    // System.out.println(response.toString());
  }
}
