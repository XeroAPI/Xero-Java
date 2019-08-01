package com.xero.example;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.xero.api.*;
import com.xero.models.accounting.*;
import com.xero.models.accounting.Phone.PhoneTypeEnum;
import com.xero.api.client.AccountingApi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.threeten.bp.*;

@WebServlet("/AuthenticatedResource")
public class AuthenticatedResource extends HttpServlet 
{
	private static final long serialVersionUID = 1L; 
	final static Logger logger = LogManager.getLogger(AuthenticatedResource.class);
	private AccountingApi accountingApi = null;
	   
	private String htmlString =  "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\">"
			+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css\" integrity=\"sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r\" crossorigin=\"anonymous\">"
			+ "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script>"
			+ "<div class=\"container\"><h1>Xero API - JAVA</h1>" 
			+ "<div class=\"form-group\">"
		  	+ "<a href=\"/\" class=\"btn btn-default\" type=\"button\">Logout</a>"
		  	+ "</div>"
			+ "<form action=\"./AuthenticatedResource\" method=\"post\">" 
			+ "<div class=\"form-group\">" 
		  	+ "<label for=\"object\">Create, Read, Update & Delete</label>"
		  	+ "<select name=\"object\" class=\"form-control\" id=\"object\">"
			+ "<option value=\"Accounts\" >Accounts</option>"
			+ "<option value=\"CreateAttachments\">Attachments - Create</option>"
			+ "<option value=\"GetAttachments\">Attachments - Get</option>"
		  	+ "<option value=\"BankTransactions\" >BankTransactions</option>"
		  	+ "<option value=\"BankTransfers\" >BankTransfers</option>"
		  	+ "<option value=\"BatchPayments\" >BatchPayments</option>"
		  	+ "<option value=\"BrandingThemes\">BrandingThemes</option>"
		  	+ "<option value=\"Contacts\">Contacts</option>"
		  	+ "<option value=\"ContactGroups\" >ContactGroups</option>"
		  	+ "<option value=\"ContactGroupContacts\">ContactGroups Contacts</option>"
		  	+ "<option value=\"CreditNotes\" >CreditNotes</option>"
		  	+ "<option value=\"CreditNotesPDF\" >CreditNote As PDF</option>"
		  	+ "<option value=\"Currencies\">Currencies</option>"
		  	+ "<option value=\"Employees\" >Employees</option>"
		  	+ "<option value=\"ExpenseClaims\">ExpenseClaims</option>"
		  	+ "<option value=\"Invoices\" >Invoices</option>"
		  	+ "<option value=\"InvoiceReminders\">InvoiceReminders</option>"
		  	+ "<option value=\"Items\">Items</option>"
		  	+ "<option value=\"Journals\">Journals</option>"
		  	+ "<option value=\"LinkedTransactions\">LinkedTransactions</option>"
		  	+ "<option value=\"ManualJournals\">ManualJournals</option>"
		  	+ "<option value=\"Organisations\" SELECTED>Organisations</option>"
		  	+ "<option value=\"Overpayments\">Overpayments</option>"
		  	+ "<option value=\"Payments\">Payments</option>"
		  	+ "<option value=\"PaymentServices\">PaymentServices</option>"
		  	+ "<option value=\"Prepayments\">Prepayments</option>"
		  	+ "<option value=\"PurchaseOrders\">PurchaseOrders</option>"
		  	+ "<option value=\"Receipts\">Receipts</option>"
		  	+ "<option value=\"RepeatingInvoices\" >RepeatingInvoices</option>"
		  	+ "<option value=\"Reports\" >Reports</option>"
		  	+ "<option value=\"TaxRates\">TaxRates</option>"
		  	+ "<option value=\"TrackingCategories\" >TrackingCategories</option>"
		  	+ "<option value=\"Users\">Users</option>"
		  	+ "<option value=\"Errors\" >Errors</option>"
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
	
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OffsetDateTime ifModifiedSince = null;
		String where = null;
		String order = null;
		boolean summarizeErrors = false;
		String ids= null;
		boolean includeArchived = false;
		String invoiceNumbers = null;
		String contactIDs = null;
		String statuses = null;
		boolean createdByMyApp = false;
		int unitDp = 2;
		Calendar now = Calendar.getInstance();
		int day = now.get(Calendar.DATE);
		int year = now.get(Calendar.YEAR);
		int lastMonth = now.get(Calendar.MONTH) - 1;
		int nextMonth = now.get(Calendar.MONTH) + 1;
		if (lastMonth == 0) {
			lastMonth = 1;
		}
		if (lastMonth == -1) {
			lastMonth = 12;
			year = year -1;
		}
		if (nextMonth == 13) {
			nextMonth = 1;
			year = year + 1;
		}
		if (day > 28) {
			day = 28;
		}
		
		PrintWriter respWriter = response.getWriter();
		response.setStatus(200);
		response.setContentType("text/html"); 
		respWriter.println(htmlString);
		respWriter.println("<div class=\"container\"><hr>begin processing request<hr><div class=\"form-group\">");
		
		String object = request.getParameter("object");
		ArrayList<String> messages = new ArrayList<String>();
		
		// Get Tokens and Xero Tenant Id from Storage
		TokenStorage store = new TokenStorage();
		String savedAccessToken =store.get(request, "access_token");
		String savedRefreshToken = store.get(request, "refresh_token");
		String xeroTenantId = store.get(request, "xero_tenant_id");	
		
		// Check expiration of token and refresh if necessary
		// This should be done prior to each API call to ensure your accessToken is valid
		String accessToken = new TokenRefresh().checkToken(savedAccessToken,savedRefreshToken,response);
		
        // Init AccountingApi client
        ApiClient defaultClient = new ApiClient();
        // Get Singleton - instance of accounting client
        accountingApi = AccountingApi.getInstance(defaultClient);	   
        
