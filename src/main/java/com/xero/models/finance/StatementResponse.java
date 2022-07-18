/*
 * Xero Finance API
 * The Finance API is a collection of endpoints which customers can use in the course of a loan application, which may assist lenders to gain the confidence they need to provide capital.
 *
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.xero.models.finance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xero.api.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

/** StatementResponse */
public class StatementResponse {
  StringUtil util = new StringUtil();

  @JsonProperty("statementId")
  private UUID statementId;

  @JsonProperty("startDate")
  private LocalDate startDate;

  @JsonProperty("endDate")
  private LocalDate endDate;

  @JsonProperty("importedDateTimeUtc")
  private OffsetDateTime importedDateTimeUtc;

  @JsonProperty("importSource")
  private String importSource;

  @JsonProperty("startBalance")
  private Double startBalance;

  @JsonProperty("endBalance")
  private Double endBalance;

  @JsonProperty("statementLines")
  private List<StatementLineResponse> statementLines = new ArrayList<StatementLineResponse>();
  /**
   * Xero Identifier of statement
   *
   * @param statementId UUID
   * @return StatementResponse
   */
  public StatementResponse statementId(UUID statementId) {
    this.statementId = statementId;
    return this;
  }

  /**
   * Xero Identifier of statement
   *
   * @return statementId
   */
  @ApiModelProperty(value = "Xero Identifier of statement")
  /**
   * Xero Identifier of statement
   *
   * @return statementId UUID
   */
  public UUID getStatementId() {
    return statementId;
  }

  /**
   * Xero Identifier of statement
   *
   * @param statementId UUID
   */
  public void setStatementId(UUID statementId) {
    this.statementId = statementId;
  }

