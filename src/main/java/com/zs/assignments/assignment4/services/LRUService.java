package com.zs.assignments.assignment4.services;

import com.zs.assignments.assignment3.models.Category;
import com.zs.assignments.assignment3.models.Product;
import com.zs.assignments.assignment3.models.SubCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public class LRUService {

    final static Logger LOGGER = LogManager.getLogger();

    public void searchAndUpdate(String name, ArrayList<Category> catalogue, LinkedHashMap<String, Category> categoryLRU, LinkedHashMap<String, SubCategory> subCategoryLRU, int capacity) {
        for (Category currentCategory : catalogue) {
            if (Objects.equals(currentCategory.getName(), name)) {
                LOGGER.info("Category not found in cache");
                print(currentCategory);
                updateCategoryLRU(categoryLRU, currentCategory, capacity);
                return;
            }
            for (SubCategory currentSubCategory : currentCategory.getSubCategories()) {
                if (Objects.equals(currentSubCategory.getName(), name)) {
                    LOGGER.info("Sub Category not found in cache");
                    print(currentSubCategory);
                    updateSubCategoryLRU(subCategoryLRU, currentSubCategory, capacity);
                    return;
                }
            }
        }
        LOGGER.warn("Do not exist");
    }

    public void updateCategoryLRU(LinkedHashMap<String, Category> categoryLRU, Category category, int capacity) {
        String name = category.getName();
        if (categoryLRU.size() == capacity)
            categoryLRU.remove(categoryLRU.keySet().iterator().next());
        categoryLRU.put(name, category);
    }

    public void updateSubCategoryLRU(LinkedHashMap<String, SubCategory> subCategoryLRU, SubCategory subCategory, int capacity) {
        String name = subCategory.getName();
        if (subCategoryLRU.size() == capacity)
            subCategoryLRU.remove(subCategoryLRU.keySet().iterator().next());
        subCategoryLRU.put(name, subCategory);
    }

    public void print(Category category) {
        LOGGER.info("├──" + category.getName());
        for (SubCategory currentCategory : category.getSubCategories())
            print(currentCategory);
    }

    public void print(SubCategory subCategory) {
        LOGGER.info("   ├── " + subCategory.getName());
        for (Product currentProduct : subCategory.getProducts())
            print(currentProduct);
    }

    public void print(Product product) {
        LOGGER.info("   |   ├── " + product.getName());
        LOGGER.info("   |     ├── " + product.getPrice());
        LOGGER.info("   |     ├── " + product.getBrand());
        LOGGER.info("   |     ├── " + product.getDescription());
        LOGGER.info("   |     ├── " + product.isReturnable());
    }
}
