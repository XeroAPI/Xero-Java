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

/** WorkingWeek */
public class WorkingWeek {
  StringUtil util = new StringUtil();

  @JsonProperty("monday")
  private Double monday;

  @JsonProperty("tuesday")
  private Double tuesday;

  @JsonProperty("wednesday")
  private Double wednesday;

  @JsonProperty("thursday")
  private Double thursday;

  @JsonProperty("friday")
  private Double friday;

  @JsonProperty("saturday")
  private Double saturday;

  @JsonProperty("sunday")
  private Double sunday;
  /**
   * The number of hours worked on a Monday
   *
   * @param monday Double
   * @return WorkingWeek
   */
  public WorkingWeek monday(Double monday) {
    this.monday = monday;
    return this;
  }

  /**
   * The number of hours worked on a Monday
   *
   * @return monday
   */
  @ApiModelProperty(required = true, value = "The number of hours worked on a Monday")
  /**
   * The number of hours worked on a Monday
   *
   * @return monday Double
   */
  public Double getMonday() {
    return monday;
  }

  /**
   * The number of hours worked on a Monday
   *
   * @param monday Double
   */
  public void setMonday(Double monday) {
    this.monday = monday;
  }

  /**
   * The number of hours worked on a Tuesday
   *
   * @param tuesday Double
   * @return WorkingWeek
   */
  public WorkingWeek tuesday(Double tuesday) {
    this.tuesday = tuesday;
    return this;
  }

  /**
   * The number of hours worked on a Tuesday
   *
   * @return tuesday
   */
  @ApiModelProperty(required = true, value = "The number of hours worked on a Tuesday")
  /**
   * The number of hours worked on a Tuesday
   *
   * @return tuesday Double
   */
  public Double getTuesday() {
    return tuesday;
  }

  /**
   * The number of hours worked on a Tuesday
   *
   * @param tuesday Double
   */
  public void setTuesday(Double tuesday) {
    this.tuesday = tuesday;
  }

  /**
   * The number of hours worked on a Wednesday
   *
   * @param wednesday Double
   * @return WorkingWeek
   */
  public WorkingWeek wednesday(Double wednesday) {
    this.wednesday = wednesday;
    return this;
  }

  /**
   * The number of hours worked on a Wednesday
   *
   * @return wednesday
   */
  @ApiModelProperty(required = true, value = "The number of hours worked on a Wednesday")
  /**
   * The number of hours worked on a Wednesday
   *
   * @return wednesday Double
   */
  public Double getWednesday() {
    return wednesday;
  }

  /**
   * The number of hours worked on a Wednesday
   *
   * @param wednesday Double
   */
  public void setWednesday(Double wednesday) {
    this.wednesday = wednesday;
  }

  /**
   * The number of hours worked on a Thursday
   *
   * @param thursday Double
   * @return WorkingWeek
   */
  public WorkingWeek thursday(Double thursday) {
    this.thursday = thursday;
    return this;
  }

  /**
   * The number of hours worked on a Thursday
   *
   * @return thursday
   */
  @ApiModelProperty(required = true, value = "The number of hours worked on a Thursday")
  /**
   * The number of hours worked on a Thursday
   *
   * @return thursday Double
   */
  public Double getThursday() {
    return thursday;
  }

  /**
   * The number of hours worked on a Thursday
   *
   * @param thursday Double
   */
  public void setThursday(Double thursday) {
    this.thursday = thursday;
  }

  /**
   * The number of hours worked on a Friday
   *
   * @param friday Double
   * @return WorkingWeek
   */
  public WorkingWeek friday(Double friday) {
    this.friday = friday;
    return this;
  }

  /**
   * The number of hours worked on a Friday
   *
   * @return friday
   */
  @ApiModelProperty(required = true, value = "The number of hours worked on a Friday")
  /**
   * The number of hours worked on a Friday
   *
   * @return friday Double
   */
  public Double getFriday() {
    return friday;
  }

  /**
   * The number of hours worked on a Friday
   *
   * @param friday Double
   */
  public void setFriday(Double friday) {
    this.friday = friday;
  }

  /**
   * The number of hours worked on a Saturday
   *
   * @param saturday Double
   * @return WorkingWeek
   */
  public WorkingWeek saturday(Double saturday) {
    this.saturday = saturday;
    return this;
  }

  /**
   * The number of hours worked on a Saturday
   *
   * @return saturday
   */
  @ApiModelProperty(required = true, value = "The number of hours worked on a Saturday")
  /**
   * The number of hours worked on a Saturday
   *
   * @return saturday Double
   */
  public Double getSaturday() {
    return saturday;
  }

  /**
   * The number of hours worked on a Saturday
   *
   * @param saturday Double
   */
  public void setSaturday(Double saturday) {
    this.saturday = saturday;
  }

  /**
   * The number of hours worked on a Sunday
   *
   * @param sunday Double
   * @return WorkingWeek
   */
  public WorkingWeek sunday(Double sunday) {
    this.sunday = sunday;
    return this;
  }

  /**
   * The number of hours worked on a Sunday
   *
   * @return sunday
   */
  @ApiModelProperty(required = true, value = "The number of hours worked on a Sunday")
  /**
   * The number of hours worked on a Sunday
   *
   * @return sunday Double
   */
  public Double getSunday() {
    return sunday;
  }

  /**
   * The number of hours worked on a Sunday
   *
   * @param sunday Double
   */
  public void setSunday(Double sunday) {
    this.sunday = sunday;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkingWeek workingWeek = (WorkingWeek) o;
    return Objects.equals(this.monday, workingWeek.monday)
        && Objects.equals(this.tuesday, workingWeek.tuesday)
        && Objects.equals(this.wednesday, workingWeek.wednesday)
        && Objects.equals(this.thursday, workingWeek.thursday)
        && Objects.equals(this.friday, workingWeek.friday)
        && Objects.equals(this.saturday, workingWeek.saturday)
        && Objects.equals(this.sunday, workingWeek.sunday);
  }

  @Override
  public int hashCode() {
    return Objects.hash(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkingWeek {\n");
    sb.append("    monday: ").append(toIndentedString(monday)).append("\n");
    sb.append("    tuesday: ").append(toIndentedString(tuesday)).append("\n");
    sb.append("    wednesday: ").append(toIndentedString(wednesday)).append("\n");
    sb.append("    thursday: ").append(toIndentedString(thursday)).append("\n");
    sb.append("    friday: ").append(toIndentedString(friday)).append("\n");
    sb.append("    saturday: ").append(toIndentedString(saturday)).append("\n");
    sb.append("    sunday: ").append(toIndentedString(sunday)).append("\n");
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