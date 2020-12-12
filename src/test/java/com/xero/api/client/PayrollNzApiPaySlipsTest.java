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

public class PayrollNzApiPaySlipsTest {

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
    public void getPaySlipsTest() throws IOException {
        System.out.println("@Test NZ Payroll - getPayslipsTest");
       
        UUID payRunId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        PaySlips response = payrollNzApi.getPaySlips(accessToken, xeroTenantId, payRunId, 1);
        
        assertThat(response.getPaySlips().get(0).getPaySlipID(),is(equalTo(UUID.fromString("17d88883-686a-400f-9551-34fa366effc4"))));
        assertThat(response.getPaySlips().get(0).getEmployeeID(),is(equalTo(UUID.fromString("68342973-c405-4b86-b5d3-d7b877c27995"))));
        assertThat(response.getPaySlips().get(0).getPayRunID(),is(equalTo(UUID.fromString("be103bd8-321b-419f-8177-48280560771a"))));
        assertThat(response.getPaySlips().get(0).getFirstName(),is(equalTo("Tony")));
        assertThat(response.getPaySlips().get(0).getLastName(),is(equalTo("Starkmzamlagmdison")));
        assertThat(response.getPaySlips().get(0).getTotalEarnings(),is(equalTo(162.0)));
        assertThat(response.getPaySlips().get(0).getGrossEarnings(),is(equalTo(162.0)));
        assertThat(response.getPaySlips().get(0).getTotalPay(),is(equalTo(137.88)));
        assertThat(response.getPaySlips().get(0).getTotalEmployerTaxes(),is(equalTo(0.7)));
        assertThat(response.getPaySlips().get(0).getTotalEmployeeTaxes(),is(equalTo(19.26)));
        assertThat(response.getPaySlips().get(0).getTotalDeductions(),is(equalTo(0.0)));
        assertThat(response.getPaySlips().get(0).getTotalReimbursements(),is(equalTo(0.0)));
        assertThat(response.getPaySlips().get(0).getTotalStatutoryDeductions(),is(equalTo(4.86)));
        assertThat(response.getPaySlips().get(0).getTotalSuperannuation(),is(equalTo(4.86)));
        assertThat(response.getPaySlips().get(0).getPaymentMethod(), is(equalTo(com.xero.models.payrollnz.PaySlip.PaymentMethodEnum.ELECTRONICALLY)));
        assertThat(response.getPaySlips().get(0).getLastEdited(), is(equalTo(LocalDateTime.of(2019, 9, 23, 2, 13, 29))));  
        assertThat(response.getPaySlips().get(0).getEarningsLines().get(0).getEarningsLineID(),is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getPaySlips().get(0).getEarningsLines().get(0).getEarningsRateID(),is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getPaySlips().get(0).getEarningsLines().get(0).getDisplayName(),is(equalTo("Ordinary Time")));
        assertThat(response.getPaySlips().get(0).getEarningsLines().get(0).getRatePerUnit(),is(equalTo(25.0)));
        assertThat(response.getPaySlips().get(0).getEarningsLines().get(0).getNumberOfUnits(),is(equalTo(6.0)));
        assertThat(response.getPaySlips().get(0).getEarningsLines().get(0).getAmount(),is(equalTo(150.0)));
        assertThat(response.getPaySlips().get(0).getEarningsLines().get(0).getIsLinkedToTimesheet(),is(equalTo(false))); 
        assertThat(response.getPaySlips().get(0).getEarningsLines().get(0).getIsSystemGenerated(),is(equalTo(true)));     
        assertThat(response.getPaySlips().get(0).getLeaveEarningsLines().get(0).getEarningsLineID(),is(equalTo(UUID.fromString("0441497f-5dc7-4cd3-a90d-f2e07e21b2a6"))));
        assertThat(response.getPaySlips().get(0).getLeaveEarningsLines().get(0).getEarningsRateID(),is(equalTo(UUID.fromString("39b3560a-5d2f-4538-924a-4349dc86396e"))));
        assertThat(response.getPaySlips().get(0).getLeaveEarningsLines().get(0).getDisplayName(),is(equalTo("Holiday Pay")));
        assertThat(response.getPaySlips().get(0).getLeaveEarningsLines().get(0).getFixedAmount(),is(equalTo(12.0)));
        assertThat(response.getPaySlips().get(0).getLeaveEarningsLines().get(0).getAmount(),is(equalTo(12.0)));
        assertThat(response.getPaySlips().get(0).getLeaveEarningsLines().get(0).getIsLinkedToTimesheet(),is(equalTo(false)));
        assertThat(response.getPaySlips().get(0).getLeaveEarningsLines().get(0).getIsSystemGenerated(),is(equalTo(true)));
        assertThat(response.getPaySlips().get(0).getLeaveAccrualLines().get(0).getLeaveTypeID(),is(equalTo(UUID.fromString("0441497f-5dc7-4cd3-a90d-f2e07e21b2a6"))));
        assertThat(response.getPaySlips().get(0).getLeaveAccrualLines().get(0).getNumberOfUnits(),is(equalTo(12.0)));
        assertThat(response.getPaySlips().get(0).getSuperannuationLines().get(0).getSuperannuationTypeID(),is(equalTo(UUID.fromString("563273ea-0dae-4f82-86a4-e0db77c008ea"))));
        assertThat(response.getPaySlips().get(0).getSuperannuationLines().get(0).getDisplayName(),is(equalTo("KiwiSaver")));
        assertThat(response.getPaySlips().get(0).getSuperannuationLines().get(0).getAmount(),is(equalTo(4.86)));
        assertThat(response.getPaySlips().get(0).getSuperannuationLines().get(0).getPercentage(),is(equalTo(3.0)));
        assertThat(response.getPaySlips().get(0).getSuperannuationLines().get(0).getManualAdjustment(),is(equalTo(false)));
        assertThat(response.getPaySlips().get(0).getPaymentLines().get(0).getPaymentLineID(),is(equalTo(UUID.fromString("a300f7c3-e934-4e67-84c5-d8687cf4a2b4"))));
        assertThat(response.getPaySlips().get(0).getPaymentLines().get(0).getAmount(),is(equalTo(137.88)));
        assertThat(response.getPaySlips().get(0).getPaymentLines().get(0).getAccountNumber(),is(equalTo("0607050201419000")));
        assertThat(response.getPaySlips().get(0).getPaymentLines().get(0).getAccountName(),is(equalTo("Casual Worker")));
        assertThat(response.getPaySlips().get(0).getEmployeeTaxLines().get(0).getTaxLineID(),is(equalTo(UUID.fromString("1d7c6670-c227-4de9-a7fc-fbf7ec16a804"))));
        assertThat(response.getPaySlips().get(0).getEmployeeTaxLines().get(0).getAmount(),is(equalTo(19.26)));
        assertThat(response.getPaySlips().get(0).getEmployeeTaxLines().get(0).getGlobalTaxTypeID(),is(equalTo("11")));
        assertThat(response.getPaySlips().get(0).getEmployeeTaxLines().get(0).getManualAdjustment(),is(equalTo(false)));
        assertThat(response.getPaySlips().get(0).getEmployerTaxLines().get(0).getTaxLineID(),is(equalTo(UUID.fromString("b790f489-9bbf-4979-a987-53dddf8b648f"))));
        assertThat(response.getPaySlips().get(0).getEmployerTaxLines().get(0).getAmount(),is(equalTo(0.7)));
        assertThat(response.getPaySlips().get(0).getEmployerTaxLines().get(0).getGlobalTaxTypeID(),is(equalTo("10")));
        assertThat(response.getPaySlips().get(0).getEmployerTaxLines().get(0).getManualAdjustment(),is(equalTo(false)));
        assertThat(response.getPaySlips().get(0).getStatutoryDeductionLines().get(0).getStatutoryDeductionTypeID(),is(equalTo(UUID.fromString("b5efd8d1-0c93-4a14-a314-b5cba4a4e6b3"))));
        assertThat(response.getPaySlips().get(0).getStatutoryDeductionLines().get(0).getAmount(),is(equalTo(4.86)));
        assertThat(response.getPaySlips().get(0).getTaxSettings().getPeriodUnits(),is(equalTo(1.0)));
        assertThat(response.getPaySlips().get(0).getTaxSettings().getPeriodType(), is(equalTo(com.xero.models.payrollnz.TaxSettings.PeriodTypeEnum.WEEKS)));
        assertThat(response.getPaySlips().get(0).getTaxSettings().getTaxCode(), is(equalTo(com.xero.models.payrollnz.TaxCode.M)));
        assertThat(response.getPaySlips().get(0).getTaxSettings().getLumpSumTaxCode(),is(equalTo("SB")));
        assertThat(response.getPaySlips().get(0).getGrossEarningsHistory().getDaysPaid(),is(equalTo(1)));
        assertThat(response.getPaySlips().get(0).getGrossEarningsHistory().getUnpaidWeeks(),is(equalTo(0)));

        //System.out.println(response.toString());
    }

