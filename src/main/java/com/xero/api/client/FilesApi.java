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


public class FilesApi {
    private ApiClient apiClient;
    private String xeroTenantId;
    private String userAgent = "Default";
    private String version = "3.0.0-beta-7";

    public FilesApi() {
        this(new ApiClient());
    }

    public FilesApi(ApiClient apiClient) {
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
    * create a new association
    * By passing in the appropriate options, you can create a new folder
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - bad input parameter - TODO
    * @param fileId File id for single object
    * @param association The association parameter
    * @return Association
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Association  createFileAssociation(UUID fileId, Association association) throws IOException {
        HttpResponse response = createFileAssociationForHttpResponse(fileId, association);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Association>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createFileAssociationForHttpResponse(UUID fileId, Association association) throws IOException {
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileId' when calling createFileAssociation");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Files/{FileId}/Associations";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("FileId", fileId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        content = apiClient.new JacksonJsonHttpContent(association);
        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
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
    public Folder  createFolder(Folder folder) throws IOException {
        HttpResponse response = createFolderForHttpResponse(folder);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Folder>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createFolderForHttpResponse(Folder folder) throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Folders";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        content = apiClient.new JacksonJsonHttpContent(folder);
        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * delete a file
    * Delete a specific file
    * <p><b>204</b> - A successful request returns 204 empty response - populate with status property in SDK
    * @param fileId File id for single object
    * @return FileResponse204
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FileResponse204  deleteFile(UUID fileId) throws IOException {
        HttpResponse response = deleteFileForHttpResponse(fileId);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<FileResponse204>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse deleteFileForHttpResponse(UUID fileId) throws IOException {
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileId' when calling deleteFile");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Files/{FileId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("FileId", fileId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.DELETE, genericUrl, content).setHeaders(headers).execute();
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
    public FileResponse204  deleteFileAssociation(UUID fileId, UUID objectId) throws IOException {
        HttpResponse response = deleteFileAssociationForHttpResponse(fileId, objectId);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<FileResponse204>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse deleteFileAssociationForHttpResponse(UUID fileId, UUID objectId) throws IOException {
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileId' when calling deleteFileAssociation");
        }// verify the required parameter 'objectId' is set
        if (objectId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'objectId' when calling deleteFileAssociation");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Files/{FileId}/Associations/{ObjectId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("FileId", fileId);
        uriVariables.put("ObjectId", objectId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.DELETE, genericUrl, content).setHeaders(headers).execute();
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
    public FileResponse204  deleteFolder(UUID folderId) throws IOException {
        HttpResponse response = deleteFolderForHttpResponse(folderId);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<FileResponse204>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse deleteFolderForHttpResponse(UUID folderId) throws IOException {
        // verify the required parameter 'folderId' is set
        if (folderId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'folderId' when calling deleteFolder");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Folders/{FolderId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("FolderId", folderId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.DELETE, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * searches files
    * By passing in the appropriate options,
    * <p><b>200</b> - search results matching criteria
    * @param objectId Object id for single object
    * @return List&lt;Association&gt;
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public List<Association>  getAssociationsByObject(UUID objectId) throws IOException {
        HttpResponse response = getAssociationsByObjectForHttpResponse(objectId);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<List<Association>>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getAssociationsByObjectForHttpResponse(UUID objectId) throws IOException {
        // verify the required parameter 'objectId' is set
        if (objectId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'objectId' when calling getAssociationsByObject");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Associations/{ObjectId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ObjectId", objectId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * searches for file by unique id
    * <p><b>200</b> - search results matching criteria
    * @param fileId File id for single object
    * @return FileObject
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FileObject  getFile(UUID fileId) throws IOException {
        HttpResponse response = getFileForHttpResponse(fileId);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<FileObject>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getFileForHttpResponse(UUID fileId) throws IOException {
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileId' when calling getFile");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Files/{FileId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("FileId", fileId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * searches files
    * By passing in the appropriate options,  
    * <p><b>200</b> - search results matching criteria
    * @param fileId File id for single object
    * @return List&lt;Association&gt;
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public List<Association>  getFileAssociations(UUID fileId) throws IOException {
        HttpResponse response = getFileAssociationsForHttpResponse(fileId);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<List<Association>>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getFileAssociationsForHttpResponse(UUID fileId) throws IOException {
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileId' when calling getFileAssociations");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Files/{FileId}/Associations";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("FileId", fileId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * searches files to retrieve the data
    * By passing in the appropriate options, retrieve data for specific file
    * <p><b>200</b> - returns the byte array of the specific file based on id
    * @param fileId File id for single object
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getFileContent(UUID fileId) throws IOException {
        HttpResponse response = getFileContentForHttpResponse(fileId);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getFileContentForHttpResponse(UUID fileId) throws IOException {
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileId' when calling getFileContent");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Files/{FileId}/Content";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/Files/{FileId}/Content";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("FileId", fileId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
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
    public Files  getFiles(Integer pagesize, Integer page, String sort) throws IOException {
        HttpResponse response = getFilesForHttpResponse(pagesize, page, sort);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Files>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getFilesForHttpResponse(Integer pagesize, Integer page, String sort) throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Files";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        if (pagesize != null) {
            String key = "pagesize";
            Object value = pagesize;
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
        }        if (sort != null) {
            String key = "sort";
            Object value = sort;
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

    

  /**
    * searches specific folder by id
    * By passing in the appropriate ID, you can search for specific folder
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter - TODO
    * @param folderId Folder id for single object
    * @return Folder
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Folder  getFolder(UUID folderId) throws IOException {
        HttpResponse response = getFolderForHttpResponse(folderId);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Folder>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getFolderForHttpResponse(UUID folderId) throws IOException {
        // verify the required parameter 'folderId' is set
        if (folderId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'folderId' when calling getFolder");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Folders/{FolderId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("FolderId", folderId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
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
    public List<Folder>  getFolders(String sort) throws IOException {
        HttpResponse response = getFoldersForHttpResponse(sort);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<List<Folder>>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getFoldersForHttpResponse(String sort) throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Folders";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        if (sort != null) {
            String key = "sort";
            Object value = sort;
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

    

  /**
    * searches inbox folder
    * Search for the user inbox
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter - TODO
    * @return Folder
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Folder  getInbox() throws IOException {
        HttpResponse response = getInboxForHttpResponse();
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Folder>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getInboxForHttpResponse() throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Inbox";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
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
    public FileObject  updateFile(UUID fileId, FileObject fileObject) throws IOException {
        HttpResponse response = updateFileForHttpResponse(fileId, fileObject);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<FileObject>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateFileForHttpResponse(UUID fileId, FileObject fileObject) throws IOException {
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileId' when calling updateFile");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Files/{FileId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("FileId", fileId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        content = apiClient.new JacksonJsonHttpContent(fileObject);
        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
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
    public Folder  updateFolder(UUID folderId, Folder folder) throws IOException {
        HttpResponse response = updateFolderForHttpResponse(folderId, folder);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Folder>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateFolderForHttpResponse(UUID folderId, Folder folder) throws IOException {
        // verify the required parameter 'folderId' is set
        if (folderId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'folderId' when calling updateFolder");
        }// verify the required parameter 'folder' is set
        if (folder == null) {
            throw new IllegalArgumentException("Missing the required parameter 'folder' when calling updateFolder");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Folders/{FolderId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("FolderId", folderId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        



        
        content = apiClient.new JacksonJsonHttpContent(folder);
        
        

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
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
    public FileObject  uploadFile(UUID folderId, byte[] body, String name, String filename, String mimeType) throws IOException {
        HttpResponse response = uploadFileForHttpResponse(folderId, body, name, filename, mimeType);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<FileObject>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse uploadFileForHttpResponse(UUID folderId, byte[] body, String name, String filename, String mimeType) throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Files";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        if (folderId != null) {
            String key = "folderId";
            Object value = folderId;
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
        



        
        

        

            UUID uuid = UUID.randomUUID();
            String boundary = uuid.toString();
           
            byte[] head = new String("\r\n--" + boundary + "\r\nContent-Disposition: form-data; name=" + name + ";FileName=" + filename + " \r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes();
            byte[] trailer = new String("\r\n--" + boundary + "--\r\n").getBytes();

            String contentType = "multipart/form-data; boundary=" + boundary;
            
            byte[] destination = new byte[head.length + body.length + trailer.length];
            System.arraycopy(head, 0, destination, 0, head.length);
            System.arraycopy(body, 0, destination, head.length, body.length);
            System.arraycopy(trailer, 0, destination, head.length + body.length, trailer.length);
            
            content = new ByteArrayContent(mimeType, destination);

        

        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
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
