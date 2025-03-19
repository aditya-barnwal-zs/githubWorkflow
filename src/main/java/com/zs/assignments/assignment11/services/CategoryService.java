package com.zs.assignments.assignment11.services;

import com.zs.assignments.assignment11.dto.CategoryResponse;
import com.zs.assignments.assignment11.dto.ProductResponse;
import com.zs.assignments.assignment11.dto.ResponseMapper;
import com.zs.assignments.assignment11.entity.Category;
import com.zs.assignments.assignment11.entity.Product;
import com.zs.assignments.assignment11.exceptions.CategoryAlreadyExistsException;
import com.zs.assignments.assignment11.exceptions.CategoryNotFoundException;
import com.zs.assignments.assignment11.repository.CategoryRepository;
import com.zs.assignments.assignment11.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Category operations
 * Handles business logic related to categories
 */
@Service
public class CategoryService {

    private static final Logger logger = LogManager.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ResponseMapper responseMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository, ResponseMapper responseMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.responseMapper = responseMapper;
    }

    /**
     * Get all categories
     *
     * @return List of all categories as DTOs
     */
    public List<CategoryResponse> getAllCategories() {
        logger.info("Fetching all categories");
        List<Category> categories = categoryRepository.findAll();
        logger.debug("Found {} categories", categories.size());
        return responseMapper.toCategoryDTOs(categories);
    }

    /**
     * Get a category by its ID
     *
     * @param id Category ID
     * @return Category as DTO
     * @throws CategoryNotFoundException if category is not found
     */
    public CategoryResponse getCategoryById(Long id) {
        logger.info("Fetching category with ID: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", id);
                    return new CategoryNotFoundException(id);
                });

        logger.debug("Found category: {}", category.getName());
        return responseMapper.toCategoryDTO(category);
    }

    /**
     * Get all products in a specific category
     *
     * @param id Category ID
     * @return List of products in the specified category as DTOs
     */
    public List<ProductResponse> getAllProductsByCategoryId(Long id) {
        logger.info("Fetching all products for category ID: {}", id);
        if (!categoryRepository.existsById(id)) {
            logger.error("Category not found with ID: {}", id);
            throw new CategoryNotFoundException(id);
        }

        List<Product> products = productRepository.findByCategoryId(id);
        logger.debug("Found {} products for category ID: {}", products.size(), id);
        return responseMapper.toProductDTOs(products);
    }

    /**
     * Create a new category
     *
     * @param categoryResponse Category information
     * @return Created category as DTO
     */
    @Transactional
    public CategoryResponse createCategory(CategoryResponse categoryResponse) {
        logger.info("Creating new category: {}", categoryResponse.getName());

        Category category = new Category();
        category.setName(categoryResponse.getName());
        if (categoryRepository.existsByName(categoryResponse.getName())) {
            throw new CategoryAlreadyExistsException("Category with this name already exists");
        }
        Category createdCategory = categoryRepository.save(category);
        logger.info("Category created successfully with ID: {}", createdCategory.getId());

        return responseMapper.toCategoryDTO(createdCategory);
    }

    /**
     * Delete a category
     *
     * @param categoryId Product ID
     */
    @Transactional
    public void deleteCategory(Long categoryId) {
        logger.info("Deleting category with ID: {}", categoryId);

        if (!categoryRepository.existsById(categoryId)) {
            logger.error("Category not found with ID: {}", categoryId);
            throw new CategoryNotFoundException(categoryId);
        }

        categoryRepository.deleteById(categoryId);
        logger.info("Category deleted successfully: {}", categoryId);
    }
}
