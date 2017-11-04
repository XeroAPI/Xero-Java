package com.xero.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import com.xero.api.XeroClient;
import com.xero.model.*;

public class SampleData {
	
	public static XeroClient client = null;

	public SampleData(XeroClient client) {
		// TODO Auto-generated constructor stub
		SampleData.client = client;
	}
	
	public static ArrayOfAccount loadAccount() {
		ArrayOfAccount array = new ArrayOfAccount();
		Account account = new Account();
		int ranNum = loadRandomNum();
		account.setCode(Integer.toString(ranNum));
		account.setName("My Expense " + Integer.toString(ranNum));
		account.setDescription("Finding Nemo");
		
		account.setType(AccountType.EXPENSE);
		array.getAccount().add(account);
		return array;
	}
	
	// BANK TRANSACTION
	public static ArrayOfBankTransaction loadBankTransaction() throws IOException {
		ArrayOfBankTransaction array = new ArrayOfBankTransaction();
		BankTransaction bt = new BankTransaction();
		bt.setDate(loadDate());;
		bt.setStatus(BankTransactionStatus.AUTHORISED);;
		bt.setType(BankTransactionType.SPEND);
		bt.setUrl("http://mycompany.org/transactions/230498239");
		bt.setLineItems(loadLineItem());
		bt.setSubTotal(new BigDecimal(20.00));
		bt.setTotalTax(new BigDecimal(0));
		bt.setTotal(new BigDecimal("20.00"));
		bt.setCurrencyRate(new BigDecimal(1.000000));
		bt.setContact(loadContact().getContact().get(0));
		bt.setLineAmountTypes(LineAmountType.INCLUSIVE);
		
		Account acct = loadBankAccount();
		bt.setBankAccount(acct);
		array.getBankTransaction().add(bt);
		return array;
	}
	
	// BANK TRANSFER
	public static ArrayOfBankTransfer loadBankTransfer() throws IOException {
		ArrayOfBankTransfer array = new ArrayOfBankTransfer();
		BankTransfer bt = new BankTransfer();
		
		List<Account> accountWhere = client.getAccounts(null,"Type==\"BANK\"",null);
		
		if(accountWhere.size() > 2) {
			BankAccount fromAccount = new BankAccount();
			fromAccount.setAccountID(accountWhere.get(0).getAccountID());
	
			BankAccount toAccount = new BankAccount();
			toAccount.setAccountID(accountWhere.get(1).getAccountID());
			
			bt.setAmount(new BigDecimal(20.00));
			bt.setDate(loadDate());
			bt.setFromBankAccount(fromAccount);
			bt.setToBankAccount(toAccount);
			bt.setCurrencyRate(new BigDecimal(1.000000));
			
			array.getBankTransfer().add(bt);
		}
		return array;
	}
	
	// CONTACT
	public static ArrayOfContact loadContact() {
		ArrayOfContact array = new ArrayOfContact();
		Contact contact = new Contact();
		contact.setName("Barney Rubble-" + loadRandomNum());
		contact.setEmailAddress("barney@bedrock.com");
		contact.setFirstName("B");
		contact.setLastName("Rubble");
		
		ArrayOfAddress arrayOfAddress = new ArrayOfAddress();
		Address address = new Address();
		address.setAddressLine1("176 Shuey Dr");
		address.setCity("Moraga");
		address.setPostalCode("94556");
		arrayOfAddress.getAddress().add(address);
		
		contact.setAddresses(arrayOfAddress);
		
		ArrayOfPhone arrayOfPhone = new ArrayOfPhone();
		Phone phone = new Phone();
		phone.setPhoneAreaCode("925");
		phone.setPhoneNumber("3770907");
		arrayOfPhone.getPhone().add(phone);
		contact.setPhones(arrayOfPhone);

		array.getContact().add(contact);
		return array;
	}
	
	// CONTACT GROUP
	public static ArrayOfContactGroup loadContactGroup() {
		ArrayOfContactGroup array = new ArrayOfContactGroup();
	
		ContactGroup cg = new ContactGroup();
		cg.setName("Fancy Cats-" + loadRandomNum());
		cg.setContacts(loadContact());
		array.getContactGroup().add(cg);
		return array;
	}
	
