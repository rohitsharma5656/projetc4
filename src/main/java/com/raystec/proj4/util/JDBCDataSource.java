package com.raystec.proj4.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.raystec.proj4.exception.ApplicationException;

/**
 * JDBC DataSource is a Data Connection Pool
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 * 
 */

public class JDBCDataSource {

	/**
	 * JDBC Database connection pool ( DCP )
	 */

	private static JDBCDataSource dataSource;

	private JDBCDataSource() {
	}

	private ComboPooledDataSource cpds = null;

	/**
	 * Create instance of Connection Pool
	 * 
	 * @return
	 */

	private static JDBCDataSource getInstance() {
		if (dataSource == null) {

			ResourceBundle rb = ResourceBundle
					.getBundle("com.raystec.proj4.bundle.system");

			dataSource = new JDBCDataSource();
			dataSource.cpds = new ComboPooledDataSource();

			try {
				dataSource.cpds.setDriverClass(rb.getString("driver"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			dataSource.cpds.setJdbcUrl(rb.getString("url"));
			dataSource.cpds.setUser(rb.getString("username"));
			dataSource.cpds.setPassword(rb.getString("password"));
			dataSource.cpds.setInitialPoolSize(new Integer((String) rb
					.getString("initialPoolSize")));
			dataSource.cpds.setAcquireIncrement(new Integer((String) rb
					.getString("acquireIncrement")));
			dataSource.cpds.setMaxPoolSize(new Integer((String) rb
					.getString("maxPoolSize")));
			dataSource.cpds.setMaxIdleTime(DataUtility.getInt(rb
					.getString("timeout")));
			dataSource.cpds.setMinPoolSize(new Integer((String) rb
					.getString("minPoolSize")));
		}
		return dataSource;
	}

	/**
	 * Gets the connection from ComboPooledDataSource
	 * 
	 * @return connection
	 */

	public static Connection getConnection() throws Exception {
		return getInstance().cpds.getConnection();
	}

	/**
	 * Closes a connection
	 * 
	 * @param connection
	 * @throws Exception
	 */

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}

	public static void trnRollback(Connection connection)
			throws ApplicationException {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				throw new ApplicationException("not successfully close"
						+ ex.getMessage());
			}
		}
	}
}
