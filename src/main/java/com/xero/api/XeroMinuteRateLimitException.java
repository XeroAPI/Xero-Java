package com.xero.api;

/** handle minute rate limit exception  */
public class XeroMinuteRateLimitException extends XeroRateLimitException {

    private static final long serialVersionUID = 1L;

    /** Init Xero Minute Rate Limit Exception 
    * @param statusCode int the server status code returned.
    * @param appLimitRemaining Integer the number of calls remaining for app limit
    * @param dayLimitRemaining Integer the number of calls in a 24 hour rolling window remain for an org
    * @param minuteLimitRemaining Integer the number of calls in a 60 second rolling window remain for an org
    * @param retryAfterSeconds Long the number of seconds to wait before resuming API calls
    * @param message String the message pertaining to the rate limit
    * @param e Exception object with details about the original exception
    */
    public XeroMinuteRateLimitException(final int statusCode, final Integer appLimitRemaining, final Integer dayLimitRemaining,
                                        final Integer minuteLimitRemaining,
                                        final Long retryAfterSeconds, final String message, final Exception e) {
        super(statusCode, appLimitRemaining, dayLimitRemaining, minuteLimitRemaining, retryAfterSeconds, message, e);
    }
}
