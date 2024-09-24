package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrollnz.*;
import com.xero.models.payrollnz.Deduction.DeductionCategoryEnum;

import java.io.IOException;
import java.util.UUID;
import java.util.*;
import java.io.InputStream;

public class PayrollNzApiDeductionsTest {

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
    public void getDeductionsTest() throws IOException {
        System.out.println("@Test NZ Payroll - getDeductionsTest");
       
        int page = 1;
        Deductions response = payrollNzApi.getDeductions(accessToken, xeroTenantId, page);
        
        assertThat(response.getDeductions().get(1).getDeductionId(), is(equalTo(UUID.fromString("a131596e-2f83-4f6c-9573-99c4cbfcefd1"))));
        assertThat(response.getDeductions().get(1).getLiabilityAccountId(), is(equalTo(UUID.fromString("26c57f04-0436-40cc-9cd9-1a21185d88bb"))));
        assertThat(response.getDeductions().get(1).getDeductionName(), is(equalTo("Rent")));
        assertThat(response.getDeductions().get(1).getDeductionCategory(), is(equalTo(com.xero.models.payrollnz.Deduction.DeductionCategoryEnum.NZOTHER)));
        assertThat(response.getDeductions().get(1).getStandardAmount(), is(equalTo(200.0)));
        assertThat(response.getDeductions().get(1).getCurrentRecord(), is(equalTo(true)));
      
        //System.out.println(response.toString());
    }

    @Test
    public void getDeductionTest() throws IOException {
        System.out.println("@Test NZ Payroll - getDeductionTest");
       
        UUID deductionId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        DeductionObject response = payrollNzApi.getDeduction(accessToken, xeroTenantId, deductionId);
        
        assertThat(response.getDeduction().getDeductionId(), is(equalTo(UUID.fromString("a3760fe4-68a4-4e38-8326-fe616af7dc74"))));
        assertThat(response.getDeduction().getLiabilityAccountId(), is(equalTo(UUID.fromString("26c57f04-0436-40cc-9cd9-1a21185d88bb"))));
        assertThat(response.getDeduction().getDeductionName(), is(equalTo("KiwiSaver Voluntary Contributions")));
        assertThat(response.getDeduction().getDeductionCategory(), is(equalTo(com.xero.models.payrollnz.Deduction.DeductionCategoryEnum.KIWISAVERVOLUNTARYCONTRIBUTIONS)));
        assertThat(response.getDeduction().getCurrentRecord(), is(equalTo(true)));
       
        //System.out.println(response.toString());
    }

    @Test
    public void createDeductionTest() throws IOException {
        System.out.println("@Test NZ Payroll - createDeductionTest");
       
        Deduction deduction = new Deduction();
        deduction.setDeductionName("Test Name");
        deduction.setDeductionCategory(DeductionCategoryEnum.NZOTHER);
        deduction.setLiabilityAccountId(UUID.randomUUID());
        DeductionObject response = payrollNzApi.createDeduction(accessToken, xeroTenantId, deduction, null);
        
        assertThat(response.getDeduction().getDeductionId(), is(equalTo(UUID.fromString("0ee805eb-f5b0-4061-9b35-d9ea550da04e"))));
        assertThat(response.getDeduction().getLiabilityAccountId(), is(equalTo(UUID.fromString("568f2e9a-0870-46cc-8678-f83f132ed4e3"))));
        assertThat(response.getDeduction().getDeductionName(), is(equalTo("My new deduction")));
        assertThat(response.getDeduction().getDeductionCategory(), is(equalTo(com.xero.models.payrollnz.Deduction.DeductionCategoryEnum.NZOTHER)));
        assertThat(response.getDeduction().getCurrentRecord(), is(equalTo(true)));
     
        //System.out.println(response.toString());
    }
}
