package com.hexaware.career.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    public static Properties loadProperties(String fileName) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(fileName);
        props.load(fis);
        return props;
    }
}