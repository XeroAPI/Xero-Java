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
import com.google.api.client.http.*;

import java.io.IOException;

public class OAuthGetTemporaryToken extends GenericUrl {

    public String callback;
    public HttpTransport transport;
    public String consumerKey;
    public OAuthSigner signer;
    protected boolean usePost;

    private boolean usingAppFirewall;
    private String appFirewallHostname = "";
    private String appFirewallUrlPrefix = "";

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


    public final OAuthCredentialsResponse execute() throws IOException {
       HttpRequestFactory requestFactory = this.transport.createRequestFactory();
       HttpRequest request = requestFactory.buildRequest(this.usePost ? "POST" : "GET", this, (HttpContent)null);
       this.createParameters().intercept(request);
       HttpResponse response = request.execute();
       response.setContentLoggingLimit(0);
       OAuthCredentialsResponse oauthResponse = new OAuthCredentialsResponse();
       UrlEncodedParser.parse(response.parseAsString(), oauthResponse);
       return oauthResponse;
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
}
