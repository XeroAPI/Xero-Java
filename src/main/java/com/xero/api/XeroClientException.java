package com.xero.api;

/**
 * Exception thrown by Xero Java API if there is an error which is not related
 * to remote API calls (like for example no config).
 *
 * @author GideonLeGrange gideon@legrange.me
 */
public class XeroClientException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Create new exception.
     *
     * @param message Error message
     */
    public XeroClientException(String message) {
        super(message);
    }

    /**
     * Create new exception.
     *
     * @param message Error message
     * @param cause Original exception causing this error.
     */
    public XeroClientException(String message, Throwable cause) {
        super(message, cause);
    }
   
}
