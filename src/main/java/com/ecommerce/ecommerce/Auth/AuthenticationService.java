package com.ecommerce.ecommerce.Auth;

import com.ecommerce.ecommerce.Client.Client;
import com.ecommerce.ecommerce.Client.ClientRepository;
import com.ecommerce.ecommerce.JWT.JWTService;
import com.ecommerce.ecommerce.Role.Role;
import com.ecommerce.ecommerce.Role.RoleRepository;
import com.ecommerce.ecommerce.User.User;
import com.ecommerce.ecommerce.provider.Provider;
import com.ecommerce.ecommerce.provider.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ClientRepository clientRepository;
    private final ProviderRepository providerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public Role checkRole(Integer role_id){
        return roleRepository.findById(role_id).orElseThrow(
                () -> new RuntimeException("Role not found")
        );
    }

    public AuthenticationResponse register(RegisterRequest request){
        Role role = checkRole(request.getRoleID());
        User user;

        if(request instanceof ClientRegisterRequest clientRequest){
             user = Client
                    .builder()
                    .email(clientRequest.getEmail())
                     .lastName(clientRequest.getLastName())
                        .firstName(clientRequest.getFirstName())
                    .password(passwordEncoder.encode(clientRequest.getPassword()))
                    .role(role)
                    .phoneNumber(clientRequest.getPhoneNumber())
                    .address(clientRequest.getAddress())
                    .build();
            clientRepository.save((Client) user);
        }
        else if(request instanceof ProviderRegisterRequest providerRequest){
             user =  Provider
                    .builder()
                     .firstName(providerRequest.getFirstName())
                        .lastName(providerRequest.getLastName())
                    .email(providerRequest.getEmail())
                    .password(passwordEncoder.encode(providerRequest.getPassword()))
                    .role(role)
                     .bank_account(providerRequest.getBank_account())
                    .build();
            providerRepository.save((Provider) user);
        }else{
            throw new RuntimeException("Invalid request");
        }

        var JwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .authenticationToken(JwtToken)
                .build();
    }

    public AuthenticationResponse auth(AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        User user;

        Optional<Client> clientOptional = clientRepository.findByEmail(request.getEmail());
        if(clientOptional.isPresent()){
            user = clientOptional.get();
        }else{
            Optional<Provider> providerOptional = providerRepository.findByEmail(request.getEmail());
            if(providerOptional.isPresent()){
                user = providerOptional.get();
            }else{
                throw new RuntimeException("User not found");
            }
        }

        var JwtToken = jwtService.generateToken(user);

        return  AuthenticationResponse
                .builder()
                .authenticationToken(JwtToken)
                .build();

    }



}
