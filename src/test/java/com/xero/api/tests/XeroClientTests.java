package com.xero.api.tests;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xero.api.Config;
import com.xero.api.JsonConfig;
import com.xero.api.XeroClient;
import com.xero.model.Contact;

public class XeroClientTests {
	XeroClient client;
	Config config;

	// Squid Proxy Error Code is 407 (Proxy Authentication Required)
	protected static final int PROXY_ERROR_CODE = 407;

	@Before
	public void setUp() {
		client = new XeroClient();
		config = JsonConfig.getInstance();
	}

	@After
	public void tearDown() {
		client = null;
	}

	@Test
	public void testGetContacts() {
		List<Contact> contactList;
		try {
			contactList = client.getContacts();
			assert (contactList.size() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
