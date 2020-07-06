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

public class XeroMethodNotAllowedException extends RuntimeException {

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

  public XeroMethodNotAllowedException(String objectType, com.xero.models.accounting.Error error) {
    this.statusCode = 405;
    this.type(objectType);
    this.elements(error.getElements());
  }

  public XeroMethodNotAllowedException(String objectType, com.xero.models.assets.Error error) {
    this.statusCode = 405;
    this.type = objectType;
    this.fieldValidationErrorsElements = error.getFieldValidationErrors();
  }

  public XeroMethodNotAllowedException(String objectType, Statements error) {
    this.statusCode = 405;
    this.type = objectType;
    this.statementItems = error.getItems();
  }

  public XeroMethodNotAllowedException(String objectType, FeedConnections error) {
    this.statusCode = 405;
    this.type = objectType;
    this.feedConnectionItems = error.getItems();
  }

  public XeroMethodNotAllowedException(
      String objectType, com.xero.models.payrolluk.Problem problem) {
    this.statusCode = 405;
    this.type = objectType;
    this.payrollUkProblem = problem;
  }

  public XeroMethodNotAllowedException(Integer statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }

  public XeroMethodNotAllowedException statusCode(Integer statusCode) {
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

  public XeroMethodNotAllowedException type(String type) {
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

  public XeroMethodNotAllowedException message(String message) {
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

  public XeroMethodNotAllowedException elements(List<Element> elements) {
    this.elements = elements;
    return this;
  }

  public XeroMethodNotAllowedException addElementsItem(Element elementsItem) {
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
  public XeroMethodNotAllowedException statementItems(List<Statement> statementItems) {
    this.statementItems = statementItems;
    return this;
  }

  public XeroMethodNotAllowedException addStatementItem(Statement item) {
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
  public XeroMethodNotAllowedException feedConnectionItems(
      List<FeedConnection> feedConnectionItems) {
    this.feedConnectionItems = feedConnectionItems;
    return this;
  }

  public XeroMethodNotAllowedException addFeedConnectionItems(FeedConnection item) {
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
  public XeroMethodNotAllowedException fieldValidationErrorsElements(
      List<FieldValidationErrorsElement> fieldValidationErrorsElements) {
    this.fieldValidationErrorsElements = fieldValidationErrorsElements;
    return this;
  }

  public XeroMethodNotAllowedException addFieldValidationErrorsElement(
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
  public XeroMethodNotAllowedException payrollUkProblem(com.xero.models.payrolluk.Problem problem) {
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

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    XeroMethodNotAllowedException error = (XeroMethodNotAllowedException) o;
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
