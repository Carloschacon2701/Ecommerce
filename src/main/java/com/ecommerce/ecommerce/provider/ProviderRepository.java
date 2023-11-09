package com.ecommerce.ecommerce.provider;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

    public Optional<Provider> findByEmail(String email);
}
