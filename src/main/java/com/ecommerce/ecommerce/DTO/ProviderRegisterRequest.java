package com.ecommerce.ecommerce.DTO;

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
public class ProviderRegisterRequest extends RegisterRequest{

    @NotNull(message = "Bank account is required")
    private Integer bank_account;
}
