package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.feedconnections.FeedConnection;
import com.xero.models.feedconnections.FeedConnections;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xero.api.exception.XeroExceptionHandler;
import com.xero.model.*;
import com.xero.api.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.ws.rs.core.UriBuilder;

public class FeedConnectionApi {
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

    public FeedConnectionApi() {
        this(JsonConfig.getInstance());
        this.xeroExceptionHandler = new XeroExceptionHandler();
    }

    public FeedConnectionApi(Config config) {
        this(config, new ConfigBasedSignerFactory(config));
        this.xeroExceptionHandler = new XeroExceptionHandler();
    }

    public FeedConnectionApi(Config config, SignerFactory signerFactory) {
        this.config = config;
        this.signerFactory = signerFactory;
        this.xeroExceptionHandler = new XeroExceptionHandler();
    }

    public FeedConnectionApi(ApiClient apiClient) {
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

    protected String POST(String url, String body, Map<String, String> params, Date modifiedAfter) throws IOException {
        
        OAuthRequestResource req = new OAuthRequestResource(
            config, 
            signerFactory, 
            url, 
            "POST", 
            body, 
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

    protected String PUT(String url, String body, Map<String, String> params, Date modifiedAfter) throws IOException {
        return "hello";
    }

    protected String DELETE(String url, String body, Map<String, String> params, Date modifiedAfter) throws IOException {
        return "hello";
    }

    protected String GET(String url, String body, Map<String, String> params, Date modifiedAfter) throws IOException {
        
        OAuthRequestResource req = new OAuthRequestResource(
            config, 
            signerFactory, 
            url, 
            "GET", 
            null, 
            params,
            "application/json");
        
        req.setToken(token);
        req.setTokenSecret(tokenSecret);
        
        if (modifiedAfter != null) {
            req.setIfModifiedSince(modifiedAfter);
        }

        try {
            Map<String, String>  resp = req.execute();
            Object r = resp.get("content");
            return r.toString();
        } catch (IOException ioe) {
             throw xeroExceptionHandler.convertException(ioe);
        }
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
    public FeedConnections createFeedConnections(FeedConnections feedConnections, Map<String, String> params) throws IOException {
        
        try {
            UriBuilder uriBuilder = UriBuilder.fromUri(config.getFeedConnectionsUrl() + "/FeedConnections");
            String url = uriBuilder.build().toString();

            String body = null;
            Date modifiedAfter = null;

            ApiClient apiClient = new ApiClient();
            body = apiClient.getObjectMapper().writeValueAsString(feedConnections);
            String response = this.POST(url, body, params, modifiedAfter);

            TypeReference<FeedConnections> typeRef = new TypeReference<FeedConnections>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);
    
        } catch (IOException e) {
            throw xeroExceptionHandler.convertException(e);
        }
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
    public FeedConnections deleteFeedConnections(FeedConnections feedConnections, Map<String, String> params) throws IOException {
        
        try {
            UriBuilder uriBuilder = UriBuilder.fromUri(config.getFeedConnectionsUrl() + "/FeedConnections/DeleteRequests");
            String url = uriBuilder.build().toString();

            String body = null;
            Date modifiedAfter = null;

            ApiClient apiClient = new ApiClient();
            body = apiClient.getObjectMapper().writeValueAsString(feedConnections);
            String response = this.POST(url, body, params, modifiedAfter);

            TypeReference<FeedConnections> typeRef = new TypeReference<FeedConnections>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);
    
        } catch (IOException e) {
            throw xeroExceptionHandler.convertException(e);
        }
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
    public FeedConnection getFeedConnection(UUID id, Map<String, String> params) throws IOException {
        
        try {
            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("id", id.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(config.getFeedConnectionsUrl() + "/FeedConnections/{id}");
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            String body = null;
            Date modifiedAfter = null;

            ApiClient apiClient = new ApiClient();
            String response = this.GET(url, body, params, modifiedAfter);

            TypeReference<FeedConnection> typeRef = new TypeReference<FeedConnection>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);
    
        } catch (IOException e) {
            throw xeroExceptionHandler.convertException(e);
        }
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
    public FeedConnections getFeedConnections(Map<String, String> params) throws IOException {
        
        try {
            UriBuilder uriBuilder = UriBuilder.fromUri(config.getFeedConnectionsUrl() + "/FeedConnections");
            String url = uriBuilder.build().toString();

            String body = null;
            Date modifiedAfter = null;

            ApiClient apiClient = new ApiClient();
            String response = this.GET(url, body, params, modifiedAfter);

            TypeReference<FeedConnections> typeRef = new TypeReference<FeedConnections>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);
    
        } catch (IOException e) {
            throw xeroExceptionHandler.convertException(e);
        }
    }
}
