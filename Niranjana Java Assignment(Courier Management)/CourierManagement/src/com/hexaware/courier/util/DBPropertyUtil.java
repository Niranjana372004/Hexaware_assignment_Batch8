//package com.hexaware.courier.util;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Properties;
//
//public class DBPropertyUtil {
//    public static String getConnectionString(String propertyFileName) {
//        Properties properties = new Properties();
//        try (FileInputStream fis = new FileInputStream(propertyFileName)) {
//            properties.load(fis);
//            return String.format("%s?user=%s&password=%s",
//                properties.getProperty("url"),
//                properties.getProperty("username"),
//                properties.getProperty("password"));
//        } catch (IOException e) {
//            System.err.println("Error loading database properties: " + e.getMessage());
//            return null;
//        }
//    }
//}

package com.hexaware.courier.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    public static String getConnectionString(String propertyFileName) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/" + propertyFileName)) {
            properties.load(fis);
            return String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s",
                properties.getProperty("db.host", "localhost"),
                properties.getProperty("db.port", "3306"),
                properties.getProperty("db.name", "assignment"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password"));
        } catch (IOException e) {
            System.err.println("Error loading database properties: " + e.getMessage());
            throw new RuntimeException("Failed to load database properties", e);
        }
    }
}