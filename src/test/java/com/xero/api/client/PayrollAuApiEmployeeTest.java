package com.xero.api.client;


import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.xero.api.ApiClient;
import com.xero.models.payrollau.*;



import org.threeten.bp.*;
import java.io.IOException;

import java.io.IOException;


import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class PayrollAuApiEmployeeTest {

  ApiClient defaultClient;
  PayrollAuApi payrollAuApi;

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
            "https://xero-payroll-au.getsandbox.com:443/payroll.xro/1.0", null, null, null, null);
    payrollAuApi = PayrollAuApi.getInstance(defaultClient);
  }

  public void tearDown() {
    payrollAuApi = null;
    defaultClient = null;
  }

  @Test
  public void getEmployeesTest() throws IOException {
    System.out.println("@Test - getEmployeesTest");

    Employees response =
        payrollAuApi.getEmployees(accessToken, xeroTenantId, null, null, null, null);

    assertThat(response.getEmployees().get(0).getFirstName(), is(equalTo("Jack")));
    assertThat(response.getEmployees().get(0).getLastName(), is(equalTo("Sparrow")));
    assertThat(
        response.getEmployees().get(0).getDateOfBirthAsDate(),
        is(equalTo(LocalDate.of(1988, 2, 20))));
    assertThat(
        response.getEmployees().get(0).getStartDateAsDate(),
        is(equalTo(LocalDate.of(2019, 1, 11))));
    assertThat(response.getEmployees().get(0).getMiddleNames(), is(equalTo("Johnson")));
    assertThat(response.getEmployees().get(0).getEmail(), is(equalTo("jack.sparrow@xero.com")));
    assertThat(
        response.getEmployees().get(0).getGender(),
        is(equalTo(com.xero.models.payrollau.Employee.GenderEnum.M)));
    assertThat(response.getEmployees().get(0).getMobile(), is(equalTo("415-1234567")));
    assertThat(response.getEmployees().get(0).getPhone(), is(equalTo("4153332323")));
    assertThat(
        response.getEmployees().get(0).getOrdinaryEarningsRateID(),
        is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
    assertThat(
        response.getEmployees().get(0).getPayrollCalendarID(),
        is(equalTo(UUID.fromString("22a05fc5-386d-4950-9842-3e7a6c812135"))));
    assertThat(
        response.getEmployees().get(0).getEmployeeID(),
        is(equalTo(UUID.fromString("b34e89ff-770d-4099-b7e5-f968767118bc"))));
    assertThat(
        response.getEmployees().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-11-05T01:03:34Z"))));
    assertThat(
        response.getEmployees().get(0).getStatus(),
        is(equalTo(com.xero.models.payrollau.EmployeeStatus.ACTIVE)));
    // System.out.println(response.toString());
  }

  @Test
  public void createEmployeeTest() throws IOException {
    System.out.println("@Test - createEmployeeTest");

    List<Employee> employee = new ArrayList<>();
    Employees response = payrollAuApi.createEmployee(accessToken, xeroTenantId, employee);

    assertThat(response.getEmployees().get(0).getFirstName(), is(equalTo("Albus")));
    assertThat(response.getEmployees().get(0).getLastName(), is(equalTo("Dumbledore")));
    assertThat(
        response.getEmployees().get(0).getDateOfBirthAsDate(),
        is(equalTo(LocalDate.of(1980, 3, 10))));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getAddressLine1(),
        is(equalTo("101 Green St")));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getCity(), is(equalTo("Island Bay")));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getRegion(),
        is(equalTo(com.xero.models.payrollau.State.NSW)));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getPostalCode(), is(equalTo("6023")));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getCountry(), is(equalTo("AUSTRALIA")));
    assertThat(
        response.getEmployees().get(0).getStartDateAsDate(),
        is(equalTo(LocalDate.of(1980, 3, 10))));
    assertThat(response.getEmployees().get(0).getMiddleNames(), is(equalTo("Percival")));
    assertThat(response.getEmployees().get(0).getEmail(), is(equalTo("albus39608@hogwarts.edu")));
    assertThat(
        response.getEmployees().get(0).getGender(),
        is(equalTo(com.xero.models.payrollau.Employee.GenderEnum.M)));
    assertThat(response.getEmployees().get(0).getMobile(), is(equalTo("555-1212")));
    assertThat(response.getEmployees().get(0).getPhone(), is(equalTo("444-2323")));
    assertThat(response.getEmployees().get(0).getIsAuthorisedToApproveLeave(), is(equalTo(true)));
    assertThat(
        response.getEmployees().get(0).getIsAuthorisedToApproveTimesheets(), is(equalTo(true)));
    assertThat(response.getEmployees().get(0).getJobTitle(), is(equalTo("Regional Manager")));
    assertThat(response.getEmployees().get(0).getClassification(), is(equalTo("corporate")));
    assertThat(
        response.getEmployees().get(0).getOrdinaryEarningsRateID(),
        is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
    assertThat(
        response.getEmployees().get(0).getEmployeeID(),
        is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
    assertThat(
        response.getEmployees().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-11-13T05:05:24.458Z"))));
    assertThat(
        response.getEmployees().get(0).getStatus(),
        is(equalTo(com.xero.models.payrollau.EmployeeStatus.ACTIVE)));

    // System.out.println(response.toString());
  }

  @Test
  public void getEmployeeTest() throws IOException {
    System.out.println("@Test - getEmployeeTest");

    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    Employees response = payrollAuApi.getEmployee(accessToken, xeroTenantId, employeeId);

    assertThat(
        response.getEmployees().get(0).getEmployeeID(),
        is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
    assertThat(response.getEmployees().get(0).getFirstName(), is(equalTo("Albus")));
    assertThat(response.getEmployees().get(0).getLastName(), is(equalTo("Dumbledore")));
    assertThat(
        response.getEmployees().get(0).getDateOfBirthAsDate(),
        is(equalTo(LocalDate.of(1980, 3, 10))));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getAddressLine1(),
        is(equalTo("101 Green St")));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getCity(), is(equalTo("Island Bay")));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getRegion(),
        is(equalTo(com.xero.models.payrollau.State.NSW)));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getPostalCode(), is(equalTo("6023")));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getCountry(), is(equalTo("AUSTRALIA")));
    assertThat(
        response.getEmployees().get(0).getStartDateAsDate(),
        is(equalTo(LocalDate.of(1980, 3, 10))));
    assertThat(response.getEmployees().get(0).getTitle(), is(equalTo("Mr.")));
    assertThat(response.getEmployees().get(0).getMiddleNames(), is(equalTo("Frank")));
    assertThat(response.getEmployees().get(0).getEmail(), is(equalTo("albus39608@hogwarts.edu")));
    assertThat(
        response.getEmployees().get(0).getGender(),
        is(equalTo(com.xero.models.payrollau.Employee.GenderEnum.M)));
    assertThat(response.getEmployees().get(0).getMobile(), is(equalTo("555-1212")));
    assertThat(response.getEmployees().get(0).getPhone(), is(equalTo("444-2323")));
    assertThat(response.getEmployees().get(0).getIsAuthorisedToApproveLeave(), is(equalTo(true)));
    assertThat(
        response.getEmployees().get(0).getIsAuthorisedToApproveTimesheets(), is(equalTo(true)));
    assertThat(response.getEmployees().get(0).getJobTitle(), is(equalTo("Regional Manager")));
    assertThat(response.getEmployees().get(0).getClassification(), is(equalTo("corporate")));
    assertThat(
        response.getEmployees().get(0).getPayrollCalendarID(),
        equalTo(UUID.fromString("78bb86b9-e1ea-47ac-b75d-f087a81931de")));
    assertThat(response.getEmployees().get(0).getEmployeeGroupName(), is(equalTo("foo")));
    // PAY TEMPLATE > EARNINGS LINES
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getPayTemplate()
            .getEarningsLines()
            .get(0)
            .getEarningsRateID(),
        is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getPayTemplate()
            .getEarningsLines()
            .get(0)
            .getCalculationType(),
        is(equalTo(com.xero.models.payrollau.EarningsRateCalculationType.USEEARNINGSRATE)));
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getPayTemplate()
            .getEarningsLines()
            .get(0)
            .getNormalNumberOfUnits(),
        is(equalTo(3.0)));
    // PAY TEMPLATE > DEDUCTION LINES
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getPayTemplate()
            .getDeductionLines()
            .get(0)
            .getDeductionTypeID(),
        is(equalTo(UUID.fromString("ed05ea82-e40a-4eb6-9c2e-4b3c03e7e938"))));
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getPayTemplate()
            .getDeductionLines()
            .get(0)
            .getCalculationType(),
        is(equalTo(com.xero.models.payrollau.DeductionTypeCalculationType.FIXEDAMOUNT)));
    assertThat(
        response.getEmployees().get(0).getPayTemplate().getDeductionLines().get(0).getAmount(),
        is(equalTo(4.0)));
    // PAY TEMPLATE > SUPERLINES
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getPayTemplate()
            .getSuperLines()
            .get(0)
            .getMinimumMonthlyEarnings(),
        is(equalTo(450.0)));
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getPayTemplate()
            .getSuperLines()
            .get(0)
            .getExpenseAccountCode(),
        is(equalTo("478")));
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getPayTemplate()
            .getSuperLines()
            .get(0)
            .getLiabilityAccountCode(),
        is(equalTo("826")));
    assertThat(
        response.getEmployees().get(0).getPayTemplate().getSuperLines().get(0).getCalculationType(),
        is(equalTo(com.xero.models.payrollau.SuperannuationCalculationType.STATUTORY)));
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getPayTemplate()
            .getSuperLines()
            .get(0)
            .getContributionType(),
        is(equalTo(com.xero.models.payrollau.SuperannuationContributionType.SGC)));
    // PAY TEMPLATE > REMIMBURSEMENT
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getPayTemplate()
            .getReimbursementLines()
            .get(0)
            .getReimbursementTypeID(),
        is(equalTo(UUID.fromString("aa8cfa40-d872-4be0-8a94-bb7f00962f74"))));
    assertThat(
        response.getEmployees().get(0).getPayTemplate().getReimbursementLines().get(0).getAmount(),
        is(equalTo(55.0)));
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getPayTemplate()
            .getReimbursementLines()
            .get(0)
            .getDescription(),
        is(equalTo("boo")));

    // PAY TEMPLATE > LEAVE LINES
    assertThat(
        response.getEmployees().get(0).getPayTemplate().getLeaveLines().get(0).getLeaveTypeID(),
        is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
    assertThat(
        response.getEmployees().get(0).getPayTemplate().getLeaveLines().get(0).getCalculationType(),
        is(equalTo(com.xero.models.payrollau.LeaveLineCalculationType.FIXEDAMOUNTEACHPERIOD)));
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getPayTemplate()
            .getLeaveLines()
            .get(0)
            .getEntitlementFinalPayPayoutType(),
        is(equalTo(com.xero.models.payrollau.EntitlementFinalPayPayoutType.NOTPAIDOUT)));

    // OPENING BALANCES
    assertThat(
        response.getEmployees().get(0).getOpeningBalances().getOpeningBalanceDateAsDate(),
        is(equalTo(LocalDate.of(2019, 11, 13))));
    // OPENING BALANCES > LEAVE LINES
    assertThat(
        response.getEmployees().get(0).getOpeningBalances().getLeaveLines().get(0).getLeaveTypeID(),
        is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));
    assertThat(
        response
            .getEmployees()
            .get(0)
            .getOpeningBalances()
            .getLeaveLines()
            .get(0)
            .getNumberOfUnits(),
        is(equalTo(10.0)));

    // LEAVE BALANCES
    assertThat(
        response.getEmployees().get(0).getLeaveBalances().get(0).getLeaveName(),
        is(equalTo("Carer Leave (unpaid)")));
    assertThat(
        response.getEmployees().get(0).getLeaveBalances().get(0).getNumberOfUnits(),
        is(equalTo(10.0)));
    assertThat(
        response.getEmployees().get(0).getLeaveBalances().get(0).getTypeOfUnits(),
        is(equalTo("Hours")));
    // UUID showing as Boolean?
    // assertThat(response.getEmployees().get(0).getLeaveBalances().get(0).getLeaveTypeID(),
    // is(equalTo(UUID.fromString("184ea8f7-d143-46dd-bef3-0c60e1aa6fca"))));

    // TAX DECLARATION
    assertThat(
        response.getEmployees().get(0).getTaxDeclaration().getAustralianResidentForTaxPurposes(),
        is(equalTo(true)));
    assertThat(
        response.getEmployees().get(0).getTaxDeclaration().getTaxFreeThresholdClaimed(),
        is(equalTo(true)));
    assertThat(
        response.getEmployees().get(0).getTaxDeclaration().getHasHELPDebt(), is(equalTo(false)));
    assertThat(
        response.getEmployees().get(0).getTaxDeclaration().getHasSFSSDebt(), is(equalTo(false)));
    assertThat(
        response.getEmployees().get(0).getTaxDeclaration().getEligibleToReceiveLeaveLoading(),
        is(equalTo(false)));
    assertThat(
        response.getEmployees().get(0).getTaxDeclaration().getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-11-13T05:35:06Z"))));
    assertThat(
        response.getEmployees().get(0).getTaxDeclaration().getHasStudentStartupLoan(),
        is(equalTo(false)));
    assertThat(
        response.getEmployees().get(0).getTaxDeclaration().getResidencyStatus(),
        is(equalTo(com.xero.models.payrollau.ResidencyStatus.AUSTRALIANRESIDENT)));
    assertThat(
        response.getEmployees().get(0).getOrdinaryEarningsRateID(),
        is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
    assertThat(
        response.getEmployees().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-11-13T05:35:06Z"))));
    assertThat(
        response.getEmployees().get(0).getStatus(),
        is(equalTo(com.xero.models.payrollau.EmployeeStatus.ACTIVE)));

    // System.out.println(response.toString());
  }

  @Test
  public void updateEmployeeTest() throws IOException {
    System.out.println("@Test - updateEmployeeTest");

    UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
    List<Employee> employees = new ArrayList<>();
    Employees response =
        payrollAuApi.updateEmployee(accessToken, xeroTenantId, employeeId, employees);

    assertThat(response.getEmployees().get(0).getFirstName(), is(equalTo("Albus")));
    assertThat(response.getEmployees().get(0).getLastName(), is(equalTo("Dumbledore")));
    assertThat(
        response.getEmployees().get(0).getDateOfBirthAsDate(),
        is(equalTo(LocalDate.of(1980, 3, 10))));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getAddressLine1(),
        is(equalTo("101 Green St")));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getCity(), is(equalTo("Island Bay")));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getRegion(),
        is(equalTo(com.xero.models.payrollau.State.NSW)));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getPostalCode(), is(equalTo("6023")));
    assertThat(
        response.getEmployees().get(0).getHomeAddress().getCountry(), is(equalTo("AUSTRALIA")));
    assertThat(
        response.getEmployees().get(0).getStartDateAsDate(),
        is(equalTo(LocalDate.of(1980, 3, 10))));
    assertThat(response.getEmployees().get(0).getMiddleNames(), is(equalTo("Frank")));
    assertThat(response.getEmployees().get(0).getEmail(), is(equalTo("albus39608@hogwarts.edu")));
    assertThat(
        response.getEmployees().get(0).getGender(),
        is(equalTo(com.xero.models.payrollau.Employee.GenderEnum.M)));
    assertThat(response.getEmployees().get(0).getMobile(), is(equalTo("555-1212")));
    assertThat(response.getEmployees().get(0).getPhone(), is(equalTo("444-2323")));
    assertThat(response.getEmployees().get(0).getIsAuthorisedToApproveLeave(), is(equalTo(true)));
    assertThat(
        response.getEmployees().get(0).getIsAuthorisedToApproveTimesheets(), is(equalTo(true)));
    assertThat(response.getEmployees().get(0).getJobTitle(), is(equalTo("Regional Manager")));
    assertThat(response.getEmployees().get(0).getClassification(), is(equalTo("corporate")));
    assertThat(
        response.getEmployees().get(0).getOrdinaryEarningsRateID(),
        is(equalTo(UUID.fromString("ab874dfb-ab09-4c91-954e-43acf6fc23b4"))));
    assertThat(
        response.getEmployees().get(0).getEmployeeID(),
        is(equalTo(UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"))));
    assertThat(
        response.getEmployees().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-11-13T05:05:24.755Z"))));
    assertThat(
        response.getEmployees().get(0).getStatus(),
        is(equalTo(com.xero.models.payrollau.EmployeeStatus.ACTIVE)));
    // System.out.println(response.toString());
  }
}
