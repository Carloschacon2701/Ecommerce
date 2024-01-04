package com.ecommerce.ecommerce.products;

import com.ecommerce.ecommerce.DTO.ProductToAdd;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {

    private final ProductService productService;



    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer providerId,
            @PageableDefault(size = 5, page = 0)
            Pageable pageable
    ){
        ProductQueryString queries = new ProductQueryString(name, price, providerId);
        return new ResponseEntity<>(
                this.productService.getAllProducts(queries, pageable),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductToAdd product){
        return new ResponseEntity<>(
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