	// CREDIT NOTE
	public static ArrayOfCreditNote loadCreditNote() throws IOException {
		ArrayOfCreditNote array = new ArrayOfCreditNote();
	
		CreditNote cn = new CreditNote();
		cn.setType(CreditNoteType.ACCPAYCREDIT);
		cn.setDate(loadDate());
		cn.setLineAmountTypes(LineAmountType.INCLUSIVE);
		cn.setLineItems(loadLineItem());
		cn.setContact(loadSingleContact());
		array.getCreditNote().add(cn);
		return array;
	}
	
	// EMPLOYEE
	public static ArrayOfEmployee loadEmployee() {
		ArrayOfEmployee array = new ArrayOfEmployee();
		
		Hyperlink link = new Hyperlink();
		link.setUrl("http://www.sfmoma.org");
		link.setDescription("SF Mueseum of Modern Art");
		
		Employee e = new Employee();
		e.setFirstName("Jackson");
		e.setLastName("Pollock-" + loadRandomNum());
		e.setExternalLink(link);
		array.getEmployee().add(e);
		return array;
	}
	
	// EXPENSE CLAIM
	public static ArrayOfExpenseClaim loadExpenseClaim() throws IOException {
		ArrayOfExpenseClaim array = new ArrayOfExpenseClaim();
		
		ExpenseClaim e = new ExpenseClaim();
		e.setAmountDue(new BigDecimal(10.00));
		
		List<User> UserWhere = client.getUsers(null,"IsSubscriber==true",null);
		if (UserWhere.size() == 0) {
			UserWhere = client.getUsers();
		}
		e.setUser(UserWhere.get(0));
		
		List<Receipt> newReceipt = client.createReceipts(SampleData.loadReceipt().getReceipt());
		ArrayOfReceipt arrayReceipts = new ArrayOfReceipt();
		arrayReceipts.getReceipt().add(newReceipt.get(0));
		e.setReceipts(arrayReceipts);
		
		array.getExpenseClaim().add(e);
		return array;
	}
	
	// INVOICE
	public static ArrayOfInvoice loadInvoice() throws IOException {
		ArrayOfInvoice array = new ArrayOfInvoice();
		
		Invoice inv = new Invoice();
		inv.setContact(loadSingleContact());
		inv.setCurrencyCode(CurrencyCode.USD);
		inv.setLineItems(loadLineItem());
		inv.setDate(loadDate());
		inv.setDueDate(loadDate());
		inv.setInvoiceNumber( Integer.toString(loadRandomNum()) );
		inv.setType(InvoiceType.ACCREC);
		inv.setStatus(InvoiceStatus.DRAFT);
		array.getInvoice().add(inv);
		return array;
	}
	
	// BAD INVOICE
	public static ArrayOfInvoice loadBadInvoice() throws IOException {
		ArrayOfInvoice array = new ArrayOfInvoice();
		
		Invoice inv = new Invoice();
		inv.setContact(loadSingleContact());
		inv.setCurrencyCode(CurrencyCode.AUD);
		inv.setLineItems(loadLineItem());
		inv.setDate(loadDate());
		inv.setDueDate(loadDate());
		inv.setInvoiceNumber( Integer.toString(loadRandomNum()) );
		inv.setType(InvoiceType.ACCREC);
		inv.setStatus(InvoiceStatus.DRAFT);
		array.getInvoice().add(inv);
		return array;
	}
	
	// BAD INVOICE 2
	public static ArrayOfInvoice loadBadInvoice2() throws IOException {
		ArrayOfInvoice array = new ArrayOfInvoice();
		
		Invoice inv = new Invoice();
		inv.setContact(loadSingleContact());
		inv.setCurrencyCode(CurrencyCode.NZD);
		inv.setLineItems(loadLineItem());
		inv.setDate(loadDate());
		inv.setDueDate(loadDate());
		inv.setInvoiceNumber( Integer.toString(loadRandomNum()) );
		inv.setType(InvoiceType.ACCREC);
		inv.setStatus(InvoiceStatus.DRAFT);
		array.getInvoice().add(inv);
		return array;
	}
	
