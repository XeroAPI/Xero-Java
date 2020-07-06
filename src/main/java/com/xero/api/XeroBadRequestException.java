package com.xero.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.xero.models.accounting.Element;
import com.xero.models.assets.FieldValidationErrorsElement;
import com.xero.models.bankfeeds.FeedConnection;
import com.xero.models.bankfeeds.FeedConnections;
import com.xero.models.bankfeeds.Statement;
import com.xero.models.bankfeeds.Statements;

import io.swagger.annotations.ApiModelProperty;

public class XeroBadRequestException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private Integer statusCode;
  private String type;
  private String message;
  private List<Element> elements = new ArrayList<Element>();
  private List<Statement> statementItems = new ArrayList<Statement>();
  private List<FeedConnection> feedConnectionItems = new ArrayList<FeedConnection>();
  private List<FieldValidationErrorsElement> fieldValidationErrorsElements =
      new ArrayList<FieldValidationErrorsElement>();
  private com.xero.models.payrolluk.Problem payrollUkProblem =
      new com.xero.models.payrolluk.Problem();
  private List<com.xero.models.payrollau.Employee> employeeItems =
      new ArrayList<com.xero.models.payrollau.Employee>();

  private List<com.xero.models.payrollau.LeaveApplication> leaveApplicationItems =
      new ArrayList<com.xero.models.payrollau.LeaveApplication>();
  private List<com.xero.models.payrollau.PayItem> payItemItems =
      new ArrayList<com.xero.models.payrollau.PayItem>();
  private List<com.xero.models.payrollau.PayrollCalendar> payrollCalendarItems =
      new ArrayList<com.xero.models.payrollau.PayrollCalendar>();
  private List<com.xero.models.payrollau.SuperFund> superFundItems =
      new ArrayList<com.xero.models.payrollau.SuperFund>();
  private List<com.xero.models.payrollau.Timesheet> timesheetItems =
      new ArrayList<com.xero.models.payrollau.Timesheet>();
  private List<com.xero.models.payrollau.PayRun> payRunItems =
      new ArrayList<com.xero.models.payrollau.PayRun>();

  public XeroBadRequestException(String objectType, com.xero.models.accounting.Error error) {
    this.statusCode = 400;
    this.type(objectType);
    this.elements(error.getElements());
  }

  public XeroBadRequestException(String objectType, com.xero.models.assets.Error error) {
    this.statusCode = 400;
    this.type = objectType;
    this.fieldValidationErrorsElements = error.getFieldValidationErrors();
  }

  public XeroBadRequestException(String objectType, Statements error) {
    this.statusCode = 400;
    this.type = objectType;
    this.statementItems = error.getItems();
  }

  public XeroBadRequestException(String objectType, FeedConnections error) {
    this.statusCode = 400;
    this.type = objectType;
    this.feedConnectionItems = error.getItems();
  }

  public XeroBadRequestException(String objectType, com.xero.models.payrolluk.Problem problem) {
    this.statusCode = 400;
    this.type = objectType;
    this.payrollUkProblem = problem;
  }

  public XeroBadRequestException(String objectType, com.xero.models.payrollau.Employees employees) {
    this.statusCode = 400;
    this.type = objectType;
    this.employeeItems = employees.getEmployees();
  }

  public XeroBadRequestException(
      String objectType, com.xero.models.payrollau.LeaveApplications leaveApplications) {
    this.statusCode = 400;
    this.type = objectType;
    this.leaveApplicationItems = leaveApplications.getLeaveApplications();
  }

  public XeroBadRequestException(String objectType, com.xero.models.payrollau.PayItems payItems) {
    this.statusCode = 400;
    this.type = objectType;
    // this.payItemItems = payItems.g
  }

  public XeroBadRequestException(
      String objectType, com.xero.models.payrollau.PayrollCalendars payrollCalendars) {
    this.statusCode = 400;
    this.type = objectType;
    this.payrollCalendarItems = payrollCalendars.getPayrollCalendars();
  }

  public XeroBadRequestException(
      String objectType, com.xero.models.payrollau.SuperFunds superFunds) {
    this.statusCode = 400;
    this.type = objectType;
    this.superFundItems = superFunds.getSuperFunds();
  }

  public XeroBadRequestException(
      String objectType, com.xero.models.payrollau.Timesheets timesheets) {
    this.statusCode = 400;
    this.type = objectType;
    this.timesheetItems = timesheets.getTimesheets();
  }

  public XeroBadRequestException(String objectType, com.xero.models.payrollau.PayRuns payRuns) {
    this.statusCode = 400;
    this.type = objectType;
    this.payRunItems = payRuns.getPayRuns();
  }

  public XeroBadRequestException(Integer statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }

  public XeroBadRequestException statusCode(Integer statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  /**
   * Exception number
   *
   * @return statusCode
   */
  @ApiModelProperty(value = "Status Code")
  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }

  public XeroBadRequestException type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Exception type
   *
   * @return type
   */
  @ApiModelProperty(value = "API set type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public XeroBadRequestException message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Exception message
   *
   * @return message
   */
  @ApiModelProperty(value = "Exception message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public XeroBadRequestException elements(List<Element> elements) {
    this.elements = elements;
    return this;
  }

  public XeroBadRequestException addElementsItem(Element elementsItem) {
    if (this.elements == null) {
      this.elements = new ArrayList<Element>();
    }
    this.elements.add(elementsItem);
    return this;
  }

  /**
   * Array of Elements of validation Errors
   *
   * @return elements
   */
  @ApiModelProperty(value = "Array of Elements of validation Errors")
  public List<Element> getElements() {
    return elements;
  }

  public void setElements(List<Element> elements) {
    this.elements = elements;
  }

  // Bank Feed Statement items
  public XeroBadRequestException statementItems(List<Statement> statementItems) {
    this.statementItems = statementItems;
    return this;
  }

  public XeroBadRequestException addStatementItem(Statement item) {
    if (this.statementItems == null) {
      this.statementItems = new ArrayList<Statement>();
    }
    this.statementItems.add(item);
    return this;
  }

  /**
   * Array of Statements Items of Errors Array
   *
   * @return statementItems
   */
  @ApiModelProperty(value = "Array of Statement Items with Errors Array")
  public List<Statement> getStatementItems() {
    return statementItems;
  }

  public void setStatementItems(List<Statement> statementItems) {
    this.statementItems = statementItems;
  }

  // Bank Feed FeedConnection items
  public XeroBadRequestException feedConnectionItems(List<FeedConnection> feedConnectionItems) {
    this.feedConnectionItems = feedConnectionItems;
    return this;
  }

  public XeroBadRequestException addFeedConnectionItems(FeedConnection item) {
    if (this.feedConnectionItems == null) {
      this.feedConnectionItems = new ArrayList<FeedConnection>();
    }
    this.feedConnectionItems.add(item);
    return this;
  }

  /**
   * Array of FeedConnection of Errors Array
   *
   * @return statementItems
   */
  @ApiModelProperty(value = "Array of FeedConnection Items with Errors Array")
  public List<FeedConnection> getFeedConnectionItems() {
    return feedConnectionItems;
  }

  public void setFeedConnectionItems(List<FeedConnection> feedConnectionItems) {
    this.feedConnectionItems = feedConnectionItems;
  }

  // Assets field Validation Errors
  public XeroBadRequestException fieldValidationErrorsElements(
      List<FieldValidationErrorsElement> fieldValidationErrorsElements) {
    this.fieldValidationErrorsElements = fieldValidationErrorsElements;
    return this;
  }

  public XeroBadRequestException addFieldValidationErrorsElement(
      FieldValidationErrorsElement element) {
    if (this.fieldValidationErrorsElements == null) {
      this.fieldValidationErrorsElements = new ArrayList<FieldValidationErrorsElement>();
    }
    this.fieldValidationErrorsElements.add(element);
    return this;
  }

  /**
   * Array of Assets Errors Array
   *
   * @return statementItems
   */
  @ApiModelProperty(value = "Array of FieldValidationErrorElement")
  public List<FieldValidationErrorsElement> getFieldValidationErrorsElements() {
    return fieldValidationErrorsElements;
  }

  public void setFieldValidationErrorsElements(
      List<FieldValidationErrorsElement> fieldValidationErrorsElements) {
    this.fieldValidationErrorsElements = fieldValidationErrorsElements;
  }

  // UK Payroll Problems Errors
  public XeroBadRequestException payrollUkProblem(com.xero.models.payrolluk.Problem problem) {
    this.payrollUkProblem = problem;
    return this;
  }

  /**
   * Exception type
   *
   * @return com.xero.models.payrolluk.Problem
   */
  @ApiModelProperty(value = "UK Payroll problem")
  public com.xero.models.payrolluk.Problem getPayrollUkProblem() {
    return payrollUkProblem;
  }

  public void setPayrollUkProblem(com.xero.models.payrolluk.Problem problem) {
    this.payrollUkProblem = problem;
  }

  // Payroll AU Employees
  public XeroBadRequestException employeeItemsItems(
      List<com.xero.models.payrollau.Employee> employeeItems) {
    this.employeeItems = employeeItems;
    return this;
  }

  public XeroBadRequestException addEmployeeItems(com.xero.models.payrollau.Employee item) {
    if (this.employeeItems == null) {
      this.employeeItems = new ArrayList<com.xero.models.payrollau.Employee>();
    }
    this.employeeItems.add(item);
    return this;
  }

  /**
   * Array of FeedConnection of Errors Array
   *
   * @return statementItems
   */
  @ApiModelProperty(value = "Array of FeedConnection Items with Errors Array")
  public List<com.xero.models.payrollau.Employee> getEmployeeItems() {
    return employeeItems;
  }

  public void setEmployeeItems(List<com.xero.models.payrollau.Employee> employeeItems) {
    this.employeeItems = employeeItems;
  }

  // Payroll AU PayItems
  public XeroBadRequestException payItemItems(
      List<com.xero.models.payrollau.PayItem> payItemItems) {
    this.payItemItems = payItemItems;
    return this;
  }

  public XeroBadRequestException addPayItemItems(com.xero.models.payrollau.PayItem item) {
    if (this.payItemItems == null) {
      this.payItemItems = new ArrayList<com.xero.models.payrollau.PayItem>();
    }
    this.payItemItems.add(item);
    return this;
  }

  /**
   * Array of PayItems with Validation Error Array
   *
   * @return payItems
   */
  @ApiModelProperty(value = "Array of PayItems with Validation Array")
  public List<com.xero.models.payrollau.PayItem> getPayItemItems() {
    return payItemItems;
  }

  public void setPayItemItems(List<com.xero.models.payrollau.PayItem> payItems) {
    this.payItemItems = payItems;
  }

  // Payroll AU PayRun
  public XeroBadRequestException payRunItems(List<com.xero.models.payrollau.PayRun> payRunItems) {
    this.payRunItems = payRunItems;
    return this;
  }

  public XeroBadRequestException addPayRunItems(com.xero.models.payrollau.PayRun item) {
    if (this.payRunItems == null) {
      this.payRunItems = new ArrayList<com.xero.models.payrollau.PayRun>();
    }
    this.payRunItems.add(item);
    return this;
  }

  /**
   * Array of PayRun with Validation Error Array
   *
   * @return payRunItems
   */
  @ApiModelProperty(value = "Array of PayRun with Validation Array")
  public List<com.xero.models.payrollau.PayRun> getPayRunItems() {
    return payRunItems;
  }

  public void setPayRunItems(List<com.xero.models.payrollau.PayRun> payRunItems) {
    this.payRunItems = payRunItems;
  }

  // Payroll AU PayrollCalendar
  public XeroBadRequestException payrollCalendarItems(
      List<com.xero.models.payrollau.PayrollCalendar> payrollCalendarItems) {
    this.payrollCalendarItems = payrollCalendarItems;
    return this;
  }

  public XeroBadRequestException addPayrollCalendarItems(
      com.xero.models.payrollau.PayrollCalendar item) {
    if (this.payrollCalendarItems == null) {
      this.payrollCalendarItems = new ArrayList<com.xero.models.payrollau.PayrollCalendar>();
    }
    this.payrollCalendarItems.add(item);
    return this;
  }

  /**
   * Array of PayrollCalendar with Validation Error Array
   *
   * @return payrollCalendarItems
   */
  @ApiModelProperty(value = "Array of PayrollCalendar with Validation Array")
  public List<com.xero.models.payrollau.PayrollCalendar> getPayrollCalendarItems() {
    return payrollCalendarItems;
  }

  public void setPayrollCalendarItems(
      List<com.xero.models.payrollau.PayrollCalendar> payrollCalendarItems) {
    this.payrollCalendarItems = payrollCalendarItems;
  }

  // Payroll AU SuperFund
  public XeroBadRequestException superFundItems(
      List<com.xero.models.payrollau.SuperFund> superFundItems) {
    this.superFundItems = superFundItems;
    return this;
  }

  public XeroBadRequestException addSuperFundItems(com.xero.models.payrollau.SuperFund item) {
    if (this.superFundItems == null) {
      this.superFundItems = new ArrayList<com.xero.models.payrollau.SuperFund>();
    }
    this.superFundItems.add(item);
    return this;
  }

  /**
   * Array of SuperFund with Validation Error Array
   *
   * @return superFundItems
   */
  @ApiModelProperty(value = "Array of SuperFund with Validation Array")
  public List<com.xero.models.payrollau.SuperFund> getSuperFundItems() {
    return superFundItems;
  }

  public void setSuperFundItems(List<com.xero.models.payrollau.SuperFund> superFundItems) {
    this.superFundItems = superFundItems;
  }

  // Payroll AU Timesheet
  public XeroBadRequestException timesheetItems(
      List<com.xero.models.payrollau.Timesheet> timesheetItems) {
    this.timesheetItems = timesheetItems;
    return this;
  }

  public XeroBadRequestException addTimesheetItems(com.xero.models.payrollau.Timesheet item) {
    if (this.timesheetItems == null) {
      this.timesheetItems = new ArrayList<com.xero.models.payrollau.Timesheet>();
    }
    this.timesheetItems.add(item);
    return this;
  }

  /**
   * Array of Timesheet with Validation Error Array
   *
   * @return timesheetItems
   */
  @ApiModelProperty(value = "Array of Timesheet with Validation Array")
  public List<com.xero.models.payrollau.Timesheet> getTimesheetItems() {
    return timesheetItems;
  }

  public void setTimesheetItems(List<com.xero.models.payrollau.Timesheet> timesheetItems) {
    this.timesheetItems = timesheetItems;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    XeroBadRequestException error = (XeroBadRequestException) o;
    return Objects.equals(this.statusCode, error.statusCode)
        && Objects.equals(this.type, error.type)
        && Objects.equals(this.message, error.message)
        && Objects.equals(this.elements, error.elements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, type, message); // , elements
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    elements: ").append(toIndentedString(elements)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
