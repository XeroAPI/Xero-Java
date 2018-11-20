package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.assets.Asset;
import com.xero.models.assets.AssetType;
import com.xero.models.assets.Assets;
import com.xero.models.assets.Setting;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xero.api.exception.XeroExceptionHandler;
import com.xero.model.*;
import com.xero.api.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.threeten.bp.OffsetDateTime;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.ws.rs.core.UriBuilder;

public class AssetApi {
    private ApiClient apiClient;
    private XeroExceptionHandler xeroExceptionHandler;
    private Config config;
    private SignerFactory signerFactory;
    private String token = null;
    private String tokenSecret = null;
    final static Logger logger = LogManager.getLogger(XeroClient.class);
    protected static final DateFormat utcFormatter;

    static {
        utcFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    protected static final Pattern MESSAGE_PATTERN = Pattern.compile("<Message>(.*)</Message>");
    protected final ObjectFactory objFactory = new ObjectFactory();

    
    public AssetApi(Config config) {
        this(config, new ConfigBasedSignerFactory(config));
        this.xeroExceptionHandler = new XeroExceptionHandler();
        this.apiClient = apiClient;
    }

    public AssetApi(Config config, SignerFactory signerFactory) {
        this.config = config;
        this.signerFactory = signerFactory;
        this.xeroExceptionHandler = new XeroExceptionHandler();
    }

    public AssetApi(ApiClient apiClient) {
        this(JsonConfig.getInstance());
        this.xeroExceptionHandler = new XeroExceptionHandler();
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void setOAuthToken(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    
    protected String DATA(String url, String body, Map<String, String> params, String method) throws IOException {
        return this.DATA(url,body,params,method,null, "application/json");
    }

    protected String DATA(String url, String body, Map<String, String> params, String method, OffsetDateTime ifModifiedSince) throws IOException {
        return this.DATA(url,body,params,method,ifModifiedSince,"application/json");
    }

    protected String DATA(String url, String body, Map<String, String> params, String method, String contentType) throws IOException {
        return this.DATA(url,body,params,method,null,contentType);
    }

    protected String DATA(String url, String body, Map<String, String> params, String method, OffsetDateTime ifModifiedSince, String contentType) throws IOException {
        
        OAuthRequestResource req = new OAuthRequestResource(
            config, 
            signerFactory, 
            url, 
            method, 
            body, 
            params,
            contentType,
            "application/json");
        
        req.setToken(token);
        req.setTokenSecret(tokenSecret);
        
        if (ifModifiedSince != null) {
            req.setIfModifiedSince(ifModifiedSince);
        }

        try {
            Map<String, String>  resp = req.execute();
            Object r = resp.get("content");
            return r.toString();
        } catch (IOException ioe) {
             throw xeroExceptionHandler.convertException(ioe);
        }
    }

    protected String DATA(String url, String body, Map<String, String> params, String method, String xeroApplicationId, String xeroTenantId, String xeroUserId) throws IOException {
        
        OAuthRequestResource req = new OAuthRequestResource(
            config, 
            signerFactory, 
            url, 
            method, 
            body, 
            params,
            null,
            "application/json");
        
        req.setToken(token);
        req.setTokenSecret(tokenSecret);
        
        //if (ifModifiedSince != null) {
        //    req.setIfModifiedSince(ifModifiedSince);
        //}

        try {
            Map<String, String>  resp = req.execute();
            Object r = resp.get("content");
            return r.toString();
        } catch (IOException ioe) {
             throw xeroExceptionHandler.convertException(ioe);
        }
    }

   
    protected ByteArrayInputStream FILE(String url, String body, Map<String, String> params, String method) throws IOException {
       return this.FILE(url,body,params,method,"application/octet-stream");
    }

    protected ByteArrayInputStream FILE(String url, String body, Map<String, String> params, String method, String accept) throws IOException {
        
        OAuthRequestResource req = new OAuthRequestResource(
            config, 
            signerFactory, 
            url, 
            method, 
            body, 
            params,
            accept,
            "application/json");
        
        req.setToken(token);
        req.setTokenSecret(tokenSecret);
        
        try {
            ByteArrayInputStream resp = req.executefile();
            return resp;
        } catch (IOException ioe) {
             throw xeroExceptionHandler.convertException(ioe);
        }
    }

    protected String FILE(String url, String body, Map<String, String> params, String method, byte[] byteBody) throws IOException {
        
        OAuthRequestResource req = new OAuthRequestResource(
            config, 
            signerFactory, 
            url, 
            method,
            "application/octet-stream",
            byteBody, 
            params,
            "application/json");
        
        req.setToken(token);
        req.setTokenSecret(tokenSecret);
       
        try {
            Map<String, String>  resp = req.execute();
            Object r = resp.get("content");
            return r.toString();
        } catch (IOException ioe) {
             throw xeroExceptionHandler.convertException(ioe);
        }
    }

  /**
    * adds a fixed asset
    * Adds an asset to the system
    * <p><b>200</b> - return single object - create new asset
    * <p><b>400</b> - invalid input, object invalid
    * @param asset Fixed asset to add
    * @return Asset
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Asset createAsset(Asset asset) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Assets";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(asset);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Asset> typeRef = new TypeReference<Asset>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * adds a fixed asset type
    * Adds an fixed asset type to the system
    * <p><b>200</b> - results single object -  created fixed type
    * <p><b>400</b> - invalid input, object invalid
    * <p><b>409</b> - a type already exists
    * @param assetType Asset type to add
    * @return AssetType
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public AssetType createAssetType(AssetType assetType) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/AssetTypes";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(assetType);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<AssetType> typeRef = new TypeReference<AssetType>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * retrieves fixed asset by id
    * By passing in the appropriate asset id, you can search for a specific fixed asset in the system 
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter
    * @param id fixed asset id for single object
    * @return Asset
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Asset getAssetById(UUID id) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Assets/{id}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Assets/{id}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("id", id.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET");
            
            TypeReference<Asset> typeRef = new TypeReference<Asset>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * searches fixed asset settings
    * By passing in the appropriate options, you can search for available fixed asset types in the system
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter
    * @return Setting
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Setting getAssetSettings() throws IOException {
        // Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Settings";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET");
            
            TypeReference<Setting> typeRef = new TypeReference<Setting>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * searches fixed asset types
    * By passing in the appropriate options, you can search for available fixed asset types in the system
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter
    * @return List&lt;AssetType&gt;
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public List<AssetType> getAssetTypes() throws IOException {
        // Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/AssetTypes";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET");
            
            TypeReference<List<AssetType>> typeRef = new TypeReference<List<AssetType>>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * searches fixed asset
    * By passing in the appropriate options, you can search for available fixed asset in the system
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter
    * @param status Required when retrieving a collection of assets. See Asset Status Codes
    * @param page Results are paged. This specifies which page of the results to return. The default page is 1.
    * @param pageSize The number of records returned per page. By default the number of records returned is 10.
    * @param orderBy Requests can be ordered by AssetType, AssetName, AssetNumber, PurchaseDate and PurchasePrice. If the asset status is DISPOSED it also allows DisposalDate and DisposalPrice.
    * @param sortDirection ASC or DESC
    * @param filterBy A string that can be used to filter the list to only return assets containing the text. Checks it against the AssetName, AssetNumber, Description and AssetTypeName fields.
    * @return Assets
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Assets getAssets(String status, Integer page, Integer pageSize, String orderBy, String sortDirection, String filterBy) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Assets";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            params = new HashMap<>();
            if (status != null) {
                addToMapIfNotNull(params, "status", status);
            }if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }if (pageSize != null) {
                addToMapIfNotNull(params, "pageSize", pageSize);
            }if (orderBy != null) {
                addToMapIfNotNull(params, "orderBy", orderBy);
            }if (sortDirection != null) {
                addToMapIfNotNull(params, "sortDirection", sortDirection);
            }if (filterBy != null) {
                addToMapIfNotNull(params, "filterBy", filterBy);
            }
            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET");
            
            TypeReference<Assets> typeRef = new TypeReference<Assets>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }

    protected void addToMapIfNotNull(Map<String, String> map, String key, Object value) {
        if (value != null) {
            map.put(key, value.toString());
        }
    }

}

