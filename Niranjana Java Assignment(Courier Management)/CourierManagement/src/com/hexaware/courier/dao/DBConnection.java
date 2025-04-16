//package com.hexaware.courier.dao;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import com.hexaware.courier.util.DBConnUtil;
//import com.hexaware.courier.util.DBPropertyUtil;
//
//public class DBConnection {
//    private static Connection connection;
//    private static final String PROPERTY_FILE = "db.properties";
//
//    public static Connection getConnection() throws SQLException {
//        if (connection == null || connection.isClosed()) {
//            String connectionString = DBPropertyUtil.getConnectionString(PROPERTY_FILE);
//            if (connectionString == null) {
//                throw new SQLException("Failed to get connection string from properties");
//            }
//            connection = DBConnUtil.getConnection(connectionString);
//            System.out.println("Successfully connected to 'assignment' database");
//        }
//        return connection;
//    }
//
//    public static void closeConnection() {
//        try {
//            if (connection != null && !connection.isClosed()) {
//                connection.close();
//                System.out.println("Database connection closed");
//            }
//        } catch (SQLException e) {
//            System.err.println("Error closing connection: " + e.getMessage());
//        }
//    }
//}

package com.hexaware.courier.dao;

import java.sql.Connection;
import java.sql.SQLException;
import com.hexaware.courier.util.DBConnUtil;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DBConnUtil.getConnection();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}