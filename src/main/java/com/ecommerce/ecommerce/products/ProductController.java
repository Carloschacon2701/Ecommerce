package com.ecommerce.ecommerce.products;

import com.ecommerce.ecommerce.provider.Provider;
import com.ecommerce.ecommerce.provider.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProviderService providerService;

    @Autowired
    public ProductController(ProductService productService, ProviderService providerService) {
        this.productService = productService;
        this.providerService = providerService;
    }

    @GetMapping
    public List<Product> getProducts(){
        return  this.productService.getAllProducts();
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        Optional<Product> existByName= productService.findByName(product.getName());
        Optional<Provider> existProvider = providerService.getProviderById(product.getProvider().getProviderId());

        if(!existProvider.isPresent())
            return ResponseEntity.badRequest().body("Provider with id " + product.getProvider().getProviderId() + " does not exist");

        if(existByName.isPresent())
            return ResponseEntity.badRequest().body("Product with name " + product.getName() + " already exists");
        if(product.getName() == null || product.getName().isEmpty())
            return ResponseEntity.badRequest().body("Product name cannot be null or empty");
        if(product.getPrice() <= 0) {
           return ResponseEntity.badRequest().body("Product price cannot be less than or equal to 0");
        }

        existProvider.get().getProducts().add(product);

        productService.addProduct(product);
        return ResponseEntity.ok("Product added successfully");
    }

    @PutMapping(path = "/{productID}/product-image")
    public ResponseEntity<?> uploadProductImage(@RequestParam("image") MultipartFile file, @PathVariable Integer productID)  {
        try{
            String uploadImage = productService.uploadProductImage(file,productID);
            return ResponseEntity.ok("Image uploaded successfully");

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping(path = "/{id}/image")
    public ResponseEntity<?> getProductById(@PathVariable("id") Integer id) throws Exception {
        Product productOptional = this.productService.getProductById(id);

        if(productOptional.getFilePath() == null){
            return ResponseEntity.badRequest().body("Image not found");
        }

        byte[] imageData = this.productService.getProductImage(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);

    }

}
