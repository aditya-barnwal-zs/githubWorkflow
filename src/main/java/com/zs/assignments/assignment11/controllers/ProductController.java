package com.zs.assignments.assignment11.controllers;

import com.zs.assignments.assignment11.dto.DTOMapper;
import com.zs.assignments.assignment11.dto.ProductDTO;
import com.zs.assignments.assignment11.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( path = "api/v1/product")
public class ProductController {

    ProductService productService;
    DTOMapper dtoMapper;

    @Autowired
    public ProductController(ProductService productService, DTOMapper dtoMapper) {
        this.productService = productService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public List<ProductDTO> getAllProduct(){
        return productService.getAllProducts();
    }

    @GetMapping(path = "/category/{id}")
    public List<ProductDTO> getProductsByCategoryId(@PathVariable Long id) {
        return productService.getAllProductsByCategoryId(id);
    }

    @PostMapping(path ="/create")
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO){
        return productService.createProduct(productDTO, productDTO.getCategoryId());
    }

    @PutMapping(path ="/update")
    public ProductDTO updateProduct(@Valid @RequestBody ProductDTO productDTO){
        return productService.updateProduct(productDTO, productDTO.getCategoryId(), productDTO.getId());
    }

    @DeleteMapping(path = "/{productId}")
    public ProductDTO deleteProduct(@PathVariable Long productId){
        return productService.deleteProduct(productId);
    }
}
