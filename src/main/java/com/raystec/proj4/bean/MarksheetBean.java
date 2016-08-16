package com.raystec.proj4.bean;
/**
 * MarksheetBean JavaBean encapsulates MarksheetBean attributes
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */


public class MarksheetBean extends BaseBean 
{
	/**
	 * Roll no of student
	 */
	private String rollNo;
	
	/**
	 * Id of student
	 */
	private long studentId;
	
	/**
	 * name of student
	 * 
	 */
	private String name;
	
	
	/**
	 * physics marks of student
	 */
	private Integer physics;
	
	/**
	 * chmistry marks of student
	 */
	private Integer chemistry;
	
	/**
	 * maths marks of student
	 */
	private Integer maths;

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPhysics() {
		return physics;
	}

	public void setPhysics(Integer physics) {
		this.physics = physics;
	}

	public Integer getChemistry() {
		return chemistry;
	}

	public void setChemistry(Integer chemistry) {
		this.chemistry = chemistry;
	}

	public Integer getMaths() {
		return maths;
	}

	public void setMaths(Integer maths) {
		this.maths = maths;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	
	public String getValue() {
		// TODO Auto-generated method stub
		return rollNo;
	}
	
	
	

}
