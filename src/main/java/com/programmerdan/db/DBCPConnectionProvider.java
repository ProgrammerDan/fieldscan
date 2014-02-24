package com.programmerdan.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Properties;
import java.util.Map;
import java.io.StringWriter;
import java.io.PrintWriter;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.internal.ConnectionProviderInitiator;

import org.hibernate.service.spi.Stoppable;
import org.hibernate.service.spi.Configurable;

import org.hibernate.cfg.Environment;

import org.hibernate.HibernateException;
import org.hibernate.service.UnknownUnwrapTypeException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * LARGELY based on http://www.mkyong.com/hibernate/how-to-configure-dbcp-connection-pool-in-hibernate/ and http://wiki.apache.org/commons/DBCP/Hibernate
 *
 * Note that both the above links are VERY out of date, so trying some monkey
 * patching based on hibernate-orm's github version of Proxool provider.
 * I'll probably wind up making many more changes before I'm done.
 *
 * Wraps DBCP with a Hibernate-compatible connection provider so that
 * we can benefit from DBCP's connection pooling and still use Hibernate.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Might be the only version of this boilerplate. Provides Hibernate
 *   with a path to DBCP.
 */
public class DBCPConnectionProvider implements ConnectionProvider, Stoppable,
		Configurable {
	private static final Logger log = LoggerFactory.getLogger(
			DBCPConnectionProvider.class);
	
	private static final String PREFIX = "hibernate.dbcp.";
	private static final String DBCP_PS_MAXACTIVE = 
			"hibernate.dbcp.ps.maxActive";
	private BasicDataSource ds;

	/**
	 * Configures a provider instance using what should be the hibernate
	 * properties file. Extracts relevant properties using hibernate's
	 * configuration constants settings.
	 * 
	 * @param props hibernate configuration file
	 */
	@Override
	public void configure(Map props) {
		try {
			Properties prop = new Properties();
			prop.putAll(props);
			configure(prop);
		} catch (HibernateException he) {
			log.error("Error configuring", he);
		}
	}


	public void configure(Properties props) throws HibernateException {
		try {
			log.debug("Setting up DBCPConnectionProvider");

			Properties dbcpProperties = new Properties();

			String jdbcDriverClass = props.getProperty(Environment.DRIVER);
			String jdbcUrl = props.getProperty(Environment.URL);
			dbcpProperties.put("driverClassName", jdbcDriverClass);
			dbcpProperties.put("url", jdbcUrl);

			String username = props.getProperty(Environment.USER);
			String password = props.getProperty(Environment.PASS);
			dbcpProperties.put("username", username);
			dbcpProperties.put("password", password);

			String isolationLevel = props.getProperty(Environment.ISOLATION);
			if ((isolationLevel != null) &&
					(isolationLevel.trim().length() > 0)) {
				dbcpProperties.put("defaultTransactionIsolation",
						isolationLevel);
			}

			String autocommit = props.getProperty(Environment.AUTOCOMMIT);
			if ((autocommit != null) && autocommit.trim().length() > 0) {
				dbcpProperties.put("defaultAutoCommit", autocommit);
			} else {
				dbcpProperties.put("defaultAutoCommit",
						String.valueOf(Boolean.FALSE));
			}

			String poolSize = props.getProperty(Environment.POOL_SIZE);
			if ((poolSize != null) && (poolSize.trim().length() > 0)
					&& (Integer.parseInt(poolSize) > 0)) {
				dbcpProperties.put("maxActive", poolSize);
			}

			// Copying all "driver" properties into "connectionProperties"
			Properties driverProps = ConnectionProviderInitiator
					.getConnectionProperties(props);
			if (driverProps.size() > 0) {
				StringBuffer connectionProperties = new StringBuffer();
				Boolean first = true;
				for (Map.Entry entry : driverProps.entrySet()) {
					if (!first) {
						connectionProperties.append(";");
					}
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					connectionProperties.append(key).append("=")
							.append(value);
					first = false;
				}

				dbcpProperties.put("connectionProperties",
						connectionProperties.toString());
			}

			// Copy all DBCP properties removing the prefix.
			for (Map.Entry entry : props.entrySet()) {
				String key = (String) entry.getKey();
				if (key.startsWith(PREFIX)) {
					String property = key.substring(PREFIX.length());
					String value = (String) entry.getValue();
					dbcpProperties.put(property, value);
				}
			}

			// Backward-compatibility TODO: evaluate if this is needed.
			if (props.getProperty(DBCP_PS_MAXACTIVE) != null) {
				dbcpProperties.put("poolPreparedStatements",
						String.valueOf(Boolean.TRUE));
				dbcpProperties.put("maxOpenPreparedStatements",
						props.getProperty(DBCP_PS_MAXACTIVE));
			}

			if (log.isDebugEnabled()) {
				StringWriter sw = new StringWriter();
				dbcpProperties.list(new PrintWriter(sw, true));
				log.debug(sw.toString());
			}

			ds = (BasicDataSource) BasicDataSourceFactory
					.createDataSource(dbcpProperties);

			// Force the data source to initialize.
			Connection testConn = ds.getConnection();
			testConn.close();

			logStatistics();
		} catch (Exception e) { // TODO: Let's catch some real exceptions
			log.error("Could not create a DBCP pool", e);
			if (ds != null) { 
				try {
					ds.close();
				} catch (Exception d) {
					log.error("Could not clean up, either", d);
				}
			}
			throw new HibernateException("Could not create a DBCP pool", e);
		}
		log.debug("Setting up DBCPConnectionProvider complete");
	}

	/**
	 * Gets a connection from the pool.
	 * 
	 * @return a connection from the pool
	 * @see ConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} finally {
			logStatistics();
		}
		return conn;
	}

	/**
	 * Closes a connection previously retrieved from the pool.
	 * 
	 * @param conn the connection to close
	 * @see ConnectionProvider#closeConnection(Connection)
	 */
	@Override
	public void closeConnection(Connection conn) throws SQLException {
		try {
			conn.close();
		} finally {
			logStatistics();
		}
	}

	/**
	 * Close the Connection Provider
	 * 
	 * @see ConnectionProvider.close()
	 */
	@Deprecated
	public void close() throws HibernateException {
		stop();
	}

	/**
	 * Apparently close was deprecated before 4.3.1, my base example
	 * was hopelessly out of date. Hoping these changes fix the problem.
	 */
	@Override
	public void stop() {
		log.debug("Closing up the DBCPConnectionProvider");
		logStatistics();
		try {
			if (ds != null) {
				ds.close();
				ds = null;
			} else {
				log.warn("Cannot close DBCP pool (not initialized)");
			}
		} catch (Exception e) {
			final String msg = "Could not close DBCP pool";
			log.error(msg, e);
			throw new HibernateException(msg, e);
		}
		log.debug("Close DBCPConnectionProvider complete");
	}

	/**
	 * Internal method to show some basic pool statistics.
	 */
	protected void logStatistics() {
		if (log.isDebugEnabled()) {
			log.debug("active: " + ds.getNumActive() + " (max: " +
					ds.getMaxActive() + ") idle: " + ds.getNumIdle() +
					" (max: " + ds.getMaxIdle() + ")");
		}
	}

	/**
	 * @return boolean indicating if Aggressive Release is supported
	 * @see ConnectionProvider#supportsAggressiveRelease()
	 */
	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

	/**
	 * Borrowed from proxool implementation.
	 * @see org.hibernate.proxool.internal.ProxoolConnectionProvider
	 */
	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return ConnectionProvider.class.equals(unwrapType) ||
				DBCPConnectionProvider.class.isAssignableFrom(unwrapType);
	}

	/**
	 * Borrowed from proxool implementation.
	 * @see org.hibernate.proxool.internal.ProxoolConnectionProvider
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> unwrapType) {
		if ( ConnectionProvider.class.equals(unwrapType) ||
				DBCPConnectionProvider.class.isAssignableFrom(unwrapType)) {
			return (T) this;
		} else {
			throw new UnknownUnwrapTypeException(unwrapType);
		}
	}
}
