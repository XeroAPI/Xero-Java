package com.xero.example;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.xero.api.XeroCredential;
import com.xero.api.XeroCredential.Builder;

public class RefreshCredential {

	final static HttpTransport httpTransport = new NetHttpTransport();
	final static JsonFactory jsonFactory = new JacksonFactory();
	
	public  RefreshCredential() 
	{
		super();
	}
	
	public static Builder createRefreshCredential(HttpServletResponse resp, String clientId, String clientSecret) {
		Builder credentials;
		final  HttpServletResponse response = resp;
		credentials =  new XeroCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory)
	   			.setClientSecrets(clientId, clientSecret)
	   			.addRefreshListener(new CredentialRefreshListener() {

			@Override
			public void onTokenResponse(Credential credential, TokenResponse tokenResponse) throws IOException {
				System.out.println("Token Refresh success");
				credential.setFromTokenResponse(tokenResponse);
				// implement your own storage of new tokens
				TokenStorage store = new TokenStorage();
			    store.saveItem(response, "access_token", tokenResponse.getAccessToken());
			    store.saveItem(response, "refresh_token", tokenResponse.getRefreshToken());
			    store.saveItem(response, "expires_in_seconds", tokenResponse.getExpiresInSeconds().toString());
			}

			@Override
			public void onTokenErrorResponse(Credential credential, TokenErrorResponse tokenErrorResponse)
					throws IOException {
				System.out.println("Error Response");	
			}
		});
		return credentials;
	}
}
