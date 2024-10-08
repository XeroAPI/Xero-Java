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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xero.api.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;

/** CreditNote */
public class CreditNote {
  StringUtil util = new StringUtil();
  /** See Credit Note Types */
  public enum TypeEnum {
    /** ACCPAYCREDIT */
    ACCPAYCREDIT("ACCPAYCREDIT"),

    /** ACCRECCREDIT */
    ACCRECCREDIT("ACCRECCREDIT");

    private String value;

    TypeEnum(String value) {
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
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("Type")
  private TypeEnum type;

  @JsonProperty("Contact")
  private Contact contact;

  @JsonProperty("Date")
  private String date;

  @JsonProperty("DueDate")
  private String dueDate;
  /** See Credit Note Status Codes */
  public enum StatusEnum {
    /** DRAFT */
    DRAFT("DRAFT"),

    /** SUBMITTED */
    SUBMITTED("SUBMITTED"),

    /** DELETED */
    DELETED("DELETED"),

    /** AUTHORISED */
    AUTHORISED("AUTHORISED"),

    /** PAID */
    PAID("PAID"),

    /** VOIDED */
    VOIDED("VOIDED");

    private String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("Status")
  private StatusEnum status;

  @JsonProperty("LineAmountTypes")
  private LineAmountTypes lineAmountTypes;

  @JsonProperty("LineItems")
  private List<LineItem> lineItems = new ArrayList<LineItem>();

  @JsonProperty("SubTotal")
  private Double subTotal;

  @JsonProperty("TotalTax")
  private Double totalTax;

  @JsonProperty("Total")
  private Double total;

  @JsonProperty("CISDeduction")
  private Double ciSDeduction;

  @JsonProperty("CISRate")
  private Double ciSRate;

  @JsonProperty("UpdatedDateUTC")
  private String updatedDateUTC;

  @JsonProperty("CurrencyCode")
  private CurrencyCode currencyCode;

  @JsonProperty("FullyPaidOnDate")
  private String fullyPaidOnDate;

  @JsonProperty("CreditNoteID")
  private UUID creditNoteID;

  @JsonProperty("CreditNoteNumber")
  private String creditNoteNumber;

  @JsonProperty("Reference")
  private String reference;

  @JsonProperty("SentToContact")
  private Boolean sentToContact;

  @JsonProperty("CurrencyRate")
  private Double currencyRate;

  @JsonProperty("RemainingCredit")
  private Double remainingCredit;

  @JsonProperty("Allocations")
  private List<Allocation> allocations = new ArrayList<Allocation>();

  @JsonProperty("AppliedAmount")
  private Double appliedAmount;

  @JsonProperty("Payments")
  private List<Payment> payments = new ArrayList<Payment>();

  @JsonProperty("BrandingThemeID")
  private UUID brandingThemeID;

  @JsonProperty("StatusAttributeString")
  private String statusAttributeString;

  @JsonProperty("HasAttachments")
  private Boolean hasAttachments = false;

  @JsonProperty("HasErrors")
  private Boolean hasErrors = false;

  @JsonProperty("ValidationErrors")
  private List<ValidationError> validationErrors = new ArrayList<ValidationError>();

  @JsonProperty("Warnings")
  private List<ValidationError> warnings = new ArrayList<ValidationError>();

  @JsonProperty("InvoiceAddresses")
  private List<InvoiceAddress> invoiceAddresses = new ArrayList<InvoiceAddress>();
  /**
   * See Credit Note Types
   *
   * @param type TypeEnum
   * @return CreditNote
   */
  public CreditNote type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * See Credit Note Types
   *
   * @return type
   */
  @ApiModelProperty(value = "See Credit Note Types")
  /**
   * See Credit Note Types
   *
   * @return type TypeEnum
   */
  public TypeEnum getType() {
    return type;
  }

  /**
   * See Credit Note Types
   *
   * @param type TypeEnum
   */
  public void setType(TypeEnum type) {
    this.type = type;
  }

  /**
   * contact
   *
   * @param contact Contact
   * @return CreditNote
   */
  public CreditNote contact(Contact contact) {
    this.contact = contact;
    return this;
  }

  /**
   * Get contact
   *
   * @return contact
   */
  @ApiModelProperty(value = "")
  /**
   * contact
   *
   * @return contact Contact
   */
  public Contact getContact() {
    return contact;
  }

  /**
   * contact
   *
   * @param contact Contact
   */
  public void setContact(Contact contact) {
    this.contact = contact;
  }

  /**
   * The date the credit note is issued YYYY-MM-DD. If the Date element is not specified then it
   * will default to the current date based on the timezone setting of the organisation
   *
   * @param date String
   * @return CreditNote
   */
  public CreditNote date(String date) {
    this.date = date;
    return this;
  }

  /**
   * The date the credit note is issued YYYY-MM-DD. If the Date element is not specified then it
   * will default to the current date based on the timezone setting of the organisation
   *
   * @return date
   */
  @ApiModelProperty(
      value =
          "The date the credit note is issued YYYY-MM-DD. If the Date element is not specified"
              + " then it will default to the current date based on the timezone setting of the"
              + " organisation")
  /**
   * The date the credit note is issued YYYY-MM-DD. If the Date element is not specified then it
   * will default to the current date based on the timezone setting of the organisation
   *
   * @return date String
   */
  public String getDate() {
    return date;
  }
  /**
   * The date the credit note is issued YYYY-MM-DD. If the Date element is not specified then it
   * will default to the current date based on the timezone setting of the organisation
   *
   * @return LocalDate
   */
  public LocalDate getDateAsDate() {
    if (this.date != null) {
      try {
        return util.convertStringToDate(this.date);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * The date the credit note is issued YYYY-MM-DD. If the Date element is not specified then it
   * will default to the current date based on the timezone setting of the organisation
   *
   * @param date String
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * The date the credit note is issued YYYY-MM-DD. If the Date element is not specified then it
   * will default to the current date based on the timezone setting of the organisation
   *
   * @param date LocalDateTime
   */
  public void setDate(LocalDate date) {
    // CONVERT LocalDate args into MS DateFromat String
    Instant instant = date.atStartOfDay(ZoneId.of("UTC").normalized()).toInstant();
    long timeInMillis = instant.toEpochMilli();

    this.date = "/Date(" + Long.toString(timeInMillis) + "+0000)/";
  }

  /**
   * Date invoice is due – YYYY-MM-DD
   *
   * @param dueDate String
   * @return CreditNote
   */
  public CreditNote dueDate(String dueDate) {
    this.dueDate = dueDate;
    return this;
  }

  /**
   * Date invoice is due – YYYY-MM-DD
   *
   * @return dueDate
   */
  @ApiModelProperty(value = "Date invoice is due – YYYY-MM-DD")
  /**
   * Date invoice is due – YYYY-MM-DD
   *
   * @return dueDate String
   */
  public String getDueDate() {
    return dueDate;
  }
  /**
   * Date invoice is due – YYYY-MM-DD
   *
   * @return LocalDate
   */
  public LocalDate getDueDateAsDate() {
    if (this.dueDate != null) {
      try {
        return util.convertStringToDate(this.dueDate);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * Date invoice is due – YYYY-MM-DD
   *
   * @param dueDate String
   */
  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }

  /**
   * Date invoice is due – YYYY-MM-DD
   *
   * @param dueDate LocalDateTime
   */
  public void setDueDate(LocalDate dueDate) {
    // CONVERT LocalDate args into MS DateFromat String
    Instant instant = dueDate.atStartOfDay(ZoneId.of("UTC").normalized()).toInstant();
    long timeInMillis = instant.toEpochMilli();

    this.dueDate = "/Date(" + Long.toString(timeInMillis) + "+0000)/";
  }

  /**
   * See Credit Note Status Codes
   *
   * @param status StatusEnum
   * @return CreditNote
   */
  public CreditNote status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * See Credit Note Status Codes
   *
   * @return status
   */
  @ApiModelProperty(value = "See Credit Note Status Codes")
  /**
   * See Credit Note Status Codes
   *
   * @return status StatusEnum
   */
  public StatusEnum getStatus() {
    return status;
  }

  /**
   * See Credit Note Status Codes
   *
   * @param status StatusEnum
   */
  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  /**
   * lineAmountTypes
   *
   * @param lineAmountTypes LineAmountTypes
   * @return CreditNote
   */
  public CreditNote lineAmountTypes(LineAmountTypes lineAmountTypes) {
    this.lineAmountTypes = lineAmountTypes;
    return this;
  }

  /**
   * Get lineAmountTypes
   *
   * @return lineAmountTypes
   */
  @ApiModelProperty(value = "")
  /**
   * lineAmountTypes
   *
   * @return lineAmountTypes LineAmountTypes
   */
  public LineAmountTypes getLineAmountTypes() {
    return lineAmountTypes;
  }

  /**
   * lineAmountTypes
   *
   * @param lineAmountTypes LineAmountTypes
   */
  public void setLineAmountTypes(LineAmountTypes lineAmountTypes) {
    this.lineAmountTypes = lineAmountTypes;
  }

  /**
   * See Invoice Line Items
   *
   * @param lineItems List&lt;LineItem&gt;
   * @return CreditNote
   */
  public CreditNote lineItems(List<LineItem> lineItems) {
    this.lineItems = lineItems;
    return this;
  }

  /**
   * See Invoice Line Items
   *
   * @param lineItemsItem LineItem
   * @return CreditNote
   */
  public CreditNote addLineItemsItem(LineItem lineItemsItem) {
    if (this.lineItems == null) {
      this.lineItems = new ArrayList<LineItem>();
    }
    this.lineItems.add(lineItemsItem);
    return this;
  }

  /**
   * See Invoice Line Items
   *
   * @return lineItems
   */
  @ApiModelProperty(value = "See Invoice Line Items")
  /**
   * See Invoice Line Items
   *
   * @return lineItems List<LineItem>
   */
  public List<LineItem> getLineItems() {
    return lineItems;
  }

  /**
   * See Invoice Line Items
   *
   * @param lineItems List&lt;LineItem&gt;
   */
  public void setLineItems(List<LineItem> lineItems) {
    this.lineItems = lineItems;
  }

  /**
   * The subtotal of the credit note excluding taxes
   *
   * @param subTotal Double
   * @return CreditNote
   */
  public CreditNote subTotal(Double subTotal) {
    this.subTotal = subTotal;
    return this;
  }

  /**
   * The subtotal of the credit note excluding taxes
   *
   * @return subTotal
   */
  @ApiModelProperty(value = "The subtotal of the credit note excluding taxes")
  /**
   * The subtotal of the credit note excluding taxes
   *
   * @return subTotal Double
   */
  public Double getSubTotal() {
    return subTotal;
  }

  /**
   * The subtotal of the credit note excluding taxes
   *
   * @param subTotal Double
   */
  public void setSubTotal(Double subTotal) {
    this.subTotal = subTotal;
  }

  /**
   * The total tax on the credit note
   *
   * @param totalTax Double
   * @return CreditNote
   */
  public CreditNote totalTax(Double totalTax) {
    this.totalTax = totalTax;
    return this;
  }

  /**
   * The total tax on the credit note
   *
   * @return totalTax
   */
  @ApiModelProperty(value = "The total tax on the credit note")
  /**
   * The total tax on the credit note
   *
   * @return totalTax Double
   */
  public Double getTotalTax() {
    return totalTax;
  }

  /**
   * The total tax on the credit note
   *
   * @param totalTax Double
   */
  public void setTotalTax(Double totalTax) {
    this.totalTax = totalTax;
  }

  /**
   * The total of the Credit Note(subtotal + total tax)
   *
   * @param total Double
   * @return CreditNote
   */
  public CreditNote total(Double total) {
    this.total = total;
    return this;
  }

  /**
   * The total of the Credit Note(subtotal + total tax)
   *
   * @return total
   */
  @ApiModelProperty(value = "The total of the Credit Note(subtotal + total tax)")
  /**
   * The total of the Credit Note(subtotal + total tax)
   *
   * @return total Double
   */
  public Double getTotal() {
    return total;
  }

  /**
   * The total of the Credit Note(subtotal + total tax)
   *
   * @param total Double
   */
  public void setTotal(Double total) {
    this.total = total;
  }

  /**
   * CIS deduction for UK contractors
   *
   * @return ciSDeduction
   */
  @ApiModelProperty(value = "CIS deduction for UK contractors")
  /**
   * CIS deduction for UK contractors
   *
   * @return ciSDeduction Double
   */
  public Double getCiSDeduction() {
    return ciSDeduction;
  }

  /**
   * CIS Deduction rate for the organisation
   *
   * @return ciSRate
   */
  @ApiModelProperty(value = "CIS Deduction rate for the organisation")
  /**
   * CIS Deduction rate for the organisation
   *
   * @return ciSRate Double
   */
  public Double getCiSRate() {
    return ciSRate;
  }

  /**
   * UTC timestamp of last update to the credit note
   *
   * @return updatedDateUTC
   */
  @ApiModelProperty(
      example = "/Date(1573755038314)/",
      value = "UTC timestamp of last update to the credit note")
  /**
   * UTC timestamp of last update to the credit note
   *
   * @return updatedDateUTC String
   */
  public String getUpdatedDateUTC() {
    return updatedDateUTC;
  }
  /**
   * UTC timestamp of last update to the credit note
   *
   * @return OffsetDateTime
   */
  public OffsetDateTime getUpdatedDateUTCAsDate() {
    if (this.updatedDateUTC != null) {
      try {
        return util.convertStringToOffsetDateTime(this.updatedDateUTC);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * currencyCode
   *
   * @param currencyCode CurrencyCode
   * @return CreditNote
   */
  public CreditNote currencyCode(CurrencyCode currencyCode) {
    this.currencyCode = currencyCode;
    return this;
  }

  /**
   * Get currencyCode
   *
   * @return currencyCode
   */
  @ApiModelProperty(value = "")
  /**
   * currencyCode
   *
   * @return currencyCode CurrencyCode
   */
  public CurrencyCode getCurrencyCode() {
    return currencyCode;
  }

  /**
   * currencyCode
   *
   * @param currencyCode CurrencyCode
   */
  public void setCurrencyCode(CurrencyCode currencyCode) {
    this.currencyCode = currencyCode;
  }

  /**
   * Date when credit note was fully paid(UTC format)
   *
   * @param fullyPaidOnDate String
   * @return CreditNote
   */
  public CreditNote fullyPaidOnDate(String fullyPaidOnDate) {
    this.fullyPaidOnDate = fullyPaidOnDate;
    return this;
  }

  /**
   * Date when credit note was fully paid(UTC format)
   *
   * @return fullyPaidOnDate
   */
  @ApiModelProperty(value = "Date when credit note was fully paid(UTC format)")
  /**
   * Date when credit note was fully paid(UTC format)
   *
   * @return fullyPaidOnDate String
   */
  public String getFullyPaidOnDate() {
    return fullyPaidOnDate;
  }
  /**
   * Date when credit note was fully paid(UTC format)
   *
   * @return LocalDate
   */
  public LocalDate getFullyPaidOnDateAsDate() {
    if (this.fullyPaidOnDate != null) {
      try {
        return util.convertStringToDate(this.fullyPaidOnDate);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * Date when credit note was fully paid(UTC format)
   *
   * @param fullyPaidOnDate String
   */
  public void setFullyPaidOnDate(String fullyPaidOnDate) {
    this.fullyPaidOnDate = fullyPaidOnDate;
  }

  /**
   * Date when credit note was fully paid(UTC format)
   *
   * @param fullyPaidOnDate LocalDateTime
   */
  public void setFullyPaidOnDate(LocalDate fullyPaidOnDate) {
    // CONVERT LocalDate args into MS DateFromat String
    Instant instant = fullyPaidOnDate.atStartOfDay(ZoneId.of("UTC").normalized()).toInstant();
    long timeInMillis = instant.toEpochMilli();

    this.fullyPaidOnDate = "/Date(" + Long.toString(timeInMillis) + "+0000)/";
  }

  /**
   * Xero generated unique identifier
   *
   * @param creditNoteID UUID
   * @return CreditNote
   */
  public CreditNote creditNoteID(UUID creditNoteID) {
    this.creditNoteID = creditNoteID;
    return this;
  }

  /**
   * Xero generated unique identifier
   *
   * @return creditNoteID
   */
  @ApiModelProperty(value = "Xero generated unique identifier")
  /**
   * Xero generated unique identifier
   *
   * @return creditNoteID UUID
   */
  public UUID getCreditNoteID() {
    return creditNoteID;
  }

  /**
   * Xero generated unique identifier
   *
   * @param creditNoteID UUID
   */
  public void setCreditNoteID(UUID creditNoteID) {
    this.creditNoteID = creditNoteID;
  }

  /**
   * ACCRECCREDIT – Unique alpha numeric code identifying credit note (when missing will
   * auto-generate from your Organisation Invoice Settings)
   *
   * @param creditNoteNumber String
   * @return CreditNote
   */
  public CreditNote creditNoteNumber(String creditNoteNumber) {
    this.creditNoteNumber = creditNoteNumber;
    return this;
  }

  /**
   * ACCRECCREDIT – Unique alpha numeric code identifying credit note (when missing will
   * auto-generate from your Organisation Invoice Settings)
   *
   * @return creditNoteNumber
   */
  @ApiModelProperty(
      value =
          "ACCRECCREDIT – Unique alpha numeric code identifying credit note (when missing will"
              + " auto-generate from your Organisation Invoice Settings)")
  /**
   * ACCRECCREDIT – Unique alpha numeric code identifying credit note (when missing will
   * auto-generate from your Organisation Invoice Settings)
   *
   * @return creditNoteNumber String
   */
  public String getCreditNoteNumber() {
    return creditNoteNumber;
  }

  /**
   * ACCRECCREDIT – Unique alpha numeric code identifying credit note (when missing will
   * auto-generate from your Organisation Invoice Settings)
   *
   * @param creditNoteNumber String
   */
  public void setCreditNoteNumber(String creditNoteNumber) {
    this.creditNoteNumber = creditNoteNumber;
  }

  /**
   * ACCRECCREDIT only – additional reference number
   *
   * @param reference String
   * @return CreditNote
   */
  public CreditNote reference(String reference) {
    this.reference = reference;
    return this;
  }

  /**
   * ACCRECCREDIT only – additional reference number
   *
   * @return reference
   */
  @ApiModelProperty(value = "ACCRECCREDIT only – additional reference number")
  /**
   * ACCRECCREDIT only – additional reference number
   *
   * @return reference String
   */
  public String getReference() {
    return reference;
  }

  /**
   * ACCRECCREDIT only – additional reference number
   *
   * @param reference String
   */
  public void setReference(String reference) {
    this.reference = reference;
  }

  /**
   * Boolean to set whether the credit note in the Xero app should be marked as “sent”. This can be
   * set only on credit notes that have been approved
   *
   * @return sentToContact
   */
  @ApiModelProperty(
      value =
          "Boolean to set whether the credit note in the Xero app should be marked as “sent”. This"
              + " can be set only on credit notes that have been approved")
  /**
   * Boolean to set whether the credit note in the Xero app should be marked as “sent”. This can be
   * set only on credit notes that have been approved
   *
   * @return sentToContact Boolean
   */
  public Boolean getSentToContact() {
    return sentToContact;
  }

  /**
   * The currency rate for a multicurrency invoice. If no rate is specified, the XE.com day rate is
   * used
   *
   * @param currencyRate Double
   * @return CreditNote
   */
  public CreditNote currencyRate(Double currencyRate) {
    this.currencyRate = currencyRate;
    return this;
  }

  /**
   * The currency rate for a multicurrency invoice. If no rate is specified, the XE.com day rate is
   * used
   *
   * @return currencyRate
   */
  @ApiModelProperty(
      value =
          "The currency rate for a multicurrency invoice. If no rate is specified, the XE.com day"
              + " rate is used")
  /**
   * The currency rate for a multicurrency invoice. If no rate is specified, the XE.com day rate is
   * used
   *
   * @return currencyRate Double
   */
  public Double getCurrencyRate() {
    return currencyRate;
  }

  /**
   * The currency rate for a multicurrency invoice. If no rate is specified, the XE.com day rate is
   * used
   *
   * @param currencyRate Double
   */
  public void setCurrencyRate(Double currencyRate) {
    this.currencyRate = currencyRate;
  }

  /**
   * The remaining credit balance on the Credit Note
   *
   * @param remainingCredit Double
   * @return CreditNote
   */
  public CreditNote remainingCredit(Double remainingCredit) {
    this.remainingCredit = remainingCredit;
    return this;
  }

  /**
   * The remaining credit balance on the Credit Note
   *
   * @return remainingCredit
   */
  @ApiModelProperty(value = "The remaining credit balance on the Credit Note")
  /**
   * The remaining credit balance on the Credit Note
   *
   * @return remainingCredit Double
   */
  public Double getRemainingCredit() {
    return remainingCredit;
  }

  /**
   * The remaining credit balance on the Credit Note
   *
   * @param remainingCredit Double
   */
  public void setRemainingCredit(Double remainingCredit) {
    this.remainingCredit = remainingCredit;
  }

  /**
   * See Allocations
   *
   * @param allocations List&lt;Allocation&gt;
   * @return CreditNote
   */
  public CreditNote allocations(List<Allocation> allocations) {
    this.allocations = allocations;
    return this;
  }

  /**
   * See Allocations
   *
   * @param allocationsItem Allocation
   * @return CreditNote
   */
  public CreditNote addAllocationsItem(Allocation allocationsItem) {
    if (this.allocations == null) {
      this.allocations = new ArrayList<Allocation>();
    }
    this.allocations.add(allocationsItem);
    return this;
  }

  /**
   * See Allocations
   *
   * @return allocations
   */
  @ApiModelProperty(value = "See Allocations")
  /**
   * See Allocations
   *
   * @return allocations List<Allocation>
   */
  public List<Allocation> getAllocations() {
    return allocations;
  }

  /**
   * See Allocations
   *
   * @param allocations List&lt;Allocation&gt;
   */
  public void setAllocations(List<Allocation> allocations) {
    this.allocations = allocations;
  }

  /**
   * The amount of applied to an invoice
   *
   * @param appliedAmount Double
   * @return CreditNote
   */
  public CreditNote appliedAmount(Double appliedAmount) {
    this.appliedAmount = appliedAmount;
    return this;
  }

  /**
   * The amount of applied to an invoice
   *
   * @return appliedAmount
   */
  @ApiModelProperty(example = "2.0", value = "The amount of applied to an invoice")
  /**
   * The amount of applied to an invoice
   *
   * @return appliedAmount Double
   */
  public Double getAppliedAmount() {
    return appliedAmount;
  }

  /**
   * The amount of applied to an invoice
   *
   * @param appliedAmount Double
   */
  public void setAppliedAmount(Double appliedAmount) {
    this.appliedAmount = appliedAmount;
  }

  /**
   * See Payments
   *
   * @param payments List&lt;Payment&gt;
   * @return CreditNote
   */
  public CreditNote payments(List<Payment> payments) {
    this.payments = payments;
    return this;
  }

  /**
   * See Payments
   *
   * @param paymentsItem Payment
   * @return CreditNote
   */
  public CreditNote addPaymentsItem(Payment paymentsItem) {
    if (this.payments == null) {
      this.payments = new ArrayList<Payment>();
    }
    this.payments.add(paymentsItem);
    return this;
  }

  /**
   * See Payments
   *
   * @return payments
   */
  @ApiModelProperty(value = "See Payments")
  /**
   * See Payments
   *
   * @return payments List<Payment>
   */
  public List<Payment> getPayments() {
    return payments;
  }

  /**
   * See Payments
   *
   * @param payments List&lt;Payment&gt;
   */
  public void setPayments(List<Payment> payments) {
    this.payments = payments;
  }

  /**
   * See BrandingThemes
   *
   * @param brandingThemeID UUID
   * @return CreditNote
   */
  public CreditNote brandingThemeID(UUID brandingThemeID) {
    this.brandingThemeID = brandingThemeID;
    return this;
  }

  /**
   * See BrandingThemes
   *
   * @return brandingThemeID
   */
  @ApiModelProperty(value = "See BrandingThemes")
  /**
   * See BrandingThemes
   *
   * @return brandingThemeID UUID
   */
  public UUID getBrandingThemeID() {
    return brandingThemeID;
  }

  /**
   * See BrandingThemes
   *
   * @param brandingThemeID UUID
   */
  public void setBrandingThemeID(UUID brandingThemeID) {
    this.brandingThemeID = brandingThemeID;
  }

  /**
   * A string to indicate if a invoice status
   *
   * @param statusAttributeString String
   * @return CreditNote
   */
  public CreditNote statusAttributeString(String statusAttributeString) {
    this.statusAttributeString = statusAttributeString;
    return this;
  }

  /**
   * A string to indicate if a invoice status
   *
   * @return statusAttributeString
   */
  @ApiModelProperty(value = "A string to indicate if a invoice status")
  /**
   * A string to indicate if a invoice status
   *
   * @return statusAttributeString String
   */
  public String getStatusAttributeString() {
    return statusAttributeString;
  }

  /**
   * A string to indicate if a invoice status
   *
   * @param statusAttributeString String
   */
  public void setStatusAttributeString(String statusAttributeString) {
    this.statusAttributeString = statusAttributeString;
  }

  /**
   * boolean to indicate if a credit note has an attachment
   *
   * @param hasAttachments Boolean
   * @return CreditNote
   */
  public CreditNote hasAttachments(Boolean hasAttachments) {
    this.hasAttachments = hasAttachments;
    return this;
  }

  /**
   * boolean to indicate if a credit note has an attachment
   *
   * @return hasAttachments
   */
  @ApiModelProperty(
      example = "false",
      value = "boolean to indicate if a credit note has an attachment")
  /**
   * boolean to indicate if a credit note has an attachment
   *
   * @return hasAttachments Boolean
   */
  public Boolean getHasAttachments() {
    return hasAttachments;
  }

  /**
   * boolean to indicate if a credit note has an attachment
   *
   * @param hasAttachments Boolean
   */
  public void setHasAttachments(Boolean hasAttachments) {
    this.hasAttachments = hasAttachments;
  }

  /**
   * A boolean to indicate if a credit note has an validation errors
   *
   * @param hasErrors Boolean
   * @return CreditNote
   */
  public CreditNote hasErrors(Boolean hasErrors) {
    this.hasErrors = hasErrors;
    return this;
  }

  /**
   * A boolean to indicate if a credit note has an validation errors
   *
   * @return hasErrors
   */
  @ApiModelProperty(
      example = "false",
      value = "A boolean to indicate if a credit note has an validation errors")
  /**
   * A boolean to indicate if a credit note has an validation errors
   *
   * @return hasErrors Boolean
   */
  public Boolean getHasErrors() {
    return hasErrors;
  }

  /**
   * A boolean to indicate if a credit note has an validation errors
   *
   * @param hasErrors Boolean
   */
  public void setHasErrors(Boolean hasErrors) {
    this.hasErrors = hasErrors;
  }

  /**
   * Displays array of validation error messages from the API
   *
   * @param validationErrors List&lt;ValidationError&gt;
   * @return CreditNote
   */
  public CreditNote validationErrors(List<ValidationError> validationErrors) {
    this.validationErrors = validationErrors;
    return this;
  }

  /**
   * Displays array of validation error messages from the API
   *
   * @param validationErrorsItem ValidationError
   * @return CreditNote
   */
  public CreditNote addValidationErrorsItem(ValidationError validationErrorsItem) {
    if (this.validationErrors == null) {
      this.validationErrors = new ArrayList<ValidationError>();
    }
    this.validationErrors.add(validationErrorsItem);
    return this;
  }

  /**
   * Displays array of validation error messages from the API
   *
   * @return validationErrors
   */
  @ApiModelProperty(value = "Displays array of validation error messages from the API")
  /**
   * Displays array of validation error messages from the API
   *
   * @return validationErrors List<ValidationError>
   */
  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }

  /**
   * Displays array of validation error messages from the API
   *
   * @param validationErrors List&lt;ValidationError&gt;
   */
  public void setValidationErrors(List<ValidationError> validationErrors) {
    this.validationErrors = validationErrors;
  }

  /**
   * Displays array of warning messages from the API
   *
   * @param warnings List&lt;ValidationError&gt;
   * @return CreditNote
   */
  public CreditNote warnings(List<ValidationError> warnings) {
    this.warnings = warnings;
    return this;
  }

  /**
   * Displays array of warning messages from the API
   *
   * @param warningsItem ValidationError
   * @return CreditNote
   */
  public CreditNote addWarningsItem(ValidationError warningsItem) {
    if (this.warnings == null) {
      this.warnings = new ArrayList<ValidationError>();
    }
    this.warnings.add(warningsItem);
    return this;
  }

  /**
   * Displays array of warning messages from the API
   *
   * @return warnings
   */
  @ApiModelProperty(value = "Displays array of warning messages from the API")
  /**
   * Displays array of warning messages from the API
   *
   * @return warnings List<ValidationError>
   */
  public List<ValidationError> getWarnings() {
    return warnings;
  }

  /**
   * Displays array of warning messages from the API
   *
   * @param warnings List&lt;ValidationError&gt;
   */
  public void setWarnings(List<ValidationError> warnings) {
    this.warnings = warnings;
  }

  /**
   * An array of addresses used to auto calculate sales tax
   *
   * @param invoiceAddresses List&lt;InvoiceAddress&gt;
   * @return CreditNote
   */
  public CreditNote invoiceAddresses(List<InvoiceAddress> invoiceAddresses) {
    this.invoiceAddresses = invoiceAddresses;
    return this;
  }

  /**
   * An array of addresses used to auto calculate sales tax
   *
   * @param invoiceAddressesItem InvoiceAddress
   * @return CreditNote
   */
  public CreditNote addInvoiceAddressesItem(InvoiceAddress invoiceAddressesItem) {
    if (this.invoiceAddresses == null) {
      this.invoiceAddresses = new ArrayList<InvoiceAddress>();
    }
    this.invoiceAddresses.add(invoiceAddressesItem);
    return this;
  }

  /**
   * An array of addresses used to auto calculate sales tax
   *
   * @return invoiceAddresses
   */
  @ApiModelProperty(value = "An array of addresses used to auto calculate sales tax")
  /**
   * An array of addresses used to auto calculate sales tax
   *
   * @return invoiceAddresses List<InvoiceAddress>
   */
  public List<InvoiceAddress> getInvoiceAddresses() {
    return invoiceAddresses;
  }

  /**
   * An array of addresses used to auto calculate sales tax
   *
   * @param invoiceAddresses List&lt;InvoiceAddress&gt;
   */
  public void setInvoiceAddresses(List<InvoiceAddress> invoiceAddresses) {
    this.invoiceAddresses = invoiceAddresses;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreditNote creditNote = (CreditNote) o;
    return Objects.equals(this.type, creditNote.type)
        && Objects.equals(this.contact, creditNote.contact)
        && Objects.equals(this.date, creditNote.date)
        && Objects.equals(this.dueDate, creditNote.dueDate)
        && Objects.equals(this.status, creditNote.status)
        && Objects.equals(this.lineAmountTypes, creditNote.lineAmountTypes)
        && Objects.equals(this.lineItems, creditNote.lineItems)
        && Objects.equals(this.subTotal, creditNote.subTotal)
        && Objects.equals(this.totalTax, creditNote.totalTax)
        && Objects.equals(this.total, creditNote.total)
        && Objects.equals(this.ciSDeduction, creditNote.ciSDeduction)
        && Objects.equals(this.ciSRate, creditNote.ciSRate)
        && Objects.equals(this.updatedDateUTC, creditNote.updatedDateUTC)
        && Objects.equals(this.currencyCode, creditNote.currencyCode)
        && Objects.equals(this.fullyPaidOnDate, creditNote.fullyPaidOnDate)
        && Objects.equals(this.creditNoteID, creditNote.creditNoteID)
        && Objects.equals(this.creditNoteNumber, creditNote.creditNoteNumber)
        && Objects.equals(this.reference, creditNote.reference)
        && Objects.equals(this.sentToContact, creditNote.sentToContact)
        && Objects.equals(this.currencyRate, creditNote.currencyRate)
        && Objects.equals(this.remainingCredit, creditNote.remainingCredit)
        && Objects.equals(this.allocations, creditNote.allocations)
        && Objects.equals(this.appliedAmount, creditNote.appliedAmount)
        && Objects.equals(this.payments, creditNote.payments)
        && Objects.equals(this.brandingThemeID, creditNote.brandingThemeID)
        && Objects.equals(this.statusAttributeString, creditNote.statusAttributeString)
        && Objects.equals(this.hasAttachments, creditNote.hasAttachments)
        && Objects.equals(this.hasErrors, creditNote.hasErrors)
        && Objects.equals(this.validationErrors, creditNote.validationErrors)
        && Objects.equals(this.warnings, creditNote.warnings)
        && Objects.equals(this.invoiceAddresses, creditNote.invoiceAddresses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        type,
        contact,
        date,
        dueDate,
        status,
        lineAmountTypes,
        lineItems,
        subTotal,
        totalTax,
        total,
        ciSDeduction,
        ciSRate,
        updatedDateUTC,
        currencyCode,
        fullyPaidOnDate,
        creditNoteID,
        creditNoteNumber,
        reference,
        sentToContact,
        currencyRate,
        remainingCredit,
        allocations,
        appliedAmount,
        payments,
        brandingThemeID,
        statusAttributeString,
        hasAttachments,
        hasErrors,
        validationErrors,
        warnings,
        invoiceAddresses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreditNote {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    contact: ").append(toIndentedString(contact)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    lineAmountTypes: ").append(toIndentedString(lineAmountTypes)).append("\n");
    sb.append("    lineItems: ").append(toIndentedString(lineItems)).append("\n");
    sb.append("    subTotal: ").append(toIndentedString(subTotal)).append("\n");
    sb.append("    totalTax: ").append(toIndentedString(totalTax)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    ciSDeduction: ").append(toIndentedString(ciSDeduction)).append("\n");
    sb.append("    ciSRate: ").append(toIndentedString(ciSRate)).append("\n");
    sb.append("    updatedDateUTC: ").append(toIndentedString(updatedDateUTC)).append("\n");
    sb.append("    currencyCode: ").append(toIndentedString(currencyCode)).append("\n");
    sb.append("    fullyPaidOnDate: ").append(toIndentedString(fullyPaidOnDate)).append("\n");
    sb.append("    creditNoteID: ").append(toIndentedString(creditNoteID)).append("\n");
    sb.append("    creditNoteNumber: ").append(toIndentedString(creditNoteNumber)).append("\n");
    sb.append("    reference: ").append(toIndentedString(reference)).append("\n");
    sb.append("    sentToContact: ").append(toIndentedString(sentToContact)).append("\n");
    sb.append("    currencyRate: ").append(toIndentedString(currencyRate)).append("\n");
    sb.append("    remainingCredit: ").append(toIndentedString(remainingCredit)).append("\n");
    sb.append("    allocations: ").append(toIndentedString(allocations)).append("\n");
    sb.append("    appliedAmount: ").append(toIndentedString(appliedAmount)).append("\n");
    sb.append("    payments: ").append(toIndentedString(payments)).append("\n");
    sb.append("    brandingThemeID: ").append(toIndentedString(brandingThemeID)).append("\n");
    sb.append("    statusAttributeString: ")
        .append(toIndentedString(statusAttributeString))
        .append("\n");
    sb.append("    hasAttachments: ").append(toIndentedString(hasAttachments)).append("\n");
    sb.append("    hasErrors: ").append(toIndentedString(hasErrors)).append("\n");
    sb.append("    validationErrors: ").append(toIndentedString(validationErrors)).append("\n");
    sb.append("    warnings: ").append(toIndentedString(warnings)).append("\n");
    sb.append("    invoiceAddresses: ").append(toIndentedString(invoiceAddresses)).append("\n");
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
