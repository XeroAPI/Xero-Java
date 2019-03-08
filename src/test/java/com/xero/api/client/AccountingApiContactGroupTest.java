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

public class AccountingApiContactGroupTest {

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
    public void getContactGroupsTest() throws IOException {
        System.out.println("@Test - getContactGroupsTest");

        String where = null;
        String order = null;
        ContactGroups response = api.getContactGroups(where, order);
        assertThat(response.getContactGroups().get(0).getContactGroupID(), is(equalTo(UUID.fromString("d7a86b80-8dac-4d89-a334-9dcf5753676c"))));
        assertThat(response.getContactGroups().get(0).getName(), is(equalTo("Suppliers")));
        assertThat(response.getContactGroups().get(0).getStatus(), is(equalTo(com.xero.models.accounting.ContactGroup.StatusEnum.ACTIVE)));
        //System.out.println(response.getContactGroups().get(0).toString());
    }    

    @Test
    public void getContactGroupTest() throws IOException {
        System.out.println("@Test - getContactGroupTest");
        
        UUID contactGroupID = UUID.fromString("17b44ed7-4389-4162-91cb-3dd5766e4e22");
        ContactGroups response = api.getContactGroup(contactGroupID);

        assertThat(response.getContactGroups().get(0).getContactGroupID(), is(equalTo(UUID.fromString("17b44ed7-4389-4162-91cb-3dd5766e4e22"))));
        assertThat(response.getContactGroups().get(0).getName(), is(equalTo("Oasis")));
        assertThat(response.getContactGroups().get(0).getStatus(), is(equalTo(com.xero.models.accounting.ContactGroup.StatusEnum.ACTIVE)));
        assertThat(response.getContactGroups().get(0).getContacts().get(0).getContactID(), is(equalTo(UUID.fromString("4e1753b9-018a-4775-b6aa-1bc7871cfee3"))));
        assertThat(response.getContactGroups().get(0).getContacts().get(0).getName(), is(equalTo("Noel Gallagher")));
        assertThat(response.getContactGroups().get(0).getContacts().get(1).getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getContactGroups().get(0).getContacts().get(1).getName(), is(equalTo("Liam Gallagher")));
        //System.out.println(response.getContactGroups().get(0).toString());
    }

    @Test
    public void createContactGroupTest() throws IOException {
        System.out.println("@Test - createContactGroupTest");

        ContactGroups contactGroups = null;
        ContactGroups response = api.createContactGroup(contactGroups);
        assertThat(response.getContactGroups().get(0).getContactGroupID(), is(equalTo(UUID.fromString("d7a86b80-8dac-4d89-a334-9dcf5753676c"))));
        assertThat(response.getContactGroups().get(0).getName(), is(equalTo("Suppliers")));
        assertThat(response.getContactGroups().get(0).getStatus(), is(equalTo(com.xero.models.accounting.ContactGroup.StatusEnum.ACTIVE)));
        //System.out.println(response.getContactGroups().get(0).toString());
    }

    @Test
    public void updateContactGroupTest() throws IOException {
        System.out.println("@Test - updateContactGroupTest");

        UUID contactGroupID = UUID.fromString("13f47537-7c1d-4e62-966e-617d76558fc5");
        ContactGroups contactGroups = null;
        ContactGroups response = api.updateContactGroup(contactGroupID, contactGroups);
        assertThat(response.getContactGroups().get(0).getContactGroupID(), is(equalTo(UUID.fromString("13f47537-7c1d-4e62-966e-617d76558fc5"))));
        assertThat(response.getContactGroups().get(0).getName(), is(equalTo("Supplier Vendor")));
        assertThat(response.getContactGroups().get(0).getStatus(), is(equalTo(com.xero.models.accounting.ContactGroup.StatusEnum.ACTIVE)));
        //System.out.println(response.getContactGroups().get(0).toString());
    }
    
    @Test
    public void createContactGroupContactsTest() throws IOException {
        System.out.println("@Test - createContactGroupContactsTest");

        UUID contactGroupID = UUID.fromString("13f47537-7c1d-4e62-966e-617d76558fc5");
        Contacts contacts = null;
        Contacts response = api.createContactGroupContacts(contactGroupID, contacts);
        assertThat(response.getContacts().get(0).getContactID(), is(equalTo(UUID.fromString("a3675fc4-f8dd-4f03-ba5b-f1870566bcd7"))));
        //System.out.println(response.getContacts().get(0).toString());
    }

    @Test
    public void deleteContactGroupContactTest() throws IOException {
        System.out.println("@Test - deleteContactGroupContactTest");

        UUID contactGroupID = UUID.fromString("13f47537-7c1d-4e62-966e-617d76558fc5");
        UUID contactID = UUID.fromString("13f47537-7c1d-4e62-966e-617d76558fc5");
        api.deleteContactGroupContact(contactGroupID, contactID);
    }

    @Test
    public void deleteContactGroupContactsTest() throws IOException {
        System.out.println("@Test - deleteContactGroupContactsTest");

        UUID contactGroupID = UUID.fromString("13f47537-7c1d-4e62-966e-617d76558fc5");
        api.deleteContactGroupContacts(contactGroupID);
    }
	
}
