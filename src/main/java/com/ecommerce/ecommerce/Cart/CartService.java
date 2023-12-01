package com.ecommerce.ecommerce.Cart;

import com.ecommerce.ecommerce.CartItem.CartItem;
import com.ecommerce.ecommerce.CartItem.CartItemRepository;
import com.ecommerce.ecommerce.Client.Client;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class CartService {
    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public Cart addNewItemToCart(CartItem cartItem, Principal connectedUser) {
        var user = (Client) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        var cart = cartRepository.findByClient(user).orElseGet(() -> {
            var newCart = Cart.builder().client(user).build();
            cartRepository.save(newCart);
            return newCart;
        });

        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);

        return cart;
    }

}
