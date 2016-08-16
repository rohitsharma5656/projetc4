package com.raystec.proj4.bean;

/**
 * Faculty JavaBean encapsulates Faculty attributes
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */


import java.util.Date;

public class FacultyBean extends BaseBean  {

	/**
	 * Id of User
	 */
	private long userId;
	/**
	 * Id of College;
	 */

	private long collegeId;

	/***
	 * Name of Faculty;
	 */
	private String facultyName;
	/*
	 * MobileNo of user
	 */
	private Date date;
	/*
	 * Login of user
	 */
	private String login;

	/*
	 * Gender of user
	 */
	private String gender;
	/**
	 * Name of college;
	 */
	private String name;
	
	//assecor 

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public long getUserId() {

		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	
	public String getKey() {

		return id + "";
	}


	public String getValue() {
		return facultyName;
	}
}
