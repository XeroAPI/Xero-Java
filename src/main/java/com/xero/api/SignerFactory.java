package com.xero.api;

import com.google.api.client.auth.oauth.OAuthSigner;

public interface SignerFactory {
	OAuthSigner createSigner(String tokenSharedSecret);
}