    @Test
    public void getPaySlipTest() throws IOException {
        System.out.println("@Test NZ Payroll - getPaySlipTest");
       
        UUID paySlipId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        PaySlipObject response = payrollNzApi.getPaySlip(accessToken, xeroTenantId, paySlipId);
        
        assertThat(response.getPaySlip().getPaySlipID(),is(equalTo(UUID.fromString("17d88883-686a-400f-9551-34fa366effc4"))));
        assertThat(response.getPaySlip().getEmployeeID(),is(equalTo(UUID.fromString("68342973-c405-4b86-b5d3-d7b877c27995"))));
        assertThat(response.getPaySlip().getPayRunID(),is(equalTo(UUID.fromString("be103bd8-321b-419f-8177-48280560771a"))));
        assertThat(response.getPaySlip().getFirstName(),is(equalTo("Tony")));
        assertThat(response.getPaySlip().getLastName(),is(equalTo("Starkmzamlagmdison")));
        assertThat(response.getPaySlip().getTotalEarnings(),is(equalTo(162.0)));
        assertThat(response.getPaySlip().getGrossEarnings(),is(equalTo(162.0)));
        assertThat(response.getPaySlip().getTotalPay(),is(equalTo(137.88)));
        assertThat(response.getPaySlip().getTotalEmployerTaxes(),is(equalTo(0.7)));
        assertThat(response.getPaySlip().getTotalEmployeeTaxes(),is(equalTo(19.26)));
        assertThat(response.getPaySlip().getTotalDeductions(),is(equalTo(0.0)));
        assertThat(response.getPaySlip().getTotalReimbursements(),is(equalTo(0.0)));
        assertThat(response.getPaySlip().getTotalStatutoryDeductions(),is(equalTo(4.86)));
        assertThat(response.getPaySlip().getTotalSuperannuation(),is(equalTo(4.86)));
        assertThat(response.getPaySlip().getPaymentMethod(), is(equalTo(com.xero.models.payrollnz.PaySlip.PaymentMethodEnum.ELECTRONICALLY)));
        assertThat(response.getPaySlip().getLastEdited(), is(equalTo(LocalDateTime.of(2019, 9, 23, 2, 13, 29))));  
        assertThat(response.getPaySlip().getEarningsLines().get(0).getEarningsLineID(),is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getEarningsRateID(),is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getDisplayName(),is(equalTo("Ordinary Time")));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getRatePerUnit(),is(equalTo(25.0)));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getNumberOfUnits(),is(equalTo(6.0)));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getAmount(),is(equalTo(150.0)));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getIsLinkedToTimesheet(),is(equalTo(false))); 
        assertThat(response.getPaySlip().getEarningsLines().get(0).getIsSystemGenerated(),is(equalTo(true)));     
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getEarningsLineID(),is(equalTo(UUID.fromString("0441497f-5dc7-4cd3-a90d-f2e07e21b2a6"))));
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getEarningsRateID(),is(equalTo(UUID.fromString("39b3560a-5d2f-4538-924a-4349dc86396e"))));
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getDisplayName(),is(equalTo("Holiday Pay")));
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getFixedAmount(),is(equalTo(12.0)));
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getAmount(),is(equalTo(12.0)));
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getIsLinkedToTimesheet(),is(equalTo(false)));
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getIsSystemGenerated(),is(equalTo(true)));
        assertThat(response.getPaySlip().getLeaveAccrualLines().get(0).getLeaveTypeID(),is(equalTo(UUID.fromString("0441497f-5dc7-4cd3-a90d-f2e07e21b2a6"))));
        assertThat(response.getPaySlip().getLeaveAccrualLines().get(0).getNumberOfUnits(),is(equalTo(12.0)));
        assertThat(response.getPaySlip().getSuperannuationLines().get(0).getSuperannuationTypeID(),is(equalTo(UUID.fromString("563273ea-0dae-4f82-86a4-e0db77c008ea"))));
        assertThat(response.getPaySlip().getSuperannuationLines().get(0).getDisplayName(),is(equalTo("KiwiSaver")));
        assertThat(response.getPaySlip().getSuperannuationLines().get(0).getAmount(),is(equalTo(4.86)));
        assertThat(response.getPaySlip().getSuperannuationLines().get(0).getPercentage(),is(equalTo(3.0)));
        assertThat(response.getPaySlip().getSuperannuationLines().get(0).getManualAdjustment(),is(equalTo(false)));
        assertThat(response.getPaySlip().getPaymentLines().get(0).getPaymentLineID(),is(equalTo(UUID.fromString("a300f7c3-e934-4e67-84c5-d8687cf4a2b4"))));
        assertThat(response.getPaySlip().getPaymentLines().get(0).getAmount(),is(equalTo(137.88)));
        assertThat(response.getPaySlip().getPaymentLines().get(0).getAccountNumber(),is(equalTo("0607050201419000")));
        assertThat(response.getPaySlip().getPaymentLines().get(0).getAccountName(),is(equalTo("Casual Worker")));
        assertThat(response.getPaySlip().getEmployeeTaxLines().get(0).getTaxLineID(),is(equalTo(UUID.fromString("1d7c6670-c227-4de9-a7fc-fbf7ec16a804"))));
        assertThat(response.getPaySlip().getEmployeeTaxLines().get(0).getAmount(),is(equalTo(19.26)));
        assertThat(response.getPaySlip().getEmployeeTaxLines().get(0).getGlobalTaxTypeID(),is(equalTo("11")));
        assertThat(response.getPaySlip().getEmployeeTaxLines().get(0).getManualAdjustment(),is(equalTo(false)));
        assertThat(response.getPaySlip().getEmployerTaxLines().get(0).getTaxLineID(),is(equalTo(UUID.fromString("b790f489-9bbf-4979-a987-53dddf8b648f"))));
        assertThat(response.getPaySlip().getEmployerTaxLines().get(0).getAmount(),is(equalTo(0.7)));
        assertThat(response.getPaySlip().getEmployerTaxLines().get(0).getGlobalTaxTypeID(),is(equalTo("10")));
        assertThat(response.getPaySlip().getEmployerTaxLines().get(0).getManualAdjustment(),is(equalTo(false)));
        assertThat(response.getPaySlip().getStatutoryDeductionLines().get(0).getStatutoryDeductionTypeID(),is(equalTo(UUID.fromString("b5efd8d1-0c93-4a14-a314-b5cba4a4e6b3"))));
        assertThat(response.getPaySlip().getStatutoryDeductionLines().get(0).getAmount(),is(equalTo(4.86)));
        assertThat(response.getPaySlip().getTaxSettings().getPeriodUnits(),is(equalTo(1.0)));
        assertThat(response.getPaySlip().getTaxSettings().getPeriodType(), is(equalTo(com.xero.models.payrollnz.TaxSettings.PeriodTypeEnum.WEEKS)));
        assertThat(response.getPaySlip().getTaxSettings().getTaxCode(), is(equalTo(com.xero.models.payrollnz.TaxCode.M)));
        assertThat(response.getPaySlip().getTaxSettings().getLumpSumTaxCode(),is(equalTo("SB")));
        assertThat(response.getPaySlip().getGrossEarningsHistory().getDaysPaid(),is(equalTo(1)));
        assertThat(response.getPaySlip().getGrossEarningsHistory().getUnpaidWeeks(),is(equalTo(0)));

        //System.out.println(response.toString());
    }

    @Test
    public void updatePaySlipLineItemsTest() throws IOException {
        System.out.println("@Test NZ Payroll - updatePaySlipLineItemsTest");
       
        UUID paySlipId = UUID.fromString("cdfb8371-0b21-4b8a-8903-1024df6c391e");
        PaySlip paySlip = new PaySlip();
        PaySlipObject response = payrollNzApi.updatePaySlipLineItems(accessToken, xeroTenantId, paySlipId, paySlip);
        
        assertThat(response.getPaySlip().getPaySlipID(),is(equalTo(UUID.fromString("51a01760-cf9d-4ba1-bf3a-2065d4f8e073"))));
        assertThat(response.getPaySlip().getEmployeeID(),is(equalTo(UUID.fromString("68342973-c405-4b86-b5d3-d7b877c27995"))));
        assertThat(response.getPaySlip().getPayRunID(),is(equalTo(UUID.fromString("8ba9831d-38e4-43d4-808e-472a5d195bce"))));
        assertThat(response.getPaySlip().getFirstName(),is(equalTo("Tony")));
        assertThat(response.getPaySlip().getLastName(),is(equalTo("Starkwpjgdjxdvwson")));
        assertThat(response.getPaySlip().getTotalEarnings(),is(equalTo(3628.8)));
        assertThat(response.getPaySlip().getGrossEarnings(),is(equalTo(3628.8)));
        assertThat(response.getPaySlip().getTotalPay(),is(equalTo(2362.72)));
        assertThat(response.getPaySlip().getTotalEmployerTaxes(),is(equalTo(18.9)));
        assertThat(response.getPaySlip().getTotalEmployeeTaxes(),is(equalTo(1057.22)));
        assertThat(response.getPaySlip().getTotalDeductions(),is(equalTo(100.0)));
        assertThat(response.getPaySlip().getTotalReimbursements(),is(equalTo(0.0)));
        assertThat(response.getPaySlip().getTotalStatutoryDeductions(),is(equalTo(108.86)));
        assertThat(response.getPaySlip().getTotalSuperannuation(),is(equalTo(108.86)));
        assertThat(response.getPaySlip().getPaymentMethod(), is(equalTo(com.xero.models.payrollnz.PaySlip.PaymentMethodEnum.ELECTRONICALLY)));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getEarningsLineID(),is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getEarningsRateID(),is(equalTo(UUID.fromString("f9d8f5b5-9049-47f4-8541-35e200f750a5"))));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getDisplayName(),is(equalTo("Ordinary Time")));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getRatePerUnit(),is(equalTo(25.0)));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getNumberOfUnits(),is(equalTo(0.0)));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getAmount(),is(equalTo(0.0)));
        assertThat(response.getPaySlip().getEarningsLines().get(0).getIsLinkedToTimesheet(),is(equalTo(false))); 
        assertThat(response.getPaySlip().getEarningsLines().get(0).getIsSystemGenerated(),is(equalTo(true)));     
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getEarningsLineID(),is(equalTo(UUID.fromString("0441497f-5dc7-4cd3-a90d-f2e07e21b2a6"))));
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getEarningsRateID(),is(equalTo(UUID.fromString("39b3560a-5d2f-4538-924a-4349dc86396e"))));
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getDisplayName(),is(equalTo("Holiday Pay")));
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getFixedAmount(),is(equalTo(268.8)));
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getAmount(),is(equalTo(268.8)));
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getIsLinkedToTimesheet(),is(equalTo(false)));
        assertThat(response.getPaySlip().getLeaveEarningsLines().get(0).getIsSystemGenerated(),is(equalTo(true)));
        assertThat(response.getPaySlip().getLeaveAccrualLines().get(0).getLeaveTypeID(),is(equalTo(UUID.fromString("0441497f-5dc7-4cd3-a90d-f2e07e21b2a6"))));
        assertThat(response.getPaySlip().getLeaveAccrualLines().get(0).getNumberOfUnits(),is(equalTo(268.8)));
        assertThat(response.getPaySlip().getDeductionLines().get(0).getDeductionTypeID(),is(equalTo(UUID.fromString("a3760fe4-68a4-4e38-8326-fe616af7dc74"))));
        assertThat(response.getPaySlip().getDeductionLines().get(0).getDisplayName(),is(equalTo("KiwiSaver Voluntary Contributions")));
        assertThat(response.getPaySlip().getDeductionLines().get(0).getAmount(),is(equalTo(100.0)));
        assertThat(response.getPaySlip().getSuperannuationLines().get(0).getSuperannuationTypeID(),is(equalTo(UUID.fromString("563273ea-0dae-4f82-86a4-e0db77c008ea"))));
        assertThat(response.getPaySlip().getSuperannuationLines().get(0).getDisplayName(),is(equalTo("KiwiSaver")));
        assertThat(response.getPaySlip().getSuperannuationLines().get(0).getAmount(),is(equalTo(108.86)));
        assertThat(response.getPaySlip().getSuperannuationLines().get(0).getPercentage(),is(equalTo(3.0)));
        assertThat(response.getPaySlip().getSuperannuationLines().get(0).getManualAdjustment(),is(equalTo(false)));
        assertThat(response.getPaySlip().getPaymentLines().get(0).getPaymentLineID(),is(equalTo(UUID.fromString("6dc42925-2a11-4041-ac9a-4098e77791d5"))));
        assertThat(response.getPaySlip().getPaymentLines().get(0).getAmount(),is(equalTo(2262.72)));
        assertThat(response.getPaySlip().getPaymentLines().get(0).getAccountNumber(),is(equalTo("0607050201419000")));
        assertThat(response.getPaySlip().getPaymentLines().get(0).getAccountName(),is(equalTo("Casual Worker")));
        assertThat(response.getPaySlip().getEmployeeTaxLines().get(0).getTaxLineID(),is(equalTo(UUID.fromString("2faf8f5d-6446-4bea-a2cd-d5cd7e8b9384"))));
        assertThat(response.getPaySlip().getEmployeeTaxLines().get(0).getAmount(),is(equalTo(1057.22)));
        assertThat(response.getPaySlip().getEmployeeTaxLines().get(0).getGlobalTaxTypeID(),is(equalTo("11")));
        assertThat(response.getPaySlip().getEmployerTaxLines().get(0).getTaxLineID(),is(equalTo(UUID.fromString("caa8d4fa-0949-460d-90f5-f43ec9f3db12"))));
        assertThat(response.getPaySlip().getEmployerTaxLines().get(0).getAmount(),is(equalTo(18.9)));
        assertThat(response.getPaySlip().getEmployerTaxLines().get(0).getGlobalTaxTypeID(),is(equalTo("10")));
        assertThat(response.getPaySlip().getEmployerTaxLines().get(0).getManualAdjustment(),is(equalTo(false)));
        assertThat(response.getPaySlip().getStatutoryDeductionLines().get(0).getStatutoryDeductionTypeID(),is(equalTo(UUID.fromString("b5efd8d1-0c93-4a14-a314-b5cba4a4e6b3"))));
        assertThat(response.getPaySlip().getStatutoryDeductionLines().get(0).getAmount(),is(equalTo(108.86)));
        assertThat(response.getPaySlip().getTaxSettings().getPeriodUnits(),is(equalTo(1.0)));
        assertThat(response.getPaySlip().getTaxSettings().getPeriodType(), is(equalTo(com.xero.models.payrollnz.TaxSettings.PeriodTypeEnum.WEEKS)));
        assertThat(response.getPaySlip().getTaxSettings().getTaxCode(), is(equalTo(com.xero.models.payrollnz.TaxCode.M)));
        assertThat(response.getPaySlip().getTaxSettings().getLumpSumTaxCode(),is(equalTo("SB")));
        assertThat(response.getPaySlip().getGrossEarningsHistory().getDaysPaid(),is(equalTo(3)));
        assertThat(response.getPaySlip().getGrossEarningsHistory().getUnpaidWeeks(),is(equalTo(0)));

        //System.out.println(response.toString());
    }
}
