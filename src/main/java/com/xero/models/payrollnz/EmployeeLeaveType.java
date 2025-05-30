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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xero.api.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import java.util.UUID;
import org.threeten.bp.LocalDate;

/** EmployeeLeaveType */
public class EmployeeLeaveType {
  StringUtil util = new StringUtil();

  @JsonProperty("leaveTypeID")
  private UUID leaveTypeID;
  /** The schedule of accrual */
  public enum ScheduleOfAccrualEnum {
    /** ANNUALLYAFTER6MONTHS */
    ANNUALLYAFTER6MONTHS("AnnuallyAfter6Months"),

    /** ONANNIVERSARYDATE */
    ONANNIVERSARYDATE("OnAnniversaryDate"),

    /** PERCENTAGEOFGROSSEARNINGS */
    PERCENTAGEOFGROSSEARNINGS("PercentageOfGrossEarnings"),

    /** NOACCRUALS */
    NOACCRUALS("NoAccruals");

    private String value;

    ScheduleOfAccrualEnum(String value) {
      this.value = value;
    }

    /**
     * getValue
     *
     * @return String value
     */
    @JsonValue
    public String getValue() {
      return value;
    }

    /**
     * toString
     *
     * @return String value
     */
    @Override
    public String toString() {
      return String.valueOf(value);
    }

