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

public class PayrollNzApiPaymentMethodsTest {

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
    public void getEmployeePaymentMethodTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEmployeePaymentMethodTest");
       
        int page = 1;
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        PaymentMethodObject response = payrollNzApi.getEmployeePaymentMethod(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getPaymentMethod().getBankAccounts().get(0).getAccountName(), is(equalTo("Casual Worker")));
        assertThat(response.getPaymentMethod().getBankAccounts().get(0).getAccountNumber(), is(equalTo("0607050201419000")));
        assertThat(response.getPaymentMethod().getBankAccounts().get(0).getCalculationType(), is(equalTo(com.xero.models.payrollnz.BankAccount.CalculationTypeEnum.BALANCE)));
        
        //System.out.println(response.toString());
    }

    @Test
    public void createEmployeePaymentMethodTest() throws IOException {
        System.out.println("@Test NZ Payroll - createEmployeePaymentMethodTest - METHOD NOT WORKING");
       
        int page = 1;
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        PaymentMethod paymentMethod = new PaymentMethod();
        PaymentMethodObject response = payrollNzApi.createEmployeePaymentMethod(accessToken, xeroTenantId, employeeId, paymentMethod);
        
        // assertThat(response.getPaymentMethod().getPaymentMethod(), is(equalTo(com.xero.models.payrollnz.PaymentMethod.PaymentMethodEnum.ELECTRONICALLY)));
        // assertThat(response.getPaymentMethod().getBankAccounts().get(0).getAccountName(), is(equalTo("Sid BofA")));
        // assertThat(response.getPaymentMethod().getBankAccounts().get(0).getAccountNumber(), is(equalTo("24987654")));
        // assertThat(response.getPaymentMethod().getBankAccounts().get(0).getSortCode(), is(equalTo("287654")));

        //System.out.println(response.toString());
    }
}
