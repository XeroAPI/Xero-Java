package com.xero.api;

import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthRsaSigner;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Enumeration;

public class RsaSigner implements Signer {

	private OAuthRsaSigner signer = new OAuthRsaSigner();
	
	public RsaSigner(Config config) {

		InputStream oauthPKCS12Stream = null;
		oauthPKCS12Stream = getClass().getResourceAsStream("/" + config.getPathToPrivateKey());
		String oauthPKCS12Password = config.getPrivateKeyPassword();

		KeyStore oauthKeyStore = null;
		try {
			oauthKeyStore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		try {
			try {
				oauthKeyStore.load(oauthPKCS12Stream, oauthPKCS12Password.toCharArray());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (CertificateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PrivateKey oauthKey = null;
		try{
			for (Enumeration<String> e = oauthKeyStore.aliases(); e.hasMoreElements(); ) {
				String alias = e.nextElement();

				try {
					if (oauthKeyStore.isKeyEntry(alias)) {
						try {
							oauthKey = (PrivateKey)oauthKeyStore.getKey(alias,oauthPKCS12Password.toCharArray());
						} catch (UnrecoverableKeyException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (KeyStoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}catch (KeyStoreException e2) {
			e2.printStackTrace();
		}

		signer.privateKey = oauthKey;
	}

	public OAuthRsaSigner createRsaSigner() {
		// TODO Auto-generated method stub
		return signer;
	}

	public OAuthHmacSigner createHmacSigner() {
		// TODO Auto-generated method stub
		return null;
	}

}
