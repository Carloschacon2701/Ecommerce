package com.ecommerce.ecommerce.products;

import com.ecommerce.ecommerce.User.User;
import com.ecommerce.ecommerce.provider.Provider;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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
    private Provider provider;

}
