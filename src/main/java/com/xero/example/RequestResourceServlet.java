package com.xero.example;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import com.xero.api.XeroClient;
import com.xero.api.client.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xero.api.ApiClient;
import com.xero.api.Config;
import com.xero.api.JsonConfig;
import com.xero.api.OAuthAccessToken;
import com.xero.api.OAuthRequestResource;
import com.xero.api.XeroApiException;
import com.xero.api.XeroClientException;
import com.xero.models.accounting.Account;
import com.xero.models.accounting.Accounts;
import com.xero.models.accounting.Allocation;
import com.xero.models.accounting.Allocations;
import com.xero.models.accounting.Attachments;
import com.xero.models.accounting.BankTransaction;
import com.xero.models.accounting.BankTransactions;
import com.xero.models.accounting.BankTransfer;
import com.xero.models.accounting.BankTransfers;
import com.xero.models.accounting.BatchPayment;
import com.xero.models.accounting.BatchPayments;
import com.xero.models.accounting.BrandingThemes;
import com.xero.models.accounting.CISSettings;
import com.xero.models.accounting.Contact;
import com.xero.models.accounting.ContactGroup;
import com.xero.models.accounting.ContactGroups;
import com.xero.models.accounting.Contacts;
import com.xero.models.accounting.CreditNote;
import com.xero.models.accounting.CreditNotes;
import com.xero.models.accounting.Currencies;
import com.xero.models.accounting.Currency;
import com.xero.models.accounting.Employee;
import com.xero.models.accounting.Employees;
import com.xero.models.accounting.ExpenseClaim;
import com.xero.models.accounting.ExpenseClaims;
import com.xero.models.accounting.ExternalLink;
import com.xero.models.accounting.HistoryRecord;
import com.xero.models.accounting.HistoryRecords;
import com.xero.models.accounting.Invoice;
import com.xero.models.accounting.InvoiceReminders;
import com.xero.models.accounting.Invoices;
import com.xero.models.accounting.Item;
import com.xero.models.accounting.Items;
import com.xero.models.accounting.JournalLine;
import com.xero.models.accounting.Journals;
import com.xero.models.accounting.LineAmountTypes;
import com.xero.models.accounting.LineItem;
import com.xero.models.accounting.LinkedTransaction;
import com.xero.models.accounting.LinkedTransactions;
import com.xero.models.accounting.ManualJournal;
import com.xero.models.accounting.ManualJournals;
import com.xero.models.accounting.OnlineInvoices;
import com.xero.models.accounting.Organisations;
import com.xero.models.accounting.Overpayments;
import com.xero.models.accounting.Payment;
import com.xero.models.accounting.PaymentService;
import com.xero.models.accounting.PaymentServices;
import com.xero.models.accounting.Payments;
import com.xero.models.accounting.Prepayments;
import com.xero.models.accounting.PurchaseOrder;
import com.xero.models.accounting.PurchaseOrders;
import com.xero.models.accounting.Receipt;
import com.xero.models.accounting.Receipts;
import com.xero.models.accounting.RepeatingInvoices;
import com.xero.models.accounting.ReportWithRows;
import com.xero.models.accounting.RequestEmpty;
import com.xero.models.accounting.Response204;
import com.xero.models.accounting.TaxComponent;
import com.xero.models.accounting.TaxRate;
import com.xero.models.accounting.TaxRate.ReportTaxTypeEnum;
import com.xero.models.accounting.TaxRates;
import com.xero.models.accounting.TaxType;
import com.xero.models.accounting.TrackingCategories;
import com.xero.models.accounting.TrackingCategory;
import com.xero.models.accounting.TrackingOption;
import com.xero.models.accounting.TrackingOptions;
import com.xero.models.accounting.User;
import com.xero.models.accounting.Users;
import com.xero.models.assets.*;
import com.xero.models.assets.BookDepreciationSetting.AveragingMethodEnum;
import com.xero.models.assets.BookDepreciationSetting.DepreciationCalculationMethodEnum;
import com.xero.models.assets.BookDepreciationSetting.DepreciationMethodEnum;
import com.xero.models.bankfeeds.*;
import com.xero.models.bankfeeds.Statements;
import com.xero.models.files.Association;
import com.xero.models.files.FileObject;
import com.xero.models.files.FileResponse204;
import com.xero.models.files.Files;
import com.xero.models.files.Folder;
import com.xero.models.files.Folders;
import com.xero.models.files.ObjectGroup;
import com.xero.models.bankfeeds.FeedConnection.AccountTypeEnum;

//import java.time.LocalDate;
import org.threeten.bp.*;

