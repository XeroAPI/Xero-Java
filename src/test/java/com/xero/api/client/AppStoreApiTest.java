package com.xero.api.client;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Every.everyItem;

import com.xero.api.ApiClient;
import com.xero.api.XeroApiException;
import com.xero.api.client.*;
import com.xero.models.appstore.*;

import java.io.File;
import java.net.URL;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import org.threeten.bp.*;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import java.util.List;

import org.apache.commons.io.IOUtils;

public class AppStoreApiTest {

	ApiClient defaultClient; 
	AppStoreApi appStoreApi; 
	String accessToken;
    String xeroTenantId; 	
	
	private static boolean setUpIsDone = false;

	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
		defaultClient = new ApiClient("https://xero-app-store.getsandbox.com:443/appstore/2.0",null,null,null,null);
        appStoreApi = AppStoreApi.getInstance(defaultClient);
        
		// ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
		if (setUpIsDone) {
        	return;
    	}

    	try {
    		System.out.println("Sleep for 60 seconds");
	    	Thread.sleep(60);
    	} catch(InterruptedException e) {
    		System.out.println(e);
    	}

    	// do the setup
    	setUpIsDone = true;
	}

	public void tearDown() {
		appStoreApi = null;
		defaultClient = null;
	}

    @Test
	public void testGetSubscription() throws Exception {
		System.out.println("@Test - getSubscription");

        try {
            UUID subscriptionId = UUID.fromString("01b5a6f4-8936-4bfa-b703-830702312b87");
            Subscription response = appStoreApi.getSubscription(accessToken, subscriptionId);
            assertThat(response.getId().toString(), (equalTo("01b5a6f4-8936-4bfa-b703-830702312b87")));
            assertThat(response.getOrganisationId().toString(), (equalTo("fdc5be44-9b3e-4ebb-a0e9-11b9737f9a28")));

            System.out.println(response.toString());
        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }
}
