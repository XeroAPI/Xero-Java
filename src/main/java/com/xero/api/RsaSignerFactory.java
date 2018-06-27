package com.xero.api;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.client.auth.oauth.OAuthRsaSigner;

public class RsaSignerFactory implements SignerFactory {

  private OAuthRsaSigner signer = new OAuthRsaSigner();
  final static Logger logger = LogManager.getLogger(RsaSignerFactory.class);
  
  public RsaSignerFactory(String pathToPrivateKey, String privateKeyPassword) {
    this(RsaSignerFactory.class.getResourceAsStream(pathToPrivateKey), privateKeyPassword);
  }

  public RsaSignerFactory(InputStream privateKeyInputStream, String privateKeyPassword) {
    try {
      KeyStore oauthKeyStore;
      oauthKeyStore = KeyStore.getInstance("PKCS12");

      oauthKeyStore.load(privateKeyInputStream, privateKeyPassword.toCharArray());

      PrivateKey oauthKey = null;
      for (String alias : Collections.list(oauthKeyStore.aliases())) {
        if (oauthKeyStore.isKeyEntry(alias)) {
          oauthKey = (PrivateKey) oauthKeyStore.getKey(alias, privateKeyPassword.toCharArray());
        }
      }

      signer.privateKey = oauthKey;
    } catch (IOException | GeneralSecurityException | RuntimeException ex) {
    		logger.error(ex);
    		throw new RuntimeException(ex);
    }
  }

  @Override
  public OAuthRsaSigner createSigner(String tokenSharedSecret) {
    return signer;
  }
}