public class RequestResourceServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L; 
	private Config config = JsonConfig.getInstance();
	final static Logger logger = LogManager.getLogger(OAuthRequestResource.class);
	   
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
		  	+ "<option value=\"Assets\" >Assets</option>"
			+ "<option value=\"Accounts\" >Accounts</option>"
			+ "<option value=\"CreateAttachments\">Attachments - Create</option>"
			+ "<option value=\"GetAttachments\">Attachments - Get</option>"
			+ "<option value=\"BankFeedConnections\">Bank Feed Connections</option>"
			+ "<option value=\"BankStatements\">Bank Statements</option>"
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
			+ "<option value=\"Files\" >Files</option>"
		  	+ "<option value=\"Folders\" >Folders</option>"
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
			System.out.println("Time to refresh access token");
			refreshToken.setToken(storage.get(request, "token"));
			refreshToken.setTokenSecret(storage.get(request, "tokenSecret"));
			refreshToken.setSessionHandle(storage.get(request, "sessionHandle"));
			
			boolean success = refreshToken.build().execute();
			if (!success) {
				try {
					request.getRequestDispatcher("index.jsp").forward(request, response);
				} catch (ServletException e) {
					logger.error(e);
				}
			}
			// DEMONSTRATION ONLY - Store in Cookie - you can extend TokenStorage
			// and implement the save() method for your database
			storage.save(response,refreshToken.getAll());
			token =  refreshToken.getToken();
			tokenSecret = refreshToken.getTokenSecret();
		}
		
		System.out.println(token);
		System.out.println(tokenSecret);
		
		ApiClient apiClientForBankFeeds = new ApiClient(config.getBankFeedsUrl(),null,null,null);
		BankFeedsApi bankFeedsApi = new BankFeedsApi(apiClientForBankFeeds);
		bankFeedsApi.setOAuthToken(token, tokenSecret);
		
		ApiClient apiClientForAssets = new ApiClient(config.getAssetsUrl(),null,null,null);
		AssetApi assetApi = new AssetApi(apiClientForAssets);
		assetApi.setOAuthToken(token, tokenSecret);
		
		ApiClient apiClientForAccounting = new ApiClient(config.getApiUrl(),null,null,null);
		AccountingApi accountingApi = new AccountingApi(apiClientForAccounting);
		accountingApi.setOAuthToken(token, tokenSecret);
		
		ApiClient apiClientForFiles = new ApiClient(config.getFilesUrl(),null,null,null);
		FilesApi filesApi = new FilesApi(apiClientForFiles);
		filesApi.setOAuthToken(token, tokenSecret);
		
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
		Calendar now = Calendar.getInstance();
		if (object.equals("Files")) {
			/* FILES */
			//JSON 
			try {
				List<Folder> myFolders = filesApi.getFolders(null);
				if (myFolders.size() > 1) {
					System.out.println("My folder : " +  myFolders.get(1).getName());					
				}
				
				// Upload new File
				String name = "XeroLogo.png";
				String fileName = "XeroLogo";
				String mimeType = URLConnection.guessContentTypeFromName(name);
				InputStream inputStream = JsonConfig.class.getResourceAsStream("/" + name);
				byte[] bytes = IOUtils.toByteArray(inputStream);
				FileObject newFileObj = filesApi.uploadFile(null,bytes, name, fileName ,mimeType);
				messages.add("Files found: " + newFileObj.getName());
				inputStream.close();
				
				// Get ALL files
				
				Files myFiles = filesApi.getFiles(null, null, null);
				if (myFiles.getItems().size() > 0) {
					UUID fileId = myFiles.getItems().get(0).getId();
					String myFileName = myFiles.getItems().get(0).getName();
					messages.add("Files found - total: " + myFiles.getItems().size());	
					messages.add("File Name: " + myFileName);	
					
					// Get ONE file
					FileObject oneFile = filesApi.getFile(fileId);
					messages.add("Get one file - name: " + oneFile.getName());
					
					// Update ONE file
					FileObject newFileObject = new FileObject();
					newFileObject.setName("HelloWorld.jpg");
					FileObject updatedFile = filesApi.updateFile(fileId, newFileObject);
					messages.add("Get one file - name: " + updatedFile.getName());
					
					// GET File Content
					ByteArrayInputStream input= filesApi.getFileContent(fileId);
					String saveFilePath = saveFile(input,"MyNewFile.jpg");
					messages.add("Save it here: " + saveFilePath);
					
					// Create file Association
					Association association = new Association();
					Invoices invoices = accountingApi.getInvoices(null, null, null, null, null, null, "AUTHORISED", null, false, null);
					UUID invoiceId = invoices.getInvoices().get(0).getInvoiceID();
					association.setObjectGroup(ObjectGroup.INVOICE);
					association.setObjectId(invoiceId);
					Association fileAssociation = filesApi.createFileAssociation(fileId, association);
					messages.add("file association obj group: " + fileAssociation.getObjectGroup());
					
					List<Association> fileAssociations = filesApi.getFileAssociations(fileId);
					messages.add("Total File associations: " + fileAssociations.size());
					UUID objectId = fileAssociations.get(0).getObjectId();
					
					// GET Object Associations
					List<Association> objectAssociations = filesApi.getAssociationsByObject(objectId);
					messages.add("Total OBJECT associations: " + objectAssociations.size());
					
					// DELETE file Association
					FileResponse204 deletedAssociation = filesApi.deleteFileAssociation(fileId, objectId);
					messages.add("Delete file association status: " + deletedAssociation.getStatus());
					
					List<Association> fileAssociationsNew = filesApi.getFileAssociations(fileId);
					messages.add("Total File associations: " + fileAssociationsNew.size());
					
					// DELETE ONE file
					FileResponse204 deletedFile = filesApi.deleteFile(fileId);
					messages.add("Delete file status: " + deletedFile.getStatus());	
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			
			}	
		} else if (object.equals("Folders")) {
			/* FOLDERS */
			//JSON 
			try {
				// Get ALL Folders
				List<Folder> myFolders = filesApi.getFolders(null);
				if (myFolders.size() > 0) {
					UUID folderId = myFolders.get(0).getId();
					messages.add("Folders found - total: " + myFolders.size());	
					
					// GET one Folder
					Folder oneFolder = filesApi.getFolder(folderId);
					messages.add("One Folders found - name: " + oneFolder.getName());	
					
					// Create one Folder
					Folder folder = new Folder();
					folder.setEmail("foo@bar.com");
					folder.setName("NewFolder"+ SampleData.loadRandomNum());
					Folder createdFolder = filesApi.createFolder(folder);
					UUID newFolderId = createdFolder.getId();
					messages.add("New Folder - name: " + createdFolder.getName());	

					// Update one Folder
					Folder folder2 = new Folder();
					folder2.setName("UpdatedFolder"+ SampleData.loadRandomNum());
					Folder updatedFolder = filesApi.updateFolder(newFolderId,folder2);
					messages.add("Updated Folder - name: " + updatedFolder.getName());	
					
					// Update one Folder
					//FileResponse204 deleteFolder = filesApi.deleteFolder(newFolderId);
					//messages.add("Deleted Folder - status: " + deleteFolder.getStatus());	

					// Update one Folder
					Folder inboxFolder = filesApi.getInbox();
					messages.add("Get Inbox Folder - email: " + inboxFolder.getEmail());	

				}
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		
		} else if (object.equals("Accounts")) {
			/* ACCOUNTS */
			//JSON 
			try {
				// GET all accounts
				Accounts accounts = accountingApi.getAccounts(null, null, null);
				messages.add("Get a all Accounts - total : " + accounts.getAccounts().size());				
				
				// GET one account
				Accounts oneAccount = accountingApi.getAccount(accounts.getAccounts().get(0).getAccountID());
				messages.add("Get a one Account - name : " + oneAccount.getAccounts().get(0).getName());				
				
				// CREATE account
				Account acct = new Account();
				acct.setName("Bye" + SampleData.loadRandomNum());
				acct.setCode("Hello" + SampleData.loadRandomNum());
				acct.setDescription("Foo boo");
				acct.setType(Account.TypeEnum.EXPENSE);
				Accounts newAccount = accountingApi.createAccount(acct);
				messages.add("Create a new Account - Name : " + newAccount.getAccounts().get(0).getName() + " Description : " + newAccount.getAccounts().get(0).getDescription() + "");
				UUID accountID = newAccount.getAccounts().get(0).getAccountID();
				
				// CREATE Bank account
				Account bankAcct = new Account();
				bankAcct.setName("Checking " + SampleData.loadRandomNum());
				bankAcct.setCode("12" + SampleData.loadRandomNum());
				bankAcct.setType(Account.TypeEnum.BANK);
				bankAcct.setBankAccountNumber("1234" + SampleData.loadRandomNum());
				Accounts newBankAccount = accountingApi.createAccount(bankAcct);
				messages.add("Create Bank Account - Name : " + newBankAccount.getAccounts().get(0).getName());				
				
				// GET BANK account
			    where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
				Accounts accountsWhere = accountingApi.getAccounts(ifModifiedSince, where, order);
				messages.add("Get a all Accounts - total : " + accountsWhere.getAccounts().size());				
			
				// UDPATE Account
				newAccount.getAccounts().get(0).setDescription("Monsters Inc.");
				newAccount.getAccounts().get(0).setStatus(null);
				Accounts updateAccount = accountingApi.updateAccount(accountID, newAccount);
				messages.add("Update Account - Name : " + updateAccount.getAccounts().get(0).getName() + " Description : " + updateAccount.getAccounts().get(0).getDescription() + "");
				
				// ARCHIVE Account
				Accounts archiveAccounts = new Accounts();
				Account archiveAccount = new Account();
				archiveAccount.setStatus(com.xero.models.accounting.Account.StatusEnum.ARCHIVED);
				archiveAccount.setAccountID(accountID);
				archiveAccounts.addAccountsItem(archiveAccount);
				Accounts achivedAccount = accountingApi.updateAccount(accountID, archiveAccounts);
				messages.add("Archived Account - Name : " + achivedAccount.getAccounts().get(0).getName() + " Status: " + achivedAccount.getAccounts().get(0).getStatus());
				
				// DELETE Account
				UUID deleteAccountID = newAccount.getAccounts().get(0).getAccountID();
				Accounts deleteAccount = accountingApi.deleteAccount(deleteAccountID);
				messages.add("Delete account - Status? : " + deleteAccount.getAccounts().get(0).getStatus());	
							
			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}
	
		} else if (object.equals("GetAttachments")) {
			
			try {
				// GET Account Attachment 
				Accounts accounts = accountingApi.getAccounts(ifModifiedSince, where, order);
				if (accounts.getAccounts().size() > 0) {
					UUID accountID = accounts.getAccounts().get(0).getAccountID();				
					Attachments accountsAttachments = accountingApi.getAccountAttachments(accountID);
					if (accountsAttachments.getAttachments().size() > 0) {
						UUID attachementId = accountsAttachments.getAttachments().get(0).getAttachmentID();
						String contentType = accountsAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream input	 = accountingApi.getAccountAttachmentById(accountID,attachementId, contentType);
						String fileName = "Account_" + accountsAttachments.getAttachments().get(0).getFileName();
						String saveFilePath = saveFile(input,fileName);
						messages.add("Get Account attachment - save it here: " + saveFilePath);	
					}
				}
				
				// GET BankTransactions Attachment 
				BankTransactions bankTransactions = accountingApi.getBankTransactions(ifModifiedSince, where, order, null);
				if (bankTransactions.getBankTransactions().size() > 0) {
					UUID BankTransactionID = bankTransactions.getBankTransactions().get(0).getBankTransactionID();				
					Attachments bankTransactionsAttachments = accountingApi.getBankTransactionAttachments(BankTransactionID);
					if (bankTransactionsAttachments.getAttachments().size() > 0) {
						UUID BankTransactionAttachementID = bankTransactionsAttachments.getAttachments().get(0).getAttachmentID();
						String BankTransactionContentType = bankTransactionsAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream BankTransactionInput	 = accountingApi.getAccountAttachmentById(BankTransactionID,BankTransactionAttachementID, BankTransactionContentType);
						String BankTransactionFileName = "BankTransaction_" + bankTransactionsAttachments.getAttachments().get(0).getFileName();
						String BankTransactionSaveFilePath = saveFile(BankTransactionInput,BankTransactionFileName);
						messages.add("Get BankTransactions attachment - save it here: " + BankTransactionSaveFilePath);
					}
				}
				
				// GET BankTransfers Attachment 
				BankTransfers bankTransfers = accountingApi.getBankTransfers(ifModifiedSince, where, order);
				if (bankTransfers.getBankTransfers().size() > 0) {
					UUID BankTransferID = bankTransfers.getBankTransfers().get(0).getBankTransferID();				
					Attachments bankTransfersAttachments = accountingApi.getBankTransferAttachments(BankTransferID);
					if (bankTransfersAttachments.getAttachments().size() > 0) {	
						UUID BankTransferAttachementID = bankTransfersAttachments.getAttachments().get(0).getAttachmentID();
						String BankTransferContentType = bankTransfersAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream BankTransferInput	 = accountingApi.getAccountAttachmentById(BankTransferID,BankTransferAttachementID, BankTransferContentType);
						String BankTransferFileName = "BankTransfer_" + bankTransfersAttachments.getAttachments().get(0).getFileName();
						String BankTransferSaveFilePath = saveFile(BankTransferInput,BankTransferFileName);
						messages.add("Get BankTransfers attachment - save it here: " + BankTransferSaveFilePath);
					}
				}
				// GET Contacts Attachment 
				Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				if (contacts.getContacts().size() > 0) {
					UUID ContactID = contacts.getContacts().get(0).getContactID();				
					Attachments contactsAttachments = accountingApi.getContactAttachments(ContactID);
					if (contactsAttachments.getAttachments().size() > 0) {	
						UUID ContactAttachementID = contactsAttachments.getAttachments().get(0).getAttachmentID();
						String ContactContentType = contactsAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream ContactInput	 = accountingApi.getAccountAttachmentById(ContactID,ContactAttachementID, ContactContentType);
						String ContactFileName = "Contact_" + contactsAttachments.getAttachments().get(0).getFileName();
						String ContactSaveFilePath = saveFile(ContactInput,ContactFileName);
						messages.add("Get Contacts attachment - save it here: " + ContactSaveFilePath);
					}
				}
				// GET CreditNotes Attachment 
				CreditNotes creditNotes = accountingApi.getCreditNotes(ifModifiedSince, where, order, null);
				if (creditNotes.getCreditNotes().size() > 0) {	
					UUID CreditNoteID = creditNotes.getCreditNotes().get(0).getCreditNoteID();				
					Attachments creditNotesAttachments = accountingApi.getCreditNoteAttachments(CreditNoteID);
					if (creditNotesAttachments.getAttachments().size() > 0) {
						UUID CreditNoteAttachementID = creditNotesAttachments.getAttachments().get(0).getAttachmentID();
						String CreditNoteContentType = creditNotesAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream CreditNoteInput	 = accountingApi.getAccountAttachmentById(CreditNoteID,CreditNoteAttachementID, CreditNoteContentType);
						String CreditNoteFileName = "CreditNote_" + creditNotesAttachments.getAttachments().get(0).getFileName();
						String CreditNoteSaveFilePath = saveFile(CreditNoteInput,CreditNoteFileName);
						messages.add("Get CreditNotes attachment - save it here: " + CreditNoteSaveFilePath);
					}
				}
				
				// GET Invoices Attachment 
				Invoices invoices = accountingApi.getInvoices(ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp);
				if (invoices.getInvoices().size() > 0) {	
					UUID InvoiceID = invoices.getInvoices().get(0).getInvoiceID();				
					Attachments invoicesAttachments = accountingApi.getInvoiceAttachments(InvoiceID);
					if (invoicesAttachments.getAttachments().size() > 0) {	
						UUID InvoiceAttachementID = invoicesAttachments.getAttachments().get(0).getAttachmentID();
						String InvoiceContentType = invoicesAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream InvoiceInput	 = accountingApi.getAccountAttachmentById(InvoiceID,InvoiceAttachementID, InvoiceContentType);
						String InvoiceFileName = "Invoice_" + invoicesAttachments.getAttachments().get(0).getFileName();
						String InvoiceSaveFilePath = saveFile(InvoiceInput,InvoiceFileName);
						messages.add("Get Invoices attachment - save it here: " + InvoiceSaveFilePath);
					}
				}

				// GET ManualJournals Attachment 
				ManualJournals manualJournals = accountingApi.getManualJournals(ifModifiedSince, where, order, null);
				if (manualJournals.getManualJournals().size() > 0) {	
					UUID ManualJournalID = manualJournals.getManualJournals().get(0).getManualJournalID();				
					Attachments manualJournalsAttachments = accountingApi.getManualJournalAttachments(ManualJournalID);
					if (manualJournalsAttachments.getAttachments().size() > 0) {	
						UUID ManualJournalAttachementID = manualJournalsAttachments.getAttachments().get(0).getAttachmentID();
						String ManualJournalContentType = manualJournalsAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream ManualJournalInput	 = accountingApi.getAccountAttachmentById(ManualJournalID,ManualJournalAttachementID, ManualJournalContentType);
						String ManualJournalFileName = "ManualJournal_" + manualJournalsAttachments.getAttachments().get(0).getFileName();
						String ManualJournalSaveFilePath = saveFile(ManualJournalInput,ManualJournalFileName);
						messages.add("Get ManualJournals attachment - save it here: " + ManualJournalSaveFilePath);
					}
				}
				
				// GET Receipts Attachment 
				Receipts receipts = accountingApi.getReceipts(ifModifiedSince, where, order);
				if (receipts.getReceipts().size() > 0) {			
					UUID ReceiptID = receipts.getReceipts().get(0).getReceiptID();				
					Attachments receiptsAttachments = accountingApi.getReceiptAttachments(ReceiptID);
					if (receiptsAttachments.getAttachments().size() > 0) {			
						UUID ReceiptAttachementID = receiptsAttachments.getAttachments().get(0).getAttachmentID();
						String ReceiptContentType = receiptsAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream ReceiptInput	 = accountingApi.getAccountAttachmentById(ReceiptID,ReceiptAttachementID, ReceiptContentType);
						String ReceiptFileName = "Receipt_" + receiptsAttachments.getAttachments().get(0).getFileName();						
						String ReceiptSaveFilePath = saveFile(ReceiptInput,ReceiptFileName);
						messages.add("Get Receipts attachment - save it here: " + ReceiptSaveFilePath);
					}
				}

				// GET RepeatingInvoices Attachment 
				RepeatingInvoices repeatingInvoices = accountingApi.getRepeatingInvoices(where, order);
				if (repeatingInvoices.getRepeatingInvoices().size() > 0) {			
					UUID RepeatingInvoiceID = repeatingInvoices.getRepeatingInvoices().get(0).getRepeatingInvoiceID();				
					Attachments repeatingInvoicesAttachments = accountingApi.getRepeatingInvoiceAttachments(RepeatingInvoiceID);
					if (repeatingInvoicesAttachments.getAttachments().size() > 0) {			
						UUID RepeatingInvoiceAttachementID = repeatingInvoicesAttachments.getAttachments().get(0).getAttachmentID();
						String RepeatingInvoiceContentType = repeatingInvoicesAttachments.getAttachments().get(0).getMimeType();
						ByteArrayInputStream RepeatingInvoiceInput	 = accountingApi.getAccountAttachmentById(RepeatingInvoiceID,RepeatingInvoiceAttachementID, RepeatingInvoiceContentType);
						String RepeatingInvoiceFileName = "RepeatingInvoice_" + repeatingInvoicesAttachments.getAttachments().get(0).getFileName();
						String RepeatingInvoiceSaveFilePath = saveFile(RepeatingInvoiceInput,RepeatingInvoiceFileName);
						messages.add("Get RepeatingInvoices attachment - save it here: " + RepeatingInvoiceSaveFilePath);
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	
		} else if (object.equals("CreateAttachments")) {
			try {
				// JSON
				InputStream inputStream = JsonConfig.class.getResourceAsStream("/helo-heros.jpg");
				byte[] bytes = IOUtils.toByteArray(inputStream);
				String newFileName = "sample5.jpg";
	
				// CREATE Accounts attachment
			    where = "Status==\"ACTIVE\"";
				Accounts myAccounts = accountingApi.getAccounts(ifModifiedSince, where, order);
				if ( myAccounts.getAccounts().size() > 0) {
					UUID accountID = myAccounts.getAccounts().get(0).getAccountID();
					String accountName = myAccounts.getAccounts().get(0).getName();
					Attachments createdAttachments = accountingApi.createAccountAttachmentByFileName(accountID, newFileName, bytes);
					messages.add("Attachment to Name: " + accountName + " Account ID: " + accountID + " attachment - ID: " + createdAttachments.getAttachments().get(0).getAttachmentID());
				}
				
				// CREATE BankTransactions attachment
				BankTransactions myBanktransactions = accountingApi.getBankTransactions(ifModifiedSince, where, order, null);
				if ( myBanktransactions.getBankTransactions().size() > 0) {
					UUID banktransactionID = myBanktransactions.getBankTransactions().get(0).getBankTransactionID();			
					Attachments createdBanktransationAttachments = accountingApi.createBankTransactionAttachmentByFileName(banktransactionID, newFileName, bytes);
					messages.add("Attachment to BankTransaction ID: " + banktransactionID + " attachment - ID: "  + createdBanktransationAttachments.getAttachments().get(0).getAttachmentID());
				}
				
				// CREATE BankTransfer attachment
				where = null;
				BankTransfers myBankTransfer = accountingApi.getBankTransfers(ifModifiedSince, where, order);
				if ( myBankTransfer.getBankTransfers().size() > 0) {
					UUID bankTransferID = myBankTransfer.getBankTransfers().get(0).getBankTransferID();			
					Attachments createdBankTransferAttachments = accountingApi.createBankTransferAttachmentByFileName(bankTransferID, newFileName, bytes);
					messages.add("Attachment to BankTransfer ID: " + bankTransferID + " attachment - ID: " + createdBankTransferAttachments.getAttachments().get(0).getAttachmentID());
				}
				
				// CREATE Contacts attachment
				where =  "ContactStatus==\"ACTIVE\"";
				Contacts contactsWhere = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				if ( contactsWhere.getContacts().size() > 0) {
					UUID contactID = contactsWhere.getContacts().get(0).getContactID();
					Attachments createdContactAttachments = accountingApi.createContactAttachmentByFileName(contactID, newFileName, bytes);
					messages.add("Attachment to Contact ID: " + contactID + " attachment - ID: " + createdContactAttachments.getAttachments().get(0).getAttachmentID());
				}
				
				where = "Status==\"ACTIVE\"";
				// CREATE CreditNotes attachment
				CreditNotes myCreditNotes = accountingApi.getCreditNotes(ifModifiedSince, where, order, null);
				if ( myCreditNotes.getCreditNotes().size() > 0) {
					UUID creditNoteID = myCreditNotes.getCreditNotes().get(0).getCreditNoteID();
					Attachments createdCreditNoteAttachments = accountingApi.createCreditNoteAttachmentByFileName(creditNoteID, newFileName, bytes);
					messages.add("Attachment to Credit Notes ID: " + creditNoteID + " attachment - ID: " + createdCreditNoteAttachments.getAttachments().get(0).getAttachmentID());
				}
				
				// CREATE invoice attachment
				Invoices myInvoices = accountingApi.getInvoices(ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp);
				if ( myInvoices.getInvoices().size() > 0) {
					UUID invoiceID = myInvoices.getInvoices().get(0).getInvoiceID();
					Attachments createdInvoiceAttachments = accountingApi.createInvoiceAttachmentByFileName(invoiceID, newFileName, bytes);
					messages.add("Attachment to Invoice ID: " + invoiceID + " attachment - ID: "  + createdInvoiceAttachments.getAttachments().get(0).getAttachmentID());
				}
				
				// CREATE ManualJournals attachment
				ManualJournals myManualJournals = accountingApi.getManualJournals(ifModifiedSince, where, order, null);
				if ( myManualJournals.getManualJournals().size() > 0) {
					UUID manualJournalID = myManualJournals.getManualJournals().get(0).getManualJournalID();
					Attachments createdManualJournalAttachments = accountingApi.createManualJournalAttachmentByFileName(manualJournalID, newFileName, bytes);
					messages.add("Attachment to Manual Journal ID: " + manualJournalID + " attachment - ID: " + createdManualJournalAttachments.getAttachments().get(0).getAttachmentID());
				}
				
				// CREATE Receipts attachment
				Receipts myReceipts = accountingApi.getReceipts(ifModifiedSince, where, order);
				if ( myReceipts.getReceipts().size() > 0) {
					UUID receiptID = myReceipts.getReceipts().get(0).getReceiptID();
					Attachments createdReceiptsAttachments = accountingApi.createReceiptAttachmentByFileName(receiptID, newFileName, bytes);
					messages.add("Attachment to Receipt ID: " + receiptID + " attachment - ID: " + createdReceiptsAttachments.getAttachments().get(0).getAttachmentID());
				}
				
				// CREATE Repeating Invoices attachment
				RepeatingInvoices myRepeatingInvoices = accountingApi.getRepeatingInvoices(where, order);
				if ( myRepeatingInvoices.getRepeatingInvoices().size() > 0) {	
					UUID repeatingInvoiceID = myRepeatingInvoices.getRepeatingInvoices().get(0).getRepeatingInvoiceID();
					Attachments createdRepeatingInvoiceAttachments = accountingApi.createRepeatingInvoiceAttachmentByFileName(repeatingInvoiceID, newFileName, bytes);
					messages.add("Attachment to Repeating Invoices ID: " + repeatingInvoiceID + " attachment - ID: " + createdRepeatingInvoiceAttachments.getAttachments().get(0).getAttachmentID());
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if(object.equals("Assets")) {
			/* Asset */
			// Create Asset
			try {
				Asset asset = new Asset();
				asset.setAssetName("Computer" + SampleData.loadRandomNum());
				asset.setAssetNumber("1234" + SampleData.loadRandomNum());
				Asset newAsset = assetApi.createAsset(asset);
				messages.add("New Asset created: " + newAsset.getAssetName());	
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			try {
				String orderBy = null;
				String sortDirection = null;
				String filterBy = null;
				String status = "DRAFT";				
				Assets assets = assetApi.getAssets(status, null, null, orderBy, sortDirection, filterBy);
				messages.add("Assets Found: " + assets.getItems().get(0).getAssetName());
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			try {		
				List<AssetType> assetTypes = assetApi.getAssetTypes();
				messages.add("AssetType Found: " + assetTypes.get(0).getAssetTypeName());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			try {
				where = "Type==\"FIXED\"";
				Accounts accountFixedAsset = accountingApi.getAccounts(ifModifiedSince, where, order);
				UUID fixedAssetAccountId = accountFixedAsset.getAccounts().get(0).getAccountID();
				where = "Type==\"EXPENSE\"";
				Accounts accountDepreciationExpense = accountingApi.getAccounts(ifModifiedSince, where, order);
				UUID depreciationExpenseAccountId = accountDepreciationExpense.getAccounts().get(0).getAccountID();
				where = "Type==\"DEPRECIATN\"";
				Accounts accountAccumulatedDepreciation = accountingApi.getAccounts(ifModifiedSince, where, order);
				UUID accumulatedDepreciationAccountId = accountAccumulatedDepreciation.getAccounts().get(0).getAccountID();
				
				AssetType assetType = new AssetType();
				assetType.setAssetTypeName("Machinery" + SampleData.loadRandomNum());
				assetType.setFixedAssetAccountId(fixedAssetAccountId);
				assetType.setDepreciationExpenseAccountId(depreciationExpenseAccountId);
				assetType.setAccumulatedDepreciationAccountId(accumulatedDepreciationAccountId);
				
				float depreciationRate = 0.05f;
				BookDepreciationSetting bookDepreciationSetting = new BookDepreciationSetting();
				bookDepreciationSetting.setAveragingMethod(AveragingMethodEnum.ACTUALDAYS);
				bookDepreciationSetting.setDepreciationCalculationMethod(DepreciationCalculationMethodEnum.NONE);
				bookDepreciationSetting.setDepreciationRate(depreciationRate);
				bookDepreciationSetting.setDepreciationMethod(DepreciationMethodEnum.DIMINISHINGVALUE100);
				assetType.setBookDepreciationSetting(bookDepreciationSetting);
				
				AssetType newAssetType = assetApi.createAssetType(assetType);	
				messages.add("Asset Type Created: " + newAssetType.getAssetTypeName());
			
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			try {			
				Setting setting = assetApi.getAssetSettings();
				messages.add("Asset Setting Start date: " + setting.getAssetStartDate());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		
		} else if(object.equals("BankFeedConnections")) {
			/* BANKFEED CONNECTIONS */
			// Create New Feed Connection
			try {
				FeedConnection newBank = new FeedConnection();
				newBank.setAccountName("SDK Bank " + SampleData.loadRandomNum());
				newBank.setAccountNumber("1234" + SampleData.loadRandomNum());
				newBank.setAccountType(AccountTypeEnum.BANK);
				newBank.setAccountToken("foobar" + SampleData.loadRandomNum());
				newBank.setCurrency("GBP");
				
				FeedConnections feedConnections = new FeedConnections();
				feedConnections.addItemsItem(newBank);
				
				FeedConnections fc1 = bankFeedsApi.createFeedConnections(feedConnections);
				messages.add("New Bank with status: " + fc1.getItems().get(0).getStatus());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			// Get ALL Feed Connection
			try {
				FeedConnections fc = bankFeedsApi.getFeedConnections(null,null);
				messages.add("Total Banks found: " + fc.getItems().size());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			// Get one Feed Connection
			try {
				FeedConnections fc = bankFeedsApi.getFeedConnections(null, null);
				String id = fc.getItems().get(0).getId();
				FeedConnection oneFC = bankFeedsApi.getFeedConnection(id);
				messages.add("One Bank: " + oneFC.getAccountName());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			// Delete Feed Connection
			try {
				FeedConnections fc = bankFeedsApi.getFeedConnections(null, null);
				FeedConnections allFeedConnections = bankFeedsApi.getFeedConnections(null, null);
				FeedConnections deleteFeedConnections = new FeedConnections();
				
				FeedConnection feedConnectionOne = new FeedConnection();
				feedConnectionOne.setId(allFeedConnections.getItems().get(fc.getItems().size()-1).getId());
				deleteFeedConnections.addItemsItem(feedConnectionOne);
				
				FeedConnections deletedFeedConnection = bankFeedsApi.deleteFeedConnections(deleteFeedConnections);				
				messages.add("Deleted Bank status: " + deletedFeedConnection.getItems().get(0).getStatus());
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		} else if(object.equals("BankStatements")) {
			// BANK STATEMENTS 
			// Create One Statement
			int day = now.get(Calendar.DATE);
			int year = now.get(Calendar.YEAR);
			int lastMonth = now.get(Calendar.MONTH) - 1;
			int nextMonth = now.get(Calendar.MONTH) + 1;
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
			try {
				Statements arrayOfStatements = new Statements();
				Statement newStatement = new Statement();
				
				LocalDate stDate = LocalDate.of(year, nextMonth, day);
				newStatement.setStartDate(stDate);
				LocalDate endDate = LocalDate.of(year, lastMonth, day);
				newStatement.endDate(endDate);
				StartBalance stBalance = new StartBalance();
				stBalance.setAmount("100");
				stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
				newStatement.setStartBalance(stBalance);
				
				EndBalance endBalance = new EndBalance();
				endBalance.setAmount("300");
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
				Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements);
				messages.add("New Bank Statement Status: " + rStatements.getItems().get(0).getStatus());
							
			} catch (Exception e) {
				TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
				Statements statementErrors =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);
				System.out.println(statementErrors.getItems().get(0).getErrors().get(0).getDetail());
			}
			
			// Create One Statement, Then GET One Statement
			try {
				Statements arrayOfStatements = new Statements();
				Statement newStatement = new Statement();
				LocalDate stDate = LocalDate.of(year, lastMonth, 01);
				newStatement.setStartDate(stDate);
				LocalDate endDate = LocalDate.of(year, lastMonth, 15);
				newStatement.endDate(endDate);
				StartBalance stBalance = new StartBalance();
				stBalance.setAmount("100");
				stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
				newStatement.setStartBalance(stBalance);
				
				EndBalance endBalance = new EndBalance();
				endBalance.setAmount("300");
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
				LocalDate postedDate = LocalDate.of(year, lastMonth, 05);
				newStatementLine.setPostedDate(postedDate);
			
				StatementLines arrayStatementLines = new StatementLines();
				arrayStatementLines.add(newStatementLine);
				
				newStatement.setStatementLines(arrayStatementLines);
				arrayOfStatements.addItemsItem(newStatement);
				Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements);
				String statementId = rStatements.getItems().get(0).getId();
				Statement oneStatement = bankFeedsApi.getStatement(statementId);				
				messages.add("New Bank Statement Status: " + oneStatement.getStatementLineCount());
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
				
			// Create Duplicate Statement - to test error handling
			try {
				Statements arrayOfStatements = new Statements();
				Statement newStatement = new Statement();
				LocalDate stDate = LocalDate.of(year, lastMonth, 01);
				newStatement.setStartDate(stDate);
				LocalDate endDate = LocalDate.of(year, lastMonth, 15);
				newStatement.endDate(endDate);
				StartBalance stBalance = new StartBalance();
				stBalance.setAmount("100");
				stBalance.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
				newStatement.setStartBalance(stBalance);
				
				EndBalance endBalance = new EndBalance();
				endBalance.setAmount("300");
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
				LocalDate postedDate = LocalDate.of(year, lastMonth, 05);
				newStatementLine.setPostedDate(postedDate);
			
				StatementLines arrayStatementLines = new StatementLines();
				arrayStatementLines.add(newStatementLine);
				
				newStatement.setStatementLines(arrayStatementLines);
				arrayOfStatements.addItemsItem(newStatement);
				Statements rStatements2 = bankFeedsApi.createStatements(arrayOfStatements);
				messages.add("New Bank Statement Status: " + rStatements2.getItems().get(0).getStatus());
				
				//DUPLICATE
				Statements rStatements3 = bankFeedsApi.createStatements(arrayOfStatements);
				
				messages.add("New Bank Statement Status: " + rStatements3.getItems().get(0).getStatus());
				
			} catch (Exception e) { 
				TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
				Statements statementErrors =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);
				System.out.println(statementErrors.getItems().get(0).getErrors().get(0).getDetail());
				System.out.println(statementErrors.getItems().get(0).getErrors().get(0).getStatus());
			}
			
			//Get ALL Statements
			try {
				Statements allStatements = bankFeedsApi.getStatements(null, null, null, null, null);
				messages.add("Statement total: " + allStatements.getItems().size());
			} catch (Exception e) {
				TypeReference<Statements> typeRef = new TypeReference<Statements>() {};
				Statements statementErrors =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);
				System.out.println(statementErrors.getItems().get(0).getErrors().get(0).getDetail());
			}
			
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
					
					Statements rStatements = bankFeedsApi.createStatements(arrayOfStatements);
					
					messages.add("Statement Status: " + rStatements.getItems().get(0).getStatus());
			
				} else {
					messages.add("Need at least 2 feed connections to perform this test");
				}
				
			} catch (XeroApiException e) {
				try {
					TypeReference<com.xero.models.bankfeeds.Error> typeRef = new TypeReference<com.xero.models.bankfeeds.Error>() {};
					com.xero.models.bankfeeds.Error error =  apiClientForBankFeeds.getObjectMapper().readValue(e.getMessage(), typeRef);
					messages.add(error.getDetail());
				} catch (IOException ioe) {
					System.out.println("IO:" + ioe.toString());
				}
			} catch (Exception e) {
				messages.add("Generic Exception " + e.toString());
			}
			
		} else if (object.equals("BankTransactions")) {
			/* BANK TRANSACTION */
			try {
			    where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
				Accounts accountsWhere = accountingApi.getAccounts(ifModifiedSince, where, order);
				
				Account bankAcct = new Account();
				bankAcct.setCode(accountsWhere.getAccounts().get(0).getCode());
				
				where = null;
				Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				Contact useContact = new Contact();
				useContact.setContactID(contacts.getContacts().get(0).getContactID());
				
				// Maker sure we have at least 1 bank
				if(accountsWhere.getAccounts().size() > 0) {
					// Create Bank Transaction
					List<LineItem> lineItems = new ArrayList<>();
					LineItem li = new LineItem();
					li.setAccountCode("400");
					li.setDescription("Foobar");
					li.setQuantity(1.0f);
					li.setUnitAmount(20.0f);
					lineItems.add(li);
					BankTransaction bt = new BankTransaction();
					bt.setBankAccount(bankAcct);
					bt.setContact(useContact);
					bt.setLineitems(lineItems);
					bt.setType(com.xero.models.accounting.BankTransaction.TypeEnum.SPEND);
					BankTransactions bts = new BankTransactions();
					bts.addBankTransactionsItem(bt);					
					BankTransactions newBankTransaction = accountingApi.createBankTransaction(bts, summarizeErrors);
					messages.add("Create new BankTransaction : amount:" + newBankTransaction.getBankTransactions().get(0).getTotal());				
					
					// GET all Bank Transaction
					BankTransactions bankTransactions = accountingApi.getBankTransactions(ifModifiedSince, where, order, null);
					messages.add("Get a all Bank Transactions - total : " + bankTransactions.getBankTransactions().size());				

					// GET one Bank Transaction
					BankTransactions oneBankTransaction = accountingApi.getBankTransaction(bankTransactions.getBankTransactions().get(0).getBankTransactionID());
					messages.add("Get a one Bank Transaction : amount:" + oneBankTransaction.getBankTransactions().get(0).getTotal());				
					
					// UDPATE Bank Transaction
					newBankTransaction.getBankTransactions().get(0).setSubTotal(null);
					newBankTransaction.getBankTransactions().get(0).setTotal(null);	
					newBankTransaction.getBankTransactions().get(0).setReference("You just updated");
					BankTransactions updateBankTransaction = accountingApi.updateBankTransaction(newBankTransaction.getBankTransactions().get(0).getBankTransactionID(),newBankTransaction);
					messages.add("Update new BankTransaction : reference:" + updateBankTransaction.getBankTransactions().get(0).getReference());				

					// DELETE Bank Transaction
					newBankTransaction.getBankTransactions().get(0).setStatus(com.xero.models.accounting.BankTransaction.StatusEnum.DELETED);
					BankTransactions deletedBankTransaction = accountingApi.updateBankTransaction(newBankTransaction.getBankTransactions().get(0).getBankTransactionID(),newBankTransaction);
					messages.add("Deleted new Bank Transaction : Status:" + deletedBankTransaction.getBankTransactions().get(0).getStatus());				
				
					// GET  Bank Transaction History
					HistoryRecords hr = accountingApi.getBankTransactionsHistory(oneBankTransaction.getBankTransactions().get(0).getBankTransactionID());
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
			
		} else if (object.equals("BankTransfers")) {		
			/* BANK TRANSFER */
			try {
			    where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
				Accounts accountsWhere = accountingApi.getAccounts(ifModifiedSince, where, order);
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
					BankTransfers newBankTranfer = accountingApi.createBankTransfer(newBTs);
					messages.add("Get a one Bank Transfer - amount : " + newBankTranfer.getBankTransfers().get(0).getAmount());				

					// GET all Bank Transfers
					BankTransfers bankTranfers = accountingApi.getBankTransfers(ifModifiedSince, where, order);
					messages.add("Get a all Bank Transfers - total : " + bankTranfers.getBankTransfers().size());				
					UUID bankTransferId = bankTranfers.getBankTransfers().get(0).getBankTransferID();

					// GET one Bank Transfer
					BankTransfers oneBankTranfer = accountingApi.getBankTransfer(bankTransferId);
					messages.add("Get a one Bank Transfer - amount : " + oneBankTranfer.getBankTransfers().get(0).getAmount());				
			
					// GET  Bank Transfer History
					HistoryRecords hr = accountingApi.getBankTransferHistory(bankTransferId);
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
			
		} else if (object.equals("BatchPayments")) {
			try {
				/* BATCH PAYMENTS */
				// CREATE payment
				where =  "Status==\"AUTHORISED\"&&Type==\"ACCREC\"";			
				Invoices allInvoices = accountingApi.getInvoices(ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp);
				Invoice inv = new Invoice();
				inv.setInvoiceID(allInvoices.getInvoices().get(0).getInvoiceID());
				Invoice inv2 = new Invoice();
				inv2.setInvoiceID(allInvoices.getInvoices().get(1).getInvoiceID());
				Invoice inv3 = new Invoice();
				inv3.setInvoiceID(allInvoices.getInvoices().get(3).getInvoiceID());
				where = null;
			
			    where = "EnablePaymentsToAccount==true";
				Accounts accountsWhere = accountingApi.getAccounts(ifModifiedSince, where, order);
				Account paymentAccount = new Account();
				paymentAccount.setAccountID(accountsWhere.getAccounts().get(0).getAccountID());
				where = null;
				
				BatchPayments createBatchPayments = new BatchPayments();
				BatchPayment createBatchPayment = new BatchPayment();
				createBatchPayment.setAccount(paymentAccount);
				createBatchPayment.setAmount(3.0f);
				LocalDate currDate = LocalDate.now();
				createBatchPayment.setDate(currDate);
				createBatchPayment.setReference("Foobar" + SampleData.loadRandomNum());

				Payment payment01 = new Payment();
				payment01.setAccount(paymentAccount);
				payment01.setInvoice(inv);
				payment01.setAmount(1.0f);
				payment01.setDate(currDate);
				
				Payment payment02 = new Payment();
				payment02.setAccount(paymentAccount);
				payment02.setInvoice(inv2);
				payment02.setAmount(1.0f);
				payment02.setDate(currDate);
				
				Payment payment03 = new Payment();
				payment03.setAccount(paymentAccount);
				payment03.setInvoice(inv3);
				payment03.setAmount(1.0f);
				payment03.setDate(currDate);
				
				createBatchPayment.addPaymentsItem(payment01);
				createBatchPayment.addPaymentsItem(payment02);
				createBatchPayment.addPaymentsItem(payment03);
				
				createBatchPayments.addBatchPaymentsItem(createBatchPayment);
				
				BatchPayments newBatchPayments = accountingApi.createBatchPayment(createBatchPayments);
				messages.add("Create BatchPayments - ID : " + newBatchPayments.getBatchPayments().get(0).getTotalAmount());					
				
				// GET all Payments
				BatchPayments allBatchPayments = accountingApi.getBatchPayments(ifModifiedSince, where, order);
				messages.add("Get BatchPayments - Total : " + allBatchPayments.getBatchPayments().size());					
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
			}
		
		} else if (object.equals("BrandingThemes")) {
			/* BRANDING THEME */
			try {
				// GET all BrandingTheme
				BrandingThemes bt = accountingApi.getBrandingThemes();
				messages.add("Get a All Branding Themes - total : " + bt.getBrandingThemes().size());				

				// GET one BrandingTheme
				UUID btID = bt.getBrandingThemes().get(0).getBrandingThemeID();
				BrandingThemes oneBt = accountingApi.getBrandingTheme(btID);
				messages.add("Get a one Branding Themes - name : " + oneBt.getBrandingThemes().get(0).getName());				

				// Create PaymentService for a Branding Theme  
				PaymentServices paymentServices = accountingApi.getPaymentServices();
				UUID paymentServiceID = paymentServices.getPaymentServices().get(0).getPaymentServiceID();
				PaymentServices btPaymentServices = new PaymentServices();
				PaymentService btPaymentService = new PaymentService();
				btPaymentService.setPaymentServiceID(paymentServiceID);
				btPaymentServices.addPaymentServicesItem(btPaymentService);
				PaymentServices createdPaymentService = accountingApi.createBrandingThemePaymentServices(btID, btPaymentServices);
				messages.add("Created payment services for Branding Themes - name : " + createdPaymentService.getPaymentServices().get(0).getPaymentServiceName());				

				// GET Payment Services for a single Branding Theme
				PaymentServices paymentServicesForBrandingTheme = accountingApi.getBrandingThemePaymentServices(btID);
				messages.add("Get payment services for Branding Themes - name : " + paymentServicesForBrandingTheme.getPaymentServices().get(0).getPaymentServiceName());				

			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
			}
			
		} else if (object.equals("Contacts")) {		
			/* CONTACTS */
			try {
				// CREATE contact
				Contact contact = new Contact();
				contact.setName("Foo" + SampleData.loadRandomNum());
				contact.setEmailAddress("sid" + SampleData.loadRandomNum() + "@blah.com");
				Contacts newContact = accountingApi.createContact(contact);
				messages.add("Create new Contact - Name : " + newContact.getContacts().get(0).getName());
				
				// UPDATE contact
				newContact.getContacts().get(0).setName("Bar" + SampleData.loadRandomNum());
				UUID contactID = newContact.getContacts().get(0).getContactID();
				Contacts updatedContact = accountingApi.updateContact(contactID, newContact);
				messages.add("Update new Contact - Name : " + updatedContact.getContacts().get(0).getName());
				
				// GET all contact
				Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				messages.add("Get a All Contacts - Total : " + contacts.getContacts().size());	

				// GET one contact
				UUID oneContactID = contacts.getContacts().get(0).getContactID();
				Contacts oneContact = accountingApi.getContact(oneContactID);
				messages.add("Get a One Contact - Name : " + oneContact.getContacts().get(0).getName());	
				
				// GET contact cisSettings
			    where = "Name==\"sidney\"";   
				Contacts cisContact = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				if (cisContact.getContacts().size() > 0) {
					CISSettings cisSettings = accountingApi.getContactCISSettings(cisContact.getContacts().get(0).getContactID());
					messages.add("Get a Contact cisSettings - Enabled? : " + cisSettings.getCiSSettings().get(0).getCiSEnabled());	
				}
				where = null;
				
				// GET active contacts
			    where =  "ContactStatus==\"ACTIVE\"";
				Contacts contactsWhere = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				messages.add("Get a all ACTIVE Contacts - Total : " + contactsWhere.getContacts().size());
				where = null;
				
				// Get Contact History
				HistoryRecords contactHistory = accountingApi.getContactHistory(contactID);
				messages.add("Contact History - count : " + contactHistory.getHistoryRecords().size() );
			
				// Create Contact History
				HistoryRecords newHistoryRecords = new  HistoryRecords();
				HistoryRecord newHistoryRecord = new  HistoryRecord();
				newHistoryRecord.setDetails("Hello World");
				newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
				
				HistoryRecords newInvoiceHistory = accountingApi.createContactHistory(contactID,newHistoryRecords);
				messages.add("Contact History - note added to  : " + newInvoiceHistory.getHistoryRecords().get(0).getDetails());
				
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());	
			}
		} else if (object.equals("ContactGroups")) {
		
			/* CONTACT GROUP  */		
			try {
				// Create contact group
				ContactGroups newCGs = new ContactGroups();
				ContactGroup cg = new ContactGroup();
				cg.setName("NewGroup" + SampleData.loadRandomNum());
				newCGs.addContactGroupsItem(cg);
				ContactGroups newContactGroup = accountingApi.createContactGroup(newCGs);
				messages.add("Create a ContactGroup - Name : " + newContactGroup.getContactGroups().get(0).getName());
				
				// UPDATE Contact group
				newCGs.getContactGroups().get(0).setName("Old Group" + SampleData.loadRandomNum());
				UUID newContactGroupID = newContactGroup.getContactGroups().get(0).getContactGroupID();
				ContactGroups updateContactGroup = accountingApi.updateContactGroup(newContactGroupID, newCGs);
				messages.add("Update a ContactGroup - Name : " + updateContactGroup.getContactGroups().get(0).getName());

				// GET all contact groups
				ContactGroups contactGroups = accountingApi.getContactGroups(where, order);
				messages.add("Get all ContactGroups - Total : " + contactGroups.getContactGroups().size());
				
				// GET one contact groups
				UUID contactGroupId = contactGroups.getContactGroups().get(0).getContactGroupID();
				ContactGroups oneCg = accountingApi.getContactGroup(contactGroupId);
				messages.add("Get one ContactGroups - Name : " + oneCg.getContactGroups().get(0).getName());
				
				// DELETE contact Group
				newCGs.getContactGroups().get(0).setStatus(com.xero.models.accounting.ContactGroup.StatusEnum.DELETED);
				UUID contactGroupID = newContactGroup.getContactGroups().get(0).getContactGroupID();
				ContactGroups deletedContactGroup = accountingApi.updateContactGroup(contactGroupID, contactGroups);
				messages.add("Delete a ContactGroup - Name : " + deletedContactGroup.getContactGroups().get(0).getName());
					
			} catch (XeroApiException e) {
				System.out.println(e.getResponseCode());
				System.out.println(e.getMessage());
			}
			
		} else if (object.equals("ContactGroupContacts")) {
			/* CONTACT GROUP CONTACTS */
			try {	
				// Create new Contact Group
				ContactGroups newCGs = new ContactGroups();
				ContactGroup cg = new ContactGroup();
				cg.setName("NewGroup" + SampleData.loadRandomNum());
				newCGs.addContactGroupsItem(cg);
				ContactGroups newContactGroup = accountingApi.createContactGroup(newCGs);
			
				Contacts allContacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				
				// Create Contacts in Group
				Contacts contactList = new Contacts();
				contactList.addContactsItem(allContacts.getContacts().get(0));
				contactList.addContactsItem(allContacts.getContacts().get(1));
				UUID contactGroupID = newContactGroup.getContactGroups().get(0).getContactGroupID();
				Contacts addContacts = accountingApi.createContactGroupContacts(contactGroupID, contactList);
				messages.add("Add 2 Contacts to Contact Group - Total : " + addContacts.getContacts().size());	
			
				// DELETE all Contacts in Group
				Response204 deleteContacts = accountingApi.deleteContactGroupContacts(newContactGroup.getContactGroups().get(0).getContactGroupID());
				messages.add("Delete All Contacts  to Contact Group - Status? : " + deleteContacts.getStatus());	
				ContactGroups oneCg = accountingApi.getContactGroup(newContactGroup.getContactGroups().get(0).getContactGroupID());
				messages.add("Get ContactGroups - Total Contacts : " + oneCg.getContactGroups().get(0).getContacts().size());
				
				// DELETE Single Contact
				Contacts contactList2 = new Contacts();
				contactList2.addContactsItem(allContacts.getContacts().get(3));
				contactList2.addContactsItem(allContacts.getContacts().get(4));
				
				UUID newContactGroupID = newContactGroup.getContactGroups().get(0).getContactGroupID();
				Contacts addContacts2 = accountingApi.createContactGroupContacts(newContactGroupID, contactList2);				
				messages.add("Add 2 Contacts to Contact Group - Total : " + addContacts2.getContacts().size());	
			
				// DELETE Single CONACTS
				Response204 deleteContacts2 = accountingApi.deleteContactGroupContact(newContactGroup.getContactGroups().get(0).getContactGroupID(),allContacts.getContacts().get(3).getContactID());
				messages.add("Delete 1 contact from Contact Group - Status? : " + deleteContacts2.getStatus());	
				ContactGroups oneCg2 = accountingApi.getContactGroup(newContactGroup.getContactGroups().get(0).getContactGroupID());
				messages.add("Get ContactGroups - Total Contacts : " + oneCg2.getContactGroups().get(0).getContacts().size());
				
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());
			}
			
		} else if (object.equals("CreditNotesPDF")) {
			// GET CreditNote As a PDF
			CreditNotes creditNotes = accountingApi.getCreditNotes(ifModifiedSince, where, order, null);
			UUID creditNoteId = creditNotes.getCreditNotes().get(0).getCreditNoteID();
			ByteArrayInputStream CreditNoteInput	 = accountingApi.getCreditNoteAsPdf(creditNoteId, "application/pdf");
			String CreditNoteFileName = "CreditNoteAsPDF.pdf";
			
			String CreditNoteSaveFilePath = saveFile(CreditNoteInput,CreditNoteFileName);
			messages.add("Get CreditNote attachment - save it here: " + CreditNoteSaveFilePath);
					
		} else if (object.equals("CreditNotes")) {
			/* CREDIT NOTE */
			// JSON - complete - except Attachment
			try {	
				Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				
				// Create Credit Note
				List<LineItem> lineItems = new ArrayList<>();
				LineItem li = new LineItem();
				li.setAccountCode("400");
				li.setDescription("Foobar");
				li.setQuantity(2.0f);
				li.setUnitAmount(20.0f);
				lineItems.add(li);
				
				CreditNotes newCNs = new CreditNotes();
				CreditNote cn = new CreditNote();
				cn.setContact(contacts.getContacts().get(0));
				cn.setLineItems(lineItems);  
				cn.setType(com.xero.models.accounting.CreditNote.TypeEnum.ACCPAYCREDIT);
				newCNs.addCreditNotesItem(cn);
				CreditNotes newCreditNote = accountingApi.createCreditNote(summarizeErrors, newCNs);
				messages.add("Create a CreditNote - Amount : " + newCreditNote.getCreditNotes().get(0).getTotal());
				UUID newCreditNoteId = newCreditNote.getCreditNotes().get(0).getCreditNoteID();
				
				// GET all Credit Note
				CreditNotes creditNotes = accountingApi.getCreditNotes(ifModifiedSince, where, order, null);
				messages.add("Get all CreditNotes - Total : " + creditNotes.getCreditNotes().size());
				
				// GET One Credit Note
				UUID creditNoteID = creditNotes.getCreditNotes().get(0).getCreditNoteID();
				CreditNotes oneCreditNote = accountingApi.getCreditNote(creditNoteID);
				messages.add("Get a CreditNote - Amount : " + oneCreditNote.getCreditNotes().get(0).getTotal());
				
				// UPDATE Credit Note
				newCNs.getCreditNotes().get(0).setStatus(com.xero.models.accounting.CreditNote.StatusEnum.AUTHORISED);
				CreditNotes updatedCreditNote = accountingApi.updateCreditNote(newCreditNoteId, newCNs);				
				messages.add("Update a CreditNote - Ref : " + updatedCreditNote.getCreditNotes().get(0).getReference());
				
				// Allocate Credit Note
				Allocations allocations = new Allocations();
				Allocation allocation = new Allocation();
				
			    where =  "Status==\"AUTHORISED\"&&Type==\"ACCPAY\"";
			    Invoices allInvoices = accountingApi.getInvoices(ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp);
				Invoice inv = new Invoice();
				inv.setInvoiceID(allInvoices.getInvoices().get(0).getInvoiceID());
				allocation.setInvoice(inv);
				allocation.setAmount(1.0f);
				LocalDate currDate = LocalDate.now();
				allocation.setDate(currDate);
				allocations.addAllocationsItem(allocation);
				where = null;
				
				Allocations allocatedCreditNote = accountingApi.createCreditNoteAllocation(newCreditNoteId,allocations);
				messages.add("Update CreditNote Allocation - Amount : " + allocatedCreditNote.getAllocations().get(0).getAmount());
				
				// Get Invoice History
				HistoryRecords history = accountingApi.getCreditNoteHistory(creditNoteID);
				messages.add("History - count : " + history.getHistoryRecords().size() );
			
				// Create Invoice History
				HistoryRecords newHistoryRecords = new  HistoryRecords();
				HistoryRecord newHistoryRecord = new  HistoryRecord();
				newHistoryRecord.setDetails("Hello World");
				newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
				
				HistoryRecords newHistory = accountingApi.createCreditNoteHistory(creditNoteID, newHistoryRecords);
				messages.add("History - note added to  : " + newHistory.getHistoryRecords().get(0).getDetails());
			
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());	
			}
			
			
		} else if (object.equals("Currencies")) {

			/* CURRENCY  */
			// JSON - incomplete
			try {	
				//Get All
				Currencies currencies = accountingApi.getCurrencies(where, order);
				messages.add("Get all Currencies - Total : " + currencies.getCurrencies().size());
				
				// Create New
				// Error: 400
				/*
				Currency curr = new Currency();
				curr.setCode("SGD");
				Currencies currs = new Currencies();
				currs.addCurrenciesItem(curr);
				Currencies newCurrency = accountingApi.createCurrency(currs);
				messages.add("New Currencies - Code : " + newCurrency.getCurrencies().get(0).getCode());
				*/
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());	
			}	
			
		} else if (object.equals("Employees")) {

			/*  EMPLOYEE */
			// JSON 
			try {	
				// Create
				Employee employee = new Employee();
				employee.setFirstName("Sam");
				employee.setLastName("Jackson" + SampleData.loadRandomNum());
				ExternalLink extLink = new ExternalLink();
				extLink.setUrl("http://twitter.com/#!/search/Homer+Simpson");
				employee.setExternalLink(extLink);
				Employees emps = new Employees();
				emps.addEmployeesItem(employee);
				
				Employees newEmployee = accountingApi.createEmployee(emps);
				messages.add("Create an Employee - Last Name : " + newEmployee.getEmployees().get(0).getLastName());
		
				// Update				
				newEmployee.getEmployees().get(0).setLastName("Anderson" + SampleData.loadRandomNum());
				Employees updateEmployee = accountingApi.updateEmployee(newEmployee.getEmployees().get(0).getEmployeeID(),newEmployee);
				messages.add("Update an Employee - Last Name : " + updateEmployee.getEmployees().get(0).getLastName());
			
				//Get All
				Employees employees = accountingApi.getEmployees(ifModifiedSince, where, order);
				messages.add("Get all Employees - Total : " + employees.getEmployees().size());
				
				// Get One
				Employees oneEmployee = accountingApi.getEmployee(employees.getEmployees().get(0).getEmployeeID());
				messages.add("Get one Employees - Name : " + oneEmployee.getEmployees().get(0).getFirstName());
				
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());	
			}
			
		} else if (object.equals("ExpenseClaims")) {
			/*  EXPENSE CLAIM */
			//Create
		    where = "IsSubscriber==true";
			Users users = accountingApi.getUsers(ifModifiedSince, where, order);
			where = null;
			
		    where = "ShowInExpenseClaims==true&&Status==\"ACTIVE\"";
			Accounts accounts = accountingApi.getAccounts(ifModifiedSince, where, order);
			where = null;
			
			if (users.getUsers().size() > 0) {
				User user = new User();
				user.setUserID(users.getUsers().get(0).getUserID());
				
				Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				Contact useContact = new Contact();
				useContact.setContactID(contacts.getContacts().get(0).getContactID());
				
				// CREATE NEW RECEIPT
				Receipts receipts = new Receipts();
				Receipt receipt = new Receipt();
				
				LineItem li = new LineItem();
				li.setAccountCode(accounts.getAccounts().get(0).getCode());
				li.setDescription("Foobar");
				li.setQuantity(2.0f);
				li.setUnitAmount(20.00f);
				li.setLineAmount(40.00f);
				li.setTaxType(TaxType.NONE);
				
				receipt.addLineitemsItem(li);
				receipt.setUser(user);
				receipt.lineAmountTypes(LineAmountTypes.NOTAX);
				receipt.contact(useContact);
				receipt.setStatus(com.xero.models.accounting.Receipt.StatusEnum.DRAFT);
				receipts.addReceiptsItem(receipt);
				Receipts newReceipts = accountingApi.createReceipt(receipts);
	
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
				ExpenseClaims newExpenseClaims = accountingApi.createExpenseClaim(createExpenseClaims, summarizeErrors);
				messages.add("Create new Expense Claim - Status : " + newExpenseClaims.getExpenseClaims().get(0).getStatus());
				
				// UPDATE EXPENSE CLAIM
				createExpenseClaims.getExpenseClaims().get(0).setStatus(com.xero.models.accounting.ExpenseClaim.StatusEnum.AUTHORISED);
				UUID expenseClaimID = newExpenseClaims.getExpenseClaims().get(0).getExpenseClaimID();
				ExpenseClaims updateExpenseClaims = accountingApi.updateExpenseClaim(expenseClaimID, createExpenseClaims);
				messages.add("Update new Expense Claim - Status : " + updateExpenseClaims.getExpenseClaims().get(0).getStatus());
				
				//Get All Expense Claims
				ExpenseClaims expenseClaims = accountingApi.getExpenseClaims(ifModifiedSince, where, order);
				messages.add("Get all Expense Claim - Total : " + expenseClaims.getExpenseClaims().size());
				
				// Get One Expense Claim
				ExpenseClaims oneExpenseClaim = accountingApi.getExpenseClaim(expenseClaims.getExpenseClaims().get(0).getExpenseClaimID());
				messages.add("Get one Expense Claim - Total : " + oneExpenseClaim.getExpenseClaims().get(0).getStatus());
				
				// VOID EXPENSE CLAIM
				createExpenseClaims.getExpenseClaims().get(0).setStatus(com.xero.models.accounting.ExpenseClaim.StatusEnum.VOIDED);
				ExpenseClaims voidExpenseClaims = accountingApi.updateExpenseClaim(expenseClaimID, createExpenseClaims);
				messages.add("Void new Expense Claim - Status : " + voidExpenseClaims.getExpenseClaims().get(0).getStatus());
				
				// Get Invoice History
				HistoryRecords history = accountingApi.getExpenseClaimHistory(expenseClaimID);
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
		
		} else if (object.equals("Invoices")) {
			/*  INVOICE */	
			// GET Invoice As a PDF
			Invoices myInvoicesForPDF = accountingApi.getInvoices(ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp);
			UUID invoiceIDForPDF = myInvoicesForPDF.getInvoices().get(0).getInvoiceID();
			ByteArrayInputStream InvoiceNoteInput	 = accountingApi.getInvoiceAsPdf(invoiceIDForPDF, "application/pdf");
			String InvoiceFileName = "InvoiceAsPDF.pdf";			
			String InvoiceSaveFilePath = saveFile(InvoiceNoteInput,InvoiceFileName);
			messages.add("Get Invoice attachment - save it here: " + InvoiceSaveFilePath);

			// Create Invoice
		    where = "Type==\"REVENUE\"";
			Accounts accounts = accountingApi.getAccounts(ifModifiedSince, where, order);
			String accountCodeForInvoice = accounts.getAccounts().get(0).getCode();
			where = null;
			
			Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
			UUID contactIDForInvoice = contacts.getContacts().get(0).getContactID();
			
			Contact useContact = new Contact();
			useContact.setContactID(contactIDForInvoice);
			
			Invoices newInvoices = new Invoices();
			Invoice myInvoice = new Invoice();
			
			LineItem li = new LineItem();
			li.setAccountCode(accountCodeForInvoice);
			li.setDescription("Acme Tires");
			li.setQuantity(2f);
			li.setUnitAmount(20.00f);
			li.setLineAmount(40.00f);
			li.setTaxType(TaxType.NONE);
			
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
			
			Invoices newInvoice = accountingApi.createInvoice(newInvoices, summarizeErrors);
			messages.add("Create invoice - Reference : " + newInvoice.getInvoices().get(0).getReference());
			
			UUID newInvoiceID = newInvoice.getInvoices().get(0).getInvoiceID();
			Invoices updateInvoices = new Invoices();
			Invoice updateInvoice = new Invoice();
			updateInvoice.setInvoiceID(newInvoiceID);
			updateInvoice.setReference("Red Fish, Blue Fish");
			updateInvoices.addInvoicesItem(updateInvoice);
			
			Invoices updatedInvoice = accountingApi.updateInvoice(newInvoiceID,updateInvoices);
			messages.add("Update invoice - Reference : " + updatedInvoice.getInvoices().get(0).getReference());
			
			//Get All
			Invoices invoices = accountingApi.getInvoices(ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp);
			messages.add("Get all invoices - Total : " + invoices.getInvoices().size());
			
			//Get Invoice If-Modified-Since
			OffsetDateTime invModified =  OffsetDateTime.of(LocalDateTime.of(2018, 12, 06, 15, 00), ZoneOffset.UTC);
			
			System.out.println(invModified.toString());

			Invoices invoicesSince = accountingApi.getInvoices(invModified, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp);
			messages.add("Get all invoices - Since Modfied Date - Total : " + invoicesSince.getInvoices().size());
		
			// Get One
			Invoices oneInvoice = accountingApi.getInvoice(invoices.getInvoices().get(0).getInvoiceID());
			messages.add("Get one invoice - total : " + oneInvoice.getInvoices().get(0).getTotal());
			LocalDate myDate = oneInvoice.getInvoices().get(0).getDate();
			OffsetDateTime myUTC = oneInvoice.getInvoices().get(0).getUpdatedDateUTC();
	
			// Get Online Invoice
			OnlineInvoices onlineInvoice = accountingApi.getOnlineInvoice(newInvoiceID);
			messages.add("Get Online invoice - URL : " + onlineInvoice.getOnlineInvoices().get(0).getOnlineInvoiceUrl());
		
			// Email Invoice
			RequestEmpty empty = new RequestEmpty();
			Response204 emailInvoice = accountingApi.emailInvoice(newInvoiceID,empty);
			messages.add("Email invoice - response : " + emailInvoice.toString() );
		
			// Get Invoice History
			HistoryRecords history = accountingApi.getInvoiceHistory(newInvoiceID);
			messages.add("History - count : " + history.getHistoryRecords().size() );
		
			// Create Invoice History
			HistoryRecords newHistoryRecords = new  HistoryRecords();
			HistoryRecord newHistoryRecord = new  HistoryRecord();
			newHistoryRecord.setDetails("Hello World");
			newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
			HistoryRecords newHistory = accountingApi.createInvoiceHistory(newInvoiceID,newHistoryRecords);
			messages.add("History - note added to  : " + newHistory);
			
			// CREATE invoice attachment
			Invoices myInvoices = accountingApi.getInvoices(ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp);
			UUID invoiceID = myInvoices.getInvoices().get(0).getInvoiceID();
			InputStream inputStream = JsonConfig.class.getResourceAsStream("/helo-heros.jpg");
			byte[] bytes = IOUtils.toByteArray(inputStream);
			
			String newFileName = "sample2.jpg";
			Attachments createdAttachments = accountingApi.createInvoiceAttachmentByFileName(invoiceID, newFileName, bytes);
			messages.add("Attachment to Invoice complete - ID: " + createdAttachments.getAttachments().get(0).getAttachmentID());
	 		
			// GET Invoice Attachment 
			Attachments attachments = accountingApi.getInvoiceAttachments(invoiceID);
			System.out.println(attachments.getAttachments().get(0).getFileName());
			UUID attachementId = attachments.getAttachments().get(0).getAttachmentID();
			String contentType = attachments.getAttachments().get(0).getMimeType();
			ByteArrayInputStream InvoiceAttachmentInput	 = accountingApi.getInvoiceAttachmentById(invoiceID,attachementId, contentType);

			String InvoiceAttachmentFileName = attachments.getAttachments().get(0).getFileName();
			String InvoiceAttachmentSaveFilePath = saveFile(InvoiceAttachmentInput,InvoiceAttachmentFileName);
			messages.add("Get Invoice attachment - save it here: " + InvoiceAttachmentSaveFilePath);				
			
		} else if (object.equals("InvoiceReminders")) {
			/* INVOICE REMINDER */
			try {				
				InvoiceReminders invReminders = accountingApi.getInvoiceReminders();
				messages.add("Get a Invoice Reminder - Is Enabled: " + invReminders.getInvoiceReminders().get(0).getEnabled() );
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());	
			}
		} else if (object.equals("Items")) {
			/* ITEM */
			try {
				// Create Items
				Items myItems = new Items();
				Item myItem = new Item();
				myItem.setCode("abc" + SampleData.loadRandomNum());
				myItem.setDescription("foobar");
				myItem.setName("Hello"+SampleData.loadRandomNum());
				myItems.addItemsItem(myItem);
				Items newItems = accountingApi.createItem(myItems);
				messages.add("Create new item - Description : " + newItems.getItems().get(0).getDescription());
				UUID newItemId = newItems.getItems().get(0).getItemID();
				
				// Update Item
				newItems.getItems().get(0).setDescription("Barfoo");
				Items updateItem = accountingApi.updateItem(newItemId, newItems);
				messages.add("Update item - Description : " + updateItem.getItems().get(0).getDescription());
				
				//Get All Items
				Items items = accountingApi.getItems(ifModifiedSince, where, order);
				messages.add("Get all items - Total : " + items.getItems().size());
				
				// Get One Item
				UUID itemId = items.getItems().get(0).getItemID();
				Items oneItem = accountingApi.getItem(itemId);
				messages.add("Get one item - Description : " + oneItem.getItems().get(0).getDescription());
			
				// Get Invoice History
				HistoryRecords history = accountingApi.getItemHistory(itemId);
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
				Response204 deleteItem = accountingApi.deleteItem(newItemId);
				messages.add("Delete one item - status : " + deleteItem.getStatus());
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());	
			}
			
		} else if (object.equals("Journals")) {
			/* JOURNAL */
			try {
				boolean paymentsOnly = false;
				// GET all Journals
				Journals journals = accountingApi.getJournals(ifModifiedSince, null, paymentsOnly);
				messages.add("Get Journals - total : " + journals.getJournals().size());
				
				// GET Journal with offset
				Journals journalsOffset = accountingApi.getJournals(ifModifiedSince, null, paymentsOnly);
				messages.add("Get Journals offset - total : " + journalsOffset.getJournals().size());
				
				// GET one Journal
				UUID journalId = journals.getJournals().get(0).getJournalID();
				Journals oneJournal = accountingApi.getJournal(journalId);
				messages.add("Get one Journal - number : " + oneJournal.getJournals().get(0).getJournalNumber());
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());	
			}
		} else if (object.equals("LinkedTransactions")) {

			/* LINKED TRANSACTION */
			try {
				// Create Linked Transaction
			    where = "Type==\"EXPENSE\"";
				Accounts accounts = accountingApi.getAccounts(ifModifiedSince, where, order);
				where = null;
				
				Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				Contact useContact = new Contact();
				useContact.setContactID(contacts.getContacts().get(0).getContactID());
				
				Invoices newInvoices = new Invoices();
				Invoice myInvoice = new Invoice();
				
				LineItem li = new LineItem();
				li.setAccountCode(accounts.getAccounts().get(0).getCode());
				li.setDescription("Acme Tires");
				li.setQuantity(2f);
				li.setUnitAmount(20.00f);
				li.setLineAmount(40.00f);
				li.setTaxType(TaxType.NONE);
				
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
				
				Invoices newInvoice = accountingApi.createInvoice(newInvoices, summarizeErrors);
				
				UUID sourceTransactionID1 = newInvoice.getInvoices().get(0).getInvoiceID();
				UUID sourceLineItemID1 = newInvoice.getInvoices().get(0).getLineItems().get(0).getLineItemID();
				LinkedTransactions newLinkedTransactions = new LinkedTransactions();
				LinkedTransaction newLinkedTransaction = new LinkedTransaction();
				newLinkedTransaction.setSourceTransactionID(sourceTransactionID1);
				newLinkedTransaction.setSourceLineItemID(sourceLineItemID1);
				newLinkedTransactions.addLinkedTransactionsItem(newLinkedTransaction);
				
				LinkedTransactions createdLinkedTransaction = accountingApi.createLinkedTransaction(newLinkedTransactions);
				messages.add("Create LinkedTransaction - Status : " + createdLinkedTransaction.getLinkedTransactions().get(0).getStatus());
				
				// Created Linked Transaction 2
				Contact contact = new Contact();
				contact.setName("Foo" + SampleData.loadRandomNum());
				contact.setEmailAddress("sid" + SampleData.loadRandomNum() + "@blah.com");
				Contacts newContact = accountingApi.createContact(contact);
				UUID newContactID = newContact.getContacts().get(0).getContactID();
				
				Invoices newInvoice2 = accountingApi.createInvoice(newInvoices, summarizeErrors);
				
				UUID sourceTransactionID2 = newInvoice2.getInvoices().get(0).getInvoiceID();
				UUID sourceLineItemID2 = newInvoice2.getInvoices().get(0).getLineItems().get(0).getLineItemID();
				LinkedTransactions newLinkedTransactions2 = new LinkedTransactions();
				LinkedTransaction newLinkedTransaction2 = new LinkedTransaction();
				newLinkedTransaction2.setSourceTransactionID(sourceTransactionID2);
				newLinkedTransaction2.setSourceLineItemID(sourceLineItemID2);
				newLinkedTransaction2.setContactID(newContactID);
				newLinkedTransactions2.addLinkedTransactionsItem(newLinkedTransaction2);
				
				LinkedTransactions createdLinkedTransaction2 = accountingApi.createLinkedTransaction(newLinkedTransactions2);
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
				
				Invoices newInvoiceAccRec = accountingApi.createInvoice(newInvoicesAccRec, summarizeErrors);
				UUID sourceTransactionID4 = newInvoiceAccRec.getInvoices().get(0).getInvoiceID();
				UUID sourceLineItemID4 = newInvoiceAccRec.getInvoices().get(0).getLineItems().get(0).getLineItemID();
				
				Invoices newInvoice3 = accountingApi.createInvoice(newInvoices, summarizeErrors);
				
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
				
				LinkedTransactions createdLinkedTransaction3 = accountingApi.createLinkedTransaction(newLinkedTransactions3);
				messages.add("Create LinkedTransaction 3 - Status : " + createdLinkedTransaction3.getLinkedTransactions().get(0).getStatus());
				
				// GET all Link Transactions
				
				int page = 1;
				String linkedTransactionID = null;
				String sourceTransactionID = null;
				String targetTransactionID = null;
				String status = null;
				String contactID = null;
				LinkedTransactions linkTransactions = accountingApi.getLinkedTransactions(page, linkedTransactionID, sourceTransactionID, contactID, status, targetTransactionID);
				messages.add("Get Link Transactions - total : " + linkTransactions.getLinkedTransactions().size());
				
				// GET all Link Transactions
				UUID linkedTransactionID2 = linkTransactions.getLinkedTransactions().get(0).getLinkedTransactionID();
				LinkedTransactions oneLinkTransaction = accountingApi.getLinkedTransaction(linkedTransactionID2);
				messages.add("Get one Link Transaction - Status : " + oneLinkTransaction.getLinkedTransactions().get(0).getStatus());
				
				// DELETE LINKEDTRANSACTION
				UUID newLinkedTransactionID = createdLinkedTransaction.getLinkedTransactions().get(0).getLinkedTransactionID();
				Response204 deleteLinkedTransaction = accountingApi.deleteLinkedTransaction(newLinkedTransactionID);
				messages.add("Delete LinkedTransaction - Status : " + deleteLinkedTransaction.getStatus());
				
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());	
			}
			
		} else if (object.equals("ManualJournals")) {
			/* MANUAL JOURNAL */ 
			try {
				// Create Manual Journal
			    where = "Type==\"EXPENSE\"";
				Accounts accounts = accountingApi.getAccounts(ifModifiedSince, where, order);
				String accountCode = accounts.getAccounts().get(0).getCode();
				where = null;
				
				ManualJournals manualJournals = new ManualJournals();
				ManualJournal manualJournal = new ManualJournal();
				LocalDate currDate = LocalDate.now();
				manualJournal.setDate(currDate);
				manualJournal.setNarration("Foo bar");
				
				JournalLine credit = new JournalLine();
				credit.description("Hello there");
				credit.setAccountCode(accountCode);
				credit.setLineAmount("100");
				manualJournal.addJournalLinesItem(credit);
				
				JournalLine debit = new JournalLine();
				debit.description("Goodbye");
				debit.setAccountCode(accountCode);
				debit.setLineAmount("-100");
				manualJournal.addJournalLinesItem(debit);
				manualJournals.addManualJournalsItem(manualJournal);
				ManualJournals createdManualJournals = accountingApi.createManualJournal(manualJournals);
				messages.add("Create Manual Journal - Narration : " + createdManualJournals.getManualJournals().get(0).getNarration());
				
				// GET all Manual Journal
				ManualJournals getManualJournals = accountingApi.getManualJournals(ifModifiedSince, where, order, null);
				messages.add("Get Manual Journal - total : " + getManualJournals.getManualJournals().size());
				
				// GET one Manual Journal
				UUID manualJournalId = getManualJournals.getManualJournals().get(0).getManualJournalID();			
				ManualJournals oneManualJournal = accountingApi.getManualJournal(manualJournalId);
				messages.add("Get one Manual Journal - Narration : " + oneManualJournal.getManualJournals().get(0).getNarration());
				
				// Update Manual Journal
				ManualJournals updateManualJournals = new ManualJournals();
				ManualJournal updateManualJournal = new ManualJournal();
				updateManualJournal.setManualJournalID(manualJournalId);
				updateManualJournal.setNarration("Hello Xero");
				updateManualJournals.addManualJournalsItem(updateManualJournal);
				ManualJournals updatedManualJournal = accountingApi.updateManualJournal(manualJournalId,updateManualJournals);
				messages.add("Update Manual Journal - Narration : " + updatedManualJournal.getManualJournals().get(0).getNarration());
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());	
			}
	
		} else if (object.equals("Organisations")) {
			/* Organisation */ 
			try {
				Organisations organisations = accountingApi.getOrganisations();
				messages.add("Get a Organisation - Name : " + organisations.getOrganisations().get(0).getName());
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());	
			}			
		} else if (object.equals("Overpayments")) {
			/* OVERPAYMENT */
		    where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
			Accounts accountsWhere = accountingApi.getAccounts(ifModifiedSince, where, order);
			Account bankAccount = new Account();
			bankAccount.setAccountID(accountsWhere.getAccounts().get(0).getAccountID());
			where = null;
			
		    where = "SystemAccount==\"DEBTORS\"";
			Accounts arAccounts = accountingApi.getAccounts(ifModifiedSince, where, order);
			Account arAccount = arAccounts.getAccounts().get(0);
			where = null;
			
			Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
			Contact useContact = new Contact();
			useContact.setContactID(contacts.getContacts().get(0).getContactID());
			
			// Maker sure we have at least 2 banks
			if(accountsWhere.getAccounts().size() > 0) {	
				List<LineItem> lineItems = new ArrayList<>();
				LineItem li = new LineItem();
				li.setAccountCode(arAccount.getCode());
				li.setDescription("Foobar");
				li.setQuantity(1f);
				li.setUnitAmount(20.00f);
				lineItems.add(li);
				
				BankTransaction bt = new BankTransaction();
				bt.setBankAccount(bankAccount);
				bt.setContact(useContact);
				bt.setLineitems(lineItems);
				bt.setType(com.xero.models.accounting.BankTransaction.TypeEnum.RECEIVE_OVERPAYMENT);
				BankTransactions bts = new BankTransactions();
				bts.addBankTransactionsItem(bt);					
				BankTransactions newBankTransaction = accountingApi.createBankTransaction(bts, summarizeErrors);
				
				Overpayments overpayments = accountingApi.getOverpayments(ifModifiedSince, where, order, null);
				messages.add("Get a Overpayments - Count : " + overpayments.getOverpayments().size());
				
				if(overpayments.getOverpayments().size() > 0) {	
					UUID overpaymentId = overpayments.getOverpayments().get(2).getOverpaymentID();
					Overpayments oneOverpayment = accountingApi.getOverpayment(overpaymentId);
					messages.add("Get one Overpayment - Total : " + oneOverpayment.getOverpayments().get(0).getTotal());					
					
				    where = "Status==\"AUTHORISED\"&&Type==\"ACCREC\"";					
					Invoices allInvoices = accountingApi.getInvoices(ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp);
					Invoice inv = new Invoice();
					inv.setInvoiceID(allInvoices.getInvoices().get(0).getInvoiceID());
					where = null;
					
					Allocations allocations = new Allocations();
					Allocation allocation = new Allocation();
					allocation.setAmount(1.0f);
					LocalDate currDate = LocalDate.now();
					allocation.setDate(currDate);
					allocation.setInvoice(inv);
					allocations.addAllocationsItem(allocation);
					
					Allocations newAllocation = accountingApi.createOverpaymentAllocation(overpaymentId, allocations);
					messages.add("Create OverPayment allocation - Amt : " + newAllocation.getAllocations().get(0).getAmount());					
				
					// Get History
					HistoryRecords history = accountingApi.getOverpaymentHistory(overpaymentId);
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
			Invoices allInvoices = accountingApi.getInvoices(ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp);
			Invoice inv = new Invoice();
			inv.setInvoiceID(allInvoices.getInvoices().get(0).getInvoiceID());
			where = null;
		
		    where = "EnablePaymentsToAccount==true";
			Accounts accountsWhere = accountingApi.getAccounts(ifModifiedSince, where, order);
			Account paymentAccount = new Account();
			paymentAccount.setCode(accountsWhere.getAccounts().get(0).getCode());
			where = null;
			
			Payments createPayments = new Payments();
			Payment createPayment = new Payment();
			createPayment.setAccount(paymentAccount);
			createPayment.setInvoice(inv);
			createPayment.setAmount(1.00f);

			LocalDate currDate = LocalDate.now();
			createPayment.setDate(currDate);
			createPayments.addPaymentsItem(createPayment);
			
			Payments newPayments = accountingApi.createPayment(createPayments);
			messages.add("Create Payments - Amt : " + newPayments.getPayments().get(0).getAmount());					
			
			// GET all Payments
			Payments payments = accountingApi.getPayments(ifModifiedSince, where, order);
			messages.add("Get Payments - Total : " + payments.getPayments().size());					
			
			// GET one Payment
			UUID paymentID = payments.getPayments().get(0).getPaymentID();
			Payments onePayment = accountingApi.getPayment(paymentID);
			messages.add("Get Payments - Amount : " + onePayment.getPayments().get(0).getAmount());		
			
			// Get  History
			HistoryRecords allHistory = accountingApi.getPaymentHistory(paymentID);
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
			/* Payment Services	*/
			try {
				// CREATE PaymentService
				PaymentServices newPaymentServices = new PaymentServices();
				PaymentService newPaymentService = new PaymentService();
				newPaymentService.setPaymentServiceName("PayUp"+SampleData.loadRandomNum());
				newPaymentService.setPaymentServiceUrl("https://www.payupnow.com/");
				newPaymentService.setPayNowText("Time To PayUp");
				newPaymentServices.addPaymentServicesItem(newPaymentService);
				PaymentServices createdPaymentService = accountingApi.createPaymentService(newPaymentServices);
				messages.add("Create PaymentServices - name : " + createdPaymentService.getPaymentServices().get(0).getPaymentServiceName());				
			
				// GET all Payments
				PaymentServices paymentServices = accountingApi.getPaymentServices();
				messages.add("Get PaymentServices - Total : " + paymentServices.getPaymentServices().size());					
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());	
			}
		} else if (object.equals("Prepayments")) {
			/* PREPAYMENT */
		    where = "Status==\"ACTIVE\"&&Type==\"BANK\"";
			Accounts accountsWhere = accountingApi.getAccounts(ifModifiedSince, where, order);
			Account bankAccount = new Account();
			bankAccount.setAccountID(accountsWhere.getAccounts().get(0).getAccountID());
			where = null;
			
		    where = "Type==\"EXPENSE\"";
			Accounts arAccounts = accountingApi.getAccounts(ifModifiedSince, where, order);
			Account arAccount = arAccounts.getAccounts().get(0);
			where = null;
			
			Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
			Contact useContact = new Contact();
			useContact.setContactID(contacts.getContacts().get(0).getContactID());
			
			// Maker sure we have at least 2 banks
			if(accountsWhere.getAccounts().size() > 0) {	
				List<LineItem> lineItems = new ArrayList<>();
				LineItem li = new LineItem();
				li.setAccountCode(arAccount.getCode());
				li.setDescription("Foobar");
				li.setQuantity(1f);
				li.setTaxType(TaxType.NONE);
				li.setUnitAmount(20.00f);
				lineItems.add(li);
				
				BankTransaction bt = new BankTransaction();
				bt.setBankAccount(bankAccount);
				bt.setContact(useContact);
				bt.setLineitems(lineItems);
				bt.setType(com.xero.models.accounting.BankTransaction.TypeEnum.RECEIVE_PREPAYMENT);
				BankTransactions bts = new BankTransactions();
				bts.addBankTransactionsItem(bt);					
				BankTransactions newBankTransaction = accountingApi.createBankTransaction(bts, summarizeErrors);
				where =  "Status==\"AUTHORISED\" && TYPE==\"RECEIVE-PREPAYMENT\"";
				Prepayments prepayments = accountingApi.getPrepayments(ifModifiedSince, where, order, null);
				messages.add("Get a Prepayments - Count : " + prepayments.getPrepayments().size());
				where = null;
				if(prepayments.getPrepayments().size() > 0) {	
					UUID prepaymentId = prepayments.getPrepayments().get(0).getPrepaymentID();
					Prepayments onePrepayment = accountingApi.getPrepayment(prepaymentId);
					messages.add("Get one Prepayment - Total : " + onePrepayment.getPrepayments().get(0).getTotal());
				    where = "Status==\"AUTHORISED\"&&Type==\"ACCREC\"";					
					Invoices allInvoices = accountingApi.getInvoices(ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, null, includeArchived, createdByMyApp);
					Invoice inv = new Invoice();
					inv.setInvoiceID(allInvoices.getInvoices().get(0).getInvoiceID());
					where = null;
					
					Allocations allocations = new Allocations();
					Allocation allocation = new Allocation();
					allocation.setAmount(1.0f);
					LocalDate currDate = LocalDate.now();
					allocation.setDate(currDate);
					allocation.setInvoice(inv);
					allocations.addAllocationsItem(allocation);
					
					//Allocations newAllocation = accountingApi.createPrepaymentAllocation(prepaymentId, allocations);
					//messages.add("Create PrePayment allocation - Amt : " + newAllocation.getAllocations().get(0).getAmount());		
					
					// Get History
					HistoryRecords history = accountingApi.getPrepaymentHistory(prepaymentId);
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
			/* PURCHASE ORDERS */
			try {
				// CREATE Purchase Order
			    where = "Type==\"EXPENSE\"";
				Accounts arAccounts = accountingApi.getAccounts(ifModifiedSince, where, order);
				Account arAccount = arAccounts.getAccounts().get(0);
				where = null;
				
				PurchaseOrders purchaseOrders = new PurchaseOrders();
				PurchaseOrder purchaseOrder = new PurchaseOrder();
				LocalDate currDate = LocalDate.now();
				purchaseOrder.setDate(currDate);
				Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				Contact useContact = new Contact();
				useContact.setContactID(contacts.getContacts().get(0).getContactID());
				purchaseOrder.setContact(useContact);
				
				List<LineItem> lineItems = new ArrayList<>();
				LineItem li = new LineItem();
				li.setAccountCode(arAccount.getCode());
				li.setDescription("Foobar");
				li.setQuantity(1f);
				li.setUnitAmount(20.00f);
				lineItems.add(li);
				purchaseOrder.setLineItems(lineItems);
				purchaseOrders.addPurchaseOrdersItem(purchaseOrder);
				PurchaseOrders createdPurchaseOrders = accountingApi.createPurchaseOrder(purchaseOrders, summarizeErrors);
				messages.add("Create Purchase order - total : " + createdPurchaseOrders.getPurchaseOrders().get(0).getTotal());					
			
				// UPDATE Purchase Orders
				UUID newPurchaseOrderID = createdPurchaseOrders.getPurchaseOrders().get(0).getPurchaseOrderID();
				createdPurchaseOrders.getPurchaseOrders().get(0).setAttentionTo("Jimmy");
				PurchaseOrders updatePurchaseOrders = accountingApi.updatePurchaseOrder(newPurchaseOrderID, createdPurchaseOrders);
				messages.add("Update Purchase order - attn : " + updatePurchaseOrders.getPurchaseOrders().get(0).getAttentionTo());								
				
				// GET Purchase Orders
				String status = null;
				String dateFrom = null;
				String dateTo = null;
				PurchaseOrders allPurchaseOrders = accountingApi.getPurchaseOrders(ifModifiedSince, status, dateFrom, dateTo, order, null);
				messages.add("Get Purchase orders - Count : " + allPurchaseOrders.getPurchaseOrders().size());					
			
				// GET one Purchase Order
				UUID purchaseOrderID = allPurchaseOrders.getPurchaseOrders().get(0).getPurchaseOrderID();
				PurchaseOrders onePurchaseOrder = accountingApi.getPurchaseOrder(purchaseOrderID);
				messages.add("Get one Purchase order - Total : " + onePurchaseOrder.getPurchaseOrders().get(0).getTotal());					
				
				// DELETE Purchase Orders
				createdPurchaseOrders.getPurchaseOrders().get(0).setStatus(com.xero.models.accounting.PurchaseOrder.StatusEnum.DELETED);
				PurchaseOrders deletePurchaseOrders = accountingApi.updatePurchaseOrder(newPurchaseOrderID, createdPurchaseOrders);
				messages.add("Delete Purchase order - Status : " + deletePurchaseOrders.getPurchaseOrders().get(0).getStatus());								
				
				// Get History
				HistoryRecords history = accountingApi.getInvoiceHistory(purchaseOrderID);
				messages.add("History - count : " + history.getHistoryRecords().size() );
			
				// Create History
				HistoryRecords newHistoryRecords = new  HistoryRecords();
				HistoryRecord newHistoryRecord = new  HistoryRecord();
				newHistoryRecord.setDetails("Hello World");
				newHistoryRecords.addHistoryRecordsItem(newHistoryRecord);
				HistoryRecords newHistory = accountingApi.createPurchaseOrderHistory(purchaseOrderID,newHistoryRecords);
				messages.add("History - note added to  : " + newHistory.getHistoryRecords().get(0).getDetails());
			
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());
			}
		} else if (object.equals("Receipts")) {
			/* RECEIPTS */
			try {
				//Create
			    where = "IsSubscriber==true";
				Users users = accountingApi.getUsers(ifModifiedSince, where, order);
				where = null;
				
			    where = "ShowInExpenseClaims==true && Status==\"ACTIVE\"";
				Accounts accounts = accountingApi.getAccounts(ifModifiedSince, where, order);
				where = null;
				
				User useUser = new User();
				useUser.setUserID(users.getUsers().get(0).getUserID());
					
				Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				Contact useContact = new Contact();
				useContact.setContactID(contacts.getContacts().get(0).getContactID());
					
				// CREATE NEW RECEIPT
				Receipts receipts = new Receipts();
				Receipt receipt = new Receipt();
				
				LineItem li = new LineItem();
				li.setAccountCode(accounts.getAccounts().get(0).getCode());
				li.setDescription("Foobar");
				li.setQuantity(2f);
				li.setUnitAmount(20.00f);
				li.setLineAmount(40.00f);
				li.setTaxType(TaxType.NONE);
				
				receipt.addLineitemsItem(li);
				receipt.setUser(useUser);
				receipt.lineAmountTypes(LineAmountTypes.NOTAX);
				receipt.contact(useContact);
				receipt.setStatus(com.xero.models.accounting.Receipt.StatusEnum.DRAFT);
				receipts.addReceiptsItem(receipt);
				Receipts newReceipts = accountingApi.createReceipt(receipts);
				messages.add("Create Receipts - Total : " + newReceipts.getReceipts().get(0).getTotal());								
				
				// UPDATE Receipts
				UUID newReceiptId = newReceipts.getReceipts().get(0).getReceiptID();
				newReceipts.getReceipts().get(0).setReference("Foobar");
				Receipts updateReceipts = accountingApi.updateReceipt(newReceiptId, newReceipts);
				messages.add("Create Receipts - Ref : " + updateReceipts.getReceipts().get(0).getReference());								
				
				// GET all Receipts
				Receipts allReceipts = accountingApi.getReceipts(ifModifiedSince, where, order);
				messages.add("Create Receipts - Count : " + allReceipts.getReceipts().size());								
				
				// GET one Receipts
				UUID receiptID = allReceipts.getReceipts().get(0).getReceiptID();
				Receipts oneReceipts = accountingApi.getReceipt(receiptID);
				messages.add("Create Receipts - Total : " + oneReceipts.getReceipts().get(0).getTotal());								
				
				// Get History
				HistoryRecords history = accountingApi.getReceiptHistory(receiptID);
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
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());
			}					
		} else if (object.equals("RepeatingInvoices")) {
	        /* REPEATING INVOICE */
			try {
				// GET all Repeating Invoices
				RepeatingInvoices repeatingInvoices = accountingApi.getRepeatingInvoices(where, order);
				if ( repeatingInvoices.getRepeatingInvoices().size() > 0) {
					messages.add("Repeating Invoice - count : " + repeatingInvoices.getRepeatingInvoices().size() );
					
					// GET one Repeating Invoices
					UUID repeatingInvoiceID = repeatingInvoices.getRepeatingInvoices().get(0).getRepeatingInvoiceID();
					RepeatingInvoices repeatingInvoice = accountingApi.getRepeatingInvoice(repeatingInvoiceID);
					messages.add("Repeating Invoice - total : " + repeatingInvoice.getRepeatingInvoices().get(0).getTotal());
				
					// Get History
					HistoryRecords history = accountingApi.getRepeatingInvoiceHistory(repeatingInvoiceID);
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
			} catch (XeroApiException e) {
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
			
			Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
			UUID contactId = contacts.getContacts().get(0).getContactID();
			LocalDate xDate = LocalDate.now();
			LocalDate xFromDate = LocalDate.now();
			LocalDate xToDate = LocalDate.now();
			
			ReportWithRows reportAgedPayablesByContact = accountingApi.getReportAgedPayablesByContact(contactId, xDate, xFromDate, xToDate);
			messages.add("Get a Reports - Name:" + reportAgedPayablesByContact.getReports().get(0).getReportName());
			
			// AgedReceivablesByContact
			ReportWithRows reportAgedReceivablesByContact = accountingApi.getReportAgedReceivablesByContact(contactId, date, fromDate, toDate);
			messages.add("Get a Reports - Name:" + reportAgedReceivablesByContact.getReports().get(0).getReportName());
			
			// reportBalanceSheet
			ReportWithRows reportBalanceSheet = accountingApi.getReportBalanceSheet(toDate, null, null, trackingOptionID1, trackingOptionID2, standardLayout, paymentsOnly);
			messages.add("Get a Reports - Name:" + reportBalanceSheet.getReports().get(0).getReportName());
			
			// reportBankSummary
			ReportWithRows reportBankSummary = accountingApi.getReportBankSummary(toDate, null, null);
			messages.add("Get a Reports - Name:" + reportBankSummary.getReports().get(0).getReportName());
			
			// reportBASorGSTlist - AU and NZ only
			ReportWithRows reportTax = accountingApi.getReportBASorGSTList();
			System.out.println(reportTax.toString());
			
			// reportExecutiveSummary
			ReportWithRows reportExecutiveSummary = accountingApi.getReportExecutiveSummary(toDate);
			messages.add("Get a Reports - Name:" + reportExecutiveSummary.getReports().get(0).getReportName());
			
			// reportProfitandLoss
			fromDate = "2018-01-01";
		    toDate = "2018-12-31";
		    profitLossTimeframe = "MONTH";
		    standardLayout = true;
		    paymentsOnly = false;
			ReportWithRows reportProfitLoss = accountingApi.getReportProfitAndLoss(fromDate, toDate, null, profitLossTimeframe, trackingCategoryID, trackingCategoryID2, trackingOptionID, trackingOptionID2, standardLayout, paymentsOnly);
			messages.add("Get a Reports - Name:" + reportProfitLoss.getReports().get(0).getReportName());
			fromDate = null;
		    toDate = null;
						
			// reportTrialBalance
			ReportWithRows reportTrialBalance = accountingApi.getReportTrialBalance(toDate, paymentsOnly);
			messages.add("Get a Reports - Name:" + reportTrialBalance.getReports().get(0).getReportName());
			
		} else if (object.equals("TaxRates")) {
			/* TAX RATE   */
			try {
				// CREATE Tax Rate
				
				TaxRates newTaxRates = new TaxRates();
				TaxRate newTaxRate = new TaxRate();
				TaxComponent rate01 = new TaxComponent();
				rate01.setName("State Tax");
				rate01.setRate(2.25f);
				newTaxRate.setReportTaxType(ReportTaxTypeEnum.INPUT);
				newTaxRate.setName("SDKTax"+SampleData.loadRandomNum());
				newTaxRate.addTaxComponentsItem(rate01);
				newTaxRates.addTaxRatesItem(newTaxRate);
				
				TaxRates createdTaxRate = accountingApi.createTaxRate(newTaxRates);
				messages.add("CREATE TaxRate - name : " + createdTaxRate.getTaxRates().get(0).getName());
				
				// UDPATE Tax Rate
				newTaxRates.getTaxRates().get(0).setStatus(com.xero.models.accounting.TaxRate.StatusEnum.DELETED);
				TaxRates updatedTaxRate = accountingApi.updateTaxRate(newTaxRates);
				messages.add("UPDATED TaxRate - status : " + updatedTaxRate.getTaxRates().get(0).getStatus());
				
				// GET Tax Rate
				String taxType = null;
				TaxRates taxRates = accountingApi.getTaxRates(where, order, taxType);
				messages.add("GET TaxRate - cnt : " + taxRates.getTaxRates().size());
				
				// GET Tax Rate
			    taxType = "CAPEXINPUT2";
				TaxRates taxRatesByType = accountingApi.getTaxRates(where, order, taxType);
				messages.add("GET TaxRate by Cap Purchase Type : " + taxRatesByType.getTaxRates().size());
				
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());
			}
			
		} else if (object.equals("TrackingCategories")) {

			/* TRACKING CATEGORIES  */
			try {
				// GET Tracking Categories
				TrackingCategories trackingCategories = accountingApi.getTrackingCategories(where, order, includeArchived);
				int count = trackingCategories.getTrackingCategories().size();
				
				if (count == 2) {
					//DELETE Tracking Categories
					UUID trackingCategoryID = trackingCategories.getTrackingCategories().get(0).getTrackingCategoryID();
					TrackingCategories deletedTrackingCategories = accountingApi.deleteTrackingCategory(trackingCategoryID);
					messages.add("DELETED tracking categories - status : " + deletedTrackingCategories.getTrackingCategories().get(0).getStatus());					
				}
				
				// CREATE  Tracking Categories
				TrackingCategory newTrackingCategory = new TrackingCategory();
				newTrackingCategory.setName("Foo"+SampleData.loadRandomNum());
				TrackingCategories createdTrackingCategories = accountingApi.createTrackingCategory(newTrackingCategory);
				messages.add("CREATED tracking categories - name : " + createdTrackingCategories.getTrackingCategories().get(0).getName());					
			
				// UPDATE  Tracking Categories
				UUID newTrackingCategoryID = createdTrackingCategories.getTrackingCategories().get(0).getTrackingCategoryID();
				newTrackingCategory.setName("Foo"+SampleData.loadRandomNum());
				TrackingCategories updatedTrackingCategories = accountingApi.updateTrackingCategory(newTrackingCategoryID,newTrackingCategory);
				messages.add("UPDATED tracking categories - name : " + updatedTrackingCategories.getTrackingCategories().get(0).getName());					
				
				// GET one Tracking Categories
				UUID oneTrackingCategoryID = trackingCategories.getTrackingCategories().get(0).getTrackingCategoryID();
				TrackingCategories oneTrackingCategories = accountingApi.getTrackingCategory(oneTrackingCategoryID);
				messages.add("GET ONE tracking categories - name : " + oneTrackingCategories.getTrackingCategories().get(0).getName());
				
				// Create one Option
				TrackingOption option = new TrackingOption();
				option.setName("Bar"+SampleData.loadRandomNum());
				TrackingOptions newTrackingOptions = accountingApi.createTrackingOptions(oneTrackingCategoryID,option);
				messages.add("CREATE option - name : " + newTrackingOptions.getOptions().get(0).getName());
				
				// DELETE All options
				UUID newOptionId = newTrackingOptions.getOptions().get(0).getTrackingOptionID();                                 
				TrackingOptions deleteOptions = accountingApi.deleteTrackingOptions(oneTrackingCategoryID, newOptionId);
				messages.add("DELETE one option - Status : " + deleteOptions.getOptions().get(0).getStatus());
				
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());
			}
			
		} else if (object.equals("Users")) {
			/* USER */
			try {
				// GET Users
				Users users = accountingApi.getUsers(ifModifiedSince, where, order);
				messages.add("GET Users - cnt : " + users.getUsers().size());
				
				//GET One User
				UUID userID = users.getUsers().get(0).getUserID();
				Users user = accountingApi.getUser(userID);
				messages.add("GET Users - First Name : " + user.getUsers().get(0).getFirstName());
			} catch (XeroApiException e) {
				System.out.println(e.getMessage());
			}
		
		} else if (object.equals("Errors")) {
			try {
				Contact contact = new Contact();
				contact.setName("Sidney Maestre");
				Contacts createContact1 = accountingApi.createContact(contact);
				Contacts createContact2 = accountingApi.createContact(contact);	
			} catch (XeroApiException e) {
				System.out.println("Response Code: " + e.getResponseCode());
				System.out.println("Error Type: " + e.getError().getType());
				System.out.println("Error Number: " + e.getError().getErrorNumber());
				System.out.println("Error Message: " + e.getError().getMessage());
				if (e.getResponseCode() == 400) {
					System.out.println("Validation Message: " + e.getError().getElements().get(0).getValidationErrors().get(0).getMessage());
				}
			}
					
			try {
				Contacts contacts = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
				Contact useContact = new Contact();
				useContact.setContactID(contacts.getContacts().get(0).getContactID());
				
				Invoices newInvoices = new Invoices();
				Invoice myInvoice = new Invoice();
				
				LineItem li = new LineItem();
				li.setAccountCode("123456789");
				li.setDescription("Acme Tires");
				li.setQuantity(2f);
				li.setUnitAmount(20.00f);
				li.setLineAmount(40.00f);
				li.setTaxType(TaxType.NONE);
				
				myInvoice.addLineItemsItem(li);
				myInvoice.setContact(useContact);
				LocalDate dueDate =  LocalDate.of(2018,Month.OCTOBER,10);
				myInvoice.setDueDate(dueDate);
				LocalDate todayDate =  LocalDate.now();
				myInvoice.setDate(todayDate);
				
				myInvoice.setType(com.xero.models.accounting.Invoice.TypeEnum.ACCREC);
				myInvoice.setReference("One Fish, Two Fish");
				myInvoice.setStatus(com.xero.models.accounting.Invoice.StatusEnum.AUTHORISED);
				newInvoices.addInvoicesItem(myInvoice);
				
				Invoices newInvoice = accountingApi.createInvoice(newInvoices, summarizeErrors);
				messages.add("Create invoice - Reference : " + newInvoice.getInvoices().get(0).getReference());
			} catch (XeroApiException e) {
				System.out.println("Response Code: " + e.getResponseCode());
				System.out.println("Error Type: " + e.getError().getType());
				System.out.println("Error Number: " + e.getError().getErrorNumber());
				System.out.println("Error Message: " + e.getError().getMessage());
				if (e.getResponseCode() == 400) {
					System.out.println("Validation Message: " + e.getError().getElements().get(0).getValidationErrors().get(0).getMessage());
				}
			}
			
			try {
				UUID badContactId = UUID.fromString("bd2270c3-0000-4c11-9cfb-000b551c3f51");
				Contacts badContacts = accountingApi.getContact(badContactId);	
			} catch (XeroApiException e) {
				System.out.println("Response Code: " + e.getResponseCode());
				System.out.println("Error Type: " + e.getError().getType());
				System.out.println("Error Number: " + e.getError().getErrorNumber());
				System.out.println("Error Message: " + e.getError().getMessage());
				if (e.getResponseCode() == 400) {
					System.out.println("Validation Message: " + e.getError().getElements().get(0).getValidationErrors().get(0).getMessage());
				}
			}
			
			Contacts ContactList = accountingApi.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);
			int num4 = SampleData.findRandomNum(ContactList.getContacts().size());			
			UUID contactId = ContactList.getContacts().get(num4).getContactID();
			try {
				for(int i=65; i>1; i--){
					Contacts allMyContacts = accountingApi.getContact(contactId);
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
}
