package com.zs.assignments.assignment3.controller;

import com.zs.assignments.assignment3.models.Category;
import com.zs.assignments.assignment3.models.SubCategory;
import com.zs.assignments.assignment3.services.CatalogueServices;
import com.zs.assignments.assignment3.services.CategoryServices;
import com.zs.assignments.assignment3.services.ProductServices;
import com.zs.assignments.assignment3.services.SubCategoryServices;

import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    Scanner sc = new Scanner(System.in);
    CatalogueServices catalogueServices = new CatalogueServices();
    CategoryServices categoryServices = new CategoryServices();
    ProductServices productServices = new ProductServices();
    SubCategoryServices subCategoryServices = new SubCategoryServices();

    public void control(ArrayList<Category> catalogue) {
        System.out.println("What you want to do: ");

        int option;

        do {
            System.out.println("1. View Catalogue \n" +
                    "2. Add Category \n" +
                    "3. Add Sub-Category \n" +
                    "4. Add Product \n" +
                    "5. Delete Category \n" +
                    "6. Delete Sub-Category \n" +
                    "7. Delete Product \n" +
                    "8. Exit");
            option = sc.nextInt();

            handleOperation(catalogue, option);
        }
        while (option != 8);

    }

    public void handleOperation(ArrayList<Category> catalogue, int option) {
        switch (option) {
            case 1:
                catalogueServices.viewCatalogue(catalogue);
                break;

            case 2: {
                System.out.println("Enter the new category name: ");
                String categoryName = sc.next();
                categoryServices.addCategory(catalogue, categoryName);
                break;
            }
            case 3: {
                System.out.println("Enter the category: ");
                String categoryName = sc.next();
                if (!categoryServices.isCategoryExist(catalogue, categoryName)) {
                    System.out.println("Category do not exist");
                    return;
                }
                Category category = categoryServices.getCategory(catalogue, categoryName);
                subCategoryServices.addSubCategory(category);
                break;
            }
            case 4: {
                System.out.println("Enter the category: ");
                String categoryName = sc.next();
                if (!categoryServices.isCategoryExist(catalogue, categoryName)) {
                    System.out.println("Category do not exist");
                    return;
                }
                Category category = categoryServices.getCategory(catalogue, categoryName);

                System.out.println("Enter the sub-category: ");
                String subCategoryName = sc.next();
                if (!subCategoryServices.isSubCategoryExist(category, subCategoryName)) {
                    System.out.println("Sub-Category do not exist");
                    return;
                }
                SubCategory subCategory = subCategoryServices.getSubCategory(category, subCategoryName);
                String name;
                String brand;
                String description;
                int price;
                boolean isReturnable;

                System.out.println("Enter the new product details: ");
                System.out.println("Enter the Name: ");
                name = sc.next();
                System.out.println("Enter the Brand: ");
                brand = sc.next();
                System.out.println("Enter the Description: ");
                description = sc.nextLine();
                System.out.println("Enter the Price: ");
                price = sc.nextInt();
                System.out.println("Is the product returnable: Yes or Not ");
                isReturnable = sc.nextBoolean();
                productServices.addProduct(subCategory, name, brand, description, price, isReturnable);
                break;
            }
            case 5: {
                System.out.println("Enter the category name: ");
                String categoryName = sc.next();
                categoryServices.deleteCategory(catalogue, categoryName);
                break;
            }
            case 6: {
                System.out.println("Enter the category: ");
                String categoryName = sc.next();
                if (!categoryServices.isCategoryExist(catalogue, categoryName)) {
                    System.out.println("Category do not exist");
                    return;
                }
                Category category = categoryServices.getCategory(catalogue, categoryName);
                subCategoryServices.deleteSubCategory(category);
                break;
            }
            case 7: {
                System.out.println("Enter the category: ");
                String categoryName = sc.next();
                if (!categoryServices.isCategoryExist(catalogue, categoryName)) {
                    System.out.println("Category do not exist");
                    return;
                }
                Category category = categoryServices.getCategory(catalogue, categoryName);

                System.out.println("Enter the sub-category: ");
                String subCategoryName = sc.next();
                if (!subCategoryServices.isSubCategoryExist(category, subCategoryName)) {
                    System.out.println("Sub-Category do not exist");
                    return;
                }
                System.out.println("Enter the Product name: ");
                String productName;
                productName = sc.next();
                SubCategory subCategory = subCategoryServices.getSubCategory(category, subCategoryName);
                productServices.deleteProduct(subCategory, productName);
                break;
            }
            case 8:
                System.out.println("Exiting...");
                break;

            default:
                System.out.println("Please choose a correct option");
                break;
        }
    }
}
