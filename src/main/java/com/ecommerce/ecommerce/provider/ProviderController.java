package com.ecommerce.ecommerce.provider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/providers")
public class ProviderController {

    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @RequestMapping(path = "/all")
    @GetMapping()
    public List<Provider> getProviders(){
        return this.providerService.getAllProviders();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getProviderById(@PathVariable("id") Long id) {
        Optional<Provider> providerOptional = providerService.getProviderById(id);

        if (providerOptional.isPresent()) {
            Provider provider = providerOptional.get();
            return new ResponseEntity<>(provider, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Provider not found", HttpStatus.NOT_FOUND);
        }
    }

}
