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
import java.util.UUID;
import org.threeten.bp.LocalDate;

/** EmployeeWorkingPattern */
public class EmployeeWorkingPattern {
  StringUtil util = new StringUtil();

  @JsonProperty("payeeWorkingPatternID")
  private UUID payeeWorkingPatternID;

  @JsonProperty("effectiveFrom")
  private LocalDate effectiveFrom;
  /**
   * The Xero identifier for for Employee working pattern
   *
   * @param payeeWorkingPatternID UUID
   * @return EmployeeWorkingPattern
   */
  public EmployeeWorkingPattern payeeWorkingPatternID(UUID payeeWorkingPatternID) {
    this.payeeWorkingPatternID = payeeWorkingPatternID;
    return this;
  }

  /**
   * The Xero identifier for for Employee working pattern
   *
   * @return payeeWorkingPatternID
   */
  @ApiModelProperty(required = true, value = "The Xero identifier for for Employee working pattern")
  /**
   * The Xero identifier for for Employee working pattern
   *
   * @return payeeWorkingPatternID UUID
   */
  public UUID getPayeeWorkingPatternID() {
    return payeeWorkingPatternID;
  }

  /**
   * The Xero identifier for for Employee working pattern
   *
   * @param payeeWorkingPatternID UUID
   */
  public void setPayeeWorkingPatternID(UUID payeeWorkingPatternID) {
    this.payeeWorkingPatternID = payeeWorkingPatternID;
  }

  /**
   * The effective date of the corresponding salary and wages
   *
   * @param effectiveFrom LocalDate
   * @return EmployeeWorkingPattern
   */
  public EmployeeWorkingPattern effectiveFrom(LocalDate effectiveFrom) {
    this.effectiveFrom = effectiveFrom;
    return this;
  }

  /**
   * The effective date of the corresponding salary and wages
   *
   * @return effectiveFrom
   */
  @ApiModelProperty(
      required = true,
      value = "The effective date of the corresponding salary and wages")
  /**
   * The effective date of the corresponding salary and wages
   *
   * @return effectiveFrom LocalDate
   */
  public LocalDate getEffectiveFrom() {
    return effectiveFrom;
  }

  /**
   * The effective date of the corresponding salary and wages
   *
   * @param effectiveFrom LocalDate
   */
  public void setEffectiveFrom(LocalDate effectiveFrom) {
    this.effectiveFrom = effectiveFrom;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeWorkingPattern employeeWorkingPattern = (EmployeeWorkingPattern) o;
    return Objects.equals(this.payeeWorkingPatternID, employeeWorkingPattern.payeeWorkingPatternID)
        && Objects.equals(this.effectiveFrom, employeeWorkingPattern.effectiveFrom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(payeeWorkingPatternID, effectiveFrom);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmployeeWorkingPattern {\n");
    sb.append("    payeeWorkingPatternID: ")
        .append(toIndentedString(payeeWorkingPatternID))
        .append("\n");
    sb.append("    effectiveFrom: ").append(toIndentedString(effectiveFrom)).append("\n");
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