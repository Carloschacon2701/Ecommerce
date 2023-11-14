package com.ecommerce.ecommerce.products;

import com.ecommerce.ecommerce.User.User;
import com.ecommerce.ecommerce.provider.Provider;
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
    private Long id;
    private String name;
    private double price;

    @Lob
    @Column(name = "image", length = 1000, nullable = true)
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

}
