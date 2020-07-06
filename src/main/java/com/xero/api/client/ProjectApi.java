package com.xero.api.client;

import com.xero.api.ApiClient;

import org.threeten.bp.OffsetDateTime;
import com.xero.models.project.Project;
import com.xero.models.project.ProjectCreateOrUpdate;
import com.xero.models.project.ProjectPatch;
import com.xero.models.project.ProjectUsers;
import com.xero.models.project.Projects;
import com.xero.models.project.Task;
import com.xero.models.project.Tasks;
import com.xero.models.project.TimeEntries;
import com.xero.models.project.TimeEntry;
import com.xero.models.project.TimeEntryCreateOrUpdate;
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

public class ProjectApi {
  private ApiClient apiClient;
  private static ProjectApi instance = null;
  private String userAgent = "Default";
  private String version = "4.1.1";
  static final Logger logger = LoggerFactory.getLogger(ProjectApi.class);

  public ProjectApi() {
    this(new ApiClient());
  }

  public static ProjectApi getInstance(ApiClient apiClient) {
    if (instance == null) {
      instance = new ProjectApi(apiClient);
    }
    return instance;
  }

  public ProjectApi(ApiClient apiClient) {
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
   * create one or more new projects
   *
   * <p><b>201</b> - OK/success, returns the new project object
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param projectCreateOrUpdate Create a new project with ProjectCreateOrUpdate object
   * @param accessToken Authorization token for user set in header of each request
   * @return Project
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Project createProject(
      String accessToken, String xeroTenantId, ProjectCreateOrUpdate projectCreateOrUpdate)
      throws IOException {
    try {
      TypeReference<Project> typeRef = new TypeReference<Project>() {};
      HttpResponse response =
          createProjectForHttpResponse(accessToken, xeroTenantId, projectCreateOrUpdate);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createProject -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createProjectForHttpResponse(
      String accessToken, String xeroTenantId, ProjectCreateOrUpdate projectCreateOrUpdate)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createProject");
    } // verify the required parameter 'projectCreateOrUpdate' is set
    if (projectCreateOrUpdate == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectCreateOrUpdate' when calling createProject");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createProject");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/projects");
    String url = uriBuilder.build().toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(projectCreateOrUpdate);

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
   * Allows you to create a task Allows you to create a specific task
   *
   * <p><b>200</b> - OK/success, returns the newly created time entry
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param projectId You can specify an individual project by appending the projectId to the
   *     endpoint
   * @param timeEntryCreateOrUpdate The time entry object you are creating
   * @param accessToken Authorization token for user set in header of each request
   * @return TimeEntry
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TimeEntry createTimeEntry(
      String accessToken,
      String xeroTenantId,
      UUID projectId,
      TimeEntryCreateOrUpdate timeEntryCreateOrUpdate)
      throws IOException {
    try {
      TypeReference<TimeEntry> typeRef = new TypeReference<TimeEntry>() {};
      HttpResponse response =
          createTimeEntryForHttpResponse(
              accessToken, xeroTenantId, projectId, timeEntryCreateOrUpdate);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : createTimeEntry -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse createTimeEntryForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID projectId,
      TimeEntryCreateOrUpdate timeEntryCreateOrUpdate)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling createTimeEntry");
    } // verify the required parameter 'projectId' is set
    if (projectId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectId' when calling createTimeEntry");
    } // verify the required parameter 'timeEntryCreateOrUpdate' is set
    if (timeEntryCreateOrUpdate == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timeEntryCreateOrUpdate' when calling createTimeEntry");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling createTimeEntry");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("projectId", projectId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/projects/{projectId}/time");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("POST " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(timeEntryCreateOrUpdate);

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
   * Allows you to delete a time entry Allows you to delete a specific time entry
   *
   * <p><b>204</b> - Success - return response 204 no content
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param projectId You can specify an individual project by appending the projectId to the
   *     endpoint
   * @param timeEntryId You can specify an individual task by appending the id to the endpoint
   * @param accessToken Authorization token for user set in header of each request
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public void deleteTimeEntry(
      String accessToken, String xeroTenantId, UUID projectId, UUID timeEntryId)
      throws IOException {
    try {
      deleteTimeEntryForHttpResponse(accessToken, xeroTenantId, projectId, timeEntryId);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : deleteTimeEntry -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
  }

  public HttpResponse deleteTimeEntryForHttpResponse(
      String accessToken, String xeroTenantId, UUID projectId, UUID timeEntryId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling deleteTimeEntry");
    } // verify the required parameter 'projectId' is set
    if (projectId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectId' when calling deleteTimeEntry");
    } // verify the required parameter 'timeEntryId' is set
    if (timeEntryId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timeEntryId' when calling deleteTimeEntry");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling deleteTimeEntry");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("projectId", projectId);
    uriVariables.put("timeEntryId", timeEntryId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/projects/{projectId}/time/{timeEntryId}");
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
   * Allows you to retrieve a single project Allows you to retrieve a specific project
   *
   * <p><b>200</b> - OK/success, returns a list of project objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param projectId You can specify an individual project by appending the projectId to the
   *     endpoint
   * @param accessToken Authorization token for user set in header of each request
   * @return Project
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Project getProject(String accessToken, String xeroTenantId, UUID projectId)
      throws IOException {
    try {
      TypeReference<Project> typeRef = new TypeReference<Project>() {};
      HttpResponse response = getProjectForHttpResponse(accessToken, xeroTenantId, projectId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getProject -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getProjectForHttpResponse(
      String accessToken, String xeroTenantId, UUID projectId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getProject");
    } // verify the required parameter 'projectId' is set
    if (projectId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectId' when calling getProject");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getProject");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("projectId", projectId);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/projects/{projectId}");
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
   * list all project users Allows you to retrieve the users on a projects.
   *
   * <p><b>200</b> - OK/success, returns a list of project objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param page set to 1 by default. The requested number of the page in paged response - Must be a
   *     number greater than 0.
   * @param pageSize Optional, it is set to 50 by default. The number of items to return per page in
   *     a paged response - Must be a number between 1 and 500.
   * @param accessToken Authorization token for user set in header of each request
   * @return ProjectUsers
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public ProjectUsers getProjectUsers(
      String accessToken, String xeroTenantId, Integer page, Integer pageSize) throws IOException {
    try {
      TypeReference<ProjectUsers> typeRef = new TypeReference<ProjectUsers>() {};
      HttpResponse response =
          getProjectUsersForHttpResponse(accessToken, xeroTenantId, page, pageSize);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getProjectUsers -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getProjectUsersForHttpResponse(
      String accessToken, String xeroTenantId, Integer page, Integer pageSize) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getProjectUsers");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getProjectUsers");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/projectsusers");
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
    if (pageSize != null) {
      String key = "pageSize";
      Object value = pageSize;
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
   * list all projects Allows you to retrieve, create and update projects.
   *
   * <p><b>200</b> - OK/success, returns a list of project objects
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param projectIds Search for all projects that match a comma separated list of projectIds
   * @param contactID Filter for projects for a specific contact
   * @param states Filter for projects in a particular state (INPROGRESS or CLOSED)
   * @param page set to 1 by default. The requested number of the page in paged response - Must be a
   *     number greater than 0.
   * @param pageSize Optional, it is set to 50 by default. The number of items to return per page in
   *     a paged response - Must be a number between 1 and 500.
   * @param accessToken Authorization token for user set in header of each request
   * @return Projects
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Projects getProjects(
      String accessToken,
      String xeroTenantId,
      List<UUID> projectIds,
      UUID contactID,
      String states,
      Integer page,
      Integer pageSize)
      throws IOException {
    try {
      TypeReference<Projects> typeRef = new TypeReference<Projects>() {};
      HttpResponse response =
          getProjectsForHttpResponse(
              accessToken, xeroTenantId, projectIds, contactID, states, page, pageSize);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getProjects -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getProjectsForHttpResponse(
      String accessToken,
      String xeroTenantId,
      List<UUID> projectIds,
      UUID contactID,
      String states,
      Integer page,
      Integer pageSize)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getProjects");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getProjects");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/projects");
    if (projectIds != null) {
      String key = "projectIds";
      Object value = projectIds;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (contactID != null) {
      String key = "contactID";
      Object value = contactID;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (states != null) {
      String key = "states";
      Object value = states;
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
    if (pageSize != null) {
      String key = "pageSize";
      Object value = pageSize;
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
   * Allows you to retrieve a single project Allows you to retrieve a specific project
   *
   * <p><b>200</b> - OK/success, returns a list of task objects
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param projectId You can specify an individual project by appending the projectId to the
   *     endpoint
   * @param taskId You can specify an individual task by appending the taskId to the endpoint, i.e.
   *     GET https://.../tasks/{taskId}
   * @param accessToken Authorization token for user set in header of each request
   * @return Task
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Task getTask(String accessToken, String xeroTenantId, UUID projectId, UUID taskId)
      throws IOException {
    try {
      TypeReference<Task> typeRef = new TypeReference<Task>() {};
      HttpResponse response = getTaskForHttpResponse(accessToken, xeroTenantId, projectId, taskId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getTask -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getTaskForHttpResponse(
      String accessToken, String xeroTenantId, UUID projectId, UUID taskId) throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getTask");
    } // verify the required parameter 'projectId' is set
    if (projectId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectId' when calling getTask");
    } // verify the required parameter 'taskId' is set
    if (taskId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'taskId' when calling getTask");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getTask");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("projectId", projectId);
    uriVariables.put("taskId", taskId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/projects/{projectId}/tasks/{taskId}");
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
   * Allows you to retrieve a single project Allows you to retrieve a specific project
   *
   * <p><b>200</b> - OK/success, returns a list of task objects
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param projectId You can specify an individual project by appending the projectId to the
   *     endpoint
   * @param page Set to 1 by default. The requested number of the page in paged response - Must be a
   *     number greater than 0.
   * @param pageSize Optional, it is set to 50 by default. The number of items to return per page in
   *     a paged response - Must be a number between 1 and 500.
   * @param taskIds taskIdsSearch for all tasks that match a comma separated list of taskIds, i.e.
   *     GET https://.../tasks?taskIds&#x3D;{taskId},{taskId}
   * @param accessToken Authorization token for user set in header of each request
   * @return Tasks
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public Tasks getTasks(
      String accessToken,
      String xeroTenantId,
      UUID projectId,
      Integer page,
      Integer pageSize,
      String taskIds)
      throws IOException {
    try {
      TypeReference<Tasks> typeRef = new TypeReference<Tasks>() {};
      HttpResponse response =
          getTasksForHttpResponse(accessToken, xeroTenantId, projectId, page, pageSize, taskIds);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getTasks -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getTasksForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID projectId,
      Integer page,
      Integer pageSize,
      String taskIds)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getTasks");
    } // verify the required parameter 'projectId' is set
    if (projectId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectId' when calling getTasks");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getTasks");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("projectId", projectId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/projects/{projectId}/tasks");
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
    if (pageSize != null) {
      String key = "pageSize";
      Object value = pageSize;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (taskIds != null) {
      String key = "taskIds";
      Object value = taskIds;
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
   * Allows you to retrieve the time entries associated with a specific project Allows you to
   * retrieve the time entries associated with a specific project
   *
   * <p><b>200</b> - OK/success, returns a list of time entry objects
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param projectId Identifier of the project, that the task (which the time entry is logged
   *     against) belongs to.
   * @param userId The xero user identifier of the person who logged time.
   * @param taskId Identifier of the task that time entry is logged against.
   * @param invoiceId Finds all time entries for this invoice.
   * @param contactId Finds all time entries for this contact identifier.
   * @param page Set to 1 by default. The requested number of the page in paged response - Must be a
   *     number greater than 0.
   * @param pageSize Optional, it is set to 50 by default. The number of items to return per page in
   *     a paged response - Must be a number between 1 and 500.
   * @param states Comma-separated list of states to find. Will find all time entries that are in
   *     the status of whatever’s specified.
   * @param isChargeable Finds all time entries which relate to tasks with the charge type
   *     &#x60;TIME&#x60; or &#x60;FIXED&#x60;.
   * @param dateAfterUtc ISO 8601 UTC date. Finds all time entries on or after this date filtered on
   *     the &#x60;dateUtc&#x60; field.
   * @param dateBeforeUtc ISO 8601 UTC date. Finds all time entries on or before this date filtered
   *     on the &#x60;dateUtc&#x60; field.
   * @param accessToken Authorization token for user set in header of each request
   * @return TimeEntries
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TimeEntries getTimeEntries(
      String accessToken,
      String xeroTenantId,
      UUID projectId,
      UUID userId,
      UUID taskId,
      UUID invoiceId,
      UUID contactId,
      Integer page,
      Integer pageSize,
      List<String> states,
      Boolean isChargeable,
      OffsetDateTime dateAfterUtc,
      OffsetDateTime dateBeforeUtc)
      throws IOException {
    try {
      TypeReference<TimeEntries> typeRef = new TypeReference<TimeEntries>() {};
      HttpResponse response =
          getTimeEntriesForHttpResponse(
              accessToken,
              xeroTenantId,
              projectId,
              userId,
              taskId,
              invoiceId,
              contactId,
              page,
              pageSize,
              states,
              isChargeable,
              dateAfterUtc,
              dateBeforeUtc);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getTimeEntries -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getTimeEntriesForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID projectId,
      UUID userId,
      UUID taskId,
      UUID invoiceId,
      UUID contactId,
      Integer page,
      Integer pageSize,
      List<String> states,
      Boolean isChargeable,
      OffsetDateTime dateAfterUtc,
      OffsetDateTime dateBeforeUtc)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getTimeEntries");
    } // verify the required parameter 'projectId' is set
    if (projectId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectId' when calling getTimeEntries");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getTimeEntries");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("projectId", projectId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/projects/{projectId}/time");
    if (userId != null) {
      String key = "userId";
      Object value = userId;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (taskId != null) {
      String key = "taskId";
      Object value = taskId;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (invoiceId != null) {
      String key = "invoiceId";
      Object value = invoiceId;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
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
    if (pageSize != null) {
      String key = "pageSize";
      Object value = pageSize;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (states != null) {
      String key = "states";
      Object value = states;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (isChargeable != null) {
      String key = "isChargeable";
      Object value = isChargeable;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (dateAfterUtc != null) {
      String key = "dateAfterUtc";
      Object value = dateAfterUtc;
      if (value instanceof Collection) {
        uriBuilder = uriBuilder.queryParam(key, ((Collection) value).toArray());
      } else if (value instanceof Object[]) {
        uriBuilder = uriBuilder.queryParam(key, (Object[]) value);
      } else {
        uriBuilder = uriBuilder.queryParam(key, value);
      }
    }
    if (dateBeforeUtc != null) {
      String key = "dateBeforeUtc";
      Object value = dateBeforeUtc;
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
   * Allows you to get a single time entry in a project Allows you to upget a single time entry in a
   * project
   *
   * <p><b>200</b> - OK/success, returns a single time entry
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param projectId You can specify an individual project by appending the projectId to the
   *     endpoint
   * @param timeEntryId You can specify an individual time entry by appending the id to the endpoint
   * @param accessToken Authorization token for user set in header of each request
   * @return TimeEntry
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public TimeEntry getTimeEntry(
      String accessToken, String xeroTenantId, UUID projectId, UUID timeEntryId)
      throws IOException {
    try {
      TypeReference<TimeEntry> typeRef = new TypeReference<TimeEntry>() {};
      HttpResponse response =
          getTimeEntryForHttpResponse(accessToken, xeroTenantId, projectId, timeEntryId);
      return apiClient.getObjectMapper().readValue(response.getContent(), typeRef);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : getTimeEntry -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
    return null;
  }

  public HttpResponse getTimeEntryForHttpResponse(
      String accessToken, String xeroTenantId, UUID projectId, UUID timeEntryId)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling getTimeEntry");
    } // verify the required parameter 'projectId' is set
    if (projectId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectId' when calling getTimeEntry");
    } // verify the required parameter 'timeEntryId' is set
    if (timeEntryId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timeEntryId' when calling getTimeEntry");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling getTimeEntry");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("projectId", projectId);
    uriVariables.put("timeEntryId", timeEntryId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/projects/{projectId}/time/{timeEntryId}");
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
   * creates a project for the specified contact Allows you to update a specific projects.
   *
   * <p><b>204</b> - Success - return response 204 no content
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param projectId You can specify an individual project by appending the projectId to the
   *     endpoint
   * @param projectPatch Update the status of an existing Project
   * @param accessToken Authorization token for user set in header of each request
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public void patchProject(
      String accessToken, String xeroTenantId, UUID projectId, ProjectPatch projectPatch)
      throws IOException {
    try {
      patchProjectForHttpResponse(accessToken, xeroTenantId, projectId, projectPatch);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : patchProject -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
  }

  public HttpResponse patchProjectForHttpResponse(
      String accessToken, String xeroTenantId, UUID projectId, ProjectPatch projectPatch)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling patchProject");
    } // verify the required parameter 'projectId' is set
    if (projectId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectId' when calling patchProject");
    } // verify the required parameter 'projectPatch' is set
    if (projectPatch == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectPatch' when calling patchProject");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling patchProject");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("projectId", projectId);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/projects/{projectId}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PATCH " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(projectPatch);

    Credential credential =
        new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
    HttpTransport transport = apiClient.getHttpTransport();
    HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
    return requestFactory
        .buildRequest(HttpMethods.PATCH, genericUrl, content)
        .setHeaders(headers)
        .setConnectTimeout(apiClient.getConnectionTimeout())
        .setReadTimeout(apiClient.getReadTimeout())
        .execute();
  }

  /**
   * update a specific project Allows you to update a specific projects.
   *
   * <p><b>204</b> - Success - return response 204 no content
   *
   * <p><b>400</b> - A failed request due to validation error
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param projectId You can specify an individual project by appending the projectId to the
   *     endpoint
   * @param projectCreateOrUpdate Request of type ProjectCreateOrUpdate
   * @param accessToken Authorization token for user set in header of each request
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public void updateProject(
      String accessToken,
      String xeroTenantId,
      UUID projectId,
      ProjectCreateOrUpdate projectCreateOrUpdate)
      throws IOException {
    try {
      updateProjectForHttpResponse(accessToken, xeroTenantId, projectId, projectCreateOrUpdate);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateProject -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
  }

  public HttpResponse updateProjectForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID projectId,
      ProjectCreateOrUpdate projectCreateOrUpdate)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateProject");
    } // verify the required parameter 'projectId' is set
    if (projectId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectId' when calling updateProject");
    } // verify the required parameter 'projectCreateOrUpdate' is set
    if (projectCreateOrUpdate == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectCreateOrUpdate' when calling updateProject");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateProject");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("application/json");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("projectId", projectId);

    UriBuilder uriBuilder = UriBuilder.fromUri(apiClient.getBasePath() + "/projects/{projectId}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(projectCreateOrUpdate);

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
   * Allows you to update time entry in a project Allows you to update time entry in a project
   *
   * <p><b>204</b> - Success - return response 204 no content
   *
   * @param xeroTenantId Xero identifier for Tenant
   * @param projectId You can specify an individual project by appending the projectId to the
   *     endpoint
   * @param timeEntryId You can specify an individual time entry by appending the id to the endpoint
   * @param timeEntryCreateOrUpdate The time entry object you are updating
   * @param accessToken Authorization token for user set in header of each request
   * @throws IOException if an error occurs while attempting to invoke the API
   */
  public void updateTimeEntry(
      String accessToken,
      String xeroTenantId,
      UUID projectId,
      UUID timeEntryId,
      TimeEntryCreateOrUpdate timeEntryCreateOrUpdate)
      throws IOException {
    try {
      updateTimeEntryForHttpResponse(
          accessToken, xeroTenantId, projectId, timeEntryId, timeEntryCreateOrUpdate);
    } catch (HttpResponseException e) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "------------------ HttpResponseException "
                + e.getStatusCode()
                + " : updateTimeEntry -------------------");
        logger.debug(e.toString());
      }
      XeroApiExceptionHandler handler = new XeroApiExceptionHandler();
      handler.execute(e);
    } catch (IOException ioe) {
      throw ioe;
    }
  }

  public HttpResponse updateTimeEntryForHttpResponse(
      String accessToken,
      String xeroTenantId,
      UUID projectId,
      UUID timeEntryId,
      TimeEntryCreateOrUpdate timeEntryCreateOrUpdate)
      throws IOException {
    // verify the required parameter 'xeroTenantId' is set
    if (xeroTenantId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'xeroTenantId' when calling updateTimeEntry");
    } // verify the required parameter 'projectId' is set
    if (projectId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'projectId' when calling updateTimeEntry");
    } // verify the required parameter 'timeEntryId' is set
    if (timeEntryId == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timeEntryId' when calling updateTimeEntry");
    } // verify the required parameter 'timeEntryCreateOrUpdate' is set
    if (timeEntryCreateOrUpdate == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'timeEntryCreateOrUpdate' when calling updateTimeEntry");
    }
    if (accessToken == null) {
      throw new IllegalArgumentException(
          "Missing the required parameter 'accessToken' when calling updateTimeEntry");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.set("Xero-Tenant-Id", xeroTenantId);
    headers.setAccept("");
    headers.setUserAgent(this.getUserAgent());
    // create a map of path variables
    final Map<String, Object> uriVariables = new HashMap<String, Object>();
    uriVariables.put("projectId", projectId);
    uriVariables.put("timeEntryId", timeEntryId);

    UriBuilder uriBuilder =
        UriBuilder.fromUri(apiClient.getBasePath() + "/projects/{projectId}/time/{timeEntryId}");
    String url = uriBuilder.buildFromMap(uriVariables).toString();
    GenericUrl genericUrl = new GenericUrl(url);
    if (logger.isDebugEnabled()) {
      logger.debug("PUT " + genericUrl.toString());
    }

    HttpContent content = null;
    content = apiClient.new JacksonJsonHttpContent(timeEntryCreateOrUpdate);

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
