package com.xero.api;

/**
 * Base authentication exception all other Xero authentication exceptions should extend.
 */
public class XeroAuthenticationException extends XeroException {
    private static final long serialVersionUID = -6292824871010327632L;

    /** Xero Authentication Exception 
    * @param message String with details about the exception
    * @param e Exception object with details about the original exception
    */
    public XeroAuthenticationException(final String message, final Exception e) {
        super(message, e);
    }
}
