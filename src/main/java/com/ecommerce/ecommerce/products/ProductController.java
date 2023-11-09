package com.ecommerce.ecommerce.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts(){
        return  this.productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        Optional<Product> existByName= productService.findByName(product.getName());
        if(existByName.isPresent())
            return ResponseEntity.badRequest().body("Product with name " + product.getName() + " already exists");
        if(product.getName() == null || product.getName().isEmpty())
            return ResponseEntity.badRequest().body("Product name cannot be null or empty");
        if(product.getPrice() <= 0) {
           return ResponseEntity.badRequest().body("Product price cannot be less than or equal to 0");
        }

        productService.addProduct(product);
        return ResponseEntity.ok("Product added successfully");
    }

    @PostMapping(path = "/product-image")
    public ResponseEntity<?> uploadProductImage(@RequestBody MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        return ResponseEntity.ok("Image uploaded successfully");

    }

}
