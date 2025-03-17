package com.zs.assignments.assignment11.services;

import com.zs.assignments.assignment11.dto.DTOMapper;
import com.zs.assignments.assignment11.dto.ProductDTO;
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
    private final DTOMapper dtoMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, DTOMapper dtoMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Get all products
     *
     * @return List of all products as DTOs
     */
    public List<ProductDTO> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        logger.debug("Found {} products", products.size());
        return dtoMapper.toProductDTOs(products);
    }

    /**
     * Get all products in a specific category
     *
     * @param id Category ID
     * @return List of products in the specified category as DTOs
     */
    public List<ProductDTO> getAllProductsByCategoryId(Long id) {
        logger.info("Fetching all products for category ID: {}", id);
        if (!categoryRepository.existsById(id)) {
            logger.error("Category not found with ID: {}", id);
            throw new CategoryNotFoundException(id);
        }

        List<Product> products = productRepository.findByCategoryId(id);
        logger.debug("Found {} products for category ID: {}", products.size(), id);
        return dtoMapper.toProductDTOs(products);
    }

    /**
     * Create a new product
     *
     * @param productDTO Product information
     * @param categoryId Category ID
     * @return Created product as DTO
     */
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO, Long categoryId) {
        logger.info("Creating new product: {} in category ID: {}", productDTO.getName(), categoryId);

        // Check if category exists
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", categoryId);
                    return new CategoryNotFoundException(categoryId);
                });

        Optional<Product> existingProduct = productRepository.findByName(productDTO.getName());
        if (existingProduct.isPresent()) {
            logger.error("Product with name '{}' already exists", productDTO.getName());
            throw new ProductAlreadyExistsException(productDTO.getName(), "name");
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);

        Product createdProduct = productRepository.save(product);
        logger.info("Product created successfully with ID: {}", createdProduct.getId());

        return dtoMapper.toProductDTO(createdProduct);
    }

    /**
     * Update an existing product
     *
     * @param productDTO Updated product information
     * @param categoryId Category ID
     * @param productId  Product ID
     * @return Updated product as DTO
     */
    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO, Long categoryId, Long productId) {
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

        Optional<Product> nameCheck = productRepository.findByName(productDTO.getName());
        if (nameCheck.isPresent() && !nameCheck.get().getId().equals(productId)) {
            logger.error("Another product with name '{}' already exists", productDTO.getName());
            throw new ProductAlreadyExistsException(productDTO.getName(), "name");
        }

        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setCategory(category);

        Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Product updated successfully: {}", updatedProduct.getId());

        return dtoMapper.toProductDTO(updatedProduct);
    }

    /**
     * Delete a product
     *
     * @param productId Product ID
     * @return The deleted product as DTO
     */
    @Transactional
    public ProductDTO deleteProduct(Long productId) {
        logger.info("Deleting product with ID: {}", productId);

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("Product not found with ID: {}", productId);
                    return new ProductNotFoundException(productId);
                });

        productRepository.deleteById(productId);
        logger.info("Product deleted successfully: {}", productId);

        return dtoMapper.toProductDTO(existingProduct);
    }
}
