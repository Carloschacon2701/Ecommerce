package com.ecommerce.ecommerce.User;
import com.ecommerce.ecommerce.Client.Client;
import com.ecommerce.ecommerce.Client.ClientRepository;
import com.ecommerce.ecommerce.provider.Provider;
import com.ecommerce.ecommerce.provider.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  ClientRepository clientRepository;

    @Autowired
    private ProviderRepository providerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> clientOptional = clientRepository.findByEmail(username);

        if(clientOptional.isPresent()){
            return clientOptional.get();
        }else{
            Optional<Provider> providerOptional = providerRepository.findByEmail(username);

            if(providerOptional.isPresent()){
                return providerOptional.get();
            }else{
                throw new UsernameNotFoundException("User not found");
            }
        }
    }
}
