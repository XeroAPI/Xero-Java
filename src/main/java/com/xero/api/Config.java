package com.xero.api;

public interface Config {

    String getAppType();

    String getPrivateKeyPassword();

    String getPathToPrivateKey();

    String getConsumerKey();

    String getConsumerSecret();

    String getApiUrl();

    String getRequestTokenUrl();

    String getAuthorizeUrl();

    String getAccessTokenUrl();

    String getUserAgent();

    String getAccept();

    String getRedirectUri();
    
    int getConnectTimeout();
    
    // in seconds
    void setConnectTimeout(int connectTimeout);

}
