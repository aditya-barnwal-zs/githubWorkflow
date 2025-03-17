package com.zs.assignments.assignment11.repository;

import com.zs.assignments.assignment11.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldSaveAndRetrieveCategory() {
        Category category = new Category();
        category.setName("Test Category");

        Category savedCategory = categoryRepository.save(category);
        Optional<Category> retrievedCategory = categoryRepository.findById(savedCategory.getId());

        assertTrue(retrievedCategory.isPresent());
        assertEquals("Test Category", retrievedCategory.get().getName());
    }

    @Test
    void shouldFindAllCategories() {
        Category category1 = new Category("Electronics");
        Category category2 = new Category("Clothing");
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        Iterable<Category> categories = categoryRepository.findAll();
        long count = categoryRepository.count();

        assertNotNull(categories);
        assertTrue(count >= 2); // There might be existing categories from other tests
    }

    @Test
    void shouldDeleteCategory() {
        Category category = new Category("To be deleted");
        Category savedCategory = categoryRepository.save(category);
        assertTrue(categoryRepository.existsById(savedCategory.getId()));

        categoryRepository.deleteById(savedCategory.getId());

        assertFalse(categoryRepository.existsById(savedCategory.getId()));
    }
}
