package com.ecommerce.ecommerce.Cart;

import com.ecommerce.ecommerce.CartItem.CartItem;
import com.ecommerce.ecommerce.Client.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Cart {

    @Id
    @GeneratedValue
    private Integer cartId;

    private Long TotalAmountToPay;

    @OneToOne(mappedBy = "cart")
    @JoinColumn(name = "client_id")
    private Client client;


    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;

    @PostLoad
    public void calculateTotalAmountToPay() {
        this.TotalAmountToPay = this.cartItems.stream().mapToLong(CartItem::getTotalPrice).sum();
    }
}
