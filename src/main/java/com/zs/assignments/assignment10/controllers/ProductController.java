package com.zs.assignments.assignment10.controllers;

import com.zs.assignments.assignment10.models.Product;
import com.zs.assignments.assignment10.services.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * Handles user interactions for managing products.
 * This class takes input from the user and calls service methods.
 */
public class ProductController {
    private final Logger LOGGER = LogManager.getLogger(ProductController.class);
    private final Scanner SC = new Scanner(System.in);

    ProductService productService;

    public ProductController() {
        this.productService = new ProductService();
    }

    /**
     * This method shows options to the user and handles product operations like view, save, delete, etc.
     */
    public void applyOperations() {
        LOGGER.info("What you want to do: ");

        int option;

        do {
            LOGGER.info("1. View all Product \n" +
                    "2. View a Product \n" +
                    "3. Save a Product \n" +
                    "4. Delete a Product \n" +
                    "5. Exit");
            option = SC.nextInt();

            handleOperation(option);
        }
        while (option != 5);
    }

    /**
     * Handles the operation based on user input.
     * @param option The user-selected option.
     */
    public void handleOperation(int option) {
        switch (option) {
            case 1: {
                LOGGER.info("Details of All product");
                productService.printAllProducts(productService.findAllProduct());
                break;
            }
            case 2: {
                LOGGER.info("Enter the id of product");
                int id = SC.nextInt();
                Product product = productService.findById(id);
                if (product == null) {
                    LOGGER.info("Product do not exists");
                    return;
                }
                LOGGER.info("{} | {} | {}", product.getId(), product.getName(), product.getPrice());
                break;
            }
            case 3: {
                LOGGER.info("Enter details of the product");
                LOGGER.info("Enter id");
                int id = SC.nextInt();
                LOGGER.info("Enter name");
                String name = SC.next();
                LOGGER.info("Enter price");
                int price = SC.nextInt();
                Product product = new Product(id, name, price);
                productService.saveProduct(product);
                break;
            }
            case 4: {
                LOGGER.info("Enter the id of product");
                int id = SC.nextInt();
                Product product = productService.findById(id);
                if (product == null) {
                    LOGGER.info("Product do not exists");
                    return;
                }
                LOGGER.info("Product Deleted successfully");
                productService.deleteProduct(id);
                break;
            }
            case 5: {
                LOGGER.info("Exiting...");
                break;
            }
            default: {
                LOGGER.info("Choose correct option");
            }
        }
    }
}
