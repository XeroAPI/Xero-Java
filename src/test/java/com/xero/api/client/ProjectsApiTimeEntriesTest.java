package com.xero.api.client;


import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.xero.api.ApiClient;
import com.xero.models.project.*;



import org.threeten.bp.*;
import java.io.IOException;

import java.io.IOException;


import java.util.UUID;


import java.util.List;

public class ProjectsApiTimeEntriesTest {

  ApiClient defaultClient;
  ProjectApi projectApi;
  String accessToken;
  String xeroTenantId;

  private static boolean setUpIsDone = false;

  @Before
  public void setUp() {
    // Set Access Token and Tenant Id
    accessToken = "123";
    xeroTenantId = "xyz";

    // Init projectsApi client
    // NEW Sandbox for API Mocking
    defaultClient =
        new ApiClient(
            "https://xero-projects.getsandbox.com:443/projects.xro/2.0", null, null, null, null);
    projectApi = ProjectApi.getInstance(defaultClient);

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
    projectApi = null;
    defaultClient = null;
  }

  @Test
  public void getTimeEntriesTest() throws IOException {
    System.out.println("@Test - getTimeEntriesTest");
    UUID projectId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    UUID userId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    UUID taskId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    UUID invoiceId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    UUID contactId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    boolean isChargeable = false;
    OffsetDateTime dateAfterUtc = null;
    OffsetDateTime dateBeforeUtc = null;
    int page = 1;
    int pageSize = 50;
    List<String> states = null;

    TimeEntries response =
        projectApi.getTimeEntries(
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

    assertThat(response.getPagination().getPage(), is(equalTo(1)));
    assertThat(response.getPagination().getItemCount(), is(equalTo(9)));
    assertThat(response.getPagination().getPageCount(), is(equalTo(1)));
    assertThat(response.getPagination().getPageSize(), is(equalTo(50)));
    assertThat(
        response.getItems().get(0).getTimeEntryId(),
        is(equalTo(UUID.fromString("3cd35eca-704f-4bca-b258-236028ae8ed1"))));
    assertThat(
        response.getItems().get(0).getUserId(),
        is(equalTo(UUID.fromString("740add2a-a703-4b8a-a670-1093919c2040"))));
    assertThat(
        response.getItems().get(0).getProjectId(),
        is(equalTo(UUID.fromString("b021e7cb-1903-4292-b48b-5b27b4271e3e"))));
    assertThat(
        response.getItems().get(0).getTaskId(),
        is(equalTo(UUID.fromString("7be77337-feec-4458-bb1b-dbaa5a4aafce"))));
    assertThat(
        response.getItems().get(0).getDateUtc(),
        is(equalTo(OffsetDateTime.parse("2020-02-27T15:00Z"))));
    assertThat(
        response.getItems().get(0).getDateEnteredUtc(),
        is(equalTo(OffsetDateTime.parse("2020-02-21T21:41:22.264272700Z"))));
    assertThat(response.getItems().get(0).getDuration(), is(equalTo(45)));
    assertThat(
        response.getItems().get(0).getStatus(),
        is(equalTo(com.xero.models.project.TimeEntry.StatusEnum.ACTIVE)));
    // System.out.println(response.toString());
  }

  @Test
  public void createTimeEntryTest() throws IOException {
    System.out.println("@Test - createTimeEntryTest");

    TimeEntryCreateOrUpdate timeEntryCreateOrUpdate = new TimeEntryCreateOrUpdate();
    UUID projectId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");

    TimeEntry response =
        projectApi.createTimeEntry(accessToken, xeroTenantId, projectId, timeEntryCreateOrUpdate);
    assertThat(
        response.getTimeEntryId(),
        is(equalTo(UUID.fromString("c6539534-f1d2-43a6-80df-3bd1f8aca24d"))));
    assertThat(
        response.getUserId(), is(equalTo(UUID.fromString("740add2a-a703-4b8a-a670-1093919c2040"))));
    assertThat(
        response.getProjectId(),
        is(equalTo(UUID.fromString("b021e7cb-1903-4292-b48b-5b27b4271e3e"))));
    assertThat(
        response.getTaskId(), is(equalTo(UUID.fromString("7be77337-feec-4458-bb1b-dbaa5a4aafce"))));
    assertThat(response.getDateUtc(), is(equalTo(OffsetDateTime.parse("2020-02-26T15:00Z"))));
    assertThat(
        response.getDateEnteredUtc(),
        is(equalTo(OffsetDateTime.parse("2020-02-28T20:37:42.865283200Z"))));
    assertThat(response.getDuration(), is(equalTo(30)));
    assertThat(
        response.getStatus(), is(equalTo(com.xero.models.project.TimeEntry.StatusEnum.ACTIVE)));

    // System.out.println(response.toString());
  }

  @Test
  public void getTimeEntryTest() throws IOException {
    System.out.println("@Test - getTimeEntryTest");
    UUID projectId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");
    UUID timeEntryId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");

    TimeEntry response = projectApi.getTimeEntry(accessToken, xeroTenantId, projectId, timeEntryId);
    assertThat(
        response.getTimeEntryId(),
        is(equalTo(UUID.fromString("3cd35eca-704f-4bca-b258-236028ae8ed1"))));
    assertThat(
        response.getUserId(), is(equalTo(UUID.fromString("740add2a-a703-4b8a-a670-1093919c2040"))));
    assertThat(
        response.getProjectId(),
        is(equalTo(UUID.fromString("b021e7cb-1903-4292-b48b-5b27b4271e3e"))));
    assertThat(
        response.getTaskId(), is(equalTo(UUID.fromString("7be77337-feec-4458-bb1b-dbaa5a4aafce"))));
    assertThat(response.getDateUtc(), is(equalTo(OffsetDateTime.parse("2020-02-27T15:00Z"))));
    assertThat(
        response.getDateEnteredUtc(),
        is(equalTo(OffsetDateTime.parse("2020-02-21T21:41:22.264272700Z"))));
    assertThat(response.getDuration(), is(equalTo(45)));
    assertThat(
        response.getStatus(), is(equalTo(com.xero.models.project.TimeEntry.StatusEnum.ACTIVE)));
    assertThat(response.getDescription(), is(equalTo("My description")));

    // System.out.println(response.toString());
  }
}
