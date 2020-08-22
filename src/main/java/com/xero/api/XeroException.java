package com.xero.api;

/**
 * Base application exceptions all other Xero exceptions should extend.
 */
public class XeroException extends RuntimeException {

    private static final long serialVersionUID = 1708393240501381142L;

    public XeroException() {
    }

    public XeroException(final String message) {
        super(message);
    }

}
