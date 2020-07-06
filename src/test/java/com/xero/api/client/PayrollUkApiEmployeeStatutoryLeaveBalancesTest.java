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

public class PayrollUkApiEmployeeStatutoryLeaveBalancesTest {

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
  public void getEmployeeStatutoryLeaveBalancesTest() throws IOException {
    System.out.println("@Test UK Payroll - getEmployeeStatutoryLeaveBalancesTest");

    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    LocalDate asOfDate = LocalDate.of(2020, Month.MARCH, 30);
    EmployeeStatutoryLeaveBalanceObject response =
        payrollUkApi.getEmployeeStatutoryLeaveBalances(
            accessToken, xeroTenantId, employeeId, "Sick", asOfDate);

    assertThat(
        response.getLeaveBalance().getLeaveType(),
        is(equalTo(com.xero.models.payrolluk.EmployeeStatutoryLeaveBalance.LeaveTypeEnum.SICK)));
    assertThat(
        response.getLeaveBalance().getUnits(),
        is(equalTo(com.xero.models.payrolluk.EmployeeStatutoryLeaveBalance.UnitsEnum.HOURS)));
    assertThat(response.getLeaveBalance().getBalanceRemaining(), is(equalTo(1120.0)));

    // System.out.println(response.toString());
  }
}
