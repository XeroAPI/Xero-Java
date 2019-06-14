package com.xero.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.xero.api.XeroApi20;


/**
 * Servlet implementation class Callback
 */
@WebServlet("/Callback")
public class Callback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Callback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String clientId = "-your-clientid-";
        final String clientSecret = "-your-clientsecret-";
        final String redirectURI = "-your-redirecturi-";
        final OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .defaultScope("openid email profile offline_access accounting.settings accounting.transactions") // replace with desired scope
                .callback(redirectURI)
                .build(XeroApi20.instance());
        

        PrintWriter respWriter = response.getWriter();
		response.setStatus(200);
		response.setContentType("text/html"); 
		respWriter.println("<div class=\"container\">Let's make <a href=\"./AuthenticatedResource\">API calls</a></div>");
     	
		String code = "123";
		if (request.getParameter("code") != null) {   
			code = request.getParameter("code");
		}
		

        OAuth2AccessToken accessToken;
		try {
			accessToken = service.getAccessToken(code);
			TokenStorage store = new TokenStorage();
			store.saveItem(response, "access_token", accessToken.getAccessToken());
			store.saveItem(response, "refresh_token", accessToken.getRefreshToken());

			// REFRESH BITS
			//accessToken = service.refreshAccessToken(accessToken.getRefreshToken());
			//store.saveItem(response, "access_token", accessToken.getAccessToken());
			
			// GET CONNECTIONS
			String requestUrl = "https://api.xero.com/connections";
			final OAuthRequest requestConn = new OAuthRequest(Verb.GET, requestUrl);
	        requestConn.addHeader("Accept", "application/json");
	        service.signRequest(accessToken.getAccessToken(), requestConn);
            final Response responseConn = service.execute(requestConn);
         
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(responseConn.getBody());
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            System.out.println(jsonObject.get("tenantId"));
            store.saveItem(response, "tenant_id", jsonObject.get("tenantId").toString());
            
            response.sendRedirect("./AuthenticatedResource");

		} catch (InterruptedException | ExecutionException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
