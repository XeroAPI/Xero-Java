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

public class AccountingApiRepeatingInvoicesTest {

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
  public void createRepeatingInvoiceAttachmentByFileNameTest() throws IOException {
    System.out.println("@Test - createRepeatingInvoiceAttachmentByFileName");
    UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    String fileName = "sample5.jpg";
    Attachments response =
        accountingApi.createRepeatingInvoiceAttachmentByFileName(
            accessToken, xeroTenantId, repeatingInvoiceID, fileName, body);

    assertThat(
        response.getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("e078e56c-9a2b-4f6c-a1fa-5d19b0dab611"))));
    assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("foobar.jpg")));
    assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
    assertThat(
        response.getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/RepeatingInvoices/428c0d75-909f-4b04-8403-a48dc27283b0/Attachments/foobar.jpg")));
    assertThat(
        response.getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal("2878711"))));
    assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
    // System.out.println(response.getAttachments().get(0).toString());
  }

  @Test
  public void getRepeatingInvoiceTest() throws IOException {
    System.out.println("@Test - getRepeatingInvoice");
    UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    RepeatingInvoices response =
        accountingApi.getRepeatingInvoice(accessToken, xeroTenantId, repeatingInvoiceID);

    assertThat(
        response.getRepeatingInvoices().get(0).getType(),
        is(equalTo(com.xero.models.accounting.RepeatingInvoice.TypeEnum.ACCREC)));
    assertThat(
        response.getRepeatingInvoices().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
    assertThat(response.getRepeatingInvoices().get(0).getSchedule().getPeriod(), is(equalTo(1)));
    assertThat(
        response.getRepeatingInvoices().get(0).getSchedule().getUnit(),
        is(equalTo(com.xero.models.accounting.Schedule.UnitEnum.MONTHLY)));
    assertThat(response.getRepeatingInvoices().get(0).getSchedule().getDueDate(), is(equalTo(10)));
    assertThat(
        response.getRepeatingInvoices().get(0).getSchedule().getDueDateType(),
        is(equalTo(com.xero.models.accounting.Schedule.DueDateTypeEnum.OFFOLLOWINGMONTH)));
    assertThat(
        response.getRepeatingInvoices().get(0).getSchedule().getStartDateAsDate(),
        is(equalTo(LocalDate.of(2019, 04, 15))));
    assertThat(
        response.getRepeatingInvoices().get(0).getSchedule().getNextScheduledDateAsDate(),
        is(equalTo(LocalDate.of(2019, 04, 15))));
    assertThat(
        response.getRepeatingInvoices().get(0).getSchedule().getEndDateAsDate(),
        is(equalTo(LocalDate.of(2019, 9, 30))));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("13a8353c-d2af-4d5b-920c-438449f08900"))));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Guitars Fender Strat")));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getQuantity(),
        is(equalTo(1.0)));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getUnitAmount(),
        is(equalTo(5000.0)));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getTaxType(),
        is(equalTo("OUTPUT2")));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getAccountCode(),
        is(equalTo("200")));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getTaxAmount(),
        is(equalTo(750.0)));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getLineAmount(),
        is(equalTo(5000.0)));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(response.getRepeatingInvoices().get(0).getReference(), is(equalTo("[Week]")));
    assertThat(
        response.getRepeatingInvoices().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getRepeatingInvoices().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.RepeatingInvoice.StatusEnum.AUTHORISED)));
    assertThat(response.getRepeatingInvoices().get(0).getSubTotal(), is(equalTo(5000.0)));
    assertThat(
        response.getRepeatingInvoices().get(0).getSubTotal().toString(), is(equalTo("5000.0")));
    assertThat(response.getRepeatingInvoices().get(0).getTotalTax(), is(equalTo(750.0)));
    assertThat(
        response.getRepeatingInvoices().get(0).getTotalTax().toString(), is(equalTo("750.0")));
    assertThat(response.getRepeatingInvoices().get(0).getTotal(), is(equalTo(5750.0)));
    assertThat(response.getRepeatingInvoices().get(0).getTotal().toString(), is(equalTo("5750.0")));
    assertThat(
        response.getRepeatingInvoices().get(0).getRepeatingInvoiceID(),
        is(equalTo(UUID.fromString("428c0d75-909f-4b04-8403-a48dc27283b0"))));
    assertThat(
        response.getRepeatingInvoices().get(0).getID(),
        is(equalTo(UUID.fromString("428c0d75-909f-4b04-8403-a48dc27283b0"))));
    assertThat(response.getRepeatingInvoices().get(0).getHasAttachments(), is(equalTo(true)));
    assertThat(
        response.getRepeatingInvoices().get(0).getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("2a488b0f-3966-4b6e-a7e1-b6d3286351f2"))));
    assertThat(
        response.getRepeatingInvoices().get(0).getAttachments().get(0).getFileName(),
        is(equalTo("HelloWorld.jpg")));
    assertThat(
        response.getRepeatingInvoices().get(0).getAttachments().get(0).getMimeType(),
        is(equalTo("image/jpg")));
    assertThat(
        response.getRepeatingInvoices().get(0).getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/Invoices/428c0d75-909f-4b04-8403-a48dc27283b0/Attachments/HelloWorld.jpg")));
    assertThat(
        response.getRepeatingInvoices().get(0).getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal("2878711"))));
    assertThat(
        response.getRepeatingInvoices().get(0).getAttachments().get(0).getIncludeOnline(),
        is(equalTo(null)));
    // System.out.println(response.getRepeatingInvoices().get(0).toString());
  }

  @Test
  public void getRepeatingInvoiceAttachmentsTest() throws IOException {
    System.out.println("@Test - getRepeatingInvoiceAttachments");
    UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Attachments response =
        accountingApi.getRepeatingInvoiceAttachments(accessToken, xeroTenantId, repeatingInvoiceID);

    assertThat(
        response.getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("2a488b0f-3966-4b6e-a7e1-b6d3286351f2"))));
    assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
    assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
    assertThat(
        response.getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/RepeatingInvoices/428c0d75-909f-4b04-8403-a48dc27283b0/Attachments/HelloWorld.jpg")));
    assertThat(
        response.getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal("2878711"))));
    assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
    // System.out.println(response.getAttachments().get(0).toString());
  }

  @Test
  public void getRepeatingInvoicesTest() throws IOException {
    System.out.println("@Test - getRepeatingInvoices");
    String where = null;
    String order = null;
    RepeatingInvoices response =
        accountingApi.getRepeatingInvoices(accessToken, xeroTenantId, where, order);

    assertThat(
        response.getRepeatingInvoices().get(0).getType(),
        is(equalTo(com.xero.models.accounting.RepeatingInvoice.TypeEnum.ACCREC)));
    assertThat(
        response.getRepeatingInvoices().get(0).getContact().getContactID(),
        is(equalTo(UUID.fromString("430fa14a-f945-44d3-9f97-5df5e28441b8"))));
    assertThat(response.getRepeatingInvoices().get(0).getSchedule().getPeriod(), is(equalTo(1)));
    assertThat(
        response.getRepeatingInvoices().get(0).getSchedule().getUnit(),
        is(equalTo(com.xero.models.accounting.Schedule.UnitEnum.MONTHLY)));
    assertThat(response.getRepeatingInvoices().get(0).getSchedule().getDueDate(), is(equalTo(10)));
    assertThat(
        response.getRepeatingInvoices().get(0).getSchedule().getDueDateType(),
        is(equalTo(com.xero.models.accounting.Schedule.DueDateTypeEnum.OFFOLLOWINGMONTH)));
    assertThat(
        response.getRepeatingInvoices().get(0).getSchedule().getStartDateAsDate(),
        is(equalTo(LocalDate.of(2019, 04, 15))));
    assertThat(
        response.getRepeatingInvoices().get(0).getSchedule().getNextScheduledDateAsDate(),
        is(equalTo(LocalDate.of(2019, 04, 15))));
    assertThat(
        response.getRepeatingInvoices().get(0).getSchedule().getEndDateAsDate(),
        is(equalTo(LocalDate.of(2019, 9, 30))));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getLineItemID(),
        is(equalTo(UUID.fromString("13a8353c-d2af-4d5b-920c-438449f08900"))));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getDescription(),
        is(equalTo("Guitars Fender Strat")));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getQuantity(),
        is(equalTo(1.0)));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getUnitAmount(),
        is(equalTo(5000.0)));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getTaxType(),
        is(equalTo("OUTPUT2")));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getAccountCode(),
        is(equalTo("200")));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getTaxAmount(),
        is(equalTo(750.0)));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineItems().get(0).getLineAmount(),
        is(equalTo(5000.0)));
    assertThat(
        response.getRepeatingInvoices().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.EXCLUSIVE)));
    assertThat(response.getRepeatingInvoices().get(0).getReference(), is(equalTo("[Week]")));
    assertThat(
        response.getRepeatingInvoices().get(0).getCurrencyCode(),
        is(equalTo(com.xero.models.accounting.CurrencyCode.NZD)));
    assertThat(
        response.getRepeatingInvoices().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.RepeatingInvoice.StatusEnum.AUTHORISED)));
    assertThat(response.getRepeatingInvoices().get(0).getSubTotal(), is(equalTo(5000.0)));
    assertThat(
        response.getRepeatingInvoices().get(0).getSubTotal().toString(), is(equalTo("5000.0")));
    assertThat(response.getRepeatingInvoices().get(0).getTotalTax(), is(equalTo(750.0)));
    assertThat(
        response.getRepeatingInvoices().get(0).getTotalTax().toString(), is(equalTo("750.0")));
    assertThat(response.getRepeatingInvoices().get(0).getTotal(), is(equalTo(5750.0)));
    assertThat(response.getRepeatingInvoices().get(0).getTotal().toString(), is(equalTo("5750.0")));
    assertThat(
        response.getRepeatingInvoices().get(0).getRepeatingInvoiceID(),
        is(equalTo(UUID.fromString("428c0d75-909f-4b04-8403-a48dc27283b0"))));
    assertThat(
        response.getRepeatingInvoices().get(0).getID(),
        is(equalTo(UUID.fromString("428c0d75-909f-4b04-8403-a48dc27283b0"))));
    assertThat(response.getRepeatingInvoices().get(0).getHasAttachments(), is(equalTo(true)));
    // System.out.println(response.getRepeatingInvoices().get(0).toString());
  }
  /*
  @Test
  public void updateRepeatingInvoiceAttachmentByFileNameTest() throws IOException {
      System.out.println("@Test - updateRepeatingInvoiceAttachmentByFileName");
      UUID repeatingInvoiceID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
      String fileName = "sample5.jpg";
      Attachments response = accountingApi.updateRepeatingInvoiceAttachmentByFileName(repeatingInvoiceID, fileName, body);

      assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("d086d5f4-9c3d-4edc-a87e-906248eeb652"))));
      assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
      assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
      assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/RepeatingInvoices/428c0d75-909f-4b04-8403-a48dc27283b0/Attachments/HelloWorld.jpg")));
      assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
      assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
      //System.out.println(response.getAttachments().get(0).toString());
  }
  */
}
