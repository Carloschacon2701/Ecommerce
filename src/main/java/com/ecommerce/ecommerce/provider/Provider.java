package com.ecommerce.ecommerce.provider;


import com.ecommerce.ecommerce.User.User;
import com.ecommerce.ecommerce.products.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Provider extends User {


}
