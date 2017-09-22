package com.xero.example;

import com.xero.api.Config;
import org.springframework.core.env.Environment;

public class SpringConfig implements Config {

    private String APP_TYPE = "Public";
    private String USER_AGENT = "Xero-Java-SDK-Default";
    private String ACCEPT = "application/xml";
    private String CONSUMER_KEY;
    private String CONSUMER_SECRET;
    private String API_BASE_URL = "https://api.xero.com";
    private String API_ENDPOINT_URL = "https://api.xero.com/api.xro/2.0/";
    private String REQUEST_TOKEN_URL = "https://api.xero.com/oauth/RequestToken";
    private String AUTHENTICATE_URL = "https://api.xero.com/oauth/Authorize";
    private String ACCESS_TOKEN_URL = "https://api.xero.com/oauth/AccessToken";
    private String CALLBACK_BASE_URL;
    private String AUTH_CALLBACK_URL;
    private String PATH_TO_PRIVATE_KEY_CERT;
    private String PRIVATE_KEY_PASSWORD;

    private String prefix = "";
    private Environment environment;

    public SpringConfig(Environment environment) {
        this("", environment);
    }

    public SpringConfig(String prefix, Environment environment) {
        this.prefix = prefix;
        this.environment = environment;
        load();
    }

    @Override
    public String getAppType() {
        return APP_TYPE;
    }

    @Override
    public String getPrivateKeyPassword() {
        return PRIVATE_KEY_PASSWORD;
    }

    @Override
    public String getPathToPrivateKey() {
        return PATH_TO_PRIVATE_KEY_CERT;
    }

    @Override
    public String getConsumerKey() {
        return CONSUMER_KEY;
    }

    @Override
    public String getConsumerSecret() {
        return CONSUMER_SECRET;
    }

    @Override
    public String getApiUrl() {
        return API_ENDPOINT_URL;
    }

    @Override
    public String getRequestTokenUrl() {
        return REQUEST_TOKEN_URL;
    }

    @Override
    public String getAuthorizeUrl() {
        return AUTHENTICATE_URL;
    }

    @Override
    public String getAccessTokenUrl() {
        return ACCESS_TOKEN_URL;
    }

    @Override
    public String getUserAgent() {
        return USER_AGENT;
    }

    @Override
    public String getAccept() {
        return ACCEPT;
    }

    @Override
    public String getRedirectUri() {
        return AUTH_CALLBACK_URL;
    }

    public void load() {

        if (environment.containsProperty(prefix + "AppType")) {
            APP_TYPE = environment.getProperty(prefix + "AppType");
        }

        if (environment.containsProperty(prefix + "UserAgent")) {
            USER_AGENT = environment.getProperty(prefix + "UserAgent");
        }

        if (environment.containsProperty(prefix + "Accept")) {
            ACCEPT = environment.getProperty(prefix + "Accept");
        }

        if (environment.containsProperty(prefix + "ConsumerKey")) {
            CONSUMER_KEY = environment.getProperty(prefix + "ConsumerKey");
        }

        if (environment.containsProperty(prefix + "ConsumerSecret")) {
            CONSUMER_SECRET = environment.getProperty(prefix + "ConsumerSecret");
        }

        if (environment.containsProperty(prefix + "ApiBaseUrl")) {
            API_BASE_URL = environment.getProperty(prefix + "ApiBaseUrl");
            if (environment.containsProperty(prefix + "ApiEndpointPath")) {
                String endpointPath = environment.getProperty(prefix + "ApiEndpointPath");
                API_ENDPOINT_URL = API_BASE_URL + endpointPath;
            }

            if (environment.containsProperty(prefix + "RequestTokenPath")) {
                String requestPath = environment.getProperty(prefix + "RequestTokenPath");
                REQUEST_TOKEN_URL = API_BASE_URL + requestPath;
            }

            if (environment.containsProperty(prefix + "AccessTokenPath")) {
                String accessPath = environment.getProperty(prefix + "AccessTokenPath");
                ACCESS_TOKEN_URL = API_BASE_URL + accessPath;
            }

            if (environment.containsProperty(prefix + "AuthenticateUrl")) {
                String authenticatePath = environment.getProperty(prefix + "AuthenticateUrl");
                AUTHENTICATE_URL = API_BASE_URL + authenticatePath;
            }
        }

        if (environment.containsProperty(prefix + "CallbackBaseUrl")) {
            CALLBACK_BASE_URL = environment.getProperty(prefix + "CallbackBaseUrl");
            if (environment.containsProperty(prefix + "CallbackPath")) {
                String callbackPath = environment.getProperty(prefix + "CallbackPath");
                AUTH_CALLBACK_URL = CALLBACK_BASE_URL + callbackPath;
            }
        }

        if (environment.containsProperty(prefix + "PrivateKeyCert")) {
            PATH_TO_PRIVATE_KEY_CERT = environment.getProperty(prefix + "PrivateKeyCert");
            PRIVATE_KEY_PASSWORD = environment.getProperty(prefix + "PrivateKeyPassword");
        }
    }

}
