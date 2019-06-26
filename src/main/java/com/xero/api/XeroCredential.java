package com.xero.api;


 import com.google.api.client.auth.oauth2.BearerToken;
 import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
 import com.google.api.client.auth.oauth2.Credential;
 import com.google.api.client.auth.oauth2.CredentialRefreshListener;
 import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
 import com.google.api.client.auth.oauth2.TokenRequest;
 import com.google.api.client.auth.oauth2.TokenResponse;
 import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
 import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
 import com.google.api.client.googleapis.auth.oauth2.OAuth2Utils;
 import com.google.api.client.googleapis.util.Utils;
 import com.google.api.client.http.GenericUrl;
 import com.google.api.client.http.HttpExecuteInterceptor;
 import com.google.api.client.http.HttpRequestInitializer;
 import com.google.api.client.http.HttpTransport;
 import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
 import com.google.api.client.json.GenericJson;
 import com.google.api.client.json.JsonFactory;
 import com.google.api.client.json.JsonObjectParser;
 import com.google.api.client.json.webtoken.JsonWebSignature;
 import com.google.api.client.json.webtoken.JsonWebToken;
 import com.google.api.client.util.Beta;
 import com.google.api.client.util.Clock;
 import com.google.api.client.util.Joiner;
 import com.google.api.client.util.PemReader;
 import com.google.api.client.util.PemReader.Section;
 import com.google.api.client.util.Preconditions;
 import com.google.api.client.util.SecurityUtils;
 import com.google.api.client.util.store.DataStoreFactory;

 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.Reader;
 import java.io.StringReader;
 import java.security.GeneralSecurityException;
 import java.security.KeyFactory;
 import java.security.NoSuchAlgorithmException;
 import java.security.PrivateKey;
 import java.security.spec.InvalidKeySpecException;
 import java.security.spec.PKCS8EncodedKeySpec;
 import java.util.Collection;
 import java.util.Collections;

 /**
  * Thread-safe Google-specific implementation of the OAuth 2.0 helper for accessing protected
  * resources using an access token, as well as optionally refreshing the access token when it
  * expires using a refresh token.
  *
  * <p>
  * There are three modes supported: access token only, refresh token flow, and service account flow
  * (with or without impersonating a user).
  * </p>
  *
  * <p>
  * If all you have is an access token, you simply pass the {@link TokenResponse} to the credential
  * using {@link Builder#setFromTokenResponse(TokenResponse)}. Google credential uses
  * {@link BearerToken#authorizationHeaderAccessMethod()} as the access method. Sample usage:
  * </p>
  *
  * <pre>
   public static GoogleCredential createCredentialWithAccessTokenOnly(TokenResponse tokenResponse) {
     return new GoogleCredential().setFromTokenResponse(tokenResponse);
   }
  * </pre>
  *
  * <p>
  * If you have a refresh token, it is similar to the case of access token only, but you additionally
  * need to pass the credential the client secrets using
  * {@link Builder#setClientSecrets(String, String)}. Google credential uses
  * {@link XeroOAuthConstants#TOKEN_SERVER_URL} as the token server URL, and
  * {@link ClientParametersAuthentication} with the client ID and secret as the client
  * authentication. Sample usage:
  * </p>
  *
  *
  * <p>
  * The <a href="https://developers.google.com/accounts/docs/OAuth2ServiceAccount">service account
  * flow</a> is used when you want to access data owned by your client application. You download the
  * private key in a {@code .p12} file from the Google APIs Console. Use
  * </p>
  *
  
  * <p>
  * If you need to persist the access token in a data store, use {@link DataStoreFactory} and
  * {@link Builder#addRefreshListener(CredentialRefreshListener)} with
  * {@link DataStoreCredentialRefreshListener}.
  * </p>
  *
  * <p>
  * If you have a custom request initializer, request execute interceptor, or unsuccessful response
  * handler, take a look at the sample usage for {@link HttpExecuteInterceptor} and
  * </p>
  *
  * @since 1.7
  * @author Yaniv Inbar
  */
 public class XeroCredential extends Credential {

   public XeroCredential(AccessMethod method) {
		super(method);
		// TODO Auto-generated constructor stub
	}

	static final String USER_FILE_TYPE = "authorized_user";
   static final String SERVICE_ACCOUNT_FILE_TYPE = "service_account";

 

  

   /**
    * {@link Beta} 
    * Return a credential defined by a Json file.
    *
    * @param credentialStream the stream with the credential definition.
    * @param transport the transport for Http calls.
    * @param jsonFactory the factory for Json parsing and formatting.
    * @return the credential defined by the credentialStream.
    * @throws IOException if the credential cannot be created from the stream.
    */
   

   /**
    * Service account ID (typically an e-mail address) or {@code null} if not using the service
    * account flow.
    */
   private String serviceAccountId;

   /**
    * Service account Project ID or {@code null} if not present, either because this is not using the
    * service account flow, or is using an older version of the service account configuration.
    */
   private String serviceAccountProjectId;

   /**
    * Collection of OAuth scopes to use with the service account flow or {@code null} if not
    * using the service account flow.
    */
   private Collection<String> serviceAccountScopes;

   /**
    * Private key to use with the service account flow or {@code null} if not using the service
    * account flow.
    */
   private PrivateKey serviceAccountPrivateKey;

   /**
    * ID of private key to use with the service account flow or {@code null} if not using the
    * service account flow.
    */
   private String serviceAccountPrivateKeyId;

   /**
    * Email address of the user the application is trying to impersonate in the service account flow
    * or {@code null} for none or if not using the service account flow.
    */
   private String serviceAccountUser;

   /**
    * Constructor with the ability to access protected resources, but not refresh tokens.
    *
    * <p>
    * To use with the ability to refresh tokens, use {@link Builder}.
    * </p>
    */
   public XeroCredential() {
     this(new Builder());
   }

   /**
    * @param builder Google credential builder
    *
    * @since 1.14
    */
   protected XeroCredential(Builder builder) {
     super(builder);
     if (builder.serviceAccountPrivateKey == null) {
       Preconditions.checkArgument(builder.serviceAccountId == null
           && builder.serviceAccountScopes == null && builder.serviceAccountUser == null);
     } else {
       serviceAccountId = Preconditions.checkNotNull(builder.serviceAccountId);
       serviceAccountProjectId = builder.serviceAccountProjectId;
       serviceAccountScopes =
           (builder.serviceAccountScopes == null)
               ? Collections.<String>emptyList()
               : Collections.unmodifiableCollection(builder.serviceAccountScopes);
       serviceAccountPrivateKey = builder.serviceAccountPrivateKey;
       serviceAccountPrivateKeyId = builder.serviceAccountPrivateKeyId;
       serviceAccountUser = builder.serviceAccountUser;
     }
   }

   @Override
   public XeroCredential setAccessToken(String accessToken) {
     return (XeroCredential) super.setAccessToken(accessToken);
   }

   @Override
   public XeroCredential setRefreshToken(String refreshToken) {
     if (refreshToken != null) {
       Preconditions.checkArgument(
           getJsonFactory() != null && getTransport() != null && getClientAuthentication() != null,
           "Please use the Builder and call setJsonFactory, setTransport and setClientSecrets");
     }
     return (XeroCredential) super.setRefreshToken(refreshToken);
   }

   @Override
   public XeroCredential setExpirationTimeMilliseconds(Long expirationTimeMilliseconds) {
     return (XeroCredential) super.setExpirationTimeMilliseconds(expirationTimeMilliseconds);
   }

   @Override
   public XeroCredential setExpiresInSeconds(Long expiresIn) {
     return (XeroCredential) super.setExpiresInSeconds(expiresIn);
   }

   @Override
   public XeroCredential setFromTokenResponse(TokenResponse tokenResponse) {
     return (XeroCredential) super.setFromTokenResponse(tokenResponse);
   }

   @Override
   @Beta
   protected TokenResponse executeRefreshToken() throws IOException {
     if (serviceAccountPrivateKey == null) {
       return super.executeRefreshToken();
     }
     // service accounts: no refresh token; instead use private key to request new access token
     JsonWebSignature.Header header = new JsonWebSignature.Header();
     header.setAlgorithm("RS256");
     header.setType("JWT");
     header.setKeyId(serviceAccountPrivateKeyId);
     JsonWebToken.Payload payload = new JsonWebToken.Payload();
     long currentTime = getClock().currentTimeMillis();
     payload.setIssuer(serviceAccountId);
     payload.setAudience(getTokenServerEncodedUrl());
     payload.setIssuedAtTimeSeconds(currentTime / 1000);
     payload.setExpirationTimeSeconds(currentTime / 1000 + 3600);
     payload.setSubject(serviceAccountUser);
     payload.put("scope", Joiner.on(' ').join(serviceAccountScopes));
     try {
       String assertion = JsonWebSignature.signUsingRsaSha256(
           serviceAccountPrivateKey, getJsonFactory(), header, payload);
       TokenRequest request = new TokenRequest(
           getTransport(), getJsonFactory(), new GenericUrl(getTokenServerEncodedUrl()),
           "urn:ietf:params:oauth:grant-type:jwt-bearer");
       request.put("assertion", assertion);
       return request.execute();
     } catch (GeneralSecurityException exception) {
       IOException e = new IOException();
       e.initCause(exception);
       throw e;
     }
   }

   /**
    * @return Returns the service account ID (typically an e-mail address) or {@code null} if not using the
    * service account flow.
    */
   public final String getServiceAccountId() {
     return serviceAccountId;
   }

   /**
    * @return  Returns the service account Project ID or {@code null} if not present, either because this is
    * not using the service account flow, or is using an older version of the service account
    * configuration.
    */
   public final String getServiceAccountProjectId() {
     return serviceAccountProjectId;
   }

   /**
    * @return Returns a collection of OAuth scopes to use with the service account flow or {@code null}
    * if not using the service account flow.
    */
   public final Collection<String> getServiceAccountScopes() {
     return serviceAccountScopes;
   }

   /**
    * @return Returns the space-separated OAuth scopes to use with the service account flow or
    * {@code null} if not using the service account flow.
    *
    * @since 1.15
    */
   public final String getServiceAccountScopesAsString() {
     return serviceAccountScopes == null ? null : Joiner.on(' ').join(serviceAccountScopes);
   }

   /**
    * @return Returns the private key to use with the service account flow or {@code null} if not using
    * the service account flow.
    */
   public final PrivateKey getServiceAccountPrivateKey() {
     return serviceAccountPrivateKey;
   }

   /**
    * {@link Beta} 
    * @return Returns the ID of the private key to use with the service account flow or {@code null} if
    * not using the service account flow.
    */
   @Beta
   public final String getServiceAccountPrivateKeyId() {
     return serviceAccountPrivateKeyId;
   }

   /**
    * @return Returns the email address of the user the application is trying to impersonate in the service
    * account flow or {@code null} for none or if not using the service account flow.
    */
   public final String getServiceAccountUser() {
     return serviceAccountUser;
   }

   /**
    * {@link Beta} 
    * @return Indicates whether the credential requires scopes to be specified by calling createScoped
    * before use.
    */
   @Beta
   public boolean createScopedRequired() {
     if (serviceAccountPrivateKey == null) {
       return false;
     }
     return (serviceAccountScopes == null || serviceAccountScopes.isEmpty());
   }

   
   /**
    * Google credential builder.
    *
    * <p>
    * Implementation is not thread-safe.
    * </p>
    */
   public static class Builder extends Credential.Builder {

     /** Service account ID (typically an e-mail address) or {@code null} for none. */
     String serviceAccountId;

     /**
      * Collection of OAuth scopes to use with the service account flow or {@code null} for none.
      */
     Collection<String> serviceAccountScopes;

     /** Private key to use with the service account flow or {@code null} for none. */
     PrivateKey serviceAccountPrivateKey;

     /** Id of the private key to use with the service account flow or {@code null} for none. */
     String serviceAccountPrivateKeyId;

     /** Project Id associated with the Service Account */
     String serviceAccountProjectId;

     /**
      * Email address of the user the application is trying to impersonate in the service account
      * flow or {@code null} for none.
      */
     String serviceAccountUser;

     public Builder() {
       super(BearerToken.authorizationHeaderAccessMethod());
       setTokenServerEncodedUrl(XeroOAuthConstants.TOKEN_SERVER_URL);
     }

     @Override
     public XeroCredential build() {
       return new XeroCredential(this);
     }

     @Override
     public Builder setTransport(HttpTransport transport) {
       return (Builder) super.setTransport(transport);
     }

     @Override
     public Builder setJsonFactory(JsonFactory jsonFactory) {
       return (Builder) super.setJsonFactory(jsonFactory);
     }

     /**
      * @since 1.9
      */
     @Override
     public Builder setClock(Clock clock) {
       return (Builder) super.setClock(clock);
     }

     /**
      * Sets the client identifier and secret.
      *
      * Overriding is only supported for the purpose of calling the super implementation and changing
      * the return type, but nothing else.
      * @param clientId the string that represents the API key
      * @param clientSecret the string that represents the API secret
      * @return an instance of XeroCredential class
      */
     public Builder setClientSecrets(String clientId, String clientSecret) {
       setClientAuthentication(new ClientParametersAuthentication(clientId, clientSecret));
       return this;
     }


     /**
      * @return Returns a collection of OAuth scopes to use with the service account flow or {@code null}
      * for none.
      */
     public final Collection<String> getServiceAccountScopes() {
       return serviceAccountScopes;
     }

    

     @Override
     public Builder setRequestInitializer(HttpRequestInitializer requestInitializer) {
       return (Builder) super.setRequestInitializer(requestInitializer);
     }

     @Override
     public Builder addRefreshListener(CredentialRefreshListener refreshListener) {
       return (Builder) super.addRefreshListener(refreshListener);
     }

     @Override
     public Builder setRefreshListeners(Collection<CredentialRefreshListener> refreshListeners) {
       return (Builder) super.setRefreshListeners(refreshListeners);
     }

     @Override
     public Builder setTokenServerUrl(GenericUrl tokenServerUrl) {
       return (Builder) super.setTokenServerUrl(tokenServerUrl);
     }

     @Override
     public Builder setTokenServerEncodedUrl(String tokenServerEncodedUrl) {
       return (Builder) super.setTokenServerEncodedUrl(tokenServerEncodedUrl);
     }

     @Override
     public Builder setClientAuthentication(HttpExecuteInterceptor clientAuthentication) {
       return (Builder) super.setClientAuthentication(clientAuthentication);
     }
   }
 
 }
