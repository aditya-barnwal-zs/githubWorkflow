package com.zs.assignments.assignment9.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Manages the database connection using a singleton pattern.
 * Loads database configurations from a properties file and establishes a connection.
 */
public class DatabaseConfig {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseConfig.class);
    private static Connection connection;
    private static DatabaseConfig databaseConfig;

    /**
     * Private constructor that initializes the database connection using properties
     * from the {@code application.properties} file.
     */
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
        } catch (SQLException | IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Returns the singleton instance of {@code DatabaseConfig}, ensuring only one
     * instance of the database configuration is created.
     *
     * @return Singleton instance of {@code DatabaseConfig}.
     */
    public static DatabaseConfig getDatabaseConfig() {
        if (databaseConfig == null) {
            databaseConfig = new DatabaseConfig();
        }
        return databaseConfig;
    }

    /**
     * Returns the active database connection instance.
     *
     * @return {@code Connection} object representing the database connection.
     */
    public Connection getConnection() {
        return connection;
    }
}