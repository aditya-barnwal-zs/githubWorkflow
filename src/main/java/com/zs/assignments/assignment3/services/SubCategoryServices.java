package com.zs.assignments.assignment3.services;

import com.zs.assignments.assignment3.models.Category;
import com.zs.assignments.assignment3.models.SubCategory;

import java.util.ArrayList;
import java.util.Objects;

public class SubCategoryServices {

    public void addSubCategory(Category category, String newSubCategoryName) {

        if (isSubCategoryExist(category, newSubCategoryName)) {
            System.out.println("Sub-Category already exist");
            return;
        }
        SubCategory newSubCategory = new SubCategory(newSubCategoryName);
        ArrayList<SubCategory> allSubCategoriesList = category.getSubCategories();

        allSubCategoriesList.add(newSubCategory);
    }

    public void deleteSubCategory(Category category, String subCategoryName) {

        ArrayList<SubCategory> allSubCategoriesList = category.getSubCategories();
        SubCategory subCategory = findSubCategoryByName(category, subCategoryName);
        if (findSubCategoryByName(category, subCategoryName) != null) {
            allSubCategoriesList.remove(subCategory);
            System.out.println("Sub-Category " + subCategoryName + "deleted succesfully");
            return;
        }
        System.out.println("Sub-Category do not exist");
    }

    public boolean isSubCategoryExist(Category category, String subCategoryName) {
        return findSubCategoryByName(category, subCategoryName) != null;

    }

    public SubCategory findSubCategoryByName(Category category, String subCategoryName) {

        ArrayList<SubCategory> allSubCategoriesList = category.getSubCategories();
        for (SubCategory currentSubCategory : allSubCategoriesList) {
            if (Objects.equals(currentSubCategory.getName(), subCategoryName)) {
                return currentSubCategory;
            }
        }
        return null;
    }
}
