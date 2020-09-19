package com.xero.api;

public class XeroServerErrorException extends XeroException {

    private static final long serialVersionUID = 1L;
    private int statusCode = 0;
    private String message;
    
    public XeroServerErrorException(int statusCode, String message, Exception e) {
        super(statusCode + " : " + message, e);
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

}
