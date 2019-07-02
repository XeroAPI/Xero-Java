package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.assets.Asset;
import com.xero.models.assets.AssetType;
import com.xero.models.assets.Assets;
import com.xero.models.assets.Setting;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.FileContent;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AssetApi {
    private ApiClient apiClient;
    private String xeroTenantId;
    private String userAgent = "Default";
    private String version = "3.0.0-beta-7";

    public AssetApi() {
        this(new ApiClient());
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

    public String getXeroTenantId() {
        return xeroTenantId;
    }

    public void setXeroTenantId(String xeroTenantId) {
        this.xeroTenantId = xeroTenantId;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public String getUserAgent() {
        return this.userAgent +  "[Xero-Java-" + this.version + "]";
    }

     private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
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
    public Asset  createAsset(Asset asset) throws IOException {
        HttpResponse response = createAssetForHttpResponse(asset);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Asset>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createAssetForHttpResponse( Asset asset) throws IOException {
        // verify the required parameter 'asset' is set
        if (asset == null) {
            throw new IllegalArgumentException("Missing the required parameter 'asset' when calling createAsset");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Assets";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(asset);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
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
    public AssetType  createAssetType(AssetType assetType) throws IOException {
        HttpResponse response = createAssetTypeForHttpResponse(assetType);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<AssetType>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createAssetTypeForHttpResponse( AssetType assetType) throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/AssetTypes";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(assetType);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
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
    public Asset  getAssetById(UUID id) throws IOException {
        HttpResponse response = getAssetByIdForHttpResponse(id);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Asset>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getAssetByIdForHttpResponse( UUID id) throws IOException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new IllegalArgumentException("Missing the required parameter 'id' when calling getAssetById");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Assets/{id}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("id", id);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * searches fixed asset settings
    * By passing in the appropriate options, you can search for available fixed asset types in the system
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter
    * @return Setting
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Setting  getAssetSettings() throws IOException {
        HttpResponse response = getAssetSettingsForHttpResponse();
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Setting>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getAssetSettingsForHttpResponse() throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Settings";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * searches fixed asset types
    * By passing in the appropriate options, you can search for available fixed asset types in the system
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter
    * @return List&lt;AssetType&gt;
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public List<AssetType>  getAssetTypes() throws IOException {
        HttpResponse response = getAssetTypesForHttpResponse();
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<List<AssetType>>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getAssetTypesForHttpResponse() throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/AssetTypes";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
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
    public Assets  getAssets(String status, Integer page, Integer pageSize, String orderBy, String sortDirection, String filterBy) throws IOException {
        HttpResponse response = getAssetsForHttpResponse(status, page, pageSize, orderBy, sortDirection, filterBy);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Assets>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getAssetsForHttpResponse( String status,  Integer page,  Integer pageSize,  String orderBy,  String sortDirection,  String filterBy) throws IOException {
        // verify the required parameter 'status' is set
        if (status == null) {
            throw new IllegalArgumentException("Missing the required parameter 'status' when calling getAssets");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Assets";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (page != null) {
            String key = "page";
            Object value = page;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (pageSize != null) {
            String key = "pageSize";
            Object value = pageSize;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (orderBy != null) {
            String key = "orderBy";
            Object value = orderBy;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (sortDirection != null) {
            String key = "sortDirection";
            Object value = sortDirection;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (filterBy != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
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
