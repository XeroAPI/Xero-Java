package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrollnz.*;

import java.io.IOException;
import java.util.UUID;
import java.util.*;
import java.io.InputStream;

public class PayrollNzApiTrackingCategoriesTest {

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
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")){
            properties.load(input);
            defaultClient = new ApiClient(properties.getProperty("payrollnz.api.url"),null,null,null,null);
            payrollNzApi = PayrollNzApi.getInstance(defaultClient); 

        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

	public void tearDown() {
		payrollNzApi = null;
        defaultClient = null;
	}

    @Test
    public void getTrackingCategoriesTest() throws IOException {
        System.out.println("@Test NZ Payroll - getTrackingCategoriesTest");
       
        TrackingCategories response = payrollNzApi.getTrackingCategories(accessToken, xeroTenantId);
        
        assertThat(response.getTrackingCategories().getEmployeeGroupsTrackingCategoryID(), is(equalTo(UUID.fromString("351953c4-8127-4009-88c3-f9cd8c9cbe9f"))));
        assertThat(response.getTrackingCategories().getTimesheetTrackingCategoryID(), is(equalTo(UUID.fromString("f8c0b291-be04-497a-a083-dd9cd19658b5"))));

        //System.out.println(response.toString());
    }
}
