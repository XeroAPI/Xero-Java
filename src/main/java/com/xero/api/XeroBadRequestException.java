package com.xero.api;

import com.xero.models.payrollau.Timesheets;
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

/** handle bad request exception  */
public class XeroBadRequestException extends XeroException {

    private static final long serialVersionUID = 1L;
    private Integer statusCode;
    private String type;
    private String message;
    private List<Element> elements = new ArrayList<Element>();
    private List<Statement> statementItems = new ArrayList<Statement>();
    private List<FeedConnection> feedConnectionItems = new ArrayList<FeedConnection>();
    private List<FieldValidationErrorsElement> fieldValidationErrorsElements = new ArrayList<FieldValidationErrorsElement>();
    private com.xero.models.payrolluk.Problem payrollUkProblem = new com.xero.models.payrolluk.Problem();
    private com.xero.models.payrollnz.Problem payrollNzProblem = new com.xero.models.payrollnz.Problem();
    private List<com.xero.models.payrollau.Employee> employeeItems = new ArrayList<com.xero.models.payrollau.Employee>();
    
    private List<com.xero.models.payrollau.LeaveApplication> leaveApplicationItems = new ArrayList<com.xero.models.payrollau.LeaveApplication>();
    private List<com.xero.models.payrollau.PayItem> payItemItems = new ArrayList<com.xero.models.payrollau.PayItem>();
    private List<com.xero.models.payrollau.PayrollCalendar> payrollCalendarItems = new ArrayList<com.xero.models.payrollau.PayrollCalendar>();
    private List<com.xero.models.payrollau.SuperFund> superFundItems = new ArrayList<com.xero.models.payrollau.SuperFund>();
    private List<com.xero.models.payrollau.Timesheet> timesheetItems = new ArrayList<com.xero.models.payrollau.Timesheet>();
    private List<com.xero.models.payrollau.PayRun> payRunItems = new ArrayList<com.xero.models.payrollau.PayRun>();
    
    /** XeroBadRequestException
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Error object with details specific to accounting API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, com.xero.models.accounting.Error error, Exception e) {
        super(e);
        this.statusCode = 400;
        this.type(objectType);
        this.elements(error.getElements());
    }
    
    /** XeroBadRequestException   
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Error object with details specific to Assets API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, com.xero.models.assets.Error error, Exception e) {
        super(e);
        this.statusCode = 400;
        this.type = objectType;
        this.fieldValidationErrorsElements = error.getFieldValidationErrors();
    }
    
    /** XeroBadRequestException   
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Statements object with details specific to Bank Feeds API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, Statements error, Exception e) {
        super(e);
        this.statusCode = 400;
        this.type = objectType;
        this.statementItems = error.getItems();
    }
    
    /** XeroBadRequestException   
    * @param objectType String object type being interacted with when the error was returned.
    * @param error FeedConnections object with details specific to Bank Feeds API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, FeedConnections error, Exception e) {
        super(e);
        this.statusCode = 400;
        this.type = objectType;
        this.feedConnectionItems = error.getItems();
    }
    
    /** XeroBadRequestException   
    * @param objectType String object type being interacted with when the error was returned.
    * @param problem Problem object with details specific to UK Payroll API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, com.xero.models.payrolluk.Problem problem, Exception e) {
        super(e);
        this.statusCode = 400;
        this.type = objectType;
        this.payrollUkProblem = problem;
    }
    
    /** XeroBadRequestException   
    * @param objectType String object type being interacted with when the error was returned.
    * @param problem Problem object with details specific to NZ Payroll API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, com.xero.models.payrollnz.Problem problem, Exception e)  {
        super(e);
        this.statusCode = 400;
        this.type = objectType;
        this.payrollNzProblem = problem;
    }

    /** XeroBadRequestException   
    * @param objectType String object type being interacted with when the error was returned.
    * @param employees Employees object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, com.xero.models.payrollau.Employees employees, Exception e) {
        super(e);
        this.statusCode = 400;
        this.type = objectType;
        this.employeeItems = employees.getEmployees();
    }
    
    /** XeroBadRequestException 
    * @param objectType String object type being interacted with when the error was returned.
    * @param leaveApplications LeaveApplications object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, com.xero.models.payrollau.LeaveApplications leaveApplications, Exception e) {
        super(e);
        this.statusCode = 400;
        this.type = objectType;
        this.leaveApplicationItems = leaveApplications.getLeaveApplications();
    }

    /** XeroBadRequestException 
    * @param objectType String object type being interacted with when the error was returned.
    * @param payItems PayItems object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, com.xero.models.payrollau.PayItems payItems, Exception e) {
        super(e);
        this.statusCode = 400;
        this.type = objectType;
    }
    
    /** XeroBadRequestException 
    * @param objectType String object type being interacted with when the error was returned.
    * @param payrollCalendars PayrollCalendars object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, com.xero.models.payrollau.PayrollCalendars payrollCalendars, Exception e) {
        super(e);
        this.statusCode = 400;
        this.type = objectType;
        this.payrollCalendarItems = payrollCalendars.getPayrollCalendars();
    }
    
    /** XeroBadRequestException 
    * @param objectType String object type being interacted with when the error was returned.
    * @param superFunds SuperFunds object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, com.xero.models.payrollau.SuperFunds superFunds, Exception e) {
        this.statusCode = 400;
        this.type = objectType;
        this.superFundItems = superFunds.getSuperFunds();
    }
    
    /** XeroBadRequestException 
    * @param objectType String object type being interacted with when the error was returned.
    * @param timesheets Timesheets object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, com.xero.models.payrollau.Timesheets timesheets, Exception e) {
        super(e);
        this.statusCode = 400;
        this.type = objectType;
        this.timesheetItems = timesheets.getTimesheets();
    }
    
    /** XeroBadRequestException 
    * @param objectType String object type being interacted with when the error was returned.
    * @param payRuns PayRuns object with details specific to AU Payroll API
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(String objectType, com.xero.models.payrollau.PayRuns payRuns, Exception e) {
        super(e);
        this.statusCode = 400;
        this.type = objectType;
        this.payRunItems = payRuns.getPayRuns();
    }

    /** XeroBadRequestException 
    * @param statusCode Integer the server status code returned.
    * @param message String with details about the exception
    */
    public XeroBadRequestException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    /** XeroBadRequestException 
    * @param statusCode Integer the server status code returned.
    * @param message String with details about the exception
    * @param e Exception object with details about the original exception
    */
    public XeroBadRequestException(Integer statusCode, String message, Exception e) {
        super(message, e);
        this.statusCode = statusCode;
        this.message = message;
    }