    /**
     * fromValue
     *
     * @param value String
     */
    @JsonCreator
    public static ScheduleOfAccrualEnum fromValue(String value) {
      for (ScheduleOfAccrualEnum b : ScheduleOfAccrualEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("scheduleOfAccrual")
  private ScheduleOfAccrualEnum scheduleOfAccrual;

  @JsonProperty("hoursAccruedAnnually")
  private Double hoursAccruedAnnually;

  @JsonProperty("UnitsAccruedAnnually")
  private Double unitsAccruedAnnually;

  @JsonProperty("typeOfUnitsToAccrue")
  private String typeOfUnitsToAccrue;

  @JsonProperty("maximumToAccrue")
  private Double maximumToAccrue;

  @JsonProperty("openingBalance")
  private Double openingBalance;

  @JsonProperty("openingBalanceTypeOfUnits")
  private String openingBalanceTypeOfUnits;

  @JsonProperty("rateAccruedHourly")
  private Double rateAccruedHourly;

  @JsonProperty("percentageOfGrossEarnings")
  private Double percentageOfGrossEarnings;

  @JsonProperty("includeHolidayPayEveryPay")
  private Boolean includeHolidayPayEveryPay;

  @JsonProperty("showAnnualLeaveInAdvance")
  private Boolean showAnnualLeaveInAdvance;

  @JsonProperty("annualLeaveTotalAmountPaid")
  private Double annualLeaveTotalAmountPaid;

  @JsonProperty("scheduleOfAccrualDate")
  private LocalDate scheduleOfAccrualDate;
  /**
   * The Xero identifier for leave type
   *
   * @param leaveTypeID UUID
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType leaveTypeID(UUID leaveTypeID) {
    this.leaveTypeID = leaveTypeID;
    return this;
  }

  /**
   * The Xero identifier for leave type
   *
   * @return leaveTypeID
   */
  @ApiModelProperty(value = "The Xero identifier for leave type")
  /**
   * The Xero identifier for leave type
   *
   * @return leaveTypeID UUID
   */
  public UUID getLeaveTypeID() {
    return leaveTypeID;
  }

  /**
   * The Xero identifier for leave type
   *
   * @param leaveTypeID UUID
   */
  public void setLeaveTypeID(UUID leaveTypeID) {
    this.leaveTypeID = leaveTypeID;
  }

  /**
   * The schedule of accrual
   *
   * @param scheduleOfAccrual ScheduleOfAccrualEnum
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType scheduleOfAccrual(ScheduleOfAccrualEnum scheduleOfAccrual) {
    this.scheduleOfAccrual = scheduleOfAccrual;
    return this;
  }

  /**
   * The schedule of accrual
   *
   * @return scheduleOfAccrual
   */
  @ApiModelProperty(value = "The schedule of accrual")
  /**
   * The schedule of accrual
   *
   * @return scheduleOfAccrual ScheduleOfAccrualEnum
   */
  public ScheduleOfAccrualEnum getScheduleOfAccrual() {
    return scheduleOfAccrual;
  }

  /**
   * The schedule of accrual
   *
   * @param scheduleOfAccrual ScheduleOfAccrualEnum
   */
  public void setScheduleOfAccrual(ScheduleOfAccrualEnum scheduleOfAccrual) {
    this.scheduleOfAccrual = scheduleOfAccrual;
  }

  /**
   * Deprecated use UnitsAccruedAnnually
   *
   * @param hoursAccruedAnnually Double
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType hoursAccruedAnnually(Double hoursAccruedAnnually) {
    this.hoursAccruedAnnually = hoursAccruedAnnually;
    return this;
  }

  /**
   * Deprecated use UnitsAccruedAnnually
   *
   * @return hoursAccruedAnnually
   */
  @ApiModelProperty(value = "Deprecated use UnitsAccruedAnnually")
  /**
   * Deprecated use UnitsAccruedAnnually
   *
   * @return hoursAccruedAnnually Double
   */
  public Double getHoursAccruedAnnually() {
    return hoursAccruedAnnually;
  }

  /**
   * Deprecated use UnitsAccruedAnnually
   *
   * @param hoursAccruedAnnually Double
   */
  public void setHoursAccruedAnnually(Double hoursAccruedAnnually) {
    this.hoursAccruedAnnually = hoursAccruedAnnually;
  }

  /**
   * The number of units accrued for the leave annually. This is 0 when the ScheduleOfAccrual chosen
   * is \&quot;NoAccruals\&quot;
   *
   * @param unitsAccruedAnnually Double
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType unitsAccruedAnnually(Double unitsAccruedAnnually) {
    this.unitsAccruedAnnually = unitsAccruedAnnually;
    return this;
  }

  /**
   * The number of units accrued for the leave annually. This is 0 when the ScheduleOfAccrual chosen
   * is \&quot;NoAccruals\&quot;
   *
   * @return unitsAccruedAnnually
   */
  @ApiModelProperty(
      value =
          "The number of units accrued for the leave annually. This is 0 when the"
              + " ScheduleOfAccrual chosen is \"NoAccruals\"")
  /**
   * The number of units accrued for the leave annually. This is 0 when the ScheduleOfAccrual chosen
   * is \&quot;NoAccruals\&quot;
   *
   * @return unitsAccruedAnnually Double
   */
  public Double getUnitsAccruedAnnually() {
    return unitsAccruedAnnually;
  }

  /**
   * The number of units accrued for the leave annually. This is 0 when the ScheduleOfAccrual chosen
   * is \&quot;NoAccruals\&quot;
   *
   * @param unitsAccruedAnnually Double
   */
  public void setUnitsAccruedAnnually(Double unitsAccruedAnnually) {
    this.unitsAccruedAnnually = unitsAccruedAnnually;
  }

  /**
   * The type of units accrued for the leave annually
   *
   * @param typeOfUnitsToAccrue String
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType typeOfUnitsToAccrue(String typeOfUnitsToAccrue) {
    this.typeOfUnitsToAccrue = typeOfUnitsToAccrue;
    return this;
  }

  /**
   * The type of units accrued for the leave annually
   *
   * @return typeOfUnitsToAccrue
   */
  @ApiModelProperty(value = "The type of units accrued for the leave annually")
  /**
   * The type of units accrued for the leave annually
   *
   * @return typeOfUnitsToAccrue String
   */
  public String getTypeOfUnitsToAccrue() {
    return typeOfUnitsToAccrue;
  }

  /**
   * The type of units accrued for the leave annually
   *
   * @param typeOfUnitsToAccrue String
   */
  public void setTypeOfUnitsToAccrue(String typeOfUnitsToAccrue) {
    this.typeOfUnitsToAccrue = typeOfUnitsToAccrue;
  }

  /**
   * The maximum number of units that can be accrued for the leave
   *
   * @param maximumToAccrue Double
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType maximumToAccrue(Double maximumToAccrue) {
    this.maximumToAccrue = maximumToAccrue;
    return this;
  }

  /**
   * The maximum number of units that can be accrued for the leave
   *
   * @return maximumToAccrue
   */
  @ApiModelProperty(value = "The maximum number of units that can be accrued for the leave")
  /**
   * The maximum number of units that can be accrued for the leave
   *
   * @return maximumToAccrue Double
   */
  public Double getMaximumToAccrue() {
    return maximumToAccrue;
  }

  /**
   * The maximum number of units that can be accrued for the leave
   *
   * @param maximumToAccrue Double
   */
  public void setMaximumToAccrue(Double maximumToAccrue) {
    this.maximumToAccrue = maximumToAccrue;
  }

  /**
   * The initial number of units assigned when the leave was added to the employee
   *
   * @param openingBalance Double
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType openingBalance(Double openingBalance) {
    this.openingBalance = openingBalance;
    return this;
  }

  /**
   * The initial number of units assigned when the leave was added to the employee
   *
   * @return openingBalance
   */
  @ApiModelProperty(
      value = "The initial number of units assigned when the leave was added to the employee")
  /**
   * The initial number of units assigned when the leave was added to the employee
   *
   * @return openingBalance Double
   */
  public Double getOpeningBalance() {
    return openingBalance;
  }

  /**
   * The initial number of units assigned when the leave was added to the employee
   *
   * @param openingBalance Double
   */
  public void setOpeningBalance(Double openingBalance) {
    this.openingBalance = openingBalance;
  }

  /**
   * The type of units for the opening balance
   *
   * @param openingBalanceTypeOfUnits String
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType openingBalanceTypeOfUnits(String openingBalanceTypeOfUnits) {
    this.openingBalanceTypeOfUnits = openingBalanceTypeOfUnits;
    return this;
  }

  /**
   * The type of units for the opening balance
   *
   * @return openingBalanceTypeOfUnits
   */
  @ApiModelProperty(value = "The type of units for the opening balance")
  /**
   * The type of units for the opening balance
   *
   * @return openingBalanceTypeOfUnits String
   */
  public String getOpeningBalanceTypeOfUnits() {
    return openingBalanceTypeOfUnits;
  }

  /**
   * The type of units for the opening balance
   *
   * @param openingBalanceTypeOfUnits String
   */
  public void setOpeningBalanceTypeOfUnits(String openingBalanceTypeOfUnits) {
    this.openingBalanceTypeOfUnits = openingBalanceTypeOfUnits;
  }

  /**
   * The number of hours added to the leave balance for every hour worked by the employee. This is
   * normally 0, unless the scheduleOfAccrual chosen is \&quot;OnHourWorked\&quot;
   *
   * @param rateAccruedHourly Double
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType rateAccruedHourly(Double rateAccruedHourly) {
    this.rateAccruedHourly = rateAccruedHourly;
    return this;
  }

  /**
   * The number of hours added to the leave balance for every hour worked by the employee. This is
   * normally 0, unless the scheduleOfAccrual chosen is \&quot;OnHourWorked\&quot;
   *
   * @return rateAccruedHourly
   */
  @ApiModelProperty(
      value =
          "The number of hours added to the leave balance for every hour worked by the employee."
              + " This is normally 0, unless the scheduleOfAccrual chosen is \"OnHourWorked\"")
  /**
   * The number of hours added to the leave balance for every hour worked by the employee. This is
   * normally 0, unless the scheduleOfAccrual chosen is \&quot;OnHourWorked\&quot;
   *
   * @return rateAccruedHourly Double
   */
  public Double getRateAccruedHourly() {
    return rateAccruedHourly;
  }

  /**
   * The number of hours added to the leave balance for every hour worked by the employee. This is
   * normally 0, unless the scheduleOfAccrual chosen is \&quot;OnHourWorked\&quot;
   *
   * @param rateAccruedHourly Double
   */
  public void setRateAccruedHourly(Double rateAccruedHourly) {
    this.rateAccruedHourly = rateAccruedHourly;
  }

  /**
   * Specific for scheduleOfAccrual having percentage of gross earnings. Identifies how much
   * percentage of gross earnings is accrued per pay period.
   *
   * @param percentageOfGrossEarnings Double
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType percentageOfGrossEarnings(Double percentageOfGrossEarnings) {
    this.percentageOfGrossEarnings = percentageOfGrossEarnings;
    return this;
  }

  /**
   * Specific for scheduleOfAccrual having percentage of gross earnings. Identifies how much
   * percentage of gross earnings is accrued per pay period.
   *
   * @return percentageOfGrossEarnings
   */
  @ApiModelProperty(
      value =
          "Specific for scheduleOfAccrual having percentage of gross earnings. Identifies how much"
              + " percentage of gross earnings is accrued per pay period.")
  /**
   * Specific for scheduleOfAccrual having percentage of gross earnings. Identifies how much
   * percentage of gross earnings is accrued per pay period.
   *
   * @return percentageOfGrossEarnings Double
   */
  public Double getPercentageOfGrossEarnings() {
    return percentageOfGrossEarnings;
  }

  /**
   * Specific for scheduleOfAccrual having percentage of gross earnings. Identifies how much
   * percentage of gross earnings is accrued per pay period.
   *
   * @param percentageOfGrossEarnings Double
   */
  public void setPercentageOfGrossEarnings(Double percentageOfGrossEarnings) {
    this.percentageOfGrossEarnings = percentageOfGrossEarnings;
  }

  /**
   * Specific to Holiday pay. Flag determining if pay for leave type is added on each pay run.
   *
   * @param includeHolidayPayEveryPay Boolean
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType includeHolidayPayEveryPay(Boolean includeHolidayPayEveryPay) {
    this.includeHolidayPayEveryPay = includeHolidayPayEveryPay;
    return this;
  }

  /**
   * Specific to Holiday pay. Flag determining if pay for leave type is added on each pay run.
   *
   * @return includeHolidayPayEveryPay
   */
  @ApiModelProperty(
      value =
          "Specific to Holiday pay. Flag determining if pay for leave type is added on each pay"
              + " run.")
  /**
   * Specific to Holiday pay. Flag determining if pay for leave type is added on each pay run.
   *
   * @return includeHolidayPayEveryPay Boolean
   */
  public Boolean getIncludeHolidayPayEveryPay() {
    return includeHolidayPayEveryPay;
  }

  /**
   * Specific to Holiday pay. Flag determining if pay for leave type is added on each pay run.
   *
   * @param includeHolidayPayEveryPay Boolean
   */
  public void setIncludeHolidayPayEveryPay(Boolean includeHolidayPayEveryPay) {
    this.includeHolidayPayEveryPay = includeHolidayPayEveryPay;
  }

  /**
   * Specific to Annual Leave. Flag to include leave available to take in advance in the balance in
   * the payslip
   *
   * @param showAnnualLeaveInAdvance Boolean
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType showAnnualLeaveInAdvance(Boolean showAnnualLeaveInAdvance) {
    this.showAnnualLeaveInAdvance = showAnnualLeaveInAdvance;
    return this;
  }

  /**
   * Specific to Annual Leave. Flag to include leave available to take in advance in the balance in
   * the payslip
   *
   * @return showAnnualLeaveInAdvance
   */
  @ApiModelProperty(
      value =
          "Specific to Annual Leave. Flag to include leave available to take in advance in the"
              + " balance in the payslip")
  /**
   * Specific to Annual Leave. Flag to include leave available to take in advance in the balance in
   * the payslip
   *
   * @return showAnnualLeaveInAdvance Boolean
   */
  public Boolean getShowAnnualLeaveInAdvance() {
    return showAnnualLeaveInAdvance;
  }

  /**
   * Specific to Annual Leave. Flag to include leave available to take in advance in the balance in
   * the payslip
   *
   * @param showAnnualLeaveInAdvance Boolean
   */
  public void setShowAnnualLeaveInAdvance(Boolean showAnnualLeaveInAdvance) {
    this.showAnnualLeaveInAdvance = showAnnualLeaveInAdvance;
  }

  /**
   * Specific to Annual Leave. Annual leave balance in dollars
   *
   * @param annualLeaveTotalAmountPaid Double
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType annualLeaveTotalAmountPaid(Double annualLeaveTotalAmountPaid) {
    this.annualLeaveTotalAmountPaid = annualLeaveTotalAmountPaid;
    return this;
  }

  /**
   * Specific to Annual Leave. Annual leave balance in dollars
   *
   * @return annualLeaveTotalAmountPaid
   */
  @ApiModelProperty(value = "Specific to Annual Leave. Annual leave balance in dollars")
  /**
   * Specific to Annual Leave. Annual leave balance in dollars
   *
   * @return annualLeaveTotalAmountPaid Double
   */
  public Double getAnnualLeaveTotalAmountPaid() {
    return annualLeaveTotalAmountPaid;
  }

  /**
   * Specific to Annual Leave. Annual leave balance in dollars
   *
   * @param annualLeaveTotalAmountPaid Double
   */
  public void setAnnualLeaveTotalAmountPaid(Double annualLeaveTotalAmountPaid) {
    this.annualLeaveTotalAmountPaid = annualLeaveTotalAmountPaid;
  }

  /**
   * The date when an employee becomes entitled to their accrual.
   *
   * @param scheduleOfAccrualDate LocalDate
   * @return EmployeeLeaveType
   */
  public EmployeeLeaveType scheduleOfAccrualDate(LocalDate scheduleOfAccrualDate) {
    this.scheduleOfAccrualDate = scheduleOfAccrualDate;
    return this;
  }

  /**
   * The date when an employee becomes entitled to their accrual.
   *
   * @return scheduleOfAccrualDate
   */
  @ApiModelProperty(
      example = "Sun Jan 19 00:00:00 UTC 2020",
      value = "The date when an employee becomes entitled to their accrual.")
  /**
   * The date when an employee becomes entitled to their accrual.
   *
   * @return scheduleOfAccrualDate LocalDate
   */
  public LocalDate getScheduleOfAccrualDate() {
    return scheduleOfAccrualDate;
  }

  /**
   * The date when an employee becomes entitled to their accrual.
   *
   * @param scheduleOfAccrualDate LocalDate
   */
  public void setScheduleOfAccrualDate(LocalDate scheduleOfAccrualDate) {
    this.scheduleOfAccrualDate = scheduleOfAccrualDate;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeLeaveType employeeLeaveType = (EmployeeLeaveType) o;
    return Objects.equals(this.leaveTypeID, employeeLeaveType.leaveTypeID)
        && Objects.equals(this.scheduleOfAccrual, employeeLeaveType.scheduleOfAccrual)
        && Objects.equals(this.hoursAccruedAnnually, employeeLeaveType.hoursAccruedAnnually)
        && Objects.equals(this.unitsAccruedAnnually, employeeLeaveType.unitsAccruedAnnually)
        && Objects.equals(this.typeOfUnitsToAccrue, employeeLeaveType.typeOfUnitsToAccrue)
        && Objects.equals(this.maximumToAccrue, employeeLeaveType.maximumToAccrue)
        && Objects.equals(this.openingBalance, employeeLeaveType.openingBalance)
        && Objects.equals(
            this.openingBalanceTypeOfUnits, employeeLeaveType.openingBalanceTypeOfUnits)
        && Objects.equals(this.rateAccruedHourly, employeeLeaveType.rateAccruedHourly)
        && Objects.equals(
            this.percentageOfGrossEarnings, employeeLeaveType.percentageOfGrossEarnings)
        && Objects.equals(
            this.includeHolidayPayEveryPay, employeeLeaveType.includeHolidayPayEveryPay)
        && Objects.equals(this.showAnnualLeaveInAdvance, employeeLeaveType.showAnnualLeaveInAdvance)
        && Objects.equals(
            this.annualLeaveTotalAmountPaid, employeeLeaveType.annualLeaveTotalAmountPaid)
        && Objects.equals(this.scheduleOfAccrualDate, employeeLeaveType.scheduleOfAccrualDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        leaveTypeID,
        scheduleOfAccrual,
        hoursAccruedAnnually,
        unitsAccruedAnnually,
        typeOfUnitsToAccrue,
        maximumToAccrue,
        openingBalance,
        openingBalanceTypeOfUnits,
        rateAccruedHourly,
        percentageOfGrossEarnings,
        includeHolidayPayEveryPay,
        showAnnualLeaveInAdvance,
        annualLeaveTotalAmountPaid,
        scheduleOfAccrualDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EmployeeLeaveType {\n");
    sb.append("    leaveTypeID: ").append(toIndentedString(leaveTypeID)).append("\n");
    sb.append("    scheduleOfAccrual: ").append(toIndentedString(scheduleOfAccrual)).append("\n");
    sb.append("    hoursAccruedAnnually: ")
        .append(toIndentedString(hoursAccruedAnnually))
        .append("\n");
    sb.append("    unitsAccruedAnnually: ")
        .append(toIndentedString(unitsAccruedAnnually))
        .append("\n");
    sb.append("    typeOfUnitsToAccrue: ")
        .append(toIndentedString(typeOfUnitsToAccrue))
        .append("\n");
    sb.append("    maximumToAccrue: ").append(toIndentedString(maximumToAccrue)).append("\n");
    sb.append("    openingBalance: ").append(toIndentedString(openingBalance)).append("\n");
    sb.append("    openingBalanceTypeOfUnits: ")
        .append(toIndentedString(openingBalanceTypeOfUnits))
        .append("\n");
    sb.append("    rateAccruedHourly: ").append(toIndentedString(rateAccruedHourly)).append("\n");
    sb.append("    percentageOfGrossEarnings: ")
        .append(toIndentedString(percentageOfGrossEarnings))
        .append("\n");
    sb.append("    includeHolidayPayEveryPay: ")
        .append(toIndentedString(includeHolidayPayEveryPay))
        .append("\n");
    sb.append("    showAnnualLeaveInAdvance: ")
        .append(toIndentedString(showAnnualLeaveInAdvance))
        .append("\n");
    sb.append("    annualLeaveTotalAmountPaid: ")
        .append(toIndentedString(annualLeaveTotalAmountPaid))
        .append("\n");
    sb.append("    scheduleOfAccrualDate: ")
        .append(toIndentedString(scheduleOfAccrualDate))
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
