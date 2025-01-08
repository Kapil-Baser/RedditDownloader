package com.redditapp.redditdownloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = ConfigLoader.class.getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Unable to find properties file");
            }
            properties.load(input);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static Properties getProperties() {
        return properties;
    }
}
