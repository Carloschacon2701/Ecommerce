package com.ecommerce.ecommerce.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientRegisterRequest extends RegisterRequest{

    @NotEmpty(message = "Address is required")
    @NotNull(message = "Address is required")
    private String address;

    @NotEmpty(message = "Phone number is required")
    @NotNull(message = "Phone number is required")
    private String phoneNumber;
}
