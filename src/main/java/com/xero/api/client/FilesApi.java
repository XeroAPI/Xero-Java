package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.files.Association;
import java.io.File;
import com.xero.models.files.FileObject;
import com.xero.models.files.FileResponse204;
import com.xero.models.files.Files;
import com.xero.models.files.Folder;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xero.api.exception.XeroExceptionHandler;
import com.xero.model.*;
import com.xero.api.*;

import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
    final static Logger logger = LoggerFactory.getLogger(XeroClient.class);
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
        return this.FILE(url,body,params,method,byteBody,"application/octet-stream");
    }

    protected String FILE(String url, String body, Map<String, String> params, String method, byte[] byteBody, String contentType) throws IOException {

        OAuthRequestResource req = new OAuthRequestResource(
            config,
            signerFactory,
            url,
            method,
            contentType,
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
    * create a new association
    * By passing in the appropriate options, you can create a new folder
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - bad input parameter - TODO
    * @param fileId File id for single object
    * @param association The association parameter
    * @return Association
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Association createFileAssociation(UUID fileId, Association association) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Files/{FileId}/Associations";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Files/{FileId}/Associations";
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


            strBody = apiClient.getObjectMapper().writeValueAsString(association);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<Association> typeRef = new TypeReference<Association>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * create a new folder
    * By passing in the appropriate properties, you can create a new folder
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter - TODO
    * @param folder The folder parameter
    * @return Folder
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Folder createFolder(Folder folder) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Folders";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            strBody = apiClient.getObjectMapper().writeValueAsString(folder);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<Folder> typeRef = new TypeReference<Folder>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * delete a file
    * Delete a specific file
    * <p><b>204</b> - A successful request returns 204 empty response - populate with status property in SDK
    * @param fileId File id for single object
    * @return FileResponse204
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FileResponse204 deleteFile(UUID fileId) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Files/{FileId}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Files/{FileId}";
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


            String response = this.DATA(url, strBody, params, "DELETE");

            TypeReference<FileResponse204> typeRef = new TypeReference<FileResponse204>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * create a new association
    * By passing in the appropriate options, you can create a new folder
    * <p><b>204</b> - A successful request
    * <p><b>400</b> - bad input parameter - TODO
    * @param fileId File id for single object
    * @param objectId Object id for single object
    * @return FileResponse204
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FileResponse204 deleteFileAssociation(UUID fileId, UUID objectId) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Files/{FileId}/Associations/{ObjectId}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Files/{FileId}/Associations/{ObjectId}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            }

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("FileId", fileId.toString());
            uriVariables.put("ObjectId", objectId.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();


            String response = this.DATA(url, strBody, params, "DELETE");

            TypeReference<FileResponse204> typeRef = new TypeReference<FileResponse204>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * delete a folder
    * By passing in the appropriate ID, you can delete a folder
    * <p><b>204</b> - A successful request
    * <p><b>400</b> - bad input parameter - TODO
    * @param folderId Folder id for single object
    * @return FileResponse204
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FileResponse204 deleteFolder(UUID folderId) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Folders/{FolderId}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Folders/{FolderId}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            }

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("FolderId", folderId.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();


            String response = this.DATA(url, strBody, params, "DELETE");

            TypeReference<FileResponse204> typeRef = new TypeReference<FileResponse204>() {};
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
    * @param objectId Object id for single object
    * @return List&lt;Association&gt;
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public List<Association> getAssociationsByObject(UUID objectId) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Associations/{ObjectId}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Associations/{ObjectId}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            }

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ObjectId", objectId.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();


            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<List<Association>> typeRef = new TypeReference<List<Association>>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * searches for file by unique id
    * <p><b>200</b> - search results matching criteria
    * @param fileId File id for single object
    * @return FileObject
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FileObject getFile(UUID fileId) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Files/{FileId}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Files/{FileId}";
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


            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<FileObject> typeRef = new TypeReference<FileObject>() {};
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
    * @return List&lt;Association&gt;
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public List<Association> getFileAssociations(UUID fileId) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Files/{FileId}/Associations";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Files/{FileId}/Associations";
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


            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<List<Association>> typeRef = new TypeReference<List<Association>>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * searches files to retrieve the data
    * By passing in the appropriate options, retrieve data for specific file
    * <p><b>200</b> - returns the byte array of the specific file based on id
    * @param fileId File id for single object
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getFileContent(UUID fileId) throws IOException {
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
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter - TODO
    * @param pagesize pass an optional page size value
    * @param page number of records to skip for pagination
    * @param sort values to sort by
    * @return Files
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Files getFiles(Integer pagesize, Integer page, String sort) throws IOException {
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
    * searches specific folder by id
    * By passing in the appropriate ID, you can search for specific folder
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter - TODO
    * @param folderId Folder id for single object
    * @return Folder
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Folder getFolder(UUID folderId) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Folders/{FolderId}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Folders/{FolderId}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            }

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("FolderId", folderId.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();


            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Folder> typeRef = new TypeReference<Folder>() {};
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
    * <p><b>400</b> - bad input parameter - TODO
    * @param sort values to sort by
    * @return List&lt;Folder&gt;
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public List<Folder> getFolders(String sort) throws IOException {
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

            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<List<Folder>> typeRef = new TypeReference<List<Folder>>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * searches inbox folder
    * Search for the user inbox
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter - TODO
    * @return Folder
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Folder getInbox() throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Inbox";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Folder> typeRef = new TypeReference<Folder>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Update a file
    * Update properties on a single file
    * <p><b>200</b> - A successful request
    * @param fileId File id for single object
    * @param fileObject The fileObject parameter
    * @return FileObject
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FileObject updateFile(UUID fileId, FileObject fileObject) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Files/{FileId}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Files/{FileId}";
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


            strBody = apiClient.getObjectMapper().writeValueAsString(fileObject);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<FileObject> typeRef = new TypeReference<FileObject>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * update folder
    * By passing in the appropriate ID and properties, you can update a folder
    * <p><b>200</b> - return the updated object
    * <p><b>400</b> - bad input parameter - TODO
    * @param folderId Folder id for single object
    * @param folder The folder parameter
    * @return Folder
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Folder updateFolder(UUID folderId, Folder folder) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Folders/{FolderId}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Folders/{FolderId}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            }

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("FolderId", folderId.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();


            strBody = apiClient.getObjectMapper().writeValueAsString(folder);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Folder> typeRef = new TypeReference<Folder>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * upload an File
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - invalid input, object invalid - TODO
    * <p><b>409</b> - an existing item already exists - TODO
    * @param folderId pass an optional folder id to save file to specific folder
    * @param body The body parameter
    * @param name exact name of the file you are uploading
    * @param filename The filename parameter
    * @param mimeType The mimeType parameter
    * @return FileObject
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FileObject uploadFile(UUID folderId, byte[] body, String name, String filename, String mimeType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Files";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (folderId != null) {
                addToMapIfNotNull(params, "folderId", folderId);
            }

            UUID uuid = UUID.randomUUID();
            String boundary = uuid.toString();

            byte[] head = new String("\r\n--" + boundary + "\r\nContent-Disposition: form-data; name=" + name + ";FileName=" + filename + " \r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes();
            byte[] trailer = new String("\r\n--" + boundary + "--\r\n").getBytes();

            String contentType = "multipart/form-data; boundary=" + boundary;

            byte[] destination = new byte[head.length + body.length + trailer.length];
            System.arraycopy(head, 0, destination, 0, head.length);
            System.arraycopy(body, 0, destination, head.length, body.length);
            System.arraycopy(trailer, 0, destination, head.length + body.length, trailer.length);

            String response = this.FILE(url, strBody, params, "POST", destination, contentType);


            TypeReference<FileObject> typeRef = new TypeReference<FileObject>() {};
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

