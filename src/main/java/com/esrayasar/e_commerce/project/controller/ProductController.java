package com.esrayasar.e_commerce.project.controller;

import com.esrayasar.e_commerce.project.dto.ProductCreateDTO;
import com.esrayasar.e_commerce.project.dto.ProductResponseDTO;
import com.esrayasar.e_commerce.project.dto.ProductUpdateDTO;
import com.esrayasar.e_commerce.project.entity.Category;
import com.esrayasar.e_commerce.project.entity.Product;
import com.esrayasar.e_commerce.project.service.ICategoryService;
import com.esrayasar.e_commerce.project.service.IProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/api/products")
public class ProductController {

    private final IProductService productService;
    private final ICategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(IProductService productService, ICategoryService categoryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }

        Category category = categoryService.getCategoryById(productCreateDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + productCreateDTO.getCategoryId()));

        Product product = modelMapper.map(productCreateDTO, Product.class);
        product.setCategory(category);

        Product savedProduct = productService.saveProduct(product);
        ProductResponseDTO productResponseDTO = modelMapper.map(savedProduct, ProductResponseDTO.class);

        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO productUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }

        Category category = categoryService.getCategoryById(productUpdateDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + productUpdateDTO.getCategoryId()));

        Product product = modelMapper.map(productUpdateDTO, Product.class);
        product.setId(id);
        product.setCategory(category);

        Product updatedProduct = productService.updateProduct(id, product);
        ProductResponseDTO productResponseDTO = modelMapper.map(updatedProduct, ProductResponseDTO.class);
        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDTO> productResponseDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(modelMapper.map(product, ProductResponseDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}