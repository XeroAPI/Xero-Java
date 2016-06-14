package com.xero.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config {

	private String APP_TYPE;
	private String USER_AGENT;
	private String ACCEPT;
	private String CONSUMER_KEY;
	private String CONSUMER_SECRET;
	private String API_BASE_URL;
	private String API_ENDPOINT_URL;
	private String REQUEST_TOKEN_URL;
	private String AUTHENTICATE_URL;
	private String ACCESS_TOKEN_URL;
	private String CALLBACK_BASE_URL;
	private String AUTH_CALLBACK_URL;
	private String SIGNATURE_METHOD;
	private String PATH_TO_PRIVATE_KEY_CERT;
	private String PRIVATE_KEY_PASSWORD;
	private String PATH_TO_ENTRUST_SSL_CERT;
	private String ENTRUST_SSL_PASSWORD;
		
	private static Config instance = null;
   
	private Config(){ }
	   
	/* Static 'instance' method */
	public static Config getInstance() 
	{
		if(instance == null) 
		{
			instance = new Config();
			instance.load();
		}
	    return instance;
	}
	
	public String getAppType() 
	{
		return APP_TYPE;
	}
	  
	public String getEntrustPassword() 
	{
		return ENTRUST_SSL_PASSWORD;
	}
	  
	public String getPathToEntrust() 
	{
		return PATH_TO_ENTRUST_SSL_CERT;
	}
	  
	public String getPrivateKeyPassword() 
	{
		return PRIVATE_KEY_PASSWORD;
	}
	  
	public String getPathToPrivateKey() 
	{
		return PATH_TO_PRIVATE_KEY_CERT;
	}
	  
	public String getSignatureMethod() 
	{
		return SIGNATURE_METHOD;
	}

	public String getConsumerKey() 
	{
		return CONSUMER_KEY;
	}
	  
	public String getConsumerSecret() 
	{
		return CONSUMER_SECRET;
	}
	  
	public String getApiUrl() 
	{
		return API_ENDPOINT_URL;
	}
	  
	public String getRequestTokenUrl() 
	{
		return REQUEST_TOKEN_URL;
	}
	  
	public String getAuthorizeUrl() 
	{
		return AUTHENTICATE_URL;
	}
	  
	public String getAccessTokenUrl() 
	{
		return ACCESS_TOKEN_URL;
	}
	  
	public String getUserAgent() 
	{
		return USER_AGENT;
	}
	  
	public String getAccept() 
	{
		return ACCEPT;
	}
	  
	public String getRedirectUri() 
	{
		return AUTH_CALLBACK_URL;
	}
	  
	public void load() 
	{
		
		final ClassLoader loader = Config.class.getClassLoader();
		URL path = loader.getResource("config.json");
		File f = new File(path.getFile());
		
		JSONParser parser = new JSONParser();
		  
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(f));
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
				API_ENDPOINT_URL = API_BASE_URL + endpointPath;
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
		}
		
		if (jsonObject.containsKey("AuthenticateUrl")) 
		{
			AUTHENTICATE_URL = (String) jsonObject.get("AuthenticateUrl");
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
		
		if (jsonObject.containsKey("EntrustCert")) 
		{
			String entrustCert = (String) jsonObject.get("EntrustCert");
			URL entrustPath = loader.getResource(entrustCert);
			PATH_TO_ENTRUST_SSL_CERT = entrustPath.getPath();	
			ENTRUST_SSL_PASSWORD = (String) jsonObject.get("EntrustCertPassword");
		}
		
		if (jsonObject.containsKey("PrivateKeyCert")) 
		{
			String privateKeyCert = (String) jsonObject.get("PrivateKeyCert");
			URL privateKeyPath = loader.getResource(privateKeyCert);
			PATH_TO_PRIVATE_KEY_CERT = privateKeyPath.getPath();
			PRIVATE_KEY_PASSWORD = (String) jsonObject.get("PrivateKeyPassword");
		}
		
		System.out.println("Loading Instance");
	}
}
