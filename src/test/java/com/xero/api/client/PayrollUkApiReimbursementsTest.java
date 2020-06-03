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
import com.xero.models.payrolluk.*;
import com.xero.models.payrolluk.Benefit.CalculationTypeEnum;
import com.xero.models.payrolluk.Benefit.CategoryEnum;

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

public class PayrollUkApiReimbursementsTest {

	ApiClient defaultClient; 
    PayrollUkApi payrollUkApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        // Init projectApi client
        // NEW Sandbox for API Mocking
		defaultClient = new ApiClient("https://xero-payroll-uk.getsandbox.com:443/payroll.xro/2.0",null,null,null,null);
        payrollUkApi = PayrollUkApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollUkApi = null;
        defaultClient = null;
	}

    @Test
    public void getReimbursementsTest() throws IOException {
        System.out.println("@Test UK Payroll - getReimbursementsTest");
       
        int page = 1;
        Reimbursements response = payrollUkApi.getReimbursements(accessToken, xeroTenantId, page);
        
        assertThat(response.getReimbursements().get(0).getReimbursementID(),is(equalTo(UUID.fromString("fef6115f-1606-4a74-be54-312d46b0eb0e"))));
        assertThat(response.getReimbursements().get(0).getName(), is(equalTo("Travel Allowance")));
        assertThat(response.getReimbursements().get(0).getAccountID(),is(equalTo(UUID.fromString("c7b73345-7f25-428a-bb97-7b20a1470a53"))));
        assertThat(response.getReimbursements().get(0).getCurrentRecord(), is(equalTo(true)));
        
        //System.out.println(response.toString());
    }

    @Test
    public void getReimbursementTest() throws IOException {
        System.out.println("@Test UK Payroll - getReimbursementTest");
       
        UUID reimbursementID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        ReimbursementObject response = payrollUkApi.getReimbursement(accessToken, xeroTenantId, reimbursementID);
        
        assertThat(response.getReimbursement().getReimbursementID(),is(equalTo(UUID.fromString("fef6115f-1606-4a74-be54-312d46b0eb0e"))));
        assertThat(response.getReimbursement().getName(), is(equalTo("Travel Allowance")));
        assertThat(response.getReimbursement().getAccountID(),is(equalTo(UUID.fromString("c7b73345-7f25-428a-bb97-7b20a1470a53"))));
        assertThat(response.getReimbursement().getCurrentRecord(), is(equalTo(true)));
        
        //System.out.println(response.toString());
    }

    @Test
    public void createReimbursementTest() throws IOException {
        System.out.println("@Test UK Payroll - createReimbursementTest");
       
        Reimbursement reimbursement = new Reimbursement();
        ReimbursementObject response = payrollUkApi.createReimbursement(accessToken, xeroTenantId, reimbursement);
        
        assertThat(response.getReimbursement().getReimbursementID(),is(equalTo(UUID.fromString("2b1b587a-39f6-43f8-9dd9-a858314333c8"))));
        assertThat(response.getReimbursement().getName(), is(equalTo("My new Reimburse")));
        assertThat(response.getReimbursement().getAccountID(),is(equalTo(UUID.fromString("9ee28149-32a9-4661-8eab-a28738696983"))));
        assertThat(response.getReimbursement().getCurrentRecord(), is(equalTo(true)));
        
        //System.out.println(response.toString());
    }
}
