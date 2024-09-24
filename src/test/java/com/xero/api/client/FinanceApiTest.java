package com.xero.api.client;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.xero.api.ApiClient;
import com.xero.api.XeroApiException;
import com.xero.api.util.ConfigurationLoader;
import com.xero.models.finance.*;

import java.util.*;
import java.io.IOException;
import java.io.InputStream;

public class FinanceApiTest {

	ApiClient defaultClient; 
	FinanceApi financeApi; 
	String accessToken;
    String xeroTenantId; 	
	
	private static boolean setUpIsDone = false;

	@Before
	public void setUp() {
		// Set Access Token and Tenant Id
        accessToken = "123";
        xeroTenantId = "xyz";
        defaultClient = new ApiClient(ConfigurationLoader.getProperty("finance.api.url"),null,null,null,null);
        financeApi = FinanceApi.getInstance(defaultClient); 
        
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
		financeApi = null;
		defaultClient = null;
	}

    @Test
	public void testGetCashValidation() throws Exception {
		System.out.println("@Test - getCashValidation");
		List<CashValidationResponse> response = new ArrayList<CashValidationResponse>();

        try {
            //UUID subscriptionId = UUID.fromString("73151de8-3676-4887-a021-edec960dd537");
            response = financeApi.getCashValidation(accessToken, "73151de8-3676-4887-a021-edec960dd537", null, null, null);
            assertThat(response.get(0).getAccountId().toString(), (equalTo("73151de8-3676-4887-a021-edec960dd537")));
            assertThat(response.get(0).getStatementBalance().getValue().toString(), (equalTo("100.0")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

	@Test
	public void testGetAccountingActivityAccountUsage() throws Exception {
		System.out.println("@Test - getAccountingActivityAccountUsage");

        try {
            AccountUsageResponse response = financeApi.getAccountingActivityAccountUsage(accessToken, "73151de8-3676-4887-a021-edec960dd537", null, null);
            assertThat(response.getOrganisationId().toString(), (equalTo("73151de8-3676-4887-a021-edec960dd537")));
            assertThat(response.getAccountUsage().get(0).getMonth(), (equalTo("2010-03")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

	@Test
	public void testGetAccountingActivityLockHistory() throws Exception {
		System.out.println("@Test - getAccountingActivityLockHistory");

        try {
            LockHistoryResponse response = financeApi.getAccountingActivityLockHistory(accessToken, "73151de8-3676-4887-a021-edec960dd537", null);
            assertThat(response.getOrganisationId().toString(), (equalTo("73151de8-3676-4887-a021-edec960dd537")));
            assertThat(response.getLockDates().get(1).getUpdatedDateUtc().toString(), (equalTo("2019-01-21T10:59:33Z")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

	@Test
	public void testGetAccountingActivityReportHistory() throws Exception {
		System.out.println("@Test - getAccountingActivityReportHistory");

        try {
            ReportHistoryResponse response = financeApi.getAccountingActivityReportHistory(accessToken, "73151de8-3676-4887-a021-edec960dd537", null);
            assertThat(response.getOrganisationId().toString(), (equalTo("73151de8-3676-4887-a021-edec960dd537")));
            assertThat(response.getReports().get(0).getPublishedDateUtc().toString(), (equalTo("2019-09-23T00:30:17.407Z")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

	@Test
	public void testGetAccountingActivityUserActivities() throws Exception {
		System.out.println("@Test - getAccountingActivityUserActivities");

        try {
            UserActivitiesResponse response = financeApi.getAccountingActivityUserActivities(accessToken, "73151de8-3676-4887-a021-edec960dd537", null);
            assertThat(response.getOrganisationId().toString(), (equalTo("73151de8-3676-4887-a021-edec960dd537")));
            assertThat(response.getUsers().get(0).getMonthPeriod().toString(), (equalTo("2020-01")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
	public void testGetFinancialStatementBalanceSheet() throws Exception {
		System.out.println("@Test - getFinancialStatementBalanceSheet");

        try {
            BalanceSheetResponse response = financeApi.getFinancialStatementBalanceSheet(accessToken, "73151de8-3676-4887-a021-edec960dd537", "2020-06-30");
            assertThat(response.getAsset().getAccountTypes().get(0).getAccounts().get(0).getAccountID().toString(), (equalTo("abcdeabc-80bb-47f5-9418-d1fc2241b838")));
            assertThat(response.getBalanceDate().toString(), (equalTo("2021-05-12")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
	public void testGetFinancialStatementCashflow() throws Exception {
		System.out.println("@Test - getFinancialStatementCashflow");

        try {
            CashflowResponse response = financeApi.getFinancialStatementCashflow(accessToken, "73151de8-3676-4887-a021-edec960dd537", "2020-09-15", "2020-09-15");
            assertThat(response.getCashflowActivities().get(0).getCashflowTypes().get(0).getAccounts().get(0).getAccountId().toString(), (equalTo("abcdefab-4d1e-4d1a-9e4c-68b2c2a278e2")));
            assertThat(response.getStartDate().toString(), (equalTo("2018-07-01")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
	public void testGetFinancialStatementProfitAndLoss() throws Exception {
		System.out.println("@Test - getFinancialStatementProfitAndLoss");

        try {
            ProfitAndLossResponse response = financeApi.getFinancialStatementProfitAndLoss(accessToken, "73151de8-3676-4887-a021-edec960dd537", "2020-09-15", "2020-09-15");
            assertThat(response.getRevenue().getAccountTypes().get(0).getAccounts().get(0).getAccountID().toString(), (equalTo("abcdefab-2006-43c2-a5da-3c0e5f43b452")));
            assertThat(response.getStartDate().toString(), (equalTo("2020-07-01")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
	public void testGetFinancialStatementTrialBalance() throws Exception {
		System.out.println("@Test - getFinancialStatementTrialBalance");

        try {
            TrialBalanceResponse response = financeApi.getFinancialStatementTrialBalance(accessToken, "73151de8-3676-4887-a021-edec960dd537", "2020-09-15");
            assertThat(response.getAccounts().get(0).getAccountId().toString(), (equalTo("abcdefab-3bbf-4f2a-9e4c-20ec7b8e6b41")));
            assertThat(response.getStartDate().toString(), (equalTo("2020-07-01")));

        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
	public void testGetFinancialStatementContactsRevenue() throws Exception {
		System.out.println("@Test - getFinancialStatementContactsRevenue");
        List<UUID> contacts = new ArrayList<UUID>();
        contacts.add(UUID.fromString("1f580fe2-0659-31ee-eeb4-5c49d15d8bfa"));
        try {
            IncomeByContactResponse response = financeApi.getFinancialStatementContactsRevenue(accessToken, "73151de8-3676-4887-a021-edec960dd537",contacts,true,null,null);
            assertThat(response.getContacts().get(0).getName(), (equalTo("FirstContact")));
        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
	public void testGetFinancialStatementContactsExpense() throws Exception {
		System.out.println("@Test - getFinancialStatementContactsExpense");
        List<UUID> contacts = new ArrayList<UUID>();
        contacts.add(UUID.fromString("1f580fe2-0659-31ee-eeb4-5c49d15d8bfa"));
        try {
            IncomeByContactResponse response = financeApi.getFinancialStatementContactsExpense(accessToken, "73151de8-3676-4887-a021-edec960dd537",contacts,true,null,null);
            assertThat(response.getContacts().get(0).getName(), (equalTo("FirstContact")));
        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }

    @Test
	public void testGetBankStatementAccounting() throws Exception {
		System.out.println("@Test - getBankStatementAccounting");
        UUID bankAccountID = UUID.fromString("1234eee9-47f0-4179-bd46-9adb4f21cc7f");
        try {
            BankStatementAccountingResponse response = financeApi.getBankStatementAccounting(accessToken, "73151de8-3676-4887-a021-edec960dd537", bankAccountID, "2021-01-01", "2021-01-02", null);
            assertThat(response.getStatements().get(0).getStatementId().toString(), (equalTo("7c29eee9-47f0-4179-bd46-9adb4f21cc7f")));
            assertThat(response.getStatements().get(0).getStatementLines().get(0).getPayments().get(0).getInvoice().getContact().getContactName(), (equalTo("Bob")));
            assertThat(response.getStatements().get(0).getEndBalance(), (equalTo(200.0)));
        } catch (XeroApiException xe) {
            System.out.println(xe.toString());
        }
    }
}
