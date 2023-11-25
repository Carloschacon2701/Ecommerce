package com.ecommerce.ecommerce.Purchase;


import com.ecommerce.ecommerce.Client.Client;
import com.ecommerce.ecommerce.Status.Status;
import com.ecommerce.ecommerce.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue
    private Integer purchaseId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private Double TotalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Date date;
}
