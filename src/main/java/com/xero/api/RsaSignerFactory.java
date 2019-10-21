package com.xero.api;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Collections;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.google.api.client.auth.oauth.OAuthRsaSigner;

public class RsaSignerFactory implements SignerFactory {

    private OAuthRsaSigner signer = new OAuthRsaSigner();
    final static Logger logger = LoggerFactory.getLogger(RsaSignerFactory.class);

    public RsaSignerFactory(String pathToPrivateKey, String privateKeyPassword) {
        try (InputStream privateKeyInputStream = getInputStreamForPath(pathToPrivateKey)) {
            signer.privateKey = loadPrivateKey(privateKeyInputStream, privateKeyPassword.toCharArray());
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    public RsaSignerFactory(InputStream privateKeyInputStream, String privateKeyPassword) {
        signer.privateKey = loadPrivateKey(privateKeyInputStream, privateKeyPassword.toCharArray());
    }

    @Override
    public OAuthRsaSigner createSigner(String tokenSharedSecret) {
        return signer;
    }

    private static PrivateKey loadPrivateKey(InputStream stream, char[] password) {
        PrivateKey oauthKey = null;
        try {
            KeyStore oauthKeyStore = KeyStore.getInstance("PKCS12");
            oauthKeyStore.load(stream, password);

            for (String alias : Collections.list(oauthKeyStore.aliases())) {
                if (oauthKeyStore.isKeyEntry(alias)) {
                    oauthKey = (PrivateKey) oauthKeyStore.getKey(alias, password);
                }
            }
        } catch (IOException | GeneralSecurityException | RuntimeException ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }

        return oauthKey;
    }

    private static InputStream getInputStreamForPath(String path) {
        File f = new File(path);
        if (f.isFile()) {
            try {
                return new FileInputStream(f);
            } catch (FileNotFoundException e) {
                logger.info("Could not open file from file system, defaulting to classpath.");
            }
        }

        return RsaSignerFactory.class.getResourceAsStream(path);
    }
}
