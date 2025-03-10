package com.zs.assignments.assignment10.dao;

import com.zs.assignments.assignment10.models.Product;

import java.sql.ResultSet;

public interface ProductDao {
    ResultSet findAll();

    Product findById(int id);

    boolean insert(Product product);

    boolean update(Product product);

    boolean deleteById(int id);
}
