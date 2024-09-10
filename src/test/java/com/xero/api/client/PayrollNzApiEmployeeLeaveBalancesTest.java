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

public class PayrollNzApiEmployeeLeaveBalancesTest {

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
		defaultClient = new ApiClient("http://127.0.0.1:4016",null,null,null,null);
        payrollNzApi = PayrollNzApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollNzApi = null;
        defaultClient = null;
	}

    @Test
    public void getEmployeeLeaveBalancesTest() throws IOException {
        System.out.println("@Test NZ Payroll - getEmployeeLeaveBalancesTest");
        
        UUID employeeId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        EmployeeLeaveBalances response = payrollNzApi.getEmployeeLeaveBalances(accessToken, xeroTenantId, employeeId);
        
        assertThat(response.getPagination().getPage() , is(equalTo(1)));
        assertThat(response.getPagination().getPageSize() , is(equalTo(100)));
        assertThat(response.getPagination().getPageCount() , is(equalTo(1)));
        assertThat(response.getPagination().getItemCount() , is(equalTo(4)));
        assertThat(response.getLeaveBalances().get(0).getLeaveTypeID(), is(equalTo(UUID.fromString("0441497f-5dc7-4cd3-a90d-f2e07e21b2a6"))));
        assertThat(response.getLeaveBalances().get(0).getName() , is(equalTo("Holiday Pay")));
        assertThat(response.getLeaveBalances().get(0).getBalance() , is(equalTo(0.0)));
        assertThat(response.getLeaveBalances().get(0).getTypeOfUnits() , is(equalTo("Dollars")));

        //System.out.println(response.toString());
    }
}
