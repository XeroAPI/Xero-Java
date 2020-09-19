package com.xero.api;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class XeroExceptionsTest {

    @InjectMocks
    private static XeroApiExceptionHandler xeroApiExceptionHandler;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private com.xero.models.accounting.Error error;
    @Mock
    private HttpResponseException httpResponseException;
    @Mock
    private HttpHeaders httpHeaders;
    @Mock
    private com.xero.models.payrolluk.Problem problem;

    private final String objectType = "objectType";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testXeroBadRequestException() {
        Exception e = new Exception();
        // XeroBadRequestException extends XeroException so we can catch either
        expectedException.expect(XeroException.class);
        expectedException.expect(is(new XeroBadRequestException(objectType, error, e)));

        xeroApiExceptionHandler.validationError(objectType, error, e);
    }

    @Test
    public void testXeroMethodNotAllowedException() {
        Exception e = new Exception();
        // XeroMethodNotAllowedException extends XeroException so we can catch either
        expectedException.expect(XeroException.class);
        expectedException.expect(is(new XeroMethodNotAllowedException(objectType, problem, e)));

        xeroApiExceptionHandler.validationError(405, objectType, problem, e);
    }

    @Test
    public void testXeroUnauthorizedException() {
        int statusCode = 401;
        String message = "Unauthorized - check your scopes and confirm access to this resource";

        // XeroUnauthorizedException extends XeroAuthenticationException and XeroException so we can catch either
        expectedException.expect(XeroException.class);
        expectedException.expect(XeroAuthenticationException.class);
        expectedException.expect(XeroUnauthorizedException.class);
        expectedException.expectMessage(message);

        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        xeroApiExceptionHandler.execute(httpResponseException);
    }

    @Test
    public void testXeroForbiddenException() {
        int statusCode = 403;
        String message = "Forbidden - authentication unsuccessful";

        // XeroForbiddenException extends XeroAuthenticationException and XeroException so we can catch either
        expectedException.expect(XeroException.class);
        expectedException.expect(XeroAuthenticationException.class);
        expectedException.expect(XeroForbiddenException.class);
        expectedException.expectMessage(message);

        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        xeroApiExceptionHandler.execute(httpResponseException);
    }

    @Test
    public void testXeroNotFoundException() {
        int statusCode = 404;
        String message = "The resource you're looking for cannot be found";

        // XeroNotFoundException extends XeroException so we can catch either
        expectedException.expect(XeroException.class);
        expectedException.expect(XeroNotFoundException.class);
        expectedException.expectMessage(message);

        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        xeroApiExceptionHandler.execute(httpResponseException);
    }

    @Test
    public void testXeroRateLimitException() {
        int statusCode = 429;
        String message = "You've exceeded the per 100 rate limit";

        // XeroRateLimitException extends XeroException so we can catch either
        expectedException.expect(XeroException.class);
        expectedException.expect(XeroRateLimitException.class);
        expectedException.expectMessage(message);

        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        when(httpResponseException.getHeaders()).thenReturn(httpHeaders);
        when(httpHeaders.get("x-rate-limit-problem")).thenReturn(100);
        xeroApiExceptionHandler.execute(httpResponseException);
    }

    @Test
    public void testXeroServerErrorException() {
        int statusCode = 500;
        String message = "An error occurred in Xero. Check the API Status page http://status.developer.xero.com for current service status.";

        // XeroServerErrorException extends XeroException so we can catch either
        expectedException.expect(XeroException.class);
        expectedException.expect(XeroServerErrorException.class);
        expectedException.expectMessage(message);

        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        xeroApiExceptionHandler.execute(httpResponseException);
    }

    @Test
    public void testXeroApiException() {
        int statusCode = 1;
        String message = "Unknown http status code 1";

        // XeroApiException extends XeroException so we can catch either
        expectedException.expect(XeroException.class);
        expectedException.expect(XeroApiException.class);
        expectedException.expectMessage(message);

        when(httpResponseException.getStatusMessage()).thenReturn(message);
        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        xeroApiExceptionHandler.execute(httpResponseException);
    }

}
