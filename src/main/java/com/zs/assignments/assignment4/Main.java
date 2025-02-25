package com.zs.assignments.assignment4;

import com.zs.assignments.assignment3.models.Category;
import com.zs.assignments.assignment3.models.SubCategory;
import com.zs.assignments.assignment3.services.CatalogueServices;
import com.zs.assignments.assignment4.controller.LRUController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) {

        Logger logger = LogManager.getLogger();
        logger.info("Program started");

        CatalogueServices catalogueServices = new CatalogueServices();
        ArrayList<Category> catalogue = catalogueServices.dummyCatalogue();

        int capacity = 3;
        LinkedHashMap<String, Category> categoryLRU = new LinkedHashMap<>(capacity, 0.5f, true);
        LinkedHashMap<String, SubCategory> subCategoryLRU = new LinkedHashMap<>(capacity, 0.5f, true);


        LRUController controller = new LRUController();
        controller.runProgram(catalogue, categoryLRU, subCategoryLRU, capacity);
    }
}
