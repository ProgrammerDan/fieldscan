package com.programmerdan.fieldscan;

/**
 * Simple class to encapsulate setup exceptions.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Initial version.
 */
public class FieldScanSetupException extends Exception {

	public static final String SETUP_EXCEPTION = "Failed to setup FieldScan";

	public FieldScanSetupException() {
		super(FieldScanSetupException.SETUP_EXCEPTION);
	}

	public FieldScanSetupException(Throwable cause) {
		super(FieldScanSetupException.SETUP_EXCEPTION, cause);
	}
}
