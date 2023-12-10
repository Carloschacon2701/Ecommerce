package com.ecommerce.ecommerce.CartItem;

import com.ecommerce.ecommerce.Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository< CartItem, Integer> {
    List<CartItem> findAllByCart(Cart cart);
}
