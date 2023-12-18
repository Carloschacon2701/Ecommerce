package com.ecommerce.ecommerce.Auth;

import com.ecommerce.ecommerce.DTO.RegisterRequest;
import com.ecommerce.ecommerce.JWT.JWTService;
import com.ecommerce.ecommerce.Role.RoleRepository;
import com.ecommerce.ecommerce.Token.Token;
import com.ecommerce.ecommerce.Token.TokenRepository;
import com.ecommerce.ecommerce.Token.TokenType;
import com.ecommerce.ecommerce.User.User;
import com.ecommerce.ecommerce.User.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;

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

        var JwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(savedUser, JwtToken);

        return AuthenticationResponse
                .builder()
                .accessToken(JwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse googleAuth(GoogleAuthRequest request)  {
        String idTokenRequest = request.getIdToken();

        try{
            GoogleIdToken idToken = googleIdTokenVerifier.verify(idTokenRequest);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                System.out.println(payload);

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = payload.getEmailVerified();
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");
            }

            var accessToken = jwtService.generateToken(new HashMap<>(), new User());
            var refreshToken = jwtService.generateRefreshToken(new User());
            saveUserToken(new User(), accessToken);

            return AuthenticationResponse
                    .builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

         }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

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
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, JwtToken);

        return  AuthenticationResponse
                .builder()
                .accessToken(JwtToken)
                .refreshToken(refreshToken)
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


    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);

        if(username != null){
            var user = this.userRepository.findByEmail(username).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );

            if(jwtService.IsTokenValid(refreshToken, user)){
                var accessToken = jwtService.generateToken(user);

                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                var authResponse = AuthenticationResponse.builder()
                        .refreshToken(refreshToken)
                        .accessToken(accessToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }

    }
}
