package com.xero.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xero.api.OAuthAccessToken;
import com.xero.api.TokenStorage;
import com.xero.api.Config;

public class CallbackServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private Config config = Config.getInstance();

	public CallbackServlet() 
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		// DEMONSTRATION ONLY - retrieve TempToken from Cookie
		TokenStorage storage = new TokenStorage();

		// retrieve OAuth verifier code from callback URL param
		String verifier = request.getParameter("oauth_verifier");

		// Swap your temp token for 30 oauth token
		OAuthAccessToken accessToken = new OAuthAccessToken(config);
		accessToken.build(verifier,storage.get(request,"tempToken"),storage.get(request,"tempTokenSecret")).execute();

		if(!accessToken.isSuccess())
		{
			storage.clear(response);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else 
		{
			// DEMONSTRATION ONLY - Store in Cookie - you can extend TokenStorage
			// and implement the save() method for your database
			storage.save(response,accessToken.getAll());			
			request.getRequestDispatcher("callback.jsp").forward(request, response);			
		}
	}	
}