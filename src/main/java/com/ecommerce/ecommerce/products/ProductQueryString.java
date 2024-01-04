package com.ecommerce.ecommerce.products;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductQueryString {
    private String name;
    private Double price;
    private Integer providerId;
}
