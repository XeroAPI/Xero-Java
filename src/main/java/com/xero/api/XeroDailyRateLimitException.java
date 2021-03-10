package com.xero.api;

public class XeroDailyRateLimitException extends XeroRateLimitException {

    private static final long serialVersionUID = 1L;

    public XeroDailyRateLimitException(final int statusCode, final Integer appLimitRemaining, final Integer dayLimitRemaining,
                                       final Integer minuteLimitRemaining,
                                       final Long retryAfterSeconds, final String message, final Exception e) {
        super(statusCode, appLimitRemaining, dayLimitRemaining, minuteLimitRemaining, retryAfterSeconds, message, e);
    }
}