  /**
   * Start date of statement
   *
   * @param startDate LocalDate
   * @return StatementResponse
   */
  public StatementResponse startDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Start date of statement
   *
   * @return startDate
   */
  @ApiModelProperty(value = "Start date of statement")
  /**
   * Start date of statement
   *
   * @return startDate LocalDate
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * Start date of statement
   *
   * @param startDate LocalDate
   */
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  /**
   * End date of statement
   *
   * @param endDate LocalDate
   * @return StatementResponse
   */
  public StatementResponse endDate(LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * End date of statement
   *
   * @return endDate
   */
  @ApiModelProperty(value = "End date of statement")
  /**
   * End date of statement
   *
   * @return endDate LocalDate
   */
  public LocalDate getEndDate() {
    return endDate;
  }

  /**
   * End date of statement
   *
   * @param endDate LocalDate
   */
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  /**
   * Utc date time of when the statement was imported in Xero
   *
   * @param importedDateTimeUtc OffsetDateTime
   * @return StatementResponse
   */
  public StatementResponse importedDateTimeUtc(OffsetDateTime importedDateTimeUtc) {
    this.importedDateTimeUtc = importedDateTimeUtc;
    return this;
  }

  /**
   * Utc date time of when the statement was imported in Xero
   *
   * @return importedDateTimeUtc
   */
  @ApiModelProperty(value = "Utc date time of when the statement was imported in Xero")
  /**
   * Utc date time of when the statement was imported in Xero
   *
   * @return importedDateTimeUtc OffsetDateTime
   */
  public OffsetDateTime getImportedDateTimeUtc() {
    return importedDateTimeUtc;
  }

  /**
   * Utc date time of when the statement was imported in Xero
   *
   * @param importedDateTimeUtc OffsetDateTime
   */
  public void setImportedDateTimeUtc(OffsetDateTime importedDateTimeUtc) {
    this.importedDateTimeUtc = importedDateTimeUtc;
  }

  /**
   * Indicates the source of the statement data. Either imported from 1) direct bank feed OR 2)
   * manual customer entry or upload. Manual import sources are STMTIMPORTSRC/MANUAL,
   * STMTIMPORTSRC/CSV, STMTIMPORTSRC/OFX, Ofx or STMTIMPORTSRC/QIF. All other import sources are
   * direct and, depending on the direct solution, may contain the name of the financial
   * institution.
   *
   * @param importSource String
   * @return StatementResponse
   */
  public StatementResponse importSource(String importSource) {
    this.importSource = importSource;
    return this;
  }

  /**
   * Indicates the source of the statement data. Either imported from 1) direct bank feed OR 2)
   * manual customer entry or upload. Manual import sources are STMTIMPORTSRC/MANUAL,
   * STMTIMPORTSRC/CSV, STMTIMPORTSRC/OFX, Ofx or STMTIMPORTSRC/QIF. All other import sources are
   * direct and, depending on the direct solution, may contain the name of the financial
   * institution.
   *
   * @return importSource
   */
  @ApiModelProperty(
      value =
          "Indicates the source of the statement data. Either imported from 1) direct bank feed OR"
              + " 2) manual customer entry or upload. Manual import sources are"
              + " STMTIMPORTSRC/MANUAL, STMTIMPORTSRC/CSV, STMTIMPORTSRC/OFX, Ofx or"
              + " STMTIMPORTSRC/QIF. All other import sources are direct and, depending on the"
              + " direct solution, may contain the name of the financial institution.")
  /**
   * Indicates the source of the statement data. Either imported from 1) direct bank feed OR 2)
   * manual customer entry or upload. Manual import sources are STMTIMPORTSRC/MANUAL,
   * STMTIMPORTSRC/CSV, STMTIMPORTSRC/OFX, Ofx or STMTIMPORTSRC/QIF. All other import sources are
   * direct and, depending on the direct solution, may contain the name of the financial
   * institution.
   *
   * @return importSource String
   */
  public String getImportSource() {
    return importSource;
  }

  /**
   * Indicates the source of the statement data. Either imported from 1) direct bank feed OR 2)
   * manual customer entry or upload. Manual import sources are STMTIMPORTSRC/MANUAL,
   * STMTIMPORTSRC/CSV, STMTIMPORTSRC/OFX, Ofx or STMTIMPORTSRC/QIF. All other import sources are
   * direct and, depending on the direct solution, may contain the name of the financial
   * institution.
   *
   * @param importSource String
   */
  public void setImportSource(String importSource) {
    this.importSource = importSource;
  }

  /**
   * Opening balance sourced from imported bank statements (if supplied). Note, for manually
   * uploaded statements, this balance is also manual and usually not supplied.
   *
   * @param startBalance Double
   * @return StatementResponse
   */
  public StatementResponse startBalance(Double startBalance) {
    this.startBalance = startBalance;
    return this;
  }

  /**
   * Opening balance sourced from imported bank statements (if supplied). Note, for manually
   * uploaded statements, this balance is also manual and usually not supplied.
   *
   * @return startBalance
   */
  @ApiModelProperty(
      value =
          "Opening balance sourced from imported bank statements (if supplied). Note, for manually"
              + " uploaded statements, this balance is also manual and usually not supplied.")
  /**
   * Opening balance sourced from imported bank statements (if supplied). Note, for manually
   * uploaded statements, this balance is also manual and usually not supplied.
   *
   * @return startBalance Double
   */
  public Double getStartBalance() {
    return startBalance;
  }

  /**
   * Opening balance sourced from imported bank statements (if supplied). Note, for manually
   * uploaded statements, this balance is also manual and usually not supplied.
   *
   * @param startBalance Double
   */
  public void setStartBalance(Double startBalance) {
    this.startBalance = startBalance;
  }

  /**
   * Closing balance sourced from imported bank statements (if supplied). Note, for manually
   * uploaded statements, this balance is also manual and usually not supplied.
   *
   * @param endBalance Double
   * @return StatementResponse
   */
  public StatementResponse endBalance(Double endBalance) {
    this.endBalance = endBalance;
    return this;
  }

  /**
   * Closing balance sourced from imported bank statements (if supplied). Note, for manually
   * uploaded statements, this balance is also manual and usually not supplied.
   *
   * @return endBalance
   */
  @ApiModelProperty(
      value =
          "Closing balance sourced from imported bank statements (if supplied). Note, for manually"
              + " uploaded statements, this balance is also manual and usually not supplied.")
  /**
   * Closing balance sourced from imported bank statements (if supplied). Note, for manually
   * uploaded statements, this balance is also manual and usually not supplied.
   *
   * @return endBalance Double
   */
  public Double getEndBalance() {
    return endBalance;
  }

  /**
   * Closing balance sourced from imported bank statements (if supplied). Note, for manually
   * uploaded statements, this balance is also manual and usually not supplied.
   *
   * @param endBalance Double
   */
  public void setEndBalance(Double endBalance) {
    this.endBalance = endBalance;
  }

  /**
   * List of statement lines
   *
   * @param statementLines List&lt;StatementLineResponse&gt;
   * @return StatementResponse
   */
  public StatementResponse statementLines(List<StatementLineResponse> statementLines) {
    this.statementLines = statementLines;
    return this;
  }

  /**
   * List of statement lines
   *
   * @param statementLinesItem StatementLineResponse
   * @return StatementResponse
   */
  public StatementResponse addStatementLinesItem(StatementLineResponse statementLinesItem) {
    if (this.statementLines == null) {
      this.statementLines = new ArrayList<StatementLineResponse>();
    }
    this.statementLines.add(statementLinesItem);
    return this;
  }

  /**
   * List of statement lines
   *
   * @return statementLines
   */
  @ApiModelProperty(value = "List of statement lines")
  /**
   * List of statement lines
   *
   * @return statementLines List<StatementLineResponse>
   */
  public List<StatementLineResponse> getStatementLines() {
    return statementLines;
  }

  /**
   * List of statement lines
   *
   * @param statementLines List&lt;StatementLineResponse&gt;
   */
  public void setStatementLines(List<StatementLineResponse> statementLines) {
    this.statementLines = statementLines;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatementResponse statementResponse = (StatementResponse) o;
    return Objects.equals(this.statementId, statementResponse.statementId)
        && Objects.equals(this.startDate, statementResponse.startDate)
        && Objects.equals(this.endDate, statementResponse.endDate)
        && Objects.equals(this.importedDateTimeUtc, statementResponse.importedDateTimeUtc)
        && Objects.equals(this.importSource, statementResponse.importSource)
        && Objects.equals(this.startBalance, statementResponse.startBalance)
        && Objects.equals(this.endBalance, statementResponse.endBalance)
        && Objects.equals(this.statementLines, statementResponse.statementLines);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        statementId,
        startDate,
        endDate,
        importedDateTimeUtc,
        importSource,
        startBalance,
        endBalance,
        statementLines);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatementResponse {\n");
    sb.append("    statementId: ").append(toIndentedString(statementId)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    importedDateTimeUtc: ")
        .append(toIndentedString(importedDateTimeUtc))
        .append("\n");
    sb.append("    importSource: ").append(toIndentedString(importSource)).append("\n");
    sb.append("    startBalance: ").append(toIndentedString(startBalance)).append("\n");
    sb.append("    endBalance: ").append(toIndentedString(endBalance)).append("\n");
    sb.append("    statementLines: ").append(toIndentedString(statementLines)).append("\n");
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