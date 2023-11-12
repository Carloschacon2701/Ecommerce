package com.ecommerce.ecommerce.Auth;

import com.ecommerce.ecommerce.JWT.JWTService;
import com.ecommerce.ecommerce.Role.Role;
import com.ecommerce.ecommerce.Role.RoleRepository;
import com.ecommerce.ecommerce.User.User;
import com.ecommerce.ecommerce.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationResponse register(RegisterRequest request){
        Optional<Role> role = roleRepository.findById(request.getRoleID());
        System.out.println(role);

        if(role.isEmpty()){
            throw new RuntimeException("Role not found");
        }

        var  user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role.get())
                .build();

        System.out.println("inside here");
        userRepository.save(user);
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

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Password is incorrect");
        }

        var JwtToken = jwtService.generateToken(user);

        return  AuthenticationResponse
                .builder()
                .authenticationToken(JwtToken)
                .build();

    }



}
