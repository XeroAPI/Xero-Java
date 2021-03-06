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

/** The status of the quote. */
public enum QuoteStatusCodes {

  /** DRAFT */
  DRAFT("DRAFT"),

  /** SENT */
  SENT("SENT"),

  /** DECLINED */
  DECLINED("DECLINED"),

  /** ACCEPTED */
  ACCEPTED("ACCEPTED"),

  /** INVOICED */
  INVOICED("INVOICED"),

  /** DELETED */
  DELETED("DELETED");

  private String value;

  QuoteStatusCodes(String value) {
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
  public static QuoteStatusCodes fromValue(String value) {
    for (QuoteStatusCodes b : QuoteStatusCodes.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
