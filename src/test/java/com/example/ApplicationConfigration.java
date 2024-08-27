package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public enum ApplicationConfigration {
    INSTANCE;
    private Properties properties;

    ApplicationConfigration() {
        loadProperties();
    }
    private void loadProperties() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/Application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBaseUrl() { return properties.getProperty("baseURI");}
    public String getUsername() { return properties.getProperty("username");}
    public String getPassword() { return properties.getProperty("password");}

}
