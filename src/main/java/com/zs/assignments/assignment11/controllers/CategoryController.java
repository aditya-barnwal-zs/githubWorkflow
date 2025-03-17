package com.zs.assignments.assignment11.controllers;

import com.zs.assignments.assignment11.dto.CategoryDTO;
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
@RequestMapping(path = "api/v1/category")
@Tag(name = "Category", description = "Category management API")
public class CategoryController {

    private static final Logger logger = LogManager.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * GET /api/v1/category : Get all categories
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categories
     */
    @Operation(summary = "Get all categories", description = "Returns a list of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        logger.info("REST request to get all Categories");
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * GET /api/v1/category/{id} : Get a specific category by ID
     *
     * @param id the ID of the category to retrieve
     * @return the ResponseEntity with status 200 (OK) and the category
     */
    @Operation(summary = "Get category by ID", description = "Returns a specific category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved category",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(
            @Parameter(description = "Category ID", required = true)
            @PathVariable Long id) {
        logger.info("REST request to get Category : {}", id);
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    /**
     * POST /api/v1/category/create : Create a new category
     *
     * @param categoryDTO the category to create
     * @return the ResponseEntity with status 201 (Created) and the new category
     */
    @Operation(summary = "Create a category", description = "Creates a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping(path = "/create")
    public ResponseEntity<CategoryDTO> createCategory(
            @Parameter(description = "Category to create", required = true, schema = @Schema(implementation = CategoryDTO.class))
            @Valid @RequestBody CategoryDTO categoryDTO) {
        logger.info("REST request to create Category : {}", categoryDTO.getName());
        CategoryDTO result = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
