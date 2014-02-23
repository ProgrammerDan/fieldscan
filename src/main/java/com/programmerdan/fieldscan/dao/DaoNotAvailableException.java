package com.programmerdan.fieldscan.dao;

/**
 * Simple exception, thrown when asking the factory for a DAO it
 * cannot provide.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 23, 2014
 *   Basic exception buildout.
 */
public class DaoNotAvailableException extends Exception {

	private Class<?> failType;

	/**
	 * Simple constructor, allows specification of requested class
	 * that DAO Factory was unable to provide.
	 *
	 * @param obj the Class type requested that is unavailable.
	 */
	public DaoNotAvailableException(Class<?> obj) {
		super("DAO for " + obj + " not available");
		failType = obj;
	}

	/**
	 * Gets the class type as stored within this exception.
	 */
	public Class<?> getFailType() {
		return failType;
	}
}
