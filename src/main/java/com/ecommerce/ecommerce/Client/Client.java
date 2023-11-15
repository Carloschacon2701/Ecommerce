package com.ecommerce.ecommerce.Client;

import com.ecommerce.ecommerce.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name= "client")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Client extends User {

    @Id
    @GeneratedValue
    @Column(name = "client_id")
    private Integer clientId;

    private String address;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;


}
