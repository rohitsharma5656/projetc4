package com.raystec.proj4.bean;

/**
 * Course JavaBean encapsulates Course attributes
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
public class CourseBean extends BaseBean implements DropdownListBean {

	private String name;

	/**
	 * Role Description
	 */
	private String subject;
	private String description;

	// private String subjectCode;

	/*
	 * public String getSubjectCode() { return subjectCode; }
	 * 
	 * public void setSubjectCode(String subjectCode) { this.subjectCode =
	 * subjectCode; }
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getKey() {
		return id + "";
	}

	public String getValue() {
		return name + "-" + subject;
	}
}
