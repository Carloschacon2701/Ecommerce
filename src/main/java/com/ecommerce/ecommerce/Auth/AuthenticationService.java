package com.ecommerce.ecommerce.Auth;

import com.ecommerce.ecommerce.AWS.CognitoService;
import com.ecommerce.ecommerce.DTO.RegisterRequest;
import com.ecommerce.ecommerce.DTO.TokenRequest;
import com.ecommerce.ecommerce.Role.RoleRepository;
import com.ecommerce.ecommerce.User.User;
import com.ecommerce.ecommerce.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.*;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CognitoService cognitoService;


    public User signUp(RegisterRequest request) {

        var user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .bankAccount(request.getBankAccount())
                .role(roleRepository.findByRoleId(request.getRoleID()).get())
                .build();

        var savedUser = userRepository.save(user);

        cognitoService.CognitoSignUp(request.getEmail(), request.getPassword());

       return savedUser;

    }

    public AuthenticationResponse initiateAuth(AuthRequest request) {
        AuthenticationResultType response = cognitoService.CognitoSignIn(request.getEmail(), request.getPassword());

        return AuthenticationResponse.builder()
                .refreshToken(response.refreshToken())
                .accessToken(response.accessToken())
                .build();

    }



}
