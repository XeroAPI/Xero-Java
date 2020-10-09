package com.xero.api;

/**
 * Base application exception all other Xero exceptions should extend.
 */
public class XeroException extends RuntimeException {

    private static final long serialVersionUID = 1708393240501381142L;

    public XeroException() {
    }

    public XeroException(final Throwable cause) {
        super(cause);
    }

    public XeroException(final String message) {
        super(message);
    }

    public XeroException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
