package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.identity.Connection;

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
import java.nio.file.Files;
import java.io.ByteArrayInputStream;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class IdentityApi {
    private ApiClient apiClient;
    private String xeroTenantId;

    public IdentityApi() {
        this(new ApiClient());
    }

    public IdentityApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public String getXeroTenantId(String xeroTenantId) {
        return xeroTenantId;
    }

    public void setXeroTenantId(String xeroTenantId) {
        this.xeroTenantId = xeroTenantId;
    }

  /**
    * Allows you to retrieve the connections for this users
    * Override the base server url that include version
    * <p><b>200</b> - Success - return response of type Connections array with 0 to n Connection
    * @return List&lt;Connection&gt;
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public List<Connection>  getConnections() throws IOException {
        HttpResponse response = getConnectionsForHttpResponse();
        TypeReference typeRef = new TypeReference<List<Connection>>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getConnectionsForHttpResponse() throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", this.xeroTenantId);
        headers.setAccept("application/json"); 
        String correctPath = "/connections";
        UriBuilder uriBuilder = UriBuilder.fromUri("https://api.xero.com" + correctPath);
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
