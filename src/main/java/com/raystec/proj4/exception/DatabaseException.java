package com.raystec.proj4.exception;

/**
 * DatabaseException is propogated by DAO classes when an unhandled Database
 * exception occurred
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c)Rays Technologies
 * 
 */
public class DatabaseException extends Exception {

	/**
	 * @param msg
	 *            : Error message
	 */

	public DatabaseException(String msg) {
		super(msg);
	}
}
