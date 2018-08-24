package com.xero.api;

public interface Config {

    String getAppType();

    String getPrivateKeyPassword();

    String getPathToPrivateKey();

    String getConsumerKey();

    String getConsumerSecret();

    String getApiUrl();

    String getFilesUrl();
    
    String getAssetsUrl();
    
    String getFeedConnectionsUrl();

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

    String getDecimalPlaces();

    String getAppFirewallHostname();

    String getAppFirewallUrlPrefix();
    
    String getKeyStorePath();
    
    String getKeyStorePassword();

    boolean isUsingAppFirewall();

    // SETTERS

    void setConsumerKey(String consumerKey);

    void setConsumerSecret(String consumerSecret);

    void setAppType(String appType);

    void setAuthCallBackUrl(String authCallbackUrl);

    void setConnectTimeout(int connectTimeout);

    void setReadTimeout(int readTimeout);

    void setDecimalPlaces(String decimalPlaces);

    void setUsingAppFirewall(boolean usingAppFirewall);

    void setAppFirewallHostname(String appFirewallHostname);

    void setAppFirewallUrlPrefix(String appFirewallUrlPrefix);
    
    void setKeyStorePath(String keyStorePath);
    
    void setKeyStorePassword(String keyStorePassword);
}
