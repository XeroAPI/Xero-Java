package com.xero.api;

import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthRsaSigner;

public class HmacSigner implements Signer {

	private OAuthHmacSigner signer = new OAuthHmacSigner();
	private Config config;

	public HmacSigner(Config config)  
	{
		this.config = config;
	}
	
	public OAuthRsaSigner createRsaSigner() 
	{
		return null;
	}

	public OAuthHmacSigner createHmacSigner(String tokenSecret) 
	{
		signer.tokenSharedSecret = tokenSecret;
		signer.clientSharedSecret = config.getConsumerSecret();
		return signer;
	}

	public OAuthHmacSigner createHmacSigner() 
	{
		signer.clientSharedSecret = config.getConsumerSecret();
		return signer;
	}

}
