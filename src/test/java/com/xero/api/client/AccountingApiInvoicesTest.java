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

public class AccountingApiInvoicesTest {

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
  public void createInvoiceTest() throws IOException {
    System.out.println("@Test - createInvoices");
    Invoices invoices = new Invoices();
    Invoices response = accountingApi.createInvoices(accessToken, xeroTenantId, invoices, false, 4);

    assertThat(
        response.getInvoices().get(0).getType(),
        is(equalTo(com.xero.models.accounting.Invoice.TypeEnum.ACCREC)));
    assertThat(
        response.getInvoices().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 11))));
    assertThat(
        response.getInvoices().get(0).getDueDateAsDate(), is(equalTo(LocalDate.of(2018, 12, 10))));
    assertThat(
        response.getInvoices().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(response.getInvoices().get(0).getInvoiceNumber(), is(equalTo("INV-0007")));
    assertThat(response.getInvoices().get(0).getReference(), is(equalTo("Website Design")));
    assertThat(
        response.getInvoices().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(response.getInvoices().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getInvoices().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(
        response.getInvoices().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.Invoice.StatusEnum.AUTHORISED)));
    assertThat(response.getInvoices().get(0).getSentToContact(), is(equalTo(false)));
    assertThat(response.getInvoices().get(0).getSubTotal(), is(equalTo(40.0)));
    assertThat(response.getInvoices().get(0).getSubTotal().toString(), is(equalTo("40.0")));
    assertThat(response.getInvoices().get(0).getTotalTax(), is(equalTo(0.0)));
    assertThat(response.getInvoices().get(0).getTotalTax().toString(), is(equalTo("0.0")));
    assertThat(response.getInvoices().get(0).getTotal(), is(equalTo(40.0)));
    assertThat(response.getInvoices().get(0).getTotal().toString(), is(equalTo("40.0")));
    assertThat(
        response.getInvoices().get(0).getInvoiceID(),
        is(equalTo(UUID.fromString("ed255415-e141-4150-aab7-89c3bbbb851c"))));
    assertThat(response.getInvoices().get(0).getIsDiscounted(), is(equalTo(false)));
    assertThat(response.getInvoices().get(0).getAmountDue(), is(equalTo(40.0)));
    assertThat(response.getInvoices().get(0).getAmountDue().toString(), is(equalTo("40.0")));
    assertThat(response.getInvoices().get(0).getAmountPaid(), is(equalTo(0.0)));
    assertThat(response.getInvoices().get(0).getAmountPaid().toString(), is(equalTo("0.0")));
    assertThat(
        response.getInvoices().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-11T17:58:46.117Z"))));
    assertThat(response.getInvoices().get(0).getHasErrors(), is(equalTo(false)));
    assertThat(
        response.getInvoices().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
    assertThat(response.getInvoices().get(0).getContact().getName(), is(equalTo("Liam Gallagher")));
    assertThat(response.getInvoices().get(0).getContact().getFirstName(), is(equalTo("Liam")));
    assertThat(response.getInvoices().get(0).getContact().getLastName(), is(equalTo("Gallagher")));
    assertThat(
        response.getInvoices().get(0).getContact().getContactPersons().get(0).getFirstName(),
        is(equalTo("Debbie")));
    assertThat(
        response.getInvoices().get(0).getContact().getContactPersons().get(0).getLastName(),
        is(equalTo("Gwyther")));
    assertThat(
        response.getInvoices().get(0).getContact().getContactPersons().get(0).getEmailAddress(),
        is(equalTo("debbie@rockstar.com")));
    assertThat(
        response.getInvoices().get(0).getContact().getContactGroups().get(0).getName(),
        is(equalTo("Oasis")));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("5f7a612b-fdcc-4d33-90fa-a9f6bc6db32f"))));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Acme Tires")));
    assertThat(response.getInvoices().get(0).getLineItems().get(0).getQuantity(), is(equalTo(2.0)));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(20.0)));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("200")));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getTaxType(), is(equalTo("NONE")));
    assertThat(response.getInvoices().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(0.)));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(40.0)));
    // System.out.println(response.getInvoices().get(0).toString());
  }

  @Test
  public void getInvoiceTest() throws IOException {
    System.out.println("@Test - getInvoiceTest");
    UUID invoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Invoices response = accountingApi.getInvoice(accessToken, xeroTenantId, invoiceID, 4);

    assertThat(
        response.getInvoices().get(0).getType(),
        is(equalTo(com.xero.models.accounting.Invoice.TypeEnum.ACCREC)));
    assertThat(
        response.getInvoices().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 07))));
    assertThat(
        response.getInvoices().get(0).getDueDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 13))));
    assertThat(
        response.getInvoices().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(response.getInvoices().get(0).getInvoiceNumber(), is(equalTo("INV-0006")));
    assertThat(response.getInvoices().get(0).getReference(), is(equalTo("Tour")));
    assertThat(
        response.getInvoices().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(response.getInvoices().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getInvoices().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(
        response.getInvoices().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.Invoice.StatusEnum.PAID)));
    assertThat(response.getInvoices().get(0).getSentToContact(), is(equalTo(false)));
    assertThat(response.getInvoices().get(0).getSubTotal(), is(equalTo(148062.76)));
    assertThat(response.getInvoices().get(0).getSubTotal().toString(), is(equalTo("148062.76")));
    assertThat(response.getInvoices().get(0).getTotalTax(), is(equalTo(0.0)));
    assertThat(response.getInvoices().get(0).getTotalTax().toString(), is(equalTo("0.0")));
    assertThat(response.getInvoices().get(0).getTotal(), is(equalTo(148062.76)));
    assertThat(response.getInvoices().get(0).getTotal().toString(), is(equalTo("148062.76")));
    assertThat(
        response.getInvoices().get(0).getInvoiceID(),
        is(equalTo(UUID.fromString("a03ffcd2-5d91-4c7e-b483-318584e9e439"))));
    assertThat(response.getInvoices().get(0).getIsDiscounted(), is(equalTo(false)));
    assertThat(response.getInvoices().get(0).getAmountDue(), is(equalTo(0.0)));
    assertThat(response.getInvoices().get(0).getAmountDue().toString(), is(equalTo("0.0")));
    assertThat(response.getInvoices().get(0).getAmountPaid().toString(), is(equalTo("148062.76")));
    assertThat(response.getInvoices().get(0).getAmountPaid(), is(equalTo(148062.76)));
    assertThat(
        response.getInvoices().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-07T17:59:28.133Z"))));
    assertThat(response.getInvoices().get(0).getHasErrors(), is(equalTo(false)));
    assertThat(
        response.getInvoices().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
    assertThat(response.getInvoices().get(0).getContact().getName(), is(equalTo("Liam Gallagher")));
    assertThat(response.getInvoices().get(0).getContact().getFirstName(), is(equalTo("Liam")));
    assertThat(response.getInvoices().get(0).getContact().getLastName(), is(equalTo("Gallagher")));
    assertThat(
        response.getInvoices().get(0).getContact().getContactPersons().get(0).getFirstName(),
        is(equalTo("Debbie")));
    assertThat(
        response.getInvoices().get(0).getContact().getContactPersons().get(0).getLastName(),
        is(equalTo("Gwyther")));
    assertThat(
        response.getInvoices().get(0).getContact().getContactPersons().get(0).getEmailAddress(),
        is(equalTo("debbie@rockstar.com")));
    assertThat(
        response.getInvoices().get(0).getContact().getContactGroups().get(0).getName(),
        is(equalTo("Oasis")));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("b18f39d9-7739-4246-9288-72afe939d2d5"))));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Guitars Fender Strat")));
    assertThat(response.getInvoices().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getUnitAmount(),
        is(equalTo(148062.76)));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getItemCode(), is(equalTo("123")));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("200")));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getTaxType(), is(equalTo("NONE")));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(0.0)));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getLineAmount(),
        is(equalTo(148062.76)));
    assertThat(response.getInvoices().get(0).getHasAttachments(), is(equalTo(true)));
    assertThat(
        response.getInvoices().get(0).getFullyPaidOnDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 19))));
    assertThat(
        response.getInvoices().get(0).getPayments().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 19))));
    assertThat(
        response.getInvoices().get(0).getPayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(
        response.getInvoices().get(0).getPayments().get(0).getAmount(), is(equalTo(148062.76)));
    assertThat(
        response.getInvoices().get(0).getPayments().get(0).getReference(), is(equalTo("Yahoo")));
    assertThat(
        response.getInvoices().get(0).getPayments().get(0).getPaymentID(),
        is(equalTo(UUID.fromString("38928000-e9a0-420c-8884-f624bab2a351"))));
    assertThat(
        response.getInvoices().get(0).getPayments().get(0).getHasAccount(), is(equalTo(false)));
    assertThat(
        response.getInvoices().get(0).getPayments().get(0).getHasValidationErrors(),
        is(equalTo(false)));
    assertThat(
        response.getInvoices().get(0).getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("3a2fe7e0-fac7-4ea2-afb2-31cedaabd294"))));
    assertThat(
        response.getInvoices().get(0).getAttachments().get(0).getFileName(),
        is(equalTo("helo-heros.jpg")));
    assertThat(
        response.getInvoices().get(0).getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/Invoices/a03ffcd2-5d91-4c7e-b483-318584e9e439/Attachments/helo-heros.jpg")));
    assertThat(
        response.getInvoices().get(0).getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal("2878711"))));
    assertThat(
        response.getInvoices().get(0).getAttachments().get(0).getMimeType(),
        is(equalTo("image/jpeg")));
    assertThat(response.getInvoices().get(0).getStatusAttributeString(), is(equalTo("ERROR")));
    assertThat(
        response.getInvoices().get(0).getValidationErrors().get(0).getMessage(),
        is(equalTo("Invoice # must be unique.")));
    // System.out.println(response.getInvoices().get(0).toString());
  }

  @Test
  public void getInvoiceAttachmentsTest() throws IOException {
    System.out.println("@Test - getInvoiceAttachments");
    UUID invoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Attachments response =
        accountingApi.getInvoiceAttachments(accessToken, xeroTenantId, invoiceID);

    assertThat(
        response.getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("9808ad7f-c8d4-41cf-995e-bc29cb76fd2c"))));
    assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("foobar.jpg")));
    assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
    assertThat(
        response.getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/Invoices/4074292c-09b3-456d-84e7-add864c6c39b/Attachments/foobar.jpg")));
    assertThat(
        response.getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal("2878711"))));
    assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
    // System.out.println(response.getAttachments().get(0).toString());
  }

  @Test
  public void getInvoicesTest() throws IOException {
    System.out.println("@Test - getInvoices");
    OffsetDateTime ifModifiedSince = null;
    String where = null;
    String order = null;
    List<UUID> ids = new ArrayList();
    List<String> invoiceNumbers = new ArrayList();
    List<UUID> contactIDs = new ArrayList();
    List<String> statuses = new ArrayList();
    Integer page = null;
    Boolean includeArchived = null;
    Boolean createdByMyApp = null;
    Integer unitdp = null;
    Invoices response =
        accountingApi.getInvoices(
            accessToken,
            xeroTenantId,
            ifModifiedSince,
            where,
            order,
            ids,
            invoiceNumbers,
            contactIDs,
            statuses,
            page,
            includeArchived,
            createdByMyApp,
            unitdp);

    assertThat(
        response.getInvoices().get(0).getType(),
        is(equalTo(com.xero.models.accounting.Invoice.TypeEnum.ACCREC)));
    assertThat(
        response.getInvoices().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2018, 10, 20))));
    assertThat(
        response.getInvoices().get(0).getDueDateAsDate(), is(equalTo(LocalDate.of(2018, 12, 30))));
    assertThat(
        response.getInvoices().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(response.getInvoices().get(0).getInvoiceNumber(), is(equalTo("INV-0001")));
    assertThat(response.getInvoices().get(0).getReference(), is(equalTo("Red Fish, Blue Fish")));
    assertThat(
        response.getInvoices().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(response.getInvoices().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getInvoices().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(
        response.getInvoices().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.Invoice.StatusEnum.VOIDED)));
    assertThat(response.getInvoices().get(0).getSentToContact(), is(equalTo(true)));
    assertThat(response.getInvoices().get(0).getSubTotal(), is(equalTo(40.0)));
    assertThat(response.getInvoices().get(0).getSubTotal().toString(), is(equalTo("40.0")));
    assertThat(response.getInvoices().get(0).getTotalTax(), is(equalTo(0.0)));
    assertThat(response.getInvoices().get(0).getTotalTax().toString(), is(equalTo("0.0")));
    assertThat(response.getInvoices().get(0).getTotal(), is(equalTo(40.0)));
    assertThat(response.getInvoices().get(0).getTotal().toString(), is(equalTo("40.0")));
    assertThat(
        response.getInvoices().get(0).getInvoiceID(),
        is(equalTo(UUID.fromString("d4956132-ed94-4dd7-9eaa-aa22dfdf06f2"))));
    assertThat(response.getInvoices().get(0).getIsDiscounted(), is(equalTo(false)));
    assertThat(response.getInvoices().get(0).getAmountDue(), is(equalTo(0.0)));
    assertThat(response.getInvoices().get(0).getAmountDue().toString(), is(equalTo("0.0")));
    assertThat(response.getInvoices().get(0).getAmountPaid().toString(), is(equalTo("0.0")));
    assertThat(response.getInvoices().get(0).getAmountPaid(), is(equalTo(0.0)));
    assertThat(response.getInvoices().get(0).getAmountCredited().toString(), is(equalTo("0.0")));
    assertThat(response.getInvoices().get(0).getAmountCredited(), is(equalTo(0.0)));
    assertThat(
        response.getInvoices().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2018-11-02T16:31:30.160Z"))));
    assertThat(response.getInvoices().get(0).getHasErrors(), is(equalTo(false)));
    assertThat(
        response.getInvoices().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("a3675fc4-f8dd-4f03-ba5b-f1870566bcd7"))));
    assertThat(
        response.getInvoices().get(0).getContact().getName(), is(equalTo("Barney Rubble-83203")));
    // System.out.println(response.getInvoices().get(0).toString());
  }

  @Test
  public void getOnlineInvoiceTest() throws IOException {
    System.out.println("@Test - getOnlineInvoice");
    UUID invoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    OnlineInvoices response = accountingApi.getOnlineInvoice(accessToken, xeroTenantId, invoiceID);

    assertThat(
        response.getOnlineInvoices().get(0).getOnlineInvoiceUrl(),
        is(equalTo("https://in.xero.com/bCWCCfytGdTXoJam9HENWlQt07G6zcDaj4gQojHu")));
    // System.out.println(response.getOnlineInvoices().get(0).toString());
  }

  @Test
  public void updateInvoiceTest() throws IOException {
    System.out.println("@Test - updateInvoice");
    UUID invoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Invoices invoices = new Invoices();
    Invoices response =
        accountingApi.updateInvoice(accessToken, xeroTenantId, invoiceID, invoices, 4);

    assertThat(
        response.getInvoices().get(0).getType(),
        is(equalTo(com.xero.models.accounting.Invoice.TypeEnum.ACCREC)));
    assertThat(
        response.getInvoices().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 11))));
    assertThat(
        response.getInvoices().get(0).getDueDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 12))));
    assertThat(
        response.getInvoices().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(response.getInvoices().get(0).getInvoiceNumber(), is(equalTo("INV-0008")));
    assertThat(
        response.getInvoices().get(0).getReference(), is(equalTo("My the Force be With You")));
    assertThat(
        response.getInvoices().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(response.getInvoices().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getInvoices().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(
        response.getInvoices().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.Invoice.StatusEnum.SUBMITTED)));
    assertThat(response.getInvoices().get(0).getSentToContact(), is(equalTo(false)));
    assertThat(response.getInvoices().get(0).getSubTotal(), is(equalTo(500.0)));
    assertThat(response.getInvoices().get(0).getSubTotal().toString(), is(equalTo("500.0")));
    assertThat(response.getInvoices().get(0).getTotalTax(), is(equalTo(75.0)));
    assertThat(response.getInvoices().get(0).getTotalTax().toString(), is(equalTo("75.0")));
    assertThat(response.getInvoices().get(0).getTotal(), is(equalTo(575.0)));
    assertThat(response.getInvoices().get(0).getTotal().toString(), is(equalTo("575.0")));
    assertThat(
        response.getInvoices().get(0).getInvoiceID(),
        is(equalTo(UUID.fromString("4074292c-09b3-456d-84e7-add864c6c39b"))));
    assertThat(response.getInvoices().get(0).getIsDiscounted(), is(equalTo(false)));
    assertThat(response.getInvoices().get(0).getAmountDue(), is(equalTo(575.0)));
    assertThat(response.getInvoices().get(0).getAmountDue().toString(), is(equalTo("575.0")));
    assertThat(response.getInvoices().get(0).getAmountPaid().toString(), is(equalTo("0.0")));
    assertThat(response.getInvoices().get(0).getAmountPaid(), is(equalTo(0.0)));
    assertThat(
        response.getInvoices().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-11T18:42:08.987Z"))));
    assertThat(response.getInvoices().get(0).getHasErrors(), is(equalTo(false)));
    assertThat(
        response.getInvoices().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("be392c72-c121-4f83-9512-03ac71e54c20"))));
    assertThat(response.getInvoices().get(0).getContact().getName(), is(equalTo("Luke Skywalker")));
    assertThat(
        response.getInvoices().get(0).getContact().getContactStatus(),
        is(equalTo(com.xero.models.accounting.Contact.ContactStatusEnum.ACTIVE)));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("6de1bf9f-de95-4c47-9287-37305db758c9"))));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Light Saber")));
    assertThat(response.getInvoices().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(500.00)));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("200")));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getTaxType(), is(equalTo("OUTPUT2")));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(75.0)));
    assertThat(
        response.getInvoices().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(500.00)));
    // System.out.println(response.getInvoices().get(0).toString());
  }
  /*
      @Test
      public void updateInvoiceAttachmentByFileNameTest() throws IOException {
          System.out.println("@Test - updateInvoiceAttachmentByFileName");
          UUID invoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
          String fileName = "sample5.jpg";
          Attachments response = accountingApi.updateInvoiceAttachmentByFileName(invoiceID, fileName, body);

          assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("08085449-fda3-45f4-a685-ff44c8a29ee3"))));
          assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
          assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
          assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/Invoices/4074292c-09b3-456d-84e7-add864c6c39b/Attachments/HelloWorld.jpg")));
          assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
          assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
          //System.out.println(response.getAttachments().get(0).toString());
      }
  */
  @Test
  public void getInvoiceRemindersTest() throws IOException {
    System.out.println("@Test - getInvoiceReminders");
    InvoiceReminders response = accountingApi.getInvoiceReminders(accessToken, xeroTenantId);

    assertThat(response.getInvoiceReminders().get(0).getEnabled(), is(equalTo(false)));
    // System.out.println(response.getInvoiceReminders().get(0).toString());
  }
}