    /** Init StatusCode
    * @param statusCode Integer the server status code returned
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException statusCode(Integer statusCode) {
      this.statusCode = statusCode;
      return this;
    }
 
     /**
     * Exception number
     * @return statusCode
    **/
    @ApiModelProperty(value = "Status Code")
    public Integer getStatusCode() {
      return statusCode;
    }

    /** Set StatusCode 
    * @param statusCode Integer the server status code returned
    */
    public void setStatusCode(Integer statusCode) {
      this.statusCode = statusCode;
    }

    /** Init Type 
    * @param type String the server status code returned
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException type(String type) {
      this.type = type;
      return this;
    }

     /**
     * Exception type
     * @return type
    **/
    @ApiModelProperty(value = "API set type")
    public String getType() {
      return type;
    }

    /** Set Type
    * @param type String the server status code returned
    */
    public void setType(String type) {
      this.type = type;
    }

    /** Init Message
    * @param message String with the details about the exception
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException message(String message) {
      this.message = message;
      return this;
    }

     /**
     * Exception message
     * @return message
    **/
    @ApiModelProperty(value = "Exception message")
    public String getMessage() {
      return message;
    }

    /** Set Message
    * @param message String with the details about the exception
    */
    public void setMessage(String message) {
      this.message = message;
    }

    /** Init Elements
    * @param elements List&lt;Element&gt; with the details about the exception
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException elements(List<Element> elements) {
      this.elements = elements;
      return this;
    }

    /** Add Elements 
    * @param elementsItem Element object with the details about the exception
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException addElementsItem(Element elementsItem) {
      if (this.elements == null) {
        this.elements = new ArrayList<Element>();
      }
      this.elements.add(elementsItem);
      return this;
    }

     /**
     * Array of Elements of validation Errors
     * @return elements
    **/
    @ApiModelProperty(value = "Array of Elements of validation Errors")
    public List<Element> getElements() {
      return elements;
    }

    /** Set Elements 
    * @param elements List&lt;Element&gt; with the details about the exception
    */
    public void setElements(List<Element> elements) {
      this.elements = elements;
    }
    
