package com.ecommerce.ecommerce.Auth;


import com.ecommerce.ecommerce.DTO.ClientRegisterRequest;
import com.ecommerce.ecommerce.DTO.ProviderRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    @PostMapping(path = "/register/client")
    public ResponseEntity<AuthenticationResponse> registerClient(@RequestBody ClientRegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(path = "/register/provider")
    public ResponseEntity<AuthenticationResponse> registerProvider(@RequestBody ProviderRegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse>auth(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authenticationService.auth(request));
    }
}
