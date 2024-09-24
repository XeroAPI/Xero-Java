package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrolluk.*;
import java.io.IOException;
import java.util.*;
import java.io.InputStream;

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
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")){
            properties.load(input);
            defaultClient = new ApiClient(properties.getProperty("payrolluk.api.url"),null,null,null,null);
            payrollUkApi = PayrollUkApi.getInstance(defaultClient); 

        } catch (IOException ex) {
            ex.printStackTrace();
        }
       
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
        reimbursement.setName("My new Reimburse");
        reimbursement.setAccountID(UUID.randomUUID());
        ReimbursementObject response = payrollUkApi.createReimbursement(accessToken, xeroTenantId, reimbursement, null);
        
        assertThat(response.getReimbursement().getReimbursementID(),is(equalTo(UUID.fromString("2b1b587a-39f6-43f8-9dd9-a858314333c8"))));
        assertThat(response.getReimbursement().getName(), is(equalTo("My new Reimburse")));
        assertThat(response.getReimbursement().getAccountID(),is(equalTo(UUID.fromString("9ee28149-32a9-4661-8eab-a28738696983"))));
        assertThat(response.getReimbursement().getCurrentRecord(), is(equalTo(true)));
        
        //System.out.println(response.toString());
    }
}
