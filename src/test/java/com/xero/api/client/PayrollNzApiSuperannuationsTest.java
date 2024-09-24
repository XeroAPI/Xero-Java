package com.xero.api.client;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.models.payrollnz.*;

import java.io.IOException;
import java.util.UUID;

public class PayrollNzApiSuperannuationsTest {

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
    public void getSuperannuationsTest() throws IOException {
        System.out.println("@Test NZ Payroll - getSuperannuationsTest");
       
        int page = 1;
        Superannuations response = payrollNzApi.getSuperannuations(accessToken, xeroTenantId, page);
        
        assertThat(response.getBenefits().get(0).getId(), is(equalTo(UUID.fromString("563273ea-0dae-4f82-86a4-e0db77c008ea"))));
        assertThat(response.getBenefits().get(0).getLiabilityAccountId(), is(equalTo(UUID.fromString("fa5cdc43-643b-4ad8-b4ac-3ffe0d0f4488"))));
        assertThat(response.getBenefits().get(0).getExpenseAccountId(), is(equalTo(UUID.fromString("b343c3b0-5941-4166-82b4-dd926622e0e2"))));
        assertThat(response.getBenefits().get(0).getName(), is(equalTo("KiwiSaver")));
        assertThat(response.getBenefits().get(0).getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getBenefits().get(0).getPercentage(), is(equalTo(3.0)));
        assertThat(response.getBenefits().get(0).getCalculationTypeNZ(), is(equalTo(com.xero.models.payrollnz.Benefit.CalculationTypeNZEnum.PERCENTAGEOFTAXABLEEARNINGS)));

        //System.out.println(response.toString());
    }

    @Test
    public void getSuperannuationTest() throws IOException {
        System.out.println("@Test NZ Payroll - getSuperannuationTest");
       
        UUID superannuationId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        SuperannuationObject response = payrollNzApi.getSuperannuation(accessToken, xeroTenantId, superannuationId);
        
        assertThat(response.getBenefit().getId(), is(equalTo(UUID.fromString("563273ea-0dae-4f82-86a4-e0db77c008ea"))));
        assertThat(response.getBenefit().getLiabilityAccountId(), is(equalTo(UUID.fromString("fa5cdc43-643b-4ad8-b4ac-3ffe0d0f4488"))));
        assertThat(response.getBenefit().getExpenseAccountId(), is(equalTo(UUID.fromString("b343c3b0-5941-4166-82b4-dd926622e0e2"))));
        assertThat(response.getBenefit().getName(), is(equalTo("KiwiSaver")));
        assertThat(response.getBenefit().getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getBenefit().getPercentage(), is(equalTo(3.0)));
        assertThat(response.getBenefit().getCalculationTypeNZ(), is(equalTo(com.xero.models.payrollnz.Benefit.CalculationTypeNZEnum.PERCENTAGEOFTAXABLEEARNINGS)));

        //System.out.println(response.toString());
    }

    @Test
    public void createSuperannuationTest() throws IOException {
        System.out.println("@Test NZ Payroll - createSuperannuationTest");
       
        Benefit benefit = new Benefit();
        benefit.setName("New Benefit");
        benefit.setCategory(com.xero.models.payrollnz.Benefit.CategoryEnum.OTHER);
        benefit.setLiabilityAccountId(UUID.randomUUID());
        benefit.setExpenseAccountId(UUID.randomUUID());
        benefit.setCalculationTypeNZ(com.xero.models.payrollnz.Benefit.CalculationTypeNZEnum.FIXEDAMOUNT);
        benefit.setPercentage(12.00);
        SuperannuationObject response = payrollNzApi.createSuperannuation(accessToken, xeroTenantId, benefit, null);
        
        assertThat(response.getBenefit().getId(), is(equalTo(UUID.fromString("8905a754-7ce8-40e2-9fa5-f819deb7adce"))));
        assertThat(response.getBenefit().getLiabilityAccountId(), is(equalTo(UUID.fromString("568f2e9a-0870-46cc-8678-f83f132ed4e3"))));
        assertThat(response.getBenefit().getExpenseAccountId(), is(equalTo(UUID.fromString("e4eb36f6-97e3-4427-a394-dd4e1b355c2e"))));
        assertThat(response.getBenefit().getName(), is(equalTo("SidSaver")));
        assertThat(response.getBenefit().getCategory(), is(equalTo(com.xero.models.payrollnz.Benefit.CategoryEnum.OTHER)));
        assertThat(response.getBenefit().getCurrentRecord(), is(equalTo(true)));
        assertThat(response.getBenefit().getStandardAmount(), is(equalTo(10.0)));
        assertThat(response.getBenefit().getCalculationTypeNZ(), is(equalTo(com.xero.models.payrollnz.Benefit.CalculationTypeNZEnum.FIXEDAMOUNT)));

        //System.out.println(response.toString());
    }
}
