package com.ecommerce.ecommerce.User;

import com.ecommerce.ecommerce.DTO.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void updateUser(UpdateUserRequest request, Principal connectedUser){
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        var DBUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        DBUser.setFirstName(request.getFirstName());
        DBUser.setLastName(request.getLastName());
        DBUser.setAddress(request.getAddress());
        DBUser.setPhoneNumber(request.getPhoneNumber());
        DBUser.setBankAccount(request.getBankAccount());

        userRepository.save(DBUser);
    }

}
