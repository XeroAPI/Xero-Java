package com.xero.api;

import java.io.IOException;
import java.util.HashMap;

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth.OAuthSigner;
import com.google.api.client.http.apache.ApacheHttpTransport;

public class OAuthRequestToken 
{

	private String tempToken = null;
	private String tempTokenSecret = null;
	private OAuthGetTemporaryToken tokenRequest = null;
	private Config config;

	public OAuthRequestToken(Config config) 
	{
		this.config = config;
	}

	public void execute() 
	{
		OAuthSigner signer = null;
		if(config.getAppType().equals("PUBLIC"))
		{
			signer = new HmacSigner(config).createHmacSigner();
		}	else {
			signer = new RsaSigner(config).createRsaSigner();
		}

		tokenRequest = new OAuthGetTemporaryToken(config.getRequestTokenUrl());
		tokenRequest.consumerKey = config.getConsumerKey();
		tokenRequest.callback = config.getRedirectUri();

		/* DEPRECATED ENTRUST CERTIFICATE
		if(c.getAppType().equals("PARTNER"))
		{
			tokenRequest.transport = new PartnerHttpClient().getPartnerHttpClient();
		}	else {
			tokenRequest.transport = new ApacheHttpTransport();
		}
		*/
		tokenRequest.transport = new ApacheHttpTransport();

		tokenRequest.signer= signer;

		OAuthCredentialsResponse temporaryTokenResponse = null;
		try 
		{
			temporaryTokenResponse = tokenRequest.execute();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		tempToken = temporaryTokenResponse.token;
		tempTokenSecret = temporaryTokenResponse.tokenSecret;
	}

	public String getTempToken()
	{
		return tempToken;
	}

	public String getTempTokenSecret()
	{
		return tempTokenSecret;
	}

	public HashMap<String, String> getAll(){
		HashMap<String,String> map = new HashMap<String,String>();

		map.put("tempToken",getTempToken());
		map.put("tempTokenSecret",getTempTokenSecret());
		map.put("sessionHandle","");
		map.put("tokenTimestamp","");

		return map;
	}
}