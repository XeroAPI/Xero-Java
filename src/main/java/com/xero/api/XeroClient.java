package com.xero.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.google.api.client.http.HttpResponse;
import com.xero.api.TokenStorage;
import com.xero.model.*;

public class XeroClient {
	
	private static Config c = Config.getInstance();  
	static String data = null;
	protected String token = null;
	protected String tokenSecret = null;
	protected String tokenTimestamp = null;
	protected static final DateFormat utcFormatter;
	static 
	{
		utcFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
	}
	
	protected static final Pattern MESSAGE_PATTERN = Pattern.compile("<Message>(.*)</Message>");
	protected final ObjectFactory objFactory = new ObjectFactory();
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected TokenStorage storage;
	
	public XeroClient(HttpServletRequest request, HttpServletResponse response, TokenStorage storage) {
		this.request = request;
		this.response = response;
		this.storage = storage;
		
		OAuthAccessToken refreshToken = new OAuthAccessToken();
		tokenTimestamp = storage.get(request, "tokenTimestamp");
		
		if(c.getAppType().equals("PARTNER") && refreshToken.isStale(tokenTimestamp))
		{
			System.out.println("Time for a refresh");

			refreshToken.setToken(storage.get(request, "token"));
			refreshToken.setTokenSecret(storage.get(request, "tokenSecret"));
			refreshToken.setSessionHandle(storage.get(request, "sessionHandle"));
			try {
				boolean success = refreshToken.build().execute();
				if (!success) {
					try {
						this.request.getRequestDispatcher("index.jsp").forward(this.request, this.response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				System.out.println("Problem refreshing token" + e.toString());
			}

			// DEMONSTRATION ONLY - Store in Cookie - you can extend TokenStorage
			// and implement the save() method for your database
			storage.save(response,refreshToken.getAll());
			token =  refreshToken.getToken();
			tokenSecret = refreshToken.getTokenSecret();
		} 
		else 
		{
			System.out.println("Using the existing token");
			token = storage.get(request, "token");
			tokenSecret = storage.get(request, "tokenSecret");
			System.out.println("Token: " + token);
			System.out.println("Token Secret: " + tokenSecret);
		}	
	}
	
	public boolean tokenIsNull() {
		if (token != null && !token.isEmpty()) { 
			return false;
		} else {
			return true;
		}
	}
	
	public void excecute(String resource, String method,String xml) {
		HttpResponse resp = null;
		OAuthRequestResource req = new OAuthRequestResource(resource,method,xml,null);
		req.setToken(token);
		req.setTokenSecret(tokenSecret);
		try {
			resp = req.execute();
			data = resp.parseAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected XeroApiException newApiException(HttpResponse response) throws IOException {
	    ApiException exception = null;
	    try {
	      exception = unmarshallResponse(response.parseAsString(), ApiException.class);
	    } catch (Exception e) {
	    }
	    
	    // Jibx doesn't support xsi:type, so we pull out errors this somewhat-hacky way 
	    Matcher matcher = MESSAGE_PATTERN.matcher(response.parseAsString());
	    StringBuilder messages = new StringBuilder();
	    while (matcher.find()) {
	      if (messages.length() > 0) {
	        messages.append(", ");
	      }
	      messages.append(matcher.group(1));
	    }
	    
	    if (exception == null) {
	      if (messages.length() > 0) {
	        return new XeroApiException(response.getStatusCode(), messages.toString());
	      }
	      return new XeroApiException(response.getStatusCode());
	    }
	    return new XeroApiException(response.getStatusCode(), "Error number " + exception.getErrorNumber() + ". " + messages);  
	}
		
	protected Response get(String endPoint) {
		return get(endPoint, null, null);
	}

	protected Response get(String endPoint, Date modifiedAfter, Map<String,String> params) {
		
		HttpResponse resp = null;
		
		OAuthRequestResource req = new OAuthRequestResource(endPoint,"GET",null,params);
		req.setToken(token);
		req.setTokenSecret(tokenSecret);
		if (modifiedAfter != null) {
			req.setIfModifiedSince(modifiedAfter);
		}
		try {
			resp = req.execute();
			if (resp.getStatusCode() != 200) {
				  throw newApiException(resp);
			}
		    return unmarshallResponse(resp.parseAsString(), Response.class);
		} catch (IOException e) {
			String error401 = "401 Unauthorized";
			if(e.getMessage().toLowerCase().contains(error401.toLowerCase())) {
				try {
					this.request.getRequestDispatcher("index.jsp").forward(this.request, this.response);
				} catch (ServletException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return null;
	}
	
	protected Response put(String endPoint, JAXBElement<?> object) {
		String contents = marshallRequest(object);		

		HttpResponse resp = null;
		OAuthRequestResource req = new OAuthRequestResource(endPoint,"PUT",contents,null);
		req.setToken(token);
		req.setTokenSecret(tokenSecret);
		try {
			resp = req.execute();
				
			if (resp.getStatusCode() != 200) {
			      throw newApiException(resp);
			}
			
		    return unmarshallResponse(resp.parseAsString(), Response.class);
		} catch (IOException e) {
			String error401 = "401 Unauthorized";
			if(e.getMessage().toLowerCase().contains(error401.toLowerCase())) {
				try {
					this.request.getRequestDispatcher("index.jsp").forward(this.request, this.response);
				} catch (ServletException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
			e.printStackTrace();
		}
		return null;
	}

	protected Response post(String endPoint, JAXBElement<?> object) {
	    String contents = marshallRequest(object);
	    HttpResponse resp = null;
		OAuthRequestResource req = new OAuthRequestResource(endPoint,"POST",contents,null);
		req.setToken(token);
		req.setTokenSecret(tokenSecret);
		try {
			resp = req.execute();
			if (resp.getStatusCode() == 401) {
				System.out.println("Token Problem: " + resp.parseAsString());
			} else if (resp.getStatusCode() != 200) {
			      throw newApiException(resp);
			}
		    return unmarshallResponse(resp.parseAsString(), Response.class);
		} catch (IOException e) {
			String error401 = "401 Unauthorized";
			if(e.getMessage().toLowerCase().contains(error401.toLowerCase())) {
				try {
					this.request.getRequestDispatcher("index.jsp").forward(this.request, this.response);
				} catch (ServletException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return null;
	}
	
	protected Response delete(String endPoint) {
		HttpResponse resp = null;
		OAuthRequestResource req = new OAuthRequestResource(endPoint,"DELETE",null,null);
		req.setToken(token);
		req.setTokenSecret(tokenSecret);
		try {
			resp = req.execute();
			// No-Content Returned from Xero API
			if (resp.getStatusCode() == 204) {
				//System.out.println("No Content Returned");
				return unmarshallResponse("<Response><Status>DELETED</Status></Response>", Response.class);
			} else if (resp.getStatusCode() == 401) {
				System.out.println("Token Problem: " + resp.parseAsString());
			} else if (resp.getStatusCode() != 200) {
			      throw newApiException(resp);
			}
				
			return unmarshallResponse(resp.parseAsString(), Response.class);
			
		} catch (IOException e) {
			String error401 = "401 Unauthorized";
			if(e.getMessage().toLowerCase().contains(error401.toLowerCase())) {
				try {
					this.request.getRequestDispatcher("index.jsp").forward(this.request, this.response);
				} catch (ServletException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return null;
	}
	
	protected <T> String marshallRequest(JAXBElement<?> object) {
		try {
			JAXBContext context = JAXBContext.newInstance(object.getValue().getClass());
			Marshaller marshaller = context.createMarshaller();
			StringWriter writer = new StringWriter();
			marshaller.marshal(object, writer);
			return writer.toString();
		} catch (JAXBException e) {
		      throw new IllegalStateException("Error marshalling request object " + object.getClass(), e);
		}
	}

	protected static <T> T unmarshallResponse(String responseBody, Class<T> clazz) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
		    Unmarshaller u = context.createUnmarshaller();
		    /*u.setEventHandler(new ValidationEventHandler() 
		    {
		    	@Override
		    	public boolean handleEvent(ValidationEvent event) 
		    	{
		    		throw new RuntimeException(event.getMessage(),event.getLinkedException());
		    	}
		    });*/
		    Source source = new StreamSource(new ByteArrayInputStream(responseBody.getBytes()));
		    return u.unmarshal(source, clazz).getValue();
		} catch (JAXBException e) {
			throw new IllegalStateException("Error unmarshalling response: " + responseBody, e);
		}
	}
		  
	protected void addToMapIfNotNull(Map<String,String> map, String key, Object value) {
		if (value != null) {
			map.put(key, value.toString());
		}
	}
	
	protected <T> T singleResult(List<T> list) {
	    if (list.isEmpty()) {
	      return null;
	    }
	    if (list.size() > 1) {
	      throw new IllegalStateException("Got multiple results for query");
	    }
	    return list.get(0);
	}
	
	//ACCOUNTS 
	public List<Account> getAccounts() {
		Response responseObj = get("Accounts");
		if (responseObj.getAccounts() == null) {
			ArrayOfAccount array = new ArrayOfAccount();
			return array.getAccount();
		} else {
			return responseObj.getAccounts().getAccount();
		}
	}
	
	public List<Account> getAccounts(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
		
	    Response responseObj = get("Accounts", modifiedAfter, params);
		if (responseObj.getAccounts() == null) {
			ArrayOfAccount array = new ArrayOfAccount();
			return array.getAccount();
		} else {
			return responseObj.getAccounts().getAccount();
		}
	}
	
	public List<Account> createAccounts(List<Account> objects) {
		ArrayOfAccount array = new ArrayOfAccount();
		array.getAccount().addAll(objects);
		return put("Accounts", objFactory.createAccounts(array)).getAccounts().getAccount();
	}
	
	public List<Account> updateAccount(List<Account> objects) {
		ArrayOfAccount array = new ArrayOfAccount();
		array.getAccount().addAll(objects);
		return post("Accounts", objFactory.createAccounts(array)).getAccounts().getAccount();
	}
		
	public Account getAccount(String id) {
	    return singleResult(get("Accounts/" + id).getAccounts().getAccount());
	}
	
	public String deleteAccount(String id) {
	    return delete("Accounts/" + id).getStatus();
	}
	
	//BANK TRANSACTION 
	public List<BankTransaction> getBankTransactions() {
	    Response responseObj = get("BankTransactions");
		if (responseObj.getBankTransactions() == null) {
			ArrayOfBankTransaction array = new ArrayOfBankTransaction();
			return array.getBankTransaction();
		} else {
			return responseObj.getBankTransactions().getBankTransaction();
		}
	}
	
	public List<BankTransaction> getBankTransactions(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    
	    Response responseObj = get("BankTransactions", modifiedAfter, params);
		if (responseObj.getBankTransactions() == null) {
			ArrayOfBankTransaction array = new ArrayOfBankTransaction();
			return array.getBankTransaction();
		} else {
			return responseObj.getBankTransactions().getBankTransaction();
		}	    
	}
	
	public List<BankTransaction> createBankTransactions(List<BankTransaction> bankTransactions) {
		ArrayOfBankTransaction array = new ArrayOfBankTransaction();
		array.getBankTransaction().addAll(bankTransactions);
		return put("BankTransactions", objFactory.createBankTransactions(array)).getBankTransactions().getBankTransaction();
	}
		
	public BankTransaction getBankTransaction(String id) {
	    return singleResult(get("BankTransactions/" + id).getBankTransactions().getBankTransaction());
	}
	
	//BANK TRANSFERS 
	public List<BankTransfer> getBankTransfers() {
	    Response responseObj = get("BankTransfers");
		if (responseObj.getBankTransfers() == null) {
			ArrayOfBankTransfer array = new ArrayOfBankTransfer();
			return array.getBankTransfer();
		} else {
			return responseObj.getBankTransfers().getBankTransfer();
		}
	}
	
	public List<BankTransfer> getBankTransfers(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    Response responseObj = get("BankTransfers", modifiedAfter, params);
		if (responseObj.getBankTransfers() == null) {
			ArrayOfBankTransfer array = new ArrayOfBankTransfer();
			return array.getBankTransfer();
		} else {
			return responseObj.getBankTransfers().getBankTransfer();
		}
	}
	
	public List<BankTransfer> createBankTransfers(List<BankTransfer> bankTransfers) {
		ArrayOfBankTransfer array = new ArrayOfBankTransfer();
		array.getBankTransfer().addAll(bankTransfers);
		return put("BankTransfers", objFactory.createBankTransfers(array)).getBankTransfers().getBankTransfer();
	}
		
	public BankTransfer getBankTransfer(String id) {
	    return singleResult(get("BankTransfers/" + id).getBankTransfers().getBankTransfer());
	}
	
	//BRANDING THEMES 
	public List<BrandingTheme> getBrandingThemes() {
	    Response responseObj = get("BrandingThemes");
		if (responseObj.getBrandingThemes() == null) {
			ArrayOfBrandingTheme array = new ArrayOfBrandingTheme();
			return array.getBrandingTheme();
		} else {
			return responseObj.getBrandingThemes().getBrandingTheme();
		}
	}
	
	public List<BrandingTheme> getBrandingThemes(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    Response responseObj = get("BrandingThemes", modifiedAfter, params);
		if (responseObj.getBrandingThemes() == null) {
			ArrayOfBrandingTheme array = new ArrayOfBrandingTheme();
			return array.getBrandingTheme();
		} else {
			return responseObj.getBrandingThemes().getBrandingTheme();
		}
	}
		
	public BrandingTheme getBrandingTheme(String id) {
	    return singleResult(get("BrandingThemes/" + id).getBrandingThemes().getBrandingTheme());
	}
	
	//CONTACT 
	public List<Contact> getContacts() {
		Response responseObj = get("Contacts");
		if (responseObj.getContacts() == null) {
			ArrayOfContact array = new ArrayOfContact();
			return array.getContact();
		} else {
			return responseObj.getContacts().getContact();
		}
	}
	
	public List<Contact> getContacts(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    Response responseObj = get("Contacts", modifiedAfter, params);
		if (responseObj.getContacts() == null) {
			ArrayOfContact array = new ArrayOfContact();
			return array.getContact();
		} else {
			return responseObj.getContacts().getContact();
		}
	}
	
	public List<Contact> createContact(List<Contact> contacts) {
		ArrayOfContact array = new ArrayOfContact();
		array.getContact().addAll(contacts);
		return put("Contacts", objFactory.createContacts(array)).getContacts().getContact();
	}
	
	public List<Contact> updateContact(List<Contact> contacts) {
		ArrayOfContact array = new ArrayOfContact();
		array.getContact().addAll(contacts);
		return post("Contacts", objFactory.createContacts(array)).getContacts().getContact();
	}
		
	public Contact getContact(String id) {
	    return singleResult(get("Contacts/" + id).getContacts().getContact());
	}
			
	//ContactGroups 
	public List<ContactGroup> getContactGroups() {
	    Response responseObj = get("ContactGroups");
		if (responseObj.getContactGroups() == null) {
			ArrayOfContactGroup array = new ArrayOfContactGroup();
			return array.getContactGroup();
		} else {
			return responseObj.getContactGroups().getContactGroup();
		}
	}
	
	public List<ContactGroup> getContactGroups(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    
	    Response responseObj = get("ContactGroups", modifiedAfter, params);
		if (responseObj.getContactGroups() == null) {
			ArrayOfContactGroup array = new ArrayOfContactGroup();
			return array.getContactGroup();
		} else {
			return responseObj.getContactGroups().getContactGroup();
		}
	}
	
	public List<ContactGroup> createContactGroups(List<ContactGroup> ContactGroups) {
		ArrayOfContactGroup array = new ArrayOfContactGroup();
		array.getContactGroup().addAll(ContactGroups);
		return put("ContactGroups", objFactory.createContactGroups(array)).getContactGroups().getContactGroup();
	}
	
	public List<ContactGroup> updateContactGroup(List<ContactGroup> objects) {
		ArrayOfContactGroup array = new ArrayOfContactGroup();
		array.getContactGroup().addAll(objects);
		return post("ContactGroups", objFactory.createContactGroups(array)).getContactGroups().getContactGroup();
	}
		
	public ContactGroup getContactGroup(String id) {
	    return singleResult(get("ContactGroups/" + id).getContactGroups().getContactGroup());
	}
	
	public List<ContactGroup> deleteContactGroup(ContactGroup object) {
		object.setStatus(ContactGroupStatus.DELETED);
		
		ArrayOfContactGroup array = new ArrayOfContactGroup();
		array.getContactGroup().add(object);
		return post("ContactGroups", objFactory.createContactGroups(array)).getContactGroups().getContactGroup();
	}
	
	// ContactGroup Contacts
	public List<Contact> createContactGroupContacts(List<Contact> objects,String id) {
		ArrayOfContact array = new ArrayOfContact();
		array.getContact().addAll(objects);	
		return put("ContactGroups/" + id + "/Contacts", objFactory.createContacts(array)).getContacts().getContact();	
	}
	
	public String deleteSingleContactFromContactGroup(String ContactGroupId, String ContactId) {
	    return delete("ContactGroups/" + ContactGroupId + "/Contacts/" + ContactId).getStatus();
	}
	
	public String deleteAllContactsFromContactGroup(String ContactGroupId) {
	    return delete("ContactGroups/" + ContactGroupId + "/Contacts").getStatus();
	}
	
	
	//CreditNotes
	public List<CreditNote> getCreditNotes() {
	    Response responseObj = get("CreditNotes");
		if (responseObj.getCreditNotes() == null) {
			ArrayOfCreditNote array = new ArrayOfCreditNote();
			return array.getCreditNote();
		} else {
			return responseObj.getCreditNotes().getCreditNote();
		}
	}
	
	public List<CreditNote> getCreditNotes(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    
	    Response responseObj = get("CreditNotes", modifiedAfter, params);
		if (responseObj.getCreditNotes() == null) {
			ArrayOfCreditNote array = new ArrayOfCreditNote();
			return array.getCreditNote();
		} else {
			return responseObj.getCreditNotes().getCreditNote();
		}
	}
	
	public List<CreditNote> createCreditNotes(List<CreditNote> objects) {
		ArrayOfCreditNote array = new ArrayOfCreditNote();
		array.getCreditNote().addAll(objects);
		return put("CreditNote", objFactory.createCreditNotes(array)).getCreditNotes().getCreditNote();
	}
	
	public List<CreditNote> updateCreditNote(List<CreditNote> objects) {
		ArrayOfCreditNote array = new ArrayOfCreditNote();
		array.getCreditNote().addAll(objects);
		return post("CreditNotes", objFactory.createCreditNotes(array)).getCreditNotes().getCreditNote();
	}
		
	public CreditNote getCreditNote(String id) {
	    return singleResult(get("CreditNotes/" + id).getCreditNotes().getCreditNote());
	}
	
	//CURRENCY
	public List<Currency> getCurrencies() {
	    Response responseObj = get("Currencies");
		if (responseObj.getCurrencies() == null) {
			ArrayOfCurrency array = new ArrayOfCurrency();
			return array.getCurrency();
		} else {
			return responseObj.getCurrencies().getCurrency();
		}
	}
	
	public List<Currency> getCurrencies(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    
	    Response responseObj = get("Currencies", modifiedAfter, params);
		if (responseObj.getCurrencies() == null) {
			ArrayOfCurrency array = new ArrayOfCurrency();
			return array.getCurrency();
		} else {
			return responseObj.getCurrencies().getCurrency();
		}
	}
		
	public Currency getCurrency(String id) {
	    return singleResult(get("Currencies/" + id).getCurrencies().getCurrency());
	}
	
	//EMPLOYEES
	public List<Employee> getEmployees() {
	    Response responseObj = get("Employees");
		if (responseObj.getEmployees() == null) {
			ArrayOfEmployee array = new ArrayOfEmployee();
			return array.getEmployee();
		} else {
			return responseObj.getEmployees().getEmployee();
		}
	}
	
	public List<Employee> getEmployees(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    
	    Response responseObj = get("Employees", modifiedAfter, params);
		if (responseObj.getEmployees() == null) {
			ArrayOfEmployee array = new ArrayOfEmployee();
			return array.getEmployee();
		} else {
			return responseObj.getEmployees().getEmployee();
		}
	}
	
	public List<Employee> createEmployees(List<Employee> objects) {
		ArrayOfEmployee array = new ArrayOfEmployee();
		array.getEmployee().addAll(objects);
		return put("Employee", objFactory.createEmployees(array)).getEmployees().getEmployee();
	}
	
	public List<Employee> updateEmployee(List<Employee> objects) {
		ArrayOfEmployee array = new ArrayOfEmployee();
		array.getEmployee().addAll(objects);
		return post("Employees", objFactory.createEmployees(array)).getEmployees().getEmployee();
	}
		
	public Employee getEmployee(String id) {
	    return singleResult(get("Employees/" + id).getEmployees().getEmployee());
	}
	
	//EXPENSE CLAIMS
	public List<ExpenseClaim> getExpenseClaims() {
	    Response responseObj = get("ExpenseClaims");
		if (responseObj.getExpenseClaims() == null) {
			ArrayOfExpenseClaim array = new ArrayOfExpenseClaim();
			return array.getExpenseClaim();
		} else {
			return responseObj.getExpenseClaims().getExpenseClaim();
		}
	}
	
	public List<ExpenseClaim> getExpenseClaims(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    
	    Response responseObj = get("ExpenseClaims", modifiedAfter, params);
		if (responseObj.getExpenseClaims() == null) {
			ArrayOfExpenseClaim array = new ArrayOfExpenseClaim();
			return array.getExpenseClaim();
		} else {
			return responseObj.getExpenseClaims().getExpenseClaim();
		}
	}
	
	public List<ExpenseClaim> createExpenseClaims(List<ExpenseClaim> objects) {
		ArrayOfExpenseClaim array = new ArrayOfExpenseClaim();
		array.getExpenseClaim().addAll(objects);
		return put("ExpenseClaim", objFactory.createExpenseClaims(array)).getExpenseClaims().getExpenseClaim();
	}
	
	public List<ExpenseClaim> updateExpenseClaim(List<ExpenseClaim> objects) {
		ArrayOfExpenseClaim array = new ArrayOfExpenseClaim();
		array.getExpenseClaim().addAll(objects);
		return post("ExpenseClaims", objFactory.createExpenseClaims(array)).getExpenseClaims().getExpenseClaim();
	}
		
	public ExpenseClaim getExpenseClaim(String id) {
	    return singleResult(get("ExpenseClaims/" + id).getExpenseClaims().getExpenseClaim());
	}
	
	//INVOICES
	public List<Invoice> getInvoices() {
	    Response responseObj = get("Invoices");
		if (responseObj.getInvoices() == null) {
			ArrayOfInvoice array = new ArrayOfInvoice();
			return array.getInvoice();
		} else {
			return responseObj.getInvoices().getInvoice();
		}
	}
	
	public List<Invoice> getInvoices(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    
	    Response responseObj = get("Invoices", modifiedAfter, params);
 		if (responseObj.getInvoices() == null) {
 			ArrayOfInvoice array = new ArrayOfInvoice();
 			return array.getInvoice();
 		} else {
 			return responseObj.getInvoices().getInvoice();
 		}
	}
	
	public List<Invoice> createInvoices(List<Invoice> invoices) {
		ArrayOfInvoice array = new ArrayOfInvoice();
		array.getInvoice().addAll(invoices);
		return put("Invoices", objFactory.createInvoices(array)).getInvoices().getInvoice();
	}
	
	public List<Invoice> updateInvoice(List<Invoice> objects) {
		ArrayOfInvoice array = new ArrayOfInvoice();
		array.getInvoice().addAll(objects);
		return post("Invoices", objFactory.createInvoices(array)).getInvoices().getInvoice();
	}
		
	public Invoice getInvoice(String id) {
	    return singleResult(get("Invoices/" + id).getInvoices().getInvoice());
	}
	
	// INVOICE REMINDER 
	public List<InvoiceReminder> getInvoiceReminders() {
	    Response responseObj = get("InvoiceReminders/Settings");
		if (responseObj.getInvoiceReminders() == null) {
			ArrayOfInvoiceReminder array = new ArrayOfInvoiceReminder();
			return array.getInvoiceReminder();
		} else {
			return responseObj.getInvoiceReminders().getInvoiceReminder();
		}
	}
	
	//ITEMS 
	public List<Item> getItems() {
	    Response responseObj = get("Items");
		if (responseObj.getItems() == null) {
			ArrayOfItem array = new ArrayOfItem();
			return array.getItem();
		} else {
			return responseObj.getItems().getItem();
		}
	}
	
	public List<Item> getItems(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);

	    Response responseObj = get("Items", modifiedAfter, params);
  		if (responseObj.getItems() == null) {
  			ArrayOfItem array = new ArrayOfItem();
  			return array.getItem();
  		} else {
  			return responseObj.getItems().getItem();
  		}
	}
	
	public List<Item> createItems(List<Item> objects) {
		ArrayOfItem array = new ArrayOfItem();
		array.getItem().addAll(objects);
		return put("Items", objFactory.createItems(array)).getItems().getItem();
	}
	
	public List<Item> updateItem(List<Item> objects) {
		ArrayOfItem array = new ArrayOfItem();
		array.getItem().addAll(objects);
		return post("Items", objFactory.createItems(array)).getItems().getItem();
	}
		
	public Item getItem(String id) {
	    return singleResult(get("Items/" + id).getItems().getItem());
	}
	
	public String deleteItem(String id) {
	    return delete("Items/" + id).getStatus();
	}
	
	// JOURNALS
	public List<Journal> getJournals() {
	    Response responseObj = get("Journals");
		if (responseObj.getJournals() == null) {
			ArrayOfJournal array = new ArrayOfJournal();
			return array.getJournal();
		} else {
			return responseObj.getJournals().getJournal();
		}
	}
	
	public List<Journal> getJournals(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    
	    Response responseObj = get("Journals", modifiedAfter, params);
		if (responseObj.getJournals() == null) {
			ArrayOfJournal array = new ArrayOfJournal();
			return array.getJournal();
		} else {
			return responseObj.getJournals().getJournal();
		}
	}
		
	public Journal getJournal(String id) {
	    return singleResult(get("Journals/" + id).getJournals().getJournal());
	}
	
	//LINKED TRANSACTIONS 
	public List<LinkedTransaction> getLinkedTransactions() {
	    Response responseObj = get("LinkedTransactions");
		if (responseObj.getLinkedTransactions() == null) {
			ArrayOfLinkedTransaction array = new ArrayOfLinkedTransaction();
			return array.getLinkedTransaction();
		} else {
			return responseObj.getLinkedTransactions().getLinkedTransaction();
		}
	}
	
	public List<LinkedTransaction> getLinkedTransactions(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);

	    Response responseObj = get("LinkedTransactions", modifiedAfter, params);
		if (responseObj.getLinkedTransactions() == null) {
			ArrayOfLinkedTransaction array = new ArrayOfLinkedTransaction();
			return array.getLinkedTransaction();
		} else {
			return responseObj.getLinkedTransactions().getLinkedTransaction();
		}
	}
	
	public List<LinkedTransaction> createLinkedTransactions(List<LinkedTransaction> objects) {
		ArrayOfLinkedTransaction array = new ArrayOfLinkedTransaction();
		array.getLinkedTransaction().addAll(objects);
		return put("LinkedTransactions", objFactory.createLinkedTransactions(array)).getLinkedTransactions().getLinkedTransaction();
	}
	
	public List<LinkedTransaction> updateLinkedTransaction(List<LinkedTransaction> objects) {
		ArrayOfLinkedTransaction array = new ArrayOfLinkedTransaction();
		array.getLinkedTransaction().addAll(objects);
		return post("LinkedTransactions", objFactory.createLinkedTransactions(array)).getLinkedTransactions().getLinkedTransaction();
	}
		
	public LinkedTransaction getLinkedTransaction(String id) {
	    return singleResult(get("LinkedTransactions/" + id).getLinkedTransactions().getLinkedTransaction());
	}
	
	public String deleteLinkedTransaction(String id) {
	    return delete("LinkedTransactions/" + id).getStatus();
	}
	
	//ManualJournals
	public List<ManualJournal> getManualJournals() {
		Response responseObj = get("ManualJournal");
		if (responseObj.getManualJournals() == null) {
			ArrayOfManualJournal array = new ArrayOfManualJournal();
			return array.getManualJournal();
		} else {
			return responseObj.getManualJournals().getManualJournal();
		}
	}
	
	public List<ManualJournal> getManualJournals(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    
	    Response responseObj = get("ManualJournals", modifiedAfter, params);
		if (responseObj.getManualJournals() == null) {
			ArrayOfManualJournal array = new ArrayOfManualJournal();
			return array.getManualJournal();
		} else {
			return responseObj.getManualJournals().getManualJournal();
		}
	}
	
	public List<ManualJournal> createManualJournals(List<ManualJournal> objects) {
		ArrayOfManualJournal array = new ArrayOfManualJournal();
		array.getManualJournal().addAll(objects);
		return put("ManualJournal", objFactory.createManualjournals(array)).getManualJournals().getManualJournal();
	}
	
	public List<ManualJournal> updateManualJournal(List<ManualJournal> objects) {
		ArrayOfManualJournal array = new ArrayOfManualJournal();
		array.getManualJournal().addAll(objects);
		return post("ManualJournals", objFactory.createManualjournals(array)).getManualJournals().getManualJournal();
	}
		
	public ManualJournal getManualJournal(String id) {
	    return singleResult(get("ManualJournals/" + id).getManualJournals().getManualJournal());
	}
	
	//ORGANIZATION
	public List<Organisation> getOrganisations() {
	    return get("Organisations").getOrganisations().getOrganisation();
	}
	
	//OVERPAYMENTS
	public List<Overpayment> getOverpayments() {
	    Response responseObj = get("Overpayments");
		if (responseObj.getOverpayments() == null) {
			ArrayOfOverpayment array = new ArrayOfOverpayment();
			return array.getOverpayment();
		} else {
			return responseObj.getOverpayments().getOverpayment();
		}
	}
	
	public List<Overpayment> getOverpayments(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);

	    Response responseObj = get("Overpayments", modifiedAfter, params);
  		if (responseObj.getOverpayments() == null) {
  			ArrayOfOverpayment array = new ArrayOfOverpayment();
  			return array.getOverpayment();
  		} else {
  			return responseObj.getOverpayments().getOverpayment();
  		}
	}
	
	public List<Allocation> createOverpaymentAllocations(List<Allocation> objects,String id) {
		ArrayOfAllocation array = new ArrayOfAllocation();
		array.getAllocation().addAll(objects);
		return put("Overpayments/" + id + "/Allocations", objFactory.createAllocations(array)).getAllocations().getAllocation();	
	}

	public Overpayment getOverpayment(String id) {
	    return singleResult(get("Overpayments/" + id).getOverpayments().getOverpayment());
	}
	
	//PAYMENTS 
	public List<Payment> getPayments() {
	    Response responseObj = get("Payments");
		if (responseObj.getPayments() == null) {
			ArrayOfPayment array = new ArrayOfPayment();
			return array.getPayment();
		} else {
			return responseObj.getPayments().getPayment();
		}
	}
	
	public List<Payment> getPayments(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);

	    Response responseObj = get("Payments", modifiedAfter, params);
		if (responseObj.getPayments() == null) {
			ArrayOfPayment array = new ArrayOfPayment();
			return array.getPayment();
		} else {
			return responseObj.getPayments().getPayment();
		}
	}
	
	public List<Payment> createPayments(List<Payment> objects) {
		ArrayOfPayment array = new ArrayOfPayment();
		array.getPayment().addAll(objects);
		return put("Payments", objFactory.createPayments(array)).getPayments().getPayment();
	}
	
	public List<Payment> deletePayment(List<Payment> objects) {
		ArrayOfPayment array = new ArrayOfPayment();
		array.getPayment().addAll(objects);
		return post("Payments", objFactory.createPayments(array)).getPayments().getPayment();
	}
		
	public Payment getPayment(String id) {
	    return singleResult(get("Payments/" + id).getPayments().getPayment());
	}
	
	//PREPAYMENTS
	public List<Prepayment> getPrepayments() {
	    Response responseObj = get("Prepayments");
		if (responseObj.getPrepayments() == null) {
			ArrayOfPrepayment array = new ArrayOfPrepayment();
			return array.getPrepayment();
		} else {
			return responseObj.getPrepayments().getPrepayment();
		}
	}
	
	public List<Prepayment> getPrepayments(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    
	    Response responseObj = get("Prepayments", modifiedAfter, params);
 		if (responseObj.getPrepayments() == null) {
 			ArrayOfPrepayment array = new ArrayOfPrepayment();
 			return array.getPrepayment();
 		} else {
 			return responseObj.getPrepayments().getPrepayment();
 		}
	}
	
	public List<Allocation> createPrepaymentAllocations(List<Allocation> objects,String id) {
		ArrayOfAllocation array = new ArrayOfAllocation();
		array.getAllocation().addAll(objects);
		return put("Prepayments/" + id + "/Allocations", objFactory.createAllocations(array)).getAllocations().getAllocation();	
	}

	public Prepayment getPrepayment(String id) {
	    return singleResult(get("Prepayments/" + id).getPrepayments().getPrepayment());
	}
	
	//PURCHASE ORDERS 
	public List<PurchaseOrder> getPurchaseOrders() {
	    Response responseObj = get("PurchaseOrders");
		if (responseObj.getPurchaseOrders() == null) {
			ArrayOfPurchaseOrder array = new ArrayOfPurchaseOrder();
			return array.getPurchaseOrder();
		} else {
			return responseObj.getPurchaseOrders().getPurchaseOrder();
		}
	}
	
	public List<PurchaseOrder> getPurchaseOrders(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);

	    Response responseObj = get("PurchaseOrders", modifiedAfter, params);
 		if (responseObj.getPurchaseOrders() == null) {
 			ArrayOfPurchaseOrder array = new ArrayOfPurchaseOrder();
 			return array.getPurchaseOrder();
 		} else {
 			return responseObj.getPurchaseOrders().getPurchaseOrder();
 		}
	}
	
	public List<PurchaseOrder> createPurchaseOrders(List<PurchaseOrder> objects) {
		ArrayOfPurchaseOrder array = new ArrayOfPurchaseOrder();
		array.getPurchaseOrder().addAll(objects);
		return put("PurchaseOrders", objFactory.createPurchaseOrders(array)).getPurchaseOrders().getPurchaseOrder();
	}
	
	public List<PurchaseOrder> updatePurchaseOrder(List<PurchaseOrder> objects) {
		ArrayOfPurchaseOrder array = new ArrayOfPurchaseOrder();
		array.getPurchaseOrder().addAll(objects);
		return post("PurchaseOrders", objFactory.createPurchaseOrders(array)).getPurchaseOrders().getPurchaseOrder();
	}
		
	public PurchaseOrder getPurchaseOrder(String id) {
	    return singleResult(get("PurchaseOrders/" + id).getPurchaseOrders().getPurchaseOrder());
	}
	
	//RECEIPTS
	public List<Receipt> getReceipts() {
	    Response responseObj = get("Receipts");
		if (responseObj.getReceipts() == null) {
			ArrayOfReceipt array = new ArrayOfReceipt();
			return array.getReceipt();
		} else {
			return responseObj.getReceipts().getReceipt();
		}
	}
	
	public List<Receipt> getReceipts(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);

	    Response responseObj = get("Receipts", modifiedAfter, params);
		if (responseObj.getReceipts() == null) {
			ArrayOfReceipt array = new ArrayOfReceipt();
			return array.getReceipt();
		} else {
			return responseObj.getReceipts().getReceipt();
		}
	}
	
	public List<Receipt> createReceipts(List<Receipt> objects) {
		ArrayOfReceipt array = new ArrayOfReceipt();
		array.getReceipt().addAll(objects);
		return put("Receipt", objFactory.createReceipts(array)).getReceipts().getReceipt();
	}
	
	public List<Receipt> updateReceipt(List<Receipt> objects) {
		ArrayOfReceipt array = new ArrayOfReceipt();
		array.getReceipt().addAll(objects);
		return post("Receipts", objFactory.createReceipts(array)).getReceipts().getReceipt();
	}
		
	public Receipt getReceipt(String id) {
	    return singleResult(get("Receipts/" + id).getReceipts().getReceipt());
	}
	
	//REPEATING INVOICES
	public List<RepeatingInvoice> getRepeatingInvoices() {
		Response responseObj = get("RepeatingInvoices");
		if (responseObj.getRepeatingInvoices() == null) {
			ArrayOfRepeatingInvoice array = new ArrayOfRepeatingInvoice();
			return array.getRepeatingInvoice();
		} else {
			return responseObj.getRepeatingInvoices().getRepeatingInvoice();
		}
	    //return get("RepeatingInvoices").getRepeatingInvoices().getRepeatingInvoice();
	}
	
	public List<RepeatingInvoice> getRepeatingInvoices(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);

	    Response responseObj = get("RepeatingInvoices", modifiedAfter, params);
		if (responseObj.getRepeatingInvoices() == null) {
			ArrayOfRepeatingInvoice array = new ArrayOfRepeatingInvoice();
			return array.getRepeatingInvoice();
		} else {
			return responseObj.getRepeatingInvoices().getRepeatingInvoice();
		}
	}
		
	public RepeatingInvoice getRepeatingInvoice(String id) {
	    return singleResult(get("RepeatingInvoices/" + id).getRepeatingInvoices().getRepeatingInvoice());
	}
	
	//REPORTS		
	public Report getReport(String id, String where, String order) {
		Map<String, String> params = new HashMap<>();
		addToMapIfNotNull(params, "Where", where);
		addToMapIfNotNull(params, "order", order);
	    return singleResult(get("Report/" + id, null,params).getReports().getReport());
	}
	
	//TAX RATES 
	public List<TaxRate> getTaxRates() {
	    Response responseObj = get("TaxRates");
		if (responseObj.getTaxRates() == null) {
			ArrayOfTaxRate array = new ArrayOfTaxRate();
			return array.getTaxRate();
		} else {
			return responseObj.getTaxRates().getTaxRate();
		}
	}
	
	public List<TaxRate> getTaxRates(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);

	    Response responseObj = get("TaxRates", modifiedAfter, params);
 		if (responseObj.getTaxRates() == null) {
 			ArrayOfTaxRate array = new ArrayOfTaxRate();
 			return array.getTaxRate();
 		} else {
 			return responseObj.getTaxRates().getTaxRate();
 		}
	}
	
	public List<TaxRate> createTaxRates(List<TaxRate> objects) {
		ArrayOfTaxRate array = new ArrayOfTaxRate();
		array.getTaxRate().addAll(objects);
		return put("TaxRates", objFactory.createTaxRates(array)).getTaxRates().getTaxRate();
	}
	
	public List<TaxRate> updateTaxRate(List<TaxRate> objects) {
		ArrayOfTaxRate array = new ArrayOfTaxRate();
		array.getTaxRate().addAll(objects);
		return post("TaxRates", objFactory.createTaxRates(array)).getTaxRates().getTaxRate();
	}
		
	public TaxRate getTaxRate(String id) {
	    return singleResult(get("TaxRates/" + id).getTaxRates().getTaxRate());
	}
	
	//TRACKING CATEGORIES 
	public List<TrackingCategory> getTrackingCategories() {
	    Response responseObj = get("TrackingCategories");
		if (responseObj.getTrackingCategories() == null) {
			ArrayOfTrackingCategory array = new ArrayOfTrackingCategory();
			return array.getTrackingCategory();
		} else {
			return responseObj.getTrackingCategories().getTrackingCategory();
		}
	}
	
	public List<TrackingCategory> getTrackingCategories(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);

	    Response responseObj = get("TrackingCategories", modifiedAfter, params);
		if (responseObj.getTrackingCategories() == null) {
			ArrayOfTrackingCategory array = new ArrayOfTrackingCategory();
			return array.getTrackingCategory();
		} else {
			return responseObj.getTrackingCategories().getTrackingCategory();
		}
	}
	
	public List<TrackingCategory> createTrackingCategories(List<TrackingCategory> objects) {
		ArrayOfTrackingCategory array = new ArrayOfTrackingCategory();
		array.getTrackingCategory().addAll(objects);
		return put("TrackingCategories", objFactory.createTrackingCategories(array)).getTrackingCategories().getTrackingCategory();
	}
	
	public List<TrackingCategory> updateTrackingCategory(List<TrackingCategory> objects) {
		ArrayOfTrackingCategory array = new ArrayOfTrackingCategory();
		array.getTrackingCategory().addAll(objects);
		return post("TrackingCategories", objFactory.createTrackingCategories(array)).getTrackingCategories().getTrackingCategory();
	}
		
	public TrackingCategory getTrackingCategory(String id) {
	    return singleResult(get("TrackingCategories/" + id).getTrackingCategories().getTrackingCategory());
	}
	
	public String deleteTrackingCategory(String id) {
	    return delete("TrackingCategories/" + id).getStatus();
	}
	
	// TRACK CATEGORY OPTIONS
	public List<TrackingCategoryOption> createTrackingCategoryOption(List<TrackingCategoryOption> objects,String id) {
		ArrayOfTrackingCategoryOption array = new ArrayOfTrackingCategoryOption();
		array.getOption().addAll(objects);	
		return put("TrackingCategories/" + id + "/Options", objFactory.createTrackingCategoryOptions(array)).getOptions().getOption();	
	}
	
	// USERS
	public List<User> getUsers() {
		Response responseObj = get("Users");
		if (responseObj.getUsers() == null) {
			ArrayOfUser array = new ArrayOfUser();
			return array.getUser();
		} else {
			return responseObj.getUsers().getUser();
		}
	}
	
	public List<User> getUsers(Date modifiedAfter, String where, String order) {
	    Map<String, String> params = new HashMap<>();
	    addToMapIfNotNull(params, "Where", where);
	    addToMapIfNotNull(params, "order", order);
	    
	    Response responseObj = get("Users", modifiedAfter, params);
		if (responseObj.getUsers() == null) {
			ArrayOfUser array = new ArrayOfUser();
			return array.getUser();
		} else {
			return responseObj.getUsers().getUser();
		}
	}
		
	public User getUser(String id) {
	    return singleResult(get("Users/" + id).getUsers().getUser());
	}

	public String getData() {
		return data;
	}
}
