package com.ecommerce.ecommerce.products;

import com.ecommerce.ecommerce.provider.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

     Optional<Product> findByName(String name);
}
