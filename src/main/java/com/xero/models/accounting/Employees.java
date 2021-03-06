/*
 * Xero Accounting API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.xero.models.accounting;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xero.api.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Employees */
public class Employees {
  StringUtil util = new StringUtil();

  @JsonProperty("Employees")
  private List<Employee> employees = new ArrayList<Employee>();
  /**
   * employees
   *
   * @param employees List&lt;Employee&gt;
   * @return Employees
   */
  public Employees employees(List<Employee> employees) {
    this.employees = employees;
    return this;
  }

  /**
   * employees
   *
   * @param employeesItem Employee
   * @return Employees
   */
  public Employees addEmployeesItem(Employee employeesItem) {
    if (this.employees == null) {
      this.employees = new ArrayList<Employee>();
    }
    this.employees.add(employeesItem);
    return this;
  }

  /**
   * Get employees
   *
   * @return employees
   */
  @ApiModelProperty(value = "")
  /**
   * employees
   *
   * @return employees List<Employee>
   */
  public List<Employee> getEmployees() {
    return employees;
  }

  /**
   * employees
   *
   * @param employees List&lt;Employee&gt;
   */
  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employees employees = (Employees) o;
    return Objects.equals(this.employees, employees.employees);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employees);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Employees {\n");
    sb.append("    employees: ").append(toIndentedString(employees)).append("\n");
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
