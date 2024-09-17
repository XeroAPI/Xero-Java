package com.xero.api.client;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.OffsetDateTime;

import com.xero.api.ApiClient;
import com.xero.models.project.Project;
import com.xero.models.project.ProjectCreateOrUpdate;
import com.xero.models.project.Projects;

public class ProjectsApiProjectsTest {

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
		defaultClient = new ApiClient("http://127.0.0.1:4018",null,null,null,null);
        projectApi = projectApi.getInstance(defaultClient);   
       
        // ADDED TO MANAGE RATE LIMITS while using SwaggerHub to mock APIs
        if (setUpIsDone) {
            return;
        }

        try {
            System.out.println("Sleep for 60 seconds");
            Thread.sleep(60);
        } catch(InterruptedException e) {
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
    public void getProjectsTest() throws IOException {
        System.out.println("@Test - getProjectsTest");
        List<UUID> projectIds = null;
        UUID contactID = null;
        String states = null;
        int page = 1;
        int pageSize = 50;
        Projects response = projectApi.getProjects(accessToken, xeroTenantId, projectIds, contactID, states, page, pageSize);
        
        assertThat(response.getPagination().getPage(), is(equalTo(1)));
        assertThat(response.getPagination().getItemCount(), is(equalTo(9)));
        assertThat(response.getPagination().getPageCount(), is(equalTo(1)));
        assertThat(response.getPagination().getPageSize(), is(equalTo(50)));
        assertThat(response.getItems().get(0).getContactId(), is(equalTo(UUID.fromString("00000000-0000-0000-0000-000000000000"))));
        assertThat(response.getItems().get(0).getProjectId(), is(equalTo(UUID.fromString("00000000-0000-0000-0000-000000000000"))));
        assertThat(response.getItems().get(0).getName(), is(equalTo("FooProject28916")));
        assertThat(response.getItems().get(0).getCurrencyCode(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getMinutesLogged(), is(equalTo(180)));
        assertThat(response.getItems().get(0).getTotalTaskAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getTotalTaskAmount().getValue(), is(equalTo(60.0)));
        assertThat(response.getItems().get(0).getTotalExpenseAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getTotalExpenseAmount().getValue(), is(equalTo(499.0)));
        assertThat(response.getItems().get(0).getMinutesToBeInvoiced(), is(equalTo(180)));
        assertThat(response.getItems().get(0).getTaskAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getTaskAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().get(0).getExpenseAmountToBeInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getExpenseAmountToBeInvoiced().getValue(), is(equalTo(499.0)));
        assertThat(response.getItems().get(0).getExpenseAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getExpenseAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().get(0).getProjectAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getProjectAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().get(0).getDeposit().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getDeposit().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().get(0).getDepositApplied().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getDepositApplied().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().get(0).getCreditNoteAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getCreditNoteAmount().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().get(0).getTotalInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getTotalInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getItems().get(0).getTotalToBeInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getTotalToBeInvoiced().getValue(), is(equalTo(559.0)));
        assertThat(response.getItems().get(0).getEstimate().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getItems().get(0).getEstimate().getValue(), is(equalTo(99.99)));        
        assertThat(response.getItems().get(0).getDeadlineUtc(), is(equalTo(OffsetDateTime.parse("2020-03-03T15:00Z"))));
        
        // System.out.println(response.toString());
    }

    @Test
    public void createProjectsTest() throws IOException {
        System.out.println("@Test - createProjectsTest");

        ProjectCreateOrUpdate projectCreateOrUpdate = new ProjectCreateOrUpdate();
        projectCreateOrUpdate.setName("New Kitchen");
        Project response = projectApi.createProject(accessToken, xeroTenantId, projectCreateOrUpdate, null);
        
        assertThat(response.getContactId(), is(equalTo(UUID.fromString("00000000-0000-0000-0000-000000000000"))));
        assertThat(response.getProjectId(), is(equalTo(UUID.fromString("00000000-0000-0000-0000-000000000000"))));
        assertThat(response.getName(), is(equalTo("New Kitchen")));
        assertThat(response.getCurrencyCode(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getMinutesLogged(), is(equalTo(0)));
        assertThat(response.getTotalTaskAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalTaskAmount().getValue(), is(equalTo(0.0)));
        assertThat(response.getTotalExpenseAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalExpenseAmount().getValue(), is(equalTo(0.0)));
        assertThat(response.getMinutesToBeInvoiced(), is(equalTo(0)));
        assertThat(response.getTaskAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTaskAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getExpenseAmountToBeInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getExpenseAmountToBeInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getExpenseAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getExpenseAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getProjectAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getProjectAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getDeposit().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getDeposit().getValue(), is(equalTo(0.0)));
        assertThat(response.getDepositApplied().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getDepositApplied().getValue(), is(equalTo(0.0)));
        assertThat(response.getCreditNoteAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getCreditNoteAmount().getValue(), is(equalTo(0.0)));
        assertThat(response.getTotalInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getTotalToBeInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalToBeInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getEstimate().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getEstimate().getValue(), is(equalTo(99.99)));        
        assertThat(response.getDeadlineUtc(), is(equalTo(OffsetDateTime.parse("2020-03-03T15:00Z"))));        
        assertThat(response.getStatus(), is(equalTo(com.xero.models.project.ProjectStatus.INPROGRESS)));

       // System.out.println(response.toString());
    }

    @Test
    public void getProjectTest() throws IOException {
        System.out.println("@Test - getProjectTest");
        UUID projectId = UUID.fromString("8138a266-fb42-49b2-a104-014b7045753d");  
        Project response = projectApi.getProject(accessToken, xeroTenantId, projectId);
        
        assertThat(response.getContactId(), is(equalTo(UUID.fromString("00000000-0000-0000-0000-000000000000"))));
        assertThat(response.getProjectId(), is(equalTo(UUID.fromString("00000000-0000-0000-0000-000000000000"))));
        assertThat(response.getName(), is(equalTo("Remodeling 2012")));
        assertThat(response.getCurrencyCode(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getMinutesLogged(), is(equalTo(180)));
        assertThat(response.getTotalTaskAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalTaskAmount().getValue(), is(equalTo(60.0)));
        assertThat(response.getTotalExpenseAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalExpenseAmount().getValue(), is(equalTo(499.0)));
        assertThat(response.getMinutesToBeInvoiced(), is(equalTo(180)));
        assertThat(response.getTaskAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTaskAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getExpenseAmountToBeInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getExpenseAmountToBeInvoiced().getValue(), is(equalTo(499.0)));
        assertThat(response.getExpenseAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getExpenseAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getProjectAmountInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getProjectAmountInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getDeposit().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getDeposit().getValue(), is(equalTo(0.0)));
        assertThat(response.getDepositApplied().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getDepositApplied().getValue(), is(equalTo(0.0)));
        assertThat(response.getCreditNoteAmount().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getCreditNoteAmount().getValue(), is(equalTo(0.0)));
        assertThat(response.getTotalInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalInvoiced().getValue(), is(equalTo(0.0)));
        assertThat(response.getTotalToBeInvoiced().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getTotalToBeInvoiced().getValue(), is(equalTo(559.0)));
        assertThat(response.getEstimate().getCurrency(), is(equalTo(com.xero.models.project.CurrencyCode.AUD)));
        assertThat(response.getEstimate().getValue(), is(equalTo(99.99)));        
        assertThat(response.getDeadlineUtc(), is(equalTo(OffsetDateTime.parse("2020-03-03T15:00Z"))));
        
        // System.out.println(response.toString());
    }
}
