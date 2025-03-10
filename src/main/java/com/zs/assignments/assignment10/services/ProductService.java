package com.zs.assignments.assignment10.services;

import com.zs.assignments.assignment10.dao.ProductDao;
import com.zs.assignments.assignment10.dao.ProductDaoImpl;
import com.zs.assignments.assignment10.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {
    private final Logger LOGGER = LogManager.getLogger(ProductService.class);
    ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDaoImpl();
    }

    public ArrayList<Product> findAllProduct() {
        ArrayList<Product> products = new ArrayList<>();
        try (ResultSet resultSet = productDao.findAll()) {
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price")));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return products;
    }


    public Product findById(int id) {
        return productDao.findById(id);
    }

    public boolean saveProduct(Product product) {
        if (productDao.findById(product.getId()) == null) {
            LOGGER.info("Product inserted successfully");
            return productDao.insert(product);
        }
        if (productDao.update(product)) {
            LOGGER.info("Product updated successfully");
            return true;
        }
        return false;
    }

    public boolean deleteProduct(int id) {
        return productDao.deleteById(id);
    }

    public void printAllProducts(ArrayList<Product> products) {

        LOGGER.info("ID | Name | Price");
        LOGGER.info("------------------");

        for (Product product : products) {
            int id = product.getId();
            String name = product.getName();
            int price = product.getPrice();
            LOGGER.info("{} | {} | {}", id, name, price);
        }
    }
}
