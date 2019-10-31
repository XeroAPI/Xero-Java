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

public class AccountingApiLinkedTransactionsTest {

	CustomJsonConfig config;
	ApiClient apiClientForAccounting; 
	AccountingApi api; 

    private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
		config = new CustomJsonConfig();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting-oauth1/2.0.0",null,null,null);
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
    public void createLinkedTransactionTest() throws IOException {
        System.out.println("@Test - createLinkedTransaction");
        LinkedTransactions linkedTransactions = null;
        LinkedTransactions response = api.createLinkedTransaction(linkedTransactions);

        assertThat(response.getLinkedTransactions().get(0).getSourceTransactionID(), is(equalTo(UUID.fromString("a848644a-f20f-4630-98c3-386bd7505631"))));
        assertThat(response.getLinkedTransactions().get(0).getSourceLineItemID(), is(equalTo(UUID.fromString("b0df260d-3cc8-4ced-9bd6-41924f624ed3"))));
        assertThat(response.getLinkedTransactions().get(0).getLinkedTransactionID(), is(equalTo(UUID.fromString("e9684b6c-4df9-45a0-917b-85cc29857008"))));
        assertThat(response.getLinkedTransactions().get(0).getStatus(), is(equalTo(com.xero.models.accounting.LinkedTransaction.StatusEnum.DRAFT)));
        assertThat(response.getLinkedTransactions().get(0).getType(), is(equalTo(com.xero.models.accounting.LinkedTransaction.TypeEnum.BILLABLEEXPENSE)));
        assertThat(response.getLinkedTransactions().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-11T17:37:35-07:00"))));  
        assertThat(response.getLinkedTransactions().get(0).getSourceTransactionTypeCode(), is(equalTo(com.xero.models.accounting.LinkedTransaction.SourceTransactionTypeCodeEnum.ACCPAY)));
        assertThat(response.getLinkedTransactions().get(0).getValidationErrors().get(0).getMessage(), is(equalTo("The SourceLineItemID and SourceTransactionID do not match")));
        //System.out.println(response.getLinkedTransactions().get(0).toString());
    }
    
    @Test
    public void deleteLinkedTransactionTest() throws IOException {
        System.out.println("@Test - deleteLinkedTransaction");
        UUID linkedTransactionID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        api.deleteLinkedTransaction(linkedTransactionID);
    }

    @Test
    public void getLinkedTransactionTest() throws IOException {
        System.out.println("@Test - getLinkedTransaction");
        UUID linkedTransactionID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        LinkedTransactions response = api.getLinkedTransaction(linkedTransactionID);

        assertThat(response.getLinkedTransactions().get(0).getSourceTransactionID(), is(equalTo(UUID.fromString("aec416dd-38ea-40dc-9f0b-813c8c71f87f"))));
        assertThat(response.getLinkedTransactions().get(0).getSourceLineItemID(), is(equalTo(UUID.fromString("77e0b23b-5b79-4f4b-ae20-c9031d41442f"))));
        assertThat(response.getLinkedTransactions().get(0).getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getLinkedTransactions().get(0).getTargetTransactionID(), is(equalTo(UUID.fromString("83693fc1-5b05-4807-b190-109d4a85dd5f"))));
        assertThat(response.getLinkedTransactions().get(0).getTargetLineItemID(), is(equalTo(UUID.fromString("d5128ff1-0f39-4d2a-a6d5-46dfaf5f075c"))));
        assertThat(response.getLinkedTransactions().get(0).getLinkedTransactionID(), is(equalTo(UUID.fromString("5cf7d9c0-b9a7-4433-a2dc-ae3c11bba39b"))));
        assertThat(response.getLinkedTransactions().get(0).getStatus(), is(equalTo(com.xero.models.accounting.LinkedTransaction.StatusEnum.ONDRAFT)));
        assertThat(response.getLinkedTransactions().get(0).getType(), is(equalTo(com.xero.models.accounting.LinkedTransaction.TypeEnum.BILLABLEEXPENSE)));
        assertThat(response.getLinkedTransactions().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-11T16:46:31-07:00"))));  
        assertThat(response.getLinkedTransactions().get(0).getSourceTransactionTypeCode(), is(equalTo(com.xero.models.accounting.LinkedTransaction.SourceTransactionTypeCodeEnum.ACCPAY)));
        //System.out.println(response.getLinkedTransactions().get(0).toString());
    }
    
    @Test
    public void getLinkedTransactionsTest() throws IOException {
        System.out.println("@Test - getLinkedTransactions");
        Integer page = null;
        String linkedTransactionID = null;
        String sourceTransactionID = null;
        String contactID = null;
        String status = null;
        String targetTransactionID = null;
        LinkedTransactions response = api.getLinkedTransactions(page, linkedTransactionID, sourceTransactionID, contactID, status, targetTransactionID);

        assertThat(response.getLinkedTransactions().get(0).getSourceTransactionID(), is(equalTo(UUID.fromString("aec416dd-38ea-40dc-9f0b-813c8c71f87f"))));
        assertThat(response.getLinkedTransactions().get(0).getSourceLineItemID(), is(equalTo(UUID.fromString("77e0b23b-5b79-4f4b-ae20-c9031d41442f"))));
        assertThat(response.getLinkedTransactions().get(0).getContactID(), is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
        assertThat(response.getLinkedTransactions().get(0).getTargetTransactionID(), is(equalTo(UUID.fromString("83693fc1-5b05-4807-b190-109d4a85dd5f"))));
        assertThat(response.getLinkedTransactions().get(0).getTargetLineItemID(), is(equalTo(UUID.fromString("d5128ff1-0f39-4d2a-a6d5-46dfaf5f075c"))));
        assertThat(response.getLinkedTransactions().get(0).getLinkedTransactionID(), is(equalTo(UUID.fromString("5cf7d9c0-b9a7-4433-a2dc-ae3c11bba39b"))));
        assertThat(response.getLinkedTransactions().get(0).getStatus(), is(equalTo(com.xero.models.accounting.LinkedTransaction.StatusEnum.ONDRAFT)));
        assertThat(response.getLinkedTransactions().get(0).getType(), is(equalTo(com.xero.models.accounting.LinkedTransaction.TypeEnum.BILLABLEEXPENSE)));
        assertThat(response.getLinkedTransactions().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-11T16:46:31-07:00"))));  
        assertThat(response.getLinkedTransactions().get(0).getSourceTransactionTypeCode(), is(equalTo(com.xero.models.accounting.LinkedTransaction.SourceTransactionTypeCodeEnum.ACCPAY)));
        //System.out.println(response.getLinkedTransactions().get(0).toString());
    }

    @Test
    public void updateLinkedTransactionTest() throws IOException {
        System.out.println("@Test - updateLinkedTransaction");
        UUID linkedTransactionID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        LinkedTransactions linkedTransactions = null;
        LinkedTransactions response = api.updateLinkedTransaction(linkedTransactionID, linkedTransactions);

        assertThat(response.getLinkedTransactions().get(0).getSourceTransactionID(), is(equalTo(UUID.fromString("a848644a-f20f-4630-98c3-386bd7505631"))));
        assertThat(response.getLinkedTransactions().get(0).getSourceLineItemID(), is(equalTo(UUID.fromString("b0df260d-3cc8-4ced-9bd6-41924f624ed3"))));
        assertThat(response.getLinkedTransactions().get(0).getContactID(), is(equalTo(UUID.fromString("4e1753b9-018a-4775-b6aa-1bc7871cfee3"))));
        assertThat(response.getLinkedTransactions().get(0).getLinkedTransactionID(), is(equalTo(UUID.fromString("e9684b6c-4df9-45a0-917b-85cc29857008"))));     
        assertThat(response.getLinkedTransactions().get(0).getStatus(), is(equalTo(com.xero.models.accounting.LinkedTransaction.StatusEnum.DRAFT)));
        assertThat(response.getLinkedTransactions().get(0).getType(), is(equalTo(com.xero.models.accounting.LinkedTransaction.TypeEnum.BILLABLEEXPENSE)));
        assertThat(response.getLinkedTransactions().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-11T17:37:35-07:00"))));  
        assertThat(response.getLinkedTransactions().get(0).getSourceTransactionTypeCode(), is(equalTo(com.xero.models.accounting.LinkedTransaction.SourceTransactionTypeCodeEnum.ACCPAY)));
        //System.out.println(response.getLinkedTransactions().get(0).toString());
    }
}