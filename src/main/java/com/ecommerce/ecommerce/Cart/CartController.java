package com.ecommerce.ecommerce.Cart;

import com.ecommerce.ecommerce.CartItem.CartItem;
import com.ecommerce.ecommerce.DTO.CartItemToAdd;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add-item")
    public ResponseEntity<?> addNewItemToCart(Principal connectUser, @RequestBody @Valid CartItemToAdd cartItem) {
        return ResponseEntity.ok(cartService.addNewItemToCart(cartItem, connectUser));
    }

    @PutMapping("/pay-items")
    public ResponseEntity<?> payItems(Principal connectUser, @RequestBody Set<Integer> itemsToPay) {
        return ResponseEntity.ok(cartService.payItems(connectUser, itemsToPay));
    }


    @GetMapping("")
    public Cart getCart(Principal connectedUser) {
        return cartService.getCart(connectedUser);
    }


}
