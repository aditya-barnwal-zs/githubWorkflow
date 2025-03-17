package com.zs.assignments.assignment11.exceptions;

/**
 * Exception thrown when a category is not found in the database
 */
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(Long id) {
        super("Category not found with id: " + id);
    }
}
