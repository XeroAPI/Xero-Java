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

public class AccountingApiExpenseClaimsTest {

	ApiClient defaultClient; 
    AccountingApi accountingApi; 
    String xeroTenantId = "3697c2dc5-cc47-4afd-8ec8-74990b8761e9";  
    File body;

    private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
		// Set Access Token from Storage
        String accessToken = "123";
        Credential credential =  new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        
        // Create requestFactory with credentials
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);

        // Init AccountingApi client
        defaultClient = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting/2.0.0",null,null,null,requestFactory);
        accountingApi = new AccountingApi(defaultClient);
       
        ClassLoader classLoader = getClass().getClassLoader();
        body = new File(classLoader.getResource("helo-heros.jpg").getFile());
       
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
    public void createExpenseClaimTest() throws IOException {
        System.out.println("@Test - createExpenseClaim");
        ExpenseClaims expenseClaims = new ExpenseClaims();
        Boolean summarizeErrors = null;
        ExpenseClaims response = accountingApi.createExpenseClaim(xeroTenantId, expenseClaims, summarizeErrors);

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
        HistoryRecords historyRecords = new HistoryRecords();
        //HistoryRecords response = accountingApi.createExpenseClaimHistory(expenseClaimID, historyRecords);

        // TODO: test validations
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }

    @Test
    public void getExpenseClaimTest() throws IOException {
        System.out.println("@Test - getExpenseClaim");
        UUID expenseClaimID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        ExpenseClaims response = accountingApi.getExpenseClaim(xeroTenantId, expenseClaimID);

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
        HistoryRecords response = accountingApi.getExpenseClaimHistory(xeroTenantId, expenseClaimID);

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
        ExpenseClaims response = accountingApi.getExpenseClaims(xeroTenantId, ifModifiedSince, where, order);

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
        ExpenseClaims expenseClaims = new ExpenseClaims();
        ExpenseClaims response = accountingApi.updateExpenseClaim(xeroTenantId, expenseClaimID, expenseClaims);

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