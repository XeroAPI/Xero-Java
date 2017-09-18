package com.xero.api;

import com.google.api.client.auth.oauth.OAuthSigner;

public class ConfigBasedSignerFactory implements SignerFactory {
  private static final String PUBLIC_APP = "PUBLIC";
  private Config config;

  public ConfigBasedSignerFactory(Config config) {
    this.config = config;
  }

  @Override
  public OAuthSigner createSigner(String tokenSharedSecret) {
    if (config.getAppType().equals(PUBLIC_APP)) {
      return new HmacSignerFactory(config.getConsumerSecret()).createSigner(tokenSharedSecret);
    } else {
      return new RsaSignerFactory(config.getPathToPrivateKey(), config.getPrivateKeyPassword()).createSigner(
        tokenSharedSecret);
    }
  }
}
