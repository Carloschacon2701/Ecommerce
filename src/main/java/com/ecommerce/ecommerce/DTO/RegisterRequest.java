package com.ecommerce.ecommerce.DTO;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull(message = "First name is required")
    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotEmpty(message = "Last name is required")
    private String lastName;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email is not valid")
    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$", message = "Password must contain at least 8 characters, one uppercase letter and one special character")
    private String password;

    @NotNull(message = "Address is required")
    @NotEmpty(message = "Address is required")
    private String address;

    @NotNull(message = "Phone number is required")
    @Min(value = 1, message = "Phone number must be greater than 0")
    private Integer phoneNumber;

    @NotNull(message = "Bank account is required")
    @Min(value = 1, message = "Bank account must be greater than 0")
    private Integer bankAccount;


}
