package com.zs.assignments.assignment11.exceptions;

/**
 * Exception thrown when attempting to create a product that already exists
 */
public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(String message) {
        super(message);
    }

    public ProductAlreadyExistsException(String name, String fieldName) {
        super("Product with " + fieldName + " '" + name + "' already exists");
    }
}
