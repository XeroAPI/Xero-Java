package com.xero.example;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
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
		// Replace these with your client id and secret
        final String clientId = "--your-clientid--";
        final String clientSecret = "--your-clientsecret--";
        final String secretState = "secret" + new Random().nextInt(999_999);
        final OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .defaultScope("openid email profile offline_access accounting.settings accounting.transactions") // replace with desired scope
                .callback("http://localhost:8080/Callback")
                .build(XeroApi20.instance());
        
        // Obtain the Authorization URL
        final String authorizationUrl = service.createAuthorizationUrlBuilder()
                .state(secretState)
                .build();

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
