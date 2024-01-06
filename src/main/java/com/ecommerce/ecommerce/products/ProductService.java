package com.ecommerce.ecommerce.products;

import com.ecommerce.ecommerce.AWS.S3Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ecommerce.ecommerce.DTO.ProductToAdd;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService  {

    private final S3Service s3Service;
    private final ProductRepository productRepository;
    private final ProductDaoRepository productDaoRepository;
    private static final String UPLOAD_DIR =System.getProperty("user.dir") + "/src/main/resources/static/productImages/";


    public Page<Product> getAllProducts(
            ProductQueryString queries,
            Pageable pageable
    ){
        return this.productDaoRepository.findAllByQuery(queries, pageable);
    }

    public Product addProduct(ProductToAdd product){
        Optional<Product> existByName= productRepository.findByName(product.getName());

        if(existByName.isPresent())
            throw new RuntimeException("Product with name " + product.getName() + " already exists");

        Product newProduct = Product.builder()
                .name(product.getName())
                .price(product.getPrice())
                .provider(product.getProvider())
                .build();

       return this.productRepository.save(newProduct);
    }

    @Transactional
    public String uploadProductImage(MultipartFile file, Integer id) throws Exception {

        String uuid = UUID.randomUUID().toString() + "." + file.getContentType().split("/")[1];

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

        s3Service.uploadFile(uuid, file.getBytes());

        productOptional.setFilePath(uuid);

        return "Image uploaded successfully for product with id " + id;
    }

    public byte[] getProductImage(Integer id) throws Exception {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product with id " + id + " does not exist")
        );

        String filePath = product.getFilePath();
        if (filePath == null || filePath.isEmpty()) {
            throw new RuntimeException("No image found for product with id " + id);
        }

        return s3Service.getFile(filePath);
    }

}
