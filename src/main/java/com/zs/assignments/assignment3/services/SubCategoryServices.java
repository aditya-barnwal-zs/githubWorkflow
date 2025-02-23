package com.zs.assignments.assignment3.services;

import com.zs.assignments.assignment3.models.Category;
import com.zs.assignments.assignment3.models.SubCategory;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class SubCategoryServices {
    Scanner sc= new Scanner(System.in);
    public void addSubCategory(Category category){
        System.out.println("Enter the new Sub-Category name: ");
        String newSubCategoryName= sc.next();

        if(isSubCategoryExist(category, newSubCategoryName)){
            System.out.println("Sub-Category already exist");
            return;
        }
        SubCategory newSubCategory=new SubCategory(newSubCategoryName);
        ArrayList<SubCategory> allSubCategoriesList= category.getSubCategories();

        allSubCategoriesList.add(newSubCategory);
    }

    public void deleteSubCategory(Category category){
        System.out.println("Enter the Sub-Category name: ");
        String SubCategoryName= sc.next();

        ArrayList<SubCategory> allSubCategoriesList= category.getSubCategories();
        for(SubCategory currentSubCategory: allSubCategoriesList) {
            if (Objects.equals(currentSubCategory.getName(), SubCategoryName)) {
                allSubCategoriesList.remove(currentSubCategory);
                System.out.println("Sub-Category " + SubCategoryName + "deleted succesfully");
                return;
            }
        }
        System.out.println("Sub-Category do not exist");
    }

    public boolean isSubCategoryExist(Category category, String subCategoryName){
        ArrayList<SubCategory> allSubCategoriesList= category.getSubCategories();

        for(SubCategory currentSubCategory: allSubCategoriesList) {
            if (Objects.equals(currentSubCategory.getName(), subCategoryName)) {
                return true;
            }
        }
        return false;
    }

    public SubCategory getSubCategory(Category category, String subCategoryName){
        ArrayList<SubCategory> allSubCategoriesList= category.getSubCategories();

        for(SubCategory currentSubCategory: allSubCategoriesList) {
            if (Objects.equals(currentSubCategory.getName(), subCategoryName)) {
                return currentSubCategory;
            }
        }
        return new SubCategory("NULL");
    }
}
