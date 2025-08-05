package com.esrayasar.e_commerce.project.service;


import com.esrayasar.e_commerce.project.entity.Product;
import com.esrayasar.e_commerce.project.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    @Autowired ProductServiceImpl(IProductRepository  productRepository){
        this.productRepository=productRepository;
    }

    @Override
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }
    @Override
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));


        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setStockQuantity(productDetails.getStockQuantity());


        return productRepository.save(existingProduct);

    }

    @Override
    public  void deleteProduct(Long id){

        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);

    }










}
