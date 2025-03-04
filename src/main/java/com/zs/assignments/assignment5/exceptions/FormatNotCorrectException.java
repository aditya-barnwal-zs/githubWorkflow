package com.zs.assignments.assignment5.exceptions;

/**
 * Custom exception to handle cases where the input format does not match the expected structure.
 */
public class FormatNotCorrectException extends Exception {

    /**
     * Constructs a new FormatNotCorrectException with a specified error message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public FormatNotCorrectException(String message) {
        super(message);
    }
}
