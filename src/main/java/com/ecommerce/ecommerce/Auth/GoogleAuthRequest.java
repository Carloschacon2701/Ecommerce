package com.ecommerce.ecommerce.Auth;

import lombok.Data;

@Data
public class GoogleAuthRequest {
    private String idToken;
    private String address;
    private Integer  phoneNumber;
    private Integer bankAccount;

}
