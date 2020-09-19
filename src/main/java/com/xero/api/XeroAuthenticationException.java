package com.xero.api;

/**
 * Base authentication exception all other Xero authentication exceptions should extend.
 */
public class XeroAuthenticationException extends XeroException {
    private static final long serialVersionUID = -6292824871010327632L;

    public XeroAuthenticationException(final String message, final Exception e) {
        super(message, e);
    }
}
