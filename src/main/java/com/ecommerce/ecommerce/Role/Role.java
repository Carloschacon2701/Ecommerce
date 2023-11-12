package com.ecommerce.ecommerce.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Role  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Integer roleId;

    private String authority;

    public Role(String authority) {
        this.authority = authority;

    }
}