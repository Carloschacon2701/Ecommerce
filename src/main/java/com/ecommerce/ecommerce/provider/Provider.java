package com.ecommerce.ecommerce.provider;


import com.ecommerce.ecommerce.Token.Token;
import com.ecommerce.ecommerce.User.User;
import com.ecommerce.ecommerce.products.Product;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "provider")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class Provider extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "provider_id")
    private Integer providerId;

    private Integer bank_account;

    @OneToMany(mappedBy = "provider")
    @JsonManagedReference
    private List<Product> products;

    @OneToMany(mappedBy = "provider")
    private List<Token> tokens;
}
