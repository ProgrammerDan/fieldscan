package com.programmerdan.fieldscan;

/**
 * Base class for all project specific exceptions.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 17, 2014
 *   Basic exception subclass.
 */
public class FieldScanException extends Exception {

	public static final String DEFAULT_EXCEPTION = 
			"An error occured during scanning. View stack for details.";

	/**
	 * Basic constructor, uses {@link #DEFAULT_EXCEPTION} as message.
	 */
	public FieldScanException() {
		super(FieldScanException.DEFAULT_EXCEPTION);
	}

	/**
	 * Allows the passing of upstream throwable, using the 
	 * {@link #DEFAULT_MESSAGE}.
	 *
	 * @param t {@link Throwable} upstream cause
	 */
	public FieldScanException(Throwable t) {
		super(FieldScanException.DEFAULT_EXCEPTION, t);
	}

	/**
	 * Allows the passing of upstream throwable and a custom message.
	 *
	 * @param message Description of exception
	 * @Param t {@link Throwable} upstream cause
	 */
	public FieldScanException(String message, Throwable t) {
		super(message, t);
	}

	/**
	 * Allows the passing of a custom message.
	 *
	 * @param message Description of exception
	 */
	public FieldScanException(String message) {
		super(message);
	}
}
