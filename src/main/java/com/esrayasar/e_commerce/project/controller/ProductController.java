package com.esrayasar.e_commerce.project.controller;

import com.esrayasar.e_commerce.project.entity.Product;
import com.esrayasar.e_commerce.project.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api/products")
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController (IProductService productService){
        this.productService=productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product savedProduct=productService.saveProduct(product);
        return new  ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>>getAllProducts(){
        List<Product>products=productService.getAllProducts();
        return ResponseEntity.ok(products);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product>getProductById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(product->ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product>updateProduct(@PathVariable Long id,@RequestBody Product productDetails){
        Product updateProduct=productService.updateProduct(id,productDetails);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping
    public ResponseEntity<Product>deleteProduct(@PathVariable Long id){

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }



























}
