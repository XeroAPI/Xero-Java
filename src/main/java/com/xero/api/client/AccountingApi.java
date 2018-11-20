package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.accounting.Account;
import com.xero.models.accounting.Accounts;
import com.xero.models.accounting.Allocation;
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
import com.xero.models.accounting.ManualJournals;
import org.threeten.bp.OffsetDateTime;
import com.xero.models.accounting.OnlineInvoices;
import com.xero.models.accounting.Organisations;
import com.xero.models.accounting.Overpayments;
import com.xero.models.accounting.PaymentServices;
import com.xero.models.accounting.Payments;
import com.xero.models.accounting.Prepayments;
import com.xero.models.accounting.PurchaseOrders;
import com.xero.models.accounting.Receipts;
import com.xero.models.accounting.RepeatingInvoices;
import com.xero.models.accounting.ReportWithRows;
import com.xero.models.accounting.Reports;
import com.xero.models.accounting.RequestEmpty;
import com.xero.models.accounting.Response204;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.threeten.bp.OffsetDateTime;

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
        this.apiClient = apiClient;
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
        
        OAuthRequestResource req = new OAuthRequestResource(
            config, 
            signerFactory, 
            url, 
            method,
            "application/octet-stream",
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param account The account parameter
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts createAccount(Account account) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Accounts";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param accountID The accountID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createAccountAttachmentByFileName(UUID accountID, String fileName, byte[] body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransactions The bankTransactions parameter
    * @param summarizeErrors response format that shows validation errors for each bank transaction
    * @return BankTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransactions createBankTransaction(BankTransactions bankTransactions, Boolean summarizeErrors) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransactionID The bankTransactionID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createBankTransactionAttachmentByFileName(UUID bankTransactionID, String fileName, byte[] body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransactionID The bankTransactionID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createBankTransactionHistoryRecord(UUID bankTransactionID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransfers The bankTransfers parameter
    * @return BankTransfers
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransfers createBankTransfer(BankTransfers bankTransfers) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BankTransfers";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransferID The bankTransferID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createBankTransferAttachmentByFileName(UUID bankTransferID, String fileName, byte[] body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransferID The bankTransferID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createBankTransferHistoryRecord(UUID bankTransferID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param batchPayments The batchPayments parameter
    * @return BatchPayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BatchPayments createBatchPayment(BatchPayments batchPayments) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BatchPayments";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param batchPaymentID The batchPaymentID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createBatchPaymentHistoryRecord(UUID batchPaymentID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param brandingThemeID The brandingThemeID parameter
    * @param paymentServices The paymentServices parameter
    * @return PaymentServices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PaymentServices createBrandingThemePaymentServices(UUID brandingThemeID, PaymentServices paymentServices) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(paymentServices);
                        
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param contact The contact parameter
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts createContact(Contact contact) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Contacts";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param contactID The contactID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createContactAttachmentByFileName(UUID contactID, String fileName, byte[] body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param contactGroups The contactGroups parameter
    * @return ContactGroups
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ContactGroups createContactGroup(ContactGroups contactGroups) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ContactGroups";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param contactGroupID The contactGroupID parameter
    * @param contacts The contacts parameter
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts createContactGroupContacts(UUID contactGroupID, Contacts contacts) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param contactID The contactID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createContactHistory(UUID contactID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param summarizeErrors shows validation errors for each credit note
    * @param creditNotes The creditNotes parameter
    * @return CreditNotes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CreditNotes createCreditNote(Boolean summarizeErrors, CreditNotes creditNotes) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param creditNoteID The creditNoteID parameter
    * @param allocations The allocations parameter
    * @return Allocations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Allocations createCreditNoteAllocation(UUID creditNoteID, Allocations allocations) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param creditNoteID The creditNoteID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createCreditNoteAttachmentByFileName(UUID creditNoteID, String fileName, byte[] body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param creditNoteID The creditNoteID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createCreditNoteHistory(UUID creditNoteID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param currencies The currencies parameter
    * @return Currencies
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Currencies createCurrency(Currencies currencies) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Currencies";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param employees The employees parameter
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees createEmployee(Employees employees) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Employees";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param expenseClaims The expenseClaims parameter
    * @param summarizeErrors shows validation errors for each expense claim
    * @return ExpenseClaims
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ExpenseClaims createExpenseClaim(ExpenseClaims expenseClaims, Boolean summarizeErrors) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();

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
    * Allows you to retrieve a history records of an ExpenseClaim
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param expenseClaimID The expenseClaimID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createExpenseClaimHistory(UUID expenseClaimID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param invoices The invoices parameter
    * @param summarizeErrors shows validation errors for each invoice
    * @return Invoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Invoices createInvoice(Invoices invoices, Boolean summarizeErrors) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param invoiceID The invoiceID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createInvoiceAttachmentByFileName(UUID invoiceID, String fileName, byte[] body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param invoiceID The invoiceID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createInvoiceHistory(UUID invoiceID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param items The items parameter
    * @return Items
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Items createItem(Items items) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Items";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param itemID The itemID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createItemHistory(UUID itemID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param linkedTransactions The linkedTransactions parameter
    * @return LinkedTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LinkedTransactions createLinkedTransaction(LinkedTransactions linkedTransactions) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/LinkedTransactions";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param manualJournals The manualJournals parameter
    * @return ManualJournals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ManualJournals createManualJournal(ManualJournals manualJournals) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/ManualJournals";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param manualJournalID The manualJournalID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createManualJournalAttachmentByFileName(UUID manualJournalID, String fileName, byte[] body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param overpaymentID The overpaymentID parameter
    * @param allocations The allocations parameter
    * @return Allocations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Allocations createOverpaymentAllocation(UUID overpaymentID, Allocations allocations) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * Allows you to retrieve a history records of an Overpayment
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param overpaymentID The overpaymentID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createOverpaymentHistory(UUID overpaymentID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param payments The payments parameter
    * @return Payments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payments createPayment(Payments payments) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Payments";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * Allows you to retrieve a history records of an invoice
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param paymentID The paymentID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createPaymentHistory(UUID paymentID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param paymentServices The paymentServices parameter
    * @return PaymentServices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PaymentServices createPaymentService(PaymentServices paymentServices) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/PaymentServices";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param prepayments The prepayments parameter
    * @return Prepayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Prepayments createPrepayment(Prepayments prepayments) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Prepayments";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param prepaymentID The prepaymentID parameter
    * @param allocation The allocation parameter
    * @return Allocation
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Allocation createPrepaymentAllocation(UUID prepaymentID, Allocation allocation) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(allocation);
                        
            String response = this.DATA(url, strBody, params, "PUT");
                        
            TypeReference<Allocation> typeRef = new TypeReference<Allocation>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param purchaseOrders The purchaseOrders parameter
    * @param summarizeErrors shows validation errors for each purchase order.
    * @return PurchaseOrders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PurchaseOrders createPurchaseOrder(PurchaseOrders purchaseOrders, Boolean summarizeErrors) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param purchaseOrderID The purchaseOrderID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createPurchaseOrderHistory(UUID purchaseOrderID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param receipts The receipts parameter
    * @return Receipts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Receipts createReceipt(Receipts receipts) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Receipts";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param receiptID The receiptID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createReceiptAttachmentByFileName(UUID receiptID, String fileName, byte[] body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param receiptID The receiptID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createReceiptHistory(UUID receiptID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param repeatingInvoiceID The repeatingInvoiceID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments createRepeatingInvoiceAttachmentByFileName(UUID repeatingInvoiceID, String fileName, byte[] body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param repeatingInvoiceID The repeatingInvoiceID parameter
    * @param historyRecords The historyRecords parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords createRepeatingInvoiceHistory(UUID repeatingInvoiceID, HistoryRecords historyRecords) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param taxRates The taxRates parameter
    * @return TaxRates
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TaxRates createTaxRate(TaxRates taxRates) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TaxRates";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param trackingCategory The trackingCategory parameter
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories createTrackingCategory(TrackingCategory trackingCategory) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TrackingCategories";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param trackingCategoryID The trackingCategoryID parameter
    * @param trackingOption The trackingOption parameter
    * @return TrackingOptions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingOptions createTrackingOptions(UUID trackingCategoryID, TrackingOption trackingOption) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param accountID The accountID parameter
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts deleteAccount(UUID accountID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param contactGroupID The contactGroupID parameter
    * @param contactID The contactID parameter
    * @return Response204
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Response204 deleteContactGroupContact(UUID contactGroupID, UUID contactID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "DELETE");
            
            TypeReference<Response204> typeRef = new TypeReference<Response204>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * @param contactGroupID The contactGroupID parameter
    * @return Response204
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Response204 deleteContactGroupContacts(UUID contactGroupID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "DELETE");
            
            TypeReference<Response204> typeRef = new TypeReference<Response204>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param itemID The itemID parameter
    * @return Response204
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Response204 deleteItem(UUID itemID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "DELETE");
            
            TypeReference<Response204> typeRef = new TypeReference<Response204>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>204</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param linkedTransactionID The linkedTransactionID parameter
    * @return Response204
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Response204 deleteLinkedTransaction(UUID linkedTransactionID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "DELETE");
            
            TypeReference<Response204> typeRef = new TypeReference<Response204>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param trackingCategoryID The trackingCategoryID parameter
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories deleteTrackingCategory(UUID trackingCategoryID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param trackingCategoryID The trackingCategoryID parameter
    * @param trackingOptionID The trackingOptionID parameter
    * @return TrackingOptions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingOptions deleteTrackingOptions(UUID trackingCategoryID, UUID trackingOptionID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>204</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param invoiceID The invoiceID parameter
    * @param requestEmpty The requestEmpty parameter
    * @return Response204
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Response204 emailInvoice(UUID invoiceID, RequestEmpty requestEmpty) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(requestEmpty);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Response204> typeRef = new TypeReference<Response204>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve the full chart of accounts
    * <p><b>200</b> - A successful request
    * @param accountID The accountID parameter
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts getAccount(UUID accountID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve Attachments on Account by Filename
    * <p><b>200</b> - A successful request
    * @param accountID The accountID parameter
    * @param fileName The fileName parameter
    * @param contentType The contentType parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getAccountAttachmentByFileName(UUID accountID, String fileName, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET", contentType);
            
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on Account
    * <p><b>200</b> - A successful request
    * @param accountID The accountID parameter
    * @param attachmentID The attachmentID parameter
    * @param contentType The contentType parameter
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getAccountAttachmentById(UUID accountID, UUID attachmentID, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
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
    * <p><b>200</b> - A successful request
    * @param accountID The accountID parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getAccountAttachments(UUID accountID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts getAccounts(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve any spend or receive money transactions
    * <p><b>200</b> - A successful request
    * @param bankTransactionID The bankTransactionID parameter
    * @return BankTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransactions getBankTransaction(UUID bankTransactionID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param bankTransactionID The bankTransactionID parameter
    * @param fileName The fileName parameter
    * @param contentType The contentType parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getBankTransactionAttachmentByFileName(UUID bankTransactionID, String fileName, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET", contentType);
            
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on BankTransaction
    * <p><b>200</b> - A successful request
    * @param bankTransactionID The bankTransactionID parameter
    * @param attachmentID The attachmentID parameter
    * @param contentType The contentType parameter
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getBankTransactionAttachmentById(UUID bankTransactionID, UUID attachmentID, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
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
    * <p><b>200</b> - A successful request
    * @param bankTransactionID The bankTransactionID parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getBankTransactionAttachments(UUID bankTransactionID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 bank transactions will be returned in a single API call with line items shown for each bank transaction
    * @return BankTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransactions getBankTransactions(OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve history from a bank transfers
    * <p><b>200</b> - A successful request
    * @param bankTransactionID The bankTransactionID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getBankTransactionsHistory(UUID bankTransactionID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param bankTransferID The bankTransferID parameter
    * @return BankTransfers
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransfers getBankTransfer(UUID bankTransferID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param bankTransferID The bankTransferID parameter
    * @param fileName The fileName parameter
    * @param contentType The contentType parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getBankTransferAttachmentByFileName(UUID bankTransferID, String fileName, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET", contentType);
            
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on BankTransfer
    * <p><b>200</b> - A successful request
    * @param bankTransferID The bankTransferID parameter
    * @param attachmentID The attachmentID parameter
    * @param contentType The contentType parameter
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getBankTransferAttachmentById(UUID bankTransferID, UUID attachmentID, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
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
    * <p><b>200</b> - A successful request
    * @param bankTransferID The bankTransferID parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getBankTransferAttachments(UUID bankTransferID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param bankTransferID The bankTransferID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getBankTransferHistory(UUID bankTransferID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return BankTransfers
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransfers getBankTransfers(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param batchPaymentID The batchPaymentID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getBatchPaymentHistory(UUID batchPaymentID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return BatchPayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BatchPayments getBatchPayments(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * GET a BrandingTheme
    * <p><b>200</b> - A successful request
    * @param brandingThemeID The brandingThemeID parameter
    * @return BrandingThemes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BrandingThemes getBrandingTheme(UUID brandingThemeID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * GET a BrandingTheme Payment services
    * <p><b>200</b> - A successful request
    * @param brandingThemeID The brandingThemeID parameter
    * @return PaymentServices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PaymentServices getBrandingThemePaymentServices(UUID brandingThemeID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * GET a BrandingTheme
    * <p><b>200</b> - A successful response
    * @return BrandingThemes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BrandingThemes getBrandingThemes() throws IOException {
        // Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/BrandingThemes";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param contactID The contactID parameter
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts getContact(UUID contactID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param contactID The contactID parameter
    * @param fileName The fileName parameter
    * @param contentType The contentType parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getContactAttachmentByFileName(UUID contactID, String fileName, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET", contentType);
            
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on Contacts
    * <p><b>200</b> - A successful request
    * @param contactID The contactID parameter
    * @param attachmentID The attachmentID parameter
    * @param contentType The contentType parameter
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getContactAttachmentById(UUID contactID, UUID attachmentID, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
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
    * <p><b>200</b> - A successful request
    * @param contactID The contactID parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getContactAttachments(UUID contactID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param contactID The contactID parameter
    * @return CISSettings
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CISSettings getContactCISSettings(UUID contactID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve the ContactID and Name of all the contacts in a contact group
    * <p><b>200</b> - A successful request
    * @param contactGroupID The contactGroupID parameter
    * @return ContactGroups
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ContactGroups getContactGroup(UUID contactGroupID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return ContactGroups
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ContactGroups getContactGroups(String where, String order) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param contactID The contactID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getContactHistory(UUID contactID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
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
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve any credit notes
    * <p><b>200</b> - A successful request
    * @param creditNoteID The creditNoteID parameter
    * @return CreditNotes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CreditNotes getCreditNote(UUID creditNoteID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param creditNoteID The creditNoteID parameter
    * @param contentType The contentType parameter
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getCreditNoteAsPdf(UUID creditNoteID, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
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
    * <p><b>200</b> - A successful request
    * @param creditNoteID The creditNoteID parameter
    * @param fileName The fileName parameter
    * @param contentType The contentType parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getCreditNoteAttachmentByFileName(UUID creditNoteID, String fileName, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET", contentType);
            
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on CreditNote
    * <p><b>200</b> - A successful request
    * @param creditNoteID The creditNoteID parameter
    * @param attachmentID The attachmentID parameter
    * @param contentType The contentType parameter
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getCreditNoteAttachmentById(UUID creditNoteID, UUID attachmentID, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
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
    * <p><b>200</b> - A successful request
    * @param creditNoteID The creditNoteID parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getCreditNoteAttachments(UUID creditNoteID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param creditNoteID The creditNoteID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getCreditNoteHistory(UUID creditNoteID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 credit notes will be returned in a single API call with line items shown for each credit note
    * @return CreditNotes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CreditNotes getCreditNotes(OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Currencies
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Currencies getCurrencies(String where, String order) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve employees to see their status
    * <p><b>200</b> - A successful request
    * @param employeeID The employeeID parameter
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees getEmployee(UUID employeeID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve, add and update employees used in Xero payrun functionality
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees getEmployees(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve expense claims to see their status
    * <p><b>200</b> - A successful request
    * @param expenseClaimID The expenseClaimID parameter
    * @return ExpenseClaims
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ExpenseClaims getExpenseClaim(UUID expenseClaimID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param expenseClaimID The expenseClaimID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getExpenseClaimHistory(UUID expenseClaimID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve expense claims to see their status
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return ExpenseClaims
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ExpenseClaims getExpenseClaims(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve any sales invoices or purchase bills
    * <p><b>200</b> - A successful request
    * @param invoiceID The invoiceID parameter
    * @return Invoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Invoices getInvoice(UUID invoiceID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param invoiceID The invoiceID parameter
    * @param contentType The contentType parameter
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getInvoiceAsPdf(UUID invoiceID, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
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
    * <p><b>200</b> - A successful request
    * @param invoiceID The invoiceID parameter
    * @param fileName The fileName parameter
    * @param contentType The contentType parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getInvoiceAttachmentByFileName(UUID invoiceID, String fileName, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET", contentType);
            
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on invoices or purchase bills
    * <p><b>200</b> - A successful request
    * @param invoiceID The invoiceID parameter
    * @param attachmentID The attachmentID parameter
    * @param contentType The contentType parameter
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getInvoiceAttachmentById(UUID invoiceID, UUID attachmentID, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
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
    * <p><b>200</b> - A successful request
    * @param invoiceID The invoiceID parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getInvoiceAttachments(UUID invoiceID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param invoiceID The invoiceID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getInvoiceHistory(UUID invoiceID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @return InvoiceReminders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public InvoiceReminders getInvoiceReminders() throws IOException {
        // Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/InvoiceReminders/Settings";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
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
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve any items
    * <p><b>200</b> - A successful request
    * @param itemID The itemID parameter
    * @return Items
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Items getItem(UUID itemID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param itemID The itemID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getItemHistory(UUID itemID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Items
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Items getItems(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve any journals.
    * <p><b>200</b> - A successful request
    * @param journalID The journalID parameter
    * @return Journals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Journals getJournal(UUID journalID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param offset The offset parameter
    * @param paymentsOnly The paymentsOnly parameter
    * @return Journals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Journals getJournals(OffsetDateTime ifModifiedSince, Integer offset, Boolean paymentsOnly) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Retrieve linked transactions (billable expenses)
    * <p><b>200</b> - A successful request
    * @param linkedTransactionID The linkedTransactionID parameter
    * @return LinkedTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LinkedTransactions getLinkedTransaction(UUID linkedTransactionID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
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
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve any manual journals
    * <p><b>200</b> - A successful request
    * @param manualJournalID The manualJournalID parameter
    * @return ManualJournals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ManualJournals getManualJournal(UUID manualJournalID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve Attachments on ManualJournal on file name
    * <p><b>200</b> - A successful request
    * @param manualJournalID The manualJournalID parameter
    * @param fileName The fileName parameter
    * @param contentType The contentType parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getManualJournalAttachmentByFileName(UUID manualJournalID, String fileName, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET", contentType);
            
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on ManualJournals
    * <p><b>200</b> - A successful request
    * @param manualJournalID The manualJournalID parameter
    * @param attachmentID The attachmentID parameter
    * @param contentType The contentType parameter
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getManualJournalAttachmentById(UUID manualJournalID, UUID attachmentID, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
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
    * <p><b>200</b> - A successful request
    * @param manualJournalID The manualJournalID parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getManualJournalAttachments(UUID manualJournalID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 manual journals will be returned in a single API call with line items shown for each overpayment
    * @return ManualJournals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ManualJournals getManualJournals(OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param invoiceID The invoiceID parameter
    * @return OnlineInvoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public OnlineInvoices getOnlineInvoice(UUID invoiceID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * GET a Organisation
    * <p><b>200</b> - A successful request
    * @param shortCode The shortCode parameter
    * @return Organisations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Organisations getOrganisationByShortCode(UUID shortCode) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * GET a Organisation
    * <p><b>200</b> - A successful response
    * @return Organisations
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Organisations getOrganisations() throws IOException {
        // Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Organisation";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve overpayments
    * <p><b>200</b> - A successful request
    * @param overpaymentID The overpaymentID parameter
    * @return Overpayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Overpayments getOverpayment(UUID overpaymentID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param overpaymentID The overpaymentID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getOverpaymentHistory(UUID overpaymentID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 overpayments will be returned in a single API call with line items shown for each overpayment
    * @return Overpayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Overpayments getOverpayments(OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Retrieve either one or many payments for invoices and credit notes
    * <p><b>200</b> - A successful request
    * @param paymentID The paymentID parameter
    * @return Payments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payments getPayment(UUID paymentID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve a history records of an invoice
    * <p><b>200</b> - A successful request
    * @param paymentID The paymentID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getPaymentHistory(UUID paymentID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * Retrieve either one or many payment services
    * <p><b>200</b> - A successful response
    * @return PaymentServices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PaymentServices getPaymentServices() throws IOException {
        // Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/PaymentServices";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();
            
            
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
    * Retrieve either one or many payments for invoices and credit notes
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Payments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payments getPayments(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve prepayments
    * <p><b>200</b> - A successful request
    * @param prepaymentID The prepaymentID parameter
    * @return Prepayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Prepayments getPrepayment(UUID prepaymentID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve prepayments
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 prepayments will be returned in a single API call with line items shown for each overpayment
    * @return Prepayments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Prepayments getPrepayments(OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve purchase orders
    * <p><b>200</b> - A successful request
    * @param purchaseOrderID The purchaseOrderID parameter
    * @return PurchaseOrders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PurchaseOrders getPurchaseOrder(UUID purchaseOrderID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param purchaseOrderID The purchaseOrderID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getPurchaseOrderHistory(UUID purchaseOrderID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
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
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve draft expense claim receipts for any user
    * <p><b>200</b> - A successful request
    * @param receiptID The receiptID parameter
    * @return Receipts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Receipts getReceipt(UUID receiptID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve Attachments on invoices or purchase bills
    * <p><b>200</b> - A successful request
    * @param receiptID The receiptID parameter
    * @param fileName The fileName parameter
    * @param contentType The contentType parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getReceiptAttachmentByFileName(UUID receiptID, String fileName, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET", contentType);
            
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on Receipts
    * <p><b>200</b> - A successful request
    * @param receiptID The receiptID parameter
    * @param attachmentID The attachmentID parameter
    * @param contentType The contentType parameter
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getReceiptAttachmentById(UUID receiptID, UUID attachmentID, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
           
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments for expense claim receipts for any user
    * <p><b>200</b> - A successful request
    * @param receiptID The receiptID parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getReceiptAttachments(UUID receiptID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param receiptID The receiptID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getReceiptHistory(UUID receiptID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Receipts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Receipts getReceipts(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve any repeating invoice templates
    * <p><b>200</b> - A successful request
    * @param repeatingInvoiceID The repeatingInvoiceID parameter
    * @return RepeatingInvoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public RepeatingInvoices getRepeatingInvoice(UUID repeatingInvoiceID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve Attachments on invoices or purchase bills
    * <p><b>200</b> - A successful request
    * @param repeatingInvoiceID The repeatingInvoiceID parameter
    * @param fileName The fileName parameter
    * @param contentType The contentType parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getRepeatingInvoiceAttachmentByFileName(UUID repeatingInvoiceID, String fileName, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET", contentType);
            
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on RepeatingInvoices
    * <p><b>200</b> - A successful request
    * @param repeatingInvoiceID The repeatingInvoiceID parameter
    * @param attachmentID The attachmentID parameter
    * @param contentType The contentType parameter
    * @return File
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ByteArrayInputStream getRepeatingInvoiceAttachmentById(UUID repeatingInvoiceID, UUID attachmentID, String contentType) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            ByteArrayInputStream response = this.FILE(url, strBody, params, "GET", contentType);
            return response;
           
            
        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * Allows you to retrieve Attachments on repeating invoice templates
    * <p><b>200</b> - A successful request
    * @param repeatingInvoiceID The repeatingInvoiceID parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments getRepeatingInvoiceAttachments(UUID repeatingInvoiceID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve history for RepeatingInvoice
    * <p><b>200</b> - A successful request
    * @param repeatingInvoiceID The repeatingInvoiceID parameter
    * @return HistoryRecords
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public HistoryRecords getRepeatingInvoiceHistory(UUID repeatingInvoiceID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * Allows you to retrieve any repeating invoice templates
    * <p><b>200</b> - A successful response
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return RepeatingInvoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public RepeatingInvoices getRepeatingInvoices(String where, String order) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param contactId The contactId parameter
    * @param date The date parameter
    * @param fromDate The fromDate parameter
    * @param toDate The toDate parameter
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportAgedPayablesByContact(UUID contactId, String date, String fromDate, String toDate) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param contactId The contactId parameter
    * @param date The date parameter
    * @param fromDate The fromDate parameter
    * @param toDate The toDate parameter
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportAgedReceivablesByContact(UUID contactId, String date, String fromDate, String toDate) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param reportID The reportID parameter
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportBASorGST(String reportID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportBASorGSTList() throws IOException {
        // Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/Reports";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param date The date parameter
    * @param periods The periods parameter
    * @param timeframe The period size to compare to (1&#x3D;month, 3&#x3D;quarter, 12&#x3D;year)
    * @param trackingOptionID1 The trackingOptionID1 parameter
    * @param trackingOptionID2 The trackingOptionID2 parameter
    * @param standardLayout The standardLayout parameter
    * @param paymentsOnly The paymentsOnly parameter
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportBalanceSheet(String date, Integer periods, Integer timeframe, String trackingOptionID1, String trackingOptionID2, Boolean standardLayout, Boolean paymentsOnly) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param date e.g. 2018-03-31
    * @param period The number of periods to compare (integer between 1 and 12)
    * @param timeframe The period size to compare to (1&#x3D;month, 3&#x3D;quarter, 12&#x3D;year)
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportBankSummary(String date, Integer period, Integer timeframe) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param date e.g. 2018-03-31
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportExecutiveSummary(String date) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param fromDate The fromDate parameter
    * @param toDate The toDate parameter
    * @param periods The number of periods to compare (integer between 1 and 12)
    * @param timeframe The period size to compare to (1&#x3D;month, 3&#x3D;quarter, 12&#x3D;year)
    * @param trackingCategoryID The trackingCategoryID parameter
    * @param trackingCategoryID2 The trackingCategoryID2 parameter
    * @param trackingOptionID The trackingOptionID parameter
    * @param trackingOptionID2 The trackingOptionID2 parameter
    * @param standardLayout The standardLayout parameter
    * @param paymentsOnly The paymentsOnly parameter
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportProfitAndLoss(String fromDate, String toDate, Integer periods, String timeframe, String trackingCategoryID, String trackingCategoryID2, String trackingOptionID, String trackingOptionID2, Boolean standardLayout, Boolean paymentsOnly) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param reportYear The reportYear parameter
    * @return Reports
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Reports getReportTenNinetyNine(String reportYear) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * <p><b>200</b> - A successful request
    * @param date The date parameter
    * @param paymentsOnly The paymentsOnly parameter
    * @return ReportWithRows
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ReportWithRows getReportTrialBalance(String date, Boolean paymentsOnly) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * GET a TaxRate
    * <p><b>200</b> - A successful response
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param taxType Filter by tax type
    * @return TaxRates
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TaxRates getTaxRates(String where, String order, String taxType) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Retrieve tracking categories and options for a Xero organisation
    * <p><b>200</b> - A successful response
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param includeArchived e.g. includeArchived&#x3D;true - Categories and options with a status of ARCHIVED will be included in the response
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories getTrackingCategories(String where, String order, Boolean includeArchived) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
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
    * Retrieve tracking categories and options for a Xero organisation
    * <p><b>200</b> - A successful request
    * @param trackingCategoryID The trackingCategoryID parameter
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories getTrackingCategory(UUID trackingCategoryID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * GET a User
    * <p><b>200</b> - A successful request
    * @param userID The userID parameter
    * @return Users
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Users getUser(UUID userID) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();
            
            
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
    * GET a User
    * <p><b>200</b> - A successful response
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @return Users
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Users getUsers(OffsetDateTime ifModifiedSince, String where, String order) throws IOException {
        //, Map<String, String> params
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
            ApiClient apiClient = new ApiClient();
            
            
            String response = this.DATA(url, strBody, params, "GET", ifModifiedSince);
            
            TypeReference<Users> typeRef = new TypeReference<Users>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param accountID The accountID parameter
    * @param accounts The accounts parameter
    * @return Accounts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Accounts updateAccount(UUID accountID, Accounts accounts) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param accountID The accountID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateAccountAttachmentByFileName(UUID accountID, UUID fileName, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param accountID The accountID parameter
    * @param attachmentID The attachmentID parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateAccountAttachmentById(UUID accountID, UUID attachmentID, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransactionID The bankTransactionID parameter
    * @param bankTransactions The bankTransactions parameter
    * @return BankTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public BankTransactions updateBankTransaction(UUID bankTransactionID, BankTransactions bankTransactions) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransactionID The bankTransactionID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateBankTransactionAttachmentByFileName(UUID bankTransactionID, UUID fileName, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransactionID The bankTransactionID parameter
    * @param attachmentID The attachmentID parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateBankTransactionAttachmentById(UUID bankTransactionID, UUID attachmentID, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransferID The bankTransferID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateBankTransferAttachmentByFileName(UUID bankTransferID, UUID fileName, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param bankTransferID The bankTransferID parameter
    * @param attachmentID The attachmentID parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateBankTransferAttachmentById(UUID bankTransferID, UUID attachmentID, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param contactID The contactID parameter
    * @param contacts The contacts parameter
    * @return Contacts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Contacts updateContact(UUID contactID, Contacts contacts) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param contactID The contactID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateContactAttachmentByFileName(UUID contactID, UUID fileName, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param contactID The contactID parameter
    * @param attachmentID The attachmentID parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateContactAttachmentById(UUID contactID, UUID attachmentID, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param contactGroupID The contactGroupID parameter
    * @param contactGroups The contactGroups parameter
    * @return ContactGroups
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ContactGroups updateContactGroup(UUID contactGroupID, ContactGroups contactGroups) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param creditNoteID The creditNoteID parameter
    * @param creditNotes The creditNotes parameter
    * @return CreditNotes
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public CreditNotes updateCreditNote(UUID creditNoteID, CreditNotes creditNotes) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param creditNoteID The creditNoteID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateCreditNoteAttachmentByFileName(UUID creditNoteID, UUID fileName, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param creditNoteID The creditNoteID parameter
    * @param attachmentID The attachmentID parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateCreditNoteAttachmentById(UUID creditNoteID, UUID attachmentID, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param employeeID The employeeID parameter
    * @param employees The employees parameter
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees updateEmployee(UUID employeeID, Employees employees) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param expenseClaimID The expenseClaimID parameter
    * @param expenseClaims The expenseClaims parameter
    * @return ExpenseClaims
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ExpenseClaims updateExpenseClaim(UUID expenseClaimID, ExpenseClaims expenseClaims) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param invoiceID The invoiceID parameter
    * @param invoices The invoices parameter
    * @return Invoices
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Invoices updateInvoice(UUID invoiceID, Invoices invoices) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param invoiceID The invoiceID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateInvoiceAttachmentByFileName(UUID invoiceID, UUID fileName, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param invoiceID The invoiceID parameter
    * @param attachmentID The attachmentID parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateInvoiceAttachmentById(UUID invoiceID, UUID attachmentID, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param itemID The itemID parameter
    * @param items The items parameter
    * @return Items
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Items updateItem(UUID itemID, Items items) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param linkedTransactionID The linkedTransactionID parameter
    * @param linkedTransactions The linkedTransactions parameter
    * @return LinkedTransactions
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LinkedTransactions updateLinkedTransaction(UUID linkedTransactionID, LinkedTransactions linkedTransactions) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param manualJournalID The manualJournalID parameter
    * @param manualJournals The manualJournals parameter
    * @return ManualJournals
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public ManualJournals updateManualJournal(UUID manualJournalID, ManualJournals manualJournals) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param manualJournalID The manualJournalID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateManualJournalAttachmentByFileName(UUID manualJournalID, UUID fileName, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param manualJournalID The manualJournalID parameter
    * @param attachmentID The attachmentID parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateManualJournalAttachmentById(UUID manualJournalID, UUID attachmentID, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param paymentID The paymentID parameter
    * @param payments The payments parameter
    * @return Payments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payments updatePayment(UUID paymentID, Payments payments) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param purchaseOrderID The purchaseOrderID parameter
    * @param purchaseOrders The purchaseOrders parameter
    * @return PurchaseOrders
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PurchaseOrders updatePurchaseOrder(UUID purchaseOrderID, PurchaseOrders purchaseOrders) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param receiptID The receiptID parameter
    * @param receipts The receipts parameter
    * @return Receipts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Receipts updateReceipt(UUID receiptID, Receipts receipts) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param receiptID The receiptID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateReceiptAttachmentByFileName(UUID receiptID, UUID fileName, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param receiptID The receiptID parameter
    * @param attachmentID The attachmentID parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateReceiptAttachmentById(UUID receiptID, UUID attachmentID, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param repeatingInvoiceID The repeatingInvoiceID parameter
    * @param fileName The fileName parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateRepeatingInvoiceAttachmentByFileName(UUID repeatingInvoiceID, UUID fileName, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param repeatingInvoiceID The repeatingInvoiceID parameter
    * @param attachmentID The attachmentID parameter
    * @param body The body parameter
    * @return Attachments
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Attachments updateRepeatingInvoiceAttachmentById(UUID repeatingInvoiceID, UUID attachmentID, File body) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

            strBody = apiClient.getObjectMapper().writeValueAsString(body);
                        
            String response = this.DATA(url, strBody, params, "POST");
                        
            TypeReference<Attachments> typeRef = new TypeReference<Attachments>() {};
            return apiClient.getObjectMapper().readValue(response, typeRef);

        } catch (IOException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage());
        } catch (XeroApiException e) {
            throw xeroExceptionHandler.handleBadRequest(e.getMessage(), e.getResponseCode(),JSONUtils.isJSONValid(e.getMessage()));
        }
    }
  /**
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param taxRates The taxRates parameter
    * @return TaxRates
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TaxRates updateTaxRate(TaxRates taxRates) throws IOException {
        //, Map<String, String> params
        try {
            String strBody = null;
            Map<String, String> params = null;
            String correctPath = "/TaxRates";
            UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
            String url = uriBuilder.build().toString();


            ApiClient apiClient = new ApiClient();

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
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - A failed request due to validation error
    * @param trackingCategoryID The trackingCategoryID parameter
    * @param trackingCategory The trackingCategory parameter
    * @return TrackingCategories
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public TrackingCategories updateTrackingCategory(UUID trackingCategoryID, TrackingCategory trackingCategory) throws IOException {
        //, Map<String, String> params
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


            ApiClient apiClient = new ApiClient();

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

