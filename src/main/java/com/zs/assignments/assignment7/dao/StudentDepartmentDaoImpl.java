package com.zs.assignments.assignment7.dao;

import com.zs.assignments.assignment7.config.DatabaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

import static com.zs.assignments.assignment7.services.StudentService.getRandomDepartment;

/**
 * Repository class for handling database operations related to the Student-Department association.
 */
public class StudentDepartmentDaoImpl implements StudentDepartmentDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Connection CONNECTION = DatabaseConfig.getDatabaseConfig().getConnection();

    /**
     * Assigns a random department to each student in the database.
     */
    @Override
    public void assignDepartment() {

        try {
            String query = "SELECT id from Student";
            ResultSet studentId;
            Statement statement = CONNECTION.createStatement();
            studentId = statement.executeQuery(query);

            String insertQuery = "INSERT INTO StudentDepartment (studentId, departmentId) VALUES (?, ?)";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(insertQuery);
            CONNECTION.setAutoCommit(false);

            while (studentId.next()) {
                preparedStatement.setInt(1, studentId.getInt("id"));
                preparedStatement.setInt(2, getRandomDepartment());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            CONNECTION.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
