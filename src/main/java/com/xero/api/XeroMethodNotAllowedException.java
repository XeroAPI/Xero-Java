package com.xero.api;

import com.xero.models.payrolluk.Problem;
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

/** handle method not allowed exception  */
public class XeroMethodNotAllowedException extends XeroException {

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
    
    /** XeroMethodNotAllowedException
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Error object with details specific to accounting API
    */
    public XeroMethodNotAllowedException(String objectType, com.xero.models.accounting.Error error) {
        this.statusCode = 405;
        this.type(objectType);
        this.elements(error.getElements());
    }

    /** XeroMethodNotAllowedException 
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Error object with details specific to assets API
    */
    public XeroMethodNotAllowedException(String objectType, com.xero.models.assets.Error error) {
        this.statusCode = 405;
        this.type = objectType;
        this.fieldValidationErrorsElements = error.getFieldValidationErrors();
    }

    /** XeroMethodNotAllowedException 
    * @param objectType String object type being interacted with when the error was returned.
    * @param error Statements object with details specific to Bank Feeds API
    */
    public XeroMethodNotAllowedException(String objectType, Statements error) {
        this.statusCode = 405;
        this.type = objectType;
        this.statementItems = error.getItems();
    }
    
    /** XeroMethodNotAllowedException 
    * @param objectType String object type being interacted with when the error was returned.
    * @param error FeedConnections object with details specific to Bank Feeds API
    */
    public XeroMethodNotAllowedException(String objectType, FeedConnections error) {
        this.statusCode = 405;
        this.type = objectType;
        this.feedConnectionItems = error.getItems();
    }
    
    /** XeroMethodNotAllowedException 
    * @param objectType String object type being interacted with when the error was returned.
    * @param problem Problem object with details specific to UK Payroll API
    * @param e Exception object with details about the original exception
    */
    public XeroMethodNotAllowedException(String objectType, com.xero.models.payrolluk.Problem problem, Exception e) {
        super(e);
        this.statusCode = 405;
        this.type = objectType;
        this.payrollUkProblem = problem;
    }
    
    /** XeroMethodNotAllowedException 
    * @param objectType String object type being interacted with when the error was returned.
    * @param problem Problem object with details specific to UK Payroll API
    * @param e Exception object with details about the original exception
    */
    public XeroMethodNotAllowedException(String objectType, com.xero.models.payrollnz.Problem problem,  Exception e) {
        super(e);
        this.statusCode = 405;
        this.type = objectType;
        this.payrollNzProblem = problem;
    }
    
    /** XeroMethodNotAllowedException 
    * @param statusCode Integer the server status code returned.
    * @param message String with details about the exception
    */
    public XeroMethodNotAllowedException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    
    /** XeroMethodNotAllowedException   
    * @param statusCode Integer the server status code returned
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException statusCode(Integer statusCode) {
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

    /** XeroMethodNotAllowedException 
    * @param statusCode Integer the server status code returned
    */
    public void setStatusCode(Integer statusCode) {
      this.statusCode = statusCode;
    }

    /** XeroMethodNotAllowedException 
    * @param type String the server status code returned
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException type(String type) {
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

    /** XeroMethodNotAllowedException 
    * @param type String the server status code returned
    */
    public void setType(String type) {
      this.type = type;
    }

    /** XeroMethodNotAllowedException 
    * @param message String with the details about the exception
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException message(String message) {
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

    /** XeroMethodNotAllowedException 
    * @param message String with the details about the exception
    */
    public void setMessage(String message) {
      this.message = message;
    }

    /** XeroMethodNotAllowedException 
    * @param elements List&lt;Element&gt; with the details about the exception
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException elements(List<Element> elements) {
      this.elements = elements;
      return this;
    }

    /** XeroMethodNotAllowedException 
    * @param elementsItem Element object with the details about the exception
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException addElementsItem(Element elementsItem) {
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

    /** XeroMethodNotAllowedException 
    * @param elements List&lt;Element&gt; with the details about the exception
    */
    public void setElements(List<Element> elements) {
      this.elements = elements;
    }
    
    /** XeroMethodNotAllowedException 
    * @param statementItems List &lt;Statement&gt; a list of bank statements
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException statementItems(List<Statement> statementItems) {
      this.statementItems = statementItems;
      return this;
    }
    
    /** XeroMethodNotAllowedException 
    * @param item Statement object containing bank statement details
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException addStatementItem(Statement item) {
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
    
    /** XeroMethodNotAllowedException 
    * @param statementItems List &lt;Statement&gt; a list of bank statements
    */
    public void setStatementItems(List<Statement> statementItems) {
      this.statementItems = statementItems;
    }
    
    /** XeroMethodNotAllowedException  
    * @param feedConnectionItems List &lt;FeedConnection&gt; a list of feed connections
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException feedConnectionItems(List<FeedConnection> feedConnectionItems) {
      this.feedConnectionItems = feedConnectionItems;
      return this;
    }
    
    /** XeroMethodNotAllowedException 
    * @param item FeedConnection a list of feed connections
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException addFeedConnectionItems(FeedConnection item) {
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
    
    /** XeroMethodNotAllowedException 
    * @param feedConnectionItems List &lt;FeedConnection&gt; a list of feed connections
    */
    public void setFeedConnectionItems(List<FeedConnection> feedConnectionItems) {
      this.feedConnectionItems = feedConnectionItems;
    }
    
    /** XeroMethodNotAllowedException 
    * @param fieldValidationErrorsElements List &lt;FieldValidationErrorsElement&gt; a list of field validation errors
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException fieldValidationErrorsElements(List<FieldValidationErrorsElement> fieldValidationErrorsElements) {
      this.fieldValidationErrorsElements = fieldValidationErrorsElements;
      return this;
    }
    
    /** XeroMethodNotAllowedException 
    * @param element FieldValidationErrorsElement a list of field validation errors
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException addFieldValidationErrorsElement(FieldValidationErrorsElement element) {
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
    
    /** XeroMethodNotAllowedException  
    * @param fieldValidationErrorsElements List &lt;FieldValidationErrorsElement&gt; a list of field validation errors
    */
    public void setFieldValidationErrorsElements(List<FieldValidationErrorsElement> fieldValidationErrorsElements) {
      this.fieldValidationErrorsElements = fieldValidationErrorsElements;
    }
        
    /** XeroMethodNotAllowedException 
    * @param problem the validation errors in NZ Payroll
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException payrollNzProblem(com.xero.models.payrollnz.Problem problem) {
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
    
    /** XeroMethodNotAllowedException 
    * @param problem the validation errors in NZ Payroll
    */
    public void setPayrollNzProblem(com.xero.models.payrollnz.Problem problem) {
      this.payrollNzProblem = problem;
    }
    
    /** XeroMethodNotAllowedException 
    * @param problem the validation errors in UK Payroll
    * @return XeroMethodNotAllowedException an instance the bad request exception  
    */
    public XeroMethodNotAllowedException payrollUkProblem(com.xero.models.payrolluk.Problem problem) {
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
    
    /** XeroMethodNotAllowedException 
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

      XeroMethodNotAllowedException error = (XeroMethodNotAllowedException) o;
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
