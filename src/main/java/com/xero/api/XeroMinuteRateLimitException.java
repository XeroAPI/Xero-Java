package com.xero.api;

public class XeroMinuteRateLimitException extends XeroRateLimitException {

    private static final long serialVersionUID = 1L;

    public XeroMinuteRateLimitException(final int statusCode, final Integer appLimitRemaining, final Integer dayLimitRemaining,
                                        final Integer minuteLimitRemaining,
                                        final Long retryAfterSeconds, final String message, final Exception e) {
        super(statusCode, appLimitRemaining, dayLimitRemaining, minuteLimitRemaining, retryAfterSeconds, message, e);
    }
}
