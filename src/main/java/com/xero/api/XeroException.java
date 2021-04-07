package com.xero.api;

/**
* Base application exception all other Xero exceptions should extend.
*/
public class XeroException extends RuntimeException {

    private static final long serialVersionUID = 1708393240501381142L;

    /** Init Xero Exception */
    public XeroException() {
    }

    /** Init Xero Exception
    * @param cause Throwable reason for exception
    */
    public XeroException(final Throwable cause) {
        super(cause);
    }

    /** Init Xero Exception
    * @param message String details about the exception
    */
    public XeroException(final String message) {
        super(message);
    }

    /** Init Xero Exception
    * @param cause Throwable reason for exception
    * @param message String details about the exception
    */
    public XeroException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
