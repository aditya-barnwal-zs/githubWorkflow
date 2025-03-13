package com.zs.assignments.assignment10.services;

import com.zs.assignments.assignment10.dao.ProductDao;
import com.zs.assignments.assignment10.dao.ProductDaoImpl;
import com.zs.assignments.assignment10.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles business logic for product operations.
 * Calls DAO layer for database interaction.
 */
public class ProductService {
    private final Logger LOGGER = LogManager.getLogger(ProductService.class);
    ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDaoImpl();
    }

    /**
     * Fetches all products from the database.
     * @return List of products.
     */
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

    /**
     * Finds a product by its ID.
     * @param id Product ID.
     * @return Product object if found, otherwise null.
     */
    public Product findById(int id) {
        return productDao.findById(id);
    }

    /**
     * Saves a product. If it exists, updates it; otherwise, inserts it.
     * @param product Product to save.
     * @return true if successful, false otherwise.
     */
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

    /**
     * Deletes a product by ID.
     * @param id Product ID.
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deleteProduct(int id) {
        return productDao.deleteById(id);
    }

    /**
     * Prints the products.
     * @param products List of products to print.
     */
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
