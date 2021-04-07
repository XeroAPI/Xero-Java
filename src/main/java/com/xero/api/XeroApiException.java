package com.xero.api;

import com.xero.models.accounting.Error;

import java.util.HashMap;
import java.util.Map;

/** Legacy XeroApiException general exception class for returning exception details  */
public class XeroApiException extends XeroException {

    private static final long serialVersionUID = 1L;
    private final int responseCode;
    private String message;
    private Map<String, String> messageMap = new HashMap<String, String>();
    private Error error;

    /** Init Xero API Exception
    * @param responseCode int of the status code returned by the API
    */
    public XeroApiException(int responseCode) {
        super(responseCode + " response.");
        this.responseCode = responseCode;
    }

    /** Init Xero API Exception
    * @param responseCode int of the status code returned by the API
    * @param message String details about the exception
    * @param e Exception the original exception thrown
    */
    public XeroApiException(int responseCode, String message, Exception e) {
        super(responseCode + " response: " + message, e);
        this.responseCode = responseCode;
        this.message = message;
    }

    /** Init Xero API Exception
    * @param responseCode int of the status code returned by the API
    * @param map Map&lt;String, String&gt; array of details about the exception
    */
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

    /** Init Xero API Exception
    * @param responseCode int of the status code returned by the API
    * @param error Error details about the exception
    * @param e Exception the original exception thrown
    */
    public XeroApiException(int responseCode, Error error, Exception e) {
        super(responseCode + " response: none", e);
        this.responseCode = responseCode;
        this.error = error;
    }

    /** Init Xero API Exception
    * @param responseCode int of the status code returned by the API
    * @param message String details about the exception
    * @param error Error details about the exception
    * @param e Exception the original exception thrown
    */
    public XeroApiException(int responseCode, String message, Error error, Exception e) {
        super(responseCode + " response: " + message, e);
        this.responseCode = responseCode;
        this.message = message;
        this.error = error;
    }
    
    /** Init Xero API Exception
    * @return int of the status code returned by the API
    */
    public int getResponseCode() {
        return responseCode;
    }

    /** Init Xero API Exception
    * @return String with the details about the exception
    */
    public String getMessage() {
        return message;
    }

    /** Init Xero API Exception
    * @return Map&lt;String, String&gt; array of messages about the exception
    */
    public Map<String, String> getMessages() {
        return messageMap;
    }

    /** Init Xero API Exception
    * @return Error object with information about the exception
    */
    public Error getError() {
        return error;
    }

}
