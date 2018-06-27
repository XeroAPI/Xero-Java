package com.xero.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XeroHttpContext {
	private Config config;
	private String accept;
	private String contentType;
	private String ifModifiedSince = null;
	final static Logger logger = LogManager.getLogger(XeroHttpContext.class);
	
	public XeroHttpContext(Config config)
	{
		this.config = config;
	}
	
	public XeroHttpContext(Config config,String accept,String contentType, String ifModifiedSince)
	{
		this.config = config;
		this.accept = accept;
		this.contentType = contentType;
		this.ifModifiedSince = ifModifiedSince;
	}
	
	public CloseableHttpClient getHttpClient() throws FileNotFoundException, IOException 
	{
		CloseableHttpClient httpclient = null;
		
		Header contentHeader = new BasicHeader( HttpHeaders.CONTENT_TYPE, this.contentType == null ? "application/xml" : this.contentType);
	    Header acceptHeader = new BasicHeader( HttpHeaders.ACCEPT, this.accept != null ? this.accept : config.getAccept());
	    Header userAgentHeader = new BasicHeader( HttpHeaders.USER_AGENT,  config.getUserAgent());
	    
		List<Header> headers = new ArrayList<Header>();
		headers.add(contentHeader);
		headers.add(acceptHeader);
		headers.add(userAgentHeader);
		
		if(this.ifModifiedSince != null) {
			Header modifiedHeader = new BasicHeader( HttpHeaders.IF_MODIFIED_SINCE, this.ifModifiedSince);
			headers.add(modifiedHeader);
		}
		
		
		if ((config.getKeyStorePath() == null || config.getKeyStorePath().length() == 0) && (config.getKeyStorePassword() == null || config.getKeyStorePassword().length() == 0))
		{
			if(logger.isInfoEnabled()){
				logger.info("You must use Java 1.8 to skip setting the Key Store Path & Key Store Password in config.json");
			}
			httpclient = HttpClients.custom().setDefaultHeaders(headers).build();
			
		} else {
			if(logger.isInfoEnabled()){
				logger.info("Key Store Path & Key Store Password in config.json will be used to set the SSLContext and force TLS 1.2");
			}
			   
			KeyStore keyStore = null;
			try {
				keyStore = KeyStore.getInstance("JKS");
			} catch (KeyStoreException e) {
				logger.error(e);
			}
				
			try {
				keyStore.load(new FileInputStream(config.getKeyStorePath()), 
						config.getKeyStorePassword().toCharArray());
			} catch (NoSuchAlgorithmException | CertificateException e1) {
				logger.error(e1);
			}
		 	TrustManagerFactory tmf = null;
			try {
				tmf = TrustManagerFactory.getInstance(
				    TrustManagerFactory.getDefaultAlgorithm());
			} catch (NoSuchAlgorithmException e) {
				logger.error(e);
			}
		
		    try {
			tmf.init(keyStore);
			
			} catch (KeyStoreException e) {
				logger.error(e);
			}
			    SSLContext ctx = null;
			try {
				ctx = SSLContext.getInstance("TLS");
			} catch (NoSuchAlgorithmException e) {
				logger.error(e);
			}
		
			try {
				ctx.init(null, tmf.getTrustManagers(), new SecureRandom());
			} catch (KeyManagementException e) {
				logger.error(e);
			}
			    
		    SSLContext.setDefault(ctx);
		      
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		                 ctx,
		                 new String[] { "TLSv1.2" },
		                 new String[] {"TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256","TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256","TLS_RSA_WITH_AES_128_CBC_SHA256","TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256","TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256","TLS_DHE_RSA_WITH_AES_128_CBC_SHA256","TLS_DHE_DSS_WITH_AES_128_CBC_SHA256","TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA","TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA","TLS_RSA_WITH_AES_128_CBC_SHA","TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA","TLS_ECDH_RSA_WITH_AES_128_CBC_SHA","TLS_DHE_RSA_WITH_AES_128_CBC_SHA","TLS_DHE_DSS_WITH_AES_128_CBC_SHA","TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA","TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA","TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA","TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA","TLS_EMPTY_RENEGOTIATION_INFO_SCSV"},
		                 SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		    
		    httpclient = HttpClients.custom()
		                .setSSLSocketFactory(sslsf)
		                .setDefaultHeaders(headers)
		                .build();
		}
		
		return httpclient;   
	}
}
