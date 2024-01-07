package com.ecommerce.ecommerce.DTO;

import com.ecommerce.ecommerce.products.Product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentCreation {

    @NotNull(message = "Product id is required")
    private Product product;

    @NotNull(message = "Text is required")
    @NotEmpty(message = "Text is required")
    private String text;
}
