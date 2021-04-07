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

public class PayrollNzApiPayRunsTest {

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
    public void getPayRunsTest() throws IOException {
        System.out.println("@Test NZ Payroll - getPayRunsTest");
       
        int page = 1;
        PayRuns response = payrollNzApi.getPayRuns(accessToken, xeroTenantId, page, null);
        
        assertThat(response.getPayRuns().get(0).getPayRunID(), is(equalTo(UUID.fromString("8ba9831d-38e4-43d4-808e-472a5d195bce"))));
        assertThat(response.getPayRuns().get(0).getPayrollCalendarID(), is(equalTo(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"))));
        assertThat(response.getPayRuns().get(0).getPayRunType(), is(equalTo(com.xero.models.payrollnz.PayRun.PayRunTypeEnum.SCHEDULED)));
        assertThat(response.getPayRuns().get(0).getPeriodStartDate(),  is(equalTo(LocalDate.of(2019, 06, 10))));
        assertThat(response.getPayRuns().get(0).getPeriodEndDate(),  is(equalTo(LocalDate.of(2019, 06, 16))));
        assertThat(response.getPayRuns().get(0).getPaymentDate(),  is(equalTo(LocalDate.of(2019, 06, 18))));
        assertThat(response.getPayRuns().get(0).getTotalCost(),  is(equalTo(6735.81)));
        assertThat(response.getPayRuns().get(0).getTotalPay(),  is(equalTo(4524.81)));
        assertThat(response.getPayRuns().get(0).getPayRunStatus(), is(equalTo(com.xero.models.payrollnz.PayRun.PayRunStatusEnum.DRAFT)));
        assertThat(response.getPayRuns().get(0).getCalendarType(), is(equalTo(com.xero.models.payrollnz.CalendarType.WEEKLY)));
    
        //System.out.println(response.toString());
    }

    @Test
    public void getPayRunTest() throws IOException {
        System.out.println("@Test NZ Payroll - getPayRunTest");
       
        UUID payRunID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        PayRunObject response = payrollNzApi.getPayRun(accessToken, xeroTenantId, payRunID);
        
        assertThat(response.getPayRun().getPayRunID(), is(equalTo(UUID.fromString("8ba9831d-38e4-43d4-808e-472a5d195bce"))));
        assertThat(response.getPayRun().getPayrollCalendarID(), is(equalTo(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"))));
        assertThat(response.getPayRun().getPayRunType(), is(equalTo(com.xero.models.payrollnz.PayRun.PayRunTypeEnum.SCHEDULED)));
        assertThat(response.getPayRun().getPeriodStartDate(),  is(equalTo(LocalDate.of(2019, 06, 10))));
        assertThat(response.getPayRun().getPeriodEndDate(),  is(equalTo(LocalDate.of(2019, 06, 16))));        
        assertThat(response.getPayRun().getPaymentDate(),  is(equalTo(LocalDate.of(2019, 06, 18))));
        assertThat(response.getPayRun().getTotalCost(),  is(equalTo(6735.81)));
        assertThat(response.getPayRun().getTotalPay(),  is(equalTo(4524.81)));
        assertThat(response.getPayRun().getPayRunStatus(), is(equalTo(com.xero.models.payrollnz.PayRun.PayRunStatusEnum.DRAFT)));
        assertThat(response.getPayRun().getCalendarType(), is(equalTo(com.xero.models.payrollnz.CalendarType.WEEKLY)));
        assertThat(response.getPayRun().getPaySlips().get(0).getPaySlipID(), is(equalTo(UUID.fromString("51a01760-cf9d-4ba1-bf3a-2065d4f8e073"))));
        assertThat(response.getPayRun().getPaySlips().get(0).getEmployeeID(), is(equalTo(UUID.fromString("68342973-c405-4b86-b5d3-d7b877c27995"))));
        assertThat(response.getPayRun().getPaySlips().get(0).getFirstName(), is(equalTo("Tony")));
        assertThat(response.getPayRun().getPaySlips().get(0).getLastName(), is(equalTo("Starkmzamlagmdison")));
        assertThat(response.getPayRun().getPaySlips().get(0).getLastEdited(), is(equalTo(LocalDateTime.of(2020, 8, 28, 21, 58, 8))));        
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalEarnings(), is(equalTo(648.0)));
        assertThat(response.getPayRun().getPaySlips().get(0).getGrossEarnings(), is(equalTo(648.0)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalPay(), is(equalTo(525.0)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalEmployerTaxes(), is(equalTo(3.32)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalEmployeeTaxes(), is(equalTo(103.56)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalDeductions(), is(equalTo(0.0)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalReimbursements(), is(equalTo(0.0)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalStatutoryDeductions(), is(equalTo(19.44)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalSuperannuation(), is(equalTo(19.44)));
        assertThat(response.getPayRun().getPaySlips().get(0).getPaymentMethod(), is(equalTo(com.xero.models.payrollnz.PaySlip.PaymentMethodEnum.ELECTRONICALLY)));

        //System.out.println(response.toString());
    }

    @Test
    public void createPayRunTest() throws IOException {
        System.out.println("@Test NZ Payroll - createPayRunTest");
       
        PayRun payRun = new PayRun();
        PayRunObject response = payrollNzApi.createPayRun(accessToken, xeroTenantId, payRun);
        
        assertThat(response.getPayRun().getPayRunID(), is(equalTo(UUID.fromString("591dbf2f-786b-4814-8c61-93bebaee47f9"))));
        assertThat(response.getPayRun().getPayrollCalendarID(), is(equalTo(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"))));
        assertThat(response.getPayRun().getPayRunType(), is(equalTo(com.xero.models.payrollnz.PayRun.PayRunTypeEnum.SCHEDULED)));
        assertThat(response.getPayRun().getPeriodStartDate(),  is(equalTo(LocalDate.of(2019, 06, 17))));
        assertThat(response.getPayRun().getPeriodEndDate(),  is(equalTo(LocalDate.of(2019, 06, 23))));        
        assertThat(response.getPayRun().getPaymentDate(),  is(equalTo(LocalDate.of(2019, 06, 25))));
        assertThat(response.getPayRun().getTotalCost(),  is(equalTo(9416.7)));
        assertThat(response.getPayRun().getTotalPay(),  is(equalTo(6137.2)));
        assertThat(response.getPayRun().getPayRunStatus(), is(equalTo(com.xero.models.payrollnz.PayRun.PayRunStatusEnum.DRAFT)));
        assertThat(response.getPayRun().getCalendarType(), is(equalTo(com.xero.models.payrollnz.CalendarType.WEEKLY)));
        assertThat(response.getPayRun().getPaySlips().get(0).getPaySlipID(), is(equalTo(UUID.fromString("8b3a099c-2309-480a-a6ee-1dc4f4b2668b"))));
        assertThat(response.getPayRun().getPaySlips().get(0).getEmployeeID(), is(equalTo(UUID.fromString("68342973-c405-4b86-b5d3-d7b877c27995"))));
        assertThat(response.getPayRun().getPaySlips().get(0).getFirstName(), is(equalTo("Tony")));
        assertThat(response.getPayRun().getPaySlips().get(0).getLastName(), is(equalTo("Starkwpjgdjxdvwson")));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalEarnings(), is(equalTo(3628.8)));
        assertThat(response.getPayRun().getPaySlips().get(0).getGrossEarnings(), is(equalTo(3628.8)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalPay(), is(equalTo(2462.72)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalEmployerTaxes(), is(equalTo(18.9)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalEmployeeTaxes(), is(equalTo(1057.22)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalDeductions(), is(equalTo(0.0)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalReimbursements(), is(equalTo(0.0)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalStatutoryDeductions(), is(equalTo(108.86)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalSuperannuation(), is(equalTo(108.86)));
        assertThat(response.getPayRun().getPaySlips().get(0).getPaymentMethod(), is(equalTo(com.xero.models.payrollnz.PaySlip.PaymentMethodEnum.ELECTRONICALLY)));

        //System.out.println(response.toString());

    }

    @Test
    public void updatePayRunTest() throws IOException {
        System.out.println("@Test NZ Payroll - updatePayRunTest");
       
        UUID payRunID = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e"); 
        PayRun payRun = new PayRun();
        PayRunObject response = payrollNzApi.updatePayRun(accessToken, xeroTenantId, payRunID, payRun);
        
        assertThat(response.getPayRun().getPayRunID(), is(equalTo(UUID.fromString("8ba9831d-38e4-43d4-808e-472a5d195bce"))));
        assertThat(response.getPayRun().getPayrollCalendarID(), is(equalTo(UUID.fromString("9aa56064-990f-4ad3-a189-d966d8f6a030"))));
        assertThat(response.getPayRun().getPayRunType(), is(equalTo(com.xero.models.payrollnz.PayRun.PayRunTypeEnum.SCHEDULED)));
        assertThat(response.getPayRun().getPeriodStartDate(),  is(equalTo(LocalDate.of(2019, 06, 10))));
        assertThat(response.getPayRun().getPeriodEndDate(),  is(equalTo(LocalDate.of(2019, 06, 16))));        
        assertThat(response.getPayRun().getPaymentDate(),  is(equalTo(LocalDate.of(2019, 07, 01))));
        assertThat(response.getPayRun().getTotalCost(),  is(equalTo(9806.03)));
        assertThat(response.getPayRun().getTotalPay(),  is(equalTo(6362.53)));
        assertThat(response.getPayRun().getPayRunStatus(), is(equalTo(com.xero.models.payrollnz.PayRun.PayRunStatusEnum.DRAFT)));
        assertThat(response.getPayRun().getCalendarType(), is(equalTo(com.xero.models.payrollnz.CalendarType.WEEKLY)));
        assertThat(response.getPayRun().getPaySlips().get(0).getPaySlipID(), is(equalTo(UUID.fromString("51a01760-cf9d-4ba1-bf3a-2065d4f8e073"))));
        assertThat(response.getPayRun().getPaySlips().get(0).getEmployeeID(), is(equalTo(UUID.fromString("68342973-c405-4b86-b5d3-d7b877c27995"))));
        assertThat(response.getPayRun().getPaySlips().get(0).getFirstName(), is(equalTo("Tony")));
        assertThat(response.getPayRun().getPaySlips().get(0).getLastName(), is(equalTo("Starkwpjgdjxdvwson")));
        assertThat(response.getPayRun().getPaySlips().get(0).getLastEdited(), is(equalTo(LocalDateTime.of(2020, 9, 10, 17, 20, 23))));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalEarnings(), is(equalTo(3628.8)));
        assertThat(response.getPayRun().getPaySlips().get(0).getGrossEarnings(), is(equalTo(3628.8)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalPay(), is(equalTo(2362.72)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalEmployerTaxes(), is(equalTo(18.9)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalEmployeeTaxes(), is(equalTo(1057.22)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalDeductions(), is(equalTo(100.0)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalReimbursements(), is(equalTo(0.0)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalStatutoryDeductions(), is(equalTo(108.86)));
        assertThat(response.getPayRun().getPaySlips().get(0).getTotalSuperannuation(), is(equalTo(108.86)));
        assertThat(response.getPayRun().getPaySlips().get(0).getPaymentMethod(), is(equalTo(com.xero.models.payrollnz.PaySlip.PaymentMethodEnum.ELECTRONICALLY)));

        //System.out.println(response.toString());
    }
}
