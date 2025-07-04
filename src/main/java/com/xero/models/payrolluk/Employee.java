/*
 * Xero Payroll UK
 * This is the Xero Payroll API for orgs in the UK region.
 *
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.xero.models.payrolluk;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xero.api.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

/** Employee */
public class Employee {
  StringUtil util = new StringUtil();

  @JsonProperty("employeeID")
  private UUID employeeID;

  @JsonProperty("title")
  private String title;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("dateOfBirth")
  private LocalDate dateOfBirth;

  @JsonProperty("address")
  private Address address;

  @JsonProperty("email")
  private String email;
  /** The employee’s gender */
  public enum GenderEnum {
    /** M */
    M("M"),

    /** F */
    F("F");

    private String value;

    GenderEnum(String value) {
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
    public static GenderEnum fromValue(String value) {
      for (GenderEnum b : GenderEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("gender")
  private GenderEnum gender;

  @JsonProperty("phoneNumber")
  private String phoneNumber;

  @JsonProperty("startDate")
  private LocalDate startDate;

  @JsonProperty("endDate")
  private LocalDate endDate;

  @JsonProperty("payrollCalendarID")
  private UUID payrollCalendarID;

  @JsonProperty("updatedDateUTC")
  private LocalDateTime updatedDateUTC;

  @JsonProperty("createdDateUTC")
  private LocalDateTime createdDateUTC;

  @JsonProperty("niCategory")
  private NICategoryLetter niCategory;

  @JsonProperty("niCategories")
  private List<NICategory> niCategories = new ArrayList<NICategory>();

  @JsonProperty("nationalInsuranceNumber")
  private String nationalInsuranceNumber;

  @JsonProperty("isOffPayrollWorker")
  private Boolean isOffPayrollWorker;
  /**
   * Xero unique identifier for the employee
   *
   * @param employeeID UUID
   * @return Employee
   */
  public Employee employeeID(UUID employeeID) {
    this.employeeID = employeeID;
    return this;
  }

  /**
   * Xero unique identifier for the employee
   *
   * @return employeeID
   */
  @ApiModelProperty(
      example = "d90457c4-f1be-4f2e-b4e3-f766390a7e30",
      value = "Xero unique identifier for the employee")
  /**
   * Xero unique identifier for the employee
   *
   * @return employeeID UUID
   */
  public UUID getEmployeeID() {
    return employeeID;
  }

  /**
   * Xero unique identifier for the employee
   *
   * @param employeeID UUID
   */
  public void setEmployeeID(UUID employeeID) {
    this.employeeID = employeeID;
  }

  /**
   * Title of the employee
   *
   * @param title String
   * @return Employee
   */
  public Employee title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Title of the employee
   *
   * @return title
   */
  @ApiModelProperty(example = "Mrs", required = true, value = "Title of the employee")
  /**
   * Title of the employee
   *
   * @return title String
   */
  public String getTitle() {
    return title;
  }

  /**
   * Title of the employee
   *
   * @param title String
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * First name of employee
   *
   * @param firstName String
   * @return Employee
   */
  public Employee firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * First name of employee
   *
   * @return firstName
   */
  @ApiModelProperty(example = "Karen", required = true, value = "First name of employee")
  /**
   * First name of employee
   *
   * @return firstName String
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * First name of employee
   *
   * @param firstName String
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Last name of employee
   *
   * @param lastName String
   * @return Employee
   */
  public Employee lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Last name of employee
   *
   * @return lastName
   */
  @ApiModelProperty(example = "Jones", required = true, value = "Last name of employee")
  /**
   * Last name of employee
   *
   * @return lastName String
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Last name of employee
   *
   * @param lastName String
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Date of birth of the employee (YYYY-MM-DD)
   *
   * @param dateOfBirth LocalDate
   * @return Employee
   */
  public Employee dateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  /**
   * Date of birth of the employee (YYYY-MM-DD)
   *
   * @return dateOfBirth
   */
  @ApiModelProperty(
      example = "Wed Jan 02 00:00:00 UTC 2019",
      required = true,
      value = "Date of birth of the employee (YYYY-MM-DD)")
  /**
   * Date of birth of the employee (YYYY-MM-DD)
   *
   * @return dateOfBirth LocalDate
   */
  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  /**
   * Date of birth of the employee (YYYY-MM-DD)
   *
   * @param dateOfBirth LocalDate
   */
  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * address
   *
   * @param address Address
   * @return Employee
   */
  public Employee address(Address address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   *
   * @return address
   */
  @ApiModelProperty(required = true, value = "")
  /**
   * address
   *
   * @return address Address
   */
  public Address getAddress() {
    return address;
  }

  /**
   * address
   *
   * @param address Address
   */
  public void setAddress(Address address) {
    this.address = address;
  }

  /**
   * The email address for the employee
   *
   * @param email String
   * @return Employee
   */
  public Employee email(String email) {
    this.email = email;
    return this;
  }

  /**
   * The email address for the employee
   *
   * @return email
   */
  @ApiModelProperty(example = "developer@me.com", value = "The email address for the employee")
  /**
   * The email address for the employee
   *
   * @return email String
   */
  public String getEmail() {
    return email;
  }

  /**
   * The email address for the employee
   *
   * @param email String
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * The employee’s gender
   *
   * @param gender GenderEnum
   * @return Employee
   */
  public Employee gender(GenderEnum gender) {
    this.gender = gender;
    return this;
  }

  /**
   * The employee’s gender
   *
   * @return gender
   */
  @ApiModelProperty(example = "F", required = true, value = "The employee’s gender")
  /**
   * The employee’s gender
   *
   * @return gender GenderEnum
   */
  public GenderEnum getGender() {
    return gender;
  }

  /**
   * The employee’s gender
   *
   * @param gender GenderEnum
   */
  public void setGender(GenderEnum gender) {
    this.gender = gender;
  }

  /**
   * Employee phone number
   *
   * @param phoneNumber String
   * @return Employee
   */
  public Employee phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Employee phone number
   *
   * @return phoneNumber
   */
  @ApiModelProperty(example = "415-555-1212", value = "Employee phone number")
  /**
   * Employee phone number
   *
   * @return phoneNumber String
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Employee phone number
   *
   * @param phoneNumber String
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Employment start date of the employee at the time it was requested
   *
   * @param startDate LocalDate
   * @return Employee
   */
  public Employee startDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Employment start date of the employee at the time it was requested
   *
   * @return startDate
   */
  @ApiModelProperty(
      example = "Sun Jan 19 00:00:00 UTC 2020",
      value = "Employment start date of the employee at the time it was requested")
  /**
   * Employment start date of the employee at the time it was requested
   *
   * @return startDate LocalDate
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Employment start date of the employee at the time it was requested
   *
   * @param startDate LocalDate
   */
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  /**
   * Employment end date of the employee at the time it was requested
   *
   * @param endDate LocalDate
   * @return Employee
   */
  public Employee endDate(LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * Employment end date of the employee at the time it was requested
   *
   * @return endDate
   */
  @ApiModelProperty(
      example = "Sun Jan 19 00:00:00 UTC 2020",
      value = "Employment end date of the employee at the time it was requested")
  /**
   * Employment end date of the employee at the time it was requested
   *
   * @return endDate LocalDate
   */
  public LocalDate getEndDate() {
    return endDate;
  }

  /**
   * Employment end date of the employee at the time it was requested
   *
   * @param endDate LocalDate
   */
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  /**
   * Xero unique identifier for the payroll calendar of the employee
   *
   * @param payrollCalendarID UUID
   * @return Employee
   */
  public Employee payrollCalendarID(UUID payrollCalendarID) {
    this.payrollCalendarID = payrollCalendarID;
    return this;
  }

  /**
   * Xero unique identifier for the payroll calendar of the employee
   *
   * @return payrollCalendarID
   */
  @ApiModelProperty(value = "Xero unique identifier for the payroll calendar of the employee")
  /**
   * Xero unique identifier for the payroll calendar of the employee
   *
   * @return payrollCalendarID UUID
   */
  public UUID getPayrollCalendarID() {
    return payrollCalendarID;
  }

  /**
   * Xero unique identifier for the payroll calendar of the employee
   *
   * @param payrollCalendarID UUID
   */
  public void setPayrollCalendarID(UUID payrollCalendarID) {
    this.payrollCalendarID = payrollCalendarID;
  }

  /**
   * UTC timestamp of last update to the employee
   *
   * @param updatedDateUTC LocalDateTime
   * @return Employee
   */
  public Employee updatedDateUTC(LocalDateTime updatedDateUTC) {
    this.updatedDateUTC = updatedDateUTC;
    return this;
  }

  /**
   * UTC timestamp of last update to the employee
   *
   * @return updatedDateUTC
   */
  @ApiModelProperty(value = "UTC timestamp of last update to the employee")
  /**
   * UTC timestamp of last update to the employee
   *
   * @return updatedDateUTC LocalDateTime
   */
  public LocalDateTime getUpdatedDateUTC() {
    return updatedDateUTC;
  }

  /**
   * UTC timestamp of last update to the employee
   *
   * @param updatedDateUTC LocalDateTime
   */
  public void setUpdatedDateUTC(LocalDateTime updatedDateUTC) {
    this.updatedDateUTC = updatedDateUTC;
  }

  /**
   * UTC timestamp when the employee was created in Xero
   *
   * @param createdDateUTC LocalDateTime
   * @return Employee
   */
  public Employee createdDateUTC(LocalDateTime createdDateUTC) {
    this.createdDateUTC = createdDateUTC;
    return this;
  }

  /**
   * UTC timestamp when the employee was created in Xero
   *
   * @return createdDateUTC
   */
  @ApiModelProperty(value = "UTC timestamp when the employee was created in Xero")
  /**
   * UTC timestamp when the employee was created in Xero
   *
   * @return createdDateUTC LocalDateTime
   */
  public LocalDateTime getCreatedDateUTC() {
    return createdDateUTC;
  }

  /**
   * UTC timestamp when the employee was created in Xero
   *
   * @param createdDateUTC LocalDateTime
   */
  public void setCreatedDateUTC(LocalDateTime createdDateUTC) {
    this.createdDateUTC = createdDateUTC;
  }

  /**
   * niCategory
   *
   * @param niCategory NICategoryLetter
   * @return Employee
   */
  public Employee niCategory(NICategoryLetter niCategory) {
    this.niCategory = niCategory;
    return this;
  }

  /**
   * Get niCategory
   *
   * @return niCategory
   */
  @ApiModelProperty(value = "")
  /**
   * niCategory
   *
   * @return niCategory NICategoryLetter
   */
  public NICategoryLetter getNiCategory() {
    return niCategory;
  }

  /**
   * niCategory
   *
   * @param niCategory NICategoryLetter
   */
  public void setNiCategory(NICategoryLetter niCategory) {
    this.niCategory = niCategory;
  }

  /**
   * The employee&#39;s NI categories
   *
   * @param niCategories List&lt;NICategory&gt;
   * @return Employee
   */
  public Employee niCategories(List<NICategory> niCategories) {
    this.niCategories = niCategories;
    return this;
  }

  /**
   * The employee&#39;s NI categories
   *
   * @param niCategoriesItem NICategory
   * @return Employee
   */
  public Employee addNiCategoriesItem(NICategory niCategoriesItem) {
    if (this.niCategories == null) {
      this.niCategories = new ArrayList<NICategory>();
    }
    this.niCategories.add(niCategoriesItem);
    return this;
  }

  /**
   * The employee&#39;s NI categories
   *
   * @return niCategories
   */
  @ApiModelProperty(value = "The employee's NI categories")
  /**
   * The employee&#39;s NI categories
   *
   * @return niCategories List<NICategory>
   */
  public List<NICategory> getNiCategories() {
    return niCategories;
  }

  /**
   * The employee&#39;s NI categories
   *
   * @param niCategories List&lt;NICategory&gt;
   */
  public void setNiCategories(List<NICategory> niCategories) {
    this.niCategories = niCategories;
  }

  /**
   * National insurance number of the employee
   *
   * @param nationalInsuranceNumber String
   * @return Employee
   */
  public Employee nationalInsuranceNumber(String nationalInsuranceNumber) {
    this.nationalInsuranceNumber = nationalInsuranceNumber;
    return this;
  }

  /**
   * National insurance number of the employee
   *
   * @return nationalInsuranceNumber
   */
  @ApiModelProperty(example = "AB123456C", value = "National insurance number of the employee")
  /**
   * National insurance number of the employee
   *
   * @return nationalInsuranceNumber String
   */
  public String getNationalInsuranceNumber() {
    return nationalInsuranceNumber;
  }

  /**
   * National insurance number of the employee
   *
   * @param nationalInsuranceNumber String
   */
  public void setNationalInsuranceNumber(String nationalInsuranceNumber) {
    this.nationalInsuranceNumber = nationalInsuranceNumber;
  }

  /**
   * Whether the employee is an off payroll worker
   *
   * @param isOffPayrollWorker Boolean
   * @return Employee
   */
  public Employee isOffPayrollWorker(Boolean isOffPayrollWorker) {
    this.isOffPayrollWorker = isOffPayrollWorker;
    return this;
  }

  /**
   * Whether the employee is an off payroll worker
   *
   * @return isOffPayrollWorker
   */
  @ApiModelProperty(value = "Whether the employee is an off payroll worker")
  /**
   * Whether the employee is an off payroll worker
   *
   * @return isOffPayrollWorker Boolean
   */
  public Boolean getIsOffPayrollWorker() {
    return isOffPayrollWorker;
  }

  /**
   * Whether the employee is an off payroll worker
   *
   * @param isOffPayrollWorker Boolean
   */
  public void setIsOffPayrollWorker(Boolean isOffPayrollWorker) {
    this.isOffPayrollWorker = isOffPayrollWorker;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employee employee = (Employee) o;
    return Objects.equals(this.employeeID, employee.employeeID)
        && Objects.equals(this.title, employee.title)
        && Objects.equals(this.firstName, employee.firstName)
        && Objects.equals(this.lastName, employee.lastName)
        && Objects.equals(this.dateOfBirth, employee.dateOfBirth)
        && Objects.equals(this.address, employee.address)
        && Objects.equals(this.email, employee.email)
        && Objects.equals(this.gender, employee.gender)
        && Objects.equals(this.phoneNumber, employee.phoneNumber)
        && Objects.equals(this.startDate, employee.startDate)
        && Objects.equals(this.endDate, employee.endDate)
        && Objects.equals(this.payrollCalendarID, employee.payrollCalendarID)
        && Objects.equals(this.updatedDateUTC, employee.updatedDateUTC)
        && Objects.equals(this.createdDateUTC, employee.createdDateUTC)
        && Objects.equals(this.niCategory, employee.niCategory)
        && Objects.equals(this.niCategories, employee.niCategories)
        && Objects.equals(this.nationalInsuranceNumber, employee.nationalInsuranceNumber)
        && Objects.equals(this.isOffPayrollWorker, employee.isOffPayrollWorker);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        employeeID,
        title,
        firstName,
        lastName,
        dateOfBirth,
        address,
        email,
        gender,
        phoneNumber,
        startDate,
        endDate,
        payrollCalendarID,
        updatedDateUTC,
        createdDateUTC,
        niCategory,
        niCategories,
        nationalInsuranceNumber,
        isOffPayrollWorker);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Employee {\n");
    sb.append("    employeeID: ").append(toIndentedString(employeeID)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    payrollCalendarID: ").append(toIndentedString(payrollCalendarID)).append("\n");
    sb.append("    updatedDateUTC: ").append(toIndentedString(updatedDateUTC)).append("\n");
    sb.append("    createdDateUTC: ").append(toIndentedString(createdDateUTC)).append("\n");
    sb.append("    niCategory: ").append(toIndentedString(niCategory)).append("\n");
    sb.append("    niCategories: ").append(toIndentedString(niCategories)).append("\n");
    sb.append("    nationalInsuranceNumber: ")
        .append(toIndentedString(nationalInsuranceNumber))
        .append("\n");
    sb.append("    isOffPayrollWorker: ").append(toIndentedString(isOffPayrollWorker)).append("\n");
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
