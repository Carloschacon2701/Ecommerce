package com.ecommerce.ecommerce.User;


import com.ecommerce.ecommerce.DTO.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
 @RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping(path = "/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ){
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().body(Map.of("message", "Password changed successfully"));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> updateUser(
            @RequestBody UpdateUserRequest request,
            Principal connectedUser
    ){
        userService.updateUser(request, connectedUser);
        return ResponseEntity.ok().body(Map.of("message", "User updated successfully"));
    }
}