	// ITEMS
	public static ArrayOfItem loadItem() {
		ArrayOfItem array = new ArrayOfItem();
		String rand = Integer.toString(loadRandomNum());
		Item item = new Item();
		item.setCode( rand);
		item.setDescription("ACME hammer-" + rand);
		item.setName("ACME hammer-" + rand );
		item.setIsSold(true);
	
		array.getItem().add(item);
		return array;
	}
	
	// LINKED TRANSACTIONS
	public static ArrayOfLinkedTransaction loadLinkedTransaction() throws IOException {
		ArrayOfLinkedTransaction array = new ArrayOfLinkedTransaction();
		
		Invoice newBill = loadNewBill();
		@SuppressWarnings("unused")
		Invoice newSalesInvoice = loadNewSalesInvoice();
		LinkedTransaction lt = new LinkedTransaction();
		lt.setContactID(loadSingleContact().getContactID());
		lt.setSourceTransactionID(newBill.getInvoiceID());
		lt.setSourceLineItemID(newBill.getLineItems().getLineItem().get(0).getLineItemID());
		//lt.setTargetTransactionID(newSalesInvoice.getInvoiceID());
		//lt.setTargetLineItemID(newSalesInvoice.getLineItems().getLineItem().get(0).getLineItemID());
			
		array.getLinkedTransaction().add(lt);
		return array;
	}
	
	// MANUAL JOURNAL
	public static ArrayOfManualJournal loadManualJournal() throws IOException {
		ArrayOfManualJournal array = new ArrayOfManualJournal();
		ArrayOfManualJournalLine arrayOfMJLine = new ArrayOfManualJournalLine();
		
		List<Account> accountSales = client.getAccounts(null,"Type==\"REVENUE\"",null);
		List<Account> accountDirectCosts = client.getAccounts(null,"Type==\"DIRECTCOSTS\"",null);
		
		if (accountSales.size() == 0 ){
			System.out.println("no revenue accounts");
		}
		
		if (accountDirectCosts.size() == 0 ){
			System.out.println("no direct cost accounts");
		}

		
		ManualJournalLine debit = new ManualJournalLine();
		debit.setDescription("My MJ Debit");
		debit.setAccountCode("400");
		debit.setLineAmount(new BigDecimal(10.00));
		arrayOfMJLine.getJournalLine().add(debit);
		
		ManualJournalLine credit = new ManualJournalLine();
		credit.setDescription("My MJ Credit");
		credit.setAccountCode("500");
		credit.setLineAmount(new BigDecimal(-10.00));
		arrayOfMJLine.getJournalLine().add(credit);
	
		ManualJournal mj = new ManualJournal();
		mj.setJournalLines(arrayOfMJLine);
		mj.setDate(loadDate());
		mj.setNarration("My Fake Journal");
		mj.setStatus(ManualJournalStatus.DRAFT);
		array.getManualJournal().add(mj);
		return array;
	}
	
	// NEW ALLOCATION
	public static ArrayOfAllocation loadAllocation() throws IOException {
		ArrayOfAllocation array = new ArrayOfAllocation();
		Allocation allocation = new Allocation();
		allocation.setDate(loadDate());
		allocation.setAppliedAmount(new BigDecimal("1.00"));
		
		Invoice inv = loadNewSalesInvoice();
		allocation.setInvoice(inv);
		
		array.getAllocation().add(allocation);
		return array;
	}
	
	// OVERPAYMENT
	public static BankTransaction loadNewlyCreatedOverpayment() throws IOException {
		ArrayOfBankTransaction array = new ArrayOfBankTransaction();
		BankTransaction bt = new BankTransaction();
		bt.setDate(loadDate());;
		bt.setStatus(BankTransactionStatus.AUTHORISED);;
		bt.setType(BankTransactionType.RECEIVE_OVERPAYMENT);
		bt.setUrl("http://mycompany.org/transactions/230498239");
		bt.setLineItems(loadLineItemForOverpayment());
		
		bt.setSubTotal(new BigDecimal(20.00));
		bt.setTotalTax(new BigDecimal(0));
		bt.setTotal(new BigDecimal("20.00"));
		bt.setCurrencyRate(new BigDecimal(1.000000));
		bt.setContact(loadContact().getContact().get(0));
		bt.setLineAmountTypes(LineAmountType.NO_TAX);
		
		Account acct = loadBankAccount();
		bt.setBankAccount(acct);
		array.getBankTransaction().add(bt);
		
		List<BankTransaction> newBankTransaction = client.createBankTransactions(array.getBankTransaction());
		return newBankTransaction.get(0);
	}
	
