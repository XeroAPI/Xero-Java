package com.xero.api.client;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;

import com.xero.api.ApiClient;
import com.xero.models.payrollau.SuperFundProducts;

public class PayrollAuApiSuperfundProductTest {

	ApiClient defaultClient; 
    PayrollAuApi payrollAuApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        // Init projectApi client
        // NEW Sandbox for API Mocking
		defaultClient = new ApiClient("http://127.0.0.1:4015",null,null,null,null);
        payrollAuApi = PayrollAuApi.getInstance(defaultClient);   
       
	}

	public void tearDown() {
		payrollAuApi = null;
        defaultClient = null;
	}

	@Test
    public void getSuperfundProductsTest() throws IOException {
        System.out.println("@Test - getSuperfundProductsTest");
        
        SuperFundProducts response = payrollAuApi.getSuperfundProducts(accessToken, xeroTenantId, null, null);
        
        assertThat(response.getSuperFundProducts().get(0).getABN(), is(equalTo("24248426878")));        
        assertThat(response.getSuperFundProducts().get(0).getUSI(), is(equalTo("OSF0001AU")));        
        assertThat(response.getSuperFundProducts().get(0).getProductName(), is(equalTo("Accumulate Plus (Commonwealth Bank Group Super)")));        
       //System.out.println(response.toString());
    }
}
