package com.xero.api.client;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Before;
import org.junit.Test;

import com.xero.api.ApiClient;
import com.xero.api.XeroApiException;
import com.xero.models.appstore.Subscription;

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
        
		defaultClient = new ApiClient("http://127.0.0.1:4011",null,null,null,null);
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
