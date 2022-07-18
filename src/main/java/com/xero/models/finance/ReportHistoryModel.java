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
import java.util.Objects;
import org.threeten.bp.OffsetDateTime;

/** ReportHistoryModel */
public class ReportHistoryModel {
  StringUtil util = new StringUtil();

  @JsonProperty("reportName")
  private String reportName;

  @JsonProperty("reportDateText")
  private String reportDateText;

  @JsonProperty("publishedDateUtc")
  private OffsetDateTime publishedDateUtc;
  /**
   * Report code or report title
   *
   * @param reportName String
   * @return ReportHistoryModel
   */
  public ReportHistoryModel reportName(String reportName) {
    this.reportName = reportName;
    return this;
  }

  /**
   * Report code or report title
   *
   * @return reportName
   */
  @ApiModelProperty(value = "Report code or report title")
  /**
   * Report code or report title
   *
   * @return reportName String
   */
  public String getReportName() {
    return reportName;
  }

  /**
   * Report code or report title
   *
   * @param reportName String
   */
  public void setReportName(String reportName) {
    this.reportName = reportName;
  }

  /**
   * The date or date range of the report
   *
   * @param reportDateText String
   * @return ReportHistoryModel
   */
  public ReportHistoryModel reportDateText(String reportDateText) {
    this.reportDateText = reportDateText;
    return this;
  }

  /**
   * The date or date range of the report
   *
   * @return reportDateText
   */
  @ApiModelProperty(value = "The date or date range of the report")
  /**
   * The date or date range of the report
   *
   * @return reportDateText String
   */
  public String getReportDateText() {
    return reportDateText;
  }

  /**
   * The date or date range of the report
   *
   * @param reportDateText String
   */
  public void setReportDateText(String reportDateText) {
    this.reportDateText = reportDateText;
  }

  /**
   * The system date time that the report was published
   *
   * @param publishedDateUtc OffsetDateTime
   * @return ReportHistoryModel
   */
  public ReportHistoryModel publishedDateUtc(OffsetDateTime publishedDateUtc) {
    this.publishedDateUtc = publishedDateUtc;
    return this;
  }

  /**
   * The system date time that the report was published
   *
   * @return publishedDateUtc
   */
  @ApiModelProperty(value = "The system date time that the report was published")
  /**
   * The system date time that the report was published
   *
   * @return publishedDateUtc OffsetDateTime
   */
  public OffsetDateTime getPublishedDateUtc() {
    return publishedDateUtc;
  }

  /**
   * The system date time that the report was published
   *
   * @param publishedDateUtc OffsetDateTime
   */
  public void setPublishedDateUtc(OffsetDateTime publishedDateUtc) {
    this.publishedDateUtc = publishedDateUtc;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReportHistoryModel reportHistoryModel = (ReportHistoryModel) o;
    return Objects.equals(this.reportName, reportHistoryModel.reportName)
        && Objects.equals(this.reportDateText, reportHistoryModel.reportDateText)
        && Objects.equals(this.publishedDateUtc, reportHistoryModel.publishedDateUtc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reportName, reportDateText, publishedDateUtc);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReportHistoryModel {\n");
    sb.append("    reportName: ").append(toIndentedString(reportName)).append("\n");
    sb.append("    reportDateText: ").append(toIndentedString(reportDateText)).append("\n");
    sb.append("    publishedDateUtc: ").append(toIndentedString(publishedDateUtc)).append("\n");
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