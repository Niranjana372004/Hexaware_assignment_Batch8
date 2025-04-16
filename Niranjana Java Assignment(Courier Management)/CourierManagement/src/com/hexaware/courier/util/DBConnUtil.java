//package com.hexaware.courier.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnUtil {
//    public static Connection getConnection(String connectionString) throws SQLException {
//        try {
//            // Load the JDBC driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            
//            // Establish the connection
//            return DriverManager.getConnection(connectionString);
//        } catch (ClassNotFoundException e) {
//            throw new SQLException("JDBC Driver not found", e);
//        }
//    }
//}

package com.hexaware.courier.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    private static String connectionString;
    
    static {
        try {
            // Load the connection string once when class is loaded
            connectionString = DBPropertyUtil.getConnectionString("db.properties");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize DBConnUtil", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connectionString == null) {
            throw new SQLException("Database connection string is not initialized");
        }
        return DriverManager.getConnection(connectionString);
    }
}