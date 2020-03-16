package com.xero.api.client;

import static org.junit.Assert.assertTrue;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Every.everyItem;

import com.xero.api.ApiClient;
import com.xero.api.client.*;
import com.xero.models.project.*;

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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class ProjectsApiProjectUsersTest {

	ApiClient defaultClient; 
    ProjectApi projectApi; 
	String accessToken;
    String xeroTenantId; 
   
    private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        // Init projectApi client
        // NEW Sandbox for API Mocking
		defaultClient = new ApiClient("https://xero-projects.getsandbox.com:443/projects.xro/2.0",null,null,null,null);
        projectApi = ProjectApi.getInstance(defaultClient);   
       
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
		projectApi = null;
        defaultClient = null;
	}

	@Test
    public void getProjectUsersTest() throws IOException {
        System.out.println("@Test - getProjectUsersTest");
        
        int page = 1;
        int pageSize = 50;
        ProjectUsers response = projectApi.getProjectUsers(accessToken, xeroTenantId, page, pageSize);
        
        assertThat(response.getPagination().getPage(), is(equalTo(1)));
        assertThat(response.getPagination().getItemCount(), is(equalTo(2)));
        assertThat(response.getPagination().getPageCount(), is(equalTo(1)));
        assertThat(response.getPagination().getPageSize(), is(equalTo(50)));
        assertThat(response.getItems().get(0).getUserId(), is(equalTo(UUID.fromString("740add2a-a703-4b8a-a670-1093919c2040"))));
        assertThat(response.getItems().get(0).getName(), is(equalTo("Sidney Maestre")));
        assertThat(response.getItems().get(0).getEmail(), is(equalTo("sid.maestre@xero.com")));

       // System.out.println(response.toString());
    }
}
