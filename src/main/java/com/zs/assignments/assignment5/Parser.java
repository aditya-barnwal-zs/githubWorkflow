package com.zs.assignments.assignment5;

import com.zs.assignments.assignment5.controllers.ParserController;

public class Parser {
    public static void main(String[] args) {
        ParserController parserController = new ParserController();
        String gitLogPath= "/Users/zopsmart/Desktop/Projects/zs-assignments/gitlog.txt";

        parserController.runParser(gitLogPath);
    }
}
