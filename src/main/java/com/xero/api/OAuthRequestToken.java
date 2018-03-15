package com.xero.api;

import java.io.IOException;
import java.util.HashMap;

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthSigner;

public class OAuthRequestToken {

  private String tempToken = null;
  private String tempTokenSecret = null;
  private OAuthGetTemporaryToken tokenRequest = null;
  private Config config;
  private SignerFactory signerFactory;

  public OAuthRequestToken(Config config) {
    this(config, new ConfigBasedSignerFactory(config));
  }

  public OAuthRequestToken(Config config, SignerFactory signerFactory) {
    this.config = config;
    this.signerFactory = signerFactory;
  }

  public void execute() {
    OAuthSigner signer = signerFactory.createSigner(null);

    if (config.isUsingAppFirewall()) {
        tokenRequest = new OAuthGetTemporaryToken(config.getRequestTokenUrl(), config.isUsingAppFirewall(),
                                                    config.getAppFirewallHostname(), config.getAppFirewallUrlPrefix());
    } else {
        tokenRequest = new OAuthGetTemporaryToken(config.getRequestTokenUrl());
    }
  
    tokenRequest.setConfig(config);
    tokenRequest.signer = signer;
    
    OAuthCredentialsResponse temporaryTokenResponse = null;
    try {
    	  	temporaryTokenResponse = tokenRequest.execute();
    		tempToken = temporaryTokenResponse.token;
    		tempTokenSecret = temporaryTokenResponse.tokenSecret;
    } catch (IOException e) {
    		e.printStackTrace();
    }
  }

  public String getTempToken() {
    return tempToken;
  }

  public String getTempTokenSecret() {
    return tempTokenSecret;
  }

  public HashMap<String, String> getAll() {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("tempToken", getTempToken());
    map.put("tempTokenSecret", getTempTokenSecret());
    map.put("sessionHandle", "");
    map.put("tokenTimestamp", "");

    return map;
  }
}
