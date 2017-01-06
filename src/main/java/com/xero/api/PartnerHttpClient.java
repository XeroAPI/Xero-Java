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
		
		HttpClient client = new DefaultHttpClient();

		transport = new ApacheHttpTransport(client);
	}

	public ApacheHttpTransport getPartnerHttpClient(){
		return transport;
	}

}
