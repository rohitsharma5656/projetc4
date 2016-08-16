package com.raystec.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.CollegeBean;
import com.raystec.proj4.bean.FacultyBean;
import com.raystec.proj4.bean.UserBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DatabaseException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.util.JDBCDataSource;

/**
 * JDBC Implementation of FacultyModel
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
public class FacultyModel {

	private static Logger log = Logger.getLogger(FacultyModel.class);

	/**
	 * Find next PK of Faculty
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("FacultyModel nextPK started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			java.sql.PreparedStatement pstm = conn
					.prepareStatement("SELECT MAX(ID) FROM ST_FACULTY");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("DataBase Exceptio..." + e);
			throw new DatabaseException("Exception in nextpk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Facultymodel nextPK Ended");
		return pk + 1;
	}

	/**
k	 * Add a Faculty
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 * 
	 */

	public long add(FacultyBean bean) throws ApplicationException,
			DuplicateRecordException {
		log.debug("Facultymodel add method started");
		Connection conn = null;

		// get User Name
		UserModel model = new UserModel();
		UserBean Bean = model.findByPK(bean.getUserId());
		bean.setFacultyName(Bean.getFirstName() + " " + Bean.getLastName());

		// get duplicate records
		FacultyBean existbean = findById(bean.getUserId());
		if (existbean != null) {
			throw new DuplicateRecordException("faculty Name Already Exists");

		}

		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			java.sql.PreparedStatement pstm = conn
					.prepareStatement("INSERT INTO st_faculty VALUES(?,?,?,?,?,?,?,?)");
			pstm.setLong(1, pk);
			pstm.setString(2, bean.getFacultyName());
			pstm.setLong(3, bean.getUserId());
			pstm.setLong(4, bean.getCollegeId());
			pstm.setString(5, bean.getCreatedBy());
			pstm.setString(6, bean.getModifiedBy());
			pstm.setTimestamp(7, bean.getCreatedDatetime());
			pstm.setTimestamp(8, bean.getModifiedDatetime());
			pstm.executeUpdate();
			conn.commit();
			pstm.close();
		} catch (Exception e) {
			log.error("DataBase Exception..." + e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
				throw new ApplicationException(
						"Exception :add rollback exception " + e1.getMessage());
			}
			throw new ApplicationException(
					"Exception : Exception in add Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("FacultyModel add method Ended");
		return pk;
	}

	/**
	 * Delete a Faculty
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public void delete(FacultyBean bean) throws ApplicationException {
		log.debug("FacultyModel delete method started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);// Begin transaction
			java.sql.PreparedStatement pstm = conn
					.prepareStatement(" DELETE FAC,US,TIME FROM ST_FACULTY AS FAC INNER JOIN ST_USER AS US ON FAC.USER_ID = US.Id  LEFT JOIN ST_TIMETABLE AS TIME ON FAC.ID = TIME.FACULTY_ID WHERE FAC.ID=?");
			pstm.setLong(1, bean.getId());
			pstm.executeUpdate();
			pstm.close();
			conn.commit();
		} catch (Exception e) {
			log.error("DataBase Exception..." + e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new ApplicationException(
						"Exception : delete rollback exception.."
								+ e1.getMessage());

			}
			throw new ApplicationException(
					"Exception : Exception in delete faculty ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("FacultyModel delete method Ended");
	}

	/**
	 * Update a Faculty
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 */

	public void update(FacultyBean bean) throws ApplicationException,
			DuplicateRecordException, DatabaseException {
		log.debug("FacultyModel update method started");
		Connection conn = null;

		// set faculty name time update
		UserModel u1 = new UserModel();
		UserBean ubean = u1.findByPK(bean.getUserId());
		bean.setFacultyName(ubean.getFirstName() + " " + ubean.getLastName());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			java.sql.PreparedStatement pstm = conn
					.prepareStatement("UPDATE ST_FACULTY SET USER_ID=?,FACULTY_NAME=?, COLLEGE_ID=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstm.setLong(1, bean.getUserId());

			pstm.setString(2, bean.getFacultyName());
			pstm.setLong(3, bean.getCollegeId());

			pstm.setString(4, bean.getCreatedBy());
			pstm.setString(5, bean.getModifiedBy());
			pstm.setTimestamp(6, bean.getCreatedDatetime());
			pstm.setTimestamp(7, bean.getModifiedDatetime());
			pstm.setLong(8, bean.getId());

			pstm.executeUpdate();
			conn.commit();
			pstm.close();

		} catch (Exception e) {
			log.error("DataBase Exception...", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
				throw new ApplicationException(
						"Exception : Delete rollback exception..." + e1);
			}
			throw new ApplicationException("Exception in updating user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("FacultyModel update method Ended");
	}

	/****
	 * find User by pk
	 * 
	 * @param pk
	 *            :get parameter
	 * @return bean
	 * @throws ApplicationException
	 */

	public FacultyBean findByPk(long pk) throws ApplicationException {
		log.debug("Model findByPk Started");
		System.out.println("findby pk model--" + pk);
		StringBuffer sql = new StringBuffer(
				"SELECT FAC.ID,FAC.FACULTY_NAME,CL.ID,CL.NAME,US.ID FROM ST_FACULTY AS FAC INNER JOIN ST_USER AS US ON FAC.USER_ID = US.Id INNER JOIN st_college AS CL ON FAC.COLLEGE_ID = CL.ID WHERE FAC.ID= ?");
		FacultyBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, pk);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {

				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFacultyName(rs.getString(2));
				bean.setCollegeId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setUserId(rs.getLong(5));

			}
			rs.close();

		} catch (Exception e) {

			e.printStackTrace();
			log.error("Database Excepton ...", e);
			throw new ApplicationException("Exception in getting Faculty By pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPk End");
		System.out.println("********************" + bean.getUserId());
		return bean;
	}

	/****
	 * find User by pk
	 * 
	 * @param pk
	 *            :get parameter
	 * @return bean
	 * @throws ApplicationException
	 */

	public FacultyBean findById(long facultyId) throws ApplicationException {
		log.debug("Model findByPk Started");
		StringBuffer sql = new StringBuffer(
				"SELECT FAC.ID,FAC.FACULTY_NAME,CL.ID,CL.NAME,US.ID FROM ST_FACULTY AS FAC INNER JOIN ST_USER AS US ON FAC.USER_ID = US.Id INNER JOIN st_college AS CL ON FAC.COLLEGE_ID = CL.ID WHERE FAC.USER_ID= ?");
		FacultyBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, facultyId);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {

				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFacultyName(rs.getString(2));
				bean.setCollegeId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setUserId(rs.getLong(5));

			}
			rs.close();

		} catch (Exception e) {

			e.printStackTrace();
			log.error("Database Excepton ...", e);
			throw new ApplicationException(
					"Exception in getting Faculty By Name");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByName End");
		return bean;
	}

	/***
	 * search Faculty
	 * 
	 * @param bean
	 * @return
	 * @throws ApplicationException
	 */

	public List search(FacultyBean bean) throws ApplicationException {
		return search(bean, 0, 0);

	}

	/**
	 * @return list :List of Faculty
	 * @param bean
	 *            : search parameter
	 * @param pageNo
	 *            : current Page No.
	 * @param pageSize
	 *            : size of Page
	 * @return
	 * @throws ApplicationException
	 */

	public List search(FacultyBean Bean, int pageNo, int pageSize)
			throws ApplicationException {
		log.debug("Model Faculty search Started");

		StringBuffer sql = new StringBuffer(
				"SELECT FAC.ID,FAC.FACULTY_NAME,US.LOGIN,US.DOB,US.GENDER,CL.NAME FROM ST_FACULTY AS FAC INNER JOIN ST_USER AS US ON FAC.USER_ID = US.Id INNER JOIN st_college AS CL ON FAC.COLLEGE_ID = CL.ID WHERE 1=1");

		if (Bean != null) {
			if (Bean.getId() > 0) {

				sql.append(" AND FAC.ID =" + Bean.getId());
			}
			if (Bean.getFacultyName() != null
					&& Bean.getFacultyName().length() > 0) {
				sql.append(" AND FAC.Faculty_NAME like '"
						+ Bean.getFacultyName() + "%'");

			}

		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);

		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FacultyBean fBean = new FacultyBean();

				fBean.setId(rs.getLong(1));
				fBean.setFacultyName(rs.getString(2));
				fBean.setLogin(rs.getString(3));
				fBean.setDate(rs.getDate(4));
				fBean.setGender(rs.getString(5));
				fBean.setName(rs.getString(6));

				list.add(fBean);

			}
			rs.close();

		} catch (Exception e) {
			log.debug("Database Exception ..", e);
			e.printStackTrace();
			throw new ApplicationException(
					" Exception : Exception in search Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Faculty search End");

		return list;

	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List ofFaculty with pagination
	 * 
	 * @param pageNo
	 *            :current page No
	 * @param pageSize
	 *            : current Page No.
	 * @return list: List ofFaculty
	 * @throws ApplicationException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("ModelFaculty list started ");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY ");

		// if page is greater than zero then apply pagination
		if (pageSize > 0) {
			// calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);

		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FacultyBean bean = new FacultyBean();
				bean.setId(rs.getLong(1));

				bean.setFacultyName(rs.getString(2));
				bean.setUserId(rs.getLong(3));
				bean.setCollegeId(rs.getLong(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

				list.add(bean);

			}
			rs.close();

		} catch (Exception e) {

			log.debug("Database Exception  ", e);
			e.printStackTrace();
			throw new ApplicationException(
					" Exception : Exception in getting list of Faculty");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("ModelFaculty list end ");

		return list;
	}
}
