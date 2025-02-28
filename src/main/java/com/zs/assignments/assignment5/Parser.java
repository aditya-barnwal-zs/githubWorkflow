package com.zs.assignments.assignment5;

import com.zs.assignments.assignment5.controllers.ParserController;

/**
 * Entry point for the application
 */
public class Parser {
    private final static String FILE_PATH = "/Users/zopsmart/Desktop/Projects/zs-assignments/gitlog.txt";

    /**
     * Main method to start the parser.
     */
    public static void main(String[] args) {
        ParserController parserController = new ParserController();
        parserController.runParser(FILE_PATH);
    }
}
