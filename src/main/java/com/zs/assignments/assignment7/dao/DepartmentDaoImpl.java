package com.zs.assignments.assignment7.dao;

import com.zs.assignments.assignment7.config.DatabaseConfig;
import com.zs.assignments.assignment7.models.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Repository class for handling database operations related to the Department entity.
 */
public class DepartmentDaoImpl implements DepartmentDao {
    private final static Logger LOGGER = LogManager.getLogger();
    private final Connection CONNECTION = DatabaseConfig.getDatabaseConfig().getConnection();

    /**
     * Inserts multiple departments into the database in bulk.
     *
     * @param departmentList a list of departments to be added
     */
    @Override
    public void addDepartmentInBulk(ArrayList<Department> departmentList) {
        String query = "INSERT INTO Department (id , name) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
            for (Department department : departmentList) {
                preparedStatement.setInt(1, department.getId());
                preparedStatement.setString(2, department.getName());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            CONNECTION.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
