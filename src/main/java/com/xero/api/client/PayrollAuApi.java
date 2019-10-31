package com.xero.api.client;

import com.xero.api.ApiClient;

import com.xero.models.payrollau.Employee;
import com.xero.models.payrollau.Employees;
import com.xero.models.payrollau.LeaveApplication;
import com.xero.models.payrollau.LeaveApplications;
import com.xero.models.payrollau.ModelAPIException;
import org.threeten.bp.OffsetDateTime;
import com.xero.models.payrollau.PayItems;
import com.xero.models.payrollau.PayrollCalendars;
import com.xero.models.payrollau.Payruns;
import com.xero.models.payrollau.Payslip;
import com.xero.models.payrollau.Settings;
import com.xero.models.payrollau.SuperfundProducts;
import com.xero.models.payrollau.Superfunds;
import com.xero.models.payrollau.Timesheets;
import java.util.UUID;
import com.xero.api.XeroApiException;
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
import com.google.api.client.http.javanet.NetHttpTransport;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PayrollAuApi {
    private ApiClient apiClient;
    private static PayrollAuApi instance = null;
    private String userAgent = "Default";
    private String version = "3.1.0";

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
        return this.userAgent +  "[Xero-Java-" + this.version + "]";
    }

  /**
    * Use this method to create a payroll employee
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - invalid input, object invalid - TODO
    * @param xeroTenantId Xero identifier for Tenant
    * @param employee The employee parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees  createEmployee(String accessToken, String xeroTenantId, List<Employee> employee) throws IOException {
        try {
            TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
            HttpResponse response = createEmployeeForHttpResponse(accessToken, xeroTenantId, employee);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse createEmployeeForHttpResponse(String accessToken,  String xeroTenantId,  List<Employee> employee) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createEmployee");
        }// verify the required parameter 'employee' is set
        if (employee == null) {
            throw new IllegalArgumentException("Missing the required parameter 'employee' when calling createEmployee");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling createEmployee");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Employees";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(employee);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Use this method to create a Leave Application
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - invalid input, object invalid - TODO
    * @param xeroTenantId Xero identifier for Tenant
    * @param leaveApplication The leaveApplication parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return LeaveApplications
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LeaveApplications  createLeaveApplication(String accessToken, String xeroTenantId, List<LeaveApplication> leaveApplication) throws IOException {
        try {
            TypeReference<LeaveApplications> typeRef = new TypeReference<LeaveApplications>() {};
            HttpResponse response = createLeaveApplicationForHttpResponse(accessToken, xeroTenantId, leaveApplication);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse createLeaveApplicationForHttpResponse(String accessToken,  String xeroTenantId,  List<LeaveApplication> leaveApplication) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createLeaveApplication");
        }// verify the required parameter 'leaveApplication' is set
        if (leaveApplication == null) {
            throw new IllegalArgumentException("Missing the required parameter 'leaveApplication' when calling createLeaveApplication");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling createLeaveApplication");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/LeaveApplications";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(leaveApplication);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Use this method to create a Pay Item
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - invalid input, object invalid - TODO
    * @param xeroTenantId Xero identifier for Tenant
    * @param payItems The payItems parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return PayItems
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PayItems  createPayItem(String accessToken, String xeroTenantId, List<PayItems> payItems) throws IOException {
        try {
            TypeReference<PayItems> typeRef = new TypeReference<PayItems>() {};
            HttpResponse response = createPayItemForHttpResponse(accessToken, xeroTenantId, payItems);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse createPayItemForHttpResponse(String accessToken,  String xeroTenantId,  List<PayItems> payItems) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createPayItem");
        }// verify the required parameter 'payItems' is set
        if (payItems == null) {
            throw new IllegalArgumentException("Missing the required parameter 'payItems' when calling createPayItem");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling createPayItem");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PayItems";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(payItems);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Use this method to create a Payroll Calendars
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - invalid input, object invalid - TODO
    * @param xeroTenantId Xero identifier for Tenant
    * @param payrollCalendars The payrollCalendars parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return PayrollCalendars
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PayrollCalendars  createPayrollCalendar(String accessToken, String xeroTenantId, List<PayrollCalendars> payrollCalendars) throws IOException {
        try {
            TypeReference<PayrollCalendars> typeRef = new TypeReference<PayrollCalendars>() {};
            HttpResponse response = createPayrollCalendarForHttpResponse(accessToken, xeroTenantId, payrollCalendars);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse createPayrollCalendarForHttpResponse(String accessToken,  String xeroTenantId,  List<PayrollCalendars> payrollCalendars) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createPayrollCalendar");
        }// verify the required parameter 'payrollCalendars' is set
        if (payrollCalendars == null) {
            throw new IllegalArgumentException("Missing the required parameter 'payrollCalendars' when calling createPayrollCalendar");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling createPayrollCalendar");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PayrollCalendars";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(payrollCalendars);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Use this method to create a payrun
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - invalid input, object invalid - TODO
    * @param xeroTenantId Xero identifier for Tenant
    * @param payruns The payruns parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return Payruns
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payruns  createPayrun(String accessToken, String xeroTenantId, List<Payruns> payruns) throws IOException {
        try {
            TypeReference<Payruns> typeRef = new TypeReference<Payruns>() {};
            HttpResponse response = createPayrunForHttpResponse(accessToken, xeroTenantId, payruns);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse createPayrunForHttpResponse(String accessToken,  String xeroTenantId,  List<Payruns> payruns) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createPayrun");
        }// verify the required parameter 'payruns' is set
        if (payruns == null) {
            throw new IllegalArgumentException("Missing the required parameter 'payruns' when calling createPayrun");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling createPayrun");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payruns";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(payruns);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Use this method to create a super fund
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - invalid input, object invalid - TODO
    * @param xeroTenantId Xero identifier for Tenant
    * @param superfunds The superfunds parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return Superfunds
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Superfunds  createSuperfund(String accessToken, String xeroTenantId, List<Superfunds> superfunds) throws IOException {
        try {
            TypeReference<Superfunds> typeRef = new TypeReference<Superfunds>() {};
            HttpResponse response = createSuperfundForHttpResponse(accessToken, xeroTenantId, superfunds);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse createSuperfundForHttpResponse(String accessToken,  String xeroTenantId,  List<Superfunds> superfunds) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createSuperfund");
        }// verify the required parameter 'superfunds' is set
        if (superfunds == null) {
            throw new IllegalArgumentException("Missing the required parameter 'superfunds' when calling createSuperfund");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling createSuperfund");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Superfunds";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(superfunds);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Use this method to create a timesheet
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - invalid input, object invalid - TODO
    * @param xeroTenantId Xero identifier for Tenant
    * @param timesheets The timesheets parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return Timesheets
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Timesheets  createTimesheet(String accessToken, String xeroTenantId, Timesheets timesheets) throws IOException {
        try {
            TypeReference<Timesheets> typeRef = new TypeReference<Timesheets>() {};
            HttpResponse response = createTimesheetForHttpResponse(accessToken, xeroTenantId, timesheets);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse createTimesheetForHttpResponse(String accessToken,  String xeroTenantId,  Timesheets timesheets) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling createTimesheet");
        }// verify the required parameter 'timesheets' is set
        if (timesheets == null) {
            throw new IllegalArgumentException("Missing the required parameter 'timesheets' when calling createTimesheet");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling createTimesheet");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Timesheets";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(timesheets);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches for an employee by unique id
    * <p><b>200</b> - search results matching criteria
    * @param xeroTenantId Xero identifier for Tenant
    * @param employeeId Employee id for single object
    * @param accessToken Authorization token for user set in header of each request
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees  getEmployee(String accessToken, String xeroTenantId, UUID employeeId) throws IOException {
        try {
            TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
            HttpResponse response = getEmployeeForHttpResponse(accessToken, xeroTenantId, employeeId);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getEmployeeForHttpResponse(String accessToken,  String xeroTenantId,  UUID employeeId) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getEmployee");
        }// verify the required parameter 'employeeId' is set
        if (employeeId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'employeeId' when calling getEmployee");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getEmployee");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Employees/{EmployeeId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("EmployeeId", employeeId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches employees
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - validation error for a bad request
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 employees will be returned in a single API call
    * @param accessToken Authorization token for user set in header of each request
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees  getEmployees(String accessToken, String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        try {
            TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
            HttpResponse response = getEmployeesForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order, page);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getEmployeesForHttpResponse(String accessToken,  String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer page) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getEmployees");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getEmployees");
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

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches for an Leave Application by unique id
    * <p><b>200</b> - search results matching criteria
    * @param xeroTenantId Xero identifier for Tenant
    * @param leaveApplicationId Leave Application id for single object
    * @param accessToken Authorization token for user set in header of each request
    * @return LeaveApplications
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LeaveApplications  getLeaveApplication(String accessToken, String xeroTenantId, UUID leaveApplicationId) throws IOException {
        try {
            TypeReference<LeaveApplications> typeRef = new TypeReference<LeaveApplications>() {};
            HttpResponse response = getLeaveApplicationForHttpResponse(accessToken, xeroTenantId, leaveApplicationId);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getLeaveApplicationForHttpResponse(String accessToken,  String xeroTenantId,  UUID leaveApplicationId) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getLeaveApplication");
        }// verify the required parameter 'leaveApplicationId' is set
        if (leaveApplicationId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'leaveApplicationId' when calling getLeaveApplication");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getLeaveApplication");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/LeaveApplications/{LeaveApplicationId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("LeaveApplicationId", leaveApplicationId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches Leave Applications
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - validation error for a bad request
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 objects will be returned in a single API call
    * @param accessToken Authorization token for user set in header of each request
    * @return LeaveApplications
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LeaveApplications  getLeaveApplications(String accessToken, String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        try {
            TypeReference<LeaveApplications> typeRef = new TypeReference<LeaveApplications>() {};
            HttpResponse response = getLeaveApplicationsForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order, page);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getLeaveApplicationsForHttpResponse(String accessToken,  String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer page) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getLeaveApplications");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getLeaveApplications");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/LeaveApplications";
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

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches Pay Items
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - validation error for a bad request
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 objects will be returned in a single API call
    * @param accessToken Authorization token for user set in header of each request
    * @return PayItems
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PayItems  getPayItems(String accessToken, String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        try {
            TypeReference<PayItems> typeRef = new TypeReference<PayItems>() {};
            HttpResponse response = getPayItemsForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order, page);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getPayItemsForHttpResponse(String accessToken,  String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer page) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPayItems");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getPayItems");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PayItems";
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

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches Payroll Calendars
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - validation error for a bad request
    * @param xeroTenantId Xero identifier for Tenant
    * @param payrollCalendarID Payroll Calendar id for single object
    * @param accessToken Authorization token for user set in header of each request
    * @return PayrollCalendars
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PayrollCalendars  getPayrollCalendar(String accessToken, String xeroTenantId, UUID payrollCalendarID) throws IOException {
        try {
            TypeReference<PayrollCalendars> typeRef = new TypeReference<PayrollCalendars>() {};
            HttpResponse response = getPayrollCalendarForHttpResponse(accessToken, xeroTenantId, payrollCalendarID);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getPayrollCalendarForHttpResponse(String accessToken,  String xeroTenantId,  UUID payrollCalendarID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPayrollCalendar");
        }// verify the required parameter 'payrollCalendarID' is set
        if (payrollCalendarID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'payrollCalendarID' when calling getPayrollCalendar");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getPayrollCalendar");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PayrollCalendars/{PayrollCalendarID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PayrollCalendarID", payrollCalendarID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches Payroll Calendars
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - validation error for a bad request
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 objects will be returned in a single API call
    * @param accessToken Authorization token for user set in header of each request
    * @return PayrollCalendars
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public PayrollCalendars  getPayrollCalendars(String accessToken, String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        try {
            TypeReference<PayrollCalendars> typeRef = new TypeReference<PayrollCalendars>() {};
            HttpResponse response = getPayrollCalendarsForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order, page);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getPayrollCalendarsForHttpResponse(String accessToken,  String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer page) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPayrollCalendars");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getPayrollCalendars");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/PayrollCalendars";
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

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches for an payrun by unique id
    * <p><b>200</b> - search results matching criteria
    * @param xeroTenantId Xero identifier for Tenant
    * @param payrunID Payrun id for single object
    * @param accessToken Authorization token for user set in header of each request
    * @return Payruns
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payruns  getPayrun(String accessToken, String xeroTenantId, UUID payrunID) throws IOException {
        try {
            TypeReference<Payruns> typeRef = new TypeReference<Payruns>() {};
            HttpResponse response = getPayrunForHttpResponse(accessToken, xeroTenantId, payrunID);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getPayrunForHttpResponse(String accessToken,  String xeroTenantId,  UUID payrunID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPayrun");
        }// verify the required parameter 'payrunID' is set
        if (payrunID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'payrunID' when calling getPayrun");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getPayrun");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payruns/{PayrunID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PayrunID", payrunID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches payruns
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - validation error for a bad request
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 payruns will be returned in a single API call
    * @param accessToken Authorization token for user set in header of each request
    * @return Payruns
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payruns  getPayruns(String accessToken, String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        try {
            TypeReference<Payruns> typeRef = new TypeReference<Payruns>() {};
            HttpResponse response = getPayrunsForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order, page);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getPayrunsForHttpResponse(String accessToken,  String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer page) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPayruns");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getPayruns");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payruns";
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

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches for an payslip by unique id
    * <p><b>200</b> - search results matching criteria
    * @param xeroTenantId Xero identifier for Tenant
    * @param payslipID Payslip id for single object
    * @param accessToken Authorization token for user set in header of each request
    * @return Payslip
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payslip  getPayslip(String accessToken, String xeroTenantId, UUID payslipID) throws IOException {
        try {
            TypeReference<Payslip> typeRef = new TypeReference<Payslip>() {};
            HttpResponse response = getPayslipForHttpResponse(accessToken, xeroTenantId, payslipID);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getPayslipForHttpResponse(String accessToken,  String xeroTenantId,  UUID payslipID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getPayslip");
        }// verify the required parameter 'payslipID' is set
        if (payslipID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'payslipID' when calling getPayslip");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getPayslip");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payslip/{PayslipID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PayslipID", payslipID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * retrieve settings
    * <p><b>200</b> - payroll settings
    * @param xeroTenantId Xero identifier for Tenant
    * @param accessToken Authorization token for user set in header of each request
    * @return Settings
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Settings  getSettings(String accessToken, String xeroTenantId) throws IOException {
        try {
            TypeReference<Settings> typeRef = new TypeReference<Settings>() {};
            HttpResponse response = getSettingsForHttpResponse(accessToken, xeroTenantId);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getSettingsForHttpResponse(String accessToken,  String xeroTenantId) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getSettings");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getSettings");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Settings";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches for an Superfund by unique id
    * <p><b>200</b> - search results matching criteria
    * @param xeroTenantId Xero identifier for Tenant
    * @param superfundID Superfund id for single object
    * @param accessToken Authorization token for user set in header of each request
    * @return Superfunds
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Superfunds  getSuperfund(String accessToken, String xeroTenantId, UUID superfundID) throws IOException {
        try {
            TypeReference<Superfunds> typeRef = new TypeReference<Superfunds>() {};
            HttpResponse response = getSuperfundForHttpResponse(accessToken, xeroTenantId, superfundID);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getSuperfundForHttpResponse(String accessToken,  String xeroTenantId,  UUID superfundID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getSuperfund");
        }// verify the required parameter 'superfundID' is set
        if (superfundID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'superfundID' when calling getSuperfund");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getSuperfund");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Superfunds/{SuperfundID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("SuperfundID", superfundID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches SuperfundProducts
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - validation error for a bad request
    * @param xeroTenantId Xero identifier for Tenant
    * @param ABN The ABN of the Regulated SuperFund
    * @param USI The USI of the Regulated SuperFund
    * @param accessToken Authorization token for user set in header of each request
    * @return SuperfundProducts
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public SuperfundProducts  getSuperfundProducts(String accessToken, String xeroTenantId, String ABN, String USI) throws IOException {
        try {
            TypeReference<SuperfundProducts> typeRef = new TypeReference<SuperfundProducts>() {};
            HttpResponse response = getSuperfundProductsForHttpResponse(accessToken, xeroTenantId, ABN, USI);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getSuperfundProductsForHttpResponse(String accessToken,  String xeroTenantId,  String ABN,  String USI) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getSuperfundProducts");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getSuperfundProducts");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/SuperfundProducts";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
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
        }        if (USI != null) {
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

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches Superfunds
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - validation error for a bad request
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 Superfunds will be returned in a single API call
    * @param accessToken Authorization token for user set in header of each request
    * @return Superfunds
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Superfunds  getSuperfunds(String accessToken, String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        try {
            TypeReference<Superfunds> typeRef = new TypeReference<Superfunds>() {};
            HttpResponse response = getSuperfundsForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order, page);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getSuperfundsForHttpResponse(String accessToken,  String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer page) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getSuperfunds");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getSuperfunds");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Superfunds";
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

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches for an timesheet by unique id
    * <p><b>200</b> - search results matching criteria
    * @param xeroTenantId Xero identifier for Tenant
    * @param timesheetID Timesheet id for single object
    * @param accessToken Authorization token for user set in header of each request
    * @return Timesheets
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Timesheets  getTimesheet(String accessToken, String xeroTenantId, UUID timesheetID) throws IOException {
        try {
            TypeReference<Timesheets> typeRef = new TypeReference<Timesheets>() {};
            HttpResponse response = getTimesheetForHttpResponse(accessToken, xeroTenantId, timesheetID);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getTimesheetForHttpResponse(String accessToken,  String xeroTenantId,  UUID timesheetID) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getTimesheet");
        }// verify the required parameter 'timesheetID' is set
        if (timesheetID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'timesheetID' when calling getTimesheet");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getTimesheet");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Timesheets/{TimesheetID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("TimesheetID", timesheetID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * searches timesheets
    * <p><b>200</b> - search results matching criteria
    * <p><b>400</b> - validation error for a bad request
    * @param xeroTenantId Xero identifier for Tenant
    * @param ifModifiedSince Only records created or modified since this timestamp will be returned
    * @param where Filter by an any element
    * @param order Order by an any element
    * @param page e.g. page&#x3D;1 – Up to 100 timesheets will be returned in a single API call
    * @param accessToken Authorization token for user set in header of each request
    * @return Timesheets
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Timesheets  getTimesheets(String accessToken, String xeroTenantId, OffsetDateTime ifModifiedSince, String where, String order, Integer page) throws IOException {
        try {
            TypeReference<Timesheets> typeRef = new TypeReference<Timesheets>() {};
            HttpResponse response = getTimesheetsForHttpResponse(accessToken, xeroTenantId, ifModifiedSince, where, order, page);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse getTimesheetsForHttpResponse(String accessToken,  String xeroTenantId,  OffsetDateTime ifModifiedSince,  String where,  String order,  Integer page) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling getTimesheets");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling getTimesheets");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Timesheets";
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

        
        HttpContent content = null;
        
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.GET, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Update an Employee
    * Update properties on a single employee
    * <p><b>200</b> - A successful request
    * @param xeroTenantId Xero identifier for Tenant
    * @param employeeId Employee id for single object
    * @param employee The employee parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return Employees
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Employees  updateEmployee(String accessToken, String xeroTenantId, UUID employeeId, List<Employee> employee) throws IOException {
        try {
            TypeReference<Employees> typeRef = new TypeReference<Employees>() {};
            HttpResponse response = updateEmployeeForHttpResponse(accessToken, xeroTenantId, employeeId, employee);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse updateEmployeeForHttpResponse(String accessToken,  String xeroTenantId,  UUID employeeId,  List<Employee> employee) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateEmployee");
        }// verify the required parameter 'employeeId' is set
        if (employeeId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'employeeId' when calling updateEmployee");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling updateEmployee");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Employees/{EmployeeId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("EmployeeId", employeeId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(employee);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Use this method to create a Leave Application
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - invalid input, object invalid - TODO
    * @param xeroTenantId Xero identifier for Tenant
    * @param leaveApplicationId Leave Application id for single object
    * @param leaveApplication The leaveApplication parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return LeaveApplications
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public LeaveApplications  updateLeaveApplication(String accessToken, String xeroTenantId, UUID leaveApplicationId, List<LeaveApplication> leaveApplication) throws IOException {
        try {
            TypeReference<LeaveApplications> typeRef = new TypeReference<LeaveApplications>() {};
            HttpResponse response = updateLeaveApplicationForHttpResponse(accessToken, xeroTenantId, leaveApplicationId, leaveApplication);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse updateLeaveApplicationForHttpResponse(String accessToken,  String xeroTenantId,  UUID leaveApplicationId,  List<LeaveApplication> leaveApplication) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateLeaveApplication");
        }// verify the required parameter 'leaveApplicationId' is set
        if (leaveApplicationId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'leaveApplicationId' when calling updateLeaveApplication");
        }// verify the required parameter 'leaveApplication' is set
        if (leaveApplication == null) {
            throw new IllegalArgumentException("Missing the required parameter 'leaveApplication' when calling updateLeaveApplication");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling updateLeaveApplication");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/LeaveApplications/{LeaveApplicationId}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("LeaveApplicationId", leaveApplicationId);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(leaveApplication);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Update a Payrun
    * Update properties on a single payrun
    * <p><b>200</b> - A successful request
    * @param xeroTenantId Xero identifier for Tenant
    * @param payrunID Payrun id for single object
    * @param payruns The payruns parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return Payruns
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payruns  updatePayrun(String accessToken, String xeroTenantId, UUID payrunID, List<Payruns> payruns) throws IOException {
        try {
            TypeReference<Payruns> typeRef = new TypeReference<Payruns>() {};
            HttpResponse response = updatePayrunForHttpResponse(accessToken, xeroTenantId, payrunID, payruns);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse updatePayrunForHttpResponse(String accessToken,  String xeroTenantId,  UUID payrunID,  List<Payruns> payruns) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updatePayrun");
        }// verify the required parameter 'payrunID' is set
        if (payrunID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'payrunID' when calling updatePayrun");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling updatePayrun");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payruns/{PayrunID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PayrunID", payrunID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(payruns);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Use this add, update or delete one or more payslip line items
    * <p><b>200</b> - A successful request
    * <p><b>400</b> - invalid input, object invalid - TODO
    * @param xeroTenantId Xero identifier for Tenant
    * @param payslip The payslip parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return Payslip
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payslip  updatePayslip(String accessToken, String xeroTenantId, List<Payslip> payslip) throws IOException {
        try {
            TypeReference<Payslip> typeRef = new TypeReference<Payslip>() {};
            HttpResponse response = updatePayslipForHttpResponse(accessToken, xeroTenantId, payslip);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse updatePayslipForHttpResponse(String accessToken,  String xeroTenantId,  List<Payslip> payslip) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updatePayslip");
        }// verify the required parameter 'payslip' is set
        if (payslip == null) {
            throw new IllegalArgumentException("Missing the required parameter 'payslip' when calling updatePayslip");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling updatePayslip");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payslip";
        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.build().toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(payslip);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Update a Payslip
    * Update lines on a single payslip
    * <p><b>200</b> - A successful request
    * @param xeroTenantId Xero identifier for Tenant
    * @param payslipID Payslip id for single object
    * @param payslip The payslip parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return Payslip
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Payslip  updatePayslipByID(String accessToken, String xeroTenantId, UUID payslipID, List<Payslip> payslip) throws IOException {
        try {
            TypeReference<Payslip> typeRef = new TypeReference<Payslip>() {};
            HttpResponse response = updatePayslipByIDForHttpResponse(accessToken, xeroTenantId, payslipID, payslip);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse updatePayslipByIDForHttpResponse(String accessToken,  String xeroTenantId,  UUID payslipID,  List<Payslip> payslip) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updatePayslipByID");
        }// verify the required parameter 'payslipID' is set
        if (payslipID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'payslipID' when calling updatePayslipByID");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling updatePayslipByID");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Payslip/{PayslipID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("PayslipID", payslipID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(payslip);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Update a Superfund
    * Update properties on a single Superfund
    * <p><b>200</b> - A successful request
    * @param xeroTenantId Xero identifier for Tenant
    * @param superfundID Superfund id for single object
    * @param superfunds The superfunds parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return Superfunds
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Superfunds  updateSuperfund(String accessToken, String xeroTenantId, UUID superfundID, Superfunds superfunds) throws IOException {
        try {
            TypeReference<Superfunds> typeRef = new TypeReference<Superfunds>() {};
            HttpResponse response = updateSuperfundForHttpResponse(accessToken, xeroTenantId, superfundID, superfunds);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse updateSuperfundForHttpResponse(String accessToken,  String xeroTenantId,  UUID superfundID,  Superfunds superfunds) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateSuperfund");
        }// verify the required parameter 'superfundID' is set
        if (superfundID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'superfundID' when calling updateSuperfund");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling updateSuperfund");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Superfunds/{SuperfundID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("SuperfundID", superfundID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(superfunds);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
    }

  /**
    * Update a Timesheet
    * Update properties on a single timesheet
    * <p><b>200</b> - A successful request
    * @param xeroTenantId Xero identifier for Tenant
    * @param timesheetID Timesheet id for single object
    * @param timesheets The timesheets parameter
    * @param accessToken Authorization token for user set in header of each request
    * @return Timesheets
    * @throws IOException if an error occurs while attempting to invoke the API
    **/
    public Timesheets  updateTimesheet(String accessToken, String xeroTenantId, UUID timesheetID, Timesheets timesheets) throws IOException {
        try {
            TypeReference<Timesheets> typeRef = new TypeReference<Timesheets>() {};
            HttpResponse response = updateTimesheetForHttpResponse(accessToken, xeroTenantId, timesheetID, timesheets);
            return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
        } catch (HttpResponseException e) {
            XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
            handler.execute(e,apiClient);
        } catch (IOException ioe) {
            throw ioe;
        }
        return null;
    }

    public HttpResponse updateTimesheetForHttpResponse(String accessToken,  String xeroTenantId,  UUID timesheetID,  Timesheets timesheets) throws IOException {
        // verify the required parameter 'xeroTenantId' is set
        if (xeroTenantId == null) {
            throw new IllegalArgumentException("Missing the required parameter 'xeroTenantId' when calling updateTimesheet");
        }// verify the required parameter 'timesheetID' is set
        if (timesheetID == null) {
            throw new IllegalArgumentException("Missing the required parameter 'timesheetID' when calling updateTimesheet");
        }
        if (accessToken == null) {
            throw new IllegalArgumentException("Missing the required parameter 'accessToken' when calling updateTimesheet");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("xero-tenant-id", xeroTenantId);
        headers.setAccept("application/json"); 
        headers.setUserAgent(this.getUserAgent());
        
        String correctPath = "/Timesheets/{TimesheetID}";
        
        // create a map of path variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("TimesheetID", timesheetID);

        UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + correctPath);
        String url = uriBuilder.buildFromMap(uriVariables).toString();
        GenericUrl genericUrl = new GenericUrl(url);

        
        HttpContent content = null;
        
        
        content = apiClient.new JacksonJsonHttpContent(timesheets);
        
        
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpTransport transport = new NetHttpTransport();        
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        
        return requestFactory.buildRequest(HttpMethods.POST, genericUrl, content).setHeaders(headers).execute();
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
