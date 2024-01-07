package com.ecommerce.ecommerce.products;

import com.ecommerce.ecommerce.Comments.Comment;
import com.ecommerce.ecommerce.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@Builder
public class Product {

    @Id
    @GeneratedValue()
    private Integer id;

    @Column(unique = true)
    private String name;

    private double price;

    private String FilePath;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    @JsonBackReference
    private User provider;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<Comment> comments;

}
