package com.xero.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xero.api.Config;

public class CustomJsonConfig implements Config {
	
  private String APP_TYPE = "PARTNER";
  private String USER_AGENT = "Xero-Java-SDK";
  private String ACCEPT = "application/xml";
  private String CONSUMER_KEY = "YOUR_CONSUMER_KEY";
  private String CONSUMER_SECRET = "YOUR_CONSUMER_SECRET";
  private String API_ENDPOINT_URL = "https://api.xero.com/api.xro/2.0/";
  private String FILES_ENDPOINT_URL = "https://api.xero.com/files.xro/1.0/";
  private String REQUEST_TOKEN_URL = "https://api.xero.com/oauth/RequestToken";
  private String AUTHENTICATE_URL = "https://api.xero.com/oauth/Authorize";
  private String ACCESS_TOKEN_URL = "https://api.xero.com/oauth/AccessToken";
  private String AUTH_CALLBACK_URL="http://localhost:8080/xero-sdk-demo-7/CallbackServlet";
  private String PATH_TO_PRIVATE_KEY_CERT = "/certs/public_privatekey.pfx";
  private String PRIVATE_KEY_PASSWORD = "1234";  
  private String PROXY_HOST;
  private long PROXY_PORT = 80;
  private boolean PROXY_HTTPS_ENABLED = false;
  private int CONNECT_TIMEOUT = 60;
  private int READ_TIMEOUT = 60;
  private String DECIMAL_PLACES = null;
  private boolean USING_APP_FIREWALL = false;
  private String APP_FIREWALL_HOSTNAME;
  private String APP_FIREWALL_URL_PREFIX;
  private String KEY_STORE_PATH;
  private String KEY_STORE_PASSWORD;

  final static Logger logger = LogManager.getLogger(CustomJsonConfig.class); 

  public String getAppType() {
    return APP_TYPE;
  }

  public String getPrivateKeyPassword() {
    return PRIVATE_KEY_PASSWORD;
  }

  public String getPathToPrivateKey() {
    return PATH_TO_PRIVATE_KEY_CERT;
  }

  public String getConsumerKey() {
    return CONSUMER_KEY;
  }

  public String getConsumerSecret() {
    return CONSUMER_SECRET;
  }

  public String getApiUrl() {
    return API_ENDPOINT_URL;
  }

  public String getFilesUrl() {
    return FILES_ENDPOINT_URL;
  }

  public String getRequestTokenUrl() {
    return REQUEST_TOKEN_URL;
  }

  public String getAuthorizeUrl() {
    return AUTHENTICATE_URL;
  }

  public String getAccessTokenUrl() {
    return ACCESS_TOKEN_URL;
  }

  public String getUserAgent() {
    return USER_AGENT + " " + CONSUMER_KEY + " [Xero-Java-1.0.8]";
  }

  public String getAccept() {
    return ACCEPT;
  }

  public String getRedirectUri() {
    return AUTH_CALLBACK_URL;
  }

  public String getProxyHost() {
    return PROXY_HOST;
  }

  public long getProxyPort() {
    return PROXY_PORT;
  }

  public boolean getProxyHttpsEnabled() {
    return PROXY_HTTPS_ENABLED;
  }

  public int getConnectTimeout() {
    // in seconds
    return CONNECT_TIMEOUT;
  }

  public int getReadTimeout() {
    // in seconds
    return READ_TIMEOUT;
  }

  public String getDecimalPlaces(){
    // 2 or 4
    return DECIMAL_PLACES;
  }

  public boolean isUsingAppFirewall() {
    return USING_APP_FIREWALL;
  }

  public String getAppFirewallHostname() {
    return APP_FIREWALL_HOSTNAME;
  }

  public String getAppFirewallUrlPrefix() {
    return APP_FIREWALL_URL_PREFIX;
  }
  
  public String getKeyStorePath() {
    return KEY_STORE_PATH;
  }
  
  public String getKeyStorePassword() {
    return KEY_STORE_PASSWORD;
  }
	
  public void setConsumerKey(String consumerKey) { }
	
  public void setConsumerSecret(String consumerSecret) {  }
	
  public void setAppType(String appType) { }
	
  public void setAuthCallBackUrl(String authCallbackUrl) { }
	
  public void setConnectTimeout(int connectTimeout) { }
	
  public void setReadTimeout(int readTimeout) { }
	
  public void setDecimalPlaces(String decimalPlaces) { }
	
  public void setUsingAppFirewall(boolean usingAppFirewall) { }
	
  public void setAppFirewallHostname(String appFirewallHostname) { }
	
  public void setAppFirewallUrlPrefix(String appFirewallUrlPrefix) { }
	
  public void setKeyStorePath(String keyStorePath) { }
	
  public void setKeyStorePassword(String keyStorePassword) { }

}


