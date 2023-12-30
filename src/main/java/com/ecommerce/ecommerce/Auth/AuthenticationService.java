package com.ecommerce.ecommerce.Auth;

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

    private final CognitoIdentityProviderClient cognitoIdentityProviderClient;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Value("${application.security.cognito.clientID}")
    private String clientID;

    @Value("${application.security.cognito.poolID}")
    private String userPoolId;


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

        AttributeType userAttrs = AttributeType.builder()
                .name("email")
                .value(request.getEmail())
                .build();

        List<AttributeType> userAttrsList = new ArrayList<>();
        userAttrsList.add(userAttrs);
        try {
            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .userAttributes(userAttrsList)
                    .username(request.getEmail())
                    .clientId(clientID)
                    .password(request.getPassword())
                    .build();

            var result = cognitoIdentityProviderClient.signUp(signUpRequest);

            return savedUser;

        } catch(CognitoIdentityProviderException e) {
            throw new RuntimeException(e.awsErrorDetails().errorMessage());
        }

    }

    public AuthenticationResponse initiateAuth(AuthRequest request) {
        try {
            Map<String,String> authParameters = new HashMap<>();
            authParameters.put("USERNAME", request.getEmail());
            authParameters.put("PASSWORD", request.getPassword());

            AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                    .clientId(clientID)
                    .userPoolId(userPoolId)
                    .authParameters(authParameters)
                    .authFlow(AuthFlowType.ADMIN_USER_PASSWORD_AUTH)
                    .build();

            AuthenticationResultType response = cognitoIdentityProviderClient.adminInitiateAuth(authRequest).authenticationResult();

            return AuthenticationResponse.builder()
                    .refreshToken(response.refreshToken())
                    .accessToken(response.accessToken())
                    .build();

        } catch(CognitoIdentityProviderException e) {
            throw new RuntimeException(e.awsErrorDetails().errorMessage());
        }

    }



}
