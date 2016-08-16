package com.raystec.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.CollegeBean;
import com.raystec.proj4.bean.StudentBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DatabaseException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.util.JDBCDataSource;
/**
 * JDBC Implementation of StudentModel
 * 
 @author:Builder Pattern
* @version 1.0
* @Copyright (c) Rays Technologies
 */
public class StudentModel {
	public static Logger log = Logger.getLogger(StudentModel.class);

	/**
	 * find next pk started
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("next pk started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn
					.prepareStatement("SELECT MAX(ID) FROM STUDENT");
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Data base Exception...", e);
			throw new DatabaseException("Exception:Exception in getting pk ");

		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("model next pk end");
		return pk + 1;
	}

	/**
	 * add Student
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */
	public long add(StudentBean bean) throws ApplicationException,
			DuplicateRecordException {
		log.debug("model add started");
		Connection conn = null;

		// get Collage name
		CollegeModel cModel = new CollegeModel();
		CollegeBean collegeBean = cModel.findByPk(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());

		StudentBean duplicateName = findByEmailId(bean.getEmail());
		int pk = 0;
		if (duplicateName != null) 
		{
			throw new DuplicateRecordException("email all ready exixts ");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			PreparedStatement psmt = conn
					.prepareStatement("INSERT INTO STUDENT VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			// Get auto-generated next primary key
			System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false);

			psmt.setInt(1, pk);
			psmt.setLong(2, bean.getCollegeId());
			psmt.setString(3, bean.getCollegeName());
			psmt.setString(4, bean.getFirstName());
			psmt.setString(5, bean.getLastName());
			psmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
			psmt.setString(7, bean.getMobileNo());
			psmt.setString(8, bean.getEmail());
			psmt.setString(9, bean.getCreatedBy());
			psmt.setString(10, bean.getModifiedBy());
			psmt.setTimestamp(11, bean.getCreatedDatetime());
			psmt.setTimestamp(12, bean.getModifiedDatetime());
			psmt.executeUpdate();
			conn.commit();
			psmt.close();
		} catch (Exception e) {
			log.error("DataBase Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception:add rollback exception" + ex.getMessage());
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("model add end");
		}
		return pk;

	}

	/**
	 * delete Student
	 * 
	 * @param bean
	 * @throws Exception
	 */
	public void delete(StudentBean bean) throws ApplicationException {
		log.debug("model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);// begin transation
			PreparedStatement psmt = conn
					.prepareStatement("DELETE FROM STUDENT WHERE ID=?");
			psmt.setLong(1, bean.getId());
			psmt.executeUpdate();
			conn.commit();
			psmt.close();
		} catch (Exception e) {
			log.error("Database Exception....", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"EXception:Delete rollback exception "
								+ ex.getMessage());
			}
			throw new ApplicationException(
					"Exceoion:Exception in delete Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model delete started");
	}

	/**
	 * update Student
	 */
	public void update(StudentBean bean) throws Exception
	{
		log.debug("model updated started");
		Connection conn = null;
		StudentBean beanExit = findByEmailId(bean.getEmail());
		// iif update role already exist
		if (beanExit != null && beanExit.getId() != bean.getId()) {
			throw new DuplicateRecordException("EMal id is already exits");
		}
		CollegeModel cmodel = new CollegeModel();
		CollegeBean collegeBean = cmodel.findByPk(bean.getCollegeId());
		bean.setCollegeName(collegeBean.getName());
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);// begin transation
			PreparedStatement psmt = conn.prepareStatement("UPDATE STUDENT SET COLLEGE_ID=?,COLLEGE_NAME=?,FIRST_NAME=?,LAST_NAME=?,DATE_OF_BIRTH=?,MOBILE_NO=?,EMAIL=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			psmt.setLong(1, bean.getCollegeId());
			psmt.setString(2, bean.getCollegeName());
			psmt.setString(3, bean.getFirstName());
			psmt.setString(4, bean.getLastName());
			psmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			psmt.setString(6, bean.getMobileNo());
		//	System.out.println("Test Email");
			psmt.setString(7, bean.getEmail());
			//System.out.println("Test getEmail");
			psmt.setString(8, bean.getCreatedBy());
			psmt.setString(9, bean.getModifiedBy());
			psmt.setTimestamp(10, bean.getCreatedDatetime());
			psmt.setTimestamp(11, bean.getModifiedDatetime());
			psmt.setLong(12, bean.getId());
			psmt.executeUpdate();
			conn.commit();
			psmt.close();
		} catch (Exception e) {
			log.error("Database exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception:Exception delet rollback exception "
								+ ex.getMessage());
			}
			throw new ApplicationException("Exception in updating student");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * find user by student
	 * 
	 * @param Email
	 *            :get Parameter
	 * @return bean
	 * @throws
	 */
	public StudentBean findByEmailId(String Email) throws ApplicationException {
		log.debug("Model find by email started ");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM STUDENT WHERE EMAIL=?");
		StudentBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, Email);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) 
			{
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			log.error("database Exception..", e);
			throw new ApplicationException(
					"Exception:Exception in getting user by mail");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("mpdel find by email end");
		return bean;
	}

	/**
	 * find student by pk
	 * 
	 * @throws ApplicationException
	 */
	public StudentBean findByPK(long pk) throws ApplicationException {
		log.debug("model find by pk started");
		StringBuffer sql = new StringBuffer("SELECT * FROM STUDENT WHERE ID=?");
		StudentBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql.toString());
			psmt.setLong(1, pk);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exceptin..", e);
			throw new ApplicationException(
					"Exception:Exception in getting by user pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model find by pk end");
		return bean;
	}

	/**
	 * Get List of Student
	 * 
	 * @return list : List of Student
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Search Student
	 * 
	 * @param bean
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(StudentBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * List search
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("deprecation")
	public List search(StudentBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		log.debug("model search started");
		StringBuffer sql = new StringBuffer("SELECT * FROM STUDENT WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id= " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName()
						+ "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME LIKE '" + bean.getLastName() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + bean.getDob());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE__NO like '" + bean.getMobileNo() + "%'");
			}
			if (bean.getEmail() != null && bean.getEmail().length() > 0) {
				sql.append(" AND EMAIL like '" + bean.getEmail() + "%'");
			}
			if (bean.getCollegeName() != null
					&& bean.getCollegeName().length() > 0) {
				sql.append(" AND COLLAGE_NAME= " + bean.getCollegeName());
			}
		}
		// if page size is greater then zero then apply pagination
		if (pageSize > 0) {
			// calculate index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
		}
		ArrayList alist = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql.toString());
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				alist.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Data base execption", e);
			throw new ApplicationException(
					"Exception:exception in search student ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model search end");
		return alist;

	}

	/**
	 * Get List of Student with pagination
	 * 
	 * @return list : List of Student
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("model list started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM STUDENT");
		// IF PAGE SIZE IS GREATER THEN ZERO THEN APPLY PAGINATION
		if (pageSize > 0) {
			// caluculate
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql.toString());
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				StudentBean bean = new StudentBean();

				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Databse Exception..", e);
			throw new ApplicationException(
					"Exception:Exception in geeting list of student");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model list end");
		return list;

	}
}
