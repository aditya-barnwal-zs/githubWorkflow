package com.zs.assignments.assignment7.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Connection connection;
    private static DatabaseConfig databaseConfig;

    private DatabaseConfig() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/application.properties"));

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to PostgreSQL successfully!");
            } else {
                System.out.println("Failed to connect!");
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseConfig getDatabaseConfig() {
        if (databaseConfig == null) {
            databaseConfig = new DatabaseConfig();
        }
        return databaseConfig;
    }

    public Connection getConnection() {
        return connection;
    }
}
