package com.xero.api;

/**
 * Base application exception all other rate limit Xero exceptions should extend
 */
public class XeroRateLimitException extends XeroException {

    private static final long serialVersionUID = 1L;
    private int statusCode = 0;
    private String message;

    /**
     * The remaining app limit
     */
    private final Integer appLimitRemaining;

    /**
     * The remaining day limit
     */
    private final Integer dayLimitRemaining;

    /**
     * The remaining minute limit
     */
    private final Integer minuteLimitRemaining;

    /**
     * retryAfterSeconds that tells you how many seconds to wait before making another request.
     * Requests are counted against a fixed window which will reset at different times for each tenant.
     * It is important to use the Retry-After header to know when you can start making calls again.
     */
    private final Long retryAfterSeconds;

    public XeroRateLimitException(int statusCode, Integer appLimitRemaining, Integer dayLimitRemaining, Integer minuteLimitRemaining,
                                  Long retryAfterSeconds, String message, Exception e) {
        super(statusCode + " : " + message, e);
        this.statusCode = statusCode;
        this.appLimitRemaining = appLimitRemaining;
        this.dayLimitRemaining = dayLimitRemaining;
        this.minuteLimitRemaining = minuteLimitRemaining;
        this.message = message;
        this.retryAfterSeconds = retryAfterSeconds;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Integer getAppLimitRemaining() {
        return appLimitRemaining;
    }

    public Integer getDayLimitRemaining() {
        return dayLimitRemaining;
    }

    public Integer getMinuteLimitRemaining() {
        return minuteLimitRemaining;
    }

    public long getRetryAfterSeconds() {
        return retryAfterSeconds;
    }
}
