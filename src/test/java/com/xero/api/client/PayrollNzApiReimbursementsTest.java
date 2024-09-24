package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrollnz.*;

import java.io.IOException;
import java.util.*;
import java.io.InputStream;

public class PayrollNzApiReimbursementsTest {

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
    public void getReimbursementsTest() throws IOException {
        System.out.println("@Test NZ Payroll - getReimbursementsTest");
       
        int page = 1;
        Reimbursements response = payrollNzApi.getReimbursements(accessToken, xeroTenantId, page);
        
        assertThat(response.getReimbursements().get(0).getReimbursementID(),is(equalTo(UUID.fromString("ddf9b572-5861-4f97-a109-9d612df52242"))));
        assertThat(response.getReimbursements().get(0).getName(), is(equalTo("Mileage")));
        assertThat(response.getReimbursements().get(0).getAccountID(),is(equalTo(UUID.fromString("36d33c5d-7dea-4911-9ed0-7fccc16f2b5f"))));
        assertThat(response.getReimbursements().get(0).getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getReimbursements().get(0).getCalculationType(), is(equalTo(com.xero.models.payrollnz.Reimbursement.CalculationTypeEnum.RATEPERUNIT)));
        assertThat(response.getReimbursements().get(0).getReimbursementCategory(), is(equalTo(com.xero.models.payrollnz.Reimbursement.ReimbursementCategoryEnum.NOGST)));
        assertThat(response.getReimbursements().get(0).getStandardTypeOfUnits(), is(equalTo(com.xero.models.payrollnz.Reimbursement.StandardTypeOfUnitsEnum.KM)));
        assertThat(response.getReimbursements().get(0).getStandardRatePerUnit(), is(equalTo(0.79)));

        //System.out.println(response.toString());
    }

    @Test
    public void getReimbursementTest() throws IOException {
        System.out.println("@Test NZ Payroll - getReimbursementTest");
       
        UUID reimbursementID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        ReimbursementObject response = payrollNzApi.getReimbursement(accessToken, xeroTenantId, reimbursementID);
        
        // assertThat(response.getReimbursement().getReimbursementID(),is(equalTo(UUID.fromString("fef6115f-1606-4a74-be54-312d46b0eb0e"))));
        // assertThat(response.getReimbursement().getName(), is(equalTo("Travel Allowance")));
        // assertThat(response.getReimbursement().getAccountID(),is(equalTo(UUID.fromString("c7b73345-7f25-428a-bb97-7b20a1470a53"))));
        // assertThat(response.getReimbursement().getCurrentRecord(), is(equalTo(true)));
        
/*
 reimbursement: class Reimbursement {
        reimbursementID: 0d4e5476-1147-4a2c-9db4-ab6a15f81f1d
        name: GST
        currentRecord: true
        reimbursementCategory: GST
    }
}

 "reimbursementID": "0d4e5476-1147-4a2c-9db4-ab6a15f81f1d",
                            "name": "GST",
                            "currentRecord": true,
                            "reimbursementCategory": "GST",
                          }

*/

        //System.out.println(response.toString());
    }

    @Test
    public void createReimbursementTest() throws IOException {
        System.out.println("@Test NZ Payroll - createReimbursementTest");
       
        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setName("My new Reimburse");
        reimbursement.setAccountID(UUID.randomUUID());
        ReimbursementObject response = payrollNzApi.createReimbursement(accessToken, xeroTenantId, reimbursement, null);
        
        // assertThat(response.getReimbursement().getReimbursementID(),is(equalTo(UUID.fromString("2b1b587a-39f6-43f8-9dd9-a858314333c8"))));
        // assertThat(response.getReimbursement().getName(), is(equalTo("My new Reimburse")));
        // assertThat(response.getReimbursement().getAccountID(),is(equalTo(UUID.fromString("9ee28149-32a9-4661-8eab-a28738696983"))));
        // assertThat(response.getReimbursement().getCurrentRecord(), is(equalTo(true)));
        
/*
 reimbursement: class Reimbursement {
        reimbursementID: c7a8e7fd-b4f1-4f7b-9256-1b3edaa72de7
        name: My new Reimburse
        accountID: fa5cdc43-643b-4ad8-b4ac-3ffe0d0f4488
        currentRecord: true
        reimbursementCategory: GSTInclusive
        calculationType: FixedAmount
        standardAmount: null
        standardTypeOfUnits: null
        standardRatePerUnits: null
    }
}

*/

        //System.out.println(response.toString());
    }
}
