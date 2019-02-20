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
import com.xero.api.Config;
import com.xero.api.JsonConfig;
import com.xero.api.client.*;
import com.xero.models.accounting.*;

import com.xero.example.SampleData;

import org.threeten.bp.*;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Calendar;
import java.util.Map;


public class AccountingApiTest {

	Config config;
	
	ApiClient apiClientForAccounting; 
	AccountingApi accountingApi; 
	Map<String, String> params;

	OffsetDateTime ifModifiedSince;
	String where;
	String order;
	boolean summarizeErrors;
	String ids;
	boolean includeArchived;
	String invoiceNumbers;
	String contactIDs;
	String statuses;
	boolean createdByMyApp;

	@Before
	public void setUp() {
		config = JsonConfig.getInstance();
		apiClientForAccounting = new ApiClient(config.getApiUrl(),null,null,null);
		accountingApi = new AccountingApi(config);
		accountingApi.setApiClient(apiClientForAccounting);
		accountingApi.setOAuthToken(config.getConsumerKey(), config.getConsumerSecret());

		ifModifiedSince = null;
		where = null;
		order = null;
		summarizeErrors = false;
		ids= null;
		includeArchived = false;
		invoiceNumbers = null;
		contactIDs = null;
		statuses = null;
		createdByMyApp = false;
	}

	@After
	public void tearDown() {
		accountingApi = null;
		apiClientForAccounting = null;
	}

	@Test
	public void testGetOrgansation() {
		try {
			Organisations organisations = accountingApi.getOrganisations();

			assert(organisations.getOrganisations().size() > 0);
			System.out.println("Get a Organisation - Name : " + organisations.getOrganisations().get(0).getName());
		} catch (XeroApiException e) {
			System.out.println(e.getMessage());	
		} catch (Exception e) {
			System.out.println(e.getMessage());	
		}
	}
	

}