	// PAYMENTS
	public static ArrayOfPayment loadPayment() throws IOException {
		ArrayOfPayment array = new ArrayOfPayment();
		Payment pay = new Payment();
		pay.setDate(loadDate());
		
		Account acct = loadBankAccount();
		pay.setAccount(acct);
		pay.setAmount(new BigDecimal(".99"));

		Invoice inv = loadNewSalesInvoice();
		pay.setInvoice(inv);
		
		array.getPayment().add(pay);
		return array;
	}
	
	
	// PREPAYMENT
	public static BankTransaction loadNewlyCreatedPrepayment() throws IOException {
		ArrayOfBankTransaction array = new ArrayOfBankTransaction();
		BankTransaction bt = new BankTransaction();
		bt.setDate(loadDate());;
		bt.setStatus(BankTransactionStatus.AUTHORISED);;
		bt.setType(BankTransactionType.RECEIVE_PREPAYMENT);
		bt.setUrl("http://mycompany.org/transactions/230498239");
		bt.setLineItems(loadLineItemForPrepayment());
		
		bt.setSubTotal(new BigDecimal(20.00));
		bt.setTotalTax(new BigDecimal(0));
		bt.setTotal(new BigDecimal("20.00"));
		bt.setCurrencyRate(new BigDecimal(1.000000));
		bt.setContact(loadContact().getContact().get(0));
		bt.setLineAmountTypes(LineAmountType.NO_TAX);
		Account acct = loadBankAccount();
		bt.setBankAccount(acct);
		array.getBankTransaction().add(bt);
		
		List<BankTransaction> newBankTransaction = client.createBankTransactions(array.getBankTransaction());
		return newBankTransaction.get(0);
	}
	
	
	
	// PURCHASEORDER
	public static ArrayOfPurchaseOrder loadPurchaseOrder() throws IOException {
		ArrayOfPurchaseOrder array = new ArrayOfPurchaseOrder();
		PurchaseOrder po = new PurchaseOrder();
		po.setDate(loadDate());
		po.setAttentionTo("Sid Maestre");
		po.setContact(loadSingleContact());
		po.setDate(loadDate());
		po.setStatus(PurchaseOrderStatus.DRAFT);
		po.setReference("What is the Matrix?");
		po.setLineItems(loadLineItem());
		po.setPurchaseOrderNumber("XRO-" + loadRandomNum());
		
		array.getPurchaseOrder().add(po);
		return array;
	}
		
	// RECEIPT
	public static ArrayOfReceipt loadReceipt() throws IOException {
		ArrayOfReceipt array = new ArrayOfReceipt();
		
		Receipt r = new Receipt();
		r.setContact(loadSingleContact());
		List<User> UserWhere = client.getUsers(null,"IsSubscriber==true",null);
		if (UserWhere.size() == 0) {
			UserWhere = client.getUsers();
		}
		r.setUser(UserWhere.get(0));
		r.setLineItems(loadExpenseLineItem());
		r.setDate(loadDate());
		r.setReference("Does Barry Manilow know you raid his wardrobe?");
		r.setStatus(ReceiptStatus.DRAFT);
		r.setLineAmountTypes(LineAmountType.INCLUSIVE);
		
		array.getReceipt().add(r);
		return array;
	}
	
	// TAXRATES
	public static ArrayOfTaxRate loadTaxRate() {
		ArrayOfTaxRate array = new ArrayOfTaxRate();
		ArrayOfTaxComponent arrayOfTaxComponent = new ArrayOfTaxComponent();
		TaxComponent taxComponent = new TaxComponent();
		taxComponent.setName("Little One-" + loadRandomNum());
		taxComponent.setRate("2");
		taxComponent.setIsCompound(false);
		arrayOfTaxComponent.getTaxComponent().add(taxComponent);	
		
		TaxComponent taxComponent2 = new TaxComponent();
		taxComponent2.setName("Big One-" + loadRandomNum());
		taxComponent2.setRate("5");
		taxComponent2.setIsCompound(true);
		arrayOfTaxComponent.getTaxComponent().add(taxComponent2);
	
		TaxRate taxrate = new TaxRate();
		taxrate.setTaxComponents(arrayOfTaxComponent);
		taxrate.setName("CA Big fat tax-"+ loadRandomNum());
		array.getTaxRate().add(taxrate);
		return array;
	}
	
