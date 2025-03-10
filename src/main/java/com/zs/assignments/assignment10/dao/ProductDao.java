package com.zs.assignments.assignment10.dao;

import com.zs.assignments.assignment10.models.Product;

import java.sql.ResultSet;

/**
 * Interface for Product Data Access Object (DAO).
 * Defines operations for handling Product records in the database.
 */
public interface ProductDao {
    ResultSet findAll();

    Product findById(int id);

    boolean insert(Product product);

    boolean update(Product product);

    boolean deleteById(int id);
}
