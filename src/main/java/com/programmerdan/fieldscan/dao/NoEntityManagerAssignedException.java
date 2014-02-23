package com.programmerdan.fieldscan.dao;

/**
 * Basic RuntimeException for the exceptional state that
 * no EntityManager has been assigned to the DAO.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 23, 2014
 *   First implementation.
 */
public class NoEntityManagerAssingedException extends FieldScanDaoException {

	/**
	 * Standard message for No Entity Manager assigned.
	 */
	public static final String SPECIFIC_EXCEPTION = 
			"While accessing EntityManager, discovered none assigned";
	
	/**
	 * Standard constructor, passes back the standard message.
	 */
	public NoEntityManagerAssignedException() {
		super(NoEntityManagerAssignedException.SPECIFIC_EXCEPTION);
	}

	/**
	 * Standard constructor with throwable.
	 *
	 * @param cause the {@link Throwable} cause.
	 */
	public NoEntityManagerAssignedException(Throwable cause) {
		super(NoEntityManagerAssignedException.SPECIFIC_EXCEPTION, cause);
	}

}

