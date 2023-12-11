package com.ecommerce.ecommerce.Cart;

import com.ecommerce.ecommerce.CartItem.CartItem;
import com.ecommerce.ecommerce.CartItem.CartItemRepository;
import com.ecommerce.ecommerce.DTO.CartItemToAdd;
import com.ecommerce.ecommerce.Status.Status;
import com.ecommerce.ecommerce.User.User;
import com.ecommerce.ecommerce.User.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
public class CartService {
    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository){
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CartItem addNewItemToCart(CartItemToAdd cartItemToAdd, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        var UserFromDB = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new RuntimeException("Error adding carItem. User not found")
        );

        var cart = cartRepository.findByClient(UserFromDB).orElseGet(() -> {
            Cart newCart =  Cart.builder().client(UserFromDB).build();
            return cartRepository.save(newCart);
        });

         var cartItem = CartItem.builder()
            .product(cartItemToAdd.getProduct())
            .quantity(cartItemToAdd.getQuantity()).status(Status.PENDING)
            .build();

        cartItem.setCart(cart);

        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> payItems(Principal connectedUser, List<Integer> itemsToPay ) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        var BDUser = userRepository.findByEmail(user.getEmail()).orElseThrow();

        var cart = cartRepository.findByClient(BDUser).orElseThrow();

        var cartItems = cartItemRepository.findAllByCart(cart);

        cartItems.forEach(cartItem -> {
            if (itemsToPay.contains(cartItem.getItemId())) {
                cartItem.setStatus(Status.PAID);
            }
        });

        return cartItemRepository.saveAll(cartItems);

    }



    public Cart getCart(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return cartRepository.findByClient(user).orElseThrow();
    }

}
