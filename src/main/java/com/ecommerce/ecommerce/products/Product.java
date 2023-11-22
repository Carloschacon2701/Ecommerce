package com.ecommerce.ecommerce.products;

import com.ecommerce.ecommerce.provider.Provider;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue()
    private Integer id;
    private String name;
    private double price;

    private String FilePath;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    @JsonBackReference
    private Provider provider;

}
