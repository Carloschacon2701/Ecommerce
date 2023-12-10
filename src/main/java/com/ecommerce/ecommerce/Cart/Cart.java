package com.ecommerce.ecommerce.Cart;

import com.ecommerce.ecommerce.CartItem.CartItem;
import com.ecommerce.ecommerce.User.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private User client;


    @OneToMany(mappedBy = "cart")
    @JsonManagedReference
    private List<CartItem> cartItems;

    @PostLoad
    public void calculateTotalAmountToPay() {
        this.TotalAmountToPay = this.cartItems.stream().mapToLong(CartItem::getTotalPrice).sum();
    }
}
