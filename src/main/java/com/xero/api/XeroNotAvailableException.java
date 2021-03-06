package com.xero.api;

/** handle not available exception  */
public class XeroNotAvailableException extends XeroException {

    private static final long serialVersionUID = 1L;
    private int statusCode = 0;
    private String message;

    /** XeroNotAvailableException
     * @param statusCode Integer the server status code returned.
     * @param message String with details about the exception
     * @param e Exception object with details about the original exception
     */
    public XeroNotAvailableException(int statusCode, String message, Exception e) {
        super(statusCode + " : " + message, e);
        this.statusCode = statusCode;
        this.message = message;
    }

    /**
     * get status code
     * @return statusCode
     **/
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * get message
     * @return message
     **/
    public String getMessage() {
        return message;
    }

}
