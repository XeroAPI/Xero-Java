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

public class AccountingApiExpenseClaimsTest {

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
            Thread.sleep(30000);
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
    public void createExpenseClaimTest() throws IOException {
        System.out.println("@Test - createExpenseClaim");
        ExpenseClaims expenseClaims = null;
        Boolean summarizeErrors = null;
        ExpenseClaims response = api.createExpenseClaim(expenseClaims, summarizeErrors);

        assertThat(response.getExpenseClaims().get(0).getExpenseClaimID(), is(equalTo(UUID.fromString("646b15ab-b874-4e13-82ae-f4385b2ac4b6"))));
        assertThat(response.getExpenseClaims().get(0).getStatus(), is(equalTo(com.xero.models.accounting.ExpenseClaim.StatusEnum.SUBMITTED)));
        assertThat(response.getExpenseClaims().get(0).getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getExpenseClaims().get(0).getUser().getFirstName(), is(equalTo("API ")));
        assertThat(response.getExpenseClaims().get(0).getUser().getLastName(), is(equalTo("Team")));
        assertThat(response.getExpenseClaims().get(0).getUser().getEmailAddress(), is(equalTo("api@xero.com")));
        assertThat(response.getExpenseClaims().get(0).getUser().getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2017-11-29T04:06:19.217-08:00"))));
        assertThat(response.getExpenseClaims().get(0).getUser().getIsSubscriber(), is(equalTo(true)));
        assertThat(response.getExpenseClaims().get(0).getUser().getOrganisationRole(), is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
        assertThat(response.getExpenseClaims().get(0).getReceipts().get(0).getDate(), is(equalTo(LocalDate.of(2019, 03, 11))));
        assertThat(response.getExpenseClaims().get(0).getReceipts().get(0).getReceiptID(), is(equalTo(UUID.fromString("dc1c7f6d-0a4c-402f-acac-551d62ce5816"))));
        assertThat(response.getExpenseClaims().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-11T10:37:30.107-07:00"))));  
        assertThat(response.getExpenseClaims().get(0).getTotal(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().get(0).getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().get(0).getAmountDue(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().get(0).getAmountDue().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().get(0).getAmountPaid(), is(equalTo(0.0)));
        assertThat(response.getExpenseClaims().get(0).getAmountPaid().toString(), is(equalTo("0.0")));
        //assertThat(response.getExpenseClaims().get(0).getPayments(), is(equalTo("CONTAINER_PLACEHOLDER")));
        //assertThat(response.getExpenseClaims().get(0).getPaymentDueDate(), is(equalTo(LocalDate.of(YYYY, MM, DD))));  
        //assertThat(response.getExpenseClaims().get(0).getReportingDate(), is(equalTo(LocalDate.of(YYYY, MM, DD))));  
        //assertThat(response.getExpenseClaims().get(0).getReceiptID(), is(equalTo(UUID.fromString("UUID_PLACEHOLDER"))));
        //System.out.println(response.getExpenseClaims().get(0).toString());
    }
    
    @Test
    public void createExpenseClaimHistoryTest() throws IOException {
        System.out.println("@Test - createExpenseClaimHistory not implemented");
        UUID expenseClaimID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords historyRecords = null;
        //HistoryRecords response = api.createExpenseClaimHistory(expenseClaimID, historyRecords);

        // TODO: test validations
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }

    @Test
    public void getExpenseClaimTest() throws IOException {
        System.out.println("@Test - getExpenseClaim");
        UUID expenseClaimID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        ExpenseClaims response = api.getExpenseClaim(expenseClaimID);

        assertThat(response.getExpenseClaims().get(0).getExpenseClaimID(), is(equalTo(UUID.fromString("646b15ab-b874-4e13-82ae-f4385b2ac4b6"))));
        assertThat(response.getExpenseClaims().get(0).getStatus(), is(equalTo(com.xero.models.accounting.ExpenseClaim.StatusEnum.AUTHORISED)));
        assertThat(response.getExpenseClaims().get(0).getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getExpenseClaims().get(0).getUser().getFirstName(), is(equalTo("API ")));
        assertThat(response.getExpenseClaims().get(0).getUser().getLastName(), is(equalTo("Team")));
        assertThat(response.getExpenseClaims().get(0).getUser().getEmailAddress(), is(equalTo("api@xero.com")));
        assertThat(response.getExpenseClaims().get(0).getUser().getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2017-11-29T04:06:19.217-08:00"))));
        assertThat(response.getExpenseClaims().get(0).getUser().getIsSubscriber(), is(equalTo(true)));
        assertThat(response.getExpenseClaims().get(0).getUser().getOrganisationRole(), is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
        assertThat(response.getExpenseClaims().get(0).getReceipts().get(0).getDate(), is(equalTo(LocalDate.of(2019, 03, 11))));
        assertThat(response.getExpenseClaims().get(0).getReceipts().get(0).getReceiptID(), is(equalTo(UUID.fromString("dc1c7f6d-0a4c-402f-acac-551d62ce5816"))));
        assertThat(response.getExpenseClaims().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-11T10:37:31.767-07:00"))));  
        assertThat(response.getExpenseClaims().get(0).getTotal(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().get(0).getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().get(0).getAmountDue(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().get(0).getAmountDue().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().get(0).getAmountPaid(), is(equalTo(0.0)));
        assertThat(response.getExpenseClaims().get(0).getAmountPaid().toString(), is(equalTo("0.0")));
        //System.out.println(response.getExpenseClaims().get(0).toString());
    }

    @Test
    public void getExpenseClaimHistoryTest() throws IOException {
        System.out.println("@Test - getExpenseClaimHistory");
        UUID expenseClaimID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        HistoryRecords response = api.getExpenseClaimHistory(expenseClaimID);

        assertThat(response.getHistoryRecords().get(0).getUser(), is(equalTo("System Generated")));       
        assertThat(response.getHistoryRecords().get(0).getChanges(), is(equalTo("Voided")));     
        assertThat(response.getHistoryRecords().get(0).getDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-11T10:37:36.747-07:00"))));    
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }

    @Test
    public void getExpenseClaimsTest() throws IOException {
        System.out.println("@Test - getExpenseClaims");
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        ExpenseClaims response = api.getExpenseClaims(ifModifiedSince, where, order);

        // TODO: test validations
        assertThat(response.getExpenseClaims().get(0).getExpenseClaimID(), is(equalTo(UUID.fromString("646b15ab-b874-4e13-82ae-f4385b2ac4b6"))));
        assertThat(response.getExpenseClaims().get(0).getStatus(), is(equalTo(com.xero.models.accounting.ExpenseClaim.StatusEnum.AUTHORISED)));
        assertThat(response.getExpenseClaims().get(0).getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getExpenseClaims().get(0).getUser().getFirstName(), is(equalTo("API ")));
        assertThat(response.getExpenseClaims().get(0).getUser().getLastName(), is(equalTo("Team")));
        assertThat(response.getExpenseClaims().get(0).getUser().getEmailAddress(), is(equalTo("api@xero.com")));
        assertThat(response.getExpenseClaims().get(0).getUser().getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2017-11-29T04:06:19.217-08:00"))));
        assertThat(response.getExpenseClaims().get(0).getUser().getIsSubscriber(), is(equalTo(true)));
        assertThat(response.getExpenseClaims().get(0).getUser().getOrganisationRole(), is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
        assertThat(response.getExpenseClaims().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-11T10:37:31.767-07:00"))));  
        assertThat(response.getExpenseClaims().get(0).getTotal(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().get(0).getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().get(0).getAmountDue(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().get(0).getAmountDue().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().get(0).getAmountPaid(), is(equalTo(0.0)));
        assertThat(response.getExpenseClaims().get(0).getAmountPaid().toString(), is(equalTo("0.0")));
        //System.out.println(response.getExpenseClaims().get(0).toString());
    }

    @Test
    public void updateExpenseClaimTest() throws IOException {
        System.out.println("@Test - updateExpenseClaim");
        UUID expenseClaimID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        ExpenseClaims expenseClaims = null;
        ExpenseClaims response = api.updateExpenseClaim(expenseClaimID, expenseClaims);

        // TODO: test validations
        assertThat(response.getExpenseClaims().get(0).getExpenseClaimID(), is(equalTo(UUID.fromString("646b15ab-b874-4e13-82ae-f4385b2ac4b6"))));
        assertThat(response.getExpenseClaims().get(0).getStatus(), is(equalTo(com.xero.models.accounting.ExpenseClaim.StatusEnum.AUTHORISED)));
        assertThat(response.getExpenseClaims().get(0).getUser().getUserID(), is(equalTo(UUID.fromString("d1164823-0ac1-41ad-987b-b4e30fe0b273"))));
        assertThat(response.getExpenseClaims().get(0).getUser().getFirstName(), is(equalTo("API ")));
        assertThat(response.getExpenseClaims().get(0).getUser().getLastName(), is(equalTo("Team")));
        assertThat(response.getExpenseClaims().get(0).getUser().getEmailAddress(), is(equalTo("api@xero.com")));
        assertThat(response.getExpenseClaims().get(0).getUser().getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2017-11-29T04:06:19.217-08:00"))));
        assertThat(response.getExpenseClaims().get(0).getUser().getIsSubscriber(), is(equalTo(true)));
        assertThat(response.getExpenseClaims().get(0).getUser().getOrganisationRole(), is(equalTo(com.xero.models.accounting.User.OrganisationRoleEnum.FINANCIALADVISER)));
        assertThat(response.getExpenseClaims().get(0).getReceipts().get(0).getDate(), is(equalTo(LocalDate.of(2019, 03, 11))));
        assertThat(response.getExpenseClaims().get(0).getReceipts().get(0).getReceiptID(), is(equalTo(UUID.fromString("dc1c7f6d-0a4c-402f-acac-551d62ce5816"))));
        assertThat(response.getExpenseClaims().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-11T10:37:31.767-07:00"))));  
        assertThat(response.getExpenseClaims().get(0).getTotal(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().get(0).getTotal().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().get(0).getAmountDue(), is(equalTo(40.0)));
        assertThat(response.getExpenseClaims().get(0).getAmountDue().toString(), is(equalTo("40.0")));
        assertThat(response.getExpenseClaims().get(0).getAmountPaid(), is(equalTo(0.0)));
        assertThat(response.getExpenseClaims().get(0).getAmountPaid().toString(), is(equalTo("0.0")));
        //System.out.println(response.getExpenseClaims().get(0).toString());
    }
}
