package com.kgcorner.scaledge.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigurationBuilder {
    private static ConfigurationBuilder INSTANCE;
    private ConfigurationBuilder() {}

    public static ConfigurationBuilder getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ConfigurationBuilder();
        }
        return INSTANCE;
    }

    /**
     * This method loads given properties file and return a copy of {@link Properties} object
     * @param name
     * @return copy of {@link Properties} object containing all properties defined in given file
     */
    public Properties loadConfigurationFile(String name) {
        Properties prop = null;
        InputStream input = null;

        try {
            input = new FileInputStream(name);
            prop = new Properties();
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop!=null?(Properties) prop.clone():null;
    }

    /**
     * This method loads given properties file and return a copy of {@link Properties} object
     * @param fileStream InputStream of the properties file
     * @return copy of {@link Properties} object containing all properties defined in given file
     */
    public Properties loadConfigurationFile(InputStream fileStream) {
        Properties prop = null;
        InputStream input = null;

        try {
            input = fileStream;
            prop = new Properties();
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return (Properties) prop.clone();
    }

}
