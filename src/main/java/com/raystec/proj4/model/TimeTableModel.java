package com.raystec.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.CollegeBean;
import com.raystec.proj4.bean.CourseBean;
import com.raystec.proj4.bean.FacultyBean;
import com.raystec.proj4.bean.MarksheetBean;
import com.raystec.proj4.bean.TimeTableBean;
import com.raystec.proj4.bean.UserBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DatabaseException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.util.JDBCDataSource;

/**
 * JDBC Implementation of CollegeModel
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
public class TimeTableModel {
	private static Logger log = Logger.getLogger(TimeTableModel.class);

	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT MAX(ID) FROM ST_TIMETABLE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * Add a Timetable
	 * 
	 * @param bean
	 * @throws DatabaseException
	 * 
	 */

	public long add(TimeTableBean bean) throws DuplicateRecordException,
			ApplicationException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		// get Faculty Name
		UserModel umodel = new UserModel();
		UserBean userBean = umodel.findByPK(bean.getUserId());
		bean.setFirstName(userBean.getFirstName() + " "
				+ userBean.getLastName());

		// get Course Name
		CourseModel model = new CourseModel();
		CourseBean courseBean = model.findByPK(bean.getCourseId());
		bean.setCourseName(courseBean.getName());
		bean.setSubject(courseBean.getSubject());
		// TimeTableBean duplicate = findByName(courseBean.getSubject());
		TimeTableBean duplicate = findBySubFacDat(bean.getUserId());

		TimeTableBean beanExist = findByTime(bean.getTime(), bean.getDay());
		if (duplicate != null
				&& bean.getFirstName().equals(duplicate.getFirstName())
				&& beanExist != null) {
			throw new DuplicateRecordException("Time Name already exists");
		}
		
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);

			pstmt.setLong(2, bean.getUserId());
			pstmt.setString(3, bean.getFirstName());
			pstmt.setLong(4, bean.getCourseId());
			pstmt.setString(5, bean.getCourseName());
			pstmt.setString(6, bean.getSubject());
			pstmt.setString(7, bean.getDay());
			pstmt.setString(8, bean.getTime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			log.error("DataBase Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {

				ex.printStackTrace();
				throw new ApplicationException(
						"Exception : add rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add end");
		return pk;
	}

	/**
	 * Delete a Student
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(TimeTableBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception : Delete rollback exception "
								+ ex.getMessage());
			}
			throw new ApplicationException(
					"Exception : Exception in delete Timetable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	/**
	 * Update a Timetable
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */

	public void update(TimeTableBean bean) throws ApplicationException,
			DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		// get Faculty Name
		UserModel umodel = new UserModel();
		UserBean userBean = umodel.findByPK(bean.getUserId());
		bean.setFirstName(userBean.getFirstName() + " "
				+ userBean.getLastName());
		// get Course Name
		CourseModel model = new CourseModel();
		CourseBean courseBean = model.findByPK(bean.getCourseId());
		bean.setCourseName(courseBean.getName());
		bean.setSubject(courseBean.getSubject());
		TimeTableBean duplicate = findByName(courseBean.getSubject());

		TimeTableBean beanExist = findByTime(bean.getTime(), bean.getDay());
		if (duplicate != null
				&& bean.getFirstName().equals(duplicate.getFirstName())
				&& beanExist != null) {
			throw new DuplicateRecordException("Subject Name already exists");
		}
		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("UPDATE ST_TIMETABLE SET FACULTY_ID=?, FACULTY_NAME=?, COURSE_ID=?, COURSE_NAME=?, SUBJECT=?, DAY=?, TIME=?  WHERE ID=?");

			pstmt.setLong(1, bean.getUserId());
			pstmt.setString(2, bean.getFirstName());
			pstmt.setLong(3, bean.getCourseId());
			pstmt.setString(4, bean.getCourseName());
			pstmt.setString(5, bean.getSubject());
			pstmt.setString(6, bean.getDay());
			pstmt.setString(7, bean.getTime());

			pstmt.setLong(8, bean.getId());

			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				e.printStackTrace();
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception : Delete rollback exception "
								+ ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Timetable ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	public TimeTableBean findByName(String name) throws ApplicationException {
		log.debug("Model findByName Started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE SUBJECT=?");
		TimeTableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));

				bean.setUserId(rs.getLong(2));
				bean.setFirstName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSubject(rs.getString(6));
				bean.setDay(rs.getString(7));
				bean.setTime(rs.getString(8));

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in getting faculty by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByName End");
		return bean;
	}

	/**
	 * Search Timetable
	 * 
	 * @param bean
	 *            : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(TimeTableBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Search Timetable with pagination
	 * 
	 * @return list : List of Timetable
	 * @param bean
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 * @throws DatabaseException
	 */

	public List search(TimeTableBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FACULTY_NAME like '" + bean.getFirstName()
						+ "%'");
			}
			if (bean.getSubject() != null && bean.getSubject().length() > 0) {
				sql.append(" AND SUBJECT like '" + bean.getSubject() + "%'");
			}

			// if page size is greater than zero then apply pagination
			if (pageSize > 0) {
				// Calculate start record index
				pageNo = (pageNo - 1) * pageSize;

				sql.append(" Limit " + pageNo + ", " + pageSize);
				// sql.append(" limit " + pageNo + "," + pageSize);
			}

		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));

				bean.setUserId(rs.getLong(2));
				bean.setFirstName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSubject(rs.getString(6));
				bean.setDay(rs.getString(7));
				bean.setTime(rs.getString(8));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in search Timetable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	/**
	 * Get List of Timetable
	 * 
	 * @return list : List of Student
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public TimeTableBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE ID=?");
		TimeTableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setUserId(rs.getLong(2));
				bean.setFirstName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSubject(rs.getString(6));
				bean.setDay(rs.getString(7));
				bean.setTime(rs.getString(8));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

	/**
	 * Get List of Timetable with pagination
	 * 
	 * @return list : List of Timetable
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_TIMETABLE");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				TimeTableBean bean = new TimeTableBean();
				bean.setId(rs.getLong(1));

				bean.setUserId(rs.getLong(4));
				bean.setFirstName(rs.getString(5));
				bean.setCourseId(rs.getLong(6));
				bean.setCourseName(rs.getString(7));
				bean.setSubject(rs.getString(8));
				bean.setTime(rs.getString(9));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in getting list of Timetable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}

	public TimeTableBean findBySubFacDat(Long faculty)
			throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_TIMETABLE WHERE FACULTY_ID=? ");
		TimeTableBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, faculty);
			// pstmt.setString(2, time);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setUserId(rs.getLong(2));
				// bean.setUserId(rs.getLong(2));
				bean.setFirstName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSubject(rs.getString(6));
				bean.setDay(rs.getString(7));
				bean.setTime(rs.getString(8));

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

	public TimeTableBean findByTime(String time, String day)
			throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM st_timetable WHERE TIME=? AND DAY=?");
		TimeTableBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, time);
			pstmt.setString(2, day);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));

				bean.setUserId(rs.getLong(2));
				bean.setFirstName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSubject(rs.getString(6));
				bean.setDay(rs.getString(7));
				bean.setTime(rs.getString(8));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

}
