package com.xero.api.client;


import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.xero.api.ApiClient;
import com.xero.models.accounting.*;

import java.io.File;


import org.threeten.bp.*;
import java.io.IOException;

import java.io.File;
import java.io.IOException;


import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class AccountingApiContactsTest {

  ApiClient defaultClient;
  AccountingApi accountingApi;
  String accessToken;
  String xeroTenantId;

  File body;

  private static boolean setUpIsDone = false;

  @Before
  public void setUp() {
    // Set Access Token and Tenant Id
    accessToken = "123";
    xeroTenantId = "xyz";

    // Init AccountingApi client
    // NEW Sandbox for API Mocking
    defaultClient =
        new ApiClient(
            "https://xero-accounting.getsandbox.com:443/api.xro/2.0", null, null, null, null);

    accountingApi = AccountingApi.getInstance(defaultClient);

    ClassLoader classLoader = getClass().getClassLoader();
    body = new File(classLoader.getResource("helo-heros.jpg").getFile());

    // ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
    if (setUpIsDone) {
      return;
    }

    try {
      System.out.println("Sleep for 60 seconds");
      Thread.sleep(60);
    } catch (InterruptedException e) {
      System.out.println(e);
    }
    // do the setup
    setUpIsDone = true;
  }

  public void tearDown() {
    accountingApi = null;
    defaultClient = null;
  }

  @Test
  public void getContactsTest() throws IOException {
    System.out.println("@Test - getContactsTest");

    OffsetDateTime ifModifiedSince = null;
    String where = null;
    String order = null;
    List<UUID> ids = new ArrayList();
    Boolean includeArchived = null;
    Contacts response =
        accountingApi.getContacts(
            accessToken, xeroTenantId, ifModifiedSince, where, order, ids, null, includeArchived);

    assertThat(
        response.getContacts().get(0).getContactID(),
        is(equalTo(UUID.fromString("5cc8cf28-567e-4d43-b287-687cfcaec47c"))));
    assertThat(response.getContacts().get(0).getName(), is(equalTo("Katherine Warren")));
    assertThat(response.getContacts().get(0).getFirstName(), is(equalTo("Katherine")));
    assertThat(response.getContacts().get(0).getLastName(), is(equalTo("Warren")));
    assertThat(
        response.getContacts().get(0).getEmailAddress(), is(equalTo("kat.warren@clampett.com")));
    assertThat(
        response.getContacts().get(0).getContactStatus(),
        is(equalTo(com.xero.models.accounting.Contact.ContactStatusEnum.ACTIVE)));
    assertThat(
        response.getContacts().get(0).getAddresses().get(1).getAddressType(),
        is(equalTo(com.xero.models.accounting.Address.AddressTypeEnum.POBOX)));
    assertThat(
        response.getContacts().get(0).getAddresses().get(1).getCity(), is(equalTo("Palo Alto")));
    assertThat(response.getContacts().get(0).getAddresses().get(1).getRegion(), is(equalTo("CA")));
    assertThat(
        response.getContacts().get(0).getAddresses().get(1).getPostalCode(), is(equalTo("94020")));
    assertThat(
        response.getContacts().get(0).getAddresses().get(1).getCountry(),
        is(equalTo("United States")));
    assertThat(
        response.getContacts().get(0).getPhones().get(1).getPhoneType(),
        is(equalTo(com.xero.models.accounting.Phone.PhoneTypeEnum.DEFAULT)));
    assertThat(
        response.getContacts().get(0).getPhones().get(1).getPhoneNumber(), is(equalTo("847-1294")));
    assertThat(
        response.getContacts().get(0).getPhones().get(1).getPhoneAreaCode(), is(equalTo("(626)")));
    assertThat(response.getContacts().get(0).getIsSupplier(), is(equalTo(true)));
    assertThat(response.getContacts().get(0).getIsCustomer(), is(equalTo(true)));
    assertThat(
        response.getContacts().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2017-08-21T20:49:04.227Z"))));
    assertThat(
        response.getContacts().get(0).getBalances().getAccountsReceivable().getOutstanding(),
        is(equalTo(760.0)));
    assertThat(
        response.getContacts().get(0).getBalances().getAccountsReceivable().getOverdue(),
        is(equalTo(920.0)));
    assertThat(
        response.getContacts().get(0).getBalances().getAccountsPayable().getOutstanding(),
        is(equalTo(231.6)));
    assertThat(
        response.getContacts().get(0).getBalances().getAccountsPayable().getOverdue(),
        is(equalTo(360.0)));
    assertThat(response.getContacts().get(0).getHasAttachments(), is(equalTo(false)));
    // System.out.println(response.getContacts().get(0).toString());
  }

  @Test
  public void getContactTest() throws IOException {
    System.out.println("@Test - getContactTest");

    UUID contactID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Contacts response = accountingApi.getContact(accessToken, xeroTenantId, contactID);

    assertThat(
        response.getContacts().get(0).getContactID(),
        is(equalTo(UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d"))));
    assertThat(response.getContacts().get(0).getContactNumber(), is(equalTo("SB2")));
    assertThat(response.getContacts().get(0).getAccountNumber(), is(equalTo("1234567")));
    assertThat(
        response.getContacts().get(0).getContactStatus(),
        is(equalTo(com.xero.models.accounting.Contact.ContactStatusEnum.ACTIVE)));
    assertThat(response.getContacts().get(0).getName(), is(equalTo("Acme Parts Co.")));
    assertThat(response.getContacts().get(0).getFirstName(), is(equalTo("Blake")));
    assertThat(response.getContacts().get(0).getLastName(), is(equalTo("Kohler")));
    assertThat(response.getContacts().get(0).getEmailAddress(), is(equalTo("bk@krave.co")));
    assertThat(response.getContacts().get(0).getSkypeUserName(), is(equalTo("blake")));
    assertThat(
        response.getContacts().get(0).getContactPersons().get(0).getFirstName(),
        is(equalTo("Sue")));
    assertThat(
        response.getContacts().get(0).getContactPersons().get(0).getLastName(),
        is(equalTo("Johnson")));
    assertThat(
        response.getContacts().get(0).getContactPersons().get(0).getEmailAddress(),
        is(equalTo("sue.johnson@krave.com")));
    assertThat(
        response.getContacts().get(0).getContactPersons().get(0).getIncludeInEmails(),
        is(equalTo(true)));
    assertThat(response.getContacts().get(0).getBankAccountDetails(), is(equalTo("12334567")));
    assertThat(response.getContacts().get(0).getTaxNumber(), is(equalTo("123-22-3456")));
    assertThat(response.getContacts().get(0).getAccountsReceivableTaxType(), is(equalTo("TAX003")));
    assertThat(response.getContacts().get(0).getAccountsPayableTaxType(), is(equalTo("TAX022")));
    assertThat(
        response.getContacts().get(0).getAddresses().get(0).getAddressType(),
        is(equalTo(com.xero.models.accounting.Address.AddressTypeEnum.STREET)));
    assertThat(
        response.getContacts().get(0).getAddresses().get(0).getAddressLine1(),
        is(equalTo("123 Fake Street")));
    assertThat(
        response.getContacts().get(0).getAddresses().get(0).getCity(), is(equalTo("Vancouver")));
    assertThat(
        response.getContacts().get(0).getAddresses().get(0).getRegion(),
        is(equalTo("British Columbia")));
    assertThat(
        response.getContacts().get(0).getAddresses().get(0).getPostalCode(),
        is(equalTo("V6B 2T4")));
    assertThat(
        response.getContacts().get(0).getPhones().get(0).getPhoneType(),
        is(equalTo(com.xero.models.accounting.Phone.PhoneTypeEnum.DDI)));
    assertThat(
        response.getContacts().get(0).getPhones().get(0).getPhoneCountryCode(), is(equalTo("4")));
    assertThat(
        response.getContacts().get(0).getPhones().get(0).getPhoneNumber(),
        is(equalTo("489-44493")));
    assertThat(
        response.getContacts().get(0).getPhones().get(0).getPhoneAreaCode(), is(equalTo("345")));
    assertThat(response.getContacts().get(0).getIsSupplier(), is(equalTo(true)));
    assertThat(response.getContacts().get(0).getIsCustomer(), is(equalTo(true)));
    assertThat(
        response.getContacts().get(0).getDefaultCurrency(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.USD)));
    assertThat(response.getContacts().get(0).getSalesDefaultAccountCode(), is(equalTo("002")));
    assertThat(response.getContacts().get(0).getPurchasesDefaultAccountCode(), is(equalTo("660")));
    assertThat(response.getContacts().get(0).getWebsite(), is(equalTo("http://www.google.com")));
    assertThat(
        response.getContacts().get(0).getBrandingTheme().getBrandingThemeID(),
        is(equalTo(UUID.fromString("dabc7637-62c1-4941-8a6e-ee44fa5090e7"))));
    assertThat(response.getContacts().get(0).getBrandingTheme().getName(), is(equalTo("Standard")));
    assertThat(
        response.getContacts().get(0).getBatchPayments().getDetails(), is(equalTo("biz checking")));
    assertThat(
        response.getContacts().get(0).getBatchPayments().getBankAccountNumber(),
        is(equalTo("12334567")));
    assertThat(
        response.getContacts().get(0).getBatchPayments().getBankAccountName(),
        is(equalTo("Citi Bank")));
    assertThat(response.getContacts().get(0).getDiscount(), is(equalTo(13.0)));
    assertThat(
        response.getContacts().get(0).getBalances().getAccountsReceivable().getOutstanding(),
        is(equalTo(118.90)));
    assertThat(
        response.getContacts().get(0).getBalances().getAccountsReceivable().getOverdue(),
        is(equalTo(136.90)));
    assertThat(
        response.getContacts().get(0).getBalances().getAccountsPayable().getOutstanding(),
        is(equalTo(-43.60)));
    assertThat(
        response.getContacts().get(0).getBalances().getAccountsPayable().getOverdue(),
        is(equalTo(40.00)));
    assertThat(
        response.getContacts().get(0).getPaymentTerms().getBills().getDay(),
        is(equalTo(Integer.parseInt("12"))));
    assertThat(
        response.getContacts().get(0).getPaymentTerms().getBills().getType(),
        is(equalTo(com.xero.models.accounting.PaymentTermType.OFFOLLOWINGMONTH)));
    assertThat(
        response.getContacts().get(0).getPaymentTerms().getSales().getDay(),
        is(equalTo(Integer.parseInt("14"))));
    assertThat(
        response.getContacts().get(0).getPaymentTerms().getSales().getType(),
        is(equalTo(com.xero.models.accounting.PaymentTermType.OFCURRENTMONTH)));
    assertThat(
        response.getContacts().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-01T17:02:57.193Z"))));
    // System.out.println(response.getContacts().get(0).toString());
  }
  /*
  	@Test
       public void createContactTest() throws IOException {
          System.out.println("@Test - createContactTest");

          Contact contact = new Contact();
          Contacts response = accountingApi.createContact(accessToken,xeroTenantId,contact);
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
  */
  @Test
  public void updateContactTest() throws IOException {
    System.out.println("@Test - updateContactTest");

    UUID contactID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Contacts contacts = new Contacts();
    Contacts response = accountingApi.updateContact(accessToken, xeroTenantId, contactID, contacts);
    assertThat(
        response.getContacts().get(0).getContactID(),
        is(equalTo(UUID.fromString("d5be01fb-b09f-4c3a-9c67-e10c2a03412c"))));
    assertThat(
        response.getContacts().get(0).getContactStatus(),
        is(equalTo(com.xero.models.accounting.Contact.ContactStatusEnum.ACTIVE)));
    assertThat(response.getContacts().get(0).getName(), is(equalTo("FooBar")));
    assertThat(response.getContacts().get(0).getEmailAddress(), is(equalTo("sid30680@blah.com")));
    assertThat(
        response.getContacts().get(0).getPhones().get(3).getPhoneType(),
        is(equalTo(com.xero.models.accounting.Phone.PhoneTypeEnum.MOBILE)));
    assertThat(
        response.getContacts().get(0).getPhones().get(3).getPhoneNumber(), is(equalTo("555-1212")));
    assertThat(
        response.getContacts().get(0).getPhones().get(3).getPhoneAreaCode(), is(equalTo("415")));
    assertThat(response.getContacts().get(0).getIsSupplier(), is(equalTo(false)));
    assertThat(response.getContacts().get(0).getIsCustomer(), is(equalTo(false)));
    assertThat(
        response.getContacts().get(0).getPaymentTerms().getBills().getDay(),
        is(equalTo(Integer.parseInt("15"))));
    assertThat(
        response.getContacts().get(0).getPaymentTerms().getBills().getType(),
        is(equalTo(com.xero.models.accounting.PaymentTermType.OFCURRENTMONTH)));
    assertThat(
        response.getContacts().get(0).getPaymentTerms().getSales().getDay(),
        is(equalTo(Integer.parseInt("10"))));
    assertThat(
        response.getContacts().get(0).getPaymentTerms().getSales().getType(),
        is(equalTo(com.xero.models.accounting.PaymentTermType.DAYSAFTERBILLMONTH)));
    assertThat(
        response.getContacts().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-01T00:27:11.763Z"))));
    assertThat(response.getContacts().get(0).getHasValidationErrors(), is(equalTo(false)));
    // System.out.println(response.getContacts().get(0).toString());
  }

  @Test
  public void createContactAttachmentByFileNameTest() throws IOException {
    System.out.println("@Test - createContactAttachmentByFileNameTest");

    UUID contactID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
    String fileName = "sample5.jpg";

    Attachments response =
        accountingApi.createContactAttachmentByFileName(
            accessToken, xeroTenantId, contactID, fileName, body);
    assertThat(
        response.getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("27e37b01-6996-4ebe-836c-95fd472ad674"))));
    assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("sample5.jpg")));
    assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
    assertThat(
        response.getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/Contacts/8138a266-fb42-49b2-a104-014b7045753d/Attachments/sample5.jpg")));
    assertThat(
        response.getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal("2878711"))));
    assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
    // System.out.println(response.getAttachments().get(0).toString());
  }

  @Test
  public void getContactAttachmentsTest() throws IOException {
    System.out.println("@Test - getContactAttachmentsTest");

    UUID contactID = UUID.fromString("04e0a3e3-b116-456a-9f32-9706f0d33afa");
    Attachments response =
        accountingApi.getContactAttachments(accessToken, xeroTenantId, contactID);

    assertThat(
        response.getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("04e0a3e3-b116-456a-9f32-9706f0d33afa"))));
    assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("sample5.jpg")));
    assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
    assertThat(
        response.getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/Contacts/8138a266-fb42-49b2-a104-014b7045753d/Attachments/sample5.jpg")));
    assertThat(
        response.getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal("2878711"))));
    assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
    // System.out.println(response.getAttachments().get(0).toString());
  }

  @Test
  public void getContactCISSettingsTest() throws IOException {
    System.out.println("@Test - getContactCISSettingsTest - not implemented");

    // UUID contactID = null;
    // CISSettings response = api.getContactCISSettings(contactID);

    // TODO: test validations
  }
}
