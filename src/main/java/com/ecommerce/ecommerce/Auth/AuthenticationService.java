package com.ecommerce.ecommerce.Auth;

import com.ecommerce.ecommerce.DTO.RegisterRequest;
import com.ecommerce.ecommerce.JWT.JWTService;
import com.ecommerce.ecommerce.Role.RoleRepository;
import com.ecommerce.ecommerce.Token.Token;
import com.ecommerce.ecommerce.Token.TokenRepository;
import com.ecommerce.ecommerce.Token.TokenType;
import com.ecommerce.ecommerce.User.User;
import com.ecommerce.ecommerce.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request, Integer Role_id){
        var user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .bankAccount(request.getBankAccount())
                .role(roleRepository.findByRoleId(Role_id).get())
                .build();

       var savedUser = userRepository.save(user);

        var JwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, JwtToken);

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

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        var JwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, JwtToken);

        return  AuthenticationResponse
                .builder()
                .authenticationToken(JwtToken)
                .build();

    }

    private void saveUserToken(User user, String JwtToken) {
        var token = Token.builder()
                .user(user)
                .token(JwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .expired(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var tokens = tokenRepository.findAllValidTokensByUser(user.getId());

        if(tokens.isEmpty()) return;

        tokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });

        tokenRepository.saveAll(tokens);
    }



}
