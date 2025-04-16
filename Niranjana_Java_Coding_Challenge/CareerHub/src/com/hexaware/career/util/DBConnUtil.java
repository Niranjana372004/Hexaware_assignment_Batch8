package com.hexaware.career.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnUtil {
    public static Connection getConnection(String propFile) {
        try {
            Properties props = DBPropertyUtil.loadProperties(propFile);
            Class.forName(props.getProperty("driver"));
            return DriverManager.getConnection(
                props.getProperty("url"),
                props.getProperty("username"),
                props.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}