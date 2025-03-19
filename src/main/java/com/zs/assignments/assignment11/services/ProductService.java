package com.zs.assignments.assignment11.services;

import com.zs.assignments.assignment11.dto.ResponseMapper;
import com.zs.assignments.assignment11.dto.ProductResponse;
import com.zs.assignments.assignment11.entity.Category;
import com.zs.assignments.assignment11.entity.Product;
import com.zs.assignments.assignment11.exceptions.CategoryNotFoundException;
import com.zs.assignments.assignment11.exceptions.ProductAlreadyExistsException;
import com.zs.assignments.assignment11.exceptions.ProductNotFoundException;
import com.zs.assignments.assignment11.repository.CategoryRepository;
import com.zs.assignments.assignment11.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Product operations
 * Handles business logic related to products
 */
@Service
public class ProductService {

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ResponseMapper responseMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ResponseMapper responseMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.responseMapper = responseMapper;
    }

    /**
     * Get all products
     *
     * @return List of all products as DTOs
     */
    public List<ProductResponse> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        logger.debug("Found {} products", products.size());
        return responseMapper.toProductDTOs(products);
    }

    /**
     * Create a new product
     *
     * @param productResponse Product information
     * @param categoryId Category ID
     * @return Created product as DTO
     */
    @Transactional
    public ProductResponse createProduct(ProductResponse productResponse, Long categoryId) {
        logger.info("Creating new product: {} in category ID: {}", productResponse.getName(), categoryId);

        // Check if category exists
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", categoryId);
                    return new CategoryNotFoundException(categoryId);
                });

        Optional<Product> existingProduct = productRepository.findByName(productResponse.getName());
        if (existingProduct != null && existingProduct.isPresent()) {
            logger.error("Product with name '{}' already exists", productResponse.getName());
            throw new ProductAlreadyExistsException(productResponse.getName(), "name");
        }

        Product product = new Product();
        product.setName(productResponse.getName());
        product.setPrice(productResponse.getPrice());
        product.setCategory(category);

        Product createdProduct = productRepository.save(product);
        logger.info("Product created successfully with ID: {}", createdProduct.getId());

        return responseMapper.toProductDTO(createdProduct);
    }

    /**
     * Update an existing product
     *
     * @param productResponse Updated product information
     * @param categoryId Category ID
     * @param productId  Product ID
     * @return Updated product as DTO
     */
    @Transactional
    public ProductResponse updateProduct(ProductResponse productResponse, Long categoryId, Long productId) {
        logger.info("Updating product ID: {} with new data", productId);

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new ProductNotFoundException(productId);
                });

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", categoryId);
                    return new CategoryNotFoundException(categoryId);
                });

        Optional<Product> nameCheck = productRepository.findByName(productResponse.getName());
        if (nameCheck != null && nameCheck.isPresent() && !nameCheck.get().getId().equals(productId)) {
            logger.error("Another product with name '{}' already exists", productResponse.getName());
            throw new ProductAlreadyExistsException(productResponse.getName(), "name");
        }

        existingProduct.setName(productResponse.getName());
        existingProduct.setPrice(productResponse.getPrice());
        existingProduct.setCategory(category);

        Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Product updated successfully: {}", updatedProduct.getId());

        return responseMapper.toProductDTO(updatedProduct);
    }

    /**
     * Delete a product
     *
     * @param productId Product ID
     */
    @Transactional
    public void deleteProduct(Long productId) {
        logger.info("Deleting product with ID: {}", productId);

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new ProductNotFoundException(productId);
                });

        productRepository.deleteById(productId);
        logger.info("Product deleted successfully: {}", productId);
    }
}
