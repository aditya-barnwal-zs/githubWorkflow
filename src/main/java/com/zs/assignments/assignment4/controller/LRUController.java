package com.zs.assignments.assignment4.controller;

import com.zs.assignments.assignment3.controller.Controller;
import com.zs.assignments.assignment3.models.Category;
import com.zs.assignments.assignment3.models.SubCategory;
import com.zs.assignments.assignment4.services.LRUService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class LRUController {
    final static Scanner sc = new Scanner(System.in);
    final static Logger LOGGER = LogManager.getLogger();
    LRUService lruService = new LRUService();

    public void runProgram(ArrayList<Category> catalogue, LinkedHashMap<String, Category> categoryLRU, LinkedHashMap<String, SubCategory> subCategoryLRU, int capacity) {

        int option;

        do {
            LOGGER.info("What you want to do:");
            LOGGER.info("1. Search a Category or Sub-Category \n"
                    + "2. Exit");
            option = sc.nextInt();

            handleOperation(option, catalogue, categoryLRU, subCategoryLRU, capacity);
        }
        while (option != 2);
    }

    private void handleOperation(int option, ArrayList<Category> catalogue, LinkedHashMap<String, Category> categoryLRU, LinkedHashMap<String, SubCategory> subCategoryLRU, int capacity) {
        switch (option) {

            case 1:
                LOGGER.info("Enter the Category or Sub-Category name: ");
                String name = sc.next();
                if (categoryLRU.get(name) != null) {
                    LOGGER.info("Found category in Category LRU");
                    lruService.print(categoryLRU.get(name));
                    return;
                }
                if (subCategoryLRU.get(name) != null) {
                    LOGGER.info("Found category in SubCategory LRU");
                    lruService.print(subCategoryLRU.get(name));
                    return;
                }
                lruService.searchAndUpdate(name, catalogue, categoryLRU, subCategoryLRU, capacity);
                break;

            case 2:
                LOGGER.info("Exiting...");
                break;

            default:
                LOGGER.warn("Choose a correct option");
                break;
        }
    }
}
