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
import com.xero.models.accounting.*;

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

public class AccountingApiContactGroupTest {

	ApiClient defaultClient; 
    AccountingApi accountingApi; 
	String accessToken;
    String xeroTenantId; 
     
    
    private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        // Init AccountingApi client
        // NEW Sandbox for API Mocking
		defaultClient = new ApiClient("https://xero-accounting.getsandbox.com:443/api.xro/2.0",null,null,null,null);
        
        accountingApi = AccountingApi.getInstance(defaultClient);   
       
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
		accountingApi = null;
        defaultClient = null;
	}

    @Test
    public void getContactGroupsTest() throws IOException {
        System.out.println("@Test - getContactGroupsTest");

        String where = null;
        String order = null;
        ContactGroups response = accountingApi.getContactGroups(accessToken,xeroTenantId,where, order);
        assertThat(response.getContactGroups().get(0).getContactGroupID(), is(equalTo(UUID.fromString("d7a86b80-8dac-4d89-a334-9dcf5753676c"))));
        assertThat(response.getContactGroups().get(0).getName(), is(equalTo("Suppliers")));
        assertThat(response.getContactGroups().get(0).getStatus(), is(equalTo(com.xero.models.accounting.ContactGroup.StatusEnum.ACTIVE)));
        //System.out.println(response.getContactGroups().get(0).toString());
    }    

    @Test
    public void getContactGroupTest() throws IOException {
        System.out.println("@Test - getContactGroupTest");
        
        UUID contactGroupID = UUID.fromString("17b44ed7-4389-4162-91cb-3dd5766e4e22");
        ContactGroups response = accountingApi.getContactGroup(accessToken,xeroTenantId,contactGroupID);

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

        ContactGroups contactGroups = new ContactGroups();
        ContactGroups response = accountingApi.createContactGroup(accessToken,xeroTenantId,contactGroups);
        assertThat(response.getContactGroups().get(0).getContactGroupID(), is(equalTo(UUID.fromString("d7a86b80-8dac-4d89-a334-9dcf5753676c"))));
        assertThat(response.getContactGroups().get(0).getName(), is(equalTo("Suppliers")));
        assertThat(response.getContactGroups().get(0).getStatus(), is(equalTo(com.xero.models.accounting.ContactGroup.StatusEnum.ACTIVE)));
        //System.out.println(response.getContactGroups().get(0).toString());
    }

    @Test
    public void updateContactGroupTest() throws IOException {
        System.out.println("@Test - updateContactGroupTest");

        UUID contactGroupID = UUID.fromString("13f47537-7c1d-4e62-966e-617d76558fc5");
        ContactGroups contactGroups = new ContactGroups();
        ContactGroups response = accountingApi.updateContactGroup(accessToken,xeroTenantId,contactGroupID, contactGroups);
        assertThat(response.getContactGroups().get(0).getContactGroupID(), is(equalTo(UUID.fromString("13f47537-7c1d-4e62-966e-617d76558fc5"))));
        assertThat(response.getContactGroups().get(0).getName(), is(equalTo("Supplier Vendor")));
        assertThat(response.getContactGroups().get(0).getStatus(), is(equalTo(com.xero.models.accounting.ContactGroup.StatusEnum.ACTIVE)));
        //System.out.println(response.getContactGroups().get(0).toString());
    }
    
    @Test
    public void createContactGroupContactsTest() throws IOException {
        System.out.println("@Test - createContactGroupContactsTest");

        UUID contactGroupID = UUID.fromString("13f47537-7c1d-4e62-966e-617d76558fc5");
        Contacts contacts = new Contacts();
        Contacts response = accountingApi.createContactGroupContacts(accessToken,xeroTenantId,contactGroupID, contacts);
        assertThat(response.getContacts().get(0).getContactID(), is(equalTo(UUID.fromString("a3675fc4-f8dd-4f03-ba5b-f1870566bcd7"))));
        //System.out.println(response.getContacts().get(0).toString());
    }

    @Test
    public void deleteContactGroupContactTest() throws IOException {
        System.out.println("@Test - deleteContactGroupContactTest");

        UUID contactGroupID = UUID.fromString("13f47537-7c1d-4e62-966e-617d76558fc5");
        UUID contactID = UUID.fromString("13f47537-7c1d-4e62-966e-617d76558fc5");
        accountingApi.deleteContactGroupContact(accessToken,xeroTenantId,contactGroupID, contactID);
    }

    @Test
    public void deleteContactGroupContactsTest() throws IOException {
        System.out.println("@Test - deleteContactGroupContactsTest");

        UUID contactGroupID = UUID.fromString("13f47537-7c1d-4e62-966e-617d76558fc5");
        accountingApi.deleteContactGroupContacts(accessToken,xeroTenantId,contactGroupID);
    }
	
}