    /** Init Statement Items 
    * @param statementItems List &lt;Statement&gt; a list of bank statements
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException statementItems(List<Statement> statementItems) {
      this.statementItems = statementItems;
      return this;
    }
    
    /** Add Statement Items
    * @param item Statement object containing bank statement details
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException addStatementItem(Statement item) {
      if (this.statementItems == null) {
        this.statementItems = new ArrayList<Statement>();
      }
      this.statementItems.add(item);
      return this;
    }
    
    /**
    * Array of Statements Items of Errors Array
    * @return statementItems
    **/
    @ApiModelProperty(value = "Array of Statement Items with Errors Array")
    public List<Statement> getStatementItems() {
      return statementItems;
    }
    
    /** Set Statement Items
    * @param statementItems List &lt;Statement&gt; a list of bank statements
    */
    public void setStatementItems(List<Statement> statementItems) {
      this.statementItems = statementItems;
    }
    
    /** Init Feed Connection Items 
    * @param feedConnectionItems List &lt;FeedConnection&gt; a list of feed connections
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException feedConnectionItems(List<FeedConnection> feedConnectionItems) {
      this.feedConnectionItems = feedConnectionItems;
      return this;
    }
    
    /** Add Feed Connection Items 
    * @param item FeedConnection a list of feed connections
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException addFeedConnectionItems(FeedConnection item) {
      if (this.feedConnectionItems == null) {
        this.feedConnectionItems = new ArrayList<FeedConnection>();
      }
      this.feedConnectionItems.add(item);
      return this;
    }
    
    /**
    * Array of FeedConnection of Errors Array
    * @return statementItems
    **/
    @ApiModelProperty(value = "Array of FeedConnection Items with Errors Array")
    public List<FeedConnection> getFeedConnectionItems() {
      return feedConnectionItems;
    }
    
    /** Set Feed Connection Items
    * @param feedConnectionItems List &lt;FeedConnection&gt; a list of feed connections
    */
    public void setFeedConnectionItems(List<FeedConnection> feedConnectionItems) {
      this.feedConnectionItems = feedConnectionItems;
    }
    
    /** Init Assets Field Validation Errors 
    * @param fieldValidationErrorsElements List &lt;FieldValidationErrorsElement&gt; a list of field validation errors
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException fieldValidationErrorsElements(List<FieldValidationErrorsElement> fieldValidationErrorsElements) {
      this.fieldValidationErrorsElements = fieldValidationErrorsElements;
      return this;
    }
    
    /** Add Assets Field Validation Errors 
    * @param element FieldValidationErrorsElement a list of field validation errors
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException addFieldValidationErrorsElement(FieldValidationErrorsElement element) {
      if (this.fieldValidationErrorsElements == null) {
        this.fieldValidationErrorsElements = new ArrayList<FieldValidationErrorsElement>();
      }
      this.fieldValidationErrorsElements.add(element);
      return this;
    }
    
    /**
    * Array of Assets Errors Array
    * @return List&lt;FieldValidationErrorsElement&gt;
    **/
    @ApiModelProperty(value = "Array of FieldValidationErrorElement")
    public List<FieldValidationErrorsElement> getFieldValidationErrorsElements() {
      return fieldValidationErrorsElements;
    }
    
    /** Set Assets Field Validation Errors 
    * @param fieldValidationErrorsElements List &lt;FieldValidationErrorsElement&gt; a list of field validation errors
    */
    public void setFieldValidationErrorsElements(List<FieldValidationErrorsElement> fieldValidationErrorsElements) {
      this.fieldValidationErrorsElements = fieldValidationErrorsElements;
    }
    
    /** Init Payroll UK Problem 
    * @param problem the validation errors in UK Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException payrollUkProblem(com.xero.models.payrolluk.Problem problem) {
      this.payrollUkProblem = problem;
      return this;
    }
    
     /**
     * Exception type
     * @return com.xero.models.payrolluk.Problem
    **/
    @ApiModelProperty(value = "UK Payroll problem")
    public com.xero.models.payrolluk.Problem getPayrollUkProblem() {
      return payrollUkProblem;
    }
    
    /** Set Payroll UK Problem 
    * @param problem the validation errors in UK Payroll
    */
    public void setPayrollUkProblem(com.xero.models.payrolluk.Problem problem) {
      this.payrollUkProblem = problem;
    }
      
    /** Init Payroll NZ Problem 
    * @param problem the validation errors in NZ Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException payrollNzProblem(com.xero.models.payrollnz.Problem problem) {
      this.payrollNzProblem = problem;
      return this;
    }

    /**
     * Exception type
     * @return com.xero.models.payrollnz.Problem
    **/
    @ApiModelProperty(value = "NZ Payroll problem")
    public com.xero.models.payrollnz.Problem getPayrollNzProblem() {
      return payrollNzProblem;
    }

