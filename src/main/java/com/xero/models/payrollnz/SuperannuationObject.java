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
import java.util.Objects;

/** SuperannuationObject */
public class SuperannuationObject {
  StringUtil util = new StringUtil();

  @JsonProperty("pagination")
  private Pagination pagination;

  @JsonProperty("problem")
  private Problem problem;

  @JsonProperty("benefit")
  private Benefit benefit;
  /**
   * pagination
   *
   * @param pagination Pagination
   * @return SuperannuationObject
   */
  public SuperannuationObject pagination(Pagination pagination) {
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
   * @return SuperannuationObject
   */
  public SuperannuationObject problem(Problem problem) {
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
   * benefit
   *
   * @param benefit Benefit
   * @return SuperannuationObject
   */
  public SuperannuationObject benefit(Benefit benefit) {
    this.benefit = benefit;
    return this;
  }

  /**
   * Get benefit
   *
   * @return benefit
   */
  @ApiModelProperty(value = "")
  /**
   * benefit
   *
   * @return benefit Benefit
   */
  public Benefit getBenefit() {
    return benefit;
  }

  /**
   * benefit
   *
   * @param benefit Benefit
   */
  public void setBenefit(Benefit benefit) {
    this.benefit = benefit;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SuperannuationObject superannuationObject = (SuperannuationObject) o;
    return Objects.equals(this.pagination, superannuationObject.pagination)
        && Objects.equals(this.problem, superannuationObject.problem)
        && Objects.equals(this.benefit, superannuationObject.benefit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pagination, problem, benefit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SuperannuationObject {\n");
    sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
    sb.append("    problem: ").append(toIndentedString(problem)).append("\n");
    sb.append("    benefit: ").append(toIndentedString(benefit)).append("\n");
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
