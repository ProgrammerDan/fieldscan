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
			"An error occured during scanning. View stack for details."

	public FieldScanException() {
		super(FieldScanException.DEFAULT_EXCEPTION);
	}

	public FieldScanException(Throwable t) {
		super(FieldScanException.DEFAULT_EXCEPTION, t);
	}

	public FieldScanException(String message, Throwable t) {
		super(message, t);
	}
}
