package com.ecommerce.ecommerce.CartItem;

import com.ecommerce.ecommerce.Cart.Cart;
import com.ecommerce.ecommerce.products.Product;
import jakarta.persistence.*;
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
    private Cart cart;

    @PostLoad
    public void calculateTotalPrice() {
        this.totalPrice = (long) (this.quantity * this.product.getPrice());
    }
}
