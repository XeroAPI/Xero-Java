package com.xero.api.client;

import com.xero.api.ApiClient;

import java.io.File;
import com.xero.models.files.Files;
import com.xero.models.files.Folder;
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

public class FilesApi {
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

    
    public FilesApi(Config config) {
        this(config, new ConfigBasedSignerFactory(config));
        this.xeroExceptionHandler = new XeroExceptionHandler();
        this.apiClient = apiClient;
    }

    public FilesApi(Config config, SignerFactory signerFactory) {
        this.config = config;
        this.signerFactory = signerFactory;
        this.xeroExceptionHandler = new XeroExceptionHandler();
    }

    public FilesApi(ApiClient apiClient) {
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
    * upload an File
    * Adds an File to the Xero
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - invalid input, object invalid
    * <p><b>409</b> - an existing item already exists
    * @param body File to add
    * @return Files
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Files createFile(File body) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Files";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Files> typeRef = new TypeReference<Files>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * searches files
    * By passing in the appropriate options,  
    * <p><b>200</b> - search results matching criteria
    * @param fileId File id for single object
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getFileContent(UUID fileId) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Files/{FileId}/Content";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Files/{FileId}/Content";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("FileId", fileId.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();


            ApiClient apiClient = new ApiClient();
            ByteArrayInputStream response = this.FILE(url, strBody, params, "GET");
            return response;
           
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * searches files
    * By passing in the appropriate options, you can search for available inventory in the system 
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter
    * @param pagesize pass an optional page size value
    * @param page number of records to skip for pagination
    * @param sort values to sort by
    * @return Files
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Files getFiles(Integer pagesize, Integer page, String sort) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Files";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            params = new HashMap<>();
            if (pagesize != null) {
                addToMapIfNotNull(params, "pagesize", pagesize);
            }if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }if (sort != null) {
                addToMapIfNotNull(params, "sort", sort);
            }
            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET");
            
            TypeReference<Files> typeRef = new TypeReference<Files>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * searches folder
    * By passing in the appropriate options, you can search for available folders
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter
    * @param sort values to sort by
    * @return List&lt;Folder&gt;
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public List<Folder> getFolders(String sort) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Folders";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            params = new HashMap<>();
            if (sort != null) {
                addToMapIfNotNull(params, "sort", sort);
            }
            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET");
            
            TypeReference<List<Folder>> typeRef = new TypeReference<List<Folder>>() {};
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

