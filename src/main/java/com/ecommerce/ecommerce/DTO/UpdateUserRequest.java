package com.ecommerce.ecommerce.DTO;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String address;
    private Integer phoneNumber;
    private Integer bankAccount;
}
