package com.zs.assignments.assignment7.dao;

import com.zs.assignments.assignment7.config.DatabaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryDaoImpl implements QueryDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Connection CONNECTION = DatabaseConfig.getDatabaseConfig().getConnection();

    @Override
    public void explainQuery() {
        try {
            String query = "EXPLAIN SELECT * FROM student";
            Statement statement = CONNECTION.createStatement();
            ResultSet result = statement.executeQuery(query);
            LOGGER.info(query);
            while (result.next()) {
                LOGGER.info(result.getString(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void explainAnalyzeQuery() {
        try {
            String query = "EXPLAIN ANALYZE SELECT * FROM student";
            Statement statement = CONNECTION.createStatement();
            ResultSet result = statement.executeQuery(query);
            LOGGER.info(query);
            while (result.next()) {
                LOGGER.info(result.getString(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
