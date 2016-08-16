package com.raystec.proj4.exception;

/**
 * DuplicateRecordException thrown when a duplicate record occurred
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c)Rays Technologies
 * 
 */
public class DuplicateRecordException extends Exception {

	/**
	 * @param msg
	 *            error message
	 */
	public DuplicateRecordException(String msg) {
		super(msg);
	}

}
