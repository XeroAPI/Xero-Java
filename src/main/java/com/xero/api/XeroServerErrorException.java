package com.xero.api;

/** handle server error exception  */
public class XeroServerErrorException extends XeroException {

    private static final long serialVersionUID = 1L;
    private int statusCode = 0;
    private String message;
    
    /** Init XeroServerErrorException 
    * @param statusCode int the server status code returned.
    * @param message String the message pertaining to the rate limit
    * @param e Exception object with details about the original exception
    */
    public XeroServerErrorException(int statusCode, String message, Exception e) {
        super(statusCode + " : " + message, e);
        this.statusCode = statusCode;
        this.message = message;
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

}
