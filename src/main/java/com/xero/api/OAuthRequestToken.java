package com.xero.api;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpHost;

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth.OAuthSigner;
import com.google.api.client.http.apache.ApacheHttpTransport;

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

    tokenRequest = new OAuthGetTemporaryToken(config.getRequestTokenUrl());
    tokenRequest.consumerKey = config.getConsumerKey();
    tokenRequest.callback = config.getRedirectUri();

    ApacheHttpTransport.Builder transBuilder = new ApacheHttpTransport.Builder();
    if (config.getProxyHost() != null && "" != config.getProxyHost()) {
      String proxy_host = config.getProxyHost();
      long proxy_port = config.getProxyPort();
      boolean proxyHttps = config.getProxyHttpsEnabled();
      String proxy_schema = proxyHttps == true ? "https" : "http";
      System.out.println("proxy.host=" + proxy_host + ", proxy.port=" + proxy_port + ", proxy_schema=" + proxy_schema);
      HttpHost proxy = new HttpHost(proxy_host, (int) proxy_port, proxy_schema);
      transBuilder.setProxy(proxy);
      tokenRequest.transport = transBuilder.build();
    } else {
      tokenRequest.transport = new ApacheHttpTransport();
    }

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