        if (object.equals("Accounts")) {
			// ACCOUNTS
			try {
				// GET all accounts
				Accounts accounts = accountingApi.getAccounts(accessToken,xeroTenantId,null, null, null);
				messages.add("Get a all Accounts - total : " + accounts.getAccounts().size());
				
				// GET one account
				Accounts oneAccount = accountingApi.getAccount(accessToken,xeroTenantId,accounts.getAccounts().get(0).getAccountID());
				messages.add("Get a one Account - name : " + oneAccount.getAccounts().get(0).getName());				
				
				// CREATE account
				Account acct = new Account();
				acct.setName("Bye" + loadRandomNum());
				acct.setCode("Hello" + loadRandomNum());
				acct.setDescription("Foo boo");
				acct.setType(com.xero.models.accounting.AccountType.EXPENSE);
				Accounts newAccount = accountingApi.createAccount(accessToken,xeroTenantId,acct);
				messages.add("Create a new Account - Name : " + newAccount.getAccounts().get(0).getName() + " Description : " + newAccount.getAccounts().get(0).getDescription() + "");
				UUID accountID = newAccount.getAccounts().get(0).getAccountID();
				
				// CREATE Bank account
				Account bankAcct = new Account();
				bankAcct.setName("Checking " + loadRandomNum());
				bankAcct.setCode("12" + loadRandomNum());
				bankAcct.setType(com.xero.models.accounting.AccountType.BANK);
				bankAcct.setBankAccountNumber("1234" + loadRandomNum());
				Accounts newBankAccount = accountingApi.createAccount(accessToken, xeroTenantId,bankAcct);
				messages.add("Create Bank Account - Name : " + newBankAccount.getAccounts().get(0).getName());				
				
				// GET BANK account
			    where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
				Accounts accountsWhere = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
				messages.add("Get a all Accounts - total : " + accountsWhere.getAccounts().size());				
				
				// UDPATE Account
				newAccount.getAccounts().get(0).setDescription("Monsters Inc.");
				newAccount.getAccounts().get(0).setStatus(null);
				Accounts updateAccount = accountingApi.updateAccount(accessToken,xeroTenantId,accountID, newAccount);
				messages.add("Update Account - Name : " + updateAccount.getAccounts().get(0).getName() + " Description : " + updateAccount.getAccounts().get(0).getDescription() + "");
				
				// ARCHIVE Account
				Accounts archiveAccounts = new Accounts();
				Account archiveAccount = new Account();
				archiveAccount.setStatus(com.xero.models.accounting.Account.StatusEnum.ARCHIVED);
				archiveAccount.setAccountID(accountID);
				archiveAccounts.addAccountsItem(archiveAccount);
				Accounts achivedAccount = accountingApi.updateAccount(accessToken,xeroTenantId,accountID, archiveAccounts);
				messages.add("Archived Account - Name : " + achivedAccount.getAccounts().get(0).getName() + " Status: " + achivedAccount.getAccounts().get(0).getStatus());
				
				// DELETE Account
				UUID deleteAccountID = newAccount.getAccounts().get(0).getAccountID();
				Accounts deleteAccount = accountingApi.deleteAccount(accessToken,xeroTenantId,deleteAccountID);
				messages.add("Delete account - Status? : " + deleteAccount.getAccounts().get(0).getStatus());	
				
			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}
			
        } else if (object.equals("GetAttachments")) {
        	
        	try {
				// GET Account Attachment 
        		where = "Status==\"ACTIVE\"";
				Accounts accounts = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
				if (accounts.getAccounts().size() > 0) {
					UUID accountID = accounts.getAccounts().get(0).getAccountID();				
					Attachments accountsAttachments = accountingApi.getAccountAttachments(accessToken,xeroTenantId,accountID);
					if (accountsAttachments.getAttachments().size() > 0) {
						UUID attachementId = accountsAttachments.getAttachments().get(0).getAttachmentID();
						String contentType = accountsAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream input	 = accountingApi.getAccountAttachmentById(accessToken,xeroTenantId,accountID,attachementId, contentType);
						String fileName = "Account_" + accountsAttachments.getAttachments().get(0).getFileName();
						String saveFilePath = saveFile(input,fileName);
						messages.add("Get Account attachment - save it here: " + saveFilePath);	
					}
				}
				
				// GET BankTransactions Attachment
				where = null;
				BankTransactions bankTransactions = accountingApi.getBankTransactions(accessToken,xeroTenantId,ifModifiedSince, where, order, null, null);
				if (bankTransactions.getBankTransactions().size() > 0) {
					UUID BankTransactionID = bankTransactions.getBankTransactions().get(0).getBankTransactionID();				
					Attachments bankTransactionsAttachments = accountingApi.getBankTransactionAttachments(accessToken,xeroTenantId,BankTransactionID);
					if (bankTransactionsAttachments.getAttachments().size() > 0) {
						UUID BankTransactionAttachementID = bankTransactionsAttachments.getAttachments().get(0).getAttachmentID();
						String BankTransactionContentType = bankTransactionsAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream BankTransactionInput	 = accountingApi.getAccountAttachmentById(accessToken,xeroTenantId,BankTransactionID,BankTransactionAttachementID, BankTransactionContentType);
						String BankTransactionFileName = "BankTransaction_" + bankTransactionsAttachments.getAttachments().get(0).getFileName();
						String BankTransactionSaveFilePath = saveFile(BankTransactionInput,BankTransactionFileName);
						messages.add("Get BankTransactions attachment - save it here: " + BankTransactionSaveFilePath);
					}
				}
				
				// GET BankTransfers Attachment 
				BankTransfers bankTransfers = accountingApi.getBankTransfers(accessToken,xeroTenantId,ifModifiedSince, where, order);
				if (bankTransfers.getBankTransfers().size() > 0) {
					UUID BankTransferID = bankTransfers.getBankTransfers().get(0).getBankTransferID();				
					Attachments bankTransfersAttachments = accountingApi.getBankTransferAttachments(accessToken,xeroTenantId,BankTransferID);
					if (bankTransfersAttachments.getAttachments().size() > 0) {	
						UUID BankTransferAttachementID = bankTransfersAttachments.getAttachments().get(0).getAttachmentID();
						String BankTransferContentType = bankTransfersAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream BankTransferInput	 = accountingApi.getAccountAttachmentById(accessToken,xeroTenantId,BankTransferID,BankTransferAttachementID, BankTransferContentType);
						String BankTransferFileName = "BankTransfer_" + bankTransfersAttachments.getAttachments().get(0).getFileName();
						String BankTransferSaveFilePath = saveFile(BankTransferInput,BankTransferFileName);
						messages.add("Get BankTransfers attachment - save it here: " + BankTransferSaveFilePath);
					}
				}
				// GET Contacts Attachment 
				where =  "ContactStatus==\"ACTIVE\"";
				Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
				if (contacts.getContacts().size() > 0) {
					UUID ContactID = contacts.getContacts().get(0).getContactID();				
					Attachments contactsAttachments = accountingApi.getContactAttachments(accessToken,xeroTenantId,ContactID);
					if (contactsAttachments.getAttachments().size() > 0) {	
						UUID ContactAttachementID = contactsAttachments.getAttachments().get(0).getAttachmentID();
						String ContactContentType = contactsAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream ContactInput	 = accountingApi.getAccountAttachmentById(accessToken,xeroTenantId,ContactID,ContactAttachementID, ContactContentType);
						String ContactFileName = "Contact_" + contactsAttachments.getAttachments().get(0).getFileName();
						String ContactSaveFilePath = saveFile(ContactInput,ContactFileName);
						messages.add("Get Contacts attachment - save it here: " + ContactSaveFilePath);
					}
				}
				// GET CreditNotes Attachment 
				where = "Status==\"AUTHORISED\"";
				CreditNotes creditNotes = accountingApi.getCreditNotes(accessToken,xeroTenantId,ifModifiedSince, where, order, null);
				if (creditNotes.getCreditNotes().size() > 0) {	
					UUID CreditNoteID = creditNotes.getCreditNotes().get(0).getCreditNoteID();				
					Attachments creditNotesAttachments = accountingApi.getCreditNoteAttachments(accessToken,xeroTenantId,CreditNoteID);
					if (creditNotesAttachments.getAttachments().size() > 0) {
						UUID CreditNoteAttachementID = creditNotesAttachments.getAttachments().get(0).getAttachmentID();
						String CreditNoteContentType = creditNotesAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream CreditNoteInput	 = accountingApi.getAccountAttachmentById(accessToken,xeroTenantId,CreditNoteID,CreditNoteAttachementID, CreditNoteContentType);
						String CreditNoteFileName = "CreditNote_" + creditNotesAttachments.getAttachments().get(0).getFileName();
						String CreditNoteSaveFilePath = saveFile(CreditNoteInput,CreditNoteFileName);
						messages.add("Get CreditNotes attachment - save it here: " + CreditNoteSaveFilePath);
					}
				}
				
				// GET Invoices Attachment 
				Invoices invoices = accountingApi.getInvoices(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp, null);
				if (invoices.getInvoices().size() > 0) {	
					UUID InvoiceID = invoices.getInvoices().get(0).getInvoiceID();				
					Attachments invoicesAttachments = accountingApi.getInvoiceAttachments(accessToken,xeroTenantId,InvoiceID);
					if (invoicesAttachments.getAttachments().size() > 0) {	
						UUID InvoiceAttachementID = invoicesAttachments.getAttachments().get(0).getAttachmentID();
						String InvoiceContentType = invoicesAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream InvoiceInput	 = accountingApi.getAccountAttachmentById(accessToken,xeroTenantId,InvoiceID,InvoiceAttachementID, InvoiceContentType);
						String InvoiceFileName = "Invoice_" + invoicesAttachments.getAttachments().get(0).getFileName();
						String InvoiceSaveFilePath = saveFile(InvoiceInput,InvoiceFileName);
						messages.add("Get Invoices attachment - save it here: " + InvoiceSaveFilePath);
					}
				}

				// GET ManualJournals Attachment 
				where= null;
				ManualJournals manualJournals = accountingApi.getManualJournals(accessToken,xeroTenantId,ifModifiedSince, where, order, null);
				if (manualJournals.getManualJournals().size() > 0) {	
					UUID ManualJournalID = manualJournals.getManualJournals().get(0).getManualJournalID();				
					Attachments manualJournalsAttachments = accountingApi.getManualJournalAttachments(accessToken,xeroTenantId,ManualJournalID);
					if (manualJournalsAttachments.getAttachments().size() > 0) {	
						UUID ManualJournalAttachementID = manualJournalsAttachments.getAttachments().get(0).getAttachmentID();
						String ManualJournalContentType = manualJournalsAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream ManualJournalInput	 = accountingApi.getAccountAttachmentById(accessToken,xeroTenantId,ManualJournalID,ManualJournalAttachementID, ManualJournalContentType);
						String ManualJournalFileName = "ManualJournal_" + manualJournalsAttachments.getAttachments().get(0).getFileName();
						String ManualJournalSaveFilePath = saveFile(ManualJournalInput,ManualJournalFileName);
						messages.add("Get ManualJournals attachment - save it here: " + ManualJournalSaveFilePath);
					}
				}
				
				// GET Receipts Attachment 
				Receipts receipts = accountingApi.getReceipts(accessToken,xeroTenantId,ifModifiedSince, where, order, null);
				if (receipts.getReceipts().size() > 0) {			
					UUID ReceiptID = receipts.getReceipts().get(0).getReceiptID();				
					Attachments receiptsAttachments = accountingApi.getReceiptAttachments(accessToken,xeroTenantId,ReceiptID);
					if (receiptsAttachments.getAttachments().size() > 0) {			
						UUID ReceiptAttachementID = receiptsAttachments.getAttachments().get(0).getAttachmentID();
						String ReceiptContentType = receiptsAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream ReceiptInput	 = accountingApi.getAccountAttachmentById(accessToken,xeroTenantId,ReceiptID,ReceiptAttachementID, ReceiptContentType);
						String ReceiptFileName = "Receipt_" + receiptsAttachments.getAttachments().get(0).getFileName();						
						String ReceiptSaveFilePath = saveFile(ReceiptInput,ReceiptFileName);
						messages.add("Get Receipts attachment - save it here: " + ReceiptSaveFilePath);
					}
				}

				// GET RepeatingInvoices Attachment 
				RepeatingInvoices repeatingInvoices = accountingApi.getRepeatingInvoices(accessToken,xeroTenantId,where, order);
				if (repeatingInvoices.getRepeatingInvoices().size() > 0) {			
					UUID RepeatingInvoiceID = repeatingInvoices.getRepeatingInvoices().get(0).getRepeatingInvoiceID();				
					Attachments repeatingInvoicesAttachments = accountingApi.getRepeatingInvoiceAttachments(accessToken,xeroTenantId,RepeatingInvoiceID);
					if (repeatingInvoicesAttachments.getAttachments().size() > 0) {			
						UUID RepeatingInvoiceAttachementID = repeatingInvoicesAttachments.getAttachments().get(0).getAttachmentID();
						String RepeatingInvoiceContentType = repeatingInvoicesAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream RepeatingInvoiceInput	 = accountingApi.getAccountAttachmentById(accessToken,xeroTenantId,RepeatingInvoiceID,RepeatingInvoiceAttachementID, RepeatingInvoiceContentType);
						String RepeatingInvoiceFileName = "RepeatingInvoice_" + repeatingInvoicesAttachments.getAttachments().get(0).getFileName();
						String RepeatingInvoiceSaveFilePath = saveFile(RepeatingInvoiceInput,RepeatingInvoiceFileName);
						messages.add("Get RepeatingInvoices attachment - save it here: " + RepeatingInvoiceSaveFilePath);
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	
        } else if (object.equals("CreateAttachments")) {	
        	// JSON
        	File bytes = new File("/Users/sid.maestre/eclipse-workspace/xero-sdk-oauth2-dev-01/resources/youngsid.jpg");
			String newFileName = bytes.getName();
	
        	try {
				// CREATE Accounts attachment
			    where = "Status==\"ACTIVE\"";
				Accounts myAccounts = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
				if ( myAccounts.getAccounts().size() > 0) {
					UUID accountID = myAccounts.getAccounts().get(0).getAccountID();
					String accountName = myAccounts.getAccounts().get(0).getName();
					Attachments createdAttachments = accountingApi.createAccountAttachmentByFileName(accessToken,xeroTenantId,accountID, newFileName, bytes);
					messages.add("Attachment to Name: " + accountName + " Account ID: " + accountID + " attachment - ID: " + createdAttachments.getAttachments().get(0).getAttachmentID());
				}
        	} catch (Exception e) {
				System.out.println(e);
			}
			
        	try {
        		where = null;
				// CREATE BankTransactions attachment
				BankTransactions myBanktransactions = accountingApi.getBankTransactions(accessToken,xeroTenantId,ifModifiedSince, where, order, null, null);
				if ( myBanktransactions.getBankTransactions().size() > 0) {
					UUID banktransactionID = myBanktransactions.getBankTransactions().get(0).getBankTransactionID();			
					Attachments createdBanktransationAttachments = accountingApi.createBankTransactionAttachmentByFileName(accessToken,xeroTenantId,banktransactionID, newFileName, bytes);
					messages.add("Attachment to BankTransaction ID: " + banktransactionID + " attachment - ID: "  + createdBanktransationAttachments.getAttachments().get(0).getAttachmentID());
				}
        	} catch (Exception e) {
				System.out.println(e);
			}
			
        	try {
				// CREATE BankTransfer attachment
				BankTransfers myBankTransfer = accountingApi.getBankTransfers(accessToken,xeroTenantId,ifModifiedSince, where, order);
				if ( myBankTransfer.getBankTransfers().size() > 0) {
					UUID bankTransferID = myBankTransfer.getBankTransfers().get(0).getBankTransferID();			
					Attachments createdBankTransferAttachments = accountingApi.createBankTransferAttachmentByFileName(accessToken,xeroTenantId,bankTransferID, newFileName, bytes);
					messages.add("Attachment to BankTransfer ID: " + bankTransferID + " attachment - ID: " + createdBankTransferAttachments.getAttachments().get(0).getAttachmentID());
				}
        	} catch (Exception e) {
				System.out.println(e);
			}
			
        	try {
				// CREATE Contacts attachment
				where =  "ContactStatus==\"ACTIVE\"";
				Contacts contactsWhere = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
				if ( contactsWhere.getContacts().size() > 0) {
					UUID contactID = contactsWhere.getContacts().get(0).getContactID();
					Attachments createdContactAttachments = accountingApi.createContactAttachmentByFileName(accessToken,xeroTenantId,contactID, newFileName, bytes);
					messages.add("Attachment to Contact ID: " + contactID + " attachment - ID: " + createdContactAttachments.getAttachments().get(0).getAttachmentID());
				}
        	} catch (Exception e) {
				System.out.println(e);
			}
			
        	try {
				where = "Status==\"AUTHORISED\"";
				// CREATE CreditNotes attachment
				CreditNotes myCreditNotes = accountingApi.getCreditNotes(accessToken,xeroTenantId,ifModifiedSince, where, order, null);
				if ( myCreditNotes.getCreditNotes().size() > 0) {
					UUID creditNoteID = myCreditNotes.getCreditNotes().get(0).getCreditNoteID();
					Attachments createdCreditNoteAttachments = accountingApi.createCreditNoteAttachmentByFileName(accessToken,xeroTenantId,creditNoteID, newFileName, bytes);
					messages.add("Attachment to Credit Notes ID: " + creditNoteID + " attachment - ID: " + createdCreditNoteAttachments.getAttachments().get(0).getAttachmentID());
				}
        	} catch (Exception e) {
				System.out.println(e);
			}
			
        	try {
				// CREATE invoice attachment
				Invoices myInvoices = accountingApi.getInvoices(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp, null);
				if ( myInvoices.getInvoices().size() > 0) {
					UUID invoiceID = myInvoices.getInvoices().get(0).getInvoiceID();
					Attachments createdInvoiceAttachments = accountingApi.createInvoiceAttachmentByFileName(accessToken,xeroTenantId,invoiceID, newFileName, bytes);
					messages.add("Attachment to Invoice ID: " + invoiceID + " attachment - ID: "  + createdInvoiceAttachments.getAttachments().get(0).getAttachmentID());
				}
        	} catch (Exception e) {
				System.out.println(e);
			}
			
        	try {
				// CREATE ManualJournals attachment
        		where= null;
				ManualJournals myManualJournals = accountingApi.getManualJournals(accessToken,xeroTenantId,ifModifiedSince, where, order, null);
				System.out.println(myManualJournals.getManualJournals().size());
				
				if ( myManualJournals.getManualJournals().size() > 0) {
					UUID manualJournalID = myManualJournals.getManualJournals().get(0).getManualJournalID();
					Attachments createdManualJournalAttachments = accountingApi.createManualJournalAttachmentByFileName(accessToken,xeroTenantId,manualJournalID, newFileName, bytes);
					messages.add("Attachment to Manual Journal ID: " + manualJournalID + " attachment - ID: " + createdManualJournalAttachments.getAttachments().get(0).getAttachmentID());
				}
        	} catch (Exception e) {
				System.out.println(e);
			}
			
        	try {
				// CREATE Receipts attachment
				Receipts myReceipts = accountingApi.getReceipts(accessToken,xeroTenantId,ifModifiedSince, where, order, null);
				if ( myReceipts.getReceipts().size() > 0) {
					UUID receiptID = myReceipts.getReceipts().get(0).getReceiptID();
					Attachments createdReceiptsAttachments = accountingApi.createReceiptAttachmentByFileName(accessToken,xeroTenantId,receiptID, newFileName, bytes);
					messages.add("Attachment to Receipt ID: " + receiptID + " attachment - ID: " + createdReceiptsAttachments.getAttachments().get(0).getAttachmentID());
				}
        	} catch (Exception e) {
				System.out.println(e);
			}
			
        	try {
				// CREATE Repeating Invoices attachment
				RepeatingInvoices myRepeatingInvoices = accountingApi.getRepeatingInvoices(accessToken,xeroTenantId,where, order);
				if ( myRepeatingInvoices.getRepeatingInvoices().size() > 0) {	
					UUID repeatingInvoiceID = myRepeatingInvoices.getRepeatingInvoices().get(0).getRepeatingInvoiceID();
					Attachments createdRepeatingInvoiceAttachments = accountingApi.createRepeatingInvoiceAttachmentByFileName(accessToken,xeroTenantId,repeatingInvoiceID, newFileName, bytes);
					messages.add("Attachment to Repeating Invoices ID: " + repeatingInvoiceID + " attachment - ID: " + createdRepeatingInvoiceAttachments.getAttachments().get(0).getAttachmentID());
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
        
        } else if (object.equals("BankTransfers")) {		
			/* BANK TRANSFER */
			try {
			    where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
				Accounts accountsWhere = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
				where = null;
				// Maker sure we have at least 2 banks
				if(accountsWhere.getAccounts().size() > 1) {				
					// CREATE bank transfer
					BankTransfer bankTransfer = new BankTransfer();
					bankTransfer.setFromBankAccount(accountsWhere.getAccounts().get(1));
					bankTransfer.setToBankAccount(accountsWhere.getAccounts().get(0));
					bankTransfer.setAmount("50.00");
					BankTransfers newBTs = new BankTransfers();
					newBTs.addBankTransfersItem(bankTransfer);					
					BankTransfers newBankTranfer = accountingApi.createBankTransfer(accessToken,xeroTenantId,newBTs);
					messages.add("Get a one Bank Transfer - amount : " + newBankTranfer.getBankTransfers().get(0).getAmount());				

					// GET all Bank Transfers
					BankTransfers bankTranfers = accountingApi.getBankTransfers(accessToken,xeroTenantId,ifModifiedSince, where, order);
					messages.add("Get a all Bank Transfers - total : " + bankTranfers.getBankTransfers().size());				
					UUID bankTransferId = bankTranfers.getBankTransfers().get(0).getBankTransferID();

					// GET one Bank Transfer
					BankTransfers oneBankTranfer = accountingApi.getBankTransfer(accessToken,xeroTenantId,bankTransferId);
					messages.add("Get a one Bank Transfer - amount : " + oneBankTranfer.getBankTransfers().get(0).getAmount());				
			
					// GET  Bank Transfer History
					HistoryRecords hr = accountingApi.getBankTransferHistory(accessToken,xeroTenantId,bankTransferId);
					messages.add("Get a one Bank Transfer History Record - details :" + hr.getHistoryRecords().get(0).getDetails());				
					
					// CREATE  Bank Transfer History
					// Error: "The document with the supplied id was not found for this endpoint.
					/*
					HistoryRecords historyRecords = new HistoryRecords();	
					HistoryRecord historyRecord = new HistoryRecord();
					historyRecord.setDetails("This is a sample history note");
					historyRecords.addHistoryRecordsItem(historyRecord);
					HistoryRecords newHr = accountingApi.createBankTransferHistoryRecord(bankTransferId, historyRecords);  
					messages.add("Get a one Bank Transfer History Record - details :" + newHr.getHistoryRecords().get(0).getDetails());				
					*/
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}	
			
		
        } else if (object.equals("BankTransactions")) {
			/* BANK TRANSACTION */
			try {
			    where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
				Accounts accountsWhere = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
				
				Account bankAcct = new Account();
				bankAcct.setCode(accountsWhere.getAccounts().get(0).getCode());
				
				where = null;
				Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
				Contact useContact = new Contact();
				useContact.setContactID(contacts.getContacts().get(0).getContactID());
				
				// Maker sure we have at least 1 bank
				if(accountsWhere.getAccounts().size() > 0) {
					// Create Bank Transaction
					List<LineItem> lineItems = new ArrayList<>();
					LineItem li = new LineItem();
					li.setAccountCode("400");
					li.setDescription("Foobar");
					li.setQuantity(1.0);
					li.setUnitAmount(20.0);
					lineItems.add(li);
					BankTransaction bt = new BankTransaction();
					bt.setBankAccount(bankAcct);
					bt.setContact(useContact);
					bt.setLineitems(lineItems);
					bt.setType(com.xero.models.accounting.BankTransaction.TypeEnum.SPEND);
					BankTransactions bts = new BankTransactions();
					bts.addBankTransactionsItem(bt);					
					BankTransactions newBankTransaction = accountingApi.createBankTransaction(accessToken,xeroTenantId,bts, summarizeErrors);
					messages.add("Create new BankTransaction : amount:" + newBankTransaction.getBankTransactions().get(0).getTotal());				
					
					// GET all Bank Transaction
					BankTransactions bankTransactions = accountingApi.getBankTransactions(accessToken,xeroTenantId,ifModifiedSince, where, order, null, null);
					messages.add("Get a all Bank Transactions - total : " + bankTransactions.getBankTransactions().size());				

					// GET one Bank Transaction
					BankTransactions oneBankTransaction = accountingApi.getBankTransaction(accessToken,xeroTenantId,bankTransactions.getBankTransactions().get(0).getBankTransactionID());
					messages.add("Get a one Bank Transaction : amount:" + oneBankTransaction.getBankTransactions().get(0).getTotal());				
					
					// UDPATE Bank Transaction
					newBankTransaction.getBankTransactions().get(0).setSubTotal(null);
					newBankTransaction.getBankTransactions().get(0).setTotal(null);	
					newBankTransaction.getBankTransactions().get(0).setReference("You just updated");
					BankTransactions updateBankTransaction = accountingApi.updateBankTransaction(accessToken,xeroTenantId,newBankTransaction.getBankTransactions().get(0).getBankTransactionID(),newBankTransaction);
					messages.add("Update new BankTransaction : reference:" + updateBankTransaction.getBankTransactions().get(0).getReference());				

					// DELETE Bank Transaction
					newBankTransaction.getBankTransactions().get(0).setStatus(com.xero.models.accounting.BankTransaction.StatusEnum.DELETED);
					BankTransactions deletedBankTransaction = accountingApi.updateBankTransaction(accessToken,xeroTenantId,newBankTransaction.getBankTransactions().get(0).getBankTransactionID(),newBankTransaction);
					messages.add("Deleted new Bank Transaction : Status:" + deletedBankTransaction.getBankTransactions().get(0).getStatus());				
				
					// GET  Bank Transaction History
					HistoryRecords hr = accountingApi.getBankTransactionsHistory(accessToken,xeroTenantId,oneBankTransaction.getBankTransactions().get(0).getBankTransactionID());
					messages.add("Get a one Bank Transaction History Record - details :" + hr.getHistoryRecords().get(0).getDetails());				
					
					// CREATE  Bank Transaction History
					// Error: "The document with the supplied id was not found for this end point.
					/*
					HistoryRecords historyRecords = new HistoryRecords();	
					HistoryRecord historyRecord = new HistoryRecord();
					historyRecord.setDetails("This is a sample history note");
					historyRecords.addHistoryRecordsItem(historyRecord);
					HistoryRecords newHr = accountingApi.createBankTransactionHistoryRecord(oneBankTransaction.getBankTransactions().get(0).getBankTransactionID(), historyRecords);  
					messages.add("Create a one Bank Transaction History Record - details :" + newHr.getHistoryRecords().get(0).getDetails());				
					*/
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}			
		
        } else if (object.equals("BatchPayments")) {
        	//BATCH PAYMENTS 
        	try {
				// CREATE payment
				where =  "Status==\"AUTHORISED\"&&Type==\"ACCREC\"";			
				Invoices allInvoices = accountingApi.getInvoices(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp, null);
				Invoice inv = new Invoice();
				inv.setInvoiceID(allInvoices.getInvoices().get(0).getInvoiceID());
				Invoice inv2 = new Invoice();
				inv2.setInvoiceID(allInvoices.getInvoices().get(1).getInvoiceID());
				Invoice inv3 = new Invoice();
				inv3.setInvoiceID(allInvoices.getInvoices().get(3).getInvoiceID());
				where = null;
			
			    where = "EnablePaymentsToAccount==true";
				Accounts accountsWhere = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
				Account paymentAccount = new Account();
				paymentAccount.setAccountID(accountsWhere.getAccounts().get(0).getAccountID());
				where = null;
				
				BatchPayments createBatchPayments = new BatchPayments();
				BatchPayment createBatchPayment = new BatchPayment();
				createBatchPayment.setAccount(paymentAccount);
				createBatchPayment.setAmount(3.0);
				LocalDate currDate = LocalDate.now();
				createBatchPayment.setDate(currDate);
				createBatchPayment.setReference("Foobar" + loadRandomNum());

				Payment payment01 = new Payment();
				payment01.setAccount(paymentAccount);
				payment01.setInvoice(inv);
				payment01.setAmount(1.0);
				payment01.setDate(currDate);
				
				Payment payment02 = new Payment();
				payment02.setAccount(paymentAccount);
				payment02.setInvoice(inv2);
				payment02.setAmount(1.0);
				payment02.setDate(currDate);
				
				Payment payment03 = new Payment();
				payment03.setAccount(paymentAccount);
				payment03.setInvoice(inv3);
				payment03.setAmount(1.0);
				payment03.setDate(currDate);
				
				createBatchPayment.addPaymentsItem(payment01);
				createBatchPayment.addPaymentsItem(payment02);
				createBatchPayment.addPaymentsItem(payment03);
				
				createBatchPayments.addBatchPaymentsItem(createBatchPayment);
				
				BatchPayments newBatchPayments = accountingApi.createBatchPayment(accessToken,xeroTenantId,createBatchPayments);
				messages.add("Create BatchPayments - ID : " + newBatchPayments.getBatchPayments().get(0).getTotalAmount());					
				
				// GET all Payments
				BatchPayments allBatchPayments = accountingApi.getBatchPayments(accessToken,xeroTenantId,ifModifiedSince, where, order);
				messages.add("Get BatchPayments - Total : " + allBatchPayments.getBatchPayments().size());					
			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}
        
        } else if (object.equals("BrandingThemes")) {
			/* BRANDING THEME */
			try {
				// GET all BrandingTheme
				BrandingThemes bt = accountingApi.getBrandingThemes(accessToken,xeroTenantId);
				messages.add("Get a All Branding Themes - total : " + bt.getBrandingThemes().size());				

				// GET one BrandingTheme
				UUID btID = bt.getBrandingThemes().get(0).getBrandingThemeID();
				BrandingThemes oneBt = accountingApi.getBrandingTheme(accessToken,xeroTenantId,btID);
				messages.add("Get a one Branding Themes - name : " + oneBt.getBrandingThemes().get(0).getName());				
/*
				// Create PaymentService for a Branding Theme  
				PaymentServices paymentServices = accountingApi.getPaymentServices();
				UUID paymentServiceID = paymentServices.getPaymentServices().get(0).getPaymentServiceID();
				PaymentService btPaymentService = new PaymentService();
				btPaymentService.setPaymentServiceType("Custom");
				btPaymentService.setPaymentServiceID(paymentServiceID);
				PaymentServices createdPaymentService = accountingApi.createBrandingThemePaymentServices(btID, btPaymentService);
				messages.add("Created payment services for Branding Themes - name : " + createdPaymentService.getPaymentServices().get(0).getPaymentServiceName());				
*/
				// GET Payment Services for a single Branding Theme
				PaymentServices paymentServicesForBrandingTheme = accountingApi.getBrandingThemePaymentServices(accessToken,xeroTenantId,btID);
				messages.add("Get payment services for Branding Themes - name : " + paymentServicesForBrandingTheme.getPaymentServices().get(0).getPaymentServiceName());				

			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}
		
        } else if (object.equals("Contacts")) {		
			/* CONTACTS */
			try {
				// CREATE contact
				Contact contact = new Contact();
				contact.setName("Foo" + loadRandomNum());
				contact.setEmailAddress("sid" + loadRandomNum() + "@blah.com");
				List<Phone> phones = new ArrayList<Phone>();
				Phone phone = new Phone();
				phone.setPhoneType(PhoneTypeEnum.MOBILE);
				phone.setPhoneNumber("555-1212");
				phone.setPhoneAreaCode("415");
				phones.add(phone);
				contact.setPhones(phones);
				Contacts newContact = accountingApi.createContact(accessToken,xeroTenantId,contact);
				messages.add("Create new Contact - Name : " + newContact.getContacts().get(0).getName());
				
				// UPDATE contact
				newContact.getContacts().get(0).setName("Bar" + loadRandomNum());
				UUID contactID = newContact.getContacts().get(0).getContactID();
				Contacts updatedContact = accountingApi.updateContact(accessToken,xeroTenantId,contactID, newContact);
				messages.add("Update new Contact - Name : " + updatedContact.getContacts().get(0).getName());
				
				// GET all contact
				Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
				messages.add("Get a All Contacts - Total : " + contacts.getContacts().size());	

				// GET one contact
				UUID oneContactID = contacts.getContacts().get(0).getContactID();
				Contacts oneContact = accountingApi.getContact(accessToken,xeroTenantId,oneContactID);
				messages.add("Get a One Contact - Name : " + oneContact.getContacts().get(0).getName());	
				
				// GET contact cisSettings
			    where = "Name==\"sidney\"";   
				Contacts cisContact = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
				if (cisContact.getContacts().size() > 0) {
					CISSettings cisSettings = accountingApi.getContactCISSettings(accessToken,xeroTenantId,cisContact.getContacts().get(0).getContactID());
					messages.add("Get a Contact cisSettings - Enabled? : " + cisSettings.getCiSSettings().get(0).getCiSEnabled());	
				}
				where = null;
				
				// GET active contacts
			    where =  "ContactStatus==\"ACTIVE\"";
				Contacts contactsWhere = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
				messages.add("Get a all ACTIVE Contacts - Total : " + contactsWhere.getContacts().size());
				where = null;
				
				// Get Contact History
				HistoryRecords contactHistory = accountingApi.getContactHistory(accessToken,xeroTenantId,contactID);
				messages.add("Contact History - count : " + contactHistory.getHistoryRecords().size() );
			
				// Create Contact History
				HistoryRecords newHistoryRecords = new  HistoryRecords();
				HistoryRecord newHistoryRecord = new  HistoryRecord();
				newHistoryRecord.setDetails("Hello World");
				newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
				
				HistoryRecords newInvoiceHistory = accountingApi.createContactHistory(accessToken,xeroTenantId,contactID,newHistoryRecords);
				messages.add("Contact History - note added to  : " + newInvoiceHistory.getHistoryRecords().get(0).getDetails());
				
			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}
		
        } else if (object.equals("ContactGroups")) {
    		
			/* CONTACT GROUP  */		
			try {
				// Create contact group
				ContactGroups newCGs = new ContactGroups();
				ContactGroup cg = new ContactGroup();
				cg.setName("NewGroup" + loadRandomNum());
				newCGs.addContactGroupsItem(cg);
				ContactGroups newContactGroup = accountingApi.createContactGroup(accessToken,xeroTenantId,newCGs);
				messages.add("Create a ContactGroup - Name : " + newContactGroup.getContactGroups().get(0).getName());
				
				// UPDATE Contact group
				newCGs.getContactGroups().get(0).setName("Old Group" + loadRandomNum());
				UUID newContactGroupID = newContactGroup.getContactGroups().get(0).getContactGroupID();
				ContactGroups updateContactGroup = accountingApi.updateContactGroup(accessToken,xeroTenantId,newContactGroupID, newCGs);
				messages.add("Update a ContactGroup - Name : " + updateContactGroup.getContactGroups().get(0).getName());

				// GET all contact groups
				ContactGroups contactGroups = accountingApi.getContactGroups(accessToken,xeroTenantId,where, order);
				messages.add("Get all ContactGroups - Total : " + contactGroups.getContactGroups().size());
				
				// GET one contact groups
				UUID contactGroupId = contactGroups.getContactGroups().get(0).getContactGroupID();
				ContactGroups oneCg = accountingApi.getContactGroup(accessToken,xeroTenantId,contactGroupId);
				messages.add("Get one ContactGroups - Name : " + oneCg.getContactGroups().get(0).getName());
				
				// DELETE contact Group
				newCGs.getContactGroups().get(0).setStatus(com.xero.models.accounting.ContactGroup.StatusEnum.DELETED);
				UUID contactGroupID = newContactGroup.getContactGroups().get(0).getContactGroupID();
				ContactGroups deletedContactGroup = accountingApi.updateContactGroup(accessToken,xeroTenantId,contactGroupID, contactGroups);
				messages.add("Delete a ContactGroup - Name : " + deletedContactGroup.getContactGroups().get(0).getName());
					
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		} else if (object.equals("ContactGroupContacts")) {
			/* CONTACT GROUP CONTACTS */
			try {	
				// Create new Contact Group
				ContactGroups newCGs = new ContactGroups();
				ContactGroup cg = new ContactGroup();
				cg.setName("NewGroup" + loadRandomNum());
				newCGs.addContactGroupsItem(cg);
				ContactGroups newContactGroup = accountingApi.createContactGroup(accessToken,xeroTenantId,newCGs);
			
				Contacts allContacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
				
				// Create Contacts in Group
				Contacts contactList = new Contacts();
				contactList.addContactsItem(allContacts.getContacts().get(0));
				contactList.addContactsItem(allContacts.getContacts().get(1));
				UUID contactGroupID = newContactGroup.getContactGroups().get(0).getContactGroupID();
				Contacts addContacts = accountingApi.createContactGroupContacts(accessToken,xeroTenantId,contactGroupID, contactList);
				messages.add("Add 2 Contacts to Contact Group - Total : " + addContacts.getContacts().size());	
				
				// DELETE all Contacts in Group
				accountingApi.deleteContactGroupContacts(accessToken,xeroTenantId,newContactGroup.getContactGroups().get(0).getContactGroupID());
				messages.add("Delete All Contacts  to Contact Group - no content in response ");	
				
				ContactGroups oneCg = accountingApi.getContactGroup(accessToken,xeroTenantId,newContactGroup.getContactGroups().get(0).getContactGroupID());
				messages.add("Get ContactGroups - Total Contacts : " + oneCg.getContactGroups().get(0).getContacts().size());
				
				// DELETE Single Contact
				Contacts contactList2 = new Contacts();
				contactList2.addContactsItem(allContacts.getContacts().get(3));
				contactList2.addContactsItem(allContacts.getContacts().get(4));
				
				UUID newContactGroupID = newContactGroup.getContactGroups().get(0).getContactGroupID();
				Contacts addContacts2 = accountingApi.createContactGroupContacts(accessToken,xeroTenantId,newContactGroupID, contactList2);				
				messages.add("Add 2 Contacts to Contact Group - Total : " + addContacts2.getContacts().size());	

				// DELETE Single CONACTS
				accountingApi.deleteContactGroupContact(accessToken,xeroTenantId,newContactGroup.getContactGroups().get(0).getContactGroupID(),allContacts.getContacts().get(3).getContactID());
				messages.add("Delete 1 contact from Contact Group - no content in response");	
				
				ContactGroups oneCg2 = accountingApi.getContactGroup(accessToken,xeroTenantId,newContactGroup.getContactGroups().get(0).getContactGroupID());
				messages.add("Get ContactGroups - Total Contacts : " + oneCg2.getContactGroups().get(0).getContacts().size());
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		
		} else if (object.equals("CreditNotesPDF")) {
			// GET CreditNote As a PDF
			CreditNotes creditNotes = accountingApi.getCreditNotes(accessToken,xeroTenantId,ifModifiedSince, where, order, null);
			UUID creditNoteId = creditNotes.getCreditNotes().get(0).getCreditNoteID();
			ByteArrayInputStream CreditNoteInput = accountingApi.getCreditNoteAsPdf(accessToken,xeroTenantId,creditNoteId, "application/pdf");
			String CreditNoteFileName = "CreditNoteAsPDF.pdf";
			
			String CreditNoteSaveFilePath = saveFile(CreditNoteInput,CreditNoteFileName);
			messages.add("Get CreditNote attachment - save it here: " + CreditNoteSaveFilePath);
		
		} else if (object.equals("CreditNotes")) {
			// CREDIT NOTE
			try {	
				Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
				
				// Create Credit Note
				List<LineItem> lineItems = new ArrayList<>();
				LineItem li = new LineItem();
				li.setAccountCode("400");
				li.setDescription("Foobar");
				li.setQuantity(2.0);
				li.setUnitAmount(20.0);
				lineItems.add(li);
				
				CreditNotes newCNs = new CreditNotes();
				CreditNote cn = new CreditNote();
				cn.setContact(contacts.getContacts().get(0));
				cn.setLineItems(lineItems);  
				cn.setType(com.xero.models.accounting.CreditNote.TypeEnum.ACCPAYCREDIT);
				newCNs.addCreditNotesItem(cn);
				CreditNotes newCreditNote = accountingApi.createCreditNote(accessToken,xeroTenantId,newCNs, summarizeErrors);
				messages.add("Create a CreditNote - Amount : " + newCreditNote.getCreditNotes().get(0).getTotal());
				UUID newCreditNoteId = newCreditNote.getCreditNotes().get(0).getCreditNoteID();
				
				// GET all Credit Note
				CreditNotes creditNotes = accountingApi.getCreditNotes(accessToken,xeroTenantId,ifModifiedSince, where, order, null);
				messages.add("Get all CreditNotes - Total : " + creditNotes.getCreditNotes().size());
				
				// GET One Credit Note
				UUID creditNoteID = creditNotes.getCreditNotes().get(0).getCreditNoteID();
				CreditNotes oneCreditNote = accountingApi.getCreditNote(accessToken,xeroTenantId,creditNoteID);
				messages.add("Get a CreditNote - Amount : " + oneCreditNote.getCreditNotes().get(0).getTotal());
				
				// UPDATE Credit Note
				newCNs.getCreditNotes().get(0).setStatus(com.xero.models.accounting.CreditNote.StatusEnum.AUTHORISED);
				CreditNotes updatedCreditNote = accountingApi.updateCreditNote(accessToken,xeroTenantId,newCreditNoteId, newCNs);				
				messages.add("Update a CreditNote - Ref : " + updatedCreditNote.getCreditNotes().get(0).getReference());
				
				// Allocate Credit Note
				Allocations allocations = new Allocations();
				Allocation allocation = new Allocation();
				
			    where =  "Status==\"AUTHORISED\"&&Type==\"ACCPAY\"";
			    Invoices allInvoices = accountingApi.getInvoices(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp, null);
				Invoice inv = new Invoice();
				inv.setInvoiceID(allInvoices.getInvoices().get(0).getInvoiceID());
				allocation.setInvoice(inv);
				allocation.setAmount(1.0);
				LocalDate currDate = LocalDate.now();
				allocation.setDate(currDate);
				allocations.addAllocationsItem(allocation);
				where = null;
				
				Allocations allocatedCreditNote = accountingApi.createCreditNoteAllocation(accessToken,xeroTenantId,newCreditNoteId,allocations);
				messages.add("Update CreditNote Allocation - Amount : " + allocatedCreditNote.getAllocations().get(0).getAmount());
				
				// Get Invoice History
				HistoryRecords history = accountingApi.getCreditNoteHistory(accessToken,xeroTenantId,creditNoteID);
				messages.add("History - count : " + history.getHistoryRecords().size() );
			
				// Create Invoice History
				HistoryRecords newHistoryRecords = new  HistoryRecords();
				HistoryRecord newHistoryRecord = new  HistoryRecord();
				newHistoryRecord.setDetails("Hello World");
				newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
				
				HistoryRecords newHistory = accountingApi.createCreditNoteHistory(accessToken,xeroTenantId,creditNoteID, newHistoryRecords);
				messages.add("History - note added to  : " + newHistory.getHistoryRecords().get(0).getDetails());
			
			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}
			
		} else if (object.equals("Currencies")) {

			/* CURRENCY  */
			// JSON - incomplete
			try {	
				//Get All
				Currencies currencies = accountingApi.getCurrencies(accessToken,xeroTenantId,where, order);
				messages.add("Get all Currencies - Total : " + currencies.getCurrencies().size());
				
				// Create New
// Error: 400
				/*
				Currency curr = new Currency();
				curr.setCode(CurrencyCode.SGD);
				Currencies currs = new Currencies();
				currs.addCurrenciesItem(curr);
				Currencies newCurrency = accountingApi.createCurrency(currs);
				messages.add("New Currencies - Code : " + newCurrency.getCurrencies().get(0).getCode());
				*/
			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}	
			
		} else if (object.equals("Employees")) {
			//  EMPLOYEE
			try {	
			
				// Create
				Employee employee = new Employee();
				employee.setFirstName("Sam");
				employee.setLastName("Jackson" + loadRandomNum());
				ExternalLink extLink = new ExternalLink();
				extLink.setUrl("http://twitter.com/#!/search/Homer+Simpson");
				employee.setExternalLink(extLink);
				Employees emps = new Employees();
				emps.addEmployeesItem(employee);
				
				Employees newEmployee = accountingApi.createEmployee(accessToken,xeroTenantId,emps);
				messages.add("Create an Employee - Last Name : " + newEmployee.getEmployees().get(0).getLastName());

				//Get All
				Employees employees = accountingApi.getEmployees(accessToken,xeroTenantId,ifModifiedSince, where, order);
				messages.add("Get all Employees - Total : " + employees.getEmployees().size());
				
				UUID newEmpId = employees.getEmployees().get(0).getEmployeeID();
				
				// Get One
				Employees oneEmployee = accountingApi.getEmployee(accessToken,xeroTenantId,newEmpId);
				messages.add("Get one Employees - Name : " + oneEmployee.getEmployees().get(0).getFirstName());

// 404 ERROR
				// Update	No EmployeeID as part of URI - first/last name used as unique key
				ExternalLink extLink2 = new ExternalLink();
				extLink2.setUrl("http://twitter.com/#!/search/Bart+Simpson");
				newEmployee.getEmployees().get(0).setExternalLink(extLink2);
			
				Employees updateEmployee = accountingApi.updateEmployee(accessToken,xeroTenantId,newEmpId, newEmployee);
				messages.add("Update an Employee - Last Name : " + updateEmployee.getEmployees().get(0).getLastName());
	
			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}
			
		} else if (object.equals("ExpenseClaims")) {
			// EXPENSE CLAIM
			try {
				//Create
			    //where = "IsSubscriber==true";
				Users users = accountingApi.getUsers(accessToken,xeroTenantId,ifModifiedSince, where, order);
				where = null;
				
			    where = "ShowInExpenseClaims==true&&Status==\"ACTIVE\"";
				Accounts accounts = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
				where = null;
				
				if (users.getUsers().size() > 0) {
					User user = new User();
					user.setUserID(users.getUsers().get(0).getUserID());
					
					Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
					Contact useContact = new Contact();
					useContact.setContactID(contacts.getContacts().get(0).getContactID());
					
					// CREATE NEW RECEIPT
					Receipts receipts = new Receipts();
					Receipt receipt = new Receipt();
					
					LineItem li = new LineItem();
					li.setAccountCode(accounts.getAccounts().get(0).getCode());
					li.setDescription("Foobar");
					li.setQuantity(2.0);
					li.setUnitAmount(20.00);
					li.setLineAmount(40.00);
					li.setTaxType("NONE");
					
					receipt.addLineitemsItem(li);
					receipt.setUser(user);
					receipt.lineAmountTypes(LineAmountTypes.NOTAX);
					receipt.contact(useContact);
					receipt.setStatus(com.xero.models.accounting.Receipt.StatusEnum.DRAFT);
					receipts.addReceiptsItem(receipt);
					Receipts newReceipts = accountingApi.createReceipt(accessToken,xeroTenantId,receipts);
		
					// CREATE EXPENSE CLAIM
					ExpenseClaims createExpenseClaims = new ExpenseClaims();
					ExpenseClaim expenseClaim = new ExpenseClaim();
					expenseClaim.setUser(user);
					
					Receipts myReceipts = new Receipts();
					Receipt myReceipt = new Receipt();
					myReceipt.setReceiptID(newReceipts.getReceipts().get(0).getReceiptID());
					myReceipts.addReceiptsItem(myReceipt);
					expenseClaim.setReceipts(myReceipts.getReceipts());
					expenseClaim.setStatus(com.xero.models.accounting.ExpenseClaim.StatusEnum.SUBMITTED);
					createExpenseClaims.addExpenseClaimsItem(expenseClaim);
					ExpenseClaims newExpenseClaims = accountingApi.createExpenseClaim(accessToken,xeroTenantId,createExpenseClaims, summarizeErrors);
					messages.add("Create new Expense Claim - Status : " + newExpenseClaims.getExpenseClaims().get(0).getStatus());
					
					// UPDATE EXPENSE CLAIM
					createExpenseClaims.getExpenseClaims().get(0).setStatus(com.xero.models.accounting.ExpenseClaim.StatusEnum.AUTHORISED);
					UUID expenseClaimID = newExpenseClaims.getExpenseClaims().get(0).getExpenseClaimID();
					ExpenseClaims updateExpenseClaims = accountingApi.updateExpenseClaim(accessToken,xeroTenantId,expenseClaimID, createExpenseClaims);
					messages.add("Update new Expense Claim - Status : " + updateExpenseClaims.getExpenseClaims().get(0).getStatus());
					
					//Get All Expense Claims
					ExpenseClaims expenseClaims = accountingApi.getExpenseClaims(accessToken,xeroTenantId,ifModifiedSince, where, order);
					messages.add("Get all Expense Claim - Total : " + expenseClaims.getExpenseClaims().size());
					
					// Get One Expense Claim
					ExpenseClaims oneExpenseClaim = accountingApi.getExpenseClaim(accessToken,xeroTenantId,expenseClaims.getExpenseClaims().get(0).getExpenseClaimID());
					messages.add("Get one Expense Claim - Total : " + oneExpenseClaim.getExpenseClaims().get(0).getStatus());
					
					// VOID EXPENSE CLAIM
					createExpenseClaims.getExpenseClaims().get(0).setStatus(com.xero.models.accounting.ExpenseClaim.StatusEnum.VOIDED);
					ExpenseClaims voidExpenseClaims = accountingApi.updateExpenseClaim(accessToken,xeroTenantId,expenseClaimID, createExpenseClaims);
					messages.add("Void new Expense Claim - Status : " + voidExpenseClaims.getExpenseClaims().get(0).getStatus());
					
					// Get Invoice History
					HistoryRecords history = accountingApi.getExpenseClaimHistory(accessToken,xeroTenantId,expenseClaimID);
					messages.add("History - count : " + history.getHistoryRecords().size() );
				
					// Create Invoice History
					// Error: "The document with the supplied id was not found for this endpoint.
					/*
					HistoryRecords newHistoryRecords = new  HistoryRecords();
					HistoryRecord newHistoryRecord = new  HistoryRecord();
					newHistoryRecord.setDetails("Hello World");
					newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
					HistoryRecords newHistory = accountingApi.createExpenseClaimHistory(expenseClaimID, newHistoryRecords);
					messages.add("History - note added to  : " + newHistory.getHistoryRecords().get(0).getDetails());
					*/
				} else {
					System.out.println("No User Found");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}
		
		} else if (object.equals("Invoices")) {
			//  INVOICE 
		
			// GET Invoice As a PDF
			Invoices myInvoicesForPDF = accountingApi.getInvoices(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp, null);
			UUID invoiceIDForPDF = myInvoicesForPDF.getInvoices().get(0).getInvoiceID();
			ByteArrayInputStream InvoiceNoteInput	 = accountingApi.getInvoiceAsPdf(accessToken,xeroTenantId,invoiceIDForPDF, "application/pdf");
			String InvoiceFileName = "InvoiceAsPDF.pdf";			
			String InvoiceSaveFilePath = saveFile(InvoiceNoteInput,InvoiceFileName);
			messages.add("Get Invoice attachment - save it here: " + InvoiceSaveFilePath);
	
			// Create Invoice
		    where = "Type==\"REVENUE\"";
			Accounts accounts = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
			String accountCodeForInvoice = accounts.getAccounts().get(0).getCode();
			where = null;
			
			Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
			
			UUID contactIDForInvoice = contacts.getContacts().get(0).getContactID();

			for(int i=0; i > contacts.getContacts().size(); i++) {
				String email = contacts.getContacts().get(i).getEmailAddress().toString();
					
				 if ( email != null && !email.isEmpty()) {
					 contactIDForInvoice = contacts.getContacts().get(i).getContactID();
					 break;
				 }
			}
			
			contactIDForInvoice = UUID.fromString("9f857fe0-4a14-408b-b526-4f742db3b079");
			Contact useContact = new Contact();
			useContact.setContactID(contactIDForInvoice);
			
			Invoices newInvoices = new Invoices();
			Invoice myInvoice = new Invoice();
			
			LineItem li = new LineItem();
			li.setAccountCode(accountCodeForInvoice);
			li.setDescription("Acme Tires");
			li.setQuantity(2.0);
			li.setUnitAmount(20.00);
			li.setLineAmount(40.00);
			li.setTaxType("NONE");
			
			myInvoice.addLineItemsItem(li);
			myInvoice.setContact(useContact);
			LocalDate dueDate =  LocalDate.of(2018,Month.DECEMBER,10);
			myInvoice.setDueDate(dueDate);
			LocalDate todayDate =  LocalDate.now();
			myInvoice.setDate(todayDate);
			myInvoice.setType(com.xero.models.accounting.Invoice.TypeEnum.ACCREC);
			myInvoice.setReference("One Fish, Two Fish");
			myInvoice.setStatus(com.xero.models.accounting.Invoice.StatusEnum.AUTHORISED);
			newInvoices.addInvoicesItem(myInvoice);
			
			Invoices newInvoice = accountingApi.createInvoice(accessToken,xeroTenantId,newInvoices, summarizeErrors);
			messages.add("Create invoice - Reference : " + newInvoice.getInvoices().get(0).getReference());
			UUID newInvoiceID = newInvoice.getInvoices().get(0).getInvoiceID();
			Invoices updateInvoices = new Invoices();
			Invoice updateInvoice = new Invoice();
			updateInvoice.setInvoiceID(newInvoiceID);
			updateInvoice.setReference("Red Fish, Blue Fish");
			updateInvoices.addInvoicesItem(updateInvoice);
			
			Invoices updatedInvoice = accountingApi.updateInvoice(accessToken,xeroTenantId,newInvoiceID,updateInvoices);
			messages.add("Update invoice - Reference : " + updatedInvoice.getInvoices().get(0).getReference());
			
			//Get All
			Invoices invoices = accountingApi.getInvoices(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp, null);
			messages.add("Get all invoices - Total : " + invoices.getInvoices().size());
			
			//Get Invoice If-Modified-Since
			OffsetDateTime invModified =  OffsetDateTime.of(LocalDateTime.of(2018, 12, 06, 15, 00), ZoneOffset.UTC);
			Invoices invoicesSince = accountingApi.getInvoices(accessToken,xeroTenantId,invModified, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp, null);
			messages.add("Get all invoices - Since Modfied Date - Total : " + invoicesSince.getInvoices().size());
		
			// Get One
			Invoices oneInvoice = accountingApi.getInvoice(accessToken,xeroTenantId,invoices.getInvoices().get(0).getInvoiceID());
			messages.add("Get one invoice - total : " + oneInvoice.getInvoices().get(0).getTotal());
			LocalDate myDate = oneInvoice.getInvoices().get(0).getDate();
			OffsetDateTime myUTC = oneInvoice.getInvoices().get(0).getUpdatedDateUTC();
	
			// Get Online Invoice
			OnlineInvoices onlineInvoice = accountingApi.getOnlineInvoice(accessToken,xeroTenantId,newInvoiceID);
			messages.add("Get Online invoice - URL : " + onlineInvoice.getOnlineInvoices().get(0).getOnlineInvoiceUrl());

			// Email Invoice
			RequestEmpty empty = new RequestEmpty();
			accountingApi.emailInvoice(accessToken,xeroTenantId,newInvoiceID,empty);
			messages.add("Email invoice - no content in response");

			// Get Invoice History
			HistoryRecords history = accountingApi.getInvoiceHistory(accessToken,xeroTenantId,newInvoiceID);
			messages.add("History - count : " + history.getHistoryRecords().size() );
		
			// Create Invoice History
			HistoryRecords newHistoryRecords = new  HistoryRecords();
			HistoryRecord newHistoryRecord = new  HistoryRecord();
			newHistoryRecord.setDetails("Hello World");
			newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
			HistoryRecords newHistory = accountingApi.createInvoiceHistory(accessToken,xeroTenantId,newInvoiceID,newHistoryRecords);
			messages.add("History - note added to  : " + newHistory.getHistoryRecords().get(0).getDetails());
		
			// CREATE invoice attachment
			statuses = "AUTHORISED";
			Invoices myInvoices = accountingApi.getInvoices(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp, null);
			UUID invoiceID = myInvoices.getInvoices().get(0).getInvoiceID();
			
			File requestBodyFile = new File("/Users/sid.maestre/eclipse-workspace/xero-sdk-oauth2-dev-01/resources/youngsid.jpg");
			String newFileName = requestBodyFile.getName();
			
			Attachments createdAttachments = accountingApi.createInvoiceAttachmentByFileName(accessToken,xeroTenantId,invoiceID, newFileName, requestBodyFile);
			messages.add("Attachment to Invoice complete - ID: " + createdAttachments.getAttachments().get(0).getAttachmentID());
	 		
			// GET Invoice Attachment 
			System.out.println(invoiceID);
			Attachments attachments = accountingApi.getInvoiceAttachments(accessToken,xeroTenantId,invoiceID);
			System.out.println(attachments.getAttachments().get(0).getFileName());
			UUID attachementId = attachments.getAttachments().get(0).getAttachmentID();
			String contentType = attachments.getAttachments().get(0).getMimeType();
			ByteArrayInputStream InvoiceAttachmentInput	 = accountingApi.getInvoiceAttachmentById(accessToken,xeroTenantId,invoiceID,attachementId, contentType);

			String InvoiceAttachmentFileName = attachments.getAttachments().get(0).getFileName();
			String InvoiceAttachmentSaveFilePath = saveFile(InvoiceAttachmentInput,InvoiceAttachmentFileName);
			messages.add("Get Invoice attachment - save it here: " + InvoiceAttachmentSaveFilePath);				
			
		} else if (object.equals("InvoiceReminders")) {
			// INVOICE REMINDER 
			try {				
				InvoiceReminders invReminders = accountingApi.getInvoiceReminders(accessToken,xeroTenantId);
				messages.add("Get a Invoice Reminder - Is Enabled: " + invReminders.getInvoiceReminders().get(0).getEnabled() );
			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}
		} else if (object.equals("Items")) {
			// ITEM 
			try {
				// Create Items
				Items myItems = new Items();
				Item myItem = new Item();
				myItem.setCode("abc" + loadRandomNum());
				myItem.setDescription("foobar");
				myItem.setName("Hello"+loadRandomNum());
				myItems.addItemsItem(myItem);
				Items newItems = accountingApi.createItem(accessToken,xeroTenantId,myItems);
				messages.add("Create new item - Description : " + newItems.getItems().get(0).getDescription());
				UUID newItemId = newItems.getItems().get(0).getItemID();
				
				// Update Item
				newItems.getItems().get(0).setDescription("Barfoo");
				Items updateItem = accountingApi.updateItem(accessToken,xeroTenantId,newItemId, newItems);
				messages.add("Update item - Description : " + updateItem.getItems().get(0).getDescription());
				
				//Get All Items
				Items items = accountingApi.getItems(accessToken,xeroTenantId,ifModifiedSince, where, order, null);
				messages.add("Get all items - Total : " + items.getItems().size());
				
				// Get One Item
				UUID itemId = items.getItems().get(0).getItemID();
				Items oneItem = accountingApi.getItem(accessToken,xeroTenantId,itemId);
				messages.add("Get one item - Description : " + oneItem.getItems().get(0).getDescription());
			
				// Get Invoice History
				HistoryRecords history = accountingApi.getItemHistory(accessToken,xeroTenantId,itemId);
				messages.add("History - count : " + history.getHistoryRecords().size() );
			
				// Create Invoice History
				// Error: "The document with the supplied id was not found for this endpoint.
				/*
				HistoryRecords newHistoryRecords = new  HistoryRecords();
				HistoryRecord newHistoryRecord = new  HistoryRecord();
				newHistoryRecord.setDetails("Hello World");
				newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
				HistoryRecords createdHistory = accountingApi.createItemHistory(itemId,newHistoryRecords);
				messages.add("History - note added to  : " + createdHistory.getHistoryRecords().get(0).getDetails());
				*/
				
				//Delete
				accountingApi.deleteItem(accessToken,xeroTenantId,newItemId);
				messages.add("Delete one item - no content in response");
			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}
			
		} else if (object.equals("Journals")) {
			// JOURNAL 
			try {
					
				boolean paymentsOnly = false;
				// GET all Journals
				Journals journals = accountingApi.getJournals(accessToken,xeroTenantId,ifModifiedSince, null, paymentsOnly);
				messages.add("Get Journals - total : " + journals.getJournals().size());
				
				// GET Journal with offset
				Journals journalsOffset = accountingApi.getJournals(accessToken,xeroTenantId,ifModifiedSince, null, paymentsOnly);
				messages.add("Get Journals offset - total : " + journalsOffset.getJournals().size());
// 404 ERROR
/*
				// GET one Journal
				UUID journalId = journals.getJournals().get(0).getJournalID();
				Journals oneJournal = accountingApi.getJournal(journalId);
				messages.add("Get one Journal - number : " + oneJournal.getJournals().get(0).getJournalNumber());
*/
			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}
		} else if (object.equals("LinkedTransactions")) {

			/* LINKED TRANSACTION */
			try {
				// Create Linked Transaction
			    where = "Type==\"EXPENSE\"";
				Accounts accounts = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
				where = null;
				
				Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
				Contact useContact = new Contact();
				useContact.setContactID(contacts.getContacts().get(0).getContactID());
				
				Invoices newInvoices = new Invoices();
				Invoice myInvoice = new Invoice();
				
				LineItem li = new LineItem();
				li.setAccountCode(accounts.getAccounts().get(0).getCode());
				li.setDescription("Acme Tires");
				li.setQuantity(2.0);
				li.setUnitAmount(20.00);
				li.setLineAmount(40.00);
				li.setTaxType("NONE");
				
				myInvoice.addLineItemsItem(li);
				myInvoice.setContact(useContact);
				LocalDate dueDate =  LocalDate.of(2018,Month.OCTOBER,10);
				myInvoice.setDueDate(dueDate);
				LocalDate todayDate =  LocalDate.now();
				myInvoice.setDate(todayDate);
				myInvoice.setType(com.xero.models.accounting.Invoice.TypeEnum.ACCPAY);
				myInvoice.setReference("One Fish, Two Fish");
				myInvoice.setStatus(com.xero.models.accounting.Invoice.StatusEnum.AUTHORISED);
				newInvoices.addInvoicesItem(myInvoice);
				
				Invoices newInvoice = accountingApi.createInvoice(accessToken,xeroTenantId,newInvoices, summarizeErrors);
				
				UUID sourceTransactionID1 = newInvoice.getInvoices().get(0).getInvoiceID();
				UUID sourceLineItemID1 = newInvoice.getInvoices().get(0).getLineItems().get(0).getLineItemID();
				LinkedTransactions newLinkedTransactions = new LinkedTransactions();
				LinkedTransaction newLinkedTransaction = new LinkedTransaction();
				newLinkedTransaction.setSourceTransactionID(sourceTransactionID1);
				newLinkedTransaction.setSourceLineItemID(sourceLineItemID1);
				newLinkedTransactions.addLinkedTransactionsItem(newLinkedTransaction);
				
				LinkedTransactions createdLinkedTransaction = accountingApi.createLinkedTransaction(accessToken,xeroTenantId,newLinkedTransactions);
				messages.add("Create LinkedTransaction - Status : " + createdLinkedTransaction.getLinkedTransactions().get(0).getStatus());
				
				// Created Linked Transaction 2
				Contact contact = new Contact();
				contact.setName("Foo" + loadRandomNum());
				contact.setEmailAddress("sid" + loadRandomNum() + "@blah.com");
				Contacts newContact = accountingApi.createContact(accessToken,xeroTenantId,contact);
				UUID newContactID = newContact.getContacts().get(0).getContactID();
				
				Invoices newInvoice2 = accountingApi.createInvoice(accessToken,xeroTenantId,newInvoices, summarizeErrors);
				
				UUID sourceTransactionID2 = newInvoice2.getInvoices().get(0).getInvoiceID();
				UUID sourceLineItemID2 = newInvoice2.getInvoices().get(0).getLineItems().get(0).getLineItemID();
				LinkedTransactions newLinkedTransactions2 = new LinkedTransactions();
				LinkedTransaction newLinkedTransaction2 = new LinkedTransaction();
				newLinkedTransaction2.setSourceTransactionID(sourceTransactionID2);
				newLinkedTransaction2.setSourceLineItemID(sourceLineItemID2);
				newLinkedTransaction2.setContactID(newContactID);
				newLinkedTransactions2.addLinkedTransactionsItem(newLinkedTransaction2);
				
				LinkedTransactions createdLinkedTransaction2 = accountingApi.createLinkedTransaction(accessToken,xeroTenantId,newLinkedTransactions2);
				messages.add("Create LinkedTransaction 2 - Status : " + createdLinkedTransaction2.getLinkedTransactions().get(0).getStatus());
				
				// Created Linked Transaction 3
				Invoices newInvoicesAccRec = new Invoices();
				Invoice myInvoiceAccRec = new Invoice();
				
				myInvoiceAccRec.addLineItemsItem(li);
				myInvoiceAccRec.setContact(useContact);
				
				myInvoiceAccRec.setDueDate(dueDate);
				myInvoiceAccRec.setDate(todayDate);
				
				myInvoiceAccRec.setType(com.xero.models.accounting.Invoice.TypeEnum.ACCREC);
				myInvoiceAccRec.setStatus(com.xero.models.accounting.Invoice.StatusEnum.AUTHORISED);
				newInvoicesAccRec.addInvoicesItem(myInvoiceAccRec);
				
				Invoices newInvoiceAccRec = accountingApi.createInvoice(accessToken,xeroTenantId,newInvoicesAccRec, summarizeErrors);
				UUID sourceTransactionID4 = newInvoiceAccRec.getInvoices().get(0).getInvoiceID();
				UUID sourceLineItemID4 = newInvoiceAccRec.getInvoices().get(0).getLineItems().get(0).getLineItemID();
				
				Invoices newInvoice3 = accountingApi.createInvoice(accessToken,xeroTenantId,newInvoices, summarizeErrors);
				
				UUID sourceTransactionID3 = newInvoice3.getInvoices().get(0).getInvoiceID();
				UUID sourceLineItemID3 = newInvoice3.getInvoices().get(0).getLineItems().get(0).getLineItemID();
				LinkedTransactions newLinkedTransactions3 = new LinkedTransactions();
				LinkedTransaction newLinkedTransaction3 = new LinkedTransaction();
				newLinkedTransaction3.setSourceTransactionID(sourceTransactionID3);
				newLinkedTransaction3.setSourceLineItemID(sourceLineItemID3);
				newLinkedTransaction3.setContactID(useContact.getContactID());
				newLinkedTransaction3.setTargetTransactionID(sourceTransactionID4);
				newLinkedTransaction3.setTargetLineItemID(sourceLineItemID4);
				newLinkedTransactions3.addLinkedTransactionsItem(newLinkedTransaction3);
				
				LinkedTransactions createdLinkedTransaction3 = accountingApi.createLinkedTransaction(accessToken,xeroTenantId,newLinkedTransactions3);
				messages.add("Create LinkedTransaction 3 - Status : " + createdLinkedTransaction3.getLinkedTransactions().get(0).getStatus());
				
				// GET all Link Transactions
				
				int page = 1;
				String linkedTransactionID = null;
				String sourceTransactionID = null;
				String targetTransactionID = null;
				String status = null;
				String contactID = null;
				LinkedTransactions linkTransactions = accountingApi.getLinkedTransactions(accessToken,xeroTenantId,page, linkedTransactionID, sourceTransactionID, contactID, status, targetTransactionID);
				messages.add("Get Link Transactions - total : " + linkTransactions.getLinkedTransactions().size());
				
				// GET all Link Transactions
				UUID linkedTransactionID2 = linkTransactions.getLinkedTransactions().get(0).getLinkedTransactionID();
				LinkedTransactions oneLinkTransaction = accountingApi.getLinkedTransaction(accessToken,xeroTenantId,linkedTransactionID2);
				messages.add("Get one Link Transaction - Status : " + oneLinkTransaction.getLinkedTransactions().get(0).getStatus());
// 500 Error				
				/*
				// DELETE LINKEDTRANSACTION
				UUID newLinkedTransactionID = createdLinkedTransaction.getLinkedTransactions().get(0).getLinkedTransactionID();
				accountingApi.deleteLinkedTransaction(newLinkedTransactionID);
				messages.add("Delete LinkedTransaction - no content in response");
				*/
			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}
		
		} else if (object.equals("ManualJournals")) {
			// MANUAL JOURNAL  
			try {
				// Create Manual Journal
			    where = "Type==\"EXPENSE\" && Status ==\"ACTIVE\"";
				Accounts accounts = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
				String accountCode = accounts.getAccounts().get(0).getCode();
				where = null;
				ManualJournals manualJournals = new ManualJournals();
				ManualJournal manualJournal = new ManualJournal();
				LocalDate currDate = LocalDate.now();
				manualJournal.setDate(currDate);
				manualJournal.setNarration("Foo bar");
				
				ManualJournalLine credit = new ManualJournalLine();
				credit.setDescription("Hello there");
				credit.setAccountCode(accountCode);
				credit.setLineAmount(100.00);
				manualJournal.addJournalLinesItem(credit);
				
				ManualJournalLine debit = new ManualJournalLine();
				debit.setDescription("Goodbye");
				debit.setAccountCode(accountCode);
				debit.setLineAmount(-100.00);
				manualJournal.addJournalLinesItem(debit);
				manualJournals.addManualJournalsItem(manualJournal);
				ManualJournals createdManualJournals = accountingApi.createManualJournal(accessToken,xeroTenantId,manualJournals);
				UUID newManualJournalId = createdManualJournals.getManualJournals().get(0).getManualJournalID();
				messages.add("Create Manual Journal - Narration : " + createdManualJournals.getManualJournals().get(0).getNarration());
				
				// GET all Manual Journal
				ManualJournals getManualJournals = accountingApi.getManualJournals(accessToken,xeroTenantId,ifModifiedSince, where, order, null);
				messages.add("Get Manual Journal - total : " + getManualJournals.getManualJournals().size());
				
				// GET one Manual Journal
				UUID manualJournalId = getManualJournals.getManualJournals().get(0).getManualJournalID();			
				ManualJournals oneManualJournal = accountingApi.getManualJournal(accessToken,xeroTenantId,manualJournalId);
				messages.add("Get one Manual Journal - Narration : " + oneManualJournal.getManualJournals().get(0).getNarration());
				
				// Update Manual Journal
				ManualJournals updateManualJournals = new ManualJournals();
				ManualJournal updateManualJournal = new ManualJournal();
				updateManualJournal.setManualJournalID(newManualJournalId);
				updateManualJournal.setNarration("Hello Xero");
				updateManualJournals.addManualJournalsItem(updateManualJournal);
				ManualJournals updatedManualJournal = accountingApi.updateManualJournal(accessToken,xeroTenantId,newManualJournalId,updateManualJournals);
				messages.add("Update Manual Journal - Narration : " + updatedManualJournal.getManualJournals().get(0).getNarration());
				
			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}
			
        } else if (object.equals("Organisations")) {
			// Organisation 
			try {
				Organisations organisations = accountingApi.getOrganisations(accessToken,xeroTenantId);
				messages.add("Get a Organisation - Name : " + organisations.getOrganisations().get(0).getName());
			} catch (Exception e) {
				
				System.out.println("ERROR");
				System.out.println(e.getMessage());	
			}		
			
			
     
		} else if (object.equals("Overpayments")) {
			// OVERPAYMENT 
		    where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
			Accounts accountsWhere = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
			Account bankAccount = new Account();
			bankAccount.setAccountID(accountsWhere.getAccounts().get(0).getAccountID());
			where = null;
			
		    where = "SystemAccount==\"DEBTORS\"";
			Accounts arAccounts = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
			Account arAccount = arAccounts.getAccounts().get(0);
			where = null;
			
			Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
			Contact useContact = new Contact();
			useContact.setContactID(contacts.getContacts().get(0).getContactID());
			
			// Maker sure we have at least 2 banks
			if(accountsWhere.getAccounts().size() > 0) {	
				List<LineItem> lineItems = new ArrayList<>();
				LineItem li = new LineItem();
				li.setAccountCode(arAccount.getCode());
				li.setDescription("Foobar");
				li.setQuantity(1.0);
				li.setUnitAmount(20.00);
				lineItems.add(li);
				
				BankTransaction bt = new BankTransaction();
				bt.setBankAccount(bankAccount);
				bt.setContact(useContact);
				bt.setLineitems(lineItems);
				bt.setType(com.xero.models.accounting.BankTransaction.TypeEnum.RECEIVE_OVERPAYMENT);
				BankTransactions bts = new BankTransactions();
				bts.addBankTransactionsItem(bt);					
				BankTransactions newBankTransaction = accountingApi.createBankTransaction(accessToken,xeroTenantId,bts, summarizeErrors);
				
				Overpayments overpayments = accountingApi.getOverpayments(accessToken,xeroTenantId,ifModifiedSince, where, order, null, null);
				messages.add("Get a Overpayments - Count : " + overpayments.getOverpayments().size());
				
				if(overpayments.getOverpayments().size() > 0) {	
					UUID overpaymentId = overpayments.getOverpayments().get(2).getOverpaymentID();
					Overpayments oneOverpayment = accountingApi.getOverpayment(accessToken,xeroTenantId,overpaymentId);
					messages.add("Get one Overpayment - Total : " + oneOverpayment.getOverpayments().get(0).getTotal());					
					
				    where = "Status==\"AUTHORISED\"&&Type==\"ACCREC\"";					
					Invoices allInvoices = accountingApi.getInvoices(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp, null);
					Invoice inv = new Invoice();
					inv.setInvoiceID(allInvoices.getInvoices().get(0).getInvoiceID());
					where = null;
					
					Allocations allocations = new Allocations();
					Allocation allocation = new Allocation();
					allocation.setAmount(1.0);
					LocalDate currDate = LocalDate.now();
					allocation.setDate(currDate);
					allocation.setInvoice(inv);
					allocations.addAllocationsItem(allocation);
					
					Allocations newAllocation = accountingApi.createOverpaymentAllocation(accessToken,xeroTenantId,overpaymentId, allocations);
					messages.add("Create OverPayment allocation - Amt : " + newAllocation.getAllocations().get(0).getAmount());					
				
					// Get History
					HistoryRecords history = accountingApi.getOverpaymentHistory(accessToken,xeroTenantId,overpaymentId);
					messages.add("History - count : " + history.getHistoryRecords().size() );
				
					// Create  History
					// Error: "The document with the supplied id was not found for this endpoint.					
					/*
					HistoryRecords newHistoryRecords = new  HistoryRecords();
					HistoryRecord newHistoryRecord = new  HistoryRecord();
					newHistoryRecord.setDetails("Hello World");
					newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
					HistoryRecords createdHistory = accountingApi.createOverpaymentHistory(overpaymentId,newHistoryRecords);
					messages.add("History - note added to  : " + createdHistory.getHistoryRecords().get(0).getDetails());
					*/
				}
			}
	
		} else if (object.equals("Payments")) {
			/* Payment 	*/
			// CREATE payment
			where =  "Status==\"AUTHORISED\"&&Type==\"ACCREC\"";			
			Invoices allInvoices = accountingApi.getInvoices(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp, null);
			Invoice inv = new Invoice();
			inv.setInvoiceID(allInvoices.getInvoices().get(0).getInvoiceID());
			where = null;
		
		    where = "EnablePaymentsToAccount==true";
			Accounts accountsWhere = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
			Account paymentAccount = new Account();
			paymentAccount.setCode(accountsWhere.getAccounts().get(0).getCode());
			where = null;
			
			Payments createPayments = new Payments();
			Payment createPayment = new Payment();
			createPayment.setAccount(paymentAccount);
			createPayment.setInvoice(inv);
			createPayment.setAmount(1.00);
	
			LocalDate currDate = LocalDate.now();
			createPayment.setDate(currDate);
			createPayments.addPaymentsItem(createPayment);
			
			Payments newPayments = accountingApi.createPayment(accessToken,xeroTenantId,createPayments);
			messages.add("Create Payments - Amt : " + newPayments.getPayments().get(0).getAmount());					
			
			// GET all Payments
			Payments payments = accountingApi.getPayments(accessToken,xeroTenantId,ifModifiedSince, where, order);
			messages.add("Get Payments - Total : " + payments.getPayments().size());					
			
			// GET one Payment
			UUID paymentID = payments.getPayments().get(0).getPaymentID();
			Payments onePayment = accountingApi.getPayment(accessToken,xeroTenantId,paymentID);
			messages.add("Get Payments - Amount : " + onePayment.getPayments().get(0).getAmount());		
			
			// Get  History
			HistoryRecords allHistory = accountingApi.getPaymentHistory(accessToken,xeroTenantId,paymentID);
			messages.add("History - count : " + allHistory.getHistoryRecords().size() );
		
			// Create History
			/*
			HistoryRecords newHistoryRecords = new  HistoryRecords();
			HistoryRecord newHistoryRecord = new  HistoryRecord();
			newHistoryRecord.setDetails("Hello World");
			newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);			
			HistoryRecords newHistory = accountingApi.createPaymentHistory(paymentID,newHistoryRecords);
			messages.add("History - note added to  : " + newHistory.getHistoryRecords().get(0).getDetails());
			*/		
		} else if (object.equals("PaymentServices")) {
			// Payment Services	
			try {
				// CREATE PaymentService
				PaymentServices newPaymentServices = new PaymentServices();
				PaymentService newPaymentService = new PaymentService();
				newPaymentService.setPaymentServiceName("PayUp"+ loadRandomNum());
				newPaymentService.setPaymentServiceUrl("https://www.payupnow.com/");
				newPaymentService.setPayNowText("Time To PayUp");
				newPaymentServices.addPaymentServicesItem(newPaymentService);
				PaymentServices createdPaymentService = accountingApi.createPaymentService(accessToken,xeroTenantId,newPaymentServices);
				messages.add("Create PaymentServices - name : " + createdPaymentService.getPaymentServices().get(0).getPaymentServiceName());				
			
				// GET all Payments
				PaymentServices paymentServices = accountingApi.getPaymentServices(accessToken,xeroTenantId);
				messages.add("Get PaymentServices - Total : " + paymentServices.getPaymentServices().size());					
			} catch (Exception e) {
				System.out.println(e.getMessage());	
			}
		} else if (object.equals("Prepayments")) {
			/* PREPAYMENT */
		    where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
			Accounts accountsWhere = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
			Account bankAccount = new Account();
			bankAccount.setAccountID(accountsWhere.getAccounts().get(0).getAccountID());
			where = null;
			
		    where = "Type==\"EXPENSE\"";
			Accounts arAccounts = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
			Account arAccount = arAccounts.getAccounts().get(0);
			where = null;
			
			Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
			Contact useContact = new Contact();
			useContact.setContactID(contacts.getContacts().get(0).getContactID());
			
			// Maker sure we have at least 2 banks
			if(accountsWhere.getAccounts().size() > 0) {	
				List<LineItem> lineItems = new ArrayList<>();
				LineItem li = new LineItem();
				li.setAccountCode(arAccount.getCode());
				li.setDescription("Foobar");
				li.setQuantity(1.0);
				li.setTaxType("NONE");
				li.setUnitAmount(20.00);
				lineItems.add(li);
				
				BankTransaction bt = new BankTransaction();
				bt.setBankAccount(bankAccount);
				bt.setContact(useContact);
				bt.setLineitems(lineItems);
				bt.setType(com.xero.models.accounting.BankTransaction.TypeEnum.RECEIVE_PREPAYMENT);
				BankTransactions bts = new BankTransactions();
				bts.addBankTransactionsItem(bt);					
				BankTransactions newBankTransaction = accountingApi.createBankTransaction(accessToken,xeroTenantId,bts, summarizeErrors);
				where =  "Status==\"AUTHORISED\" && TYPE==\"RECEIVE-PREPAYMENT\"";
				Prepayments prepayments = accountingApi.getPrepayments(accessToken,xeroTenantId,ifModifiedSince, where, order, null, null);
				messages.add("Get a Prepayments - Count : " + prepayments.getPrepayments().size());
				where = null;
				if(prepayments.getPrepayments().size() > 0) {	
					UUID prepaymentId = prepayments.getPrepayments().get(0).getPrepaymentID();
					Prepayments onePrepayment = accountingApi.getPrepayment(accessToken,xeroTenantId,prepaymentId);
					messages.add("Get one Prepayment - Total : " + onePrepayment.getPrepayments().get(0).getTotal());
				    where = "Status==\"AUTHORISED\"&&Type==\"ACCREC\"";					
					Invoices allInvoices = accountingApi.getInvoices(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp, null);
					Invoice inv = new Invoice();
					inv.setInvoiceID(allInvoices.getInvoices().get(0).getInvoiceID());
					where = null;
					
					Allocations allocations = new Allocations();
					Allocation allocation = new Allocation();
					allocation.setAmount(1.0);
					LocalDate currDate = LocalDate.now();
					allocation.setDate(currDate);
					allocation.setInvoice(inv);
					allocations.addAllocationsItem(allocation);
					
					//Allocations newAllocation = accountingApi.createPrepaymentAllocation(prepaymentId, allocations);
					//messages.add("Create PrePayment allocation - Amt : " + newAllocation.getAllocations().get(0).getAmount());		
					
					// Get History
					HistoryRecords history = accountingApi.getPrepaymentHistory(accessToken,xeroTenantId,prepaymentId);
					messages.add("History - count : " + history.getHistoryRecords().size() );
				
					// Create  History
					// Error: "The document with the supplied id was not found for this end point.					
					/*
					HistoryRecords newHistoryRecords = new  HistoryRecords();
					HistoryRecord newHistoryRecord = new  HistoryRecord();
					newHistoryRecord.setDetails("Hello World");
					newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
					HistoryRecords createdHistory = accountingApi.createPrepaymentHistory(prepaymentId,newHistoryRecords);
					messages.add("History - note added to  : " + createdHistory.getHistoryRecords().get(0).getDetails());	
					*/
				}
			}
		} else if (object.equals("PurchaseOrders")) {
			// PURCHASE ORDERS 
			try {
				// CREATE Purchase Order
			    where = "Type==\"EXPENSE\"";
				Accounts arAccounts = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
				Account arAccount = arAccounts.getAccounts().get(0);
				where = null;
				
				PurchaseOrders purchaseOrders = new PurchaseOrders();
				PurchaseOrder purchaseOrder = new PurchaseOrder();
				LocalDate currDate = LocalDate.now();
				purchaseOrder.setDate(currDate);
				Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
				Contact useContact = new Contact();
				useContact.setContactID(contacts.getContacts().get(0).getContactID());
				purchaseOrder.setContact(useContact);
				
				List<LineItem> lineItems = new ArrayList<>();
				LineItem li = new LineItem();
				li.setAccountCode(arAccount.getCode());
				li.setDescription("Foobar");
				li.setQuantity(1.0);
				li.setUnitAmount(20.00);
				lineItems.add(li);
				purchaseOrder.setLineItems(lineItems);
				purchaseOrders.addPurchaseOrdersItem(purchaseOrder);
				PurchaseOrders createdPurchaseOrders = accountingApi.createPurchaseOrder(accessToken,xeroTenantId,purchaseOrders, summarizeErrors);
				messages.add("Create Purchase order - total : " + createdPurchaseOrders.getPurchaseOrders().get(0).getTotal());					
			
				// UPDATE Purchase Orders
				UUID newPurchaseOrderID = createdPurchaseOrders.getPurchaseOrders().get(0).getPurchaseOrderID();
				createdPurchaseOrders.getPurchaseOrders().get(0).setAttentionTo("Jimmy");
				PurchaseOrders updatePurchaseOrders = accountingApi.updatePurchaseOrder(accessToken,xeroTenantId,newPurchaseOrderID, createdPurchaseOrders);
				messages.add("Update Purchase order - attn : " + updatePurchaseOrders.getPurchaseOrders().get(0).getAttentionTo());								
				
				// GET Purchase Orders
				String status = null;
				String dateFrom = null;
				String dateTo = null;
				PurchaseOrders allPurchaseOrders = accountingApi.getPurchaseOrders(accessToken,xeroTenantId,ifModifiedSince, status, dateFrom, dateTo, order, null);
				messages.add("Get Purchase orders - Count : " + allPurchaseOrders.getPurchaseOrders().size());					
			
				// GET one Purchase Order
				UUID purchaseOrderID = allPurchaseOrders.getPurchaseOrders().get(0).getPurchaseOrderID();
				PurchaseOrders onePurchaseOrder = accountingApi.getPurchaseOrder(accessToken,xeroTenantId,purchaseOrderID);
				messages.add("Get one Purchase order - Total : " + onePurchaseOrder.getPurchaseOrders().get(0).getTotal());					
				
				// DELETE Purchase Orders
				createdPurchaseOrders.getPurchaseOrders().get(0).setStatus(com.xero.models.accounting.PurchaseOrder.StatusEnum.DELETED);
				PurchaseOrders deletePurchaseOrders = accountingApi.updatePurchaseOrder(accessToken,xeroTenantId,newPurchaseOrderID, createdPurchaseOrders);
				messages.add("Delete Purchase order - Status : " + deletePurchaseOrders.getPurchaseOrders().get(0).getStatus());								
				
				// Get History
				HistoryRecords history = accountingApi.getInvoiceHistory(accessToken,xeroTenantId,purchaseOrderID);
				messages.add("History - count : " + history.getHistoryRecords().size() );
			
				// Create History
				HistoryRecords newHistoryRecords = new  HistoryRecords();
				HistoryRecord newHistoryRecord = new  HistoryRecord();
				newHistoryRecord.setDetails("Hello World");
				newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
				HistoryRecords newHistory = accountingApi.createPurchaseOrderHistory(accessToken,xeroTenantId,purchaseOrderID,newHistoryRecords);
				messages.add("History - note added to  : " + newHistory.getHistoryRecords().get(0).getDetails());
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Receipts")) {
			/* RECEIPTS */
			try {
				//Create
//			    where = "IsSubscriber==true";
				Users users = accountingApi.getUsers(accessToken,xeroTenantId,ifModifiedSince, where, order);
				where = null;
				
			    where = "ShowInExpenseClaims==true && Status==\"ACTIVE\"";
				Accounts accounts = accountingApi.getAccounts(accessToken,xeroTenantId,ifModifiedSince, where, order);
				where = null;
				
				User useUser = new User();
				useUser.setUserID(users.getUsers().get(0).getUserID());
					
				Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
				Contact useContact = new Contact();
				useContact.setContactID(contacts.getContacts().get(0).getContactID());
					
				// CREATE NEW RECEIPT
				Receipts receipts = new Receipts();
				Receipt receipt = new Receipt();
				
				LineItem li = new LineItem();
				li.setAccountCode(accounts.getAccounts().get(0).getCode());
				li.setDescription("Foobar");
				li.setQuantity(2.0);
				li.setUnitAmount(20.00);
				li.setLineAmount(40.00);
				li.setTaxType("NONE");
				
				receipt.addLineitemsItem(li);
				receipt.setUser(useUser);
				receipt.lineAmountTypes(LineAmountTypes.NOTAX);
				receipt.contact(useContact);
				receipt.setStatus(com.xero.models.accounting.Receipt.StatusEnum.DRAFT);
				receipts.addReceiptsItem(receipt);
				Receipts newReceipts = accountingApi.createReceipt(accessToken,xeroTenantId,receipts);
				messages.add("Create Receipts - Total : " + newReceipts.getReceipts().get(0).getTotal());								
				
				// UPDATE Receipts
				UUID newReceiptId = newReceipts.getReceipts().get(0).getReceiptID();
				newReceipts.getReceipts().get(0).setReference("Foobar");
				Receipts updateReceipts = accountingApi.updateReceipt(accessToken,xeroTenantId,newReceiptId, newReceipts);
				messages.add("Create Receipts - Ref : " + updateReceipts.getReceipts().get(0).getReference());								
				
				// GET all Receipts
				Receipts allReceipts = accountingApi.getReceipts(accessToken,xeroTenantId,ifModifiedSince, where, order, null);
				messages.add("Create Receipts - Count : " + allReceipts.getReceipts().size());								
				
				// GET one Receipts
				UUID receiptID = allReceipts.getReceipts().get(0).getReceiptID();
				Receipts oneReceipts = accountingApi.getReceipt(accessToken,xeroTenantId,receiptID);
				messages.add("Create Receipts - Total : " + oneReceipts.getReceipts().get(0).getTotal());								
				
				// Get History
				HistoryRecords history = accountingApi.getReceiptHistory(accessToken,xeroTenantId,receiptID);
				messages.add("History - count : " + history.getHistoryRecords().size() );
			
				// Create History
				// Error: "The document with the supplied id was not found for this endpoint.
				/*
				HistoryRecords newHistoryRecords = new  HistoryRecords();
				HistoryRecord newHistoryRecord = new  HistoryRecord();
				newHistoryRecord.setDetails("Hello World");
				newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
				HistoryRecords newHistory = accountingApi.createReceiptHistory(receiptID, newHistoryRecords);
				messages.add("History - note added to  : " + newHistory.getHistoryRecords().get(0).getDetails());
				*/
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}					
		} else if (object.equals("RepeatingInvoices")) {
	        /* REPEATING INVOICE */
			try {
				// GET all Repeating Invoices
				RepeatingInvoices repeatingInvoices = accountingApi.getRepeatingInvoices(accessToken,xeroTenantId,where, order);
				if ( repeatingInvoices.getRepeatingInvoices().size() > 0) {
					messages.add("Repeating Invoice - count : " + repeatingInvoices.getRepeatingInvoices().size() );
					
					// GET one Repeating Invoices
					UUID repeatingInvoiceID = repeatingInvoices.getRepeatingInvoices().get(0).getRepeatingInvoiceID();
					RepeatingInvoices repeatingInvoice = accountingApi.getRepeatingInvoice(accessToken,xeroTenantId,repeatingInvoiceID);
					messages.add("Repeating Invoice - total : " + repeatingInvoice.getRepeatingInvoices().get(0).getTotal());
				
					// Get History
					HistoryRecords history = accountingApi.getRepeatingInvoiceHistory(accessToken,xeroTenantId,repeatingInvoiceID);
					messages.add("History - count : " + history.getHistoryRecords().size() );
					
					// Create History
					// Error: "The document with the supplied id was not found for this endpoint.
					/*
					HistoryRecords newHistoryRecords = new  HistoryRecords();
					HistoryRecord newHistoryRecord = new  HistoryRecord();
					newHistoryRecord.setDetails("Hello World");
					newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
					HistoryRecords newHistory = accountingApi.createRepeatingInvoiceHistory(repeatingInvoiceID,  newHistoryRecords);
					messages.add("History - note added to  : " + newHistory.getHistoryRecords().get(0).getDetails());
					*/
				} else {
					messages.add("Zero repeating Invoices found" );
					
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Reports")) {
			
			/* REPORTS */			
			/*
			// TenNinetyNine - US Only
			String reportYear = null;
			Reports reports = accountingApi.getReportTenNinetyNine(reportYear);
			System.out.println(reports.toString());
		    */
			// AgedPayablesByContact
			String date = null;
			String fromDate = null;
			String toDate = null;
			String profitLossTimeframe = null;
			String trackingOptionID1 = null;
			String trackingOptionID2 = null;
			boolean standardLayout = false;
			boolean paymentsOnly = false;
			String trackingCategoryID = null;
			String trackingCategoryID2 = null;
			String trackingOptionID = null;
			
			Contacts contacts = accountingApi.getContacts(accessToken,xeroTenantId,ifModifiedSince, where, order, ids, null, includeArchived);
			UUID contactId = contacts.getContacts().get(0).getContactID();
			LocalDate xDate = LocalDate.now();
			LocalDate xFromDate = LocalDate.now();
			LocalDate xToDate = LocalDate.now();
			
			ReportWithRows reportAgedPayablesByContact = accountingApi.getReportAgedPayablesByContact(accessToken,xeroTenantId,contactId, xDate, xFromDate, xToDate);
			messages.add("Get a Reports - Name:" + reportAgedPayablesByContact.getReports().get(0).getReportName());
			
			// AgedReceivablesByContact
			ReportWithRows reportAgedReceivablesByContact = accountingApi.getReportAgedReceivablesByContact(accessToken,xeroTenantId,contactId, xDate, xFromDate, xToDate);
			messages.add("Get a Reports - Name:" + reportAgedReceivablesByContact.getReports().get(0).getReportName());
			
			// reportBalanceSheet
			ReportWithRows reportBalanceSheet = accountingApi.getReportBalanceSheet(accessToken,xeroTenantId,toDate, 3, "MONTH", trackingOptionID1, trackingOptionID2, standardLayout, paymentsOnly);
			messages.add("Get a Reports - Name:" + reportBalanceSheet.getReports().get(0).getReportName());
			
			// reportBankSummary
			ReportWithRows reportBankSummary = accountingApi.getReportBankSummary(accessToken,xeroTenantId,xToDate, null, null);
			messages.add("Get a Reports - Name:" + reportBankSummary.getReports().get(0).getReportName());
			
			// reportBASorGSTlist - AU and NZ only
			ReportWithRows reportTax = accountingApi.getReportBASorGSTList(accessToken,xeroTenantId);
			System.out.println(reportTax.toString());
			
			// reportBudgetSummary
			int budgetPeriod = 1;
			int budgetTimeframe = 3;
			ReportWithRows reportBudgetSummary = accountingApi.getReportBudgetSummary(accessToken,xeroTenantId,xToDate, budgetPeriod, budgetTimeframe);
			messages.add("Get a Reports - Name:" + reportBudgetSummary.getReports().get(0).getReportName());
			
			// reportExecutiveSummary
			ReportWithRows reportExecutiveSummary = accountingApi.getReportExecutiveSummary(accessToken,xeroTenantId,xToDate);
			messages.add("Get a Reports - Name:" + reportExecutiveSummary.getReports().get(0).getReportName());
			
			// reportProfitandLoss
			fromDate = "2018-01-01";
		    toDate = "2018-12-31";
		    profitLossTimeframe = "MONTH";
		    standardLayout = true;
		    paymentsOnly = false;
			ReportWithRows reportProfitLoss = accountingApi.getReportProfitAndLoss(accessToken,xeroTenantId,xFromDate, xToDate, null, profitLossTimeframe, trackingCategoryID, trackingCategoryID2, trackingOptionID, trackingOptionID2, standardLayout, paymentsOnly);
			messages.add("Get a Reports - Name:" + reportProfitLoss.getReports().get(0).getReportName());
			fromDate = null;
		    toDate = null;
						
			// reportTrialBalance
			ReportWithRows reportTrialBalance = accountingApi.getReportTrialBalance(accessToken,xeroTenantId,xToDate, paymentsOnly);
			messages.add("Get a Reports - Name:" + reportTrialBalance.getReports().get(0).getReportName());
		
		} else if (object.equals("TrackingCategories")) {
			// TRACKING CATEGORIES 
			try {
				// GET Tracking Categories
				TrackingCategories trackingCategories = accountingApi.getTrackingCategories(accessToken,xeroTenantId,where, order, includeArchived);
				int count = trackingCategories.getTrackingCategories().size();
				
				if (count == 2) {
					//DELETE Tracking Categories
					UUID trackingCategoryID = trackingCategories.getTrackingCategories().get(0).getTrackingCategoryID();
					TrackingCategories deletedTrackingCategories = accountingApi.deleteTrackingCategory(accessToken,xeroTenantId,trackingCategoryID);
					messages.add("DELETED tracking categories - status : " + deletedTrackingCategories.getTrackingCategories().get(0).getStatus());					
				}
				
				// CREATE  Tracking Categories
				TrackingCategory newTrackingCategory = new TrackingCategory();
				newTrackingCategory.setName("Foo"+ loadRandomNum());
				TrackingCategories createdTrackingCategories = accountingApi.createTrackingCategory(accessToken,xeroTenantId,newTrackingCategory);
				messages.add("CREATED tracking categories - name : " + createdTrackingCategories.getTrackingCategories().get(0).getName());					
			
				// UPDATE  Tracking Categories
				UUID newTrackingCategoryID = createdTrackingCategories.getTrackingCategories().get(0).getTrackingCategoryID();
				newTrackingCategory.setName("Foo"+ loadRandomNum());
				TrackingCategories updatedTrackingCategories = accountingApi.updateTrackingCategory(accessToken,xeroTenantId,newTrackingCategoryID,newTrackingCategory);
				messages.add("UPDATED tracking categories - name : " + updatedTrackingCategories.getTrackingCategories().get(0).getName());					
				
				// GET one Tracking Categories
				if (count > 0) 
				{
					UUID oneTrackingCategoryID = trackingCategories.getTrackingCategories().get(0).getTrackingCategoryID();
					TrackingCategories oneTrackingCategories = accountingApi.getTrackingCategory(accessToken,xeroTenantId,oneTrackingCategoryID);
					messages.add("GET ONE tracking categories - name : " + oneTrackingCategories.getTrackingCategories().get(0).getName());
					
					// Create one Option
					TrackingOption option = new TrackingOption();
					option.setName("Bar"+ loadRandomNum());
					TrackingOptions newTrackingOptions = accountingApi.createTrackingOptions(accessToken,xeroTenantId,oneTrackingCategoryID,option);
					messages.add("CREATE option - name : " + newTrackingOptions.getOptions().get(0).getName());
					
					// DELETE All options
					//UUID newOptionId = newTrackingOptions.getOptions().get(0).getTrackingOptionID();                                 
					//TrackingOptions deleteOptions = accountingApi.deleteTrackingOptions(oneTrackingCategoryID, newOptionId);
					//messages.add("DELETE one option - Status : " + deleteOptions.getOptions().get(0).getStatus());
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		
		} else if (object.equals("TaxRates")) {
			// TAX RATE
			try {
				// CREATE Tax Rate
				TaxRates newTaxRates = new TaxRates();
				TaxRate newTaxRate = new TaxRate();
				TaxComponent rate01 = new TaxComponent();
				rate01.setName("State Tax");
				rate01.setRate(2.25);
				newTaxRate.setName("SDKTax"+ loadRandomNum());
				newTaxRate.addTaxComponentsItem(rate01);
				newTaxRates.addTaxRatesItem(newTaxRate);
				
				TaxRates createdTaxRate = accountingApi.createTaxRate(accessToken,xeroTenantId,newTaxRates);
				messages.add("CREATE TaxRate - name : " + createdTaxRate.getTaxRates().get(0).getName());
				
				// UDPATE Tax Rate
				newTaxRates.getTaxRates().get(0).setStatus(com.xero.models.accounting.TaxRate.StatusEnum.DELETED);
				TaxRates updatedTaxRate = accountingApi.updateTaxRate(accessToken,xeroTenantId,newTaxRates);
				messages.add("UPDATED TaxRate - status : " + updatedTaxRate.getTaxRates().get(0).getStatus());
				
				// GET Tax Rate
				String taxType = null;
				TaxRates taxRates = accountingApi.getTaxRates(accessToken,xeroTenantId,where, order, taxType);
				messages.add("GET TaxRate - cnt : " + taxRates.getTaxRates().size());
				
				// GET Tax Rate
			    taxType = "CAPEXINPUT2";
				TaxRates taxRatesByType = accountingApi.getTaxRates(accessToken,xeroTenantId,where, order, taxType);
				messages.add("GET TaxRate by Cap Purchase Type : " + taxRatesByType.getTaxRates().size());
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}	
		} else if (object.equals("Users")) {
			// USER
			try {
				// GET Users
				Users users = accountingApi.getUsers(accessToken,xeroTenantId,ifModifiedSince, where, order);
				messages.add("GET Users - cnt : " + users.getUsers().size());
				
				//GET One User
				UUID userID = users.getUsers().get(0).getUserID();
				Users user = accountingApi.getUser(accessToken,xeroTenantId,userID);
				messages.add("GET Users - First Name : " + user.getUsers().get(0).getFirstName());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		for (int i = 0; i < messages.size(); i++) {
			System.out.println(messages.get(i));
			respWriter.println("<p>" + messages.get(i) + "</p>");
		}
		
		respWriter.println("<hr>end processing request<hr><div class=\"form-group\">");
		respWriter.println("</div></div>");
	}
	
	protected void addToMapIfNotNull(Map<String, String> map, String key, Object value) {
        if (value != null) {
            map.put(key, value.toString());
        }
    }
	
	protected String saveFile(ByteArrayInputStream input, String fileName) {
		String saveFilePath = null;
		File f = new File("./");
		String dirPath;
		try {
			dirPath = f.getCanonicalPath();
		
			FileOutputStream output = new FileOutputStream(fileName);
	
			int DEFAULT_BUFFER_SIZE = 1024;
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int n = 0;
			n = input.read(buffer, 0, DEFAULT_BUFFER_SIZE);
			while (n >= 0) {
			   output.write(buffer, 0, n);
			   n = input.read(buffer, 0, DEFAULT_BUFFER_SIZE);
			}
			input.close();
			output.close();
			
			saveFilePath = dirPath + File.separator + fileName;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return saveFilePath;
	}
	
	public static int loadRandomNum() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100000);
		return randomInt;
	}
}
