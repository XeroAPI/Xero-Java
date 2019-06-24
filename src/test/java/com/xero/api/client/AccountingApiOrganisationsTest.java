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

public class AccountingApiOrganisationsTest {

	ApiClient defaultClient; 
    AccountingApi accountingApi; 
    String xeroTenantId = "3697c2dc5-cc47-4afd-8ec8-74990b8761e9";  
   
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
    public void getOrganisationsTest() throws IOException {
        System.out.println("@Test - getOrganisations");
        Organisations response = accountingApi.getOrganisations(xeroTenantId);

        assertThat(response.getOrganisations().get(0).getOrganisationID(), is(equalTo(UUID.fromString("b2c885a9-4bb9-4a00-9b6e-6c2bf60b1a2b"))));
        assertThat(response.getOrganisations().get(0).getApIKey(), is(equalTo("CTJ60UH519MXQIXEJSDPDALS3EOZ5Y")));
        assertThat(response.getOrganisations().get(0).getName(), is(equalTo("Dev Evangelist - Sid Test 3 (NZ-2016-02)")));
        assertThat(response.getOrganisations().get(0).getLegalName(), is(equalTo("Dev Evangelist - Sid Test 3 (NZ-2016-02)")));
        assertThat(response.getOrganisations().get(0).getPaysTax(), is(equalTo(true)));
        assertThat(response.getOrganisations().get(0).getVersion(), is(equalTo(com.xero.models.accounting.Organisation.VersionEnum.NZ)));
        assertThat(response.getOrganisations().get(0).getOrganisationType(), is(equalTo(com.xero.models.accounting.Organisation.OrganisationTypeEnum.COMPANY)));
        assertThat(response.getOrganisations().get(0).getBaseCurrency(), is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
        assertThat(response.getOrganisations().get(0).getCountryCode(), is(equalTo(com.xero.models.accounting.CountryCode.NZ)));
        assertThat(response.getOrganisations().get(0).getIsDemoCompany(), is(equalTo(false)));
        assertThat(response.getOrganisations().get(0).getOrganisationStatus(), is(equalTo("ACTIVE")));
        assertThat(response.getOrganisations().get(0).getFinancialYearEndDay(), is(equalTo(31)));
        assertThat(response.getOrganisations().get(0).getFinancialYearEndMonth(), is(equalTo(3)));
        assertThat(response.getOrganisations().get(0).getTaxNumber(), is(equalTo("071-138-054")));
        assertThat(response.getOrganisations().get(0).getSalesTaxBasis(), is(equalTo(com.xero.models.accounting.Organisation.SalesTaxBasisEnum.PAYMENTS)));
        assertThat(response.getOrganisations().get(0).getSalesTaxPeriod(), is(equalTo(com.xero.models.accounting.Organisation.SalesTaxPeriodEnum.TWOMONTHS)));
        assertThat(response.getOrganisations().get(0).getDefaultSalesTax(), is(equalTo("Tax Exclusive")));
        assertThat(response.getOrganisations().get(0).getDefaultPurchasesTax(), is(equalTo("Tax Exclusive")));
        assertThat(response.getOrganisations().get(0).getPeriodLockDate(), is(equalTo(LocalDate.of(2018,12,30))));  
        assertThat(response.getOrganisations().get(0).getEndOfYearLockDate(), is(equalTo(LocalDate.of(2018,12,30))));  
        assertThat(response.getOrganisations().get(0).getCreatedDateUTC(), is(equalTo(OffsetDateTime.parse("2016-02-18T12:29:53-08:00"))));  
        assertThat(response.getOrganisations().get(0).getTimezone(), is(equalTo(com.xero.models.accounting.TimeZone.NEWZEALANDSTANDARDTIME)));
        assertThat(response.getOrganisations().get(0).getOrganisationEntityType(), is(equalTo(com.xero.models.accounting.Organisation.OrganisationEntityTypeEnum.COMPANY)));
        assertThat(response.getOrganisations().get(0).getShortCode(), is(equalTo("!mBdtL")));
        assertThat(response.getOrganisations().get(0).getPropertyClass(), is(equalTo(com.xero.models.accounting.Organisation.PropertyClassEnum.PREMIUM)));
        assertThat(response.getOrganisations().get(0).getEdition(), is(equalTo(com.xero.models.accounting.Organisation.EditionEnum.BUSINESS)));
        //assertThat(response.getOrganisations().get(0).getRegistrationNumber(), is(equalTo("STRING_ONLY_PLACEHOLDER")));        
        //assertThat(response.getOrganisations().get(0).getLineOfBusiness(), is(equalTo("STRING_ONLY_PLACEHOLDER")));
        //assertThat(response.getOrganisations().get(0).getAddresses(), is(equalTo("CONTAINER_PLACEHOLDER")));
        //assertThat(response.getOrganisations().get(0).getPhones(), is(equalTo("CONTAINER_PLACEHOLDER")));
        //assertThat(response.getOrganisations().get(0).getExternalLinks(), is(equalTo("CONTAINER_PLACEHOLDER")));
        //assertThat(response.getOrganisations().get(0).getPaymentTerms(), is(equalTo("MODEL_PLACEHOLDER")));
        //System.out.println(response.getOrganisations().get(0).toString());
    }
}