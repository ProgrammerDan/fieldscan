package com.programmerdan.fieldscan.dao;

/**
 * Basic RuntimeException for the exceptional state that
 * no transaction is available.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 23, 2014
 *   First implementation.
 */
public class NoTransactionAvailableException extends FieldScanDaoException {

	/**
	 * Standard message for No Transaction Available.
	 */
	public static final String SPECIFIC_EXCEPTION = 
			"Attempted to get a transaction, but none available.";
	
	/**
	 * Standard constructor, passes back the standard message.
	 */
	public NoTransactionAvailableException() {
		super(NoTransactionAvailableException.SPECIFIC_EXCEPTION);
	}

	/**
	 * Standard constructor with throwable.
	 *
	 * @param cause the {@link Throwable} cause.
	 */
	public NoTransactionAvailableException(Throwable cause) {
		super(NoTransactionAvailableException.SPECIFIC_EXCEPTION, cause);
	}

}
