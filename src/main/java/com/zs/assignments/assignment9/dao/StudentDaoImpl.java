package com.zs.assignments.assignment9.dao;

import com.zs.assignments.assignment9.config.DatabaseConfig;
import com.zs.assignments.assignment9.models.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class StudentDaoImpl implements StudentDao {
    private final Logger LOGGER = LogManager.getLogger();
    private final Connection CONNECTION = DatabaseConfig.getDatabaseConfig().getConnection();

    @Override
    public Student createStudent(Student student) {
        String query = "INSERT INTO Student(id, firstName, secondName, VALUES ( ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) return student;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Student getStudentById(int id) {
        String query = "SELECT FROM Student WHERE id = ?";
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
