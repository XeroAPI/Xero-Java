package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.payrolluk.*;
import java.io.IOException;
import java.util.*;
import java.io.InputStream;

public class PayrollUkApiTrackingCategoriesTest {

	ApiClient defaultClient; 
    PayrollUkApi payrollUkApi;
     
	String accessToken;
    String xeroTenantId; 
	
	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("payrolluk.api.url"),null,null,null,null);
        payrollUkApi = PayrollUkApi.getInstance(defaultClient); 
	}

	public void tearDown() {
		payrollUkApi = null;
        defaultClient = null;
	}

    @Test
    public void getTrackingCategoriesTest() throws IOException {
        System.out.println("@Test UK Payroll - getTrackingCategoriesTest");
       
        TrackingCategories response = payrollUkApi.getTrackingCategories(accessToken, xeroTenantId);
        
        assertThat(response.getTrackingCategories().getEmployeeGroupsTrackingCategoryID(), is(equalTo(UUID.fromString("9d8ad8f6-0d0f-41e0-8851-ef47e8b54ae6"))));
        assertThat(response.getTrackingCategories().getTimesheetTrackingCategoryID(), is(equalTo(UUID.fromString("1d7d2015-10e7-4ecb-8b44-f331c8b63e2d"))));
          
        //System.out.println(response.toString());
    }
}
