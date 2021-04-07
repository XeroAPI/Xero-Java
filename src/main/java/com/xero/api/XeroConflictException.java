package com.xero.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xero.models.accounting.Element;
import com.xero.models.assets.FieldValidationErrorsElement;
import com.xero.models.bankfeeds.FeedConnection;
import com.xero.models.bankfeeds.FeedConnections;
import com.xero.models.bankfeeds.Statement;
import com.xero.models.bankfeeds.Statements;

import io.swagger.annotations.ApiModelProperty;

/** handle bank feed conflict exception  */
public class XeroConflictException extends XeroException {

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
    
    /** XeroConflictException
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Error object with details specific to accounting API
    */
    public XeroConflictException(String objectType, com.xero.models.accounting.Error error) {
        this.statusCode = 409;
        this.type(objectType);
        this.elements(error.getElements());
    }
    
    /** XeroConflictException
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Error object with details specific to assets API
    */
    public XeroConflictException(String objectType, com.xero.models.assets.Error error) {
        this.statusCode = 409;
        this.type = objectType;
        this.fieldValidationErrorsElements = error.getFieldValidationErrors();
    }
    
    /** XeroConflictException
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Statements object with details specific to Bank Feeds API
    */
    public XeroConflictException(String objectType, Statements error) {
        this.statusCode = 409;
        this.type = objectType;
        this.statementItems = error.getItems();
    }
    
    /** XeroConflictException 
    * @param objectType String object type being interacted with when the error was returned.
    * @param error FeedConnections object with details specific to Bank Feeds API
    */
    public XeroConflictException(String objectType, FeedConnections error) {
        this.statusCode = 409;
        this.type = objectType;
        this.feedConnectionItems = error.getItems();
    }
    
    /** XeroConflictException   
    * @param objectType String object type being interacted with when the error was returned.
    * @param problem Problem object with details specific to UK Payroll API
    */
    public XeroConflictException(String objectType, com.xero.models.payrolluk.Problem problem) {
        this.statusCode = 409;
        this.type = objectType;
        this.payrollUkProblem = problem;
    }
    
    /** XeroConflictException   
    * @param objectType String object type being interacted with when the error was returned.
    * @param problem Problem object with details specific to UK Payroll API
    * @param e Exception object with details about the original exception
    */
    public XeroConflictException(String objectType, com.xero.models.payrollnz.Problem problem, Exception e) {
        super(e);
        this.statusCode = 409;
        this.type = objectType;
        this.payrollNzProblem = problem;
    }
    
    /** XeroConflictException 
    * @param statusCode Integer the server status code returned.
    * @param message String with details about the exception
    */
    public XeroConflictException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    
    /** XeroConflictException  
    * @param statusCode Integer the server status code returned.
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException statusCode(Integer statusCode) {
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

    /** Set Status Code  
    * @param statusCode Integer the server status code returned.
    */
    public void setStatusCode(Integer statusCode) {
      this.statusCode = statusCode;
    }

    /** Init Type 
    * @param type String the server status code returned
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException type(String type) {
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
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException message(String message) {
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
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException elements(List<Element> elements) {
      this.elements = elements;
      return this;
    }

    /** Add Elements 
    * @param elementsItem Element object with the details about the exception
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException addElementsItem(Element elementsItem) {
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
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException statementItems(List<Statement> statementItems) {
      this.statementItems = statementItems;
      return this;
    }
    
    /** Add Statement Items
    * @param item Statement object containing bank statement details
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException addStatementItem(Statement item) {
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
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException feedConnectionItems(List<FeedConnection> feedConnectionItems) {
      this.feedConnectionItems = feedConnectionItems;
      return this;
    }
    
    /** Add Feed Connection Items 
    * @param item FeedConnection a list of feed connections
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException addFeedConnectionItems(FeedConnection item) {
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
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException fieldValidationErrorsElements(List<FieldValidationErrorsElement> fieldValidationErrorsElements) {
      this.fieldValidationErrorsElements = fieldValidationErrorsElements;
      return this;
    }
    
    /** Add Assets Field Validation Errors 
    * @param element FieldValidationErrorsElement a list of field validation errors
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException addFieldValidationErrorsElement(FieldValidationErrorsElement element) {
      if (this.fieldValidationErrorsElements == null) {
        this.fieldValidationErrorsElements = new ArrayList<FieldValidationErrorsElement>();
      }
      this.fieldValidationErrorsElements.add(element);
      return this;
    }
    
    /**
    * Array of Assets Errors Array
    * @return statementItems
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
        
    /** Init Payroll NZ Problem 
    * @param problem the validation errors in NZ Payroll
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException payrollNzProblem(com.xero.models.payrollnz.Problem problem) {
      this.payrollNzProblem = problem;
      return this;
    }
    
     /**
     * Exception type
     * @return com.xero.models.payrolluk.Problem
    **/
    @ApiModelProperty(value = "NZ Payroll problem")
    public com.xero.models.payrollnz.Problem getPayrollNzProblem() {
      return payrollNzProblem;
    }
    
    /** Set Payroll nz Problem 
    * @param problem the validation errors in NZ Payroll
    */
    public void setPayrollNzProblem(com.xero.models.payrollnz.Problem problem) {
      this.payrollNzProblem = problem;
    }
    
    /** Init Payroll UK Problem 
    * @param problem the validation errors in UK Payroll
    * @return XeroConflictException an instance the conflict exception  
    */
    public XeroConflictException payrollUkProblem(com.xero.models.payrolluk.Problem problem) {
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
      
    @Override
    public boolean equals(java.lang.Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      XeroConflictException error = (XeroConflictException) o;
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
