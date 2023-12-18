package com.ecommerce.ecommerce.User;
import com.ecommerce.ecommerce.Cart.Cart;
import com.ecommerce.ecommerce.Role.Role;
import com.ecommerce.ecommerce.Token.Token;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
public class User  implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer id;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private String address;

    private Integer phoneNumber;

    private Integer bankAccount;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Token> tokens;


    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return
                List.of(new SimpleGrantedAuthority(
                       "ROLE_" + role.getAuthority()
                ));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToOne(mappedBy = "client", optional = false)
    private Cart cart;


}
