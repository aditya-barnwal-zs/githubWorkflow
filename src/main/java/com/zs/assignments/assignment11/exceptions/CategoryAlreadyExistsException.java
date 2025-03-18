package com.zs.assignments.assignment11.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }

    public CategoryAlreadyExistsException(String name, String fieldName) {
        super("Category with " + fieldName + " '" + name + "' already exists");
    }
}
