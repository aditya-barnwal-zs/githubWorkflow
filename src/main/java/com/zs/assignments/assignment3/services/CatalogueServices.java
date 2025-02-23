package com.zs.assignments.assignment3.services;

import com.zs.assignments.assignment3.models.Category;
import com.zs.assignments.assignment3.models.Product;
import com.zs.assignments.assignment3.models.SubCategory;

import java.util.ArrayList;

public class CatalogueServices {
    public void viewCatalogue(ArrayList<Category> catalogue) {
        System.out.println("Catalogue " + catalogue.size());

        for (Category category : catalogue) {
            System.out.println("   ├── " + category.getName());

            for (SubCategory subCategory : category.getSubCategories()) {
                System.out.println("   |   ├── " + subCategory.getName());

                for (Product product : subCategory.getProducts()) {
                    System.out.println("   |      |   ├── " + product.getName());
                    System.out.println("   |      |     ├── " + product.getPrice());
                    System.out.println("   |      |     ├── " + product.getBrand());
                    System.out.println("   |      |     ├── " + product.getDescription());
                    System.out.println("   |      |     ├── " + product.isReturnable());
                }
            }
        }
    }

    public ArrayList<Category> dummyCatalogue() {

        ArrayList<Category> catalogue = new ArrayList<>();

        Category category = new Category("Electronics");
        SubCategory subCategory = new SubCategory(
                "Home Applainces");
        Product product = new Product(
                "Chimney",
                12000,
                "Faber",
                "auto clean",
                false);
        Product product2 = new Product(
                "Water Purifer",
                10000,
                "Livpure",
                "3 step filteration",
                true);

        ArrayList<Product> productArray = new ArrayList<>();
        productArray.add(product);
        productArray.add(product2);

        subCategory.setProducts(productArray);

        ArrayList<SubCategory> subcategoryArray = new ArrayList<>();
        subcategoryArray.add(subCategory);

        category.setSubCategories(subcategoryArray);

        catalogue.add(category);
        return catalogue;
    }
}
