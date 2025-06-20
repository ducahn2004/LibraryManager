package org.group4.dao.base;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BaseDAO is an abstract class that provides a centralized configuration
 * for database connection management using HikariCP, a high-performance
 * JDBC connection pool. This class initializes a connection pool and
 * provides a method to obtain database connections for use in DAO classes.
 */
public abstract class BaseDAO {

  /** Logger for logging informational and error messages. */
  private static final Logger logger = Logger.getLogger(BaseDAO.class.getName());

  /**
   * The HikariDataSource instance used for managing database connections.
   * <p>It is configured to connect to the specified MySQL database with
   *    defined settings for optimal connection pool usage.</p>
   */
  private static final HikariDataSource dataSource;

  // Static block to initialize HikariCP configuration and set up the connection pool
  static {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(System.getenv("JDBC_DATABASE_URL"));
    config.setUsername(System.getenv("JDBC_DATABASE_USERNAME"));
    config.setPassword(System.getenv("JDBC_DATABASE_PASSWORD"));
    config.setMaximumPoolSize(10);

    dataSource = new HikariDataSource(config);
    logger.log(Level.INFO, "Database connection pool initialized successfully");
  }

  /**
   * Retrieves a database connection from the HikariCP connection pool.
   * This connection should be closed after usage to return it to the pool.
   *
   * @return a Connection object to interact with the database
   * @throws SQLException if a database access error occurs
   */
  protected Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
}
