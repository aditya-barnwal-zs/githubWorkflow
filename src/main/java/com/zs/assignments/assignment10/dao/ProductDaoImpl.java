package com.zs.assignments.assignment10.dao;

import com.zs.assignments.assignment10.config.DatabaseConfig;
import com.zs.assignments.assignment10.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 * Implements database operations for Product.
 * Handles CRUD operations using SQL queries.
 */
public class ProductDaoImpl implements ProductDao {
    private final Logger LOGGER = LogManager.getLogger(ProductDaoImpl.class);
    private final Connection CONNECTION;

    /**
     * Constructor to get the database connection.
     */
    public ProductDaoImpl() {
        CONNECTION = DatabaseConfig.getDatabaseConfig().getConnection();
    }

    /**
     * Gets all products from the database.
     * @return ResultSet containing all products.
     */
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

    /**
     * Finds a product by its ID.
     * @param id Product ID.
     * @return Product object if found, otherwise null.
     */
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

    /**
     * Inserts a new product into the database.
     * @param product Product to insert.
     * @return true if insertion successful, false otherwise.
     */
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

    /**
     * Updates an existing product.
     * @param product Product with updated details.
     * @return true if update was successful, false otherwise.
     */
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

    /**
     * Deletes a product by its ID.
     * @param id Product ID.
     * @return true if deletion was successful, false otherwise.
     */
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
