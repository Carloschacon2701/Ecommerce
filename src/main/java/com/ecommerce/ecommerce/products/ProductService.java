package com.ecommerce.ecommerce.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService  {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return this.productRepository.findAll();
    }

    public Product getProductById(Long id){
        return this.productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product with id " + id + " does not exist"));
    }

    public Product addProduct(Product product){
        return this.productRepository.save(product);
    }

    public Optional<Product> findByName(String name){
        return this.productRepository.findByName(name);
    }

}
