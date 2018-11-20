package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.bankfeeds.Error;
import com.xero.models.bankfeeds.FeedConnection;
import com.xero.models.bankfeeds.FeedConnections;
import com.xero.models.bankfeeds.Statement;
import com.xero.models.bankfeeds.Statements;

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

public class BankFeedsApi {
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

    
    public BankFeedsApi(Config config) {
        this(config, new ConfigBasedSignerFactory(config));
        this.xeroExceptionHandler = new XeroExceptionHandler();
        this.apiClient = apiClient;
    }

    public BankFeedsApi(Config config, SignerFactory signerFactory) {
        this.config = config;
        this.signerFactory = signerFactory;
        this.xeroExceptionHandler = new XeroExceptionHandler();
    }

    public BankFeedsApi(ApiClient apiClient) {
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
    * create one or more new feed connection
    * By passing in the appropriate body, you can create one or more new feed connections in the system 
    * <p><b>201</b> - feed connection created
    * <p><b>400</b> - invalid input, object invalid
    * @param feedConnections Feed Connection(s) to add
    * @return FeedConnections
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public FeedConnections createFeedConnections(FeedConnections feedConnections) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/FeedConnections";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(feedConnections);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<FeedConnections> typeRef = new TypeReference<FeedConnections>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>202</b> - Success
    * <p><b>400</b> - Statement failed validation
    * <p><b>403</b> - Invalid application or feed connection
    * <p><b>409</b> - Duplicate statement received
    * <p><b>413</b> - Statement exceeds size limit
    * <p><b>500</b> - Intermittent Xero Error
    * @param statements Feed Connection(s) to add
    * @return Statements
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Statements createStatements(Statements statements) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Statements";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(statements);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
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
    public FeedConnections deleteFeedConnections(FeedConnections feedConnections) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/FeedConnections/DeleteRequests";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(feedConnections);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<FeedConnections> typeRef = new TypeReference<FeedConnections>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
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
    public FeedConnection getFeedConnection(String id) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/FeedConnections/{id}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/FeedConnections/{id}";
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
            
            TypeReference<FeedConnection> typeRef = new TypeReference<FeedConnection>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
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
    public FeedConnections getFeedConnections(Integer page, Integer pageSize) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/FeedConnections";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            params = new HashMap<>();
            if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }if (pageSize != null) {
                addToMapIfNotNull(params, "pageSize", pageSize);
            }
            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET");
            
            TypeReference<FeedConnections> typeRef = new TypeReference<FeedConnections>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - search results matching id for single statement
    * <p><b>404</b> - Statement not found
    * @param statementId The statementId parameter
    * @return Statement
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Statement getStatement(String statementId) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Statements/{statementId}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Statements/{statementId}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("statementId", statementId.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET");
            
            TypeReference<Statement> typeRef = new TypeReference<Statement>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
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
    public Statements getStatements(Integer page, Integer pageSize, String xeroApplicationId, String xeroTenantId, String xeroUserId) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Statements";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            params = new HashMap<>();
            if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }if (pageSize != null) {
                addToMapIfNotNull(params, "pageSize", pageSize);
            }
            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET", xeroApplicationId, xeroTenantId, xeroUserId);
            
            TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
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