	// TRACKING CATEGORY
	public static ArrayOfTrackingCategory loadTrackingCategory() {
		ArrayOfTrackingCategory array = new ArrayOfTrackingCategory();
		
		TrackingCategory tc = new TrackingCategory();
		tc.setName("Star Wars-"+ loadRandomNum());
	
		array.getTrackingCategory().add(tc);
		return array;
	}
	
	// TRACKING CATEGORY OPTION
	public static ArrayOfTrackingCategoryOption loadTrackingCategoryOption() {
		ArrayOfTrackingCategoryOption array = new ArrayOfTrackingCategoryOption();
		
		TrackingCategoryOption tc = new TrackingCategoryOption();
		tc.setName("The Empire Strikes Back-"+ loadRandomNum());
		
		array.getOption().add(tc);
		return array;
	}
	
	public static ArrayOfTrackingCategoryOption loadTrackingCategoryOptionMulti() {
		ArrayOfTrackingCategoryOption array = new ArrayOfTrackingCategoryOption();
		
		TrackingCategoryOption tc = new TrackingCategoryOption();
		tc.setName("The Empire Strikes Back-"+ loadRandomNum());
		
		TrackingCategoryOption tc2 = new TrackingCategoryOption();
		tc2.setName("Return of the Jedi");

		TrackingCategoryOption tc3 = new TrackingCategoryOption();
		tc3.setName("Return of the Jedi");

		
		array.getOption().add(tc);
		array.getOption().add(tc2);
		array.getOption().add(tc3);
		return array;
	}
	
	// GENERAL 
	
	public static int findRandomNum(int total) {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(total);
		return randomInt;
	}
	
	private static Calendar loadDate() {
		Calendar calendar = new GregorianCalendar(2016,9,19,13,10,00);
		return calendar;
	}
	
	public static Contact loadSingleContact() throws IOException {
		List<Contact> contacts = SampleData.client.getContacts();
		return contacts.get(0);
	}
	
	@SuppressWarnings("unused")
	private static Receipt loadSingleReceipt() throws IOException {
		List<Receipt> r = SampleData.client.getReceipts();
		return r.get(0);
	}
	
	private static User loadSingleUser() throws IOException {
		List<User> users = SampleData.client.getUsers();
		return users.get(0);
	}
	
