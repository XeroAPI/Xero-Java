package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.bankfeeds.FeedConnection;
import com.xero.models.bankfeeds.FeedConnections;
import com.xero.models.bankfeeds.Statement;
import com.xero.models.bankfeeds.Statements;
import java.util.UUID;
import com.xero.api.XeroApiExceptionHandler;
import com.xero.models.bankfeeds.Statements;

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

import org.apache.commons.io.IOUtils;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class BankFeedsApi {
  private ApiClient apiClient;
  private static BankFeedsApi instance = null;
  private String userAgent = "Default";
  private String version = "4.1.1";
  static final Logger logger = LoggerFactory.getLogger(BankFeedsApi.class);

  public BankFeedsApi() {
    this(new ApiClient());
  }

  public static BankFeedsApi getInstance(ApiClient apiClient) {
    if (instance == null) {
      instance = new BankFeedsApi(apiClient);
    }
    return instance;
  }

  public BankFeedsApi(ApiClient apiClient) {
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
   * create one or more new feed connection By passing in the FeedConnections array object in the
   * body, you can create one or more new feed connections
   *
   * <p><b>201</b> - success new feed connection(s)response
   *
   * <p><b>400</b> - invalid input, object invalid
   *
   * <p><b>409</b> - failed to create new feed connection(s)response
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param feedConnections Feed Connection(s) array object in the body
   * @param accessToken Authorization token for user set in header of each request
   * @return FeedConnections
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public FeedConnections createFeedConnections(
      String accessToken, String xeroTenantId, FeedConnections feedConnections) throws IOException {
    try {
      TypeReference<FeedConnections> typeRef = new TypeReference<FeedConnections>() {};
      HttpResponse response =
          createFeedConnectionsForHttpResponse(accessToken, xeroTenantId, feedConnections);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createFeedConnections -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<FeedConnections> errorTypeRef = new TypeReference<FeedConnections>() {};
        FeedConnections bankFeedError =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError("FeedConnections", bankFeedError);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createFeedConnectionsForHttpResponse(
      String accessToken, String xeroTenantId, FeedConnections feedConnections) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createFeedConnections");
    } // verify the required parameter 'feedConnections' is set
    if (feedConnections == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'feedConnections' when calling createFeedConnections");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createFeedConnections");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/FeedConnections");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(feedConnections);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * <b>202</b> - Success returns Statements array of objects in response
   *
   * <p><b>400</b> - Statement failed validation
   *
   * <p><b>403</b> - Invalid application or feed connection
   *
   * <p><b>409</b> - Duplicate statement received
   *
   * <p><b>413</b> - Statement exceeds size limit
   *
   * <p><b>422</b> - Unprocessable Entity
   *
   * <p><b>500</b> - Intermittent Xero Error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param statements Statements array of objects in the body
   * @param accessToken Authorization token for user set in header of each request
   * @return Statements
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Statements createStatements(String accessToken, String xeroTenantId, Statements statements)
      throws IOException {
    try {
      TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
      HttpResponse response =
          createStatementsForHttpResponse(accessToken, xeroTenantId, statements);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createStatements -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<Statements> errorTypeRef = new TypeReference<Statements>() {};
        Statements bankFeedError =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError("Statements", bankFeedError);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createStatementsForHttpResponse(
      String accessToken, String xeroTenantId, Statements statements) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createStatements");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createStatements");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/jsonapplication/problem+json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Statements");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(statements);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Delete an exsiting feed connection By passing in FeedConnections array object in the body, you
   * can delete a feed connection.
   *
   * <p><b>202</b> - Success response for deleted feed connection
   *
   * <p><b>400</b> - bad input parameter
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param feedConnections Feed Connections array object in the body
   * @param accessToken Authorization token for user set in header of each request
   * @return FeedConnections
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public FeedConnections deleteFeedConnections(
      String accessToken, String xeroTenantId, FeedConnections feedConnections) throws IOException {
    try {
      TypeReference<FeedConnections> typeRef = new TypeReference<FeedConnections>() {};
      HttpResponse response =
          deleteFeedConnectionsForHttpResponse(accessToken, xeroTenantId, feedConnections);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteFeedConnections -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse deleteFeedConnectionsForHttpResponse(
      String accessToken, String xeroTenantId, FeedConnections feedConnections) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteFeedConnections");
    } // verify the required parameter 'feedConnections' is set
    if (feedConnections == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'feedConnections' when calling deleteFeedConnections");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteFeedConnections");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/FeedConnections/DeleteRequests");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(feedConnections);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Retrive single feed connection based on unique id provided By passing in a FeedConnection Id
   * options, you can search for matching feed connections
   *
   * <p><b>200</b> - success returns a FeedConnection object matching the id in response
   *
   * <p><b>400</b> - bad input parameter
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param id Unique identifier for retrieving single object
   * @param accessToken Authorization token for user set in header of each request
   * @return FeedConnection
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public FeedConnection getFeedConnection(String accessToken, String xeroTenantId, UUID id)
      throws IOException {
    try {
      TypeReference<FeedConnection> typeRef = new TypeReference<FeedConnection>() {};
      HttpResponse response = getFeedConnectionForHttpResponse(accessToken, xeroTenantId, id);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getFeedConnection -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getFeedConnectionForHttpResponse(
      String accessToken, String xeroTenantId, UUID id) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getFeedConnection");
    } // verify the required parameter 'id' is set
    if (id == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'id' when calling getFeedConnection");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getFeedConnection");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("id", id);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/FeedConnections/{id}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
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
   * searches feed connections By passing in the appropriate options, you can search for available
   * feed connections in the system.
   *
   * <p><b>201</b> - search results matching criteria returned with pagination and items array
   *
   * <p><b>400</b> - validation error response
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page Page number which specifies the set of records to retrieve. By default the number
   *     of the records per set is 10. Example -
   *     https://api.xero.com/bankfeeds.xro/1.0/FeedConnections?page&#x3D;1 to get the second set of
   *     the records. When page value is not a number or a negative number, by default, the first
   *     set of records is returned.
   * @param pageSize Page size which specifies how many records per page will be returned (default
   *     10). Example - https://api.xero.com/bankfeeds.xro/1.0/FeedConnections?pageSize&#x3D;100 to
   *     specify page size of 100.
   * @param accessToken Authorization token for user set in header of each request
   * @return FeedConnections
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public FeedConnections getFeedConnections(
      String accessToken, String xeroTenantId, Integer page, Integer pageSize) throws IOException {
    try {
      TypeReference<FeedConnections> typeRef = new TypeReference<FeedConnections>() {};
      HttpResponse response =
          getFeedConnectionsForHttpResponse(accessToken, xeroTenantId, page, pageSize);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getFeedConnections -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getFeedConnectionsForHttpResponse(
      String accessToken, String xeroTenantId, Integer page, Integer pageSize) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getFeedConnections");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getFeedConnections");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/FeedConnections");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (pageSize != null) {
      String key = "pageSize";
      Object value = pageSize;
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

  /**
   * Retrive single statement based on unique id provided By passing in a statement id, you can
   * search for matching statements
   *
   * <p><b>200</b> - search results matching id for single statement
   *
   * <p><b>404</b> - Statement not found
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param statementId statement id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return Statement
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Statement getStatement(String accessToken, String xeroTenantId, UUID statementId)
      throws IOException {
    try {
      TypeReference<Statement> typeRef = new TypeReference<Statement>() {};
      HttpResponse response = getStatementForHttpResponse(accessToken, xeroTenantId, statementId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getStatement -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getStatementForHttpResponse(
      String accessToken, String xeroTenantId, UUID statementId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getStatement");
    } // verify the required parameter 'statementId' is set
    if (statementId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'statementId' when calling getStatement");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getStatement");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("statementId", statementId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Statements/{statementId}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
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
   * Retrive all statements based on unique search criteria By passing in parameters, you can search
   * for matching statements
   *
   * <p><b>200</b> - success returns Statements array of objects response
   *
   * <p><b>400</b> - bad input parameter
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page unique id for single object
   * @param pageSize Page size which specifies how many records per page will be returned (default
   *     10). Example - https://api.xero.com/bankfeeds.xro/1.0/Statements?pageSize&#x3D;100 to
   *     specify page size of 100.
   * @param xeroApplicationId The xeroApplicationId parameter
   * @param xeroUserId The xeroUserId parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return Statements
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Statements getStatements(
      String accessToken,
      String xeroTenantId,
      Integer page,
      Integer pageSize,
      String xeroApplicationId,
      String xeroUserId)
      throws IOException {
    try {
      TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
      HttpResponse response =
          getStatementsForHttpResponse(
              accessToken, xeroTenantId, page, pageSize, xeroApplicationId, xeroUserId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getStatements -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getStatementsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      Integer page,
      Integer pageSize,
      String xeroApplicationId,
      String xeroUserId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getStatements");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getStatements");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.set("Xero-Application-Id", xeroApplicationId);
    headers.set("Xero-User-Id", xeroUserId);
    headers.setAccept("application/jsonapplication/problem+json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Statements");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (pageSize != null) {
      String key = "pageSize";
      Object value = pageSize;
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
