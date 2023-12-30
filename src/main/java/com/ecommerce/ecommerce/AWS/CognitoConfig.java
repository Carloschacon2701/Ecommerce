package com.ecommerce.ecommerce.AWS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
public class CognitoConfig {

    @Bean
    public CognitoIdentityProviderClient cognitoIdentityProviderClient(){
        return CognitoIdentityProviderClient.builder()
                .region(Region.US_EAST_2)
                .build();
    }
}
