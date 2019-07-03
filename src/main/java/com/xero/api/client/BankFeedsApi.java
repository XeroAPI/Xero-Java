package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.bankfeeds.Error;
import com.xero.models.bankfeeds.FeedConnection;
import com.xero.models.bankfeeds.FeedConnections;
import com.xero.models.bankfeeds.Statement;
import com.xero.models.bankfeeds.Statements;

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


public class BankFeedsApi {
    private ApiClient apiClient;
    private String xeroTenantId;
    private String userAgent = "Default";
    private String version = "3.0.0-beta-7";

    public BankFeedsApi() {
        this(new ApiClient());
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
    * create one or more new feed connection
    * By passing in the appropriate body, you can create one or more new feed connections in the system 
    * <p><b>201</b> - feed connection created
    * <p><b>400</b> - invalid input, object invalid
    * @param feedConnections Feed Connection(s) to add
    * @return FeedConnections
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FeedConnections  createFeedConnections(FeedConnections feedConnections) throws IOException {
        HttpResponse response = createFeedConnectionsForHttpResponse(feedConnections);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<FeedConnections>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createFeedConnectionsForHttpResponse( FeedConnections feedConnections) throws IOException {
        // verify the required parameter 'feedConnections' is set
        if (feedConnections == null) {
            throw new IllegalArgumentException("Missing the required parameter 'feedConnections' when calling createFeedConnections");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/FeedConnections";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(feedConnections);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * <p><b>202</b> - Success
    * <p><b>400</b> - Statement failed validation
    * <p><b>403</b> - Invalid application or feed connection
    * <p><b>409</b> - Duplicate statement received
    * <p><b>413</b> - Statement exceeds size limit
    * <p><b>422</b> - Unprocessable Entity
    * <p><b>500</b> - Intermittent Xero Error
    * @param statements Feed Connection(s) to add
    * @return Statements
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Statements  createStatements(Statements statements) throws IOException {
        HttpResponse response = createStatementsForHttpResponse(statements);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Statements>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createStatementsForHttpResponse( Statements statements) throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Statements";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(statements);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * delete an exsiting feed connection
    * By passing in the appropriate body, you can create a new feed connections in the system 
    * <p><b>202</b> - create results matching body content
    * <p><b>400</b> - bad input parameter
    * @param feedConnections Feed Connections to delete
    * @return FeedConnections
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FeedConnections  deleteFeedConnections(FeedConnections feedConnections) throws IOException {
        HttpResponse response = deleteFeedConnectionsForHttpResponse(feedConnections);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<FeedConnections>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse deleteFeedConnectionsForHttpResponse( FeedConnections feedConnections) throws IOException {
        // verify the required parameter 'feedConnections' is set
        if (feedConnections == null) {
            throw new IllegalArgumentException("Missing the required parameter 'feedConnections' when calling deleteFeedConnections");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/FeedConnections/DeleteRequests";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(feedConnections);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * get single feed connection by id
    * By passing in a FeedConnection Id options, you can search for available feed connections in the system 
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter
    * @param id feed connection id for single object
    * @return FeedConnection
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FeedConnection  getFeedConnection(String id) throws IOException {
        HttpResponse response = getFeedConnectionForHttpResponse(id);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<FeedConnection>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getFeedConnectionForHttpResponse( String id) throws IOException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new IllegalArgumentException("Missing the required parameter 'id' when calling getFeedConnection");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/FeedConnections/{id}";
        
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
    * searches feed connections
    * By passing in the appropriate options, you can search for available feed connections in the system
    * <p><b>201</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter
    * @param page Page number which specifies the set of records to retrieve. By default the number of the records per set is 10. Example - https://api.xero.com/bankfeeds.xro/1.0/FeedConnections?page&#x3D;1 to get the second set of the records. When page value is not a number or a negative number, by default, the first set of records is returned.
    * @param pageSize Page size which specifies how many records per page will be returned (default 10). Example - https://api.xero.com/bankfeeds.xro/1.0/FeedConnections?pageSize&#x3D;100 to specify page size of 100.
    * @return FeedConnections
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FeedConnections  getFeedConnections(Integer page, Integer pageSize) throws IOException {
        HttpResponse response = getFeedConnectionsForHttpResponse(page, pageSize);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<FeedConnections>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getFeedConnectionsForHttpResponse( Integer page,  Integer pageSize) throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/FeedConnections";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * <p><b>200</b> - search results matching id for single statement
    * <p><b>404</b> - Statement not found
    * @param statementId The statementId parameter
    * @return Statement
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Statement  getStatement(String statementId) throws IOException {
        HttpResponse response = getStatementForHttpResponse(statementId);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Statement>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getStatementForHttpResponse( String statementId) throws IOException {
        // verify the required parameter 'statementId' is set
        if (statementId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'statementId' when calling getStatement");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Statements/{statementId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("statementId", statementId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - bad input parameter
    * @param page The page parameter
    * @param pageSize The pageSize parameter
    * @param xeroApplicationId The xeroApplicationId parameter
    * @param xeroTenantId The xeroTenantId parameter
    * @param xeroUserId The xeroUserId parameter
    * @return Statements
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Statements  getStatements(Integer page, Integer pageSize, String xeroApplicationId, String xeroTenantId, String xeroUserId) throws IOException {
        HttpResponse response = getStatementsForHttpResponse(page, pageSize, xeroApplicationId, xeroTenantId, xeroUserId);
        //InputStream instream = response.getContent();
        //String result = convertStreamToString(instream);
        //System.out.println("RESPONSE: " + result);
        //instream.close();
        TypeReference typeRef = new TypeReference<Statements>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getStatementsForHttpResponse( Integer page,  Integer pageSize,  String xeroApplicationId,  String xeroTenantId,  String xeroUserId) throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Statements";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
