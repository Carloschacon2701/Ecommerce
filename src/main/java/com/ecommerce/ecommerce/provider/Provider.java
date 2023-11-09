package com.ecommerce.ecommerce.provider;


import com.ecommerce.ecommerce.products.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Provider {

    @Id
    @GeneratedValue()
    private Long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "provider")
    @JsonIgnore
    private List<Product> products;


}
