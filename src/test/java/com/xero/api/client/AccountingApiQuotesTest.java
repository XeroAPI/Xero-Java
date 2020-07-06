package com.xero.api.client;


import org.junit.Before;
import org.junit.Test;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.xero.api.ApiClient;
import com.xero.models.accounting.*;



import org.threeten.bp.*;

import java.util.UUID;



public class AccountingApiQuotesTest {

  ApiClient defaultClient;
  AccountingApi accountingApi;
  String accessToken;
  String xeroTenantId;

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
  public void testGetQuotes() throws Exception {
    System.out.println("@Test - getQuotes");

    OffsetDateTime ifModifiedSince = null;
    String order = null;
    LocalDate dateFrom = null;
    LocalDate dateTo = null;
    LocalDate expiryDateFrom = null;
    LocalDate expiryDateTo = null;
    UUID contactID = null;
    String status = null;
    Integer page = null;

    Quotes quotes =
        accountingApi.getQuotes(
            accessToken,
            xeroTenantId,
            ifModifiedSince,
            dateFrom,
            dateTo,
            expiryDateFrom,
            expiryDateTo,
            contactID,
            status,
            page,
            order);

    assert (quotes.getQuotes().size() == 1);
    assertThat(
        quotes.getQuotes().get(0).getQuoteID(),
        is(equalTo(UUID.fromString("be59294f-2a9c-4cee-8c64-0f0ddbc1883a"))));
    assertThat(quotes.getQuotes().get(0).getQuoteNumber(), is(equalTo("QU-0001")));
    assertThat(quotes.getQuotes().get(0).getReference(), is(equalTo("REF-123")));
    assertThat(
        quotes.getQuotes().get(0).getTerms(), is(equalTo("Not valid after the expiry date")));
    assertThat(
        quotes.getQuotes().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("060816db-0ed7-44de-ab58-8fee9316fcd5"))));
    assertThat(quotes.getQuotes().get(0).getContact().getName(), is(equalTo("Adam")));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("ccf5e45c-73b6-4659-83e8-520f4c6126fd"))));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Fish out of Water")));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(19.95)));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getItemCode(), is(equalTo("BOOK")));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("200")));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getTaxType(), is(equalTo("OUTPUT2")));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(2.69)));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(17.96)));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getDiscountRate(), is(equalTo(10.0)));
    assertThat(
        quotes
            .getQuotes()
            .get(0)
            .getLineItems()
            .get(0)
            .getTracking()
            .get(0)
            .getTrackingCategoryID(),
        is(equalTo(UUID.fromString("351953c4-8127-4009-88c3-f9cd8c9cbe9f"))));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getTracking().get(0).getTrackingOptionID(),
        is(equalTo(UUID.fromString("ce205173-7387-4651-9726-2cf4c5405ba2"))));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getTracking().get(0).getName(),
        is(equalTo("Region")));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getTracking().get(0).getOption(),
        is(equalTo("Eastside")));
    assertThat(quotes.getQuotes().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 10, 24))));
    assertThat(
        quotes.getQuotes().get(0).getExpiryDateAsDate(), is(equalTo(LocalDate.of(2019, 10, 25))));
    assertThat(
        quotes.getQuotes().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.AUD)));
    assertThat(quotes.getQuotes().get(0).getCurrencyRate(), is(equalTo(0.937053)));
    assertThat(quotes.getQuotes().get(0).getSubTotal(), is(equalTo(17.96)));
    assertThat(quotes.getQuotes().get(0).getTotalTax(), is(equalTo(2.69)));
    assertThat(quotes.getQuotes().get(0).getTotal(), is(equalTo(20.65)));
    assertThat(quotes.getQuotes().get(0).getTotalDiscount(), is(equalTo(1.99)));
    assertThat(quotes.getQuotes().get(0).getTitle(), is(equalTo("Your Quote")));
    assertThat(quotes.getQuotes().get(0).getSummary(), is(equalTo("Please buy this")));
    assertThat(
        quotes.getQuotes().get(0).getBrandingThemeID(),
        is(equalTo(UUID.fromString("4c82c365-35cb-467f-bb11-dce1f2f2f67c"))));
    assertThat(
        quotes.getQuotes().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-10-23T22:22:53.890Z"))));
    assertThat(
        quotes.getQuotes().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.QuoteStatusCodes.ACCEPTED)));
    assertThat(
        quotes.getQuotes().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.QuoteLineAmountTypes.EXCLUSIVE)));

    // System.out.println(quotes.toString());
  }

  @Test
  public void testGetQuote() throws Exception {
    System.out.println("@Test - getQuote");

    UUID quoteID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
    Quotes quotes = accountingApi.getQuote(accessToken, xeroTenantId, quoteID);

    assert (quotes.getQuotes().size() == 1);
    assertThat(
        quotes.getQuotes().get(0).getQuoteID(),
        is(equalTo(UUID.fromString("1f90e77a-7b88-4462-874f-1aa675be8fef"))));
    assertThat(quotes.getQuotes().get(0).getQuoteNumber(), is(equalTo("QU-0007")));
    assertThat(quotes.getQuotes().get(0).getReference(), is(equalTo("MyQuote")));
    assertThat(quotes.getQuotes().get(0).getTerms(), is(equalTo("These are my terms")));
    assertThat(
        quotes.getQuotes().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("4bc3ecb2-8e2a-4267-a171-0e0ce7e5ac2a"))));
    assertThat(quotes.getQuotes().get(0).getContact().getName(), is(equalTo("ABC Limited")));
    assertThat(quotes.getQuotes().get(0).getContact().getFirstName(), is(equalTo("John")));
    assertThat(quotes.getQuotes().get(0).getContact().getLastName(), is(equalTo("Smith")));
    assertThat(
        quotes.getQuotes().get(0).getContact().getEmailAddress(),
        is(equalTo("john.smith@gmail.com")));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("09b47d9f-f78d-4bab-b226-957f55bfb1b5"))));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Half day training - Microsoft Office")));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(500.0)));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getItemCode(), is(equalTo("Train-MS")));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("400")));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getTaxType(), is(equalTo("NONE")));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(0.0)));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(500.0)));
    assertThat(
        quotes
            .getQuotes()
            .get(0)
            .getLineItems()
            .get(0)
            .getTracking()
            .get(0)
            .getTrackingCategoryID(),
        is(equalTo(UUID.fromString("9bd3f506-6d91-4625-81f0-0f9147f099f4"))));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getTracking().get(0).getTrackingOptionID(),
        is(equalTo(UUID.fromString("d30e2a0d-ae6f-4806-88ca-d8ebdba2af73"))));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getTracking().get(0).getName(),
        is(equalTo("Avengers")));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getTracking().get(0).getOption(),
        is(equalTo("IronMan")));
    assertThat(quotes.getQuotes().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2020, 02, 1))));
    assertThat(
        quotes.getQuotes().get(0).getExpiryDateAsDate(), is(equalTo(LocalDate.of(2020, 02, 15))));
    assertThat(
        quotes.getQuotes().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(quotes.getQuotes().get(0).getCurrencyRate(), is(equalTo(1.54715)));
    assertThat(quotes.getQuotes().get(0).getSubTotal(), is(equalTo(500.0)));
    assertThat(quotes.getQuotes().get(0).getTotalTax(), is(equalTo(0.0)));
    assertThat(quotes.getQuotes().get(0).getTotal(), is(equalTo(500.0)));
    assertThat(quotes.getQuotes().get(0).getTotalDiscount(), is(equalTo(0.0)));
    assertThat(
        quotes.getQuotes().get(0).getBrandingThemeID(),
        is(equalTo(UUID.fromString("324587a9-7eed-46c0-ad64-fa941a1b5b3e"))));
    assertThat(
        quotes.getQuotes().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2020-02-02T01:42:37.040Z"))));
    assertThat(
        quotes.getQuotes().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.QuoteStatusCodes.DRAFT)));
    assertThat(
        quotes.getQuotes().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.QuoteLineAmountTypes.EXCLUSIVE)));
    // System.out.println(quotes.toString());
  }

  @Test
  public void testCreateQuote() throws Exception {
    System.out.println("@Test - createQuote");

    Quotes newQuote = new Quotes();
    Quotes quotes = accountingApi.createQuotes(accessToken, xeroTenantId, newQuote, false);

    assert (quotes.getQuotes().size() == 1);
    assertThat(
        quotes.getQuotes().get(0).getQuoteID(),
        is(equalTo(UUID.fromString("60031d53-6488-4321-9cbd-c1db6dbf9ba4"))));
    assertThat(quotes.getQuotes().get(0).getQuoteNumber(), is(equalTo("QU-0008")));
    assertThat(
        quotes.getQuotes().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("6a65f055-b0e0-471a-a933-d1ffdd89393f"))));
    assertThat(quotes.getQuotes().get(0).getContact().getName(), is(equalTo("John Smith-82160")));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("26995857-0eea-45fb-b46c-f8ea896ec46e"))));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getDescription(), is(equalTo("Foobar")));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(20.0)));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("12775")));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(0.0)));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(20.0)));
    assertThat(quotes.getQuotes().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2020, 02, 1))));
    assertThat(
        quotes.getQuotes().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.USD)));
    assertThat(quotes.getQuotes().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(quotes.getQuotes().get(0).getSubTotal(), is(equalTo(20.0)));
    assertThat(quotes.getQuotes().get(0).getTotalTax(), is(equalTo(0.0)));
    assertThat(quotes.getQuotes().get(0).getTotal(), is(equalTo(20.0)));
    assertThat(
        quotes.getQuotes().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2020-02-02T01:43:02.913Z"))));
    assertThat(
        quotes.getQuotes().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.QuoteStatusCodes.DRAFT)));
    assertThat(
        quotes.getQuotes().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.QuoteLineAmountTypes.EXCLUSIVE)));

    // System.out.println(quotes.toString());
  }

  @Test
  public void testUpdateQuote() throws Exception {
    System.out.println("@Test - updateQuote");

    UUID quoteID = UUID.fromString("297c2dc5-cc47-4afd-8ec8-74990b8761e9");
    Quotes upQuote = new Quotes();
    Quotes quotes = accountingApi.updateQuote(accessToken, xeroTenantId, quoteID, upQuote);

    assert (quotes.getQuotes().size() == 1);
    assertThat(
        quotes.getQuotes().get(0).getQuoteID(),
        is(equalTo(UUID.fromString("8ce6b14c-ef87-4f45-93f0-853137c6d0e1"))));
    assertThat(quotes.getQuotes().get(0).getQuoteNumber(), is(equalTo("QU-0008")));
    assertThat(quotes.getQuotes().get(0).getReference(), is(equalTo("I am an update")));
    assertThat(
        quotes.getQuotes().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("8ed7dd03-4e6a-4078-a807-c5309abfec52"))));
    assertThat(
        quotes.getQuotes().get(0).getContact().getName(), is(equalTo("Orlena Greenville 35")));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("be69f44e-9c72-4fcd-9152-0174867cce49"))));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getDescription(), is(equalTo("Foobar")));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(20.0)));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("12775")));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(0.0)));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(20.0)));
    assertThat(quotes.getQuotes().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2020, 2, 1))));
    assertThat(
        quotes.getQuotes().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.USD)));
    assertThat(quotes.getQuotes().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(quotes.getQuotes().get(0).getSubTotal(), is(equalTo(20.0)));
    assertThat(quotes.getQuotes().get(0).getTotalTax(), is(equalTo(0.0)));
    assertThat(quotes.getQuotes().get(0).getTotal(), is(equalTo(20.0)));
    assertThat(
        quotes.getQuotes().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2020-02-02T01:07:24.360Z"))));
    assertThat(
        quotes.getQuotes().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.QuoteStatusCodes.DRAFT)));
    assertThat(
        quotes.getQuotes().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.QuoteLineAmountTypes.EXCLUSIVE)));

    // System.out.println(quotes.toString());
  }

  @Test
  public void testUpdateOrCreateQuote() throws Exception {
    System.out.println("@Test - updateOrCreateQuote");

    Quotes newQuote = new Quotes();
    Quotes quotes = accountingApi.updateOrCreateQuotes(accessToken, xeroTenantId, newQuote, false);

    assert (quotes.getQuotes().size() == 1);
    assertThat(
        quotes.getQuotes().get(0).getQuoteID(),
        is(equalTo(UUID.fromString("fd53e0b7-4d24-4c20-be85-043a62ea5847"))));
    assertThat(quotes.getQuotes().get(0).getQuoteNumber(), is(equalTo("QU-0009")));
    assertThat(
        quotes.getQuotes().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("6a65f055-b0e0-471a-a933-d1ffdd89393f"))));
    assertThat(quotes.getQuotes().get(0).getContact().getName(), is(equalTo("John Smith-82160")));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("898c7fd6-0d94-4ac0-ace8-87e350a042de"))));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getDescription(), is(equalTo("Foobar")));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getQuantity(), is(equalTo(1.0)));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(20.0)));
    assertThat(
        quotes.getQuotes().get(0).getLineItems().get(0).getAccountCode(), is(equalTo("12775")));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(0.0)));
    assertThat(quotes.getQuotes().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(20.0)));
    assertThat(quotes.getQuotes().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2020, 02, 01))));
    assertThat(
        quotes.getQuotes().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.USD)));
    assertThat(quotes.getQuotes().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(quotes.getQuotes().get(0).getSubTotal(), is(equalTo(20.0)));
    assertThat(quotes.getQuotes().get(0).getTotalTax(), is(equalTo(0.0)));
    assertThat(quotes.getQuotes().get(0).getTotal(), is(equalTo(20.0)));
    assertThat(
        quotes.getQuotes().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2020-02-02T01:43:03.467Z"))));
    assertThat(
        quotes.getQuotes().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.QuoteStatusCodes.DRAFT)));
    assertThat(
        quotes.getQuotes().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.QuoteLineAmountTypes.EXCLUSIVE)));

    // System.out.println(quotes.toString());
  }
}
