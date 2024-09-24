package com.xero.api.client;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.api.XeroApiException;
import com.xero.models.appstore.*;

import java.util.UUID;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

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
        
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")){
            properties.load(input);
            defaultClient = new ApiClient(properties.getProperty("appstore.api.url"),null,null,null,null);
            appStoreApi = AppStoreApi.getInstance(defaultClient); 

        } catch (IOException ex) {
            ex.printStackTrace();
        }  
        
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
