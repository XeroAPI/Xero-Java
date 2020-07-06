package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.payrollau.Employee;
import com.xero.models.payrollau.Employees;
import com.xero.models.payrollau.LeaveApplication;
import com.xero.models.payrollau.LeaveApplications;
import com.xero.models.payrollau.PayItem;
import com.xero.models.payrollau.PayItems;
import com.xero.models.payrollau.PayRun;
import com.xero.models.payrollau.PayRuns;
import com.xero.models.payrollau.PayrollCalendar;
import com.xero.models.payrollau.PayrollCalendars;
import com.xero.models.payrollau.PayslipLines;
import com.xero.models.payrollau.PayslipObject;
import com.xero.models.payrollau.Payslips;
import com.xero.models.payrollau.SettingsObject;
import com.xero.models.payrollau.SuperFund;
import com.xero.models.payrollau.SuperFundProducts;
import com.xero.models.payrollau.SuperFunds;
import com.xero.models.payrollau.Timesheet;
import com.xero.models.payrollau.TimesheetObject;
import com.xero.models.payrollau.Timesheets;
import java.util.UUID;
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
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.apache.commons.io.IOUtils;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PayrollAuApi {
  private ApiClient apiClient;
  private static PayrollAuApi instance = null;
  private String userAgent = "Default";
  private String version = "4.1.1";
  static final Logger logger = LoggerFactory.getLogger(PayrollAuApi.class);

  public PayrollAuApi() {
    this(new ApiClient());
  }

  public static PayrollAuApi getInstance(ApiClient apiClient) {
    if (instance == null) {
      instance = new PayrollAuApi(apiClient);
    }
    return instance;
  }

  public PayrollAuApi(ApiClient apiClient) {
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
   * Use this method to create a payroll employee
   *
   * <p><b>200</b> - A successful request
   *
   * <p><b>400</b> - invalid input, object invalid - TODO
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employee The employee parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return Employees
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Employees createEmployee(String accessToken, String xeroTenantId, List<Employee> employee)
      throws IOException {
    try {
      TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
      HttpResponse response = createEmployeeForHttpResponse(accessToken, xeroTenantId, employee);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createEmployee -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<Employees> objectTypeRef = new TypeReference<Employees>() {};
        Employees object = apiClient.getObjectMapper().readValue(e.getContent(), objectTypeRef);
        if (object.getEmployees() == null || object.getEmployees().isEmpty()) {
          TypeReference<com.xero.models.accounting.Error> errorTypeRef =
              new TypeReference<com.xero.models.accounting.Error>() {};
          com.xero.models.accounting.Error error =
              apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
          handler.validationError("Error", error.getMessage());
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

  public HttpResponse createEmployeeForHttpResponse(
      String accessToken, String xeroTenantId, List<Employee> employee) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createEmployee");
    } // verify the required parameter 'employee' is set
    if (employee == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employee' when calling createEmployee");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createEmployee");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Employees");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employee);

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
   * Use this method to create a Leave Application
   *
   * <p><b>200</b> - A successful request
   *
   * <p><b>400</b> - invalid input, object invalid - TODO
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param leaveApplication The leaveApplication parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return LeaveApplications
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public LeaveApplications createLeaveApplication(
      String accessToken, String xeroTenantId, List<LeaveApplication> leaveApplication)
      throws IOException {
    try {
      TypeReference<LeaveApplications> typeRef = new TypeReference<LeaveApplications>() {};
      HttpResponse response =
          createLeaveApplicationForHttpResponse(accessToken, xeroTenantId, leaveApplication);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createLeaveApplication -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<LeaveApplications> objectTypeRef = new TypeReference<LeaveApplications>() {};
        LeaveApplications object =
            apiClient.getObjectMapper().readValue(e.getContent(), objectTypeRef);
        if (object.getLeaveApplications() == null || object.getLeaveApplications().isEmpty()) {
          TypeReference<com.xero.models.accounting.Error> errorTypeRef =
              new TypeReference<com.xero.models.accounting.Error>() {};
          com.xero.models.accounting.Error error =
              apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
          handler.validationError("Error", error.getMessage());
        }
        handler.validationError("LeaveApplications", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createLeaveApplicationForHttpResponse(
      String accessToken, String xeroTenantId, List<LeaveApplication> leaveApplication)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createLeaveApplication");
    } // verify the required parameter 'leaveApplication' is set
    if (leaveApplication == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'leaveApplication' when calling createLeaveApplication");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createLeaveApplication");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/LeaveApplications");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(leaveApplication);

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
   * Use this method to create a Pay Item
   *
   * <p><b>200</b> - A successful request - currently returns empty array for JSON
   *
   * <p><b>400</b> - invalid input, object invalid - TODO
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payItem The payItem parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return PayItems
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayItems createPayItem(String accessToken, String xeroTenantId, PayItem payItem)
      throws IOException {
    try {
      TypeReference<PayItems> typeRef = new TypeReference<PayItems>() {};
      HttpResponse response = createPayItemForHttpResponse(accessToken, xeroTenantId, payItem);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPayItem -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPayItemForHttpResponse(
      String accessToken, String xeroTenantId, PayItem payItem) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPayItem");
    } // verify the required parameter 'payItem' is set
    if (payItem == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payItem' when calling createPayItem");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPayItem");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayItems");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(payItem);

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
   * Use this method to create a PayRun
   *
   * <p><b>200</b> - A successful request
   *
   * <p><b>400</b> - invalid input, object invalid - TODO
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payRun The payRun parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return PayRuns
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayRuns createPayRun(String accessToken, String xeroTenantId, List<PayRun> payRun)
      throws IOException {
    try {
      TypeReference<PayRuns> typeRef = new TypeReference<PayRuns>() {};
      HttpResponse response = createPayRunForHttpResponse(accessToken, xeroTenantId, payRun);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPayRun -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PayRuns> objectTypeRef = new TypeReference<PayRuns>() {};
        PayRuns object = apiClient.getObjectMapper().readValue(e.getContent(), objectTypeRef);
        if (object.getPayRuns() == null || object.getPayRuns().isEmpty()) {
          TypeReference<com.xero.models.accounting.Error> errorTypeRef =
              new TypeReference<com.xero.models.accounting.Error>() {};
          com.xero.models.accounting.Error error =
              apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
          handler.validationError("Error", error.getMessage());
        }
        handler.validationError("PayRuns", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPayRunForHttpResponse(
      String accessToken, String xeroTenantId, List<PayRun> payRun) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPayRun");
    } // verify the required parameter 'payRun' is set
    if (payRun == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payRun' when calling createPayRun");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPayRun");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayRuns");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(payRun);

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
   * Use this method to create a Payroll Calendars
   *
   * <p><b>200</b> - A successful request
   *
   * <p><b>400</b> - invalid input, object invalid - TODO
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payrollCalendar The payrollCalendar parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return PayrollCalendars
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayrollCalendars createPayrollCalendar(
      String accessToken, String xeroTenantId, List<PayrollCalendar> payrollCalendar)
      throws IOException {
    try {
      TypeReference<PayrollCalendars> typeRef = new TypeReference<PayrollCalendars>() {};
      HttpResponse response =
          createPayrollCalendarForHttpResponse(accessToken, xeroTenantId, payrollCalendar);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createPayrollCalendar -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<PayrollCalendars> objectTypeRef = new TypeReference<PayrollCalendars>() {};
        PayrollCalendars object =
            apiClient.getObjectMapper().readValue(e.getContent(), objectTypeRef);
        if (object.getPayrollCalendars() == null || object.getPayrollCalendars().isEmpty()) {
          TypeReference<com.xero.models.accounting.Error> errorTypeRef =
              new TypeReference<com.xero.models.accounting.Error>() {};
          com.xero.models.accounting.Error error =
              apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
          handler.validationError("Error", error.getMessage());
        }
        handler.validationError("PayrollCalendars", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createPayrollCalendarForHttpResponse(
      String accessToken, String xeroTenantId, List<PayrollCalendar> payrollCalendar)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createPayrollCalendar");
    } // verify the required parameter 'payrollCalendar' is set
    if (payrollCalendar == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payrollCalendar' when calling createPayrollCalendar");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createPayrollCalendar");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayrollCalendars");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(payrollCalendar);

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
   * Use this method to create a super fund
   *
   * <p><b>200</b> - A successful request
   *
   * <p><b>400</b> - invalid input, object invalid - TODO
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param superFund The superFund parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return SuperFunds
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SuperFunds createSuperfund(
      String accessToken, String xeroTenantId, List<SuperFund> superFund) throws IOException {
    try {
      TypeReference<SuperFunds> typeRef = new TypeReference<SuperFunds>() {};
      HttpResponse response = createSuperfundForHttpResponse(accessToken, xeroTenantId, superFund);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createSuperfund -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<SuperFunds> objectTypeRef = new TypeReference<SuperFunds>() {};
        SuperFunds object = apiClient.getObjectMapper().readValue(e.getContent(), objectTypeRef);
        if (object.getSuperFunds() == null || object.getSuperFunds().isEmpty()) {
          TypeReference<com.xero.models.accounting.Error> errorTypeRef =
              new TypeReference<com.xero.models.accounting.Error>() {};
          com.xero.models.accounting.Error error =
              apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
          handler.validationError("Error", error.getMessage());
        }
        handler.validationError("SuperFunds", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createSuperfundForHttpResponse(
      String accessToken, String xeroTenantId, List<SuperFund> superFund) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createSuperfund");
    } // verify the required parameter 'superFund' is set
    if (superFund == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'superFund' when calling createSuperfund");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createSuperfund");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Superfunds");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(superFund);

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
   * Use this method to create a timesheet
   *
   * <p><b>200</b> - A successful request
   *
   * <p><b>400</b> - invalid input, object invalid - TODO
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param timesheet The timesheet parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return Timesheets
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Timesheets createTimesheet(
      String accessToken, String xeroTenantId, List<Timesheet> timesheet) throws IOException {
    try {
      TypeReference<Timesheets> typeRef = new TypeReference<Timesheets>() {};
      HttpResponse response = createTimesheetForHttpResponse(accessToken, xeroTenantId, timesheet);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createTimesheet -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<Timesheets> objectTypeRef = new TypeReference<Timesheets>() {};
        Timesheets object = apiClient.getObjectMapper().readValue(e.getContent(), objectTypeRef);
        if (object.getTimesheets() == null || object.getTimesheets().isEmpty()) {
          TypeReference<com.xero.models.accounting.Error> errorTypeRef =
              new TypeReference<com.xero.models.accounting.Error>() {};
          com.xero.models.accounting.Error error =
              apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
          handler.validationError("Error", error.getMessage());
        }
        handler.validationError("Timesheets", object);
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createTimesheetForHttpResponse(
      String accessToken, String xeroTenantId, List<Timesheet> timesheet) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createTimesheet");
    } // verify the required parameter 'timesheet' is set
    if (timesheet == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheet' when calling createTimesheet");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createTimesheet");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Timesheets");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(timesheet);

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
   * searches for an employee by unique id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return Employees
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Employees getEmployee(String accessToken, String xeroTenantId, UUID employeeId)
      throws IOException {
    try {
      TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
      HttpResponse response = getEmployeeForHttpResponse(accessToken, xeroTenantId, employeeId);
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
      String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getEmployee");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling getEmployee");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getEmployee");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}");
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
   * searches employees
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page e.g. page&#x3D;1 – Up to 100 employees will be returned in a single API call
   * @param accessToken Authorization token for user set in header of each request
   * @return Employees
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Employees getEmployees(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    try {
      TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
      HttpResponse response =
          getEmployeesForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, page);
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
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
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
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.set("If-Modified-Since", ifModifiedSince);
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
   * searches for an Leave Application by unique id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param leaveApplicationId Leave Application id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return LeaveApplications
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public LeaveApplications getLeaveApplication(
      String accessToken, String xeroTenantId, UUID leaveApplicationId) throws IOException {
    try {
      TypeReference<LeaveApplications> typeRef = new TypeReference<LeaveApplications>() {};
      HttpResponse response =
          getLeaveApplicationForHttpResponse(accessToken, xeroTenantId, leaveApplicationId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getLeaveApplication -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getLeaveApplicationForHttpResponse(
      String accessToken, String xeroTenantId, UUID leaveApplicationId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getLeaveApplication");
    } // verify the required parameter 'leaveApplicationId' is set
    if (leaveApplicationId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'leaveApplicationId' when calling getLeaveApplication");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getLeaveApplication");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("LeaveApplicationId", leaveApplicationId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/LeaveApplications/{LeaveApplicationId}");
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
   * searches Leave Applications
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page e.g. page&#x3D;1 – Up to 100 objects will be returned in a single API call
   * @param accessToken Authorization token for user set in header of each request
   * @return LeaveApplications
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public LeaveApplications getLeaveApplications(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    try {
      TypeReference<LeaveApplications> typeRef = new TypeReference<LeaveApplications>() {};
      HttpResponse response =
          getLeaveApplicationsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getLeaveApplications -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getLeaveApplicationsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getLeaveApplications");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getLeaveApplications");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.set("If-Modified-Since", ifModifiedSince);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/LeaveApplications");
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
   * searches Pay Items
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page e.g. page&#x3D;1 – Up to 100 objects will be returned in a single API call
   * @param accessToken Authorization token for user set in header of each request
   * @return PayItems
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayItems getPayItems(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    try {
      TypeReference<PayItems> typeRef = new TypeReference<PayItems>() {};
      HttpResponse response =
          getPayItemsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPayItems -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPayItemsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPayItems");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPayItems");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.set("If-Modified-Since", ifModifiedSince);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayItems");
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
   * searches for an payrun by unique id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payRunID PayRun id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return PayRuns
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayRuns getPayRun(String accessToken, String xeroTenantId, UUID payRunID)
      throws IOException {
    try {
      TypeReference<PayRuns> typeRef = new TypeReference<PayRuns>() {};
      HttpResponse response = getPayRunForHttpResponse(accessToken, xeroTenantId, payRunID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPayRun -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPayRunForHttpResponse(
      String accessToken, String xeroTenantId, UUID payRunID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPayRun");
    } // verify the required parameter 'payRunID' is set
    if (payRunID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payRunID' when calling getPayRun");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPayRun");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PayRunID", payRunID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayRuns/{PayRunID}");
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
   * searches PayRuns
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page e.g. page&#x3D;1 – Up to 100 PayRuns will be returned in a single API call
   * @param accessToken Authorization token for user set in header of each request
   * @return PayRuns
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayRuns getPayRuns(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    try {
      TypeReference<PayRuns> typeRef = new TypeReference<PayRuns>() {};
      HttpResponse response =
          getPayRunsForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPayRuns -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPayRunsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPayRuns");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPayRuns");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.set("If-Modified-Since", ifModifiedSince);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayRuns");
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
   * searches Payroll Calendars
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payrollCalendarID Payroll Calendar id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return PayrollCalendars
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayrollCalendars getPayrollCalendar(
      String accessToken, String xeroTenantId, UUID payrollCalendarID) throws IOException {
    try {
      TypeReference<PayrollCalendars> typeRef = new TypeReference<PayrollCalendars>() {};
      HttpResponse response =
          getPayrollCalendarForHttpResponse(accessToken, xeroTenantId, payrollCalendarID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPayrollCalendar -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPayrollCalendarForHttpResponse(
      String accessToken, String xeroTenantId, UUID payrollCalendarID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPayrollCalendar");
    } // verify the required parameter 'payrollCalendarID' is set
    if (payrollCalendarID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payrollCalendarID' when calling getPayrollCalendar");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPayrollCalendar");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PayrollCalendarID", payrollCalendarID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/PayrollCalendars/{PayrollCalendarID}");
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
   * searches Payroll Calendars
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page e.g. page&#x3D;1 – Up to 100 objects will be returned in a single API call
   * @param accessToken Authorization token for user set in header of each request
   * @return PayrollCalendars
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayrollCalendars getPayrollCalendars(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    try {
      TypeReference<PayrollCalendars> typeRef = new TypeReference<PayrollCalendars>() {};
      HttpResponse response =
          getPayrollCalendarsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPayrollCalendars -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPayrollCalendarsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPayrollCalendars");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPayrollCalendars");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.set("If-Modified-Since", ifModifiedSince);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayrollCalendars");
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
   * searches for an payslip by unique id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payslipID Payslip id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return PayslipObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayslipObject getPayslip(String accessToken, String xeroTenantId, UUID payslipID)
      throws IOException {
    try {
      TypeReference<PayslipObject> typeRef = new TypeReference<PayslipObject>() {};
      HttpResponse response = getPayslipForHttpResponse(accessToken, xeroTenantId, payslipID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getPayslip -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getPayslipForHttpResponse(
      String accessToken, String xeroTenantId, UUID payslipID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getPayslip");
    } // verify the required parameter 'payslipID' is set
    if (payslipID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payslipID' when calling getPayslip");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getPayslip");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PayslipID", payslipID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Payslip/{PayslipID}");
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
   * retrieve settings
   *
   * <p><b>200</b> - payroll settings
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param accessToken Authorization token for user set in header of each request
   * @return SettingsObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SettingsObject getSettings(String accessToken, String xeroTenantId) throws IOException {
    try {
      TypeReference<SettingsObject> typeRef = new TypeReference<SettingsObject>() {};
      HttpResponse response = getSettingsForHttpResponse(accessToken, xeroTenantId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getSettings -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getSettingsForHttpResponse(String accessToken, String xeroTenantId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getSettings");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getSettings");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Settings");
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
   * searches for an Superfund by unique id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param superFundID Superfund id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return SuperFunds
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SuperFunds getSuperfund(String accessToken, String xeroTenantId, UUID superFundID)
      throws IOException {
    try {
      TypeReference<SuperFunds> typeRef = new TypeReference<SuperFunds>() {};
      HttpResponse response = getSuperfundForHttpResponse(accessToken, xeroTenantId, superFundID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getSuperfund -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getSuperfundForHttpResponse(
      String accessToken, String xeroTenantId, UUID superFundID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getSuperfund");
    } // verify the required parameter 'superFundID' is set
    if (superFundID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'superFundID' when calling getSuperfund");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getSuperfund");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("SuperFundID", superFundID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Superfunds/{SuperFundID}");
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
   * searches SuperfundProducts
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ABN The ABN of the Regulated SuperFund
   * @param USI The USI of the Regulated SuperFund
   * @param accessToken Authorization token for user set in header of each request
   * @return SuperFundProducts
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SuperFundProducts getSuperfundProducts(
      String accessToken, String xeroTenantId, String ABN, String USI) throws IOException {
    try {
      TypeReference<SuperFundProducts> typeRef = new TypeReference<SuperFundProducts>() {};
      HttpResponse response =
          getSuperfundProductsForHttpResponse(accessToken, xeroTenantId, ABN, USI);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getSuperfundProducts -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getSuperfundProductsForHttpResponse(
      String accessToken, String xeroTenantId, String ABN, String USI) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getSuperfundProducts");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getSuperfundProducts");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/SuperfundProducts");
    if (ABN != null) {
      String key = "ABN";
      Object value = ABN;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (USI != null) {
      String key = "USI";
      Object value = USI;
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
   * searches SuperFunds
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page e.g. page&#x3D;1 – Up to 100 SuperFunds will be returned in a single API call
   * @param accessToken Authorization token for user set in header of each request
   * @return SuperFunds
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SuperFunds getSuperfunds(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    try {
      TypeReference<SuperFunds> typeRef = new TypeReference<SuperFunds>() {};
      HttpResponse response =
          getSuperfundsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getSuperfunds -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getSuperfundsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getSuperfunds");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getSuperfunds");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.set("If-Modified-Since", ifModifiedSince);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Superfunds");
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
   * searches for an timesheet by unique id
   *
   * <p><b>200</b> - search results matching criteria
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param timesheetID Timesheet id for single object
   * @param accessToken Authorization token for user set in header of each request
   * @return TimesheetObject
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TimesheetObject getTimesheet(String accessToken, String xeroTenantId, UUID timesheetID)
      throws IOException {
    try {
      TypeReference<TimesheetObject> typeRef = new TypeReference<TimesheetObject>() {};
      HttpResponse response = getTimesheetForHttpResponse(accessToken, xeroTenantId, timesheetID);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getTimesheet -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getTimesheetForHttpResponse(
      String accessToken, String xeroTenantId, UUID timesheetID) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getTimesheet");
    } // verify the required parameter 'timesheetID' is set
    if (timesheetID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetID' when calling getTimesheet");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getTimesheet");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TimesheetID", timesheetID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Timesheets/{TimesheetID}");
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
   * searches timesheets
   *
   * <p><b>200</b> - search results matching criteria
   *
   * <p><b>400</b> - validation error for a bad request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param ifModifiedSince Only records created or modified since this timestamp will be returned
   * @param where Filter by an any element
   * @param order Order by an any element
   * @param page e.g. page&#x3D;1 – Up to 100 timesheets will be returned in a single API call
   * @param accessToken Authorization token for user set in header of each request
   * @return Timesheets
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Timesheets getTimesheets(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    try {
      TypeReference<Timesheets> typeRef = new TypeReference<Timesheets>() {};
      HttpResponse response =
          getTimesheetsForHttpResponse(
              accessToken, xeroTenantId, ifModifiedSince, where, order, page);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getTimesheets -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getTimesheetsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      String ifModifiedSince,
      String where,
      String order,
      Integer page)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getTimesheets");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getTimesheets");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.set("If-Modified-Since", ifModifiedSince);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Timesheets");
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
   * Update an Employee Update properties on a single employee
   *
   * <p><b>200</b> - A successful request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param employeeId Employee id for single object
   * @param employee The employee parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return Employees
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Employees updateEmployee(
      String accessToken, String xeroTenantId, UUID employeeId, List<Employee> employee)
      throws IOException {
    try {
      TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
      HttpResponse response =
          updateEmployeeForHttpResponse(accessToken, xeroTenantId, employeeId, employee);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateEmployee -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateEmployeeForHttpResponse(
      String accessToken, String xeroTenantId, UUID employeeId, List<Employee> employee)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateEmployee");
    } // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'employeeId' when calling updateEmployee");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateEmployee");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("EmployeeId", employeeId);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Employees/{EmployeeId}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(employee);

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
   * Use this method to update a Leave Application
   *
   * <p><b>200</b> - A successful request
   *
   * <p><b>400</b> - invalid input, object invalid - TODO
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param leaveApplicationId Leave Application id for single object
   * @param leaveApplication The leaveApplication parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return LeaveApplications
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public LeaveApplications updateLeaveApplication(
      String accessToken,
      String xeroTenantId,
      UUID leaveApplicationId,
      List<LeaveApplication> leaveApplication)
      throws IOException {
    try {
      TypeReference<LeaveApplications> typeRef = new TypeReference<LeaveApplications>() {};
      HttpResponse response =
          updateLeaveApplicationForHttpResponse(
              accessToken, xeroTenantId, leaveApplicationId, leaveApplication);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateLeaveApplication -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateLeaveApplicationForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID leaveApplicationId,
      List<LeaveApplication> leaveApplication)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateLeaveApplication");
    } // verify the required parameter 'leaveApplicationId' is set
    if (leaveApplicationId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'leaveApplicationId' when calling"
              + " updateLeaveApplication");
    } // verify the required parameter 'leaveApplication' is set
    if (leaveApplication == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'leaveApplication' when calling updateLeaveApplication");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateLeaveApplication");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("LeaveApplicationId", leaveApplicationId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/LeaveApplications/{LeaveApplicationId}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(leaveApplication);

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
   * Update a PayRun Update properties on a single PayRun
   *
   * <p><b>200</b> - A successful request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payRunID PayRun id for single object
   * @param payRun The payRun parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return PayRuns
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public PayRuns updatePayRun(
      String accessToken, String xeroTenantId, UUID payRunID, List<PayRun> payRun)
      throws IOException {
    try {
      TypeReference<PayRuns> typeRef = new TypeReference<PayRuns>() {};
      HttpResponse response =
          updatePayRunForHttpResponse(accessToken, xeroTenantId, payRunID, payRun);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updatePayRun -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updatePayRunForHttpResponse(
      String accessToken, String xeroTenantId, UUID payRunID, List<PayRun> payRun)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updatePayRun");
    } // verify the required parameter 'payRunID' is set
    if (payRunID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payRunID' when calling updatePayRun");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updatePayRun");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PayRunID", payRunID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/PayRuns/{PayRunID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(payRun);

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
   * Update a Payslip Update lines on a single payslips
   *
   * <p><b>200</b> - A successful request - currently returns empty array for JSON
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param payslipID Payslip id for single object
   * @param payslipLines The payslipLines parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return Payslips
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Payslips updatePayslip(
      String accessToken, String xeroTenantId, UUID payslipID, List<PayslipLines> payslipLines)
      throws IOException {
    try {
      TypeReference<Payslips> typeRef = new TypeReference<Payslips>() {};
      HttpResponse response =
          updatePayslipForHttpResponse(accessToken, xeroTenantId, payslipID, payslipLines);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updatePayslip -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      if (e.getStatusCode() == 400 || e.getStatusCode() == 405) {
        TypeReference<com.xero.models.accounting.Error> errorTypeRef =
            new TypeReference<com.xero.models.accounting.Error>() {};
        com.xero.models.accounting.Error error =
            apiClient.getObjectMapper().readValue(e.getContent(), errorTypeRef);
        handler.validationError("Error", error.getMessage());
      } else {
        handler.execute(e);
      }
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updatePayslipForHttpResponse(
      String accessToken, String xeroTenantId, UUID payslipID, List<PayslipLines> payslipLines)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updatePayslip");
    } // verify the required parameter 'payslipID' is set
    if (payslipID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'payslipID' when calling updatePayslip");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updatePayslip");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("PayslipID", payslipID);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/Payslip/{PayslipID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(payslipLines);

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
   * Update a Superfund Update properties on a single Superfund
   *
   * <p><b>200</b> - A successful request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param superFundID Superfund id for single object
   * @param superFund The superFund parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return SuperFunds
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public SuperFunds updateSuperfund(
      String accessToken, String xeroTenantId, UUID superFundID, List<SuperFund> superFund)
      throws IOException {
    try {
      TypeReference<SuperFunds> typeRef = new TypeReference<SuperFunds>() {};
      HttpResponse response =
          updateSuperfundForHttpResponse(accessToken, xeroTenantId, superFundID, superFund);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateSuperfund -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateSuperfundForHttpResponse(
      String accessToken, String xeroTenantId, UUID superFundID, List<SuperFund> superFund)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateSuperfund");
    } // verify the required parameter 'superFundID' is set
    if (superFundID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'superFundID' when calling updateSuperfund");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateSuperfund");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("SuperFundID", superFundID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Superfunds/{SuperFundID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(superFund);

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
   * Update a Timesheet Update properties on a single timesheet
   *
   * <p><b>200</b> - A successful request
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param timesheetID Timesheet id for single object
   * @param timesheet The timesheet parameter
   * @param accessToken Authorization token for user set in header of each request
   * @return Timesheets
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Timesheets updateTimesheet(
      String accessToken, String xeroTenantId, UUID timesheetID, List<Timesheet> timesheet)
      throws IOException {
    try {
      TypeReference<Timesheets> typeRef = new TypeReference<Timesheets>() {};
      HttpResponse response =
          updateTimesheetForHttpResponse(accessToken, xeroTenantId, timesheetID, timesheet);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateTimesheet -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse updateTimesheetForHttpResponse(
      String accessToken, String xeroTenantId, UUID timesheetID, List<Timesheet> timesheet)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateTimesheet");
    } // verify the required parameter 'timesheetID' is set
    if (timesheetID == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timesheetID' when calling updateTimesheet");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateTimesheet");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("TimesheetID", timesheetID);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/Timesheets/{TimesheetID}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(timesheet);

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
