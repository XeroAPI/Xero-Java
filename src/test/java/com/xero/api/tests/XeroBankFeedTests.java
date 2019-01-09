package com.xero.api.tests;

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
import com.xero.api.Config;
import com.xero.api.JsonConfig;
import com.xero.api.client.*;
import com.xero.models.bankfeeds.*;
import com.xero.models.bankfeeds.Error;
import com.xero.models.bankfeeds.FeedConnection.AccountTypeEnum;

import com.xero.example.SampleData;

import org.threeten.bp.LocalDate;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Calendar;
import java.util.Map;

public class XeroBankFeedTests {

	Config config;
	ApiClient apiClientForBankFeeds;
	BankFeedsApi bankFeedsApi;
	Map<String, String> params;
	String token;
	String tokenSecret;
	String badToken;
	String badTokenSecret;
	Calendar now;
	String expectedStatus;
	int day;
	int year;
	int lastMonth;

	@Before
	public void setUp() {
		config = JsonConfig.getInstance();
		// 30min OAUTH TOKEN/SECRET from org in approved region(UK)
		token = "ZG6ZYF2HHGHSWXE1CYCYF3IROFBLHA";
		tokenSecret = "7JMQOGZAMOH6NPRVSTGPUI0PUURTIB";

		//30min OAUTH TOKEN/SECRET from org in NON-approved region(US)
		badToken = "LZOMQZTXGFYPZ9BGSP2DTDDERHMDIG";
		badTokenSecret = "UXBHLD6BXPRDMSVNUHKVDNZ5KE7D1P";

		apiClientForBankFeeds = new ApiClient(config.getBankFeedsUrl(),null,null,null);
		bankFeedsApi = new BankFeedsApi(apiClientForBankFeeds);
		bankFeedsApi.setOAuthToken(token, tokenSecret);
		params = null;
		now = Calendar.getInstance();

		day = now.get(Calendar.DATE);
		year = now.get(Calendar.YEAR);
		lastMonth = now.get(Calendar.MONTH) - 1;
		if (lastMonth == -1) {
			lastMonth = 12;
			year = year -1;
		}
		
		if (day > 28) {
			day = 28;
		}	
	}
   

   	@After
	public void tearDown() {
		config = null;
	}

	@Test
    public void feedconnection_success_create() {
        System.out.println("@Test - feedconnection_success_create");
		
		try {
			String accountToken = "foobar" + SampleData.loadRandomNum();
			FeedConnection newBank = new FeedConnection();
			newBank.setAccountName("SDK Bank " + SampleData.loadRandomNum());
			newBank.setAccountNumber("1234" + SampleData.loadRandomNum());
			newBank.setAccountType(AccountTypeEnum.BANK);
			newBank.setAccountToken(accountToken);
			newBank.setCurrency("GBP");
			
			FeedConnections arrayFeedConnections = new FeedConnections();
			arrayFeedConnections.addItemsItem(newBank);
			
			FeedConnections fc = bankFeedsApi.createFeedConnections(arrayFeedConnections);
			
			// ASSERT
			assert(fc.getItems().size() > 0);
			assert(fc.getItems().get(0).getAccountToken().equals(accountToken));
			System.out.println("New Bank Feed with status: " + fc.getItems().get(0).getStatus());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    }

    @Test
    public void feedconnection_fail_get_feedconnection_wrong_region_org() {
        System.out.println("@Test - feedconnection_fail_get_feedconnection_wrong_region_org");
		
        try {
			String accountToken = "foobar" + SampleData.loadRandomNum();
			FeedConnection newBank = new FeedConnection();
			newBank.setAccountName("SDK Bank " + SampleData.loadRandomNum());
			newBank.setAccountNumber("1234" + SampleData.loadRandomNum());
			newBank.setAccountType(AccountTypeEnum.BANK);
			newBank.setAccountToken(accountToken);
			newBank.setCurrency("GBP");
			
			FeedConnections arrayFeedConnections = new FeedConnections();
			arrayFeedConnections.addItemsItem(newBank);
			
			bankFeedsApi.setOAuthToken(badToken, badTokenSecret);
			FeedConnections fc = bankFeedsApi.createFeedConnections(arrayFeedConnections);
		
		
		} catch (XeroApiException e) {
			
			int actualCode = e.getResponseCode();
			assertThat(actualCode, is(equalTo(403)));

			try {
				TypeReference<Error> typeRef = new TypeReference<Error>() {};
				Error error =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);
				assert(error.getStatus().equals(403));
				System.out.println(error.getDetail());
			} catch (IOException ioe) {
				System.out.println("IO:" + ioe.toString());
			}
		} catch (Exception e) {
			System.out.println("Generic Exception " + e.getMessage());
		}
    }

