package com.ecommerce.ecommerce.Auth;


import com.ecommerce.ecommerce.DTO.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    @PostMapping(path = "/register/client")
    public ResponseEntity<AuthenticationResponse> registerClient(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request, 2));
    }

    @PostMapping(path = "/register/provider")
    public ResponseEntity<AuthenticationResponse> registerProvider(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request, 1));
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse>auth(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authenticationService.auth(request));
    }

    @PostMapping(path ="/oauth2/google")
    public ResponseEntity<AuthenticationResponse> googleAuth(@RequestBody GoogleAuthRequest request){
        return ResponseEntity.ok(authenticationService.googleAuth(request));
    }

    @PostMapping(path = "/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
