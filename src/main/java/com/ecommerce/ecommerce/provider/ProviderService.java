package com.ecommerce.ecommerce.provider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public Provider addProvider(Provider provider){
        return this.providerRepository.save(provider);
    }

    public List<Provider> getAllProviders(){
        return this.providerRepository.findAll();
    }

    public Optional<Provider> getProviderById(Integer id){
        return this.providerRepository.findById(id);
    }

    public Optional<Provider> findByEmail(String email){
        return this.providerRepository.findByEmail(email);
    }
}
