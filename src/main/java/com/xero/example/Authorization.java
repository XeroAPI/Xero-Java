package com.xero.example;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.xero.api.ApiClient;
import com.xero.api.XeroApi20;

/**
 * Servlet implementation class Authorization
 */
@WebServlet("/Authorization")
public class Authorization extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authorization() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TokenStorage store = new TokenStorage();
		final String clientId = "-your-clientid-";
        final String clientSecret = "-your-clientsecret-";
        final String redirectURI = "-your-redirecturi-";
        final String secretState = "secret" + new Random().nextInt(999_999);
        // Configure OAuth2 access token for authorization: oAuth2AuthCode
	    final OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .defaultScope("openid email profile offline_access accounting.settings accounting.transactions accounting.contacts  accounting.journals.read accounting.reports.read accounting.attachments paymentservices") // replace with desired scope
                .callback(redirectURI)
                .build(XeroApi20.instance());
        
        // Obtain the Authorization URL
        final String authorizationUrl = service.createAuthorizationUrlBuilder()
                .state(secretState)
                .build();
        System.out.println(authorizationUrl);
		response.sendRedirect(authorizationUrl);	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
