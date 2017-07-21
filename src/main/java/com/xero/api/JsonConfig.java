package com.xero.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonConfig implements Config {

	private String APP_TYPE = "Public";
	private String USER_AGENT = "Xero-Java-SDK-Default";
	private String ACCEPT = "application/xml";
	private String CONSUMER_KEY;
	private String CONSUMER_SECRET;
	private String API_BASE_URL = "https://api.xero.com";
	private String DEFAULT_API_ENDPOINT_URL = "/api.xro/2.0/";
	private String BANKFEEDS_API_ENDPOINT_URL = "/bankfeeds.xro/1.0/";
	private String REQUEST_TOKEN_URL = "/oauth/RequestToken";
	private String AUTHENTICATE_URL = "/oauth/Authorize";
	private String ACCESS_TOKEN_URL = "/oauth/AccessToken";
	private String CALLBACK_BASE_URL;
	private String AUTH_CALLBACK_URL;
	private String PATH_TO_PRIVATE_KEY_CERT;
	private String PRIVATE_KEY_PASSWORD;
	private String PROXY_HOST;
	private long PROXY_PORT = 80;
	private boolean PROXY_HTTPS_ENABLED = false;
	
	private int CONNECT_TIMEOUT = 60;
	private String configFile;
	
	private static Config instance = null;
   
	public JsonConfig(String configFile) {
		this.configFile = configFile;
		load();
	}
	   
	/* Static 'instance' method */
	public static Config getInstance() 
	{
		if(instance == null) 
		{
			instance = new JsonConfig("config.json");
		}
	    return instance;
	}
	
	@Override
	public String getAppType()
	{
		return APP_TYPE;
	}
	  
	@Override
	public String getPrivateKeyPassword()
	{
		return PRIVATE_KEY_PASSWORD;
	}
	  
	@Override
	public String getPathToPrivateKey()
	{
		return PATH_TO_PRIVATE_KEY_CERT;
	}

	@Override
	public String getConsumerKey()
	{
		return CONSUMER_KEY;
	}
	  
	@Override
	public String getConsumerSecret()
	{
		return CONSUMER_SECRET;
	}
	  
	@Override
	public String getApiUrl()
	{
		return API_BASE_URL + DEFAULT_API_ENDPOINT_URL;
	}
	
	@Override
	public String getApiUrl(String resource)
	{
		if("".equals(resource) || resource == null) {
			return getApiUrl();
		}
		
		//Check the resource to determine which API to use.
		resource = resource.toLowerCase();
		String endpointUrl;
		
		switch (resource) {
		case "feedconnections":
			endpointUrl = BANKFEEDS_API_ENDPOINT_URL + resource;
			break;
		default:
			endpointUrl = DEFAULT_API_ENDPOINT_URL + resource;
			break;
		}
		return API_BASE_URL + endpointUrl;
	}
	  
	@Override
	public String getRequestTokenUrl()
	{
		return API_BASE_URL + REQUEST_TOKEN_URL;
	}
	  
	@Override
	public String getAuthorizeUrl()
	{
		return API_BASE_URL + AUTHENTICATE_URL;
	}
	  
	@Override
	public String getAccessTokenUrl()
	{
		return API_BASE_URL + ACCESS_TOKEN_URL;
	}
	  
	@Override
	public String getUserAgent()
	{
		return USER_AGENT + " [Xero-Java-0.4.5]";
	}
	  
	@Override
	public String getAccept()
	{
		return ACCEPT;
	}
	  
	@Override
	public String getRedirectUri()
	{
		return AUTH_CALLBACK_URL;
	}
	
	@Override
	public String getProxyHost()
	{
		return PROXY_HOST;
	}
	
	@Override
	public long getProxyPort()
	{
		return PROXY_PORT;
	}
	
	@Override
	public boolean getProxyHttpsEnabled()
	{
		return PROXY_HTTPS_ENABLED;
	}
	
	@Override
	public int getConnectTimeout()
	{
		// in seconds
		return CONNECT_TIMEOUT;
	}
	
	@Override
	public void setConnectTimeout(int connectTimeout)
	{
		// in seconds
		CONNECT_TIMEOUT = connectTimeout;
	}

	@Override
	public void setConsumerKey(String consumerKey)
	{
		// in seconds
		CONSUMER_KEY = consumerKey;
	}

	@Override
	public void setConsumerSecret(String consumerSecret)
	{
		// in seconds
		CONSUMER_SECRET = consumerSecret;
	}

	@Override
	public void setAppType(String appType)
	{
		// in seconds
		APP_TYPE = appType;
	}

	@Override
	public void setAuthCallBackUrl(String authCallbackUrl)
	{
		// in seconds
		AUTH_CALLBACK_URL = authCallbackUrl;
	}
	  
	public void load() 
	{
		InputStream inputStream = JsonConfig.class.getResourceAsStream("/" + configFile);
		InputStreamReader reader = new InputStreamReader(inputStream);

		JSONParser parser = new JSONParser();
		  
		Object obj = null;
		try {
			obj = parser.parse(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = (JSONObject) obj;
		
		if (jsonObject.containsKey("AppType")) 
		{
			APP_TYPE = (String) jsonObject.get("AppType");
		}
			
		if (jsonObject.containsKey("UserAgent")) 
		{
			USER_AGENT = (String) jsonObject.get("UserAgent");
		}
		
		if (jsonObject.containsKey("Accept")) 
		{
			ACCEPT = (String) jsonObject.get("Accept");
		}
		
		if (jsonObject.containsKey("ConsumerKey")) 
		{
			CONSUMER_KEY = (String) jsonObject.get("ConsumerKey");
		}
		
		if (jsonObject.containsKey("ConsumerSecret")) 
		{
			CONSUMER_SECRET = (String) jsonObject.get("ConsumerSecret");
		}
		
		if (jsonObject.containsKey("ApiBaseUrl")) 
		{
			API_BASE_URL = (String) jsonObject.get("ApiBaseUrl");
			if (jsonObject.containsKey("ApiEndpointPath")) 
			{	
				String endpointPath = (String) jsonObject.get("ApiEndpointPath");
				DEFAULT_API_ENDPOINT_URL = API_BASE_URL + endpointPath;
			}
			
			if (jsonObject.containsKey("RequestTokenPath")) 
			{
				String requestPath = (String) jsonObject.get("RequestTokenPath");
				REQUEST_TOKEN_URL = API_BASE_URL + requestPath;
			}
			
			if (jsonObject.containsKey("AccessTokenPath")) 
			{
				String accessPath = (String) jsonObject.get("AccessTokenPath");
				ACCESS_TOKEN_URL = API_BASE_URL + accessPath;
			}
			
			if (jsonObject.containsKey("AuthenticateUrl")) 
			{
				String authenticatePath = (String) jsonObject.get("AuthenticateUrl");
				AUTHENTICATE_URL = API_BASE_URL + authenticatePath;
			}
		}

		if (jsonObject.containsKey("CallbackBaseUrl")) 
		{
			CALLBACK_BASE_URL = (String) jsonObject.get("CallbackBaseUrl");
			if (jsonObject.containsKey("CallbackPath")) 
			{
				String callbackPath = (String) jsonObject.get("CallbackPath");
				AUTH_CALLBACK_URL = CALLBACK_BASE_URL + callbackPath;
			}
		}
		
		if (jsonObject.containsKey("PrivateKeyCert")) 
		{
			PATH_TO_PRIVATE_KEY_CERT = (String) jsonObject.get("PrivateKeyCert");
			PRIVATE_KEY_PASSWORD = (String) jsonObject.get("PrivateKeyPassword");
		}
		
		if (jsonObject.containsKey("ProxyHost")) 
		{
			PROXY_HOST = (String) jsonObject.get("ProxyHost");
			if (jsonObject.containsKey("ProxyPort")) 
			{
				PROXY_PORT = (long) jsonObject.get("ProxyPort");
			}
			
			if (jsonObject.containsKey("ProxyHttpsEnabled")) 
			{
				PROXY_HTTPS_ENABLED = (boolean) jsonObject.get("ProxyHttpsEnabled");
			}
		}
	}

}
