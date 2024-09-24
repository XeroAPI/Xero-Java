package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.accounting.*;

import java.util.UUID;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class AccountingApiBrandingThemeTest {

	ApiClient defaultClient; 
    AccountingApi accountingApi; 
	String accessToken;
    String xeroTenantId; 
     
    
	private static boolean setUpIsDone = false;

	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        
        // Init AccountingApi client
        // NEW Sandbox for API Mocking
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")){
            properties.load(input);
            defaultClient = new ApiClient(properties.getProperty("accounting.api.url"),null,null,null,null);
            accountingApi = AccountingApi.getInstance(defaultClient); 

        } catch (IOException ex) {
            ex.printStackTrace();
        }
       
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
	public void testGetBrandingThemes() throws Exception {
		System.out.println("@Test - getBrandingThemes");

		BrandingThemes bt = accountingApi.getBrandingThemes(accessToken,xeroTenantId);
		assertThat(bt.getBrandingThemes().get(0).getBrandingThemeID().toString(), is(equalTo("dabc7637-62c1-4941-8a6e-ee44fa5090e7")));
		assertThat(bt.getBrandingThemes().get(0).getName(), is(equalTo("Standard")));
		//System.out.println(bt.toString());
	}

	@Test
	public void testGetBrandingTheme() throws Exception {
		System.out.println("@Test - getBrandingTheme");
		UUID brandingThemeId = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	

		BrandingThemes oneBrandingTheme = accountingApi.getBrandingTheme(accessToken,xeroTenantId,brandingThemeId);	
		assertThat(oneBrandingTheme.getBrandingThemes().get(0).getBrandingThemeID().toString(), is(equalTo("dabc7637-62c1-4941-8a6e-ee44fa5090e7")));
		assertThat(oneBrandingTheme.getBrandingThemes().get(0).getName(), is(equalTo("Standard")));
		//System.out.println(bt.toString());
	}

	@Test
	public void testGetBrandingThemePaymentServices() throws Exception {
		System.out.println("@Test - getBrandingThemePaymentServices");
		UUID brandingThemeId = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	

		PaymentServices paymentServicesForBrandingTheme = accountingApi.getBrandingThemePaymentServices(accessToken,xeroTenantId,brandingThemeId);	
		assertThat(paymentServicesForBrandingTheme.getPaymentServices().get(1).getPaymentServiceID().toString(), is(equalTo("dede7858-14e3-4a46-bf95-4d4cc491e645")));
		assertThat(paymentServicesForBrandingTheme.getPaymentServices().get(1).getPaymentServiceName(), is(equalTo("ACME Payment")));
		//System.out.println(paymentServicesForBrandingTheme.toString());
	}

	@Test
	public void testCreateBrandingThemePaymentServices() throws Exception {
		System.out.println("@Test - createBrandingThemePaymentServices");
		UUID brandingThemeId = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	
		UUID paymentServiceId = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");	

		PaymentServices btPaymentServices = new PaymentServices();
		PaymentService btPaymentService = new PaymentService();
		btPaymentService.setPaymentServiceID(paymentServiceId);
		btPaymentService.setPaymentServiceName("ACME Payments");
		btPaymentService.setPaymentServiceUrl("http://www.mydomain.com/paymentservice");
		btPaymentService.setPayNowText("Pay Now");
		btPaymentServices.addPaymentServicesItem(btPaymentService);
		PaymentServices response = accountingApi.createBrandingThemePaymentServices(accessToken,xeroTenantId,brandingThemeId, btPaymentServices, null);	
		assertThat(response.getPaymentServices().get(0).getPaymentServiceID().toString(), is(equalTo("00000000-0000-0000-0000-000000000000")));
		assertThat(response.getPaymentServices().get(0).getPaymentServiceName(), is(equalTo("ACME Payments")));
		assertThat(response.getPaymentServices().get(0).getPaymentServiceUrl(), is(equalTo("https://www.payupnow.com/")));
		assertThat(response.getPaymentServices().get(0).getPayNowText(), is(equalTo("Pay Now")));
	
		//System.out.println(response.toString());
	}
}
