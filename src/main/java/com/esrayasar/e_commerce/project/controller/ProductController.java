package com.esrayasar.e_commerce.project.controller;

import com.esrayasar.e_commerce.project.dto.ProductCreateDTO;
import com.esrayasar.e_commerce.project.dto.ProductResponseDTO;
import com.esrayasar.e_commerce.project.dto.ProductUpdateDTO;
import com.esrayasar.e_commerce.project.entity.Product;
import com.esrayasar.e_commerce.project.service.IProductService;
import jakarta.validation.Valid;
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

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            throw new IllegalArgumentException(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }

        Product savedProduct = convertToEntity(productCreateDTO);
        Product savedProductResult = productService.saveProduct(savedProduct);
        return new ResponseEntity<>(convertToResponseDTO(savedProductResult), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDTO> productResponseDTOs = products.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(convertToResponseDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO productUpdateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            throw new IllegalArgumentException(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }

        Product updatedProduct = convertToEntity(productUpdateDTO); // DTO'yu entity'ye çevir
        updatedProduct.setId(id); // ID'yi ayarla
        Product updatedProductResult = productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(convertToResponseDTO(updatedProductResult));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    private Product convertToEntity(ProductCreateDTO productCreateDTO) {
        Product product = new Product();
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setStockQuantity(productCreateDTO.getStockQuantity());
        return product;
    }

    private Product convertToEntity(ProductUpdateDTO productUpdateDTO) {
        Product product = new Product();
        product.setName(productUpdateDTO.getName());
        product.setDescription(productUpdateDTO.getDescription());
        product.setPrice(productUpdateDTO.getPrice());
        product.setStockQuantity(productUpdateDTO.getStockQuantity());
        return product;
    }



    private ProductResponseDTO convertToResponseDTO(Product product) {
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setStockQuantity(product.getStockQuantity());
        return responseDTO;
    }
}