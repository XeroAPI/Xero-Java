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

/** EmployeeLeaveSetupObject */
public class EmployeeLeaveSetupObject {
  StringUtil util = new StringUtil();

  @JsonProperty("pagination")
  private Pagination pagination;

  @JsonProperty("problem")
  private Problem problem;

  @JsonProperty("leaveSetup")
  private EmployeeLeaveSetup leaveSetup;
  /**
   * pagination
   *
   * @param pagination Pagination
   * @return EmployeeLeaveSetupObject
   */
  public EmployeeLeaveSetupObject pagination(Pagination pagination) {
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
   * @return EmployeeLeaveSetupObject
   */
  public EmployeeLeaveSetupObject problem(Problem problem) {
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
   * leaveSetup
   *
   * @param leaveSetup EmployeeLeaveSetup
   * @return EmployeeLeaveSetupObject
   */
  public EmployeeLeaveSetupObject leaveSetup(EmployeeLeaveSetup leaveSetup) {
    this.leaveSetup = leaveSetup;
    return this;
  }

  /**
   * Get leaveSetup
   *
   * @return leaveSetup
   */
  @ApiModelProperty(value = "")
  /**
   * leaveSetup
   *
   * @return leaveSetup EmployeeLeaveSetup
   */
  public EmployeeLeaveSetup getLeaveSetup() {
    return leaveSetup;
  }

  /**
   * leaveSetup
   *
   * @param leaveSetup EmployeeLeaveSetup
   */
  public void setLeaveSetup(EmployeeLeaveSetup leaveSetup) {
    this.leaveSetup = leaveSetup;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeLeaveSetupObject employeeLeaveSetupObject = (EmployeeLeaveSetupObject) o;
    return Objects.equals(this.pagination, employeeLeaveSetupObject.pagination)
        && Objects.equals(this.problem, employeeLeaveSetupObject.problem)
        && Objects.equals(this.leaveSetup, employeeLeaveSetupObject.leaveSetup);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pagination, problem, leaveSetup);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmployeeLeaveSetupObject {\n");
    sb.append("    pagination: ").append(toIndentedString(pagination)).append("\n");
    sb.append("    problem: ").append(toIndentedString(problem)).append("\n");
    sb.append("    leaveSetup: ").append(toIndentedString(leaveSetup)).append("\n");
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
