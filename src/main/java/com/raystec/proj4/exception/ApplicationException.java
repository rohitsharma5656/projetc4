package com.raystec.proj4.exception;

/**
 * ApplicationException is propogated from Service classes when an business
 * logic exception occurered.
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c)Rays Technologies
 * 
 */
public class ApplicationException extends Exception {

	/**
	 * @param msg
	 *            : Error message
	 */

	public ApplicationException(String msg) {
		super(msg);
	}
}
