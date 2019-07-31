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
import com.xero.models.accounting.Contact;
import com.xero.models.accounting.ContactGroups;
import com.xero.models.accounting.Contacts;
import com.xero.models.accounting.CreditNotes;
import com.xero.models.accounting.Currencies;
import com.xero.models.accounting.Employees;
import com.xero.models.accounting.Error;
import com.xero.models.accounting.ExpenseClaims;
import java.io.File;
import com.xero.models.accounting.HistoryRecords;
import com.xero.models.accounting.InvoiceReminders;
import com.xero.models.accounting.Invoices;
import com.xero.models.accounting.Items;
import com.xero.models.accounting.Journals;
import com.xero.models.accounting.LinkedTransactions;
import org.threeten.bp.LocalDate;
import com.xero.models.accounting.ManualJournals;
import org.threeten.bp.OffsetDateTime;
import com.xero.models.accounting.OnlineInvoices;
import com.xero.models.accounting.Organisations;
import com.xero.models.accounting.Overpayments;
import com.xero.models.accounting.PaymentService;
import com.xero.models.accounting.PaymentServices;
import com.xero.models.accounting.Payments;
import com.xero.models.accounting.Prepayments;
import com.xero.models.accounting.PurchaseOrders;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.FileContent;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AccountingApi {
    private ApiClient apiClient;
    private String xeroTenantId;
    private String userAgent = "Default";
    private String version = "3.0.0-beta-7";

    public AccountingApi() {
        this(new ApiClient());
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

    public String getXeroTenantId() {
        return xeroTenantId;
    }

    public void setXeroTenantId(String xeroTenantId) {
        this.xeroTenantId = xeroTenantId;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public String getUserAgent() {
        return this.userAgent +  "[Xero-Java-" + this.version + "]";
    }

  /**
    * Allows you to create a new chart of accounts
    * <p><b>200</b> - Success - created new Account and return response of type Accounts array with new Account
    * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
    * @param xeroTenantId Xero identifier for Tenant
    * @param account Request of type Account
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts  createAccount(String xeroTenantId, Account account) throws IOException {
        HttpResponse response = createAccountForHttpResponse(xeroTenantId, account);
        TypeReference typeRef = new TypeReference<Accounts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createAccountForHttpResponse( String xeroTenantId,  Account account) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createAccount");
        }// verify the required parameter 'account' is set
        if (account == null) {
            throw new IllegalArgumentException("Missing the required parameter 'account' when calling createAccount");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Accounts";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(account);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create Attachment on Account
    * <p><b>200</b> - Success - return response of type Attachments array of Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param accountID Unique identifier for Account object
    * @param fileName Name of the attachment
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  createAccountAttachmentByFileName(String xeroTenantId, UUID accountID, String fileName, File body) throws IOException {
        HttpResponse response = createAccountAttachmentByFileNameForHttpResponse(xeroTenantId, accountID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createAccountAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID accountID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createAccountAttachmentByFileName");
        }// verify the required parameter 'accountID' is set
        if (accountID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accountID' when calling createAccountAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling createAccountAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling createAccountAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Accounts/{AccountID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("AccountID", accountID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create a spend or receive money transaction
    * <p><b>200</b> - Success - return response of type BankTransactions array with new BankTransaction
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransactions The bankTransactions parameter
    * @param summarizeErrors response format that shows validation errors for each bank transaction
    * @return BankTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransactions  createBankTransaction(String xeroTenantId, BankTransactions bankTransactions, Boolean summarizeErrors) throws IOException {
        HttpResponse response = createBankTransactionForHttpResponse(xeroTenantId, bankTransactions, summarizeErrors);
        TypeReference typeRef = new TypeReference<BankTransactions>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createBankTransactionForHttpResponse( String xeroTenantId,  BankTransactions bankTransactions,  Boolean summarizeErrors) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createBankTransaction");
        }// verify the required parameter 'bankTransactions' is set
        if (bankTransactions == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransactions' when calling createBankTransaction");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransactions";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        if (summarizeErrors != null) {
            String key = "SummarizeErrors";
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(bankTransactions);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to createa an Attachment on BankTransaction by Filename
    * <p><b>200</b> - Success - return response of Attachments array of Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @param fileName The name of the file being attached
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  createBankTransactionAttachmentByFileName(String xeroTenantId, UUID bankTransactionID, String fileName, File body) throws IOException {
        HttpResponse response = createBankTransactionAttachmentByFileNameForHttpResponse(xeroTenantId, bankTransactionID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createBankTransactionAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID bankTransactionID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createBankTransactionAttachmentByFileName");
        }// verify the required parameter 'bankTransactionID' is set
        if (bankTransactionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransactionID' when calling createBankTransactionAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling createBankTransactionAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling createBankTransactionAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransactionID", bankTransactionID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create history record for a bank transactions
    * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createBankTransactionHistoryRecord(String xeroTenantId, UUID bankTransactionID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createBankTransactionHistoryRecordForHttpResponse(xeroTenantId, bankTransactionID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createBankTransactionHistoryRecordForHttpResponse( String xeroTenantId,  UUID bankTransactionID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createBankTransactionHistoryRecord");
        }// verify the required parameter 'bankTransactionID' is set
        if (bankTransactionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransactionID' when calling createBankTransactionHistoryRecord");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createBankTransactionHistoryRecord");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransactions/{BankTransactionID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransactionID", bankTransactionID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create a bank transfers
    * <p><b>200</b> - Success - return response of BankTransfers array of one BankTransfer
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransfers The bankTransfers parameter
    * @return BankTransfers
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransfers  createBankTransfer(String xeroTenantId, BankTransfers bankTransfers) throws IOException {
        HttpResponse response = createBankTransferForHttpResponse(xeroTenantId, bankTransfers);
        TypeReference typeRef = new TypeReference<BankTransfers>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createBankTransferForHttpResponse( String xeroTenantId,  BankTransfers bankTransfers) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createBankTransfer");
        }// verify the required parameter 'bankTransfers' is set
        if (bankTransfers == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransfers' when calling createBankTransfer");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransfers";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(bankTransfers);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * <p><b>200</b> - Success - return response of Attachments array of 0 to N Attachment for a Bank Transfer
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @param fileName The name of the file being attached to a Bank Transfer
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  createBankTransferAttachmentByFileName(String xeroTenantId, UUID bankTransferID, String fileName, File body) throws IOException {
        HttpResponse response = createBankTransferAttachmentByFileNameForHttpResponse(xeroTenantId, bankTransferID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createBankTransferAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID bankTransferID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createBankTransferAttachmentByFileName");
        }// verify the required parameter 'bankTransferID' is set
        if (bankTransferID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransferID' when calling createBankTransferAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling createBankTransferAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling createBankTransferAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransfers/{BankTransferID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransferID", bankTransferID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * <p><b>200</b> - Success - return response HistoryRecords array with the newly created HistoryRecord for a Bank Transfer
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createBankTransferHistoryRecord(String xeroTenantId, UUID bankTransferID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createBankTransferHistoryRecordForHttpResponse(xeroTenantId, bankTransferID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createBankTransferHistoryRecordForHttpResponse( String xeroTenantId,  UUID bankTransferID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createBankTransferHistoryRecord");
        }// verify the required parameter 'bankTransferID' is set
        if (bankTransferID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransferID' when calling createBankTransferHistoryRecord");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createBankTransferHistoryRecord");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransfers/{BankTransferID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransferID", bankTransferID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Create one or many BatchPayments for invoices
    * <p><b>200</b> - Success - return response of type BatchPayments array of BatchPayment objects
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param batchPayments Request of type BatchPayments containing a Payments array with one or more Payment objects
    * @return BatchPayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BatchPayments  createBatchPayment(String xeroTenantId, BatchPayments batchPayments) throws IOException {
        HttpResponse response = createBatchPaymentForHttpResponse(xeroTenantId, batchPayments);
        TypeReference typeRef = new TypeReference<BatchPayments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createBatchPaymentForHttpResponse( String xeroTenantId,  BatchPayments batchPayments) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createBatchPayment");
        }// verify the required parameter 'batchPayments' is set
        if (batchPayments == null) {
            throw new IllegalArgumentException("Missing the required parameter 'batchPayments' when calling createBatchPayment");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BatchPayments";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(batchPayments);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create a history record for a Batch Payment
    * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param batchPaymentID Unique identifier for BatchPayment
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createBatchPaymentHistoryRecord(String xeroTenantId, UUID batchPaymentID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createBatchPaymentHistoryRecordForHttpResponse(xeroTenantId, batchPaymentID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createBatchPaymentHistoryRecordForHttpResponse( String xeroTenantId,  UUID batchPaymentID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createBatchPaymentHistoryRecord");
        }// verify the required parameter 'batchPaymentID' is set
        if (batchPaymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'batchPaymentID' when calling createBatchPaymentHistoryRecord");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createBatchPaymentHistoryRecord");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BatchPayments/{BatchPaymentID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BatchPaymentID", batchPaymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allow for the creation of new custom payment service for specified Branding Theme
    * <p><b>200</b> - Success - return response of type PaymentServices array with newly created PaymentService
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param brandingThemeID Unique identifier for a Branding Theme
    * @param paymentService The paymentService parameter
    * @return PaymentServices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PaymentServices  createBrandingThemePaymentServices(String xeroTenantId, UUID brandingThemeID, PaymentService paymentService) throws IOException {
        HttpResponse response = createBrandingThemePaymentServicesForHttpResponse(xeroTenantId, brandingThemeID, paymentService);
        TypeReference typeRef = new TypeReference<PaymentServices>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createBrandingThemePaymentServicesForHttpResponse( String xeroTenantId,  UUID brandingThemeID,  PaymentService paymentService) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createBrandingThemePaymentServices");
        }// verify the required parameter 'brandingThemeID' is set
        if (brandingThemeID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'brandingThemeID' when calling createBrandingThemePaymentServices");
        }// verify the required parameter 'paymentService' is set
        if (paymentService == null) {
            throw new IllegalArgumentException("Missing the required parameter 'paymentService' when calling createBrandingThemePaymentServices");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BrandingThemes/{BrandingThemeID}/PaymentServices";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BrandingThemeID", brandingThemeID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(paymentService);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * <p><b>200</b> - Success - return response of type Contacts array with newly created Contact
    * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
    * @param xeroTenantId Xero identifier for Tenant
    * @param contact The contact parameter
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts  createContact(String xeroTenantId, Contact contact) throws IOException {
        HttpResponse response = createContactForHttpResponse(xeroTenantId, contact);
        TypeReference typeRef = new TypeReference<Contacts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createContactForHttpResponse( String xeroTenantId,  Contact contact) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createContact");
        }// verify the required parameter 'contact' is set
        if (contact == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contact' when calling createContact");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Contacts";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(contact);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * <p><b>200</b> - Success - return response of type Attachments array with an newly created Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactID Unique identifier for a Contact
    * @param fileName Name for the file you are attaching
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  createContactAttachmentByFileName(String xeroTenantId, UUID contactID, String fileName, File body) throws IOException {
        HttpResponse response = createContactAttachmentByFileNameForHttpResponse(xeroTenantId, contactID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createContactAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID contactID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createContactAttachmentByFileName");
        }// verify the required parameter 'contactID' is set
        if (contactID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactID' when calling createContactAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling createContactAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling createContactAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Contacts/{ContactID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactID", contactID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create a contact group
    * <p><b>200</b> - Success - return response of type Contact Groups array of newly created Contact Group
    * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactGroups an array of contact groups with names specified
    * @return ContactGroups
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ContactGroups  createContactGroup(String xeroTenantId, ContactGroups contactGroups) throws IOException {
        HttpResponse response = createContactGroupForHttpResponse(xeroTenantId, contactGroups);
        TypeReference typeRef = new TypeReference<ContactGroups>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createContactGroupForHttpResponse( String xeroTenantId,  ContactGroups contactGroups) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createContactGroup");
        }// verify the required parameter 'contactGroups' is set
        if (contactGroups == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactGroups' when calling createContactGroup");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ContactGroups";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(contactGroups);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to add Contacts to a Contract Group
    * <p><b>200</b> - Success - return response of type Contacts array of added Contacts
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactGroupID Unique identifier for a Contact Group
    * @param contacts an array of contacts with ContactID to be added to ContactGroup
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts  createContactGroupContacts(String xeroTenantId, UUID contactGroupID, Contacts contacts) throws IOException {
        HttpResponse response = createContactGroupContactsForHttpResponse(xeroTenantId, contactGroupID, contacts);
        TypeReference typeRef = new TypeReference<Contacts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createContactGroupContactsForHttpResponse( String xeroTenantId,  UUID contactGroupID,  Contacts contacts) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createContactGroupContacts");
        }// verify the required parameter 'contactGroupID' is set
        if (contactGroupID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactGroupID' when calling createContactGroupContacts");
        }// verify the required parameter 'contacts' is set
        if (contacts == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contacts' when calling createContactGroupContacts");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ContactGroups/{ContactGroupID}/Contacts";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactGroupID", contactGroupID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(contacts);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a history records of an Contact
    * <p><b>200</b> - Success - return response of type History Records array of newly created History Record for a specific Contact
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactID Unique identifier for a Contact
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createContactHistory(String xeroTenantId, UUID contactID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createContactHistoryForHttpResponse(xeroTenantId, contactID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createContactHistoryForHttpResponse( String xeroTenantId,  UUID contactID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createContactHistory");
        }// verify the required parameter 'contactID' is set
        if (contactID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactID' when calling createContactHistory");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createContactHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Contacts/{ContactID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactID", contactID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create a credit note
    * <p><b>200</b> - Success - return response of type Credit Notes array of newly created CreditNote
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param creditNotes an array of Credit Notes with a single CreditNote object.
    * @param summarizeErrors shows validation errors for each credit note
    * @return CreditNotes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CreditNotes  createCreditNote(String xeroTenantId, CreditNotes creditNotes, Boolean summarizeErrors) throws IOException {
        HttpResponse response = createCreditNoteForHttpResponse(xeroTenantId, creditNotes, summarizeErrors);
        TypeReference typeRef = new TypeReference<CreditNotes>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createCreditNoteForHttpResponse( String xeroTenantId,  CreditNotes creditNotes,  Boolean summarizeErrors) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createCreditNote");
        }// verify the required parameter 'creditNotes' is set
        if (creditNotes == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNotes' when calling createCreditNote");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        if (summarizeErrors != null) {
            String key = "SummarizeErrors";
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(creditNotes);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create Allocation on CreditNote
    * <p><b>200</b> - Success - return response of type Allocations array with newly created Allocation for specific Credit Note
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param creditNoteID Unique identifier for a Credit Note
    * @param allocations an array of Allocations with single Allocation object defined.
    * @return Allocations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Allocations  createCreditNoteAllocation(String xeroTenantId, UUID creditNoteID, Allocations allocations) throws IOException {
        HttpResponse response = createCreditNoteAllocationForHttpResponse(xeroTenantId, creditNoteID, allocations);
        TypeReference typeRef = new TypeReference<Allocations>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createCreditNoteAllocationForHttpResponse( String xeroTenantId,  UUID creditNoteID,  Allocations allocations) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createCreditNoteAllocation");
        }// verify the required parameter 'creditNoteID' is set
        if (creditNoteID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNoteID' when calling createCreditNoteAllocation");
        }// verify the required parameter 'allocations' is set
        if (allocations == null) {
            throw new IllegalArgumentException("Missing the required parameter 'allocations' when calling createCreditNoteAllocation");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes/{CreditNoteID}/Allocations";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("CreditNoteID", creditNoteID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(allocations);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create Attachments on CreditNote by file name
    * <p><b>200</b> - Success - return response of type Attachments array with newly created Attachment for specific Credit Note
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param creditNoteID Unique identifier for a Credit Note
    * @param fileName Name of the file you are attaching to Credit Note
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  createCreditNoteAttachmentByFileName(String xeroTenantId, UUID creditNoteID, String fileName, File body) throws IOException {
        HttpResponse response = createCreditNoteAttachmentByFileNameForHttpResponse(xeroTenantId, creditNoteID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createCreditNoteAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID creditNoteID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createCreditNoteAttachmentByFileName");
        }// verify the required parameter 'creditNoteID' is set
        if (creditNoteID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNoteID' when calling createCreditNoteAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling createCreditNoteAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling createCreditNoteAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("CreditNoteID", creditNoteID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a history records of an CreditNote
    * <p><b>200</b> - Success - return response of type HistoryRecords array with newly created HistoryRecord for specific Credit Note
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param creditNoteID Unique identifier for a Credit Note
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createCreditNoteHistory(String xeroTenantId, UUID creditNoteID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createCreditNoteHistoryForHttpResponse(xeroTenantId, creditNoteID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createCreditNoteHistoryForHttpResponse( String xeroTenantId,  UUID creditNoteID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createCreditNoteHistory");
        }// verify the required parameter 'creditNoteID' is set
        if (creditNoteID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNoteID' when calling createCreditNoteHistory");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createCreditNoteHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes/{CreditNoteID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("CreditNoteID", creditNoteID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * <p><b>200</b> - Unsupported - return response incorrect exception, API is not able to create new Currency
    * @param xeroTenantId Xero identifier for Tenant
    * @param currencies The currencies parameter
    * @return Currencies
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Currencies  createCurrency(String xeroTenantId, Currencies currencies) throws IOException {
        HttpResponse response = createCurrencyForHttpResponse(xeroTenantId, currencies);
        TypeReference typeRef = new TypeReference<Currencies>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createCurrencyForHttpResponse( String xeroTenantId,  Currencies currencies) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createCurrency");
        }// verify the required parameter 'currencies' is set
        if (currencies == null) {
            throw new IllegalArgumentException("Missing the required parameter 'currencies' when calling createCurrency");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Currencies";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(currencies);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create new employees used in Xero payrun
    * <p><b>200</b> - Success - return response of type Employees array with new Employee
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param employees The employees parameter
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees  createEmployee(String xeroTenantId, Employees employees) throws IOException {
        HttpResponse response = createEmployeeForHttpResponse(xeroTenantId, employees);
        TypeReference typeRef = new TypeReference<Employees>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createEmployeeForHttpResponse( String xeroTenantId,  Employees employees) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createEmployee");
        }// verify the required parameter 'employees' is set
        if (employees == null) {
            throw new IllegalArgumentException("Missing the required parameter 'employees' when calling createEmployee");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Employees";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(employees);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve expense claims
    * <p><b>200</b> - Success - return response of type ExpenseClaims array with newly created ExpenseClaim
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param expenseClaims The expenseClaims parameter
    * @param summarizeErrors shows validation errors for each expense claim
    * @return ExpenseClaims
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ExpenseClaims  createExpenseClaim(String xeroTenantId, ExpenseClaims expenseClaims, Boolean summarizeErrors) throws IOException {
        HttpResponse response = createExpenseClaimForHttpResponse(xeroTenantId, expenseClaims, summarizeErrors);
        TypeReference typeRef = new TypeReference<ExpenseClaims>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createExpenseClaimForHttpResponse( String xeroTenantId,  ExpenseClaims expenseClaims,  Boolean summarizeErrors) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createExpenseClaim");
        }// verify the required parameter 'expenseClaims' is set
        if (expenseClaims == null) {
            throw new IllegalArgumentException("Missing the required parameter 'expenseClaims' when calling createExpenseClaim");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ExpenseClaims";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        if (summarizeErrors != null) {
            String key = "SummarizeErrors";
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(expenseClaims);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create a history records of an ExpenseClaim
    * <p><b>200</b> - Unsupported - return response incorrect exception, API is not able to create HistoryRecord for Expense Claims
    * @param xeroTenantId Xero identifier for Tenant
    * @param expenseClaimID Unique identifier for a ExpenseClaim
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createExpenseClaimHistory(String xeroTenantId, UUID expenseClaimID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createExpenseClaimHistoryForHttpResponse(xeroTenantId, expenseClaimID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createExpenseClaimHistoryForHttpResponse( String xeroTenantId,  UUID expenseClaimID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createExpenseClaimHistory");
        }// verify the required parameter 'expenseClaimID' is set
        if (expenseClaimID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'expenseClaimID' when calling createExpenseClaimHistory");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createExpenseClaimHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ExpenseClaims/{ExpenseClaimID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ExpenseClaimID", expenseClaimID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create any sales invoices or purchase bills
    * <p><b>200</b> - Success - return response of type Invoices array with newly created Invoice
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoices The invoices parameter
    * @param summarizeErrors shows validation errors for each invoice
    * @return Invoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Invoices  createInvoice(String xeroTenantId, Invoices invoices, Boolean summarizeErrors) throws IOException {
        HttpResponse response = createInvoiceForHttpResponse(xeroTenantId, invoices, summarizeErrors);
        TypeReference typeRef = new TypeReference<Invoices>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createInvoiceForHttpResponse( String xeroTenantId,  Invoices invoices,  Boolean summarizeErrors) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createInvoice");
        }// verify the required parameter 'invoices' is set
        if (invoices == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoices' when calling createInvoice");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        if (summarizeErrors != null) {
            String key = "SummarizeErrors";
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(invoices);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create an Attachment on invoices or purchase bills by it&#39;s filename
    * <p><b>200</b> - Success - return response of type Attachments array with newly created Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoiceID Unique identifier for an Invoice
    * @param fileName Name of the file you are attaching
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  createInvoiceAttachmentByFileName(String xeroTenantId, UUID invoiceID, String fileName, File body) throws IOException {
        HttpResponse response = createInvoiceAttachmentByFileNameForHttpResponse(xeroTenantId, invoiceID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createInvoiceAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID invoiceID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createInvoiceAttachmentByFileName");
        }// verify the required parameter 'invoiceID' is set
        if (invoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoiceID' when calling createInvoiceAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling createInvoiceAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling createInvoiceAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices/{InvoiceID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("InvoiceID", invoiceID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a history records of an invoice
    * <p><b>200</b> - Success - return response of type HistoryRecords array with newly created HistoryRecord for specific Invoice
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoiceID Unique identifier for an Invoice
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createInvoiceHistory(String xeroTenantId, UUID invoiceID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createInvoiceHistoryForHttpResponse(xeroTenantId, invoiceID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createInvoiceHistoryForHttpResponse( String xeroTenantId,  UUID invoiceID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createInvoiceHistory");
        }// verify the required parameter 'invoiceID' is set
        if (invoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoiceID' when calling createInvoiceHistory");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createInvoiceHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices/{InvoiceID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("InvoiceID", invoiceID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create an item
    * <p><b>200</b> - Success - return response of type Items array with newly created Item
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param items The items parameter
    * @return Items
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Items  createItem(String xeroTenantId, Items items) throws IOException {
        HttpResponse response = createItemForHttpResponse(xeroTenantId, items);
        TypeReference typeRef = new TypeReference<Items>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createItemForHttpResponse( String xeroTenantId,  Items items) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createItem");
        }// verify the required parameter 'items' is set
        if (items == null) {
            throw new IllegalArgumentException("Missing the required parameter 'items' when calling createItem");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Items";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(items);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create a history record for items
    * <p><b>200</b> - Unsupported - return response incorrect exception, API is not able to create HistoryRecord for Items
    * @param xeroTenantId Xero identifier for Tenant
    * @param itemID Unique identifier for an Item
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createItemHistory(String xeroTenantId, UUID itemID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createItemHistoryForHttpResponse(xeroTenantId, itemID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createItemHistoryForHttpResponse( String xeroTenantId,  UUID itemID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createItemHistory");
        }// verify the required parameter 'itemID' is set
        if (itemID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'itemID' when calling createItemHistory");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createItemHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Items/{ItemID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ItemID", itemID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create linked transactions (billable expenses)
    * <p><b>200</b> - Success - return response of type LinkedTransactions array with newly created LinkedTransaction
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param linkedTransactions The linkedTransactions parameter
    * @return LinkedTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LinkedTransactions  createLinkedTransaction(String xeroTenantId, LinkedTransactions linkedTransactions) throws IOException {
        HttpResponse response = createLinkedTransactionForHttpResponse(xeroTenantId, linkedTransactions);
        TypeReference typeRef = new TypeReference<LinkedTransactions>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createLinkedTransactionForHttpResponse( String xeroTenantId,  LinkedTransactions linkedTransactions) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createLinkedTransaction");
        }// verify the required parameter 'linkedTransactions' is set
        if (linkedTransactions == null) {
            throw new IllegalArgumentException("Missing the required parameter 'linkedTransactions' when calling createLinkedTransaction");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/LinkedTransactions";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(linkedTransactions);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create a manual journal
    * <p><b>200</b> - Success - return response of type ManualJournals array with newly created ManualJournal
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param manualJournals The manualJournals parameter
    * @return ManualJournals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ManualJournals  createManualJournal(String xeroTenantId, ManualJournals manualJournals) throws IOException {
        HttpResponse response = createManualJournalForHttpResponse(xeroTenantId, manualJournals);
        TypeReference typeRef = new TypeReference<ManualJournals>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createManualJournalForHttpResponse( String xeroTenantId,  ManualJournals manualJournals) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createManualJournal");
        }// verify the required parameter 'manualJournals' is set
        if (manualJournals == null) {
            throw new IllegalArgumentException("Missing the required parameter 'manualJournals' when calling createManualJournal");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ManualJournals";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(manualJournals);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create a specified Attachment on ManualJournal by file name
    * <p><b>200</b> - Success - return response of type Attachments array with a newly created Attachment for a ManualJournals
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param manualJournalID Unique identifier for a ManualJournal
    * @param fileName The name of the file being attached to a ManualJournal
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  createManualJournalAttachmentByFileName(String xeroTenantId, UUID manualJournalID, String fileName, File body) throws IOException {
        HttpResponse response = createManualJournalAttachmentByFileNameForHttpResponse(xeroTenantId, manualJournalID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createManualJournalAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID manualJournalID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createManualJournalAttachmentByFileName");
        }// verify the required parameter 'manualJournalID' is set
        if (manualJournalID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'manualJournalID' when calling createManualJournalAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling createManualJournalAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling createManualJournalAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ManualJournalID", manualJournalID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Allocations for overpayments
    * <p><b>200</b> - Success - return response of type Allocations array with all Allocation for Overpayments
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param overpaymentID Unique identifier for a Overpayment
    * @param allocations The allocations parameter
    * @return Allocations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Allocations  createOverpaymentAllocation(String xeroTenantId, UUID overpaymentID, Allocations allocations) throws IOException {
        HttpResponse response = createOverpaymentAllocationForHttpResponse(xeroTenantId, overpaymentID, allocations);
        TypeReference typeRef = new TypeReference<Allocations>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createOverpaymentAllocationForHttpResponse( String xeroTenantId,  UUID overpaymentID,  Allocations allocations) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createOverpaymentAllocation");
        }// verify the required parameter 'overpaymentID' is set
        if (overpaymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'overpaymentID' when calling createOverpaymentAllocation");
        }// verify the required parameter 'allocations' is set
        if (allocations == null) {
            throw new IllegalArgumentException("Missing the required parameter 'allocations' when calling createOverpaymentAllocation");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Overpayments/{OverpaymentID}/Allocations";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("OverpaymentID", overpaymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(allocations);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create history records of an Overpayment
    * <p><b>200</b> - Unsupported - return response incorrect exception, API is not able to create HistoryRecord for Overpayments
    * <p><b>400</b> - A failed request due to validation error - API is not able to create HistoryRecord for Overpayments
    * @param xeroTenantId Xero identifier for Tenant
    * @param overpaymentID Unique identifier for a Overpayment
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createOverpaymentHistory(String xeroTenantId, UUID overpaymentID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createOverpaymentHistoryForHttpResponse(xeroTenantId, overpaymentID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createOverpaymentHistoryForHttpResponse( String xeroTenantId,  UUID overpaymentID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createOverpaymentHistory");
        }// verify the required parameter 'overpaymentID' is set
        if (overpaymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'overpaymentID' when calling createOverpaymentHistory");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createOverpaymentHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Overpayments/{OverpaymentID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("OverpaymentID", overpaymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create payments for invoices and credit notes
    * <p><b>200</b> - Success - return response of type Payments array for newly created Payment
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param payments The payments parameter
    * @return Payments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payments  createPayment(String xeroTenantId, Payments payments) throws IOException {
        HttpResponse response = createPaymentForHttpResponse(xeroTenantId, payments);
        TypeReference typeRef = new TypeReference<Payments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createPaymentForHttpResponse( String xeroTenantId,  Payments payments) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createPayment");
        }// verify the required parameter 'payments' is set
        if (payments == null) {
            throw new IllegalArgumentException("Missing the required parameter 'payments' when calling createPayment");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payments";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(payments);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create a history record for a payment
    * <p><b>200</b> - Unsupported - return response incorrect exception, API is not able to create HistoryRecord for Payments
    * <p><b>400</b> - A failed request due to validation error - API is not able to create HistoryRecord for Payments
    * @param xeroTenantId Xero identifier for Tenant
    * @param paymentID Unique identifier for a Payment
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createPaymentHistory(String xeroTenantId, UUID paymentID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createPaymentHistoryForHttpResponse(xeroTenantId, paymentID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createPaymentHistoryForHttpResponse( String xeroTenantId,  UUID paymentID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createPaymentHistory");
        }// verify the required parameter 'paymentID' is set
        if (paymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'paymentID' when calling createPaymentHistory");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createPaymentHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payments/{PaymentID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PaymentID", paymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create payment services
    * <p><b>200</b> - Success - return response of type PaymentServices array for newly created PaymentService
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param paymentServices The paymentServices parameter
    * @return PaymentServices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PaymentServices  createPaymentService(String xeroTenantId, PaymentServices paymentServices) throws IOException {
        HttpResponse response = createPaymentServiceForHttpResponse(xeroTenantId, paymentServices);
        TypeReference typeRef = new TypeReference<PaymentServices>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createPaymentServiceForHttpResponse( String xeroTenantId,  PaymentServices paymentServices) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createPaymentService");
        }// verify the required parameter 'paymentServices' is set
        if (paymentServices == null) {
            throw new IllegalArgumentException("Missing the required parameter 'paymentServices' when calling createPaymentService");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PaymentServices";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(paymentServices);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create an Allocation for prepayments
    * <p><b>200</b> - Success - return response of type Allocations array of Allocation for all Prepayment
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param prepaymentID The prepaymentID parameter
    * @param allocations The allocations parameter
    * @return Allocations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Allocations  createPrepaymentAllocation(String xeroTenantId, UUID prepaymentID, Allocations allocations) throws IOException {
        HttpResponse response = createPrepaymentAllocationForHttpResponse(xeroTenantId, prepaymentID, allocations);
        TypeReference typeRef = new TypeReference<Allocations>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createPrepaymentAllocationForHttpResponse( String xeroTenantId,  UUID prepaymentID,  Allocations allocations) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createPrepaymentAllocation");
        }// verify the required parameter 'prepaymentID' is set
        if (prepaymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'prepaymentID' when calling createPrepaymentAllocation");
        }// verify the required parameter 'allocations' is set
        if (allocations == null) {
            throw new IllegalArgumentException("Missing the required parameter 'allocations' when calling createPrepaymentAllocation");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Prepayments/{PrepaymentID}/Allocations";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PrepaymentID", prepaymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(allocations);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create a history record for an Prepayment
    * <p><b>200</b> - Success - return response of type HistoryRecords array for newly created HistoryRecord for PrePayment
    * <p><b>400</b> - Unsupported - return response incorrect exception, API is not able to create HistoryRecord for Expense Claims
    * @param xeroTenantId Xero identifier for Tenant
    * @param prepaymentID Unique identifier for a PrePayment
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createPrepaymentHistory(String xeroTenantId, UUID prepaymentID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createPrepaymentHistoryForHttpResponse(xeroTenantId, prepaymentID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createPrepaymentHistoryForHttpResponse( String xeroTenantId,  UUID prepaymentID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createPrepaymentHistory");
        }// verify the required parameter 'prepaymentID' is set
        if (prepaymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'prepaymentID' when calling createPrepaymentHistory");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createPrepaymentHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Prepayments/{PrepaymentID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PrepaymentID", prepaymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create purchase orders
    * <p><b>200</b> - Success - return response of type PurchaseOrder array for specified PurchaseOrder
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param purchaseOrders The purchaseOrders parameter
    * @param summarizeErrors shows validation errors for each purchase order.
    * @return PurchaseOrders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PurchaseOrders  createPurchaseOrder(String xeroTenantId, PurchaseOrders purchaseOrders, Boolean summarizeErrors) throws IOException {
        HttpResponse response = createPurchaseOrderForHttpResponse(xeroTenantId, purchaseOrders, summarizeErrors);
        TypeReference typeRef = new TypeReference<PurchaseOrders>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createPurchaseOrderForHttpResponse( String xeroTenantId,  PurchaseOrders purchaseOrders,  Boolean summarizeErrors) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createPurchaseOrder");
        }// verify the required parameter 'purchaseOrders' is set
        if (purchaseOrders == null) {
            throw new IllegalArgumentException("Missing the required parameter 'purchaseOrders' when calling createPurchaseOrder");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PurchaseOrders";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        if (summarizeErrors != null) {
            String key = "SummarizeErrors";
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(purchaseOrders);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create HistoryRecord for purchase orders
    * <p><b>200</b> - Success - return response of type HistoryRecords array for newly created HistoryRecord for PurchaseOrder
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param purchaseOrderID Unique identifier for a PurchaseOrder
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createPurchaseOrderHistory(String xeroTenantId, UUID purchaseOrderID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createPurchaseOrderHistoryForHttpResponse(xeroTenantId, purchaseOrderID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createPurchaseOrderHistoryForHttpResponse( String xeroTenantId,  UUID purchaseOrderID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createPurchaseOrderHistory");
        }// verify the required parameter 'purchaseOrderID' is set
        if (purchaseOrderID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'purchaseOrderID' when calling createPurchaseOrderHistory");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createPurchaseOrderHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PurchaseOrders/{PurchaseOrderID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PurchaseOrderID", purchaseOrderID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create draft expense claim receipts for any user
    * <p><b>200</b> - Success - return response of type Receipts array for newly created Receipt
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param receipts The receipts parameter
    * @return Receipts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Receipts  createReceipt(String xeroTenantId, Receipts receipts) throws IOException {
        HttpResponse response = createReceiptForHttpResponse(xeroTenantId, receipts);
        TypeReference typeRef = new TypeReference<Receipts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createReceiptForHttpResponse( String xeroTenantId,  Receipts receipts) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createReceipt");
        }// verify the required parameter 'receipts' is set
        if (receipts == null) {
            throw new IllegalArgumentException("Missing the required parameter 'receipts' when calling createReceipt");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Receipts";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(receipts);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create Attachment on expense claim receipts by file name
    * <p><b>200</b> - Success - return response of type Attachments array with newly created Attachment for a specified Receipt
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param receiptID Unique identifier for a Receipt
    * @param fileName The name of the file being attached to the Receipt
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  createReceiptAttachmentByFileName(String xeroTenantId, UUID receiptID, String fileName, File body) throws IOException {
        HttpResponse response = createReceiptAttachmentByFileNameForHttpResponse(xeroTenantId, receiptID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createReceiptAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID receiptID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createReceiptAttachmentByFileName");
        }// verify the required parameter 'receiptID' is set
        if (receiptID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'receiptID' when calling createReceiptAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling createReceiptAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling createReceiptAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Receipts/{ReceiptID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ReceiptID", receiptID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a history records of an Receipt
    * <p><b>200</b> - Unsupported - return response incorrect exception, API is not able to create HistoryRecord for Receipts
    * <p><b>400</b> - Unsupported - return response incorrect exception, API is not able to create HistoryRecord for Receipts
    * @param xeroTenantId Xero identifier for Tenant
    * @param receiptID Unique identifier for a Receipt
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createReceiptHistory(String xeroTenantId, UUID receiptID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createReceiptHistoryForHttpResponse(xeroTenantId, receiptID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createReceiptHistoryForHttpResponse( String xeroTenantId,  UUID receiptID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createReceiptHistory");
        }// verify the required parameter 'receiptID' is set
        if (receiptID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'receiptID' when calling createReceiptHistory");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createReceiptHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Receipts/{ReceiptID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ReceiptID", receiptID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create attachment on repeating invoices by file name
    * <p><b>200</b> - Success - return response of type Attachments array with updated Attachment for a specified Repeating Invoice
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @param fileName The name of the file being attached to a Repeating Invoice
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  createRepeatingInvoiceAttachmentByFileName(String xeroTenantId, UUID repeatingInvoiceID, String fileName, File body) throws IOException {
        HttpResponse response = createRepeatingInvoiceAttachmentByFileNameForHttpResponse(xeroTenantId, repeatingInvoiceID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createRepeatingInvoiceAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID repeatingInvoiceID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createRepeatingInvoiceAttachmentByFileName");
        }// verify the required parameter 'repeatingInvoiceID' is set
        if (repeatingInvoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'repeatingInvoiceID' when calling createRepeatingInvoiceAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling createRepeatingInvoiceAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling createRepeatingInvoiceAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create history for a repeating invoice
    * <p><b>200</b> - Unsupported - return response incorrect exception, API is not able to create HistoryRecord for Repeating Invoice
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  createRepeatingInvoiceHistory(String xeroTenantId, UUID repeatingInvoiceID, HistoryRecords historyRecords) throws IOException {
        HttpResponse response = createRepeatingInvoiceHistoryForHttpResponse(xeroTenantId, repeatingInvoiceID, historyRecords);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createRepeatingInvoiceHistoryForHttpResponse( String xeroTenantId,  UUID repeatingInvoiceID,  HistoryRecords historyRecords) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createRepeatingInvoiceHistory");
        }// verify the required parameter 'repeatingInvoiceID' is set
        if (repeatingInvoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'repeatingInvoiceID' when calling createRepeatingInvoiceHistory");
        }// verify the required parameter 'historyRecords' is set
        if (historyRecords == null) {
            throw new IllegalArgumentException("Missing the required parameter 'historyRecords' when calling createRepeatingInvoiceHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(historyRecords);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create Tax Rates
    * <p><b>200</b> - Success - return response of type TaxRates array newly created TaxRate
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param taxRates The taxRates parameter
    * @return TaxRates
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TaxRates  createTaxRate(String xeroTenantId, TaxRates taxRates) throws IOException {
        HttpResponse response = createTaxRateForHttpResponse(xeroTenantId, taxRates);
        TypeReference typeRef = new TypeReference<TaxRates>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createTaxRateForHttpResponse( String xeroTenantId,  TaxRates taxRates) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createTaxRate");
        }// verify the required parameter 'taxRates' is set
        if (taxRates == null) {
            throw new IllegalArgumentException("Missing the required parameter 'taxRates' when calling createTaxRate");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/TaxRates";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(taxRates);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create tracking categories
    * <p><b>200</b> - Success - return response of type TrackingCategories array of newly created TrackingCategory
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param trackingCategory The trackingCategory parameter
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories  createTrackingCategory(String xeroTenantId, TrackingCategory trackingCategory) throws IOException {
        HttpResponse response = createTrackingCategoryForHttpResponse(xeroTenantId, trackingCategory);
        TypeReference typeRef = new TypeReference<TrackingCategories>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createTrackingCategoryForHttpResponse( String xeroTenantId,  TrackingCategory trackingCategory) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createTrackingCategory");
        }// verify the required parameter 'trackingCategory' is set
        if (trackingCategory == null) {
            throw new IllegalArgumentException("Missing the required parameter 'trackingCategory' when calling createTrackingCategory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/TrackingCategories";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(trackingCategory);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to create options for a specified tracking category
    * <p><b>200</b> - Success - return response of type TrackingOptions array of options for a specified category
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param trackingCategoryID Unique identifier for a TrackingCategory
    * @param trackingOption The trackingOption parameter
    * @return TrackingOptions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingOptions  createTrackingOptions(String xeroTenantId, UUID trackingCategoryID, TrackingOption trackingOption) throws IOException {
        HttpResponse response = createTrackingOptionsForHttpResponse(xeroTenantId, trackingCategoryID, trackingOption);
        TypeReference typeRef = new TypeReference<TrackingOptions>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse createTrackingOptionsForHttpResponse( String xeroTenantId,  UUID trackingCategoryID,  TrackingOption trackingOption) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createTrackingOptions");
        }// verify the required parameter 'trackingCategoryID' is set
        if (trackingCategoryID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'trackingCategoryID' when calling createTrackingOptions");
        }// verify the required parameter 'trackingOption' is set
        if (trackingOption == null) {
            throw new IllegalArgumentException("Missing the required parameter 'trackingOption' when calling createTrackingOptions");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/TrackingCategories/{TrackingCategoryID}/Options";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("TrackingCategoryID", trackingCategoryID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(trackingOption);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.PUT, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to delete a chart of accounts
    * <p><b>200</b> - Success - delete existing Account and return response of type Accounts array with deleted Account
    * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
    * @param xeroTenantId Xero identifier for Tenant
    * @param accountID Unique identifier for retrieving single object
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts  deleteAccount(String xeroTenantId, UUID accountID) throws IOException {
        HttpResponse response = deleteAccountForHttpResponse(xeroTenantId, accountID);
        TypeReference typeRef = new TypeReference<Accounts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse deleteAccountForHttpResponse( String xeroTenantId,  UUID accountID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling deleteAccount");
        }// verify the required parameter 'accountID' is set
        if (accountID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accountID' when calling deleteAccount");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Accounts/{AccountID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("AccountID", accountID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.DELETE, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to delete a specific Contact from a Contract Group
    * <p><b>204</b> - Success - return response 204 no content
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactGroupID Unique identifier for a Contact Group
    * @param contactID Unique identifier for a Contact
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public void deleteContactGroupContact(String xeroTenantId, UUID contactGroupID, UUID contactID) throws IOException {
        deleteContactGroupContactForHttpResponse(xeroTenantId, contactGroupID, contactID);
    }

    public HttpResponse deleteContactGroupContactForHttpResponse( String xeroTenantId,  UUID contactGroupID,  UUID contactID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling deleteContactGroupContact");
        }// verify the required parameter 'contactGroupID' is set
        if (contactGroupID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactGroupID' when calling deleteContactGroupContact");
        }// verify the required parameter 'contactID' is set
        if (contactID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactID' when calling deleteContactGroupContact");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ContactGroups/{ContactGroupID}/Contacts/{ContactID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactGroupID", contactGroupID);
        uriVariables.put("ContactID", contactID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.DELETE, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to delete  all Contacts from a Contract Group
    * <p><b>200</b> - Success - return response 204 no content
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactGroupID Unique identifier for a Contact Group
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public void deleteContactGroupContacts(String xeroTenantId, UUID contactGroupID) throws IOException {
        deleteContactGroupContactsForHttpResponse(xeroTenantId, contactGroupID);
    }

    public HttpResponse deleteContactGroupContactsForHttpResponse( String xeroTenantId,  UUID contactGroupID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling deleteContactGroupContacts");
        }// verify the required parameter 'contactGroupID' is set
        if (contactGroupID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactGroupID' when calling deleteContactGroupContacts");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ContactGroups/{ContactGroupID}/Contacts";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactGroupID", contactGroupID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.DELETE, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to delete a specified item
    * <p><b>204</b> - Success - return response 204 no content
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param itemID Unique identifier for an Item
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public void deleteItem(String xeroTenantId, UUID itemID) throws IOException {
        deleteItemForHttpResponse(xeroTenantId, itemID);
    }

    public HttpResponse deleteItemForHttpResponse( String xeroTenantId,  UUID itemID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling deleteItem");
        }// verify the required parameter 'itemID' is set
        if (itemID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'itemID' when calling deleteItem");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Items/{ItemID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ItemID", itemID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.DELETE, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to delete a specified linked transactions (billable expenses)
    * <p><b>204</b> - Success - return response 204 no content
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param linkedTransactionID Unique identifier for a LinkedTransaction
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public void deleteLinkedTransaction(String xeroTenantId, UUID linkedTransactionID) throws IOException {
        deleteLinkedTransactionForHttpResponse(xeroTenantId, linkedTransactionID);
    }

    public HttpResponse deleteLinkedTransactionForHttpResponse( String xeroTenantId,  UUID linkedTransactionID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling deleteLinkedTransaction");
        }// verify the required parameter 'linkedTransactionID' is set
        if (linkedTransactionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'linkedTransactionID' when calling deleteLinkedTransaction");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/LinkedTransactions/{LinkedTransactionID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("LinkedTransactionID", linkedTransactionID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.DELETE, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update a specified payment for invoices and credit notes
    * <p><b>200</b> - Success - return response of type Payments array for updated Payment
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param paymentID Unique identifier for a Payment
    * @param payments The payments parameter
    * @return Payments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payments  deletePayment(String xeroTenantId, UUID paymentID, Payments payments) throws IOException {
        HttpResponse response = deletePaymentForHttpResponse(xeroTenantId, paymentID, payments);
        TypeReference typeRef = new TypeReference<Payments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse deletePaymentForHttpResponse( String xeroTenantId,  UUID paymentID,  Payments payments) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling deletePayment");
        }// verify the required parameter 'paymentID' is set
        if (paymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'paymentID' when calling deletePayment");
        }// verify the required parameter 'payments' is set
        if (payments == null) {
            throw new IllegalArgumentException("Missing the required parameter 'payments' when calling deletePayment");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payments/{PaymentID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PaymentID", paymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(payments);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to delete tracking categories
    * <p><b>200</b> - Success - return response of type TrackingCategories array of deleted TrackingCategory
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param trackingCategoryID Unique identifier for a TrackingCategory
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories  deleteTrackingCategory(String xeroTenantId, UUID trackingCategoryID) throws IOException {
        HttpResponse response = deleteTrackingCategoryForHttpResponse(xeroTenantId, trackingCategoryID);
        TypeReference typeRef = new TypeReference<TrackingCategories>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse deleteTrackingCategoryForHttpResponse( String xeroTenantId,  UUID trackingCategoryID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling deleteTrackingCategory");
        }// verify the required parameter 'trackingCategoryID' is set
        if (trackingCategoryID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'trackingCategoryID' when calling deleteTrackingCategory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/TrackingCategories/{TrackingCategoryID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("TrackingCategoryID", trackingCategoryID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.DELETE, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to delete a specified option for a specified tracking category
    * <p><b>200</b> - Success - return response of type TrackingOptions array of remaining options for a specified category
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param trackingCategoryID Unique identifier for a TrackingCategory
    * @param trackingOptionID Unique identifier for a Tracking Option
    * @return TrackingOptions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingOptions  deleteTrackingOptions(String xeroTenantId, UUID trackingCategoryID, UUID trackingOptionID) throws IOException {
        HttpResponse response = deleteTrackingOptionsForHttpResponse(xeroTenantId, trackingCategoryID, trackingOptionID);
        TypeReference typeRef = new TypeReference<TrackingOptions>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse deleteTrackingOptionsForHttpResponse( String xeroTenantId,  UUID trackingCategoryID,  UUID trackingOptionID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling deleteTrackingOptions");
        }// verify the required parameter 'trackingCategoryID' is set
        if (trackingCategoryID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'trackingCategoryID' when calling deleteTrackingOptions");
        }// verify the required parameter 'trackingOptionID' is set
        if (trackingOptionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'trackingOptionID' when calling deleteTrackingOptions");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/TrackingCategories/{TrackingCategoryID}/Options/{TrackingOptionID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("TrackingCategoryID", trackingCategoryID);
        uriVariables.put("TrackingOptionID", trackingOptionID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.DELETE, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to email a copy of invoice to related Contact
    * <p><b>204</b> - Success - return response 204 no content
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoiceID Unique identifier for an Invoice
    * @param requestEmpty The requestEmpty parameter
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public void emailInvoice(String xeroTenantId, UUID invoiceID, RequestEmpty requestEmpty) throws IOException {
        emailInvoiceForHttpResponse(xeroTenantId, invoiceID, requestEmpty);
    }

    public HttpResponse emailInvoiceForHttpResponse( String xeroTenantId,  UUID invoiceID,  RequestEmpty requestEmpty) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling emailInvoice");
        }// verify the required parameter 'invoiceID' is set
        if (invoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoiceID' when calling emailInvoice");
        }// verify the required parameter 'requestEmpty' is set
        if (requestEmpty == null) {
            throw new IllegalArgumentException("Missing the required parameter 'requestEmpty' when calling emailInvoice");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices/{InvoiceID}/Email";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("InvoiceID", invoiceID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(requestEmpty);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a single chart of accounts
    * <p><b>200</b> - Success - return response of type Accounts array with one Account
    * @param xeroTenantId Xero identifier for Tenant
    * @param accountID Unique identifier for retrieving single object
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts  getAccount(String xeroTenantId, UUID accountID) throws IOException {
        HttpResponse response = getAccountForHttpResponse(xeroTenantId, accountID);
        TypeReference typeRef = new TypeReference<Accounts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getAccountForHttpResponse( String xeroTenantId,  UUID accountID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getAccount");
        }// verify the required parameter 'accountID' is set
        if (accountID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accountID' when calling getAccount");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Accounts/{AccountID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("AccountID", accountID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachment on Account by Filename
    * <p><b>200</b> - Success - return response of attachment for Account as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param accountID Unique identifier for Account object
    * @param fileName Name of the attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getAccountAttachmentByFileName(String xeroTenantId, UUID accountID, String fileName, String contentType) throws IOException {
        HttpResponse response = getAccountAttachmentByFileNameForHttpResponse(xeroTenantId, accountID, fileName, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getAccountAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID accountID,  String fileName,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getAccountAttachmentByFileName");
        }// verify the required parameter 'accountID' is set
        if (accountID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accountID' when calling getAccountAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling getAccountAttachmentByFileName");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getAccountAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Accounts/{AccountID}/Attachments/{FileName}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/Accounts/{AccountID}/Attachments/{FileName}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("AccountID", accountID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve specific Attachment on Account
    * <p><b>200</b> - Success - return response of attachment for Account as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param accountID Unique identifier for Account object
    * @param attachmentID Unique identifier for Attachment object
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getAccountAttachmentById(String xeroTenantId, UUID accountID, UUID attachmentID, String contentType) throws IOException {
        HttpResponse response = getAccountAttachmentByIdForHttpResponse(xeroTenantId, accountID, attachmentID, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getAccountAttachmentByIdForHttpResponse( String xeroTenantId,  UUID accountID,  UUID attachmentID,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getAccountAttachmentById");
        }// verify the required parameter 'accountID' is set
        if (accountID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accountID' when calling getAccountAttachmentById");
        }// verify the required parameter 'attachmentID' is set
        if (attachmentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'attachmentID' when calling getAccountAttachmentById");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getAccountAttachmentById");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Accounts/{AccountID}/Attachments/{AttachmentID}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/Accounts/{AccountID}/Attachments/{AttachmentID}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("AccountID", accountID);
        uriVariables.put("AttachmentID", attachmentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments for accounts
    * <p><b>200</b> - Success - return response of type Attachments array of Attachment
    * @param xeroTenantId Xero identifier for Tenant
    * @param accountID Unique identifier for Account object
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  getAccountAttachments(String xeroTenantId, UUID accountID) throws IOException {
        HttpResponse response = getAccountAttachmentsForHttpResponse(xeroTenantId, accountID);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getAccountAttachmentsForHttpResponse( String xeroTenantId,  UUID accountID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getAccountAttachments");
        }// verify the required parameter 'accountID' is set
        if (accountID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accountID' when calling getAccountAttachments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Accounts/{AccountID}/Attachments";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("AccountID", accountID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve the full chart of accounts
    * <p><b>200</b> - Success - return response of type Accounts array with 0 to n Account
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts  getAccounts(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        HttpResponse response = getAccountsForHttpResponse(xeroTenantId, ifModifiedSince, where, order);
        TypeReference typeRef = new TypeReference<Accounts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getAccountsForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getAccounts");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Accounts";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a single spend or receive money transaction
    * <p><b>200</b> - Success - return response of type BankTransactions array with a specific BankTransaction
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @return BankTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransactions  getBankTransaction(String xeroTenantId, UUID bankTransactionID) throws IOException {
        HttpResponse response = getBankTransactionForHttpResponse(xeroTenantId, bankTransactionID);
        TypeReference typeRef = new TypeReference<BankTransactions>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBankTransactionForHttpResponse( String xeroTenantId,  UUID bankTransactionID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBankTransaction");
        }// verify the required parameter 'bankTransactionID' is set
        if (bankTransactionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransactionID' when calling getBankTransaction");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransactions/{BankTransactionID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransactionID", bankTransactionID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments on BankTransaction by Filename
    * <p><b>200</b> - Success - return response of attachment for BankTransaction as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @param fileName The name of the file being attached
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getBankTransactionAttachmentByFileName(String xeroTenantId, UUID bankTransactionID, String fileName, String contentType) throws IOException {
        HttpResponse response = getBankTransactionAttachmentByFileNameForHttpResponse(xeroTenantId, bankTransactionID, fileName, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getBankTransactionAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID bankTransactionID,  String fileName,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBankTransactionAttachmentByFileName");
        }// verify the required parameter 'bankTransactionID' is set
        if (bankTransactionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransactionID' when calling getBankTransactionAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling getBankTransactionAttachmentByFileName");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getBankTransactionAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransactionID", bankTransactionID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments on a specific BankTransaction
    * <p><b>200</b> - Success - return response of attachment for BankTransaction as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @param attachmentID Xero generated unique identifier for an attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getBankTransactionAttachmentById(String xeroTenantId, UUID bankTransactionID, UUID attachmentID, String contentType) throws IOException {
        HttpResponse response = getBankTransactionAttachmentByIdForHttpResponse(xeroTenantId, bankTransactionID, attachmentID, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getBankTransactionAttachmentByIdForHttpResponse( String xeroTenantId,  UUID bankTransactionID,  UUID attachmentID,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBankTransactionAttachmentById");
        }// verify the required parameter 'bankTransactionID' is set
        if (bankTransactionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransactionID' when calling getBankTransactionAttachmentById");
        }// verify the required parameter 'attachmentID' is set
        if (attachmentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'attachmentID' when calling getBankTransactionAttachmentById");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getBankTransactionAttachmentById");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransactions/{BankTransactionID}/Attachments/{AttachmentID}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/BankTransactions/{BankTransactionID}/Attachments/{AttachmentID}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransactionID", bankTransactionID);
        uriVariables.put("AttachmentID", attachmentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve any attachments to bank transactions
    * <p><b>200</b> - Success - return response of type Attachments array with 0 to n Attachment
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  getBankTransactionAttachments(String xeroTenantId, UUID bankTransactionID) throws IOException {
        HttpResponse response = getBankTransactionAttachmentsForHttpResponse(xeroTenantId, bankTransactionID);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBankTransactionAttachmentsForHttpResponse( String xeroTenantId,  UUID bankTransactionID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBankTransactionAttachments");
        }// verify the required parameter 'bankTransactionID' is set
        if (bankTransactionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransactionID' when calling getBankTransactionAttachments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransactions/{BankTransactionID}/Attachments";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransactionID", bankTransactionID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve any spend or receive money transactions
    * <p><b>200</b> - Success - return response of type BankTransactions array with 0 to n BankTransaction
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 bank transactions will be returned in a single API call with line items shown for each bank transaction
    * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts
    * @return BankTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransactions  getBankTransactions(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page, Integer unitdp) throws IOException {
        HttpResponse response = getBankTransactionsForHttpResponse(xeroTenantId, ifModifiedSince, where, order, page, unitdp);
        TypeReference typeRef = new TypeReference<BankTransactions>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBankTransactionsForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer page,  Integer unitdp) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBankTransactions");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransactions";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
            String key = "order";
            Object value = order;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (page != null) {
            String key = "page";
            Object value = page;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (unitdp != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve history from a bank transactions
    * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getBankTransactionsHistory(String xeroTenantId, UUID bankTransactionID) throws IOException {
        HttpResponse response = getBankTransactionsHistoryForHttpResponse(xeroTenantId, bankTransactionID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBankTransactionsHistoryForHttpResponse( String xeroTenantId,  UUID bankTransactionID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBankTransactionsHistory");
        }// verify the required parameter 'bankTransactionID' is set
        if (bankTransactionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransactionID' when calling getBankTransactionsHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransactions/{BankTransactionID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransactionID", bankTransactionID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve any bank transfers
    * <p><b>200</b> - Success - return response of BankTransfers array with one BankTransfer
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @return BankTransfers
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransfers  getBankTransfer(String xeroTenantId, UUID bankTransferID) throws IOException {
        HttpResponse response = getBankTransferForHttpResponse(xeroTenantId, bankTransferID);
        TypeReference typeRef = new TypeReference<BankTransfers>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBankTransferForHttpResponse( String xeroTenantId,  UUID bankTransferID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBankTransfer");
        }// verify the required parameter 'bankTransferID' is set
        if (bankTransferID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransferID' when calling getBankTransfer");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransfers/{BankTransferID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransferID", bankTransferID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments on BankTransfer by file name
    * <p><b>200</b> - Success - return response of binary data from the Attachment to a Bank Transfer
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @param fileName The name of the file being attached to a Bank Transfer
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getBankTransferAttachmentByFileName(String xeroTenantId, UUID bankTransferID, String fileName, String contentType) throws IOException {
        HttpResponse response = getBankTransferAttachmentByFileNameForHttpResponse(xeroTenantId, bankTransferID, fileName, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getBankTransferAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID bankTransferID,  String fileName,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBankTransferAttachmentByFileName");
        }// verify the required parameter 'bankTransferID' is set
        if (bankTransferID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransferID' when calling getBankTransferAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling getBankTransferAttachmentByFileName");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getBankTransferAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransfers/{BankTransferID}/Attachments/{FileName}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/BankTransfers/{BankTransferID}/Attachments/{FileName}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransferID", bankTransferID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments on BankTransfer
    * <p><b>200</b> - Success - return response of binary data from the Attachment to a Bank Transfer
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @param attachmentID Xero generated unique identifier for an Attachment to a bank transfer
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getBankTransferAttachmentById(String xeroTenantId, UUID bankTransferID, UUID attachmentID, String contentType) throws IOException {
        HttpResponse response = getBankTransferAttachmentByIdForHttpResponse(xeroTenantId, bankTransferID, attachmentID, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getBankTransferAttachmentByIdForHttpResponse( String xeroTenantId,  UUID bankTransferID,  UUID attachmentID,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBankTransferAttachmentById");
        }// verify the required parameter 'bankTransferID' is set
        if (bankTransferID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransferID' when calling getBankTransferAttachmentById");
        }// verify the required parameter 'attachmentID' is set
        if (attachmentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'attachmentID' when calling getBankTransferAttachmentById");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getBankTransferAttachmentById");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransfers/{BankTransferID}/Attachments/{AttachmentID}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/BankTransfers/{BankTransferID}/Attachments/{AttachmentID}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransferID", bankTransferID);
        uriVariables.put("AttachmentID", attachmentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments from  bank transfers
    * <p><b>200</b> - Success - return response of Attachments array of 0 to N Attachment for a Bank Transfer
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  getBankTransferAttachments(String xeroTenantId, UUID bankTransferID) throws IOException {
        HttpResponse response = getBankTransferAttachmentsForHttpResponse(xeroTenantId, bankTransferID);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBankTransferAttachmentsForHttpResponse( String xeroTenantId,  UUID bankTransferID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBankTransferAttachments");
        }// verify the required parameter 'bankTransferID' is set
        if (bankTransferID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransferID' when calling getBankTransferAttachments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransfers/{BankTransferID}/Attachments";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransferID", bankTransferID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve history from a bank transfers
    * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord for a Bank Transfer
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getBankTransferHistory(String xeroTenantId, UUID bankTransferID) throws IOException {
        HttpResponse response = getBankTransferHistoryForHttpResponse(xeroTenantId, bankTransferID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBankTransferHistoryForHttpResponse( String xeroTenantId,  UUID bankTransferID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBankTransferHistory");
        }// verify the required parameter 'bankTransferID' is set
        if (bankTransferID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransferID' when calling getBankTransferHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransfers/{BankTransferID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransferID", bankTransferID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve all bank transfers
    * <p><b>200</b> - Success - return response of BankTransfers array of 0 to N BankTransfer
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return BankTransfers
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransfers  getBankTransfers(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        HttpResponse response = getBankTransfersForHttpResponse(xeroTenantId, ifModifiedSince, where, order);
        TypeReference typeRef = new TypeReference<BankTransfers>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBankTransfersForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBankTransfers");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransfers";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve history from a Batch Payment
    * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
    * @param xeroTenantId Xero identifier for Tenant
    * @param batchPaymentID Unique identifier for BatchPayment
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getBatchPaymentHistory(String xeroTenantId, UUID batchPaymentID) throws IOException {
        HttpResponse response = getBatchPaymentHistoryForHttpResponse(xeroTenantId, batchPaymentID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBatchPaymentHistoryForHttpResponse( String xeroTenantId,  UUID batchPaymentID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBatchPaymentHistory");
        }// verify the required parameter 'batchPaymentID' is set
        if (batchPaymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'batchPaymentID' when calling getBatchPaymentHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BatchPayments/{BatchPaymentID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BatchPaymentID", batchPaymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Retrieve either one or many BatchPayments for invoices
    * <p><b>200</b> - Success - return response of type BatchPayments array of BatchPayment objects
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return BatchPayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BatchPayments  getBatchPayments(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        HttpResponse response = getBatchPaymentsForHttpResponse(xeroTenantId, ifModifiedSince, where, order);
        TypeReference typeRef = new TypeReference<BatchPayments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBatchPaymentsForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBatchPayments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BatchPayments";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specific BrandingThemes
    * <p><b>200</b> - Success - return response of type BrandingThemes with one BrandingTheme
    * @param xeroTenantId Xero identifier for Tenant
    * @param brandingThemeID Unique identifier for a Branding Theme
    * @return BrandingThemes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BrandingThemes  getBrandingTheme(String xeroTenantId, UUID brandingThemeID) throws IOException {
        HttpResponse response = getBrandingThemeForHttpResponse(xeroTenantId, brandingThemeID);
        TypeReference typeRef = new TypeReference<BrandingThemes>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBrandingThemeForHttpResponse( String xeroTenantId,  UUID brandingThemeID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBrandingTheme");
        }// verify the required parameter 'brandingThemeID' is set
        if (brandingThemeID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'brandingThemeID' when calling getBrandingTheme");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BrandingThemes/{BrandingThemeID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BrandingThemeID", brandingThemeID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve the Payment services for a Branding Theme
    * <p><b>200</b> - Success - return response of type PaymentServices array with 0 to N PaymentService
    * @param xeroTenantId Xero identifier for Tenant
    * @param brandingThemeID Unique identifier for a Branding Theme
    * @return PaymentServices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PaymentServices  getBrandingThemePaymentServices(String xeroTenantId, UUID brandingThemeID) throws IOException {
        HttpResponse response = getBrandingThemePaymentServicesForHttpResponse(xeroTenantId, brandingThemeID);
        TypeReference typeRef = new TypeReference<PaymentServices>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBrandingThemePaymentServicesForHttpResponse( String xeroTenantId,  UUID brandingThemeID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBrandingThemePaymentServices");
        }// verify the required parameter 'brandingThemeID' is set
        if (brandingThemeID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'brandingThemeID' when calling getBrandingThemePaymentServices");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BrandingThemes/{BrandingThemeID}/PaymentServices";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BrandingThemeID", brandingThemeID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve all the BrandingThemes
    * <p><b>200</b> - Success - return response of type BrandingThemes
    * @param xeroTenantId Xero identifier for Tenant
    * @return BrandingThemes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BrandingThemes  getBrandingThemes(String xeroTenantId) throws IOException {
        HttpResponse response = getBrandingThemesForHttpResponse(xeroTenantId);
        TypeReference typeRef = new TypeReference<BrandingThemes>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getBrandingThemesForHttpResponse( String xeroTenantId) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getBrandingThemes");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BrandingThemes";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve, add and update contacts in a Xero organisation
    * <p><b>200</b> - Success - return response of type Contacts array with a unique Contact
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactID Unique identifier for a Contact
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts  getContact(String xeroTenantId, UUID contactID) throws IOException {
        HttpResponse response = getContactForHttpResponse(xeroTenantId, contactID);
        TypeReference typeRef = new TypeReference<Contacts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getContactForHttpResponse( String xeroTenantId,  UUID contactID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getContact");
        }// verify the required parameter 'contactID' is set
        if (contactID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactID' when calling getContact");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Contacts/{ContactID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactID", contactID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments on Contacts by file name
    * <p><b>200</b> - Success - return response of attachment for Contact as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactID Unique identifier for a Contact
    * @param fileName Name for the file you are attaching
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getContactAttachmentByFileName(String xeroTenantId, UUID contactID, String fileName, String contentType) throws IOException {
        HttpResponse response = getContactAttachmentByFileNameForHttpResponse(xeroTenantId, contactID, fileName, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getContactAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID contactID,  String fileName,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getContactAttachmentByFileName");
        }// verify the required parameter 'contactID' is set
        if (contactID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactID' when calling getContactAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling getContactAttachmentByFileName");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getContactAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Contacts/{ContactID}/Attachments/{FileName}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/Contacts/{ContactID}/Attachments/{FileName}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactID", contactID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments on Contacts
    * <p><b>200</b> - Success - return response of attachment for Contact as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactID Unique identifier for a Contact
    * @param attachmentID Unique identifier for a Attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getContactAttachmentById(String xeroTenantId, UUID contactID, UUID attachmentID, String contentType) throws IOException {
        HttpResponse response = getContactAttachmentByIdForHttpResponse(xeroTenantId, contactID, attachmentID, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getContactAttachmentByIdForHttpResponse( String xeroTenantId,  UUID contactID,  UUID attachmentID,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getContactAttachmentById");
        }// verify the required parameter 'contactID' is set
        if (contactID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactID' when calling getContactAttachmentById");
        }// verify the required parameter 'attachmentID' is set
        if (attachmentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'attachmentID' when calling getContactAttachmentById");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getContactAttachmentById");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Contacts/{ContactID}/Attachments/{AttachmentID}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/Contacts/{ContactID}/Attachments/{AttachmentID}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactID", contactID);
        uriVariables.put("AttachmentID", attachmentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve, add and update contacts in a Xero organisation
    * <p><b>200</b> - Success - return response of type Attachments array with 0 to N Attachment
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactID Unique identifier for a Contact
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  getContactAttachments(String xeroTenantId, UUID contactID) throws IOException {
        HttpResponse response = getContactAttachmentsForHttpResponse(xeroTenantId, contactID);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getContactAttachmentsForHttpResponse( String xeroTenantId,  UUID contactID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getContactAttachments");
        }// verify the required parameter 'contactID' is set
        if (contactID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactID' when calling getContactAttachments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Contacts/{ContactID}/Attachments";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactID", contactID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve CISSettings for a contact in a Xero organisation
    * <p><b>200</b> - Success - return response of type CISSettings for a specific Contact
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactID Unique identifier for a Contact
    * @return CISSettings
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CISSettings  getContactCISSettings(String xeroTenantId, UUID contactID) throws IOException {
        HttpResponse response = getContactCISSettingsForHttpResponse(xeroTenantId, contactID);
        TypeReference typeRef = new TypeReference<CISSettings>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getContactCISSettingsForHttpResponse( String xeroTenantId,  UUID contactID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getContactCISSettings");
        }// verify the required parameter 'contactID' is set
        if (contactID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactID' when calling getContactCISSettings");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Contacts/{ContactID}/CISSettings";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactID", contactID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a unique Contract Group by ID
    * <p><b>200</b> - Success - return response of type Contact Groups array with a specific Contact Group
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactGroupID Unique identifier for a Contact Group
    * @return ContactGroups
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ContactGroups  getContactGroup(String xeroTenantId, UUID contactGroupID) throws IOException {
        HttpResponse response = getContactGroupForHttpResponse(xeroTenantId, contactGroupID);
        TypeReference typeRef = new TypeReference<ContactGroups>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getContactGroupForHttpResponse( String xeroTenantId,  UUID contactGroupID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getContactGroup");
        }// verify the required parameter 'contactGroupID' is set
        if (contactGroupID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactGroupID' when calling getContactGroup");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ContactGroups/{ContactGroupID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactGroupID", contactGroupID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve the ContactID and Name of all the contacts in a contact group
    * <p><b>200</b> - Success - return response of type Contact Groups array of Contact Group
    * @param xeroTenantId Xero identifier for Tenant
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return ContactGroups
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ContactGroups  getContactGroups(String xeroTenantId, String where, String order) throws IOException {
        HttpResponse response = getContactGroupsForHttpResponse(xeroTenantId, where, order);
        TypeReference typeRef = new TypeReference<ContactGroups>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getContactGroupsForHttpResponse( String xeroTenantId,  String where,  String order) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getContactGroups");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ContactGroups";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a history records of an Contact
    * <p><b>200</b> - Success - return response of type History Records array of 0 to N History Record for a specific Contact
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactID Unique identifier for a Contact
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getContactHistory(String xeroTenantId, UUID contactID) throws IOException {
        HttpResponse response = getContactHistoryForHttpResponse(xeroTenantId, contactID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getContactHistoryForHttpResponse( String xeroTenantId,  UUID contactID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getContactHistory");
        }// verify the required parameter 'contactID' is set
        if (contactID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactID' when calling getContactHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Contacts/{ContactID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactID", contactID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve, add and update contacts in a Xero organisation
    * <p><b>200</b> - Success - return response of type Contacts array with 0 to N Contact
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param ids Filter by a comma separated list of ContactIDs. Allows you to retrieve a specific set of contacts in a single call.
    * @param page e.g. page&#x3D;1 - Up to 100 contacts will be returned in a single API call.
    * @param includeArchived e.g. includeArchived&#x3D;true - Contacts with a status of ARCHIVED will be included in the response
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts  getContacts(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, String ids, Integer page, Boolean includeArchived) throws IOException {
        HttpResponse response = getContactsForHttpResponse(xeroTenantId, ifModifiedSince, where, order, ids, page, includeArchived);
        TypeReference typeRef = new TypeReference<Contacts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getContactsForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  String ids,  Integer page,  Boolean includeArchived) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getContacts");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Contacts";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
            String key = "order";
            Object value = order;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (ids != null) {
            String key = "IDs";
            Object value = ids;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (page != null) {
            String key = "page";
            Object value = page;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (includeArchived != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specific credit note
    * <p><b>200</b> - Success - return response of type Credit Notes array with a unique CreditNote
    * @param xeroTenantId Xero identifier for Tenant
    * @param creditNoteID Unique identifier for a Credit Note
    * @return CreditNotes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CreditNotes  getCreditNote(String xeroTenantId, UUID creditNoteID) throws IOException {
        HttpResponse response = getCreditNoteForHttpResponse(xeroTenantId, creditNoteID);
        TypeReference typeRef = new TypeReference<CreditNotes>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getCreditNoteForHttpResponse( String xeroTenantId,  UUID creditNoteID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getCreditNote");
        }// verify the required parameter 'creditNoteID' is set
        if (creditNoteID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNoteID' when calling getCreditNote");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes/{CreditNoteID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("CreditNoteID", creditNoteID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Credit Note as PDF files
    * <p><b>200</b> - Success - return response of binary data from the Attachment to a Credit Note
    * @param xeroTenantId Xero identifier for Tenant
    * @param creditNoteID Unique identifier for a Credit Note
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getCreditNoteAsPdf(String xeroTenantId, UUID creditNoteID, String contentType) throws IOException {
        HttpResponse response = getCreditNoteAsPdfForHttpResponse(xeroTenantId, creditNoteID, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getCreditNoteAsPdfForHttpResponse( String xeroTenantId,  UUID creditNoteID,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getCreditNoteAsPdf");
        }// verify the required parameter 'creditNoteID' is set
        if (creditNoteID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNoteID' when calling getCreditNoteAsPdf");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getCreditNoteAsPdf");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes/{CreditNoteID}/pdf";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/CreditNotes/{CreditNoteID}/pdf";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("CreditNoteID", creditNoteID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments on CreditNote by file name
    * <p><b>200</b> - Success - return response of attachment for Credit Note as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param creditNoteID Unique identifier for a Credit Note
    * @param fileName Name of the file you are attaching to Credit Note
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getCreditNoteAttachmentByFileName(String xeroTenantId, UUID creditNoteID, String fileName, String contentType) throws IOException {
        HttpResponse response = getCreditNoteAttachmentByFileNameForHttpResponse(xeroTenantId, creditNoteID, fileName, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getCreditNoteAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID creditNoteID,  String fileName,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getCreditNoteAttachmentByFileName");
        }// verify the required parameter 'creditNoteID' is set
        if (creditNoteID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNoteID' when calling getCreditNoteAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling getCreditNoteAttachmentByFileName");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getCreditNoteAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("CreditNoteID", creditNoteID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments on CreditNote
    * <p><b>200</b> - Success - return response of attachment for Credit Note as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param creditNoteID Unique identifier for a Credit Note
    * @param attachmentID Unique identifier for a Attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getCreditNoteAttachmentById(String xeroTenantId, UUID creditNoteID, UUID attachmentID, String contentType) throws IOException {
        HttpResponse response = getCreditNoteAttachmentByIdForHttpResponse(xeroTenantId, creditNoteID, attachmentID, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getCreditNoteAttachmentByIdForHttpResponse( String xeroTenantId,  UUID creditNoteID,  UUID attachmentID,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getCreditNoteAttachmentById");
        }// verify the required parameter 'creditNoteID' is set
        if (creditNoteID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNoteID' when calling getCreditNoteAttachmentById");
        }// verify the required parameter 'attachmentID' is set
        if (attachmentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'attachmentID' when calling getCreditNoteAttachmentById");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getCreditNoteAttachmentById");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes/{CreditNoteID}/Attachments/{AttachmentID}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/CreditNotes/{CreditNoteID}/Attachments/{AttachmentID}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("CreditNoteID", creditNoteID);
        uriVariables.put("AttachmentID", attachmentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments for credit notes
    * <p><b>200</b> - Success - return response of type Attachments array with all Attachment for specific Credit Note
    * @param xeroTenantId Xero identifier for Tenant
    * @param creditNoteID Unique identifier for a Credit Note
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  getCreditNoteAttachments(String xeroTenantId, UUID creditNoteID) throws IOException {
        HttpResponse response = getCreditNoteAttachmentsForHttpResponse(xeroTenantId, creditNoteID);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getCreditNoteAttachmentsForHttpResponse( String xeroTenantId,  UUID creditNoteID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getCreditNoteAttachments");
        }// verify the required parameter 'creditNoteID' is set
        if (creditNoteID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNoteID' when calling getCreditNoteAttachments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes/{CreditNoteID}/Attachments";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("CreditNoteID", creditNoteID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a history records of an CreditNote
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for specific Credit Note
    * @param xeroTenantId Xero identifier for Tenant
    * @param creditNoteID Unique identifier for a Credit Note
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getCreditNoteHistory(String xeroTenantId, UUID creditNoteID) throws IOException {
        HttpResponse response = getCreditNoteHistoryForHttpResponse(xeroTenantId, creditNoteID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getCreditNoteHistoryForHttpResponse( String xeroTenantId,  UUID creditNoteID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getCreditNoteHistory");
        }// verify the required parameter 'creditNoteID' is set
        if (creditNoteID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNoteID' when calling getCreditNoteHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes/{CreditNoteID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("CreditNoteID", creditNoteID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve any credit notes
    * <p><b>200</b> - Success - return response of type Credit Notes array of CreditNote
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 credit notes will be returned in a single API call with line items shown for each credit note
    * @return CreditNotes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CreditNotes  getCreditNotes(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        HttpResponse response = getCreditNotesForHttpResponse(xeroTenantId, ifModifiedSince, where, order, page);
        TypeReference typeRef = new TypeReference<CreditNotes>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getCreditNotesForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer page) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getCreditNotes");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
            String key = "order";
            Object value = order;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (page != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve currencies for your organisation
    * <p><b>200</b> - Success - return response of type Currencies array with all Currencies
    * @param xeroTenantId Xero identifier for Tenant
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Currencies
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Currencies  getCurrencies(String xeroTenantId, String where, String order) throws IOException {
        HttpResponse response = getCurrenciesForHttpResponse(xeroTenantId, where, order);
        TypeReference typeRef = new TypeReference<Currencies>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getCurrenciesForHttpResponse( String xeroTenantId,  String where,  String order) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getCurrencies");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Currencies";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specific employee used in Xero payrun
    * <p><b>200</b> - Success - return response of type Employees array with specified Employee
    * @param xeroTenantId Xero identifier for Tenant
    * @param employeeID Unique identifier for a Employee
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees  getEmployee(String xeroTenantId, UUID employeeID) throws IOException {
        HttpResponse response = getEmployeeForHttpResponse(xeroTenantId, employeeID);
        TypeReference typeRef = new TypeReference<Employees>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getEmployeeForHttpResponse( String xeroTenantId,  UUID employeeID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getEmployee");
        }// verify the required parameter 'employeeID' is set
        if (employeeID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'employeeID' when calling getEmployee");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Employees/{EmployeeID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("EmployeeID", employeeID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve employees used in Xero payrun
    * <p><b>200</b> - Success - return response of type Employees array with all Employee
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees  getEmployees(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        HttpResponse response = getEmployeesForHttpResponse(xeroTenantId, ifModifiedSince, where, order);
        TypeReference typeRef = new TypeReference<Employees>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getEmployeesForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getEmployees");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Employees";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified expense claim
    * <p><b>200</b> - Success - return response of type ExpenseClaims array with specified ExpenseClaim
    * @param xeroTenantId Xero identifier for Tenant
    * @param expenseClaimID Unique identifier for a ExpenseClaim
    * @return ExpenseClaims
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ExpenseClaims  getExpenseClaim(String xeroTenantId, UUID expenseClaimID) throws IOException {
        HttpResponse response = getExpenseClaimForHttpResponse(xeroTenantId, expenseClaimID);
        TypeReference typeRef = new TypeReference<ExpenseClaims>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getExpenseClaimForHttpResponse( String xeroTenantId,  UUID expenseClaimID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getExpenseClaim");
        }// verify the required parameter 'expenseClaimID' is set
        if (expenseClaimID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'expenseClaimID' when calling getExpenseClaim");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ExpenseClaims/{ExpenseClaimID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ExpenseClaimID", expenseClaimID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a history records of an ExpenseClaim
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for specific ExpenseClaim
    * @param xeroTenantId Xero identifier for Tenant
    * @param expenseClaimID Unique identifier for a ExpenseClaim
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getExpenseClaimHistory(String xeroTenantId, UUID expenseClaimID) throws IOException {
        HttpResponse response = getExpenseClaimHistoryForHttpResponse(xeroTenantId, expenseClaimID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getExpenseClaimHistoryForHttpResponse( String xeroTenantId,  UUID expenseClaimID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getExpenseClaimHistory");
        }// verify the required parameter 'expenseClaimID' is set
        if (expenseClaimID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'expenseClaimID' when calling getExpenseClaimHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ExpenseClaims/{ExpenseClaimID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ExpenseClaimID", expenseClaimID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve expense claims
    * <p><b>200</b> - Success - return response of type ExpenseClaims array with all ExpenseClaims
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return ExpenseClaims
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ExpenseClaims  getExpenseClaims(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        HttpResponse response = getExpenseClaimsForHttpResponse(xeroTenantId, ifModifiedSince, where, order);
        TypeReference typeRef = new TypeReference<ExpenseClaims>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getExpenseClaimsForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getExpenseClaims");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ExpenseClaims";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified sales invoice or purchase bill
    * <p><b>200</b> - Success - return response of type Invoices array with specified Invoices
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoiceID Unique identifier for an Invoice
    * @return Invoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Invoices  getInvoice(String xeroTenantId, UUID invoiceID) throws IOException {
        HttpResponse response = getInvoiceForHttpResponse(xeroTenantId, invoiceID);
        TypeReference typeRef = new TypeReference<Invoices>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getInvoiceForHttpResponse( String xeroTenantId,  UUID invoiceID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getInvoice");
        }// verify the required parameter 'invoiceID' is set
        if (invoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoiceID' when calling getInvoice");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices/{InvoiceID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("InvoiceID", invoiceID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve invoices or purchase bills as PDF files
    * <p><b>200</b> - Success - return response of byte array pdf version of specified Invoices
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoiceID Unique identifier for an Invoice
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getInvoiceAsPdf(String xeroTenantId, UUID invoiceID, String contentType) throws IOException {
        HttpResponse response = getInvoiceAsPdfForHttpResponse(xeroTenantId, invoiceID, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getInvoiceAsPdfForHttpResponse( String xeroTenantId,  UUID invoiceID,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getInvoiceAsPdf");
        }// verify the required parameter 'invoiceID' is set
        if (invoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoiceID' when calling getInvoiceAsPdf");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getInvoiceAsPdf");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices/{InvoiceID}/pdf";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/Invoices/{InvoiceID}/pdf";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("InvoiceID", invoiceID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachment on invoices or purchase bills by it&#39;s filename
    * <p><b>200</b> - Success - return response of attachment for Invoice as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoiceID Unique identifier for an Invoice
    * @param fileName Name of the file you are attaching
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getInvoiceAttachmentByFileName(String xeroTenantId, UUID invoiceID, String fileName, String contentType) throws IOException {
        HttpResponse response = getInvoiceAttachmentByFileNameForHttpResponse(xeroTenantId, invoiceID, fileName, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getInvoiceAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID invoiceID,  String fileName,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getInvoiceAttachmentByFileName");
        }// verify the required parameter 'invoiceID' is set
        if (invoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoiceID' when calling getInvoiceAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling getInvoiceAttachmentByFileName");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getInvoiceAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices/{InvoiceID}/Attachments/{FileName}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/Invoices/{InvoiceID}/Attachments/{FileName}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("InvoiceID", invoiceID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified Attachment on invoices or purchase bills by it&#39;s ID
    * <p><b>200</b> - Success - return response of attachment for Invoice as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoiceID Unique identifier for an Invoice
    * @param attachmentID Unique identifier for an Attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getInvoiceAttachmentById(String xeroTenantId, UUID invoiceID, UUID attachmentID, String contentType) throws IOException {
        HttpResponse response = getInvoiceAttachmentByIdForHttpResponse(xeroTenantId, invoiceID, attachmentID, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getInvoiceAttachmentByIdForHttpResponse( String xeroTenantId,  UUID invoiceID,  UUID attachmentID,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getInvoiceAttachmentById");
        }// verify the required parameter 'invoiceID' is set
        if (invoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoiceID' when calling getInvoiceAttachmentById");
        }// verify the required parameter 'attachmentID' is set
        if (attachmentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'attachmentID' when calling getInvoiceAttachmentById");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getInvoiceAttachmentById");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices/{InvoiceID}/Attachments/{AttachmentID}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/Invoices/{InvoiceID}/Attachments/{AttachmentID}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("InvoiceID", invoiceID);
        uriVariables.put("AttachmentID", attachmentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments on invoices or purchase bills
    * <p><b>200</b> - Success - return response of type Attachments array of Attachments for specified Invoices
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoiceID Unique identifier for an Invoice
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  getInvoiceAttachments(String xeroTenantId, UUID invoiceID) throws IOException {
        HttpResponse response = getInvoiceAttachmentsForHttpResponse(xeroTenantId, invoiceID);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getInvoiceAttachmentsForHttpResponse( String xeroTenantId,  UUID invoiceID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getInvoiceAttachments");
        }// verify the required parameter 'invoiceID' is set
        if (invoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoiceID' when calling getInvoiceAttachments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices/{InvoiceID}/Attachments";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("InvoiceID", invoiceID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a history records of an invoice
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for specific Invoice
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoiceID Unique identifier for an Invoice
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getInvoiceHistory(String xeroTenantId, UUID invoiceID) throws IOException {
        HttpResponse response = getInvoiceHistoryForHttpResponse(xeroTenantId, invoiceID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getInvoiceHistoryForHttpResponse( String xeroTenantId,  UUID invoiceID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getInvoiceHistory");
        }// verify the required parameter 'invoiceID' is set
        if (invoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoiceID' when calling getInvoiceHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices/{InvoiceID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("InvoiceID", invoiceID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve invoice reminder settings
    * <p><b>200</b> - Success - return response of Invoice Reminders
    * @param xeroTenantId Xero identifier for Tenant
    * @return InvoiceReminders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public InvoiceReminders  getInvoiceReminders(String xeroTenantId) throws IOException {
        HttpResponse response = getInvoiceRemindersForHttpResponse(xeroTenantId);
        TypeReference typeRef = new TypeReference<InvoiceReminders>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getInvoiceRemindersForHttpResponse( String xeroTenantId) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getInvoiceReminders");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/InvoiceReminders/Settings";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve any sales invoices or purchase bills
    * <p><b>200</b> - Success - return response of type Invoices array with all Invoices
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param ids Filter by a comma-separated list of InvoicesIDs. For faster response times we recommend using these explicit parameters instead of passing OR conditions into the Where filter.
    * @param invoiceNumbers Filter by a comma-separated list of InvoiceNumbers. For faster response times we recommend using these explicit parameters instead of passing OR conditions into the Where filter.
    * @param contactIDs Filter by a comma-separated list of ContactIDs. For faster response times we recommend using these explicit parameters instead of passing OR conditions into the Where filter.
    * @param statuses Filter by a comma-separated list Statuses. For faster response times we recommend using these explicit parameters instead of passing OR conditions into the Where filter.
    * @param page e.g. page&#x3D;1 – Up to 100 invoices will be returned in a single API call with line items shown for each invoice
    * @param includeArchived e.g. includeArchived&#x3D;true - Contacts with a status of ARCHIVED will be included in the response
    * @param createdByMyApp When set to true you&#39;ll only retrieve Invoices created by your app
    * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts
    * @return Invoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Invoices  getInvoices(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, String ids, String invoiceNumbers, String contactIDs, String statuses, Integer page, Boolean includeArchived, Boolean createdByMyApp, Integer unitdp) throws IOException {
        HttpResponse response = getInvoicesForHttpResponse(xeroTenantId, ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, page, includeArchived, createdByMyApp, unitdp);
        TypeReference typeRef = new TypeReference<Invoices>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getInvoicesForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  String ids,  String invoiceNumbers,  String contactIDs,  String statuses,  Integer page,  Boolean includeArchived,  Boolean createdByMyApp,  Integer unitdp) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getInvoices");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
            String key = "order";
            Object value = order;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (ids != null) {
            String key = "IDs";
            Object value = ids;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (invoiceNumbers != null) {
            String key = "InvoiceNumbers";
            Object value = invoiceNumbers;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (contactIDs != null) {
            String key = "ContactIDs";
            Object value = contactIDs;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (statuses != null) {
            String key = "Statuses";
            Object value = statuses;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (page != null) {
            String key = "page";
            Object value = page;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (includeArchived != null) {
            String key = "includeArchived";
            Object value = includeArchived;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (createdByMyApp != null) {
            String key = "createdByMyApp";
            Object value = createdByMyApp;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (unitdp != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified item
    * <p><b>200</b> - Success - return response of type Items array with specified Item
    * @param xeroTenantId Xero identifier for Tenant
    * @param itemID Unique identifier for an Item
    * @return Items
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Items  getItem(String xeroTenantId, UUID itemID) throws IOException {
        HttpResponse response = getItemForHttpResponse(xeroTenantId, itemID);
        TypeReference typeRef = new TypeReference<Items>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getItemForHttpResponse( String xeroTenantId,  UUID itemID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getItem");
        }// verify the required parameter 'itemID' is set
        if (itemID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'itemID' when calling getItem");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Items/{ItemID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ItemID", itemID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve history for items
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for specific Item
    * @param xeroTenantId Xero identifier for Tenant
    * @param itemID Unique identifier for an Item
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getItemHistory(String xeroTenantId, UUID itemID) throws IOException {
        HttpResponse response = getItemHistoryForHttpResponse(xeroTenantId, itemID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getItemHistoryForHttpResponse( String xeroTenantId,  UUID itemID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getItemHistory");
        }// verify the required parameter 'itemID' is set
        if (itemID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'itemID' when calling getItemHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Items/{ItemID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ItemID", itemID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve any items
    * <p><b>200</b> - Success - return response of type Items array with all Item
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts
    * @return Items
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Items  getItems(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer unitdp) throws IOException {
        HttpResponse response = getItemsForHttpResponse(xeroTenantId, ifModifiedSince, where, order, unitdp);
        TypeReference typeRef = new TypeReference<Items>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getItemsForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer unitdp) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getItems");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Items";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
            String key = "order";
            Object value = order;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (unitdp != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified journals.
    * <p><b>200</b> - Success - return response of type Journals array with specified Journal
    * @param xeroTenantId Xero identifier for Tenant
    * @param journalID Unique identifier for a Journal
    * @return Journals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Journals  getJournal(String xeroTenantId, UUID journalID) throws IOException {
        HttpResponse response = getJournalForHttpResponse(xeroTenantId, journalID);
        TypeReference typeRef = new TypeReference<Journals>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getJournalForHttpResponse( String xeroTenantId,  UUID journalID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getJournal");
        }// verify the required parameter 'journalID' is set
        if (journalID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'journalID' when calling getJournal");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Journals/{JournalID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("JournalID", journalID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve any journals.
    * <p><b>200</b> - Success - return response of type Journals array with all Journals
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param offset Offset by a specified journal number. e.g. journals with a JournalNumber greater than the offset will be returned
    * @param paymentsOnly Filter to retrieve journals on a cash basis. Journals are returned on an accrual basis by default.
    * @return Journals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Journals  getJournals(String xeroTenantId, OffsetDateTime ifModifiedSince, Integer offset, Boolean paymentsOnly) throws IOException {
        HttpResponse response = getJournalsForHttpResponse(xeroTenantId, ifModifiedSince, offset, paymentsOnly);
        TypeReference typeRef = new TypeReference<Journals>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getJournalsForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  Integer offset,  Boolean paymentsOnly) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getJournals");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Journals";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (paymentsOnly != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified linked transactions (billable expenses)
    * <p><b>200</b> - Success - return response of type LinkedTransactions array with a specified LinkedTransaction
    * @param xeroTenantId Xero identifier for Tenant
    * @param linkedTransactionID Unique identifier for a LinkedTransaction
    * @return LinkedTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LinkedTransactions  getLinkedTransaction(String xeroTenantId, UUID linkedTransactionID) throws IOException {
        HttpResponse response = getLinkedTransactionForHttpResponse(xeroTenantId, linkedTransactionID);
        TypeReference typeRef = new TypeReference<LinkedTransactions>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getLinkedTransactionForHttpResponse( String xeroTenantId,  UUID linkedTransactionID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getLinkedTransaction");
        }// verify the required parameter 'linkedTransactionID' is set
        if (linkedTransactionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'linkedTransactionID' when calling getLinkedTransaction");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/LinkedTransactions/{LinkedTransactionID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("LinkedTransactionID", linkedTransactionID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Retrieve linked transactions (billable expenses)
    * <p><b>200</b> - Success - return response of type LinkedTransactions array with all LinkedTransaction
    * @param xeroTenantId Xero identifier for Tenant
    * @param page Up to 100 linked transactions will be returned in a single API call. Use the page parameter to specify the page to be returned e.g. page&#x3D;1.
    * @param linkedTransactionID The Xero identifier for an Linked Transaction
    * @param sourceTransactionID Filter by the SourceTransactionID. Get all the linked transactions created from a particular ACCPAY invoice
    * @param contactID Filter by the ContactID. Get all the linked transactions that have been assigned to a particular customer.
    * @param status Filter by the combination of ContactID and Status. Get all the linked transactions that have been assigned to a particular customer and have a particular status e.g. GET /LinkedTransactions?ContactID&#x3D;4bb34b03-3378-4bb2-a0ed-6345abf3224e&amp;Status&#x3D;APPROVED.
    * @param targetTransactionID Filter by the TargetTransactionID. Get all the linked transactions allocated to a particular ACCREC invoice
    * @return LinkedTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LinkedTransactions  getLinkedTransactions(String xeroTenantId, Integer page, String linkedTransactionID, String sourceTransactionID, String contactID, String status, String targetTransactionID) throws IOException {
        HttpResponse response = getLinkedTransactionsForHttpResponse(xeroTenantId, page, linkedTransactionID, sourceTransactionID, contactID, status, targetTransactionID);
        TypeReference typeRef = new TypeReference<LinkedTransactions>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getLinkedTransactionsForHttpResponse( String xeroTenantId,  Integer page,  String linkedTransactionID,  String sourceTransactionID,  String contactID,  String status,  String targetTransactionID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getLinkedTransactions");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/LinkedTransactions";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (linkedTransactionID != null) {
            String key = "LinkedTransactionID";
            Object value = linkedTransactionID;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (sourceTransactionID != null) {
            String key = "SourceTransactionID";
            Object value = sourceTransactionID;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (contactID != null) {
            String key = "ContactID";
            Object value = contactID;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (status != null) {
            String key = "Status";
            Object value = status;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (targetTransactionID != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified manual journals
    * <p><b>200</b> - Success - return response of type ManualJournals array with a specified ManualJournals
    * @param xeroTenantId Xero identifier for Tenant
    * @param manualJournalID Unique identifier for a ManualJournal
    * @return ManualJournals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ManualJournals  getManualJournal(String xeroTenantId, UUID manualJournalID) throws IOException {
        HttpResponse response = getManualJournalForHttpResponse(xeroTenantId, manualJournalID);
        TypeReference typeRef = new TypeReference<ManualJournals>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getManualJournalForHttpResponse( String xeroTenantId,  UUID manualJournalID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getManualJournal");
        }// verify the required parameter 'manualJournalID' is set
        if (manualJournalID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'manualJournalID' when calling getManualJournal");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ManualJournals/{ManualJournalID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ManualJournalID", manualJournalID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve specified Attachment on ManualJournal by file name
    * <p><b>200</b> - Success - return response of attachment for Manual Journal as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param manualJournalID Unique identifier for a ManualJournal
    * @param fileName The name of the file being attached to a ManualJournal
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getManualJournalAttachmentByFileName(String xeroTenantId, UUID manualJournalID, String fileName, String contentType) throws IOException {
        HttpResponse response = getManualJournalAttachmentByFileNameForHttpResponse(xeroTenantId, manualJournalID, fileName, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getManualJournalAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID manualJournalID,  String fileName,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getManualJournalAttachmentByFileName");
        }// verify the required parameter 'manualJournalID' is set
        if (manualJournalID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'manualJournalID' when calling getManualJournalAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling getManualJournalAttachmentByFileName");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getManualJournalAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ManualJournalID", manualJournalID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve specified Attachment on ManualJournals
    * <p><b>200</b> - Success - return response of attachment for Manual Journal as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param manualJournalID Unique identifier for a ManualJournal
    * @param attachmentID Unique identifier for a Attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getManualJournalAttachmentById(String xeroTenantId, UUID manualJournalID, UUID attachmentID, String contentType) throws IOException {
        HttpResponse response = getManualJournalAttachmentByIdForHttpResponse(xeroTenantId, manualJournalID, attachmentID, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getManualJournalAttachmentByIdForHttpResponse( String xeroTenantId,  UUID manualJournalID,  UUID attachmentID,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getManualJournalAttachmentById");
        }// verify the required parameter 'manualJournalID' is set
        if (manualJournalID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'manualJournalID' when calling getManualJournalAttachmentById");
        }// verify the required parameter 'attachmentID' is set
        if (attachmentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'attachmentID' when calling getManualJournalAttachmentById");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getManualJournalAttachmentById");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ManualJournals/{ManualJournalID}/Attachments/{AttachmentID}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/ManualJournals/{ManualJournalID}/Attachments/{AttachmentID}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ManualJournalID", manualJournalID);
        uriVariables.put("AttachmentID", attachmentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachment for manual journals
    * <p><b>200</b> - Success - return response of type Attachments array with all Attachments for a ManualJournals
    * @param xeroTenantId Xero identifier for Tenant
    * @param manualJournalID Unique identifier for a ManualJournal
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  getManualJournalAttachments(String xeroTenantId, UUID manualJournalID) throws IOException {
        HttpResponse response = getManualJournalAttachmentsForHttpResponse(xeroTenantId, manualJournalID);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getManualJournalAttachmentsForHttpResponse( String xeroTenantId,  UUID manualJournalID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getManualJournalAttachments");
        }// verify the required parameter 'manualJournalID' is set
        if (manualJournalID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'manualJournalID' when calling getManualJournalAttachments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ManualJournals/{ManualJournalID}/Attachments";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ManualJournalID", manualJournalID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve any manual journals
    * <p><b>200</b> - Success - return response of type ManualJournals array with a all ManualJournals
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 manual journals will be returned in a single API call with line items shown for each overpayment
    * @return ManualJournals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ManualJournals  getManualJournals(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        HttpResponse response = getManualJournalsForHttpResponse(xeroTenantId, ifModifiedSince, where, order, page);
        TypeReference typeRef = new TypeReference<ManualJournals>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getManualJournalsForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer page) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getManualJournals");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ManualJournals";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
            String key = "order";
            Object value = order;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (page != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a URL to an online invoice
    * <p><b>200</b> - Success - return response of type OnlineInvoice array with one OnlineInvoice
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoiceID Unique identifier for an Invoice
    * @return OnlineInvoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public OnlineInvoices  getOnlineInvoice(String xeroTenantId, UUID invoiceID) throws IOException {
        HttpResponse response = getOnlineInvoiceForHttpResponse(xeroTenantId, invoiceID);
        TypeReference typeRef = new TypeReference<OnlineInvoices>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getOnlineInvoiceForHttpResponse( String xeroTenantId,  UUID invoiceID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getOnlineInvoice");
        }// verify the required parameter 'invoiceID' is set
        if (invoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoiceID' when calling getOnlineInvoice");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices/{InvoiceID}/OnlineInvoice";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("InvoiceID", invoiceID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you To verify if an organisation is using contruction industry scheme, you can retrieve the CIS settings for the organistaion.
    * <p><b>200</b> - Success - return response of type Organisation array with specified Organisation
    * @param xeroTenantId Xero identifier for Tenant
    * @param organisationID The organisationID parameter
    * @return CISOrgSetting
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CISOrgSetting  getOrganisationCISSettings(String xeroTenantId, UUID organisationID) throws IOException {
        HttpResponse response = getOrganisationCISSettingsForHttpResponse(xeroTenantId, organisationID);
        TypeReference typeRef = new TypeReference<CISOrgSetting>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getOrganisationCISSettingsForHttpResponse( String xeroTenantId,  UUID organisationID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getOrganisationCISSettings");
        }// verify the required parameter 'organisationID' is set
        if (organisationID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'organisationID' when calling getOrganisationCISSettings");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Organisation/{OrganisationID}/CISSettings";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("OrganisationID", organisationID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Organisation details
    * <p><b>200</b> - Success - return response of type Organisation array with all Organisation
    * @param xeroTenantId Xero identifier for Tenant
    * @return Organisations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Organisations  getOrganisations(String xeroTenantId) throws IOException {
        HttpResponse response = getOrganisationsForHttpResponse(xeroTenantId);
        TypeReference typeRef = new TypeReference<Organisations>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getOrganisationsForHttpResponse( String xeroTenantId) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getOrganisations");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Organisation";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified overpayments
    * <p><b>200</b> - Success - return response of type Overpayments array with specified Overpayments
    * @param xeroTenantId Xero identifier for Tenant
    * @param overpaymentID Unique identifier for a Overpayment
    * @return Overpayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Overpayments  getOverpayment(String xeroTenantId, UUID overpaymentID) throws IOException {
        HttpResponse response = getOverpaymentForHttpResponse(xeroTenantId, overpaymentID);
        TypeReference typeRef = new TypeReference<Overpayments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getOverpaymentForHttpResponse( String xeroTenantId,  UUID overpaymentID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getOverpayment");
        }// verify the required parameter 'overpaymentID' is set
        if (overpaymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'overpaymentID' when calling getOverpayment");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Overpayments/{OverpaymentID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("OverpaymentID", overpaymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a history records of an Overpayment
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for Overpayments
    * @param xeroTenantId Xero identifier for Tenant
    * @param overpaymentID Unique identifier for a Overpayment
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getOverpaymentHistory(String xeroTenantId, UUID overpaymentID) throws IOException {
        HttpResponse response = getOverpaymentHistoryForHttpResponse(xeroTenantId, overpaymentID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getOverpaymentHistoryForHttpResponse( String xeroTenantId,  UUID overpaymentID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getOverpaymentHistory");
        }// verify the required parameter 'overpaymentID' is set
        if (overpaymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'overpaymentID' when calling getOverpaymentHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Overpayments/{OverpaymentID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("OverpaymentID", overpaymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve overpayments
    * <p><b>200</b> - Success - return response of type Overpayments array with all Overpayments
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 overpayments will be returned in a single API call with line items shown for each overpayment
    * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts
    * @return Overpayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Overpayments  getOverpayments(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page, Integer unitdp) throws IOException {
        HttpResponse response = getOverpaymentsForHttpResponse(xeroTenantId, ifModifiedSince, where, order, page, unitdp);
        TypeReference typeRef = new TypeReference<Overpayments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getOverpaymentsForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer page,  Integer unitdp) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getOverpayments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Overpayments";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
            String key = "order";
            Object value = order;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (page != null) {
            String key = "page";
            Object value = page;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (unitdp != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified payment for invoices and credit notes
    * <p><b>200</b> - Success - return response of type Payments array for specified Payment
    * @param xeroTenantId Xero identifier for Tenant
    * @param paymentID Unique identifier for a Payment
    * @return Payments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payments  getPayment(String xeroTenantId, UUID paymentID) throws IOException {
        HttpResponse response = getPaymentForHttpResponse(xeroTenantId, paymentID);
        TypeReference typeRef = new TypeReference<Payments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getPaymentForHttpResponse( String xeroTenantId,  UUID paymentID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPayment");
        }// verify the required parameter 'paymentID' is set
        if (paymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'paymentID' when calling getPayment");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payments/{PaymentID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PaymentID", paymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve history records of a payment
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for Payments
    * @param xeroTenantId Xero identifier for Tenant
    * @param paymentID Unique identifier for a Payment
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getPaymentHistory(String xeroTenantId, UUID paymentID) throws IOException {
        HttpResponse response = getPaymentHistoryForHttpResponse(xeroTenantId, paymentID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getPaymentHistoryForHttpResponse( String xeroTenantId,  UUID paymentID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPaymentHistory");
        }// verify the required parameter 'paymentID' is set
        if (paymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'paymentID' when calling getPaymentHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payments/{PaymentID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PaymentID", paymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve payment services
    * <p><b>200</b> - Success - return response of type PaymentServices array for all PaymentService
    * @param xeroTenantId Xero identifier for Tenant
    * @return PaymentServices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PaymentServices  getPaymentServices(String xeroTenantId) throws IOException {
        HttpResponse response = getPaymentServicesForHttpResponse(xeroTenantId);
        TypeReference typeRef = new TypeReference<PaymentServices>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getPaymentServicesForHttpResponse( String xeroTenantId) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPaymentServices");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PaymentServices";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve payments for invoices and credit notes
    * <p><b>200</b> - Success - return response of type Payments array for all Payments
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Payments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payments  getPayments(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        HttpResponse response = getPaymentsForHttpResponse(xeroTenantId, ifModifiedSince, where, order);
        TypeReference typeRef = new TypeReference<Payments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getPaymentsForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPayments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payments";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified prepayments
    * <p><b>200</b> - Success - return response of type Prepayments array for a specified Prepayment
    * @param xeroTenantId Xero identifier for Tenant
    * @param prepaymentID Unique identifier for a PrePayment
    * @return Prepayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Prepayments  getPrepayment(String xeroTenantId, UUID prepaymentID) throws IOException {
        HttpResponse response = getPrepaymentForHttpResponse(xeroTenantId, prepaymentID);
        TypeReference typeRef = new TypeReference<Prepayments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getPrepaymentForHttpResponse( String xeroTenantId,  UUID prepaymentID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPrepayment");
        }// verify the required parameter 'prepaymentID' is set
        if (prepaymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'prepaymentID' when calling getPrepayment");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Prepayments/{PrepaymentID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PrepaymentID", prepaymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a history records of an Prepayment
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for PrePayment
    * @param xeroTenantId Xero identifier for Tenant
    * @param prepaymentID Unique identifier for a PrePayment
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getPrepaymentHistory(String xeroTenantId, UUID prepaymentID) throws IOException {
        HttpResponse response = getPrepaymentHistoryForHttpResponse(xeroTenantId, prepaymentID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getPrepaymentHistoryForHttpResponse( String xeroTenantId,  UUID prepaymentID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPrepaymentHistory");
        }// verify the required parameter 'prepaymentID' is set
        if (prepaymentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'prepaymentID' when calling getPrepaymentHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Prepayments/{PrepaymentID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PrepaymentID", prepaymentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve prepayments
    * <p><b>200</b> - Success - return response of type Prepayments array for all Prepayment
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 prepayments will be returned in a single API call with line items shown for each overpayment
    * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts
    * @return Prepayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Prepayments  getPrepayments(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page, Integer unitdp) throws IOException {
        HttpResponse response = getPrepaymentsForHttpResponse(xeroTenantId, ifModifiedSince, where, order, page, unitdp);
        TypeReference typeRef = new TypeReference<Prepayments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getPrepaymentsForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer page,  Integer unitdp) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPrepayments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Prepayments";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
            String key = "order";
            Object value = order;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (page != null) {
            String key = "page";
            Object value = page;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (unitdp != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified purchase orders
    * <p><b>200</b> - Success - return response of type PurchaseOrder array for specified PurchaseOrder
    * @param xeroTenantId Xero identifier for Tenant
    * @param purchaseOrderID Unique identifier for a PurchaseOrder
    * @return PurchaseOrders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PurchaseOrders  getPurchaseOrder(String xeroTenantId, UUID purchaseOrderID) throws IOException {
        HttpResponse response = getPurchaseOrderForHttpResponse(xeroTenantId, purchaseOrderID);
        TypeReference typeRef = new TypeReference<PurchaseOrders>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getPurchaseOrderForHttpResponse( String xeroTenantId,  UUID purchaseOrderID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPurchaseOrder");
        }// verify the required parameter 'purchaseOrderID' is set
        if (purchaseOrderID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'purchaseOrderID' when calling getPurchaseOrder");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PurchaseOrders/{PurchaseOrderID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PurchaseOrderID", purchaseOrderID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve history for PurchaseOrder
    * <p><b>200</b> - Success - return response of type HistoryRecords array for all HistoryRecord for PurchaseOrder
    * @param xeroTenantId Xero identifier for Tenant
    * @param purchaseOrderID Unique identifier for a PurchaseOrder
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getPurchaseOrderHistory(String xeroTenantId, UUID purchaseOrderID) throws IOException {
        HttpResponse response = getPurchaseOrderHistoryForHttpResponse(xeroTenantId, purchaseOrderID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getPurchaseOrderHistoryForHttpResponse( String xeroTenantId,  UUID purchaseOrderID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPurchaseOrderHistory");
        }// verify the required parameter 'purchaseOrderID' is set
        if (purchaseOrderID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'purchaseOrderID' when calling getPurchaseOrderHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PurchaseOrders/{PurchaseOrderID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PurchaseOrderID", purchaseOrderID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve purchase orders
    * <p><b>200</b> - Success - return response of type PurchaseOrder array of all PurchaseOrder
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param status Filter by purchase order status
    * @param dateFrom Filter by purchase order date (e.g. GET https://.../PurchaseOrders?DateFrom&#x3D;2015-12-01&amp;DateTo&#x3D;2015-12-31
    * @param dateTo Filter by purchase order date (e.g. GET https://.../PurchaseOrders?DateFrom&#x3D;2015-12-01&amp;DateTo&#x3D;2015-12-31
    * @param order Order by an any element
    * @param page To specify a page, append the page parameter to the URL e.g. ?page&#x3D;1. If there are 100 records in the response you will need to check if there is any more data by fetching the next page e.g ?page&#x3D;2 and continuing this process until no more results are returned.
    * @return PurchaseOrders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PurchaseOrders  getPurchaseOrders(String xeroTenantId, OffsetDateTime ifModifiedSince, String status, String dateFrom, String dateTo, String order, Integer page) throws IOException {
        HttpResponse response = getPurchaseOrdersForHttpResponse(xeroTenantId, ifModifiedSince, status, dateFrom, dateTo, order, page);
        TypeReference typeRef = new TypeReference<PurchaseOrders>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getPurchaseOrdersForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String status,  String dateFrom,  String dateTo,  String order,  Integer page) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPurchaseOrders");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PurchaseOrders";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (dateFrom != null) {
            String key = "DateFrom";
            Object value = dateFrom;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (dateTo != null) {
            String key = "DateTo";
            Object value = dateTo;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (order != null) {
            String key = "order";
            Object value = order;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (page != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified draft expense claim receipts
    * <p><b>200</b> - Success - return response of type Receipts array for a specified Receipt
    * @param xeroTenantId Xero identifier for Tenant
    * @param receiptID Unique identifier for a Receipt
    * @return Receipts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Receipts  getReceipt(String xeroTenantId, UUID receiptID) throws IOException {
        HttpResponse response = getReceiptForHttpResponse(xeroTenantId, receiptID);
        TypeReference typeRef = new TypeReference<Receipts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReceiptForHttpResponse( String xeroTenantId,  UUID receiptID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReceipt");
        }// verify the required parameter 'receiptID' is set
        if (receiptID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'receiptID' when calling getReceipt");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Receipts/{ReceiptID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ReceiptID", receiptID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments on expense claim receipts by file name
    * <p><b>200</b> - Success - return response of attachment for Receipt as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param receiptID Unique identifier for a Receipt
    * @param fileName The name of the file being attached to the Receipt
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getReceiptAttachmentByFileName(String xeroTenantId, UUID receiptID, String fileName, String contentType) throws IOException {
        HttpResponse response = getReceiptAttachmentByFileNameForHttpResponse(xeroTenantId, receiptID, fileName, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getReceiptAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID receiptID,  String fileName,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReceiptAttachmentByFileName");
        }// verify the required parameter 'receiptID' is set
        if (receiptID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'receiptID' when calling getReceiptAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling getReceiptAttachmentByFileName");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getReceiptAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Receipts/{ReceiptID}/Attachments/{FileName}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/Receipts/{ReceiptID}/Attachments/{FileName}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ReceiptID", receiptID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments on expense claim receipts by ID
    * <p><b>200</b> - Success - return response of attachment for Receipt as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param receiptID Unique identifier for a Receipt
    * @param attachmentID Unique identifier for a Attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getReceiptAttachmentById(String xeroTenantId, UUID receiptID, UUID attachmentID, String contentType) throws IOException {
        HttpResponse response = getReceiptAttachmentByIdForHttpResponse(xeroTenantId, receiptID, attachmentID, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getReceiptAttachmentByIdForHttpResponse( String xeroTenantId,  UUID receiptID,  UUID attachmentID,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReceiptAttachmentById");
        }// verify the required parameter 'receiptID' is set
        if (receiptID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'receiptID' when calling getReceiptAttachmentById");
        }// verify the required parameter 'attachmentID' is set
        if (attachmentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'attachmentID' when calling getReceiptAttachmentById");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getReceiptAttachmentById");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Receipts/{ReceiptID}/Attachments/{AttachmentID}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/Receipts/{ReceiptID}/Attachments/{AttachmentID}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ReceiptID", receiptID);
        uriVariables.put("AttachmentID", attachmentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments for expense claim receipts
    * <p><b>200</b> - Success - return response of type Attachments array of Attachments for a specified Receipt
    * @param xeroTenantId Xero identifier for Tenant
    * @param receiptID Unique identifier for a Receipt
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  getReceiptAttachments(String xeroTenantId, UUID receiptID) throws IOException {
        HttpResponse response = getReceiptAttachmentsForHttpResponse(xeroTenantId, receiptID);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReceiptAttachmentsForHttpResponse( String xeroTenantId,  UUID receiptID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReceiptAttachments");
        }// verify the required parameter 'receiptID' is set
        if (receiptID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'receiptID' when calling getReceiptAttachments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Receipts/{ReceiptID}/Attachments";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ReceiptID", receiptID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a history records of an Receipt
    * <p><b>200</b> - Success - return response of type HistoryRecords array of all HistoryRecord for Receipt
    * @param xeroTenantId Xero identifier for Tenant
    * @param receiptID Unique identifier for a Receipt
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getReceiptHistory(String xeroTenantId, UUID receiptID) throws IOException {
        HttpResponse response = getReceiptHistoryForHttpResponse(xeroTenantId, receiptID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReceiptHistoryForHttpResponse( String xeroTenantId,  UUID receiptID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReceiptHistory");
        }// verify the required parameter 'receiptID' is set
        if (receiptID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'receiptID' when calling getReceiptHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Receipts/{ReceiptID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ReceiptID", receiptID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve draft expense claim receipts for any user
    * <p><b>200</b> - Success - return response of type Receipts array for all Receipt
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts
    * @return Receipts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Receipts  getReceipts(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer unitdp) throws IOException {
        HttpResponse response = getReceiptsForHttpResponse(xeroTenantId, ifModifiedSince, where, order, unitdp);
        TypeReference typeRef = new TypeReference<Receipts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReceiptsForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer unitdp) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReceipts");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Receipts";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
            String key = "order";
            Object value = order;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (unitdp != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified repeating invoice
    * <p><b>200</b> - Success - return response of type Repeating Invoices array with a specified Repeating Invoice
    * @param xeroTenantId Xero identifier for Tenant
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @return RepeatingInvoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public RepeatingInvoices  getRepeatingInvoice(String xeroTenantId, UUID repeatingInvoiceID) throws IOException {
        HttpResponse response = getRepeatingInvoiceForHttpResponse(xeroTenantId, repeatingInvoiceID);
        TypeReference typeRef = new TypeReference<RepeatingInvoices>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getRepeatingInvoiceForHttpResponse( String xeroTenantId,  UUID repeatingInvoiceID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoice");
        }// verify the required parameter 'repeatingInvoiceID' is set
        if (repeatingInvoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'repeatingInvoiceID' when calling getRepeatingInvoice");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve specified attachment on repeating invoices by file name
    * <p><b>200</b> - Success - return response of attachment for Repeating Invoice as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @param fileName The name of the file being attached to a Repeating Invoice
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getRepeatingInvoiceAttachmentByFileName(String xeroTenantId, UUID repeatingInvoiceID, String fileName, String contentType) throws IOException {
        HttpResponse response = getRepeatingInvoiceAttachmentByFileNameForHttpResponse(xeroTenantId, repeatingInvoiceID, fileName, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getRepeatingInvoiceAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID repeatingInvoiceID,  String fileName,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoiceAttachmentByFileName");
        }// verify the required parameter 'repeatingInvoiceID' is set
        if (repeatingInvoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'repeatingInvoiceID' when calling getRepeatingInvoiceAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling getRepeatingInvoiceAttachmentByFileName");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getRepeatingInvoiceAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified Attachments on repeating invoices
    * <p><b>200</b> - Success - return response of attachment for Repeating Invoice as binary data
    * @param xeroTenantId Xero identifier for Tenant
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @param attachmentID Unique identifier for a Attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream  getRepeatingInvoiceAttachmentById(String xeroTenantId, UUID repeatingInvoiceID, UUID attachmentID, String contentType) throws IOException {
        HttpResponse response = getRepeatingInvoiceAttachmentByIdForHttpResponse(xeroTenantId, repeatingInvoiceID, attachmentID, contentType);
        InputStream is = response.getContent();
        return convertInputToByteArray(is);

    }

    public HttpResponse getRepeatingInvoiceAttachmentByIdForHttpResponse( String xeroTenantId,  UUID repeatingInvoiceID,  UUID attachmentID,  String contentType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoiceAttachmentById");
        }// verify the required parameter 'repeatingInvoiceID' is set
        if (repeatingInvoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'repeatingInvoiceID' when calling getRepeatingInvoiceAttachmentById");
        }// verify the required parameter 'attachmentID' is set
        if (attachmentID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'attachmentID' when calling getRepeatingInvoiceAttachmentById");
        }// verify the required parameter 'contentType' is set
        if (contentType == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contentType' when calling getRepeatingInvoiceAttachmentById");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{AttachmentID}";
        
        // Hacky path manipulation to support different return types from same endpoint
        String path = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{AttachmentID}";
        String type = "/pdf";
        if(path.toLowerCase().contains(type.toLowerCase())) {
            correctPath = path.replace("/pdf","");
            headers.setAccept("application/pdf"); 
        }
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);
        uriVariables.put("AttachmentID", attachmentID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Attachments on repeating invoice
    * <p><b>200</b> - Success - return response of type Attachments array with all Attachments for a specified Repeating Invoice
    * @param xeroTenantId Xero identifier for Tenant
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  getRepeatingInvoiceAttachments(String xeroTenantId, UUID repeatingInvoiceID) throws IOException {
        HttpResponse response = getRepeatingInvoiceAttachmentsForHttpResponse(xeroTenantId, repeatingInvoiceID);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getRepeatingInvoiceAttachmentsForHttpResponse( String xeroTenantId,  UUID repeatingInvoiceID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoiceAttachments");
        }// verify the required parameter 'repeatingInvoiceID' is set
        if (repeatingInvoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'repeatingInvoiceID' when calling getRepeatingInvoiceAttachments");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve history for a repeating invoice
    * <p><b>200</b> - Success - return response of type HistoryRecords array of all HistoryRecord for Repeating Invoice
    * @param xeroTenantId Xero identifier for Tenant
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords  getRepeatingInvoiceHistory(String xeroTenantId, UUID repeatingInvoiceID) throws IOException {
        HttpResponse response = getRepeatingInvoiceHistoryForHttpResponse(xeroTenantId, repeatingInvoiceID);
        TypeReference typeRef = new TypeReference<HistoryRecords>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getRepeatingInvoiceHistoryForHttpResponse( String xeroTenantId,  UUID repeatingInvoiceID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoiceHistory");
        }// verify the required parameter 'repeatingInvoiceID' is set
        if (repeatingInvoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'repeatingInvoiceID' when calling getRepeatingInvoiceHistory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/History";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve any repeating invoices
    * <p><b>200</b> - Success - return response of type Repeating Invoices array for all Repeating Invoice
    * @param xeroTenantId Xero identifier for Tenant
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return RepeatingInvoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public RepeatingInvoices  getRepeatingInvoices(String xeroTenantId, String where, String order) throws IOException {
        HttpResponse response = getRepeatingInvoicesForHttpResponse(xeroTenantId, where, order);
        TypeReference typeRef = new TypeReference<RepeatingInvoices>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getRepeatingInvoicesForHttpResponse( String xeroTenantId,  String where,  String order) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoices");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/RepeatingInvoices";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve report for AgedPayablesByContact
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactId Unique identifier for a Contact
    * @param date The date of the Aged Payables By Contact report
    * @param fromDate The from date of the Aged Payables By Contact report
    * @param toDate The to date of the Aged Payables By Contact report
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows  getReportAgedPayablesByContact(String xeroTenantId, UUID contactId, LocalDate date, LocalDate fromDate, LocalDate toDate) throws IOException {
        HttpResponse response = getReportAgedPayablesByContactForHttpResponse(xeroTenantId, contactId, date, fromDate, toDate);
        TypeReference typeRef = new TypeReference<ReportWithRows>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReportAgedPayablesByContactForHttpResponse( String xeroTenantId,  UUID contactId,  LocalDate date,  LocalDate fromDate,  LocalDate toDate) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReportAgedPayablesByContact");
        }// verify the required parameter 'contactId' is set
        if (contactId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactId' when calling getReportAgedPayablesByContact");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Reports/AgedPayablesByContact";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (date != null) {
            String key = "date";
            Object value = date;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (fromDate != null) {
            String key = "fromDate";
            Object value = fromDate;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (toDate != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve report for AgedReceivablesByContact
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactId Unique identifier for a Contact
    * @param date The date of the Aged Receivables By Contact report
    * @param fromDate The from date of the Aged Receivables By Contact report
    * @param toDate The to date of the Aged Receivables By Contact report
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows  getReportAgedReceivablesByContact(String xeroTenantId, UUID contactId, LocalDate date, LocalDate fromDate, LocalDate toDate) throws IOException {
        HttpResponse response = getReportAgedReceivablesByContactForHttpResponse(xeroTenantId, contactId, date, fromDate, toDate);
        TypeReference typeRef = new TypeReference<ReportWithRows>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReportAgedReceivablesByContactForHttpResponse( String xeroTenantId,  UUID contactId,  LocalDate date,  LocalDate fromDate,  LocalDate toDate) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReportAgedReceivablesByContact");
        }// verify the required parameter 'contactId' is set
        if (contactId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactId' when calling getReportAgedReceivablesByContact");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Reports/AgedReceivablesByContact";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (date != null) {
            String key = "date";
            Object value = date;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (fromDate != null) {
            String key = "fromDate";
            Object value = fromDate;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (toDate != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve report for BAS only valid for AU orgs
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param xeroTenantId Xero identifier for Tenant
    * @param reportID Unique identifier for a Report
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows  getReportBASorGST(String xeroTenantId, String reportID) throws IOException {
        HttpResponse response = getReportBASorGSTForHttpResponse(xeroTenantId, reportID);
        TypeReference typeRef = new TypeReference<ReportWithRows>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReportBASorGSTForHttpResponse( String xeroTenantId,  String reportID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReportBASorGST");
        }// verify the required parameter 'reportID' is set
        if (reportID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'reportID' when calling getReportBASorGST");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Reports/{ReportID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ReportID", reportID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve report for BAS only valid for AU orgs
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param xeroTenantId Xero identifier for Tenant
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows  getReportBASorGSTList(String xeroTenantId) throws IOException {
        HttpResponse response = getReportBASorGSTListForHttpResponse(xeroTenantId);
        TypeReference typeRef = new TypeReference<ReportWithRows>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReportBASorGSTListForHttpResponse( String xeroTenantId) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReportBASorGSTList");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Reports";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve report for BalanceSheet
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param xeroTenantId Xero identifier for Tenant
    * @param date The date of the Balance Sheet report
    * @param periods The number of periods for the Balance Sheet report
    * @param timeframe The period size to compare to (MONTH, QUARTER, YEAR)
    * @param trackingOptionID1 The tracking option 1 for the Balance Sheet report
    * @param trackingOptionID2 The tracking option 2 for the Balance Sheet report
    * @param standardLayout The standard layout boolean for the Balance Sheet report
    * @param paymentsOnly return a cash basis for the Balance Sheet report
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows  getReportBalanceSheet(String xeroTenantId, String date, Integer periods, String timeframe, String trackingOptionID1, String trackingOptionID2, Boolean standardLayout, Boolean paymentsOnly) throws IOException {
        HttpResponse response = getReportBalanceSheetForHttpResponse(xeroTenantId, date, periods, timeframe, trackingOptionID1, trackingOptionID2, standardLayout, paymentsOnly);
        TypeReference typeRef = new TypeReference<ReportWithRows>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReportBalanceSheetForHttpResponse( String xeroTenantId,  String date,  Integer periods,  String timeframe,  String trackingOptionID1,  String trackingOptionID2,  Boolean standardLayout,  Boolean paymentsOnly) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReportBalanceSheet");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Reports/BalanceSheet";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (periods != null) {
            String key = "periods";
            Object value = periods;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (timeframe != null) {
            String key = "timeframe";
            Object value = timeframe;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (trackingOptionID1 != null) {
            String key = "trackingOptionID1";
            Object value = trackingOptionID1;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (trackingOptionID2 != null) {
            String key = "trackingOptionID2";
            Object value = trackingOptionID2;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (standardLayout != null) {
            String key = "standardLayout";
            Object value = standardLayout;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (paymentsOnly != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve report for BankSummary
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param xeroTenantId Xero identifier for Tenant
    * @param date The date for the Bank Summary report e.g. 2018-03-31
    * @param period The number of periods to compare (integer between 1 and 12)
    * @param timeframe The period size to compare to (1&#x3D;month, 3&#x3D;quarter, 12&#x3D;year)
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows  getReportBankSummary(String xeroTenantId, LocalDate date, Integer period, Integer timeframe) throws IOException {
        HttpResponse response = getReportBankSummaryForHttpResponse(xeroTenantId, date, period, timeframe);
        TypeReference typeRef = new TypeReference<ReportWithRows>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReportBankSummaryForHttpResponse( String xeroTenantId,  LocalDate date,  Integer period,  Integer timeframe) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReportBankSummary");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Reports/BankSummary";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (period != null) {
            String key = "period";
            Object value = period;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (timeframe != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve report for Budget Summary
    * <p><b>200</b> - success- return a Report with Rows object
    * @param xeroTenantId Xero identifier for Tenant
    * @param date The date for the Bank Summary report e.g. 2018-03-31
    * @param period The number of periods to compare (integer between 1 and 12)
    * @param timeframe The period size to compare to (1&#x3D;month, 3&#x3D;quarter, 12&#x3D;year)
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows  getReportBudgetSummary(String xeroTenantId, LocalDate date, Integer period, Integer timeframe) throws IOException {
        HttpResponse response = getReportBudgetSummaryForHttpResponse(xeroTenantId, date, period, timeframe);
        TypeReference typeRef = new TypeReference<ReportWithRows>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReportBudgetSummaryForHttpResponse( String xeroTenantId,  LocalDate date,  Integer period,  Integer timeframe) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReportBudgetSummary");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Reports/BudgetSummary";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (period != null) {
            String key = "period";
            Object value = period;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (timeframe != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve report for ExecutiveSummary
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param xeroTenantId Xero identifier for Tenant
    * @param date The date for the Bank Summary report e.g. 2018-03-31
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows  getReportExecutiveSummary(String xeroTenantId, LocalDate date) throws IOException {
        HttpResponse response = getReportExecutiveSummaryForHttpResponse(xeroTenantId, date);
        TypeReference typeRef = new TypeReference<ReportWithRows>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReportExecutiveSummaryForHttpResponse( String xeroTenantId,  LocalDate date) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReportExecutiveSummary");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Reports/ExecutiveSummary";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve report for ProfitAndLoss
    * <p><b>200</b> - Success - return response of type ReportWithRows
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
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows  getReportProfitAndLoss(String xeroTenantId, LocalDate fromDate, LocalDate toDate, Integer periods, String timeframe, String trackingCategoryID, String trackingCategoryID2, String trackingOptionID, String trackingOptionID2, Boolean standardLayout, Boolean paymentsOnly) throws IOException {
        HttpResponse response = getReportProfitAndLossForHttpResponse(xeroTenantId, fromDate, toDate, periods, timeframe, trackingCategoryID, trackingCategoryID2, trackingOptionID, trackingOptionID2, standardLayout, paymentsOnly);
        TypeReference typeRef = new TypeReference<ReportWithRows>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReportProfitAndLossForHttpResponse( String xeroTenantId,  LocalDate fromDate,  LocalDate toDate,  Integer periods,  String timeframe,  String trackingCategoryID,  String trackingCategoryID2,  String trackingOptionID,  String trackingOptionID2,  Boolean standardLayout,  Boolean paymentsOnly) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReportProfitAndLoss");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Reports/ProfitAndLoss";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (toDate != null) {
            String key = "toDate";
            Object value = toDate;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (periods != null) {
            String key = "periods";
            Object value = periods;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (timeframe != null) {
            String key = "timeframe";
            Object value = timeframe;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (trackingCategoryID != null) {
            String key = "trackingCategoryID";
            Object value = trackingCategoryID;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (trackingCategoryID2 != null) {
            String key = "trackingCategoryID2";
            Object value = trackingCategoryID2;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (trackingOptionID != null) {
            String key = "trackingOptionID";
            Object value = trackingOptionID;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (trackingOptionID2 != null) {
            String key = "trackingOptionID2";
            Object value = trackingOptionID2;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (standardLayout != null) {
            String key = "standardLayout";
            Object value = standardLayout;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (paymentsOnly != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve report for TenNinetyNine
    * <p><b>200</b> - Success - return response of type Reports
    * @param xeroTenantId Xero identifier for Tenant
    * @param reportYear The year of the 1099 report
    * @return Reports
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Reports  getReportTenNinetyNine(String xeroTenantId, String reportYear) throws IOException {
        HttpResponse response = getReportTenNinetyNineForHttpResponse(xeroTenantId, reportYear);
        TypeReference typeRef = new TypeReference<Reports>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReportTenNinetyNineForHttpResponse( String xeroTenantId,  String reportYear) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReportTenNinetyNine");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Reports/TenNinetyNine";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve report for TrialBalance
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param xeroTenantId Xero identifier for Tenant
    * @param date The date for the Trial Balance report e.g. 2018-03-31
    * @param paymentsOnly Return cash only basis for the Trial Balance report
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows  getReportTrialBalance(String xeroTenantId, LocalDate date, Boolean paymentsOnly) throws IOException {
        HttpResponse response = getReportTrialBalanceForHttpResponse(xeroTenantId, date, paymentsOnly);
        TypeReference typeRef = new TypeReference<ReportWithRows>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getReportTrialBalanceForHttpResponse( String xeroTenantId,  LocalDate date,  Boolean paymentsOnly) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getReportTrialBalance");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Reports/TrialBalance";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (paymentsOnly != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve Tax Rates
    * <p><b>200</b> - Success - return response of type TaxRates array with TaxRates
    * @param xeroTenantId Xero identifier for Tenant
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param taxType Filter by tax type
    * @return TaxRates
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TaxRates  getTaxRates(String xeroTenantId, String where, String order, String taxType) throws IOException {
        HttpResponse response = getTaxRatesForHttpResponse(xeroTenantId, where, order, taxType);
        TypeReference typeRef = new TypeReference<TaxRates>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getTaxRatesForHttpResponse( String xeroTenantId,  String where,  String order,  String taxType) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getTaxRates");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/TaxRates";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
            String key = "order";
            Object value = order;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (taxType != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve tracking categories and options
    * <p><b>200</b> - Success - return response of type TrackingCategories array of TrackingCategory
    * @param xeroTenantId Xero identifier for Tenant
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param includeArchived e.g. includeArchived&#x3D;true - Categories and options with a status of ARCHIVED will be included in the response
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories  getTrackingCategories(String xeroTenantId, String where, String order, Boolean includeArchived) throws IOException {
        HttpResponse response = getTrackingCategoriesForHttpResponse(xeroTenantId, where, order, includeArchived);
        TypeReference typeRef = new TypeReference<TrackingCategories>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getTrackingCategoriesForHttpResponse( String xeroTenantId,  String where,  String order,  Boolean includeArchived) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getTrackingCategories");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/TrackingCategories";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
            String key = "order";
            Object value = order;
            if (value instanceof Collection) {
                uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
            } else if (value instanceof Object[]) {
                uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
            } else {
                uriBuilder = uriBuilder.queryParam(key, value);
            }
        }        if (includeArchived != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve tracking categories and options for specified category
    * <p><b>200</b> - Success - return response of type TrackingCategories array of specified TrackingCategory
    * @param xeroTenantId Xero identifier for Tenant
    * @param trackingCategoryID Unique identifier for a TrackingCategory
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories  getTrackingCategory(String xeroTenantId, UUID trackingCategoryID) throws IOException {
        HttpResponse response = getTrackingCategoryForHttpResponse(xeroTenantId, trackingCategoryID);
        TypeReference typeRef = new TypeReference<TrackingCategories>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getTrackingCategoryForHttpResponse( String xeroTenantId,  UUID trackingCategoryID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getTrackingCategory");
        }// verify the required parameter 'trackingCategoryID' is set
        if (trackingCategoryID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'trackingCategoryID' when calling getTrackingCategory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/TrackingCategories/{TrackingCategoryID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("TrackingCategoryID", trackingCategoryID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified user
    * <p><b>200</b> - Success - return response of type Users array of specified User
    * @param xeroTenantId Xero identifier for Tenant
    * @param userID Unique identifier for a User
    * @return Users
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Users  getUser(String xeroTenantId, UUID userID) throws IOException {
        HttpResponse response = getUserForHttpResponse(xeroTenantId, userID);
        TypeReference typeRef = new TypeReference<Users>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getUserForHttpResponse( String xeroTenantId,  UUID userID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getUser");
        }// verify the required parameter 'userID' is set
        if (userID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'userID' when calling getUser");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Users/{UserID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("UserID", userID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve users
    * <p><b>200</b> - Success - return response of type Users array of all User
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Users
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Users  getUsers(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        HttpResponse response = getUsersForHttpResponse(xeroTenantId, ifModifiedSince, where, order);
        TypeReference typeRef = new TypeReference<Users>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse getUsersForHttpResponse( String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getUsers");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Users";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (order != null) {
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

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update a chart of accounts
    * <p><b>200</b> - Success - update existing Account and return response of type Accounts array with updated Account
    * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
    * @param xeroTenantId Xero identifier for Tenant
    * @param accountID Unique identifier for retrieving single object
    * @param accounts Request of type Accounts array with one Account
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts  updateAccount(String xeroTenantId, UUID accountID, Accounts accounts) throws IOException {
        HttpResponse response = updateAccountForHttpResponse(xeroTenantId, accountID, accounts);
        TypeReference typeRef = new TypeReference<Accounts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateAccountForHttpResponse( String xeroTenantId,  UUID accountID,  Accounts accounts) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateAccount");
        }// verify the required parameter 'accountID' is set
        if (accountID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accountID' when calling updateAccount");
        }// verify the required parameter 'accounts' is set
        if (accounts == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accounts' when calling updateAccount");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Accounts/{AccountID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("AccountID", accountID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(accounts);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update Attachment on Account by Filename
    * <p><b>200</b> - Success - return response of type Attachments array of Attachment
    * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
    * @param xeroTenantId Xero identifier for Tenant
    * @param accountID Unique identifier for Account object
    * @param fileName Name of the attachment
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  updateAccountAttachmentByFileName(String xeroTenantId, UUID accountID, String fileName, File body) throws IOException {
        HttpResponse response = updateAccountAttachmentByFileNameForHttpResponse(xeroTenantId, accountID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateAccountAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID accountID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateAccountAttachmentByFileName");
        }// verify the required parameter 'accountID' is set
        if (accountID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accountID' when calling updateAccountAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling updateAccountAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling updateAccountAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Accounts/{AccountID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("AccountID", accountID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update a single spend or receive money transaction
    * <p><b>200</b> - Success - return response of type BankTransactions array with updated BankTransaction
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @param bankTransactions The bankTransactions parameter
    * @return BankTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransactions  updateBankTransaction(String xeroTenantId, UUID bankTransactionID, BankTransactions bankTransactions) throws IOException {
        HttpResponse response = updateBankTransactionForHttpResponse(xeroTenantId, bankTransactionID, bankTransactions);
        TypeReference typeRef = new TypeReference<BankTransactions>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateBankTransactionForHttpResponse( String xeroTenantId,  UUID bankTransactionID,  BankTransactions bankTransactions) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateBankTransaction");
        }// verify the required parameter 'bankTransactionID' is set
        if (bankTransactionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransactionID' when calling updateBankTransaction");
        }// verify the required parameter 'bankTransactions' is set
        if (bankTransactions == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransactions' when calling updateBankTransaction");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransactions/{BankTransactionID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransactionID", bankTransactionID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(bankTransactions);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update an Attachment on BankTransaction by Filename
    * <p><b>200</b> - Success - return response of Attachments array of Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @param fileName The name of the file being attached
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  updateBankTransactionAttachmentByFileName(String xeroTenantId, UUID bankTransactionID, String fileName, File body) throws IOException {
        HttpResponse response = updateBankTransactionAttachmentByFileNameForHttpResponse(xeroTenantId, bankTransactionID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateBankTransactionAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID bankTransactionID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateBankTransactionAttachmentByFileName");
        }// verify the required parameter 'bankTransactionID' is set
        if (bankTransactionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransactionID' when calling updateBankTransactionAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling updateBankTransactionAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling updateBankTransactionAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransactionID", bankTransactionID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * <p><b>200</b> - Success - return response of Attachments array of 0 to N Attachment for a Bank Transfer
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @param fileName The name of the file being attached to a Bank Transfer
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  updateBankTransferAttachmentByFileName(String xeroTenantId, UUID bankTransferID, String fileName, File body) throws IOException {
        HttpResponse response = updateBankTransferAttachmentByFileNameForHttpResponse(xeroTenantId, bankTransferID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateBankTransferAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID bankTransferID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateBankTransferAttachmentByFileName");
        }// verify the required parameter 'bankTransferID' is set
        if (bankTransferID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'bankTransferID' when calling updateBankTransferAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling updateBankTransferAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling updateBankTransferAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/BankTransfers/{BankTransferID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("BankTransferID", bankTransferID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * <p><b>200</b> - Success - return response of type Contacts array with an updated Contact
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactID Unique identifier for a Contact
    * @param contacts an array of Contacts containing single Contact object with properties to update
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts  updateContact(String xeroTenantId, UUID contactID, Contacts contacts) throws IOException {
        HttpResponse response = updateContactForHttpResponse(xeroTenantId, contactID, contacts);
        TypeReference typeRef = new TypeReference<Contacts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateContactForHttpResponse( String xeroTenantId,  UUID contactID,  Contacts contacts) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateContact");
        }// verify the required parameter 'contactID' is set
        if (contactID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactID' when calling updateContact");
        }// verify the required parameter 'contacts' is set
        if (contacts == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contacts' when calling updateContact");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Contacts/{ContactID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactID", contactID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(contacts);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * <p><b>200</b> - Success - return response of type Attachments array with an updated Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactID Unique identifier for a Contact
    * @param fileName Name for the file you are attaching
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  updateContactAttachmentByFileName(String xeroTenantId, UUID contactID, String fileName, File body) throws IOException {
        HttpResponse response = updateContactAttachmentByFileNameForHttpResponse(xeroTenantId, contactID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateContactAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID contactID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateContactAttachmentByFileName");
        }// verify the required parameter 'contactID' is set
        if (contactID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactID' when calling updateContactAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling updateContactAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling updateContactAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Contacts/{ContactID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactID", contactID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update a Contract Group
    * <p><b>200</b> - Success - return response of type Contact Groups array of updated Contact Group
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param contactGroupID Unique identifier for a Contact Group
    * @param contactGroups an array of Contact groups with Name of specific group to update
    * @return ContactGroups
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ContactGroups  updateContactGroup(String xeroTenantId, UUID contactGroupID, ContactGroups contactGroups) throws IOException {
        HttpResponse response = updateContactGroupForHttpResponse(xeroTenantId, contactGroupID, contactGroups);
        TypeReference typeRef = new TypeReference<ContactGroups>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateContactGroupForHttpResponse( String xeroTenantId,  UUID contactGroupID,  ContactGroups contactGroups) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateContactGroup");
        }// verify the required parameter 'contactGroupID' is set
        if (contactGroupID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactGroupID' when calling updateContactGroup");
        }// verify the required parameter 'contactGroups' is set
        if (contactGroups == null) {
            throw new IllegalArgumentException("Missing the required parameter 'contactGroups' when calling updateContactGroup");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ContactGroups/{ContactGroupID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ContactGroupID", contactGroupID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(contactGroups);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update a specific credit note
    * <p><b>200</b> - Success - return response of type Credit Notes array with updated CreditNote
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param creditNoteID Unique identifier for a Credit Note
    * @param creditNotes an array of Credit Notes containing credit note details to update
    * @return CreditNotes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CreditNotes  updateCreditNote(String xeroTenantId, UUID creditNoteID, CreditNotes creditNotes) throws IOException {
        HttpResponse response = updateCreditNoteForHttpResponse(xeroTenantId, creditNoteID, creditNotes);
        TypeReference typeRef = new TypeReference<CreditNotes>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateCreditNoteForHttpResponse( String xeroTenantId,  UUID creditNoteID,  CreditNotes creditNotes) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateCreditNote");
        }// verify the required parameter 'creditNoteID' is set
        if (creditNoteID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNoteID' when calling updateCreditNote");
        }// verify the required parameter 'creditNotes' is set
        if (creditNotes == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNotes' when calling updateCreditNote");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes/{CreditNoteID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("CreditNoteID", creditNoteID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(creditNotes);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update Attachments on CreditNote by file name
    * <p><b>200</b> - Success - return response of type Attachments array with updated Attachment for specific Credit Note
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param creditNoteID Unique identifier for a Credit Note
    * @param fileName Name of the file you are attaching to Credit Note
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  updateCreditNoteAttachmentByFileName(String xeroTenantId, UUID creditNoteID, String fileName, File body) throws IOException {
        HttpResponse response = updateCreditNoteAttachmentByFileNameForHttpResponse(xeroTenantId, creditNoteID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateCreditNoteAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID creditNoteID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateCreditNoteAttachmentByFileName");
        }// verify the required parameter 'creditNoteID' is set
        if (creditNoteID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'creditNoteID' when calling updateCreditNoteAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling updateCreditNoteAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling updateCreditNoteAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("CreditNoteID", creditNoteID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update a specific employee used in Xero payrun
    * <p><b>200</b> - Success - return response of type Employees array with updated Employee
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param employeeID Unique identifier for a Employee
    * @param employees The employees parameter
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees  updateEmployee(String xeroTenantId, UUID employeeID, Employees employees) throws IOException {
        HttpResponse response = updateEmployeeForHttpResponse(xeroTenantId, employeeID, employees);
        TypeReference typeRef = new TypeReference<Employees>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateEmployeeForHttpResponse( String xeroTenantId,  UUID employeeID,  Employees employees) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateEmployee");
        }// verify the required parameter 'employeeID' is set
        if (employeeID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'employeeID' when calling updateEmployee");
        }// verify the required parameter 'employees' is set
        if (employees == null) {
            throw new IllegalArgumentException("Missing the required parameter 'employees' when calling updateEmployee");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Employees/{EmployeeID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("EmployeeID", employeeID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(employees);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update specified expense claims
    * <p><b>200</b> - Success - return response of type ExpenseClaims array with updated ExpenseClaim
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param expenseClaimID Unique identifier for a ExpenseClaim
    * @param expenseClaims The expenseClaims parameter
    * @return ExpenseClaims
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ExpenseClaims  updateExpenseClaim(String xeroTenantId, UUID expenseClaimID, ExpenseClaims expenseClaims) throws IOException {
        HttpResponse response = updateExpenseClaimForHttpResponse(xeroTenantId, expenseClaimID, expenseClaims);
        TypeReference typeRef = new TypeReference<ExpenseClaims>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateExpenseClaimForHttpResponse( String xeroTenantId,  UUID expenseClaimID,  ExpenseClaims expenseClaims) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateExpenseClaim");
        }// verify the required parameter 'expenseClaimID' is set
        if (expenseClaimID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'expenseClaimID' when calling updateExpenseClaim");
        }// verify the required parameter 'expenseClaims' is set
        if (expenseClaims == null) {
            throw new IllegalArgumentException("Missing the required parameter 'expenseClaims' when calling updateExpenseClaim");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ExpenseClaims/{ExpenseClaimID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ExpenseClaimID", expenseClaimID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(expenseClaims);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update a specified sales invoices or purchase bills
    * <p><b>200</b> - Success - return response of type Invoices array with updated Invoice
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoiceID Unique identifier for an Invoice
    * @param invoices The invoices parameter
    * @return Invoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Invoices  updateInvoice(String xeroTenantId, UUID invoiceID, Invoices invoices) throws IOException {
        HttpResponse response = updateInvoiceForHttpResponse(xeroTenantId, invoiceID, invoices);
        TypeReference typeRef = new TypeReference<Invoices>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateInvoiceForHttpResponse( String xeroTenantId,  UUID invoiceID,  Invoices invoices) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateInvoice");
        }// verify the required parameter 'invoiceID' is set
        if (invoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoiceID' when calling updateInvoice");
        }// verify the required parameter 'invoices' is set
        if (invoices == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoices' when calling updateInvoice");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices/{InvoiceID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("InvoiceID", invoiceID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(invoices);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update Attachment on invoices or purchase bills by it&#39;s filename
    * <p><b>200</b> - Success - return response of type Attachments array with updated Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param invoiceID Unique identifier for an Invoice
    * @param fileName Name of the file you are attaching
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  updateInvoiceAttachmentByFileName(String xeroTenantId, UUID invoiceID, String fileName, File body) throws IOException {
        HttpResponse response = updateInvoiceAttachmentByFileNameForHttpResponse(xeroTenantId, invoiceID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateInvoiceAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID invoiceID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateInvoiceAttachmentByFileName");
        }// verify the required parameter 'invoiceID' is set
        if (invoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'invoiceID' when calling updateInvoiceAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling updateInvoiceAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling updateInvoiceAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Invoices/{InvoiceID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("InvoiceID", invoiceID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to udpate a specified item
    * <p><b>200</b> - Success - return response of type Items array with updated Item
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param itemID Unique identifier for an Item
    * @param items The items parameter
    * @return Items
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Items  updateItem(String xeroTenantId, UUID itemID, Items items) throws IOException {
        HttpResponse response = updateItemForHttpResponse(xeroTenantId, itemID, items);
        TypeReference typeRef = new TypeReference<Items>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateItemForHttpResponse( String xeroTenantId,  UUID itemID,  Items items) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateItem");
        }// verify the required parameter 'itemID' is set
        if (itemID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'itemID' when calling updateItem");
        }// verify the required parameter 'items' is set
        if (items == null) {
            throw new IllegalArgumentException("Missing the required parameter 'items' when calling updateItem");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Items/{ItemID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ItemID", itemID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(items);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update a specified linked transactions (billable expenses)
    * <p><b>200</b> - Success - return response of type LinkedTransactions array with updated LinkedTransaction
    * <p><b>400</b> - Success - return response of type LinkedTransactions array with updated LinkedTransaction
    * @param xeroTenantId Xero identifier for Tenant
    * @param linkedTransactionID Unique identifier for a LinkedTransaction
    * @param linkedTransactions The linkedTransactions parameter
    * @return LinkedTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LinkedTransactions  updateLinkedTransaction(String xeroTenantId, UUID linkedTransactionID, LinkedTransactions linkedTransactions) throws IOException {
        HttpResponse response = updateLinkedTransactionForHttpResponse(xeroTenantId, linkedTransactionID, linkedTransactions);
        TypeReference typeRef = new TypeReference<LinkedTransactions>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateLinkedTransactionForHttpResponse( String xeroTenantId,  UUID linkedTransactionID,  LinkedTransactions linkedTransactions) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateLinkedTransaction");
        }// verify the required parameter 'linkedTransactionID' is set
        if (linkedTransactionID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'linkedTransactionID' when calling updateLinkedTransaction");
        }// verify the required parameter 'linkedTransactions' is set
        if (linkedTransactions == null) {
            throw new IllegalArgumentException("Missing the required parameter 'linkedTransactions' when calling updateLinkedTransaction");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/LinkedTransactions/{LinkedTransactionID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("LinkedTransactionID", linkedTransactionID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(linkedTransactions);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update a specified manual journal
    * <p><b>200</b> - Success - return response of type ManualJournals array with an updated ManualJournal
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param manualJournalID Unique identifier for a ManualJournal
    * @param manualJournals The manualJournals parameter
    * @return ManualJournals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ManualJournals  updateManualJournal(String xeroTenantId, UUID manualJournalID, ManualJournals manualJournals) throws IOException {
        HttpResponse response = updateManualJournalForHttpResponse(xeroTenantId, manualJournalID, manualJournals);
        TypeReference typeRef = new TypeReference<ManualJournals>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateManualJournalForHttpResponse( String xeroTenantId,  UUID manualJournalID,  ManualJournals manualJournals) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateManualJournal");
        }// verify the required parameter 'manualJournalID' is set
        if (manualJournalID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'manualJournalID' when calling updateManualJournal");
        }// verify the required parameter 'manualJournals' is set
        if (manualJournals == null) {
            throw new IllegalArgumentException("Missing the required parameter 'manualJournals' when calling updateManualJournal");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ManualJournals/{ManualJournalID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ManualJournalID", manualJournalID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(manualJournals);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update a specified Attachment on ManualJournal by file name
    * <p><b>200</b> - Success - return response of type Attachments array with an update Attachment for a ManualJournals
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param manualJournalID Unique identifier for a ManualJournal
    * @param fileName The name of the file being attached to a ManualJournal
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  updateManualJournalAttachmentByFileName(String xeroTenantId, UUID manualJournalID, String fileName, File body) throws IOException {
        HttpResponse response = updateManualJournalAttachmentByFileNameForHttpResponse(xeroTenantId, manualJournalID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateManualJournalAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID manualJournalID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateManualJournalAttachmentByFileName");
        }// verify the required parameter 'manualJournalID' is set
        if (manualJournalID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'manualJournalID' when calling updateManualJournalAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling updateManualJournalAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling updateManualJournalAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ManualJournalID", manualJournalID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update a specified purchase order
    * <p><b>200</b> - Success - return response of type PurchaseOrder array for updated PurchaseOrder
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param purchaseOrderID Unique identifier for a PurchaseOrder
    * @param purchaseOrders The purchaseOrders parameter
    * @return PurchaseOrders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PurchaseOrders  updatePurchaseOrder(String xeroTenantId, UUID purchaseOrderID, PurchaseOrders purchaseOrders) throws IOException {
        HttpResponse response = updatePurchaseOrderForHttpResponse(xeroTenantId, purchaseOrderID, purchaseOrders);
        TypeReference typeRef = new TypeReference<PurchaseOrders>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updatePurchaseOrderForHttpResponse( String xeroTenantId,  UUID purchaseOrderID,  PurchaseOrders purchaseOrders) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updatePurchaseOrder");
        }// verify the required parameter 'purchaseOrderID' is set
        if (purchaseOrderID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'purchaseOrderID' when calling updatePurchaseOrder");
        }// verify the required parameter 'purchaseOrders' is set
        if (purchaseOrders == null) {
            throw new IllegalArgumentException("Missing the required parameter 'purchaseOrders' when calling updatePurchaseOrder");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PurchaseOrders/{PurchaseOrderID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PurchaseOrderID", purchaseOrderID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(purchaseOrders);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to retrieve a specified draft expense claim receipts
    * <p><b>200</b> - Success - return response of type Receipts array for updated Receipt
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param receiptID Unique identifier for a Receipt
    * @param receipts The receipts parameter
    * @return Receipts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Receipts  updateReceipt(String xeroTenantId, UUID receiptID, Receipts receipts) throws IOException {
        HttpResponse response = updateReceiptForHttpResponse(xeroTenantId, receiptID, receipts);
        TypeReference typeRef = new TypeReference<Receipts>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateReceiptForHttpResponse( String xeroTenantId,  UUID receiptID,  Receipts receipts) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateReceipt");
        }// verify the required parameter 'receiptID' is set
        if (receiptID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'receiptID' when calling updateReceipt");
        }// verify the required parameter 'receipts' is set
        if (receipts == null) {
            throw new IllegalArgumentException("Missing the required parameter 'receipts' when calling updateReceipt");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Receipts/{ReceiptID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ReceiptID", receiptID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(receipts);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update Attachment on expense claim receipts by file name
    * <p><b>200</b> - Success - return response of type Attachments array with updated Attachment for a specified Receipt
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param receiptID Unique identifier for a Receipt
    * @param fileName The name of the file being attached to the Receipt
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  updateReceiptAttachmentByFileName(String xeroTenantId, UUID receiptID, String fileName, File body) throws IOException {
        HttpResponse response = updateReceiptAttachmentByFileNameForHttpResponse(xeroTenantId, receiptID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateReceiptAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID receiptID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateReceiptAttachmentByFileName");
        }// verify the required parameter 'receiptID' is set
        if (receiptID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'receiptID' when calling updateReceiptAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling updateReceiptAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling updateReceiptAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Receipts/{ReceiptID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ReceiptID", receiptID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update specified attachment on repeating invoices by file name
    * <p><b>200</b> - Success - return response of type Attachments array with specified Attachment for a specified Repeating Invoice
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @param fileName The name of the file being attached to a Repeating Invoice
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments  updateRepeatingInvoiceAttachmentByFileName(String xeroTenantId, UUID repeatingInvoiceID, String fileName, File body) throws IOException {
        HttpResponse response = updateRepeatingInvoiceAttachmentByFileNameForHttpResponse(xeroTenantId, repeatingInvoiceID, fileName, body);
        TypeReference typeRef = new TypeReference<Attachments>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateRepeatingInvoiceAttachmentByFileNameForHttpResponse( String xeroTenantId,  UUID repeatingInvoiceID,  String fileName, File  body) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateRepeatingInvoiceAttachmentByFileName");
        }// verify the required parameter 'repeatingInvoiceID' is set
        if (repeatingInvoiceID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'repeatingInvoiceID' when calling updateRepeatingInvoiceAttachmentByFileName");
        }// verify the required parameter 'fileName' is set
        if (fileName == null) {
            throw new IllegalArgumentException("Missing the required parameter 'fileName' when calling updateRepeatingInvoiceAttachmentByFileName");
        }// verify the required parameter 'body' is set
        if (body == null) {
            throw new IllegalArgumentException("Missing the required parameter 'body' when calling updateRepeatingInvoiceAttachmentByFileName");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID);
        uriVariables.put("FileName", fileName);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        java.nio.file.Path bodyPath = body.toPath();
        String mimeType = Files.probeContentType(bodyPath);
        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        
        content = new FileContent(mimeType, body);
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update Tax Rates
    * <p><b>200</b> - Success - return response of type TaxRates array updated TaxRate
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param taxRates The taxRates parameter
    * @return TaxRates
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TaxRates  updateTaxRate(String xeroTenantId, TaxRates taxRates) throws IOException {
        HttpResponse response = updateTaxRateForHttpResponse(xeroTenantId, taxRates);
        TypeReference typeRef = new TypeReference<TaxRates>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateTaxRateForHttpResponse( String xeroTenantId,  TaxRates taxRates) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateTaxRate");
        }// verify the required parameter 'taxRates' is set
        if (taxRates == null) {
            throw new IllegalArgumentException("Missing the required parameter 'taxRates' when calling updateTaxRate");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/TaxRates";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(taxRates);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

    

  /**
    * Allows you to update tracking categories
    * <p><b>200</b> - Success - return response of type TrackingCategories array of updated TrackingCategory
    * <p><b>400</b> - A failed request due to validation error
    * @param xeroTenantId Xero identifier for Tenant
    * @param trackingCategoryID Unique identifier for a TrackingCategory
    * @param trackingCategory The trackingCategory parameter
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories  updateTrackingCategory(String xeroTenantId, UUID trackingCategoryID, TrackingCategory trackingCategory) throws IOException {
        HttpResponse response = updateTrackingCategoryForHttpResponse(xeroTenantId, trackingCategoryID, trackingCategory);
        TypeReference typeRef = new TypeReference<TrackingCategories>() {};
        return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    }

    public HttpResponse updateTrackingCategoryForHttpResponse( String xeroTenantId,  UUID trackingCategoryID,  TrackingCategory trackingCategory) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateTrackingCategory");
        }// verify the required parameter 'trackingCategoryID' is set
        if (trackingCategoryID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'trackingCategoryID' when calling updateTrackingCategory");
        }// verify the required parameter 'trackingCategory' is set
        if (trackingCategory == null) {
            throw new IllegalArgumentException("Missing the required parameter 'trackingCategory' when calling updateTrackingCategory");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/TrackingCategories/{TrackingCategoryID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("TrackingCategoryID", trackingCategoryID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        //HttpContent content = new FileContent(mimeType, body);
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(trackingCategory);
        
        
        
        return apiClient.getHttpRequestFactory().buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
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
