package com.xero.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.String.format;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonConfig implements Config {

	private String SDK_VERSION = "2.2.18";
  private String APP_TYPE = "Public";
  private String USER_AGENT = "Xero-Java-SDK";
  private String ACCEPT = "application/xml";
  private String CONSUMER_KEY;
  private String CONSUMER_SECRET;
  private String API_BASE_URL = "https://api.xero.com";
  private String API_ENDPOINT_URL = "https://api.xero.com/api.xro/2.0/";
  private String FILES_ENDPOINT_URL = "https://api.xero.com/files.xro/1.0/";
  private String ASSETS_ENDPOINT_URL = "https://api.xero.com/assets.xro/1.0";
  private String BANKFEEDS_ENDPOINT_URL = "https://api.xero.com/bankfeeds.xro/1.0";
  private String REQUEST_TOKEN_URL = "https://api.xero.com/oauth/RequestToken";
  private String AUTHENTICATE_URL = "https://api.xero.com/oauth/Authorize";
  private String ACCESS_TOKEN_URL = "https://api.xero.com/oauth/AccessToken";
  private String API_ENDPOINT_STEM = "/api.xro/2.0/";
  private String FILES_ENDPOINT_STEM = "/files.xro/1.0/";
  private String ASSETS_ENDPOINT_STEM = "/assets.xro/1.0/";
  private String BANKFEEDS_ENDPOINT_STEM = "/bankfeeds.xro/1.0/";
  private String REQUEST_TOKEN_STEM = "/oauth/RequestToken";
  private String AUTHENTICATE_STEM = "/oauth/Authorize";
  private String ACCESS_TOKEN_STEM = "/oauth/AccessToken";

  private String CALLBACK_BASE_URL;
  private String AUTH_CALLBACK_URL;
  private String PATH_TO_PRIVATE_KEY_CERT;
  private String PRIVATE_KEY_PASSWORD;
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
  private String configFile;
  private static Config instance = null;
  final static Logger logger = LogManager.getLogger(JsonConfig.class);
  
  public JsonConfig(String configFile) {
    this.configFile = configFile;
    
	load(); 
  }

  /* Static 'instance' method */
  public static Config getInstance() {
    if (instance == null) {
      instance = new JsonConfig("config.json");
    }
    return instance;
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
  public String getFilesUrl() {
    return FILES_ENDPOINT_URL;
  }
  
  @Override
  public String getAssetsUrl() {
    return ASSETS_ENDPOINT_URL;
  }
  
  @Override
  public String getBankFeedsUrl() {
    return BANKFEEDS_ENDPOINT_URL;
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
    return USER_AGENT + " " + CONSUMER_KEY + " [Xero-Java-" + SDK_VERSION + "]";
  }

  @Override
  public String getAccept() {
    return ACCEPT;
  }

  @Override
  public String getRedirectUri() {
    return AUTH_CALLBACK_URL;
  }

  @Override
  public String getProxyHost() {
    return PROXY_HOST;
  }

  @Override
  public long getProxyPort() {
    return PROXY_PORT;
  }

  @Override
  public boolean getProxyHttpsEnabled() {
    return PROXY_HTTPS_ENABLED;
  }

  @Override
  public int getConnectTimeout() {
    // in seconds
    return CONNECT_TIMEOUT;
  }

  @Override
  public int getReadTimeout() {
    // in seconds
    return READ_TIMEOUT;
  }

  @Override
  public String getDecimalPlaces(){
    // 2 or 4
    return DECIMAL_PLACES;
  }

  @Override
  public boolean isUsingAppFirewall() {
    return USING_APP_FIREWALL;
  }

  @Override
  public String getAppFirewallHostname() {
    return APP_FIREWALL_HOSTNAME;
  }

  @Override
  public String getAppFirewallUrlPrefix() {
    return APP_FIREWALL_URL_PREFIX;
  }
  
  @Override
  public String getKeyStorePath() {
    return KEY_STORE_PATH;
  }
  
  @Override
  public String getKeyStorePassword() {
    return KEY_STORE_PASSWORD;
  }

// SETTERS

  @Override
  public void setConsumerKey(String consumerKey) {
    CONSUMER_KEY = consumerKey;
  }

  @Override
  public void setConsumerSecret(String consumerSecret) {
    CONSUMER_SECRET = consumerSecret;
  }

  @Override
  public void setAppType(String appType) {
    APP_TYPE = appType;
  }

  @Override
  public void setAuthCallBackUrl(String authCallbackUrl) {
    AUTH_CALLBACK_URL = authCallbackUrl;
  }

  @Override
  public void setConnectTimeout(int connectTimeout) {
    // in seconds
    CONNECT_TIMEOUT = connectTimeout;
  }

  @Override
  public void setReadTimeout(int readTimeout) {
    // in seconds
    READ_TIMEOUT = readTimeout;
  }

   @Override
  public void setDecimalPlaces(String decimalPlaces) {
    // 2 or 4
    DECIMAL_PLACES = decimalPlaces;
  }

  @Override
    public void setUsingAppFirewall(boolean usingAppFirewall) {
      this.USING_APP_FIREWALL = usingAppFirewall;
  }

  @Override
  public void setAppFirewallHostname(String appFirewallHostname) {
      this.APP_FIREWALL_HOSTNAME = appFirewallHostname;
  }

  @Override
  public void setAppFirewallUrlPrefix(String appFirewallUrlPrefix) {
      this.APP_FIREWALL_URL_PREFIX = appFirewallUrlPrefix;
  }
  
  @Override
  public void setKeyStorePath(String keyStorePath) {
      this.KEY_STORE_PATH = keyStorePath;
  }
  
  @Override
  public void setKeyStorePassword(String keyStorePassword) {
      this.KEY_STORE_PASSWORD = keyStorePassword;
  }

  private void load() {  
	InputStream inputStream = JsonConfig.class.getResourceAsStream("/" + configFile);
    
	if (inputStream == null) {
  		logger.error(format("Config file '%s' could not be found in src/resources folder. Missing file?", configFile));
		throw new XeroClientException(format("Config file '%s' could not be opened. Missing file?", configFile));    
	} else {
		JSONObject jsonObject;
		try(InputStreamReader reader = new InputStreamReader(inputStream)) {
			JSONParser parser = new JSONParser();
			jsonObject = (JSONObject) parser.parse(reader);
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new XeroClientException(format("Config file '%s' not found", configFile), e);
		} catch (IOException e) {
			logger.error(e);
			throw new XeroClientException(format("IO error reading config file '%s' not found", configFile), e);
		} catch (ParseException e) {
			logger.error(e);
			throw new XeroClientException(format("Parse error reading config file '%s' not found", configFile), e);
		}

	    if (jsonObject.containsKey("AppType")) {
	      APP_TYPE = (String) jsonObject.get("AppType");
	    }
	
	    if (jsonObject.containsKey("UserAgent")) {
	      USER_AGENT = (String) jsonObject.get("UserAgent");
	    }
	
	    if (jsonObject.containsKey("Accept")) {
	      ACCEPT = (String) jsonObject.get("Accept");
	    }
	
	    if (jsonObject.containsKey("ConsumerKey")) {
	      CONSUMER_KEY = (String) jsonObject.get("ConsumerKey");
	    }
	
	    if (jsonObject.containsKey("ConsumerSecret")) {
	      CONSUMER_SECRET = (String) jsonObject.get("ConsumerSecret");
	    }
	
	    if (jsonObject.containsKey("ApiBaseUrl")) {
	      API_BASE_URL = (String) jsonObject.get("ApiBaseUrl");
	      if (jsonObject.containsKey("ApiEndpointPath")) {
	        String endpointPath = (String) jsonObject.get("ApiEndpointPath");
	        API_ENDPOINT_URL = API_BASE_URL + endpointPath;
	      } else {
	    	  	API_ENDPOINT_URL = API_BASE_URL + API_ENDPOINT_STEM;
	    	  }
	
	      if (jsonObject.containsKey("FilesEndpointPath")) {
	        String filesEndpointPath = (String) jsonObject.get("FilesEndpointPath");
	        FILES_ENDPOINT_URL = API_BASE_URL + filesEndpointPath;
	      } else {
	    	  	FILES_ENDPOINT_URL = API_BASE_URL + FILES_ENDPOINT_STEM;
	      }
	      
	      if (jsonObject.containsKey("AssetsEndpointPath")) {
	    	  	String assetsEndpointPath = (String) jsonObject.get("AssetsEndpointPath");
	    	  		ASSETS_ENDPOINT_URL = API_BASE_URL + assetsEndpointPath;
	      } else {
	      		ASSETS_ENDPOINT_URL = API_BASE_URL + ASSETS_ENDPOINT_STEM;
	      }
	      
	      if (jsonObject.containsKey("BankFeedsEndpointPath")) {
	    	  	String BankFeedsEndpointPath = (String) jsonObject.get("BankFeedsEndpointPath");
	    	  	BANKFEEDS_ENDPOINT_URL = API_BASE_URL + BankFeedsEndpointPath;
	      } else {
	    	  BANKFEEDS_ENDPOINT_URL = API_BASE_URL + BANKFEEDS_ENDPOINT_STEM;
	      }
	      
	      if (jsonObject.containsKey("RequestTokenPath")) {
	        String requestPath = (String) jsonObject.get("RequestTokenPath");
	        REQUEST_TOKEN_URL = API_BASE_URL + requestPath;
	      } else {
	    	  	REQUEST_TOKEN_URL = API_BASE_URL + REQUEST_TOKEN_STEM;            
	      }
	
	      if (jsonObject.containsKey("AccessTokenPath")) {
	        String accessPath = (String) jsonObject.get("AccessTokenPath");
	        ACCESS_TOKEN_URL = API_BASE_URL + accessPath;
	      } else {
	    	  	ACCESS_TOKEN_URL = API_BASE_URL + ACCESS_TOKEN_STEM;
	      }
	
	      if (jsonObject.containsKey("AuthenticateUrl")) {
	        String authenticatePath = (String) jsonObject.get("AuthenticateUrl");
	        AUTHENTICATE_URL = API_BASE_URL + authenticatePath;
	      } else {
	    	  	AUTHENTICATE_URL = API_BASE_URL + AUTHENTICATE_STEM;
	      }
	    }
	
	    if (jsonObject.containsKey("CallbackBaseUrl")) {
	      CALLBACK_BASE_URL = (String) jsonObject.get("CallbackBaseUrl");
	      if (jsonObject.containsKey("CallbackPath")) {
	        String callbackPath = (String) jsonObject.get("CallbackPath");
	        AUTH_CALLBACK_URL = CALLBACK_BASE_URL + callbackPath;
	      }
	    }
	
	    if (jsonObject.containsKey("PrivateKeyCert")) {
	      PATH_TO_PRIVATE_KEY_CERT = (String) jsonObject.get("PrivateKeyCert");
	      PRIVATE_KEY_PASSWORD = (String) jsonObject.get("PrivateKeyPassword");
	    }
	
	    if (jsonObject.containsKey("ProxyHost")) {
	      PROXY_HOST = (String) jsonObject.get("ProxyHost");
	      if (jsonObject.containsKey("ProxyPort")) {
	        PROXY_PORT = (long) jsonObject.get("ProxyPort");
	      }
	
	      if (jsonObject.containsKey("ProxyHttpsEnabled")) {
	        PROXY_HTTPS_ENABLED = (boolean) jsonObject.get("ProxyHttpsEnabled");
	      }
	    }
	
	    if (jsonObject.containsKey("DecimalPlaces")) {
	    		DECIMAL_PLACES = (String) jsonObject.get("DecimalPlaces");
	    }
	    
	    if (jsonObject.containsKey("KeyStorePath")) {
			KEY_STORE_PATH = (String) jsonObject.get("KeyStorePath");
	    }
	    
	    if (jsonObject.containsKey("KeyStorePassword")) {
			KEY_STORE_PASSWORD = (String) jsonObject.get("KeyStorePassword");
	    }
	
	    if (jsonObject.containsKey("usingAppFirewall")) {
	        USING_APP_FIREWALL = (boolean) jsonObject.get("usingAppFirewall");
	
	        if (jsonObject.containsKey("appFirewallHostname")) {
	            APP_FIREWALL_HOSTNAME = (String) jsonObject.get("appFirewallHostname");
	        }
	
	        if (jsonObject.containsKey("appFirewallUrlPrefix")) {
	            APP_FIREWALL_URL_PREFIX = (String) jsonObject.get("appFirewallUrlPrefix");
	        }
	    }
    }
  }
}
