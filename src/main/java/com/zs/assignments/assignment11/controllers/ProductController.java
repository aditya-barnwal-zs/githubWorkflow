package com.zs.assignments.assignment11.controllers;

import com.zs.assignments.assignment11.dto.ResponseMapper;
import com.zs.assignments.assignment11.dto.ProductResponse;
import com.zs.assignments.assignment11.services.ProductService;
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
 * REST controller for managing products
 */
@RestController
@RequestMapping(path = "api/v1/products")
@Tag(name = "Product", description = "Product management API")
public class ProductController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    private final ProductService productService;
    private final ResponseMapper responseMapper;

    @Autowired
    public ProductController(ProductService productService, ResponseMapper responseMapper) {
        this.productService = productService;
        this.responseMapper = responseMapper;
    }

    /**
     * GET /api/v1/products : Get all products
     *
     * @return the ResponseEntity with status 200 (OK) and the list of products
     */
    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        logger.info("REST request to get all Products");
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * POST /api/v1/products : Create a new product
     *
     * @param productResponse the product to create
     * @return the ResponseEntity with status 201 (Created) and the new product
     */
    @Operation(summary = "Create a product", description = "Creates a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "409", description = "Product already exists")
    })
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Parameter(description = "Product to create", required = true, schema = @Schema(implementation = ProductResponse.class))
            @Valid @RequestBody ProductResponse productResponse) {
        logger.info("REST request to create Product : {}", productResponse.getName());
        ProductResponse result = productService.createProduct(productResponse, productResponse.getCategoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * PUT /api/v1/products : Update an existing product
     *
     * @param productResponse the product to update
     * @return the ResponseEntity with status 200 (OK) and the updated product
     */
    @Operation(summary = "Update a product", description = "Updates an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Product or Category not found"),
            @ApiResponse(responseCode = "409", description = "Product name already in use")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "Product to update", required = true, schema = @Schema(implementation = ProductResponse.class))
            @PathVariable Long id, @Valid @RequestBody ProductResponse productResponse) {
        logger.info("REST request to update Product : {}", id);
        ProductResponse result = productService.updateProduct(productResponse, productResponse.getCategoryId(), id);
        return ResponseEntity.ok(result);
    }

    /**
     * DELETE /api/v1/products/{productId} : Delete a product
     *
     * @param id the id of the product to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @Operation(summary = "Delete a product", description = "Deletes an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long id) {
        logger.info("REST request to delete Product : {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
