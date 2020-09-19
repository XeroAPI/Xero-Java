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
import com.xero.models.payrollnz.*;

import java.io.File;
import java.net.URL;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import org.threeten.bp.*;
import org.threeten.bp.temporal.ChronoUnit;
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

public class PayrollNzApiSettingsTest {

	ApiClient defaultClient; 
    PayrollNzApi payrollNzApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        // Init projectApi client
        // NEW Sandbox for API Mocking
		defaultClient = new ApiClient("https://xero-payroll-nz.getsandbox.com:443/payroll.xro/2.0",null,null,null,null);
        payrollNzApi = PayrollNzApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollNzApi = null;
        defaultClient = null;
	}

    @Test
    public void getSettingsTest() throws IOException {
        System.out.println("@Test NZ Payroll - getSettingsTest");
       
        Settings response = payrollNzApi.getSettings(accessToken, xeroTenantId);
        
        assertThat(response.getSettings().getAccounts().get(0).getAccountID(), is(equalTo(UUID.fromString("fa5cdc43-643b-4ad8-b4ac-3ffe0d0f4488"))));
        assertThat(response.getSettings().getAccounts().get(0).getType(), is(equalTo(com.xero.models.payrollnz.Account.TypeEnum.PAYELIABILITY)));
        assertThat(response.getSettings().getAccounts().get(0).getCode() , is(equalTo("825")));
        assertThat(response.getSettings().getAccounts().get(0).getName() , is(equalTo("PAYE Payable")));
        assertThat(response.getSettings().getAccounts().get(1).getAccountID(), is(equalTo(UUID.fromString("e529775e-ea49-4a19-86f0-8d3e1ecda2cd"))));
        assertThat(response.getSettings().getAccounts().get(1).getType(), is(equalTo(com.xero.models.payrollnz.Account.TypeEnum.WAGESPAYABLE)));
        assertThat(response.getSettings().getAccounts().get(1).getCode() , is(equalTo("814")));
        assertThat(response.getSettings().getAccounts().get(1).getName() , is(equalTo("Wages Payable - Payroll")));
        assertThat(response.getSettings().getAccounts().get(2).getAccountID(), is(equalTo(UUID.fromString("1c91e520-a12b-45cc-8194-99950858e5bf"))));
        assertThat(response.getSettings().getAccounts().get(2).getType(), is(equalTo(com.xero.models.payrollnz.Account.TypeEnum.WAGESEXPENSE)));
        assertThat(response.getSettings().getAccounts().get(2).getCode() , is(equalTo("477")));
        assertThat(response.getSettings().getAccounts().get(2).getName() , is(equalTo("Salaries")));
        assertThat(response.getSettings().getAccounts().get(3).getAccountID(), is(equalTo(UUID.fromString("ac993f75-035b-433c-82e0-7b7a2d40802c"))));
        assertThat(response.getSettings().getAccounts().get(3).getType(), is(equalTo(com.xero.models.payrollnz.Account.TypeEnum.BANK)));
        assertThat(response.getSettings().getAccounts().get(3).getCode() , is(equalTo("090")));
        assertThat(response.getSettings().getAccounts().get(3).getName() , is(equalTo("Business Bank Account")));

        //System.out.println(response.toString());
    }
}
