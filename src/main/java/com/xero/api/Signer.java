package com.xero.api;

import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthRsaSigner;

public interface Signer {
	OAuthRsaSigner createRsaSigner();
	OAuthHmacSigner createHmacSigner();
}
