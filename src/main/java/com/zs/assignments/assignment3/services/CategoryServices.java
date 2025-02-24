package com.zs.assignments.assignment3.services;

import com.zs.assignments.assignment3.models.Category;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryServices {

    public void addCategory(ArrayList<Category> catalogue, String categoryName) {
        if (findCategoryByName(catalogue, categoryName) != null) {
            System.out.println("Category already exist");
            return;
        }
        Category newCategory = new Category(categoryName);
        catalogue.add(newCategory);
    }

    public void deleteCategory(ArrayList<Category> catalogue, String categoryName) {
        Category category = findCategoryByName(catalogue, categoryName);
        if (category != null) {
            catalogue.remove(category);
            System.out.println("Category " + categoryName + "deleted succesfully");
            return;
        }
        System.out.println("Category do not exist");
    }

    public boolean isCategoryExist(ArrayList<Category> allCategoriesList, String categoryName) {
        return findCategoryByName(allCategoriesList, categoryName) != null;
    }

    public Category findCategoryByName(ArrayList<Category> allCategoriesList, String categoryName) {
        for (Category currentCategory : allCategoriesList) {
            if (Objects.equals(currentCategory.getName(), categoryName)) {
                return currentCategory;
            }
        }
        return null;
    }
}