	public static int loadRandomNum() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100000);
		return randomInt;
	}
	
	public static Account loadBankAccount() throws IOException {
		List<Account> accountWhere = client.getAccounts(null,"Type==\"BANK\"",null);
		
		return accountWhere.get(0);
	}
	
	private static Invoice loadNewBill() throws IOException {	
		ArrayOfInvoice array = new ArrayOfInvoice();
		
		Invoice inv = new Invoice();
		inv.setContact(loadSingleContact());
		inv.setCurrencyCode(CurrencyCode.USD);
		inv.setLineItems(loadLineItem());
		inv.setDate(loadDate());
		inv.setDueDate(loadDate());
		inv.setInvoiceNumber( Integer.toString(loadRandomNum()) );
		inv.setType(InvoiceType.ACCPAY);
		inv.setStatus(InvoiceStatus.AUTHORISED);
		array.getInvoice().add(inv);
		
		List<Invoice> newInvoice = client.createInvoices(array.getInvoice());
		
		return newInvoice.get(0);
	}
	
	private static Invoice loadNewSalesInvoice() throws IOException {	
		ArrayOfInvoice array = new ArrayOfInvoice();
		
		Invoice inv = new Invoice();
		inv.setContact(loadSingleContact());
		inv.setCurrencyCode(CurrencyCode.USD);
		inv.setLineItems(loadEmptyLineItem());
		inv.setDate(loadDate());
		inv.setDueDate(loadDate());
		inv.setType(InvoiceType.ACCREC);
		inv.setStatus(InvoiceStatus.AUTHORISED);
		array.getInvoice().add(inv);
		
		List<Invoice> newInvoice = client.createInvoices(array.getInvoice());
		
		return newInvoice.get(0);
	}
	
	private static ArrayOfLineItem loadLineItem() throws IOException{
		List<Account> accountDirectCosts = client.getAccounts(null,"Type==\"DIRECTCOSTS\"",null);
	
		ArrayOfLineItem array = new ArrayOfLineItem();
		LineItem line = new LineItem();
		line.setDescription("Yearly Bank Account Fee");
		line.setQuantity(new BigDecimal("1.00"));
		line.setUnitAmount(new BigDecimal("20.00"));
		line.setLineAmount(new BigDecimal("20.00"));
		line.setAccountCode(accountDirectCosts.get(0).getCode());
		
		
		List<TrackingCategory> TrackingCategoryList = client.getTrackingCategories();
		if (TrackingCategoryList.size() > 0) {
			int num10 = SampleData.findRandomNum(TrackingCategoryList.size());
			
			ArrayOfTrackingCategory trackingCategories = new ArrayOfTrackingCategory();
			TrackingCategory trackingCategory = new TrackingCategory();
			
			trackingCategory.setTrackingCategoryID(TrackingCategoryList.get(num10).getTrackingCategoryID());
			trackingCategory.setName(TrackingCategoryList.get(num10).getName());
			trackingCategory.setOption(TrackingCategoryList.get(num10).getOptions().getOption().get(0).getName());
			
			trackingCategories.getTrackingCategory().add(trackingCategory);
			
			line.setTracking(trackingCategories);
			
		}
		
		array.getLineItem().add(line);
		return array;	
	}
	
	private static ArrayOfLineItem loadLineItemForOverpayment() throws IOException{
		List<Account> accountDebtors = client.getAccounts(null,"SystemAccount==\"DEBTORS\"",null);
		
		ArrayOfLineItem array = new ArrayOfLineItem();
		LineItem line = new LineItem();
		line.setDescription("My Overpayment for Subscription");
		line.setQuantity(new BigDecimal("1.00"));
		line.setUnitAmount(new BigDecimal("20.00"));
		line.setLineAmount(new BigDecimal("20.00"));
		line.setAccountCode(accountDebtors.get(0).getCode());
		array.getLineItem().add(line);
		return array;	
	}
	
	private static ArrayOfLineItem loadLineItemForPrepayment(){
		ArrayOfLineItem array = new ArrayOfLineItem();
		LineItem line = new LineItem();
		line.setDescription("My Prepayment for legal services");
		line.setQuantity(new BigDecimal("1.00"));
		line.setUnitAmount(new BigDecimal("20.00"));
		line.setLineAmount(new BigDecimal("20.00"));
		line.setAccountCode("400");
		array.getLineItem().add(line);
		return array;	
	}
	
	private static ArrayOfLineItem loadEmptyLineItem() throws IOException{
		List<Account> accountSales = client.getAccounts(null,"Type==\"REVENUE\"",null);
		
		ArrayOfLineItem array = new ArrayOfLineItem();
		LineItem line = new LineItem();
		line.setDescription("Yearly Bank Account Fee");
		line.setQuantity(new BigDecimal("1.00"));
		line.setUnitAmount(new BigDecimal("30.00"));
		line.setLineAmount(new BigDecimal("30.00"));
		line.setAccountCode(accountSales.get(0).getCode());
		array.getLineItem().add(line);
		return array;	
	}
	
	private static ArrayOfLineItem loadExpenseLineItem() throws IOException{
		List<Account> accountExpense = client.getAccounts(null,"Type==\"EXPENSE\"",null);
		
		ArrayOfLineItem array = new ArrayOfLineItem();
		LineItem line = new LineItem();
		line.setDescription("Coffee and Bagel");
		line.setQuantity(new BigDecimal("1.00"));
		line.setUnitAmount(new BigDecimal("7.50"));
		line.setLineAmount(new BigDecimal("7.50"));
		line.setAccountCode(accountExpense.get(0).getCode());
		array.getLineItem().add(line);
		return array;	
	}
}
