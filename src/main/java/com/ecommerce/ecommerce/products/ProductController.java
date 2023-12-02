package com.ecommerce.ecommerce.products;

import com.ecommerce.ecommerce.DTO.ProductToAdd;
import com.ecommerce.ecommerce.provider.Provider;
import com.ecommerce.ecommerce.provider.ProviderService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductToAdd product){
        return new ResponseEntity(
                productService.addProduct(product),
                HttpStatus.CREATED
        );

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

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(this.productService.getProductImage(id));

    }

}
