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

public class ProjectsApiProjectUsersTest {

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

    // Init projectApi client
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
  public void getProjectUsersTest() throws IOException {
    System.out.println("@Test - getProjectUsersTest");

    int page = 1;
    int pageSize = 50;
    ProjectUsers response = projectApi.getProjectUsers(accessToken, xeroTenantId, page, pageSize);

    assertThat(response.getPagination().getPage(), is(equalTo(1)));
    assertThat(response.getPagination().getItemCount(), is(equalTo(2)));
    assertThat(response.getPagination().getPageCount(), is(equalTo(1)));
    assertThat(response.getPagination().getPageSize(), is(equalTo(50)));
    assertThat(
        response.getItems().get(0).getUserId(),
        is(equalTo(UUID.fromString("740add2a-a703-4b8a-a670-1093919c2040"))));
    assertThat(response.getItems().get(0).getName(), is(equalTo("Sidney Maestre")));
    assertThat(response.getItems().get(0).getEmail(), is(equalTo("sid.maestre@xero.com")));

    // System.out.println(response.toString());
  }
}
