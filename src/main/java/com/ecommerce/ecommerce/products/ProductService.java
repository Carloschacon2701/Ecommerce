package com.ecommerce.ecommerce.products;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService  {

    private final ProductRepository productRepository;
    private static final String UPLOAD_DIR =System.getProperty("user.dir") + "/src/main/resources/static/productImages/";

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return this.productRepository.findAll();
    }

    public Product getProductById(Integer id){
        return this.productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product with id " + id + " does not exist"));
    }

    public void addProduct(Product product){
         this.productRepository.save(product);
    }

    public Optional<Product> findByName(String name){
        return this.productRepository.findByName(name);
    }

    @Transactional
    public String uploadProductImage(MultipartFile file, Integer id) throws Exception {
        String FilePath = UPLOAD_DIR + "product_" + id + ".jpg";

        ArrayList<String> allowedTypes = new ArrayList<>(
                List.of("image/jpeg", "image/png", "image/jpg")
        );

        Product productOptional = productRepository.findById(id).orElseThrow(
                () -> new Exception("Product with id " + id + " does not exist")
        );

        if(file.isEmpty()){
            throw new Exception("Image cannot be empty");
        }

        if(!allowedTypes.contains(file.getContentType())){
            throw new Exception("Type " + file.getContentType() + " is not allowed");
        }

        file.transferTo(new File(FilePath));
        productOptional.setFilePath(FilePath);

        return "Image uploaded successfully for product with id " + id;
    }

    public byte[] getProductImage(Integer id) throws Exception {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new Exception("Product with id " + id + " does not exist")
        );

        String filePath = product.getFilePath();
        if (filePath == null || filePath.isEmpty()) {
            throw new Exception("No image found for product with id " + id);
        }

        byte[] fileContent = Files.readAllBytes(new File(filePath).toPath());

        return fileContent;
    }

}
