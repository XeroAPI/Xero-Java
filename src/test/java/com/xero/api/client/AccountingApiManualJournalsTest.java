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

public class AccountingApiManualJournalsTest {

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
  public void createManualJournalsTest() throws IOException {
    System.out.println("@Test - createManualJournals");
    ManualJournals manualJournals = new ManualJournals();
    ManualJournals response =
        accountingApi.createManualJournals(accessToken, xeroTenantId, manualJournals, false);

    assertThat(response.getManualJournals().get(0).getNarration(), is(equalTo("Foo bar")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getLineAmount(),
        is(equalTo(100.0)));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getLineAmount().toString(),
        is(equalTo("100.0")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getAccountCode(),
        is(equalTo("400")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getDescription(),
        is(equalTo("Hello there")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getTaxType(),
        is(equalTo("NONE")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getIsBlank(),
        is(equalTo(false)));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getLineAmount(),
        is(equalTo(-100.0)));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getLineAmount().toString(),
        is(equalTo("-100.0")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getAccountCode(),
        is(equalTo("400")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getDescription(),
        is(equalTo("Goodbye")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getTaxType(),
        is(equalTo("NONE")));
    assertThat(
        response
            .getManualJournals()
            .get(0)
            .getJournalLines()
            .get(1)
            .getTracking()
            .get(0)
            .getTrackingCategoryID(),
        is(equalTo(UUID.fromString("6a68adde-f210-4465-b0a9-0d8cc6f50762"))));
    assertThat(
        response
            .getManualJournals()
            .get(0)
            .getJournalLines()
            .get(1)
            .getTracking()
            .get(0)
            .getTrackingOptionID(),
        is(equalTo(UUID.fromString("dc54c220-0140-495a-b925-3246adc0075f"))));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getTracking().get(0).getName(),
        is(equalTo("Simpsons")));
    assertThat(
        response
            .getManualJournals()
            .get(0)
            .getJournalLines()
            .get(1)
            .getTracking()
            .get(0)
            .getOption(),
        is(equalTo("Bart")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getIsBlank(),
        is(equalTo(false)));
    assertThat(
        response.getManualJournals().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 14))));
    assertThat(
        response.getManualJournals().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
    assertThat(
        response.getManualJournals().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.ManualJournal.StatusEnum.DRAFT)));
    assertThat(response.getManualJournals().get(0).getShowOnCashBasisReports(), is(equalTo(true)));
    assertThat(
        response.getManualJournals().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-14T20:39:32.920Z"))));
    assertThat(
        response.getManualJournals().get(0).getManualJournalID(),
        is(equalTo(UUID.fromString("d312dd5e-a53e-46d1-9d51-c569ef4570b7"))));
    assertThat(
        response.getManualJournals().get(0).getWarnings().get(0).getMessage(),
        is(
            equalTo(
                "Account code '476' has been removed as it does not match a recognised account.")));
    assertThat(
        response.getManualJournals().get(0).getValidationErrors().get(0).getMessage(),
        is(equalTo("The total debits (100.00) must equal total credits (-10.00)")));
    // System.out.println(response.getManualJournals().get(0).toString());
  }

  @Test
  public void createManualJournalAttachmentByFileNameTest() throws IOException {
    System.out.println("@Test - createManualJournalAttachmentByFileName");
    UUID manualJournalID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    String fileName = "sample5.jpg";
    Attachments response =
        accountingApi.createManualJournalAttachmentByFileName(
            accessToken, xeroTenantId, manualJournalID, fileName, body);

    assertThat(
        response.getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("47ac97ff-d4f9-48a0-8a8e-49fae29129e7"))));
    assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("foobar.jpg")));
    assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
    assertThat(
        response.getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/ManualJournals/0b159335-606b-485f-b51b-97b3b32bad32/Attachments/foobar.jpg")));
    assertThat(
        response.getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal("2878711"))));
    assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
    // System.out.println(response.getAttachments().get(0).toString());
  }

  @Test
  public void getManualJournalTest() throws IOException {
    System.out.println("@Test - getManualJournal");
    UUID manualJournalID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    ManualJournals response =
        accountingApi.getManualJournal(accessToken, xeroTenantId, manualJournalID);

    assertThat(
        response.getManualJournals().get(0).getNarration(),
        is(equalTo("These aren't the droids you are looking for")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getLineAmount(),
        is(equalTo(100.0)));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getLineAmount().toString(),
        is(equalTo("100.0")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getAccountCode(),
        is(equalTo("429")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getDescription(),
        is(equalTo("These aren't the droids you are looking for")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getTaxType(),
        is(equalTo("NONE")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getIsBlank(),
        is(equalTo(false)));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getLineAmount(),
        is(equalTo(-100.0)));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getLineAmount().toString(),
        is(equalTo("-100.0")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getAccountCode(),
        is(equalTo("200")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getDescription(),
        is(equalTo("Yes the are")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getTaxType(),
        is(equalTo("NONE")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getIsBlank(),
        is(equalTo(false)));
    assertThat(
        response.getManualJournals().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 12))));
    assertThat(
        response.getManualJournals().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
    assertThat(
        response.getManualJournals().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.ManualJournal.StatusEnum.POSTED)));
    assertThat(response.getManualJournals().get(0).getShowOnCashBasisReports(), is(equalTo(true)));
    assertThat(response.getManualJournals().get(0).getHasAttachments(), is(equalTo(true)));
    assertThat(
        response.getManualJournals().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-12T02:19:48.147Z"))));
    assertThat(
        response.getManualJournals().get(0).getManualJournalID(),
        is(equalTo(UUID.fromString("99cb8353-ce73-4a5d-8e0d-8b0edf86cfc4"))));
    assertThat(
        response.getManualJournals().get(0).getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("166ca8f8-8bc6-4780-8466-a0e474d586ea"))));
    assertThat(
        response.getManualJournals().get(0).getAttachments().get(0).getFileName(),
        is(equalTo("giphy.gif")));
    assertThat(
        response.getManualJournals().get(0).getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/manualjournal/99cb8353-ce73-4a5d-8e0d-8b0edf86cfc4/Attachments/giphy.gif")));
    assertThat(
        response.getManualJournals().get(0).getAttachments().get(0).getMimeType(),
        is(equalTo("image/gif")));
    assertThat(
        response.getManualJournals().get(0).getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal("495727"))));
    // System.out.println(response.getManualJournals().get(0).toString());
  }

  @Test
  public void getManualJournalAttachmentsTest() throws IOException {
    System.out.println("@Test - getManualJournalAttachments");
    UUID manualJournalID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    Attachments response =
        accountingApi.getManualJournalAttachments(accessToken, xeroTenantId, manualJournalID);

    assertThat(
        response.getAttachments().get(0).getAttachmentID(),
        is(equalTo(UUID.fromString("16e86f32-3e25-4209-8662-c0dfd91b654c"))));
    assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
    assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
    assertThat(
        response.getAttachments().get(0).getUrl(),
        is(
            equalTo(
                "https://api.xero.com/api.xro/2.0/ManualJournals/0b159335-606b-485f-b51b-97b3b32bad32/Attachments/HelloWorld.jpg")));
    assertThat(
        response.getAttachments().get(0).getContentLength(),
        is(equalTo(new BigDecimal("2878711"))));
    assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
    // System.out.println(response.getAttachments().get(0).toString());
  }

  @Test
  public void getManualJournalsTest() throws IOException {
    System.out.println("@Test - getManualJournals");
    OffsetDateTime ifModifiedSince = null;
    String where = null;
    String order = null;
    Integer page = null;
    ManualJournals response =
        accountingApi.getManualJournals(
            accessToken, xeroTenantId, ifModifiedSince, where, order, page);

    assertThat(
        response.getManualJournals().get(0).getNarration(),
        is(equalTo("Reversal: These aren't the droids you are looking for")));
    assertThat(
        response.getManualJournals().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 21))));
    assertThat(
        response.getManualJournals().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
    assertThat(
        response.getManualJournals().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.ManualJournal.StatusEnum.POSTED)));
    assertThat(response.getManualJournals().get(0).getShowOnCashBasisReports(), is(equalTo(true)));
    assertThat(response.getManualJournals().get(0).getHasAttachments(), is(equalTo(false)));
    assertThat(
        response.getManualJournals().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-12T02:19:48.083Z"))));
    assertThat(
        response.getManualJournals().get(0).getManualJournalID(),
        is(equalTo(UUID.fromString("0b159335-606b-485f-b51b-97b3b32bad32"))));
    // System.out.println(response.getManualJournals().get(0).toString());
  }

  @Test
  public void updateManualJournalTest() throws IOException {
    System.out.println("@Test - updateManualJournal");
    UUID manualJournalID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    ManualJournals manualJournals = new ManualJournals();
    ManualJournals response =
        accountingApi.updateManualJournal(
            accessToken, xeroTenantId, manualJournalID, manualJournals);

    assertThat(response.getManualJournals().get(0).getNarration(), is(equalTo("Hello Xero")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getLineAmount(),
        is(equalTo(100.0)));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getLineAmount().toString(),
        is(equalTo("100.0")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getAccountCode(),
        is(equalTo("400")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getDescription(),
        is(equalTo("Hello there")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getTaxType(),
        is(equalTo("NONE")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(0).getIsBlank(),
        is(equalTo(false)));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getLineAmount(),
        is(equalTo(-100.0)));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getLineAmount().toString(),
        is(equalTo("-100.0")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getAccountCode(),
        is(equalTo("400")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getDescription(),
        is(equalTo("Goodbye")));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getTaxType(),
        is(equalTo("NONE")));
    assertThat(response.getManualJournals().get(0).getHasAttachments(), is(equalTo(false)));
    assertThat(
        response.getManualJournals().get(0).getJournalLines().get(1).getIsBlank(),
        is(equalTo(false)));
    assertThat(
        response.getManualJournals().get(0).getDateAsDate(),
        is(equalTo(LocalDate.of(2019, 03, 11))));
    assertThat(
        response.getManualJournals().get(0).getLineAmountTypes(),
        is(equalTo(com.xero.models.accounting.LineAmountTypes.NOTAX)));
    assertThat(
        response.getManualJournals().get(0).getStatus(),
        is(equalTo(com.xero.models.accounting.ManualJournal.StatusEnum.DRAFT)));
    assertThat(response.getManualJournals().get(0).getShowOnCashBasisReports(), is(equalTo(true)));
    assertThat(
        response.getManualJournals().get(0).getUpdatedDateUTCAsDate(),
        is(equalTo(OffsetDateTime.parse("2019-03-12T02:28:56.820Z"))));
    assertThat(
        response.getManualJournals().get(0).getManualJournalID(),
        is(equalTo(UUID.fromString("07eac261-78ef-47a0-a0eb-a57b74137877"))));
    // System.out.println(response.getManualJournals().get(0).toString());
  }
  /*
  @Test
  public void updateManualJournalAttachmentByFileNameTest() throws IOException {
      System.out.println("@Test - updateManualJournalAttachmentByFileName");
      UUID manualJournalID = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
      String fileName = "sample5.jpg";
      Attachments response = accountingApi.updateManualJournalAttachmentByFileName(manualJournalID, fileName, body);

      assertThat(response.getAttachments().get(0).getAttachmentID(), is(equalTo(UUID.fromString("16e86f32-3e25-4209-8662-c0dfd91b654c"))));
      assertThat(response.getAttachments().get(0).getFileName(), is(equalTo("HelloWorld.jpg")));
      assertThat(response.getAttachments().get(0).getMimeType(), is(equalTo("image/jpg")));
      assertThat(response.getAttachments().get(0).getUrl(), is(equalTo("https://api.xero.com/api.xro/2.0/ManualJournals/0b159335-606b-485f-b51b-97b3b32bad32/Attachments/HelloWorld.jpg")));
      assertThat(response.getAttachments().get(0).getContentLength(), is(equalTo(new BigDecimal("2878711"))));
      assertThat(response.getAttachments().get(0).getIncludeOnline(), is(equalTo(null)));
      //System.out.println(response.getAttachments().get(0).toString());
  }
  */
}
