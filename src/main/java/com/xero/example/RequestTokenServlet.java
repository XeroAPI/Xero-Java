package com.xero.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xero.api.Config;
import com.xero.api.JsonConfig;
import com.xero.api.OAuthAuthorizeToken;
import com.xero.api.OAuthRequestToken;
import com.xero.api.XeroApiException;

public class RequestTokenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Config config = JsonConfig.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestTokenServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// IF Xero App is Private > 2 legged oauth - fwd to RequestResouce Servlet
		if(config.getAppType().equals("PRIVATE")) {
			response.sendRedirect("./callback.jsp");
		} else {
			OAuthRequestToken requestToken = new OAuthRequestToken(config);
			try {
				requestToken.execute();	
				// DEMONSTRATION ONLY - Store in Cookie - you can extend TokenStorage
				// and implement the save() method for your database
				TokenStorage storage = new TokenStorage();
				storage.save(response,requestToken.getAll());

				//Build the Authorization URL and redirect User
				OAuthAuthorizeToken authToken = new OAuthAuthorizeToken(config, requestToken.getTempToken());
				response.sendRedirect(authToken.getAuthUrl());	
			} catch (XeroApiException e) {
				String message = java.net.URLDecoder.decode(e.getMessage(), "UTF-8");
				PrintWriter respWriter = response.getWriter();
				response.setStatus(200);
				response.setContentType("text/html"); 
				respWriter.println("Error code:" + e.getResponseCode() + " Message:" + message);
			}
		}
	}
}
