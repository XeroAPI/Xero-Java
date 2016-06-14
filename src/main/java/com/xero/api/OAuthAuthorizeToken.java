package com.xero.api;

import com.google.api.client.auth.oauth.OAuthAuthorizeTemporaryTokenUrl;

public class OAuthAuthorizeToken {

	private Config c = Config.getInstance();
	private String authUrl = null;
	
	public OAuthAuthorizeToken(String tempToken) 
	{	
		OAuthAuthorizeTemporaryTokenUrl accessTempToken = new OAuthAuthorizeTemporaryTokenUrl(c.getAuthorizeUrl());
		accessTempToken.temporaryToken = tempToken;
		accessTempToken.set("oauth_callback",c.getRedirectUri());
		String url = accessTempToken.build();
		setAuthUrl(url);
	}
	
	private void setAuthUrl(String url)
	{
		authUrl = url;
	}
	
	public String getAuthUrl()
	{
		return authUrl;
	}

}
