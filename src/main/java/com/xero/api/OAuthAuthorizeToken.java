package com.xero.api;

import com.google.api.client.auth.oauth.OAuthAuthorizeTemporaryTokenUrl;

public class OAuthAuthorizeToken {

	private String authUrl = null;
	
	public OAuthAuthorizeToken(Config c, String tempToken)
	{	
		OAuthAuthorizeTemporaryTokenUrl accessTempToken = new OAuthAuthorizeTemporaryTokenUrl(c.getAuthorizeUrl());
		accessTempToken.temporaryToken = tempToken;
		accessTempToken.set("oauth_callback",c.getRedirectUri());
		authUrl = accessTempToken.build();
	}
	
	public String getAuthUrl()
	{
		return authUrl;
	}

}
