package com.ecommerce.ecommerce.Cart;

import com.ecommerce.ecommerce.CartItem.CartItem;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public Cart addNewItemToCart(Principal conectUser, @RequestBody CartItem cartItem) {
        return cartService.addNewItemToCart(cartItem, conectUser);

    }

    @GetMapping("")
    public Cart getCart(Principal connectedUser) {
        return cartService.getCart(connectedUser);
    }


}
