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
    
    String getProxyHost();
    
    long getProxyPort();
    
    boolean getProxyHttpsEnabled();
    
    int getConnectTimeout();

    int getReadTimeout();
    
    // in seconds
    void setConnectTimeout(int connectTimeout);
    void setReadTimeout(int readTimeout);

    void setConsumerKey(String consumerKey);

    void setConsumerSecret(String consumerSecret);

    void setAppType(String appType);

    void setAuthCallBackUrl(String authCallbackUrl);

}
