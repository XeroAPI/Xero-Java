/*
 * Xero Payroll NZ
 * This is the Xero Payroll API for orgs in the NZ region.
 *
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.xero.models.payrollnz;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xero.api.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** EmployeeWorkingPatternsObject */
public class EmployeeWorkingPatternsObject {
  StringUtil util = new StringUtil();

  @JsonProperty("pagination")
  private Pagination pagination;

  @JsonProperty("problem")
  private Problem problem;

  @JsonProperty("payeeWorkingPatterns")
  private List<EmployeeWorkingPattern> payeeWorkingPatterns =
      new ArrayList<EmployeeWorkingPattern>();
  /**
   * pagination
   *
   * @param pagination Pagination
   * @return EmployeeWorkingPatternsObject
   */
  public EmployeeWorkingPatternsObject pagination(Pagination pagination) {
    this.pagination = pagination;
    return this;
  }

  /**
   * Get pagination
   *
   * @return pagination
   */
  @ApiModelProperty(value = "")
  /**
   * pagination
   *
   * @return pagination Pagination
   */
  public Pagination getPagination() {
    return pagination;
  }

  /**
   * pagination
   *
   * @param pagination Pagination
   */
  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }

  /**
   * problem
   *
   * @param problem Problem
   * @return EmployeeWorkingPatternsObject
   */
  public EmployeeWorkingPatternsObject problem(Problem problem) {
    this.problem = problem;
    return this;
  }

  /**
   * Get problem
   *
   * @return problem
   */
  @ApiModelProperty(value = "")
  /**
   * problem
   *
   * @return problem Problem
   */
  public Problem getProblem() {
    return problem;
  }

  /**
   * problem
   *
   * @param problem Problem
   */
  public void setProblem(Problem problem) {
    this.problem = problem;
  }

  /**
   * payeeWorkingPatterns
   *
   * @param payeeWorkingPatterns List&lt;EmployeeWorkingPattern&gt;
   * @return EmployeeWorkingPatternsObject
   */
  public EmployeeWorkingPatternsObject payeeWorkingPatterns(
      List<EmployeeWorkingPattern> payeeWorkingPatterns) {
    this.payeeWorkingPatterns = payeeWorkingPatterns;
    return this;
  }

  /**
   * payeeWorkingPatterns
   *
   * @param payeeWorkingPatternsItem EmployeeWorkingPattern
   * @return EmployeeWorkingPatternsObject
   */
  public EmployeeWorkingPatternsObject addPayeeWorkingPatternsItem(
      EmployeeWorkingPattern payeeWorkingPatternsItem) {
    if (this.payeeWorkingPatterns == null) {
      this.payeeWorkingPatterns = new ArrayList<EmployeeWorkingPattern>();
    }
    this.payeeWorkingPatterns.add(payeeWorkingPatternsItem);
    return this;
  }

  /**
   * Get payeeWorkingPatterns
   *
   * @return payeeWorkingPatterns
   */
  @ApiModelProperty(value = "")
  /**
   * payeeWorkingPatterns
   *
   * @return payeeWorkingPatterns List<EmployeeWorkingPattern>
   */
  public List<EmployeeWorkingPattern> getPayeeWorkingPatterns() {
    return payeeWorkingPatterns;
  }

  /**
   * payeeWorkingPatterns
   *
   * @param payeeWorkingPatterns List&lt;EmployeeWorkingPattern&gt;
   */
  public void setPayeeWorkingPatterns(List<EmployeeWorkingPattern> payeeWorkingPatterns) {
    this.payeeWorkingPatterns = payeeWorkingPatterns;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeWorkingPatternsObject employeeWorkingPatternsObject = (EmployeeWorkingPatternsObject) o;
    return Objects.equals(this.pagination, employeeWorkingPatternsObject.pagination)
        && Objects.equals(this.problem, employeeWorkingPatternsObject.problem)
        && Objects.equals(
            this.payeeWorkingPatterns, employeeWorkingPatternsObject.payeeWorkingPatterns);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pagination, problem, payeeWorkingPatterns);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmployeeWorkingPatternsObject {\n");
    sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
    sb.append("    problem: ").append(toIndentedString(problem)).append("\n");
    sb.append("    payeeWorkingPatterns: ")
        .append(toIndentedString(payeeWorkingPatterns))
        .append("\n");
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
