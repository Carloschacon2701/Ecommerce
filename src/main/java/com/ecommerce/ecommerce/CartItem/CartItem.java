package com.ecommerce.ecommerce.CartItem;

import com.ecommerce.ecommerce.Cart.Cart;
import com.ecommerce.ecommerce.products.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue
    private Integer itemId;

    private Integer quantity;

    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @PostLoad
    public void calculateTotalPrice() {
        this.totalPrice = (long) (this.quantity * this.product.getPrice());
    }
}
