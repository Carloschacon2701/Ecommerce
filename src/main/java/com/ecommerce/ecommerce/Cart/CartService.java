package com.ecommerce.ecommerce.Cart;

import com.ecommerce.ecommerce.CartItem.CartItem;
import com.ecommerce.ecommerce.CartItem.CartItemRepository;
import com.ecommerce.ecommerce.Client.Client;
import com.ecommerce.ecommerce.Client.ClientRepository;
import com.ecommerce.ecommerce.DTO.CartItemToAdd;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Set;

@Service
public class CartService {
    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ClientRepository clientRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ClientRepository clientRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Cart addNewItemToCart(CartItemToAdd cartItemToAdd, Principal connectedUser) {
        var user = (Client) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Client client = clientRepository.findById(user.getClientId()).orElseThrow();

        var cart = cartRepository.findByClient(user).orElseGet(() -> {
            Cart newCart =  Cart.builder().client(client).build();
            cartRepository.save(newCart);
            return newCart;
        });

        System.out.println(cart);


         var cartItem = CartItem.builder()
            .product(cartItemToAdd.getProduct())
            .quantity(cartItemToAdd.getQuantity())
            .build();

        cartItem.setCart(cart);

        cartItemRepository.save(cartItem);

        return cart;
    }

    public Cart getCart(Principal connectedUser) {
        var user = (Client) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return cartRepository.findByClient(user).orElseThrow();
    }

}
