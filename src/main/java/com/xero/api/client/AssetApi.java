package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.assets.Asset;
import com.xero.models.assets.AssetStatusQueryParam;
import com.xero.models.assets.AssetType;
import com.xero.models.assets.Assets;
import com.xero.models.assets.Setting;
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

public class AssetApi {
  private ApiClient apiClient;
  private static AssetApi instance = null;
  private String userAgent = "Default";
  private String version = "4.1.1";
  static final Logger logger = LoggerFactory.getLogger(AssetApi.class);

  public AssetApi() {
    this(new ApiClient());
  }

  public static AssetApi getInstance(ApiClient apiClient) {
    if (instance == null) {
      instance = new AssetApi(apiClient);
    }
    return instance;
  }

  public AssetApi(ApiClient apiClient) {
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
   * adds a fixed asset Adds an asset to the system
   *
   * <p><b>200</b> - return single object - create new asset
   *
   * <p><b>400</b> - invalid input, object invalid
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param asset Fixed asset you are creating
   * @param accessToken Authorization token for user set in header of each request
   * @return Asset
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Asset createAsset(String accessToken, String xeroTenantId, Asset asset)
      throws IOException {
    try {
      TypeReference<Asset> typeRef = new TypeReference<Asset>() {};
      HttpResponse response = createAssetForHttpResponse(accessToken, xeroTenantId, asset);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createAsset -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.assets.Error> errorTypeRef =
            new TypeReference<com.xero.models.assets.Error>() {};
        com.xero.models.assets.Error assetError =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError("Asset", assetError);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createAssetForHttpResponse(
      String accessToken, String xeroTenantId, Asset asset) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createAsset");
    } // verify the required parameter 'asset' is set
    if (asset == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'asset' when calling createAsset");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createAsset");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Assets");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(asset);

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
   * adds a fixed asset type Adds an fixed asset type to the system
   *
   * <p><b>200</b> - results single object - created fixed type
   *
   * <p><b>400</b> - invalid input, object invalid
   *
   * <p><b>409</b> - a type already exists
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param assetType Asset type to add
   * @param accessToken Authorization token for user set in header of each request
   * @return AssetType
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public AssetType createAssetType(String accessToken, String xeroTenantId, AssetType assetType)
      throws IOException {
    try {
      TypeReference<AssetType> typeRef = new TypeReference<AssetType>() {};
      HttpResponse response = createAssetTypeForHttpResponse(accessToken, xeroTenantId, assetType);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createAssetType -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.assets.Error> errorTypeRef =
            new TypeReference<com.xero.models.assets.Error>() {};
        com.xero.models.assets.Error assetError =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError("AssetType", assetError);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createAssetTypeForHttpResponse(
      String accessToken, String xeroTenantId, AssetType assetType) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createAssetType");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createAssetType");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/AssetTypes");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(assetType);

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
   * retrieves fixed asset by id By passing in the appropriate asset id, you can search for a
   * specific fixed asset in the system
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - bad input parameter
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param id fixed asset id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return Asset
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Asset getAssetById(String accessToken, String xeroTenantId, UUID id) throws IOException {
    try {
      TypeReference<Asset> typeRef = new TypeReference<Asset>() {};
      HttpResponse response = getAssetByIdForHttpResponse(accessToken, xeroTenantId, id);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getAssetById -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getAssetByIdForHttpResponse(String accessToken, String xeroTenantId, UUID id)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getAssetById");
    } // verify the required parameter 'id' is set
    if (id == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'id' when calling getAssetById");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getAssetById");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("id", id);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Assets/{id}");
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
   * searches fixed asset settings By passing in the appropriate options, you can search for
   * available fixed asset types in the system
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - bad input parameter
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accessToken Authorization token for user set in header of each request
   * @return Setting
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Setting getAssetSettings(String accessToken, String xeroTenantId) throws IOException {
    try {
      TypeReference<Setting> typeRef = new TypeReference<Setting>() {};
      HttpResponse response = getAssetSettingsForHttpResponse(accessToken, xeroTenantId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getAssetSettings -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getAssetSettingsForHttpResponse(String accessToken, String xeroTenantId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getAssetSettings");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getAssetSettings");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Settings");
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
   * searches fixed asset types By passing in the appropriate options, you can search for available
   * fixed asset types in the system
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - bad input parameter
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accessToken Authorization token for user set in header of each request
   * @return List&lt;AssetType&gt;
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public List<AssetType> getAssetTypes(String accessToken, String xeroTenantId) throws IOException {
    try {
      TypeReference<List<AssetType>> typeRef = new TypeReference<List<AssetType>>() {};
      HttpResponse response = getAssetTypesForHttpResponse(accessToken, xeroTenantId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getAssetTypes -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getAssetTypesForHttpResponse(String accessToken, String xeroTenantId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getAssetTypes");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getAssetTypes");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/AssetTypes");
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
   * searches fixed asset By passing in the appropriate options, you can search for available fixed
   * asset in the system
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - bad input parameter
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param status Required when retrieving a collection of assets. See Asset Status Codes
   * @param page Results are paged. This specifies which page of the results to return. The default
   *     page is 1.
   * @param pageSize The number of records returned per page. By default the number of records
   *     returned is 10.
   * @param orderBy Requests can be ordered by AssetType, AssetName, AssetNumber, PurchaseDate and
   *     PurchasePrice. If the asset status is DISPOSED it also allows DisposalDate and
   *     DisposalPrice.
   * @param sortDirection ASC or DESC
   * @param filterBy A string that can be used to filter the list to only return assets containing
   *     the text. Checks it against the AssetName, AssetNumber, Description and AssetTypeName
   *     fields.
   * @param accessToken Authorization token for user set in header of each request
   * @return Assets
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Assets getAssets(
      String accessToken,
      String xeroTenantId,
      AssetStatusQueryParam status,
      Integer page,
      Integer pageSize,
      String orderBy,
      String sortDirection,
      String filterBy)
      throws IOException {
    try {
      TypeReference<Assets> typeRef = new TypeReference<Assets>() {};
      HttpResponse response =
          getAssetsForHttpResponse(
              accessToken, xeroTenantId, status, page, pageSize, orderBy, sortDirection, filterBy);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getAssets -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getAssetsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      AssetStatusQueryParam status,
      Integer page,
      Integer pageSize,
      String orderBy,
      String sortDirection,
      String filterBy)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getAssets");
    } // verify the required parameter 'status' is set
    if (status == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'status' when calling getAssets");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getAssets");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Assets");
    if (status != null) {
      String key = "status";
      Object value = status;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
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
    if (orderBy != null) {
      String key = "orderBy";
      Object value = orderBy;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (sortDirection != null) {
      String key = "sortDirection";
      Object value = sortDirection;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (filterBy != null) {
      String key = "filterBy";
      Object value = filterBy;
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
