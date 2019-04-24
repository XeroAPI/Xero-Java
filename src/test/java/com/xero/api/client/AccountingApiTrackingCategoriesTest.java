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
import com.xero.api.client.*;
import com.xero.models.accounting.*;
import com.xero.example.CustomJsonConfig;

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
            Thread.sleep(60000);
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

        assertThat(response.getTrackingCategories().get(0).getTrackingCategoryID(), is(equalTo(UUID.fromString("b1df776b-b093-4730-b6ea-590cca40e723"))));
        assertThat(response.getTrackingCategories().get(0).getName(), is(equalTo("FooBar")));
        assertThat(response.getTrackingCategories().get(0).getStatus(), is(equalTo(com.xero.models.accounting.TrackingCategory.StatusEnum.ACTIVE)));
        //System.out.println(response.getTrackingCategories().get(0).toString());
    }

    @Test
    public void createTrackingOptionsTest() throws IOException {
        System.out.println("@Test - createTrackingOptions");
        UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        TrackingOption trackingOption = null;
        TrackingOptions response = api.createTrackingOptions(trackingCategoryID, trackingOption);

        assertThat(response.getOptions().get(0).getTrackingOptionID(), is(equalTo(UUID.fromString("34669548-b989-487a-979f-0787d04568a2"))));
        assertThat(response.getOptions().get(0).getName(), is(equalTo("Bar40423")));
        assertThat(response.getOptions().get(0).getStatus(), is(equalTo(com.xero.models.accounting.TrackingOption.StatusEnum.ACTIVE)));
        //System.out.println(response.getOptions().get(0).toString());
    }

    @Test
    public void deleteTrackingCategoryTest() throws IOException {
        System.out.println("@Test - deleteTrackingCategory");
        UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        TrackingCategories response = api.deleteTrackingCategory(trackingCategoryID);

        assertThat(response.getTrackingCategories().get(0).getTrackingCategoryID(), is(equalTo(UUID.fromString("0390bdfd-94f2-49d6-b7a0-4a38c46ebf03"))));
        assertThat(response.getTrackingCategories().get(0).getName(), is(equalTo("Foo46189")));
        assertThat(response.getTrackingCategories().get(0).getStatus(), is(equalTo(com.xero.models.accounting.TrackingCategory.StatusEnum.DELETED)));
        //System.out.println(response.getTrackingCategories().get(0).toString());
    }
    
    @Test
    public void deleteTrackingOptionsTest() throws IOException {
        System.out.println("@Test - deleteTrackingOptions");
        UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        UUID trackingOptionID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        TrackingOptions response = api.deleteTrackingOptions(trackingCategoryID, trackingOptionID);

        assertThat(response.getOptions().get(0).getTrackingOptionID(), is(equalTo(UUID.fromString("34669548-b989-487a-979f-0787d04568a2"))));
        assertThat(response.getOptions().get(0).getName(), is(equalTo("Bar40423")));
        assertThat(response.getOptions().get(0).getStatus(), is(equalTo(com.xero.models.accounting.TrackingOption.StatusEnum.DELETED)));
        //System.out.println(response.getOptions().toString());
    }

    @Test
    public void getTrackingCategoriesTest() throws IOException {
        System.out.println("@Test - getTrackingCategories");
        String where = null;
        String order = null;
        Boolean includeArchived = null;
        TrackingCategories response = api.getTrackingCategories(where, order, includeArchived);

        assertThat(response.getTrackingCategories().get(0).getTrackingCategoryID(), is(equalTo(UUID.fromString("22f10184-0deb-44ae-a312-b1f6ea70e51f"))));
        assertThat(response.getTrackingCategories().get(0).getName(), is(equalTo("BarFoo")));
        assertThat(response.getTrackingCategories().get(0).getStatus(), is(equalTo(com.xero.models.accounting.TrackingCategory.StatusEnum.ACTIVE)));
        //System.out.println(response.getTrackingCategories().get(0).toString());
    }
    
    @Test
    public void getTrackingCategoryTest() throws IOException {
        System.out.println("@Test - getTrackingCategory");
        UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        TrackingCategories response = api.getTrackingCategory(trackingCategoryID);

        assertThat(response.getTrackingCategories().get(0).getTrackingCategoryID(), is(equalTo(UUID.fromString("22f10184-0deb-44ae-a312-b1f6ea70e51f"))));
        assertThat(response.getTrackingCategories().get(0).getName(), is(equalTo("Foo41157")));
        assertThat(response.getTrackingCategories().get(0).getStatus(), is(equalTo(com.xero.models.accounting.TrackingCategory.StatusEnum.DELETED)));
        //System.out.println(response.getTrackingCategories().get(0).toString());
    }

    @Test
    public void updateTrackingCategoryTest() throws IOException {
        System.out.println("@Test - updateTrackingCategory");
        UUID trackingCategoryID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        TrackingCategory trackingCategory = null;
        TrackingCategories response = api.updateTrackingCategory(trackingCategoryID, trackingCategory);

        assertThat(response.getTrackingCategories().get(0).getTrackingCategoryID(), is(equalTo(UUID.fromString("b1df776b-b093-4730-b6ea-590cca40e723"))));
        assertThat(response.getTrackingCategories().get(0).getName(), is(equalTo("BarFoo")));
        assertThat(response.getTrackingCategories().get(0).getStatus(), is(equalTo(com.xero.models.accounting.TrackingCategory.StatusEnum.ACTIVE)));
        //System.out.println(response.getTrackingCategories().get(0).toString());
    }
}