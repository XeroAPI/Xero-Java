package com.xero.api;

public class XeroNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private int statusCode = 0;
  private String message;

  public XeroNotFoundException(int statusCode, String message) {
    super(statusCode + " : " + message);
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
