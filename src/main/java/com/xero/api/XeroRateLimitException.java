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

    /** Init XeroRateLimitException 
    * @param statusCode int the server status code returned.
    * @param appLimitRemaining Integer the number of calls remaining for app limit
    * @param dayLimitRemaining Integer the number of calls in a 24 hour rolling window remain for an org
    * @param minuteLimitRemaining Integer the number of calls in a 60 second rolling window remain for an org
    * @param retryAfterSeconds Long the number of seconds to wait before resuming API calls
    * @param message String the message pertaining to the rate limit
    * @param e Exception object with details about the original exception
    */
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

    /** get Status Code 
    * @return int with server status code.
    */
    public int getStatusCode() {
        return statusCode;
    }

    /** get message
    * @return Sting with the message pertaining to the rate limit
    */
    public String getMessage() {
        return message;
    }

    /** get remaining app limit
    * @return Integer the number of calls remaining for app limit
    */
    public Integer getAppLimitRemaining() {
        return appLimitRemaining;
    }

    /** get remaining daily limit
    * @return Integer the number of calls in a 24 hour rolling window remain for an org
    */
    public Integer getDayLimitRemaining() {
        return dayLimitRemaining;
    }

    /** get remaining minute limit
    * @return Integer the number of calls in a 60 second rolling window remain for an org
    */
    public Integer getMinuteLimitRemaining() {
        return minuteLimitRemaining;
    }

    /** get retry after seconds
    * @return Long the number of seconds to wait before resuming API calls
    */
    public Long getRetryAfterSeconds() {
        return retryAfterSeconds;
    }
}
