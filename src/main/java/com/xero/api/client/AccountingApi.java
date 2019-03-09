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
import com.xero.api.exception.XeroExceptionHandler;
import com.xero.model.*;
import com.xero.api.*;

import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.ws.rs.core.UriBuilder;

public class AccountingApi {
    private ApiClient apiClient;
    private XeroExceptionHandler xeroExceptionHandler;
    private Config config;
    private SignerFactory signerFactory;
    private String token = null;
    private String tokenSecret = null;
    final static Logger logger = LogManager.getLogger(XeroClient.class);
    protected static final DateFormat utcFormatter;

    static {
        utcFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    protected static final Pattern MESSAGE_PATTERN = Pattern.compile("<Message>(.*)</Message>");
    protected final ObjectFactory objFactory = new ObjectFactory();

    
    public AccountingApi(Config config) {
        this(config, new ConfigBasedSignerFactory(config));
        this.xeroExceptionHandler = new XeroExceptionHandler();
    }

    public AccountingApi(Config config, SignerFactory signerFactory) {
        this.config = config;
        this.signerFactory = signerFactory;
        this.xeroExceptionHandler = new XeroExceptionHandler();
    }

    public AccountingApi(ApiClient apiClient) {
        this(JsonConfig.getInstance());
        this.xeroExceptionHandler = new XeroExceptionHandler();
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void setOAuthToken(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    
    protected String DATA(String url, String body, Map<String, String> params, String method) throws IOException {
        return this.DATA(url,body,params,method,null, "application/json");
    }

    protected String DATA(String url, String body, Map<String, String> params, String method, OffsetDateTime ifModifiedSince) throws IOException {
        return this.DATA(url,body,params,method,ifModifiedSince,"application/json");
    }

    protected String DATA(String url, String body, Map<String, String> params, String method, String contentType) throws IOException {
        return this.DATA(url,body,params,method,null,contentType);
    }

    protected String DATA(String url, String body, Map<String, String> params, String method, OffsetDateTime ifModifiedSince, String contentType) throws IOException {
        
        OAuthRequestResource req = new OAuthRequestResource(
            config, 
            signerFactory, 
            url, 
            method, 
            body, 
            params,
            contentType,
            "application/json");
        
        req.setToken(token);
        req.setTokenSecret(tokenSecret);
        
        if (ifModifiedSince != null) {
            req.setIfModifiedSince(ifModifiedSince);
        }

        try {
            Map<String, String>  resp = req.execute();
            Object r = resp.get("content");
            return r.toString();
        } catch (IOException ioe) {
             throw xeroExceptionHandler.convertException(ioe);
        }
    }

    protected String DATA(String url, String body, Map<String, String> params, String method, String xeroApplicationId, String xeroTenantId, String xeroUserId) throws IOException {
        
        OAuthRequestResource req = new OAuthRequestResource(
            config, 
            signerFactory, 
            url, 
            method, 
            body, 
            params,
            null,
            "application/json");
        
        req.setToken(token);
        req.setTokenSecret(tokenSecret);
        
        //if (ifModifiedSince != null) {
        //    req.setIfModifiedSince(ifModifiedSince);
        //}

        try {
            Map<String, String>  resp = req.execute();
            Object r = resp.get("content");
            return r.toString();
        } catch (IOException ioe) {
             throw xeroExceptionHandler.convertException(ioe);
        }
    }

   
    protected ByteArrayInputStream FILE(String url, String body, Map<String, String> params, String method) throws IOException {
       return this.FILE(url,body,params,method,"application/octet-stream");
    }

    protected ByteArrayInputStream FILE(String url, String body, Map<String, String> params, String method, String accept) throws IOException {
        
        OAuthRequestResource req = new OAuthRequestResource(
            config, 
            signerFactory, 
            url, 
            method, 
            body, 
            params,
            accept,
            "application/json");
        
        req.setToken(token);
        req.setTokenSecret(tokenSecret);
        
        try {
            ByteArrayInputStream resp = req.executefile();
            return resp;
        } catch (IOException ioe) {
             throw xeroExceptionHandler.convertException(ioe);
        }
    }

    protected String FILE(String url, String body, Map<String, String> params, String method, byte[] byteBody) throws IOException {
        return this.FILE(url,body,params,method,byteBody,"application/octet-stream");
    }
    
    protected String FILE(String url, String body, Map<String, String> params, String method, byte[] byteBody, String contentType) throws IOException {
        
        OAuthRequestResource req = new OAuthRequestResource(
            config, 
            signerFactory, 
            url, 
            method,
            contentType,
            byteBody, 
            params,
            "application/json");
        
        req.setToken(token);
        req.setTokenSecret(tokenSecret);
       
        try {
            Map<String, String>  resp = req.execute();
            Object r = resp.get("content");
            return r.toString();
        } catch (IOException ioe) {
             throw xeroExceptionHandler.convertException(ioe);
        }
    }

  /**
    * Allows you to create a new chart of accounts
    * <p><b>200</b> - Success - created new Account and return response of type Accounts array with new Account
    * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
    * @param account Request of type Account
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts createAccount(Account account) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Accounts";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(account);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Accounts> typeRef = new TypeReference<Accounts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create Attachment on Account
    * <p><b>200</b> - Success - return response of type Attachments array of Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param accountID Unique identifier for Account object
    * @param fileName Name of the attachment
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createAccountAttachmentByFileName(UUID accountID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Accounts/{AccountID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Accounts/{AccountID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("AccountID", accountID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "PUT", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create a spend or receive money transaction
    * <p><b>200</b> - Success - return response of type BankTransactions array with new BankTransaction
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransactions The bankTransactions parameter
    * @param summarizeErrors response format that shows validation errors for each bank transaction
    * @return BankTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransactions createBankTransaction(BankTransactions bankTransactions, Boolean summarizeErrors) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransactions";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (summarizeErrors != null) {
                addToMapIfNotNull(params, "SummarizeErrors", summarizeErrors);
            }
            
            strBody = apiClient.getObjectMapper().writeValueAsString(bankTransactions);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<BankTransactions> typeRef = new TypeReference<BankTransactions>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to createa an Attachment on BankTransaction by Filename
    * <p><b>200</b> - Success - return response of Attachments array of Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @param fileName The name of the file being attached
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createBankTransactionAttachmentByFileName(UUID bankTransactionID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransactionID", bankTransactionID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "PUT", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create history record for a bank transactions
    * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createBankTransactionHistoryRecord(UUID bankTransactionID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransactions/{BankTransactionID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransactions/{BankTransactionID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransactionID", bankTransactionID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create a bank transfers
    * <p><b>200</b> - Success - return response of BankTransfers array of one BankTransfer
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransfers The bankTransfers parameter
    * @return BankTransfers
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransfers createBankTransfer(BankTransfers bankTransfers) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransfers";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(bankTransfers);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<BankTransfers> typeRef = new TypeReference<BankTransfers>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - Success - return response of Attachments array of 0 to N Attachment for a Bank Transfer
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @param fileName The name of the file being attached to a Bank Transfer
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createBankTransferAttachmentByFileName(UUID bankTransferID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransfers/{BankTransferID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransfers/{BankTransferID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransferID", bankTransferID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "PUT", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - Success - return response HistoryRecords array with the newly created HistoryRecord for a Bank Transfer
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createBankTransferHistoryRecord(UUID bankTransferID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransfers/{BankTransferID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransfers/{BankTransferID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransferID", bankTransferID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Create one or many BatchPayments for invoices
    * <p><b>200</b> - Success - return response of type BatchPayments array of BatchPayment objects
    * <p><b>400</b> - A failed request due to validation error
    * @param batchPayments Request of type BatchPayments containing a Payments array with one or more Payment objects
    * @return BatchPayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BatchPayments createBatchPayment(BatchPayments batchPayments) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BatchPayments";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(batchPayments);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<BatchPayments> typeRef = new TypeReference<BatchPayments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create a history record for a Batch Payment
    * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
    * <p><b>400</b> - A failed request due to validation error
    * @param batchPaymentID Unique identifier for BatchPayment
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createBatchPaymentHistoryRecord(UUID batchPaymentID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BatchPayments/{BatchPaymentID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BatchPayments/{BatchPaymentID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BatchPaymentID", batchPaymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allow for the creation of new custom payment service for specified Branding Theme
    * <p><b>200</b> - Success - return response of type PaymentServices array with newly created PaymentService
    * <p><b>400</b> - A failed request due to validation error
    * @param brandingThemeID Unique identifier for a Branding Theme
    * @param paymentService The paymentService parameter
    * @return PaymentServices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PaymentServices createBrandingThemePaymentServices(UUID brandingThemeID, PaymentService paymentService) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BrandingThemes/{BrandingThemeID}/PaymentServices";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BrandingThemes/{BrandingThemeID}/PaymentServices";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BrandingThemeID", brandingThemeID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(paymentService);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<PaymentServices> typeRef = new TypeReference<PaymentServices>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - Success - return response of type Contacts array with newly created Contact
    * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
    * @param contact The contact parameter
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts createContact(Contact contact) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(contact);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Contacts> typeRef = new TypeReference<Contacts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - Success - return response of type Attachments array with an newly created Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param contactID Unique identifier for a Contact
    * @param fileName Name for the file you are attaching
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createContactAttachmentByFileName(UUID contactID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts/{ContactID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Contacts/{ContactID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactID", contactID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "PUT", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create a contact group
    * <p><b>200</b> - Success - return response of type Contact Groups array of newly created Contact Group
    * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
    * @param contactGroups The contactGroups parameter
    * @return ContactGroups
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ContactGroups createContactGroup(ContactGroups contactGroups) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ContactGroups";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(contactGroups);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<ContactGroups> typeRef = new TypeReference<ContactGroups>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to add Contacts to a Contract Group
    * <p><b>200</b> - Success - return response of type Contacts array of added Contacts
    * <p><b>400</b> - A failed request due to validation error
    * @param contactGroupID Unique identifier for a Contact Group
    * @param contacts The contacts parameter
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts createContactGroupContacts(UUID contactGroupID, Contacts contacts) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ContactGroups/{ContactGroupID}/Contacts";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ContactGroups/{ContactGroupID}/Contacts";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactGroupID", contactGroupID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(contacts);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Contacts> typeRef = new TypeReference<Contacts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a history records of an Contact
    * <p><b>200</b> - Success - return response of type History Records array of newly created History Record for a specific Contact
    * <p><b>400</b> - A failed request due to validation error
    * @param contactID Unique identifier for a Contact
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createContactHistory(UUID contactID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts/{ContactID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Contacts/{ContactID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactID", contactID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create a credit note
    * <p><b>200</b> - Success - return response of type Credit Notes array of newly created CreditNote
    * <p><b>400</b> - A failed request due to validation error
    * @param summarizeErrors shows validation errors for each credit note
    * @param creditNotes The creditNotes parameter
    * @return CreditNotes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CreditNotes createCreditNote(Boolean summarizeErrors, CreditNotes creditNotes) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (summarizeErrors != null) {
                addToMapIfNotNull(params, "SummarizeErrors", summarizeErrors);
            }
            
            strBody = apiClient.getObjectMapper().writeValueAsString(creditNotes);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<CreditNotes> typeRef = new TypeReference<CreditNotes>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create Allocation on CreditNote
    * <p><b>200</b> - Success - return response of type Allocations array with newly created Allocation for specific Credit Note
    * <p><b>400</b> - A failed request due to validation error
    * @param creditNoteID Unique identifier for a Credit Note
    * @param allocations The allocations parameter
    * @return Allocations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Allocations createCreditNoteAllocation(UUID creditNoteID, Allocations allocations) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes/{CreditNoteID}/Allocations";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/CreditNotes/{CreditNoteID}/Allocations";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("CreditNoteID", creditNoteID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(allocations);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Allocations> typeRef = new TypeReference<Allocations>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create Attachments on CreditNote by file name
    * <p><b>200</b> - Success - return response of type Attachments array with newly created Attachment for specific Credit Note
    * <p><b>400</b> - A failed request due to validation error
    * @param creditNoteID Unique identifier for a Credit Note
    * @param fileName Name of the file you are attaching to Credit Note
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createCreditNoteAttachmentByFileName(UUID creditNoteID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("CreditNoteID", creditNoteID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "PUT", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a history records of an CreditNote
    * <p><b>200</b> - Success - return response of type HistoryRecords array with newly created HistoryRecord for specific Credit Note
    * <p><b>400</b> - A failed request due to validation error
    * @param creditNoteID Unique identifier for a Credit Note
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createCreditNoteHistory(UUID creditNoteID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes/{CreditNoteID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/CreditNotes/{CreditNoteID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("CreditNoteID", creditNoteID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - Success - return response of type Currencies array with new Currency
    * <p><b>400</b> - A failed request due to validation error
    * @param currencies The currencies parameter
    * @return Currencies
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Currencies createCurrency(Currencies currencies) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Currencies";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(currencies);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Currencies> typeRef = new TypeReference<Currencies>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create new employees used in Xero payrun
    * <p><b>200</b> - Success - return response of type Employees array with new Employee
    * <p><b>400</b> - A failed request due to validation error
    * @param employees The employees parameter
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees createEmployee(Employees employees) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Employees";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(employees);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve expense claims
    * <p><b>200</b> - Success - return response of type ExpenseClaims array with newly created ExpenseClaim
    * <p><b>400</b> - A failed request due to validation error
    * @param expenseClaims The expenseClaims parameter
    * @param summarizeErrors shows validation errors for each expense claim
    * @return ExpenseClaims
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ExpenseClaims createExpenseClaim(ExpenseClaims expenseClaims, Boolean summarizeErrors) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ExpenseClaims";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (summarizeErrors != null) {
                addToMapIfNotNull(params, "SummarizeErrors", summarizeErrors);
            }
            
            strBody = apiClient.getObjectMapper().writeValueAsString(expenseClaims);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<ExpenseClaims> typeRef = new TypeReference<ExpenseClaims>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create a history records of an ExpenseClaim
    * <p><b>200</b> - Success - return response of type HistoryRecords array for newly created HistoryRecord for specific ExpenseClaim
    * <p><b>400</b> - A failed request due to validation error
    * @param expenseClaimID Unique identifier for a ExpenseClaim
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createExpenseClaimHistory(UUID expenseClaimID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ExpenseClaims/{ExpenseClaimID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ExpenseClaims/{ExpenseClaimID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ExpenseClaimID", expenseClaimID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create any sales invoices or purchase bills
    * <p><b>200</b> - Success - return response of type Invoices array with newly created Invoice
    * <p><b>400</b> - A failed request due to validation error
    * @param invoices The invoices parameter
    * @param summarizeErrors shows validation errors for each invoice
    * @return Invoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Invoices createInvoice(Invoices invoices, Boolean summarizeErrors) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (summarizeErrors != null) {
                addToMapIfNotNull(params, "SummarizeErrors", summarizeErrors);
            }
            
            strBody = apiClient.getObjectMapper().writeValueAsString(invoices);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Invoices> typeRef = new TypeReference<Invoices>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create an Attachment on invoices or purchase bills by it&#39;s filename
    * <p><b>200</b> - Success - return response of type Attachments array with newly created Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param invoiceID Unique identifier for an Invoice
    * @param fileName Name of the file you are attaching
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createInvoiceAttachmentByFileName(UUID invoiceID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices/{InvoiceID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Invoices/{InvoiceID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("InvoiceID", invoiceID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "PUT", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a history records of an invoice
    * <p><b>200</b> - Success - return response of type HistoryRecords array with newly created HistoryRecord for specific Invoice
    * <p><b>400</b> - A failed request due to validation error
    * @param invoiceID Unique identifier for an Invoice
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createInvoiceHistory(UUID invoiceID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices/{InvoiceID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Invoices/{InvoiceID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("InvoiceID", invoiceID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create an item
    * <p><b>200</b> - Success - return response of type Items array with newly created Item
    * <p><b>400</b> - A failed request due to validation error
    * @param items The items parameter
    * @return Items
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Items createItem(Items items) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Items";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(items);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Items> typeRef = new TypeReference<Items>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create a history record for items
    * <p><b>200</b> - Success - return response of type HistoryRecords array with newly created HistoryRecord for specific Item
    * <p><b>400</b> - A failed request due to validation error
    * @param itemID Unique identifier for an Item
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createItemHistory(UUID itemID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Items/{ItemID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Items/{ItemID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ItemID", itemID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create linked transactions (billable expenses)
    * <p><b>200</b> - Success - return response of type LinkedTransactions array with newly created LinkedTransaction
    * <p><b>400</b> - A failed request due to validation error
    * @param linkedTransactions The linkedTransactions parameter
    * @return LinkedTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LinkedTransactions createLinkedTransaction(LinkedTransactions linkedTransactions) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/LinkedTransactions";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(linkedTransactions);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<LinkedTransactions> typeRef = new TypeReference<LinkedTransactions>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create a manual journal
    * <p><b>200</b> - Success - return response of type ManualJournals array with newly created ManualJournal
    * <p><b>400</b> - A failed request due to validation error
    * @param manualJournals The manualJournals parameter
    * @return ManualJournals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ManualJournals createManualJournal(ManualJournals manualJournals) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ManualJournals";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(manualJournals);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<ManualJournals> typeRef = new TypeReference<ManualJournals>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create a specified Attachment on ManualJournal by file name
    * <p><b>200</b> - Success - return response of type Attachments array with a newly created Attachment for a ManualJournals
    * <p><b>400</b> - A failed request due to validation error
    * @param manualJournalID Unique identifier for a ManualJournal
    * @param fileName The name of the file being attached to a ManualJournal
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createManualJournalAttachmentByFileName(UUID manualJournalID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ManualJournalID", manualJournalID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "PUT", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Allocations for overpayments
    * <p><b>200</b> - Success - return response of type Allocations array with all Allocation for Overpayments
    * <p><b>400</b> - A failed request due to validation error
    * @param overpaymentID Unique identifier for a Overpayment
    * @param allocations The allocations parameter
    * @return Allocations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Allocations createOverpaymentAllocation(UUID overpaymentID, Allocations allocations) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Overpayments/{OverpaymentID}/Allocations";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Overpayments/{OverpaymentID}/Allocations";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("OverpaymentID", overpaymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(allocations);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Allocations> typeRef = new TypeReference<Allocations>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create history records of an Overpayment
    * <p><b>200</b> - Success - return response of type HistoryRecords array with newly created HistoryRecord for Overpayments
    * <p><b>400</b> - A failed request due to validation error
    * @param overpaymentID Unique identifier for a Overpayment
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createOverpaymentHistory(UUID overpaymentID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Overpayments/{OverpaymentID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Overpayments/{OverpaymentID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("OverpaymentID", overpaymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create payments for invoices and credit notes
    * <p><b>200</b> - Success - return response of type Payments array for newly created Payment
    * <p><b>400</b> - A failed request due to validation error
    * @param payments The payments parameter
    * @return Payments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payments createPayment(Payments payments) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Payments";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(payments);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Payments> typeRef = new TypeReference<Payments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create a history record for a payment
    * <p><b>200</b> - Success - return response of type HistoryRecords array with newly created HistoryRecord for Payments
    * <p><b>400</b> - A failed request due to validation error
    * @param paymentID Unique identifier for a Payment
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createPaymentHistory(UUID paymentID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Payments/{PaymentID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Payments/{PaymentID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("PaymentID", paymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create payment services
    * <p><b>200</b> - Success - return response of type PaymentServices array for newly created PaymentService
    * <p><b>400</b> - A failed request due to validation error
    * @param paymentServices The paymentServices parameter
    * @return PaymentServices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PaymentServices createPaymentService(PaymentServices paymentServices) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/PaymentServices";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(paymentServices);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<PaymentServices> typeRef = new TypeReference<PaymentServices>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create prepayments
    * <p><b>200</b> - Success - return response of type Prepayments array for newly created Prepayment
    * <p><b>400</b> - A failed request due to validation error
    * @param prepayments The prepayments parameter
    * @return Prepayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Prepayments createPrepayment(Prepayments prepayments) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Prepayments";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(prepayments);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Prepayments> typeRef = new TypeReference<Prepayments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create an Allocation for prepayments
    * <p><b>200</b> - Success - return response of type Allocations array of Allocation for all Prepayment
    * <p><b>400</b> - A failed request due to validation error
    * @param prepaymentID The prepaymentID parameter
    * @param allocations The allocations parameter
    * @return Allocations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Allocations createPrepaymentAllocation(UUID prepaymentID, Allocations allocations) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Prepayments/{PrepaymentID}/Allocations";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Prepayments/{PrepaymentID}/Allocations";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("PrepaymentID", prepaymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(allocations);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Allocations> typeRef = new TypeReference<Allocations>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create a history record for an Prepayment
    * <p><b>200</b> - Success - return response of type HistoryRecords array for newly created HistoryRecord for PrePayment
    * <p><b>400</b> - A failed request due to validation error
    * @param prepaymentID Unique identifier for a PrePayment
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createPrepaymentHistory(UUID prepaymentID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Prepayments/{PrepaymentID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Prepayments/{PrepaymentID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("PrepaymentID", prepaymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create purchase orders
    * <p><b>200</b> - Success - return response of type PurchaseOrder array for specified PurchaseOrder
    * <p><b>400</b> - A failed request due to validation error
    * @param purchaseOrders The purchaseOrders parameter
    * @param summarizeErrors shows validation errors for each purchase order.
    * @return PurchaseOrders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PurchaseOrders createPurchaseOrder(PurchaseOrders purchaseOrders, Boolean summarizeErrors) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/PurchaseOrders";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (summarizeErrors != null) {
                addToMapIfNotNull(params, "SummarizeErrors", summarizeErrors);
            }
            
            strBody = apiClient.getObjectMapper().writeValueAsString(purchaseOrders);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<PurchaseOrders> typeRef = new TypeReference<PurchaseOrders>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create HistoryRecord for purchase orders
    * <p><b>200</b> - Success - return response of type HistoryRecords array for newly created HistoryRecord for PurchaseOrder
    * <p><b>400</b> - A failed request due to validation error
    * @param purchaseOrderID Unique identifier for a PurchaseOrder
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createPurchaseOrderHistory(UUID purchaseOrderID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/PurchaseOrders/{PurchaseOrderID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/PurchaseOrders/{PurchaseOrderID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("PurchaseOrderID", purchaseOrderID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create draft expense claim receipts for any user
    * <p><b>200</b> - Success - return response of type Receipts array for newly created Receipt
    * <p><b>400</b> - A failed request due to validation error
    * @param receipts The receipts parameter
    * @return Receipts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Receipts createReceipt(Receipts receipts) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Receipts";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(receipts);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<Receipts> typeRef = new TypeReference<Receipts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create Attachment on expense claim receipts by file name
    * <p><b>200</b> - Success - return response of type Attachments array with newly created Attachment for a specified Receipt
    * <p><b>400</b> - A failed request due to validation error
    * @param receiptID Unique identifier for a Receipt
    * @param fileName The name of the file being attached to the Receipt
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createReceiptAttachmentByFileName(UUID receiptID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Receipts/{ReceiptID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Receipts/{ReceiptID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ReceiptID", receiptID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "PUT", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a history records of an Receipt
    * <p><b>200</b> - Success - return response of type HistoryRecords array for newly created HistoryRecord for Receipt
    * <p><b>400</b> - A failed request due to validation error
    * @param receiptID Unique identifier for a Receipt
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createReceiptHistory(UUID receiptID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Receipts/{ReceiptID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Receipts/{ReceiptID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ReceiptID", receiptID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create attachment on repeating invoices by file name
    * <p><b>200</b> - Success - return response of type Attachments array with updated Attachment for a specified Repeating Invoice
    * <p><b>400</b> - A failed request due to validation error
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @param fileName The name of the file being attached to a Repeating Invoice
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createRepeatingInvoiceAttachmentByFileName(UUID repeatingInvoiceID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "PUT", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create history for a repeating invoice
    * <p><b>200</b> - Success - return response of type HistoryRecords array with newly created HistoryRecord for Repeating Invoice
    * <p><b>400</b> - A failed request due to validation error
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createRepeatingInvoiceHistory(UUID repeatingInvoiceID, HistoryRecords historyRecords) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/RepeatingInvoices/{RepeatingInvoiceID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(historyRecords);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create Tax Rates
    * <p><b>200</b> - Success - return response of type TaxRates array newly created TaxRate
    * <p><b>400</b> - A failed request due to validation error
    * @param taxRates The taxRates parameter
    * @return TaxRates
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TaxRates createTaxRate(TaxRates taxRates) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TaxRates";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(taxRates);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<TaxRates> typeRef = new TypeReference<TaxRates>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create tracking categories
    * <p><b>200</b> - Success - return response of type TrackingCategories array of newly created TrackingCategory
    * <p><b>400</b> - A failed request due to validation error
    * @param trackingCategory The trackingCategory parameter
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories createTrackingCategory(TrackingCategory trackingCategory) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TrackingCategories";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(trackingCategory);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<TrackingCategories> typeRef = new TypeReference<TrackingCategories>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to create options for a specified tracking category
    * <p><b>200</b> - Success - return response of type TrackingOptions array of options for a specified category
    * <p><b>400</b> - A failed request due to validation error
    * @param trackingCategoryID Unique identifier for a TrackingCategory
    * @param trackingOption The trackingOption parameter
    * @return TrackingOptions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingOptions createTrackingOptions(UUID trackingCategoryID, TrackingOption trackingOption) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TrackingCategories/{TrackingCategoryID}/Options";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/TrackingCategories/{TrackingCategoryID}/Options";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("TrackingCategoryID", trackingCategoryID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(trackingOption);

            String response = this.DATA(url, strBody, params, "PUT");

            TypeReference<TrackingOptions> typeRef = new TypeReference<TrackingOptions>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to delete a chart of accounts
    * <p><b>200</b> - Success - delete existing Account and return response of type Accounts array with deleted Account
    * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
    * @param accountID Unique identifier for retrieving single object
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts deleteAccount(UUID accountID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Accounts/{AccountID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Accounts/{AccountID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("AccountID", accountID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "DELETE");

            TypeReference<Accounts> typeRef = new TypeReference<Accounts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to delete a specific Contact from a Contract Group
    * <p><b>204</b> - Success - return response 204 no content
    * <p><b>400</b> - A failed request due to validation error
    * @param contactGroupID Unique identifier for a Contact Group
    * @param contactID Unique identifier for a Contact
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public void deleteContactGroupContact(UUID contactGroupID, UUID contactID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ContactGroups/{ContactGroupID}/Contacts/{ContactID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ContactGroups/{ContactGroupID}/Contacts/{ContactID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactGroupID", contactGroupID.toString());
            uriVariables.put("ContactID", contactID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "DELETE");

                       

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to delete  all Contacts from a Contract Group
    * <p><b>200</b> - Success - return response 204 no content
    * @param contactGroupID Unique identifier for a Contact Group
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public void deleteContactGroupContacts(UUID contactGroupID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ContactGroups/{ContactGroupID}/Contacts";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ContactGroups/{ContactGroupID}/Contacts";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactGroupID", contactGroupID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "DELETE");

                       

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to delete a specified item
    * <p><b>204</b> - Success - return response 204 no content
    * <p><b>400</b> - A failed request due to validation error
    * @param itemID Unique identifier for an Item
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public void deleteItem(UUID itemID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Items/{ItemID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Items/{ItemID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ItemID", itemID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "DELETE");

                       

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to delete a specified linked transactions (billable expenses)
    * <p><b>204</b> - Success - return response 204 no content
    * <p><b>400</b> - A failed request due to validation error
    * @param linkedTransactionID Unique identifier for a LinkedTransaction
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public void deleteLinkedTransaction(UUID linkedTransactionID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/LinkedTransactions/{LinkedTransactionID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/LinkedTransactions/{LinkedTransactionID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("LinkedTransactionID", linkedTransactionID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "DELETE");

                       

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to delete tracking categories
    * <p><b>200</b> - Success - return response of type TrackingCategories array of deleted TrackingCategory
    * <p><b>400</b> - A failed request due to validation error
    * @param trackingCategoryID Unique identifier for a TrackingCategory
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories deleteTrackingCategory(UUID trackingCategoryID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TrackingCategories/{TrackingCategoryID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/TrackingCategories/{TrackingCategoryID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("TrackingCategoryID", trackingCategoryID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "DELETE");

            TypeReference<TrackingCategories> typeRef = new TypeReference<TrackingCategories>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to delete a specified option for a specified tracking category
    * <p><b>200</b> - Success - return response of type TrackingOptions array of remaining options for a specified category
    * <p><b>400</b> - A failed request due to validation error
    * @param trackingCategoryID Unique identifier for a TrackingCategory
    * @param trackingOptionID Unique identifier for a Tracking Option
    * @return TrackingOptions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingOptions deleteTrackingOptions(UUID trackingCategoryID, UUID trackingOptionID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TrackingCategories/{TrackingCategoryID}/Options/{TrackingOptionID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/TrackingCategories/{TrackingCategoryID}/Options/{TrackingOptionID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("TrackingCategoryID", trackingCategoryID.toString());
            uriVariables.put("TrackingOptionID", trackingOptionID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "DELETE");

            TypeReference<TrackingOptions> typeRef = new TypeReference<TrackingOptions>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to email a copy of invoice to related Contact
    * <p><b>204</b> - Success - return response 204 no content
    * <p><b>400</b> - A failed request due to validation error
    * @param invoiceID Unique identifier for an Invoice
    * @param requestEmpty The requestEmpty parameter
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public void emailInvoice(UUID invoiceID, RequestEmpty requestEmpty) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices/{InvoiceID}/Email";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Invoices/{InvoiceID}/Email";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("InvoiceID", invoiceID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(requestEmpty);

            String response = this.DATA(url, strBody, params, "POST");

                       

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a single chart of accounts
    * <p><b>200</b> - Success - return response of type Accounts array with one Account
    * @param accountID Unique identifier for retrieving single object
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts getAccount(UUID accountID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Accounts/{AccountID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Accounts/{AccountID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("AccountID", accountID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Accounts> typeRef = new TypeReference<Accounts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachment on Account by Filename
    * <p><b>200</b> - Success - return response of attachment for Account as binary data
    * @param accountID Unique identifier for Account object
    * @param fileName Name of the attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getAccountAttachmentByFileName(UUID accountID, String fileName, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Accounts/{AccountID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Accounts/{AccountID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("AccountID", accountID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve specific Attachment on Account
    * <p><b>200</b> - Success - return response of attachment for Account as binary data
    * @param accountID Unique identifier for Account object
    * @param attachmentID Unique identifier for Attachment object
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getAccountAttachmentById(UUID accountID, UUID attachmentID, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Accounts/{AccountID}/Attachments/{AttachmentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Accounts/{AccountID}/Attachments/{AttachmentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("AccountID", accountID.toString());
            uriVariables.put("AttachmentID", attachmentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments for accounts
    * <p><b>200</b> - Success - return response of type Attachments array of Attachment
    * @param accountID Unique identifier for Account object
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getAccountAttachments(UUID accountID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Accounts/{AccountID}/Attachments";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Accounts/{AccountID}/Attachments";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("AccountID", accountID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve the full chart of accounts
    * <p><b>200</b> - Success - return response of type Accounts array with 0 to n Account
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts getAccounts(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Accounts";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<Accounts> typeRef = new TypeReference<Accounts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a single spend or receive money transaction
    * <p><b>200</b> - Success - return response of type BankTransactions array with a specific BankTransaction
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @return BankTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransactions getBankTransaction(UUID bankTransactionID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransactions/{BankTransactionID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransactions/{BankTransactionID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransactionID", bankTransactionID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<BankTransactions> typeRef = new TypeReference<BankTransactions>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on BankTransaction by Filename
    * <p><b>200</b> - Success - return response of attachment for BankTransaction as binary data
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @param fileName The name of the file being attached
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getBankTransactionAttachmentByFileName(UUID bankTransactionID, String fileName, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransactionID", bankTransactionID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on a specific BankTransaction
    * <p><b>200</b> - Success - return response of attachment for BankTransaction as binary data
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @param attachmentID Xero generated unique identifier for an attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getBankTransactionAttachmentById(UUID bankTransactionID, UUID attachmentID, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransactions/{BankTransactionID}/Attachments/{AttachmentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransactions/{BankTransactionID}/Attachments/{AttachmentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransactionID", bankTransactionID.toString());
            uriVariables.put("AttachmentID", attachmentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve any attachments to bank transactions
    * <p><b>200</b> - Success - return response of type Attachments array with 0 to n Attachment
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getBankTransactionAttachments(UUID bankTransactionID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransactions/{BankTransactionID}/Attachments";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransactions/{BankTransactionID}/Attachments";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransactionID", bankTransactionID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve any spend or receive money transactions
    * <p><b>200</b> - Success - return response of type BankTransactions array with 0 to n BankTransaction
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 bank transactions will be returned in a single API call with line items shown for each bank transaction
    * @return BankTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransactions getBankTransactions(OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransactions";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<BankTransactions> typeRef = new TypeReference<BankTransactions>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve history from a bank transactions
    * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getBankTransactionsHistory(UUID bankTransactionID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransactions/{BankTransactionID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransactions/{BankTransactionID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransactionID", bankTransactionID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve any bank transfers
    * <p><b>200</b> - Success - return response of BankTransfers array with one BankTransfer
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @return BankTransfers
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransfers getBankTransfer(UUID bankTransferID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransfers/{BankTransferID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransfers/{BankTransferID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransferID", bankTransferID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<BankTransfers> typeRef = new TypeReference<BankTransfers>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on BankTransfer by file name
    * <p><b>200</b> - Success - return response of binary data from the Attachment to a Bank Transfer
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @param fileName The name of the file being attached to a Bank Transfer
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getBankTransferAttachmentByFileName(UUID bankTransferID, String fileName, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransfers/{BankTransferID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransfers/{BankTransferID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransferID", bankTransferID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on BankTransfer
    * <p><b>200</b> - Success - return response of binary data from the Attachment to a Bank Transfer
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @param attachmentID Xero generated unique identifier for an Attachment to a bank transfer
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getBankTransferAttachmentById(UUID bankTransferID, UUID attachmentID, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransfers/{BankTransferID}/Attachments/{AttachmentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransfers/{BankTransferID}/Attachments/{AttachmentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransferID", bankTransferID.toString());
            uriVariables.put("AttachmentID", attachmentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments from  bank transfers
    * <p><b>200</b> - Success - return response of Attachments array of 0 to N Attachment for a Bank Transfer
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getBankTransferAttachments(UUID bankTransferID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransfers/{BankTransferID}/Attachments";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransfers/{BankTransferID}/Attachments";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransferID", bankTransferID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve history from a bank transfers
    * <p><b>200</b> - Success - return response of HistoryRecords array of 0 to N HistoryRecord for a Bank Transfer
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getBankTransferHistory(UUID bankTransferID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransfers/{BankTransferID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransfers/{BankTransferID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransferID", bankTransferID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve all bank transfers
    * <p><b>200</b> - Success - return response of BankTransfers array of 0 to N BankTransfer
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return BankTransfers
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransfers getBankTransfers(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransfers";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<BankTransfers> typeRef = new TypeReference<BankTransfers>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve history from a Batch Payment
    * <p><b>200</b> - Success - return response of type HistoryRecords array of HistoryRecord objects
    * @param batchPaymentID Unique identifier for BatchPayment
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getBatchPaymentHistory(UUID batchPaymentID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BatchPayments/{BatchPaymentID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BatchPayments/{BatchPaymentID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BatchPaymentID", batchPaymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Retrieve either one or many BatchPayments for invoices
    * <p><b>200</b> - Success - return response of type BatchPayments array of BatchPayment objects
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return BatchPayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BatchPayments getBatchPayments(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BatchPayments";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<BatchPayments> typeRef = new TypeReference<BatchPayments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specific BrandingThemes
    * <p><b>200</b> - Success - return response of type BrandingThemes with one BrandingTheme
    * @param brandingThemeID Unique identifier for a Branding Theme
    * @return BrandingThemes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BrandingThemes getBrandingTheme(UUID brandingThemeID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BrandingThemes/{BrandingThemeID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BrandingThemes/{BrandingThemeID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BrandingThemeID", brandingThemeID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<BrandingThemes> typeRef = new TypeReference<BrandingThemes>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve the Payment services for a Branding Theme
    * <p><b>200</b> - Success - return response of type PaymentServices array with 0 to N PaymentService
    * @param brandingThemeID Unique identifier for a Branding Theme
    * @return PaymentServices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PaymentServices getBrandingThemePaymentServices(UUID brandingThemeID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BrandingThemes/{BrandingThemeID}/PaymentServices";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BrandingThemes/{BrandingThemeID}/PaymentServices";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BrandingThemeID", brandingThemeID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<PaymentServices> typeRef = new TypeReference<PaymentServices>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve all the BrandingThemes
    * <p><b>200</b> - Success - return response of type BrandingThemes
    * @return BrandingThemes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BrandingThemes getBrandingThemes() throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BrandingThemes";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<BrandingThemes> typeRef = new TypeReference<BrandingThemes>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve, add and update contacts in a Xero organisation
    * <p><b>200</b> - Success - return response of type Contacts array with a unique Contact
    * @param contactID Unique identifier for a Contact
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts getContact(UUID contactID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts/{ContactID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Contacts/{ContactID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactID", contactID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Contacts> typeRef = new TypeReference<Contacts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on Contacts by file name
    * <p><b>200</b> - Success - return response of attachment for Contact as binary data
    * @param contactID Unique identifier for a Contact
    * @param fileName Name for the file you are attaching
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getContactAttachmentByFileName(UUID contactID, String fileName, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts/{ContactID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Contacts/{ContactID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactID", contactID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on Contacts
    * <p><b>200</b> - Success - return response of attachment for Contact as binary data
    * @param contactID Unique identifier for a Contact
    * @param attachmentID Unique identifier for a Attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getContactAttachmentById(UUID contactID, UUID attachmentID, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts/{ContactID}/Attachments/{AttachmentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Contacts/{ContactID}/Attachments/{AttachmentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactID", contactID.toString());
            uriVariables.put("AttachmentID", attachmentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve, add and update contacts in a Xero organisation
    * <p><b>200</b> - Success - return response of type Attachments array with 0 to N Attachment
    * @param contactID Unique identifier for a Contact
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getContactAttachments(UUID contactID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts/{ContactID}/Attachments";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Contacts/{ContactID}/Attachments";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactID", contactID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve CISSettings for a contact in a Xero organisation
    * <p><b>200</b> - Success - return response of type CISSettings for a specific Contact
    * @param contactID Unique identifier for a Contact
    * @return CISSettings
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CISSettings getContactCISSettings(UUID contactID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts/{ContactID}/CISSettings";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Contacts/{ContactID}/CISSettings";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactID", contactID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<CISSettings> typeRef = new TypeReference<CISSettings>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a unique Contract Group by ID
    * <p><b>200</b> - Success - return response of type Contact Groups array with a specific Contact Group
    * @param contactGroupID Unique identifier for a Contact Group
    * @return ContactGroups
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ContactGroups getContactGroup(UUID contactGroupID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ContactGroups/{ContactGroupID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ContactGroups/{ContactGroupID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactGroupID", contactGroupID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ContactGroups> typeRef = new TypeReference<ContactGroups>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve the ContactID and Name of all the contacts in a contact group
    * <p><b>200</b> - Success - return response of type Contact Groups array of Contact Group
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return ContactGroups
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ContactGroups getContactGroups(String where, String order) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ContactGroups";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ContactGroups> typeRef = new TypeReference<ContactGroups>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a history records of an Contact
    * <p><b>200</b> - Success - return response of type History Records array of 0 to N History Record for a specific Contact
    * @param contactID Unique identifier for a Contact
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getContactHistory(UUID contactID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts/{ContactID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Contacts/{ContactID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactID", contactID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve, add and update contacts in a Xero organisation
    * <p><b>200</b> - Success - return response of type Contacts array with 0 to N Contact
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param ids Filter by a comma separated list of ContactIDs. Allows you to retrieve a specific set of contacts in a single call.
    * @param page e.g. page&#x3D;1 - Up to 100 contacts will be returned in a single API call.
    * @param includeArchived e.g. includeArchived&#x3D;true - Contacts with a status of ARCHIVED will be included in the response
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts getContacts(OffsetDateTime ifModifiedSince, String where, String order, String ids, Integer page, Boolean includeArchived) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }if (ids != null) {
                addToMapIfNotNull(params, "IDs", ids);
            }if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }if (includeArchived != null) {
                addToMapIfNotNull(params, "includeArchived", includeArchived);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<Contacts> typeRef = new TypeReference<Contacts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specific credit note
    * <p><b>200</b> - Success - return response of type Credit Notes array with a unique CreditNote
    * @param creditNoteID Unique identifier for a Credit Note
    * @return CreditNotes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CreditNotes getCreditNote(UUID creditNoteID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes/{CreditNoteID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/CreditNotes/{CreditNoteID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("CreditNoteID", creditNoteID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<CreditNotes> typeRef = new TypeReference<CreditNotes>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Credit Note as PDF files
    * <p><b>200</b> - Success - return response of binary data from the Attachment to a Credit Note
    * @param creditNoteID Unique identifier for a Credit Note
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getCreditNoteAsPdf(UUID creditNoteID, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes/{CreditNoteID}/pdf";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/CreditNotes/{CreditNoteID}/pdf";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("CreditNoteID", creditNoteID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on CreditNote by file name
    * <p><b>200</b> - Success - return response of attachment for Credit Note as binary data
    * @param creditNoteID Unique identifier for a Credit Note
    * @param fileName Name of the file you are attaching to Credit Note
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getCreditNoteAttachmentByFileName(UUID creditNoteID, String fileName, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("CreditNoteID", creditNoteID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on CreditNote
    * <p><b>200</b> - Success - return response of attachment for Credit Note as binary data
    * @param creditNoteID Unique identifier for a Credit Note
    * @param attachmentID Unique identifier for a Attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getCreditNoteAttachmentById(UUID creditNoteID, UUID attachmentID, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes/{CreditNoteID}/Attachments/{AttachmentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/CreditNotes/{CreditNoteID}/Attachments/{AttachmentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("CreditNoteID", creditNoteID.toString());
            uriVariables.put("AttachmentID", attachmentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments for credit notes
    * <p><b>200</b> - Success - return response of type Attachments array with all Attachment for specific Credit Note
    * @param creditNoteID Unique identifier for a Credit Note
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getCreditNoteAttachments(UUID creditNoteID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes/{CreditNoteID}/Attachments";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/CreditNotes/{CreditNoteID}/Attachments";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("CreditNoteID", creditNoteID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a history records of an CreditNote
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for specific Credit Note
    * @param creditNoteID Unique identifier for a Credit Note
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getCreditNoteHistory(UUID creditNoteID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes/{CreditNoteID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/CreditNotes/{CreditNoteID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("CreditNoteID", creditNoteID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve any credit notes
    * <p><b>200</b> - Success - return response of type Credit Notes array of CreditNote
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 credit notes will be returned in a single API call with line items shown for each credit note
    * @return CreditNotes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CreditNotes getCreditNotes(OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<CreditNotes> typeRef = new TypeReference<CreditNotes>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve currencies for your organisation
    * <p><b>200</b> - Success - return response of type Currencies array with all Currencies
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Currencies
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Currencies getCurrencies(String where, String order) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Currencies";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Currencies> typeRef = new TypeReference<Currencies>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specific employee used in Xero payrun
    * <p><b>200</b> - Success - return response of type Employees array with specified Employee
    * @param employeeID Unique identifier for a Employee
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees getEmployee(UUID employeeID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Employees/{EmployeeID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Employees/{EmployeeID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("EmployeeID", employeeID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve employees used in Xero payrun
    * <p><b>200</b> - Success - return response of type Employees array with all Employee
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees getEmployees(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Employees";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified expense claim
    * <p><b>200</b> - Success - return response of type ExpenseClaims array with specified ExpenseClaim
    * @param expenseClaimID Unique identifier for a ExpenseClaim
    * @return ExpenseClaims
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ExpenseClaims getExpenseClaim(UUID expenseClaimID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ExpenseClaims/{ExpenseClaimID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ExpenseClaims/{ExpenseClaimID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ExpenseClaimID", expenseClaimID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ExpenseClaims> typeRef = new TypeReference<ExpenseClaims>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a history records of an ExpenseClaim
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for specific ExpenseClaim
    * @param expenseClaimID Unique identifier for a ExpenseClaim
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getExpenseClaimHistory(UUID expenseClaimID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ExpenseClaims/{ExpenseClaimID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ExpenseClaims/{ExpenseClaimID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ExpenseClaimID", expenseClaimID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve expense claims
    * <p><b>200</b> - Success - return response of type ExpenseClaims array with all ExpenseClaims
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return ExpenseClaims
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ExpenseClaims getExpenseClaims(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ExpenseClaims";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<ExpenseClaims> typeRef = new TypeReference<ExpenseClaims>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified sales invoice or purchase bill
    * <p><b>200</b> - Success - return response of type Invoices array with specified Invoices
    * @param invoiceID Unique identifier for an Invoice
    * @return Invoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Invoices getInvoice(UUID invoiceID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices/{InvoiceID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Invoices/{InvoiceID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("InvoiceID", invoiceID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Invoices> typeRef = new TypeReference<Invoices>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve invoices or purchase bills as PDF files
    * <p><b>200</b> - Success - return response of byte array pdf version of specified Invoices
    * @param invoiceID Unique identifier for an Invoice
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getInvoiceAsPdf(UUID invoiceID, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices/{InvoiceID}/pdf";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Invoices/{InvoiceID}/pdf";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("InvoiceID", invoiceID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachment on invoices or purchase bills by it&#39;s filename
    * <p><b>200</b> - Success - return response of attachment for Invoice as binary data
    * @param invoiceID Unique identifier for an Invoice
    * @param fileName Name of the file you are attaching
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getInvoiceAttachmentByFileName(UUID invoiceID, String fileName, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices/{InvoiceID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Invoices/{InvoiceID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("InvoiceID", invoiceID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified Attachment on invoices or purchase bills by it&#39;s ID
    * <p><b>200</b> - Success - return response of attachment for Invoice as binary data
    * @param invoiceID Unique identifier for an Invoice
    * @param attachmentID Unique identifier for an Attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getInvoiceAttachmentById(UUID invoiceID, UUID attachmentID, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices/{InvoiceID}/Attachments/{AttachmentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Invoices/{InvoiceID}/Attachments/{AttachmentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("InvoiceID", invoiceID.toString());
            uriVariables.put("AttachmentID", attachmentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on invoices or purchase bills
    * <p><b>200</b> - Success - return response of type Attachments array of Attachments for specified Invoices
    * @param invoiceID Unique identifier for an Invoice
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getInvoiceAttachments(UUID invoiceID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices/{InvoiceID}/Attachments";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Invoices/{InvoiceID}/Attachments";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("InvoiceID", invoiceID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a history records of an invoice
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for specific Invoice
    * @param invoiceID Unique identifier for an Invoice
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getInvoiceHistory(UUID invoiceID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices/{InvoiceID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Invoices/{InvoiceID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("InvoiceID", invoiceID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve invoice reminder settings
    * <p><b>200</b> - Success - return response of Invoice Reminders
    * @return InvoiceReminders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public InvoiceReminders getInvoiceReminders() throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/InvoiceReminders/Settings";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<InvoiceReminders> typeRef = new TypeReference<InvoiceReminders>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve any sales invoices or purchase bills
    * <p><b>200</b> - Success - return response of type Invoices array with all Invoices
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
    * @return Invoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Invoices getInvoices(OffsetDateTime ifModifiedSince, String where, String order, String ids, String invoiceNumbers, String contactIDs, String statuses, Integer page, Boolean includeArchived, Boolean createdByMyApp) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }if (ids != null) {
                addToMapIfNotNull(params, "IDs", ids);
            }if (invoiceNumbers != null) {
                addToMapIfNotNull(params, "InvoiceNumbers", invoiceNumbers);
            }if (contactIDs != null) {
                addToMapIfNotNull(params, "ContactIDs", contactIDs);
            }if (statuses != null) {
                addToMapIfNotNull(params, "Statuses", statuses);
            }if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }if (includeArchived != null) {
                addToMapIfNotNull(params, "includeArchived", includeArchived);
            }if (createdByMyApp != null) {
                addToMapIfNotNull(params, "createdByMyApp", createdByMyApp);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<Invoices> typeRef = new TypeReference<Invoices>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified item
    * <p><b>200</b> - Success - return response of type Items array with specified Item
    * @param itemID Unique identifier for an Item
    * @return Items
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Items getItem(UUID itemID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Items/{ItemID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Items/{ItemID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ItemID", itemID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Items> typeRef = new TypeReference<Items>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve history for items
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for specific Item
    * @param itemID Unique identifier for an Item
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getItemHistory(UUID itemID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Items/{ItemID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Items/{ItemID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ItemID", itemID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve any items
    * <p><b>200</b> - Success - return response of type Items array with all Item
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Items
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Items getItems(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Items";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<Items> typeRef = new TypeReference<Items>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified journals.
    * <p><b>200</b> - Success - return response of type Journals array with specified Journal
    * @param journalID Unique identifier for a Journal
    * @return Journals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Journals getJournal(UUID journalID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Journals/{JournalID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Journals/{JournalID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("JournalID", journalID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Journals> typeRef = new TypeReference<Journals>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve any journals.
    * <p><b>200</b> - Success - return response of type Journals array with all Journals
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param offset Offset by a specified journal number. e.g. journals with a JournalNumber greater than the offset will be returned
    * @param paymentsOnly Filter to retrieve journals on a cash basis. Journals are returned on an accrual basis by default.
    * @return Journals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Journals getJournals(OffsetDateTime ifModifiedSince, Integer offset, Boolean paymentsOnly) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Journals";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (offset != null) {
                addToMapIfNotNull(params, "offset", offset);
            }if (paymentsOnly != null) {
                addToMapIfNotNull(params, "paymentsOnly", paymentsOnly);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<Journals> typeRef = new TypeReference<Journals>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified linked transactions (billable expenses)
    * <p><b>200</b> - Success - return response of type LinkedTransactions array with a specified LinkedTransaction
    * @param linkedTransactionID Unique identifier for a LinkedTransaction
    * @return LinkedTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LinkedTransactions getLinkedTransaction(UUID linkedTransactionID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/LinkedTransactions/{LinkedTransactionID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/LinkedTransactions/{LinkedTransactionID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("LinkedTransactionID", linkedTransactionID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<LinkedTransactions> typeRef = new TypeReference<LinkedTransactions>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Retrieve linked transactions (billable expenses)
    * <p><b>200</b> - Success - return response of type LinkedTransactions array with all LinkedTransaction
    * @param page Up to 100 linked transactions will be returned in a single API call. Use the page parameter to specify the page to be returned e.g. page&#x3D;1.
    * @param linkedTransactionID The Xero identifier for an Linked Transaction
    * @param sourceTransactionID Filter by the SourceTransactionID. Get all the linked transactions created from a particular ACCPAY invoice
    * @param contactID Filter by the ContactID. Get all the linked transactions that have been assigned to a particular customer.
    * @param status Filter by the combination of ContactID and Status. Get all the linked transactions that have been assigned to a particular customer and have a particular status e.g. GET /LinkedTransactions?ContactID&#x3D;4bb34b03-3378-4bb2-a0ed-6345abf3224e&amp;Status&#x3D;APPROVED.
    * @param targetTransactionID Filter by the TargetTransactionID. Get all the linked transactions allocated to a particular ACCREC invoice
    * @return LinkedTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LinkedTransactions getLinkedTransactions(Integer page, String linkedTransactionID, String sourceTransactionID, String contactID, String status, String targetTransactionID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/LinkedTransactions";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }if (linkedTransactionID != null) {
                addToMapIfNotNull(params, "LinkedTransactionID", linkedTransactionID);
            }if (sourceTransactionID != null) {
                addToMapIfNotNull(params, "SourceTransactionID", sourceTransactionID);
            }if (contactID != null) {
                addToMapIfNotNull(params, "ContactID", contactID);
            }if (status != null) {
                addToMapIfNotNull(params, "Status", status);
            }if (targetTransactionID != null) {
                addToMapIfNotNull(params, "TargetTransactionID", targetTransactionID);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<LinkedTransactions> typeRef = new TypeReference<LinkedTransactions>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified manual journals
    * <p><b>200</b> - Success - return response of type ManualJournals array with a specified ManualJournals
    * @param manualJournalID Unique identifier for a ManualJournal
    * @return ManualJournals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ManualJournals getManualJournal(UUID manualJournalID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ManualJournals/{ManualJournalID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ManualJournals/{ManualJournalID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ManualJournalID", manualJournalID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ManualJournals> typeRef = new TypeReference<ManualJournals>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve specified Attachment on ManualJournal by file name
    * <p><b>200</b> - Success - return response of attachment for Manual Journal as binary data
    * @param manualJournalID Unique identifier for a ManualJournal
    * @param fileName The name of the file being attached to a ManualJournal
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getManualJournalAttachmentByFileName(UUID manualJournalID, String fileName, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ManualJournalID", manualJournalID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve specified Attachment on ManualJournals
    * <p><b>200</b> - Success - return response of attachment for Manual Journal as binary data
    * @param manualJournalID Unique identifier for a ManualJournal
    * @param attachmentID Unique identifier for a Attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getManualJournalAttachmentById(UUID manualJournalID, UUID attachmentID, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ManualJournals/{ManualJournalID}/Attachments/{AttachmentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ManualJournals/{ManualJournalID}/Attachments/{AttachmentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ManualJournalID", manualJournalID.toString());
            uriVariables.put("AttachmentID", attachmentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachment for manual journals
    * <p><b>200</b> - Success - return response of type Attachments array with all Attachments for a ManualJournals
    * @param manualJournalID Unique identifier for a ManualJournal
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getManualJournalAttachments(UUID manualJournalID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ManualJournals/{ManualJournalID}/Attachments";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ManualJournals/{ManualJournalID}/Attachments";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ManualJournalID", manualJournalID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve any manual journals
    * <p><b>200</b> - Success - return response of type ManualJournals array with a all ManualJournals
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 manual journals will be returned in a single API call with line items shown for each overpayment
    * @return ManualJournals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ManualJournals getManualJournals(OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ManualJournals";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<ManualJournals> typeRef = new TypeReference<ManualJournals>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a URL to an online invoice
    * <p><b>200</b> - Success - return response of type OnlineInvoice array with one OnlineInvoice
    * @param invoiceID Unique identifier for an Invoice
    * @return OnlineInvoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public OnlineInvoices getOnlineInvoice(UUID invoiceID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices/{InvoiceID}/OnlineInvoice";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Invoices/{InvoiceID}/OnlineInvoice";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("InvoiceID", invoiceID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<OnlineInvoices> typeRef = new TypeReference<OnlineInvoices>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Organisation details by short code
    * <p><b>200</b> - Success - return response of type Organisation array with specified Organisation
    * @param shortCode The shortCode parameter
    * @return Organisations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Organisations getOrganisationByShortCode(UUID shortCode) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Organisation/{ShortCode}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Organisation/{ShortCode}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ShortCode", shortCode.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Organisations> typeRef = new TypeReference<Organisations>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Organisation details
    * <p><b>200</b> - Success - return response of type Organisation array with all Organisation
    * @return Organisations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Organisations getOrganisations() throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Organisation";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Organisations> typeRef = new TypeReference<Organisations>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified overpayments
    * <p><b>200</b> - Success - return response of type Overpayments array with specified Overpayments
    * @param overpaymentID Unique identifier for a Overpayment
    * @return Overpayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Overpayments getOverpayment(UUID overpaymentID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Overpayments/{OverpaymentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Overpayments/{OverpaymentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("OverpaymentID", overpaymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Overpayments> typeRef = new TypeReference<Overpayments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a history records of an Overpayment
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for Overpayments
    * @param overpaymentID Unique identifier for a Overpayment
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getOverpaymentHistory(UUID overpaymentID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Overpayments/{OverpaymentID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Overpayments/{OverpaymentID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("OverpaymentID", overpaymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve overpayments
    * <p><b>200</b> - Success - return response of type Overpayments array with all Overpayments
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 overpayments will be returned in a single API call with line items shown for each overpayment
    * @return Overpayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Overpayments getOverpayments(OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Overpayments";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<Overpayments> typeRef = new TypeReference<Overpayments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified payment for invoices and credit notes
    * <p><b>200</b> - Success - return response of type Payments array for specified Payment
    * @param paymentID Unique identifier for a Payment
    * @return Payments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payments getPayment(UUID paymentID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Payments/{PaymentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Payments/{PaymentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("PaymentID", paymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Payments> typeRef = new TypeReference<Payments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve history records of a payment
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for Payments
    * @param paymentID Unique identifier for a Payment
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getPaymentHistory(UUID paymentID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Payments/{PaymentID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Payments/{PaymentID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("PaymentID", paymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve payment services
    * <p><b>200</b> - Success - return response of type PaymentServices array for all PaymentService
    * @return PaymentServices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PaymentServices getPaymentServices() throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/PaymentServices";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<PaymentServices> typeRef = new TypeReference<PaymentServices>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve payments for invoices and credit notes
    * <p><b>200</b> - Success - return response of type Payments array for all Payments
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Payments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payments getPayments(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Payments";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<Payments> typeRef = new TypeReference<Payments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified prepayments
    * <p><b>200</b> - Success - return response of type Prepayments array for a specified Prepayment
    * @param prepaymentID Unique identifier for a PrePayment
    * @return Prepayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Prepayments getPrepayment(UUID prepaymentID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Prepayments/{PrepaymentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Prepayments/{PrepaymentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("PrepaymentID", prepaymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Prepayments> typeRef = new TypeReference<Prepayments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a history records of an Prepayment
    * <p><b>200</b> - Success - return response of type HistoryRecords array with all HistoryRecord for PrePayment
    * @param prepaymentID Unique identifier for a PrePayment
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getPrepaymentHistory(UUID prepaymentID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Prepayments/{PrepaymentID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Prepayments/{PrepaymentID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("PrepaymentID", prepaymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve prepayments
    * <p><b>200</b> - Success - return response of type Prepayments array for all Prepayment
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 prepayments will be returned in a single API call with line items shown for each overpayment
    * @return Prepayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Prepayments getPrepayments(OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Prepayments";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<Prepayments> typeRef = new TypeReference<Prepayments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified purchase orders
    * <p><b>200</b> - Success - return response of type PurchaseOrder array for specified PurchaseOrder
    * @param purchaseOrderID Unique identifier for a PurchaseOrder
    * @return PurchaseOrders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PurchaseOrders getPurchaseOrder(UUID purchaseOrderID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/PurchaseOrders/{PurchaseOrderID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/PurchaseOrders/{PurchaseOrderID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("PurchaseOrderID", purchaseOrderID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<PurchaseOrders> typeRef = new TypeReference<PurchaseOrders>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve history for PurchaseOrder
    * <p><b>200</b> - Success - return response of type HistoryRecords array for all HistoryRecord for PurchaseOrder
    * @param purchaseOrderID Unique identifier for a PurchaseOrder
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getPurchaseOrderHistory(UUID purchaseOrderID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/PurchaseOrders/{PurchaseOrderID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/PurchaseOrders/{PurchaseOrderID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("PurchaseOrderID", purchaseOrderID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve purchase orders
    * <p><b>200</b> - Success - return response of type PurchaseOrder array of all PurchaseOrder
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param status Filter by purchase order status
    * @param dateFrom Filter by purchase order date (e.g. GET https://.../PurchaseOrders?DateFrom&#x3D;2015-12-01&amp;DateTo&#x3D;2015-12-31
    * @param dateTo Filter by purchase order date (e.g. GET https://.../PurchaseOrders?DateFrom&#x3D;2015-12-01&amp;DateTo&#x3D;2015-12-31
    * @param order Order by an any element
    * @param page To specify a page, append the page parameter to the URL e.g. ?page&#x3D;1. If there are 100 records in the response you will need to check if there is any more data by fetching the next page e.g ?page&#x3D;2 and continuing this process until no more results are returned.
    * @return PurchaseOrders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PurchaseOrders getPurchaseOrders(OffsetDateTime ifModifiedSince, String status, String dateFrom, String dateTo, String order, Integer page) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/PurchaseOrders";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (status != null) {
                addToMapIfNotNull(params, "Status", status);
            }if (dateFrom != null) {
                addToMapIfNotNull(params, "DateFrom", dateFrom);
            }if (dateTo != null) {
                addToMapIfNotNull(params, "DateTo", dateTo);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }if (page != null) {
                addToMapIfNotNull(params, "page", page);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<PurchaseOrders> typeRef = new TypeReference<PurchaseOrders>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified draft expense claim receipts
    * <p><b>200</b> - Success - return response of type Receipts array for a specified Receipt
    * @param receiptID Unique identifier for a Receipt
    * @return Receipts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Receipts getReceipt(UUID receiptID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Receipts/{ReceiptID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Receipts/{ReceiptID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ReceiptID", receiptID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Receipts> typeRef = new TypeReference<Receipts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on expense claim receipts by file name
    * <p><b>200</b> - Success - return response of attachment for Receipt as binary data
    * @param receiptID Unique identifier for a Receipt
    * @param fileName The name of the file being attached to the Receipt
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getReceiptAttachmentByFileName(UUID receiptID, String fileName, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Receipts/{ReceiptID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Receipts/{ReceiptID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ReceiptID", receiptID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on expense claim receipts by ID
    * <p><b>200</b> - Success - return response of attachment for Receipt as binary data
    * @param receiptID Unique identifier for a Receipt
    * @param attachmentID Unique identifier for a Attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getReceiptAttachmentById(UUID receiptID, UUID attachmentID, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Receipts/{ReceiptID}/Attachments/{AttachmentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Receipts/{ReceiptID}/Attachments/{AttachmentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ReceiptID", receiptID.toString());
            uriVariables.put("AttachmentID", attachmentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments for expense claim receipts
    * <p><b>200</b> - Success - return response of type Attachments array of Attachments for a specified Receipt
    * @param receiptID Unique identifier for a Receipt
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getReceiptAttachments(UUID receiptID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Receipts/{ReceiptID}/Attachments";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Receipts/{ReceiptID}/Attachments";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ReceiptID", receiptID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a history records of an Receipt
    * <p><b>200</b> - Success - return response of type HistoryRecords array of all HistoryRecord for Receipt
    * @param receiptID Unique identifier for a Receipt
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getReceiptHistory(UUID receiptID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Receipts/{ReceiptID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Receipts/{ReceiptID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ReceiptID", receiptID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve draft expense claim receipts for any user
    * <p><b>200</b> - Success - return response of type Receipts array for all Receipt
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Receipts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Receipts getReceipts(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Receipts";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<Receipts> typeRef = new TypeReference<Receipts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified repeating invoice
    * <p><b>200</b> - Success - return response of type Repeating Invoices array with a specified Repeating Invoice
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @return RepeatingInvoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public RepeatingInvoices getRepeatingInvoice(UUID repeatingInvoiceID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/RepeatingInvoices/{RepeatingInvoiceID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<RepeatingInvoices> typeRef = new TypeReference<RepeatingInvoices>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve specified attachment on repeating invoices by file name
    * <p><b>200</b> - Success - return response of attachment for Repeating Invoice as binary data
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @param fileName The name of the file being attached to a Repeating Invoice
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getRepeatingInvoiceAttachmentByFileName(UUID repeatingInvoiceID, String fileName, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified Attachments on repeating invoices
    * <p><b>200</b> - Success - return response of attachment for Repeating Invoice as binary data
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @param attachmentID Unique identifier for a Attachment
    * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getRepeatingInvoiceAttachmentById(UUID repeatingInvoiceID, UUID attachmentID, String contentType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{AttachmentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{AttachmentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID.toString());
            uriVariables.put("AttachmentID", attachmentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

                        ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on repeating invoice
    * <p><b>200</b> - Success - return response of type Attachments array with all Attachments for a specified Repeating Invoice
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getRepeatingInvoiceAttachments(UUID repeatingInvoiceID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve history for a repeating invoice
    * <p><b>200</b> - Success - return response of type HistoryRecords array of all HistoryRecord for Repeating Invoice
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getRepeatingInvoiceHistory(UUID repeatingInvoiceID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/History";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/RepeatingInvoices/{RepeatingInvoiceID}/History";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<HistoryRecords> typeRef = new TypeReference<HistoryRecords>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve any repeating invoices
    * <p><b>200</b> - Success - return response of type Repeating Invoices array for all Repeating Invoice
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return RepeatingInvoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public RepeatingInvoices getRepeatingInvoices(String where, String order) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/RepeatingInvoices";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<RepeatingInvoices> typeRef = new TypeReference<RepeatingInvoices>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve report for AgedPayablesByContact
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param contactId Unique identifier for a Contact
    * @param date The date of the Aged Payables By Contact report
    * @param fromDate The from date of the Aged Payables By Contact report
    * @param toDate The to date of the Aged Payables By Contact report
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportAgedPayablesByContact(UUID contactId, LocalDate date, LocalDate fromDate, LocalDate toDate) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Reports/AgedPayablesByContact";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (contactId != null) {
                addToMapIfNotNull(params, "contactId", contactId);
            }if (date != null) {
                addToMapIfNotNull(params, "date", date);
            }if (fromDate != null) {
                addToMapIfNotNull(params, "fromDate", fromDate);
            }if (toDate != null) {
                addToMapIfNotNull(params, "toDate", toDate);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve report for AgedReceivablesByContact
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param contactId Unique identifier for a Contact
    * @param date The date of the Aged Receivables By Contact report
    * @param fromDate The from date of the Aged Receivables By Contact report
    * @param toDate The to date of the Aged Receivables By Contact report
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportAgedReceivablesByContact(UUID contactId, String date, String fromDate, String toDate) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Reports/AgedReceivablesByContact";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (contactId != null) {
                addToMapIfNotNull(params, "contactId", contactId);
            }if (date != null) {
                addToMapIfNotNull(params, "date", date);
            }if (fromDate != null) {
                addToMapIfNotNull(params, "fromDate", fromDate);
            }if (toDate != null) {
                addToMapIfNotNull(params, "toDate", toDate);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve report for BAS only valid for AU orgs
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param reportID Unique identifier for a Report
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportBASorGST(String reportID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Reports/{ReportID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Reports/{ReportID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ReportID", reportID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve report for BAS only valid for AU orgs
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportBASorGSTList() throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Reports";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve report for BalanceSheet
    * <p><b>200</b> - Success - return response of type ReportWithRows
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
    public ReportWithRows getReportBalanceSheet(String date, Integer periods, String timeframe, String trackingOptionID1, String trackingOptionID2, Boolean standardLayout, Boolean paymentsOnly) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Reports/BalanceSheet";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (date != null) {
                addToMapIfNotNull(params, "date", date);
            }if (periods != null) {
                addToMapIfNotNull(params, "periods", periods);
            }if (timeframe != null) {
                addToMapIfNotNull(params, "timeframe", timeframe);
            }if (trackingOptionID1 != null) {
                addToMapIfNotNull(params, "trackingOptionID1", trackingOptionID1);
            }if (trackingOptionID2 != null) {
                addToMapIfNotNull(params, "trackingOptionID2", trackingOptionID2);
            }if (standardLayout != null) {
                addToMapIfNotNull(params, "standardLayout", standardLayout);
            }if (paymentsOnly != null) {
                addToMapIfNotNull(params, "paymentsOnly", paymentsOnly);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve report for BankSummary
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param date The date for the Bank Summary report e.g. 2018-03-31
    * @param period The number of periods to compare (integer between 1 and 12)
    * @param timeframe The period size to compare to (1&#x3D;month, 3&#x3D;quarter, 12&#x3D;year)
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportBankSummary(String date, Integer period, Integer timeframe) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Reports/BankSummary";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (date != null) {
                addToMapIfNotNull(params, "date", date);
            }if (period != null) {
                addToMapIfNotNull(params, "period", period);
            }if (timeframe != null) {
                addToMapIfNotNull(params, "timeframe", timeframe);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve report for Budget Summary
    * <p><b>200</b> - success- return a Report with Rows object
    * @param date The date for the Bank Summary report e.g. 2018-03-31
    * @param period The number of periods to compare (integer between 1 and 12)
    * @param timeframe The period size to compare to (1&#x3D;month, 3&#x3D;quarter, 12&#x3D;year)
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportBudgetSummary(String date, Integer period, Integer timeframe) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Reports/BudgetSummary";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (date != null) {
                addToMapIfNotNull(params, "date", date);
            }if (period != null) {
                addToMapIfNotNull(params, "period", period);
            }if (timeframe != null) {
                addToMapIfNotNull(params, "timeframe", timeframe);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve report for ExecutiveSummary
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param date The date for the Bank Summary report e.g. 2018-03-31
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportExecutiveSummary(String date) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Reports/ExecutiveSummary";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (date != null) {
                addToMapIfNotNull(params, "date", date);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve report for ProfitAndLoss
    * <p><b>200</b> - Success - return response of type ReportWithRows
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
    public ReportWithRows getReportProfitAndLoss(String fromDate, String toDate, Integer periods, String timeframe, String trackingCategoryID, String trackingCategoryID2, String trackingOptionID, String trackingOptionID2, Boolean standardLayout, Boolean paymentsOnly) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Reports/ProfitAndLoss";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (fromDate != null) {
                addToMapIfNotNull(params, "fromDate", fromDate);
            }if (toDate != null) {
                addToMapIfNotNull(params, "toDate", toDate);
            }if (periods != null) {
                addToMapIfNotNull(params, "periods", periods);
            }if (timeframe != null) {
                addToMapIfNotNull(params, "timeframe", timeframe);
            }if (trackingCategoryID != null) {
                addToMapIfNotNull(params, "trackingCategoryID", trackingCategoryID);
            }if (trackingCategoryID2 != null) {
                addToMapIfNotNull(params, "trackingCategoryID2", trackingCategoryID2);
            }if (trackingOptionID != null) {
                addToMapIfNotNull(params, "trackingOptionID", trackingOptionID);
            }if (trackingOptionID2 != null) {
                addToMapIfNotNull(params, "trackingOptionID2", trackingOptionID2);
            }if (standardLayout != null) {
                addToMapIfNotNull(params, "standardLayout", standardLayout);
            }if (paymentsOnly != null) {
                addToMapIfNotNull(params, "paymentsOnly", paymentsOnly);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve report for TenNinetyNine
    * <p><b>200</b> - Success - return response of type Reports
    * @param reportYear The year of the 1099 report
    * @return Reports
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Reports getReportTenNinetyNine(String reportYear) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Reports/TenNinetyNine";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (reportYear != null) {
                addToMapIfNotNull(params, "reportYear", reportYear);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Reports> typeRef = new TypeReference<Reports>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve report for TrialBalance
    * <p><b>200</b> - Success - return response of type ReportWithRows
    * @param date The date for the Trial Balance report e.g. 2018-03-31
    * @param paymentsOnly Return cash only basis for the Trial Balance report
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportTrialBalance(String date, Boolean paymentsOnly) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Reports/TrialBalance";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (date != null) {
                addToMapIfNotNull(params, "date", date);
            }if (paymentsOnly != null) {
                addToMapIfNotNull(params, "paymentsOnly", paymentsOnly);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<ReportWithRows> typeRef = new TypeReference<ReportWithRows>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Tax Rates
    * <p><b>200</b> - Success - return response of type TaxRates array with TaxRates
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param taxType Filter by tax type
    * @return TaxRates
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TaxRates getTaxRates(String where, String order, String taxType) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TaxRates";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }if (taxType != null) {
                addToMapIfNotNull(params, "TaxType", taxType);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<TaxRates> typeRef = new TypeReference<TaxRates>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve tracking categories and options
    * <p><b>200</b> - Success - return response of type TrackingCategories array of TrackingCategory
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param includeArchived e.g. includeArchived&#x3D;true - Categories and options with a status of ARCHIVED will be included in the response
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories getTrackingCategories(String where, String order, Boolean includeArchived) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TrackingCategories";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }if (includeArchived != null) {
                addToMapIfNotNull(params, "includeArchived", includeArchived);
            }
            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<TrackingCategories> typeRef = new TypeReference<TrackingCategories>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve tracking categories and options for specified category
    * <p><b>200</b> - Success - return response of type TrackingCategories array of specified TrackingCategory
    * @param trackingCategoryID Unique identifier for a TrackingCategory
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories getTrackingCategory(UUID trackingCategoryID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TrackingCategories/{TrackingCategoryID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/TrackingCategories/{TrackingCategoryID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("TrackingCategoryID", trackingCategoryID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<TrackingCategories> typeRef = new TypeReference<TrackingCategories>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified user
    * <p><b>200</b> - Success - return response of type Users array of specified User
    * @param userID Unique identifier for a User
    * @return Users
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Users getUser(UUID userID) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Users/{UserID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Users/{UserID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("UserID", userID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            String response = this.DATA(url, strBody, params, "GET");

            TypeReference<Users> typeRef = new TypeReference<Users>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve users
    * <p><b>200</b> - Success - return response of type Users array of all User
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return List&lt;Users&gt;
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public List<Users> getUsers(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Users";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();
            params = new HashMap<>();
            if (where != null) {
                addToMapIfNotNull(params, "where", where);
            }if (order != null) {
                addToMapIfNotNull(params, "order", order);
            }
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);

            TypeReference<List<Users>> typeRef = new TypeReference<List<Users>>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update a chart of accounts
    * <p><b>200</b> - Success - update existing Account and return response of type Accounts array with updated Account
    * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
    * @param accountID Unique identifier for retrieving single object
    * @param accounts Request of type Accounts array with one Account
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts updateAccount(UUID accountID, Accounts accounts) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Accounts/{AccountID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Accounts/{AccountID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("AccountID", accountID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(accounts);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<Accounts> typeRef = new TypeReference<Accounts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update Attachment on Account by Filename
    * <p><b>200</b> - Success - return response of type Attachments array of Attachment
    * <p><b>400</b> - Validation Error - some data was incorrect returns response of type Error
    * @param accountID Unique identifier for Account object
    * @param fileName Name of the attachment
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateAccountAttachmentByFileName(UUID accountID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Accounts/{AccountID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Accounts/{AccountID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("AccountID", accountID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "POST", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update a single spend or receive money transaction
    * <p><b>200</b> - Success - return response of type BankTransactions array with updated BankTransaction
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @param bankTransactions The bankTransactions parameter
    * @return BankTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransactions updateBankTransaction(UUID bankTransactionID, BankTransactions bankTransactions) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransactions/{BankTransactionID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransactions/{BankTransactionID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransactionID", bankTransactionID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(bankTransactions);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<BankTransactions> typeRef = new TypeReference<BankTransactions>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update an Attachment on BankTransaction by Filename
    * <p><b>200</b> - Success - return response of Attachments array of Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransactionID Xero generated unique identifier for a bank transaction
    * @param fileName The name of the file being attached
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateBankTransactionAttachmentByFileName(UUID bankTransactionID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransactionID", bankTransactionID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "POST", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - Success - return response of Attachments array of 0 to N Attachment for a Bank Transfer
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransferID Xero generated unique identifier for a bank transfer
    * @param fileName The name of the file being attached to a Bank Transfer
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateBankTransferAttachmentByFileName(UUID bankTransferID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransfers/{BankTransferID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/BankTransfers/{BankTransferID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("BankTransferID", bankTransferID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "POST", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - Success - return response of type Contacts array with an updated Contact
    * <p><b>400</b> - A failed request due to validation error
    * @param contactID Unique identifier for a Contact
    * @param contacts The contacts parameter
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts updateContact(UUID contactID, Contacts contacts) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts/{ContactID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Contacts/{ContactID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactID", contactID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(contacts);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<Contacts> typeRef = new TypeReference<Contacts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - Success - return response of type Attachments array with an updated Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param contactID Unique identifier for a Contact
    * @param fileName Name for the file you are attaching
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateContactAttachmentByFileName(UUID contactID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts/{ContactID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Contacts/{ContactID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactID", contactID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "POST", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update a Contract Group
    * <p><b>200</b> - Success - return response of type Contact Groups array of updated Contact Group
    * <p><b>400</b> - A failed request due to validation error
    * @param contactGroupID Unique identifier for a Contact Group
    * @param contactGroups The contactGroups parameter
    * @return ContactGroups
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ContactGroups updateContactGroup(UUID contactGroupID, ContactGroups contactGroups) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ContactGroups/{ContactGroupID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ContactGroups/{ContactGroupID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ContactGroupID", contactGroupID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(contactGroups);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<ContactGroups> typeRef = new TypeReference<ContactGroups>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update a specific credit note
    * <p><b>200</b> - Success - return response of type Credit Notes array with updated CreditNote
    * <p><b>400</b> - A failed request due to validation error
    * @param creditNoteID Unique identifier for a Credit Note
    * @param creditNotes The creditNotes parameter
    * @return CreditNotes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CreditNotes updateCreditNote(UUID creditNoteID, CreditNotes creditNotes) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes/{CreditNoteID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/CreditNotes/{CreditNoteID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("CreditNoteID", creditNoteID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(creditNotes);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<CreditNotes> typeRef = new TypeReference<CreditNotes>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update Attachments on CreditNote by file name
    * <p><b>200</b> - Success - return response of type Attachments array with updated Attachment for specific Credit Note
    * <p><b>400</b> - A failed request due to validation error
    * @param creditNoteID Unique identifier for a Credit Note
    * @param fileName Name of the file you are attaching to Credit Note
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateCreditNoteAttachmentByFileName(UUID creditNoteID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("CreditNoteID", creditNoteID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "POST", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update a specific employee used in Xero payrun
    * <p><b>200</b> - Success - return response of type Employees array with updated Employee
    * <p><b>400</b> - A failed request due to validation error
    * @param employeeID Unique identifier for a Employee
    * @param employees The employees parameter
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees updateEmployee(UUID employeeID, Employees employees) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Employees/{EmployeeID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Employees/{EmployeeID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("EmployeeID", employeeID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(employees);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update specified expense claims
    * <p><b>200</b> - Success - return response of type ExpenseClaims array with updated ExpenseClaim
    * <p><b>400</b> - A failed request due to validation error
    * @param expenseClaimID Unique identifier for a ExpenseClaim
    * @param expenseClaims The expenseClaims parameter
    * @return ExpenseClaims
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ExpenseClaims updateExpenseClaim(UUID expenseClaimID, ExpenseClaims expenseClaims) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ExpenseClaims/{ExpenseClaimID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ExpenseClaims/{ExpenseClaimID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ExpenseClaimID", expenseClaimID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(expenseClaims);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<ExpenseClaims> typeRef = new TypeReference<ExpenseClaims>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update a specified sales invoices or purchase bills
    * <p><b>200</b> - Success - return response of type Invoices array with updated Invoice
    * <p><b>400</b> - A failed request due to validation error
    * @param invoiceID Unique identifier for an Invoice
    * @param invoices The invoices parameter
    * @return Invoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Invoices updateInvoice(UUID invoiceID, Invoices invoices) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices/{InvoiceID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Invoices/{InvoiceID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("InvoiceID", invoiceID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(invoices);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<Invoices> typeRef = new TypeReference<Invoices>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update Attachment on invoices or purchase bills by it&#39;s filename
    * <p><b>200</b> - Success - return response of type Attachments array with updated Attachment
    * <p><b>400</b> - A failed request due to validation error
    * @param invoiceID Unique identifier for an Invoice
    * @param fileName Name of the file you are attaching
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateInvoiceAttachmentByFileName(UUID invoiceID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Invoices/{InvoiceID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Invoices/{InvoiceID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("InvoiceID", invoiceID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "POST", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to udpate a specified item
    * <p><b>200</b> - Success - return response of type Items array with updated Item
    * <p><b>400</b> - A failed request due to validation error
    * @param itemID Unique identifier for an Item
    * @param items The items parameter
    * @return Items
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Items updateItem(UUID itemID, Items items) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Items/{ItemID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Items/{ItemID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ItemID", itemID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(items);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<Items> typeRef = new TypeReference<Items>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update a specified linked transactions (billable expenses)
    * <p><b>200</b> - Success - return response of type LinkedTransactions array with updated LinkedTransaction
    * <p><b>400</b> - A failed request due to validation error
    * @param linkedTransactionID Unique identifier for a LinkedTransaction
    * @param linkedTransactions The linkedTransactions parameter
    * @return LinkedTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LinkedTransactions updateLinkedTransaction(UUID linkedTransactionID, LinkedTransactions linkedTransactions) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/LinkedTransactions/{LinkedTransactionID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/LinkedTransactions/{LinkedTransactionID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("LinkedTransactionID", linkedTransactionID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(linkedTransactions);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<LinkedTransactions> typeRef = new TypeReference<LinkedTransactions>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update a specified manual journal
    * <p><b>200</b> - Success - return response of type ManualJournals array with an updated ManualJournal
    * <p><b>400</b> - A failed request due to validation error
    * @param manualJournalID Unique identifier for a ManualJournal
    * @param manualJournals The manualJournals parameter
    * @return ManualJournals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ManualJournals updateManualJournal(UUID manualJournalID, ManualJournals manualJournals) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ManualJournals/{ManualJournalID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ManualJournals/{ManualJournalID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ManualJournalID", manualJournalID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(manualJournals);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<ManualJournals> typeRef = new TypeReference<ManualJournals>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update a specified Attachment on ManualJournal by file name
    * <p><b>200</b> - Success - return response of type Attachments array with an update Attachment for a ManualJournals
    * <p><b>400</b> - A failed request due to validation error
    * @param manualJournalID Unique identifier for a ManualJournal
    * @param fileName The name of the file being attached to a ManualJournal
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateManualJournalAttachmentByFileName(UUID manualJournalID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ManualJournalID", manualJournalID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "POST", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update a specified payment for invoices and credit notes
    * <p><b>200</b> - Success - return response of type Payments array for updated Payment
    * <p><b>400</b> - A failed request due to validation error
    * @param paymentID Unique identifier for a Payment
    * @param payments The payments parameter
    * @return Payments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payments updatePayment(UUID paymentID, Payments payments) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Payments/{PaymentID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Payments/{PaymentID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("PaymentID", paymentID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(payments);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<Payments> typeRef = new TypeReference<Payments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update a specified purchase order
    * <p><b>200</b> - Success - return response of type PurchaseOrder array for updated PurchaseOrder
    * <p><b>400</b> - A failed request due to validation error
    * @param purchaseOrderID Unique identifier for a PurchaseOrder
    * @param purchaseOrders The purchaseOrders parameter
    * @return PurchaseOrders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PurchaseOrders updatePurchaseOrder(UUID purchaseOrderID, PurchaseOrders purchaseOrders) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/PurchaseOrders/{PurchaseOrderID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/PurchaseOrders/{PurchaseOrderID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("PurchaseOrderID", purchaseOrderID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(purchaseOrders);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<PurchaseOrders> typeRef = new TypeReference<PurchaseOrders>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve a specified draft expense claim receipts
    * <p><b>200</b> - Success - return response of type Receipts array for updated Receipt
    * <p><b>400</b> - A failed request due to validation error
    * @param receiptID Unique identifier for a Receipt
    * @param receipts The receipts parameter
    * @return Receipts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Receipts updateReceipt(UUID receiptID, Receipts receipts) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Receipts/{ReceiptID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Receipts/{ReceiptID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ReceiptID", receiptID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(receipts);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<Receipts> typeRef = new TypeReference<Receipts>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update Attachment on expense claim receipts by file name
    * <p><b>200</b> - Success - return response of type Attachments array with updated Attachment for a specified Receipt
    * <p><b>400</b> - A failed request due to validation error
    * @param receiptID Unique identifier for a Receipt
    * @param fileName The name of the file being attached to the Receipt
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateReceiptAttachmentByFileName(UUID receiptID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Receipts/{ReceiptID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/Receipts/{ReceiptID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("ReceiptID", receiptID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "POST", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update specified attachment on repeating invoices by file name
    * <p><b>200</b> - Success - return response of type Attachments array with specified Attachment for a specified Repeating Invoice
    * <p><b>400</b> - A failed request due to validation error
    * @param repeatingInvoiceID Unique identifier for a Repeating Invoice
    * @param fileName The name of the file being attached to a Repeating Invoice
    * @param body Byte array of file in body of request
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateRepeatingInvoiceAttachmentByFileName(UUID repeatingInvoiceID, String fileName, byte[] body) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("RepeatingInvoiceID", repeatingInvoiceID.toString());
            uriVariables.put("FileName", fileName.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            

            String response = this.FILE(url, strBody, params, "POST", body);

            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update Tax Rates
    * <p><b>200</b> - Success - return response of type TaxRates array updated TaxRate
    * <p><b>400</b> - A failed request due to validation error
    * @param taxRates The taxRates parameter
    * @return TaxRates
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TaxRates updateTaxRate(TaxRates taxRates) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TaxRates";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(taxRates);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<TaxRates> typeRef = new TypeReference<TaxRates>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to update tracking categories
    * <p><b>200</b> - Success - return response of type TrackingCategories array of updated TrackingCategory
    * <p><b>400</b> - A failed request due to validation error
    * @param trackingCategoryID Unique identifier for a TrackingCategory
    * @param trackingCategory The trackingCategory parameter
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories updateTrackingCategory(UUID trackingCategoryID, TrackingCategory trackingCategory) throws IOException {
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TrackingCategories/{TrackingCategoryID}";
            // Hacky path manipulation to support different return types from same endpoint
            String path = "/TrackingCategories/{TrackingCategoryID}";
            String type = "/pdf";
            if(path.toLowerCase().contains(type.toLowerCase()))
            {
                correctPath = path.replace("/pdf","");
            } 

            // create a map of path variables
            final Map<String, String> uriVariables = new HashMap<String, String>();
            uriVariables.put("TrackingCategoryID", trackingCategoryID.toString());
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.buildFromMap(uriVariables).toString();

            
            strBody = apiClient.getObjectMapper().writeValueAsString(trackingCategory);

            String response = this.DATA(url, strBody, params, "POST");

            TypeReference<TrackingCategories> typeRef = new TypeReference<TrackingCategories>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);           

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }

    protected void addToMapIfNotNull(Map<String, String> map, String key, Object value) {
        if (value != null) {
            map.put(key, value.toString());
        }
    }

}

