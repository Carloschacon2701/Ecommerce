package com.ecommerce.ecommerce.DTO;

import com.ecommerce.ecommerce.User.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductToAdd {

    @NotNull(message = "Provider is required")
    private User provider;

    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name is required")
    private String name;


    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private double price;

}
