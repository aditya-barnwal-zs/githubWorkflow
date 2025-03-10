package com.zs.assignments.assignment10.dao;

import com.zs.assignments.assignment10.config.DatabaseConfig;
import com.zs.assignments.assignment10.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class ProductDaoImpl implements ProductDao {
    private final Logger LOGGER = LogManager.getLogger(ProductDaoImpl.class);
    private final Connection CONNECTION;

    public ProductDaoImpl() {
        CONNECTION = DatabaseConfig.getDatabaseConfig().getConnection();
    }

    @Override
    public ResultSet findAll() {
        String query = "SELECT * FROM Product";
        try {
            Statement statement = CONNECTION.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Product findById(int id) {
        String query = "SELECT * FROM Product where id = ?";
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultset = preparedStatement.executeQuery();
            if (resultset.next()) {
                return new Product(resultset.getInt(1),
                        resultset.getString(2),
                        resultset.getInt(3)
                );
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean insert(Product product) {
        String query = "INSERT INTO Product VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setInt(3, product.getPrice());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        String query = "UPDATE Product SET name = ?, price = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    public boolean deleteById(int id) {
        String query = "DELETE FROM Product WHERE id = ?";
        try {
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }
}
