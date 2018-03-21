package com.xero.api;

/**
 * Replace the google OAuth API Client Class of the same name so that we can use
 * our own modified version of OAuthParameters and be able to create a customized
 * signature to deal with the L7 Application Firewall
 *
 * @author <a href="jvogtle@paychex.com">John Vogtle</a>
 */

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthSigner;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.UrlEncodedParser;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class OAuthGetTemporaryToken extends GenericUrl {

    public String callback;
    public String consumerKey;
    public OAuthSigner signer;
    protected boolean usePost;
    private boolean usingAppFirewall;
    private String appFirewallHostname = "";
    private String appFirewallUrlPrefix = "";
    public Config config;
    private CloseableHttpClient httpclient;
	private int connectTimeout = 20;
	private int readTimeout = 20;
    
    public OAuthGetTemporaryToken(String authorizationServerUrl) {
        super(authorizationServerUrl);
    }

    public OAuthGetTemporaryToken(String authorizationServerUrl,
                                  boolean usingAppFirewall, String appFirewallHostname, String appFirewallUrlPrefix) {
        super(authorizationServerUrl);
        this.usingAppFirewall = usingAppFirewall;
        this.appFirewallHostname = appFirewallHostname;
        this.appFirewallUrlPrefix = appFirewallUrlPrefix;
    }

    public final OAuthCredentialsResponse execute() throws IOException,XeroApiException {
    		this.connectTimeout = config.getConnectTimeout() * 1000;
    		this.readTimeout = config.getReadTimeout() * 1000;
    		
    		httpclient = new XeroHttpContext(config).getHttpClient();
    		GenericUrl requestUrl = new GenericUrl(this.config.getRequestTokenUrl());
    		
    		HttpGet httpget = new HttpGet(this.config.getRequestTokenUrl());
    		
    		RequestConfig.Builder requestConfig = RequestConfig.custom()
    				.setConnectTimeout(connectTimeout)
    				.setConnectionRequestTimeout(readTimeout)
    				.setSocketTimeout(connectTimeout);
		
    		//Proxy Service Setup - unable to fully test as we don't have a proxy 
    		// server to test against.
    		if(!"".equals(config.getProxyHost()) && config.getProxyHost() != null) {
    			int port = (int) (config.getProxyPort() == 80 && config.getProxyHttpsEnabled() ? 443 : config.getProxyPort());		
    			HttpHost proxy = new HttpHost(config.getProxyHost(), port, config.getProxyHttpsEnabled() ? "https" : "http");
    			requestConfig.setProxy(proxy);
    		}
    		this.createParameters().intercept(httpget,requestUrl);
    		httpget.setConfig(requestConfig.build());
    		
    		try {
    			CloseableHttpResponse response = httpclient.execute(httpget);
    			try {
    				HttpEntity entity = response.getEntity();  
    				String content = EntityUtils.toString(entity); 
 
    				OAuthCredentialsResponse oauthResponse = new OAuthCredentialsResponse();
    				UrlEncodedParser.parse(content, oauthResponse); 
    				
    				int code = response.getStatusLine().getStatusCode();
    				if (code != 200) {
    					XeroApiException e = new XeroApiException(code,content);
		        		throw e;
		        }

    				//System.out.println("RequestToken Response: " + retSrc);
    				EntityUtils.consume(entity);
    				return oauthResponse;
    			} finally {
    				response.close();
    			}
    		} finally {
    			httpclient.close();
    		}
    }

    public OAuthParameters createParameters() {
        OAuthParameters result = new OAuthParameters();
        result.usingAppFirewall = this.usingAppFirewall;
        result.appFirewallHostname = this. appFirewallHostname;
        result.appFirewallUrlPrefix = this.appFirewallUrlPrefix;
        result.consumerKey = this.consumerKey;
        result.signer = this.signer;
        result.callback = this.callback;
        return result;
    }
    
    public void setConfig(Config config) {
    		this.config = config;
    	    this.consumerKey = config.getConsumerKey();
    	    this.callback = config.getRedirectUri();
    }
}