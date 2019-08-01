package com.xero.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;

/**
 * Servlet implementation class Authorization
 */
@WebServlet("/Authorization")
public class Authorization extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String clientId = "CEC496D0B24D4448A8F1D8B6A3F5C00E";
	final String clientSecret = "RyndE0-u_Drvu-jIwBNO4Hcsug-kCcQQgUQOB4AHTHpHl2z9";
	final String redirectURI = "http://localhost:8080/xero-sdk-oauth2-dev-01/Callback";
    final String TOKEN_SERVER_URL = "https://identity.xero.com/connect/token";
    final String AUTHORIZATION_SERVER_URL = "https://login.xero.com/identity/connect/authorize";
	final NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    final JsonFactory JSON_FACTORY = new JacksonFactory();
    final String secretState = "secret" + new Random().nextInt(999_999);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authorization() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> scopeList = new ArrayList<String>();
        scopeList.add("openid");
        scopeList.add("email");
        scopeList.add("profile");
        scopeList.add("offline_access");
        scopeList.add("accounting.settings");
        scopeList.add("accounting.transactions");
        scopeList.add("accounting.contacts");
        scopeList.add("accounting.journals.read");
        scopeList.add("accounting.reports.read");
        scopeList.add("accounting.attachments");
        scopeList.add("paymentservices");
        
        DataStoreFactory DATA_STORE_FACTORY = new MemoryDataStoreFactory();		
        AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(), 
        		HTTP_TRANSPORT, 
        		JSON_FACTORY, 
        		new GenericUrl(TOKEN_SERVER_URL), 
        		new ClientParametersAuthentication(clientId, clientSecret), clientId, AUTHORIZATION_SERVER_URL)
        			.setScopes(scopeList)
        			.setDataStoreFactory(DATA_STORE_FACTORY)
        			.build();
       
        String url = flow.newAuthorizationUrl()
        	.setClientId(clientId)
        	.setScopes(scopeList)
        	.setState(secretState)
            .setRedirectUri(redirectURI).build();
        
         response.sendRedirect(url);
	}
}
