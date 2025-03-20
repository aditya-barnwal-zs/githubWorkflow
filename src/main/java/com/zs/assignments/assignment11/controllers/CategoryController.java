package com.zs.assignments.assignment11.controllers;

import com.zs.assignments.assignment11.dto.CategoryResponse;
import com.zs.assignments.assignment11.dto.ProductResponse;
import com.zs.assignments.assignment11.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing categories
 */
@RestController
@RequestMapping(path = "api/v1/categories")
@Tag(name = "Category", description = "Category management API")
public class CategoryController {

    private static final Logger logger = LogManager.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * GET /api/v1/categories : Get all categories
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categories
     */
    @Operation(summary = "Get all categories", description = "Returns a list of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        logger.info("REST request to get all Categories");
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * GET /api/v1/categories/{id} : Get a specific category by ID
     *
     * @param id the ID of the category to retrieve
     * @return the ResponseEntity with status 200 (OK) and the category
     */
    @Operation(summary = "Get category by ID", description = "Returns a specific category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved category",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @Parameter(description = "Category ID", required = true)
            @PathVariable Long id) {
        logger.info("REST request to get Category : {}", id);
        CategoryResponse category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    /**
     * GET /api/v1/categories/{id}/products : Get all products in a category
     *
     * @param id Category ID
     * @return the ResponseEntity with status 200 (OK) and the list of products
     */
    @Operation(summary = "Get products by category", description = "Returns all products in a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping(path = "/{id}/products")
    public ResponseEntity<List<ProductResponse>> getProductByCategoryId(
            @Parameter(description = "Category ID", required = true)
            @PathVariable Long id) {
        logger.info("REST request to get all Products for category ID: {}", id);
        List<ProductResponse> products = categoryService.getAllProductsByCategoryId(id);
        return ResponseEntity.ok(products);
    }

    /**
     * POST /api/v1/categories : Create a new category
     *
     * @param categoryResponse the category to create
     * @return the ResponseEntity with status 201 (Created) and the new category
     */
    @Operation(summary = "Create a category", description = "Creates a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "409", description = "Category Already Exists")
    })
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Parameter(description = "Category to create", required = true, schema = @Schema(implementation = CategoryResponse.class))
            @Valid @RequestBody CategoryResponse categoryResponse) {
        logger.info("REST request to create Category : {}", categoryResponse.getName());
        CategoryResponse result = categoryService.createCategory(categoryResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * DELETE /api/v1/categories/{id} : Delete a category
     *
     * @param id the id of the product to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @Operation(summary = "Delete a category", description = "Deletes an existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "Category ID", required = true)
            @PathVariable Long id) {
        logger.info("REST request to delete Category : {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
