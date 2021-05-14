package com.xero.api;

import com.xero.api.client.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.threetenbp.ThreeTenModule;
import org.threeten.bp.*;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.Json;
import java.io.IOException;
import java.io.OutputStream;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;

/** 
 * ApiClient for managing global http client and object mapper
*/
public class ApiClient {
    private final String basePath;
    private final HttpRequestFactory httpRequestFactory;
    private final ObjectMapper objectMapper;
    private HttpTransport httpTransport = new NetHttpTransport();
    private int connectionTimeout = 180000;
    private int readTimeout = 180000;

    private static final String defaultBasePath = "https://api.xero.com/api.xro/2.0";

    // A reasonable default object mapper. Client can pass in a chosen ObjectMapper anyway, this is just for reasonable defaults.
    private static ObjectMapper createDefaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(new RFC3339DateFormat())
            .setSerializationInclusion(Include.NON_EMPTY);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        
        ThreeTenModule module = new ThreeTenModule();
        objectMapper.registerModule(module);
        return objectMapper;
    }

    /** method for initiazing object instance with default properties */
    public ApiClient() {
        this(null, null, null, null,null);
    }

    /** ApiClient method for initiazing object instance with custom properties 
    * @param basePath String that defines the base path for each API request
    * @param transport HttpTransport required for creating the httpRequestFactory
    * @param initializer HttpRequestInitializer for additional configuration during creation of httpRequestFactory
    * @param objectMapper ObjectMapper object used to serialize and deserialize using model classes.
    * @param reqFactory HttpRequestFactory is the thread-safe light-weight HTTP request factory layer on top of the HTTP transport  
    */
    public ApiClient(
        String basePath,
        HttpTransport transport,
        HttpRequestInitializer initializer,
        ObjectMapper objectMapper,
        HttpRequestFactory reqFactory
    ) {
        this.basePath = basePath == null ? defaultBasePath : (
            basePath.endsWith("/") ? basePath.substring(0, basePath.length() - 1) : basePath
        );
        if (transport != null) {
            this.httpTransport = transport;
        }
        this.httpRequestFactory = (reqFactory != null ? reqFactory : (transport == null ? Utils.getDefaultTransport() : transport).createRequestFactory(initializer) );
        this.objectMapper = (objectMapper == null ? createDefaultObjectMapper() : objectMapper);
    }

    /** method for retrieving the httpRequestFactory property
    * @return HttpRequestFactory
    */
    public HttpRequestFactory getHttpRequestFactory() {
        return httpRequestFactory;
    }

    /** method for retrieving the connectionTimeout property 
    * @return int
    */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /** method for setting the connectionTimeout property 
    * @param connectionTimeout int defines in milliseconds the time allowed making the initial connection to the server.
    */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /** method for retrieving the readTimeout property
    * @return int
    */
    public int getReadTimeout() {
        return readTimeout;
    }

    /** method for setting the readTimeout property 
    * @param readTimeout int defines in milliseconds the time allowed to complete reading data from the server.
    */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /** method for retrieving the httpTransport property 
    * @return HttpTransport
    */
    public HttpTransport getHttpTransport() {
        return httpTransport;
    }

    /** method for setting the httpTransport property 
    * @param transport HttpTransport required for creating the httpRequestFactory
    */
    public void setHttpTransport(HttpTransport transport) {
        this.httpTransport = transport;
    }

    /** method for retrieving the basePath property 
    * @return String
    */
    public String getBasePath() {
        return basePath;
    }

    /** method for retrieving the objectMapper property 
    * @return ObjectMapper
    */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /** method for managing objects to be serialized */
    public class JacksonJsonHttpContent extends AbstractHttpContent {
        /* A POJO that can be serialized with a com.fasterxml Jackson ObjectMapper */
        private final Object data;

        /**  method for taking native object and serializing into JSON data for use by http client 
        * @param data Object 
        */
        public JacksonJsonHttpContent(Object data) {
            super(Json.MEDIA_TYPE);
            this.data = data;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            objectMapper.writeValue(out, data);
        }
    }

    /**  Builder pattern to get API instances for this client. 
    * @return AccountingApi
    */
    public AccountingApi accountingApi() {
        return new AccountingApi(this);
    }

    /** method for verifying an access token using RSA256 Algorithm
    * @param accessToken String the JWT token used to access the API
    * @return DecodedJWT
    * @exception MalformedURLException Thrown to indicate that a malformed URL has occurred. 
    * @exception JwkException thrown to indicate a problem while verifying a JWT
    */
    public DecodedJWT verify(String accessToken) throws MalformedURLException, JwkException {
		
    	DecodedJWT unverifiedJWT = JWT.decode(accessToken);        
    	JwkProvider provider = new UrlJwkProvider(new URL("https://identity.xero.com/.well-known/openid-configuration/jwks"));
		Jwk jwk = provider.get(unverifiedJWT.getKeyId());
	
		Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(),null);
		 
		JWTVerifier verifier = JWT.require(algorithm)
				 .withIssuer("https://identity.xero.com")
                 .acceptLeeway(1000)
				 .build();
		DecodedJWT verifiedJWT = verifier.verify(accessToken);
		 
    	return verifiedJWT;
    }

    /** method for revoking a refresh token
     * @param refreshToken String the refresh token used to obtain a new access token via the API
     * @param clientId String the client id used to authtenticate with the API
     * @param clientSecret String the client secret used to authtenticate with the API
     * @return revokeResponse HttpResponse a successful revocation request will return a 200 response with an empty body.
     */
    public HttpResponse revoke(String clientId, String clientSecret, String refreshToken) throws IOException {

		// BASIC AUTHENTICATION HEADER
		HttpHeaders headers = new HttpHeaders();
	    headers.setBasicAuthentication(clientId, clientSecret);
		
	    // POST BODY WITH REFRESH TOKEN
		String urlParameters  = "token=" + refreshToken;
		byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
		HttpContent content = new ByteArrayContent("application/x-www-form-urlencoded", postData);
	
		// REVOKE URL DEFINED
		GenericUrl url = new GenericUrl("https://identity.xero.com/connect/revocation");
        		
		HttpTransport transport = this.getHttpTransport();
	    HttpRequestFactory requestFactory = transport.createRequestFactory();
	    HttpResponse revokeResponse = requestFactory
		    .buildRequest(HttpMethods.POST, url, content)
		    .setHeaders(headers)
		    .setConnectTimeout(this.getConnectionTimeout())
		    .setReadTimeout(this.getReadTimeout())
		    .execute();
		
		return revokeResponse;
    }
}
