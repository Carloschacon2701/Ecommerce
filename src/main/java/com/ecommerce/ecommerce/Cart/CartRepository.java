package com.ecommerce.ecommerce.Cart;

import com.ecommerce.ecommerce.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByClient (User client);
}
