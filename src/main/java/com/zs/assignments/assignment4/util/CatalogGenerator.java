package com.zs.assignments.assignment4.util;

import com.zs.assignments.assignment3.models.Category;
import com.zs.assignments.assignment3.models.Product;
import com.zs.assignments.assignment3.models.SubCategory;

import java.util.ArrayList;

public class CatalogGenerator {
    public ArrayList<Category> dummyCatalog() {
        ArrayList<Category> catalogue = new ArrayList<>();

        Category category = new Category("Electronics");
        ArrayList<SubCategory> subCategoryArray = new ArrayList<>();

        SubCategory subCategory1 = new SubCategory("Home-Appliances");
        ArrayList<Product> productArray1 = new ArrayList<>();
        productArray1.add(new Product("Chimney", 12000, "Faber", "auto clean", false));
        subCategory1.setProducts(productArray1);

        SubCategory subCategory2 = new SubCategory("SmartPhones");
        ArrayList<Product> productArray2 = new ArrayList<>();
        productArray2.add(new Product("IphoneX", 10000, "Iphone", "A good phone", true));
        subCategory2.setProducts(productArray2);

        subCategoryArray.add(subCategory1);
        subCategoryArray.add(subCategory2);
        category.setSubCategories(subCategoryArray);
        catalogue.add(category);

        category = new Category("Grocery");
        subCategoryArray = new ArrayList<>();

        subCategory1 = new SubCategory("food");
        productArray1 = new ArrayList<>();
        productArray1.add(new Product("Rice", 100, "IndiaGate", "good rice", false));
        subCategory1.setProducts(productArray1);

        subCategory2 = new SubCategory("Personal Care");
        productArray2 = new ArrayList<>();
        productArray2.add(new Product("FaceWash", 150, "Wow", "A good face wash", true));
        subCategory2.setProducts(productArray2);

        subCategoryArray.add(subCategory1);
        subCategoryArray.add(subCategory2);
        category.setSubCategories(subCategoryArray);
        catalogue.add(category);

        category = new Category("Clothing");
        subCategoryArray = new ArrayList<>();

        subCategory1 = new SubCategory("kids");
        productArray1 = new ArrayList<>();
        productArray1.add(new Product("Jeans", 1500, "Snitch", "good pant", false));
        subCategory1.setProducts(productArray1);

        subCategory2 = new SubCategory("Men");
        productArray2 = new ArrayList<>();
        productArray2.add(new Product("Shirt", 1200, "Killer", "A good shirt", true));
        subCategory2.setProducts(productArray2);

        subCategoryArray.add(subCategory1);
        subCategoryArray.add(subCategory2);
        category.setSubCategories(subCategoryArray);
        catalogue.add(category);

        return catalogue;
    }
}
