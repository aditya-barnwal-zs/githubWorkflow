package com.zs.assignments.assignment3.services;

import com.zs.assignments.assignment3.models.Category;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CategoryServices {
    Scanner sc = new Scanner(System.in);

    public void addCategory(ArrayList<Category> catalogue, String categoryName) {

        for (Category currentCategory : catalogue) {
            if (Objects.equals(currentCategory.getName(), categoryName)) {
                System.out.println("Category already exist");
                return;
            }
        }
        Category newCategory = new Category(categoryName);
        catalogue.add(newCategory);
    }

    public void deleteCategory(ArrayList<Category> catalogue, String categoryName) {

        for (Category currentCategory : catalogue) {
            if (Objects.equals(currentCategory.getName(), categoryName)) {
                catalogue.remove(currentCategory);
                System.out.println("Category " + categoryName + "deleted succesfully");
                return;
            }
        }
        System.out.println("Category do not exist");
    }

    public boolean isCategoryExist(ArrayList<Category> allCategoriesList, String categoryName) {

        for (Category currentCategory : allCategoriesList) {
            if (Objects.equals(currentCategory.getName(), categoryName)) {
                return true;
            }
        }
        return false;
    }

    public Category getCategory(ArrayList<Category> allCategoriesList, String categoryName) {
        for (Category currentCategory : allCategoriesList) {
            if (Objects.equals(currentCategory.getName(), categoryName)) {
                return currentCategory;
            }
        }
        return null;
    }
}
