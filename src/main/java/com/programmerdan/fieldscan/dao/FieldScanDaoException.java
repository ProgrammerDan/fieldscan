package com.programmerdan.fieldscan.dao;

/**
 * Base RuntimeException type for Field scan exceptions.
 * As a runtime exception, these are "unexpected" exceptions, and
 * only occur when the backing classes are used improperly or
 * not configured correctly.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 23, 2014
 */
public class FieldScanDaoException extends RuntimeException {

	/**
	 * The generic exception message passed if none is provided.
	 */
	public static final String GENERIC_EXCEPTION = 
			"Exception within DAO component";

	/**
	 * Wrapper for {@link RuntimeException(String)}
	 *
	 * @param message the Exception message.
	 */
	public FieldScanDaoException(String message) {
		super(message);
	}

	/**
	 * Wrapper for {@link RuntimeException(String, Throwable)}
	 *
	 * @param message the Exception message.
	 * @param cause the underlying Throwable cause.
	 */
	public FieldScanDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Provides generic exception message for the underlying
	 * cause.
	 *
	 * @param cause the underlying Throwable cause.
	 */
	public FieldScanDaoException(Throwable cause) {
		super(FieldScanDaoException.GENERIC_EXCEPTION, cause);
	}

	/**
	 * Provides a generic exception message.
	 */
	public FieldScanDaoException() {
		super(FieldScanDaoException.GENERIC_EXCEPTION);
	}
}
