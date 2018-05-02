package com.xero.api.exception;

import com.google.api.client.http.HttpResponseException;
import com.xero.api.XeroApiException;
import com.xero.model.ApiException;
import com.xero.model.Elements;
import com.xero.model.Invoice;
import com.xero.model.Payment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class XeroExceptionHandlerTest {

    @InjectMocks
    private XeroExceptionHandler xeroExceptionHandler;
    @Mock
    private HttpResponseException httpResponseException;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void handlePaymentBadRequest() throws Exception {
        when(httpResponseException.getContent()).thenReturn(samplePaymentExceptionContent());
        XeroApiException xeroApiException = xeroExceptionHandler.handleBadRequest(httpResponseException);
        assertNotNull(xeroApiException);
        ApiException apiException = xeroApiException.getApiException();
        assertNotNull(apiException);
        assertFalse(apiException.getElements().isEmpty());
        Elements elements = apiException.getElements().get(0);
        assertFalse(elements.getDataContractBase().isEmpty());
        Object object = elements.getDataContractBase().get(0);
        assertTrue(object instanceof Payment);
    }

    @Test
    public void handleInvoiceBadRequest() throws Exception {
        when(httpResponseException.getContent()).thenReturn(sampleInvoiceExceptionContent());
        XeroApiException xeroApiException = xeroExceptionHandler.handleBadRequest(httpResponseException);
        assertNotNull(xeroApiException);
        ApiException apiException = xeroApiException.getApiException();
        assertNotNull(apiException);
        assertFalse(apiException.getElements().isEmpty());
        Elements elements = apiException.getElements().get(0);
        assertFalse(elements.getDataContractBase().isEmpty());
        Object object = elements.getDataContractBase().get(0);
        assertTrue(object instanceof Invoice);
    }

    @Test
    public void handleOAuthErrorRequest() throws Exception {
        when(httpResponseException.getContent()).thenReturn(sampleOAuthExceptionContent());
        XeroApiException xeroApiException = xeroExceptionHandler.handleBadRequest(httpResponseException);
        assertNotNull(xeroApiException);
        assertNotNull(xeroApiException.getMessages());
        assertNotNull(xeroApiException.getMessage());
    }

    @Test
    public void handleOtherErrorRequest() throws Exception {
        when(httpResponseException.getContent()).thenReturn("dummy");
        when(httpResponseException.getStatusCode()).thenReturn(400);
        XeroApiException xeroApiException = xeroExceptionHandler.handleBadRequest(httpResponseException);
        assertNotNull(xeroApiException);
        assertNotNull(xeroApiException.getMessages());
        assertNotNull(xeroApiException.getMessage());
        assertEquals(xeroApiException.getResponseCode(), 400);
    }

    /**
     * sample oauth exception
     * @return oauth exception
     */
    private String sampleOAuthExceptionContent() {
        return "oauth_problem=signature_method_rejected&oauth_problem_advice=Public%20applications%20must%20use%20the%20HMAC-SHA1%20signature%20method";
    }


    /**
     * extracted a sample payment exception
     *
     * @return sample payment exception
     */
    private String samplePaymentExceptionContent() {
        return "<ApiException xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> " +
            "  <ErrorNumber>10</ErrorNumber> " +
            "  <Type>ValidationException</Type> " +
            "  <Message>A validation exception occurred</Message> " +
            "  <Elements> " +
            "    <DataContractBase xsi:type=\"Payment\"> " +
            "      <ValidationErrors> " +
            "        <ValidationError> " +
            "          <Message>Account could not be found</Message> " +
            "        </ValidationError> " +
            "        <ValidationError> " +
            "          <Message>Invoice could not be found</Message> " +
            "        </ValidationError> " +
            "      </ValidationErrors> " +
            "      <Date>2017-10-26T00:00:00</Date> " +
            "      <Amount>1.00</Amount> " +
            "      <Account> " +
            "        <AccountID>297c2dc5-cc47-4afd-8ec8-74990b8761e9</AccountID> " +
            "      </Account> " +
            "      <Invoice> " +
            "        <InvoiceID>1129952a-d04c-45e6-b829-9788369b4ecf</InvoiceID> " +
            "      </Invoice> " +
            "    </DataContractBase> " +
            "    <DataContractBase xsi:type=\"Payment\"> " +
            "      <ValidationErrors> " +
            "        <ValidationError> " +
            "          <Message>Account could not be found</Message> " +
            "        </ValidationError> " +
            "        <ValidationError> " +
            "          <Message>Invoice could not be found</Message> " +
            "        </ValidationError> " +
            "      </ValidationErrors> " +
            "      <Date>2017-10-26T00:00:00</Date> " +
            "      <Amount>1.00</Amount> " +
            "      <Account> " +
            "        <AccountID>297c2dc5-cc47-4afd-8ec8-74990b8761ed</AccountID> " +
            "      </Account> " +
            "      <Invoice> " +
            "        <InvoiceID>1129952a-d04c-45e6-b829-9788369b4ecd</InvoiceID> " +
            "      </Invoice> " +
            "    </DataContractBase> " +
            "  </Elements> " +
            "</ApiException>";
    }

    /**
     * extracted a sample invoice exception
     *
     * @return sample invoice exception
     */
    private String sampleInvoiceExceptionContent() {
        return "<ApiException xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "  <ErrorNumber>10</ErrorNumber>\n" +
            "  <Type>ValidationException</Type>\n" +
            "  <Message>A validation exception occurred</Message>\n" +
            "  <Elements>\n" +
            "    <DataContractBase xsi:type=\"Invoice\">\n" +
            "      <ValidationErrors>\n" +
            "        <ValidationError>\n" +
            "          <Message>The Contact must contain at least 1 of the following elements to identify the contact: Name, ContactID, ContactNumber</Message>\n" +
            "        </ValidationError>\n" +
            "      </ValidationErrors>\n" +
            "      <Contact>\n" +
            "        <ContactID>00000000-0000-0000-0000-000000000000</ContactID>\n" +
            "      </Contact>\n" +
            "      <Date>2017-10-26T00:00:00</Date>\n" +
            "      <DueDate>2017-11-02T00:00:00</DueDate>\n" +
            "      <Status>DRAFT</Status>\n" +
            "      <LineAmountTypes>Exclusive</LineAmountTypes>\n" +
            "      <LineItems>\n" +
            "        <LineItem>\n" +
            "          <Description>Monthly rental for property at 56a Wilkins Avenue</Description>\n" +
            "          <UnitAmount>395.00</UnitAmount>\n" +
            "          <TaxType>OUTPUT</TaxType>\n" +
            "          <TaxAmount>240.00</TaxAmount>\n" +
            "          <LineAmount>1714.30</LineAmount>\n" +
            "          <AccountCode>200</AccountCode>\n" +
            "          <Quantity>4.3400</Quantity>\n" +
            "        </LineItem>\n" +
            "      </LineItems>\n" +
            "      <SubTotal>1714.30</SubTotal>\n" +
            "      <TotalTax>240.00</TotalTax>\n" +
            "      <Total>1954.30</Total>\n" +
            "      <CurrencyCode>ZAR</CurrencyCode>\n" +
            "      <Type>ACCREC</Type>\n" +
            "      <InvoiceID>00000000-0000-0000-0000-000000000000</InvoiceID>\n" +
            "    </DataContractBase>\n" +
            "    <DataContractBase xsi:type=\"Invoice\">\n" +
            "      <ValidationErrors>\n" +
            "        <ValidationError>\n" +
            "          <Message>The Contact must contain at least 1 of the following elements to identify the contact: Name, ContactID, ContactNumber</Message>\n" +
            "        </ValidationError>\n" +
            "      </ValidationErrors>\n" +
            "      <Contact>\n" +
            "        <ContactID>00000000-0000-0000-0000-000000000000</ContactID>\n" +
            "      </Contact>\n" +
            "      <Date>2017-10-27T00:00:00</Date>\n" +
            "      <DueDate>2017-11-02T00:00:00</DueDate>\n" +
            "      <Status>DRAFT</Status>\n" +
            "      <LineAmountTypes>Exclusive</LineAmountTypes>\n" +
            "      <LineItems>\n" +
            "        <LineItem>\n" +
            "          <Description>Monthly rental for property at 56a Wilkins Avenue</Description>\n" +
            "          <UnitAmount>395.00</UnitAmount>\n" +
            "          <TaxType>OUTPUT</TaxType>\n" +
            "          <TaxAmount>240.00</TaxAmount>\n" +
            "          <LineAmount>1714.30</LineAmount>\n" +
            "          <AccountCode>200</AccountCode>\n" +
            "          <Quantity>4.3400</Quantity>\n" +
            "        </LineItem>\n" +
            "      </LineItems>\n" +
            "      <SubTotal>1714.30</SubTotal>\n" +
            "      <TotalTax>240.00</TotalTax>\n" +
            "      <Total>1954.30</Total>\n" +
            "      <CurrencyCode>ZAR</CurrencyCode>\n" +
            "      <Type>ACCREC</Type>\n" +
            "      <InvoiceID>00000000-0000-0000-0000-000000000000</InvoiceID>\n" +
            "    </DataContractBase>\n" +
            "  </Elements>\n" +
            "</ApiException>";
    }

}
