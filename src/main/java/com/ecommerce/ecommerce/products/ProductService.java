package com.ecommerce.ecommerce.products;

import jakarta.transaction.Transactional;
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

@Service
public class ProductService  {

    private final ProductRepository productRepository;
    private final ProductDaoRepository productDaoRepository;
    private static final String UPLOAD_DIR =System.getProperty("user.dir") + "/src/main/resources/static/productImages/";

    @Autowired
    public ProductService(ProductRepository productRepository, ProductDaoRepository productDaoRepository) {
        this.productRepository = productRepository;
        this.productDaoRepository = productDaoRepository;
    }

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
                () -> new RuntimeException("Product with id " + id + " does not exist")
        );

        String filePath = product.getFilePath();
        if (filePath == null || filePath.isEmpty()) {
            throw new RuntimeException("No image found for product with id " + id);
        }
        return Files.readAllBytes(new File(filePath).toPath());
    }

}
