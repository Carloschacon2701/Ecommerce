package com.ecommerce.ecommerce.Auth;

import com.ecommerce.ecommerce.Role.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer roleID;

}
