package com.xero.api;

import com.xero.models.accounting.Error;

import java.util.HashMap;
import java.util.Map;

public class XeroApiException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private final int responseCode;
  private String message;
  private Map<String, String> messageMap = new HashMap<String, String>();
  private Error error;

  // LEGACY ERROR HANDLER
  public XeroApiException(int responseCode) {
    super(responseCode + " response.");
    this.responseCode = responseCode;
  }

  public XeroApiException(int responseCode, String message) {
    super(responseCode + " response: " + message);
    this.responseCode = responseCode;
    this.message = message;
  }

  public XeroApiException(int responseCode, Map<String, String> map) {
    super(responseCode + "response");
    this.responseCode = responseCode;
    for (Map.Entry<String, String> entry : map.entrySet()) {
      if (this.message == null) {
        this.message = entry.getValue() + " - ";
      } else {
        this.message = this.message + entry.getValue() + " ";
      }
    }
    this.messageMap = map;
  }

  public XeroApiException(int responseCode, Error error) {
    super(responseCode + " response: none");
    this.responseCode = responseCode;
    this.error = error;
  }

  public XeroApiException(int responseCode, String message, Error error) {
    super(responseCode + " response: " + message);
    this.responseCode = responseCode;
    this.message = message;
    this.error = error;
  }

  public int getResponseCode() {
    return responseCode;
  }

  public String getMessage() {
    return message;
  }

  public Map<String, String> getMessages() {
    return messageMap;
  }

  public Error getError() {
    return error;
  }
}
