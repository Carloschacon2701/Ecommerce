package com.ecommerce.ecommerce.JWT;

import com.ecommerce.ecommerce.DTO.TokenRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GetUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GetUserResponse;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final CognitoIdentityProviderClient cognitoIdentityProviderClient;

    public  String extractClaim(String token, String claim){
        final Map<String, String> claims = getClaims(token);
        return  claims.get(claim);
    }

    public Map<String, String> getClaims(String token) {
        Map<String, String> claims = new HashMap<>();
        try {

            GetUserRequest authRequest = GetUserRequest.builder()
                    .accessToken(token)
                    .build();

            GetUserResponse response = cognitoIdentityProviderClient.getUser(authRequest);

            response.userAttributes().forEach(attributeType -> {
                claims.put(attributeType.name(), attributeType.value());
            });

            return claims;

        } catch(CognitoIdentityProviderException e) {
            throw new RuntimeException(e.awsErrorDetails().errorMessage());
        }

    }

    public String extractUsername(String token) {
        return extractClaim(token, "email");
    }

    public boolean IsTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }


}
