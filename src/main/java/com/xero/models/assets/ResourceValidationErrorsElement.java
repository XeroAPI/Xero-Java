/*
 * Xero Assets API
 * The Assets API exposes fixed asset related functions of the Xero Accounting application and can be used for a variety of purposes such as creating assets, retrieving asset valuations etc.
 *
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.xero.models.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xero.api.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;

/** ResourceValidationErrorsElement */
public class ResourceValidationErrorsElement {
  StringUtil util = new StringUtil();

  @JsonProperty("resourceName")
  private String resourceName;

  @JsonProperty("localisedMessage")
  private String localisedMessage;

  @JsonProperty("type")
  private String type;

  @JsonProperty("title")
  private String title;

  @JsonProperty("detail")
  private String detail;
  /**
   * The field name of the erroneous field
   *
   * @param resourceName String
   * @return ResourceValidationErrorsElement
   */
  public ResourceValidationErrorsElement resourceName(String resourceName) {
    this.resourceName = resourceName;
    return this;
  }

  /**
   * The field name of the erroneous field
   *
   * @return resourceName
   */
  @ApiModelProperty(value = "The field name of the erroneous field")
  /**
   * The field name of the erroneous field
   *
   * @return resourceName String
   */
  public String getResourceName() {
    return resourceName;
  }

  /**
   * The field name of the erroneous field
   *
   * @param resourceName String
   */
  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  /**
   * Explanation of the resource validation error
   *
   * @param localisedMessage String
   * @return ResourceValidationErrorsElement
   */
  public ResourceValidationErrorsElement localisedMessage(String localisedMessage) {
    this.localisedMessage = localisedMessage;
    return this;
  }

  /**
   * Explanation of the resource validation error
   *
   * @return localisedMessage
   */
  @ApiModelProperty(value = "Explanation of the resource validation error")
  /**
   * Explanation of the resource validation error
   *
   * @return localisedMessage String
   */
  public String getLocalisedMessage() {
    return localisedMessage;
  }

  /**
   * Explanation of the resource validation error
   *
   * @param localisedMessage String
   */
  public void setLocalisedMessage(String localisedMessage) {
    this.localisedMessage = localisedMessage;
  }

  /**
   * Internal type of the resource error message
   *
   * @param type String
   * @return ResourceValidationErrorsElement
   */
  public ResourceValidationErrorsElement type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Internal type of the resource error message
   *
   * @return type
   */
  @ApiModelProperty(value = "Internal type of the resource error message")
  /**
   * Internal type of the resource error message
   *
   * @return type String
   */
  public String getType() {
    return type;
  }

  /**
   * Internal type of the resource error message
   *
   * @param type String
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Title of the resource validation error
   *
   * @param title String
   * @return ResourceValidationErrorsElement
   */
  public ResourceValidationErrorsElement title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Title of the resource validation error
   *
   * @return title
   */
  @ApiModelProperty(value = "Title of the resource validation error")
  /**
   * Title of the resource validation error
   *
   * @return title String
   */
  public String getTitle() {
    return title;
  }

  /**
   * Title of the resource validation error
   *
   * @param title String
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Detail of the resource validation error
   *
   * @param detail String
   * @return ResourceValidationErrorsElement
   */
  public ResourceValidationErrorsElement detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * Detail of the resource validation error
   *
   * @return detail
   */
  @ApiModelProperty(value = "Detail of the resource validation error")
  /**
   * Detail of the resource validation error
   *
   * @return detail String
   */
  public String getDetail() {
    return detail;
  }

  /**
   * Detail of the resource validation error
   *
   * @param detail String
   */
  public void setDetail(String detail) {
    this.detail = detail;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResourceValidationErrorsElement resourceValidationErrorsElement =
        (ResourceValidationErrorsElement) o;
    return Objects.equals(this.resourceName, resourceValidationErrorsElement.resourceName)
        && Objects.equals(this.localisedMessage, resourceValidationErrorsElement.localisedMessage)
        && Objects.equals(this.type, resourceValidationErrorsElement.type)
        && Objects.equals(this.title, resourceValidationErrorsElement.title)
        && Objects.equals(this.detail, resourceValidationErrorsElement.detail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resourceName, localisedMessage, type, title, detail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResourceValidationErrorsElement {\n");
    sb.append("    resourceName: ").append(toIndentedString(resourceName)).append("\n");
    sb.append("    localisedMessage: ").append(toIndentedString(localisedMessage)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
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
