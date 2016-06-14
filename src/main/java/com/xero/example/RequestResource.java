package com.xero.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.http.HttpResponse;
import com.xero.api.Config;
import com.xero.api.OAuthAccessToken;
import com.xero.api.OAuthRequestResource;
import com.xero.api.TokenStorage;

public class RequestResource extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private Config c = Config.getInstance();  

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestResource() 
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// Get API Resource params from URL
		String resource = request.getParameter("resource");
		String method = request.getParameter("method");
		String body = request.getParameter("body");

		// Get Xero API Resource - DEMONSTRATION ONLY get token from Cookie
		TokenStorage storage = new TokenStorage();
		HttpResponse resp = null;
		OAuthRequestResource req = new OAuthRequestResource(resource,method,body);

		OAuthAccessToken refreshToken = new OAuthAccessToken();
		
		
		if(c.getAppType().equals("PARTNER") && refreshToken.isStale(storage.get(request, "tokenTimestamp")))
		{
			System.out.println("Time for a refresh");

			refreshToken.setToken(storage.get(request, "token"));
			refreshToken.setTokenSecret(storage.get(request, "tokenSecret"));
			refreshToken.setSessionHandle(storage.get(request, "sessionHandle"));
			refreshToken.build().execute();

			// DEMONSTRATION ONLY - Store in Cookie - you can extend TokenStorage
			// and implement the save() method for your database
			storage.save(response,refreshToken.getAll());

			req.setToken( refreshToken.getToken());
			resp = req.execute(); 
		} 
		else 
		{
			System.out.println("Using the existing token");

			req.setToken(storage.get(request, "token"));
			req.setTokenSecret(storage.get(request, "tokenSecret"));
			resp = req.execute();
		}

		PrintWriter respWriter = response.getWriter();
		response.setStatus(200);
		response.setContentType("text/html"); 
		respWriter.println("<i>API Response:<br> " + resp.parseAsString());
	}
}
