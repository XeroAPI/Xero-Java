package com.xero.api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XeroClientTest {

    private XeroClient xeroClient;

    @Before
    public void setup() {
        xeroClient = new XeroClient(null, null);
    }

    @Test
    public void testNormalizeFileNameForURI() {
        assertEquals("1527238288566.png", xeroClient.normalizeFileNameForURI("1527238288566.png"));
        assertEquals("V12_abc.png", xeroClient.normalizeFileNameForURI("V12 abc.png"));
        assertEquals("Ab1_23ab.png", xeroClient.normalizeFileNameForURI("Ab1 23ab.png"));
        assertEquals("abc.png", xeroClient.normalizeFileNameForURI(" abc.png "));
        assertEquals("a-b.png", xeroClient.normalizeFileNameForURI("a-b.png"));
        assertEquals("a_b.png", xeroClient.normalizeFileNameForURI("a_b.png"));
        assertEquals("a__b.png", xeroClient.normalizeFileNameForURI("a  b.png"));
        assertEquals("a~b.png", xeroClient.normalizeFileNameForURI("a~b.png"));
    }
}
