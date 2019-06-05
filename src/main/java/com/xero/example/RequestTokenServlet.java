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

public class RequestTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private Config config = null;

	public RequestTokenServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		final String clientId = "--your-client-id--";
        final String clientSecret = "--your-client-secret--";
        final String secretState = "secret" + new Random().nextInt(999_999);
        final OAuth20Service service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .defaultScope("openid email profile offline_access accounting.settings accounting.transactions") // replace with desired scope
                .callback("http://localhost/Callback")
                .build(XeroApi20.instance());
        
        // Obtain the Authorization URL
        final String authorizationUrl = service.createAuthorizationUrlBuilder()
                .state(secretState)
                .build();

		response.sendRedirect(authorizationUrl);	
	}
}
