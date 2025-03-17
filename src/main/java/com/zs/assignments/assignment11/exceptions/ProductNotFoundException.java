package com.zs.assignments.assignment11.exceptions;

/**
 * Exception thrown when a product is not found in the database
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}
