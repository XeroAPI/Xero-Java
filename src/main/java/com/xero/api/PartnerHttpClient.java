package com.xero.api;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.api.client.http.apache.ApacheHttpTransport;

public class PartnerHttpClient {

	private ApacheHttpTransport transport = null;

	public PartnerHttpClient() {
		
		HttpClient client = new DefaultHttpClient();

		transport = new ApacheHttpTransport(client);
	}

	public ApacheHttpTransport getPartnerHttpClient(){
		return transport;
	}

}
