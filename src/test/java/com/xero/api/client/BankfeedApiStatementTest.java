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
import com.xero.api.client.*;
import com.xero.models.bankfeeds.*;

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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class BankfeedApiStatementTest {

	ApiClient defaultClient; 
	BankFeedsApi bankfeedsApi; 
	String accessToken;
    String xeroTenantId; 	
	
	private static boolean setUpIsDone = false;

	@Before
	public void setUp() {
		
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        // Init AccountingApi client
		//defaultClient = new ApiClient("https://virtserver.swaggerhub.com/Xero/bankfeeds/1.0.0",null,null,null,null);
		defaultClient = new ApiClient("https://xero-bank-feeds.getsandbox.com:443/bankfeeds.xro/1.0",null,null,null,null);
        bankfeedsApi = BankFeedsApi.getInstance(defaultClient);	
        
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
		bankfeedsApi = null;
		defaultClient = null;
	}

	@Test
	public void testCreateStatements() throws Exception {
		System.out.println("@Test - createStatements");

		Statements newStatements = new Statements();
		Statements response = bankfeedsApi.createStatements(accessToken,xeroTenantId,newStatements);
       assertThat(response.getItems().get(0).getId().toString(), (equalTo("d69b02b7-a30c-464a-99cf-ba9770373c61")));
		assertThat(response.getItems().get(0).getFeedConnectionId().toString(), (equalTo("6a4b9ff5-3a5f-4321-936b-4796163550f6")));
		assertThat(response.getItems().get(0).getStatus(), is(equalTo(com.xero.models.bankfeeds.Statement.StatusEnum.PENDING)));   
	}

	@Test
	public void testGetStatement() throws Exception {
		System.out.println("@Test - getStatement");

		Statements newStatements = new Statements();
		Statements response = bankfeedsApi.createStatements(accessToken,xeroTenantId,newStatements);
       	assertThat(response.getItems().get(0).getId().toString(), (equalTo("d69b02b7-a30c-464a-99cf-ba9770373c61")));
		assertThat(response.getItems().get(0).getFeedConnectionId().toString(), (equalTo("6a4b9ff5-3a5f-4321-936b-4796163550f6")));
		assertThat(response.getItems().get(0).getStatus(), is(equalTo(com.xero.models.bankfeeds.Statement.StatusEnum.PENDING))); 
	}

    @Test
	public void testGetStatements() throws Exception {
		System.out.println("@Test - getStatements"); 
		Statements response = bankfeedsApi.getStatements(accessToken,xeroTenantId,1, 3, null, null);        
        assertThat(response.getItems().get(0).getId().toString(), (equalTo("9817e4b8-82b3-4526-91f7-040bd278053f")));
		assertThat(response.getItems().get(0).getFeedConnectionId().toString(), (equalTo("6a4b9ff5-3a5f-4321-936b-4796163550f6")));
		assertThat(response.getItems().get(0).getStatus(), is(equalTo(com.xero.models.bankfeeds.Statement.StatusEnum.REJECTED))); 
		assertThat(response.getItems().get(0).getStartDate(), is(equalTo(LocalDate.of(2019, 8, 01))));  
		assertThat(response.getItems().get(0).getEndDate(), is(equalTo(LocalDate.of(2019, 8, 15))));  
        assertThat(response.getItems().get(0).getStartBalance().getAmount().toString(), is(equalTo("100.0")));
        assertThat(response.getItems().get(0).getStartBalance().getCreditDebitIndicator(), is(equalTo(com.xero.models.bankfeeds.CreditDebitIndicator.CREDIT))); 
        assertThat(response.getItems().get(0).getEndBalance().getAmount().toString(), is(equalTo("150.0")));
        assertThat(response.getItems().get(0).getEndBalance().getCreditDebitIndicator(), is(equalTo(com.xero.models.bankfeeds.CreditDebitIndicator.CREDIT))); 
		assertThat(response.getItems().get(0).getErrors().get(0).getTitle(), is(equalTo("Duplicate Statement Received")));
		assertThat(response.getItems().get(0).getErrors().get(0).getStatus().toString(), is(equalTo("409")));
		assertThat(response.getItems().get(0).getErrors().get(0).getDetail(), is(equalTo("The received statement was marked as a duplicate.")));
		assertThat(response.getItems().get(0).getErrors().get(0).getType(), is(equalTo(com.xero.models.bankfeeds.Error.TypeEnum.DUPLICATE_STATEMENT)));
    }
}
