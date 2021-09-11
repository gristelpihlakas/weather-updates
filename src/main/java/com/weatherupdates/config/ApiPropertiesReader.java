package com.weatherupdates.config;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ApiPropertiesReader {

    private static final Properties PROPERTIES;

    private static final String PROP_FILE = "api.properties";

    private ApiPropertiesReader() {
    }

    static {
        PROPERTIES = new Properties();
        final URL props = ClassLoader.getSystemResource(PROP_FILE);
        try {
            PROPERTIES.load(props.openStream());
        } catch (IOException ex) {
            System.out.println("Could not load api.properties.");
        }
    }

    public static String getProperty(final String name) {
        String property = PROPERTIES.getProperty(name);
        if (property == null) {
            System.out.println(name + " property not found in api.properties.");
        }
        return PROPERTIES.getProperty(name);
    }
}
