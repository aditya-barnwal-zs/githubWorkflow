package com.zs.assignments.assignment11.services;

import com.zs.assignments.assignment11.dto.DTOMapper;
import com.zs.assignments.assignment11.dto.ProductDTO;
import com.zs.assignments.assignment11.entity.Category;
import com.zs.assignments.assignment11.entity.Product;
import com.zs.assignments.assignment11.repository.CategoryRepository;
import com.zs.assignments.assignment11.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DTOMapper dtoMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, DTOMapper dtoMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<ProductDTO> getAllProducts(){
        return dtoMapper.toProductDTOs(productRepository.findAll());
    }

    public List<ProductDTO> getAllProductsByCategoryId(Long id){
        return dtoMapper.toProductDTOs(productRepository.findByCategoryId(id));
    }

    public ProductDTO createProduct(ProductDTO productDTO, Long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new EntityNotFoundException("Category not found of Id "+categoryId));

        Product existingProduct = productRepository.findByName(productDTO.getName());
        if (existingProduct != null) {
            throw new IllegalStateException("Product with name '" + productDTO.getName() + "' already exists");
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);

        Product createdProduct = productRepository.save(product);
        return dtoMapper.toProductDTO(createdProduct);
    }

    public ProductDTO updateProduct(ProductDTO productDTO, Long categoryId, Long productId){
        Product existingProduct = productRepository.findById(productId).orElseThrow(()-> new EntityNotFoundException("Product not found of Id "+productId));

        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new EntityNotFoundException("Category not found of Id "+categoryId));

        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setCategory(category);

        Product createdProduct = productRepository.save(existingProduct);
        return dtoMapper.toProductDTO(createdProduct);
    }

    public ProductDTO deleteProduct(Long productId){
        Product existingProduct = productRepository.findById(productId).orElseThrow(()-> new EntityNotFoundException("Product not found of Id "+productId));

        productRepository.deleteById(productId);
        return dtoMapper.toProductDTO(existingProduct);
    }
}
