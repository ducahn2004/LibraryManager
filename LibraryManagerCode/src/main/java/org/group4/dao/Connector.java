package org.group4.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

  public class DatabaseConector {

    private static Connection connection;

    public static Connection getConnection() {
      if (connection == null) {
        try {
          connection = DriverManager.getConnection("jdbc:mysql://192.168.31.193:3307/book",
              "Satoh",
              "Tranducanh2004@");
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      return connection;
    }

    public static void closeConnection(Connection connection) {
      try {
        if (connection != null && !connection.isClosed()) {
          connection.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
