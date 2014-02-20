package com.programmerdan.fieldscan;

/**
 * If the path given at start up is invalid or inaccessible, this exception
 * is the preferred way to handle it. 
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Initial version of this simple exception.
 */
public RootPathInvalid extends FieldScanException {
	private String invalidPath;

	/**
	 * Constructor allowing the passing of the "cause" exception.
	 * 
	 * @param invalidPath the path given that caused a failure.
	 * @param cause the underlying exception that led to this exception
	 */
	public RootPathInvalid(String invalidPath, Throwable cause) {
		super("Invalid path given to start FieldScan: " + invalidPath, e);
		this.invalidPath = invalidPath;
	}

	/**
	 * Constructor accepting just the invalid path, typical in the case
	 * of a null path.
	 *
	 * @param invalidPath the path given that caused a failure.
	 */
	public RootPathInvalid(String invalidPath) {
		super("Invalid path given to start FieldScan: " + invalidPath);
		this.invalidPath = invalidPath;
	}

	/**
	 * Gets the invalid path as stored in this exception.
	 */
	public String getInvalidPath() {
		return invalidPath;
	}
}
