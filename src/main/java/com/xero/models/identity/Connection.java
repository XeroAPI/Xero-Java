/*
 * Xero OAuth 2 Identity Service API
 * These endpoints are related to managing authentication tokens and identity for Xero API
 *
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.xero.models.identity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xero.api.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import java.util.UUID;
import org.threeten.bp.LocalDateTime;

/** Connection */
public class Connection {
  StringUtil util = new StringUtil();

  @JsonProperty("id")
  private UUID id;

  @JsonProperty("tenantId")
  private UUID tenantId;

  @JsonProperty("authEventId")
  private UUID authEventId;

  @JsonProperty("tenantType")
  private String tenantType;

  @JsonProperty("tenantName")
  private String tenantName;

  @JsonProperty("createdDateUtc")
  private LocalDateTime createdDateUtc;

  @JsonProperty("updatedDateUtc")
  private LocalDateTime updatedDateUtc;
  /**
   * Xero identifier
   *
   * @param id UUID
   * @return Connection
   */
  public Connection id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Xero identifier
   *
   * @return id
   */
  @ApiModelProperty(value = "Xero identifier")
  /**
   * Xero identifier
   *
   * @return id UUID
   */
  public UUID getId() {
    return id;
  }

  /**
   * Xero identifier
   *
   * @param id UUID
   */
  public void setId(UUID id) {
    this.id = id;
  }

  /**
   * Xero identifier of organisation
   *
   * @param tenantId UUID
   * @return Connection
   */
  public Connection tenantId(UUID tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  /**
   * Xero identifier of organisation
   *
   * @return tenantId
   */
  @ApiModelProperty(value = "Xero identifier of organisation")
  /**
   * Xero identifier of organisation
   *
   * @return tenantId UUID
   */
  public UUID getTenantId() {
    return tenantId;
  }

  /**
   * Xero identifier of organisation
   *
   * @param tenantId UUID
   */
  public void setTenantId(UUID tenantId) {
    this.tenantId = tenantId;
  }

  /**
   * Identifier shared across connections authorised at the same time
   *
   * @param authEventId UUID
   * @return Connection
   */
  public Connection authEventId(UUID authEventId) {
    this.authEventId = authEventId;
    return this;
  }

  /**
   * Identifier shared across connections authorised at the same time
   *
   * @return authEventId
   */
  @ApiModelProperty(value = "Identifier shared across connections authorised at the same time")
  /**
   * Identifier shared across connections authorised at the same time
   *
   * @return authEventId UUID
   */
  public UUID getAuthEventId() {
    return authEventId;
  }

  /**
   * Identifier shared across connections authorised at the same time
   *
   * @param authEventId UUID
   */
  public void setAuthEventId(UUID authEventId) {
    this.authEventId = authEventId;
  }

  /**
   * Xero tenant type (i.e. ORGANISATION, PRACTICE)
   *
   * @param tenantType String
   * @return Connection
   */
  public Connection tenantType(String tenantType) {
    this.tenantType = tenantType;
    return this;
  }

  /**
   * Xero tenant type (i.e. ORGANISATION, PRACTICE)
   *
   * @return tenantType
   */
  @ApiModelProperty(value = "Xero tenant type (i.e. ORGANISATION, PRACTICE)")
  /**
   * Xero tenant type (i.e. ORGANISATION, PRACTICE)
   *
   * @return tenantType String
   */
  public String getTenantType() {
    return tenantType;
  }

  /**
   * Xero tenant type (i.e. ORGANISATION, PRACTICE)
   *
   * @param tenantType String
   */
  public void setTenantType(String tenantType) {
    this.tenantType = tenantType;
  }

  /**
   * Xero tenant name
   *
   * @param tenantName String
   * @return Connection
   */
  public Connection tenantName(String tenantName) {
    this.tenantName = tenantName;
    return this;
  }

  /**
   * Xero tenant name
   *
   * @return tenantName
   */
  @ApiModelProperty(value = "Xero tenant name")
  /**
   * Xero tenant name
   *
   * @return tenantName String
   */
  public String getTenantName() {
    return tenantName;
  }

  /**
   * Xero tenant name
   *
   * @param tenantName String
   */
  public void setTenantName(String tenantName) {
    this.tenantName = tenantName;
  }

  /**
   * The date when the user connected this tenant to your app
   *
   * @param createdDateUtc LocalDateTime
   * @return Connection
   */
  public Connection createdDateUtc(LocalDateTime createdDateUtc) {
    this.createdDateUtc = createdDateUtc;
    return this;
  }

  /**
   * The date when the user connected this tenant to your app
   *
   * @return createdDateUtc
   */
  @ApiModelProperty(value = "The date when the user connected this tenant to your app")
  /**
   * The date when the user connected this tenant to your app
   *
   * @return createdDateUtc LocalDateTime
   */
  public LocalDateTime getCreatedDateUtc() {
    return createdDateUtc;
  }

  /**
   * The date when the user connected this tenant to your app
   *
   * @param createdDateUtc LocalDateTime
   */
  public void setCreatedDateUtc(LocalDateTime createdDateUtc) {
    this.createdDateUtc = createdDateUtc;
  }

  /**
   * The date when the user most recently connected this tenant to your app. May differ to the
   * created date if the user has disconnected and subsequently reconnected this tenant to your app.
   *
   * @param updatedDateUtc LocalDateTime
   * @return Connection
   */
  public Connection updatedDateUtc(LocalDateTime updatedDateUtc) {
    this.updatedDateUtc = updatedDateUtc;
    return this;
  }

  /**
   * The date when the user most recently connected this tenant to your app. May differ to the
   * created date if the user has disconnected and subsequently reconnected this tenant to your app.
   *
   * @return updatedDateUtc
   */
  @ApiModelProperty(
      value =
          "The date when the user most recently connected this tenant to your app. May differ to"
              + " the created date if the user has disconnected and subsequently reconnected this"
              + " tenant to your app.")
  /**
   * The date when the user most recently connected this tenant to your app. May differ to the
   * created date if the user has disconnected and subsequently reconnected this tenant to your app.
   *
   * @return updatedDateUtc LocalDateTime
   */
  public LocalDateTime getUpdatedDateUtc() {
    return updatedDateUtc;
  }

  /**
   * The date when the user most recently connected this tenant to your app. May differ to the
   * created date if the user has disconnected and subsequently reconnected this tenant to your app.
   *
   * @param updatedDateUtc LocalDateTime
   */
  public void setUpdatedDateUtc(LocalDateTime updatedDateUtc) {
    this.updatedDateUtc = updatedDateUtc;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Connection connection = (Connection) o;
    return Objects.equals(this.id, connection.id)
        && Objects.equals(this.tenantId, connection.tenantId)
        && Objects.equals(this.authEventId, connection.authEventId)
        && Objects.equals(this.tenantType, connection.tenantType)
        && Objects.equals(this.tenantName, connection.tenantName)
        && Objects.equals(this.createdDateUtc, connection.createdDateUtc)
        && Objects.equals(this.updatedDateUtc, connection.updatedDateUtc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, tenantId, authEventId, tenantType, tenantName, createdDateUtc, updatedDateUtc);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Connection {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    authEventId: ").append(toIndentedString(authEventId)).append("\n");
    sb.append("    tenantType: ").append(toIndentedString(tenantType)).append("\n");
    sb.append("    tenantName: ").append(toIndentedString(tenantName)).append("\n");
    sb.append("    createdDateUtc: ").append(toIndentedString(createdDateUtc)).append("\n");
    sb.append("    updatedDateUtc: ").append(toIndentedString(updatedDateUtc)).append("\n");
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