	@Test
    public void feedconnection_success_get_all() {
        System.out.println("@Test - feedconnection_success_get_all");
		
		try {
			FeedConnections fc = bankFeedsApi.getFeedConnections(null,null);
			
			// ASSERT
			assert(fc.getItems().size() > 0);
			System.out.println("Total Banks found: " + fc.getItems().size());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    }

    @Test
    public void feedconnection_success_get_one() {
        System.out.println("@Test - feedconnection_success_get_one");
		
		try {
			FeedConnections fc = bankFeedsApi.getFeedConnections(null,null);
			FeedConnection oneFC = bankFeedsApi.getFeedConnection(fc.getItems().get(0).getId());
			
			// ASSERT
			assert(!"".equals(oneFC.getAccountToken()));
			assert(!"".equals(oneFC.getAccountName()));
			assert(!"".equals(oneFC.getAccountType()));
			System.out.println("One Bank Feed - Name: " + oneFC.getAccountName());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    }

   @Test
   public void feedconnection_success_delete() {
        System.out.println("@Test - feedconnection_success_delete");
        
        try {
			FeedConnections fc = bankFeedsApi.getFeedConnections(null,null);
			Integer idx = (fc.getItems().size()-1);

			// Create FeedConnection object
			FeedConnection feedConnectionOne = new FeedConnection();
			feedConnectionOne.setId(fc.getItems().get(idx).getId());
			// FeedConnection to Delete
			FeedConnections deleteFeedConnections = new FeedConnections();
			// Add FeedConnection Object
			deleteFeedConnections.addItemsItem(feedConnectionOne);
			// Call DELETE method
			FeedConnections deletedFeedConnection = bankFeedsApi.deleteFeedConnections(deleteFeedConnections);
			String actualStatus = deletedFeedConnection.getItems().get(0).getStatus().toString();
	
			// ASSERT
			assert(deletedFeedConnection.getItems().size() > 0);
			assertThat(actualStatus, is(equalTo("PENDING")));
			System.out.println("Feed Connection deletion is pending");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
   }

   @Test
   public void statement_success_create() {
        System.out.println("@Test - statement_success_create");

        try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();
				
			LocalDate stDate = LocalDate.of(year, lastMonth, 1);							
			newStatement.setStartDate(stDate);			
			LocalDate endDate = LocalDate.of(year, lastMonth, 15);				
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("150");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			FeedConnections fc = bankFeedsApi.getFeedConnections(null,null);
			newStatement.setFeedConnectionId(fc.getItems().get(2).getId().toString());
			
			StatementLine newStatementLine = new StatementLine();
			newStatementLine.setAmount("50");
			newStatementLine.setChequeNumber("123" + SampleData.loadRandomNum());
			newStatementLine.setDescription("My new line");
			newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine.setReference("Foobar" + SampleData.loadRandomNum());
			newStatementLine.setPayeeName("StarLord" + SampleData.loadRandomNum());
			newStatementLine.setTransactionId("1234" + SampleData.loadRandomNum());
			
			LocalDate postedDate = LocalDate.of(year, lastMonth, day);				
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			arrayOfStatements.addItemsItem(newStatement);
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements);
			
			assert(rStatements.getItems().size() > 0);
			System.out.println("Statement Status: " + rStatements.getItems().get(0).getStatus());
						
		} catch (Exception e) {
			System.out.println(e.toString());
		}
 
   }

   @Test
   public void statement_success_create_2() {
        System.out.println("@Test - statement_success_create_2");

		try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();

			LocalDate stDate = LocalDate.of(year, lastMonth, day);				
			newStatement.setStartDate(stDate);

			LocalDate endDate = LocalDate.of(year, lastMonth, day);							
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("150");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			FeedConnections fc = bankFeedsApi.getFeedConnections(null, null);

			if (fc.getItems().size() > 2) {
				
				newStatement.setFeedConnectionId(fc.getItems().get(0).getId().toString());
				
				StatementLine newStatementLine = new StatementLine();
				newStatementLine.setAmount("50");
				newStatementLine.setChequeNumber("123" + SampleData.loadRandomNum());
				newStatementLine.setDescription("My new line");
				newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
				newStatementLine.setReference("Foobar" + SampleData.loadRandomNum());
				newStatementLine.setPayeeName("StarLord" + SampleData.loadRandomNum());
				newStatementLine.setTransactionId("1234" + SampleData.loadRandomNum());
				
				LocalDate postedDate = LocalDate.of(year, lastMonth, day);				
				newStatementLine.setPostedDate(postedDate);
			
				StatementLines arrayStatementLines = new StatementLines();
				arrayStatementLines.add(newStatementLine);
				
				newStatement.setStatementLines(arrayStatementLines);
				
				arrayOfStatements.addItemsItem(newStatement);
				
				Statement newStatement2 = new Statement();

				LocalDate stDate2 = LocalDate.of(year, lastMonth, day);				
				newStatement2.setStartDate(stDate2);

				LocalDate endDate2 = LocalDate.of(year, lastMonth, day);				
				newStatement2.endDate(endDate2);
				StartBalance stBalance2 = new StartBalance();
				stBalance2.setAmount("100");
				stBalance2.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
				newStatement2.setStartBalance(stBalance2);
				
				EndBalance endBalance2 = new EndBalance();
				endBalance2.setAmount("150");
				endBalance2.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
				newStatement2.endBalance(endBalance2);
				
				newStatement2.setFeedConnectionId(fc.getItems().get(1).getId().toString());
				
				StatementLine newStatementLine2 = new StatementLine();
				newStatementLine2.setAmount("50");
				newStatementLine2.setChequeNumber("123" + SampleData.loadRandomNum());
				newStatementLine2.setDescription("My new line");
				newStatementLine2.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
				newStatementLine2.setReference("Foobar" + SampleData.loadRandomNum());
				newStatementLine2.setPayeeName("StarLord" + SampleData.loadRandomNum());
				newStatementLine2.setTransactionId("1234" + SampleData.loadRandomNum());
				LocalDate postedDate2 = LocalDate.of(year, lastMonth, day);
				newStatementLine2.setPostedDate(postedDate2);
			
				StatementLines arrayStatementLines2 = new StatementLines();
				arrayStatementLines2.add(newStatementLine2);
				
				newStatement2.setStatementLines(arrayStatementLines2);
				
				arrayOfStatements.addItemsItem(newStatement2);

				Map<String, String> params = null;
				Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements);
				assert(rStatements.getItems().size() > 0);
				System.out.println("Statement Status: " + rStatements.getItems().get(0).getStatus());

			} else {
				System.out.println("Need at least 2 feed connections to perform this test");
			}

		} catch (XeroApiException e) {
			
			int actualCode = e.getResponseCode();
			assertThat(actualCode, is(equalTo(403)));

			try {
				TypeReference<Error> typeRef = new TypeReference<Error>() {};
				Error error =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);
				assert(error.getStatus().equals(403));
				System.out.println(error.getDetail());
			} catch (IOException ioe) {
				System.out.println("IO:" + ioe.toString());
			}
		} catch (Exception e) {
			System.out.println("Generic Exception " + e.toString());
		}
	}

	@Test
    public void statement_success_get_all() {
        System.out.println("@Test - statement_success_get_all");
		
        try {
			Statements allStatements = bankFeedsApi.getStatements(null, null, null, null, null);
			assert(allStatements.getItems().size() > 0);
			System.out.println("All Statments - Total: " + allStatements.getItems().size());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    }

	@Test
   	public void statement_success_get_1() {
        System.out.println("@Test - statement_success_get_1");

		try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();

			LocalDate stDate = LocalDate.of(year, lastMonth, day);				
			newStatement.setStartDate(stDate);			

			LocalDate endDate = LocalDate.of(year, lastMonth, day);				
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("150");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			FeedConnections fc = bankFeedsApi.getFeedConnections(null, null);
			newStatement.setFeedConnectionId(fc.getItems().get(2).getId().toString());
			
			StatementLine newStatementLine = new StatementLine();
			newStatementLine.setAmount("50");
			newStatementLine.setChequeNumber("123" + SampleData.loadRandomNum());
			newStatementLine.setDescription("My new line");
			newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine.setReference("Foobar" + SampleData.loadRandomNum());
			newStatementLine.setPayeeName("StarLord" + SampleData.loadRandomNum());
			newStatementLine.setTransactionId("1234" + SampleData.loadRandomNum());
			LocalDate postedDate = LocalDate.of(year, lastMonth, day);
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			
			arrayOfStatements.addItemsItem(newStatement);
			
			Map<String, String> params = null;
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements);
			
			String statementId = rStatements.getItems().get(0).getId();
			Statement oneStatement = bankFeedsApi.getStatement(statementId);	
			String actualStatus = oneStatement.getStatus().toString();
	
			// ASSERT
			assert(oneStatement.getStatementLineCount() > 0);
			assertThat(actualStatus, is(equalTo("PENDING")));

			System.out.println("Statement status: " + actualStatus);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
   	public void statement_success_create_then_get_statement_by_id() {
        System.out.println("@Test - statement_success_create_then_get_statement_by_id");

		// Success CREATE  and then GET single Statement
		try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();
			LocalDate stDate = LocalDate.of(year, lastMonth, 1);
			newStatement.setStartDate(stDate);
			LocalDate endDate = LocalDate.of(year, lastMonth, 15);
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("150");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			FeedConnections fc = bankFeedsApi.getFeedConnections(null,null);
			newStatement.setFeedConnectionId(fc.getItems().get(2).getId().toString());
			
			StatementLine newStatementLine = new StatementLine();
			newStatementLine.setAmount("50");
			newStatementLine.setChequeNumber("123" + SampleData.loadRandomNum());
			newStatementLine.setDescription("My new line");
			newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine.setReference("Foobar" + SampleData.loadRandomNum());
			newStatementLine.setPayeeName("StarLord" + SampleData.loadRandomNum());
			newStatementLine.setTransactionId("1234" + SampleData.loadRandomNum());
			LocalDate postedDate = LocalDate.of(year, lastMonth, day);
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			arrayOfStatements.addItemsItem(newStatement);
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements);
		
			Statement oneStatement = bankFeedsApi.getStatement(rStatements.getItems().get(0).getId());			
			String actualStatus = oneStatement.getStatus().toString();
	
			// ASSERT
			assert(oneStatement.getStatementLineCount() > 0);
			assertThat(actualStatus, is(equalTo("PENDING")));

			System.out.println("Statement status: " + actualStatus);			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

    @Test
   	public void statement_fail_create_statement_duplicate() {
        System.out.println("@Test - statement_fail_create_statement_duplicate");

		// FAIL
		try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();
			LocalDate stDate = LocalDate.of(year, lastMonth, 1);			
			newStatement.setStartDate(stDate);
			LocalDate endDate = LocalDate.of(year, lastMonth, 15);			
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("150");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			FeedConnections fc = bankFeedsApi.getFeedConnections(null,null);
			newStatement.setFeedConnectionId(fc.getItems().get(2).getId().toString());
			
			StatementLine newStatementLine = new StatementLine();
			newStatementLine.setAmount("50");
			newStatementLine.setChequeNumber("123");
			newStatementLine.setDescription("My new line");
			newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine.setReference("Foobar" );
			newStatementLine.setPayeeName("StarLord");
			newStatementLine.setTransactionId("1234");
			LocalDate postedDate = LocalDate.of(year, lastMonth, day);
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			arrayOfStatements.addItemsItem(newStatement);
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements);
			
			//DUPLICATE
			Statements rStatements2 = bankFeedsApi.createStatements(arrayOfStatements);
		
		} catch (XeroApiException e) {
			int actualCode = e.getResponseCode();
			assertThat(actualCode, is(equalTo(400)));
			try {
				TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
				Statements statementErrors =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);
				int errorCode = statementErrors.getItems().get(0).getErrors().get(0).getStatus();
				assertThat(errorCode, is(equalTo(409)));
				System.out.println("Error details: " + statementErrors.getItems().get(0).getErrors().get(0).getDetail());
			} catch (IOException ioe) {
				System.out.println("IO:" + ioe.toString());
			}
		} catch (Exception e) {
			System.out.println("Generic Exception " + e.toString());
		}
	}

	@Test
   	public void statement_fail_create_statement_check_num_toolong() {
        System.out.println("@Test - statement_fail_create_statement_check_num_toolong");

		// FAIL
		try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();

			LocalDate stDate = LocalDate.of(year, lastMonth, day);				
			newStatement.setStartDate(stDate);

			LocalDate endDate = LocalDate.of(year, lastMonth, day);				
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("150");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			FeedConnections fc = bankFeedsApi.getFeedConnections(null,null);
			newStatement.setFeedConnectionId(fc.getItems().get(2).getId().toString());
			
			StatementLine newStatementLine = new StatementLine();
			newStatementLine.setAmount("50");
			newStatementLine.setChequeNumber("1235678912345678912345678");
			newStatementLine.setDescription("My new line");
			newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine.setReference("Foobar" );
			newStatementLine.setPayeeName("StarLord");
			newStatementLine.setTransactionId("1234");
			LocalDate postedDate = LocalDate.of(year, lastMonth, day);
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			arrayOfStatements.addItemsItem(newStatement);
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements);
		
		} catch (XeroApiException e) {
			int actualCode = e.getResponseCode();
			assertThat(actualCode, is(equalTo(400)));
			try {
				TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
				Statements statementErrors =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);
				int errorCode = statementErrors.getItems().get(0).getErrors().get(0).getStatus();
				assertThat(errorCode, is(equalTo(400)));
				System.out.println("Error details: " + statementErrors.getItems().get(0).getErrors().get(0).getDetail());
			} catch (IOException ioe) {
				System.out.println("IO:" + ioe.toString());
			}
		} catch (Exception e) {
			System.out.println("Generic Exception " + e.toString());
		}
	}

	@Test
   	public void statement_fail_create_statement_bad_id() {
        System.out.println("@Test - statement_fail_create_statement_bad_id");

		// FAIL
		try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();

			LocalDate stDate = LocalDate.of(year, lastMonth, 1);
			newStatement.setStartDate(stDate);

			LocalDate endDate = LocalDate.of(year, lastMonth, 15);
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("150");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			// Bad ID
			newStatement.setFeedConnectionId("123");
			
			StatementLine newStatementLine = new StatementLine();
			newStatementLine.setAmount("50");
			newStatementLine.setChequeNumber("123");
			newStatementLine.setDescription("My new line");
			newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine.setReference("Foobar" );
			newStatementLine.setPayeeName("StarLord");
			newStatementLine.setTransactionId("1234");
			LocalDate postedDate = LocalDate.of(year, lastMonth, day);
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			arrayOfStatements.addItemsItem(newStatement);
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements);
			
		} catch (XeroApiException e) {
			int actualCode = e.getResponseCode();
			assertThat(actualCode, is(equalTo(400)));
			try {
				TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
				Statements statementErrors =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);
				int errorCode = statementErrors.getItems().get(0).getErrors().get(0).getStatus();
				assertThat(errorCode, is(equalTo(400)));
				System.out.println("Error details: " + statementErrors.getItems().get(0).getErrors().get(0).getDetail());
			} catch (IOException ioe) {
				System.out.println("IO:" + ioe.toString());
			}
		} catch (Exception e) {
			System.out.println("Generic Exception " + e.toString());
		}
	}	
}
