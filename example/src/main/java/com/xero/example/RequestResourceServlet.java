package com.xero.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.xero.api.XeroClient;
import com.xero.api.Config;
import com.xero.api.JsonConfig;
import com.xero.api.OAuthAccessToken;
import com.xero.api.XeroApiException;
import com.xero.model.*;

public class RequestResourceServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	//private Config config = Config.getInstance(); 
	private Config config = JsonConfig.getInstance();
	
	private String htmlString =  "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\">"
			+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css\" integrity=\"sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r\" crossorigin=\"anonymous\">"
			+ "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script>"
			+ "<div class=\"container\"><h1>Xero API - JAVA</h1>" 
			+ "<div class=\"form-group\">"
		  	+ "<a href=\"/\" class=\"btn btn-default\" type=\"button\">Logout</a>"
		  	+ "</div>"
			+ "<form action=\"./RequestResourceServlet\" method=\"post\">" 
			+ "<div class=\"form-group\">" 
		  	+ "<label for=\"object\">Create, Read, Update & Delete</label>"
		  	+ "<select name=\"object\" class=\"form-control\" id=\"object\">"
		  	+ "<option value=\"Accounts\" selected>Accounts</option>"
			+ "<option value=\"Attachments\">Attachments</option>"
		  	+ "<option value=\"BankTransactions\">BankTransactions</option>"
		  	+ "<option value=\"BankTransfers\">BankTransfers</option>"
		  	+ "<option value=\"BrandingThemes\">BrandingThemes</option>"
		  	+ "<option value=\"Contacts\">Contacts</option>"
		  	+ "<option value=\"ContactGroups\">ContactGroups</option>"
		  	+ "<option value=\"ContactGroupContacts\">ContactGroups Contacts</option>"
		  	+ "<option value=\"CreditNotes\">CreditNotes</option>"
		  	+ "<option value=\"Currencies\">Currencies</option>"
		  	+ "<option value=\"Employees\">Employees</option>"
		  	+ "<option value=\"ExpenseClaims\">ExpenseClaims</option>"
		  	+ "<option value=\"Invoices\">Invoices</option>"
		  	+ "<option value=\"InvoiceReminders\">InvoiceReminders</option>"
		  	+ "<option value=\"Items\">Items</option>"
		  	+ "<option value=\"Journals\">Journals</option>"
		  	+ "<option value=\"LinkedTransactions\">LinkedTransactions</option>"
		  	+ "<option value=\"ManualJournals\">ManualJournals</option>"
		  	+ "<option value=\"Organisations\">Organisations</option>"
		  	+ "<option value=\"Overpayments\">Overpayments</option>"
		  	+ "<option value=\"Payments\">Payments</option>"
		  	+ "<option value=\"Prepayments\">Prepayments</option>"
		  	+ "<option value=\"PurchaseOrders\">PurchaseOrders</option>"
		  	+ "<option value=\"Receipts\">Receipts</option>"
		  	+ "<option value=\"RepeatingInvoices\">RepeatingInvoices</option>"
		  	+ "<option value=\"Reports\">Reports</option>"
		  	+ "<option value=\"TaxRates\">TaxRates</option>"
		  	+ "<option value=\"TrackingCategories\">TrackingCategories</option>"
		  	+ "<option value=\"Users\">Users</option>"
		  	+ "<option value=\"Errors\">Errors</option>"
		  	+ "</select>"
		  	+ "</div>"
		  	+ "<div class=\"form-group\">"
		  	+ "<input class=\"btn btn-default\" type=\"submit\" value=\"submit\">"
		  	+ "</div>"
		  	+ "</form></div>";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		PrintWriter respWriter = response.getWriter();
		response.setStatus(200);
		response.setContentType("text/html"); 
		respWriter.println(htmlString);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter respWriter = response.getWriter();
		response.setStatus(200);
		response.setContentType("text/html"); 
		respWriter.println(htmlString);
		respWriter.println("<div class=\"container\"><hr>begin processing request<hr><div class=\"form-group\">");
		
		String object = request.getParameter("object");
		ArrayList<String> messages = new ArrayList<String>();
		
		// Get Xero API Resource - DEMONSTRATION ONLY get token from Cookie
		TokenStorage storage = new TokenStorage();
		String token = storage.get(request,"token");
		String tokenSecret = storage.get(request,"tokenSecret");
		
		if(storage.tokenIsNull(token)) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
		OAuthAccessToken refreshToken = new OAuthAccessToken(config);
		String tokenTimestamp = storage.get(request, "tokenTimestamp");
		if(config.getAppType().equals("PARTNER") && refreshToken.isStale(tokenTimestamp)) {
			System.out.println("Time for a refresh");

			refreshToken.setToken(storage.get(request, "token"));
			refreshToken.setTokenSecret(storage.get(request, "tokenSecret"));
			refreshToken.setSessionHandle(storage.get(request, "sessionHandle"));
			
			boolean success = refreshToken.build().execute();
			if (!success) {
				try {
					request.getRequestDispatcher("index.jsp").forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// DEMONSTRATION ONLY - Store in Cookie - you can extend TokenStorage
			// and implement the save() method for your database
			storage.save(response,refreshToken.getAll());
			token =  refreshToken.getToken();
			tokenSecret = refreshToken.getTokenSecret();
		}
		
		XeroClient client = new XeroClient();
		client.setOAuthToken(token, tokenSecret);
		
		SampleData data = new SampleData(client);
		
		if(object.equals("Accounts")) {
			
			/* ACCOUNT */
			try {
				List<Account> newAccount = client.createAccounts(SampleData.loadAccount().getAccount());
				messages.add("Create a new Account - Name : " + newAccount.get(0).getName() 
						+ " Description : " + newAccount.get(0).getDescription() + "");
				
				List<Account> accountWhere = client.getAccounts(null,"Type==\"BANK\"",null);
				if(accountWhere.size() > 0) {
					
					messages.add("Get a Account with WHERE clause - Name : " + accountWhere.get(0).getName() + "");
				} else {
					messages.add("Get a Account with WHERE clause - No Acccounts of Type BANK found");
				}

				List<Account> accountList = client.getAccounts();
				int num = SampleData.findRandomNum(accountList.size());
				messages.add("Get a random Account - Name : " + accountList.get(num).getName() + "");
			
				Account accountOne = client.getAccount(accountList.get(num).getAccountID());
				messages.add("Get a single Account - Name : " + accountOne.getName() + "");
				
				newAccount.get(0).setDescription("Monsters Inc.");
				newAccount.get(0).setStatus(null);
				List<Account> updateAccount = client.updateAccount(newAccount);
				messages.add("Update Account - Name : " + updateAccount.get(0).getName() 
						+ " Description : " + updateAccount.get(0).getDescription() + "");

				String status = client.deleteAccount(newAccount.get(0).getAccountID());
				messages.add("Delete Account - Name : " + newAccount.get(0).getName() 
						+ " result : " + status + "");
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
			}	

		} else if (object.equals("Attachments")) {
			
			/*  INVOICE ATTACHMENT as BYTE ARRAY */
			try {
				List<Invoice> newInvoice = client.createInvoices(SampleData.loadInvoice().getInvoice());
				messages.add("Create a new Invoice ID : " + newInvoice.get(0).getInvoiceID() + " - Reference : " +newInvoice.get(0).getReference());
				
				InputStream is = JsonConfig.class.getResourceAsStream("/helo-heros.jpg");
				byte[] bytes = IOUtils.toByteArray(is);
				
				String fileName = "sample.jpg";
				Attachment invoiceAttachment = client.createAttachment("Invoices",newInvoice.get(0).getInvoiceID(), fileName, "application/jpeg", bytes);
				messages.add("Attachment to Invoice complete - ID: " + invoiceAttachment.getAttachmentID());
				
				List<Attachment> getInvoiceAttachment = client.getAttachments("Invoices", newInvoice.get(0).getInvoiceID());
				messages.add("Get Attachment for Invoice - complete -attachment ID: " + getInvoiceAttachment.get(0).getAttachmentID());	
	
				System.out.println(getInvoiceAttachment.get(0).getFileName() + " --- " +getInvoiceAttachment.get(0).getMimeType());
				
				File f = new File("./");
				String dirPath =  f.getCanonicalPath();
				String contentPath = client.getAttachmentContent("Invoices", newInvoice.get(0).getInvoiceID(),getInvoiceAttachment.get(0).getFileName(),getInvoiceAttachment.get(0).getMimeType(),dirPath);
				messages.add("Get Attachment content - save to server - location: " + contentPath);				
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
			}	
		} else if (object.equals("BankTransactions")) {
		
			/* BANK TRANSACTION */
			try {
				List<Account> accountWhere = client.getAccounts(null,"Type==\"BANK\"",null);
				if(accountWhere.size() > 0) {
					
					List<BankTransaction> newBankTransaction = client.createBankTransactions(SampleData.loadBankTransaction().getBankTransaction());
					messages.add("Create a new Bank Transaction - ID : " +newBankTransaction.get(0).getBankTransactionID() + "");
				
					List<BankTransaction> BankTransactionWhere = client.getBankTransactions(null,"Status==\"AUTHORISED\"",null,null);
					messages.add("Get a BankTransaction with WHERE clause - ID : " + BankTransactionWhere.get(0).getBankTransactionID() + "");
					
					List<BankTransaction> BankTransactionList = client.getBankTransactions();
					int num = SampleData.findRandomNum(BankTransactionList.size());
					messages.add("Get a random BankTransaction - ID : " + BankTransactionList.get(num).getBankTransactionID() + "");
				
					BankTransaction BankTransactionOne = client.getBankTransaction(BankTransactionList.get(num).getBankTransactionID());
					messages.add("Get a single BankTransaction - ID : " + BankTransactionOne.getBankTransactionID());
					
					newBankTransaction.get(0).setReference("My Updated Reference");
					List<BankTransaction> updatedBankTransaction = client.updateBankTransactions(newBankTransaction);
					messages.add("Updated a new Bank Transaction - ID : " +updatedBankTransaction.get(0).getBankTransactionID() + "");
					
				} else {
					messages.add("Please create a Bank Acccount before using the BankTransaction Endpoint");
				}

			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("BankTransfers")) {
			
			/* BANK TRANSFER */
			try {
				List<Account> accountWhere = client.getAccounts(null,"Type==\"BANK\"",null);
				if(accountWhere.size() > 0) {
					
					List<BankTransfer> bts = SampleData.loadBankTransfer().getBankTransfer();
					if (bts.size() > 0) {
						List<BankTransfer> newBankTransfer = client.createBankTransfers(SampleData.loadBankTransfer().getBankTransfer());
						messages.add("Create a new Bank Transfer - ID : " +newBankTransfer.get(0).getBankTransferID());
					} else {
						messages.add("Can not create a new Bank Transfer without 2 Bank Accounts"); 
					}
					
					List<BankTransfer> BankTransferWhere = client.getBankTransfers(null,"Amount>Decimal(1.00)",null);
					if(BankTransferWhere.size() > 0) {
						messages.add("Get a BankTransfer with WHERE clause - ID : " + BankTransferWhere.get(0).getBankTransferID());
					}
					
					List<BankTransfer> BankTransferList = client.getBankTransfers();
					if(BankTransferList.size() > 0) {
						int num3 = SampleData.findRandomNum(BankTransferList.size());
						messages.add("Get a random BankTransfer - ID : " + BankTransferList.get(num3).getBankTransferID());
					
						BankTransfer BankTransferOne = client.getBankTransfer(BankTransferList.get(num3).getBankTransferID());
						messages.add("Get a single BankTransfer - ID : " + BankTransferOne.getBankTransferID());
					} else {
						messages.add("No Bank Transfers Found ");
					}
				} else {
					messages.add("Please create a Bank Acccount before using the BankTransfer Endpoint");
				}
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
			}	
		} else if (object.equals("BrandingThemes")) {
				
			/* BRANDING THEME */
			try {
				List<BrandingTheme> newBrandingTheme = client.getBrandingThemes();
				messages.add("Get a Branding Theme - Name : " + newBrandingTheme.get(0).getName());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
			}	
			
		} else if (object.equals("Contacts")) {		
			
			/* CONTACT */
			try {
				List<Contact> newContact = client.createContact(SampleData.loadContact().getContact());
				messages.add("Create a new Contact - Name : " + newContact.get(0).getName() + " Email : " + newContact.get(0).getEmailAddress());
				
				List<Contact> ContactWhere = client.getContacts(null,"ContactStatus==\"ACTIVE\"",null,null);
				messages.add("Get a Contact with WHERE clause - ID : " + ContactWhere.get(0).getContactID());
				
				List<Contact> ContactList = client.getContacts();
				int num4 = SampleData.findRandomNum(ContactList.size());
				messages.add("Get a random Contact - ID : " + ContactList.get(num4).getContactID());
			
				Contact ContactOne = client.getContact(ContactList.get(num4).getContactID());
				messages.add("Get a single Contact - ID : " + ContactOne.getContactID());
			 	
				newContact.get(0).setEmailAddress("sid.maestre+barney@xero.com");
				List<Contact> updateContact = client.updateContact(newContact);
				messages.add("Update Contact - Name : " + updateContact.get(0).getName() + " email : " + updateContact.get(0).getEmailAddress());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
			}	
		} else if (object.equals("ContactGroups")) {
		
			/* CONTACT GROUP  */	
			try {
				List<ContactGroup> newContactGroup = client.createContactGroups(SampleData.loadContactGroup().getContactGroup());
				messages.add("Create a new Contact Group - ID : " + newContactGroup.get(0).getContactGroupID());
				
				List<ContactGroup> newContactGroup2 = client.createContactGroups(SampleData.loadContactGroup().getContactGroup());
				messages.add("Create a new Contact Group 2 - ID : " + newContactGroup2.get(0).getContactGroupID());
				
				List<ContactGroup> ContactGroupWhere = client.getContactGroups(null,"Status==\"ACTIVE\"",null);
				messages.add("Get a ContactGroup with WHERE clause - ID : " + ContactGroupWhere.get(0).getContactGroupID());
				
				List<ContactGroup> ContactGroupList = client.getContactGroups();
				int num = SampleData.findRandomNum(ContactGroupList.size());
				messages.add("Get a random ContactGroup - ID : " + ContactGroupList.get(num).getContactGroupID());
				
				ContactGroup ContactGroupOne = client.getContactGroup(ContactGroupList.get(num).getContactGroupID());
				messages.add("Get a single ContactGroup - ID : " + ContactGroupOne.getContactGroupID());
						
				newContactGroup.get(0).setName("My Updated Group-" + SampleData.loadRandomNum());
				List<ContactGroup> updateContactGroup = client.updateContactGroup(newContactGroup);
				messages.add("Update Contact Group - ID : " + updateContactGroup.get(0).getContactGroupID() + " - Name: " + updateContactGroup.get(0).getName());
				
				List<ContactGroup> deleteContactGroup = client.deleteContactGroup(ContactGroupList.get(num));
				messages.add("Delete Contact Group - Deleted : " + deleteContactGroup.get(0).getContactGroupID());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}	
		} else if (object.equals("ContactGroupContacts")) {
			
			/* CONTACT GROUP  */	
			try {
				List<ContactGroup> newContactGroup = client.createContactGroups(SampleData.loadContactGroup().getContactGroup());
				messages.add("Create a new Contact Group - ID : " + newContactGroup.get(0).getContactGroupID());
				
				ArrayOfContact arrayContact = new ArrayOfContact();
				arrayContact.getContact().add(SampleData.loadSingleContact());
				List<Contact> newContactGroupContacts = client.createContactGroupContacts(arrayContact.getContact(),newContactGroup.get(0).getContactGroupID());
				messages.add("Add Contacts to Contact Group = ContactId : " + newContactGroupContacts.get(0).getContactID());
				
				String deleteSingleContactStatus = client.deleteSingleContactFromContactGroup(newContactGroup.get(0).getContactGroupID(),arrayContact.getContact().get(0).getContactID());
				messages.add("Delete Single Contact from Group - Deleted Status: " + deleteSingleContactStatus);
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}							
		} else if (object.equals("CreditNotes")) {
		
			/* CREDIT NOTE */
			try {
				List<CreditNote> newCreditNote = client.createCreditNotes(SampleData.loadCreditNote().getCreditNote());
				messages.add("Create a new Credit Note - ID: " + newCreditNote.get(0).getCreditNoteID());
			
				// GET PDF of CREDIT NOTE
				File f = new File("./");
				String dirPath =  f.getCanonicalPath();
				String fileSavePath = client.getCreditNotePdf(newCreditNote.get(0).getCreditNoteID(),dirPath);
				messages.add("Get a PDF copy of Credit Note - save it here: " + fileSavePath);
				//System.out.println(fileSavePath);
					
				List<CreditNote> CreditNoteWhere = client.getCreditNotes(null,"Status==\"DRAFT\"",null);
				if(CreditNoteWhere.size() > 0) {
					messages.add("Get a CreditNote with WHERE clause - ID: " + CreditNoteWhere.get(0).getCreditNoteID());
				}
				
				List<CreditNote> CreditNotePage = client.getCreditNotes(null,"Status==\"DRAFT\"",null,"1");
				if(CreditNotePage.size() > 0) {
					messages.add("Get a CreditNote with PAGE=1 clause - ID: " + CreditNoteWhere.get(0).getCreditNoteID());
				}
				
				List<CreditNote> CreditNoteList = client.getCreditNotes();
				int num = SampleData.findRandomNum(CreditNoteList.size());
				messages.add("Get a random CreditNote - ID : " + CreditNoteList.get(num).getCreditNoteID());
						
				CreditNote CreditNoteOne = client.getCreditNote(CreditNoteList.get(num).getCreditNoteID());
				messages.add("Get a single CreditNote - ID : " + CreditNoteOne.getCreditNoteID());
					
				newCreditNote.get(0).setReference("My updated Credit Note");
				List<CreditNote> updateCreditNote = client.updateCreditNote(newCreditNote);
				messages.add("Update CreditNote - ID: " + updateCreditNote.get(0).getCreditNoteID() + " - Reference: " + updateCreditNote.get(0).getReference());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
			}	
		} else if (object.equals("Currencies")) {

			/* CURRENCY  */
			try {
				List<Currency> newCurrency = client.getCurrencies();
				messages.add("GET a Currency - Description: " + newCurrency.get(0).getDescription());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
			}	
		} else if (object.equals("Employees")) {

			/*  EMPLOYEE */
			try {
				List<Employee> newEmployee = client.createEmployees(SampleData.loadEmployee().getEmployee());
				messages.add("Create a new Employee - ID: " + newEmployee.get(0).getEmployeeID());
				
				List<Employee> EmployeeWhere = client.getEmployees(null,"Status==\"ACTIVE\"",null);
				messages.add("Get a Employee with WHERE clause - FirstName : " + EmployeeWhere.get(0).getFirstName());
				
				List<Employee> EmployeeList = client.getEmployees();
				int num6 = SampleData.findRandomNum(EmployeeList.size());
				messages.add("Get a random Employee - FirstName : " + EmployeeList.get(num6).getFirstName());
			
				Employee EmployeeOne = client.getEmployee(EmployeeList.get(num6).getEmployeeID());
				messages.add("Get a single Employee - FirstName : " + EmployeeOne.getFirstName());
				
				newEmployee.get(0).setFirstName("David");
				newEmployee.get(0).setStatus(null);
				List<Employee> updateEmployee = client.updateEmployee(newEmployee);
				messages.add("Update the Employee - FirstName : " + updateEmployee.get(0).getFirstName() + " - LastName : " + updateEmployee.get(0).getLastName());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
			}	
		} else if (object.equals("ExpenseClaims")) {

			/*  EXPENSE CLAIM */
			try {
				List<ExpenseClaim> newExpenseClaim = client.createExpenseClaims(SampleData.loadExpenseClaim().getExpenseClaim());
				messages.add("Create a new Expense Claim - ID : " + newExpenseClaim.get(0).getExpenseClaimID() + " Status : " + newExpenseClaim.get(0).getStatus());
				
				List<ExpenseClaim> ExpenseClaimWhere = client.getExpenseClaims(null,"AmountDue>Decimal(1.00)",null);
				messages.add("Get a ExpenseClaim with WHERE clause - ID" + ExpenseClaimWhere.get(0).getExpenseClaimID());
				
				List<ExpenseClaim> ExpenseClaimList = client.getExpenseClaims();
				int num = SampleData.findRandomNum(ExpenseClaimList.size());
				messages.add("Get a random ExpenseClaim - ID : " + ExpenseClaimList.get(num).getExpenseClaimID());
			
				ExpenseClaim ExpenseClaimOne = client.getExpenseClaim(ExpenseClaimList.get(num).getExpenseClaimID());
				messages.add("Get a single ExpenseClaim - ID : " + ExpenseClaimOne.getExpenseClaimID());
				
				newExpenseClaim.get(0).setStatus(ExpenseClaimStatus.AUTHORISED);;
				List<ExpenseClaim> updateExpenseClaim = client.updateExpenseClaim(newExpenseClaim);
				messages.add("Update the ExpenseClaim - ID : " + updateExpenseClaim.get(0).getExpenseClaimID() + " Status : " + updateExpenseClaim.get(0).getStatus());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
			}	
		} else if (object.equals("Invoices")) {
				
			/*  INVOICE */
			try {
				List<Invoice> newInvoice = client.createInvoices(SampleData.loadInvoice().getInvoice());
				newInvoice.get(0).setReference("Just Created my Ref.");
				messages.add("Create a new Invoice ID : " + newInvoice.get(0).getInvoiceID() + " - Reference : " +newInvoice.get(0).getReference());
				
				// GET PDF of INVOICE
				File f = new File("./");
				String dirPath =  f.getCanonicalPath();
				String fileSavePath = client.getInvoicePdf(newInvoice.get(0).getInvoiceID(),dirPath);
				messages.add("Get a PDF copy of Invoice - save it here: " + fileSavePath);	
				
				List<Invoice> InvoiceWhere = client.getInvoices(null,"Status==\"DRAFT\"",null,null,null);
				messages.add("Get a Invoice with WHERE clause - InvNum : " + InvoiceWhere.get(0).getInvoiceID());
				
				// Set If Modified Since in last 24 hours
				Date date = new Date();
				Calendar cal = Calendar.getInstance();
			    cal.setTime(date);
			    cal.add(Calendar.DAY_OF_MONTH, -1);
			    
			    List<Invoice> InvoiceList24hour = client.getInvoices(cal.getTime(),null,null,null,null);
				messages.add("How many invoices modified in last 24 hours?: " + InvoiceList24hour.size());
				
				List<Invoice> InvoiceList = client.getInvoices();
				int num7 = SampleData.findRandomNum(InvoiceList.size());
				messages.add("Get a random Invoice - InvNum : " + InvoiceList.get(num7).getInvoiceID());
				
				Invoice InvoiceOne = client.getInvoice(InvoiceList.get(num7).getInvoiceID());
				messages.add("Get a single Invoice - InvNum : " + InvoiceOne.getInvoiceID());
				
				OnlineInvoice OnlineInvoice = client.getOnlineInvoice(InvoiceList.get(num7).getInvoiceID());
				messages.add("Get a Online Invoice -  : " + OnlineInvoice.getOnlineInvoiceUrl());
		
				String ids = InvoiceList.get(0).getInvoiceID() + "," + InvoiceList.get(1).getInvoiceID();
				
				List<Invoice> InvoiceMultiple = client.getInvoices(null,null,null,null,ids);
				messages.add("Get a Muliple Invoices by ID filter : " + InvoiceMultiple.size());
				
				newInvoice.get(0).setReference("Just Updated APRIL my Ref.");
				newInvoice.get(0).setStatus(null);
				List<Invoice> updateInvoice = client.updateInvoice(newInvoice);
				messages.add("Update the Invoice - InvNum : " + updateInvoice.get(0).getInvoiceID() + " - Reference : " + updateInvoice.get(0).getReference());
			
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
			
		} else if (object.equals("InvoiceReminders")) {
		
			/* INVOICE REMINDER */
			try {
				List<InvoiceReminder> newInvoiceReminder = client.getInvoiceReminders();
				messages.add("Get a Invoice Reminder - Is Enabled: " + newInvoiceReminder.get(0).isEnabled() );
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Items")) {
	
			/* ITEM */
			try {
				List<Item> newItem = client.createItems(SampleData.loadItem().getItem());
				messages.add("Create a new Item - ID : " + newItem.get(0).getItemID());
				
				List<Item> ItemWhere = client.getItems(null,"IsSold==true",null);
				messages.add("Get a Item with WHERE clause - Name : " + ItemWhere.get(0).getName());
				
				List<Item> ItemList = client.getItems();
				int num = SampleData.findRandomNum(ItemList.size());
				messages.add("Get a random Item - Name : " + ItemList.get(num).getName());
			
				Item ItemOne = client.getItem(ItemList.get(num).getItemID());
				messages.add("Get a single Item - Name : " + ItemOne.getName());	
				
				newItem.get(0).setDescription("My Updated Description");
				newItem.get(0).setStatus(null);
				List<Item> updateItem = client.updateItem(newItem);
				messages.add("Update the Item - Name : " + updateItem.get(0).getName() + " - Description : " + updateItem.get(0).getDescription());
			
				String status = client.deleteItem(newItem.get(0).getItemID());
				messages.add("Delete a new Item - Delete result: " + status);

			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Journals")) {
		
			/* JOURNAL */
			try {
				List<Journal> newJournal = client.getJournals();
				messages.add("Get a Journal - Number : " + newJournal.get(0).getJournalNumber() + " - ID: " + newJournal.get(0).getJournalID());
			
				List<Journal> newJournalOffset = client.getJournals(null,"10",true);
				messages.add("Get a Journal - Number : " + newJournalOffset.get(0).getJournalNumber() + " - ID: " + newJournalOffset.get(0).getJournalID());
			
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("LinkedTransactions")) {

			/* LINKED TRANSACTION */
			try {
				List<LinkedTransaction> newLinkedTransaction = client.createLinkedTransactions(SampleData.loadLinkedTransaction().getLinkedTransaction());
				System.out.println(newLinkedTransaction.get(0).getLinkedTransactionID());
				messages.add("Create a new LinkedTransaction -  Id:" + newLinkedTransaction.get(0).getContactID());
				
				List<LinkedTransaction> LinkedTransactionWhere = client.getLinkedTransactions(null,"Status==\"BANK\"",null,null);
				messages.add("Get a LinkedTransaction with WHERE clause - ID : " + LinkedTransactionWhere.get(0).getLinkedTransactionID());
				
				List<LinkedTransaction> LinkedTransactionList = client.getLinkedTransactions();
				int num = SampleData.findRandomNum(LinkedTransactionList.size());
				messages.add("Get a random LinkedTransaction - ID : " + LinkedTransactionList.get(num).getLinkedTransactionID());
			
				LinkedTransaction LinkedTransactionOne = client.getLinkedTransaction(LinkedTransactionList.get(num).getLinkedTransactionID());
				messages.add("Get a single LinkedTransaction - ID : " + LinkedTransactionOne.getLinkedTransactionID());
				
				List<Contact> ContactList2 = client.getContacts();
				int num2 = SampleData.findRandomNum(ContactList2.size());
				newLinkedTransaction.get(0).setContactID(ContactList2.get(num2).getContactID());
				List<LinkedTransaction> updateLinkedTransaction = client.updateLinkedTransaction(newLinkedTransaction);
				messages.add("Update the LinkedTransaction - ID : " + updateLinkedTransaction.get(0).getLinkedTransactionID() + " - Status : " + updateLinkedTransaction.get(0).getStatus());
					
				String status = client.deleteLinkedTransaction(newLinkedTransaction.get(0).getLinkedTransactionID());
				messages.add("Delete a new LinkedTransaction - Delete result: " + status);

			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}

		} else if (object.equals("ManualJournals")) {

			/* MANUAL JOURNAL */
				try {
				List<ManualJournal> newManualJournal = client.createManualJournals(SampleData.loadManualJournal().getManualJournal());
				messages.add("Create a new Manual Journal - ID : " + newManualJournal.get(0).getManualJournalID() + " - Narration : " + newManualJournal.get(0).getNarration());
				
				List<ManualJournal> ManualJournalWhere = client.getManualJournals(null,"Status==\"DRAFT\"",null,null);
				if (ManualJournalWhere.size() > 0) {
					messages.add("Get a ManualJournal with WHERE clause - Narration : " + ManualJournalWhere.get(0).getNarration());
				} else {
					messages.add("Get a ManualJournal with WHERE clause - No Manual Journal DRAFT found");
				}
				
				List<ManualJournal> ManualJournalList = client.getManualJournals();
				int num8 = SampleData.findRandomNum(ManualJournalList.size());
				messages.add("Get a random ManualJournal - Narration : " + ManualJournalList.get(num8).getNarration());
			
				ManualJournal ManualJournalOne = client.getManualJournal(ManualJournalList.get(num8).getManualJournalID());
				messages.add("Get a single ManualJournal - Narration : " + ManualJournalOne.getNarration());
				
				newManualJournal.get(0).setNarration("My Updated Narration");
				newManualJournal.get(0).setStatus(null);
				List<ManualJournal> updateManualJournal = client.updateManualJournal(newManualJournal);
				messages.add("Update the ManualJournal - ID : " + updateManualJournal.get(0).getManualJournalID() + " - Narration : " + updateManualJournal.get(0).getNarration());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Organisations")) {

			/* Organisation */
			try {
				List<Organisation> newOrganisation = client.getOrganisations();
				messages.add("Get a Organisation - Name : " + newOrganisation.get(0).getName());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Overpayments")) {

			/* OVERPAYMENT */
			try {
				List<Account> accountWhere = client.getAccounts(null,"Type==\"BANK\"",null);
				if(accountWhere.size() > 0) {
					BankTransaction bt = SampleData.loadNewlyCreatedOverpayment();
					List<Overpayment> newOverpayment = client.getOverpayments();
					if (newOverpayment.size() > 0) {
						messages.add("Get a Overpayment - ID" + newOverpayment.get(0).getOverpaymentID());
					}
					
					List<Allocation> newAllocation = client.createOverpaymentAllocations(SampleData.loadAllocation().getAllocation(),bt.getOverpaymentID());
					messages.add("Create a new Overpayment and Allocate - Applied Amount : " + newAllocation.get(0).getAppliedAmount());
				} else {
					messages.add("Please create a Bank Acccount before using the Overpayment Endpoint");
				}

			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Payments")) {

			/* Payment 	*/
			try {
				List<Account> accountWhere = client.getAccounts(null,"Type==\"BANK\"",null);
				if(accountWhere.size() > 0) {
					
					List<Payment> newPayment = client.createPayments(SampleData.loadPayment().getPayment());
					messages.add("Create a new Payment - ID : " + newPayment.get(0).getPaymentID() + " : " + newPayment.get(0).getAmount());
					
					List<Payment> PaymentWhere = client.getPayments(null,"Status==\"AUTHORISED\"",null);
					messages.add("Get a Payment with WHERE clause - ID : " + PaymentWhere.get(0).getPaymentID());
					
					List<Payment> PaymentList = client.getPayments();
					int num = SampleData.findRandomNum(PaymentList.size());
					messages.add("Get a random Payment - ID : " + PaymentList.get(num).getPaymentID());
				
					Payment PaymentOne = client.getPayment(PaymentList.get(num).getPaymentID());
					messages.add("Get a single Payment - ID : " + PaymentOne.getPaymentID());
					
					Payment deletePayment = new Payment();
					deletePayment.setPaymentID(newPayment.get(0).getPaymentID());
					deletePayment.setStatus(PaymentStatus.DELETED);
					ArrayOfPayment aPayment = new ArrayOfPayment();
					aPayment.getPayment().add(deletePayment);
					List<Payment> removedPayment = client.deletePayment(aPayment.getPayment());
					messages.add("Delete the Payment - ID : " + removedPayment.get(0).getPaymentID());
				} else {
					messages.add("Please create a Bank Acccount before trying to Apply a Payment to Account Type Bank");
				}

			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Prepayments")) {

			/* PREPAYMENT */
			try {
				List<Account> accountWhere = client.getAccounts(null,"Type==\"BANK\"",null);
				if(accountWhere.size() > 0) {
					BankTransaction bt = SampleData.loadNewlyCreatedPrepayment();
					
					List<Prepayment> newPrepayment = client.getPrepayments();
					messages.add("Get a existing Prepayment - ID : " + newPrepayment.get(0).getPrepaymentID() + " : " + newPrepayment.get(0).getTotal());
					
					List<Allocation> newAllocation = client.createPrepaymentAllocations(SampleData.loadAllocation().getAllocation(),bt.getPrepaymentID());
					messages.add("Create a new Prepayment and Allocate - Applied Amount : " + newAllocation.get(0).getAppliedAmount());
				} else {
					messages.add("Please create a Bank Acccount before using the Prepayment Endpoint");
				}
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("PurchaseOrders")) {

			/* PURCHASE ORDERS */
			try {
				List<PurchaseOrder> newPurchaseOrder = client.createPurchaseOrders(SampleData.loadPurchaseOrder().getPurchaseOrder());
				messages.add("Create a new PurchaseOrder - ID : " + newPurchaseOrder.get(0).getPurchaseOrderID() + " - Reference :" + newPurchaseOrder.get(0).getReference());
				
				// GET PDF of PURCHASE ORDER
				File f = new File("./");
				String dirPath =  f.getCanonicalPath();
				String fileSavePath = client.getPurchaseOrderPdf(newPurchaseOrder.get(0).getPurchaseOrderID(),dirPath);
				messages.add("Get a PDF copy of PurchaseOrder - save it here: " + fileSavePath);
				System.out.println(fileSavePath);
				
				List<PurchaseOrder> PurchaseOrderWhere = client.getPurchaseOrders(null,"Status==\"DRAFT\"",null,null);
				messages.add("Get a PurchaseOrder with WHERE clause - ID : " + PurchaseOrderWhere.get(0).getPurchaseOrderID());
				
				List<PurchaseOrder> PurchaseOrderList = client.getPurchaseOrders();
				int num = SampleData.findRandomNum(PurchaseOrderList.size());
				messages.add("Get a random PurchaseOrder - ID : " + PurchaseOrderList.get(num).getPurchaseOrderID());
			
				PurchaseOrder PurchaseOrderOne = client.getPurchaseOrder(PurchaseOrderList.get(num).getPurchaseOrderID());
				messages.add("Get a single PurchaseOrder - ID : " + PurchaseOrderOne.getPurchaseOrderID());
				
				newPurchaseOrder.get(0).setReference("My Updated Reference");
				newPurchaseOrder.get(0).setStatus(null);
				List<PurchaseOrder> updatePurchaseOrder = client.updatePurchaseOrder(newPurchaseOrder);
				messages.add("Update the PurchaseOrder - ID : " + updatePurchaseOrder.get(0).getPurchaseOrderID() + " - Reference:" + updatePurchaseOrder.get(0).getReference());

			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Receipts")) {
	
			/* RECEIPTS */
			try {
				List<Receipt> newReceipt = client.createReceipts(SampleData.loadReceipt().getReceipt());
				messages.add("Create a new Receipt - ID : " + newReceipt.get(0).getReceiptID() + " - Reference:" + newReceipt.get(0).getReference());
				
				List<Receipt> ReceiptWhere = client.getReceipts(null,"Status==\"DRAFT\"",null);
				messages.add("Get a Receipt with WHERE clause - ID : " + ReceiptWhere.get(0).getReceiptID());
				
				List<Receipt> ReceiptList = client.getReceipts();
				int num = SampleData.findRandomNum(ReceiptList.size());
				messages.add("Get a random Receipt - ID : " + ReceiptList.get(num).getReceiptID());
			
				Receipt ReceiptOne = client.getReceipt(ReceiptList.get(num).getReceiptID());
				messages.add("Get a single Receipt - ID : " + ReceiptOne.getReceiptID());
				
				newReceipt.get(0).setReference("You'll get the answer NEXT Saturday");
				newReceipt.get(0).setStatus(null);
				List<Receipt> updateReceipt = client.updateReceipt(newReceipt);
				messages.add("Update the Receipt - ID : " + updateReceipt.get(0).getReceiptID() + " - Reference:" + updateReceipt.get(0).getReference());

			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}							
		} else if (object.equals("RepeatingInvoices")) {

	        /* REPEATING INVOICE */
			try {
				List<RepeatingInvoice> newRepeatingInvoice = client.getRepeatingInvoices();
				if (newRepeatingInvoice.size() > 0 ){
					messages.add("Get a Repeating Invoice - ID " + newRepeatingInvoice.get(0).getRepeatingInvoiceID());
				} else {
					messages.add("No Repeating Invoices Exists");
				}
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Reports")) {

			/* REPORTS */
			try {
				Report newReport = client.getReport("BalanceSheet",null,null);
				messages.add("Get a Reports - " + newReport.getReportID() + " : " + newReport.getReportDate());
				
				List<Contact> AgedPayablesByContactList = client.getContacts();
				int num20 = SampleData.findRandomNum(AgedPayablesByContactList.size());
				
				Report newReportAgedPayablesByContact = client.getReportAgedPayablesByContact(AgedPayablesByContactList.get(num20).getContactID(), null, null, null, "1/1/2016", "1/1/2017");
				messages.add("Get Aged Payables By Contact Reports for " +  AgedPayablesByContactList.get(num20).getName() + " - Report ID " + newReportAgedPayablesByContact.getReportID() );
									
				Report newReportAgedReceivablesByContact = client.getReportAgedReceivablesByContact(AgedPayablesByContactList.get(num20).getContactID(), null, null, null, "1/1/2016", "1/1/2017");
				messages.add("Get Aged Receivables By Contact Reports for " +  AgedPayablesByContactList.get(num20).getName() + " - Report ID " + newReportAgedReceivablesByContact.getReportID() );
				
				Report newReportBalanceSheet = client.getReportBalanceSheet(null, null, "3/3/2017", null, null, true, false);
				messages.add("Get Balance Sheet Report on " +  newReportBalanceSheet.getReportDate() + " - Name: " + newReportBalanceSheet.getReportTitles().getReportTitle().get(1).toString() );
							
				List<Account> accountForBankStatement = client.getAccounts(null,"Type==\"BANK\"",null);
				if(accountForBankStatement.size() > 0) {
					Report newReportBankStatement = client.getReportBankStatement(accountForBankStatement.get(0).getAccountID(), null, null, "10/1/2016","1/1/2017");
					messages.add("Get Bank Statement " +  newReportBankStatement.getReportDate() + " - Name: " + newReportBankStatement.getReportName() );
				} else {
					messages.add("No Bank Account found - can not run Bank Statement Report");
				}
				
				Report newReportBudgetSummary = client.getReportBudgetSummary(null, null, "1/1/2017", 3, 1);				
				messages.add("Get Budget Summary Report on " +  newReportBudgetSummary.getReportDate() + " - Name: " + newReportBudgetSummary.getReportName() );
				
				Report newExecutiveSummary = client.getExecutiveSummary(null, null,"1/1/2017");			
				messages.add("Get Executive Summary Report on " +  newExecutiveSummary.getReportDate() + " - Name: " + newExecutiveSummary.getReportName() );
	
				Report newReportProfitLoss = client.getReportProfitLoss(null,null, "9/1/2016", "1/1/2017", null, null, null, null, true, false);		
				messages.add("Get Profit Loss Report on " +  newReportProfitLoss.getReportDate() + " - Name: " + newReportProfitLoss.getReportName() );
			
				Report newTrialBalance = client.getReportTrialBalance("9/1/2016", true);		
				messages.add("Get Trial Balance Report on " +  newTrialBalance.getReportDate() + " - Name: " + newTrialBalance.getReportName() );
			
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("TaxRates")) {

			/* TAX RATE   */
			try {
				List<TaxRate> newTaxRate = client.createTaxRates(SampleData.loadTaxRate().getTaxRate());
				messages.add("Create a new TaxRate - Name : " + newTaxRate.get(0).getName());
				
				List<TaxRate> TaxRateWhere = client.getTaxRates(null,"Status==\"ACTIVE\"",null);
				messages.add("Get a TaxRate with WHERE clause - Name : " + TaxRateWhere.get(0).getName());
				
				List<TaxRate> TaxRateList = client.getTaxRates();
				int num11 = SampleData.findRandomNum(TaxRateList.size());
				messages.add("Get a random TaxRate - Name : " + TaxRateList.get(num11).getName());
				
				newTaxRate.get(0).setName("Yet Another Tax Rate-" + SampleData.loadRandomNum());
				List<TaxRate> updateTaxRate = client.updateTaxRate(newTaxRate);
				messages.add("Update the TaxRate - Type : " + updateTaxRate.get(0).getTaxType() + " - Name : " + updateTaxRate.get(0).getName());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("TrackingCategories")) {

			/* TRACKING CATEGORIES  */
			try {
				List<TrackingCategory> newTrackingCategory = client.createTrackingCategories(SampleData.loadTrackingCategory().getTrackingCategory());
				messages.add("Create a new TrackingCategory - ID : " + newTrackingCategory.get(0).getTrackingCategoryID() + " -  Name : " + newTrackingCategory.get(0).getName());
				
				/* TRACKING OPTIONS  */
				List<TrackingCategoryOption> newTrackingCategoryOption = client.createTrackingCategoryOption(SampleData.loadTrackingCategoryOption().getOption(),newTrackingCategory.get(0).getTrackingCategoryID());
				messages.add("Create a new TrackingCategory Option - Name : " + newTrackingCategoryOption.get(0).getName());
					
				/* MULTIPLE TRACKING OPTIONS  */
				List<TrackingCategoryOption> newTrackingCategoryOption2 = client.createTrackingCategoryOption(SampleData.loadTrackingCategoryOptionMulti().getOption(),newTrackingCategory.get(0).getTrackingCategoryID(),false);	
				messages.add("Create a new TrackingCategory MULTI Option - Name : " + newTrackingCategoryOption2.get(0).getName());
				messages.add("Create a new TrackingCategory MULTI Option - Name : " + newTrackingCategoryOption2.get(1).getName());
				
				
				TrackingCategoryOption oneTrackingCategoryOption = new TrackingCategoryOption();
				oneTrackingCategoryOption.setName("Iron Man");
				TrackingCategoryOption updateTrackingCategoryOption = client.updateTrackingCategoryOption(oneTrackingCategoryOption,newTrackingCategory.get(0).getTrackingCategoryID(),newTrackingCategoryOption.get(0).getTrackingOptionID());
				messages.add("Update TrackingCategory Option - Name : " + updateTrackingCategoryOption.getName());
			
				String deleteTrackingCategoryOption = client.deleteTrackingCategoryOption(newTrackingCategory.get(0).getTrackingCategoryID(),newTrackingCategoryOption.get(0).getTrackingOptionID());
				messages.add("Delete TrackingCategory Option -  : " + deleteTrackingCategoryOption);
				
				List<TrackingCategory> TrackingCategoryWhere = client.getTrackingCategories(null,"Status==\"ACTIVE\"",null);
				messages.add("Get a TrackingCategory with WHERE clause - Name : " + TrackingCategoryWhere.get(0).getName());
				
				List<TrackingCategory> TrackingCategoryList = client.getTrackingCategories();
				int num10 = SampleData.findRandomNum(TrackingCategoryList.size());
				messages.add("Get a random TrackingCategory - Name : " + TrackingCategoryList.get(num10).getName());
			
				TrackingCategory TrackingCategoryOne = client.getTrackingCategory(TrackingCategoryList.get(num10).getTrackingCategoryID());
				messages.add("Get a single TrackingCategory - Name : " + TrackingCategoryOne.getName());
				
				newTrackingCategory.get(0).setName("Lord of the Rings-" + SampleData.loadRandomNum());
				List<TrackingCategory> updateTrackingCategory = client.updateTrackingCategory(newTrackingCategory);
				messages.add("Update the TrackingCategory - ID : " + updateTrackingCategory.get(0).getTrackingCategoryID() + " - Name : " + updateTrackingCategory.get(0).getName());
			
				String status = client.deleteTrackingCategory(newTrackingCategory.get(0).getTrackingCategoryID());
				messages.add("Delete a new TrackingCategory - Delete result: " + status);
			
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Users")) {

			/* USER */
			try {
				List<User> UserWhere = client.getUsers(null,"IsSubscriber==true",null);
				if(UserWhere.size() > 0)  {
					messages.add("Get a User with WHERE clause - Email: " + UserWhere.get(0).getEmailAddress());
				} else {
					messages.add("No User with IsSubscriber True found - must be Demo Company");
				}
				
				List<User> UserList = client.getUsers();
				int num = SampleData.findRandomNum(UserList.size());
				messages.add("Get a random User - Email: " + UserList.get(num).getEmailAddress());
			
				User UserOne = client.getUser(UserList.get(num).getUserID());
				messages.add("Get a single User - Email: " + UserOne.getEmailAddress());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Errors")) {
			
			/* CONTACT */
			ArrayOfContact array = new ArrayOfContact();
			Contact contact = new Contact();
			contact.setName("Sidney Maestre");
			array.getContact().add(contact);
			
			// FORCE a 400 Error
			try {
				List<Contact> duplicateContact = client.createContact(array.getContact());
				messages.add("Create a new Contact - Name : " + duplicateContact.get(0).getName());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
				messages.add("Error Code : " + e.getResponseCode() + " Message: " + e.getMessage());
			}			
			
			// FORCE a 404 Error
			try {
				Contact ContactOne = client.getContact("1234");
				messages.add("Get a single Contact - ID : " + ContactOne.getContactID());
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
				messages.add("Error Code : " + e.getResponseCode() + " Message: " + e.getMessage());
			}	
			
			// FORCE a 503 Error
			List<Contact> ContactList = client.getContacts();
			int num4 = SampleData.findRandomNum(ContactList.size());			
			try {
				for(int i=65; i>1; i--){
					Contact ContactOne = client.getContact(ContactList.get(num4).getContactID());
				}
				messages.add("Congrats - you made over 60 calls without hitting rate limit");
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
				messages.add("Error Code : " + e.getResponseCode() + " Message: " + e.getMessage());
			}				
		}
		
		for (int i = 0; i < messages.size(); i++) {
			System.out.println(messages.get(i));
			respWriter.println("<p>" + messages.get(i) + "</p>");
		}
		
		respWriter.println("<hr>end processing request<hr><div class=\"form-group\">");
		respWriter.println("</div></div>");
	}
}
