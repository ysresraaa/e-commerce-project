package com.esrayasar.e_commerce.project.service;

import com.esrayasar.e_commerce.project.entity.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Product updateProduct(Long id,Product productDetails);

    void deleteProduct(Long id);







}
