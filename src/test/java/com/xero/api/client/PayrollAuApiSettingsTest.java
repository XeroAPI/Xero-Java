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
import com.xero.models.payrollau.*;

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

public class PayrollAuApiSettingsTest {

	ApiClient defaultClient; 
    PayrollAuApi payrollAuApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        // Init projectApi client
        // NEW Sandbox for API Mocking
		defaultClient = new ApiClient("https://xero-payroll-au.getsandbox.com:443/payroll.xro/1.0",null,null,null,null);
        payrollAuApi = PayrollAuApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollAuApi = null;
        defaultClient = null;
	}

	@Test
    public void getSettingsTest() throws IOException {
        System.out.println("@Test - getSettingsTest");
        
        SettingsObject response = payrollAuApi.getSettings(accessToken, xeroTenantId);
        
        assertThat(response.getSettings().getAccounts().get(0).getAccountID(), is(equalTo(UUID.fromString("85bd2954-7ef5-4fbe-9e40-a1990d0fd63f"))));        
        assertThat(response.getSettings().getAccounts().get(0).getType(), is(equalTo(com.xero.models.payrollau.AccountType.BANK)));
        assertThat(response.getSettings().getAccounts().get(0).getCode(), is(equalTo("094")));
        assertThat(response.getSettings().getAccounts().get(0).getName(), is(equalTo("Bank of A")));
        assertThat(response.getSettings().getTrackingCategories().getEmployeeGroups().getTrackingCategoryID(), is(equalTo(UUID.fromString("a28f419f-6ec3-4dcf-9be0-7959ea983630"))));
        assertThat(response.getSettings().getTrackingCategories().getEmployeeGroups().getTrackingCategoryName(), is(equalTo("Foo70317")));
        assertThat(response.getSettings().getTrackingCategories().getTimesheetCategories().getTrackingCategoryID(), is(equalTo(UUID.fromString("89375aed-ed51-4624-9e5d-92db6bfa8974"))));
        assertThat(response.getSettings().getTrackingCategories().getTimesheetCategories().getTrackingCategoryName(), is(equalTo("Foo32551")));
        assertThat(response.getSettings().getDaysInPayrollYear(), is(equalTo(364)));
        //System.out.println(response.toString());
    }
}