    /** Set Payroll NZ Problem 
    * @param problem the validation errors in NZ Payroll
    */
    public void setPayrollNzProblem(com.xero.models.payrollnz.Problem problem) {
      this.payrollNzProblem = problem;
    }

    /** Init Payroll AU Employee items 
    * @param employeeItems List &lt;com.xero.models.payrollau.Employee&gt; with validation errors in AU Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException employeeItemsItems(List<com.xero.models.payrollau.Employee> employeeItems) {
      this.employeeItems = employeeItems;
      return this;
    }
    
    /** Add Payroll AU Employee items 
    * @param item Employee with validation errors in AU Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException addEmployeeItems(com.xero.models.payrollau.Employee item) {
      if (this.employeeItems == null) {
        this.employeeItems = new ArrayList<com.xero.models.payrollau.Employee>();
      }
      this.employeeItems.add(item);
      return this;
    }
    
    /**
    * Array of FeedConnection of Errors Array
    * @return statementItems
    **/
    @ApiModelProperty(value = "Array of FeedConnection Items with Errors Array")
    public List<com.xero.models.payrollau.Employee> getEmployeeItems() {
      return employeeItems;
    }

    /** Set Payroll AU Employee items 
    * @param employeeItems List &lt;com.xero.models.payrollau.Employee&gt; with validation errors in AU Payroll
    */
    public void setEmployeeItems(List<com.xero.models.payrollau.Employee> employeeItems) {
      this.employeeItems = employeeItems;
    }
    
    /** Init Payroll AU PayItem items
    * @param payItemItems List &lt;com.xero.models.payrollau.PayItem&gt; with validation errors in AU Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException payItemItems(List<com.xero.models.payrollau.PayItem> payItemItems) {
      this.payItemItems = payItemItems;
      return this;
    }
    
    /** Add Payroll AU PayItem items 
    * @param item com.xero.models.payrollau.PayItem with validation errors in AU Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException addPayItemItems(com.xero.models.payrollau.PayItem item) {
      if (this.payItemItems == null) {
        this.payItemItems = new ArrayList<com.xero.models.payrollau.PayItem>();
      }
      this.payItemItems.add(item);
      return this;
    }
    
    /**
    * Array of PayItems with Validation Error Array
    * @return payItems
    **/
    @ApiModelProperty(value = "Array of PayItems with Validation Array")
    public List<com.xero.models.payrollau.PayItem> getPayItemItems() {
      return payItemItems;
    }
    
    /** Set Payroll AU PayItem items 
    * @param payItems List &lt;com.xero.models.payrollau.PayItem&gt; with validation errors in AU Payroll
    */
    public void setPayItemItems(List<com.xero.models.payrollau.PayItem> payItems) {
      this.payItemItems = payItems;
    }
    
    /** Init Payroll AU PayRun items 
    * @param payRunItems List &lt;com.xero.models.payrollau.PayRun&gt; with validation errors in AU Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException payRunItems(List<com.xero.models.payrollau.PayRun> payRunItems) {
      this.payRunItems = payRunItems;
      return this;
    }
    
    /** Add Payroll AU PayRun items 
    * @param item com.xero.models.payrollau.PayRun with validation errors in AU Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException addPayRunItems(com.xero.models.payrollau.PayRun item) {
      if (this.payRunItems == null) {
        this.payRunItems = new ArrayList<com.xero.models.payrollau.PayRun>();
      }
      this.payRunItems.add(item);
      return this;
    }
    
    /**
    * Array of PayRun with Validation Error Array
    * @return payRunItems
    **/
    @ApiModelProperty(value = "Array of PayRun with Validation Array")
    public List<com.xero.models.payrollau.PayRun> getPayRunItems() {
      return payRunItems;
    }
    
    /** Set Payroll AU PayRun items 
    * @param payRunItems List &lt;com.xero.models.payrollau.PayRun&gt; with validation errors in AU Payroll
    */
    public void setPayRunItems(List<com.xero.models.payrollau.PayRun> payRunItems) {
      this.payRunItems = payRunItems;
    }
    
