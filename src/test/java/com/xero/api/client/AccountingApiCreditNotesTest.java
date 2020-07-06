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
import java.math.BigDecimal;

public class AccountingApiCreditNotesTest {

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
  public void createCreditNoteTest() throws IOException {
    System.out.println("@Test - createCreditNote");
    Boolean summarizeErrors = false;
    Integer unitdp = null;
    CreditNotes creditNotes = new CreditNotes();
    CreditNotes response =
        accountingApi.createCreditNotes(
            accessToken, xeroTenantId, creditNotes, summarizeErrors, unitdp);

    assertThat(
        response.getCreditNotes().get(0).getType(),
        is(equalTo(com.xero.models.accounting.CreditNote.TypeEnum.ACCPAYCREDIT)));
    assertThat(
        response.getCreditNotes().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.CreditNote.StatusEnum.DRAFT)));
    assertThat(response.getCreditNotes().get(0).getSubTotal(), is(equalTo(40.0)));
    assertThat(response.getCreditNotes().get(0).getSubTotal().toString(), is(equalTo("40.0")));
    assertThat(response.getCreditNotes().get(0).getTotalTax(), is(equalTo(6.0)));
    assertThat(response.getCreditNotes().get(0).getTotalTax().toString(), is(equalTo("6.0")));
    assertThat(response.getCreditNotes().get(0).getTotal(), is(equalTo(46.0)));
    assertThat(response.getCreditNotes().get(0).getTotal().toString(), is(equalTo("46.0")));
    assertThat(
        response.getCreditNotes().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-05T19:05:02.650Z"))));
    assertThat(
        response.getCreditNotes().get(0).getCreditNoteID(),
        is(equalTo(UUID.fromString("f9256f04-5a99-4680-acb9-6b4639cc439a"))));
    assertThat(response.getCreditNotes().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getCreditNotes().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getCreditNotes().get(0).getRemainingCredit(), is(equalTo(46.0)));
    assertThat(
        response.getCreditNotes().get(0).getRemainingCredit().toString(), is(equalTo("46.0")));
    assertThat(
        response.getCreditNotes().get(0).getValidationErrors().get(0).getMessage(),
        is(equalTo("An existing Credit Note with the specified CreditNoteID could not be found")));
    assertThat(
        response.getCreditNotes().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getCreditNotes().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Foobar")));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getQuantity(), is(equalTo(2.0)));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(20.0)));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getTaxType(), is(equalTo("INPUT2")));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getAccountCode(),
        is(equalTo("400")));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(6.0)));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(40.0)));
    // System.out.println(response.getCreditNotes().get(0).toString());
  }

  @Test
  public void createCreditNoteAllocationTest() throws IOException {
    System.out.println("@Test - createCreditNoteAllocation");
    UUID creditNoteID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Allocations allocations = new Allocations();
    Allocations response =
        accountingApi.createCreditNoteAllocation(
            accessToken, xeroTenantId, creditNoteID, allocations, false);

    assertThat(response.getAllocations().get(0).getAmount(), is(equalTo(1.0)));
    assertThat(
        response.getAllocations().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 05))));
    // System.out.println(response.getAllocations().get(0).toString());
  }

  /*
      @Test
      public void createCreditNoteAttachmentByFileNameTest() throws IOException {
          System.out.println("@Test - createCreditNoteAttachmentByFileName");
          UUID creditNoteID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
          String fileName = "sample5.jpg";
          Attachments response = accountingApi.createCreditNoteAttachmentByFileName(accessToken,xeroTenantId,creditNoteID, fileName, false, body);

          assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("91bbae3f-5de5-4e3d-875f-8662f25897bd"))));
          assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("sample5.jpg")));
          assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
          assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/CreditNotes/249f15fa-f2a7-4acc-8769-0984103f2225/Attachments/sample5.jpg")));
          assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
          assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
          //System.out.println(response.getAttachments().get(0).toString());
      }
  */
  @Test
  public void getCreditNoteTest() throws IOException {
    System.out.println("@Test - getCreditNote");
    UUID creditNoteID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Integer unitdp = null;
    CreditNotes response =
        accountingApi.getCreditNote(accessToken, xeroTenantId, creditNoteID, unitdp);

    assertThat(
        response.getCreditNotes().get(0).getType(),
        is(equalTo(com.xero.models.accounting.CreditNote.TypeEnum.ACCRECCREDIT)));
    assertThat(
        response.getCreditNotes().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 05))));
    assertThat(
        response.getCreditNotes().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.CreditNote.StatusEnum.AUTHORISED)));
    assertThat(
        response.getCreditNotes().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(response.getCreditNotes().get(0).getSubTotal(), is(equalTo(30.0)));
    assertThat(response.getCreditNotes().get(0).getSubTotal().toString(), is(equalTo("30.0")));
    assertThat(response.getCreditNotes().get(0).getTotalTax(), is(equalTo(4.5)));
    assertThat(response.getCreditNotes().get(0).getTotalTax().toString(), is(equalTo("4.5")));
    assertThat(response.getCreditNotes().get(0).getTotal(), is(equalTo(34.5)));
    assertThat(response.getCreditNotes().get(0).getTotal().toString(), is(equalTo("34.5")));
    assertThat(
        response.getCreditNotes().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-05T18:59:06.157Z"))));
    assertThat(
        response.getCreditNotes().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getCreditNotes().get(0).getCreditNoteID(),
        is(equalTo(UUID.fromString("249f15fa-f2a7-4acc-8769-0984103f2225"))));
    assertThat(response.getCreditNotes().get(0).getCreditNoteNumber(), is(equalTo("CN-0005")));
    assertThat(response.getCreditNotes().get(0).getReference(), is(equalTo("US Tour")));
    assertThat(response.getCreditNotes().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getCreditNotes().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getCreditNotes().get(0).getRemainingCredit(), is(equalTo(32.5)));
    assertThat(
        response.getCreditNotes().get(0).getRemainingCredit().toString(), is(equalTo("32.5")));
    assertThat(response.getCreditNotes().get(0).getHasAttachments(), is(equalTo(true)));
    assertThat(
        response.getCreditNotes().get(0).getPayments().get(0).getReference(),
        is(equalTo("Too much")));
    assertThat(
        response.getCreditNotes().get(0).getPayments().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 14))));
    assertThat(
        response.getCreditNotes().get(0).getPayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getCreditNotes().get(0).getPayments().get(0).getAmount(), is(equalTo(2.0)));
    assertThat(
        response.getCreditNotes().get(0).getPayments().get(0).getPaymentID(),
        is(equalTo(UUID.fromString("6b037c9b-2e5d-4905-84d3-eabfb3438242"))));
    assertThat(
        response.getCreditNotes().get(0).getPayments().get(0).getHasAccount(), is(equalTo(false)));
    assertThat(
        response.getCreditNotes().get(0).getPayments().get(0).getHasValidationErrors(),
        is(equalTo(false)));
    // System.out.println(response.getCreditNotes().get(0).toString());
  }

  @Test
  public void getCreditNoteAttachmentsTest() throws IOException {
    System.out.println("@Test - getCreditNoteAttachments");
    UUID creditNoteID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Attachments response =
        accountingApi.getCreditNoteAttachments(accessToken, xeroTenantId, creditNoteID);

    assertThat(
        response.getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("b7eb1fc9-a0f9-4e8e-9373-6689f5350832"))));
    assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.png")));
    assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/png")));
    assertThat(
        response.getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/CreditNotes/249f15fa-f2a7-4acc-8769-0984103f2225/Attachments/HelloWorld.png")));
    assertThat(
        response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("76091"))));
    assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
    // System.out.println(response.getAttachments().get(0).toString());
  }

  @Test
  public void getCreditNotesTest() throws IOException {
    System.out.println("@Test - getCreditNotes");
    OffsetDateTime ifModifiedSince = null;
    String where = null;
    String order = null;
    Integer page = null;
    Integer unitdp = null;
    CreditNotes response =
        accountingApi.getCreditNotes(
            accessToken, xeroTenantId, ifModifiedSince, where, order, page, unitdp);

    assertThat(
        response.getCreditNotes().get(0).getType(),
        is(equalTo(com.xero.models.accounting.CreditNote.TypeEnum.ACCRECCREDIT)));
    assertThat(
        response.getCreditNotes().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 03, 05))));
    assertThat(
        response.getCreditNotes().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.CreditNote.StatusEnum.AUTHORISED)));
    assertThat(
        response.getCreditNotes().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(response.getCreditNotes().get(0).getSubTotal(), is(equalTo(30.0)));
    assertThat(response.getCreditNotes().get(0).getSubTotal().toString(), is(equalTo("30.0")));
    assertThat(response.getCreditNotes().get(0).getTotalTax(), is(equalTo(4.5)));
    assertThat(response.getCreditNotes().get(0).getTotalTax().toString(), is(equalTo("4.5")));
    assertThat(response.getCreditNotes().get(0).getTotal(), is(equalTo(34.5)));
    assertThat(response.getCreditNotes().get(0).getTotal().toString(), is(equalTo("34.5")));
    assertThat(
        response.getCreditNotes().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-05T18:59:06.157Z"))));
    assertThat(
        response.getCreditNotes().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getCreditNotes().get(0).getCreditNoteID(),
        is(equalTo(UUID.fromString("249f15fa-f2a7-4acc-8769-0984103f2225"))));
    assertThat(response.getCreditNotes().get(0).getCreditNoteNumber(), is(equalTo("CN-0005")));
    assertThat(response.getCreditNotes().get(0).getReference(), is(equalTo("US Tour")));
    assertThat(response.getCreditNotes().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getCreditNotes().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getCreditNotes().get(0).getRemainingCredit(), is(equalTo(32.5)));
    assertThat(
        response.getCreditNotes().get(0).getRemainingCredit().toString(), is(equalTo("32.5")));
    assertThat(response.getCreditNotes().get(0).getHasAttachments(), is(equalTo(true)));
    assertThat(
        response.getCreditNotes().get(0).getPayments().get(0).getReference(),
        is(equalTo("Too much")));
    assertThat(
        response.getCreditNotes().get(0).getPayments().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 14))));
    assertThat(
        response.getCreditNotes().get(0).getPayments().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getCreditNotes().get(0).getPayments().get(0).getAmount(), is(equalTo(2.0)));
    assertThat(
        response.getCreditNotes().get(0).getPayments().get(0).getPaymentID(),
        is(equalTo(UUID.fromString("6b037c9b-2e5d-4905-84d3-eabfb3438242"))));
    assertThat(
        response.getCreditNotes().get(0).getPayments().get(0).getHasAccount(), is(equalTo(false)));
    assertThat(
        response.getCreditNotes().get(0).getPayments().get(0).getHasValidationErrors(),
        is(equalTo(false)));
    // System.out.println(response.getHistoryRecords().get(0).toString());
  }

  @Test
  public void updateCreditNoteTest() throws IOException {
    System.out.println("@Test - updateCreditNote");
    UUID creditNoteID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Integer unitdp = null;
    CreditNotes creditNotes = new CreditNotes();
    CreditNotes response =
        accountingApi.updateCreditNote(
            accessToken, xeroTenantId, creditNoteID, creditNotes, unitdp);

    assertThat(
        response.getCreditNotes().get(0).getType(),
        is(equalTo(com.xero.models.accounting.CreditNote.TypeEnum.ACCPAYCREDIT)));
    assertThat(
        response.getCreditNotes().get(0).getDateAsDate(), is(equalTo(LocalDate.of(2019, 01, 05))));
    assertThat(
        response.getCreditNotes().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.CreditNote.StatusEnum.AUTHORISED)));
    assertThat(
        response.getCreditNotes().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Foobar")));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getQuantity(), is(equalTo(2.0)));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getUnitAmount(), is(equalTo(20.0)));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getTaxType(), is(equalTo("INPUT2")));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getAccountCode(),
        is(equalTo("400")));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getTaxAmount(), is(equalTo(6.0)));
    assertThat(
        response.getCreditNotes().get(0).getLineItems().get(0).getLineAmount(), is(equalTo(40.0)));
    assertThat(response.getCreditNotes().get(0).getSubTotal(), is(equalTo(40.0)));
    assertThat(response.getCreditNotes().get(0).getSubTotal().toString(), is(equalTo("40.0")));
    assertThat(response.getCreditNotes().get(0).getTotalTax(), is(equalTo(6.0)));
    assertThat(response.getCreditNotes().get(0).getTotalTax().toString(), is(equalTo("6.0")));
    assertThat(response.getCreditNotes().get(0).getTotal(), is(equalTo(46.0)));
    assertThat(response.getCreditNotes().get(0).getTotal().toString(), is(equalTo("46.0")));
    assertThat(
        response.getCreditNotes().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-05T19:05:04.223Z"))));
    assertThat(
        response.getCreditNotes().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getCreditNotes().get(0).getCreditNoteID(),
        is(equalTo(UUID.fromString("f9256f04-5a99-4680-acb9-6b4639cc439a"))));
    assertThat(response.getCreditNotes().get(0).getReference(), is(equalTo("HelloWorld")));
    assertThat(response.getCreditNotes().get(0).getCurrencyRate(), is(equalTo(1.0)));
    assertThat(response.getCreditNotes().get(0).getCurrencyRate().toString(), is(equalTo("1.0")));
    assertThat(response.getCreditNotes().get(0).getRemainingCredit(), is(equalTo(46.0)));
    assertThat(
        response.getCreditNotes().get(0).getRemainingCredit().toString(), is(equalTo("46.0")));
    // System.out.println(response.getCreditNotes().get(0).toString());
  }
  /*
  @Test
  public void updateCreditNoteAttachmentByFileNameTest() throws IOException {
      System.out.println("@Test - updateCreditNoteAttachmentByFileName");
      UUID creditNoteID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
      String fileName = "sample5.jpg";

      Attachments response = accountingApi.updateCreditNoteAttachmentByFileName(creditNoteID, fileName, body);

      assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("103e49f1-e47c-4b4d-b5e8-77d9d00fa70a"))));
      assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
      assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
      assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/CreditNotes/249f15fa-f2a7-4acc-8769-0984103f2225/Attachments/HelloWorld.jpg")));
      assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
      assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
      //System.out.println(response.getAttachments().get(0).toString());
  }
  */
}
