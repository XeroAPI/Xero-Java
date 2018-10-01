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

	@Before
	public void setUp() {
		config = JsonConfig.getInstance();
		// 30min OAUTH TOKEN/SECRET from org in approved region(UK)
		token = "69DZUHCL0CSSRCWRUWX2YVQCQSYBRO";
		tokenSecret = "YKEEZLX7XOCLQQZR1CVVILUEZY4PP1";

		//30min OAUTH TOKEN/SECRET from org in NON-approved region(US)
		badToken = "MWCNZ1WMN99JED5KPBHXGIU4IGTURA";
		badTokenSecret = "WDO4WFYIPSY5QBXCX2WNOTBQV3KVTO";


		apiClientForBankFeeds = new ApiClient(config.getBankFeedsUrl(),null,null,null);
		bankFeedsApi = new BankFeedsApi(apiClientForBankFeeds);
		bankFeedsApi.setOAuthToken(token, tokenSecret);
		params = null;
	}
   

   	@After
	public void tearDown() {
		config = null;
	}

	@Test
    public void test_create_feedconnection() {
        System.out.println("@Test - test_create_feedconnection");
		
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
			
			FeedConnections fc = bankFeedsApi.createFeedConnections(arrayFeedConnections, null);
			System.out.println(fc.toString());
			assert(fc.getItems().size() > 0);
			assert(fc.getItems().get(0).getAccountToken().equals(accountToken));
		
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    }

    @Test
    public void test_get_feedconnection_bad_token() {
        System.out.println("@Test - test_get_feedconnection_bad_token");
		
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
			FeedConnections fc = bankFeedsApi.createFeedConnections(arrayFeedConnections, null);
		
		} catch (Exception e) {
			try {
				TypeReference<Error> typeRef = new TypeReference<Error>() {};
				Error error =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);
				assert(error.getStatus().equals(403));
				System.out.println(error.getDetail());
			} catch (IOException ioe) {
				System.out.println("IO:" + ioe.toString());
			}
		}
    }

	@Test
    public void test_get_all_feedconnection() {
        System.out.println("@Test - test_get_all_feedconnection");
		
		try {
			FeedConnections fc = bankFeedsApi.getFeedConnections(null);
			assert(fc.getItems().size() > 0);
			System.out.println(fc.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    }

    @Test
    public void test_get_one_feedconnection() {
        System.out.println("@Test - test_get_one_feedconnection");
		
		try {
			FeedConnections fc = bankFeedsApi.getFeedConnections(null);
			FeedConnection oneFC = bankFeedsApi.getFeedConnection(fc.getItems().get(0).getId(),null);
			assert(!"".equals(oneFC.getAccountToken()));
			assert(!"".equals(oneFC.getAccountName()));
			assert(!"".equals(oneFC.getAccountType()));
			System.out.println(fc.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    }

   @Test
   public void test_delete_feedconnection() {
        System.out.println("@Test - test_delete_feedconnection");
        
        try {
			FeedConnections fc = bankFeedsApi.getFeedConnections(null);
			Integer idx = (fc.getItems().size()-1);

			// Create FeedConnection object
			FeedConnection feedConnectionOne = new FeedConnection();
			feedConnectionOne.setId(fc.getItems().get(idx).getId());
			// FeedConnection to Delete
			FeedConnections deleteFeedConnections = new FeedConnections();
			// Add FeedConnection Object
			deleteFeedConnections.addItemsItem(feedConnectionOne);
			// Call DELETE method
			FeedConnections deletedFeedConnection = bankFeedsApi.deleteFeedConnections(deleteFeedConnections,null);
			
			assert(deletedFeedConnection.getItems().size() > 0);
			//assertNull(deletedFeedConnection.getItems().get(0).getAccountToken());
			System.out.println(deletedFeedConnection.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
   }

   @Test
   public void test_create_statement() {
        System.out.println("@Test - test_create_statement");

        try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();
			LocalDate stDate = LocalDate.of(2017, 9, 01);
			newStatement.setStartDate(stDate);
			LocalDate endDate = LocalDate.of(2017, 9, 15);
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("300");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			FeedConnections fc = bankFeedsApi.getFeedConnections(null);
			newStatement.setFeedConnectionId(fc.getItems().get(2).getId().toString());
			
			StatementLine newStatementLine = new StatementLine();
			newStatementLine.setAmount("50");
			newStatementLine.setChequeNumber("123" + SampleData.loadRandomNum());
			newStatementLine.setDescription("My new line");
			newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine.setReference("Foobar" + SampleData.loadRandomNum());
			newStatementLine.setPayeeName("StarLord" + SampleData.loadRandomNum());
			newStatementLine.setTransactionId("1234" + SampleData.loadRandomNum());
			LocalDate postedDate = LocalDate.of(2017, 9, 05);
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			arrayOfStatements.addItemsItem(newStatement);
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements, params);
			
			assert(rStatements.getItems().size() > 0);
			
			System.out.println(rStatements.toString());
						
		} catch (Exception e) {
			System.out.println(e.toString());
		}
 
   }

   @Test
   public void test_create_2_statement() {
        System.out.println("@Test - test_create_2_statement");

		try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();
			LocalDate stDate = LocalDate.of(2017, 9, 01);
			newStatement.setStartDate(stDate);
			LocalDate endDate = LocalDate.of(2017, 9, 15);
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("300");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			FeedConnections fc = bankFeedsApi.getFeedConnections(null);
			newStatement.setFeedConnectionId(fc.getItems().get(2).getId().toString());
			
			StatementLine newStatementLine = new StatementLine();
			newStatementLine.setAmount("50");
			newStatementLine.setChequeNumber("123" + SampleData.loadRandomNum());
			newStatementLine.setDescription("My new line");
			newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine.setReference("Foobar" + SampleData.loadRandomNum());
			newStatementLine.setPayeeName("StarLord" + SampleData.loadRandomNum());
			newStatementLine.setTransactionId("1234" + SampleData.loadRandomNum());
			LocalDate postedDate = LocalDate.of(2017, 9, 05);
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			
			arrayOfStatements.addItemsItem(newStatement);
			
			Statement newStatement2 = new Statement();
			LocalDate stDate2 = LocalDate.of(2017, 9, 01);
			newStatement2.setStartDate(stDate2);
			LocalDate endDate2 = LocalDate.of(2017, 9, 15);
			newStatement2.endDate(endDate2);
			StartBalance stBalance2 = new StartBalance();
			stBalance2.setAmount("100");
			stBalance2.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement2.setStartBalance(stBalance2);
			
			EndBalance endBalance2 = new EndBalance();
			endBalance2.setAmount("300");
			endBalance2.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement2.endBalance(endBalance2);
			
			newStatement2.setFeedConnectionId(fc.getItems().get(9).getId().toString());
			
			StatementLine newStatementLine2 = new StatementLine();
			newStatementLine2.setAmount("50");
			newStatementLine2.setChequeNumber("123" + SampleData.loadRandomNum());
			newStatementLine2.setDescription("My new line");
			newStatementLine2.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine2.setReference("Foobar" + SampleData.loadRandomNum());
			newStatementLine2.setPayeeName("StarLord" + SampleData.loadRandomNum());
			newStatementLine2.setTransactionId("1234" + SampleData.loadRandomNum());
			LocalDate postedDate2 = LocalDate.of(2017, 9, 05);
			newStatementLine2.setPostedDate(postedDate2);
		
			StatementLines arrayStatementLines2 = new StatementLines();
			arrayStatementLines2.add(newStatementLine2);
			
			newStatement2.setStatementLines(arrayStatementLines2);
			
			arrayOfStatements.addItemsItem(newStatement2);
			
			Map<String, String> params = null;
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements, params);
			
			assert(rStatements.getItems().size() > 0);
			System.out.println(rStatements.getItems().toString());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
   	public void test_get_1_statement() {
        System.out.println("@Test - test_get_1_statement");

		try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();
			LocalDate stDate = LocalDate.of(2017, 9, 01);
			newStatement.setStartDate(stDate);
			LocalDate endDate = LocalDate.of(2017, 9, 15);
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("300");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			FeedConnections fc = bankFeedsApi.getFeedConnections(null);
			newStatement.setFeedConnectionId(fc.getItems().get(2).getId().toString());
			
			StatementLine newStatementLine = new StatementLine();
			newStatementLine.setAmount("50");
			newStatementLine.setChequeNumber("123" + SampleData.loadRandomNum());
			newStatementLine.setDescription("My new line");
			newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine.setReference("Foobar" + SampleData.loadRandomNum());
			newStatementLine.setPayeeName("StarLord" + SampleData.loadRandomNum());
			newStatementLine.setTransactionId("1234" + SampleData.loadRandomNum());
			LocalDate postedDate = LocalDate.of(2017, 9, 05);
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			
			arrayOfStatements.addItemsItem(newStatement);
			
			Map<String, String> params = null;
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements, params);
			
			String statementId = rStatements.getItems().get(0).getId();
			Statement oneStatement = bankFeedsApi.getStatement(statementId, params);
	
			assert(oneStatement.getStatementLineCount() > 0);
			System.out.println(oneStatement.toString());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
   	public void test_create_statement_duplicate() {
        System.out.println("@Test - test_create_statement_duplicate");

		// FAIL
		try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();
			LocalDate stDate = LocalDate.of(2017, 9, 01);
			newStatement.setStartDate(stDate);
			LocalDate endDate = LocalDate.of(2017, 9, 15);
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("300");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			FeedConnections fc = bankFeedsApi.getFeedConnections(null);
			newStatement.setFeedConnectionId(fc.getItems().get(2).getId().toString());
			
			StatementLine newStatementLine = new StatementLine();
			newStatementLine.setAmount("50");
			newStatementLine.setChequeNumber("123");
			newStatementLine.setDescription("My new line");
			newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine.setReference("Foobar" );
			newStatementLine.setPayeeName("StarLord");
			newStatementLine.setTransactionId("1234");
			LocalDate postedDate = LocalDate.of(2017, 9, 05);
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			arrayOfStatements.addItemsItem(newStatement);
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements, null);
			
			//DUPLICATE
			Statements rStatements2 = bankFeedsApi.createStatements(arrayOfStatements, null);
		} catch (Exception e) {
			try {
				TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
				Statements statementErrors =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);
				
				assert(statementErrors.getItems().get(0).getErrors().get(0).getStatus().equals(409));
		
				System.out.println(statementErrors.getItems().get(0).getErrors().get(0).getDetail());
				System.out.println();		
			} catch (IOException ioe) {
				System.out.println(ioe.toString());
			}
		}
	}

	@Test
   	public void test_create_statement_check_num_toolong() {
        System.out.println("@Test - test_create_statement_check_num_toolong");

		// FAIL
		try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();
			LocalDate stDate = LocalDate.of(2017, 9, 01);
			newStatement.setStartDate(stDate);
			LocalDate endDate = LocalDate.of(2017, 9, 15);
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("300");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			FeedConnections fc = bankFeedsApi.getFeedConnections(null);
			newStatement.setFeedConnectionId(fc.getItems().get(2).getId().toString());
			
			StatementLine newStatementLine = new StatementLine();
			newStatementLine.setAmount("50");
			newStatementLine.setChequeNumber("1235678912345678912345678");
			newStatementLine.setDescription("My new line");
			newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine.setReference("Foobar" );
			newStatementLine.setPayeeName("StarLord");
			newStatementLine.setTransactionId("1234");
			LocalDate postedDate = LocalDate.of(2017, 9, 05);
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			arrayOfStatements.addItemsItem(newStatement);
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements, null);
			
		} catch (Exception e) {
			try {
				TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
				Statements statementErrors =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);

				assert(statementErrors.getItems().get(0).getErrors().get(0).getStatus() > 0);		
				assertThat(statementErrors.getItems().get(0).getErrors().get(0).getStatus(), is(equalTo(400)));
				System.out.println(statementErrors.getItems().get(0).getErrors().get(0).getDetail());
				System.out.println(statementErrors.getItems().get(0).getErrors().get(0).getStatus());
	
			} catch (IOException ioe) {	
				System.out.println(ioe.toString());
			}
		}
	}

	@Test
   	public void test_create_statement_bad_id() {
        System.out.println("@Test - test_create_statement_bad_id");

		// FAIL
		try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();
			LocalDate stDate = LocalDate.of(2017, 9, 01);
			newStatement.setStartDate(stDate);
			LocalDate endDate = LocalDate.of(2017, 9, 15);
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("300");
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
			LocalDate postedDate = LocalDate.of(2017, 9, 05);
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			arrayOfStatements.addItemsItem(newStatement);
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements, null);
			
		} catch (Exception e) {
			try {
				TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
				Statements statementErrors =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);

				assert(statementErrors.getItems().get(0).getErrors().get(0).getStatus() > 0);		
				assertThat(statementErrors.getItems().get(0).getErrors().get(0).getStatus(), is(equalTo(400)));
				System.out.println(statementErrors.getItems().get(0).getErrors().get(0).getDetail());
				System.out.println(statementErrors.getItems().get(0).getErrors().get(0).getStatus());
	
			} catch (IOException ioe) {	
				System.out.println(ioe.toString());
			}
		}
	}	


	@Test
   	public void test_create_then_get_statement_by_id() {
        System.out.println("@Test - test_create_then_get_statement_by_id");


		// Success CREATE  and then GET single Statement
		try {
			Statements arrayOfStatements = new Statements();
			Statement newStatement = new Statement();
			LocalDate stDate = LocalDate.of(2017, 9, 01);
			newStatement.setStartDate(stDate);
			LocalDate endDate = LocalDate.of(2017, 9, 15);
			newStatement.endDate(endDate);
			StartBalance stBalance = new StartBalance();
			stBalance.setAmount("100");
			stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.setStartBalance(stBalance);
			
			EndBalance endBalance = new EndBalance();
			endBalance.setAmount("300");
			endBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatement.endBalance(endBalance);
			
			FeedConnections fc = bankFeedsApi.getFeedConnections(null);
			newStatement.setFeedConnectionId(fc.getItems().get(2).getId().toString());
			
			StatementLine newStatementLine = new StatementLine();
			newStatementLine.setAmount("50");
			newStatementLine.setChequeNumber("123" + SampleData.loadRandomNum());
			newStatementLine.setDescription("My new line");
			newStatementLine.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
			newStatementLine.setReference("Foobar" + SampleData.loadRandomNum());
			newStatementLine.setPayeeName("StarLord" + SampleData.loadRandomNum());
			newStatementLine.setTransactionId("1234" + SampleData.loadRandomNum());
			LocalDate postedDate = LocalDate.of(2017, 9, 05);
			newStatementLine.setPostedDate(postedDate);
		
			StatementLines arrayStatementLines = new StatementLines();
			arrayStatementLines.add(newStatementLine);
			
			newStatement.setStatementLines(arrayStatementLines);
			arrayOfStatements.addItemsItem(newStatement);
			Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements, params);
		
			Statement oneStatement = bankFeedsApi.getStatement(rStatements.getItems().get(0).getId(), params);
			assert(oneStatement.getStatementLineCount() > 0);
	
			System.out.println(oneStatement.toString());
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
