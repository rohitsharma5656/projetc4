package com.raystec.proj4.exception;

/**
 * RecordNotFoundException thrown when a record not found occurred
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c)Rays Technologies
 */
public class RecordNotFoundException extends Exception {

	/**
	 * @param msg
	 *            error message
	 */
	public RecordNotFoundException(String msg) {
		super(msg);

	}
}
