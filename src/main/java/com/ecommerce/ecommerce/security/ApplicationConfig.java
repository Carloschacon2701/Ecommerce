package com.ecommerce.ecommerce.security;

import com.ecommerce.ecommerce.Client.Client;
import com.ecommerce.ecommerce.Client.ClientRepository;
import com.ecommerce.ecommerce.provider.Provider;
import com.ecommerce.ecommerce.provider.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final ClientRepository clientRepository;

    private final ProviderRepository providerRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            Optional<Client> clientOptional = this.clientRepository.findByEmail(username);
            if (clientOptional.isPresent()) {
                return clientOptional.get();
            } else {
                Optional<Provider> providerOptional = this.providerRepository.findByEmail(username);
                if (providerOptional.isPresent()) {
                    return providerOptional.get();
                } else {
                    throw new UsernameNotFoundException("User not found");
                }
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
