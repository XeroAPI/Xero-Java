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
import com.xero.example.CustomJsonConfig;

import com.xero.api.client.*;
import com.xero.models.accounting.*;

import com.xero.example.SampleData;

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

public class AccountingApiCreditNoteTest {

	CustomJsonConfig config;
	ApiClient apiClientForAccounting; 
	AccountingApi api; 

    private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
		config = new CustomJsonConfig();
		apiClientForAccounting = new ApiClient("https://virtserver.swaggerhub.com/Xero/accounting/2.0.0",null,null,null);
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
    public void createCreditNoteTest() throws IOException {
        Boolean summarizeErrors = null;
        CreditNotes creditNotes = null;
        CreditNotes response = api.createCreditNote(summarizeErrors, creditNotes);

        // TODO: test validations
    }
    
    /*

    @Test
    public void createCreditNoteAllocationTest() throws IOException {
        UUID creditNoteID = null;
        Allocations allocations = null;
        Allocations response = api.createCreditNoteAllocation(creditNoteID, allocations);

        // TODO: test validations
    }
    
    
    @Test
    public void createCreditNoteAttachmentByFileNameTest() throws IOException {
        UUID creditNoteID = null;
        String fileName = null;
        byte[] body = null;
        Attachments response = api.createCreditNoteAttachmentByFileName(creditNoteID, fileName, body);

        // TODO: test validations
    }
    
    
    @Test
    public void createCreditNoteHistoryTest() throws IOException {
        UUID creditNoteID = null;
        HistoryRecords historyRecords = null;
        HistoryRecords response = api.createCreditNoteHistory(creditNoteID, historyRecords);

        // TODO: test validations
    }

     @Test
    public void getCreditNoteTest() throws IOException {
        UUID creditNoteID = null;
        CreditNotes response = api.getCreditNote(creditNoteID);

        // TODO: test validations
    }
    
    
    @Test
    public void getCreditNoteAsPdfTest() throws IOException {
        UUID creditNoteID = null;
        String contentType = null;
        File response = api.getCreditNoteAsPdf(creditNoteID, contentType);

        // TODO: test validations
    }
    
    
    @Test
    public void getCreditNoteAttachmentByFileNameTest() throws IOException {
        UUID creditNoteID = null;
        String fileName = null;
        String contentType = null;
        Attachments response = api.getCreditNoteAttachmentByFileName(creditNoteID, fileName, contentType);

        // TODO: test validations
    }
    
    
    @Test
    public void getCreditNoteAttachmentByIdTest() throws IOException {
        UUID creditNoteID = null;
        UUID attachmentID = null;
        String contentType = null;
        File response = api.getCreditNoteAttachmentById(creditNoteID, attachmentID, contentType);

        // TODO: test validations
    }
    
    
    @Test
    public void getCreditNoteAttachmentsTest() throws IOException {
        UUID creditNoteID = null;
        Attachments response = api.getCreditNoteAttachments(creditNoteID);

        // TODO: test validations
    }
    
    
    @Test
    public void getCreditNoteHistoryTest() throws IOException {
        UUID creditNoteID = null;
        HistoryRecords response = api.getCreditNoteHistory(creditNoteID);

        // TODO: test validations
    }
    
    
    @Test
    public void getCreditNotesTest() throws IOException {
        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        BigDecimal page = null;
        CreditNotes response = api.getCreditNotes(ifModifiedSince, where, order, page);

        // TODO: test validations
    }

     @Test
    public void updateCreditNoteTest() throws IOException {
        UUID creditNoteID = null;
        CreditNotes creditNotes = null;
        CreditNotes response = api.updateCreditNote(creditNoteID, creditNotes);

        // TODO: test validations
    }
    
    
    @Test
    public void updateCreditNoteAttachmentByFileNameTest() throws IOException {
        UUID creditNoteID = null;
        UUID fileName = null;
        File body = null;
        Attachments response = api.updateCreditNoteAttachmentByFileName(creditNoteID, fileName, body);

        // TODO: test validations
    }
    
    
    @Test
    public void updateCreditNoteAttachmentByIdTest() throws IOException {
        UUID creditNoteID = null;
        UUID attachmentID = null;
        File body = null;
        Attachments response = api.updateCreditNoteAttachmentById(creditNoteID, attachmentID, body);

        // TODO: test validations
    }
    */

/*
	@Test
	public void getContactsTest() throws IOException {
        System.out.println("@Test - getContactsTest");

        OffsetDateTime ifModifiedSince = null;
        String where = null;
        String order = null;
        String ids = null;
        Boolean includeArchived = null;
        Contacts response = api.getContacts(ifModifiedSince, where, order, ids, null, includeArchived);

        assertThat(response.getContacts().get(0).getContactID(), is(equalTo(UUID.fromString("5cc8cf28-567e-4d43-b287-687cfcaec47c"))));
        assertThat(response.getContacts().get(0).getName(), is(equalTo("Katherine Warren")));
        assertThat(response.getContacts().get(0).getFirstName(), is(equalTo("Katherine")));
        assertThat(response.getContacts().get(0).getLastName(), is(equalTo("Warren")));
        assertThat(response.getContacts().get(0).getEmailAddress(), is(equalTo("kat.warren@clampett.com")));
        assertThat(response.getContacts().get(0).getContactStatus(), is(equalTo(com.xero.models.accounting.Contact.ContactStatusEnum.ACTIVE)));        
        assertThat(response.getContacts().get(0).getAddresses().get(1).getAddressType(), is(equalTo(com.xero.models.accounting.Address.AddressTypeEnum.POBOX)));
        assertThat(response.getContacts().get(0).getAddresses().get(1).getCity(), is(equalTo("Palo Alto")));
        assertThat(response.getContacts().get(0).getAddresses().get(1).getRegion(), is(equalTo("CA")));
        assertThat(response.getContacts().get(0).getAddresses().get(1).getPostalCode(), is(equalTo("94020")));
        assertThat(response.getContacts().get(0).getAddresses().get(1).getCountry(), is(equalTo("United States")));
        assertThat(response.getContacts().get(0).getPhones().get(1).getPhoneType(), is(equalTo(com.xero.models.accounting.Phone.PhoneTypeEnum.DEFAULT)));
        assertThat(response.getContacts().get(0).getPhones().get(1).getPhoneNumber(), is(equalTo("847-1294")));
        assertThat(response.getContacts().get(0).getPhones().get(1).getPhoneAreaCode(), is(equalTo("(626)")));
        assertThat(response.getContacts().get(0).getIsSupplier(), is(equalTo(true)));
        assertThat(response.getContacts().get(0).getIsCustomer(), is(equalTo(true)));
        assertThat(response.getContacts().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2017-08-21T13:49:04.227-07:00"))));
        assertThat(response.getContacts().get(0).getBalances().getAccountsReceivable().getOutstanding(), is(equalTo(760.0f)));
        assertThat(response.getContacts().get(0).getBalances().getAccountsReceivable().getOverdue(), is(equalTo(920.0f)));
        assertThat(response.getContacts().get(0).getBalances().getAccountsPayable().getOutstanding(), is(equalTo(231.6f)));
        assertThat(response.getContacts().get(0).getBalances().getAccountsPayable().getOverdue(), is(equalTo(360.0f)));    
        assertThat(response.getContacts().get(0).getHasAttachments(), is(equalTo(false)));
        //System.out.println(response.getContacts().get(0).toString());
	}

   	@Test
    public void getContactTest() throws IOException {
        System.out.println("@Test - getContactTest");

        UUID contactID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Contacts response = api.getContact(contactID);

        assertThat(response.getContacts().get(0).getContactID(), is(equalTo(UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d"))));
        assertThat(response.getContacts().get(0).getContactNumber(), is(equalTo("SB2")));
        assertThat(response.getContacts().get(0).getAccountNumber(), is(equalTo("1234567")));
        assertThat(response.getContacts().get(0).getContactStatus(), is(equalTo(com.xero.models.accounting.Contact.ContactStatusEnum.ACTIVE)));
        assertThat(response.getContacts().get(0).getName(), is(equalTo("Acme Parts Co.")));
        assertThat(response.getContacts().get(0).getFirstName(), is(equalTo("Blake")));
        assertThat(response.getContacts().get(0).getLastName(), is(equalTo("Kohler")));
        assertThat(response.getContacts().get(0).getEmailAddress(), is(equalTo("bk@krave.co")));
        assertThat(response.getContacts().get(0).getSkypeUserName(), is(equalTo("blake")));
        assertThat(response.getContacts().get(0).getContactPersons().get(0).getFirstName(), is(equalTo("Sue")));
        assertThat(response.getContacts().get(0).getContactPersons().get(0).getLastName(), is(equalTo("Johnson")));
        assertThat(response.getContacts().get(0).getContactPersons().get(0).getEmailAddress(), is(equalTo("sue.johnson@krave.com")));
        assertThat(response.getContacts().get(0).getContactPersons().get(0).getIncludeInEmails(), is(equalTo(true)));
        assertThat(response.getContacts().get(0).getBankAccountDetails(), is(equalTo("12334567")));
        assertThat(response.getContacts().get(0).getTaxNumber(), is(equalTo("123-22-3456")));
        assertThat(response.getContacts().get(0).getAccountsReceivableTaxType(), is(equalTo("TAX003")));
        assertThat(response.getContacts().get(0).getAccountsPayableTaxType(), is(equalTo("TAX022")));        
        assertThat(response.getContacts().get(0).getAddresses().get(0).getAddressType(), is(equalTo(com.xero.models.accounting.Address.AddressTypeEnum.STREET)));
        assertThat(response.getContacts().get(0).getAddresses().get(0).getAddressLine1(), is(equalTo("123 Fake Street")));
        assertThat(response.getContacts().get(0).getAddresses().get(0).getCity(), is(equalTo("Vancouver")));
        assertThat(response.getContacts().get(0).getAddresses().get(0).getRegion(), is(equalTo("British Columbia")));
        assertThat(response.getContacts().get(0).getAddresses().get(0).getPostalCode(), is(equalTo("V6B 2T4")));
        assertThat(response.getContacts().get(0).getPhones().get(0).getPhoneType(), is(equalTo(com.xero.models.accounting.Phone.PhoneTypeEnum.DDI)));
        assertThat(response.getContacts().get(0).getPhones().get(0).getPhoneCountryCode(), is(equalTo("4")));
        assertThat(response.getContacts().get(0).getPhones().get(0).getPhoneNumber(), is(equalTo("489-44493")));
        assertThat(response.getContacts().get(0).getPhones().get(0).getPhoneAreaCode(), is(equalTo("345")));
        assertThat(response.getContacts().get(0).getIsSupplier(), is(equalTo(true)));
        assertThat(response.getContacts().get(0).getIsCustomer(), is(equalTo(true)));
        assertThat(response.getContacts().get(0).getDefaultCurrency(), is(equalTo(com.xero.models.accounting.CurrencyCode.USD)));
        assertThat(response.getContacts().get(0).getSalesDefaultAccountCode(), is(equalTo("002")));
        assertThat(response.getContacts().get(0).getPurchasesDefaultAccountCode(), is(equalTo("660")));
        assertThat(response.getContacts().get(0).getWebsite(), is(equalTo("http://www.google.com")));
        assertThat(response.getContacts().get(0).getBrandingTheme().getBrandingThemeID(), is(equalTo(UUID.fromString("dabc7637-62c1-4941-8a6e-ee44fa5090e7"))));
        assertThat(response.getContacts().get(0).getBrandingTheme().getName(), is(equalTo("Standard")));
        assertThat(response.getContacts().get(0).getBatchPayments().getDetails(), is(equalTo("biz checking")));
        assertThat(response.getContacts().get(0).getBatchPayments().getBankAccountNumber(), is(equalTo("12334567")));
        assertThat(response.getContacts().get(0).getBatchPayments().getBankAccountName(), is(equalTo("Citi Bank")));
        assertThat(response.getContacts().get(0).getDiscount(), is(equalTo(13.0f)));
        assertThat(response.getContacts().get(0).getBalances().getAccountsReceivable().getOutstanding(), is(equalTo(118.90f)));
        assertThat(response.getContacts().get(0).getBalances().getAccountsReceivable().getOverdue(), is(equalTo(136.90f)));
        assertThat(response.getContacts().get(0).getBalances().getAccountsPayable().getOutstanding(), is(equalTo(-43.60f)));
        assertThat(response.getContacts().get(0).getBalances().getAccountsPayable().getOverdue(), is(equalTo(40.00f)));    
        assertThat(response.getContacts().get(0).getPaymentTerms().getBills().getDay(), is(equalTo(Integer.parseInt("12"))));
        assertThat(response.getContacts().get(0).getPaymentTerms().getBills().getType(), is(equalTo(com.xero.models.accounting.PaymentTermType.OFFOLLOWINGMONTH)));
        assertThat(response.getContacts().get(0).getPaymentTerms().getSales().getDay(), is(equalTo(Integer.parseInt("14"))));
        assertThat(response.getContacts().get(0).getPaymentTerms().getSales().getType(), is(equalTo(com.xero.models.accounting.PaymentTermType.OFCURRENTMONTH)));
        assertThat(response.getContacts().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-03-01T09:02:57.193-08:00"))));  
        //System.out.println(response.getContacts().get(0).toString());
    }
    
	@Test
     public void createContactTest() throws IOException {
        System.out.println("@Test - createContactTest");

        Contact contact = null;
        Contacts response = api.createContact(contact);
		assertThat(response.getContacts().get(0).getContactID(), is(equalTo(UUID.fromString("3ff6d40c-af9a-40a3-89ce-3c1556a25591"))));
		assertThat(response.getContacts().get(0).getContactStatus(), is(equalTo(com.xero.models.accounting.Contact.ContactStatusEnum.ACTIVE)));
		assertThat(response.getContacts().get(0).getName(), is(equalTo("Foo9987")));
        assertThat(response.getContacts().get(0).getEmailAddress(), is(equalTo("sid32476@blah.com")));
        assertThat(response.getContacts().get(0).getPhones().get(3).getPhoneNumber(), is(equalTo("555-1212")));
        assertThat(response.getContacts().get(0).getPhones().get(3).getPhoneAreaCode(), is(equalTo("415")));
        assertThat(response.getContacts().get(0).getPaymentTerms().getBills().getDay(), is(equalTo(Integer.parseInt("15"))));
        assertThat(response.getContacts().get(0).getPaymentTerms().getBills().getType(), is(equalTo(com.xero.models.accounting.PaymentTermType.OFCURRENTMONTH)));
        assertThat(response.getContacts().get(0).getPaymentTerms().getSales().getDay(), is(equalTo(Integer.parseInt("10"))));
        assertThat(response.getContacts().get(0).getPaymentTerms().getSales().getType(), is(equalTo(com.xero.models.accounting.PaymentTermType.DAYSAFTERBILLMONTH)));
        //System.out.println(response.getContacts().get(0).toString());
    }

    @Test
     public void updateContactTest() throws IOException {
        System.out.println("@Test - updateContactTest");

        UUID contactID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Contacts contacts = null;
        Contacts response = api.updateContact(contactID,contacts);
        assertThat(response.getContacts().get(0).getContactID(), is(equalTo(UUID.fromString("d5be01fb-b09f-4c3a-9c67-e10c2a03412c"))));
        assertThat(response.getContacts().get(0).getContactStatus(), is(equalTo(com.xero.models.accounting.Contact.ContactStatusEnum.ACTIVE)));
        assertThat(response.getContacts().get(0).getName(), is(equalTo("FooBar")));
        assertThat(response.getContacts().get(0).getEmailAddress(), is(equalTo("sid30680@blah.com")));
        assertThat(response.getContacts().get(0).getPhones().get(3).getPhoneType(), is(equalTo(com.xero.models.accounting.Phone.PhoneTypeEnum.MOBILE)));
        assertThat(response.getContacts().get(0).getPhones().get(3).getPhoneNumber(), is(equalTo("555-1212")));
        assertThat(response.getContacts().get(0).getPhones().get(3).getPhoneAreaCode(), is(equalTo("415")));
        assertThat(response.getContacts().get(0).getIsSupplier(), is(equalTo(false)));
        assertThat(response.getContacts().get(0).getIsCustomer(), is(equalTo(false)));
        assertThat(response.getContacts().get(0).getPaymentTerms().getBills().getDay(), is(equalTo(Integer.parseInt("15"))));
        assertThat(response.getContacts().get(0).getPaymentTerms().getBills().getType(), is(equalTo(com.xero.models.accounting.PaymentTermType.OFCURRENTMONTH)));
        assertThat(response.getContacts().get(0).getPaymentTerms().getSales().getDay(), is(equalTo(Integer.parseInt("10"))));
        assertThat(response.getContacts().get(0).getPaymentTerms().getSales().getType(), is(equalTo(com.xero.models.accounting.PaymentTermType.DAYSAFTERBILLMONTH)));
        assertThat(response.getContacts().get(0).getUpdatedDateUTC(), is(equalTo(OffsetDateTime.parse("2019-02-28T16:27:11.763-08:00"))));  
        assertThat(response.getContacts().get(0).getHasValidationErrors(), is(equalTo(false))); 
        //System.out.println(response.getContacts().get(0).toString());
    }

    @Test
    public void getContactHistoryTest() throws IOException {
        System.out.println("@Test - getContactHistoryTest");

        UUID contactID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");  
        HistoryRecords response = api.getContactHistory(contactID);
        assertThat(response.getHistoryRecords().get(0).getUser(), is(equalTo("System Generated")));       
        assertThat(response.getHistoryRecords().get(0).getChanges(), is(equalTo("Edited")));     
        assertThat(response.getHistoryRecords().get(0).getDetails(), is(equalTo("Name changed from Foo9987 to Bar8156.")));     
        assertThat(response.getHistoryRecords().get(0).getDateUTC(), is(equalTo(OffsetDateTime.parse("2019-02-28T16:15:22.350-08:00"))));  
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }

    @Test
    public void createContactHistoryTest() throws IOException {
        System.out.println("@Test - createContactHistoryTest - not implemented at this time");

        UUID contactID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");  
        HistoryRecords historyRecords = null;
        HistoryRecords response = api.createContactHistory(contactID, historyRecords);
        assertThat(response.getHistoryRecords().get(0).getDetails(), is(equalTo("Hello World")));     
        assertThat(response.getHistoryRecords().get(0).getDateUTC(), is(equalTo(OffsetDateTime.parse("2019-02-28T16:15:24.272-08:00"))));  
        //System.out.println(response.getHistoryRecords().get(0).toString());
    }

    @Test
    public void createContactAttachmentByFileNameTest() throws IOException {
        System.out.println("@Test - createContactAttachmentByFileNameTest");

        UUID contactID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");  
        InputStream inputStream = CustomJsonConfig.class.getResourceAsStream("/helo-heros.jpg");
        byte[] body = IOUtils.toByteArray(inputStream);
        String fileName = "sample5.jpg";
        
        Attachments response = api.createContactAttachmentByFileName(contactID, fileName, body);
         assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("27e37b01-6996-4ebe-836c-95fd472ad674"))));
        assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("sample5.jpg")));
        assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Contacts/8138a266-fb42-49b2-a104-014b7045753d/Attachments/sample5.jpg")));
        assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().get(0).toString());	
    }
    

    @Test
     public void getContactAttachmentsTest() throws IOException {
        System.out.println("@Test - getContactAttachmentsTest");

        UUID contactID = UUID.fromString("04e0a3e3-b116-456a-9f32-9706f0d33afa");  
        Attachments response = api.getContactAttachments(contactID);

        assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("04e0a3e3-b116-456a-9f32-9706f0d33afa"))));
        assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("sample5.jpg")));
        assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
        assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Contacts/8138a266-fb42-49b2-a104-014b7045753d/Attachments/sample5.jpg")));
        assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
        assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
        //System.out.println(response.getAttachments().get(0).toString());
    }
    


    @Test
    public void getContactCISSettingsTest() throws IOException {
        System.out.println("@Test - getContactCISSettingsTest - not implemented");

        //UUID contactID = null;
        //CISSettings response = api.getContactCISSettings(contactID);

        // TODO: test validations
    }
	*/
}
