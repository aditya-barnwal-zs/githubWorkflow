package com.zs.assignments.assignment11.repository;

import com.zs.assignments.assignment11.entity.Category;
import com.zs.assignments.assignment11.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldSaveAndRetrieveProduct() {
        Category category = new Category("Electronics");
        category = categoryRepository.save(category);

        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        Optional<Product> retrievedProduct = productRepository.findById(savedProduct.getId());

        assertTrue(retrievedProduct.isPresent());
        assertEquals("Test Product", retrievedProduct.get().getName());
        assertEquals(100.0, retrievedProduct.get().getPrice());
        assertEquals(category.getId(), retrievedProduct.get().getCategory().getId());
    }

    @Test
    void shouldFindProductByName() {
        Category category = new Category("Electronics");
        category = categoryRepository.save(category);

        Product product = new Product("Unique Name", 200.0, category);
        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findByName("Unique Name");

        assertNotNull(foundProduct);
        assertEquals("Unique Name", foundProduct.get().getName());
    }

    @Test
    void shouldFindProductsByCategoryId() {
        Category category1 = new Category("Electronics");
        Category category2 = new Category("Clothing");
        category1 = categoryRepository.save(category1);
        category2 = categoryRepository.save(category2);

        Product product1 = new Product("Product in Electronics", 300.0, category1);
        Product product2 = new Product("Another Product in Electronics", 400.0, category1);
        Product product3 = new Product("Product in Clothing", 50.0, category2);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        List<Product> electronicsProducts = productRepository.findByCategoryId(category1.getId());
        List<Product> clothingProducts = productRepository.findByCategoryId(category2.getId());

        assertEquals(2, electronicsProducts.size());
        assertEquals(1, clothingProducts.size());
    }

    @Test
    void shouldDeleteProduct() {
        Category category = new Category("Test Category");
        category = categoryRepository.save(category);

        Product product = new Product("To be deleted", 150.0, category);
        Product savedProduct = productRepository.save(product);
        assertTrue(productRepository.existsById(savedProduct.getId()));

        productRepository.deleteById(savedProduct.getId());

        assertFalse(productRepository.existsById(savedProduct.getId()));
    }
}
