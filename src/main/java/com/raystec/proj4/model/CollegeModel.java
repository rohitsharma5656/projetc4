package com.raystec.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.raystec.proj4.bean.CollegeBean;
import com.raystec.proj4.exception.ApplicationException;
import com.raystec.proj4.exception.DatabaseException;
import com.raystec.proj4.exception.DuplicateRecordException;
import com.raystec.proj4.util.JDBCDataSource;
/**
 * JDBC Implementation of CollegeModel
 * 
 @author Builder Pattern
* @version 1.0
* @Copyright (c) Rays Technologies
 */
public class CollegeModel {
	private Logger log = Logger.getLogger(CollegeModel.class);

	/**
	 * find next pk of collage
	 * 
	 * @database Exception
	 */

	public Integer nextPK() throws DatabaseException {
		log.debug("Model next pk started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn
					.prepareStatement("SELECT MAX(ID) FROM ST_COLLEGE");
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);

			}
			rs.close();
		} catch (Exception e) {
			log.error("DataBase Exception..", e);
			throw new DatabaseException("Exception:Exception in getting pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model next pk end");
		return pk + 1;
	}

	/**
	 * Add collage Bean
	 */
	public long add(CollegeBean bean) throws ApplicationException,
			DuplicateRecordException {
		log.debug("model add started");
		Connection conn = null;
		int pk = 0;
		CollegeBean duplicateCollageName = findByName(bean.getName());
		if (duplicateCollageName != null) {
			throw new DuplicateRecordException("Collage name aready exixts");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// get Auto genrated primary key
			conn.setAutoCommit(false);// begin transaction
			PreparedStatement psmt = conn
					.prepareStatement("INSERT INTO ST_COLLEGE VALUES(?,?,?,?,?,?,?,?,?,?)");
			psmt.setInt(1, pk);
			psmt.setString(2, bean.getName());
			psmt.setString(3, bean.getAddress());
			psmt.setString(4, bean.getState());
			psmt.setString(5, bean.getCity());
		
			psmt.setString(6, bean.getPhoneNo());
		
			psmt.setString(7, bean.getCreatedBy());
			psmt.setString(8, bean.getModifiedBy());
			psmt.setTimestamp(9, bean.getCreatedDatetime());
			psmt.setTimestamp(10, bean.getModifiedDatetime());
			psmt.executeUpdate();
			conn.commit();// end transaction
			psmt.close();
		} catch (Exception e) {
			log.error("Database exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Excption:add rollback excpetin"
						+ ex.getMessage());

			}
			throw new ApplicationException("Exception:Exception in add collage");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model add end");

		return pk;
	}

	public CollegeBean findByName(String name) throws ApplicationException {
		log.debug("model find by name started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM STR_COLLEGE WHERE NAME=?");
		CollegeBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, name);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {
			log.error("DataBase Exception", e);
			throw new ApplicationException(
					"Exception:Exception in getting collage by name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model find by name End");
		return bean;
	}

	public void update(CollegeBean bean) throws ApplicationException,
			DuplicateRecordException {
		log.debug("model update started");
		Connection conn = null;
		CollegeBean beanExist = findByName(bean.getName());
		// cheack if updated collage already exixts
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("collage is already exixts");

		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);// begin transaction
			PreparedStatement psmt = conn
					.prepareStatement("UPDATE ST_COLLEGE SET NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			psmt.setString(1, bean.getName());
			psmt.setString(2, bean.getAddress());
			psmt.setString(3, bean.getState());
			psmt.setString(4, bean.getCity());
			psmt.setString(5, bean.getPhoneNo());
			psmt.setString(6, bean.getCreatedBy());
			psmt.setString(7, bean.getModifiedBy());
			psmt.setTimestamp(8, bean.getCreatedDatetime());
			psmt.setTimestamp(9, bean.getModifiedDatetime());
			psmt.setLong(10, bean.getId());
			psmt.executeUpdate();
			conn.commit();
			psmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception:Delete Rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in updating collage");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update end");
	}

	public void delete(CollegeBean bean) throws DuplicateRecordException,
			ApplicationException {
		log.debug("model deleted Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement psmt = conn
					.prepareStatement("DELETE FROM ST_COLLEGE WHERE ID=?");
			
			psmt.setLong(1, bean.getId());
			psmt.executeUpdate();
			conn.commit();// End transaction
			psmt.close();
		} catch (Exception e) {
			log.error("Data base Exception", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception:Delete rollback Exception" + ex.getMessage());

			}
			throw new ApplicationException(
					"Excption:Exception in delete collage");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete started");

	}

	public CollegeBean findByPk(long pk) throws ApplicationException {
		log.debug("model find by pk started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_COLLEGE WHERE ID=?");
		CollegeBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql.toString());
			psmt.setLong(1, pk);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {

			log.error(e);
			throw new ApplicationException(
					"Exception:Exception in getting collage by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model find by pk end");
		return bean;
	}

	/**
	 * Search College with pagination
	 * 
	 * @return list : List of Users
	 * @param bean
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 * @throws DatabaseException
	 */
	public List search(CollegeBean bean, int pageNo, int pageSize)
			throws ApplicationException {
		log.debug("model search started");
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM ST_COLLEGE WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append("AND id=" + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME LIKE '" + bean.getName() + "%'");
			}
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" AND ADDRESS LIKE '" + bean.getAddress() + "%'");
			}
			if (bean.getState() != null && bean.getState().length() > 0) {
				sql.append(" AND STATE LIKE '" + bean.getState() + "%'");
			}
			if (bean.getCity() != null && bean.getCity().length() > 0) {
				sql.append(" AND CITY  LIKE '" + bean.getCity() + "%'");
			}
			if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
				sql.append(" AND PHONE_NO=" + bean.getPhoneNo() + "%'");
			}

		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// calculte start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql.toString());
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("DataBase Exception");
			throw new ApplicationException(
					"Exceptin:Exceptin in collage search");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("model search end");
		return list;

	}

	/**
	 * Search College
	 * 
	 * @param bean
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(CollegeBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * Get List of College
	 * 
	 * @return list : List of College
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * Get List of College with pagination
	 * 
	 * @return list : List of College
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 **/
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE");
		// IF PAGE SIZE GREATER THEN ZERO THEN APPLY APGINATION
		if (pageSize > 0) {
			// calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql.toString());
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				CollegeBean bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("DataBase exception..", e);
			throw new ApplicationException(
					"Exception:Exception getting list of user");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model list end");
		return list;

	}
}
