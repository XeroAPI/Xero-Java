package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.identity.Connection;
import java.util.UUID;
import com.xero.api.XeroApiExceptionHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.apache.commons.io.IOUtils;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class IdentityApi {
  private ApiClient apiClient;
  private static IdentityApi instance = null;
  private String userAgent = "Default";
  private String version = "4.1.1";
  static final Logger logger = LoggerFactory.getLogger(IdentityApi.class);

  public IdentityApi() {
    this(new ApiClient());
  }

  public static IdentityApi getInstance(ApiClient apiClient) {
    if (instance == null) {
      instance = new IdentityApi(apiClient);
    }
    return instance;
  }

  public IdentityApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public String getUserAgent() {
    return this.userAgent + " [Xero-Java-" + this.version + "]";
  }

  /**
   * Allows you to delete a connection for this user (i.e. disconnect a tenant) Override the base
   * server url that include version
   *
   * <p><b>204</b> - Success - connection has been deleted no content returned
   *
   * <p><b>404</b> - Resource not found
   *
   * @param id Unique identifier for retrieving single object
   * @param accessToken Authorization token for user set in header of each request
   * @throws IOException if an error occurs while attempting to invoke the API
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

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/connections/{id}");
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
   * Allows you to retrieve the connections for this user Override the base server url that include
   * version
   *
   * <p><b>200</b> - Success - return response of type Connections array with 0 to n Connection
   *
   * @param authEventId Filter by authEventId
   * @param accessToken Authorization token for user set in header of each request
   * @return List&lt;Connection&gt;
   * @throws IOException if an error occurs while attempting to invoke the API
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

  public HttpResponse getConnectionsForHttpResponse(String accessToken, UUID authEventId)
      throws IOException {

    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getConnections");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/connections");
    if (authEventId != null) {
      String key = "authEventId";
      Object value = authEventId;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
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