    /** Init Payroll AU Calendar items 
    * @param payrollCalendarItems List &lt;com.xero.models.payrollau.PayrollCalendar&gt; with validation errors in AU Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException payrollCalendarItems(List<com.xero.models.payrollau.PayrollCalendar> payrollCalendarItems) {
      this.payrollCalendarItems = payrollCalendarItems;
      return this;
    }
    
    /** Add Payroll AU Calendar items 
    * @param item com.xero.models.payrollau.PayrollCalendar with validation errors in AU Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException addPayrollCalendarItems(com.xero.models.payrollau.PayrollCalendar item) {
      if (this.payrollCalendarItems == null) {
        this.payrollCalendarItems = new ArrayList<com.xero.models.payrollau.PayrollCalendar>();
      }
      this.payrollCalendarItems.add(item);
      return this;
    }
    
    /**
    * Array of PayrollCalendar with Validation Error Array
    * @return payrollCalendarItems
    **/
    @ApiModelProperty(value = "Array of PayrollCalendar with Validation Array")
    public List<com.xero.models.payrollau.PayrollCalendar> getPayrollCalendarItems() {
      return payrollCalendarItems;
    }
    
    /** Set Payroll AU Calendar items
    * @param payrollCalendarItems List &lt;com.xero.models.payrollau.PayrollCalendar&gt; with validation errors in AU Payroll
    */
    public void setPayrollCalendarItems(List<com.xero.models.payrollau.PayrollCalendar> payrollCalendarItems) {
      this.payrollCalendarItems = payrollCalendarItems;
    }
    
    
    /** Init Payroll AU SuperFund items 
    * @param superFundItems List &lt;com.xero.models.payrollau.SuperFund&gt; with validation errors in AU Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException superFundItems(List<com.xero.models.payrollau.SuperFund> superFundItems) {
      this.superFundItems = superFundItems;
      return this;
    }
    
    /** Add Payroll AU SuperFund items 
    * @param item com.xero.models.payrollau.SuperFund with validation errors in AU Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException addSuperFundItems(com.xero.models.payrollau.SuperFund item) {
      if (this.superFundItems == null) {
        this.superFundItems = new ArrayList<com.xero.models.payrollau.SuperFund>();
      }
      this.superFundItems.add(item);
      return this;
    }
    
    /**
    * Array of SuperFund with Validation Error Array
    * @return superFundItems
    **/
    @ApiModelProperty(value = "Array of SuperFund with Validation Array")
    public List<com.xero.models.payrollau.SuperFund> getSuperFundItems() {
      return superFundItems;
    }
    
    /** Set Payroll AU SuperFund items 
    * @param superFundItems List &lt;com.xero.models.payrollau.SuperFund&gt; with validation errors in AU Payroll
    */
    public void setSuperFundItems(List<com.xero.models.payrollau.SuperFund> superFundItems) {
      this.superFundItems = superFundItems;
    }
    
    /** Initilize Payroll AU Timesheet items 
    * @param timesheetItems List &lt;com.xero.models.payrollau.Timesheet&gt; with validation errors in AU Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException timesheetItems(List<com.xero.models.payrollau.Timesheet> timesheetItems) {
      this.timesheetItems = timesheetItems;
      return this;
    }

    /** Add Payroll AU Timesheet items 
    * @param item com.xero.models.payrollau.Timesheet with validation errors in AU Payroll
    * @return XeroBadRequestException an instance the bad request exception  
    */
    public XeroBadRequestException addTimesheetItems(com.xero.models.payrollau.Timesheet item) {
      if (this.timesheetItems == null) {
        this.timesheetItems = new ArrayList<com.xero.models.payrollau.Timesheet>();
      }
      this.timesheetItems.add(item);
      return this;
    }
    
    /**
    * Array of Timesheet with Validation Error Array
    * @return timesheetItems
    **/
    @ApiModelProperty(value = "Array of Timesheet with Validation Array")
    public List<com.xero.models.payrollau.Timesheet> getTimesheetItems() {
      return timesheetItems;
    }
    
    /** Set Payroll AU Timesheet items 
    * @param timesheetItems List &lt;com.xero.models.payrollau.Timesheet&gt; with validation errors in AU Payroll
    **/
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
      return Objects.equals(this.statusCode, error.statusCode) &&
          Objects.equals(this.type, error.type) &&
          Objects.equals(this.message, error.message) &&
          Objects.equals(this.elements, error.elements);
    }

    @Override
    public int hashCode() {
      return Objects.hash(statusCode, type, message);  //, elements
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
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
      if (o == null) {
        return "null";
      }
      return o.toString().replace("\n", "\n    ");
    }

}
