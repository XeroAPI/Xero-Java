package com.xero.api.client;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Every.everyItem;

import com.xero.api.XeroApiException;
import com.xero.api.ApiClient;
import com.xero.api.client.*;
import com.xero.models.accounting.*;
import com.xero.example.CustomJsonConfig;

import org.threeten.bp.*;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class AccountingApiReportsTest {

	CustomJsonConfig config;
	ApiClient apiClientForAccounting; 
	AccountingApi api; 

    private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
		config = new CustomJsonConfig();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting-oauth1/2.0.0",null,null,null);
		api = new AccountingApi(config);
		api.setApiClient(apiClientForAccounting);
		api.setOAuthToken(config.getConsumerKey(), config.getConsumerSecret());

        // ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
        if (setUpIsDone) {
            return;
        }

        try {
            System.out.println("Sleep for 30 seconds");
            Thread.sleep(60000);
        } catch(InterruptedException e) {
            System.out.println(e);
        }
        // do the setup
        setUpIsDone = true;
	}

	public void tearDown() {
		api = null;
		apiClientForAccounting = null;
	}

    @Test
    public void getReportAgedPayablesByContactTest() throws IOException {
        System.out.println("@Test - getReportAgedPayablesByContact");
        UUID contactId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        LocalDate date = null;
        LocalDate fromDate = null;
        LocalDate toDate = null;
        ReportWithRows response = api.getReportAgedPayablesByContact(contactId, date, fromDate, toDate);

        assertThat(response.getReports().get(0).getReportID(), is(equalTo("AgedPayablesByContact")));
        assertThat(response.getReports().get(0).getReportName(), is(equalTo("Aged Payables By Contact")));
        assertThat(response.getReports().get(0).getReportType(), is("AgedPayablesByContact"));
        assertThat(response.getReports().get(0).getReportTitles().get(0), is(equalTo("Invoices")));
        assertThat(response.getReports().get(0).getReportTitles().get(1), is(equalTo("ABC")));
        assertThat(response.getReports().get(0).getReportTitles().get(2), is(equalTo("From 10 October 2017 to 22 April 2019")));
        assertThat(response.getReports().get(0).getReportTitles().get(3), is(equalTo("Showing payments to 22 April 2019")));
        assertThat(response.getReports().get(0).getReportDate(), is(equalTo("22 April 2019")));
        assertThat(response.getReports().get(0).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.HEADER)));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(0).getValue(), is(equalTo("Date")));
        assertThat(response.getReports().get(0).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SECTION)));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getValue(), is(equalTo("2017-10-10T00:00:00")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(1).getValue(), is(equalTo("Opening Balance")));
        assertThat(response.getReports().get(0).getRows().get(2).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SECTION)));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.ROW)));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(0).getCells().get(0).getValue(), is(equalTo("2018-10-09T00:00:00")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(0).getCells().get(0).getAttributes().get(0).getId(), is(equalTo("invoiceID")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(0).getCells().get(0).getAttributes().get(0).getValue(), is(equalTo("1f3960ae-0537-4438-a4dd-76d785e6d7d8")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SUMMARYROW)));        
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(1).getCells().get(0).getValue(), is(equalTo("Total")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(1).getCells().get(4).getValue(), is(equalTo("250.00")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(1).getCells().get(7).getValue(), is(equalTo("250.00")));
        //System.out.println(response.getReports().toString());
    }

    @Test
    public void getReportAgedReceivablesByContactTest() throws IOException {
        System.out.println("@Test - getReportAgedReceivablesByContact");
        UUID contactId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        LocalDate date = null;
        LocalDate fromDate = null;
        LocalDate toDate = null;
        ReportWithRows response = api.getReportAgedReceivablesByContact(contactId, date, fromDate, toDate);

        assertThat(response.getReports().get(0).getReportID(), is(equalTo("AgedReceivablesByContact")));
        assertThat(response.getReports().get(0).getReportName(), is(equalTo("Aged Receivables By Contact")));
        assertThat(response.getReports().get(0).getReportType(), is("AgedReceivablesByContact"));
        assertThat(response.getReports().get(0).getReportTitles().get(0), is(equalTo("Invoices")));
        assertThat(response.getReports().get(0).getReportTitles().get(1), is(equalTo("ABC")));
        assertThat(response.getReports().get(0).getReportTitles().get(2), is(equalTo("From 10 October 2017 to 23 April 2019")));
        assertThat(response.getReports().get(0).getReportTitles().get(3), is(equalTo("Showing payments to 23 April 2019")));
        assertThat(response.getReports().get(0).getReportDate(), is(equalTo("23 April 2019")));
       assertThat(response.getReports().get(0).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.HEADER)));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(0).getValue(), is(equalTo("Date")));
        assertThat(response.getReports().get(0).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SECTION)));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getValue(), is(equalTo("2017-10-10T00:00:00")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(1).getValue(), is(equalTo("Opening Balance")));
        assertThat(response.getReports().get(0).getRows().get(2).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SECTION)));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.ROW)));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(0).getCells().get(0).getValue(), is(equalTo("2018-05-13T00:00:00")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(0).getCells().get(0).getAttributes().get(0).getId(), is(equalTo("invoiceID")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(0).getCells().get(0).getAttributes().get(0).getValue(), is(equalTo("40ebad47-24e2-4dc9-a5f5-579df427671b")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.ROW)));        
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(1).getCells().get(0).getValue(), is(equalTo("2019-04-23T00:00:00")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(1).getCells().get(4).getValue(), is(equalTo("50.00")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(1).getCells().get(7).getValue(), is(equalTo("50.00")));   
        assertThat(response.getReports().get(0).getRows().get(3).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SECTION)));        
        assertThat(response.getReports().get(0).getRows().get(3).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SUMMARYROW)));        
        assertThat(response.getReports().get(0).getRows().get(3).getRows().get(0).getCells().get(0).getValue(), is(equalTo("Closing Balance")));
        assertThat(response.getReports().get(0).getRows().get(3).getRows().get(0).getCells().get(4).getValue(), is(equalTo("150.00")));
        //System.out.println(response.getReports().get(0).toString());
    }

    @Test
    public void getReportBalanceSheetTest() throws IOException {
        System.out.println("@Test - getReportBalanceSheet");
        String date = null;
        Integer periods = null;
        String timeframe = null;
        String trackingOptionID1 = null;
        String trackingOptionID2 = null;
        Boolean standardLayout = null;
        Boolean paymentsOnly = null;
        ReportWithRows response = api.getReportBalanceSheet(date, periods, timeframe, trackingOptionID1, trackingOptionID2, standardLayout, paymentsOnly);

        assertThat(response.getReports().get(0).getReportID(), is(equalTo("BalanceSheet")));
        assertThat(response.getReports().get(0).getReportName(), is(equalTo("Balance Sheet")));
        assertThat(response.getReports().get(0).getReportType(), is(equalTo("BalanceSheet")));
        assertThat(response.getReports().get(0).getReportTitles().get(0), is(equalTo("Balance Sheet")));
        assertThat(response.getReports().get(0).getReportTitles().get(1), is(equalTo("Dev Evangelist - Sid Test 3 (NZ-2016-02)")));
        assertThat(response.getReports().get(0).getReportDate(), is(equalTo("12 April 2019")));
        assertThat(response.getReports().get(0).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.HEADER)));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(1).getValue(), is(equalTo("30 Apr 2019")));
        assertThat(response.getReports().get(0).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SECTION)));
        assertThat(response.getReports().get(0).getRows().get(1).getTitle(), is(equalTo("Assets")));
        assertThat(response.getReports().get(0).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SECTION)));
        assertThat(response.getReports().get(0).getRows().get(2).getTitle(), is(equalTo("Bank")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.ROW)));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(0).getCells().get(0).getValue(), is(equalTo("Country Savings")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(0).getCells().get(0).getAttributes().get(0).getId(), is(equalTo("account")));
        assertThat(response.getReports().get(0).getRows().get(2).getRows().get(0).getCells().get(0).getAttributes().get(0).getValue(), is(equalTo("041207d2-3d61-4e5d-8c1a-b9236955a71c")));
        //System.out.println(response.getReports().toString());
    }

    @Test
    public void getReportBankSummaryTest() throws IOException {
        System.out.println("@Test - getReportBankSummary");
        LocalDate date = null;
        Integer period = null;
        Integer timeframe = null;
        
        ReportWithRows response = api.getReportBankSummary(date, period, timeframe);
        assertThat(response.getReports().get(0).getReportID(), is(equalTo("BankSummary")));
        assertThat(response.getReports().get(0).getReportName(), is(equalTo("Bank Summary")));
        assertThat(response.getReports().get(0).getReportType(), is("BankSummary"));
        assertThat(response.getReports().get(0).getReportTitles().get(0), is(equalTo("Bank Summary")));
        assertThat(response.getReports().get(0).getReportTitles().get(1), is(equalTo("MindBody Test 10 (AU-2016-02)")));
        assertThat(response.getReports().get(0).getReportTitles().get(2), is(equalTo("From 1 April 2019 to 30 April 2019")));
        assertThat(response.getReports().get(0).getReportDate(), is(equalTo("23 April 2019")));
        assertThat(response.getReports().get(0).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.HEADER)));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(0).getValue(), is(equalTo("Bank Accounts")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(1).getValue(), is(equalTo("Opening Balance")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(2).getValue(), is(equalTo("Cash Received")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(3).getValue(), is(equalTo("Cash Spent")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(4).getValue(), is(equalTo("Closing Balance")));        
        assertThat(response.getReports().get(0).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SECTION)));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.ROW)));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getValue(), is(equalTo("Big City Bank")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getAttributes().get(0).getId(), is(equalTo("accountID")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getAttributes().get(0).getValue(), is(equalTo("03f9cf1e-2deb-4bf1-b0a8-b57f08672eb8")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SUMMARYROW)));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(1).getCells().get(0).getValue(), is(equalTo("Total")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(1).getCells().get(4).getValue(), is(equalTo("10.00")));
        //System.out.println(response.getReports().get(0).toString());
    }

    @Test
    public void getReportExecutiveSummaryTest() throws IOException {
        System.out.println("@Test - getReportExecutiveSummary");
        LocalDate date = null;
        ReportWithRows response = api.getReportExecutiveSummary(date);

        assertThat(response.getReports().get(0).getReportID(), is(equalTo("ExecutiveSummary")));
        assertThat(response.getReports().get(0).getReportName(), is(equalTo("Executive Summary")));
        assertThat(response.getReports().get(0).getReportType(), is("ExecutiveSummary"));
        assertThat(response.getReports().get(0).getReportTitles().get(0), is(equalTo("Executive Summary")));
        assertThat(response.getReports().get(0).getReportTitles().get(1), is(equalTo("Dev Evangelist - Sid Test 1 (US-2016-06)")));
        assertThat(response.getReports().get(0).getReportTitles().get(2), is(equalTo("For the month of April 2019")));
        assertThat(response.getReports().get(0).getReportDate(), is(equalTo("24 April 2019")));
        assertThat(response.getReports().get(0).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.HEADER)));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(1).getValue(), is(equalTo("Apr 2019")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(2).getValue(), is(equalTo("Mar 2019")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(3).getValue(), is(equalTo("Variance")));       
        assertThat(response.getReports().get(0).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SECTION)));
        assertThat(response.getReports().get(0).getRows().get(1).getTitle(), is("Cash"));        
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.ROW)));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getValue(), is(equalTo("Cash received")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(1).getValue(), is(equalTo("0.00")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(2).getValue(), is(equalTo("0.00")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(3).getValue(), is(equalTo("0.0%")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.ROW)));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(1).getCells().get(0).getValue(), is(equalTo("Cash spent")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(1).getCells().get(1).getValue(), is(equalTo("0.00")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(1).getCells().get(2).getValue(), is(equalTo("20.00")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(1).getCells().get(3).getValue(), is(equalTo("-100.0%")));                 
        //System.out.println(response.getReports().get(0).toString());
    }

    @Test
    public void getReportTenNinetyNineTest() throws IOException {
        System.out.println("@Test - getReportTenNinetyNine");
        String reportYear = null;
        Reports response = api.getReportTenNinetyNine(reportYear);

        assertThat(response.getReports().get(0).getReportName(), is(equalTo("1099 report")));
        assertThat(response.getReports().get(0).getReportDate(), is(equalTo("1 Jan 2016 to 31 Dec 2016")));
        assertThat(response.getReports().get(0).getContacts().get(0).getBox3(), is(equalTo(1000.00)));  
        assertThat(response.getReports().get(0).getContacts().get(0).getName(), is(equalTo("Bank West")));
        assertThat(response.getReports().get(0).getContacts().get(0).getFederalTaxIDType(), is(equalTo("SSN")));
        assertThat(response.getReports().get(0).getContacts().get(0).getCity(), is(equalTo("Pinehaven")));
        assertThat(response.getReports().get(0).getContacts().get(0).getZip(), is(equalTo("12345")));
        assertThat(response.getReports().get(0).getContacts().get(0).getState(), is(equalTo("CA")));
        assertThat(response.getReports().get(0).getContacts().get(0).getEmail(), is(equalTo("jack@bowest.com")));
        assertThat(response.getReports().get(0).getContacts().get(0).getTaxID(), is(equalTo("234-22-2223")));
        assertThat(response.getReports().get(0).getContacts().get(0).getContactId(), is(equalTo(UUID.fromString("81d5706a-8057-4338-8511-747cd85f4c68"))));
        assertThat(response.getReports().get(0).getContacts().get(2).getBox1(), is(equalTo(5543.75)));  
        //System.out.println(response.getReports().toString());
    }

    @Test
    public void getReportTrialBalanceTest() throws IOException {
        System.out.println("@Test - getReportTrialBalance");
        LocalDate date = null;
        Boolean paymentsOnly = null;
        ReportWithRows response = api.getReportTrialBalance(date, paymentsOnly);
        
        assertThat(response.getReports().get(0).getReportID(), is(equalTo("TrialBalance")));
        assertThat(response.getReports().get(0).getReportName(), is(equalTo("Trial Balance")));
        assertThat(response.getReports().get(0).getReportType(), is("TrialBalance"));
        assertThat(response.getReports().get(0).getReportTitles().get(0), is(equalTo("Trial Balance")));
        assertThat(response.getReports().get(0).getReportTitles().get(1), is(equalTo("Dev Evangelist - Sid Test 1 (US-2016-06)")));
        assertThat(response.getReports().get(0).getReportTitles().get(2), is(equalTo("As at 24 April 2019")));
        assertThat(response.getReports().get(0).getReportDate(), is(equalTo("24 April 2019")));
       assertThat(response.getReports().get(0).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.HEADER)));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(0).getValue(), is(equalTo("Account")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(1).getValue(), is(equalTo("Debit")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(2).getValue(), is(equalTo("Credit")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(3).getValue(), is(equalTo("YTD Debit")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(4).getValue(), is(equalTo("YTD Credit")));        
        assertThat(response.getReports().get(0).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SECTION)));
        assertThat(response.getReports().get(0).getRows().get(1).getTitle(), is("Revenue"));        
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.ROW)));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getValue(), is(equalTo("Big Expense (002)")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getAttributes().get(0).getId(), is(equalTo("account")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getAttributes().get(0).getValue(), is(equalTo("da962997-a8bd-4dff-9616-01cdc199283f")));
        //System.out.println(response.getReports().get(0).toString());
    }    

    @Test
    public void getReportBudgetSummaryTest() throws IOException {
        System.out.println("@Test - getReportBudgetSummary");
        LocalDate date = null;
        Integer period = null;
        Integer timeframe = null;
        ReportWithRows response = api.getReportBudgetSummary(date, period, timeframe);

        assertThat(response.getReports().get(0).getReportID(), is(equalTo("BudgetSummary")));
        assertThat(response.getReports().get(0).getReportName(), is(equalTo("Budget Summary")));
        assertThat(response.getReports().get(0).getReportType(), is("BudgetSummary"));
        assertThat(response.getReports().get(0).getReportTitles().get(0), is(equalTo("Overall Budget")));
        assertThat(response.getReports().get(0).getReportTitles().get(1), is(equalTo("Budget Summary")));
        assertThat(response.getReports().get(0).getReportTitles().get(2), is(equalTo("Dev Evangelist - Sid Test 1 (US-2016-06)")));
        assertThat(response.getReports().get(0).getReportTitles().get(3), is(equalTo("April 2019 to March 2022")));
        assertThat(response.getReports().get(0).getReportDate(), is(equalTo("24 April 2019")));
         assertThat(response.getReports().get(0).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.HEADER)));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(0).getValue(), is(equalTo("Account")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(1).getValue(), is(equalTo("Jun-19")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(2).getValue(), is(equalTo("Sep-19")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(3).getValue(), is(equalTo("Dec-19")));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(4).getValue(), is(equalTo("Mar-20")));  
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(5).getValue(), is(equalTo("Jun-20")));  
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(6).getValue(), is(equalTo("Sep-20")));  
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(7).getValue(), is(equalTo("Dec-20")));  
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(8).getValue(), is(equalTo("Mar-21")));  
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(9).getValue(), is(equalTo("Jun-21")));  
        assertThat(response.getReports().get(0).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SECTION)));
        assertThat(response.getReports().get(0).getRows().get(1).getTitle(), is("Income"));        
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.ROW)));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getValue(), is(equalTo("Big Expense")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getAttributes().get(0).getId(), is(equalTo("account")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getAttributes().get(0).getValue(), is(equalTo("da962997-a8bd-4dff-9616-01cdc199283f")));
        //System.out.println(response.toString());
    }

    @Test
    public void getReportProfitAndLossTest() throws IOException {
        System.out.println("@Test - getReportProfitAndLoss");
        
        LocalDate fromDate = null;
        LocalDate toDate = null;
        String timeframe = "MONTH";
        Boolean standardLayout = true;
        Boolean paymentsOnly = false;
            
        Integer periods = null;
        String trackingCategoryID = null;
        String trackingCategoryID2 = null;
        String trackingOptionID = null;
        String trackingOptionID2 = null;
        ReportWithRows response = api.getReportProfitAndLoss(fromDate, toDate, periods, timeframe, trackingCategoryID, trackingCategoryID2, trackingOptionID, trackingOptionID2, standardLayout, paymentsOnly);

        assertThat(response.getReports().get(0).getReportID(), is(equalTo("ProfitAndLoss")));
        assertThat(response.getReports().get(0).getReportName(), is(equalTo("Profit and Loss")));
        assertThat(response.getReports().get(0).getReportType(), is("ProfitAndLoss"));
        assertThat(response.getReports().get(0).getReportTitles().get(0), is(equalTo("Income Statement")));
        assertThat(response.getReports().get(0).getReportTitles().get(1), is(equalTo("Dev Evangelist - Sid Test 1 (US-2016-06)")));
        assertThat(response.getReports().get(0).getReportTitles().get(2), is(equalTo("10 October 2018 to 24 April 2019")));
        assertThat(response.getReports().get(0).getReportDate(), is(equalTo("24 April 2019")));
        assertThat(response.getReports().get(0).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.HEADER)));
        assertThat(response.getReports().get(0).getRows().get(0).getCells().get(1).getValue(), is(equalTo("24 Apr 19")));
        assertThat(response.getReports().get(0).getRows().get(1).getRowType(), is(equalTo(com.xero.models.accounting.RowType.SECTION)));
        assertThat(response.getReports().get(0).getRows().get(1).getTitle(), is("Revenue"));        
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getRowType(), is(equalTo(com.xero.models.accounting.RowType.ROW)));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getValue(), is(equalTo("Big Expense")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getAttributes().get(0).getId(), is(equalTo("account")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(0).getAttributes().get(0).getValue(), is(equalTo("da962997-a8bd-4dff-9616-01cdc199283f")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(1).getValue(), is(equalTo("480.00")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(1).getAttributes().get(0).getId(), is(equalTo("account")));
        assertThat(response.getReports().get(0).getRows().get(1).getRows().get(0).getCells().get(1).getAttributes().get(0).getValue(), is(equalTo("da962997-a8bd-4dff-9616-01cdc199283f")));
        //System.out.println(response.toString());
    }

    @Test
    public void getReportBASorGSTTest() throws IOException {
        System.out.println("@Test - getReportBASorGST - not implemented");
        String reportID = null;
        //ReportWithRows response = api.getReportBASorGST(reportID);

        // TODO: test validations
        //System.out.println(response.getReports().get(0).toString());
    }

    @Test
    public void getReportBASorGSTListTest() throws IOException {
        System.out.println("@Test - getReportBASorGSTList - not implemented");
        //ReportWithRows response = api.getReportBASorGSTList();

        // TODO: test validations
        //System.out.println(response.getReports().get(0).toString());
    }
}