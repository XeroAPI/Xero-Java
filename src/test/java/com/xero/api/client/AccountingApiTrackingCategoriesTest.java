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


import com.xero.api.XeroApiException;
import com.xero.api.ApiClient;
import com.xero.example.CustomJsonConfig;

import com.xero.api.client.*;
import com.xero.models.accounting.*;

import com.xero.example.SampleData;

import org.threeten.bp.*;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class AccountingApiTrackingCategoriesTest {

	CustomJsonConfig config;
	ApiClient apiClientForAccounting; 
	AccountingApi api; 

    private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
		config = new CustomJsonConfig();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting/2.0.0",null,null,null);
		api = new AccountingApi(config);
		api.setApiClient(apiClientForAccounting);
		api.setOAuthToken(config.getConsumerKey(), config.getConsumerSecret());

        // ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
        if (setUpIsDone) {
            return;
        }

        try {
            System.out.println("Sleep for 30 seconds");
            Thread.sleep(60);
        } catch(InterruptedException e) {
            System.out.println(e);
        }
        // do the setup
        setUpIsDone = true;
	}

	public void tearDown() {
		api = null;
		apiClientForAccounting = null;
	}

    @Test
    public void createTrackingCategoryTest() throws IOException {
        System.out.println("@Test - createTrackingCategory");
        TrackingCategory trackingCategory = null;
        TrackingCategories response = api.createTrackingCategory(trackingCategory);

        // TODO: test validations

        System.out.println(response.getTrackingCategories().get(0).toString());
    }

    @Test
    public void createTrackingOptionsTest() throws IOException {
        System.out.println("@Test - createTrackingOptions");
        UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        TrackingOption trackingOption = null;
        TrackingOptions response = api.createTrackingOptions(trackingCategoryID, trackingOption);

        // TODO: test validations

        //System.out.println(response.getTrackingOptions().get(0).toString());
    }

    @Test
    public void deleteTrackingCategoryTest() throws IOException {
        System.out.println("@Test - deleteTrackingCategory");
        UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        TrackingCategories response = api.deleteTrackingCategory(trackingCategoryID);

        // TODO: test validations

        System.out.println(response.getTrackingCategories().get(0).toString());
    }
    
    @Test
    public void deleteTrackingOptionsTest() throws IOException {
        System.out.println("@Test - deleteTrackingOptions");
        UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        UUID trackingOptionID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        TrackingOptions response = api.deleteTrackingOptions(trackingCategoryID, trackingOptionID);

        // TODO: test validations

        //System.out.println(response.getTrackingOptions().get(0).toString());
    }

    @Test
    public void getTrackingCategoriesTest() throws IOException {
        System.out.println("@Test - getTrackingCategories");
        String where = null;
        String order = null;
        Boolean includeArchived = null;
        TrackingCategories response = api.getTrackingCategories(where, order, includeArchived);

        // TODO: test validations

        System.out.println(response.getTrackingCategories().get(0).toString());
    }
    
    @Test
    public void getTrackingCategoryTest() throws IOException {
        System.out.println("@Test - getTrackingCategory");
        UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        TrackingCategories response = api.getTrackingCategory(trackingCategoryID);

        //assertThat(response.getTrackingCategorys().get(0).getTrackingCategoryID(), is(equalTo(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))));
        //assertThat(response.getTrackingCategorys().get(0).getTrackingOptionID(), is(equalTo(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))));
        //assertThat(response.getTrackingCategorys().get(0).getName(), is(equalTo("STRING_ONLY_PLACEHOLDER")));
        //assertThat(response.getTrackingCategorys().get(0).getOption(), is(equalTo("STRING_ONLY_PLACEHOLDER")));
        //assertThat(response.getTrackingCategorys().get(0).getStatus(), is(equalTo(ENUM_PLACEHOLDER)));
        //assertThat(response.getTrackingCategorys().get(0).getOptions(), is(equalTo("CONTAINER_PLACEHOLDER")));

        //System.out.println(response.getTrackingCategories().get(0).toString());
    }

    @Test
    public void updateTrackingCategoryTest() throws IOException {
        System.out.println("@Test - updateTrackingCategory");
        UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        TrackingCategory trackingCategory = null;
        TrackingCategories response = api.updateTrackingCategory(trackingCategoryID, trackingCategory);

        // TODO: test validations

        System.out.println(response.getTrackingCategories().get(0).toString());
    }

/*
    @Test
    public void getUserTest() throws IOException {
        System.out.println("@Test - getUser");
        UUID userID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Users response = api.getUser(userID);

        assertThat(response.getUsers().get(0).getUserID(), is(equalTo(UUID.fromString("3c37ef1d-cd49-4589-9787-3c418ed8b6ac"))));
        assertThat(response.getUsers().get(0).getEmailAddress(), is(equalTo("sid.maestre@xero.com")));
        assertThat(response.getUsers().get(0).getFirstName(), is(equalTo("Sidney")));
        assertThat(response.getUsers().get(0).getLastName(), is(equalTo("Maestre")));
        assertThat(response.getUsers().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2017-10-20T11:14:21.613-07:00"))));  
        assertThat(response.getUsers().get(0).getIsSubscriber(), is(equalTo(false)));
        assertThat(response.getUsers().get(0).getOrganisationRole(), is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
        //System.out.println(response.getUsers().get(0).toString());
    }
    
    @Test
    public void getUsersTest() throws IOException {
        System.out.println("@Test - getUsers");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        Users response = api.getUsers(ifModifiedSince, where, order);

        assertThat(response.getUsers().get(0).getUserID(), is(equalTo(UUID.fromString("3c37ef1d-cd49-4589-9787-3c418ed8b6ac"))));
        assertThat(response.getUsers().get(0).getEmailAddress(), is(equalTo("sid.maestre@xero.com")));
        assertThat(response.getUsers().get(0).getFirstName(), is(equalTo("Sidney")));
        assertThat(response.getUsers().get(0).getLastName(), is(equalTo("Maestre")));
        assertThat(response.getUsers().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2017-10-20T11:14:21.613-07:00"))));  
        assertThat(response.getUsers().get(0).getIsSubscriber(), is(equalTo(false)));
        assertThat(response.getUsers().get(0).getOrganisationRole(), is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
        //System.out.println(response.getUsers().toString());
    }
    */
}
