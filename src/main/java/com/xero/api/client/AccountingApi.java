package com.xero.api.client;

import com.xero.api.ApiException;
import com.xero.api.ApiClient;
import com.xero.api.ApiResponse;
import com.xero.api.Configuration;
import com.xero.api.Pair;

import javax.ws.rs.core.GenericType;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AccountingApi {
  private ApiClient apiClient;

  public AccountingApi() {
    this(Configuration.getDefaultApiClient());
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

  /**
   * Allows you to create a new chart of accounts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param account Request of type Account (required)
   * @return Accounts
   * @throws ApiException if fails to make API call
   */
  public Accounts createAccount(String xeroTenantId, Account account) throws ApiException {
    return createAccountWithHttpInfo(xeroTenantId, account).getData();
      }

  /**
   * Allows you to create a new chart of accounts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param account Request of type Account (required)
   * @return ApiResponse&lt;Accounts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Accounts> createAccountWithHttpInfo(String xeroTenantId, Account account) throws ApiException {
    Object localVarPostBody = account;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createAccount");
    }
    
    // verify the required parameter 'account' is set
    if (account == null) {
      throw new ApiException(400, "Missing the required parameter 'account' when calling createAccount");
    }
    
    // create path and map variables
    String localVarPath = "/Accounts";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Accounts> localVarReturnType = new GenericType<Accounts>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create Attachment on Account
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for Account object (required)
   * @param fileName Name of the attachment (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments createAccountAttachmentByFileName(String xeroTenantId, UUID accountID, String fileName, byte[] body) throws ApiException {
    return createAccountAttachmentByFileNameWithHttpInfo(xeroTenantId, accountID, fileName, body).getData();
      }

  /**
   * Allows you to create Attachment on Account
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for Account object (required)
   * @param fileName Name of the attachment (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> createAccountAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID accountID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createAccountAttachmentByFileName");
    }
    
    // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new ApiException(400, "Missing the required parameter 'accountID' when calling createAccountAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling createAccountAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createAccountAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/Accounts/{AccountID}/Attachments/{FileName}"
      .replaceAll("\\{" + "AccountID" + "\\}", apiClient.escapeString(accountID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create a spend or receive money transaction
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactions  (required)
   * @param summarizeErrors response format that shows validation errors for each bank transaction (optional)
   * @return BankTransactions
   * @throws ApiException if fails to make API call
   */
  public BankTransactions createBankTransaction(String xeroTenantId, BankTransactions bankTransactions, Boolean summarizeErrors) throws ApiException {
    return createBankTransactionWithHttpInfo(xeroTenantId, bankTransactions, summarizeErrors).getData();
      }

  /**
   * Allows you to create a spend or receive money transaction
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactions  (required)
   * @param summarizeErrors response format that shows validation errors for each bank transaction (optional)
   * @return ApiResponse&lt;BankTransactions&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<BankTransactions> createBankTransactionWithHttpInfo(String xeroTenantId, BankTransactions bankTransactions, Boolean summarizeErrors) throws ApiException {
    Object localVarPostBody = bankTransactions;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createBankTransaction");
    }
    
    // verify the required parameter 'bankTransactions' is set
    if (bankTransactions == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransactions' when calling createBankTransaction");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransactions";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "SummarizeErrors", summarizeErrors));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<BankTransactions> localVarReturnType = new GenericType<BankTransactions>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to createa an Attachment on BankTransaction by Filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @param fileName The name of the file being attached (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments createBankTransactionAttachmentByFileName(String xeroTenantId, UUID bankTransactionID, String fileName, byte[] body) throws ApiException {
    return createBankTransactionAttachmentByFileNameWithHttpInfo(xeroTenantId, bankTransactionID, fileName, body).getData();
      }

  /**
   * Allows you to createa an Attachment on BankTransaction by Filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @param fileName The name of the file being attached (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> createBankTransactionAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID bankTransactionID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createBankTransactionAttachmentByFileName");
    }
    
    // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransactionID' when calling createBankTransactionAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling createBankTransactionAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createBankTransactionAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}"
      .replaceAll("\\{" + "BankTransactionID" + "\\}", apiClient.escapeString(bankTransactionID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create history record for a bank transactions
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createBankTransactionHistoryRecord(String xeroTenantId, UUID bankTransactionID, HistoryRecords historyRecords) throws ApiException {
    return createBankTransactionHistoryRecordWithHttpInfo(xeroTenantId, bankTransactionID, historyRecords).getData();
      }

  /**
   * Allows you to create history record for a bank transactions
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createBankTransactionHistoryRecordWithHttpInfo(String xeroTenantId, UUID bankTransactionID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createBankTransactionHistoryRecord");
    }
    
    // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransactionID' when calling createBankTransactionHistoryRecord");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createBankTransactionHistoryRecord");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransactions/{BankTransactionID}/History"
      .replaceAll("\\{" + "BankTransactionID" + "\\}", apiClient.escapeString(bankTransactionID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create a bank transfers
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransfers  (required)
   * @return BankTransfers
   * @throws ApiException if fails to make API call
   */
  public BankTransfers createBankTransfer(String xeroTenantId, BankTransfers bankTransfers) throws ApiException {
    return createBankTransferWithHttpInfo(xeroTenantId, bankTransfers).getData();
      }

  /**
   * Allows you to create a bank transfers
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransfers  (required)
   * @return ApiResponse&lt;BankTransfers&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<BankTransfers> createBankTransferWithHttpInfo(String xeroTenantId, BankTransfers bankTransfers) throws ApiException {
    Object localVarPostBody = bankTransfers;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createBankTransfer");
    }
    
    // verify the required parameter 'bankTransfers' is set
    if (bankTransfers == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransfers' when calling createBankTransfer");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransfers";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<BankTransfers> localVarReturnType = new GenericType<BankTransfers>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @param fileName The name of the file being attached to a Bank Transfer (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments createBankTransferAttachmentByFileName(String xeroTenantId, UUID bankTransferID, String fileName, byte[] body) throws ApiException {
    return createBankTransferAttachmentByFileNameWithHttpInfo(xeroTenantId, bankTransferID, fileName, body).getData();
      }

  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @param fileName The name of the file being attached to a Bank Transfer (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> createBankTransferAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID bankTransferID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createBankTransferAttachmentByFileName");
    }
    
    // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransferID' when calling createBankTransferAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling createBankTransferAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createBankTransferAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransfers/{BankTransferID}/Attachments/{FileName}"
      .replaceAll("\\{" + "BankTransferID" + "\\}", apiClient.escapeString(bankTransferID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createBankTransferHistoryRecord(String xeroTenantId, UUID bankTransferID, HistoryRecords historyRecords) throws ApiException {
    return createBankTransferHistoryRecordWithHttpInfo(xeroTenantId, bankTransferID, historyRecords).getData();
      }

  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createBankTransferHistoryRecordWithHttpInfo(String xeroTenantId, UUID bankTransferID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createBankTransferHistoryRecord");
    }
    
    // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransferID' when calling createBankTransferHistoryRecord");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createBankTransferHistoryRecord");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransfers/{BankTransferID}/History"
      .replaceAll("\\{" + "BankTransferID" + "\\}", apiClient.escapeString(bankTransferID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Create one or many BatchPayments for invoices
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param batchPayments Request of type BatchPayments containing a Payments array with one or more Payment objects (required)
   * @return BatchPayments
   * @throws ApiException if fails to make API call
   */
  public BatchPayments createBatchPayment(String xeroTenantId, BatchPayments batchPayments) throws ApiException {
    return createBatchPaymentWithHttpInfo(xeroTenantId, batchPayments).getData();
      }

  /**
   * Create one or many BatchPayments for invoices
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param batchPayments Request of type BatchPayments containing a Payments array with one or more Payment objects (required)
   * @return ApiResponse&lt;BatchPayments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<BatchPayments> createBatchPaymentWithHttpInfo(String xeroTenantId, BatchPayments batchPayments) throws ApiException {
    Object localVarPostBody = batchPayments;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createBatchPayment");
    }
    
    // verify the required parameter 'batchPayments' is set
    if (batchPayments == null) {
      throw new ApiException(400, "Missing the required parameter 'batchPayments' when calling createBatchPayment");
    }
    
    // create path and map variables
    String localVarPath = "/BatchPayments";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<BatchPayments> localVarReturnType = new GenericType<BatchPayments>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create a history record for a Batch Payment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param batchPaymentID Unique identifier for BatchPayment (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createBatchPaymentHistoryRecord(String xeroTenantId, UUID batchPaymentID, HistoryRecords historyRecords) throws ApiException {
    return createBatchPaymentHistoryRecordWithHttpInfo(xeroTenantId, batchPaymentID, historyRecords).getData();
      }

  /**
   * Allows you to create a history record for a Batch Payment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param batchPaymentID Unique identifier for BatchPayment (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createBatchPaymentHistoryRecordWithHttpInfo(String xeroTenantId, UUID batchPaymentID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createBatchPaymentHistoryRecord");
    }
    
    // verify the required parameter 'batchPaymentID' is set
    if (batchPaymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'batchPaymentID' when calling createBatchPaymentHistoryRecord");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createBatchPaymentHistoryRecord");
    }
    
    // create path and map variables
    String localVarPath = "/BatchPayments/{BatchPaymentID}/History"
      .replaceAll("\\{" + "BatchPaymentID" + "\\}", apiClient.escapeString(batchPaymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allow for the creation of new custom payment service for specified Branding Theme
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param brandingThemeID Unique identifier for a Branding Theme (required)
   * @param paymentService  (required)
   * @return PaymentServices
   * @throws ApiException if fails to make API call
   */
  public PaymentServices createBrandingThemePaymentServices(String xeroTenantId, UUID brandingThemeID, PaymentService paymentService) throws ApiException {
    return createBrandingThemePaymentServicesWithHttpInfo(xeroTenantId, brandingThemeID, paymentService).getData();
      }

  /**
   * Allow for the creation of new custom payment service for specified Branding Theme
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param brandingThemeID Unique identifier for a Branding Theme (required)
   * @param paymentService  (required)
   * @return ApiResponse&lt;PaymentServices&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<PaymentServices> createBrandingThemePaymentServicesWithHttpInfo(String xeroTenantId, UUID brandingThemeID, PaymentService paymentService) throws ApiException {
    Object localVarPostBody = paymentService;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createBrandingThemePaymentServices");
    }
    
    // verify the required parameter 'brandingThemeID' is set
    if (brandingThemeID == null) {
      throw new ApiException(400, "Missing the required parameter 'brandingThemeID' when calling createBrandingThemePaymentServices");
    }
    
    // verify the required parameter 'paymentService' is set
    if (paymentService == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentService' when calling createBrandingThemePaymentServices");
    }
    
    // create path and map variables
    String localVarPath = "/BrandingThemes/{BrandingThemeID}/PaymentServices"
      .replaceAll("\\{" + "BrandingThemeID" + "\\}", apiClient.escapeString(brandingThemeID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<PaymentServices> localVarReturnType = new GenericType<PaymentServices>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contact  (required)
   * @return Contacts
   * @throws ApiException if fails to make API call
   */
  public Contacts createContact(String xeroTenantId, Contact contact) throws ApiException {
    return createContactWithHttpInfo(xeroTenantId, contact).getData();
      }

  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contact  (required)
   * @return ApiResponse&lt;Contacts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Contacts> createContactWithHttpInfo(String xeroTenantId, Contact contact) throws ApiException {
    Object localVarPostBody = contact;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createContact");
    }
    
    // verify the required parameter 'contact' is set
    if (contact == null) {
      throw new ApiException(400, "Missing the required parameter 'contact' when calling createContact");
    }
    
    // create path and map variables
    String localVarPath = "/Contacts";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Contacts> localVarReturnType = new GenericType<Contacts>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @param fileName Name for the file you are attaching (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments createContactAttachmentByFileName(String xeroTenantId, UUID contactID, String fileName, byte[] body) throws ApiException {
    return createContactAttachmentByFileNameWithHttpInfo(xeroTenantId, contactID, fileName, body).getData();
      }

  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @param fileName Name for the file you are attaching (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> createContactAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID contactID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createContactAttachmentByFileName");
    }
    
    // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactID' when calling createContactAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling createContactAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createContactAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/Contacts/{ContactID}/Attachments/{FileName}"
      .replaceAll("\\{" + "ContactID" + "\\}", apiClient.escapeString(contactID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create a contact group
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactGroups an array of contact groups with names specified (required)
   * @return ContactGroups
   * @throws ApiException if fails to make API call
   */
  public ContactGroups createContactGroup(String xeroTenantId, ContactGroups contactGroups) throws ApiException {
    return createContactGroupWithHttpInfo(xeroTenantId, contactGroups).getData();
      }

  /**
   * Allows you to create a contact group
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactGroups an array of contact groups with names specified (required)
   * @return ApiResponse&lt;ContactGroups&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ContactGroups> createContactGroupWithHttpInfo(String xeroTenantId, ContactGroups contactGroups) throws ApiException {
    Object localVarPostBody = contactGroups;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createContactGroup");
    }
    
    // verify the required parameter 'contactGroups' is set
    if (contactGroups == null) {
      throw new ApiException(400, "Missing the required parameter 'contactGroups' when calling createContactGroup");
    }
    
    // create path and map variables
    String localVarPath = "/ContactGroups";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ContactGroups> localVarReturnType = new GenericType<ContactGroups>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to add Contacts to a Contract Group
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactGroupID Unique identifier for a Contact Group (required)
   * @param contacts an array of contacts with ContactID to be added to ContactGroup (required)
   * @return Contacts
   * @throws ApiException if fails to make API call
   */
  public Contacts createContactGroupContacts(String xeroTenantId, UUID contactGroupID, Contacts contacts) throws ApiException {
    return createContactGroupContactsWithHttpInfo(xeroTenantId, contactGroupID, contacts).getData();
      }

  /**
   * Allows you to add Contacts to a Contract Group
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactGroupID Unique identifier for a Contact Group (required)
   * @param contacts an array of contacts with ContactID to be added to ContactGroup (required)
   * @return ApiResponse&lt;Contacts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Contacts> createContactGroupContactsWithHttpInfo(String xeroTenantId, UUID contactGroupID, Contacts contacts) throws ApiException {
    Object localVarPostBody = contacts;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createContactGroupContacts");
    }
    
    // verify the required parameter 'contactGroupID' is set
    if (contactGroupID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactGroupID' when calling createContactGroupContacts");
    }
    
    // verify the required parameter 'contacts' is set
    if (contacts == null) {
      throw new ApiException(400, "Missing the required parameter 'contacts' when calling createContactGroupContacts");
    }
    
    // create path and map variables
    String localVarPath = "/ContactGroups/{ContactGroupID}/Contacts"
      .replaceAll("\\{" + "ContactGroupID" + "\\}", apiClient.escapeString(contactGroupID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Contacts> localVarReturnType = new GenericType<Contacts>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a history records of an Contact
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createContactHistory(String xeroTenantId, UUID contactID, HistoryRecords historyRecords) throws ApiException {
    return createContactHistoryWithHttpInfo(xeroTenantId, contactID, historyRecords).getData();
      }

  /**
   * Allows you to retrieve a history records of an Contact
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createContactHistoryWithHttpInfo(String xeroTenantId, UUID contactID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createContactHistory");
    }
    
    // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactID' when calling createContactHistory");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createContactHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Contacts/{ContactID}/History"
      .replaceAll("\\{" + "ContactID" + "\\}", apiClient.escapeString(contactID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create a credit note
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNotes an array of Credit Notes with a single CreditNote object. (required)
   * @param summarizeErrors shows validation errors for each credit note (optional)
   * @return CreditNotes
   * @throws ApiException if fails to make API call
   */
  public CreditNotes createCreditNote(String xeroTenantId, CreditNotes creditNotes, Boolean summarizeErrors) throws ApiException {
    return createCreditNoteWithHttpInfo(xeroTenantId, creditNotes, summarizeErrors).getData();
      }

  /**
   * Allows you to create a credit note
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNotes an array of Credit Notes with a single CreditNote object. (required)
   * @param summarizeErrors shows validation errors for each credit note (optional)
   * @return ApiResponse&lt;CreditNotes&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<CreditNotes> createCreditNoteWithHttpInfo(String xeroTenantId, CreditNotes creditNotes, Boolean summarizeErrors) throws ApiException {
    Object localVarPostBody = creditNotes;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createCreditNote");
    }
    
    // verify the required parameter 'creditNotes' is set
    if (creditNotes == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNotes' when calling createCreditNote");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "SummarizeErrors", summarizeErrors));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<CreditNotes> localVarReturnType = new GenericType<CreditNotes>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create Allocation on CreditNote
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param allocations an array of Allocations with single Allocation object defined. (required)
   * @return Allocations
   * @throws ApiException if fails to make API call
   */
  public Allocations createCreditNoteAllocation(String xeroTenantId, UUID creditNoteID, Allocations allocations) throws ApiException {
    return createCreditNoteAllocationWithHttpInfo(xeroTenantId, creditNoteID, allocations).getData();
      }

  /**
   * Allows you to create Allocation on CreditNote
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param allocations an array of Allocations with single Allocation object defined. (required)
   * @return ApiResponse&lt;Allocations&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Allocations> createCreditNoteAllocationWithHttpInfo(String xeroTenantId, UUID creditNoteID, Allocations allocations) throws ApiException {
    Object localVarPostBody = allocations;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createCreditNoteAllocation");
    }
    
    // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNoteID' when calling createCreditNoteAllocation");
    }
    
    // verify the required parameter 'allocations' is set
    if (allocations == null) {
      throw new ApiException(400, "Missing the required parameter 'allocations' when calling createCreditNoteAllocation");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes/{CreditNoteID}/Allocations"
      .replaceAll("\\{" + "CreditNoteID" + "\\}", apiClient.escapeString(creditNoteID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Allocations> localVarReturnType = new GenericType<Allocations>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create Attachments on CreditNote by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param fileName Name of the file you are attaching to Credit Note (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments createCreditNoteAttachmentByFileName(String xeroTenantId, UUID creditNoteID, String fileName, byte[] body) throws ApiException {
    return createCreditNoteAttachmentByFileNameWithHttpInfo(xeroTenantId, creditNoteID, fileName, body).getData();
      }

  /**
   * Allows you to create Attachments on CreditNote by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param fileName Name of the file you are attaching to Credit Note (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> createCreditNoteAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID creditNoteID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createCreditNoteAttachmentByFileName");
    }
    
    // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNoteID' when calling createCreditNoteAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling createCreditNoteAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createCreditNoteAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}"
      .replaceAll("\\{" + "CreditNoteID" + "\\}", apiClient.escapeString(creditNoteID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a history records of an CreditNote
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createCreditNoteHistory(String xeroTenantId, UUID creditNoteID, HistoryRecords historyRecords) throws ApiException {
    return createCreditNoteHistoryWithHttpInfo(xeroTenantId, creditNoteID, historyRecords).getData();
      }

  /**
   * Allows you to retrieve a history records of an CreditNote
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createCreditNoteHistoryWithHttpInfo(String xeroTenantId, UUID creditNoteID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createCreditNoteHistory");
    }
    
    // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNoteID' when calling createCreditNoteHistory");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createCreditNoteHistory");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes/{CreditNoteID}/History"
      .replaceAll("\\{" + "CreditNoteID" + "\\}", apiClient.escapeString(creditNoteID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param currencies  (required)
   * @return Currencies
   * @throws ApiException if fails to make API call
   */
  public Currencies createCurrency(String xeroTenantId, Currencies currencies) throws ApiException {
    return createCurrencyWithHttpInfo(xeroTenantId, currencies).getData();
      }

  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param currencies  (required)
   * @return ApiResponse&lt;Currencies&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Currencies> createCurrencyWithHttpInfo(String xeroTenantId, Currencies currencies) throws ApiException {
    Object localVarPostBody = currencies;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createCurrency");
    }
    
    // verify the required parameter 'currencies' is set
    if (currencies == null) {
      throw new ApiException(400, "Missing the required parameter 'currencies' when calling createCurrency");
    }
    
    // create path and map variables
    String localVarPath = "/Currencies";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Currencies> localVarReturnType = new GenericType<Currencies>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create new employees used in Xero payrun
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param employees  (required)
   * @return Employees
   * @throws ApiException if fails to make API call
   */
  public Employees createEmployee(String xeroTenantId, Employees employees) throws ApiException {
    return createEmployeeWithHttpInfo(xeroTenantId, employees).getData();
      }

  /**
   * Allows you to create new employees used in Xero payrun
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param employees  (required)
   * @return ApiResponse&lt;Employees&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Employees> createEmployeeWithHttpInfo(String xeroTenantId, Employees employees) throws ApiException {
    Object localVarPostBody = employees;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createEmployee");
    }
    
    // verify the required parameter 'employees' is set
    if (employees == null) {
      throw new ApiException(400, "Missing the required parameter 'employees' when calling createEmployee");
    }
    
    // create path and map variables
    String localVarPath = "/Employees";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Employees> localVarReturnType = new GenericType<Employees>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve expense claims
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param expenseClaims  (required)
   * @param summarizeErrors shows validation errors for each expense claim (optional)
   * @return ExpenseClaims
   * @throws ApiException if fails to make API call
   */
  public ExpenseClaims createExpenseClaim(String xeroTenantId, ExpenseClaims expenseClaims, Boolean summarizeErrors) throws ApiException {
    return createExpenseClaimWithHttpInfo(xeroTenantId, expenseClaims, summarizeErrors).getData();
      }

  /**
   * Allows you to retrieve expense claims
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param expenseClaims  (required)
   * @param summarizeErrors shows validation errors for each expense claim (optional)
   * @return ApiResponse&lt;ExpenseClaims&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ExpenseClaims> createExpenseClaimWithHttpInfo(String xeroTenantId, ExpenseClaims expenseClaims, Boolean summarizeErrors) throws ApiException {
    Object localVarPostBody = expenseClaims;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createExpenseClaim");
    }
    
    // verify the required parameter 'expenseClaims' is set
    if (expenseClaims == null) {
      throw new ApiException(400, "Missing the required parameter 'expenseClaims' when calling createExpenseClaim");
    }
    
    // create path and map variables
    String localVarPath = "/ExpenseClaims";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "SummarizeErrors", summarizeErrors));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ExpenseClaims> localVarReturnType = new GenericType<ExpenseClaims>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create a history records of an ExpenseClaim
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param expenseClaimID Unique identifier for a ExpenseClaim (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createExpenseClaimHistory(String xeroTenantId, UUID expenseClaimID, HistoryRecords historyRecords) throws ApiException {
    return createExpenseClaimHistoryWithHttpInfo(xeroTenantId, expenseClaimID, historyRecords).getData();
      }

  /**
   * Allows you to create a history records of an ExpenseClaim
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param expenseClaimID Unique identifier for a ExpenseClaim (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createExpenseClaimHistoryWithHttpInfo(String xeroTenantId, UUID expenseClaimID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createExpenseClaimHistory");
    }
    
    // verify the required parameter 'expenseClaimID' is set
    if (expenseClaimID == null) {
      throw new ApiException(400, "Missing the required parameter 'expenseClaimID' when calling createExpenseClaimHistory");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createExpenseClaimHistory");
    }
    
    // create path and map variables
    String localVarPath = "/ExpenseClaims/{ExpenseClaimID}/History"
      .replaceAll("\\{" + "ExpenseClaimID" + "\\}", apiClient.escapeString(expenseClaimID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create any sales invoices or purchase bills
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoices  (required)
   * @param summarizeErrors shows validation errors for each invoice (optional)
   * @return Invoices
   * @throws ApiException if fails to make API call
   */
  public Invoices createInvoice(String xeroTenantId, Invoices invoices, Boolean summarizeErrors) throws ApiException {
    return createInvoiceWithHttpInfo(xeroTenantId, invoices, summarizeErrors).getData();
      }

  /**
   * Allows you to create any sales invoices or purchase bills
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoices  (required)
   * @param summarizeErrors shows validation errors for each invoice (optional)
   * @return ApiResponse&lt;Invoices&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Invoices> createInvoiceWithHttpInfo(String xeroTenantId, Invoices invoices, Boolean summarizeErrors) throws ApiException {
    Object localVarPostBody = invoices;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createInvoice");
    }
    
    // verify the required parameter 'invoices' is set
    if (invoices == null) {
      throw new ApiException(400, "Missing the required parameter 'invoices' when calling createInvoice");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "SummarizeErrors", summarizeErrors));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Invoices> localVarReturnType = new GenericType<Invoices>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create an Attachment on invoices or purchase bills by it&#39;s filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param fileName Name of the file you are attaching (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments createInvoiceAttachmentByFileName(String xeroTenantId, UUID invoiceID, String fileName, byte[] body) throws ApiException {
    return createInvoiceAttachmentByFileNameWithHttpInfo(xeroTenantId, invoiceID, fileName, body).getData();
      }

  /**
   * Allows you to create an Attachment on invoices or purchase bills by it&#39;s filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param fileName Name of the file you are attaching (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> createInvoiceAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID invoiceID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceID' when calling createInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling createInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createInvoiceAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices/{InvoiceID}/Attachments/{FileName}"
      .replaceAll("\\{" + "InvoiceID" + "\\}", apiClient.escapeString(invoiceID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a history records of an invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createInvoiceHistory(String xeroTenantId, UUID invoiceID, HistoryRecords historyRecords) throws ApiException {
    return createInvoiceHistoryWithHttpInfo(xeroTenantId, invoiceID, historyRecords).getData();
      }

  /**
   * Allows you to retrieve a history records of an invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createInvoiceHistoryWithHttpInfo(String xeroTenantId, UUID invoiceID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createInvoiceHistory");
    }
    
    // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceID' when calling createInvoiceHistory");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createInvoiceHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices/{InvoiceID}/History"
      .replaceAll("\\{" + "InvoiceID" + "\\}", apiClient.escapeString(invoiceID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create an item
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param items  (required)
   * @return Items
   * @throws ApiException if fails to make API call
   */
  public Items createItem(String xeroTenantId, Items items) throws ApiException {
    return createItemWithHttpInfo(xeroTenantId, items).getData();
      }

  /**
   * Allows you to create an item
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param items  (required)
   * @return ApiResponse&lt;Items&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Items> createItemWithHttpInfo(String xeroTenantId, Items items) throws ApiException {
    Object localVarPostBody = items;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createItem");
    }
    
    // verify the required parameter 'items' is set
    if (items == null) {
      throw new ApiException(400, "Missing the required parameter 'items' when calling createItem");
    }
    
    // create path and map variables
    String localVarPath = "/Items";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Items> localVarReturnType = new GenericType<Items>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create a history record for items
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param itemID Unique identifier for an Item (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createItemHistory(String xeroTenantId, UUID itemID, HistoryRecords historyRecords) throws ApiException {
    return createItemHistoryWithHttpInfo(xeroTenantId, itemID, historyRecords).getData();
      }

  /**
   * Allows you to create a history record for items
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param itemID Unique identifier for an Item (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createItemHistoryWithHttpInfo(String xeroTenantId, UUID itemID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createItemHistory");
    }
    
    // verify the required parameter 'itemID' is set
    if (itemID == null) {
      throw new ApiException(400, "Missing the required parameter 'itemID' when calling createItemHistory");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createItemHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Items/{ItemID}/History"
      .replaceAll("\\{" + "ItemID" + "\\}", apiClient.escapeString(itemID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create linked transactions (billable expenses)
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param linkedTransactions  (required)
   * @return LinkedTransactions
   * @throws ApiException if fails to make API call
   */
  public LinkedTransactions createLinkedTransaction(String xeroTenantId, LinkedTransactions linkedTransactions) throws ApiException {
    return createLinkedTransactionWithHttpInfo(xeroTenantId, linkedTransactions).getData();
      }

  /**
   * Allows you to create linked transactions (billable expenses)
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param linkedTransactions  (required)
   * @return ApiResponse&lt;LinkedTransactions&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<LinkedTransactions> createLinkedTransactionWithHttpInfo(String xeroTenantId, LinkedTransactions linkedTransactions) throws ApiException {
    Object localVarPostBody = linkedTransactions;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createLinkedTransaction");
    }
    
    // verify the required parameter 'linkedTransactions' is set
    if (linkedTransactions == null) {
      throw new ApiException(400, "Missing the required parameter 'linkedTransactions' when calling createLinkedTransaction");
    }
    
    // create path and map variables
    String localVarPath = "/LinkedTransactions";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<LinkedTransactions> localVarReturnType = new GenericType<LinkedTransactions>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create a manual journal
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournals  (required)
   * @return ManualJournals
   * @throws ApiException if fails to make API call
   */
  public ManualJournals createManualJournal(String xeroTenantId, ManualJournals manualJournals) throws ApiException {
    return createManualJournalWithHttpInfo(xeroTenantId, manualJournals).getData();
      }

  /**
   * Allows you to create a manual journal
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournals  (required)
   * @return ApiResponse&lt;ManualJournals&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ManualJournals> createManualJournalWithHttpInfo(String xeroTenantId, ManualJournals manualJournals) throws ApiException {
    Object localVarPostBody = manualJournals;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createManualJournal");
    }
    
    // verify the required parameter 'manualJournals' is set
    if (manualJournals == null) {
      throw new ApiException(400, "Missing the required parameter 'manualJournals' when calling createManualJournal");
    }
    
    // create path and map variables
    String localVarPath = "/ManualJournals";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ManualJournals> localVarReturnType = new GenericType<ManualJournals>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create a specified Attachment on ManualJournal by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @param fileName The name of the file being attached to a ManualJournal (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments createManualJournalAttachmentByFileName(String xeroTenantId, UUID manualJournalID, String fileName, byte[] body) throws ApiException {
    return createManualJournalAttachmentByFileNameWithHttpInfo(xeroTenantId, manualJournalID, fileName, body).getData();
      }

  /**
   * Allows you to create a specified Attachment on ManualJournal by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @param fileName The name of the file being attached to a ManualJournal (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> createManualJournalAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID manualJournalID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createManualJournalAttachmentByFileName");
    }
    
    // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new ApiException(400, "Missing the required parameter 'manualJournalID' when calling createManualJournalAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling createManualJournalAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createManualJournalAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}"
      .replaceAll("\\{" + "ManualJournalID" + "\\}", apiClient.escapeString(manualJournalID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Allocations for overpayments
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param overpaymentID Unique identifier for a Overpayment (required)
   * @param allocations  (required)
   * @return Allocations
   * @throws ApiException if fails to make API call
   */
  public Allocations createOverpaymentAllocation(String xeroTenantId, UUID overpaymentID, Allocations allocations) throws ApiException {
    return createOverpaymentAllocationWithHttpInfo(xeroTenantId, overpaymentID, allocations).getData();
      }

  /**
   * Allows you to retrieve Allocations for overpayments
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param overpaymentID Unique identifier for a Overpayment (required)
   * @param allocations  (required)
   * @return ApiResponse&lt;Allocations&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Allocations> createOverpaymentAllocationWithHttpInfo(String xeroTenantId, UUID overpaymentID, Allocations allocations) throws ApiException {
    Object localVarPostBody = allocations;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createOverpaymentAllocation");
    }
    
    // verify the required parameter 'overpaymentID' is set
    if (overpaymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'overpaymentID' when calling createOverpaymentAllocation");
    }
    
    // verify the required parameter 'allocations' is set
    if (allocations == null) {
      throw new ApiException(400, "Missing the required parameter 'allocations' when calling createOverpaymentAllocation");
    }
    
    // create path and map variables
    String localVarPath = "/Overpayments/{OverpaymentID}/Allocations"
      .replaceAll("\\{" + "OverpaymentID" + "\\}", apiClient.escapeString(overpaymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Allocations> localVarReturnType = new GenericType<Allocations>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create history records of an Overpayment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param overpaymentID Unique identifier for a Overpayment (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createOverpaymentHistory(String xeroTenantId, UUID overpaymentID, HistoryRecords historyRecords) throws ApiException {
    return createOverpaymentHistoryWithHttpInfo(xeroTenantId, overpaymentID, historyRecords).getData();
      }

  /**
   * Allows you to create history records of an Overpayment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param overpaymentID Unique identifier for a Overpayment (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createOverpaymentHistoryWithHttpInfo(String xeroTenantId, UUID overpaymentID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createOverpaymentHistory");
    }
    
    // verify the required parameter 'overpaymentID' is set
    if (overpaymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'overpaymentID' when calling createOverpaymentHistory");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createOverpaymentHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Overpayments/{OverpaymentID}/History"
      .replaceAll("\\{" + "OverpaymentID" + "\\}", apiClient.escapeString(overpaymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create payments for invoices and credit notes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param payments  (required)
   * @return Payments
   * @throws ApiException if fails to make API call
   */
  public Payments createPayment(String xeroTenantId, Payments payments) throws ApiException {
    return createPaymentWithHttpInfo(xeroTenantId, payments).getData();
      }

  /**
   * Allows you to create payments for invoices and credit notes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param payments  (required)
   * @return ApiResponse&lt;Payments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Payments> createPaymentWithHttpInfo(String xeroTenantId, Payments payments) throws ApiException {
    Object localVarPostBody = payments;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createPayment");
    }
    
    // verify the required parameter 'payments' is set
    if (payments == null) {
      throw new ApiException(400, "Missing the required parameter 'payments' when calling createPayment");
    }
    
    // create path and map variables
    String localVarPath = "/Payments";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Payments> localVarReturnType = new GenericType<Payments>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create a history record for a payment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param paymentID Unique identifier for a Payment (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createPaymentHistory(String xeroTenantId, UUID paymentID, HistoryRecords historyRecords) throws ApiException {
    return createPaymentHistoryWithHttpInfo(xeroTenantId, paymentID, historyRecords).getData();
      }

  /**
   * Allows you to create a history record for a payment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param paymentID Unique identifier for a Payment (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createPaymentHistoryWithHttpInfo(String xeroTenantId, UUID paymentID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createPaymentHistory");
    }
    
    // verify the required parameter 'paymentID' is set
    if (paymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentID' when calling createPaymentHistory");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createPaymentHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Payments/{PaymentID}/History"
      .replaceAll("\\{" + "PaymentID" + "\\}", apiClient.escapeString(paymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create payment services
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param paymentServices  (required)
   * @return PaymentServices
   * @throws ApiException if fails to make API call
   */
  public PaymentServices createPaymentService(String xeroTenantId, PaymentServices paymentServices) throws ApiException {
    return createPaymentServiceWithHttpInfo(xeroTenantId, paymentServices).getData();
      }

  /**
   * Allows you to create payment services
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param paymentServices  (required)
   * @return ApiResponse&lt;PaymentServices&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<PaymentServices> createPaymentServiceWithHttpInfo(String xeroTenantId, PaymentServices paymentServices) throws ApiException {
    Object localVarPostBody = paymentServices;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createPaymentService");
    }
    
    // verify the required parameter 'paymentServices' is set
    if (paymentServices == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentServices' when calling createPaymentService");
    }
    
    // create path and map variables
    String localVarPath = "/PaymentServices";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<PaymentServices> localVarReturnType = new GenericType<PaymentServices>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create an Allocation for prepayments
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param prepaymentID  (required)
   * @param allocations  (required)
   * @return Allocations
   * @throws ApiException if fails to make API call
   */
  public Allocations createPrepaymentAllocation(String xeroTenantId, UUID prepaymentID, Allocations allocations) throws ApiException {
    return createPrepaymentAllocationWithHttpInfo(xeroTenantId, prepaymentID, allocations).getData();
      }

  /**
   * Allows you to create an Allocation for prepayments
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param prepaymentID  (required)
   * @param allocations  (required)
   * @return ApiResponse&lt;Allocations&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Allocations> createPrepaymentAllocationWithHttpInfo(String xeroTenantId, UUID prepaymentID, Allocations allocations) throws ApiException {
    Object localVarPostBody = allocations;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createPrepaymentAllocation");
    }
    
    // verify the required parameter 'prepaymentID' is set
    if (prepaymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'prepaymentID' when calling createPrepaymentAllocation");
    }
    
    // verify the required parameter 'allocations' is set
    if (allocations == null) {
      throw new ApiException(400, "Missing the required parameter 'allocations' when calling createPrepaymentAllocation");
    }
    
    // create path and map variables
    String localVarPath = "/Prepayments/{PrepaymentID}/Allocations"
      .replaceAll("\\{" + "PrepaymentID" + "\\}", apiClient.escapeString(prepaymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Allocations> localVarReturnType = new GenericType<Allocations>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create a history record for an Prepayment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param prepaymentID Unique identifier for a PrePayment (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createPrepaymentHistory(String xeroTenantId, UUID prepaymentID, HistoryRecords historyRecords) throws ApiException {
    return createPrepaymentHistoryWithHttpInfo(xeroTenantId, prepaymentID, historyRecords).getData();
      }

  /**
   * Allows you to create a history record for an Prepayment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param prepaymentID Unique identifier for a PrePayment (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createPrepaymentHistoryWithHttpInfo(String xeroTenantId, UUID prepaymentID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createPrepaymentHistory");
    }
    
    // verify the required parameter 'prepaymentID' is set
    if (prepaymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'prepaymentID' when calling createPrepaymentHistory");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createPrepaymentHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Prepayments/{PrepaymentID}/History"
      .replaceAll("\\{" + "PrepaymentID" + "\\}", apiClient.escapeString(prepaymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create purchase orders
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param purchaseOrders  (required)
   * @param summarizeErrors shows validation errors for each purchase order. (optional)
   * @return PurchaseOrders
   * @throws ApiException if fails to make API call
   */
  public PurchaseOrders createPurchaseOrder(String xeroTenantId, PurchaseOrders purchaseOrders, Boolean summarizeErrors) throws ApiException {
    return createPurchaseOrderWithHttpInfo(xeroTenantId, purchaseOrders, summarizeErrors).getData();
      }

  /**
   * Allows you to create purchase orders
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param purchaseOrders  (required)
   * @param summarizeErrors shows validation errors for each purchase order. (optional)
   * @return ApiResponse&lt;PurchaseOrders&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<PurchaseOrders> createPurchaseOrderWithHttpInfo(String xeroTenantId, PurchaseOrders purchaseOrders, Boolean summarizeErrors) throws ApiException {
    Object localVarPostBody = purchaseOrders;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createPurchaseOrder");
    }
    
    // verify the required parameter 'purchaseOrders' is set
    if (purchaseOrders == null) {
      throw new ApiException(400, "Missing the required parameter 'purchaseOrders' when calling createPurchaseOrder");
    }
    
    // create path and map variables
    String localVarPath = "/PurchaseOrders";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "SummarizeErrors", summarizeErrors));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<PurchaseOrders> localVarReturnType = new GenericType<PurchaseOrders>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create HistoryRecord for purchase orders
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param purchaseOrderID Unique identifier for a PurchaseOrder (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createPurchaseOrderHistory(String xeroTenantId, UUID purchaseOrderID, HistoryRecords historyRecords) throws ApiException {
    return createPurchaseOrderHistoryWithHttpInfo(xeroTenantId, purchaseOrderID, historyRecords).getData();
      }

  /**
   * Allows you to create HistoryRecord for purchase orders
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param purchaseOrderID Unique identifier for a PurchaseOrder (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createPurchaseOrderHistoryWithHttpInfo(String xeroTenantId, UUID purchaseOrderID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createPurchaseOrderHistory");
    }
    
    // verify the required parameter 'purchaseOrderID' is set
    if (purchaseOrderID == null) {
      throw new ApiException(400, "Missing the required parameter 'purchaseOrderID' when calling createPurchaseOrderHistory");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createPurchaseOrderHistory");
    }
    
    // create path and map variables
    String localVarPath = "/PurchaseOrders/{PurchaseOrderID}/History"
      .replaceAll("\\{" + "PurchaseOrderID" + "\\}", apiClient.escapeString(purchaseOrderID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create draft expense claim receipts for any user
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receipts  (required)
   * @return Receipts
   * @throws ApiException if fails to make API call
   */
  public Receipts createReceipt(String xeroTenantId, Receipts receipts) throws ApiException {
    return createReceiptWithHttpInfo(xeroTenantId, receipts).getData();
      }

  /**
   * Allows you to create draft expense claim receipts for any user
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receipts  (required)
   * @return ApiResponse&lt;Receipts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Receipts> createReceiptWithHttpInfo(String xeroTenantId, Receipts receipts) throws ApiException {
    Object localVarPostBody = receipts;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createReceipt");
    }
    
    // verify the required parameter 'receipts' is set
    if (receipts == null) {
      throw new ApiException(400, "Missing the required parameter 'receipts' when calling createReceipt");
    }
    
    // create path and map variables
    String localVarPath = "/Receipts";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Receipts> localVarReturnType = new GenericType<Receipts>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create Attachment on expense claim receipts by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @param fileName The name of the file being attached to the Receipt (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments createReceiptAttachmentByFileName(String xeroTenantId, UUID receiptID, String fileName, byte[] body) throws ApiException {
    return createReceiptAttachmentByFileNameWithHttpInfo(xeroTenantId, receiptID, fileName, body).getData();
      }

  /**
   * Allows you to create Attachment on expense claim receipts by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @param fileName The name of the file being attached to the Receipt (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> createReceiptAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID receiptID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createReceiptAttachmentByFileName");
    }
    
    // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new ApiException(400, "Missing the required parameter 'receiptID' when calling createReceiptAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling createReceiptAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createReceiptAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/Receipts/{ReceiptID}/Attachments/{FileName}"
      .replaceAll("\\{" + "ReceiptID" + "\\}", apiClient.escapeString(receiptID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a history records of an Receipt
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createReceiptHistory(String xeroTenantId, UUID receiptID, HistoryRecords historyRecords) throws ApiException {
    return createReceiptHistoryWithHttpInfo(xeroTenantId, receiptID, historyRecords).getData();
      }

  /**
   * Allows you to retrieve a history records of an Receipt
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createReceiptHistoryWithHttpInfo(String xeroTenantId, UUID receiptID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createReceiptHistory");
    }
    
    // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new ApiException(400, "Missing the required parameter 'receiptID' when calling createReceiptHistory");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createReceiptHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Receipts/{ReceiptID}/History"
      .replaceAll("\\{" + "ReceiptID" + "\\}", apiClient.escapeString(receiptID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create attachment on repeating invoices by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @param fileName The name of the file being attached to a Repeating Invoice (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments createRepeatingInvoiceAttachmentByFileName(String xeroTenantId, UUID repeatingInvoiceID, String fileName, byte[] body) throws ApiException {
    return createRepeatingInvoiceAttachmentByFileNameWithHttpInfo(xeroTenantId, repeatingInvoiceID, fileName, body).getData();
      }

  /**
   * Allows you to create attachment on repeating invoices by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @param fileName The name of the file being attached to a Repeating Invoice (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> createRepeatingInvoiceAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID repeatingInvoiceID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createRepeatingInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'repeatingInvoiceID' when calling createRepeatingInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling createRepeatingInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createRepeatingInvoiceAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}"
      .replaceAll("\\{" + "RepeatingInvoiceID" + "\\}", apiClient.escapeString(repeatingInvoiceID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create history for a repeating invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @param historyRecords  (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords createRepeatingInvoiceHistory(String xeroTenantId, UUID repeatingInvoiceID, HistoryRecords historyRecords) throws ApiException {
    return createRepeatingInvoiceHistoryWithHttpInfo(xeroTenantId, repeatingInvoiceID, historyRecords).getData();
      }

  /**
   * Allows you to create history for a repeating invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @param historyRecords  (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> createRepeatingInvoiceHistoryWithHttpInfo(String xeroTenantId, UUID repeatingInvoiceID, HistoryRecords historyRecords) throws ApiException {
    Object localVarPostBody = historyRecords;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createRepeatingInvoiceHistory");
    }
    
    // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'repeatingInvoiceID' when calling createRepeatingInvoiceHistory");
    }
    
    // verify the required parameter 'historyRecords' is set
    if (historyRecords == null) {
      throw new ApiException(400, "Missing the required parameter 'historyRecords' when calling createRepeatingInvoiceHistory");
    }
    
    // create path and map variables
    String localVarPath = "/RepeatingInvoices/{RepeatingInvoiceID}/History"
      .replaceAll("\\{" + "RepeatingInvoiceID" + "\\}", apiClient.escapeString(repeatingInvoiceID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create Tax Rates
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param taxRates  (required)
   * @return TaxRates
   * @throws ApiException if fails to make API call
   */
  public TaxRates createTaxRate(String xeroTenantId, TaxRates taxRates) throws ApiException {
    return createTaxRateWithHttpInfo(xeroTenantId, taxRates).getData();
      }

  /**
   * Allows you to create Tax Rates
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param taxRates  (required)
   * @return ApiResponse&lt;TaxRates&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<TaxRates> createTaxRateWithHttpInfo(String xeroTenantId, TaxRates taxRates) throws ApiException {
    Object localVarPostBody = taxRates;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createTaxRate");
    }
    
    // verify the required parameter 'taxRates' is set
    if (taxRates == null) {
      throw new ApiException(400, "Missing the required parameter 'taxRates' when calling createTaxRate");
    }
    
    // create path and map variables
    String localVarPath = "/TaxRates";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<TaxRates> localVarReturnType = new GenericType<TaxRates>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create tracking categories
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param trackingCategory  (required)
   * @return TrackingCategories
   * @throws ApiException if fails to make API call
   */
  public TrackingCategories createTrackingCategory(String xeroTenantId, TrackingCategory trackingCategory) throws ApiException {
    return createTrackingCategoryWithHttpInfo(xeroTenantId, trackingCategory).getData();
      }

  /**
   * Allows you to create tracking categories
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param trackingCategory  (required)
   * @return ApiResponse&lt;TrackingCategories&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<TrackingCategories> createTrackingCategoryWithHttpInfo(String xeroTenantId, TrackingCategory trackingCategory) throws ApiException {
    Object localVarPostBody = trackingCategory;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createTrackingCategory");
    }
    
    // verify the required parameter 'trackingCategory' is set
    if (trackingCategory == null) {
      throw new ApiException(400, "Missing the required parameter 'trackingCategory' when calling createTrackingCategory");
    }
    
    // create path and map variables
    String localVarPath = "/TrackingCategories";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<TrackingCategories> localVarReturnType = new GenericType<TrackingCategories>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to create options for a specified tracking category
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param trackingCategoryID Unique identifier for a TrackingCategory (required)
   * @param trackingOption  (required)
   * @return TrackingOptions
   * @throws ApiException if fails to make API call
   */
  public TrackingOptions createTrackingOptions(String xeroTenantId, UUID trackingCategoryID, TrackingOption trackingOption) throws ApiException {
    return createTrackingOptionsWithHttpInfo(xeroTenantId, trackingCategoryID, trackingOption).getData();
      }

  /**
   * Allows you to create options for a specified tracking category
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param trackingCategoryID Unique identifier for a TrackingCategory (required)
   * @param trackingOption  (required)
   * @return ApiResponse&lt;TrackingOptions&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<TrackingOptions> createTrackingOptionsWithHttpInfo(String xeroTenantId, UUID trackingCategoryID, TrackingOption trackingOption) throws ApiException {
    Object localVarPostBody = trackingOption;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling createTrackingOptions");
    }
    
    // verify the required parameter 'trackingCategoryID' is set
    if (trackingCategoryID == null) {
      throw new ApiException(400, "Missing the required parameter 'trackingCategoryID' when calling createTrackingOptions");
    }
    
    // verify the required parameter 'trackingOption' is set
    if (trackingOption == null) {
      throw new ApiException(400, "Missing the required parameter 'trackingOption' when calling createTrackingOptions");
    }
    
    // create path and map variables
    String localVarPath = "/TrackingCategories/{TrackingCategoryID}/Options"
      .replaceAll("\\{" + "TrackingCategoryID" + "\\}", apiClient.escapeString(trackingCategoryID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<TrackingOptions> localVarReturnType = new GenericType<TrackingOptions>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to delete a chart of accounts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for retrieving single object (required)
   * @return Accounts
   * @throws ApiException if fails to make API call
   */
  public Accounts deleteAccount(String xeroTenantId, UUID accountID) throws ApiException {
    return deleteAccountWithHttpInfo(xeroTenantId, accountID).getData();
      }

  /**
   * Allows you to delete a chart of accounts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for retrieving single object (required)
   * @return ApiResponse&lt;Accounts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Accounts> deleteAccountWithHttpInfo(String xeroTenantId, UUID accountID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling deleteAccount");
    }
    
    // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new ApiException(400, "Missing the required parameter 'accountID' when calling deleteAccount");
    }
    
    // create path and map variables
    String localVarPath = "/Accounts/{AccountID}"
      .replaceAll("\\{" + "AccountID" + "\\}", apiClient.escapeString(accountID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Accounts> localVarReturnType = new GenericType<Accounts>() {};
    return apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to delete a specific Contact from a Contract Group
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactGroupID Unique identifier for a Contact Group (required)
   * @param contactID Unique identifier for a Contact (required)
   * @throws ApiException if fails to make API call
   */
  public void deleteContactGroupContact(String xeroTenantId, UUID contactGroupID, UUID contactID) throws ApiException {

    deleteContactGroupContactWithHttpInfo(xeroTenantId, contactGroupID, contactID);
  }

  /**
   * Allows you to delete a specific Contact from a Contract Group
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactGroupID Unique identifier for a Contact Group (required)
   * @param contactID Unique identifier for a Contact (required)
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Void> deleteContactGroupContactWithHttpInfo(String xeroTenantId, UUID contactGroupID, UUID contactID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling deleteContactGroupContact");
    }
    
    // verify the required parameter 'contactGroupID' is set
    if (contactGroupID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactGroupID' when calling deleteContactGroupContact");
    }
    
    // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactID' when calling deleteContactGroupContact");
    }
    
    // create path and map variables
    String localVarPath = "/ContactGroups/{ContactGroupID}/Contacts/{ContactID}"
      .replaceAll("\\{" + "ContactGroupID" + "\\}", apiClient.escapeString(contactGroupID.toString()))
      .replaceAll("\\{" + "ContactID" + "\\}", apiClient.escapeString(contactID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };


    return apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Allows you to delete  all Contacts from a Contract Group
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactGroupID Unique identifier for a Contact Group (required)
   * @throws ApiException if fails to make API call
   */
  public void deleteContactGroupContacts(String xeroTenantId, UUID contactGroupID) throws ApiException {

    deleteContactGroupContactsWithHttpInfo(xeroTenantId, contactGroupID);
  }

  /**
   * Allows you to delete  all Contacts from a Contract Group
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactGroupID Unique identifier for a Contact Group (required)
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Void> deleteContactGroupContactsWithHttpInfo(String xeroTenantId, UUID contactGroupID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling deleteContactGroupContacts");
    }
    
    // verify the required parameter 'contactGroupID' is set
    if (contactGroupID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactGroupID' when calling deleteContactGroupContacts");
    }
    
    // create path and map variables
    String localVarPath = "/ContactGroups/{ContactGroupID}/Contacts"
      .replaceAll("\\{" + "ContactGroupID" + "\\}", apiClient.escapeString(contactGroupID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };


    return apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Allows you to delete a specified item
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param itemID Unique identifier for an Item (required)
   * @throws ApiException if fails to make API call
   */
  public void deleteItem(String xeroTenantId, UUID itemID) throws ApiException {

    deleteItemWithHttpInfo(xeroTenantId, itemID);
  }

  /**
   * Allows you to delete a specified item
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param itemID Unique identifier for an Item (required)
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Void> deleteItemWithHttpInfo(String xeroTenantId, UUID itemID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling deleteItem");
    }
    
    // verify the required parameter 'itemID' is set
    if (itemID == null) {
      throw new ApiException(400, "Missing the required parameter 'itemID' when calling deleteItem");
    }
    
    // create path and map variables
    String localVarPath = "/Items/{ItemID}"
      .replaceAll("\\{" + "ItemID" + "\\}", apiClient.escapeString(itemID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };


    return apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Allows you to delete a specified linked transactions (billable expenses)
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param linkedTransactionID Unique identifier for a LinkedTransaction (required)
   * @throws ApiException if fails to make API call
   */
  public void deleteLinkedTransaction(String xeroTenantId, UUID linkedTransactionID) throws ApiException {

    deleteLinkedTransactionWithHttpInfo(xeroTenantId, linkedTransactionID);
  }

  /**
   * Allows you to delete a specified linked transactions (billable expenses)
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param linkedTransactionID Unique identifier for a LinkedTransaction (required)
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Void> deleteLinkedTransactionWithHttpInfo(String xeroTenantId, UUID linkedTransactionID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling deleteLinkedTransaction");
    }
    
    // verify the required parameter 'linkedTransactionID' is set
    if (linkedTransactionID == null) {
      throw new ApiException(400, "Missing the required parameter 'linkedTransactionID' when calling deleteLinkedTransaction");
    }
    
    // create path and map variables
    String localVarPath = "/LinkedTransactions/{LinkedTransactionID}"
      .replaceAll("\\{" + "LinkedTransactionID" + "\\}", apiClient.escapeString(linkedTransactionID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };


    return apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Allows you to update a specified payment for invoices and credit notes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param paymentID Unique identifier for a Payment (required)
   * @param payments  (required)
   * @return Payments
   * @throws ApiException if fails to make API call
   */
  public Payments deletePayment(String xeroTenantId, UUID paymentID, Payments payments) throws ApiException {
    return deletePaymentWithHttpInfo(xeroTenantId, paymentID, payments).getData();
      }

  /**
   * Allows you to update a specified payment for invoices and credit notes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param paymentID Unique identifier for a Payment (required)
   * @param payments  (required)
   * @return ApiResponse&lt;Payments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Payments> deletePaymentWithHttpInfo(String xeroTenantId, UUID paymentID, Payments payments) throws ApiException {
    Object localVarPostBody = payments;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling deletePayment");
    }
    
    // verify the required parameter 'paymentID' is set
    if (paymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentID' when calling deletePayment");
    }
    
    // verify the required parameter 'payments' is set
    if (payments == null) {
      throw new ApiException(400, "Missing the required parameter 'payments' when calling deletePayment");
    }
    
    // create path and map variables
    String localVarPath = "/Payments/{PaymentID}"
      .replaceAll("\\{" + "PaymentID" + "\\}", apiClient.escapeString(paymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Payments> localVarReturnType = new GenericType<Payments>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to delete tracking categories
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param trackingCategoryID Unique identifier for a TrackingCategory (required)
   * @return TrackingCategories
   * @throws ApiException if fails to make API call
   */
  public TrackingCategories deleteTrackingCategory(String xeroTenantId, UUID trackingCategoryID) throws ApiException {
    return deleteTrackingCategoryWithHttpInfo(xeroTenantId, trackingCategoryID).getData();
      }

  /**
   * Allows you to delete tracking categories
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param trackingCategoryID Unique identifier for a TrackingCategory (required)
   * @return ApiResponse&lt;TrackingCategories&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<TrackingCategories> deleteTrackingCategoryWithHttpInfo(String xeroTenantId, UUID trackingCategoryID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling deleteTrackingCategory");
    }
    
    // verify the required parameter 'trackingCategoryID' is set
    if (trackingCategoryID == null) {
      throw new ApiException(400, "Missing the required parameter 'trackingCategoryID' when calling deleteTrackingCategory");
    }
    
    // create path and map variables
    String localVarPath = "/TrackingCategories/{TrackingCategoryID}"
      .replaceAll("\\{" + "TrackingCategoryID" + "\\}", apiClient.escapeString(trackingCategoryID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<TrackingCategories> localVarReturnType = new GenericType<TrackingCategories>() {};
    return apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to delete a specified option for a specified tracking category
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param trackingCategoryID Unique identifier for a TrackingCategory (required)
   * @param trackingOptionID Unique identifier for a Tracking Option (required)
   * @return TrackingOptions
   * @throws ApiException if fails to make API call
   */
  public TrackingOptions deleteTrackingOptions(String xeroTenantId, UUID trackingCategoryID, UUID trackingOptionID) throws ApiException {
    return deleteTrackingOptionsWithHttpInfo(xeroTenantId, trackingCategoryID, trackingOptionID).getData();
      }

  /**
   * Allows you to delete a specified option for a specified tracking category
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param trackingCategoryID Unique identifier for a TrackingCategory (required)
   * @param trackingOptionID Unique identifier for a Tracking Option (required)
   * @return ApiResponse&lt;TrackingOptions&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<TrackingOptions> deleteTrackingOptionsWithHttpInfo(String xeroTenantId, UUID trackingCategoryID, UUID trackingOptionID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling deleteTrackingOptions");
    }
    
    // verify the required parameter 'trackingCategoryID' is set
    if (trackingCategoryID == null) {
      throw new ApiException(400, "Missing the required parameter 'trackingCategoryID' when calling deleteTrackingOptions");
    }
    
    // verify the required parameter 'trackingOptionID' is set
    if (trackingOptionID == null) {
      throw new ApiException(400, "Missing the required parameter 'trackingOptionID' when calling deleteTrackingOptions");
    }
    
    // create path and map variables
    String localVarPath = "/TrackingCategories/{TrackingCategoryID}/Options/{TrackingOptionID}"
      .replaceAll("\\{" + "TrackingCategoryID" + "\\}", apiClient.escapeString(trackingCategoryID.toString()))
      .replaceAll("\\{" + "TrackingOptionID" + "\\}", apiClient.escapeString(trackingOptionID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<TrackingOptions> localVarReturnType = new GenericType<TrackingOptions>() {};
    return apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to email a copy of invoice to related Contact
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param requestEmpty  (required)
   * @throws ApiException if fails to make API call
   */
  public void emailInvoice(String xeroTenantId, UUID invoiceID, RequestEmpty requestEmpty) throws ApiException {

    emailInvoiceWithHttpInfo(xeroTenantId, invoiceID, requestEmpty);
  }

  /**
   * Allows you to email a copy of invoice to related Contact
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param requestEmpty  (required)
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Void> emailInvoiceWithHttpInfo(String xeroTenantId, UUID invoiceID, RequestEmpty requestEmpty) throws ApiException {
    Object localVarPostBody = requestEmpty;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling emailInvoice");
    }
    
    // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceID' when calling emailInvoice");
    }
    
    // verify the required parameter 'requestEmpty' is set
    if (requestEmpty == null) {
      throw new ApiException(400, "Missing the required parameter 'requestEmpty' when calling emailInvoice");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices/{InvoiceID}/Email"
      .replaceAll("\\{" + "InvoiceID" + "\\}", apiClient.escapeString(invoiceID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };


    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Allows you to retrieve a single chart of accounts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for retrieving single object (required)
   * @return Accounts
   * @throws ApiException if fails to make API call
   */
  public Accounts getAccount(String xeroTenantId, UUID accountID) throws ApiException {
    return getAccountWithHttpInfo(xeroTenantId, accountID).getData();
      }

  /**
   * Allows you to retrieve a single chart of accounts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for retrieving single object (required)
   * @return ApiResponse&lt;Accounts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Accounts> getAccountWithHttpInfo(String xeroTenantId, UUID accountID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getAccount");
    }
    
    // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new ApiException(400, "Missing the required parameter 'accountID' when calling getAccount");
    }
    
    // create path and map variables
    String localVarPath = "/Accounts/{AccountID}"
      .replaceAll("\\{" + "AccountID" + "\\}", apiClient.escapeString(accountID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Accounts> localVarReturnType = new GenericType<Accounts>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachment on Account by Filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for Account object (required)
   * @param fileName Name of the attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getAccountAttachmentByFileName(String xeroTenantId, UUID accountID, String fileName, String contentType) throws ApiException {
    return getAccountAttachmentByFileNameWithHttpInfo(xeroTenantId, accountID, fileName, contentType).getData();
      }

  /**
   * Allows you to retrieve Attachment on Account by Filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for Account object (required)
   * @param fileName Name of the attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getAccountAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID accountID, String fileName, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getAccountAttachmentByFileName");
    }
    
    // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new ApiException(400, "Missing the required parameter 'accountID' when calling getAccountAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling getAccountAttachmentByFileName");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getAccountAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/Accounts/{AccountID}/Attachments/{FileName}"
      .replaceAll("\\{" + "AccountID" + "\\}", apiClient.escapeString(accountID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve specific Attachment on Account
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for Account object (required)
   * @param attachmentID Unique identifier for Attachment object (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getAccountAttachmentById(String xeroTenantId, UUID accountID, UUID attachmentID, String contentType) throws ApiException {
    return getAccountAttachmentByIdWithHttpInfo(xeroTenantId, accountID, attachmentID, contentType).getData();
      }

  /**
   * Allows you to retrieve specific Attachment on Account
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for Account object (required)
   * @param attachmentID Unique identifier for Attachment object (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getAccountAttachmentByIdWithHttpInfo(String xeroTenantId, UUID accountID, UUID attachmentID, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getAccountAttachmentById");
    }
    
    // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new ApiException(400, "Missing the required parameter 'accountID' when calling getAccountAttachmentById");
    }
    
    // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new ApiException(400, "Missing the required parameter 'attachmentID' when calling getAccountAttachmentById");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getAccountAttachmentById");
    }
    
    // create path and map variables
    String localVarPath = "/Accounts/{AccountID}/Attachments/{AttachmentID}"
      .replaceAll("\\{" + "AccountID" + "\\}", apiClient.escapeString(accountID.toString()))
      .replaceAll("\\{" + "AttachmentID" + "\\}", apiClient.escapeString(attachmentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments for accounts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for Account object (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments getAccountAttachments(String xeroTenantId, UUID accountID) throws ApiException {
    return getAccountAttachmentsWithHttpInfo(xeroTenantId, accountID).getData();
      }

  /**
   * Allows you to retrieve Attachments for accounts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for Account object (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> getAccountAttachmentsWithHttpInfo(String xeroTenantId, UUID accountID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getAccountAttachments");
    }
    
    // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new ApiException(400, "Missing the required parameter 'accountID' when calling getAccountAttachments");
    }
    
    // create path and map variables
    String localVarPath = "/Accounts/{AccountID}/Attachments"
      .replaceAll("\\{" + "AccountID" + "\\}", apiClient.escapeString(accountID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve the full chart of accounts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return Accounts
   * @throws ApiException if fails to make API call
   */
  public Accounts getAccounts(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    return getAccountsWithHttpInfo(xeroTenantId, ifModifiedSince, where, order).getData();
      }

  /**
   * Allows you to retrieve the full chart of accounts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return ApiResponse&lt;Accounts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Accounts> getAccountsWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getAccounts");
    }
    
    // create path and map variables
    String localVarPath = "/Accounts";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "OAuth2" };

    GenericType<Accounts> localVarReturnType = new GenericType<Accounts>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a single spend or receive money transaction
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @return BankTransactions
   * @throws ApiException if fails to make API call
   */
  public BankTransactions getBankTransaction(String xeroTenantId, UUID bankTransactionID) throws ApiException {
    return getBankTransactionWithHttpInfo(xeroTenantId, bankTransactionID).getData();
      }

  /**
   * Allows you to retrieve a single spend or receive money transaction
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @return ApiResponse&lt;BankTransactions&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<BankTransactions> getBankTransactionWithHttpInfo(String xeroTenantId, UUID bankTransactionID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBankTransaction");
    }
    
    // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransactionID' when calling getBankTransaction");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransactions/{BankTransactionID}"
      .replaceAll("\\{" + "BankTransactionID" + "\\}", apiClient.escapeString(bankTransactionID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<BankTransactions> localVarReturnType = new GenericType<BankTransactions>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments on BankTransaction by Filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @param fileName The name of the file being attached (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getBankTransactionAttachmentByFileName(String xeroTenantId, UUID bankTransactionID, String fileName, String contentType) throws ApiException {
    return getBankTransactionAttachmentByFileNameWithHttpInfo(xeroTenantId, bankTransactionID, fileName, contentType).getData();
      }

  /**
   * Allows you to retrieve Attachments on BankTransaction by Filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @param fileName The name of the file being attached (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getBankTransactionAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID bankTransactionID, String fileName, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBankTransactionAttachmentByFileName");
    }
    
    // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransactionID' when calling getBankTransactionAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling getBankTransactionAttachmentByFileName");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getBankTransactionAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}"
      .replaceAll("\\{" + "BankTransactionID" + "\\}", apiClient.escapeString(bankTransactionID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments on a specific BankTransaction
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @param attachmentID Xero generated unique identifier for an attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getBankTransactionAttachmentById(String xeroTenantId, UUID bankTransactionID, UUID attachmentID, String contentType) throws ApiException {
    return getBankTransactionAttachmentByIdWithHttpInfo(xeroTenantId, bankTransactionID, attachmentID, contentType).getData();
      }

  /**
   * Allows you to retrieve Attachments on a specific BankTransaction
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @param attachmentID Xero generated unique identifier for an attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getBankTransactionAttachmentByIdWithHttpInfo(String xeroTenantId, UUID bankTransactionID, UUID attachmentID, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBankTransactionAttachmentById");
    }
    
    // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransactionID' when calling getBankTransactionAttachmentById");
    }
    
    // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new ApiException(400, "Missing the required parameter 'attachmentID' when calling getBankTransactionAttachmentById");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getBankTransactionAttachmentById");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransactions/{BankTransactionID}/Attachments/{AttachmentID}"
      .replaceAll("\\{" + "BankTransactionID" + "\\}", apiClient.escapeString(bankTransactionID.toString()))
      .replaceAll("\\{" + "AttachmentID" + "\\}", apiClient.escapeString(attachmentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve any attachments to bank transactions
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments getBankTransactionAttachments(String xeroTenantId, UUID bankTransactionID) throws ApiException {
    return getBankTransactionAttachmentsWithHttpInfo(xeroTenantId, bankTransactionID).getData();
      }

  /**
   * Allows you to retrieve any attachments to bank transactions
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> getBankTransactionAttachmentsWithHttpInfo(String xeroTenantId, UUID bankTransactionID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBankTransactionAttachments");
    }
    
    // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransactionID' when calling getBankTransactionAttachments");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransactions/{BankTransactionID}/Attachments"
      .replaceAll("\\{" + "BankTransactionID" + "\\}", apiClient.escapeString(bankTransactionID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve any spend or receive money transactions
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param page e.g. page&#x3D;1 – Up to 100 bank transactions will be returned in a single API call with line items shown for each bank transaction (optional)
   * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts (optional)
   * @return BankTransactions
   * @throws ApiException if fails to make API call
   */
  public BankTransactions getBankTransactions(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page, Integer unitdp) throws ApiException {
    return getBankTransactionsWithHttpInfo(xeroTenantId, ifModifiedSince, where, order, page, unitdp).getData();
      }

  /**
   * Allows you to retrieve any spend or receive money transactions
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param page e.g. page&#x3D;1 – Up to 100 bank transactions will be returned in a single API call with line items shown for each bank transaction (optional)
   * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts (optional)
   * @return ApiResponse&lt;BankTransactions&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<BankTransactions> getBankTransactionsWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page, Integer unitdp) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBankTransactions");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransactions";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page", page));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "unitdp", unitdp));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<BankTransactions> localVarReturnType = new GenericType<BankTransactions>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve history from a bank transactions
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getBankTransactionsHistory(String xeroTenantId, UUID bankTransactionID) throws ApiException {
    return getBankTransactionsHistoryWithHttpInfo(xeroTenantId, bankTransactionID).getData();
      }

  /**
   * Allows you to retrieve history from a bank transactions
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getBankTransactionsHistoryWithHttpInfo(String xeroTenantId, UUID bankTransactionID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBankTransactionsHistory");
    }
    
    // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransactionID' when calling getBankTransactionsHistory");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransactions/{BankTransactionID}/History"
      .replaceAll("\\{" + "BankTransactionID" + "\\}", apiClient.escapeString(bankTransactionID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve any bank transfers
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @return BankTransfers
   * @throws ApiException if fails to make API call
   */
  public BankTransfers getBankTransfer(String xeroTenantId, UUID bankTransferID) throws ApiException {
    return getBankTransferWithHttpInfo(xeroTenantId, bankTransferID).getData();
      }

  /**
   * Allows you to retrieve any bank transfers
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @return ApiResponse&lt;BankTransfers&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<BankTransfers> getBankTransferWithHttpInfo(String xeroTenantId, UUID bankTransferID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBankTransfer");
    }
    
    // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransferID' when calling getBankTransfer");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransfers/{BankTransferID}"
      .replaceAll("\\{" + "BankTransferID" + "\\}", apiClient.escapeString(bankTransferID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<BankTransfers> localVarReturnType = new GenericType<BankTransfers>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments on BankTransfer by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @param fileName The name of the file being attached to a Bank Transfer (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getBankTransferAttachmentByFileName(String xeroTenantId, UUID bankTransferID, String fileName, String contentType) throws ApiException {
    return getBankTransferAttachmentByFileNameWithHttpInfo(xeroTenantId, bankTransferID, fileName, contentType).getData();
      }

  /**
   * Allows you to retrieve Attachments on BankTransfer by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @param fileName The name of the file being attached to a Bank Transfer (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getBankTransferAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID bankTransferID, String fileName, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBankTransferAttachmentByFileName");
    }
    
    // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransferID' when calling getBankTransferAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling getBankTransferAttachmentByFileName");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getBankTransferAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransfers/{BankTransferID}/Attachments/{FileName}"
      .replaceAll("\\{" + "BankTransferID" + "\\}", apiClient.escapeString(bankTransferID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments on BankTransfer
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @param attachmentID Xero generated unique identifier for an Attachment to a bank transfer (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getBankTransferAttachmentById(String xeroTenantId, UUID bankTransferID, UUID attachmentID, String contentType) throws ApiException {
    return getBankTransferAttachmentByIdWithHttpInfo(xeroTenantId, bankTransferID, attachmentID, contentType).getData();
      }

  /**
   * Allows you to retrieve Attachments on BankTransfer
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @param attachmentID Xero generated unique identifier for an Attachment to a bank transfer (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getBankTransferAttachmentByIdWithHttpInfo(String xeroTenantId, UUID bankTransferID, UUID attachmentID, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBankTransferAttachmentById");
    }
    
    // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransferID' when calling getBankTransferAttachmentById");
    }
    
    // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new ApiException(400, "Missing the required parameter 'attachmentID' when calling getBankTransferAttachmentById");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getBankTransferAttachmentById");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransfers/{BankTransferID}/Attachments/{AttachmentID}"
      .replaceAll("\\{" + "BankTransferID" + "\\}", apiClient.escapeString(bankTransferID.toString()))
      .replaceAll("\\{" + "AttachmentID" + "\\}", apiClient.escapeString(attachmentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments from  bank transfers
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments getBankTransferAttachments(String xeroTenantId, UUID bankTransferID) throws ApiException {
    return getBankTransferAttachmentsWithHttpInfo(xeroTenantId, bankTransferID).getData();
      }

  /**
   * Allows you to retrieve Attachments from  bank transfers
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> getBankTransferAttachmentsWithHttpInfo(String xeroTenantId, UUID bankTransferID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBankTransferAttachments");
    }
    
    // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransferID' when calling getBankTransferAttachments");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransfers/{BankTransferID}/Attachments"
      .replaceAll("\\{" + "BankTransferID" + "\\}", apiClient.escapeString(bankTransferID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve history from a bank transfers
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getBankTransferHistory(String xeroTenantId, UUID bankTransferID) throws ApiException {
    return getBankTransferHistoryWithHttpInfo(xeroTenantId, bankTransferID).getData();
      }

  /**
   * Allows you to retrieve history from a bank transfers
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getBankTransferHistoryWithHttpInfo(String xeroTenantId, UUID bankTransferID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBankTransferHistory");
    }
    
    // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransferID' when calling getBankTransferHistory");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransfers/{BankTransferID}/History"
      .replaceAll("\\{" + "BankTransferID" + "\\}", apiClient.escapeString(bankTransferID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve all bank transfers
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return BankTransfers
   * @throws ApiException if fails to make API call
   */
  public BankTransfers getBankTransfers(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    return getBankTransfersWithHttpInfo(xeroTenantId, ifModifiedSince, where, order).getData();
      }

  /**
   * Allows you to retrieve all bank transfers
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return ApiResponse&lt;BankTransfers&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<BankTransfers> getBankTransfersWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBankTransfers");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransfers";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<BankTransfers> localVarReturnType = new GenericType<BankTransfers>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve history from a Batch Payment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param batchPaymentID Unique identifier for BatchPayment (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getBatchPaymentHistory(String xeroTenantId, UUID batchPaymentID) throws ApiException {
    return getBatchPaymentHistoryWithHttpInfo(xeroTenantId, batchPaymentID).getData();
      }

  /**
   * Allows you to retrieve history from a Batch Payment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param batchPaymentID Unique identifier for BatchPayment (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getBatchPaymentHistoryWithHttpInfo(String xeroTenantId, UUID batchPaymentID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBatchPaymentHistory");
    }
    
    // verify the required parameter 'batchPaymentID' is set
    if (batchPaymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'batchPaymentID' when calling getBatchPaymentHistory");
    }
    
    // create path and map variables
    String localVarPath = "/BatchPayments/{BatchPaymentID}/History"
      .replaceAll("\\{" + "BatchPaymentID" + "\\}", apiClient.escapeString(batchPaymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Retrieve either one or many BatchPayments for invoices
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return BatchPayments
   * @throws ApiException if fails to make API call
   */
  public BatchPayments getBatchPayments(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    return getBatchPaymentsWithHttpInfo(xeroTenantId, ifModifiedSince, where, order).getData();
      }

  /**
   * Retrieve either one or many BatchPayments for invoices
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return ApiResponse&lt;BatchPayments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<BatchPayments> getBatchPaymentsWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBatchPayments");
    }
    
    // create path and map variables
    String localVarPath = "/BatchPayments";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<BatchPayments> localVarReturnType = new GenericType<BatchPayments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specific BrandingThemes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param brandingThemeID Unique identifier for a Branding Theme (required)
   * @return BrandingThemes
   * @throws ApiException if fails to make API call
   */
  public BrandingThemes getBrandingTheme(String xeroTenantId, UUID brandingThemeID) throws ApiException {
    return getBrandingThemeWithHttpInfo(xeroTenantId, brandingThemeID).getData();
      }

  /**
   * Allows you to retrieve a specific BrandingThemes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param brandingThemeID Unique identifier for a Branding Theme (required)
   * @return ApiResponse&lt;BrandingThemes&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<BrandingThemes> getBrandingThemeWithHttpInfo(String xeroTenantId, UUID brandingThemeID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBrandingTheme");
    }
    
    // verify the required parameter 'brandingThemeID' is set
    if (brandingThemeID == null) {
      throw new ApiException(400, "Missing the required parameter 'brandingThemeID' when calling getBrandingTheme");
    }
    
    // create path and map variables
    String localVarPath = "/BrandingThemes/{BrandingThemeID}"
      .replaceAll("\\{" + "BrandingThemeID" + "\\}", apiClient.escapeString(brandingThemeID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<BrandingThemes> localVarReturnType = new GenericType<BrandingThemes>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve the Payment services for a Branding Theme
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param brandingThemeID Unique identifier for a Branding Theme (required)
   * @return PaymentServices
   * @throws ApiException if fails to make API call
   */
  public PaymentServices getBrandingThemePaymentServices(String xeroTenantId, UUID brandingThemeID) throws ApiException {
    return getBrandingThemePaymentServicesWithHttpInfo(xeroTenantId, brandingThemeID).getData();
      }

  /**
   * Allows you to retrieve the Payment services for a Branding Theme
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param brandingThemeID Unique identifier for a Branding Theme (required)
   * @return ApiResponse&lt;PaymentServices&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<PaymentServices> getBrandingThemePaymentServicesWithHttpInfo(String xeroTenantId, UUID brandingThemeID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBrandingThemePaymentServices");
    }
    
    // verify the required parameter 'brandingThemeID' is set
    if (brandingThemeID == null) {
      throw new ApiException(400, "Missing the required parameter 'brandingThemeID' when calling getBrandingThemePaymentServices");
    }
    
    // create path and map variables
    String localVarPath = "/BrandingThemes/{BrandingThemeID}/PaymentServices"
      .replaceAll("\\{" + "BrandingThemeID" + "\\}", apiClient.escapeString(brandingThemeID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<PaymentServices> localVarReturnType = new GenericType<PaymentServices>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve all the BrandingThemes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @return BrandingThemes
   * @throws ApiException if fails to make API call
   */
  public BrandingThemes getBrandingThemes(String xeroTenantId) throws ApiException {
    return getBrandingThemesWithHttpInfo(xeroTenantId).getData();
      }

  /**
   * Allows you to retrieve all the BrandingThemes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @return ApiResponse&lt;BrandingThemes&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<BrandingThemes> getBrandingThemesWithHttpInfo(String xeroTenantId) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getBrandingThemes");
    }
    
    // create path and map variables
    String localVarPath = "/BrandingThemes";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<BrandingThemes> localVarReturnType = new GenericType<BrandingThemes>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve, add and update contacts in a Xero organisation
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @return Contacts
   * @throws ApiException if fails to make API call
   */
  public Contacts getContact(String xeroTenantId, UUID contactID) throws ApiException {
    return getContactWithHttpInfo(xeroTenantId, contactID).getData();
      }

  /**
   * Allows you to retrieve, add and update contacts in a Xero organisation
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @return ApiResponse&lt;Contacts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Contacts> getContactWithHttpInfo(String xeroTenantId, UUID contactID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getContact");
    }
    
    // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactID' when calling getContact");
    }
    
    // create path and map variables
    String localVarPath = "/Contacts/{ContactID}"
      .replaceAll("\\{" + "ContactID" + "\\}", apiClient.escapeString(contactID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Contacts> localVarReturnType = new GenericType<Contacts>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments on Contacts by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @param fileName Name for the file you are attaching (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getContactAttachmentByFileName(String xeroTenantId, UUID contactID, String fileName, String contentType) throws ApiException {
    return getContactAttachmentByFileNameWithHttpInfo(xeroTenantId, contactID, fileName, contentType).getData();
      }

  /**
   * Allows you to retrieve Attachments on Contacts by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @param fileName Name for the file you are attaching (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getContactAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID contactID, String fileName, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getContactAttachmentByFileName");
    }
    
    // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactID' when calling getContactAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling getContactAttachmentByFileName");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getContactAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/Contacts/{ContactID}/Attachments/{FileName}"
      .replaceAll("\\{" + "ContactID" + "\\}", apiClient.escapeString(contactID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments on Contacts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @param attachmentID Unique identifier for a Attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getContactAttachmentById(String xeroTenantId, UUID contactID, UUID attachmentID, String contentType) throws ApiException {
    return getContactAttachmentByIdWithHttpInfo(xeroTenantId, contactID, attachmentID, contentType).getData();
      }

  /**
   * Allows you to retrieve Attachments on Contacts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @param attachmentID Unique identifier for a Attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getContactAttachmentByIdWithHttpInfo(String xeroTenantId, UUID contactID, UUID attachmentID, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getContactAttachmentById");
    }
    
    // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactID' when calling getContactAttachmentById");
    }
    
    // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new ApiException(400, "Missing the required parameter 'attachmentID' when calling getContactAttachmentById");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getContactAttachmentById");
    }
    
    // create path and map variables
    String localVarPath = "/Contacts/{ContactID}/Attachments/{AttachmentID}"
      .replaceAll("\\{" + "ContactID" + "\\}", apiClient.escapeString(contactID.toString()))
      .replaceAll("\\{" + "AttachmentID" + "\\}", apiClient.escapeString(attachmentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve, add and update contacts in a Xero organisation
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments getContactAttachments(String xeroTenantId, UUID contactID) throws ApiException {
    return getContactAttachmentsWithHttpInfo(xeroTenantId, contactID).getData();
      }

  /**
   * Allows you to retrieve, add and update contacts in a Xero organisation
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> getContactAttachmentsWithHttpInfo(String xeroTenantId, UUID contactID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getContactAttachments");
    }
    
    // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactID' when calling getContactAttachments");
    }
    
    // create path and map variables
    String localVarPath = "/Contacts/{ContactID}/Attachments"
      .replaceAll("\\{" + "ContactID" + "\\}", apiClient.escapeString(contactID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve CISSettings for a contact in a Xero organisation
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @return CISSettings
   * @throws ApiException if fails to make API call
   */
  public CISSettings getContactCISSettings(String xeroTenantId, UUID contactID) throws ApiException {
    return getContactCISSettingsWithHttpInfo(xeroTenantId, contactID).getData();
      }

  /**
   * Allows you to retrieve CISSettings for a contact in a Xero organisation
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @return ApiResponse&lt;CISSettings&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<CISSettings> getContactCISSettingsWithHttpInfo(String xeroTenantId, UUID contactID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getContactCISSettings");
    }
    
    // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactID' when calling getContactCISSettings");
    }
    
    // create path and map variables
    String localVarPath = "/Contacts/{ContactID}/CISSettings"
      .replaceAll("\\{" + "ContactID" + "\\}", apiClient.escapeString(contactID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<CISSettings> localVarReturnType = new GenericType<CISSettings>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a unique Contract Group by ID
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactGroupID Unique identifier for a Contact Group (required)
   * @return ContactGroups
   * @throws ApiException if fails to make API call
   */
  public ContactGroups getContactGroup(String xeroTenantId, UUID contactGroupID) throws ApiException {
    return getContactGroupWithHttpInfo(xeroTenantId, contactGroupID).getData();
      }

  /**
   * Allows you to retrieve a unique Contract Group by ID
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactGroupID Unique identifier for a Contact Group (required)
   * @return ApiResponse&lt;ContactGroups&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ContactGroups> getContactGroupWithHttpInfo(String xeroTenantId, UUID contactGroupID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getContactGroup");
    }
    
    // verify the required parameter 'contactGroupID' is set
    if (contactGroupID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactGroupID' when calling getContactGroup");
    }
    
    // create path and map variables
    String localVarPath = "/ContactGroups/{ContactGroupID}"
      .replaceAll("\\{" + "ContactGroupID" + "\\}", apiClient.escapeString(contactGroupID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ContactGroups> localVarReturnType = new GenericType<ContactGroups>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve the ContactID and Name of all the contacts in a contact group
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return ContactGroups
   * @throws ApiException if fails to make API call
   */
  public ContactGroups getContactGroups(String xeroTenantId, String where, String order) throws ApiException {
    return getContactGroupsWithHttpInfo(xeroTenantId, where, order).getData();
      }

  /**
   * Allows you to retrieve the ContactID and Name of all the contacts in a contact group
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return ApiResponse&lt;ContactGroups&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ContactGroups> getContactGroupsWithHttpInfo(String xeroTenantId, String where, String order) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getContactGroups");
    }
    
    // create path and map variables
    String localVarPath = "/ContactGroups";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ContactGroups> localVarReturnType = new GenericType<ContactGroups>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a history records of an Contact
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getContactHistory(String xeroTenantId, UUID contactID) throws ApiException {
    return getContactHistoryWithHttpInfo(xeroTenantId, contactID).getData();
      }

  /**
   * Allows you to retrieve a history records of an Contact
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getContactHistoryWithHttpInfo(String xeroTenantId, UUID contactID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getContactHistory");
    }
    
    // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactID' when calling getContactHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Contacts/{ContactID}/History"
      .replaceAll("\\{" + "ContactID" + "\\}", apiClient.escapeString(contactID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve, add and update contacts in a Xero organisation
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param ids Filter by a comma separated list of ContactIDs. Allows you to retrieve a specific set of contacts in a single call. (optional)
   * @param page e.g. page&#x3D;1 - Up to 100 contacts will be returned in a single API call. (optional)
   * @param includeArchived e.g. includeArchived&#x3D;true - Contacts with a status of ARCHIVED will be included in the response (optional)
   * @return Contacts
   * @throws ApiException if fails to make API call
   */
  public Contacts getContacts(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, String ids, Integer page, Boolean includeArchived) throws ApiException {
    return getContactsWithHttpInfo(xeroTenantId, ifModifiedSince, where, order, ids, page, includeArchived).getData();
      }

  /**
   * Allows you to retrieve, add and update contacts in a Xero organisation
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param ids Filter by a comma separated list of ContactIDs. Allows you to retrieve a specific set of contacts in a single call. (optional)
   * @param page e.g. page&#x3D;1 - Up to 100 contacts will be returned in a single API call. (optional)
   * @param includeArchived e.g. includeArchived&#x3D;true - Contacts with a status of ARCHIVED will be included in the response (optional)
   * @return ApiResponse&lt;Contacts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Contacts> getContactsWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, String ids, Integer page, Boolean includeArchived) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getContacts");
    }
    
    // create path and map variables
    String localVarPath = "/Contacts";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "IDs", ids));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page", page));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "includeArchived", includeArchived));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Contacts> localVarReturnType = new GenericType<Contacts>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specific credit note
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @return CreditNotes
   * @throws ApiException if fails to make API call
   */
  public CreditNotes getCreditNote(String xeroTenantId, UUID creditNoteID) throws ApiException {
    return getCreditNoteWithHttpInfo(xeroTenantId, creditNoteID).getData();
      }

  /**
   * Allows you to retrieve a specific credit note
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @return ApiResponse&lt;CreditNotes&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<CreditNotes> getCreditNoteWithHttpInfo(String xeroTenantId, UUID creditNoteID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getCreditNote");
    }
    
    // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNoteID' when calling getCreditNote");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes/{CreditNoteID}"
      .replaceAll("\\{" + "CreditNoteID" + "\\}", apiClient.escapeString(creditNoteID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<CreditNotes> localVarReturnType = new GenericType<CreditNotes>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Credit Note as PDF files
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getCreditNoteAsPdf(String xeroTenantId, UUID creditNoteID, String contentType) throws ApiException {
    return getCreditNoteAsPdfWithHttpInfo(xeroTenantId, creditNoteID, contentType).getData();
      }

  /**
   * Allows you to retrieve Credit Note as PDF files
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getCreditNoteAsPdfWithHttpInfo(String xeroTenantId, UUID creditNoteID, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getCreditNoteAsPdf");
    }
    
    // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNoteID' when calling getCreditNoteAsPdf");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getCreditNoteAsPdf");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes/{CreditNoteID}/pdf"
      .replaceAll("\\{" + "CreditNoteID" + "\\}", apiClient.escapeString(creditNoteID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments on CreditNote by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param fileName Name of the file you are attaching to Credit Note (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getCreditNoteAttachmentByFileName(String xeroTenantId, UUID creditNoteID, String fileName, String contentType) throws ApiException {
    return getCreditNoteAttachmentByFileNameWithHttpInfo(xeroTenantId, creditNoteID, fileName, contentType).getData();
      }

  /**
   * Allows you to retrieve Attachments on CreditNote by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param fileName Name of the file you are attaching to Credit Note (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getCreditNoteAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID creditNoteID, String fileName, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getCreditNoteAttachmentByFileName");
    }
    
    // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNoteID' when calling getCreditNoteAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling getCreditNoteAttachmentByFileName");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getCreditNoteAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}"
      .replaceAll("\\{" + "CreditNoteID" + "\\}", apiClient.escapeString(creditNoteID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments on CreditNote
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param attachmentID Unique identifier for a Attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getCreditNoteAttachmentById(String xeroTenantId, UUID creditNoteID, UUID attachmentID, String contentType) throws ApiException {
    return getCreditNoteAttachmentByIdWithHttpInfo(xeroTenantId, creditNoteID, attachmentID, contentType).getData();
      }

  /**
   * Allows you to retrieve Attachments on CreditNote
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param attachmentID Unique identifier for a Attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getCreditNoteAttachmentByIdWithHttpInfo(String xeroTenantId, UUID creditNoteID, UUID attachmentID, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getCreditNoteAttachmentById");
    }
    
    // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNoteID' when calling getCreditNoteAttachmentById");
    }
    
    // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new ApiException(400, "Missing the required parameter 'attachmentID' when calling getCreditNoteAttachmentById");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getCreditNoteAttachmentById");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes/{CreditNoteID}/Attachments/{AttachmentID}"
      .replaceAll("\\{" + "CreditNoteID" + "\\}", apiClient.escapeString(creditNoteID.toString()))
      .replaceAll("\\{" + "AttachmentID" + "\\}", apiClient.escapeString(attachmentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments for credit notes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments getCreditNoteAttachments(String xeroTenantId, UUID creditNoteID) throws ApiException {
    return getCreditNoteAttachmentsWithHttpInfo(xeroTenantId, creditNoteID).getData();
      }

  /**
   * Allows you to retrieve Attachments for credit notes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> getCreditNoteAttachmentsWithHttpInfo(String xeroTenantId, UUID creditNoteID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getCreditNoteAttachments");
    }
    
    // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNoteID' when calling getCreditNoteAttachments");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes/{CreditNoteID}/Attachments"
      .replaceAll("\\{" + "CreditNoteID" + "\\}", apiClient.escapeString(creditNoteID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a history records of an CreditNote
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getCreditNoteHistory(String xeroTenantId, UUID creditNoteID) throws ApiException {
    return getCreditNoteHistoryWithHttpInfo(xeroTenantId, creditNoteID).getData();
      }

  /**
   * Allows you to retrieve a history records of an CreditNote
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getCreditNoteHistoryWithHttpInfo(String xeroTenantId, UUID creditNoteID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getCreditNoteHistory");
    }
    
    // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNoteID' when calling getCreditNoteHistory");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes/{CreditNoteID}/History"
      .replaceAll("\\{" + "CreditNoteID" + "\\}", apiClient.escapeString(creditNoteID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve any credit notes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param page e.g. page&#x3D;1 – Up to 100 credit notes will be returned in a single API call with line items shown for each credit note (optional)
   * @return CreditNotes
   * @throws ApiException if fails to make API call
   */
  public CreditNotes getCreditNotes(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws ApiException {
    return getCreditNotesWithHttpInfo(xeroTenantId, ifModifiedSince, where, order, page).getData();
      }

  /**
   * Allows you to retrieve any credit notes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param page e.g. page&#x3D;1 – Up to 100 credit notes will be returned in a single API call with line items shown for each credit note (optional)
   * @return ApiResponse&lt;CreditNotes&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<CreditNotes> getCreditNotesWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getCreditNotes");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page", page));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<CreditNotes> localVarReturnType = new GenericType<CreditNotes>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve currencies for your organisation
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return Currencies
   * @throws ApiException if fails to make API call
   */
  public Currencies getCurrencies(String xeroTenantId, String where, String order) throws ApiException {
    return getCurrenciesWithHttpInfo(xeroTenantId, where, order).getData();
      }

  /**
   * Allows you to retrieve currencies for your organisation
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return ApiResponse&lt;Currencies&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Currencies> getCurrenciesWithHttpInfo(String xeroTenantId, String where, String order) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getCurrencies");
    }
    
    // create path and map variables
    String localVarPath = "/Currencies";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Currencies> localVarReturnType = new GenericType<Currencies>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specific employee used in Xero payrun
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param employeeID Unique identifier for a Employee (required)
   * @return Employees
   * @throws ApiException if fails to make API call
   */
  public Employees getEmployee(String xeroTenantId, UUID employeeID) throws ApiException {
    return getEmployeeWithHttpInfo(xeroTenantId, employeeID).getData();
      }

  /**
   * Allows you to retrieve a specific employee used in Xero payrun
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param employeeID Unique identifier for a Employee (required)
   * @return ApiResponse&lt;Employees&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Employees> getEmployeeWithHttpInfo(String xeroTenantId, UUID employeeID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getEmployee");
    }
    
    // verify the required parameter 'employeeID' is set
    if (employeeID == null) {
      throw new ApiException(400, "Missing the required parameter 'employeeID' when calling getEmployee");
    }
    
    // create path and map variables
    String localVarPath = "/Employees/{EmployeeID}"
      .replaceAll("\\{" + "EmployeeID" + "\\}", apiClient.escapeString(employeeID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Employees> localVarReturnType = new GenericType<Employees>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve employees used in Xero payrun
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return Employees
   * @throws ApiException if fails to make API call
   */
  public Employees getEmployees(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    return getEmployeesWithHttpInfo(xeroTenantId, ifModifiedSince, where, order).getData();
      }

  /**
   * Allows you to retrieve employees used in Xero payrun
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return ApiResponse&lt;Employees&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Employees> getEmployeesWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getEmployees");
    }
    
    // create path and map variables
    String localVarPath = "/Employees";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Employees> localVarReturnType = new GenericType<Employees>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified expense claim
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param expenseClaimID Unique identifier for a ExpenseClaim (required)
   * @return ExpenseClaims
   * @throws ApiException if fails to make API call
   */
  public ExpenseClaims getExpenseClaim(String xeroTenantId, UUID expenseClaimID) throws ApiException {
    return getExpenseClaimWithHttpInfo(xeroTenantId, expenseClaimID).getData();
      }

  /**
   * Allows you to retrieve a specified expense claim
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param expenseClaimID Unique identifier for a ExpenseClaim (required)
   * @return ApiResponse&lt;ExpenseClaims&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ExpenseClaims> getExpenseClaimWithHttpInfo(String xeroTenantId, UUID expenseClaimID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getExpenseClaim");
    }
    
    // verify the required parameter 'expenseClaimID' is set
    if (expenseClaimID == null) {
      throw new ApiException(400, "Missing the required parameter 'expenseClaimID' when calling getExpenseClaim");
    }
    
    // create path and map variables
    String localVarPath = "/ExpenseClaims/{ExpenseClaimID}"
      .replaceAll("\\{" + "ExpenseClaimID" + "\\}", apiClient.escapeString(expenseClaimID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ExpenseClaims> localVarReturnType = new GenericType<ExpenseClaims>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a history records of an ExpenseClaim
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param expenseClaimID Unique identifier for a ExpenseClaim (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getExpenseClaimHistory(String xeroTenantId, UUID expenseClaimID) throws ApiException {
    return getExpenseClaimHistoryWithHttpInfo(xeroTenantId, expenseClaimID).getData();
      }

  /**
   * Allows you to retrieve a history records of an ExpenseClaim
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param expenseClaimID Unique identifier for a ExpenseClaim (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getExpenseClaimHistoryWithHttpInfo(String xeroTenantId, UUID expenseClaimID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getExpenseClaimHistory");
    }
    
    // verify the required parameter 'expenseClaimID' is set
    if (expenseClaimID == null) {
      throw new ApiException(400, "Missing the required parameter 'expenseClaimID' when calling getExpenseClaimHistory");
    }
    
    // create path and map variables
    String localVarPath = "/ExpenseClaims/{ExpenseClaimID}/History"
      .replaceAll("\\{" + "ExpenseClaimID" + "\\}", apiClient.escapeString(expenseClaimID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve expense claims
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return ExpenseClaims
   * @throws ApiException if fails to make API call
   */
  public ExpenseClaims getExpenseClaims(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    return getExpenseClaimsWithHttpInfo(xeroTenantId, ifModifiedSince, where, order).getData();
      }

  /**
   * Allows you to retrieve expense claims
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return ApiResponse&lt;ExpenseClaims&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ExpenseClaims> getExpenseClaimsWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getExpenseClaims");
    }
    
    // create path and map variables
    String localVarPath = "/ExpenseClaims";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ExpenseClaims> localVarReturnType = new GenericType<ExpenseClaims>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified sales invoice or purchase bill
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @return Invoices
   * @throws ApiException if fails to make API call
   */
  public Invoices getInvoice(String xeroTenantId, UUID invoiceID) throws ApiException {
    return getInvoiceWithHttpInfo(xeroTenantId, invoiceID).getData();
      }

  /**
   * Allows you to retrieve a specified sales invoice or purchase bill
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @return ApiResponse&lt;Invoices&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Invoices> getInvoiceWithHttpInfo(String xeroTenantId, UUID invoiceID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getInvoice");
    }
    
    // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceID' when calling getInvoice");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices/{InvoiceID}"
      .replaceAll("\\{" + "InvoiceID" + "\\}", apiClient.escapeString(invoiceID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Invoices> localVarReturnType = new GenericType<Invoices>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve invoices or purchase bills as PDF files
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getInvoiceAsPdf(String xeroTenantId, UUID invoiceID, String contentType) throws ApiException {
    return getInvoiceAsPdfWithHttpInfo(xeroTenantId, invoiceID, contentType).getData();
      }

  /**
   * Allows you to retrieve invoices or purchase bills as PDF files
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getInvoiceAsPdfWithHttpInfo(String xeroTenantId, UUID invoiceID, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getInvoiceAsPdf");
    }
    
    // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceID' when calling getInvoiceAsPdf");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getInvoiceAsPdf");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices/{InvoiceID}/pdf"
      .replaceAll("\\{" + "InvoiceID" + "\\}", apiClient.escapeString(invoiceID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachment on invoices or purchase bills by it&#39;s filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param fileName Name of the file you are attaching (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getInvoiceAttachmentByFileName(String xeroTenantId, UUID invoiceID, String fileName, String contentType) throws ApiException {
    return getInvoiceAttachmentByFileNameWithHttpInfo(xeroTenantId, invoiceID, fileName, contentType).getData();
      }

  /**
   * Allows you to retrieve Attachment on invoices or purchase bills by it&#39;s filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param fileName Name of the file you are attaching (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getInvoiceAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID invoiceID, String fileName, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceID' when calling getInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling getInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getInvoiceAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices/{InvoiceID}/Attachments/{FileName}"
      .replaceAll("\\{" + "InvoiceID" + "\\}", apiClient.escapeString(invoiceID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified Attachment on invoices or purchase bills by it&#39;s ID
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param attachmentID Unique identifier for an Attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getInvoiceAttachmentById(String xeroTenantId, UUID invoiceID, UUID attachmentID, String contentType) throws ApiException {
    return getInvoiceAttachmentByIdWithHttpInfo(xeroTenantId, invoiceID, attachmentID, contentType).getData();
      }

  /**
   * Allows you to retrieve a specified Attachment on invoices or purchase bills by it&#39;s ID
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param attachmentID Unique identifier for an Attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getInvoiceAttachmentByIdWithHttpInfo(String xeroTenantId, UUID invoiceID, UUID attachmentID, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getInvoiceAttachmentById");
    }
    
    // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceID' when calling getInvoiceAttachmentById");
    }
    
    // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new ApiException(400, "Missing the required parameter 'attachmentID' when calling getInvoiceAttachmentById");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getInvoiceAttachmentById");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices/{InvoiceID}/Attachments/{AttachmentID}"
      .replaceAll("\\{" + "InvoiceID" + "\\}", apiClient.escapeString(invoiceID.toString()))
      .replaceAll("\\{" + "AttachmentID" + "\\}", apiClient.escapeString(attachmentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments on invoices or purchase bills
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments getInvoiceAttachments(String xeroTenantId, UUID invoiceID) throws ApiException {
    return getInvoiceAttachmentsWithHttpInfo(xeroTenantId, invoiceID).getData();
      }

  /**
   * Allows you to retrieve Attachments on invoices or purchase bills
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> getInvoiceAttachmentsWithHttpInfo(String xeroTenantId, UUID invoiceID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getInvoiceAttachments");
    }
    
    // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceID' when calling getInvoiceAttachments");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices/{InvoiceID}/Attachments"
      .replaceAll("\\{" + "InvoiceID" + "\\}", apiClient.escapeString(invoiceID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a history records of an invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getInvoiceHistory(String xeroTenantId, UUID invoiceID) throws ApiException {
    return getInvoiceHistoryWithHttpInfo(xeroTenantId, invoiceID).getData();
      }

  /**
   * Allows you to retrieve a history records of an invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getInvoiceHistoryWithHttpInfo(String xeroTenantId, UUID invoiceID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getInvoiceHistory");
    }
    
    // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceID' when calling getInvoiceHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices/{InvoiceID}/History"
      .replaceAll("\\{" + "InvoiceID" + "\\}", apiClient.escapeString(invoiceID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve invoice reminder settings
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @return InvoiceReminders
   * @throws ApiException if fails to make API call
   */
  public InvoiceReminders getInvoiceReminders(String xeroTenantId) throws ApiException {
    return getInvoiceRemindersWithHttpInfo(xeroTenantId).getData();
      }

  /**
   * Allows you to retrieve invoice reminder settings
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @return ApiResponse&lt;InvoiceReminders&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<InvoiceReminders> getInvoiceRemindersWithHttpInfo(String xeroTenantId) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getInvoiceReminders");
    }
    
    // create path and map variables
    String localVarPath = "/InvoiceReminders/Settings";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<InvoiceReminders> localVarReturnType = new GenericType<InvoiceReminders>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve any sales invoices or purchase bills
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param ids Filter by a comma-separated list of InvoicesIDs. For faster response times we recommend using these explicit parameters instead of passing OR conditions into the Where filter. (optional)
   * @param invoiceNumbers Filter by a comma-separated list of InvoiceNumbers. For faster response times we recommend using these explicit parameters instead of passing OR conditions into the Where filter. (optional)
   * @param contactIDs Filter by a comma-separated list of ContactIDs. For faster response times we recommend using these explicit parameters instead of passing OR conditions into the Where filter. (optional)
   * @param statuses Filter by a comma-separated list Statuses. For faster response times we recommend using these explicit parameters instead of passing OR conditions into the Where filter. (optional)
   * @param page e.g. page&#x3D;1 – Up to 100 invoices will be returned in a single API call with line items shown for each invoice (optional)
   * @param includeArchived e.g. includeArchived&#x3D;true - Contacts with a status of ARCHIVED will be included in the response (optional)
   * @param createdByMyApp When set to true you&#39;ll only retrieve Invoices created by your app (optional)
   * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts (optional)
   * @return Invoices
   * @throws ApiException if fails to make API call
   */
  public Invoices getInvoices(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, String ids, String invoiceNumbers, String contactIDs, String statuses, Integer page, Boolean includeArchived, Boolean createdByMyApp, Integer unitdp) throws ApiException {
    return getInvoicesWithHttpInfo(xeroTenantId, ifModifiedSince, where, order, ids, invoiceNumbers, contactIDs, statuses, page, includeArchived, createdByMyApp, unitdp).getData();
      }

  /**
   * Allows you to retrieve any sales invoices or purchase bills
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param ids Filter by a comma-separated list of InvoicesIDs. For faster response times we recommend using these explicit parameters instead of passing OR conditions into the Where filter. (optional)
   * @param invoiceNumbers Filter by a comma-separated list of InvoiceNumbers. For faster response times we recommend using these explicit parameters instead of passing OR conditions into the Where filter. (optional)
   * @param contactIDs Filter by a comma-separated list of ContactIDs. For faster response times we recommend using these explicit parameters instead of passing OR conditions into the Where filter. (optional)
   * @param statuses Filter by a comma-separated list Statuses. For faster response times we recommend using these explicit parameters instead of passing OR conditions into the Where filter. (optional)
   * @param page e.g. page&#x3D;1 – Up to 100 invoices will be returned in a single API call with line items shown for each invoice (optional)
   * @param includeArchived e.g. includeArchived&#x3D;true - Contacts with a status of ARCHIVED will be included in the response (optional)
   * @param createdByMyApp When set to true you&#39;ll only retrieve Invoices created by your app (optional)
   * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts (optional)
   * @return ApiResponse&lt;Invoices&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Invoices> getInvoicesWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, String ids, String invoiceNumbers, String contactIDs, String statuses, Integer page, Boolean includeArchived, Boolean createdByMyApp, Integer unitdp) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getInvoices");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "IDs", ids));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "InvoiceNumbers", invoiceNumbers));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "ContactIDs", contactIDs));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "Statuses", statuses));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page", page));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "includeArchived", includeArchived));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "createdByMyApp", createdByMyApp));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "unitdp", unitdp));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Invoices> localVarReturnType = new GenericType<Invoices>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified item
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param itemID Unique identifier for an Item (required)
   * @return Items
   * @throws ApiException if fails to make API call
   */
  public Items getItem(String xeroTenantId, UUID itemID) throws ApiException {
    return getItemWithHttpInfo(xeroTenantId, itemID).getData();
      }

  /**
   * Allows you to retrieve a specified item
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param itemID Unique identifier for an Item (required)
   * @return ApiResponse&lt;Items&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Items> getItemWithHttpInfo(String xeroTenantId, UUID itemID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getItem");
    }
    
    // verify the required parameter 'itemID' is set
    if (itemID == null) {
      throw new ApiException(400, "Missing the required parameter 'itemID' when calling getItem");
    }
    
    // create path and map variables
    String localVarPath = "/Items/{ItemID}"
      .replaceAll("\\{" + "ItemID" + "\\}", apiClient.escapeString(itemID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Items> localVarReturnType = new GenericType<Items>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve history for items
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param itemID Unique identifier for an Item (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getItemHistory(String xeroTenantId, UUID itemID) throws ApiException {
    return getItemHistoryWithHttpInfo(xeroTenantId, itemID).getData();
      }

  /**
   * Allows you to retrieve history for items
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param itemID Unique identifier for an Item (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getItemHistoryWithHttpInfo(String xeroTenantId, UUID itemID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getItemHistory");
    }
    
    // verify the required parameter 'itemID' is set
    if (itemID == null) {
      throw new ApiException(400, "Missing the required parameter 'itemID' when calling getItemHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Items/{ItemID}/History"
      .replaceAll("\\{" + "ItemID" + "\\}", apiClient.escapeString(itemID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve any items
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts (optional)
   * @return Items
   * @throws ApiException if fails to make API call
   */
  public Items getItems(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer unitdp) throws ApiException {
    return getItemsWithHttpInfo(xeroTenantId, ifModifiedSince, where, order, unitdp).getData();
      }

  /**
   * Allows you to retrieve any items
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts (optional)
   * @return ApiResponse&lt;Items&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Items> getItemsWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer unitdp) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getItems");
    }
    
    // create path and map variables
    String localVarPath = "/Items";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "unitdp", unitdp));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Items> localVarReturnType = new GenericType<Items>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified journals.
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param journalID Unique identifier for a Journal (required)
   * @return Journals
   * @throws ApiException if fails to make API call
   */
  public Journals getJournal(String xeroTenantId, UUID journalID) throws ApiException {
    return getJournalWithHttpInfo(xeroTenantId, journalID).getData();
      }

  /**
   * Allows you to retrieve a specified journals.
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param journalID Unique identifier for a Journal (required)
   * @return ApiResponse&lt;Journals&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Journals> getJournalWithHttpInfo(String xeroTenantId, UUID journalID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getJournal");
    }
    
    // verify the required parameter 'journalID' is set
    if (journalID == null) {
      throw new ApiException(400, "Missing the required parameter 'journalID' when calling getJournal");
    }
    
    // create path and map variables
    String localVarPath = "/Journals/{JournalID}"
      .replaceAll("\\{" + "JournalID" + "\\}", apiClient.escapeString(journalID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Journals> localVarReturnType = new GenericType<Journals>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve any journals.
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param offset Offset by a specified journal number. e.g. journals with a JournalNumber greater than the offset will be returned (optional)
   * @param paymentsOnly Filter to retrieve journals on a cash basis. Journals are returned on an accrual basis by default. (optional)
   * @return Journals
   * @throws ApiException if fails to make API call
   */
  public Journals getJournals(String xeroTenantId, OffsetDateTime ifModifiedSince, Integer offset, Boolean paymentsOnly) throws ApiException {
    return getJournalsWithHttpInfo(xeroTenantId, ifModifiedSince, offset, paymentsOnly).getData();
      }

  /**
   * Allows you to retrieve any journals.
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param offset Offset by a specified journal number. e.g. journals with a JournalNumber greater than the offset will be returned (optional)
   * @param paymentsOnly Filter to retrieve journals on a cash basis. Journals are returned on an accrual basis by default. (optional)
   * @return ApiResponse&lt;Journals&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Journals> getJournalsWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, Integer offset, Boolean paymentsOnly) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getJournals");
    }
    
    // create path and map variables
    String localVarPath = "/Journals";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "offset", offset));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "paymentsOnly", paymentsOnly));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Journals> localVarReturnType = new GenericType<Journals>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified linked transactions (billable expenses)
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param linkedTransactionID Unique identifier for a LinkedTransaction (required)
   * @return LinkedTransactions
   * @throws ApiException if fails to make API call
   */
  public LinkedTransactions getLinkedTransaction(String xeroTenantId, UUID linkedTransactionID) throws ApiException {
    return getLinkedTransactionWithHttpInfo(xeroTenantId, linkedTransactionID).getData();
      }

  /**
   * Allows you to retrieve a specified linked transactions (billable expenses)
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param linkedTransactionID Unique identifier for a LinkedTransaction (required)
   * @return ApiResponse&lt;LinkedTransactions&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<LinkedTransactions> getLinkedTransactionWithHttpInfo(String xeroTenantId, UUID linkedTransactionID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getLinkedTransaction");
    }
    
    // verify the required parameter 'linkedTransactionID' is set
    if (linkedTransactionID == null) {
      throw new ApiException(400, "Missing the required parameter 'linkedTransactionID' when calling getLinkedTransaction");
    }
    
    // create path and map variables
    String localVarPath = "/LinkedTransactions/{LinkedTransactionID}"
      .replaceAll("\\{" + "LinkedTransactionID" + "\\}", apiClient.escapeString(linkedTransactionID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<LinkedTransactions> localVarReturnType = new GenericType<LinkedTransactions>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Retrieve linked transactions (billable expenses)
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param page Up to 100 linked transactions will be returned in a single API call. Use the page parameter to specify the page to be returned e.g. page&#x3D;1. (optional)
   * @param linkedTransactionID The Xero identifier for an Linked Transaction (optional)
   * @param sourceTransactionID Filter by the SourceTransactionID. Get all the linked transactions created from a particular ACCPAY invoice (optional)
   * @param contactID Filter by the ContactID. Get all the linked transactions that have been assigned to a particular customer. (optional)
   * @param status Filter by the combination of ContactID and Status. Get all the linked transactions that have been assigned to a particular customer and have a particular status e.g. GET /LinkedTransactions?ContactID&#x3D;4bb34b03-3378-4bb2-a0ed-6345abf3224e&amp;Status&#x3D;APPROVED. (optional)
   * @param targetTransactionID Filter by the TargetTransactionID. Get all the linked transactions allocated to a particular ACCREC invoice (optional)
   * @return LinkedTransactions
   * @throws ApiException if fails to make API call
   */
  public LinkedTransactions getLinkedTransactions(String xeroTenantId, Integer page, String linkedTransactionID, String sourceTransactionID, String contactID, String status, String targetTransactionID) throws ApiException {
    return getLinkedTransactionsWithHttpInfo(xeroTenantId, page, linkedTransactionID, sourceTransactionID, contactID, status, targetTransactionID).getData();
      }

  /**
   * Retrieve linked transactions (billable expenses)
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param page Up to 100 linked transactions will be returned in a single API call. Use the page parameter to specify the page to be returned e.g. page&#x3D;1. (optional)
   * @param linkedTransactionID The Xero identifier for an Linked Transaction (optional)
   * @param sourceTransactionID Filter by the SourceTransactionID. Get all the linked transactions created from a particular ACCPAY invoice (optional)
   * @param contactID Filter by the ContactID. Get all the linked transactions that have been assigned to a particular customer. (optional)
   * @param status Filter by the combination of ContactID and Status. Get all the linked transactions that have been assigned to a particular customer and have a particular status e.g. GET /LinkedTransactions?ContactID&#x3D;4bb34b03-3378-4bb2-a0ed-6345abf3224e&amp;Status&#x3D;APPROVED. (optional)
   * @param targetTransactionID Filter by the TargetTransactionID. Get all the linked transactions allocated to a particular ACCREC invoice (optional)
   * @return ApiResponse&lt;LinkedTransactions&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<LinkedTransactions> getLinkedTransactionsWithHttpInfo(String xeroTenantId, Integer page, String linkedTransactionID, String sourceTransactionID, String contactID, String status, String targetTransactionID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getLinkedTransactions");
    }
    
    // create path and map variables
    String localVarPath = "/LinkedTransactions";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page", page));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "LinkedTransactionID", linkedTransactionID));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "SourceTransactionID", sourceTransactionID));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "ContactID", contactID));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "Status", status));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "TargetTransactionID", targetTransactionID));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<LinkedTransactions> localVarReturnType = new GenericType<LinkedTransactions>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified manual journals
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @return ManualJournals
   * @throws ApiException if fails to make API call
   */
  public ManualJournals getManualJournal(String xeroTenantId, UUID manualJournalID) throws ApiException {
    return getManualJournalWithHttpInfo(xeroTenantId, manualJournalID).getData();
      }

  /**
   * Allows you to retrieve a specified manual journals
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @return ApiResponse&lt;ManualJournals&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ManualJournals> getManualJournalWithHttpInfo(String xeroTenantId, UUID manualJournalID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getManualJournal");
    }
    
    // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new ApiException(400, "Missing the required parameter 'manualJournalID' when calling getManualJournal");
    }
    
    // create path and map variables
    String localVarPath = "/ManualJournals/{ManualJournalID}"
      .replaceAll("\\{" + "ManualJournalID" + "\\}", apiClient.escapeString(manualJournalID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ManualJournals> localVarReturnType = new GenericType<ManualJournals>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve specified Attachment on ManualJournal by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @param fileName The name of the file being attached to a ManualJournal (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getManualJournalAttachmentByFileName(String xeroTenantId, UUID manualJournalID, String fileName, String contentType) throws ApiException {
    return getManualJournalAttachmentByFileNameWithHttpInfo(xeroTenantId, manualJournalID, fileName, contentType).getData();
      }

  /**
   * Allows you to retrieve specified Attachment on ManualJournal by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @param fileName The name of the file being attached to a ManualJournal (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getManualJournalAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID manualJournalID, String fileName, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getManualJournalAttachmentByFileName");
    }
    
    // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new ApiException(400, "Missing the required parameter 'manualJournalID' when calling getManualJournalAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling getManualJournalAttachmentByFileName");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getManualJournalAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}"
      .replaceAll("\\{" + "ManualJournalID" + "\\}", apiClient.escapeString(manualJournalID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve specified Attachment on ManualJournals
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @param attachmentID Unique identifier for a Attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getManualJournalAttachmentById(String xeroTenantId, UUID manualJournalID, UUID attachmentID, String contentType) throws ApiException {
    return getManualJournalAttachmentByIdWithHttpInfo(xeroTenantId, manualJournalID, attachmentID, contentType).getData();
      }

  /**
   * Allows you to retrieve specified Attachment on ManualJournals
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @param attachmentID Unique identifier for a Attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getManualJournalAttachmentByIdWithHttpInfo(String xeroTenantId, UUID manualJournalID, UUID attachmentID, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getManualJournalAttachmentById");
    }
    
    // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new ApiException(400, "Missing the required parameter 'manualJournalID' when calling getManualJournalAttachmentById");
    }
    
    // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new ApiException(400, "Missing the required parameter 'attachmentID' when calling getManualJournalAttachmentById");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getManualJournalAttachmentById");
    }
    
    // create path and map variables
    String localVarPath = "/ManualJournals/{ManualJournalID}/Attachments/{AttachmentID}"
      .replaceAll("\\{" + "ManualJournalID" + "\\}", apiClient.escapeString(manualJournalID.toString()))
      .replaceAll("\\{" + "AttachmentID" + "\\}", apiClient.escapeString(attachmentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachment for manual journals
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments getManualJournalAttachments(String xeroTenantId, UUID manualJournalID) throws ApiException {
    return getManualJournalAttachmentsWithHttpInfo(xeroTenantId, manualJournalID).getData();
      }

  /**
   * Allows you to retrieve Attachment for manual journals
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> getManualJournalAttachmentsWithHttpInfo(String xeroTenantId, UUID manualJournalID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getManualJournalAttachments");
    }
    
    // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new ApiException(400, "Missing the required parameter 'manualJournalID' when calling getManualJournalAttachments");
    }
    
    // create path and map variables
    String localVarPath = "/ManualJournals/{ManualJournalID}/Attachments"
      .replaceAll("\\{" + "ManualJournalID" + "\\}", apiClient.escapeString(manualJournalID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve any manual journals
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param page e.g. page&#x3D;1 – Up to 100 manual journals will be returned in a single API call with line items shown for each overpayment (optional)
   * @return ManualJournals
   * @throws ApiException if fails to make API call
   */
  public ManualJournals getManualJournals(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws ApiException {
    return getManualJournalsWithHttpInfo(xeroTenantId, ifModifiedSince, where, order, page).getData();
      }

  /**
   * Allows you to retrieve any manual journals
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param page e.g. page&#x3D;1 – Up to 100 manual journals will be returned in a single API call with line items shown for each overpayment (optional)
   * @return ApiResponse&lt;ManualJournals&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ManualJournals> getManualJournalsWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getManualJournals");
    }
    
    // create path and map variables
    String localVarPath = "/ManualJournals";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page", page));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ManualJournals> localVarReturnType = new GenericType<ManualJournals>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a URL to an online invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @return OnlineInvoices
   * @throws ApiException if fails to make API call
   */
  public OnlineInvoices getOnlineInvoice(String xeroTenantId, UUID invoiceID) throws ApiException {
    return getOnlineInvoiceWithHttpInfo(xeroTenantId, invoiceID).getData();
      }

  /**
   * Allows you to retrieve a URL to an online invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @return ApiResponse&lt;OnlineInvoices&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<OnlineInvoices> getOnlineInvoiceWithHttpInfo(String xeroTenantId, UUID invoiceID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getOnlineInvoice");
    }
    
    // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceID' when calling getOnlineInvoice");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices/{InvoiceID}/OnlineInvoice"
      .replaceAll("\\{" + "InvoiceID" + "\\}", apiClient.escapeString(invoiceID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<OnlineInvoices> localVarReturnType = new GenericType<OnlineInvoices>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you To verify if an organisation is using contruction industry scheme, you can retrieve the CIS settings for the organistaion.
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param organisationID  (required)
   * @return CISOrgSetting
   * @throws ApiException if fails to make API call
   */
  public CISOrgSetting getOrganisationCISSettings(String xeroTenantId, UUID organisationID) throws ApiException {
    return getOrganisationCISSettingsWithHttpInfo(xeroTenantId, organisationID).getData();
      }

  /**
   * Allows you To verify if an organisation is using contruction industry scheme, you can retrieve the CIS settings for the organistaion.
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param organisationID  (required)
   * @return ApiResponse&lt;CISOrgSetting&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<CISOrgSetting> getOrganisationCISSettingsWithHttpInfo(String xeroTenantId, UUID organisationID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getOrganisationCISSettings");
    }
    
    // verify the required parameter 'organisationID' is set
    if (organisationID == null) {
      throw new ApiException(400, "Missing the required parameter 'organisationID' when calling getOrganisationCISSettings");
    }
    
    // create path and map variables
    String localVarPath = "/Organisation/{OrganisationID}/CISSettings"
      .replaceAll("\\{" + "OrganisationID" + "\\}", apiClient.escapeString(organisationID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<CISOrgSetting> localVarReturnType = new GenericType<CISOrgSetting>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Organisation details
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @return Organisations
   * @throws ApiException if fails to make API call
   */
  public Organisations getOrganisations(String xeroTenantId) throws ApiException {
    return getOrganisationsWithHttpInfo(xeroTenantId).getData();
      }

  /**
   * Allows you to retrieve Organisation details
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @return ApiResponse&lt;Organisations&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Organisations> getOrganisationsWithHttpInfo(String xeroTenantId) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getOrganisations");
    }
    
    // create path and map variables
    String localVarPath = "/Organisation";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "OAuth2" };

    GenericType<Organisations> localVarReturnType = new GenericType<Organisations>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified overpayments
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param overpaymentID Unique identifier for a Overpayment (required)
   * @return Overpayments
   * @throws ApiException if fails to make API call
   */
  public Overpayments getOverpayment(String xeroTenantId, UUID overpaymentID) throws ApiException {
    return getOverpaymentWithHttpInfo(xeroTenantId, overpaymentID).getData();
      }

  /**
   * Allows you to retrieve a specified overpayments
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param overpaymentID Unique identifier for a Overpayment (required)
   * @return ApiResponse&lt;Overpayments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Overpayments> getOverpaymentWithHttpInfo(String xeroTenantId, UUID overpaymentID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getOverpayment");
    }
    
    // verify the required parameter 'overpaymentID' is set
    if (overpaymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'overpaymentID' when calling getOverpayment");
    }
    
    // create path and map variables
    String localVarPath = "/Overpayments/{OverpaymentID}"
      .replaceAll("\\{" + "OverpaymentID" + "\\}", apiClient.escapeString(overpaymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Overpayments> localVarReturnType = new GenericType<Overpayments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a history records of an Overpayment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param overpaymentID Unique identifier for a Overpayment (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getOverpaymentHistory(String xeroTenantId, UUID overpaymentID) throws ApiException {
    return getOverpaymentHistoryWithHttpInfo(xeroTenantId, overpaymentID).getData();
      }

  /**
   * Allows you to retrieve a history records of an Overpayment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param overpaymentID Unique identifier for a Overpayment (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getOverpaymentHistoryWithHttpInfo(String xeroTenantId, UUID overpaymentID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getOverpaymentHistory");
    }
    
    // verify the required parameter 'overpaymentID' is set
    if (overpaymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'overpaymentID' when calling getOverpaymentHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Overpayments/{OverpaymentID}/History"
      .replaceAll("\\{" + "OverpaymentID" + "\\}", apiClient.escapeString(overpaymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve overpayments
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param page e.g. page&#x3D;1 – Up to 100 overpayments will be returned in a single API call with line items shown for each overpayment (optional)
   * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts (optional)
   * @return Overpayments
   * @throws ApiException if fails to make API call
   */
  public Overpayments getOverpayments(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page, Integer unitdp) throws ApiException {
    return getOverpaymentsWithHttpInfo(xeroTenantId, ifModifiedSince, where, order, page, unitdp).getData();
      }

  /**
   * Allows you to retrieve overpayments
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param page e.g. page&#x3D;1 – Up to 100 overpayments will be returned in a single API call with line items shown for each overpayment (optional)
   * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts (optional)
   * @return ApiResponse&lt;Overpayments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Overpayments> getOverpaymentsWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page, Integer unitdp) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getOverpayments");
    }
    
    // create path and map variables
    String localVarPath = "/Overpayments";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page", page));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "unitdp", unitdp));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Overpayments> localVarReturnType = new GenericType<Overpayments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified payment for invoices and credit notes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param paymentID Unique identifier for a Payment (required)
   * @return Payments
   * @throws ApiException if fails to make API call
   */
  public Payments getPayment(String xeroTenantId, UUID paymentID) throws ApiException {
    return getPaymentWithHttpInfo(xeroTenantId, paymentID).getData();
      }

  /**
   * Allows you to retrieve a specified payment for invoices and credit notes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param paymentID Unique identifier for a Payment (required)
   * @return ApiResponse&lt;Payments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Payments> getPaymentWithHttpInfo(String xeroTenantId, UUID paymentID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getPayment");
    }
    
    // verify the required parameter 'paymentID' is set
    if (paymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentID' when calling getPayment");
    }
    
    // create path and map variables
    String localVarPath = "/Payments/{PaymentID}"
      .replaceAll("\\{" + "PaymentID" + "\\}", apiClient.escapeString(paymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Payments> localVarReturnType = new GenericType<Payments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve history records of a payment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param paymentID Unique identifier for a Payment (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getPaymentHistory(String xeroTenantId, UUID paymentID) throws ApiException {
    return getPaymentHistoryWithHttpInfo(xeroTenantId, paymentID).getData();
      }

  /**
   * Allows you to retrieve history records of a payment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param paymentID Unique identifier for a Payment (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getPaymentHistoryWithHttpInfo(String xeroTenantId, UUID paymentID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getPaymentHistory");
    }
    
    // verify the required parameter 'paymentID' is set
    if (paymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentID' when calling getPaymentHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Payments/{PaymentID}/History"
      .replaceAll("\\{" + "PaymentID" + "\\}", apiClient.escapeString(paymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve payment services
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @return PaymentServices
   * @throws ApiException if fails to make API call
   */
  public PaymentServices getPaymentServices(String xeroTenantId) throws ApiException {
    return getPaymentServicesWithHttpInfo(xeroTenantId).getData();
      }

  /**
   * Allows you to retrieve payment services
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @return ApiResponse&lt;PaymentServices&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<PaymentServices> getPaymentServicesWithHttpInfo(String xeroTenantId) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getPaymentServices");
    }
    
    // create path and map variables
    String localVarPath = "/PaymentServices";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<PaymentServices> localVarReturnType = new GenericType<PaymentServices>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve payments for invoices and credit notes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return Payments
   * @throws ApiException if fails to make API call
   */
  public Payments getPayments(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    return getPaymentsWithHttpInfo(xeroTenantId, ifModifiedSince, where, order).getData();
      }

  /**
   * Allows you to retrieve payments for invoices and credit notes
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return ApiResponse&lt;Payments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Payments> getPaymentsWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getPayments");
    }
    
    // create path and map variables
    String localVarPath = "/Payments";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Payments> localVarReturnType = new GenericType<Payments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified prepayments
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param prepaymentID Unique identifier for a PrePayment (required)
   * @return Prepayments
   * @throws ApiException if fails to make API call
   */
  public Prepayments getPrepayment(String xeroTenantId, UUID prepaymentID) throws ApiException {
    return getPrepaymentWithHttpInfo(xeroTenantId, prepaymentID).getData();
      }

  /**
   * Allows you to retrieve a specified prepayments
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param prepaymentID Unique identifier for a PrePayment (required)
   * @return ApiResponse&lt;Prepayments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Prepayments> getPrepaymentWithHttpInfo(String xeroTenantId, UUID prepaymentID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getPrepayment");
    }
    
    // verify the required parameter 'prepaymentID' is set
    if (prepaymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'prepaymentID' when calling getPrepayment");
    }
    
    // create path and map variables
    String localVarPath = "/Prepayments/{PrepaymentID}"
      .replaceAll("\\{" + "PrepaymentID" + "\\}", apiClient.escapeString(prepaymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Prepayments> localVarReturnType = new GenericType<Prepayments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a history records of an Prepayment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param prepaymentID Unique identifier for a PrePayment (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getPrepaymentHistory(String xeroTenantId, UUID prepaymentID) throws ApiException {
    return getPrepaymentHistoryWithHttpInfo(xeroTenantId, prepaymentID).getData();
      }

  /**
   * Allows you to retrieve a history records of an Prepayment
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param prepaymentID Unique identifier for a PrePayment (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getPrepaymentHistoryWithHttpInfo(String xeroTenantId, UUID prepaymentID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getPrepaymentHistory");
    }
    
    // verify the required parameter 'prepaymentID' is set
    if (prepaymentID == null) {
      throw new ApiException(400, "Missing the required parameter 'prepaymentID' when calling getPrepaymentHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Prepayments/{PrepaymentID}/History"
      .replaceAll("\\{" + "PrepaymentID" + "\\}", apiClient.escapeString(prepaymentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve prepayments
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param page e.g. page&#x3D;1 – Up to 100 prepayments will be returned in a single API call with line items shown for each overpayment (optional)
   * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts (optional)
   * @return Prepayments
   * @throws ApiException if fails to make API call
   */
  public Prepayments getPrepayments(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page, Integer unitdp) throws ApiException {
    return getPrepaymentsWithHttpInfo(xeroTenantId, ifModifiedSince, where, order, page, unitdp).getData();
      }

  /**
   * Allows you to retrieve prepayments
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param page e.g. page&#x3D;1 – Up to 100 prepayments will be returned in a single API call with line items shown for each overpayment (optional)
   * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts (optional)
   * @return ApiResponse&lt;Prepayments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Prepayments> getPrepaymentsWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page, Integer unitdp) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getPrepayments");
    }
    
    // create path and map variables
    String localVarPath = "/Prepayments";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page", page));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "unitdp", unitdp));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Prepayments> localVarReturnType = new GenericType<Prepayments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified purchase orders
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param purchaseOrderID Unique identifier for a PurchaseOrder (required)
   * @return PurchaseOrders
   * @throws ApiException if fails to make API call
   */
  public PurchaseOrders getPurchaseOrder(String xeroTenantId, UUID purchaseOrderID) throws ApiException {
    return getPurchaseOrderWithHttpInfo(xeroTenantId, purchaseOrderID).getData();
      }

  /**
   * Allows you to retrieve a specified purchase orders
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param purchaseOrderID Unique identifier for a PurchaseOrder (required)
   * @return ApiResponse&lt;PurchaseOrders&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<PurchaseOrders> getPurchaseOrderWithHttpInfo(String xeroTenantId, UUID purchaseOrderID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getPurchaseOrder");
    }
    
    // verify the required parameter 'purchaseOrderID' is set
    if (purchaseOrderID == null) {
      throw new ApiException(400, "Missing the required parameter 'purchaseOrderID' when calling getPurchaseOrder");
    }
    
    // create path and map variables
    String localVarPath = "/PurchaseOrders/{PurchaseOrderID}"
      .replaceAll("\\{" + "PurchaseOrderID" + "\\}", apiClient.escapeString(purchaseOrderID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<PurchaseOrders> localVarReturnType = new GenericType<PurchaseOrders>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve history for PurchaseOrder
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param purchaseOrderID Unique identifier for a PurchaseOrder (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getPurchaseOrderHistory(String xeroTenantId, UUID purchaseOrderID) throws ApiException {
    return getPurchaseOrderHistoryWithHttpInfo(xeroTenantId, purchaseOrderID).getData();
      }

  /**
   * Allows you to retrieve history for PurchaseOrder
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param purchaseOrderID Unique identifier for a PurchaseOrder (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getPurchaseOrderHistoryWithHttpInfo(String xeroTenantId, UUID purchaseOrderID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getPurchaseOrderHistory");
    }
    
    // verify the required parameter 'purchaseOrderID' is set
    if (purchaseOrderID == null) {
      throw new ApiException(400, "Missing the required parameter 'purchaseOrderID' when calling getPurchaseOrderHistory");
    }
    
    // create path and map variables
    String localVarPath = "/PurchaseOrders/{PurchaseOrderID}/History"
      .replaceAll("\\{" + "PurchaseOrderID" + "\\}", apiClient.escapeString(purchaseOrderID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve purchase orders
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param status Filter by purchase order status (optional)
   * @param dateFrom Filter by purchase order date (e.g. GET https://.../PurchaseOrders?DateFrom&#x3D;2015-12-01&amp;DateTo&#x3D;2015-12-31 (optional)
   * @param dateTo Filter by purchase order date (e.g. GET https://.../PurchaseOrders?DateFrom&#x3D;2015-12-01&amp;DateTo&#x3D;2015-12-31 (optional)
   * @param order Order by an any element (optional)
   * @param page To specify a page, append the page parameter to the URL e.g. ?page&#x3D;1. If there are 100 records in the response you will need to check if there is any more data by fetching the next page e.g ?page&#x3D;2 and continuing this process until no more results are returned. (optional)
   * @return PurchaseOrders
   * @throws ApiException if fails to make API call
   */
  public PurchaseOrders getPurchaseOrders(String xeroTenantId, OffsetDateTime ifModifiedSince, String status, String dateFrom, String dateTo, String order, Integer page) throws ApiException {
    return getPurchaseOrdersWithHttpInfo(xeroTenantId, ifModifiedSince, status, dateFrom, dateTo, order, page).getData();
      }

  /**
   * Allows you to retrieve purchase orders
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param status Filter by purchase order status (optional)
   * @param dateFrom Filter by purchase order date (e.g. GET https://.../PurchaseOrders?DateFrom&#x3D;2015-12-01&amp;DateTo&#x3D;2015-12-31 (optional)
   * @param dateTo Filter by purchase order date (e.g. GET https://.../PurchaseOrders?DateFrom&#x3D;2015-12-01&amp;DateTo&#x3D;2015-12-31 (optional)
   * @param order Order by an any element (optional)
   * @param page To specify a page, append the page parameter to the URL e.g. ?page&#x3D;1. If there are 100 records in the response you will need to check if there is any more data by fetching the next page e.g ?page&#x3D;2 and continuing this process until no more results are returned. (optional)
   * @return ApiResponse&lt;PurchaseOrders&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<PurchaseOrders> getPurchaseOrdersWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String status, String dateFrom, String dateTo, String order, Integer page) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getPurchaseOrders");
    }
    
    // create path and map variables
    String localVarPath = "/PurchaseOrders";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "Status", status));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "DateFrom", dateFrom));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "DateTo", dateTo));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page", page));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<PurchaseOrders> localVarReturnType = new GenericType<PurchaseOrders>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified draft expense claim receipts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @return Receipts
   * @throws ApiException if fails to make API call
   */
  public Receipts getReceipt(String xeroTenantId, UUID receiptID) throws ApiException {
    return getReceiptWithHttpInfo(xeroTenantId, receiptID).getData();
      }

  /**
   * Allows you to retrieve a specified draft expense claim receipts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @return ApiResponse&lt;Receipts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Receipts> getReceiptWithHttpInfo(String xeroTenantId, UUID receiptID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReceipt");
    }
    
    // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new ApiException(400, "Missing the required parameter 'receiptID' when calling getReceipt");
    }
    
    // create path and map variables
    String localVarPath = "/Receipts/{ReceiptID}"
      .replaceAll("\\{" + "ReceiptID" + "\\}", apiClient.escapeString(receiptID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Receipts> localVarReturnType = new GenericType<Receipts>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments on expense claim receipts by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @param fileName The name of the file being attached to the Receipt (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getReceiptAttachmentByFileName(String xeroTenantId, UUID receiptID, String fileName, String contentType) throws ApiException {
    return getReceiptAttachmentByFileNameWithHttpInfo(xeroTenantId, receiptID, fileName, contentType).getData();
      }

  /**
   * Allows you to retrieve Attachments on expense claim receipts by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @param fileName The name of the file being attached to the Receipt (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getReceiptAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID receiptID, String fileName, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReceiptAttachmentByFileName");
    }
    
    // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new ApiException(400, "Missing the required parameter 'receiptID' when calling getReceiptAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling getReceiptAttachmentByFileName");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getReceiptAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/Receipts/{ReceiptID}/Attachments/{FileName}"
      .replaceAll("\\{" + "ReceiptID" + "\\}", apiClient.escapeString(receiptID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments on expense claim receipts by ID
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @param attachmentID Unique identifier for a Attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getReceiptAttachmentById(String xeroTenantId, UUID receiptID, UUID attachmentID, String contentType) throws ApiException {
    return getReceiptAttachmentByIdWithHttpInfo(xeroTenantId, receiptID, attachmentID, contentType).getData();
      }

  /**
   * Allows you to retrieve Attachments on expense claim receipts by ID
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @param attachmentID Unique identifier for a Attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getReceiptAttachmentByIdWithHttpInfo(String xeroTenantId, UUID receiptID, UUID attachmentID, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReceiptAttachmentById");
    }
    
    // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new ApiException(400, "Missing the required parameter 'receiptID' when calling getReceiptAttachmentById");
    }
    
    // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new ApiException(400, "Missing the required parameter 'attachmentID' when calling getReceiptAttachmentById");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getReceiptAttachmentById");
    }
    
    // create path and map variables
    String localVarPath = "/Receipts/{ReceiptID}/Attachments/{AttachmentID}"
      .replaceAll("\\{" + "ReceiptID" + "\\}", apiClient.escapeString(receiptID.toString()))
      .replaceAll("\\{" + "AttachmentID" + "\\}", apiClient.escapeString(attachmentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments for expense claim receipts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments getReceiptAttachments(String xeroTenantId, UUID receiptID) throws ApiException {
    return getReceiptAttachmentsWithHttpInfo(xeroTenantId, receiptID).getData();
      }

  /**
   * Allows you to retrieve Attachments for expense claim receipts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> getReceiptAttachmentsWithHttpInfo(String xeroTenantId, UUID receiptID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReceiptAttachments");
    }
    
    // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new ApiException(400, "Missing the required parameter 'receiptID' when calling getReceiptAttachments");
    }
    
    // create path and map variables
    String localVarPath = "/Receipts/{ReceiptID}/Attachments"
      .replaceAll("\\{" + "ReceiptID" + "\\}", apiClient.escapeString(receiptID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a history records of an Receipt
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getReceiptHistory(String xeroTenantId, UUID receiptID) throws ApiException {
    return getReceiptHistoryWithHttpInfo(xeroTenantId, receiptID).getData();
      }

  /**
   * Allows you to retrieve a history records of an Receipt
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getReceiptHistoryWithHttpInfo(String xeroTenantId, UUID receiptID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReceiptHistory");
    }
    
    // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new ApiException(400, "Missing the required parameter 'receiptID' when calling getReceiptHistory");
    }
    
    // create path and map variables
    String localVarPath = "/Receipts/{ReceiptID}/History"
      .replaceAll("\\{" + "ReceiptID" + "\\}", apiClient.escapeString(receiptID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve draft expense claim receipts for any user
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts (optional)
   * @return Receipts
   * @throws ApiException if fails to make API call
   */
  public Receipts getReceipts(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer unitdp) throws ApiException {
    return getReceiptsWithHttpInfo(xeroTenantId, ifModifiedSince, where, order, unitdp).getData();
      }

  /**
   * Allows you to retrieve draft expense claim receipts for any user
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param unitdp e.g. unitdp&#x3D;4 – You can opt in to use four decimal places for unit amounts (optional)
   * @return ApiResponse&lt;Receipts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Receipts> getReceiptsWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer unitdp) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReceipts");
    }
    
    // create path and map variables
    String localVarPath = "/Receipts";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "unitdp", unitdp));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Receipts> localVarReturnType = new GenericType<Receipts>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified repeating invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @return RepeatingInvoices
   * @throws ApiException if fails to make API call
   */
  public RepeatingInvoices getRepeatingInvoice(String xeroTenantId, UUID repeatingInvoiceID) throws ApiException {
    return getRepeatingInvoiceWithHttpInfo(xeroTenantId, repeatingInvoiceID).getData();
      }

  /**
   * Allows you to retrieve a specified repeating invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @return ApiResponse&lt;RepeatingInvoices&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<RepeatingInvoices> getRepeatingInvoiceWithHttpInfo(String xeroTenantId, UUID repeatingInvoiceID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoice");
    }
    
    // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'repeatingInvoiceID' when calling getRepeatingInvoice");
    }
    
    // create path and map variables
    String localVarPath = "/RepeatingInvoices/{RepeatingInvoiceID}"
      .replaceAll("\\{" + "RepeatingInvoiceID" + "\\}", apiClient.escapeString(repeatingInvoiceID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<RepeatingInvoices> localVarReturnType = new GenericType<RepeatingInvoices>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve specified attachment on repeating invoices by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @param fileName The name of the file being attached to a Repeating Invoice (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getRepeatingInvoiceAttachmentByFileName(String xeroTenantId, UUID repeatingInvoiceID, String fileName, String contentType) throws ApiException {
    return getRepeatingInvoiceAttachmentByFileNameWithHttpInfo(xeroTenantId, repeatingInvoiceID, fileName, contentType).getData();
      }

  /**
   * Allows you to retrieve specified attachment on repeating invoices by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @param fileName The name of the file being attached to a Repeating Invoice (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getRepeatingInvoiceAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID repeatingInvoiceID, String fileName, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'repeatingInvoiceID' when calling getRepeatingInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling getRepeatingInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getRepeatingInvoiceAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}"
      .replaceAll("\\{" + "RepeatingInvoiceID" + "\\}", apiClient.escapeString(repeatingInvoiceID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified Attachments on repeating invoices
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @param attachmentID Unique identifier for a Attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return File
   * @throws ApiException if fails to make API call
   */
  public File getRepeatingInvoiceAttachmentById(String xeroTenantId, UUID repeatingInvoiceID, UUID attachmentID, String contentType) throws ApiException {
    return getRepeatingInvoiceAttachmentByIdWithHttpInfo(xeroTenantId, repeatingInvoiceID, attachmentID, contentType).getData();
      }

  /**
   * Allows you to retrieve a specified Attachments on repeating invoices
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @param attachmentID Unique identifier for a Attachment (required)
   * @param contentType The mime type of the attachment file you are retrieving i.e image/jpg, application/pdf (required)
   * @return ApiResponse&lt;File&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<File> getRepeatingInvoiceAttachmentByIdWithHttpInfo(String xeroTenantId, UUID repeatingInvoiceID, UUID attachmentID, String contentType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoiceAttachmentById");
    }
    
    // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'repeatingInvoiceID' when calling getRepeatingInvoiceAttachmentById");
    }
    
    // verify the required parameter 'attachmentID' is set
    if (attachmentID == null) {
      throw new ApiException(400, "Missing the required parameter 'attachmentID' when calling getRepeatingInvoiceAttachmentById");
    }
    
    // verify the required parameter 'contentType' is set
    if (contentType == null) {
      throw new ApiException(400, "Missing the required parameter 'contentType' when calling getRepeatingInvoiceAttachmentById");
    }
    
    // create path and map variables
    String localVarPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{AttachmentID}"
      .replaceAll("\\{" + "RepeatingInvoiceID" + "\\}", apiClient.escapeString(repeatingInvoiceID.toString()))
      .replaceAll("\\{" + "AttachmentID" + "\\}", apiClient.escapeString(attachmentID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (contentType != null)
      localVarHeaderParams.put("contentType", apiClient.parameterToString(contentType));

    
    final String[] localVarAccepts = {
      "application/octet-stream"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<File> localVarReturnType = new GenericType<File>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Attachments on repeating invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments getRepeatingInvoiceAttachments(String xeroTenantId, UUID repeatingInvoiceID) throws ApiException {
    return getRepeatingInvoiceAttachmentsWithHttpInfo(xeroTenantId, repeatingInvoiceID).getData();
      }

  /**
   * Allows you to retrieve Attachments on repeating invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> getRepeatingInvoiceAttachmentsWithHttpInfo(String xeroTenantId, UUID repeatingInvoiceID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoiceAttachments");
    }
    
    // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'repeatingInvoiceID' when calling getRepeatingInvoiceAttachments");
    }
    
    // create path and map variables
    String localVarPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments"
      .replaceAll("\\{" + "RepeatingInvoiceID" + "\\}", apiClient.escapeString(repeatingInvoiceID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve history for a repeating invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @return HistoryRecords
   * @throws ApiException if fails to make API call
   */
  public HistoryRecords getRepeatingInvoiceHistory(String xeroTenantId, UUID repeatingInvoiceID) throws ApiException {
    return getRepeatingInvoiceHistoryWithHttpInfo(xeroTenantId, repeatingInvoiceID).getData();
      }

  /**
   * Allows you to retrieve history for a repeating invoice
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @return ApiResponse&lt;HistoryRecords&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<HistoryRecords> getRepeatingInvoiceHistoryWithHttpInfo(String xeroTenantId, UUID repeatingInvoiceID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoiceHistory");
    }
    
    // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'repeatingInvoiceID' when calling getRepeatingInvoiceHistory");
    }
    
    // create path and map variables
    String localVarPath = "/RepeatingInvoices/{RepeatingInvoiceID}/History"
      .replaceAll("\\{" + "RepeatingInvoiceID" + "\\}", apiClient.escapeString(repeatingInvoiceID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<HistoryRecords> localVarReturnType = new GenericType<HistoryRecords>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve any repeating invoices
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return RepeatingInvoices
   * @throws ApiException if fails to make API call
   */
  public RepeatingInvoices getRepeatingInvoices(String xeroTenantId, String where, String order) throws ApiException {
    return getRepeatingInvoicesWithHttpInfo(xeroTenantId, where, order).getData();
      }

  /**
   * Allows you to retrieve any repeating invoices
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return ApiResponse&lt;RepeatingInvoices&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<RepeatingInvoices> getRepeatingInvoicesWithHttpInfo(String xeroTenantId, String where, String order) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getRepeatingInvoices");
    }
    
    // create path and map variables
    String localVarPath = "/RepeatingInvoices";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<RepeatingInvoices> localVarReturnType = new GenericType<RepeatingInvoices>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve report for AgedPayablesByContact
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactId Unique identifier for a Contact (required)
   * @param date The date of the Aged Payables By Contact report (optional)
   * @param fromDate The from date of the Aged Payables By Contact report (optional)
   * @param toDate The to date of the Aged Payables By Contact report (optional)
   * @return ReportWithRows
   * @throws ApiException if fails to make API call
   */
  public ReportWithRows getReportAgedPayablesByContact(String xeroTenantId, UUID contactId, LocalDate date, LocalDate fromDate, LocalDate toDate) throws ApiException {
    return getReportAgedPayablesByContactWithHttpInfo(xeroTenantId, contactId, date, fromDate, toDate).getData();
      }

  /**
   * Allows you to retrieve report for AgedPayablesByContact
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactId Unique identifier for a Contact (required)
   * @param date The date of the Aged Payables By Contact report (optional)
   * @param fromDate The from date of the Aged Payables By Contact report (optional)
   * @param toDate The to date of the Aged Payables By Contact report (optional)
   * @return ApiResponse&lt;ReportWithRows&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ReportWithRows> getReportAgedPayablesByContactWithHttpInfo(String xeroTenantId, UUID contactId, LocalDate date, LocalDate fromDate, LocalDate toDate) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReportAgedPayablesByContact");
    }
    
    // verify the required parameter 'contactId' is set
    if (contactId == null) {
      throw new ApiException(400, "Missing the required parameter 'contactId' when calling getReportAgedPayablesByContact");
    }
    
    // create path and map variables
    String localVarPath = "/Reports/AgedPayablesByContact";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "contactId", contactId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "date", date));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "fromDate", fromDate));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "toDate", toDate));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ReportWithRows> localVarReturnType = new GenericType<ReportWithRows>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve report for AgedReceivablesByContact
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactId Unique identifier for a Contact (required)
   * @param date The date of the Aged Receivables By Contact report (optional)
   * @param fromDate The from date of the Aged Receivables By Contact report (optional)
   * @param toDate The to date of the Aged Receivables By Contact report (optional)
   * @return ReportWithRows
   * @throws ApiException if fails to make API call
   */
  public ReportWithRows getReportAgedReceivablesByContact(String xeroTenantId, UUID contactId, LocalDate date, LocalDate fromDate, LocalDate toDate) throws ApiException {
    return getReportAgedReceivablesByContactWithHttpInfo(xeroTenantId, contactId, date, fromDate, toDate).getData();
      }

  /**
   * Allows you to retrieve report for AgedReceivablesByContact
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactId Unique identifier for a Contact (required)
   * @param date The date of the Aged Receivables By Contact report (optional)
   * @param fromDate The from date of the Aged Receivables By Contact report (optional)
   * @param toDate The to date of the Aged Receivables By Contact report (optional)
   * @return ApiResponse&lt;ReportWithRows&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ReportWithRows> getReportAgedReceivablesByContactWithHttpInfo(String xeroTenantId, UUID contactId, LocalDate date, LocalDate fromDate, LocalDate toDate) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReportAgedReceivablesByContact");
    }
    
    // verify the required parameter 'contactId' is set
    if (contactId == null) {
      throw new ApiException(400, "Missing the required parameter 'contactId' when calling getReportAgedReceivablesByContact");
    }
    
    // create path and map variables
    String localVarPath = "/Reports/AgedReceivablesByContact";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "contactId", contactId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "date", date));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "fromDate", fromDate));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "toDate", toDate));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ReportWithRows> localVarReturnType = new GenericType<ReportWithRows>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve report for BAS only valid for AU orgs
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param reportID Unique identifier for a Report (required)
   * @return ReportWithRows
   * @throws ApiException if fails to make API call
   */
  public ReportWithRows getReportBASorGST(String xeroTenantId, String reportID) throws ApiException {
    return getReportBASorGSTWithHttpInfo(xeroTenantId, reportID).getData();
      }

  /**
   * Allows you to retrieve report for BAS only valid for AU orgs
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param reportID Unique identifier for a Report (required)
   * @return ApiResponse&lt;ReportWithRows&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ReportWithRows> getReportBASorGSTWithHttpInfo(String xeroTenantId, String reportID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReportBASorGST");
    }
    
    // verify the required parameter 'reportID' is set
    if (reportID == null) {
      throw new ApiException(400, "Missing the required parameter 'reportID' when calling getReportBASorGST");
    }
    
    // create path and map variables
    String localVarPath = "/Reports/{ReportID}"
      .replaceAll("\\{" + "ReportID" + "\\}", apiClient.escapeString(reportID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ReportWithRows> localVarReturnType = new GenericType<ReportWithRows>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve report for BAS only valid for AU orgs
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @return ReportWithRows
   * @throws ApiException if fails to make API call
   */
  public ReportWithRows getReportBASorGSTList(String xeroTenantId) throws ApiException {
    return getReportBASorGSTListWithHttpInfo(xeroTenantId).getData();
      }

  /**
   * Allows you to retrieve report for BAS only valid for AU orgs
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @return ApiResponse&lt;ReportWithRows&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ReportWithRows> getReportBASorGSTListWithHttpInfo(String xeroTenantId) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReportBASorGSTList");
    }
    
    // create path and map variables
    String localVarPath = "/Reports";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ReportWithRows> localVarReturnType = new GenericType<ReportWithRows>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve report for BalanceSheet
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param date The date of the Balance Sheet report (optional)
   * @param periods The number of periods for the Balance Sheet report (optional)
   * @param timeframe The period size to compare to (MONTH, QUARTER, YEAR) (optional)
   * @param trackingOptionID1 The tracking option 1 for the Balance Sheet report (optional)
   * @param trackingOptionID2 The tracking option 2 for the Balance Sheet report (optional)
   * @param standardLayout The standard layout boolean for the Balance Sheet report (optional)
   * @param paymentsOnly return a cash basis for the Balance Sheet report (optional)
   * @return ReportWithRows
   * @throws ApiException if fails to make API call
   */
  public ReportWithRows getReportBalanceSheet(String xeroTenantId, String date, Integer periods, String timeframe, String trackingOptionID1, String trackingOptionID2, Boolean standardLayout, Boolean paymentsOnly) throws ApiException {
    return getReportBalanceSheetWithHttpInfo(xeroTenantId, date, periods, timeframe, trackingOptionID1, trackingOptionID2, standardLayout, paymentsOnly).getData();
      }

  /**
   * Allows you to retrieve report for BalanceSheet
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param date The date of the Balance Sheet report (optional)
   * @param periods The number of periods for the Balance Sheet report (optional)
   * @param timeframe The period size to compare to (MONTH, QUARTER, YEAR) (optional)
   * @param trackingOptionID1 The tracking option 1 for the Balance Sheet report (optional)
   * @param trackingOptionID2 The tracking option 2 for the Balance Sheet report (optional)
   * @param standardLayout The standard layout boolean for the Balance Sheet report (optional)
   * @param paymentsOnly return a cash basis for the Balance Sheet report (optional)
   * @return ApiResponse&lt;ReportWithRows&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ReportWithRows> getReportBalanceSheetWithHttpInfo(String xeroTenantId, String date, Integer periods, String timeframe, String trackingOptionID1, String trackingOptionID2, Boolean standardLayout, Boolean paymentsOnly) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReportBalanceSheet");
    }
    
    // create path and map variables
    String localVarPath = "/Reports/BalanceSheet";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "date", date));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "periods", periods));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "timeframe", timeframe));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "trackingOptionID1", trackingOptionID1));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "trackingOptionID2", trackingOptionID2));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "standardLayout", standardLayout));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "paymentsOnly", paymentsOnly));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ReportWithRows> localVarReturnType = new GenericType<ReportWithRows>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve report for BankSummary
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param date The date for the Bank Summary report e.g. 2018-03-31 (optional)
   * @param period The number of periods to compare (integer between 1 and 12) (optional)
   * @param timeframe The period size to compare to (1&#x3D;month, 3&#x3D;quarter, 12&#x3D;year) (optional)
   * @return ReportWithRows
   * @throws ApiException if fails to make API call
   */
  public ReportWithRows getReportBankSummary(String xeroTenantId, LocalDate date, Integer period, Integer timeframe) throws ApiException {
    return getReportBankSummaryWithHttpInfo(xeroTenantId, date, period, timeframe).getData();
      }

  /**
   * Allows you to retrieve report for BankSummary
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param date The date for the Bank Summary report e.g. 2018-03-31 (optional)
   * @param period The number of periods to compare (integer between 1 and 12) (optional)
   * @param timeframe The period size to compare to (1&#x3D;month, 3&#x3D;quarter, 12&#x3D;year) (optional)
   * @return ApiResponse&lt;ReportWithRows&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ReportWithRows> getReportBankSummaryWithHttpInfo(String xeroTenantId, LocalDate date, Integer period, Integer timeframe) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReportBankSummary");
    }
    
    // create path and map variables
    String localVarPath = "/Reports/BankSummary";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "date", date));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "period", period));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "timeframe", timeframe));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ReportWithRows> localVarReturnType = new GenericType<ReportWithRows>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve report for Budget Summary
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param date The date for the Bank Summary report e.g. 2018-03-31 (optional)
   * @param period The number of periods to compare (integer between 1 and 12) (optional)
   * @param timeframe The period size to compare to (1&#x3D;month, 3&#x3D;quarter, 12&#x3D;year) (optional)
   * @return ReportWithRows
   * @throws ApiException if fails to make API call
   */
  public ReportWithRows getReportBudgetSummary(String xeroTenantId, LocalDate date, Integer period, Integer timeframe) throws ApiException {
    return getReportBudgetSummaryWithHttpInfo(xeroTenantId, date, period, timeframe).getData();
      }

  /**
   * Allows you to retrieve report for Budget Summary
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param date The date for the Bank Summary report e.g. 2018-03-31 (optional)
   * @param period The number of periods to compare (integer between 1 and 12) (optional)
   * @param timeframe The period size to compare to (1&#x3D;month, 3&#x3D;quarter, 12&#x3D;year) (optional)
   * @return ApiResponse&lt;ReportWithRows&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ReportWithRows> getReportBudgetSummaryWithHttpInfo(String xeroTenantId, LocalDate date, Integer period, Integer timeframe) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReportBudgetSummary");
    }
    
    // create path and map variables
    String localVarPath = "/Reports/BudgetSummary";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "date", date));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "period", period));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "timeframe", timeframe));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ReportWithRows> localVarReturnType = new GenericType<ReportWithRows>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve report for ExecutiveSummary
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param date The date for the Bank Summary report e.g. 2018-03-31 (optional)
   * @return ReportWithRows
   * @throws ApiException if fails to make API call
   */
  public ReportWithRows getReportExecutiveSummary(String xeroTenantId, LocalDate date) throws ApiException {
    return getReportExecutiveSummaryWithHttpInfo(xeroTenantId, date).getData();
      }

  /**
   * Allows you to retrieve report for ExecutiveSummary
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param date The date for the Bank Summary report e.g. 2018-03-31 (optional)
   * @return ApiResponse&lt;ReportWithRows&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ReportWithRows> getReportExecutiveSummaryWithHttpInfo(String xeroTenantId, LocalDate date) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReportExecutiveSummary");
    }
    
    // create path and map variables
    String localVarPath = "/Reports/ExecutiveSummary";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "date", date));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ReportWithRows> localVarReturnType = new GenericType<ReportWithRows>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve report for ProfitAndLoss
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param fromDate The from date for the ProfitAndLoss report e.g. 2018-03-31 (optional)
   * @param toDate The to date for the ProfitAndLoss report e.g. 2018-03-31 (optional)
   * @param periods The number of periods to compare (integer between 1 and 12) (optional)
   * @param timeframe The period size to compare to (MONTH, QUARTER, YEAR) (optional)
   * @param trackingCategoryID The trackingCategory 1 for the ProfitAndLoss report (optional)
   * @param trackingCategoryID2 The trackingCategory 2 for the ProfitAndLoss report (optional)
   * @param trackingOptionID The tracking option 1 for the ProfitAndLoss report (optional)
   * @param trackingOptionID2 The tracking option 2 for the ProfitAndLoss report (optional)
   * @param standardLayout Return the standard layout for the ProfitAndLoss report (optional)
   * @param paymentsOnly Return cash only basis for the ProfitAndLoss report (optional)
   * @return ReportWithRows
   * @throws ApiException if fails to make API call
   */
  public ReportWithRows getReportProfitAndLoss(String xeroTenantId, LocalDate fromDate, LocalDate toDate, Integer periods, String timeframe, String trackingCategoryID, String trackingCategoryID2, String trackingOptionID, String trackingOptionID2, Boolean standardLayout, Boolean paymentsOnly) throws ApiException {
    return getReportProfitAndLossWithHttpInfo(xeroTenantId, fromDate, toDate, periods, timeframe, trackingCategoryID, trackingCategoryID2, trackingOptionID, trackingOptionID2, standardLayout, paymentsOnly).getData();
      }

  /**
   * Allows you to retrieve report for ProfitAndLoss
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param fromDate The from date for the ProfitAndLoss report e.g. 2018-03-31 (optional)
   * @param toDate The to date for the ProfitAndLoss report e.g. 2018-03-31 (optional)
   * @param periods The number of periods to compare (integer between 1 and 12) (optional)
   * @param timeframe The period size to compare to (MONTH, QUARTER, YEAR) (optional)
   * @param trackingCategoryID The trackingCategory 1 for the ProfitAndLoss report (optional)
   * @param trackingCategoryID2 The trackingCategory 2 for the ProfitAndLoss report (optional)
   * @param trackingOptionID The tracking option 1 for the ProfitAndLoss report (optional)
   * @param trackingOptionID2 The tracking option 2 for the ProfitAndLoss report (optional)
   * @param standardLayout Return the standard layout for the ProfitAndLoss report (optional)
   * @param paymentsOnly Return cash only basis for the ProfitAndLoss report (optional)
   * @return ApiResponse&lt;ReportWithRows&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ReportWithRows> getReportProfitAndLossWithHttpInfo(String xeroTenantId, LocalDate fromDate, LocalDate toDate, Integer periods, String timeframe, String trackingCategoryID, String trackingCategoryID2, String trackingOptionID, String trackingOptionID2, Boolean standardLayout, Boolean paymentsOnly) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReportProfitAndLoss");
    }
    
    // create path and map variables
    String localVarPath = "/Reports/ProfitAndLoss";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "fromDate", fromDate));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "toDate", toDate));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "periods", periods));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "timeframe", timeframe));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "trackingCategoryID", trackingCategoryID));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "trackingCategoryID2", trackingCategoryID2));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "trackingOptionID", trackingOptionID));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "trackingOptionID2", trackingOptionID2));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "standardLayout", standardLayout));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "paymentsOnly", paymentsOnly));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ReportWithRows> localVarReturnType = new GenericType<ReportWithRows>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve report for TenNinetyNine
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param reportYear The year of the 1099 report (optional)
   * @return Reports
   * @throws ApiException if fails to make API call
   */
  public Reports getReportTenNinetyNine(String xeroTenantId, String reportYear) throws ApiException {
    return getReportTenNinetyNineWithHttpInfo(xeroTenantId, reportYear).getData();
      }

  /**
   * Allows you to retrieve report for TenNinetyNine
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param reportYear The year of the 1099 report (optional)
   * @return ApiResponse&lt;Reports&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Reports> getReportTenNinetyNineWithHttpInfo(String xeroTenantId, String reportYear) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReportTenNinetyNine");
    }
    
    // create path and map variables
    String localVarPath = "/Reports/TenNinetyNine";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "reportYear", reportYear));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Reports> localVarReturnType = new GenericType<Reports>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve report for TrialBalance
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param date The date for the Trial Balance report e.g. 2018-03-31 (optional)
   * @param paymentsOnly Return cash only basis for the Trial Balance report (optional)
   * @return ReportWithRows
   * @throws ApiException if fails to make API call
   */
  public ReportWithRows getReportTrialBalance(String xeroTenantId, LocalDate date, Boolean paymentsOnly) throws ApiException {
    return getReportTrialBalanceWithHttpInfo(xeroTenantId, date, paymentsOnly).getData();
      }

  /**
   * Allows you to retrieve report for TrialBalance
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param date The date for the Trial Balance report e.g. 2018-03-31 (optional)
   * @param paymentsOnly Return cash only basis for the Trial Balance report (optional)
   * @return ApiResponse&lt;ReportWithRows&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ReportWithRows> getReportTrialBalanceWithHttpInfo(String xeroTenantId, LocalDate date, Boolean paymentsOnly) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getReportTrialBalance");
    }
    
    // create path and map variables
    String localVarPath = "/Reports/TrialBalance";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "date", date));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "paymentsOnly", paymentsOnly));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ReportWithRows> localVarReturnType = new GenericType<ReportWithRows>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve Tax Rates
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param taxType Filter by tax type (optional)
   * @return TaxRates
   * @throws ApiException if fails to make API call
   */
  public TaxRates getTaxRates(String xeroTenantId, String where, String order, String taxType) throws ApiException {
    return getTaxRatesWithHttpInfo(xeroTenantId, where, order, taxType).getData();
      }

  /**
   * Allows you to retrieve Tax Rates
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param taxType Filter by tax type (optional)
   * @return ApiResponse&lt;TaxRates&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<TaxRates> getTaxRatesWithHttpInfo(String xeroTenantId, String where, String order, String taxType) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getTaxRates");
    }
    
    // create path and map variables
    String localVarPath = "/TaxRates";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "TaxType", taxType));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<TaxRates> localVarReturnType = new GenericType<TaxRates>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve tracking categories and options
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param includeArchived e.g. includeArchived&#x3D;true - Categories and options with a status of ARCHIVED will be included in the response (optional)
   * @return TrackingCategories
   * @throws ApiException if fails to make API call
   */
  public TrackingCategories getTrackingCategories(String xeroTenantId, String where, String order, Boolean includeArchived) throws ApiException {
    return getTrackingCategoriesWithHttpInfo(xeroTenantId, where, order, includeArchived).getData();
      }

  /**
   * Allows you to retrieve tracking categories and options
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @param includeArchived e.g. includeArchived&#x3D;true - Categories and options with a status of ARCHIVED will be included in the response (optional)
   * @return ApiResponse&lt;TrackingCategories&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<TrackingCategories> getTrackingCategoriesWithHttpInfo(String xeroTenantId, String where, String order, Boolean includeArchived) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getTrackingCategories");
    }
    
    // create path and map variables
    String localVarPath = "/TrackingCategories";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "includeArchived", includeArchived));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<TrackingCategories> localVarReturnType = new GenericType<TrackingCategories>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve tracking categories and options for specified category
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param trackingCategoryID Unique identifier for a TrackingCategory (required)
   * @return TrackingCategories
   * @throws ApiException if fails to make API call
   */
  public TrackingCategories getTrackingCategory(String xeroTenantId, UUID trackingCategoryID) throws ApiException {
    return getTrackingCategoryWithHttpInfo(xeroTenantId, trackingCategoryID).getData();
      }

  /**
   * Allows you to retrieve tracking categories and options for specified category
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param trackingCategoryID Unique identifier for a TrackingCategory (required)
   * @return ApiResponse&lt;TrackingCategories&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<TrackingCategories> getTrackingCategoryWithHttpInfo(String xeroTenantId, UUID trackingCategoryID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getTrackingCategory");
    }
    
    // verify the required parameter 'trackingCategoryID' is set
    if (trackingCategoryID == null) {
      throw new ApiException(400, "Missing the required parameter 'trackingCategoryID' when calling getTrackingCategory");
    }
    
    // create path and map variables
    String localVarPath = "/TrackingCategories/{TrackingCategoryID}"
      .replaceAll("\\{" + "TrackingCategoryID" + "\\}", apiClient.escapeString(trackingCategoryID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<TrackingCategories> localVarReturnType = new GenericType<TrackingCategories>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified user
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param userID Unique identifier for a User (required)
   * @return Users
   * @throws ApiException if fails to make API call
   */
  public Users getUser(String xeroTenantId, UUID userID) throws ApiException {
    return getUserWithHttpInfo(xeroTenantId, userID).getData();
      }

  /**
   * Allows you to retrieve a specified user
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param userID Unique identifier for a User (required)
   * @return ApiResponse&lt;Users&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Users> getUserWithHttpInfo(String xeroTenantId, UUID userID) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getUser");
    }
    
    // verify the required parameter 'userID' is set
    if (userID == null) {
      throw new ApiException(400, "Missing the required parameter 'userID' when calling getUser");
    }
    
    // create path and map variables
    String localVarPath = "/Users/{UserID}"
      .replaceAll("\\{" + "UserID" + "\\}", apiClient.escapeString(userID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Users> localVarReturnType = new GenericType<Users>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve users
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return Users
   * @throws ApiException if fails to make API call
   */
  public Users getUsers(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    return getUsersWithHttpInfo(xeroTenantId, ifModifiedSince, where, order).getData();
      }

  /**
   * Allows you to retrieve users
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned (optional)
   * @param where Filter by an any element (optional)
   * @param order Order by an any element (optional)
   * @return ApiResponse&lt;Users&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Users> getUsersWithHttpInfo(String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order) throws ApiException {
    Object localVarPostBody = new Object();
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling getUsers");
    }
    
    // create path and map variables
    String localVarPath = "/Users";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "where", where));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));

    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));
if (ifModifiedSince != null)
      localVarHeaderParams.put("If-Modified-Since", apiClient.parameterToString(ifModifiedSince));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Users> localVarReturnType = new GenericType<Users>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update a chart of accounts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for retrieving single object (required)
   * @param accounts Request of type Accounts array with one Account (required)
   * @return Accounts
   * @throws ApiException if fails to make API call
   */
  public Accounts updateAccount(String xeroTenantId, UUID accountID, Accounts accounts) throws ApiException {
    return updateAccountWithHttpInfo(xeroTenantId, accountID, accounts).getData();
      }

  /**
   * Allows you to update a chart of accounts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for retrieving single object (required)
   * @param accounts Request of type Accounts array with one Account (required)
   * @return ApiResponse&lt;Accounts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Accounts> updateAccountWithHttpInfo(String xeroTenantId, UUID accountID, Accounts accounts) throws ApiException {
    Object localVarPostBody = accounts;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateAccount");
    }
    
    // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new ApiException(400, "Missing the required parameter 'accountID' when calling updateAccount");
    }
    
    // verify the required parameter 'accounts' is set
    if (accounts == null) {
      throw new ApiException(400, "Missing the required parameter 'accounts' when calling updateAccount");
    }
    
    // create path and map variables
    String localVarPath = "/Accounts/{AccountID}"
      .replaceAll("\\{" + "AccountID" + "\\}", apiClient.escapeString(accountID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Accounts> localVarReturnType = new GenericType<Accounts>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update Attachment on Account by Filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for Account object (required)
   * @param fileName Name of the attachment (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments updateAccountAttachmentByFileName(String xeroTenantId, UUID accountID, String fileName, byte[] body) throws ApiException {
    return updateAccountAttachmentByFileNameWithHttpInfo(xeroTenantId, accountID, fileName, body).getData();
      }

  /**
   * Allows you to update Attachment on Account by Filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param accountID Unique identifier for Account object (required)
   * @param fileName Name of the attachment (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> updateAccountAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID accountID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateAccountAttachmentByFileName");
    }
    
    // verify the required parameter 'accountID' is set
    if (accountID == null) {
      throw new ApiException(400, "Missing the required parameter 'accountID' when calling updateAccountAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling updateAccountAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateAccountAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/Accounts/{AccountID}/Attachments/{FileName}"
      .replaceAll("\\{" + "AccountID" + "\\}", apiClient.escapeString(accountID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update a single spend or receive money transaction
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @param bankTransactions  (required)
   * @return BankTransactions
   * @throws ApiException if fails to make API call
   */
  public BankTransactions updateBankTransaction(String xeroTenantId, UUID bankTransactionID, BankTransactions bankTransactions) throws ApiException {
    return updateBankTransactionWithHttpInfo(xeroTenantId, bankTransactionID, bankTransactions).getData();
      }

  /**
   * Allows you to update a single spend or receive money transaction
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @param bankTransactions  (required)
   * @return ApiResponse&lt;BankTransactions&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<BankTransactions> updateBankTransactionWithHttpInfo(String xeroTenantId, UUID bankTransactionID, BankTransactions bankTransactions) throws ApiException {
    Object localVarPostBody = bankTransactions;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateBankTransaction");
    }
    
    // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransactionID' when calling updateBankTransaction");
    }
    
    // verify the required parameter 'bankTransactions' is set
    if (bankTransactions == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransactions' when calling updateBankTransaction");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransactions/{BankTransactionID}"
      .replaceAll("\\{" + "BankTransactionID" + "\\}", apiClient.escapeString(bankTransactionID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<BankTransactions> localVarReturnType = new GenericType<BankTransactions>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update an Attachment on BankTransaction by Filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @param fileName The name of the file being attached (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments updateBankTransactionAttachmentByFileName(String xeroTenantId, UUID bankTransactionID, String fileName, byte[] body) throws ApiException {
    return updateBankTransactionAttachmentByFileNameWithHttpInfo(xeroTenantId, bankTransactionID, fileName, body).getData();
      }

  /**
   * Allows you to update an Attachment on BankTransaction by Filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransactionID Xero generated unique identifier for a bank transaction (required)
   * @param fileName The name of the file being attached (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> updateBankTransactionAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID bankTransactionID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateBankTransactionAttachmentByFileName");
    }
    
    // verify the required parameter 'bankTransactionID' is set
    if (bankTransactionID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransactionID' when calling updateBankTransactionAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling updateBankTransactionAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateBankTransactionAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransactions/{BankTransactionID}/Attachments/{FileName}"
      .replaceAll("\\{" + "BankTransactionID" + "\\}", apiClient.escapeString(bankTransactionID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @param fileName The name of the file being attached to a Bank Transfer (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments updateBankTransferAttachmentByFileName(String xeroTenantId, UUID bankTransferID, String fileName, byte[] body) throws ApiException {
    return updateBankTransferAttachmentByFileNameWithHttpInfo(xeroTenantId, bankTransferID, fileName, body).getData();
      }

  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param bankTransferID Xero generated unique identifier for a bank transfer (required)
   * @param fileName The name of the file being attached to a Bank Transfer (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> updateBankTransferAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID bankTransferID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateBankTransferAttachmentByFileName");
    }
    
    // verify the required parameter 'bankTransferID' is set
    if (bankTransferID == null) {
      throw new ApiException(400, "Missing the required parameter 'bankTransferID' when calling updateBankTransferAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling updateBankTransferAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateBankTransferAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/BankTransfers/{BankTransferID}/Attachments/{FileName}"
      .replaceAll("\\{" + "BankTransferID" + "\\}", apiClient.escapeString(bankTransferID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @param contacts an array of Contacts containing single Contact object with properties to update (required)
   * @return Contacts
   * @throws ApiException if fails to make API call
   */
  public Contacts updateContact(String xeroTenantId, UUID contactID, Contacts contacts) throws ApiException {
    return updateContactWithHttpInfo(xeroTenantId, contactID, contacts).getData();
      }

  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @param contacts an array of Contacts containing single Contact object with properties to update (required)
   * @return ApiResponse&lt;Contacts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Contacts> updateContactWithHttpInfo(String xeroTenantId, UUID contactID, Contacts contacts) throws ApiException {
    Object localVarPostBody = contacts;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateContact");
    }
    
    // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactID' when calling updateContact");
    }
    
    // verify the required parameter 'contacts' is set
    if (contacts == null) {
      throw new ApiException(400, "Missing the required parameter 'contacts' when calling updateContact");
    }
    
    // create path and map variables
    String localVarPath = "/Contacts/{ContactID}"
      .replaceAll("\\{" + "ContactID" + "\\}", apiClient.escapeString(contactID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Contacts> localVarReturnType = new GenericType<Contacts>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @param fileName Name for the file you are attaching (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments updateContactAttachmentByFileName(String xeroTenantId, UUID contactID, String fileName, byte[] body) throws ApiException {
    return updateContactAttachmentByFileNameWithHttpInfo(xeroTenantId, contactID, fileName, body).getData();
      }

  /**
   * 
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactID Unique identifier for a Contact (required)
   * @param fileName Name for the file you are attaching (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> updateContactAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID contactID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateContactAttachmentByFileName");
    }
    
    // verify the required parameter 'contactID' is set
    if (contactID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactID' when calling updateContactAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling updateContactAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateContactAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/Contacts/{ContactID}/Attachments/{FileName}"
      .replaceAll("\\{" + "ContactID" + "\\}", apiClient.escapeString(contactID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update a Contract Group
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactGroupID Unique identifier for a Contact Group (required)
   * @param contactGroups an array of Contact groups with Name of specific group to update (required)
   * @return ContactGroups
   * @throws ApiException if fails to make API call
   */
  public ContactGroups updateContactGroup(String xeroTenantId, UUID contactGroupID, ContactGroups contactGroups) throws ApiException {
    return updateContactGroupWithHttpInfo(xeroTenantId, contactGroupID, contactGroups).getData();
      }

  /**
   * Allows you to update a Contract Group
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param contactGroupID Unique identifier for a Contact Group (required)
   * @param contactGroups an array of Contact groups with Name of specific group to update (required)
   * @return ApiResponse&lt;ContactGroups&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ContactGroups> updateContactGroupWithHttpInfo(String xeroTenantId, UUID contactGroupID, ContactGroups contactGroups) throws ApiException {
    Object localVarPostBody = contactGroups;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateContactGroup");
    }
    
    // verify the required parameter 'contactGroupID' is set
    if (contactGroupID == null) {
      throw new ApiException(400, "Missing the required parameter 'contactGroupID' when calling updateContactGroup");
    }
    
    // verify the required parameter 'contactGroups' is set
    if (contactGroups == null) {
      throw new ApiException(400, "Missing the required parameter 'contactGroups' when calling updateContactGroup");
    }
    
    // create path and map variables
    String localVarPath = "/ContactGroups/{ContactGroupID}"
      .replaceAll("\\{" + "ContactGroupID" + "\\}", apiClient.escapeString(contactGroupID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ContactGroups> localVarReturnType = new GenericType<ContactGroups>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update a specific credit note
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param creditNotes an array of Credit Notes containing credit note details to update (required)
   * @return CreditNotes
   * @throws ApiException if fails to make API call
   */
  public CreditNotes updateCreditNote(String xeroTenantId, UUID creditNoteID, CreditNotes creditNotes) throws ApiException {
    return updateCreditNoteWithHttpInfo(xeroTenantId, creditNoteID, creditNotes).getData();
      }

  /**
   * Allows you to update a specific credit note
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param creditNotes an array of Credit Notes containing credit note details to update (required)
   * @return ApiResponse&lt;CreditNotes&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<CreditNotes> updateCreditNoteWithHttpInfo(String xeroTenantId, UUID creditNoteID, CreditNotes creditNotes) throws ApiException {
    Object localVarPostBody = creditNotes;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateCreditNote");
    }
    
    // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNoteID' when calling updateCreditNote");
    }
    
    // verify the required parameter 'creditNotes' is set
    if (creditNotes == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNotes' when calling updateCreditNote");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes/{CreditNoteID}"
      .replaceAll("\\{" + "CreditNoteID" + "\\}", apiClient.escapeString(creditNoteID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<CreditNotes> localVarReturnType = new GenericType<CreditNotes>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update Attachments on CreditNote by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param fileName Name of the file you are attaching to Credit Note (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments updateCreditNoteAttachmentByFileName(String xeroTenantId, UUID creditNoteID, String fileName, byte[] body) throws ApiException {
    return updateCreditNoteAttachmentByFileNameWithHttpInfo(xeroTenantId, creditNoteID, fileName, body).getData();
      }

  /**
   * Allows you to update Attachments on CreditNote by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param creditNoteID Unique identifier for a Credit Note (required)
   * @param fileName Name of the file you are attaching to Credit Note (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> updateCreditNoteAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID creditNoteID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateCreditNoteAttachmentByFileName");
    }
    
    // verify the required parameter 'creditNoteID' is set
    if (creditNoteID == null) {
      throw new ApiException(400, "Missing the required parameter 'creditNoteID' when calling updateCreditNoteAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling updateCreditNoteAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateCreditNoteAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/CreditNotes/{CreditNoteID}/Attachments/{FileName}"
      .replaceAll("\\{" + "CreditNoteID" + "\\}", apiClient.escapeString(creditNoteID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update a specific employee used in Xero payrun
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param employeeID Unique identifier for a Employee (required)
   * @param employees  (required)
   * @return Employees
   * @throws ApiException if fails to make API call
   */
  public Employees updateEmployee(String xeroTenantId, UUID employeeID, Employees employees) throws ApiException {
    return updateEmployeeWithHttpInfo(xeroTenantId, employeeID, employees).getData();
      }

  /**
   * Allows you to update a specific employee used in Xero payrun
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param employeeID Unique identifier for a Employee (required)
   * @param employees  (required)
   * @return ApiResponse&lt;Employees&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Employees> updateEmployeeWithHttpInfo(String xeroTenantId, UUID employeeID, Employees employees) throws ApiException {
    Object localVarPostBody = employees;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateEmployee");
    }
    
    // verify the required parameter 'employeeID' is set
    if (employeeID == null) {
      throw new ApiException(400, "Missing the required parameter 'employeeID' when calling updateEmployee");
    }
    
    // verify the required parameter 'employees' is set
    if (employees == null) {
      throw new ApiException(400, "Missing the required parameter 'employees' when calling updateEmployee");
    }
    
    // create path and map variables
    String localVarPath = "/Employees/{EmployeeID}"
      .replaceAll("\\{" + "EmployeeID" + "\\}", apiClient.escapeString(employeeID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Employees> localVarReturnType = new GenericType<Employees>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update specified expense claims
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param expenseClaimID Unique identifier for a ExpenseClaim (required)
   * @param expenseClaims  (required)
   * @return ExpenseClaims
   * @throws ApiException if fails to make API call
   */
  public ExpenseClaims updateExpenseClaim(String xeroTenantId, UUID expenseClaimID, ExpenseClaims expenseClaims) throws ApiException {
    return updateExpenseClaimWithHttpInfo(xeroTenantId, expenseClaimID, expenseClaims).getData();
      }

  /**
   * Allows you to update specified expense claims
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param expenseClaimID Unique identifier for a ExpenseClaim (required)
   * @param expenseClaims  (required)
   * @return ApiResponse&lt;ExpenseClaims&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ExpenseClaims> updateExpenseClaimWithHttpInfo(String xeroTenantId, UUID expenseClaimID, ExpenseClaims expenseClaims) throws ApiException {
    Object localVarPostBody = expenseClaims;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateExpenseClaim");
    }
    
    // verify the required parameter 'expenseClaimID' is set
    if (expenseClaimID == null) {
      throw new ApiException(400, "Missing the required parameter 'expenseClaimID' when calling updateExpenseClaim");
    }
    
    // verify the required parameter 'expenseClaims' is set
    if (expenseClaims == null) {
      throw new ApiException(400, "Missing the required parameter 'expenseClaims' when calling updateExpenseClaim");
    }
    
    // create path and map variables
    String localVarPath = "/ExpenseClaims/{ExpenseClaimID}"
      .replaceAll("\\{" + "ExpenseClaimID" + "\\}", apiClient.escapeString(expenseClaimID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ExpenseClaims> localVarReturnType = new GenericType<ExpenseClaims>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update a specified sales invoices or purchase bills
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param invoices  (required)
   * @return Invoices
   * @throws ApiException if fails to make API call
   */
  public Invoices updateInvoice(String xeroTenantId, UUID invoiceID, Invoices invoices) throws ApiException {
    return updateInvoiceWithHttpInfo(xeroTenantId, invoiceID, invoices).getData();
      }

  /**
   * Allows you to update a specified sales invoices or purchase bills
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param invoices  (required)
   * @return ApiResponse&lt;Invoices&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Invoices> updateInvoiceWithHttpInfo(String xeroTenantId, UUID invoiceID, Invoices invoices) throws ApiException {
    Object localVarPostBody = invoices;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateInvoice");
    }
    
    // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceID' when calling updateInvoice");
    }
    
    // verify the required parameter 'invoices' is set
    if (invoices == null) {
      throw new ApiException(400, "Missing the required parameter 'invoices' when calling updateInvoice");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices/{InvoiceID}"
      .replaceAll("\\{" + "InvoiceID" + "\\}", apiClient.escapeString(invoiceID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Invoices> localVarReturnType = new GenericType<Invoices>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update Attachment on invoices or purchase bills by it&#39;s filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param fileName Name of the file you are attaching (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments updateInvoiceAttachmentByFileName(String xeroTenantId, UUID invoiceID, String fileName, byte[] body) throws ApiException {
    return updateInvoiceAttachmentByFileNameWithHttpInfo(xeroTenantId, invoiceID, fileName, body).getData();
      }

  /**
   * Allows you to update Attachment on invoices or purchase bills by it&#39;s filename
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param invoiceID Unique identifier for an Invoice (required)
   * @param fileName Name of the file you are attaching (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> updateInvoiceAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID invoiceID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'invoiceID' is set
    if (invoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'invoiceID' when calling updateInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling updateInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateInvoiceAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/Invoices/{InvoiceID}/Attachments/{FileName}"
      .replaceAll("\\{" + "InvoiceID" + "\\}", apiClient.escapeString(invoiceID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to udpate a specified item
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param itemID Unique identifier for an Item (required)
   * @param items  (required)
   * @return Items
   * @throws ApiException if fails to make API call
   */
  public Items updateItem(String xeroTenantId, UUID itemID, Items items) throws ApiException {
    return updateItemWithHttpInfo(xeroTenantId, itemID, items).getData();
      }

  /**
   * Allows you to udpate a specified item
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param itemID Unique identifier for an Item (required)
   * @param items  (required)
   * @return ApiResponse&lt;Items&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Items> updateItemWithHttpInfo(String xeroTenantId, UUID itemID, Items items) throws ApiException {
    Object localVarPostBody = items;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateItem");
    }
    
    // verify the required parameter 'itemID' is set
    if (itemID == null) {
      throw new ApiException(400, "Missing the required parameter 'itemID' when calling updateItem");
    }
    
    // verify the required parameter 'items' is set
    if (items == null) {
      throw new ApiException(400, "Missing the required parameter 'items' when calling updateItem");
    }
    
    // create path and map variables
    String localVarPath = "/Items/{ItemID}"
      .replaceAll("\\{" + "ItemID" + "\\}", apiClient.escapeString(itemID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Items> localVarReturnType = new GenericType<Items>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update a specified linked transactions (billable expenses)
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param linkedTransactionID Unique identifier for a LinkedTransaction (required)
   * @param linkedTransactions  (required)
   * @return LinkedTransactions
   * @throws ApiException if fails to make API call
   */
  public LinkedTransactions updateLinkedTransaction(String xeroTenantId, UUID linkedTransactionID, LinkedTransactions linkedTransactions) throws ApiException {
    return updateLinkedTransactionWithHttpInfo(xeroTenantId, linkedTransactionID, linkedTransactions).getData();
      }

  /**
   * Allows you to update a specified linked transactions (billable expenses)
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param linkedTransactionID Unique identifier for a LinkedTransaction (required)
   * @param linkedTransactions  (required)
   * @return ApiResponse&lt;LinkedTransactions&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<LinkedTransactions> updateLinkedTransactionWithHttpInfo(String xeroTenantId, UUID linkedTransactionID, LinkedTransactions linkedTransactions) throws ApiException {
    Object localVarPostBody = linkedTransactions;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateLinkedTransaction");
    }
    
    // verify the required parameter 'linkedTransactionID' is set
    if (linkedTransactionID == null) {
      throw new ApiException(400, "Missing the required parameter 'linkedTransactionID' when calling updateLinkedTransaction");
    }
    
    // verify the required parameter 'linkedTransactions' is set
    if (linkedTransactions == null) {
      throw new ApiException(400, "Missing the required parameter 'linkedTransactions' when calling updateLinkedTransaction");
    }
    
    // create path and map variables
    String localVarPath = "/LinkedTransactions/{LinkedTransactionID}"
      .replaceAll("\\{" + "LinkedTransactionID" + "\\}", apiClient.escapeString(linkedTransactionID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<LinkedTransactions> localVarReturnType = new GenericType<LinkedTransactions>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update a specified manual journal
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @param manualJournals  (required)
   * @return ManualJournals
   * @throws ApiException if fails to make API call
   */
  public ManualJournals updateManualJournal(String xeroTenantId, UUID manualJournalID, ManualJournals manualJournals) throws ApiException {
    return updateManualJournalWithHttpInfo(xeroTenantId, manualJournalID, manualJournals).getData();
      }

  /**
   * Allows you to update a specified manual journal
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @param manualJournals  (required)
   * @return ApiResponse&lt;ManualJournals&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<ManualJournals> updateManualJournalWithHttpInfo(String xeroTenantId, UUID manualJournalID, ManualJournals manualJournals) throws ApiException {
    Object localVarPostBody = manualJournals;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateManualJournal");
    }
    
    // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new ApiException(400, "Missing the required parameter 'manualJournalID' when calling updateManualJournal");
    }
    
    // verify the required parameter 'manualJournals' is set
    if (manualJournals == null) {
      throw new ApiException(400, "Missing the required parameter 'manualJournals' when calling updateManualJournal");
    }
    
    // create path and map variables
    String localVarPath = "/ManualJournals/{ManualJournalID}"
      .replaceAll("\\{" + "ManualJournalID" + "\\}", apiClient.escapeString(manualJournalID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ManualJournals> localVarReturnType = new GenericType<ManualJournals>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update a specified Attachment on ManualJournal by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @param fileName The name of the file being attached to a ManualJournal (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments updateManualJournalAttachmentByFileName(String xeroTenantId, UUID manualJournalID, String fileName, byte[] body) throws ApiException {
    return updateManualJournalAttachmentByFileNameWithHttpInfo(xeroTenantId, manualJournalID, fileName, body).getData();
      }

  /**
   * Allows you to update a specified Attachment on ManualJournal by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param manualJournalID Unique identifier for a ManualJournal (required)
   * @param fileName The name of the file being attached to a ManualJournal (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> updateManualJournalAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID manualJournalID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateManualJournalAttachmentByFileName");
    }
    
    // verify the required parameter 'manualJournalID' is set
    if (manualJournalID == null) {
      throw new ApiException(400, "Missing the required parameter 'manualJournalID' when calling updateManualJournalAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling updateManualJournalAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateManualJournalAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/ManualJournals/{ManualJournalID}/Attachments/{FileName}"
      .replaceAll("\\{" + "ManualJournalID" + "\\}", apiClient.escapeString(manualJournalID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update a specified purchase order
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param purchaseOrderID Unique identifier for a PurchaseOrder (required)
   * @param purchaseOrders  (required)
   * @return PurchaseOrders
   * @throws ApiException if fails to make API call
   */
  public PurchaseOrders updatePurchaseOrder(String xeroTenantId, UUID purchaseOrderID, PurchaseOrders purchaseOrders) throws ApiException {
    return updatePurchaseOrderWithHttpInfo(xeroTenantId, purchaseOrderID, purchaseOrders).getData();
      }

  /**
   * Allows you to update a specified purchase order
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param purchaseOrderID Unique identifier for a PurchaseOrder (required)
   * @param purchaseOrders  (required)
   * @return ApiResponse&lt;PurchaseOrders&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<PurchaseOrders> updatePurchaseOrderWithHttpInfo(String xeroTenantId, UUID purchaseOrderID, PurchaseOrders purchaseOrders) throws ApiException {
    Object localVarPostBody = purchaseOrders;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updatePurchaseOrder");
    }
    
    // verify the required parameter 'purchaseOrderID' is set
    if (purchaseOrderID == null) {
      throw new ApiException(400, "Missing the required parameter 'purchaseOrderID' when calling updatePurchaseOrder");
    }
    
    // verify the required parameter 'purchaseOrders' is set
    if (purchaseOrders == null) {
      throw new ApiException(400, "Missing the required parameter 'purchaseOrders' when calling updatePurchaseOrder");
    }
    
    // create path and map variables
    String localVarPath = "/PurchaseOrders/{PurchaseOrderID}"
      .replaceAll("\\{" + "PurchaseOrderID" + "\\}", apiClient.escapeString(purchaseOrderID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<PurchaseOrders> localVarReturnType = new GenericType<PurchaseOrders>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to retrieve a specified draft expense claim receipts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @param receipts  (required)
   * @return Receipts
   * @throws ApiException if fails to make API call
   */
  public Receipts updateReceipt(String xeroTenantId, UUID receiptID, Receipts receipts) throws ApiException {
    return updateReceiptWithHttpInfo(xeroTenantId, receiptID, receipts).getData();
      }

  /**
   * Allows you to retrieve a specified draft expense claim receipts
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @param receipts  (required)
   * @return ApiResponse&lt;Receipts&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Receipts> updateReceiptWithHttpInfo(String xeroTenantId, UUID receiptID, Receipts receipts) throws ApiException {
    Object localVarPostBody = receipts;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateReceipt");
    }
    
    // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new ApiException(400, "Missing the required parameter 'receiptID' when calling updateReceipt");
    }
    
    // verify the required parameter 'receipts' is set
    if (receipts == null) {
      throw new ApiException(400, "Missing the required parameter 'receipts' when calling updateReceipt");
    }
    
    // create path and map variables
    String localVarPath = "/Receipts/{ReceiptID}"
      .replaceAll("\\{" + "ReceiptID" + "\\}", apiClient.escapeString(receiptID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Receipts> localVarReturnType = new GenericType<Receipts>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update Attachment on expense claim receipts by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @param fileName The name of the file being attached to the Receipt (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments updateReceiptAttachmentByFileName(String xeroTenantId, UUID receiptID, String fileName, byte[] body) throws ApiException {
    return updateReceiptAttachmentByFileNameWithHttpInfo(xeroTenantId, receiptID, fileName, body).getData();
      }

  /**
   * Allows you to update Attachment on expense claim receipts by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param receiptID Unique identifier for a Receipt (required)
   * @param fileName The name of the file being attached to the Receipt (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> updateReceiptAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID receiptID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateReceiptAttachmentByFileName");
    }
    
    // verify the required parameter 'receiptID' is set
    if (receiptID == null) {
      throw new ApiException(400, "Missing the required parameter 'receiptID' when calling updateReceiptAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling updateReceiptAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateReceiptAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/Receipts/{ReceiptID}/Attachments/{FileName}"
      .replaceAll("\\{" + "ReceiptID" + "\\}", apiClient.escapeString(receiptID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update specified attachment on repeating invoices by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @param fileName The name of the file being attached to a Repeating Invoice (required)
   * @param body Byte array of file in body of request (required)
   * @return Attachments
   * @throws ApiException if fails to make API call
   */
  public Attachments updateRepeatingInvoiceAttachmentByFileName(String xeroTenantId, UUID repeatingInvoiceID, String fileName, byte[] body) throws ApiException {
    return updateRepeatingInvoiceAttachmentByFileNameWithHttpInfo(xeroTenantId, repeatingInvoiceID, fileName, body).getData();
      }

  /**
   * Allows you to update specified attachment on repeating invoices by file name
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param repeatingInvoiceID Unique identifier for a Repeating Invoice (required)
   * @param fileName The name of the file being attached to a Repeating Invoice (required)
   * @param body Byte array of file in body of request (required)
   * @return ApiResponse&lt;Attachments&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Attachments> updateRepeatingInvoiceAttachmentByFileNameWithHttpInfo(String xeroTenantId, UUID repeatingInvoiceID, String fileName, byte[] body) throws ApiException {
    Object localVarPostBody = body;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateRepeatingInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'repeatingInvoiceID' is set
    if (repeatingInvoiceID == null) {
      throw new ApiException(400, "Missing the required parameter 'repeatingInvoiceID' when calling updateRepeatingInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'fileName' is set
    if (fileName == null) {
      throw new ApiException(400, "Missing the required parameter 'fileName' when calling updateRepeatingInvoiceAttachmentByFileName");
    }
    
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateRepeatingInvoiceAttachmentByFileName");
    }
    
    // create path and map variables
    String localVarPath = "/RepeatingInvoices/{RepeatingInvoiceID}/Attachments/{FileName}"
      .replaceAll("\\{" + "RepeatingInvoiceID" + "\\}", apiClient.escapeString(repeatingInvoiceID.toString()))
      .replaceAll("\\{" + "FileName" + "\\}", apiClient.escapeString(fileName.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/octet-stream"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<Attachments> localVarReturnType = new GenericType<Attachments>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update Tax Rates
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param taxRates  (required)
   * @return TaxRates
   * @throws ApiException if fails to make API call
   */
  public TaxRates updateTaxRate(String xeroTenantId, TaxRates taxRates) throws ApiException {
    return updateTaxRateWithHttpInfo(xeroTenantId, taxRates).getData();
      }

  /**
   * Allows you to update Tax Rates
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param taxRates  (required)
   * @return ApiResponse&lt;TaxRates&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<TaxRates> updateTaxRateWithHttpInfo(String xeroTenantId, TaxRates taxRates) throws ApiException {
    Object localVarPostBody = taxRates;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateTaxRate");
    }
    
    // verify the required parameter 'taxRates' is set
    if (taxRates == null) {
      throw new ApiException(400, "Missing the required parameter 'taxRates' when calling updateTaxRate");
    }
    
    // create path and map variables
    String localVarPath = "/TaxRates";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<TaxRates> localVarReturnType = new GenericType<TaxRates>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Allows you to update tracking categories
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param trackingCategoryID Unique identifier for a TrackingCategory (required)
   * @param trackingCategory  (required)
   * @return TrackingCategories
   * @throws ApiException if fails to make API call
   */
  public TrackingCategories updateTrackingCategory(String xeroTenantId, UUID trackingCategoryID, TrackingCategory trackingCategory) throws ApiException {
    return updateTrackingCategoryWithHttpInfo(xeroTenantId, trackingCategoryID, trackingCategory).getData();
      }

  /**
   * Allows you to update tracking categories
   * 
   * @param xeroTenantId Xero identifier for Tenant (required)
   * @param trackingCategoryID Unique identifier for a TrackingCategory (required)
   * @param trackingCategory  (required)
   * @return ApiResponse&lt;TrackingCategories&gt;
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<TrackingCategories> updateTrackingCategoryWithHttpInfo(String xeroTenantId, UUID trackingCategoryID, TrackingCategory trackingCategory) throws ApiException {
    Object localVarPostBody = trackingCategory;
    
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new ApiException(400, "Missing the required parameter 'xeroTenantId' when calling updateTrackingCategory");
    }
    
    // verify the required parameter 'trackingCategoryID' is set
    if (trackingCategoryID == null) {
      throw new ApiException(400, "Missing the required parameter 'trackingCategoryID' when calling updateTrackingCategory");
    }
    
    // verify the required parameter 'trackingCategory' is set
    if (trackingCategory == null) {
      throw new ApiException(400, "Missing the required parameter 'trackingCategory' when calling updateTrackingCategory");
    }
    
    // create path and map variables
    String localVarPath = "/TrackingCategories/{TrackingCategoryID}"
      .replaceAll("\\{" + "TrackingCategoryID" + "\\}", apiClient.escapeString(trackingCategoryID.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xeroTenantId != null)
      localVarHeaderParams.put("Xero-Tenant-Id", apiClient.parameterToString(xeroTenantId));

    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<TrackingCategories> localVarReturnType = new GenericType<TrackingCategories>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}
