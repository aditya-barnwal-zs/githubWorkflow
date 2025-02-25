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
    Scanner sc = new Scanner(System.in);
    LRUService lruService = new LRUService();
    Logger logger = LogManager.getLogger();

    public void runProgram(ArrayList<Category> catalogue, LinkedHashMap<String, Category> categoryLRU, LinkedHashMap<String, SubCategory> subCategoryLRU, int capacity) {

        int option;

        do {
            logger.info("What you want to do:");
            logger.info("1. Apply operation on Catalogue \n"
                    + "2. Search a Category or Sub-Category \n"
                    + "3. Exit");
            option = sc.nextInt();

            handleOperation(option, catalogue, categoryLRU, subCategoryLRU, capacity);
        }
        while (option != 3);
    }

    private void handleOperation(int option, ArrayList<Category> catalogue, LinkedHashMap<String, Category> categoryLRU, LinkedHashMap<String, SubCategory> subCategoryLRU, int capacity) {
        switch (option) {
            case 1:
                Controller catalogueController = new Controller();
                catalogueController.control(catalogue);
                break;

            case 2:
                logger.info("Enter the Category or Sub-Category name: ");
                String name = sc.next();
                if (categoryLRU.get(name) != null) {
                    logger.info("Found category in Category LRU");
                    lruService.print(categoryLRU.get(name));
                    return;
                }
                if (subCategoryLRU.get(name) != null) {
                    logger.info("Found category in SubCategory LRU");
                    lruService.print(subCategoryLRU.get(name));
                    return;
                }
                lruService.searchAndUpdate(name, catalogue, categoryLRU, subCategoryLRU, capacity);
                break;

            case 3:
                logger.info("Exiting...");
                break;

            default:
                logger.warn("Choose a correct option");
                break;
        }
    }
}
