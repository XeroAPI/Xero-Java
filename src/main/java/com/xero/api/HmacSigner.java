package com.xero.api;

import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthRsaSigner;

public class HmacSigner implements Signer {

	private OAuthHmacSigner signer = new OAuthHmacSigner();
	private Config c = Config.getInstance();

	public HmacSigner()  
	{
		
	}
	
	public OAuthRsaSigner createRsaSigner() 
	{
		return null;
	}

	public OAuthHmacSigner createHmacSigner(String tokenSecret) 
	{
		signer.tokenSharedSecret = tokenSecret;
		signer.clientSharedSecret = c.getConsumerSecret();
		return signer;
	}

	public OAuthHmacSigner createHmacSigner() 
	{
		signer.clientSharedSecret = c.getConsumerSecret();
		return signer;
	}

}
