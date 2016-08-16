package com.raystec.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.CourseBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DatabaseException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.util.JDBCDataSource;

/**
 * JDBC Implementation of CourseModel
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */
public class CourseModel {

	private static Logger log = Logger.getLogger(CourseModel.class);

//	private static Logger log = Logger.getLogger(CourseModel.class);

	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT MAX(ID) FROM demo_ors.st_course");
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

	public long add(CourseBean bean) throws ApplicationException,
			DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		System.out.println(bean.getId());
		CourseBean subject = findBySubject(bean.getSubject());

		if (subject != null && bean.getName().equals(subject.getName())) {
			System.out.println(subject.getSubject());
			throw new DuplicateRecordException("subject already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			// Get auto-generated next primary key
			// System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO demo_ors.st_course VALUES(?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getSubject());
			// pstmt.setString(4, bean.getSubjectCode());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException(
					"Exception : Exception in add course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	public void delete(CourseBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("DELETE FROM demo_ors.st_course WHERE ID=?");
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
					"Exception : Exception in delete Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	public CourseBean findByName(String name) throws ApplicationException {
		log.debug("Model findBy EmailId Started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM demo_ors.st_course WHERE COURSE_NAME=?");
		CourseBean bean = null;
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("test Model");
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				// bean.setCreatedDatetime(rs.getTimestamp(6));
				// bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException(
					"Exception : Exception in getting User by course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy course End");
		return bean;
	}

	/**
	 * Find Role by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */
	public CourseBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM demo_ors.st_course WHERE ID=?");
		CourseBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setSubject(rs.getString(3));
				// bean.setSubjectCode(rs.getString(4));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
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

	public CourseBean findById(long pk) throws ApplicationException {
		log.debug("Model findByPk Started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_COURSE WHERE id=?");
		CourseBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setSubject(rs.getString(3));
				/*
				 * bean.setSubjectCode(rs.getString(4).substring(
				 * rs.getString(4).lastIndexOf('-') + 1));
				 */
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Excepton ...", e);
			throw new ApplicationException(
					"Exception in getting course By subject code");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBySubjectCode End");
		return bean;

	}

	public CourseBean findBySubjectCode(String subjectCode)
			throws ApplicationException {
		log.debug("Model findBySubjcetCode Started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_COURSE WHERE SUBJECT_CODE=?");
		CourseBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, subjectCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setSubject(rs.getString(3));
				/* bean.setSubjectCode(rs.getString(4)); */
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Excepton ...", e);
			throw new ApplicationException(
					"Exception in getting course By subject code");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBySubjectCode End");
		return bean;

	}

	public CourseBean findBySubject(String subject) throws ApplicationException {
		log.debug("Model findBySubjcetCode Started");

		System.out.println("find by subject me he");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_COURSE WHERE SUBJECT=?");
		CourseBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, subject);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setSubject(rs.getString(3));
				// bean.setSubjectCode(rs.getString(4));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Excepton ...", e);
			throw new ApplicationException(
					"Exception in getting course By subject code");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBySubjectCode End");
		return bean;

	}

	public CourseBean findBySubjectCodeSubject(String subject,
			String subjectCode) throws ApplicationException {
		log.debug("Model findBySubjcetCode Started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_COURSE WHERE SUBJECT=? AND SUBJECT_CODE=?");
		CourseBean bean = null;
		Connection conn = null; // "SELECT * FROM st_timetable WHERE TIME=? AND DAY=?"
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, subject);
			pstmt.setString(2, subjectCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setSubject(rs.getString(3));
				// bean.setSubjectCode(rs.getString(4));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Excepton ...", e);
			throw new ApplicationException(
					"Exception in getting course By subject code");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBySubjectCode End");
		return bean;

	}

	public void update(CourseBean bean) throws ApplicationException,
			DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		System.out.println(bean.getId() + " Update me he");
		CourseBean subject1 = findBySubject(bean.getSubject());

		if (subject1 != null && subject1.getName().equals(bean.getName())) {
			throw new DuplicateRecordException("subject alreday exits");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			String subject = bean.getName();
			PreparedStatement pstmt = conn
					.prepareStatement("UPDATE demo_ors.st_course SET COURSE_NAME=?,SUBJECT=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getSubject());
			// pstmt.setString(3, bean.getSubjectCode());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transactionb
			System.out.println("Course MOdel Update After Commit");
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException(
						"Exception : Delete rollback exception "
								+ ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Course ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	public List search(CourseBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(CourseBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		log.debug("Model search Started");
		System.out.println(bean + "test");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM demo_ors.st_course WHERE 1=1");
		if (bean != null) {
			System.out.println(bean);
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND COURSE_NAME LIKE '" + bean.getName() + "%'");
			}

			/*
			 * if (bean.getSubjectCode() != null &&
			 * bean.getSubjectCode().length() > 0) {
			 * sql.append(" AND SUBJECT_CODE like '" + bean.getSubjectCode() +
			 * "%'");
			 * 
			 * }
			 */
		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		System.out.println(sql);
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setSubject(rs.getString(3));
				// bean.setSubjectCode(rs.getString(4));
				bean.setDescription(rs.getString(4));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in search course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model search End");
		// System.out.println("search end");
		return list;
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_course");
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
				CourseBean bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setSubject(rs.getString(3));
				// bean.setSubjectCode(rs.getString(4));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in getting list of course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;

	}

	public List findBycourse(String name) throws ApplicationException {
		log.debug("Model findBy EmailId Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_course");
		CourseBean bean = null;
		Connection conn = null;
		List list = new ArrayList();
		list = null;
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				/*
				 * bean = new CourseBean(); bean.setId(rs.getLong(1));
				 * bean.setName(rs.getString(2));
				 * bean.setDescription(rs.getString(3));
				 * bean.setCreatedBy(rs.getString(4));
				 * bean.setModifiedBy(rs.getString(5));
				 * bean.setCreatedDatetime(rs.getTimestamp(6));
				 * bean.setModifiedDatetime(rs.getTimestamp(7)); list.add(bean);
				 */
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setSubject(rs.getString(3));
				// bean.setSubjectCode(rs.getString(4));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in getting User by course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy course End");
		return list;
	}
}
