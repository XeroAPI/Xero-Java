package com.xero.api;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;

import com.google.api.client.http.apache.ApacheHttpTransport;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class PartnerHttpClient {

	private ApacheHttpTransport transport = null;
	private Config c = Config.getInstance();

	public PartnerHttpClient() {

		InputStream clientCertPKCS12Stream = null;
		try {
			clientCertPKCS12Stream = new FileInputStream(c.getPathToEntrust());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String password = c.getEntrustPassword();

		KeyStore entrustStore = null;

		try {
			entrustStore = KeyStore.getInstance("PKCS12");
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			try {
				entrustStore.load(clientCertPKCS12Stream, password.toCharArray());
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		HttpClient client = new DefaultHttpClient();
		ClientConnectionManager mgr = client.getConnectionManager();
		if (!(mgr instanceof ThreadSafeClientConnManager)) {
			HttpParams params = client.getParams();
			client = new DefaultHttpClient(
					new ThreadSafeClientConnManager(params, mgr.getSchemeRegistry()), params);
		}
		SchemeRegistry schemeRegistry = client.getConnectionManager().getSchemeRegistry();

		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("TLS");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		KeyManagerFactory kmFactory = null;
		try {
			kmFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			kmFactory.init(entrustStore, password == null ? null : password.toCharArray());
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sslContext.init(kmFactory.getKeyManagers(), null, null);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SSLSocketFactory sslSocketFactory = new SSLSocketFactory(sslContext);

		Scheme partnerHttps = new Scheme("https", sslSocketFactory, 443);
		schemeRegistry.register(partnerHttps);

		transport = new ApacheHttpTransport(client);
	}

	public ApacheHttpTransport getPartnerHttpClient(){
		return transport;
	}

}
