package com.ecommerce.ecommerce.Auth;


import com.ecommerce.ecommerce.DTO.RegisterRequest;
import com.ecommerce.ecommerce.DTO.TokenRequest;
import com.ecommerce.ecommerce.JWT.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JWTService jwtService;

    @PostMapping(path = "/register/cognito")
    public ResponseEntity<?> registerCognito(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping(path="/login/cognito")
    public ResponseEntity<?> loginCognito(@RequestBody @Valid AuthRequest request){
        return ResponseEntity.ok(authenticationService.initiateAuth(request));
    }

    @PostMapping(path="/getClaims")
    public ResponseEntity<?> getClaims(@RequestBody TokenRequest request){
        return ResponseEntity.ok(jwtService.getClaims(request.getToken()));
    }
}
