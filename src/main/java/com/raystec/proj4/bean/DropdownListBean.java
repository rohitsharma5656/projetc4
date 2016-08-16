package com.raystec.proj4.bean;

/**
 * DropdownList interface is implemented by Beans those are used to create drop
 * down list on HTML pages
 * 
 *
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */


public interface DropdownListBean {

	/**
	 * Returns key of list element
	 * 
	 * @return key
	 */

	public String getKey();

	/**
	 * Returns display text of list element
	 * 
	 * @return value
	 */

	public String getValue();

}
