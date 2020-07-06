package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.accounting.Account;
import com.xero.models.accounting.Accounts;
import com.xero.models.accounting.Allocations;
import com.xero.models.accounting.Attachments;
import com.xero.models.accounting.BankTransactions;
import com.xero.models.accounting.BankTransfers;
import com.xero.models.accounting.BatchPayments;
import com.xero.models.accounting.BrandingThemes;
import com.xero.models.accounting.CISOrgSetting;
import com.xero.models.accounting.CISSettings;
import com.xero.models.accounting.ContactGroups;
import com.xero.models.accounting.Contacts;
import com.xero.models.accounting.CreditNotes;
import com.xero.models.accounting.Currencies;
import com.xero.models.accounting.Currency;
import com.xero.models.accounting.Employees;
import com.xero.models.accounting.ExpenseClaims;
import java.io.File;
import com.xero.models.accounting.HistoryRecords;
import com.xero.models.accounting.InvoiceReminders;
import com.xero.models.accounting.Invoices;
import com.xero.models.accounting.Items;
import com.xero.models.accounting.Journals;
import com.xero.models.accounting.LinkedTransaction;
import com.xero.models.accounting.LinkedTransactions;
import org.threeten.bp.LocalDate;
import com.xero.models.accounting.ManualJournals;
import org.threeten.bp.OffsetDateTime;
import com.xero.models.accounting.OnlineInvoices;
import com.xero.models.accounting.Organisations;
import com.xero.models.accounting.Overpayments;
import com.xero.models.accounting.Payment;
import com.xero.models.accounting.PaymentDelete;
import com.xero.models.accounting.PaymentService;
import com.xero.models.accounting.PaymentServices;
import com.xero.models.accounting.Payments;
import com.xero.models.accounting.Prepayments;
import com.xero.models.accounting.PurchaseOrders;
import com.xero.models.accounting.Quotes;
import com.xero.models.accounting.Receipts;
import com.xero.models.accounting.RepeatingInvoices;
import com.xero.models.accounting.ReportWithRows;
import com.xero.models.accounting.Reports;
import com.xero.models.accounting.RequestEmpty;
import com.xero.models.accounting.TaxRates;
import com.xero.models.accounting.TrackingCategories;
import com.xero.models.accounting.TrackingCategory;
import com.xero.models.accounting.TrackingOption;
import com.xero.models.accounting.TrackingOptions;
import java.util.UUID;
import com.xero.models.accounting.Users;
import com.xero.api.XeroApiExceptionHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.io.ByteArrayInputStream;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.apache.commons.io.IOUtils;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class AccountingApi {
  private ApiClient apiClient;
  private static AccountingApi instance = null;
  private String userAgent = "Default";
  private String version = "4.1.1";
  static final Logger logger = LoggerFactory.getLogger(AccountingApi.class);

  public AccountingApi() {
    this(new ApiClient());
  }

  public static AccountingApi getInstance(ApiClient apiClient) {
    if (instance == null) {
      instance = new AccountingApi(apiClient);
    }
    return instance;
  }

  public AccountingApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public String getUserAgent() {
    return this.userAgent + " [Xero-Java-" + this.version + "]";
  }

  /**
   * Allows you to create a new chart of accounts
   *
   * <p><b>200</b> - Success - created new Account and return response of type Accounts array with
   * new Account
   *
   * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param account Account object in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Accounts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Accounts createAccount(String accessToken, String xeroTenantId, Account account)
      throws IOException {
    try {
      TypeReference<Accounts> typeRef = new TypeReference<Accounts>() {};
      HttpResponse response = createAccountForHttpResponse(accessToken, xeroTenantId, account);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createAccount -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Accounts", object.getMessage());
        }
        handler.validationError("Accounts", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createAccountForHttpResponse(
      String accessToken, String xeroTenantId, Account account) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createAccount");
    } // verify the required parameter 'account' is set
    if (account == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'account' when calling createAccount");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createAccount");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Accounts");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(account);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create Attachment on Account
   *
   * <p><b>200</b> - Success - return response of type Attachments array of Attachment
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accountID Unique identifier for Account object
   * @param fileName Name of the attachment
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments createAccountAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID accountID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          createAccountAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, accountID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createAccountAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createAccountAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID accountID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createAccountAttachmentByFileName");
    } // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accountID' when calling"
              + " createAccountAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " createAccountAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling createAccountAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createAccountAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("AccountID", accountID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Accounts/{AccountID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to createa an Attachment on BankTransaction by Filename
   *
   * <p><b>200</b> - Success - return response of Attachments array of Attachment
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransactionID Xero generated unique identifier for a bank transaction
   * @param fileName The name of the file being attached
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments createBankTransactionAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID bankTransactionID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          createBankTransactionAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, bankTransactionID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createBankTransactionAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createBankTransactionAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID bankTransactionID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createBankTransactionAttachmentByFileName");
    } // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransactionID' when calling"
              + " createBankTransactionAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " createBankTransactionAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling"
              + " createBankTransactionAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createBankTransactionAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransactionID", bankTransactionID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/BankTransactions/{BankTransactionID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create history record for a bank transactions
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransactionID Xero generated unique identifier for a bank transaction
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createBankTransactionHistoryRecord(
      String accessToken,
      String xeroTenantId,
      UUID bankTransactionID,
      HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createBankTransactionHistoryRecordForHttpResponse(
              accessToken, xeroTenantId, bankTransactionID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createBankTransactionHistoryRecord -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createBankTransactionHistoryRecordForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID bankTransactionID,
      HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createBankTransactionHistoryRecord");
    } // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransactionID' when calling"
              + " createBankTransactionHistoryRecord");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling"
              + " createBankTransactionHistoryRecord");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createBankTransactionHistoryRecord");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransactionID", bankTransactionID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/BankTransactions/{BankTransactionID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create one or more spend or receive money transaction
   *
   * <p><b>200</b> - Success - return response of type BankTransactions array with new
   * BankTransaction
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransactions BankTransactions with an array of BankTransaction objects in body of
   *     request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return BankTransactions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public BankTransactions createBankTransactions(
      String accessToken,
      String xeroTenantId,
      BankTransactions bankTransactions,
      Boolean summarizeErrors,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<BankTransactions> typeRef = new TypeReference<BankTransactions>() {};
      HttpResponse response =
          createBankTransactionsForHttpResponse(
              accessToken, xeroTenantId, bankTransactions, summarizeErrors, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createBankTransactions -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("BankTransactions", object.getMessage());
        }
        handler.validationError("BankTransactions", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createBankTransactionsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      BankTransactions bankTransactions,
      Boolean summarizeErrors,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createBankTransactions");
    } // verify the required parameter 'bankTransactions' is set
    if (bankTransactions == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransactions' when calling createBankTransactions");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createBankTransactions");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/BankTransactions");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(bankTransactions);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a bank transfers
   *
   * <p><b>200</b> - Success - return response of BankTransfers array of one BankTransfer
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransfers BankTransfers with array of BankTransfer objects in request body
   * @param accessToken Authorization token for user set in header of each request
   * @return BankTransfers
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public BankTransfers createBankTransfer(
      String accessToken, String xeroTenantId, BankTransfers bankTransfers) throws IOException {
    try {
      TypeReference<BankTransfers> typeRef = new TypeReference<BankTransfers>() {};
      HttpResponse response =
          createBankTransferForHttpResponse(accessToken, xeroTenantId, bankTransfers);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createBankTransfer -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("BankTransfers", object.getMessage());
        }
        handler.validationError("BankTransfers", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createBankTransferForHttpResponse(
      String accessToken, String xeroTenantId, BankTransfers bankTransfers) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createBankTransfer");
    } // verify the required parameter 'bankTransfers' is set
    if (bankTransfers == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransfers' when calling createBankTransfer");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createBankTransfer");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/BankTransfers");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(bankTransfers);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * <b>200</b> - Success - return response of Attachments array of 0 to N Attachment for a Bank
   * Transfer
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransferID Xero generated unique identifier for a bank transfer
   * @param fileName The name of the file being attached to a Bank Transfer
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments createBankTransferAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID bankTransferID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          createBankTransferAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, bankTransferID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createBankTransferAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createBankTransferAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID bankTransferID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createBankTransferAttachmentByFileName");
    } // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransferID' when calling"
              + " createBankTransferAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " createBankTransferAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling"
              + " createBankTransferAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createBankTransferAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransferID", bankTransferID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/BankTransfers/{BankTransferID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * <b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransferID Xero generated unique identifier for a bank transfer
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createBankTransferHistoryRecord(
      String accessToken, String xeroTenantId, UUID bankTransferID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createBankTransferHistoryRecordForHttpResponse(
              accessToken, xeroTenantId, bankTransferID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createBankTransferHistoryRecord -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createBankTransferHistoryRecordForHttpResponse(
      String accessToken, String xeroTenantId, UUID bankTransferID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createBankTransferHistoryRecord");
    } // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransferID' when calling"
              + " createBankTransferHistoryRecord");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling"
              + " createBankTransferHistoryRecord");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createBankTransferHistoryRecord");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransferID", bankTransferID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/BankTransfers/{BankTransferID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Create one or many BatchPayments for invoices
   *
   * <p><b>200</b> - Success - return response of type BatchPayments array of BatchPayment objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param batchPayments BatchPayments with an array of Payments in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return BatchPayments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public BatchPayments createBatchPayment(
      String accessToken, String xeroTenantId, BatchPayments batchPayments, Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<BatchPayments> typeRef = new TypeReference<BatchPayments>() {};
      HttpResponse response =
          createBatchPaymentForHttpResponse(
              accessToken, xeroTenantId, batchPayments, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createBatchPayment -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("BatchPayments", object.getMessage());
        }
        handler.validationError("BatchPayments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createBatchPaymentForHttpResponse(
      String accessToken, String xeroTenantId, BatchPayments batchPayments, Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createBatchPayment");
    } // verify the required parameter 'batchPayments' is set
    if (batchPayments == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'batchPayments' when calling createBatchPayment");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createBatchPayment");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/BatchPayments");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(batchPayments);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a history record for a Batch Payment
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param batchPaymentID Unique identifier for BatchPayment
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createBatchPaymentHistoryRecord(
      String accessToken, String xeroTenantId, UUID batchPaymentID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createBatchPaymentHistoryRecordForHttpResponse(
              accessToken, xeroTenantId, batchPaymentID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createBatchPaymentHistoryRecord -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createBatchPaymentHistoryRecordForHttpResponse(
      String accessToken, String xeroTenantId, UUID batchPaymentID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createBatchPaymentHistoryRecord");
    } // verify the required parameter 'batchPaymentID' is set
    if (batchPaymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'batchPaymentID' when calling"
              + " createBatchPaymentHistoryRecord");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling"
              + " createBatchPaymentHistoryRecord");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createBatchPaymentHistoryRecord");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BatchPaymentID", batchPaymentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/BatchPayments/{BatchPaymentID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allow for the creation of new custom payment service for specified Branding Theme
   *
   * <p><b>200</b> - Success - return response of type PaymentServices array with newly created
   * PaymentService
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param brandingThemeID Unique identifier for a Branding Theme
   * @param paymentService PaymentService object in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return PaymentServices
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PaymentServices createBrandingThemePaymentServices(
      String accessToken, String xeroTenantId, UUID brandingThemeID, PaymentService paymentService)
      throws IOException {
    try {
      TypeReference<PaymentServices> typeRef = new TypeReference<PaymentServices>() {};
      HttpResponse response =
          createBrandingThemePaymentServicesForHttpResponse(
              accessToken, xeroTenantId, brandingThemeID, paymentService);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createBrandingThemePaymentServices -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("PaymentServices", object.getMessage());
        }
        handler.validationError("PaymentServices", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createBrandingThemePaymentServicesForHttpResponse(
      String accessToken, String xeroTenantId, UUID brandingThemeID, PaymentService paymentService)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createBrandingThemePaymentServices");
    } // verify the required parameter 'brandingThemeID' is set
    if (brandingThemeID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'brandingThemeID' when calling"
              + " createBrandingThemePaymentServices");
    } // verify the required parameter 'paymentService' is set
    if (paymentService == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'paymentService' when calling"
              + " createBrandingThemePaymentServices");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createBrandingThemePaymentServices");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BrandingThemeID", brandingThemeID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/BrandingThemes/{BrandingThemeID}/PaymentServices");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(paymentService);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * <b>200</b> - Success - return response of type Attachments array with an newly created
   * Attachment
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactID Unique identifier for a Contact
   * @param fileName Name for the file you are attaching
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments createContactAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID contactID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          createContactAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, contactID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createContactAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createContactAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createContactAttachmentByFileName");
    } // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactID' when calling"
              + " createContactAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " createContactAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling createContactAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createContactAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactID", contactID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Contacts/{ContactID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a contact group
   *
   * <p><b>200</b> - Success - return response of type Contact Groups array of newly created Contact
   * Group
   *
   * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactGroups ContactGroups with an array of names in request body
   * @param accessToken Authorization token for user set in header of each request
   * @return ContactGroups
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ContactGroups createContactGroup(
      String accessToken, String xeroTenantId, ContactGroups contactGroups) throws IOException {
    try {
      TypeReference<ContactGroups> typeRef = new TypeReference<ContactGroups>() {};
      HttpResponse response =
          createContactGroupForHttpResponse(accessToken, xeroTenantId, contactGroups);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createContactGroup -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("ContactGroups", object.getMessage());
        }
        handler.validationError("ContactGroups", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createContactGroupForHttpResponse(
      String accessToken, String xeroTenantId, ContactGroups contactGroups) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createContactGroup");
    } // verify the required parameter 'contactGroups' is set
    if (contactGroups == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactGroups' when calling createContactGroup");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createContactGroup");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/ContactGroups");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(contactGroups);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to add Contacts to a Contact Group
   *
   * <p><b>200</b> - Success - return response of type Contacts array of added Contacts
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactGroupID Unique identifier for a Contact Group
   * @param contacts Contacts with array of contacts specifiying the ContactID to be added to
   *     ContactGroup in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Contacts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Contacts createContactGroupContacts(
      String accessToken, String xeroTenantId, UUID contactGroupID, Contacts contacts)
      throws IOException {
    try {
      TypeReference<Contacts> typeRef = new TypeReference<Contacts>() {};
      HttpResponse response =
          createContactGroupContactsForHttpResponse(
              accessToken, xeroTenantId, contactGroupID, contacts);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createContactGroupContacts -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Contacts", object.getMessage());
        }
        handler.validationError("Contacts", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createContactGroupContactsForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactGroupID, Contacts contacts)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createContactGroupContacts");
    } // verify the required parameter 'contactGroupID' is set
    if (contactGroupID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactGroupID' when calling"
              + " createContactGroupContacts");
    } // verify the required parameter 'contacts' is set
    if (contacts == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contacts' when calling createContactGroupContacts");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createContactGroupContacts");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactGroupID", contactGroupID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/ContactGroups/{ContactGroupID}/Contacts");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(contacts);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an Contact
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactID Unique identifier for a Contact
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createContactHistory(
      String accessToken, String xeroTenantId, UUID contactID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createContactHistoryForHttpResponse(accessToken, xeroTenantId, contactID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createContactHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createContactHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createContactHistory");
    } // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactID' when calling createContactHistory");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling createContactHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createContactHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactID", contactID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Contacts/{ContactID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a multiple contacts (bulk) in a Xero organisation
   *
   * <p><b>200</b> - Success - return response of type Contacts array with newly created Contact
   *
   * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contacts Contacts with an array of Contact objects to create in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return Contacts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Contacts createContacts(
      String accessToken, String xeroTenantId, Contacts contacts, Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<Contacts> typeRef = new TypeReference<Contacts>() {};
      HttpResponse response =
          createContactsForHttpResponse(accessToken, xeroTenantId, contacts, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createContacts -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Contacts", object.getMessage());
        }
        handler.validationError("Contacts", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createContactsForHttpResponse(
      String accessToken, String xeroTenantId, Contacts contacts, Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createContacts");
    } // verify the required parameter 'contacts' is set
    if (contacts == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contacts' when calling createContacts");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createContacts");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Contacts");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(contacts);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create Allocation on CreditNote
   *
   * <p><b>200</b> - Success - return response of type Allocations array with newly created
   * Allocation for specific Credit Note
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNoteID Unique identifier for a Credit Note
   * @param allocations Allocations with array of Allocation object in body of request.
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return Allocations
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Allocations createCreditNoteAllocation(
      String accessToken,
      String xeroTenantId,
      UUID creditNoteID,
      Allocations allocations,
      Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<Allocations> typeRef = new TypeReference<Allocations>() {};
      HttpResponse response =
          createCreditNoteAllocationForHttpResponse(
              accessToken, xeroTenantId, creditNoteID, allocations, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createCreditNoteAllocation -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Allocations", object.getMessage());
        }
        handler.validationError("Allocations", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createCreditNoteAllocationForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID creditNoteID,
      Allocations allocations,
      Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createCreditNoteAllocation");
    } // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNoteID' when calling createCreditNoteAllocation");
    } // verify the required parameter 'allocations' is set
    if (allocations == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'allocations' when calling createCreditNoteAllocation");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createCreditNoteAllocation");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("CreditNoteID", creditNoteID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/CreditNotes/{CreditNoteID}/Allocations");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(allocations);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create Attachments on CreditNote by file name
   *
   * <p><b>200</b> - Success - return response of type Attachments array with newly created
   * Attachment for specific Credit Note
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNoteID Unique identifier for a Credit Note
   * @param fileName Name of the file you are attaching to Credit Note
   * @param body Byte array of file in body of request
   * @param includeOnline Allows an attachment to be seen by the end customer within their online
   *     invoice
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments createCreditNoteAttachmentByFileName(
      String accessToken,
      String xeroTenantId,
      UUID creditNoteID,
      String fileName,
      File body,
      Boolean includeOnline)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          createCreditNoteAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, creditNoteID, fileName, body, includeOnline);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createCreditNoteAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createCreditNoteAttachmentByFileNameForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID creditNoteID,
      String fileName,
      File body,
      Boolean includeOnline)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createCreditNoteAttachmentByFileName");
    } // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNoteID' when calling"
              + " createCreditNoteAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " createCreditNoteAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling"
              + " createCreditNoteAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createCreditNoteAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("CreditNoteID", creditNoteID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/CreditNotes/{CreditNoteID}/Attachments/{FileName}");
    if (includeOnline != null) {
      String key = "IncludeOnline";
      Object value = includeOnline;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an CreditNote
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNoteID Unique identifier for a Credit Note
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createCreditNoteHistory(
      String accessToken, String xeroTenantId, UUID creditNoteID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createCreditNoteHistoryForHttpResponse(
              accessToken, xeroTenantId, creditNoteID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createCreditNoteHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createCreditNoteHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID creditNoteID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createCreditNoteHistory");
    } // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNoteID' when calling createCreditNoteHistory");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling createCreditNoteHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createCreditNoteHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("CreditNoteID", creditNoteID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/CreditNotes/{CreditNoteID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a credit note
   *
   * <p><b>200</b> - Success - return response of type Credit Notes array of newly created
   * CreditNote
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNotes Credit Notes with array of CreditNote object in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return CreditNotes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public CreditNotes createCreditNotes(
      String accessToken,
      String xeroTenantId,
      CreditNotes creditNotes,
      Boolean summarizeErrors,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<CreditNotes> typeRef = new TypeReference<CreditNotes>() {};
      HttpResponse response =
          createCreditNotesForHttpResponse(
              accessToken, xeroTenantId, creditNotes, summarizeErrors, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createCreditNotes -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("CreditNotes", object.getMessage());
        }
        handler.validationError("CreditNotes", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createCreditNotesForHttpResponse(
      String accessToken,
      String xeroTenantId,
      CreditNotes creditNotes,
      Boolean summarizeErrors,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createCreditNotes");
    } // verify the required parameter 'creditNotes' is set
    if (creditNotes == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNotes' when calling createCreditNotes");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createCreditNotes");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/CreditNotes");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(creditNotes);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * <b>200</b> - Unsupported - return response incorrect exception, API is not able to create new
   * Currency
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param currency Currency obejct in the body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Currencies
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Currencies createCurrency(String accessToken, String xeroTenantId, Currency currency)
      throws IOException {
    try {
      TypeReference<Currencies> typeRef = new TypeReference<Currencies>() {};
      HttpResponse response = createCurrencyForHttpResponse(accessToken, xeroTenantId, currency);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createCurrency -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Currencies", object.getMessage());
        }
        handler.validationError("Currencies", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createCurrencyForHttpResponse(
      String accessToken, String xeroTenantId, Currency currency) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createCurrency");
    } // verify the required parameter 'currency' is set
    if (currency == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'currency' when calling createCurrency");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createCurrency");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Currencies");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(currency);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create new employees used in Xero payrun
   *
   * <p><b>200</b> - Success - return response of type Employees array with new Employee
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employees Employees with array of Employee object in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return Employees
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Employees createEmployees(
      String accessToken, String xeroTenantId, Employees employees, Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
      HttpResponse response =
          createEmployeesForHttpResponse(accessToken, xeroTenantId, employees, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createEmployees -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Employees", object.getMessage());
        }
        handler.validationError("Employees", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createEmployeesForHttpResponse(
      String accessToken, String xeroTenantId, Employees employees, Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createEmployees");
    } // verify the required parameter 'employees' is set
    if (employees == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employees' when calling createEmployees");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createEmployees");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Employees");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employees);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a history records of an ExpenseClaim
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param expenseClaimID Unique identifier for a ExpenseClaim
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createExpenseClaimHistory(
      String accessToken, String xeroTenantId, UUID expenseClaimID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createExpenseClaimHistoryForHttpResponse(
              accessToken, xeroTenantId, expenseClaimID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createExpenseClaimHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createExpenseClaimHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID expenseClaimID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createExpenseClaimHistory");
    } // verify the required parameter 'expenseClaimID' is set
    if (expenseClaimID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'expenseClaimID' when calling createExpenseClaimHistory");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling createExpenseClaimHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createExpenseClaimHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ExpenseClaimID", expenseClaimID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/ExpenseClaims/{ExpenseClaimID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve expense claims
   *
   * <p><b>200</b> - Success - return response of type ExpenseClaims array with newly created
   * ExpenseClaim
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param expenseClaims ExpenseClaims with array of ExpenseClaim object in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return ExpenseClaims
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ExpenseClaims createExpenseClaims(
      String accessToken, String xeroTenantId, ExpenseClaims expenseClaims) throws IOException {
    try {
      TypeReference<ExpenseClaims> typeRef = new TypeReference<ExpenseClaims>() {};
      HttpResponse response =
          createExpenseClaimsForHttpResponse(accessToken, xeroTenantId, expenseClaims);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createExpenseClaims -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("ExpenseClaims", object.getMessage());
        }
        handler.validationError("ExpenseClaims", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createExpenseClaimsForHttpResponse(
      String accessToken, String xeroTenantId, ExpenseClaims expenseClaims) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createExpenseClaims");
    } // verify the required parameter 'expenseClaims' is set
    if (expenseClaims == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'expenseClaims' when calling createExpenseClaims");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createExpenseClaims");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/ExpenseClaims");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(expenseClaims);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create an Attachment on invoices or purchase bills by it&#39;s filename
   *
   * <p><b>200</b> - Success - return response of type Attachments array with newly created
   * Attachment
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoiceID Unique identifier for an Invoice
   * @param fileName Name of the file you are attaching
   * @param body Byte array of file in body of request
   * @param includeOnline Allows an attachment to be seen by the end customer within their online
   *     invoice
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments createInvoiceAttachmentByFileName(
      String accessToken,
      String xeroTenantId,
      UUID invoiceID,
      String fileName,
      File body,
      Boolean includeOnline)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          createInvoiceAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, invoiceID, fileName, body, includeOnline);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createInvoiceAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createInvoiceAttachmentByFileNameForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID invoiceID,
      String fileName,
      File body,
      Boolean includeOnline)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createInvoiceAttachmentByFileName");
    } // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoiceID' when calling"
              + " createInvoiceAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " createInvoiceAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling createInvoiceAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createInvoiceAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("InvoiceID", invoiceID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Invoices/{InvoiceID}/Attachments/{FileName}");
    if (includeOnline != null) {
      String key = "IncludeOnline";
      Object value = includeOnline;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an invoice
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoiceID Unique identifier for an Invoice
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createInvoiceHistory(
      String accessToken, String xeroTenantId, UUID invoiceID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createInvoiceHistoryForHttpResponse(accessToken, xeroTenantId, invoiceID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createInvoiceHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createInvoiceHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID invoiceID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createInvoiceHistory");
    } // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoiceID' when calling createInvoiceHistory");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling createInvoiceHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createInvoiceHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("InvoiceID", invoiceID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Invoices/{InvoiceID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create one or more sales invoices or purchase bills
   *
   * <p><b>200</b> - Success - return response of type Invoices array with newly created Invoice
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoices Invoices with an array of invoice objects in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Invoices
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Invoices createInvoices(
      String accessToken,
      String xeroTenantId,
      Invoices invoices,
      Boolean summarizeErrors,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<Invoices> typeRef = new TypeReference<Invoices>() {};
      HttpResponse response =
          createInvoicesForHttpResponse(
              accessToken, xeroTenantId, invoices, summarizeErrors, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createInvoices -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Invoices", object.getMessage());
        }
        handler.validationError("Invoices", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createInvoicesForHttpResponse(
      String accessToken,
      String xeroTenantId,
      Invoices invoices,
      Boolean summarizeErrors,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createInvoices");
    } // verify the required parameter 'invoices' is set
    if (invoices == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoices' when calling createInvoices");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createInvoices");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Invoices");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(invoices);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a history record for items
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param itemID Unique identifier for an Item
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createItemHistory(
      String accessToken, String xeroTenantId, UUID itemID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createItemHistoryForHttpResponse(accessToken, xeroTenantId, itemID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createItemHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createItemHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID itemID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createItemHistory");
    } // verify the required parameter 'itemID' is set
    if (itemID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'itemID' when calling createItemHistory");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling createItemHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createItemHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ItemID", itemID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Items/{ItemID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create one or more items
   *
   * <p><b>200</b> - Success - return response of type Items array with newly created Item
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param items Items with an array of Item objects in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Items
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Items createItems(
      String accessToken, String xeroTenantId, Items items, Boolean summarizeErrors, Integer unitdp)
      throws IOException {
    try {
      TypeReference<Items> typeRef = new TypeReference<Items>() {};
      HttpResponse response =
          createItemsForHttpResponse(accessToken, xeroTenantId, items, summarizeErrors, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createItems -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Items", object.getMessage());
        }
        handler.validationError("Items", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createItemsForHttpResponse(
      String accessToken, String xeroTenantId, Items items, Boolean summarizeErrors, Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createItems");
    } // verify the required parameter 'items' is set
    if (items == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'items' when calling createItems");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createItems");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Items");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(items);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create linked transactions (billable expenses)
   *
   * <p><b>200</b> - Success - return response of type LinkedTransactions array with newly created
   * LinkedTransaction
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param linkedTransaction LinkedTransaction object in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return LinkedTransactions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public LinkedTransactions createLinkedTransaction(
      String accessToken, String xeroTenantId, LinkedTransaction linkedTransaction)
      throws IOException {
    try {
      TypeReference<LinkedTransactions> typeRef = new TypeReference<LinkedTransactions>() {};
      HttpResponse response =
          createLinkedTransactionForHttpResponse(accessToken, xeroTenantId, linkedTransaction);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createLinkedTransaction -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("LinkedTransactions", object.getMessage());
        }
        handler.validationError("LinkedTransactions", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createLinkedTransactionForHttpResponse(
      String accessToken, String xeroTenantId, LinkedTransaction linkedTransaction)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createLinkedTransaction");
    } // verify the required parameter 'linkedTransaction' is set
    if (linkedTransaction == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'linkedTransaction' when calling"
              + " createLinkedTransaction");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createLinkedTransaction");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/LinkedTransactions");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(linkedTransaction);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a specified Attachment on ManualJournal by file name
   *
   * <p><b>200</b> - Success - return response of type Attachments array with a newly created
   * Attachment for a ManualJournals
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param manualJournalID Unique identifier for a ManualJournal
   * @param fileName The name of the file being attached to a ManualJournal
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments createManualJournalAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID manualJournalID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          createManualJournalAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, manualJournalID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createManualJournalAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createManualJournalAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID manualJournalID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createManualJournalAttachmentByFileName");
    } // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'manualJournalID' when calling"
              + " createManualJournalAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " createManualJournalAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling"
              + " createManualJournalAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createManualJournalAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ManualJournalID", manualJournalID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/ManualJournals/{ManualJournalID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create one or more manual journals
   *
   * <p><b>200</b> - Success - return response of type ManualJournals array with newly created
   * ManualJournal
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param manualJournals ManualJournals array with ManualJournal object in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return ManualJournals
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ManualJournals createManualJournals(
      String accessToken,
      String xeroTenantId,
      ManualJournals manualJournals,
      Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<ManualJournals> typeRef = new TypeReference<ManualJournals>() {};
      HttpResponse response =
          createManualJournalsForHttpResponse(
              accessToken, xeroTenantId, manualJournals, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createManualJournals -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("ManualJournals", object.getMessage());
        }
        handler.validationError("ManualJournals", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createManualJournalsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      ManualJournals manualJournals,
      Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createManualJournals");
    } // verify the required parameter 'manualJournals' is set
    if (manualJournals == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'manualJournals' when calling createManualJournals");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createManualJournals");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/ManualJournals");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(manualJournals);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a single allocation for an overpayment
   *
   * <p><b>200</b> - Success - return response of type Allocations array with all Allocation for
   * Overpayments
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param overpaymentID Unique identifier for a Overpayment
   * @param allocations Allocations array with Allocation object in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return Allocations
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Allocations createOverpaymentAllocations(
      String accessToken,
      String xeroTenantId,
      UUID overpaymentID,
      Allocations allocations,
      Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<Allocations> typeRef = new TypeReference<Allocations>() {};
      HttpResponse response =
          createOverpaymentAllocationsForHttpResponse(
              accessToken, xeroTenantId, overpaymentID, allocations, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createOverpaymentAllocations -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Allocations", object.getMessage());
        }
        handler.validationError("Allocations", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createOverpaymentAllocationsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID overpaymentID,
      Allocations allocations,
      Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createOverpaymentAllocations");
    } // verify the required parameter 'overpaymentID' is set
    if (overpaymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'overpaymentID' when calling"
              + " createOverpaymentAllocations");
    } // verify the required parameter 'allocations' is set
    if (allocations == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'allocations' when calling createOverpaymentAllocations");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createOverpaymentAllocations");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("OverpaymentID", overpaymentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Overpayments/{OverpaymentID}/Allocations");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(allocations);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create history records of an Overpayment
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - A failed request due to validation error - API is not able to create
   * HistoryRecord for Overpayments
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param overpaymentID Unique identifier for a Overpayment
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createOverpaymentHistory(
      String accessToken, String xeroTenantId, UUID overpaymentID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createOverpaymentHistoryForHttpResponse(
              accessToken, xeroTenantId, overpaymentID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createOverpaymentHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createOverpaymentHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID overpaymentID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createOverpaymentHistory");
    } // verify the required parameter 'overpaymentID' is set
    if (overpaymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'overpaymentID' when calling createOverpaymentHistory");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling createOverpaymentHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createOverpaymentHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("OverpaymentID", overpaymentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Overpayments/{OverpaymentID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a single payment for invoices or credit notes
   *
   * <p><b>200</b> - Success - return response of type Payments array for newly created Payment
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payment Request body with a single Payment object
   * @param accessToken Authorization token for user set in header of each request
   * @return Payments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Payments createPayment(String accessToken, String xeroTenantId, Payment payment)
      throws IOException {
    try {
      TypeReference<Payments> typeRef = new TypeReference<Payments>() {};
      HttpResponse response = createPaymentForHttpResponse(accessToken, xeroTenantId, payment);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPayment -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Payments", object.getMessage());
        }
        handler.validationError("Payments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPaymentForHttpResponse(
      String accessToken, String xeroTenantId, Payment payment) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPayment");
    } // verify the required parameter 'payment' is set
    if (payment == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payment' when calling createPayment");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPayment");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Payments");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(payment);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a history record for a payment
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - A failed request due to validation error - API is not able to create
   * HistoryRecord for Payments
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param paymentID Unique identifier for a Payment
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createPaymentHistory(
      String accessToken, String xeroTenantId, UUID paymentID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createPaymentHistoryForHttpResponse(accessToken, xeroTenantId, paymentID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPaymentHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPaymentHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID paymentID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPaymentHistory");
    } // verify the required parameter 'paymentID' is set
    if (paymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'paymentID' when calling createPaymentHistory");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling createPaymentHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPaymentHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PaymentID", paymentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Payments/{PaymentID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create payment services
   *
   * <p><b>200</b> - Success - return response of type PaymentServices array for newly created
   * PaymentService
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param paymentServices PaymentServices array with PaymentService object in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return PaymentServices
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PaymentServices createPaymentService(
      String accessToken, String xeroTenantId, PaymentServices paymentServices) throws IOException {
    try {
      TypeReference<PaymentServices> typeRef = new TypeReference<PaymentServices>() {};
      HttpResponse response =
          createPaymentServiceForHttpResponse(accessToken, xeroTenantId, paymentServices);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPaymentService -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("PaymentServices", object.getMessage());
        }
        handler.validationError("PaymentServices", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPaymentServiceForHttpResponse(
      String accessToken, String xeroTenantId, PaymentServices paymentServices) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPaymentService");
    } // verify the required parameter 'paymentServices' is set
    if (paymentServices == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'paymentServices' when calling createPaymentService");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPaymentService");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PaymentServices");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(paymentServices);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create multiple payments for invoices or credit notes
   *
   * <p><b>200</b> - Success - return response of type Payments array for newly created Payment
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payments Payments array with Payment object in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return Payments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Payments createPayments(
      String accessToken, String xeroTenantId, Payments payments, Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<Payments> typeRef = new TypeReference<Payments>() {};
      HttpResponse response =
          createPaymentsForHttpResponse(accessToken, xeroTenantId, payments, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPayments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Payments", object.getMessage());
        }
        handler.validationError("Payments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPaymentsForHttpResponse(
      String accessToken, String xeroTenantId, Payments payments, Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPayments");
    } // verify the required parameter 'payments' is set
    if (payments == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payments' when calling createPayments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPayments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Payments");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(payments);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create an Allocation for prepayments
   *
   * <p><b>200</b> - Success - return response of type Allocations array of Allocation for all
   * Prepayment
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param prepaymentID Unique identifier for Prepayment
   * @param allocations Allocations with an array of Allocation object in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return Allocations
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Allocations createPrepaymentAllocations(
      String accessToken,
      String xeroTenantId,
      UUID prepaymentID,
      Allocations allocations,
      Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<Allocations> typeRef = new TypeReference<Allocations>() {};
      HttpResponse response =
          createPrepaymentAllocationsForHttpResponse(
              accessToken, xeroTenantId, prepaymentID, allocations, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPrepaymentAllocations -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Allocations", object.getMessage());
        }
        handler.validationError("Allocations", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPrepaymentAllocationsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID prepaymentID,
      Allocations allocations,
      Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPrepaymentAllocations");
    } // verify the required parameter 'prepaymentID' is set
    if (prepaymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'prepaymentID' when calling createPrepaymentAllocations");
    } // verify the required parameter 'allocations' is set
    if (allocations == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'allocations' when calling createPrepaymentAllocations");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPrepaymentAllocations");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PrepaymentID", prepaymentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Prepayments/{PrepaymentID}/Allocations");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(allocations);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a history record for an Prepayment
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - Unsupported - return response incorrect exception, API is not able to create
   * HistoryRecord for Expense Claims
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param prepaymentID Unique identifier for a PrePayment
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createPrepaymentHistory(
      String accessToken, String xeroTenantId, UUID prepaymentID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createPrepaymentHistoryForHttpResponse(
              accessToken, xeroTenantId, prepaymentID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPrepaymentHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPrepaymentHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID prepaymentID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPrepaymentHistory");
    } // verify the required parameter 'prepaymentID' is set
    if (prepaymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'prepaymentID' when calling createPrepaymentHistory");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling createPrepaymentHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPrepaymentHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PrepaymentID", prepaymentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Prepayments/{PrepaymentID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create HistoryRecord for purchase orders
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param purchaseOrderID Unique identifier for a PurchaseOrder
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createPurchaseOrderHistory(
      String accessToken, String xeroTenantId, UUID purchaseOrderID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createPurchaseOrderHistoryForHttpResponse(
              accessToken, xeroTenantId, purchaseOrderID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPurchaseOrderHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPurchaseOrderHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID purchaseOrderID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPurchaseOrderHistory");
    } // verify the required parameter 'purchaseOrderID' is set
    if (purchaseOrderID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'purchaseOrderID' when calling"
              + " createPurchaseOrderHistory");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling"
              + " createPurchaseOrderHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPurchaseOrderHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PurchaseOrderID", purchaseOrderID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/PurchaseOrders/{PurchaseOrderID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create one or more purchase orders
   *
   * <p><b>200</b> - Success - return response of type PurchaseOrder array for specified
   * PurchaseOrder
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param purchaseOrders PurchaseOrders with an array of PurchaseOrder object in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return PurchaseOrders
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PurchaseOrders createPurchaseOrders(
      String accessToken,
      String xeroTenantId,
      PurchaseOrders purchaseOrders,
      Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<PurchaseOrders> typeRef = new TypeReference<PurchaseOrders>() {};
      HttpResponse response =
          createPurchaseOrdersForHttpResponse(
              accessToken, xeroTenantId, purchaseOrders, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPurchaseOrders -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("PurchaseOrders", object.getMessage());
        }
        handler.validationError("PurchaseOrders", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPurchaseOrdersForHttpResponse(
      String accessToken,
      String xeroTenantId,
      PurchaseOrders purchaseOrders,
      Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPurchaseOrders");
    } // verify the required parameter 'purchaseOrders' is set
    if (purchaseOrders == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'purchaseOrders' when calling createPurchaseOrders");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPurchaseOrders");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PurchaseOrders");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(purchaseOrders);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create Attachment on Quote
   *
   * <p><b>200</b> - Success - return response of type Attachments array of Attachment
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param quoteID Unique identifier for Quote object
   * @param fileName Name of the attachment
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments createQuoteAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID quoteID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          createQuoteAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, quoteID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createQuoteAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createQuoteAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID quoteID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createQuoteAttachmentByFileName");
    } // verify the required parameter 'quoteID' is set
    if (quoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quoteID' when calling createQuoteAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling createQuoteAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling createQuoteAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createQuoteAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("QuoteID", quoteID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Quotes/{QuoteID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an quote
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param quoteID Unique identifier for an Quote
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createQuoteHistory(
      String accessToken, String xeroTenantId, UUID quoteID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createQuoteHistoryForHttpResponse(accessToken, xeroTenantId, quoteID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createQuoteHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createQuoteHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID quoteID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createQuoteHistory");
    } // verify the required parameter 'quoteID' is set
    if (quoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quoteID' when calling createQuoteHistory");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling createQuoteHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createQuoteHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("QuoteID", quoteID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Quotes/{QuoteID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create one or more quotes
   *
   * <p><b>200</b> - Success - return response of type Quotes with array with newly created Quote
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param quotes Quotes with an array of Quote object in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return Quotes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Quotes createQuotes(
      String accessToken, String xeroTenantId, Quotes quotes, Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<Quotes> typeRef = new TypeReference<Quotes>() {};
      HttpResponse response =
          createQuotesForHttpResponse(accessToken, xeroTenantId, quotes, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createQuotes -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Quotes", object.getMessage());
        }
        handler.validationError("Quotes", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createQuotesForHttpResponse(
      String accessToken, String xeroTenantId, Quotes quotes, Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createQuotes");
    } // verify the required parameter 'quotes' is set
    if (quotes == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quotes' when calling createQuotes");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createQuotes");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Quotes");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(quotes);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create draft expense claim receipts for any user
   *
   * <p><b>200</b> - Success - return response of type Receipts array for newly created Receipt
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param receipts Receipts with an array of Receipt object in body of request
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Receipts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Receipts createReceipt(
      String accessToken, String xeroTenantId, Receipts receipts, Integer unitdp)
      throws IOException {
    try {
      TypeReference<Receipts> typeRef = new TypeReference<Receipts>() {};
      HttpResponse response =
          createReceiptForHttpResponse(accessToken, xeroTenantId, receipts, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createReceipt -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Receipts", object.getMessage());
        }
        handler.validationError("Receipts", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createReceiptForHttpResponse(
      String accessToken, String xeroTenantId, Receipts receipts, Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createReceipt");
    } // verify the required parameter 'receipts' is set
    if (receipts == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'receipts' when calling createReceipt");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createReceipt");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Receipts");
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(receipts);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create Attachment on expense claim receipts by file name
   *
   * <p><b>200</b> - Success - return response of type Attachments array with newly created
   * Attachment for a specified Receipt
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param receiptID Unique identifier for a Receipt
   * @param fileName The name of the file being attached to the Receipt
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments createReceiptAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID receiptID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          createReceiptAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, receiptID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createReceiptAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createReceiptAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID receiptID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createReceiptAttachmentByFileName");
    } // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'receiptID' when calling"
              + " createReceiptAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " createReceiptAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling createReceiptAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createReceiptAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ReceiptID", receiptID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Receipts/{ReceiptID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an Receipt
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - Unsupported - return response incorrect exception, API is not able to create
   * HistoryRecord for Receipts
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param receiptID Unique identifier for a Receipt
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createReceiptHistory(
      String accessToken, String xeroTenantId, UUID receiptID, HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createReceiptHistoryForHttpResponse(accessToken, xeroTenantId, receiptID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createReceiptHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createReceiptHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID receiptID, HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createReceiptHistory");
    } // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'receiptID' when calling createReceiptHistory");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling createReceiptHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createReceiptHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ReceiptID", receiptID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Receipts/{ReceiptID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create attachment on repeating invoices by file name
   *
   * <p><b>200</b> - Success - return response of type Attachments array with updated Attachment for
   * a specified Repeating Invoice
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
   * @param fileName The name of the file being attached to a Repeating Invoice
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments createRepeatingInvoiceAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID repeatingInvoiceID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          createRepeatingInvoiceAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, repeatingInvoiceID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createRepeatingInvoiceAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createRepeatingInvoiceAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID repeatingInvoiceID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createRepeatingInvoiceAttachmentByFileName");
    } // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'repeatingInvoiceID' when calling"
              + " createRepeatingInvoiceAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " createRepeatingInvoiceAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling"
              + " createRepeatingInvoiceAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createRepeatingInvoiceAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create history for a repeating invoice
   *
   * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
   * @param historyRecords HistoryRecords containing an array of HistoryRecord objects in body of
   *     request
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords createRepeatingInvoiceHistory(
      String accessToken,
      String xeroTenantId,
      UUID repeatingInvoiceID,
      HistoryRecords historyRecords)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          createRepeatingInvoiceHistoryForHttpResponse(
              accessToken, xeroTenantId, repeatingInvoiceID, historyRecords);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createRepeatingInvoiceHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("HistoryRecords", object.getMessage());
        }
        handler.validationError("HistoryRecords", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createRepeatingInvoiceHistoryForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID repeatingInvoiceID,
      HistoryRecords historyRecords)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " createRepeatingInvoiceHistory");
    } // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'repeatingInvoiceID' when calling"
              + " createRepeatingInvoiceHistory");
    } // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'historyRecords' when calling"
              + " createRepeatingInvoiceHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " createRepeatingInvoiceHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/RepeatingInvoices/{RepeatingInvoiceID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(historyRecords);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create one or more Tax Rates
   *
   * <p><b>200</b> - Success - return response of type TaxRates array newly created TaxRate
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param taxRates TaxRates array with TaxRate object in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return TaxRates
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TaxRates createTaxRates(String accessToken, String xeroTenantId, TaxRates taxRates)
      throws IOException {
    try {
      TypeReference<TaxRates> typeRef = new TypeReference<TaxRates>() {};
      HttpResponse response = createTaxRatesForHttpResponse(accessToken, xeroTenantId, taxRates);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createTaxRates -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("TaxRates", object.getMessage());
        }
        handler.validationError("TaxRates", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createTaxRatesForHttpResponse(
      String accessToken, String xeroTenantId, TaxRates taxRates) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createTaxRates");
    } // verify the required parameter 'taxRates' is set
    if (taxRates == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'taxRates' when calling createTaxRates");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createTaxRates");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/TaxRates");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(taxRates);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create tracking categories
   *
   * <p><b>200</b> - Success - return response of type TrackingCategories array of newly created
   * TrackingCategory
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param trackingCategory TrackingCategory object in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return TrackingCategories
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TrackingCategories createTrackingCategory(
      String accessToken, String xeroTenantId, TrackingCategory trackingCategory)
      throws IOException {
    try {
      TypeReference<TrackingCategories> typeRef = new TypeReference<TrackingCategories>() {};
      HttpResponse response =
          createTrackingCategoryForHttpResponse(accessToken, xeroTenantId, trackingCategory);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createTrackingCategory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("TrackingCategories", object.getMessage());
        }
        handler.validationError("TrackingCategories", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createTrackingCategoryForHttpResponse(
      String accessToken, String xeroTenantId, TrackingCategory trackingCategory)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createTrackingCategory");
    } // verify the required parameter 'trackingCategory' is set
    if (trackingCategory == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'trackingCategory' when calling createTrackingCategory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createTrackingCategory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/TrackingCategories");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(trackingCategory);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create options for a specified tracking category
   *
   * <p><b>200</b> - Success - return response of type TrackingOptions array of options for a
   * specified category
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param trackingCategoryID Unique identifier for a TrackingCategory
   * @param trackingOption TrackingOption object in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return TrackingOptions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TrackingOptions createTrackingOptions(
      String accessToken,
      String xeroTenantId,
      UUID trackingCategoryID,
      TrackingOption trackingOption)
      throws IOException {
    try {
      TypeReference<TrackingOptions> typeRef = new TypeReference<TrackingOptions>() {};
      HttpResponse response =
          createTrackingOptionsForHttpResponse(
              accessToken, xeroTenantId, trackingCategoryID, trackingOption);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createTrackingOptions -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("TrackingOptions", object.getMessage());
        }
        handler.validationError("TrackingOptions", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createTrackingOptionsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID trackingCategoryID,
      TrackingOption trackingOption)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createTrackingOptions");
    } // verify the required parameter 'trackingCategoryID' is set
    if (trackingCategoryID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'trackingCategoryID' when calling createTrackingOptions");
    } // verify the required parameter 'trackingOption' is set
    if (trackingOption == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'trackingOption' when calling createTrackingOptions");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createTrackingOptions");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TrackingCategoryID", trackingCategoryID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/TrackingCategories/{TrackingCategoryID}/Options");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(trackingOption);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PUT, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to delete a chart of accounts
   *
   * <p><b>200</b> - Success - delete existing Account and return response of type Accounts array
   * with deleted Account
   *
   * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accountID Unique identifier for retrieving single object
   * @param accessToken Authorization token for user set in header of each request
   * @return Accounts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Accounts deleteAccount(String accessToken, String xeroTenantId, UUID accountID)
      throws IOException {
    try {
      TypeReference<Accounts> typeRef = new TypeReference<Accounts>() {};
      HttpResponse response = deleteAccountForHttpResponse(accessToken, xeroTenantId, accountID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteAccount -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Accounts", object.getMessage());
        }
        handler.validationError("Accounts", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse deleteAccountForHttpResponse(
      String accessToken, String xeroTenantId, UUID accountID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteAccount");
    } // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accountID' when calling deleteAccount");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteAccount");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("AccountID", accountID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Accounts/{AccountID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to delete a specific Contact from a Contact Group
   *
   * <p><b>204</b> - Success - return response 204 no content
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactGroupID Unique identifier for a Contact Group
   * @param contactID Unique identifier for a Contact
   * @param accessToken Authorization token for user set in header of each request
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public void deleteContactGroupContact(
      String accessToken, String xeroTenantId, UUID contactGroupID, UUID contactID)
      throws IOException {
    try {
      deleteContactGroupContactForHttpResponse(
          accessToken, xeroTenantId, contactGroupID, contactID);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteContactGroupContact -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
  }

  public HttpResponse deleteContactGroupContactForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactGroupID, UUID contactID)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteContactGroupContact");
    } // verify the required parameter 'contactGroupID' is set
    if (contactGroupID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactGroupID' when calling deleteContactGroupContact");
    } // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactID' when calling deleteContactGroupContact");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteContactGroupContact");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactGroupID", contactGroupID);
    uriVariables.put("ContactID", contactID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/ContactGroups/{ContactGroupID}/Contacts/{ContactID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to delete all Contacts from a Contact Group
   *
   * <p><b>204</b> - Success - return response 204 no content
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactGroupID Unique identifier for a Contact Group
   * @param accessToken Authorization token for user set in header of each request
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public void deleteContactGroupContacts(
      String accessToken, String xeroTenantId, UUID contactGroupID) throws IOException {
    try {
      deleteContactGroupContactsForHttpResponse(accessToken, xeroTenantId, contactGroupID);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteContactGroupContacts -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
  }

  public HttpResponse deleteContactGroupContactsForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactGroupID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteContactGroupContacts");
    } // verify the required parameter 'contactGroupID' is set
    if (contactGroupID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactGroupID' when calling"
              + " deleteContactGroupContacts");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteContactGroupContacts");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactGroupID", contactGroupID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/ContactGroups/{ContactGroupID}/Contacts");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to delete a specified item
   *
   * <p><b>204</b> - Success - return response 204 no content
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param itemID Unique identifier for an Item
   * @param accessToken Authorization token for user set in header of each request
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public void deleteItem(String accessToken, String xeroTenantId, UUID itemID) throws IOException {
    try {
      deleteItemForHttpResponse(accessToken, xeroTenantId, itemID);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteItem -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
  }

  public HttpResponse deleteItemForHttpResponse(
      String accessToken, String xeroTenantId, UUID itemID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteItem");
    } // verify the required parameter 'itemID' is set
    if (itemID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'itemID' when calling deleteItem");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteItem");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ItemID", itemID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Items/{ItemID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to delete a specified linked transactions (billable expenses)
   *
   * <p><b>204</b> - Success - return response 204 no content
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param linkedTransactionID Unique identifier for a LinkedTransaction
   * @param accessToken Authorization token for user set in header of each request
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public void deleteLinkedTransaction(
      String accessToken, String xeroTenantId, UUID linkedTransactionID) throws IOException {
    try {
      deleteLinkedTransactionForHttpResponse(accessToken, xeroTenantId, linkedTransactionID);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteLinkedTransaction -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
  }

  public HttpResponse deleteLinkedTransactionForHttpResponse(
      String accessToken, String xeroTenantId, UUID linkedTransactionID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteLinkedTransaction");
    } // verify the required parameter 'linkedTransactionID' is set
    if (linkedTransactionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'linkedTransactionID' when calling"
              + " deleteLinkedTransaction");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteLinkedTransaction");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("LinkedTransactionID", linkedTransactionID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/LinkedTransactions/{LinkedTransactionID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update a specified payment for invoices and credit notes
   *
   * <p><b>200</b> - Success - return response of type Payments array for updated Payment
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param paymentID Unique identifier for a Payment
   * @param paymentDelete The paymentDelete parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return Payments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Payments deletePayment(
      String accessToken, String xeroTenantId, UUID paymentID, PaymentDelete paymentDelete)
      throws IOException {
    try {
      TypeReference<Payments> typeRef = new TypeReference<Payments>() {};
      HttpResponse response =
          deletePaymentForHttpResponse(accessToken, xeroTenantId, paymentID, paymentDelete);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deletePayment -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Payments", object.getMessage());
        }
        handler.validationError("Payments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse deletePaymentForHttpResponse(
      String accessToken, String xeroTenantId, UUID paymentID, PaymentDelete paymentDelete)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deletePayment");
    } // verify the required parameter 'paymentID' is set
    if (paymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'paymentID' when calling deletePayment");
    } // verify the required parameter 'paymentDelete' is set
    if (paymentDelete == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'paymentDelete' when calling deletePayment");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deletePayment");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PaymentID", paymentID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Payments/{PaymentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(paymentDelete);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to delete tracking categories
   *
   * <p><b>200</b> - Success - return response of type TrackingCategories array of deleted
   * TrackingCategory
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param trackingCategoryID Unique identifier for a TrackingCategory
   * @param accessToken Authorization token for user set in header of each request
   * @return TrackingCategories
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TrackingCategories deleteTrackingCategory(
      String accessToken, String xeroTenantId, UUID trackingCategoryID) throws IOException {
    try {
      TypeReference<TrackingCategories> typeRef = new TypeReference<TrackingCategories>() {};
      HttpResponse response =
          deleteTrackingCategoryForHttpResponse(accessToken, xeroTenantId, trackingCategoryID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteTrackingCategory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse deleteTrackingCategoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID trackingCategoryID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteTrackingCategory");
    } // verify the required parameter 'trackingCategoryID' is set
    if (trackingCategoryID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'trackingCategoryID' when calling"
              + " deleteTrackingCategory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteTrackingCategory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TrackingCategoryID", trackingCategoryID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/TrackingCategories/{TrackingCategoryID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to delete a specified option for a specified tracking category
   *
   * <p><b>200</b> - Success - return response of type TrackingOptions array of remaining options
   * for a specified category
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param trackingCategoryID Unique identifier for a TrackingCategory
   * @param trackingOptionID Unique identifier for a Tracking Option
   * @param accessToken Authorization token for user set in header of each request
   * @return TrackingOptions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TrackingOptions deleteTrackingOptions(
      String accessToken, String xeroTenantId, UUID trackingCategoryID, UUID trackingOptionID)
      throws IOException {
    try {
      TypeReference<TrackingOptions> typeRef = new TypeReference<TrackingOptions>() {};
      HttpResponse response =
          deleteTrackingOptionsForHttpResponse(
              accessToken, xeroTenantId, trackingCategoryID, trackingOptionID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteTrackingOptions -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse deleteTrackingOptionsForHttpResponse(
      String accessToken, String xeroTenantId, UUID trackingCategoryID, UUID trackingOptionID)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteTrackingOptions");
    } // verify the required parameter 'trackingCategoryID' is set
    if (trackingCategoryID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'trackingCategoryID' when calling deleteTrackingOptions");
    } // verify the required parameter 'trackingOptionID' is set
    if (trackingOptionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'trackingOptionID' when calling deleteTrackingOptions");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteTrackingOptions");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TrackingCategoryID", trackingCategoryID);
    uriVariables.put("TrackingOptionID", trackingOptionID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/TrackingCategories/{TrackingCategoryID}/Options/{TrackingOptionID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("DELETE " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.DELETE, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to email a copy of invoice to related Contact
   *
   * <p><b>204</b> - Success - return response 204 no content
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoiceID Unique identifier for an Invoice
   * @param requestEmpty The requestEmpty parameter
   * @param accessToken Authorization token for user set in header of each request
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public void emailInvoice(
      String accessToken, String xeroTenantId, UUID invoiceID, RequestEmpty requestEmpty)
      throws IOException {
    try {
      emailInvoiceForHttpResponse(accessToken, xeroTenantId, invoiceID, requestEmpty);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : emailInvoice -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("", object.getMessage());
        }
        handler.validationError("", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
  }

  public HttpResponse emailInvoiceForHttpResponse(
      String accessToken, String xeroTenantId, UUID invoiceID, RequestEmpty requestEmpty)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling emailInvoice");
    } // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoiceID' when calling emailInvoice");
    } // verify the required parameter 'requestEmpty' is set
    if (requestEmpty == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'requestEmpty' when calling emailInvoice");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling emailInvoice");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("InvoiceID", invoiceID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Invoices/{InvoiceID}/Email");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(requestEmpty);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a single chart of accounts
   *
   * <p><b>200</b> - Success - return response of type Accounts array with one Account
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accountID Unique identifier for retrieving single object
   * @param accessToken Authorization token for user set in header of each request
   * @return Accounts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Accounts getAccount(String accessToken, String xeroTenantId, UUID accountID)
      throws IOException {
    try {
      TypeReference<Accounts> typeRef = new TypeReference<Accounts>() {};
      HttpResponse response = getAccountForHttpResponse(accessToken, xeroTenantId, accountID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getAccount -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getAccountForHttpResponse(
      String accessToken, String xeroTenantId, UUID accountID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getAccount");
    } // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accountID' when calling getAccount");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getAccount");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("AccountID", accountID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Accounts/{AccountID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachment on Account by Filename
   *
   * <p><b>200</b> - Success - return response of attachment for Account as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accountID Unique identifier for Account object
   * @param fileName Name of the attachment
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getAccountAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID accountID, String fileName, String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getAccountAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, accountID, fileName, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getAccountAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getAccountAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID accountID, String fileName, String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getAccountAttachmentByFileName");
    } // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accountID' when calling getAccountAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling getAccountAttachmentByFileName");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getAccountAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getAccountAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("AccountID", accountID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Accounts/{AccountID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve specific Attachment on Account
   *
   * <p><b>200</b> - Success - return response of attachment for Account as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accountID Unique identifier for Account object
   * @param attachmentID Unique identifier for Attachment object
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getAccountAttachmentById(
      String accessToken,
      String xeroTenantId,
      UUID accountID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getAccountAttachmentByIdForHttpResponse(
              accessToken, xeroTenantId, accountID, attachmentID, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getAccountAttachmentById -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getAccountAttachmentByIdForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID accountID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getAccountAttachmentById");
    } // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accountID' when calling getAccountAttachmentById");
    } // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'attachmentID' when calling getAccountAttachmentById");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling getAccountAttachmentById");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getAccountAttachmentById");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("AccountID", accountID);
    uriVariables.put("AttachmentID", attachmentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Accounts/{AccountID}/Attachments/{AttachmentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments for accounts
   *
   * <p><b>200</b> - Success - return response of type Attachments array of Attachment
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accountID Unique identifier for Account object
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments getAccountAttachments(String accessToken, String xeroTenantId, UUID accountID)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          getAccountAttachmentsForHttpResponse(accessToken, xeroTenantId, accountID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getAccountAttachments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getAccountAttachmentsForHttpResponse(
      String accessToken, String xeroTenantId, UUID accountID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getAccountAttachments");
    } // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accountID' when calling getAccountAttachments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getAccountAttachments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("AccountID", accountID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Accounts/{AccountID}/Attachments");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve the full chart of accounts
   *
   * <p><b>200</b> - Success - return response of type Accounts array with 0 to n Account
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param accessToken Authorization token for user set in header of each request
   * @return Accounts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Accounts getAccounts(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order)
      throws IOException {
    try {
      TypeReference<Accounts> typeRef = new TypeReference<Accounts>() {};
      HttpResponse response =
          getAccountsForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getAccounts -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getAccountsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getAccounts");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getAccounts");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Accounts");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a single spend or receive money transaction
   *
   * <p><b>200</b> - Success - return response of type BankTransactions array with a specific
   * BankTransaction
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransactionID Xero generated unique identifier for a bank transaction
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return BankTransactions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public BankTransactions getBankTransaction(
      String accessToken, String xeroTenantId, UUID bankTransactionID, Integer unitdp)
      throws IOException {
    try {
      TypeReference<BankTransactions> typeRef = new TypeReference<BankTransactions>() {};
      HttpResponse response =
          getBankTransactionForHttpResponse(accessToken, xeroTenantId, bankTransactionID, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBankTransaction -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBankTransactionForHttpResponse(
      String accessToken, String xeroTenantId, UUID bankTransactionID, Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getBankTransaction");
    } // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransactionID' when calling getBankTransaction");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getBankTransaction");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransactionID", bankTransactionID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/BankTransactions/{BankTransactionID}");
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments on BankTransaction by Filename
   *
   * <p><b>200</b> - Success - return response of attachment for BankTransaction as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransactionID Xero generated unique identifier for a bank transaction
   * @param fileName The name of the file being attached
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getBankTransactionAttachmentByFileName(
      String accessToken,
      String xeroTenantId,
      UUID bankTransactionID,
      String fileName,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getBankTransactionAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, bankTransactionID, fileName, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBankTransactionAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBankTransactionAttachmentByFileNameForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID bankTransactionID,
      String fileName,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getBankTransactionAttachmentByFileName");
    } // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransactionID' when calling"
              + " getBankTransactionAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " getBankTransactionAttachmentByFileName");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getBankTransactionAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getBankTransactionAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransactionID", bankTransactionID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/BankTransactions/{BankTransactionID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments on a specific BankTransaction
   *
   * <p><b>200</b> - Success - return response of attachment for BankTransaction as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransactionID Xero generated unique identifier for a bank transaction
   * @param attachmentID Xero generated unique identifier for an attachment
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getBankTransactionAttachmentById(
      String accessToken,
      String xeroTenantId,
      UUID bankTransactionID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getBankTransactionAttachmentByIdForHttpResponse(
              accessToken, xeroTenantId, bankTransactionID, attachmentID, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBankTransactionAttachmentById -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBankTransactionAttachmentByIdForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID bankTransactionID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getBankTransactionAttachmentById");
    } // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransactionID' when calling"
              + " getBankTransactionAttachmentById");
    } // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'attachmentID' when calling"
              + " getBankTransactionAttachmentById");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getBankTransactionAttachmentById");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getBankTransactionAttachmentById");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransactionID", bankTransactionID);
    uriVariables.put("AttachmentID", attachmentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/BankTransactions/{BankTransactionID}/Attachments/{AttachmentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve any attachments to bank transactions
   *
   * <p><b>200</b> - Success - return response of type Attachments array with 0 to n Attachment
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransactionID Xero generated unique identifier for a bank transaction
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments getBankTransactionAttachments(
      String accessToken, String xeroTenantId, UUID bankTransactionID) throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          getBankTransactionAttachmentsForHttpResponse(
              accessToken, xeroTenantId, bankTransactionID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBankTransactionAttachments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBankTransactionAttachmentsForHttpResponse(
      String accessToken, String xeroTenantId, UUID bankTransactionID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getBankTransactionAttachments");
    } // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransactionID' when calling"
              + " getBankTransactionAttachments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getBankTransactionAttachments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransactionID", bankTransactionID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/BankTransactions/{BankTransactionID}/Attachments");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve any spend or receive money transactions
   *
   * <p><b>200</b> - Success - return response of type BankTransactions array with 0 to n
   * BankTransaction
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page Up to 100 bank transactions will be returned in a single API call with line items
   *     details
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return BankTransactions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public BankTransactions getBankTransactions(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer page,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<BankTransactions> typeRef = new TypeReference<BankTransactions>() {};
      HttpResponse response =
          getBankTransactionsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, page, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBankTransactions -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBankTransactionsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer page,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getBankTransactions");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getBankTransactions");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/BankTransactions");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve history from a bank transactions
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransactionID Xero generated unique identifier for a bank transaction
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getBankTransactionsHistory(
      String accessToken, String xeroTenantId, UUID bankTransactionID) throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getBankTransactionsHistoryForHttpResponse(accessToken, xeroTenantId, bankTransactionID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBankTransactionsHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBankTransactionsHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID bankTransactionID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getBankTransactionsHistory");
    } // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransactionID' when calling"
              + " getBankTransactionsHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getBankTransactionsHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransactionID", bankTransactionID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/BankTransactions/{BankTransactionID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve any bank transfers
   *
   * <p><b>200</b> - Success - return response of BankTransfers array with one BankTransfer
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransferID Xero generated unique identifier for a bank transfer
   * @param accessToken Authorization token for user set in header of each request
   * @return BankTransfers
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public BankTransfers getBankTransfer(String accessToken, String xeroTenantId, UUID bankTransferID)
      throws IOException {
    try {
      TypeReference<BankTransfers> typeRef = new TypeReference<BankTransfers>() {};
      HttpResponse response =
          getBankTransferForHttpResponse(accessToken, xeroTenantId, bankTransferID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBankTransfer -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBankTransferForHttpResponse(
      String accessToken, String xeroTenantId, UUID bankTransferID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getBankTransfer");
    } // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransferID' when calling getBankTransfer");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getBankTransfer");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransferID", bankTransferID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/BankTransfers/{BankTransferID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments on BankTransfer by file name
   *
   * <p><b>200</b> - Success - return response of binary data from the Attachment to a Bank Transfer
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransferID Xero generated unique identifier for a bank transfer
   * @param fileName The name of the file being attached to a Bank Transfer
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getBankTransferAttachmentByFileName(
      String accessToken,
      String xeroTenantId,
      UUID bankTransferID,
      String fileName,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getBankTransferAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, bankTransferID, fileName, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBankTransferAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBankTransferAttachmentByFileNameForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID bankTransferID,
      String fileName,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getBankTransferAttachmentByFileName");
    } // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransferID' when calling"
              + " getBankTransferAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " getBankTransferAttachmentByFileName");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getBankTransferAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getBankTransferAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransferID", bankTransferID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/BankTransfers/{BankTransferID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments on BankTransfer
   *
   * <p><b>200</b> - Success - return response of binary data from the Attachment to a Bank Transfer
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransferID Xero generated unique identifier for a bank transfer
   * @param attachmentID Xero generated unique identifier for an Attachment to a bank transfer
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getBankTransferAttachmentById(
      String accessToken,
      String xeroTenantId,
      UUID bankTransferID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getBankTransferAttachmentByIdForHttpResponse(
              accessToken, xeroTenantId, bankTransferID, attachmentID, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBankTransferAttachmentById -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBankTransferAttachmentByIdForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID bankTransferID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getBankTransferAttachmentById");
    } // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransferID' when calling"
              + " getBankTransferAttachmentById");
    } // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'attachmentID' when calling"
              + " getBankTransferAttachmentById");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getBankTransferAttachmentById");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getBankTransferAttachmentById");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransferID", bankTransferID);
    uriVariables.put("AttachmentID", attachmentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/BankTransfers/{BankTransferID}/Attachments/{AttachmentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments from bank transfers
   *
   * <p><b>200</b> - Success - return response of Attachments array of 0 to N Attachment for a Bank
   * Transfer
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransferID Xero generated unique identifier for a bank transfer
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments getBankTransferAttachments(
      String accessToken, String xeroTenantId, UUID bankTransferID) throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          getBankTransferAttachmentsForHttpResponse(accessToken, xeroTenantId, bankTransferID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBankTransferAttachments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBankTransferAttachmentsForHttpResponse(
      String accessToken, String xeroTenantId, UUID bankTransferID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getBankTransferAttachments");
    } // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransferID' when calling"
              + " getBankTransferAttachments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getBankTransferAttachments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransferID", bankTransferID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/BankTransfers/{BankTransferID}/Attachments");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve history from a bank transfers
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransferID Xero generated unique identifier for a bank transfer
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getBankTransferHistory(
      String accessToken, String xeroTenantId, UUID bankTransferID) throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getBankTransferHistoryForHttpResponse(accessToken, xeroTenantId, bankTransferID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBankTransferHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBankTransferHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID bankTransferID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getBankTransferHistory");
    } // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransferID' when calling getBankTransferHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getBankTransferHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransferID", bankTransferID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/BankTransfers/{BankTransferID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve all bank transfers
   *
   * <p><b>200</b> - Success - return response of BankTransfers array of 0 to N BankTransfer
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param accessToken Authorization token for user set in header of each request
   * @return BankTransfers
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public BankTransfers getBankTransfers(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order)
      throws IOException {
    try {
      TypeReference<BankTransfers> typeRef = new TypeReference<BankTransfers>() {};
      HttpResponse response =
          getBankTransfersForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBankTransfers -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBankTransfersForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getBankTransfers");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getBankTransfers");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/BankTransfers");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve history from a Batch Payment
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param batchPaymentID Unique identifier for BatchPayment
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getBatchPaymentHistory(
      String accessToken, String xeroTenantId, UUID batchPaymentID) throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getBatchPaymentHistoryForHttpResponse(accessToken, xeroTenantId, batchPaymentID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBatchPaymentHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBatchPaymentHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID batchPaymentID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getBatchPaymentHistory");
    } // verify the required parameter 'batchPaymentID' is set
    if (batchPaymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'batchPaymentID' when calling getBatchPaymentHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getBatchPaymentHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BatchPaymentID", batchPaymentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/BatchPayments/{BatchPaymentID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Retrieve either one or many BatchPayments for invoices
   *
   * <p><b>200</b> - Success - return response of type BatchPayments array of BatchPayment objects
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param accessToken Authorization token for user set in header of each request
   * @return BatchPayments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public BatchPayments getBatchPayments(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order)
      throws IOException {
    try {
      TypeReference<BatchPayments> typeRef = new TypeReference<BatchPayments>() {};
      HttpResponse response =
          getBatchPaymentsForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBatchPayments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBatchPaymentsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getBatchPayments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getBatchPayments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/BatchPayments");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specific BrandingThemes
   *
   * <p><b>200</b> - Success - return response of type BrandingThemes with one BrandingTheme
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param brandingThemeID Unique identifier for a Branding Theme
   * @param accessToken Authorization token for user set in header of each request
   * @return BrandingThemes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public BrandingThemes getBrandingTheme(
      String accessToken, String xeroTenantId, UUID brandingThemeID) throws IOException {
    try {
      TypeReference<BrandingThemes> typeRef = new TypeReference<BrandingThemes>() {};
      HttpResponse response =
          getBrandingThemeForHttpResponse(accessToken, xeroTenantId, brandingThemeID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBrandingTheme -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBrandingThemeForHttpResponse(
      String accessToken, String xeroTenantId, UUID brandingThemeID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getBrandingTheme");
    } // verify the required parameter 'brandingThemeID' is set
    if (brandingThemeID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'brandingThemeID' when calling getBrandingTheme");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getBrandingTheme");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BrandingThemeID", brandingThemeID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/BrandingThemes/{BrandingThemeID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve the Payment services for a Branding Theme
   *
   * <p><b>200</b> - Success - return response of type PaymentServices array with 0 to N
   * PaymentService
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param brandingThemeID Unique identifier for a Branding Theme
   * @param accessToken Authorization token for user set in header of each request
   * @return PaymentServices
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PaymentServices getBrandingThemePaymentServices(
      String accessToken, String xeroTenantId, UUID brandingThemeID) throws IOException {
    try {
      TypeReference<PaymentServices> typeRef = new TypeReference<PaymentServices>() {};
      HttpResponse response =
          getBrandingThemePaymentServicesForHttpResponse(
              accessToken, xeroTenantId, brandingThemeID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBrandingThemePaymentServices -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBrandingThemePaymentServicesForHttpResponse(
      String accessToken, String xeroTenantId, UUID brandingThemeID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getBrandingThemePaymentServices");
    } // verify the required parameter 'brandingThemeID' is set
    if (brandingThemeID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'brandingThemeID' when calling"
              + " getBrandingThemePaymentServices");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getBrandingThemePaymentServices");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BrandingThemeID", brandingThemeID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/BrandingThemes/{BrandingThemeID}/PaymentServices");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve all the BrandingThemes
   *
   * <p><b>200</b> - Success - return response of type BrandingThemes
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accessToken Authorization token for user set in header of each request
   * @return BrandingThemes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public BrandingThemes getBrandingThemes(String accessToken, String xeroTenantId)
      throws IOException {
    try {
      TypeReference<BrandingThemes> typeRef = new TypeReference<BrandingThemes>() {};
      HttpResponse response = getBrandingThemesForHttpResponse(accessToken, xeroTenantId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getBrandingThemes -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getBrandingThemesForHttpResponse(String accessToken, String xeroTenantId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getBrandingThemes");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getBrandingThemes");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/BrandingThemes");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a single contacts in a Xero organisation
   *
   * <p><b>200</b> - Success - return response of type Contacts array with a unique Contact
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactID Unique identifier for a Contact
   * @param accessToken Authorization token for user set in header of each request
   * @return Contacts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Contacts getContact(String accessToken, String xeroTenantId, UUID contactID)
      throws IOException {
    try {
      TypeReference<Contacts> typeRef = new TypeReference<Contacts>() {};
      HttpResponse response = getContactForHttpResponse(accessToken, xeroTenantId, contactID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getContact -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getContactForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getContact");
    } // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactID' when calling getContact");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getContact");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactID", contactID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Contacts/{ContactID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments on Contacts by file name
   *
   * <p><b>200</b> - Success - return response of attachment for Contact as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactID Unique identifier for a Contact
   * @param fileName Name for the file you are attaching
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getContactAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID contactID, String fileName, String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getContactAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, contactID, fileName, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getContactAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getContactAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactID, String fileName, String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getContactAttachmentByFileName");
    } // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactID' when calling getContactAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling getContactAttachmentByFileName");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getContactAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getContactAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactID", contactID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Contacts/{ContactID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments on Contacts
   *
   * <p><b>200</b> - Success - return response of attachment for Contact as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactID Unique identifier for a Contact
   * @param attachmentID Unique identifier for a Attachment
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getContactAttachmentById(
      String accessToken,
      String xeroTenantId,
      UUID contactID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getContactAttachmentByIdForHttpResponse(
              accessToken, xeroTenantId, contactID, attachmentID, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getContactAttachmentById -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getContactAttachmentByIdForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID contactID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getContactAttachmentById");
    } // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactID' when calling getContactAttachmentById");
    } // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'attachmentID' when calling getContactAttachmentById");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling getContactAttachmentById");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getContactAttachmentById");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactID", contactID);
    uriVariables.put("AttachmentID", attachmentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Contacts/{ContactID}/Attachments/{AttachmentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve, add and update contacts in a Xero organisation
   *
   * <p><b>200</b> - Success - return response of type Attachments array with 0 to N Attachment
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactID Unique identifier for a Contact
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments getContactAttachments(String accessToken, String xeroTenantId, UUID contactID)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          getContactAttachmentsForHttpResponse(accessToken, xeroTenantId, contactID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getContactAttachments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getContactAttachmentsForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getContactAttachments");
    } // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactID' when calling getContactAttachments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getContactAttachments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactID", contactID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Contacts/{ContactID}/Attachments");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a single contact by Contact Number in a Xero organisation
   *
   * <p><b>200</b> - Success - return response of type Contacts array with a unique Contact
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactNumber This field is read only on the Xero contact screen, used to identify
   *     contacts in external systems (max length &#x3D; 50).
   * @param accessToken Authorization token for user set in header of each request
   * @return Contacts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Contacts getContactByContactNumber(
      String accessToken, String xeroTenantId, String contactNumber) throws IOException {
    try {
      TypeReference<Contacts> typeRef = new TypeReference<Contacts>() {};
      HttpResponse response =
          getContactByContactNumberForHttpResponse(accessToken, xeroTenantId, contactNumber);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getContactByContactNumber -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getContactByContactNumberForHttpResponse(
      String accessToken, String xeroTenantId, String contactNumber) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getContactByContactNumber");
    } // verify the required parameter 'contactNumber' is set
    if (contactNumber == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactNumber' when calling getContactByContactNumber");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getContactByContactNumber");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactNumber", contactNumber);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Contacts/{ContactNumber}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve CISSettings for a contact in a Xero organisation
   *
   * <p><b>200</b> - Success - return response of type CISSettings for a specific Contact
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactID Unique identifier for a Contact
   * @param accessToken Authorization token for user set in header of each request
   * @return CISSettings
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public CISSettings getContactCISSettings(String accessToken, String xeroTenantId, UUID contactID)
      throws IOException {
    try {
      TypeReference<CISSettings> typeRef = new TypeReference<CISSettings>() {};
      HttpResponse response =
          getContactCISSettingsForHttpResponse(accessToken, xeroTenantId, contactID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getContactCISSettings -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getContactCISSettingsForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getContactCISSettings");
    } // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactID' when calling getContactCISSettings");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getContactCISSettings");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactID", contactID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Contacts/{ContactID}/CISSettings");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a unique Contact Group by ID
   *
   * <p><b>200</b> - Success - return response of type Contact Groups array with a specific Contact
   * Group
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactGroupID Unique identifier for a Contact Group
   * @param accessToken Authorization token for user set in header of each request
   * @return ContactGroups
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ContactGroups getContactGroup(String accessToken, String xeroTenantId, UUID contactGroupID)
      throws IOException {
    try {
      TypeReference<ContactGroups> typeRef = new TypeReference<ContactGroups>() {};
      HttpResponse response =
          getContactGroupForHttpResponse(accessToken, xeroTenantId, contactGroupID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getContactGroup -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getContactGroupForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactGroupID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getContactGroup");
    } // verify the required parameter 'contactGroupID' is set
    if (contactGroupID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactGroupID' when calling getContactGroup");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getContactGroup");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactGroupID", contactGroupID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/ContactGroups/{ContactGroupID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve the ContactID and Name of all the contacts in a contact group
   *
   * <p><b>200</b> - Success - return response of type Contact Groups array of Contact Group
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param accessToken Authorization token for user set in header of each request
   * @return ContactGroups
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ContactGroups getContactGroups(
      String accessToken, String xeroTenantId, String where, String order) throws IOException {
    try {
      TypeReference<ContactGroups> typeRef = new TypeReference<ContactGroups>() {};
      HttpResponse response =
          getContactGroupsForHttpResponse(accessToken, xeroTenantId, where, order);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getContactGroups -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getContactGroupsForHttpResponse(
      String accessToken, String xeroTenantId, String where, String order) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getContactGroups");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getContactGroups");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/ContactGroups");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an Contact
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactID Unique identifier for a Contact
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getContactHistory(String accessToken, String xeroTenantId, UUID contactID)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getContactHistoryForHttpResponse(accessToken, xeroTenantId, contactID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getContactHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getContactHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getContactHistory");
    } // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactID' when calling getContactHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getContactHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactID", contactID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Contacts/{ContactID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve all contacts in a Xero organisation
   *
   * <p><b>200</b> - Success - return response of type Contacts array with 0 to N Contact
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param ids Filter by a comma separated list of ContactIDs. Allows you to retrieve a specific
   *     set of contacts in a single call.
   * @param page e.g. page&#x3D;1 - Up to 100 contacts will be returned in a single API call.
   * @param includeArchived e.g. includeArchived&#x3D;true - Contacts with a status of ARCHIVED will
   *     be included in the response
   * @param accessToken Authorization token for user set in header of each request
   * @return Contacts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Contacts getContacts(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      List<UUID> ids,
      Integer page,
      Boolean includeArchived)
      throws IOException {
    try {
      TypeReference<Contacts> typeRef = new TypeReference<Contacts>() {};
      HttpResponse response =
          getContactsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, ids, page, includeArchived);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getContacts -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getContactsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      List<UUID> ids,
      Integer page,
      Boolean includeArchived)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getContacts");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getContacts");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Contacts");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (ids != null) {
      String key = "IDs";
      Object value = ids;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (includeArchived != null) {
      String key = "includeArchived";
      Object value = includeArchived;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specific credit note
   *
   * <p><b>200</b> - Success - return response of type Credit Notes array with a unique CreditNote
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNoteID Unique identifier for a Credit Note
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return CreditNotes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public CreditNotes getCreditNote(
      String accessToken, String xeroTenantId, UUID creditNoteID, Integer unitdp)
      throws IOException {
    try {
      TypeReference<CreditNotes> typeRef = new TypeReference<CreditNotes>() {};
      HttpResponse response =
          getCreditNoteForHttpResponse(accessToken, xeroTenantId, creditNoteID, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getCreditNote -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getCreditNoteForHttpResponse(
      String accessToken, String xeroTenantId, UUID creditNoteID, Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getCreditNote");
    } // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNoteID' when calling getCreditNote");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getCreditNote");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("CreditNoteID", creditNoteID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/CreditNotes/{CreditNoteID}");
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Credit Note as PDF files
   *
   * <p><b>200</b> - Success - return response of binary data from the Attachment to a Credit Note
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNoteID Unique identifier for a Credit Note
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getCreditNoteAsPdf(
      String accessToken, String xeroTenantId, UUID creditNoteID) throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getCreditNoteAsPdfForHttpResponse(accessToken, xeroTenantId, creditNoteID);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getCreditNoteAsPdf -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getCreditNoteAsPdfForHttpResponse(
      String accessToken, String xeroTenantId, UUID creditNoteID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getCreditNoteAsPdf");
    } // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNoteID' when calling getCreditNoteAsPdf");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getCreditNoteAsPdf");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/pdf");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("CreditNoteID", creditNoteID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/CreditNotes/{CreditNoteID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments on CreditNote by file name
   *
   * <p><b>200</b> - Success - return response of attachment for Credit Note as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNoteID Unique identifier for a Credit Note
   * @param fileName Name of the file you are attaching to Credit Note
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getCreditNoteAttachmentByFileName(
      String accessToken,
      String xeroTenantId,
      UUID creditNoteID,
      String fileName,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getCreditNoteAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, creditNoteID, fileName, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getCreditNoteAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getCreditNoteAttachmentByFileNameForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID creditNoteID,
      String fileName,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getCreditNoteAttachmentByFileName");
    } // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNoteID' when calling"
              + " getCreditNoteAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " getCreditNoteAttachmentByFileName");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getCreditNoteAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getCreditNoteAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("CreditNoteID", creditNoteID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/CreditNotes/{CreditNoteID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments on CreditNote
   *
   * <p><b>200</b> - Success - return response of attachment for Credit Note as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNoteID Unique identifier for a Credit Note
   * @param attachmentID Unique identifier for a Attachment
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getCreditNoteAttachmentById(
      String accessToken,
      String xeroTenantId,
      UUID creditNoteID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getCreditNoteAttachmentByIdForHttpResponse(
              accessToken, xeroTenantId, creditNoteID, attachmentID, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getCreditNoteAttachmentById -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getCreditNoteAttachmentByIdForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID creditNoteID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getCreditNoteAttachmentById");
    } // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNoteID' when calling getCreditNoteAttachmentById");
    } // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'attachmentID' when calling getCreditNoteAttachmentById");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling getCreditNoteAttachmentById");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getCreditNoteAttachmentById");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("CreditNoteID", creditNoteID);
    uriVariables.put("AttachmentID", attachmentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/CreditNotes/{CreditNoteID}/Attachments/{AttachmentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments for credit notes
   *
   * <p><b>200</b> - Success - return response of type Attachments array with all Attachment for
   * specific Credit Note
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNoteID Unique identifier for a Credit Note
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments getCreditNoteAttachments(
      String accessToken, String xeroTenantId, UUID creditNoteID) throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          getCreditNoteAttachmentsForHttpResponse(accessToken, xeroTenantId, creditNoteID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getCreditNoteAttachments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getCreditNoteAttachmentsForHttpResponse(
      String accessToken, String xeroTenantId, UUID creditNoteID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getCreditNoteAttachments");
    } // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNoteID' when calling getCreditNoteAttachments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getCreditNoteAttachments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("CreditNoteID", creditNoteID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/CreditNotes/{CreditNoteID}/Attachments");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an CreditNote
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNoteID Unique identifier for a Credit Note
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getCreditNoteHistory(
      String accessToken, String xeroTenantId, UUID creditNoteID) throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getCreditNoteHistoryForHttpResponse(accessToken, xeroTenantId, creditNoteID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getCreditNoteHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getCreditNoteHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID creditNoteID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getCreditNoteHistory");
    } // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNoteID' when calling getCreditNoteHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getCreditNoteHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("CreditNoteID", creditNoteID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/CreditNotes/{CreditNoteID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve any credit notes
   *
   * <p><b>200</b> - Success - return response of type Credit Notes array of CreditNote
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page e.g. page&#x3D;1 – Up to 100 credit notes will be returned in a single API call
   *     with line items shown for each credit note
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return CreditNotes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public CreditNotes getCreditNotes(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer page,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<CreditNotes> typeRef = new TypeReference<CreditNotes>() {};
      HttpResponse response =
          getCreditNotesForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, page, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getCreditNotes -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getCreditNotesForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer page,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getCreditNotes");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getCreditNotes");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/CreditNotes");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve currencies for your organisation
   *
   * <p><b>200</b> - Success - return response of type Currencies array with all Currencies
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param accessToken Authorization token for user set in header of each request
   * @return Currencies
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Currencies getCurrencies(
      String accessToken, String xeroTenantId, String where, String order) throws IOException {
    try {
      TypeReference<Currencies> typeRef = new TypeReference<Currencies>() {};
      HttpResponse response = getCurrenciesForHttpResponse(accessToken, xeroTenantId, where, order);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getCurrencies -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getCurrenciesForHttpResponse(
      String accessToken, String xeroTenantId, String where, String order) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getCurrencies");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getCurrencies");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Currencies");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specific employee used in Xero payrun
   *
   * <p><b>200</b> - Success - return response of type Employees array with specified Employee
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeID Unique identifier for a Employee
   * @param accessToken Authorization token for user set in header of each request
   * @return Employees
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Employees getEmployee(String accessToken, String xeroTenantId, UUID employeeID)
      throws IOException {
    try {
      TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
      HttpResponse response = getEmployeeForHttpResponse(accessToken, xeroTenantId, employeeID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployee -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeeForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployee");
    } // verify the required parameter 'employeeID' is set
    if (employeeID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeID' when calling getEmployee");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployee");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeID", employeeID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve employees used in Xero payrun
   *
   * <p><b>200</b> - Success - return response of type Employees array with all Employee
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param accessToken Authorization token for user set in header of each request
   * @return Employees
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Employees getEmployees(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order)
      throws IOException {
    try {
      TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
      HttpResponse response =
          getEmployeesForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getEmployees -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getEmployeesForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployees");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployees");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Employees");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified expense claim
   *
   * <p><b>200</b> - Success - return response of type ExpenseClaims array with specified
   * ExpenseClaim
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param expenseClaimID Unique identifier for a ExpenseClaim
   * @param accessToken Authorization token for user set in header of each request
   * @return ExpenseClaims
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ExpenseClaims getExpenseClaim(String accessToken, String xeroTenantId, UUID expenseClaimID)
      throws IOException {
    try {
      TypeReference<ExpenseClaims> typeRef = new TypeReference<ExpenseClaims>() {};
      HttpResponse response =
          getExpenseClaimForHttpResponse(accessToken, xeroTenantId, expenseClaimID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getExpenseClaim -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getExpenseClaimForHttpResponse(
      String accessToken, String xeroTenantId, UUID expenseClaimID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getExpenseClaim");
    } // verify the required parameter 'expenseClaimID' is set
    if (expenseClaimID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'expenseClaimID' when calling getExpenseClaim");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getExpenseClaim");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ExpenseClaimID", expenseClaimID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/ExpenseClaims/{ExpenseClaimID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an ExpenseClaim
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param expenseClaimID Unique identifier for a ExpenseClaim
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getExpenseClaimHistory(
      String accessToken, String xeroTenantId, UUID expenseClaimID) throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getExpenseClaimHistoryForHttpResponse(accessToken, xeroTenantId, expenseClaimID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getExpenseClaimHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getExpenseClaimHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID expenseClaimID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getExpenseClaimHistory");
    } // verify the required parameter 'expenseClaimID' is set
    if (expenseClaimID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'expenseClaimID' when calling getExpenseClaimHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getExpenseClaimHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ExpenseClaimID", expenseClaimID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/ExpenseClaims/{ExpenseClaimID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve expense claims
   *
   * <p><b>200</b> - Success - return response of type ExpenseClaims array with all ExpenseClaims
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param accessToken Authorization token for user set in header of each request
   * @return ExpenseClaims
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ExpenseClaims getExpenseClaims(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order)
      throws IOException {
    try {
      TypeReference<ExpenseClaims> typeRef = new TypeReference<ExpenseClaims>() {};
      HttpResponse response =
          getExpenseClaimsForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getExpenseClaims -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getExpenseClaimsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getExpenseClaims");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getExpenseClaims");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/ExpenseClaims");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified sales invoice or purchase bill
   *
   * <p><b>200</b> - Success - return response of type Invoices array with specified Invoices
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoiceID Unique identifier for an Invoice
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Invoices
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Invoices getInvoice(
      String accessToken, String xeroTenantId, UUID invoiceID, Integer unitdp) throws IOException {
    try {
      TypeReference<Invoices> typeRef = new TypeReference<Invoices>() {};
      HttpResponse response =
          getInvoiceForHttpResponse(accessToken, xeroTenantId, invoiceID, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getInvoice -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getInvoiceForHttpResponse(
      String accessToken, String xeroTenantId, UUID invoiceID, Integer unitdp) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getInvoice");
    } // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoiceID' when calling getInvoice");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getInvoice");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("InvoiceID", invoiceID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Invoices/{InvoiceID}");
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve invoices or purchase bills as PDF files
   *
   * <p><b>200</b> - Success - return response of byte array pdf version of specified Invoices
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoiceID Unique identifier for an Invoice
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getInvoiceAsPdf(
      String accessToken, String xeroTenantId, UUID invoiceID) throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response = getInvoiceAsPdfForHttpResponse(accessToken, xeroTenantId, invoiceID);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getInvoiceAsPdf -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getInvoiceAsPdfForHttpResponse(
      String accessToken, String xeroTenantId, UUID invoiceID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getInvoiceAsPdf");
    } // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoiceID' when calling getInvoiceAsPdf");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getInvoiceAsPdf");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/pdf");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("InvoiceID", invoiceID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Invoices/{InvoiceID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachment on invoices or purchase bills by it&#39;s filename
   *
   * <p><b>200</b> - Success - return response of attachment for Invoice as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoiceID Unique identifier for an Invoice
   * @param fileName Name of the file you are attaching
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getInvoiceAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID invoiceID, String fileName, String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getInvoiceAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, invoiceID, fileName, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getInvoiceAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getInvoiceAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID invoiceID, String fileName, String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getInvoiceAttachmentByFileName");
    } // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoiceID' when calling getInvoiceAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling getInvoiceAttachmentByFileName");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getInvoiceAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getInvoiceAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("InvoiceID", invoiceID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Invoices/{InvoiceID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified Attachment on invoices or purchase bills by it&#39;s ID
   *
   * <p><b>200</b> - Success - return response of attachment for Invoice as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoiceID Unique identifier for an Invoice
   * @param attachmentID Unique identifier for an Attachment
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getInvoiceAttachmentById(
      String accessToken,
      String xeroTenantId,
      UUID invoiceID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getInvoiceAttachmentByIdForHttpResponse(
              accessToken, xeroTenantId, invoiceID, attachmentID, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getInvoiceAttachmentById -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getInvoiceAttachmentByIdForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID invoiceID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getInvoiceAttachmentById");
    } // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoiceID' when calling getInvoiceAttachmentById");
    } // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'attachmentID' when calling getInvoiceAttachmentById");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling getInvoiceAttachmentById");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getInvoiceAttachmentById");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("InvoiceID", invoiceID);
    uriVariables.put("AttachmentID", attachmentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Invoices/{InvoiceID}/Attachments/{AttachmentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments on invoices or purchase bills
   *
   * <p><b>200</b> - Success - return response of type Attachments array of Attachments for
   * specified Invoices
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoiceID Unique identifier for an Invoice
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments getInvoiceAttachments(String accessToken, String xeroTenantId, UUID invoiceID)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          getInvoiceAttachmentsForHttpResponse(accessToken, xeroTenantId, invoiceID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getInvoiceAttachments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getInvoiceAttachmentsForHttpResponse(
      String accessToken, String xeroTenantId, UUID invoiceID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getInvoiceAttachments");
    } // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoiceID' when calling getInvoiceAttachments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getInvoiceAttachments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("InvoiceID", invoiceID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Invoices/{InvoiceID}/Attachments");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an invoice
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoiceID Unique identifier for an Invoice
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getInvoiceHistory(String accessToken, String xeroTenantId, UUID invoiceID)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getInvoiceHistoryForHttpResponse(accessToken, xeroTenantId, invoiceID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getInvoiceHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getInvoiceHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID invoiceID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getInvoiceHistory");
    } // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoiceID' when calling getInvoiceHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getInvoiceHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("InvoiceID", invoiceID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Invoices/{InvoiceID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve invoice reminder settings
   *
   * <p><b>200</b> - Success - return response of Invoice Reminders
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accessToken Authorization token for user set in header of each request
   * @return InvoiceReminders
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public InvoiceReminders getInvoiceReminders(String accessToken, String xeroTenantId)
      throws IOException {
    try {
      TypeReference<InvoiceReminders> typeRef = new TypeReference<InvoiceReminders>() {};
      HttpResponse response = getInvoiceRemindersForHttpResponse(accessToken, xeroTenantId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getInvoiceReminders -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getInvoiceRemindersForHttpResponse(String accessToken, String xeroTenantId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getInvoiceReminders");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getInvoiceReminders");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/InvoiceReminders/Settings");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve any sales invoices or purchase bills
   *
   * <p><b>200</b> - Success - return response of type Invoices array with all Invoices
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param ids Filter by a comma-separated list of InvoicesIDs.
   * @param invoiceNumbers Filter by a comma-separated list of InvoiceNumbers.
   * @param contactIDs Filter by a comma-separated list of ContactIDs.
   * @param statuses Filter by a comma-separated list Statuses. For faster response times we
   *     recommend using these explicit parameters instead of passing OR conditions into the Where
   *     filter.
   * @param page e.g. page&#x3D;1 – Up to 100 invoices will be returned in a single API call with
   *     line items shown for each invoice
   * @param includeArchived e.g. includeArchived&#x3D;true - Contacts with a status of ARCHIVED will
   *     be included in the response
   * @param createdByMyApp When set to true you&#39;ll only retrieve Invoices created by your app
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Invoices
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Invoices getInvoices(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      List<UUID> ids,
      List<String> invoiceNumbers,
      List<UUID> contactIDs,
      List<String> statuses,
      Integer page,
      Boolean includeArchived,
      Boolean createdByMyApp,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<Invoices> typeRef = new TypeReference<Invoices>() {};
      HttpResponse response =
          getInvoicesForHttpResponse(
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
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getInvoices -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getInvoicesForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      List<UUID> ids,
      List<String> invoiceNumbers,
      List<UUID> contactIDs,
      List<String> statuses,
      Integer page,
      Boolean includeArchived,
      Boolean createdByMyApp,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getInvoices");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getInvoices");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Invoices");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (ids != null) {
      String key = "IDs";
      Object value = ids;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (invoiceNumbers != null) {
      String key = "InvoiceNumbers";
      Object value = invoiceNumbers;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (contactIDs != null) {
      String key = "ContactIDs";
      Object value = contactIDs;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (statuses != null) {
      String key = "Statuses";
      Object value = statuses;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (includeArchived != null) {
      String key = "includeArchived";
      Object value = includeArchived;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (createdByMyApp != null) {
      String key = "createdByMyApp";
      Object value = createdByMyApp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified item
   *
   * <p><b>200</b> - Success - return response of type Items array with specified Item
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param itemID Unique identifier for an Item
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Items
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Items getItem(String accessToken, String xeroTenantId, UUID itemID, Integer unitdp)
      throws IOException {
    try {
      TypeReference<Items> typeRef = new TypeReference<Items>() {};
      HttpResponse response = getItemForHttpResponse(accessToken, xeroTenantId, itemID, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getItem -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getItemForHttpResponse(
      String accessToken, String xeroTenantId, UUID itemID, Integer unitdp) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getItem");
    } // verify the required parameter 'itemID' is set
    if (itemID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'itemID' when calling getItem");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getItem");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ItemID", itemID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Items/{ItemID}");
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve history for items
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param itemID Unique identifier for an Item
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getItemHistory(String accessToken, String xeroTenantId, UUID itemID)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response = getItemHistoryForHttpResponse(accessToken, xeroTenantId, itemID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getItemHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getItemHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID itemID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getItemHistory");
    } // verify the required parameter 'itemID' is set
    if (itemID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'itemID' when calling getItemHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getItemHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ItemID", itemID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Items/{ItemID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve any items
   *
   * <p><b>200</b> - Success - return response of type Items array with all Item
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Items
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Items getItems(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<Items> typeRef = new TypeReference<Items>() {};
      HttpResponse response =
          getItemsForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getItems -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getItemsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getItems");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getItems");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Items");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified journals.
   *
   * <p><b>200</b> - Success - return response of type Journals array with specified Journal
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param journalID Unique identifier for a Journal
   * @param accessToken Authorization token for user set in header of each request
   * @return Journals
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Journals getJournal(String accessToken, String xeroTenantId, UUID journalID)
      throws IOException {
    try {
      TypeReference<Journals> typeRef = new TypeReference<Journals>() {};
      HttpResponse response = getJournalForHttpResponse(accessToken, xeroTenantId, journalID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getJournal -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getJournalForHttpResponse(
      String accessToken, String xeroTenantId, UUID journalID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getJournal");
    } // verify the required parameter 'journalID' is set
    if (journalID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'journalID' when calling getJournal");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getJournal");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("JournalID", journalID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Journals/{JournalID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve any journals.
   *
   * <p><b>200</b> - Success - return response of type Journals array with all Journals
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param offset Offset by a specified journal number. e.g. journals with a JournalNumber greater
   *     than the offset will be returned
   * @param paymentsOnly Filter to retrieve journals on a cash basis. Journals are returned on an
   *     accrual basis by default.
   * @param accessToken Authorization token for user set in header of each request
   * @return Journals
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Journals getJournals(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      Integer offset,
      Boolean paymentsOnly)
      throws IOException {
    try {
      TypeReference<Journals> typeRef = new TypeReference<Journals>() {};
      HttpResponse response =
          getJournalsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, offset, paymentsOnly);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getJournals -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getJournalsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      Integer offset,
      Boolean paymentsOnly)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getJournals");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getJournals");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Journals");
    if (offset != null) {
      String key = "offset";
      Object value = offset;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (paymentsOnly != null) {
      String key = "paymentsOnly";
      Object value = paymentsOnly;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified linked transactions (billable expenses)
   *
   * <p><b>200</b> - Success - return response of type LinkedTransactions array with a specified
   * LinkedTransaction
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param linkedTransactionID Unique identifier for a LinkedTransaction
   * @param accessToken Authorization token for user set in header of each request
   * @return LinkedTransactions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public LinkedTransactions getLinkedTransaction(
      String accessToken, String xeroTenantId, UUID linkedTransactionID) throws IOException {
    try {
      TypeReference<LinkedTransactions> typeRef = new TypeReference<LinkedTransactions>() {};
      HttpResponse response =
          getLinkedTransactionForHttpResponse(accessToken, xeroTenantId, linkedTransactionID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getLinkedTransaction -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getLinkedTransactionForHttpResponse(
      String accessToken, String xeroTenantId, UUID linkedTransactionID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getLinkedTransaction");
    } // verify the required parameter 'linkedTransactionID' is set
    if (linkedTransactionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'linkedTransactionID' when calling getLinkedTransaction");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getLinkedTransaction");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("LinkedTransactionID", linkedTransactionID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/LinkedTransactions/{LinkedTransactionID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Retrieve linked transactions (billable expenses)
   *
   * <p><b>200</b> - Success - return response of type LinkedTransactions array with all
   * LinkedTransaction
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page Up to 100 linked transactions will be returned in a single API call. Use the page
   *     parameter to specify the page to be returned e.g. page&#x3D;1.
   * @param linkedTransactionID The Xero identifier for an Linked Transaction
   * @param sourceTransactionID Filter by the SourceTransactionID. Get the linked transactions
   *     created from a particular ACCPAY invoice
   * @param contactID Filter by the ContactID. Get all the linked transactions that have been
   *     assigned to a particular customer.
   * @param status Filter by the combination of ContactID and Status. Get the linked transactions
   *     associaed to a customer and with a status
   * @param targetTransactionID Filter by the TargetTransactionID. Get all the linked transactions
   *     allocated to a particular ACCREC invoice
   * @param accessToken Authorization token for user set in header of each request
   * @return LinkedTransactions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public LinkedTransactions getLinkedTransactions(
      String accessToken,
      String xeroTenantId,
      Integer page,
      String linkedTransactionID,
      String sourceTransactionID,
      String contactID,
      String status,
      String targetTransactionID)
      throws IOException {
    try {
      TypeReference<LinkedTransactions> typeRef = new TypeReference<LinkedTransactions>() {};
      HttpResponse response =
          getLinkedTransactionsForHttpResponse(
              accessToken,
              xeroTenantId,
              page,
              linkedTransactionID,
              sourceTransactionID,
              contactID,
              status,
              targetTransactionID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getLinkedTransactions -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getLinkedTransactionsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      Integer page,
      String linkedTransactionID,
      String sourceTransactionID,
      String contactID,
      String status,
      String targetTransactionID)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getLinkedTransactions");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getLinkedTransactions");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/LinkedTransactions");
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (linkedTransactionID != null) {
      String key = "LinkedTransactionID";
      Object value = linkedTransactionID;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (sourceTransactionID != null) {
      String key = "SourceTransactionID";
      Object value = sourceTransactionID;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (contactID != null) {
      String key = "ContactID";
      Object value = contactID;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (status != null) {
      String key = "Status";
      Object value = status;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (targetTransactionID != null) {
      String key = "TargetTransactionID";
      Object value = targetTransactionID;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified manual journals
   *
   * <p><b>200</b> - Success - return response of type ManualJournals array with a specified
   * ManualJournals
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param manualJournalID Unique identifier for a ManualJournal
   * @param accessToken Authorization token for user set in header of each request
   * @return ManualJournals
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ManualJournals getManualJournal(
      String accessToken, String xeroTenantId, UUID manualJournalID) throws IOException {
    try {
      TypeReference<ManualJournals> typeRef = new TypeReference<ManualJournals>() {};
      HttpResponse response =
          getManualJournalForHttpResponse(accessToken, xeroTenantId, manualJournalID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getManualJournal -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getManualJournalForHttpResponse(
      String accessToken, String xeroTenantId, UUID manualJournalID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getManualJournal");
    } // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'manualJournalID' when calling getManualJournal");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getManualJournal");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ManualJournalID", manualJournalID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/ManualJournals/{ManualJournalID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve specified Attachment on ManualJournal by file name
   *
   * <p><b>200</b> - Success - return response of attachment for Manual Journal as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param manualJournalID Unique identifier for a ManualJournal
   * @param fileName The name of the file being attached to a ManualJournal
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getManualJournalAttachmentByFileName(
      String accessToken,
      String xeroTenantId,
      UUID manualJournalID,
      String fileName,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getManualJournalAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, manualJournalID, fileName, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getManualJournalAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getManualJournalAttachmentByFileNameForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID manualJournalID,
      String fileName,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getManualJournalAttachmentByFileName");
    } // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'manualJournalID' when calling"
              + " getManualJournalAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " getManualJournalAttachmentByFileName");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getManualJournalAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getManualJournalAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ManualJournalID", manualJournalID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/ManualJournals/{ManualJournalID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve specified Attachment on ManualJournals
   *
   * <p><b>200</b> - Success - return response of attachment for Manual Journal as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param manualJournalID Unique identifier for a ManualJournal
   * @param attachmentID Unique identifier for a Attachment
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getManualJournalAttachmentById(
      String accessToken,
      String xeroTenantId,
      UUID manualJournalID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getManualJournalAttachmentByIdForHttpResponse(
              accessToken, xeroTenantId, manualJournalID, attachmentID, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getManualJournalAttachmentById -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getManualJournalAttachmentByIdForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID manualJournalID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getManualJournalAttachmentById");
    } // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'manualJournalID' when calling"
              + " getManualJournalAttachmentById");
    } // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'attachmentID' when calling"
              + " getManualJournalAttachmentById");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getManualJournalAttachmentById");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getManualJournalAttachmentById");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ManualJournalID", manualJournalID);
    uriVariables.put("AttachmentID", attachmentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/ManualJournals/{ManualJournalID}/Attachments/{AttachmentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachment for manual journals
   *
   * <p><b>200</b> - Success - return response of type Attachments array with all Attachments for a
   * ManualJournals
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param manualJournalID Unique identifier for a ManualJournal
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments getManualJournalAttachments(
      String accessToken, String xeroTenantId, UUID manualJournalID) throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          getManualJournalAttachmentsForHttpResponse(accessToken, xeroTenantId, manualJournalID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getManualJournalAttachments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getManualJournalAttachmentsForHttpResponse(
      String accessToken, String xeroTenantId, UUID manualJournalID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getManualJournalAttachments");
    } // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'manualJournalID' when calling"
              + " getManualJournalAttachments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getManualJournalAttachments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ManualJournalID", manualJournalID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/ManualJournals/{ManualJournalID}/Attachments");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve any manual journals
   *
   * <p><b>200</b> - Success - return response of type ManualJournals array with a all
   * ManualJournals
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page e.g. page&#x3D;1 – Up to 100 manual journals will be returned in a single API call
   *     with line items shown for each overpayment
   * @param accessToken Authorization token for user set in header of each request
   * @return ManualJournals
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ManualJournals getManualJournals(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    try {
      TypeReference<ManualJournals> typeRef = new TypeReference<ManualJournals>() {};
      HttpResponse response =
          getManualJournalsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getManualJournals -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getManualJournalsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getManualJournals");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getManualJournals");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/ManualJournals");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a URL to an online invoice
   *
   * <p><b>200</b> - Success - return response of type OnlineInvoice array with one OnlineInvoice
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoiceID Unique identifier for an Invoice
   * @param accessToken Authorization token for user set in header of each request
   * @return OnlineInvoices
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public OnlineInvoices getOnlineInvoice(String accessToken, String xeroTenantId, UUID invoiceID)
      throws IOException {
    try {
      TypeReference<OnlineInvoices> typeRef = new TypeReference<OnlineInvoices>() {};
      HttpResponse response = getOnlineInvoiceForHttpResponse(accessToken, xeroTenantId, invoiceID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getOnlineInvoice -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getOnlineInvoiceForHttpResponse(
      String accessToken, String xeroTenantId, UUID invoiceID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getOnlineInvoice");
    } // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoiceID' when calling getOnlineInvoice");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getOnlineInvoice");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("InvoiceID", invoiceID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Invoices/{InvoiceID}/OnlineInvoice");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you To verify if an organisation is using contruction industry scheme, you can retrieve
   * the CIS settings for the organistaion.
   *
   * <p><b>200</b> - Success - return response of type Organisation array with specified
   * Organisation
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param organisationID The unique Xero identifier for an organisation
   * @param accessToken Authorization token for user set in header of each request
   * @return CISOrgSetting
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public CISOrgSetting getOrganisationCISSettings(
      String accessToken, String xeroTenantId, UUID organisationID) throws IOException {
    try {
      TypeReference<CISOrgSetting> typeRef = new TypeReference<CISOrgSetting>() {};
      HttpResponse response =
          getOrganisationCISSettingsForHttpResponse(accessToken, xeroTenantId, organisationID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getOrganisationCISSettings -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getOrganisationCISSettingsForHttpResponse(
      String accessToken, String xeroTenantId, UUID organisationID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getOrganisationCISSettings");
    } // verify the required parameter 'organisationID' is set
    if (organisationID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'organisationID' when calling"
              + " getOrganisationCISSettings");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getOrganisationCISSettings");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("OrganisationID", organisationID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Organisation/{OrganisationID}/CISSettings");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Organisation details
   *
   * <p><b>200</b> - Success - return response of type Organisation array with all Organisation
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accessToken Authorization token for user set in header of each request
   * @return Organisations
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Organisations getOrganisations(String accessToken, String xeroTenantId)
      throws IOException {
    try {
      TypeReference<Organisations> typeRef = new TypeReference<Organisations>() {};
      HttpResponse response = getOrganisationsForHttpResponse(accessToken, xeroTenantId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getOrganisations -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getOrganisationsForHttpResponse(String accessToken, String xeroTenantId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getOrganisations");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getOrganisations");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Organisation");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified overpayments
   *
   * <p><b>200</b> - Success - return response of type Overpayments array with specified
   * Overpayments
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param overpaymentID Unique identifier for a Overpayment
   * @param accessToken Authorization token for user set in header of each request
   * @return Overpayments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Overpayments getOverpayment(String accessToken, String xeroTenantId, UUID overpaymentID)
      throws IOException {
    try {
      TypeReference<Overpayments> typeRef = new TypeReference<Overpayments>() {};
      HttpResponse response =
          getOverpaymentForHttpResponse(accessToken, xeroTenantId, overpaymentID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getOverpayment -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getOverpaymentForHttpResponse(
      String accessToken, String xeroTenantId, UUID overpaymentID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getOverpayment");
    } // verify the required parameter 'overpaymentID' is set
    if (overpaymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'overpaymentID' when calling getOverpayment");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getOverpayment");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("OverpaymentID", overpaymentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Overpayments/{OverpaymentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an Overpayment
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param overpaymentID Unique identifier for a Overpayment
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getOverpaymentHistory(
      String accessToken, String xeroTenantId, UUID overpaymentID) throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getOverpaymentHistoryForHttpResponse(accessToken, xeroTenantId, overpaymentID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getOverpaymentHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getOverpaymentHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID overpaymentID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getOverpaymentHistory");
    } // verify the required parameter 'overpaymentID' is set
    if (overpaymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'overpaymentID' when calling getOverpaymentHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getOverpaymentHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("OverpaymentID", overpaymentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Overpayments/{OverpaymentID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve overpayments
   *
   * <p><b>200</b> - Success - return response of type Overpayments array with all Overpayments
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page e.g. page&#x3D;1 – Up to 100 overpayments will be returned in a single API call
   *     with line items shown for each overpayment
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Overpayments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Overpayments getOverpayments(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer page,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<Overpayments> typeRef = new TypeReference<Overpayments>() {};
      HttpResponse response =
          getOverpaymentsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, page, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getOverpayments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getOverpaymentsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer page,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getOverpayments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getOverpayments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Overpayments");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified payment for invoices and credit notes
   *
   * <p><b>200</b> - Success - return response of type Payments array for specified Payment
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param paymentID Unique identifier for a Payment
   * @param accessToken Authorization token for user set in header of each request
   * @return Payments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Payments getPayment(String accessToken, String xeroTenantId, UUID paymentID)
      throws IOException {
    try {
      TypeReference<Payments> typeRef = new TypeReference<Payments>() {};
      HttpResponse response = getPaymentForHttpResponse(accessToken, xeroTenantId, paymentID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPayment -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPaymentForHttpResponse(
      String accessToken, String xeroTenantId, UUID paymentID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPayment");
    } // verify the required parameter 'paymentID' is set
    if (paymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'paymentID' when calling getPayment");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPayment");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PaymentID", paymentID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Payments/{PaymentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve history records of a payment
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param paymentID Unique identifier for a Payment
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getPaymentHistory(String accessToken, String xeroTenantId, UUID paymentID)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getPaymentHistoryForHttpResponse(accessToken, xeroTenantId, paymentID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPaymentHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPaymentHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID paymentID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPaymentHistory");
    } // verify the required parameter 'paymentID' is set
    if (paymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'paymentID' when calling getPaymentHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPaymentHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PaymentID", paymentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Payments/{PaymentID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve payment services
   *
   * <p><b>200</b> - Success - return response of type PaymentServices array for all PaymentService
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accessToken Authorization token for user set in header of each request
   * @return PaymentServices
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PaymentServices getPaymentServices(String accessToken, String xeroTenantId)
      throws IOException {
    try {
      TypeReference<PaymentServices> typeRef = new TypeReference<PaymentServices>() {};
      HttpResponse response = getPaymentServicesForHttpResponse(accessToken, xeroTenantId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPaymentServices -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPaymentServicesForHttpResponse(String accessToken, String xeroTenantId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPaymentServices");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPaymentServices");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PaymentServices");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve payments for invoices and credit notes
   *
   * <p><b>200</b> - Success - return response of type Payments array for all Payments
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page Up to 100 payments will be returned in a single API call
   * @param accessToken Authorization token for user set in header of each request
   * @return Payments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Payments getPayments(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    try {
      TypeReference<Payments> typeRef = new TypeReference<Payments>() {};
      HttpResponse response =
          getPaymentsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPayments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPaymentsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPayments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPayments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Payments");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified prepayments
   *
   * <p><b>200</b> - Success - return response of type Prepayments array for a specified Prepayment
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param prepaymentID Unique identifier for a PrePayment
   * @param accessToken Authorization token for user set in header of each request
   * @return Prepayments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Prepayments getPrepayment(String accessToken, String xeroTenantId, UUID prepaymentID)
      throws IOException {
    try {
      TypeReference<Prepayments> typeRef = new TypeReference<Prepayments>() {};
      HttpResponse response = getPrepaymentForHttpResponse(accessToken, xeroTenantId, prepaymentID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPrepayment -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPrepaymentForHttpResponse(
      String accessToken, String xeroTenantId, UUID prepaymentID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPrepayment");
    } // verify the required parameter 'prepaymentID' is set
    if (prepaymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'prepaymentID' when calling getPrepayment");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPrepayment");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PrepaymentID", prepaymentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Prepayments/{PrepaymentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an Prepayment
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param prepaymentID Unique identifier for a PrePayment
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getPrepaymentHistory(
      String accessToken, String xeroTenantId, UUID prepaymentID) throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getPrepaymentHistoryForHttpResponse(accessToken, xeroTenantId, prepaymentID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPrepaymentHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPrepaymentHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID prepaymentID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPrepaymentHistory");
    } // verify the required parameter 'prepaymentID' is set
    if (prepaymentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'prepaymentID' when calling getPrepaymentHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPrepaymentHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PrepaymentID", prepaymentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Prepayments/{PrepaymentID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve prepayments
   *
   * <p><b>200</b> - Success - return response of type Prepayments array for all Prepayment
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page e.g. page&#x3D;1 – Up to 100 prepayments will be returned in a single API call with
   *     line items shown for each overpayment
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Prepayments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Prepayments getPrepayments(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer page,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<Prepayments> typeRef = new TypeReference<Prepayments>() {};
      HttpResponse response =
          getPrepaymentsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, page, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPrepayments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPrepaymentsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer page,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPrepayments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPrepayments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Prepayments");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified purchase orders
   *
   * <p><b>200</b> - Success - return response of type PurchaseOrder array for specified
   * PurchaseOrder
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param purchaseOrderID Unique identifier for a PurchaseOrder
   * @param accessToken Authorization token for user set in header of each request
   * @return PurchaseOrders
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PurchaseOrders getPurchaseOrder(
      String accessToken, String xeroTenantId, UUID purchaseOrderID) throws IOException {
    try {
      TypeReference<PurchaseOrders> typeRef = new TypeReference<PurchaseOrders>() {};
      HttpResponse response =
          getPurchaseOrderForHttpResponse(accessToken, xeroTenantId, purchaseOrderID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPurchaseOrder -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPurchaseOrderForHttpResponse(
      String accessToken, String xeroTenantId, UUID purchaseOrderID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPurchaseOrder");
    } // verify the required parameter 'purchaseOrderID' is set
    if (purchaseOrderID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'purchaseOrderID' when calling getPurchaseOrder");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPurchaseOrder");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PurchaseOrderID", purchaseOrderID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/PurchaseOrders/{PurchaseOrderID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve purchase orders as PDF files
   *
   * <p><b>200</b> - Success - return response of byte array pdf version of specified Purchase
   * Orders
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param purchaseOrderID Unique identifier for an Purchase Order
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getPurchaseOrderAsPdf(
      String accessToken, String xeroTenantId, UUID purchaseOrderID) throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getPurchaseOrderAsPdfForHttpResponse(accessToken, xeroTenantId, purchaseOrderID);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPurchaseOrderAsPdf -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPurchaseOrderAsPdfForHttpResponse(
      String accessToken, String xeroTenantId, UUID purchaseOrderID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPurchaseOrderAsPdf");
    } // verify the required parameter 'purchaseOrderID' is set
    if (purchaseOrderID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'purchaseOrderID' when calling getPurchaseOrderAsPdf");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPurchaseOrderAsPdf");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/pdf");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PurchaseOrderID", purchaseOrderID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/PurchaseOrders/{PurchaseOrderID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified purchase orders
   *
   * <p><b>200</b> - Success - return response of type PurchaseOrder array for specified
   * PurchaseOrder
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param purchaseOrderNumber Unique identifier for a PurchaseOrder
   * @param accessToken Authorization token for user set in header of each request
   * @return PurchaseOrders
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PurchaseOrders getPurchaseOrderByNumber(
      String accessToken, String xeroTenantId, String purchaseOrderNumber) throws IOException {
    try {
      TypeReference<PurchaseOrders> typeRef = new TypeReference<PurchaseOrders>() {};
      HttpResponse response =
          getPurchaseOrderByNumberForHttpResponse(accessToken, xeroTenantId, purchaseOrderNumber);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPurchaseOrderByNumber -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPurchaseOrderByNumberForHttpResponse(
      String accessToken, String xeroTenantId, String purchaseOrderNumber) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPurchaseOrderByNumber");
    } // verify the required parameter 'purchaseOrderNumber' is set
    if (purchaseOrderNumber == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'purchaseOrderNumber' when calling"
              + " getPurchaseOrderByNumber");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPurchaseOrderByNumber");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PurchaseOrderNumber", purchaseOrderNumber);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/PurchaseOrders/{PurchaseOrderNumber}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve history for PurchaseOrder
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param purchaseOrderID Unique identifier for a PurchaseOrder
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getPurchaseOrderHistory(
      String accessToken, String xeroTenantId, UUID purchaseOrderID) throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getPurchaseOrderHistoryForHttpResponse(accessToken, xeroTenantId, purchaseOrderID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPurchaseOrderHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPurchaseOrderHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID purchaseOrderID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPurchaseOrderHistory");
    } // verify the required parameter 'purchaseOrderID' is set
    if (purchaseOrderID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'purchaseOrderID' when calling getPurchaseOrderHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPurchaseOrderHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PurchaseOrderID", purchaseOrderID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/PurchaseOrders/{PurchaseOrderID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve purchase orders
   *
   * <p><b>200</b> - Success - return response of type PurchaseOrder array of all PurchaseOrder
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param status Filter by purchase order status
   * @param dateFrom Filter by purchase order date (e.g. GET
   *     https://.../PurchaseOrders?DateFrom&#x3D;2015-12-01&amp;DateTo&#x3D;2015-12-31
   * @param dateTo Filter by purchase order date (e.g. GET
   *     https://.../PurchaseOrders?DateFrom&#x3D;2015-12-01&amp;DateTo&#x3D;2015-12-31
   * @param order Order by an any element
   * @param page To specify a page, append the page parameter to the URL e.g. ?page&#x3D;1. If there
   *     are 100 records in the response you will need to check if there is any more data by
   *     fetching the next page e.g ?page&#x3D;2 and continuing this process until no more results
   *     are returned.
   * @param accessToken Authorization token for user set in header of each request
   * @return PurchaseOrders
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PurchaseOrders getPurchaseOrders(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String status,
      String dateFrom,
      String dateTo,
      String order,
      Integer page)
      throws IOException {
    try {
      TypeReference<PurchaseOrders> typeRef = new TypeReference<PurchaseOrders>() {};
      HttpResponse response =
          getPurchaseOrdersForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, status, dateFrom, dateTo, order, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPurchaseOrders -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPurchaseOrdersForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String status,
      String dateFrom,
      String dateTo,
      String order,
      Integer page)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPurchaseOrders");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPurchaseOrders");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PurchaseOrders");
    if (status != null) {
      String key = "Status";
      Object value = status;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (dateFrom != null) {
      String key = "DateFrom";
      Object value = dateFrom;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (dateTo != null) {
      String key = "DateTo";
      Object value = dateTo;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified quote
   *
   * <p><b>200</b> - Success - return response of type Quotes array with specified Quote
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param quoteID Unique identifier for an Quote
   * @param accessToken Authorization token for user set in header of each request
   * @return Quotes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Quotes getQuote(String accessToken, String xeroTenantId, UUID quoteID) throws IOException {
    try {
      TypeReference<Quotes> typeRef = new TypeReference<Quotes>() {};
      HttpResponse response = getQuoteForHttpResponse(accessToken, xeroTenantId, quoteID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getQuote -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getQuoteForHttpResponse(String accessToken, String xeroTenantId, UUID quoteID)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getQuote");
    } // verify the required parameter 'quoteID' is set
    if (quoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quoteID' when calling getQuote");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getQuote");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("QuoteID", quoteID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Quotes/{QuoteID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve quotes as PDF files
   *
   * <p><b>200</b> - Success - return response of byte array pdf version of specified Quotes
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param quoteID Unique identifier for an Quote
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getQuoteAsPdf(String accessToken, String xeroTenantId, UUID quoteID)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response = getQuoteAsPdfForHttpResponse(accessToken, xeroTenantId, quoteID);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getQuoteAsPdf -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getQuoteAsPdfForHttpResponse(
      String accessToken, String xeroTenantId, UUID quoteID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getQuoteAsPdf");
    } // verify the required parameter 'quoteID' is set
    if (quoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quoteID' when calling getQuoteAsPdf");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getQuoteAsPdf");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/pdf");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("QuoteID", quoteID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Quotes/{QuoteID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachment on Quote by Filename
   *
   * <p><b>200</b> - Success - return response of attachment for Quote as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param quoteID Unique identifier for Quote object
   * @param fileName Name of the attachment
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getQuoteAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID quoteID, String fileName, String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getQuoteAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, quoteID, fileName, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getQuoteAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getQuoteAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID quoteID, String fileName, String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getQuoteAttachmentByFileName");
    } // verify the required parameter 'quoteID' is set
    if (quoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quoteID' when calling getQuoteAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling getQuoteAttachmentByFileName");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling getQuoteAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getQuoteAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("QuoteID", quoteID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Quotes/{QuoteID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve specific Attachment on Quote
   *
   * <p><b>200</b> - Success - return response of attachment for Quote as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param quoteID Unique identifier for Quote object
   * @param attachmentID Unique identifier for Attachment object
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getQuoteAttachmentById(
      String accessToken, String xeroTenantId, UUID quoteID, UUID attachmentID, String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getQuoteAttachmentByIdForHttpResponse(
              accessToken, xeroTenantId, quoteID, attachmentID, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getQuoteAttachmentById -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getQuoteAttachmentByIdForHttpResponse(
      String accessToken, String xeroTenantId, UUID quoteID, UUID attachmentID, String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getQuoteAttachmentById");
    } // verify the required parameter 'quoteID' is set
    if (quoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quoteID' when calling getQuoteAttachmentById");
    } // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'attachmentID' when calling getQuoteAttachmentById");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling getQuoteAttachmentById");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getQuoteAttachmentById");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("QuoteID", quoteID);
    uriVariables.put("AttachmentID", attachmentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Quotes/{QuoteID}/Attachments/{AttachmentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments for Quotes
   *
   * <p><b>200</b> - Success - return response of type Attachments array of Attachment
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param quoteID Unique identifier for Quote object
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments getQuoteAttachments(String accessToken, String xeroTenantId, UUID quoteID)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          getQuoteAttachmentsForHttpResponse(accessToken, xeroTenantId, quoteID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getQuoteAttachments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getQuoteAttachmentsForHttpResponse(
      String accessToken, String xeroTenantId, UUID quoteID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getQuoteAttachments");
    } // verify the required parameter 'quoteID' is set
    if (quoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quoteID' when calling getQuoteAttachments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getQuoteAttachments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("QuoteID", quoteID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Quotes/{QuoteID}/Attachments");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an quote
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param quoteID Unique identifier for an Quote
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getQuoteHistory(String accessToken, String xeroTenantId, UUID quoteID)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response = getQuoteHistoryForHttpResponse(accessToken, xeroTenantId, quoteID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getQuoteHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getQuoteHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID quoteID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getQuoteHistory");
    } // verify the required parameter 'quoteID' is set
    if (quoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quoteID' when calling getQuoteHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getQuoteHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("QuoteID", quoteID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Quotes/{QuoteID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve any sales quotes
   *
   * <p><b>200</b> - Success - return response of type quotes array with all quotes
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param dateFrom Filter for quotes after a particular date
   * @param dateTo Filter for quotes before a particular date
   * @param expiryDateFrom Filter for quotes expiring after a particular date
   * @param expiryDateTo Filter for quotes before a particular date
   * @param contactID Filter for quotes belonging to a particular contact
   * @param status Filter for quotes of a particular Status
   * @param page e.g. page&#x3D;1 – Up to 100 Quotes will be returned in a single API call with line
   *     items shown for each quote
   * @param order Order by an any element
   * @param accessToken Authorization token for user set in header of each request
   * @return Quotes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Quotes getQuotes(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      LocalDate dateFrom,
      LocalDate dateTo,
      LocalDate expiryDateFrom,
      LocalDate expiryDateTo,
      UUID contactID,
      String status,
      Integer page,
      String order)
      throws IOException {
    try {
      TypeReference<Quotes> typeRef = new TypeReference<Quotes>() {};
      HttpResponse response =
          getQuotesForHttpResponse(
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
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getQuotes -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getQuotesForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      LocalDate dateFrom,
      LocalDate dateTo,
      LocalDate expiryDateFrom,
      LocalDate expiryDateTo,
      UUID contactID,
      String status,
      Integer page,
      String order)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getQuotes");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getQuotes");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Quotes");
    if (dateFrom != null) {
      String key = "DateFrom";
      Object value = dateFrom;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (dateTo != null) {
      String key = "DateTo";
      Object value = dateTo;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (expiryDateFrom != null) {
      String key = "ExpiryDateFrom";
      Object value = expiryDateFrom;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (expiryDateTo != null) {
      String key = "ExpiryDateTo";
      Object value = expiryDateTo;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (contactID != null) {
      String key = "ContactID";
      Object value = contactID;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (status != null) {
      String key = "Status";
      Object value = status;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (page != null) {
      String key = "page";
      Object value = page;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified draft expense claim receipts
   *
   * <p><b>200</b> - Success - return response of type Receipts array for a specified Receipt
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param receiptID Unique identifier for a Receipt
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Receipts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Receipts getReceipt(
      String accessToken, String xeroTenantId, UUID receiptID, Integer unitdp) throws IOException {
    try {
      TypeReference<Receipts> typeRef = new TypeReference<Receipts>() {};
      HttpResponse response =
          getReceiptForHttpResponse(accessToken, xeroTenantId, receiptID, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReceipt -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReceiptForHttpResponse(
      String accessToken, String xeroTenantId, UUID receiptID, Integer unitdp) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReceipt");
    } // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'receiptID' when calling getReceipt");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReceipt");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ReceiptID", receiptID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Receipts/{ReceiptID}");
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments on expense claim receipts by file name
   *
   * <p><b>200</b> - Success - return response of attachment for Receipt as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param receiptID Unique identifier for a Receipt
   * @param fileName The name of the file being attached to the Receipt
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getReceiptAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID receiptID, String fileName, String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getReceiptAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, receiptID, fileName, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReceiptAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReceiptAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID receiptID, String fileName, String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getReceiptAttachmentByFileName");
    } // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'receiptID' when calling getReceiptAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling getReceiptAttachmentByFileName");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getReceiptAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getReceiptAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ReceiptID", receiptID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Receipts/{ReceiptID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments on expense claim receipts by ID
   *
   * <p><b>200</b> - Success - return response of attachment for Receipt as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param receiptID Unique identifier for a Receipt
   * @param attachmentID Unique identifier for a Attachment
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getReceiptAttachmentById(
      String accessToken,
      String xeroTenantId,
      UUID receiptID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getReceiptAttachmentByIdForHttpResponse(
              accessToken, xeroTenantId, receiptID, attachmentID, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReceiptAttachmentById -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReceiptAttachmentByIdForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID receiptID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReceiptAttachmentById");
    } // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'receiptID' when calling getReceiptAttachmentById");
    } // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'attachmentID' when calling getReceiptAttachmentById");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling getReceiptAttachmentById");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReceiptAttachmentById");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ReceiptID", receiptID);
    uriVariables.put("AttachmentID", attachmentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Receipts/{ReceiptID}/Attachments/{AttachmentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments for expense claim receipts
   *
   * <p><b>200</b> - Success - return response of type Attachments array of Attachments for a
   * specified Receipt
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param receiptID Unique identifier for a Receipt
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments getReceiptAttachments(String accessToken, String xeroTenantId, UUID receiptID)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          getReceiptAttachmentsForHttpResponse(accessToken, xeroTenantId, receiptID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReceiptAttachments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReceiptAttachmentsForHttpResponse(
      String accessToken, String xeroTenantId, UUID receiptID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReceiptAttachments");
    } // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'receiptID' when calling getReceiptAttachments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReceiptAttachments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ReceiptID", receiptID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Receipts/{ReceiptID}/Attachments");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a history records of an Receipt
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param receiptID Unique identifier for a Receipt
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getReceiptHistory(String accessToken, String xeroTenantId, UUID receiptID)
      throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getReceiptHistoryForHttpResponse(accessToken, xeroTenantId, receiptID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReceiptHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReceiptHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID receiptID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReceiptHistory");
    } // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'receiptID' when calling getReceiptHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReceiptHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ReceiptID", receiptID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Receipts/{ReceiptID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve draft expense claim receipts for any user
   *
   * <p><b>200</b> - Success - return response of type Receipts array for all Receipt
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Receipts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Receipts getReceipts(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<Receipts> typeRef = new TypeReference<Receipts>() {};
      HttpResponse response =
          getReceiptsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReceipts -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReceiptsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReceipts");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReceipts");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Receipts");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified repeating invoice
   *
   * <p><b>200</b> - Success - return response of type Repeating Invoices array with a specified
   * Repeating Invoice
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
   * @param accessToken Authorization token for user set in header of each request
   * @return RepeatingInvoices
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public RepeatingInvoices getRepeatingInvoice(
      String accessToken, String xeroTenantId, UUID repeatingInvoiceID) throws IOException {
    try {
      TypeReference<RepeatingInvoices> typeRef = new TypeReference<RepeatingInvoices>() {};
      HttpResponse response =
          getRepeatingInvoiceForHttpResponse(accessToken, xeroTenantId, repeatingInvoiceID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getRepeatingInvoice -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getRepeatingInvoiceForHttpResponse(
      String accessToken, String xeroTenantId, UUID repeatingInvoiceID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoice");
    } // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'repeatingInvoiceID' when calling getRepeatingInvoice");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getRepeatingInvoice");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/RepeatingInvoices/{RepeatingInvoiceID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve specified attachment on repeating invoices by file name
   *
   * <p><b>200</b> - Success - return response of attachment for Repeating Invoice as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
   * @param fileName The name of the file being attached to a Repeating Invoice
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getRepeatingInvoiceAttachmentByFileName(
      String accessToken,
      String xeroTenantId,
      UUID repeatingInvoiceID,
      String fileName,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getRepeatingInvoiceAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, repeatingInvoiceID, fileName, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getRepeatingInvoiceAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getRepeatingInvoiceAttachmentByFileNameForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID repeatingInvoiceID,
      String fileName,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getRepeatingInvoiceAttachmentByFileName");
    } // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'repeatingInvoiceID' when calling"
              + " getRepeatingInvoiceAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " getRepeatingInvoiceAttachmentByFileName");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getRepeatingInvoiceAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getRepeatingInvoiceAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified Attachments on repeating invoices
   *
   * <p><b>200</b> - Success - return response of attachment for Repeating Invoice as binary data
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
   * @param attachmentID Unique identifier for a Attachment
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg,
   *     application/pdf
   * @param accessToken Authorization token for user set in header of each request
   * @return File
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ByteArrayInputStream getRepeatingInvoiceAttachmentById(
      String accessToken,
      String xeroTenantId,
      UUID repeatingInvoiceID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    try {
      TypeReference<File> typeRef = new TypeReference<File>() {};
      HttpResponse response =
          getRepeatingInvoiceAttachmentByIdForHttpResponse(
              accessToken, xeroTenantId, repeatingInvoiceID, attachmentID, contentType);
      InputStream is = response.getContent();
      return convertInputToByteArray(is);

    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getRepeatingInvoiceAttachmentById -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getRepeatingInvoiceAttachmentByIdForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID repeatingInvoiceID,
      UUID attachmentID,
      String contentType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getRepeatingInvoiceAttachmentById");
    } // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'repeatingInvoiceID' when calling"
              + " getRepeatingInvoiceAttachmentById");
    } // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'attachmentID' when calling"
              + " getRepeatingInvoiceAttachmentById");
    } // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contentType' when calling"
              + " getRepeatingInvoiceAttachmentById");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getRepeatingInvoiceAttachmentById");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.set("contentType", contentType);
    headers.setAccept("application/octet-stream");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);
    uriVariables.put("AttachmentID", attachmentID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{AttachmentID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Attachments on repeating invoice
   *
   * <p><b>200</b> - Success - return response of type Attachments array with all Attachments for a
   * specified Repeating Invoice
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments getRepeatingInvoiceAttachments(
      String accessToken, String xeroTenantId, UUID repeatingInvoiceID) throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          getRepeatingInvoiceAttachmentsForHttpResponse(
              accessToken, xeroTenantId, repeatingInvoiceID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getRepeatingInvoiceAttachments -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getRepeatingInvoiceAttachmentsForHttpResponse(
      String accessToken, String xeroTenantId, UUID repeatingInvoiceID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getRepeatingInvoiceAttachments");
    } // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'repeatingInvoiceID' when calling"
              + " getRepeatingInvoiceAttachments");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getRepeatingInvoiceAttachments");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve history for a repeating invoice
   *
   * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
   * @param accessToken Authorization token for user set in header of each request
   * @return HistoryRecords
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public HistoryRecords getRepeatingInvoiceHistory(
      String accessToken, String xeroTenantId, UUID repeatingInvoiceID) throws IOException {
    try {
      TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
      HttpResponse response =
          getRepeatingInvoiceHistoryForHttpResponse(accessToken, xeroTenantId, repeatingInvoiceID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getRepeatingInvoiceHistory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getRepeatingInvoiceHistoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID repeatingInvoiceID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoiceHistory");
    } // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'repeatingInvoiceID' when calling"
              + " getRepeatingInvoiceHistory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getRepeatingInvoiceHistory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/RepeatingInvoices/{RepeatingInvoiceID}/History");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve any repeating invoices
   *
   * <p><b>200</b> - Success - return response of type Repeating Invoices array for all Repeating
   * Invoice
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param accessToken Authorization token for user set in header of each request
   * @return RepeatingInvoices
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public RepeatingInvoices getRepeatingInvoices(
      String accessToken, String xeroTenantId, String where, String order) throws IOException {
    try {
      TypeReference<RepeatingInvoices> typeRef = new TypeReference<RepeatingInvoices>() {};
      HttpResponse response =
          getRepeatingInvoicesForHttpResponse(accessToken, xeroTenantId, where, order);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getRepeatingInvoices -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getRepeatingInvoicesForHttpResponse(
      String accessToken, String xeroTenantId, String where, String order) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoices");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getRepeatingInvoices");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/RepeatingInvoices");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve report for AgedPayablesByContact
   *
   * <p><b>200</b> - Success - return response of type ReportWithRows
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactId Unique identifier for a Contact
   * @param date The date of the Aged Payables By Contact report
   * @param fromDate The from date of the Aged Payables By Contact report
   * @param toDate The to date of the Aged Payables By Contact report
   * @param accessToken Authorization token for user set in header of each request
   * @return ReportWithRows
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ReportWithRows getReportAgedPayablesByContact(
      String accessToken,
      String xeroTenantId,
      UUID contactId,
      LocalDate date,
      LocalDate fromDate,
      LocalDate toDate)
      throws IOException {
    try {
      TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
      HttpResponse response =
          getReportAgedPayablesByContactForHttpResponse(
              accessToken, xeroTenantId, contactId, date, fromDate, toDate);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReportAgedPayablesByContact -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReportAgedPayablesByContactForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID contactId,
      LocalDate date,
      LocalDate fromDate,
      LocalDate toDate)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getReportAgedPayablesByContact");
    } // verify the required parameter 'contactId' is set
    if (contactId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactId' when calling getReportAgedPayablesByContact");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getReportAgedPayablesByContact");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Reports/AgedPayablesByContact");
    if (contactId != null) {
      String key = "contactId";
      Object value = contactId;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (date != null) {
      String key = "date";
      Object value = date;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (fromDate != null) {
      String key = "fromDate";
      Object value = fromDate;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (toDate != null) {
      String key = "toDate";
      Object value = toDate;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve report for AgedReceivablesByContact
   *
   * <p><b>200</b> - Success - return response of type ReportWithRows
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactId Unique identifier for a Contact
   * @param date The date of the Aged Receivables By Contact report
   * @param fromDate The from date of the Aged Receivables By Contact report
   * @param toDate The to date of the Aged Receivables By Contact report
   * @param accessToken Authorization token for user set in header of each request
   * @return ReportWithRows
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ReportWithRows getReportAgedReceivablesByContact(
      String accessToken,
      String xeroTenantId,
      UUID contactId,
      LocalDate date,
      LocalDate fromDate,
      LocalDate toDate)
      throws IOException {
    try {
      TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
      HttpResponse response =
          getReportAgedReceivablesByContactForHttpResponse(
              accessToken, xeroTenantId, contactId, date, fromDate, toDate);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReportAgedReceivablesByContact -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReportAgedReceivablesByContactForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID contactId,
      LocalDate date,
      LocalDate fromDate,
      LocalDate toDate)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " getReportAgedReceivablesByContact");
    } // verify the required parameter 'contactId' is set
    if (contactId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactId' when calling"
              + " getReportAgedReceivablesByContact");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " getReportAgedReceivablesByContact");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Reports/AgedReceivablesByContact");
    if (contactId != null) {
      String key = "contactId";
      Object value = contactId;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (date != null) {
      String key = "date";
      Object value = date;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (fromDate != null) {
      String key = "fromDate";
      Object value = fromDate;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (toDate != null) {
      String key = "toDate";
      Object value = toDate;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve report for BAS only valid for AU orgs
   *
   * <p><b>200</b> - Success - return response of type ReportWithRows
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param reportID Unique identifier for a Report
   * @param accessToken Authorization token for user set in header of each request
   * @return ReportWithRows
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ReportWithRows getReportBASorGST(String accessToken, String xeroTenantId, String reportID)
      throws IOException {
    try {
      TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
      HttpResponse response = getReportBASorGSTForHttpResponse(accessToken, xeroTenantId, reportID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReportBASorGST -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReportBASorGSTForHttpResponse(
      String accessToken, String xeroTenantId, String reportID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReportBASorGST");
    } // verify the required parameter 'reportID' is set
    if (reportID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'reportID' when calling getReportBASorGST");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReportBASorGST");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ReportID", reportID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Reports/{ReportID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve report for BAS only valid for AU orgs
   *
   * <p><b>200</b> - Success - return response of type ReportWithRows
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accessToken Authorization token for user set in header of each request
   * @return ReportWithRows
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ReportWithRows getReportBASorGSTList(String accessToken, String xeroTenantId)
      throws IOException {
    try {
      TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
      HttpResponse response = getReportBASorGSTListForHttpResponse(accessToken, xeroTenantId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReportBASorGSTList -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReportBASorGSTListForHttpResponse(String accessToken, String xeroTenantId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReportBASorGSTList");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReportBASorGSTList");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Reports");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve report for BalanceSheet
   *
   * <p><b>200</b> - Success - return response of type ReportWithRows
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param date The date of the Balance Sheet report
   * @param periods The number of periods for the Balance Sheet report
   * @param timeframe The period size to compare to (MONTH, QUARTER, YEAR)
   * @param trackingOptionID1 The tracking option 1 for the Balance Sheet report
   * @param trackingOptionID2 The tracking option 2 for the Balance Sheet report
   * @param standardLayout The standard layout boolean for the Balance Sheet report
   * @param paymentsOnly return a cash basis for the Balance Sheet report
   * @param accessToken Authorization token for user set in header of each request
   * @return ReportWithRows
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ReportWithRows getReportBalanceSheet(
      String accessToken,
      String xeroTenantId,
      String date,
      Integer periods,
      String timeframe,
      String trackingOptionID1,
      String trackingOptionID2,
      Boolean standardLayout,
      Boolean paymentsOnly)
      throws IOException {
    try {
      TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
      HttpResponse response =
          getReportBalanceSheetForHttpResponse(
              accessToken,
              xeroTenantId,
              date,
              periods,
              timeframe,
              trackingOptionID1,
              trackingOptionID2,
              standardLayout,
              paymentsOnly);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReportBalanceSheet -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReportBalanceSheetForHttpResponse(
      String accessToken,
      String xeroTenantId,
      String date,
      Integer periods,
      String timeframe,
      String trackingOptionID1,
      String trackingOptionID2,
      Boolean standardLayout,
      Boolean paymentsOnly)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReportBalanceSheet");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReportBalanceSheet");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Reports/BalanceSheet");
    if (date != null) {
      String key = "date";
      Object value = date;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (periods != null) {
      String key = "periods";
      Object value = periods;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (timeframe != null) {
      String key = "timeframe";
      Object value = timeframe;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (trackingOptionID1 != null) {
      String key = "trackingOptionID1";
      Object value = trackingOptionID1;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (trackingOptionID2 != null) {
      String key = "trackingOptionID2";
      Object value = trackingOptionID2;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (standardLayout != null) {
      String key = "standardLayout";
      Object value = standardLayout;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (paymentsOnly != null) {
      String key = "paymentsOnly";
      Object value = paymentsOnly;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve report for BankSummary
   *
   * <p><b>200</b> - Success - return response of type ReportWithRows
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param fromDate The from date for the Bank Summary report e.g. 2018-03-31
   * @param toDate The to date for the Bank Summary report e.g. 2018-03-31
   * @param accessToken Authorization token for user set in header of each request
   * @return ReportWithRows
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ReportWithRows getReportBankSummary(
      String accessToken, String xeroTenantId, LocalDate fromDate, LocalDate toDate)
      throws IOException {
    try {
      TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
      HttpResponse response =
          getReportBankSummaryForHttpResponse(accessToken, xeroTenantId, fromDate, toDate);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReportBankSummary -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReportBankSummaryForHttpResponse(
      String accessToken, String xeroTenantId, LocalDate fromDate, LocalDate toDate)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReportBankSummary");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReportBankSummary");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Reports/BankSummary");
    if (fromDate != null) {
      String key = "fromDate";
      Object value = fromDate;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (toDate != null) {
      String key = "toDate";
      Object value = toDate;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve report for Budget Summary
   *
   * <p><b>200</b> - success- return a Report with Rows object
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param date The date for the Bank Summary report e.g. 2018-03-31
   * @param period The number of periods to compare (integer between 1 and 12)
   * @param timeframe The period size to compare to (1&#x3D;month, 3&#x3D;quarter, 12&#x3D;year)
   * @param accessToken Authorization token for user set in header of each request
   * @return ReportWithRows
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ReportWithRows getReportBudgetSummary(
      String accessToken, String xeroTenantId, LocalDate date, Integer period, Integer timeframe)
      throws IOException {
    try {
      TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
      HttpResponse response =
          getReportBudgetSummaryForHttpResponse(accessToken, xeroTenantId, date, period, timeframe);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReportBudgetSummary -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReportBudgetSummaryForHttpResponse(
      String accessToken, String xeroTenantId, LocalDate date, Integer period, Integer timeframe)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReportBudgetSummary");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReportBudgetSummary");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Reports/BudgetSummary");
    if (date != null) {
      String key = "date";
      Object value = date;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (period != null) {
      String key = "period";
      Object value = period;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (timeframe != null) {
      String key = "timeframe";
      Object value = timeframe;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve report for ExecutiveSummary
   *
   * <p><b>200</b> - Success - return response of type ReportWithRows
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param date The date for the Bank Summary report e.g. 2018-03-31
   * @param accessToken Authorization token for user set in header of each request
   * @return ReportWithRows
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ReportWithRows getReportExecutiveSummary(
      String accessToken, String xeroTenantId, LocalDate date) throws IOException {
    try {
      TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
      HttpResponse response =
          getReportExecutiveSummaryForHttpResponse(accessToken, xeroTenantId, date);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReportExecutiveSummary -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReportExecutiveSummaryForHttpResponse(
      String accessToken, String xeroTenantId, LocalDate date) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReportExecutiveSummary");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReportExecutiveSummary");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Reports/ExecutiveSummary");
    if (date != null) {
      String key = "date";
      Object value = date;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve report for ProfitAndLoss
   *
   * <p><b>200</b> - Success - return response of type ReportWithRows
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param fromDate The from date for the ProfitAndLoss report e.g. 2018-03-31
   * @param toDate The to date for the ProfitAndLoss report e.g. 2018-03-31
   * @param periods The number of periods to compare (integer between 1 and 12)
   * @param timeframe The period size to compare to (MONTH, QUARTER, YEAR)
   * @param trackingCategoryID The trackingCategory 1 for the ProfitAndLoss report
   * @param trackingCategoryID2 The trackingCategory 2 for the ProfitAndLoss report
   * @param trackingOptionID The tracking option 1 for the ProfitAndLoss report
   * @param trackingOptionID2 The tracking option 2 for the ProfitAndLoss report
   * @param standardLayout Return the standard layout for the ProfitAndLoss report
   * @param paymentsOnly Return cash only basis for the ProfitAndLoss report
   * @param accessToken Authorization token for user set in header of each request
   * @return ReportWithRows
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ReportWithRows getReportProfitAndLoss(
      String accessToken,
      String xeroTenantId,
      LocalDate fromDate,
      LocalDate toDate,
      Integer periods,
      String timeframe,
      String trackingCategoryID,
      String trackingCategoryID2,
      String trackingOptionID,
      String trackingOptionID2,
      Boolean standardLayout,
      Boolean paymentsOnly)
      throws IOException {
    try {
      TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
      HttpResponse response =
          getReportProfitAndLossForHttpResponse(
              accessToken,
              xeroTenantId,
              fromDate,
              toDate,
              periods,
              timeframe,
              trackingCategoryID,
              trackingCategoryID2,
              trackingOptionID,
              trackingOptionID2,
              standardLayout,
              paymentsOnly);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReportProfitAndLoss -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReportProfitAndLossForHttpResponse(
      String accessToken,
      String xeroTenantId,
      LocalDate fromDate,
      LocalDate toDate,
      Integer periods,
      String timeframe,
      String trackingCategoryID,
      String trackingCategoryID2,
      String trackingOptionID,
      String trackingOptionID2,
      Boolean standardLayout,
      Boolean paymentsOnly)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReportProfitAndLoss");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReportProfitAndLoss");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Reports/ProfitAndLoss");
    if (fromDate != null) {
      String key = "fromDate";
      Object value = fromDate;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (toDate != null) {
      String key = "toDate";
      Object value = toDate;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (periods != null) {
      String key = "periods";
      Object value = periods;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (timeframe != null) {
      String key = "timeframe";
      Object value = timeframe;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (trackingCategoryID != null) {
      String key = "trackingCategoryID";
      Object value = trackingCategoryID;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (trackingCategoryID2 != null) {
      String key = "trackingCategoryID2";
      Object value = trackingCategoryID2;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (trackingOptionID != null) {
      String key = "trackingOptionID";
      Object value = trackingOptionID;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (trackingOptionID2 != null) {
      String key = "trackingOptionID2";
      Object value = trackingOptionID2;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (standardLayout != null) {
      String key = "standardLayout";
      Object value = standardLayout;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (paymentsOnly != null) {
      String key = "paymentsOnly";
      Object value = paymentsOnly;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve report for TenNinetyNine
   *
   * <p><b>200</b> - Success - return response of type Reports
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param reportYear The year of the 1099 report
   * @param accessToken Authorization token for user set in header of each request
   * @return Reports
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Reports getReportTenNinetyNine(String accessToken, String xeroTenantId, String reportYear)
      throws IOException {
    try {
      TypeReference<Reports> typeRef = new TypeReference<Reports>() {};
      HttpResponse response =
          getReportTenNinetyNineForHttpResponse(accessToken, xeroTenantId, reportYear);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReportTenNinetyNine -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReportTenNinetyNineForHttpResponse(
      String accessToken, String xeroTenantId, String reportYear) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReportTenNinetyNine");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReportTenNinetyNine");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Reports/TenNinetyNine");
    if (reportYear != null) {
      String key = "reportYear";
      Object value = reportYear;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve report for TrialBalance
   *
   * <p><b>200</b> - Success - return response of type ReportWithRows
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param date The date for the Trial Balance report e.g. 2018-03-31
   * @param paymentsOnly Return cash only basis for the Trial Balance report
   * @param accessToken Authorization token for user set in header of each request
   * @return ReportWithRows
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ReportWithRows getReportTrialBalance(
      String accessToken, String xeroTenantId, LocalDate date, Boolean paymentsOnly)
      throws IOException {
    try {
      TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
      HttpResponse response =
          getReportTrialBalanceForHttpResponse(accessToken, xeroTenantId, date, paymentsOnly);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getReportTrialBalance -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getReportTrialBalanceForHttpResponse(
      String accessToken, String xeroTenantId, LocalDate date, Boolean paymentsOnly)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getReportTrialBalance");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getReportTrialBalance");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Reports/TrialBalance");
    if (date != null) {
      String key = "date";
      Object value = date;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (paymentsOnly != null) {
      String key = "paymentsOnly";
      Object value = paymentsOnly;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve Tax Rates
   *
   * <p><b>200</b> - Success - return response of type TaxRates array with TaxRates
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param taxType Filter by tax type
   * @param accessToken Authorization token for user set in header of each request
   * @return TaxRates
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TaxRates getTaxRates(
      String accessToken, String xeroTenantId, String where, String order, String taxType)
      throws IOException {
    try {
      TypeReference<TaxRates> typeRef = new TypeReference<TaxRates>() {};
      HttpResponse response =
          getTaxRatesForHttpResponse(accessToken, xeroTenantId, where, order, taxType);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getTaxRates -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getTaxRatesForHttpResponse(
      String accessToken, String xeroTenantId, String where, String order, String taxType)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getTaxRates");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getTaxRates");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/TaxRates");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (taxType != null) {
      String key = "TaxType";
      Object value = taxType;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve tracking categories and options
   *
   * <p><b>200</b> - Success - return response of type TrackingCategories array of TrackingCategory
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param includeArchived e.g. includeArchived&#x3D;true - Categories and options with a status of
   *     ARCHIVED will be included in the response
   * @param accessToken Authorization token for user set in header of each request
   * @return TrackingCategories
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TrackingCategories getTrackingCategories(
      String accessToken, String xeroTenantId, String where, String order, Boolean includeArchived)
      throws IOException {
    try {
      TypeReference<TrackingCategories> typeRef = new TypeReference<TrackingCategories>() {};
      HttpResponse response =
          getTrackingCategoriesForHttpResponse(
              accessToken, xeroTenantId, where, order, includeArchived);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getTrackingCategories -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getTrackingCategoriesForHttpResponse(
      String accessToken, String xeroTenantId, String where, String order, Boolean includeArchived)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getTrackingCategories");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getTrackingCategories");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/TrackingCategories");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (includeArchived != null) {
      String key = "includeArchived";
      Object value = includeArchived;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve tracking categories and options for specified category
   *
   * <p><b>200</b> - Success - return response of type TrackingCategories array of specified
   * TrackingCategory
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param trackingCategoryID Unique identifier for a TrackingCategory
   * @param accessToken Authorization token for user set in header of each request
   * @return TrackingCategories
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TrackingCategories getTrackingCategory(
      String accessToken, String xeroTenantId, UUID trackingCategoryID) throws IOException {
    try {
      TypeReference<TrackingCategories> typeRef = new TypeReference<TrackingCategories>() {};
      HttpResponse response =
          getTrackingCategoryForHttpResponse(accessToken, xeroTenantId, trackingCategoryID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getTrackingCategory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getTrackingCategoryForHttpResponse(
      String accessToken, String xeroTenantId, UUID trackingCategoryID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getTrackingCategory");
    } // verify the required parameter 'trackingCategoryID' is set
    if (trackingCategoryID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'trackingCategoryID' when calling getTrackingCategory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getTrackingCategory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TrackingCategoryID", trackingCategoryID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/TrackingCategories/{TrackingCategoryID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified user
   *
   * <p><b>200</b> - Success - return response of type Users array of specified User
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param userID Unique identifier for a User
   * @param accessToken Authorization token for user set in header of each request
   * @return Users
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Users getUser(String accessToken, String xeroTenantId, UUID userID) throws IOException {
    try {
      TypeReference<Users> typeRef = new TypeReference<Users>() {};
      HttpResponse response = getUserForHttpResponse(accessToken, xeroTenantId, userID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getUser -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getUserForHttpResponse(String accessToken, String xeroTenantId, UUID userID)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getUser");
    } // verify the required parameter 'userID' is set
    if (userID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'userID' when calling getUser");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getUser");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("UserID", userID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Users/{UserID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve users
   *
   * <p><b>200</b> - Success - return response of type Users array of all User
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param accessToken Authorization token for user set in header of each request
   * @return Users
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Users getUsers(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order)
      throws IOException {
    try {
      TypeReference<Users> typeRef = new TypeReference<Users>() {};
      HttpResponse response =
          getUsersForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getUsers -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getUsersForHttpResponse(
      String accessToken,
      String xeroTenantId,
      OffsetDateTime ifModifiedSince,
      String where,
      String order)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getUsers");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getUsers");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    if (ifModifiedSince != null) {
      headers.setIfModifiedSince(ifModifiedSince.toString());
    }
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Users");
    if (where != null) {
      String key = "where";
      Object value = where;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (order != null) {
      String key = "order";
      Object value = order;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("GET " + genericUrl.toString());
    }

    HttpContent content = null;
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.GET, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update a chart of accounts
   *
   * <p><b>200</b> - Success - update existing Account and return response of type Accounts array
   * with updated Account
   *
   * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accountID Unique identifier for retrieving single object
   * @param accounts Request of type Accounts array with one Account
   * @param accessToken Authorization token for user set in header of each request
   * @return Accounts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Accounts updateAccount(
      String accessToken, String xeroTenantId, UUID accountID, Accounts accounts)
      throws IOException {
    try {
      TypeReference<Accounts> typeRef = new TypeReference<Accounts>() {};
      HttpResponse response =
          updateAccountForHttpResponse(accessToken, xeroTenantId, accountID, accounts);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateAccount -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Accounts", object.getMessage());
        }
        handler.validationError("Accounts", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateAccountForHttpResponse(
      String accessToken, String xeroTenantId, UUID accountID, Accounts accounts)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateAccount");
    } // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accountID' when calling updateAccount");
    } // verify the required parameter 'accounts' is set
    if (accounts == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accounts' when calling updateAccount");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateAccount");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("AccountID", accountID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Accounts/{AccountID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(accounts);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update Attachment on Account by Filename
   *
   * <p><b>200</b> - Success - return response of type Attachments array of Attachment
   *
   * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accountID Unique identifier for Account object
   * @param fileName Name of the attachment
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments updateAccountAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID accountID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          updateAccountAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, accountID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateAccountAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateAccountAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID accountID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateAccountAttachmentByFileName");
    } // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accountID' when calling"
              + " updateAccountAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " updateAccountAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling updateAccountAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " updateAccountAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("AccountID", accountID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Accounts/{AccountID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update a single spend or receive money transaction
   *
   * <p><b>200</b> - Success - return response of type BankTransactions array with updated
   * BankTransaction
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransactionID Xero generated unique identifier for a bank transaction
   * @param bankTransactions The bankTransactions parameter
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return BankTransactions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public BankTransactions updateBankTransaction(
      String accessToken,
      String xeroTenantId,
      UUID bankTransactionID,
      BankTransactions bankTransactions,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<BankTransactions> typeRef = new TypeReference<BankTransactions>() {};
      HttpResponse response =
          updateBankTransactionForHttpResponse(
              accessToken, xeroTenantId, bankTransactionID, bankTransactions, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateBankTransaction -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("BankTransactions", object.getMessage());
        }
        handler.validationError("BankTransactions", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateBankTransactionForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID bankTransactionID,
      BankTransactions bankTransactions,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateBankTransaction");
    } // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransactionID' when calling updateBankTransaction");
    } // verify the required parameter 'bankTransactions' is set
    if (bankTransactions == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransactions' when calling updateBankTransaction");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateBankTransaction");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransactionID", bankTransactionID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/BankTransactions/{BankTransactionID}");
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(bankTransactions);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update an Attachment on BankTransaction by Filename
   *
   * <p><b>200</b> - Success - return response of Attachments array of Attachment
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransactionID Xero generated unique identifier for a bank transaction
   * @param fileName The name of the file being attached
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments updateBankTransactionAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID bankTransactionID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          updateBankTransactionAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, bankTransactionID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateBankTransactionAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateBankTransactionAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID bankTransactionID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateBankTransactionAttachmentByFileName");
    } // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransactionID' when calling"
              + " updateBankTransactionAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " updateBankTransactionAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling"
              + " updateBankTransactionAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " updateBankTransactionAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransactionID", bankTransactionID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/BankTransactions/{BankTransactionID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * <b>200</b> - Success - return response of Attachments array of 0 to N Attachment for a Bank
   * Transfer
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransferID Xero generated unique identifier for a bank transfer
   * @param fileName The name of the file being attached to a Bank Transfer
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments updateBankTransferAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID bankTransferID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          updateBankTransferAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, bankTransferID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateBankTransferAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateBankTransferAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID bankTransferID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateBankTransferAttachmentByFileName");
    } // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransferID' when calling"
              + " updateBankTransferAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " updateBankTransferAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling"
              + " updateBankTransferAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " updateBankTransferAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("BankTransferID", bankTransferID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/BankTransfers/{BankTransferID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * <b>200</b> - Success - return response of type Contacts array with an updated Contact
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactID Unique identifier for a Contact
   * @param contacts an array of Contacts containing single Contact object with properties to update
   * @param accessToken Authorization token for user set in header of each request
   * @return Contacts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Contacts updateContact(
      String accessToken, String xeroTenantId, UUID contactID, Contacts contacts)
      throws IOException {
    try {
      TypeReference<Contacts> typeRef = new TypeReference<Contacts>() {};
      HttpResponse response =
          updateContactForHttpResponse(accessToken, xeroTenantId, contactID, contacts);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateContact -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Contacts", object.getMessage());
        }
        handler.validationError("Contacts", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateContactForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactID, Contacts contacts)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateContact");
    } // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactID' when calling updateContact");
    } // verify the required parameter 'contacts' is set
    if (contacts == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contacts' when calling updateContact");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateContact");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactID", contactID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Contacts/{ContactID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(contacts);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * <b>200</b> - Success - return response of type Attachments array with an updated Attachment
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactID Unique identifier for a Contact
   * @param fileName Name for the file you are attaching
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments updateContactAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID contactID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          updateContactAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, contactID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateContactAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateContactAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateContactAttachmentByFileName");
    } // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactID' when calling"
              + " updateContactAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " updateContactAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling updateContactAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " updateContactAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactID", contactID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Contacts/{ContactID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update a Contact Group
   *
   * <p><b>200</b> - Success - return response of type Contact Groups array of updated Contact Group
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contactGroupID Unique identifier for a Contact Group
   * @param contactGroups an array of Contact groups with Name of specific group to update
   * @param accessToken Authorization token for user set in header of each request
   * @return ContactGroups
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ContactGroups updateContactGroup(
      String accessToken, String xeroTenantId, UUID contactGroupID, ContactGroups contactGroups)
      throws IOException {
    try {
      TypeReference<ContactGroups> typeRef = new TypeReference<ContactGroups>() {};
      HttpResponse response =
          updateContactGroupForHttpResponse(
              accessToken, xeroTenantId, contactGroupID, contactGroups);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateContactGroup -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("ContactGroups", object.getMessage());
        }
        handler.validationError("ContactGroups", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateContactGroupForHttpResponse(
      String accessToken, String xeroTenantId, UUID contactGroupID, ContactGroups contactGroups)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateContactGroup");
    } // verify the required parameter 'contactGroupID' is set
    if (contactGroupID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactGroupID' when calling updateContactGroup");
    } // verify the required parameter 'contactGroups' is set
    if (contactGroups == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contactGroups' when calling updateContactGroup");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateContactGroup");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ContactGroupID", contactGroupID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/ContactGroups/{ContactGroupID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(contactGroups);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update a specific credit note
   *
   * <p><b>200</b> - Success - return response of type Credit Notes array with updated CreditNote
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNoteID Unique identifier for a Credit Note
   * @param creditNotes an array of Credit Notes containing credit note details to update
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return CreditNotes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public CreditNotes updateCreditNote(
      String accessToken,
      String xeroTenantId,
      UUID creditNoteID,
      CreditNotes creditNotes,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<CreditNotes> typeRef = new TypeReference<CreditNotes>() {};
      HttpResponse response =
          updateCreditNoteForHttpResponse(
              accessToken, xeroTenantId, creditNoteID, creditNotes, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateCreditNote -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("CreditNotes", object.getMessage());
        }
        handler.validationError("CreditNotes", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateCreditNoteForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID creditNoteID,
      CreditNotes creditNotes,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateCreditNote");
    } // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNoteID' when calling updateCreditNote");
    } // verify the required parameter 'creditNotes' is set
    if (creditNotes == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNotes' when calling updateCreditNote");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateCreditNote");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("CreditNoteID", creditNoteID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/CreditNotes/{CreditNoteID}");
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(creditNotes);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update Attachments on CreditNote by file name
   *
   * <p><b>200</b> - Success - return response of type Attachments array with updated Attachment for
   * specific Credit Note
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNoteID Unique identifier for a Credit Note
   * @param fileName Name of the file you are attaching to Credit Note
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments updateCreditNoteAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID creditNoteID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          updateCreditNoteAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, creditNoteID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateCreditNoteAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateCreditNoteAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID creditNoteID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateCreditNoteAttachmentByFileName");
    } // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNoteID' when calling"
              + " updateCreditNoteAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " updateCreditNoteAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling"
              + " updateCreditNoteAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " updateCreditNoteAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("CreditNoteID", creditNoteID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/CreditNotes/{CreditNoteID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update specified expense claims
   *
   * <p><b>200</b> - Success - return response of type ExpenseClaims array with updated ExpenseClaim
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param expenseClaimID Unique identifier for a ExpenseClaim
   * @param expenseClaims The expenseClaims parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return ExpenseClaims
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ExpenseClaims updateExpenseClaim(
      String accessToken, String xeroTenantId, UUID expenseClaimID, ExpenseClaims expenseClaims)
      throws IOException {
    try {
      TypeReference<ExpenseClaims> typeRef = new TypeReference<ExpenseClaims>() {};
      HttpResponse response =
          updateExpenseClaimForHttpResponse(
              accessToken, xeroTenantId, expenseClaimID, expenseClaims);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateExpenseClaim -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("ExpenseClaims", object.getMessage());
        }
        handler.validationError("ExpenseClaims", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateExpenseClaimForHttpResponse(
      String accessToken, String xeroTenantId, UUID expenseClaimID, ExpenseClaims expenseClaims)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateExpenseClaim");
    } // verify the required parameter 'expenseClaimID' is set
    if (expenseClaimID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'expenseClaimID' when calling updateExpenseClaim");
    } // verify the required parameter 'expenseClaims' is set
    if (expenseClaims == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'expenseClaims' when calling updateExpenseClaim");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateExpenseClaim");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ExpenseClaimID", expenseClaimID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/ExpenseClaims/{ExpenseClaimID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(expenseClaims);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update a specified sales invoices or purchase bills
   *
   * <p><b>200</b> - Success - return response of type Invoices array with updated Invoice
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoiceID Unique identifier for an Invoice
   * @param invoices The invoices parameter
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Invoices
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Invoices updateInvoice(
      String accessToken, String xeroTenantId, UUID invoiceID, Invoices invoices, Integer unitdp)
      throws IOException {
    try {
      TypeReference<Invoices> typeRef = new TypeReference<Invoices>() {};
      HttpResponse response =
          updateInvoiceForHttpResponse(accessToken, xeroTenantId, invoiceID, invoices, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateInvoice -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Invoices", object.getMessage());
        }
        handler.validationError("Invoices", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateInvoiceForHttpResponse(
      String accessToken, String xeroTenantId, UUID invoiceID, Invoices invoices, Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateInvoice");
    } // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoiceID' when calling updateInvoice");
    } // verify the required parameter 'invoices' is set
    if (invoices == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoices' when calling updateInvoice");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateInvoice");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("InvoiceID", invoiceID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Invoices/{InvoiceID}");
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(invoices);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update Attachment on invoices or purchase bills by it&#39;s filename
   *
   * <p><b>200</b> - Success - return response of type Attachments array with updated Attachment
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoiceID Unique identifier for an Invoice
   * @param fileName Name of the file you are attaching
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments updateInvoiceAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID invoiceID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          updateInvoiceAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, invoiceID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateInvoiceAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateInvoiceAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID invoiceID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateInvoiceAttachmentByFileName");
    } // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoiceID' when calling"
              + " updateInvoiceAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " updateInvoiceAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling updateInvoiceAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " updateInvoiceAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("InvoiceID", invoiceID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Invoices/{InvoiceID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update a specified item
   *
   * <p><b>200</b> - Success - return response of type Items array with updated Item
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param itemID Unique identifier for an Item
   * @param items The items parameter
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Items
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Items updateItem(
      String accessToken, String xeroTenantId, UUID itemID, Items items, Integer unitdp)
      throws IOException {
    try {
      TypeReference<Items> typeRef = new TypeReference<Items>() {};
      HttpResponse response =
          updateItemForHttpResponse(accessToken, xeroTenantId, itemID, items, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateItem -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Items", object.getMessage());
        }
        handler.validationError("Items", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateItemForHttpResponse(
      String accessToken, String xeroTenantId, UUID itemID, Items items, Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateItem");
    } // verify the required parameter 'itemID' is set
    if (itemID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'itemID' when calling updateItem");
    } // verify the required parameter 'items' is set
    if (items == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'items' when calling updateItem");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateItem");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ItemID", itemID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Items/{ItemID}");
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(items);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update a specified linked transactions (billable expenses)
   *
   * <p><b>200</b> - Success - return response of type LinkedTransactions array with updated
   * LinkedTransaction
   *
   * <p><b>400</b> - Success - return response of type LinkedTransactions array with updated
   * LinkedTransaction
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param linkedTransactionID Unique identifier for a LinkedTransaction
   * @param linkedTransactions The linkedTransactions parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return LinkedTransactions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public LinkedTransactions updateLinkedTransaction(
      String accessToken,
      String xeroTenantId,
      UUID linkedTransactionID,
      LinkedTransactions linkedTransactions)
      throws IOException {
    try {
      TypeReference<LinkedTransactions> typeRef = new TypeReference<LinkedTransactions>() {};
      HttpResponse response =
          updateLinkedTransactionForHttpResponse(
              accessToken, xeroTenantId, linkedTransactionID, linkedTransactions);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateLinkedTransaction -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("LinkedTransactions", object.getMessage());
        }
        handler.validationError("LinkedTransactions", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateLinkedTransactionForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID linkedTransactionID,
      LinkedTransactions linkedTransactions)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateLinkedTransaction");
    } // verify the required parameter 'linkedTransactionID' is set
    if (linkedTransactionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'linkedTransactionID' when calling"
              + " updateLinkedTransaction");
    } // verify the required parameter 'linkedTransactions' is set
    if (linkedTransactions == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'linkedTransactions' when calling"
              + " updateLinkedTransaction");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateLinkedTransaction");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("LinkedTransactionID", linkedTransactionID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/LinkedTransactions/{LinkedTransactionID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(linkedTransactions);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update a specified manual journal
   *
   * <p><b>200</b> - Success - return response of type ManualJournals array with an updated
   * ManualJournal
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param manualJournalID Unique identifier for a ManualJournal
   * @param manualJournals The manualJournals parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return ManualJournals
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ManualJournals updateManualJournal(
      String accessToken, String xeroTenantId, UUID manualJournalID, ManualJournals manualJournals)
      throws IOException {
    try {
      TypeReference<ManualJournals> typeRef = new TypeReference<ManualJournals>() {};
      HttpResponse response =
          updateManualJournalForHttpResponse(
              accessToken, xeroTenantId, manualJournalID, manualJournals);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateManualJournal -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("ManualJournals", object.getMessage());
        }
        handler.validationError("ManualJournals", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateManualJournalForHttpResponse(
      String accessToken, String xeroTenantId, UUID manualJournalID, ManualJournals manualJournals)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateManualJournal");
    } // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'manualJournalID' when calling updateManualJournal");
    } // verify the required parameter 'manualJournals' is set
    if (manualJournals == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'manualJournals' when calling updateManualJournal");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateManualJournal");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ManualJournalID", manualJournalID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/ManualJournals/{ManualJournalID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(manualJournals);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update a specified Attachment on ManualJournal by file name
   *
   * <p><b>200</b> - Success - return response of type Attachments array with an update Attachment
   * for a ManualJournals
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param manualJournalID Unique identifier for a ManualJournal
   * @param fileName The name of the file being attached to a ManualJournal
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments updateManualJournalAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID manualJournalID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          updateManualJournalAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, manualJournalID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateManualJournalAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateManualJournalAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID manualJournalID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateManualJournalAttachmentByFileName");
    } // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'manualJournalID' when calling"
              + " updateManualJournalAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " updateManualJournalAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling"
              + " updateManualJournalAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " updateManualJournalAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ManualJournalID", manualJournalID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/ManualJournals/{ManualJournalID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update or create one or more spend or receive money transaction
   *
   * <p><b>200</b> - Success - return response of type BankTransactions array with new
   * BankTransaction
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param bankTransactions The bankTransactions parameter
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return BankTransactions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public BankTransactions updateOrCreateBankTransactions(
      String accessToken,
      String xeroTenantId,
      BankTransactions bankTransactions,
      Boolean summarizeErrors,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<BankTransactions> typeRef = new TypeReference<BankTransactions>() {};
      HttpResponse response =
          updateOrCreateBankTransactionsForHttpResponse(
              accessToken, xeroTenantId, bankTransactions, summarizeErrors, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateOrCreateBankTransactions -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("BankTransactions", object.getMessage());
        }
        handler.validationError("BankTransactions", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateOrCreateBankTransactionsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      BankTransactions bankTransactions,
      Boolean summarizeErrors,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateOrCreateBankTransactions");
    } // verify the required parameter 'bankTransactions' is set
    if (bankTransactions == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'bankTransactions' when calling"
              + " updateOrCreateBankTransactions");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " updateOrCreateBankTransactions");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/BankTransactions");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(bankTransactions);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update OR create one or more contacts in a Xero organisation
   *
   * <p><b>200</b> - Success - return response of type Contacts array with newly created Contact
   *
   * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param contacts The contacts parameter
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return Contacts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Contacts updateOrCreateContacts(
      String accessToken, String xeroTenantId, Contacts contacts, Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<Contacts> typeRef = new TypeReference<Contacts>() {};
      HttpResponse response =
          updateOrCreateContactsForHttpResponse(
              accessToken, xeroTenantId, contacts, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateOrCreateContacts -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Contacts", object.getMessage());
        }
        handler.validationError("Contacts", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateOrCreateContactsForHttpResponse(
      String accessToken, String xeroTenantId, Contacts contacts, Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateOrCreateContacts");
    } // verify the required parameter 'contacts' is set
    if (contacts == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'contacts' when calling updateOrCreateContacts");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateOrCreateContacts");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Contacts");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(contacts);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update OR create one or more credit notes
   *
   * <p><b>200</b> - Success - return response of type Credit Notes array of newly created
   * CreditNote
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param creditNotes an array of Credit Notes with a single CreditNote object.
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return CreditNotes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public CreditNotes updateOrCreateCreditNotes(
      String accessToken,
      String xeroTenantId,
      CreditNotes creditNotes,
      Boolean summarizeErrors,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<CreditNotes> typeRef = new TypeReference<CreditNotes>() {};
      HttpResponse response =
          updateOrCreateCreditNotesForHttpResponse(
              accessToken, xeroTenantId, creditNotes, summarizeErrors, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateOrCreateCreditNotes -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("CreditNotes", object.getMessage());
        }
        handler.validationError("CreditNotes", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateOrCreateCreditNotesForHttpResponse(
      String accessToken,
      String xeroTenantId,
      CreditNotes creditNotes,
      Boolean summarizeErrors,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateOrCreateCreditNotes");
    } // verify the required parameter 'creditNotes' is set
    if (creditNotes == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'creditNotes' when calling updateOrCreateCreditNotes");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateOrCreateCreditNotes");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/CreditNotes");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(creditNotes);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a single new employees used in Xero payrun
   *
   * <p><b>200</b> - Success - return response of type Employees array with new Employee
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employees Employees with array of Employee object in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return Employees
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Employees updateOrCreateEmployees(
      String accessToken, String xeroTenantId, Employees employees, Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
      HttpResponse response =
          updateOrCreateEmployeesForHttpResponse(
              accessToken, xeroTenantId, employees, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateOrCreateEmployees -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Employees", object.getMessage());
        }
        handler.validationError("Employees", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateOrCreateEmployeesForHttpResponse(
      String accessToken, String xeroTenantId, Employees employees, Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateOrCreateEmployees");
    } // verify the required parameter 'employees' is set
    if (employees == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employees' when calling updateOrCreateEmployees");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateOrCreateEmployees");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Employees");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employees);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update OR create one or more sales invoices or purchase bills
   *
   * <p><b>200</b> - Success - return response of type Invoices array with newly created Invoice
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param invoices The invoices parameter
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Invoices
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Invoices updateOrCreateInvoices(
      String accessToken,
      String xeroTenantId,
      Invoices invoices,
      Boolean summarizeErrors,
      Integer unitdp)
      throws IOException {
    try {
      TypeReference<Invoices> typeRef = new TypeReference<Invoices>() {};
      HttpResponse response =
          updateOrCreateInvoicesForHttpResponse(
              accessToken, xeroTenantId, invoices, summarizeErrors, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateOrCreateInvoices -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Invoices", object.getMessage());
        }
        handler.validationError("Invoices", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateOrCreateInvoicesForHttpResponse(
      String accessToken,
      String xeroTenantId,
      Invoices invoices,
      Boolean summarizeErrors,
      Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateOrCreateInvoices");
    } // verify the required parameter 'invoices' is set
    if (invoices == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'invoices' when calling updateOrCreateInvoices");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateOrCreateInvoices");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Invoices");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(invoices);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update or create one or more items
   *
   * <p><b>200</b> - Success - return response of type Items array with newly created Item
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param items The items parameter
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Items
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Items updateOrCreateItems(
      String accessToken, String xeroTenantId, Items items, Boolean summarizeErrors, Integer unitdp)
      throws IOException {
    try {
      TypeReference<Items> typeRef = new TypeReference<Items>() {};
      HttpResponse response =
          updateOrCreateItemsForHttpResponse(
              accessToken, xeroTenantId, items, summarizeErrors, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateOrCreateItems -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Items", object.getMessage());
        }
        handler.validationError("Items", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateOrCreateItemsForHttpResponse(
      String accessToken, String xeroTenantId, Items items, Boolean summarizeErrors, Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateOrCreateItems");
    } // verify the required parameter 'items' is set
    if (items == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'items' when calling updateOrCreateItems");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateOrCreateItems");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Items");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(items);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to create a single manual journal
   *
   * <p><b>200</b> - Success - return response of type ManualJournals array with newly created
   * ManualJournal
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param manualJournals ManualJournals array with ManualJournal object in body of request
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return ManualJournals
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ManualJournals updateOrCreateManualJournals(
      String accessToken,
      String xeroTenantId,
      ManualJournals manualJournals,
      Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<ManualJournals> typeRef = new TypeReference<ManualJournals>() {};
      HttpResponse response =
          updateOrCreateManualJournalsForHttpResponse(
              accessToken, xeroTenantId, manualJournals, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateOrCreateManualJournals -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("ManualJournals", object.getMessage());
        }
        handler.validationError("ManualJournals", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateOrCreateManualJournalsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      ManualJournals manualJournals,
      Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateOrCreateManualJournals");
    } // verify the required parameter 'manualJournals' is set
    if (manualJournals == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'manualJournals' when calling"
              + " updateOrCreateManualJournals");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateOrCreateManualJournals");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/ManualJournals");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(manualJournals);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update or create one or more purchase orders
   *
   * <p><b>200</b> - Success - return response of type PurchaseOrder array for specified
   * PurchaseOrder
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param purchaseOrders The purchaseOrders parameter
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return PurchaseOrders
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PurchaseOrders updateOrCreatePurchaseOrders(
      String accessToken,
      String xeroTenantId,
      PurchaseOrders purchaseOrders,
      Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<PurchaseOrders> typeRef = new TypeReference<PurchaseOrders>() {};
      HttpResponse response =
          updateOrCreatePurchaseOrdersForHttpResponse(
              accessToken, xeroTenantId, purchaseOrders, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateOrCreatePurchaseOrders -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("PurchaseOrders", object.getMessage());
        }
        handler.validationError("PurchaseOrders", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateOrCreatePurchaseOrdersForHttpResponse(
      String accessToken,
      String xeroTenantId,
      PurchaseOrders purchaseOrders,
      Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateOrCreatePurchaseOrders");
    } // verify the required parameter 'purchaseOrders' is set
    if (purchaseOrders == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'purchaseOrders' when calling"
              + " updateOrCreatePurchaseOrders");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateOrCreatePurchaseOrders");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PurchaseOrders");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(purchaseOrders);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update OR create one or more quotes
   *
   * <p><b>200</b> - Success - return response of type Quotes array with updated or created Quote
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param quotes The quotes parameter
   * @param summarizeErrors If false return 200 OK and mix of successfully created obejcts and any
   *     with validation errors
   * @param accessToken Authorization token for user set in header of each request
   * @return Quotes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Quotes updateOrCreateQuotes(
      String accessToken, String xeroTenantId, Quotes quotes, Boolean summarizeErrors)
      throws IOException {
    try {
      TypeReference<Quotes> typeRef = new TypeReference<Quotes>() {};
      HttpResponse response =
          updateOrCreateQuotesForHttpResponse(accessToken, xeroTenantId, quotes, summarizeErrors);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateOrCreateQuotes -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Quotes", object.getMessage());
        }
        handler.validationError("Quotes", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateOrCreateQuotesForHttpResponse(
      String accessToken, String xeroTenantId, Quotes quotes, Boolean summarizeErrors)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateOrCreateQuotes");
    } // verify the required parameter 'quotes' is set
    if (quotes == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quotes' when calling updateOrCreateQuotes");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateOrCreateQuotes");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Quotes");
    if (summarizeErrors != null) {
      String key = "summarizeErrors";
      Object value = summarizeErrors;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(quotes);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update a specified purchase order
   *
   * <p><b>200</b> - Success - return response of type PurchaseOrder array for updated PurchaseOrder
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param purchaseOrderID Unique identifier for a PurchaseOrder
   * @param purchaseOrders The purchaseOrders parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return PurchaseOrders
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PurchaseOrders updatePurchaseOrder(
      String accessToken, String xeroTenantId, UUID purchaseOrderID, PurchaseOrders purchaseOrders)
      throws IOException {
    try {
      TypeReference<PurchaseOrders> typeRef = new TypeReference<PurchaseOrders>() {};
      HttpResponse response =
          updatePurchaseOrderForHttpResponse(
              accessToken, xeroTenantId, purchaseOrderID, purchaseOrders);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updatePurchaseOrder -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("PurchaseOrders", object.getMessage());
        }
        handler.validationError("PurchaseOrders", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updatePurchaseOrderForHttpResponse(
      String accessToken, String xeroTenantId, UUID purchaseOrderID, PurchaseOrders purchaseOrders)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updatePurchaseOrder");
    } // verify the required parameter 'purchaseOrderID' is set
    if (purchaseOrderID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'purchaseOrderID' when calling updatePurchaseOrder");
    } // verify the required parameter 'purchaseOrders' is set
    if (purchaseOrders == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'purchaseOrders' when calling updatePurchaseOrder");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updatePurchaseOrder");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PurchaseOrderID", purchaseOrderID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/PurchaseOrders/{PurchaseOrderID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(purchaseOrders);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update a specified quote
   *
   * <p><b>200</b> - Success - return response of type Quotes array with updated Quote
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param quoteID Unique identifier for an Quote
   * @param quotes The quotes parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return Quotes
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Quotes updateQuote(String accessToken, String xeroTenantId, UUID quoteID, Quotes quotes)
      throws IOException {
    try {
      TypeReference<Quotes> typeRef = new TypeReference<Quotes>() {};
      HttpResponse response =
          updateQuoteForHttpResponse(accessToken, xeroTenantId, quoteID, quotes);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateQuote -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Quotes", object.getMessage());
        }
        handler.validationError("Quotes", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateQuoteForHttpResponse(
      String accessToken, String xeroTenantId, UUID quoteID, Quotes quotes) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateQuote");
    } // verify the required parameter 'quoteID' is set
    if (quoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quoteID' when calling updateQuote");
    } // verify the required parameter 'quotes' is set
    if (quotes == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quotes' when calling updateQuote");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateQuote");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("QuoteID", quoteID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Quotes/{QuoteID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(quotes);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update Attachment on Quote by Filename
   *
   * <p><b>200</b> - Success - return response of type Attachments array of Attachment
   *
   * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param quoteID Unique identifier for Quote object
   * @param fileName Name of the attachment
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments updateQuoteAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID quoteID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          updateQuoteAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, quoteID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateQuoteAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateQuoteAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID quoteID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateQuoteAttachmentByFileName");
    } // verify the required parameter 'quoteID' is set
    if (quoteID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'quoteID' when calling updateQuoteAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling updateQuoteAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling updateQuoteAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " updateQuoteAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("QuoteID", quoteID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Quotes/{QuoteID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to retrieve a specified draft expense claim receipts
   *
   * <p><b>200</b> - Success - return response of type Receipts array for updated Receipt
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param receiptID Unique identifier for a Receipt
   * @param receipts The receipts parameter
   * @param unitdp e.g. unitdp&#x3D;4 – (Unit Decimal Places) You can opt in to use four decimal
   *     places for unit amounts
   * @param accessToken Authorization token for user set in header of each request
   * @return Receipts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Receipts updateReceipt(
      String accessToken, String xeroTenantId, UUID receiptID, Receipts receipts, Integer unitdp)
      throws IOException {
    try {
      TypeReference<Receipts> typeRef = new TypeReference<Receipts>() {};
      HttpResponse response =
          updateReceiptForHttpResponse(accessToken, xeroTenantId, receiptID, receipts, unitdp);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateReceipt -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Receipts", object.getMessage());
        }
        handler.validationError("Receipts", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateReceiptForHttpResponse(
      String accessToken, String xeroTenantId, UUID receiptID, Receipts receipts, Integer unitdp)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateReceipt");
    } // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'receiptID' when calling updateReceipt");
    } // verify the required parameter 'receipts' is set
    if (receipts == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'receipts' when calling updateReceipt");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateReceipt");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ReceiptID", receiptID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Receipts/{ReceiptID}");
    if (unitdp != null) {
      String key = "unitdp";
      Object value = unitdp;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(receipts);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update Attachment on expense claim receipts by file name
   *
   * <p><b>200</b> - Success - return response of type Attachments array with updated Attachment for
   * a specified Receipt
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param receiptID Unique identifier for a Receipt
   * @param fileName The name of the file being attached to the Receipt
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments updateReceiptAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID receiptID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          updateReceiptAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, receiptID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateReceiptAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateReceiptAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID receiptID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateReceiptAttachmentByFileName");
    } // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'receiptID' when calling"
              + " updateReceiptAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " updateReceiptAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling updateReceiptAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " updateReceiptAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("ReceiptID", receiptID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath() + "/Receipts/{ReceiptID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update specified attachment on repeating invoices by file name
   *
   * <p><b>200</b> - Success - return response of type Attachments array with specified Attachment
   * for a specified Repeating Invoice
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
   * @param fileName The name of the file being attached to a Repeating Invoice
   * @param body Byte array of file in body of request
   * @param accessToken Authorization token for user set in header of each request
   * @return Attachments
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Attachments updateRepeatingInvoiceAttachmentByFileName(
      String accessToken, String xeroTenantId, UUID repeatingInvoiceID, String fileName, File body)
      throws IOException {
    try {
      TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
      HttpResponse response =
          updateRepeatingInvoiceAttachmentByFileNameForHttpResponse(
              accessToken, xeroTenantId, repeatingInvoiceID, fileName, body);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateRepeatingInvoiceAttachmentByFileName -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("Attachments", object.getMessage());
        }
        handler.validationError("Attachments", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateRepeatingInvoiceAttachmentByFileNameForHttpResponse(
      String accessToken, String xeroTenantId, UUID repeatingInvoiceID, String fileName, File body)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling"
              + " updateRepeatingInvoiceAttachmentByFileName");
    } // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'repeatingInvoiceID' when calling"
              + " updateRepeatingInvoiceAttachmentByFileName");
    } // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'fileName' when calling"
              + " updateRepeatingInvoiceAttachmentByFileName");
    } // verify the required parameter 'body' is set
    if (body == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'body' when calling"
              + " updateRepeatingInvoiceAttachmentByFileName");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling"
              + " updateRepeatingInvoiceAttachmentByFileName");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);
    uriVariables.put("FileName", fileName);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }
    java.nio.file.Path bodyPath = body.toPath();
    String mimeType = Files.probeContentType(bodyPath);
    HttpContent content = null;
    content = new FileContent(mimeType, body);
    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update Tax Rates
   *
   * <p><b>200</b> - Success - return response of type TaxRates array updated TaxRate
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param taxRates The taxRates parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return TaxRates
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TaxRates updateTaxRate(String accessToken, String xeroTenantId, TaxRates taxRates)
      throws IOException {
    try {
      TypeReference<TaxRates> typeRef = new TypeReference<TaxRates>() {};
      HttpResponse response = updateTaxRateForHttpResponse(accessToken, xeroTenantId, taxRates);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateTaxRate -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("TaxRates", object.getMessage());
        }
        handler.validationError("TaxRates", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateTaxRateForHttpResponse(
      String accessToken, String xeroTenantId, TaxRates taxRates) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateTaxRate");
    } // verify the required parameter 'taxRates' is set
    if (taxRates == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'taxRates' when calling updateTaxRate");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateTaxRate");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/TaxRates");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(taxRates);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update tracking categories
   *
   * <p><b>200</b> - Success - return response of type TrackingCategories array of updated
   * TrackingCategory
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param trackingCategoryID Unique identifier for a TrackingCategory
   * @param trackingCategory The trackingCategory parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return TrackingCategories
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TrackingCategories updateTrackingCategory(
      String accessToken,
      String xeroTenantId,
      UUID trackingCategoryID,
      TrackingCategory trackingCategory)
      throws IOException {
    try {
      TypeReference<TrackingCategories> typeRef = new TypeReference<TrackingCategories>() {};
      HttpResponse response =
          updateTrackingCategoryForHttpResponse(
              accessToken, xeroTenantId, trackingCategoryID, trackingCategory);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateTrackingCategory -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("TrackingCategories", object.getMessage());
        }
        handler.validationError("TrackingCategories", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateTrackingCategoryForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID trackingCategoryID,
      TrackingCategory trackingCategory)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateTrackingCategory");
    } // verify the required parameter 'trackingCategoryID' is set
    if (trackingCategoryID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'trackingCategoryID' when calling"
              + " updateTrackingCategory");
    } // verify the required parameter 'trackingCategory' is set
    if (trackingCategory == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'trackingCategory' when calling updateTrackingCategory");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateTrackingCategory");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TrackingCategoryID", trackingCategoryID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/TrackingCategories/{TrackingCategoryID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(trackingCategory);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * Allows you to update options for a specified tracking category
   *
   * <p><b>200</b> - Success - return response of type TrackingOptions array of options for a
   * specified category
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param trackingCategoryID Unique identifier for a TrackingCategory
   * @param trackingOptionID Unique identifier for a Tracking Option
   * @param trackingOption The trackingOption parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return TrackingOptions
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TrackingOptions updateTrackingOptions(
      String accessToken,
      String xeroTenantId,
      UUID trackingCategoryID,
      UUID trackingOptionID,
      TrackingOption trackingOption)
      throws IOException {
    try {
      TypeReference<TrackingOptions> typeRef = new TypeReference<TrackingOptions>() {};
      HttpResponse response =
          updateTrackingOptionsForHttpResponse(
              accessToken, xeroTenantId, trackingCategoryID, trackingOptionID, trackingOption);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateTrackingOptions -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error object =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        if (object.getElements() == null || object.getElements().isEmpty()) {
          handler.validationError("TrackingOptions", object.getMessage());
        }
        handler.validationError("TrackingOptions", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateTrackingOptionsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID trackingCategoryID,
      UUID trackingOptionID,
      TrackingOption trackingOption)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateTrackingOptions");
    } // verify the required parameter 'trackingCategoryID' is set
    if (trackingCategoryID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'trackingCategoryID' when calling updateTrackingOptions");
    } // verify the required parameter 'trackingOptionID' is set
    if (trackingOptionID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'trackingOptionID' when calling updateTrackingOptions");
    } // verify the required parameter 'trackingOption' is set
    if (trackingOption == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'trackingOption' when calling updateTrackingOptions");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateTrackingOptions");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("xero-tenant-id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TrackingCategoryID", trackingCategoryID);
    uriVariables.put("TrackingOptionID", trackingOptionID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(
            apiClient.getBasePath()
                + "/TrackingCategories/{TrackingCategoryID}/Options/{TrackingOptionID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(trackingOption);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.POST, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  public ByteArrayInputStream convertInputToByteArray(InputStream is) throws IOException {
    byte[] bytes = IOUtils.toByteArray(is);
    try {
      // Process the input stream..
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
      return byteArrayInputStream;
    } finally {
      is.close();
    }
  }
}
