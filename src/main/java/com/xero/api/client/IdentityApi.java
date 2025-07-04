/*
 * Xero OAuth 2 Identity Service API
 * These endpoints are related to managing authentication tokens and identity for Xero API
 *
 * The version of the OpenAPI document: 9.0.0
 * Contact: api@xero.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.xero.api.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.xero.api.ApiClient;
import com.xero.api.XeroApiExceptionHandler;
import com.xero.models.identity.Connection;
import jakarta.ws.rs.core.UriBuilder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** IdentityApi has methods for interacting with all endpoints in the API set */
public class IdentityApi {
  private ApiClient apiClient;
  private static IdentityApi instance = null;
  private String userAgent = "Default";
  private String version = "12.0.0";
  static final Logger logger = LoggerFactory.getLogger(IdentityApi.class);

  /** IdentityApi */
  public IdentityApi() {
    this(new ApiClient());
  }

  /**
   * IdentityApi getInstance
   *
   * @param apiClient ApiClient pass into the new instance of this class
   * @return instance of this class
   */
  public static IdentityApi getInstance(ApiClient apiClient) {
    if (instance == null) {
      instance = new IdentityApi(apiClient);
    }
    return instance;
  }

  /**
   * IdentityApi
   *
   * @param apiClient ApiClient pass into the new instance of this class
   */
  public IdentityApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * get ApiClient
   *
   * @return apiClient the current ApiClient
   */
  public ApiClient getApiClient() {
    return apiClient;
  }

  /**
   * set ApiClient
   *
   * @param apiClient ApiClient pass into the new instance of this class
   */
  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * set user agent
   *
   * @param userAgent string to override the user agent
   */
  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  /**
   * get user agent
   *
   * @return String of user agent
   */
  public String getUserAgent() {
    return this.userAgent + " [Xero-Java-" + this.version + "]";
  }

  /**
   * Deletes a connection for this user (i.e. disconnect a tenant) Override the base server url that
   * include version
   *
   * <p><b>204</b> - Success - connection has been deleted no content returned
   *
   * <p><b>404</b> - Resource not found
   *
   * @param id Unique identifier for retrieving single object
   * @param accessToken Authorization token for user set in header of each request
   * @throws IOException if an error occurs while attempting to invoke the API *
   */
  public void deleteConnection(String accessToken, UUID id) throws IOException {
    try {
      deleteConnectionForHttpResponse(accessToken, id);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteConnection -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
  }

  /**
   * Deletes a connection for this user (i.e. disconnect a tenant) Override the base server url that
   * include version
   *
   * <p><b>204</b> - Success - connection has been deleted no content returned
   *
   * <p><b>404</b> - Resource not found
   *
   * @param id Unique identifier for retrieving single object
   * @param accessToken Authorization token for user set in header of each request
   * @return HttpResponse
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HttpResponse deleteConnectionForHttpResponse(String accessToken, UUID id)
      throws IOException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'id' when calling deleteConnection");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteConnection");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept("");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("id", id);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Connections/{id}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Retrieves the connections for this user Override the base server url that include version
   *
   * <p><b>200</b> - Success - return response of type Connections array with 0 to n Connection
   *
   * @param authEventId Filter by authEventId
   * @param accessToken Authorization token for user set in header of each request
   * @return List&lt;Connection&gt;
   * @throws IOException if an error occurs while attempting to invoke the API *
   */
  public List<Connection> getConnections(String accessToken, UUID authEventId) throws IOException {
    try {
      TypeReference<List<Connection>> typeRef = new TypeReference<List<Connection>>() {};
      HttpResponse response = getConnectionsForHttpResponse(accessToken, authEventId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getConnections -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  /**
   * Retrieves the connections for this user Override the base server url that include version
   *
   * <p><b>200</b> - Success - return response of type Connections array with 0 to n Connection
   *
   * @param authEventId Filter by authEventId
   * @param accessToken Authorization token for user set in header of each request
   * @return HttpResponse
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HttpResponse getConnectionsForHttpResponse(String accessToken, UUID authEventId)
      throws IOException {

    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getConnections");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Connections");
    if (authEventId != null) {
      String key = "authEventId";
      Object value = authEventId;
      if (value instanceof Collection) {
        List valueList = new ArrayList<>((Collection) value);
        if (!valueList.isEmpty() && valueList.get(0) instanceof UUID) {
          List<String> list = new ArrayList<String>();
          for (int i = 0; i < valueList.size(); i++) {
            list.add(valueList.get(i).toString());
          }
          uriBuilder = uriBuilder.queryParam(key, String.join(",", list));
        } else {
          uriBuilder = uriBuilder.queryParam(key, String.join(",", valueList));
        }
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * convert intput to byte array
   *
   * @param is InputStream the server status code returned
   * @return byteArrayInputStream a ByteArrayInputStream
   * @throws IOException for failed or interrupted I/O operations
   */
  public ByteArrayInputStream convertInputToByteArray(InputStream is) throws IOException {
    byte[] bytes = IOUtils.toByteArray(is);
    try {
      // Process the input stream..
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
      return byteArrayInputStream;
    } finally {
      is.close();
    }
  }
}
