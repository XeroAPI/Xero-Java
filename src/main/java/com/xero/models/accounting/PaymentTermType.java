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


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Gets or Sets PaymentTermType */
public enum PaymentTermType {

  /** DAYSAFTERBILLDATE */
  DAYSAFTERBILLDATE("DAYSAFTERBILLDATE"),

  /** DAYSAFTERBILLMONTH */
  DAYSAFTERBILLMONTH("DAYSAFTERBILLMONTH"),

  /** OFCURRENTMONTH */
  OFCURRENTMONTH("OFCURRENTMONTH"),

  /** OFFOLLOWINGMONTH */
  OFFOLLOWINGMONTH("OFFOLLOWINGMONTH");

  private String value;

  PaymentTermType(String value) {
    this.value = value;
  }

  /** @return String value */
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
  public static PaymentTermType fromValue(String value) {
    for (PaymentTermType b : PaymentTermType.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
