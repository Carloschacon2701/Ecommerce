package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.Role.Role;
import com.ecommerce.ecommerce.Role.RoleRepository;
import com.ecommerce.ecommerce.User.User;
import com.ecommerce.ecommerce.User.UserRepository;
import com.ecommerce.ecommerce.products.Product;
import com.ecommerce.ecommerce.products.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {

		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			RoleRepository roleRepository,
			ProductRepository productRepository,
			UserRepository userRepository
	) {
		return args -> {
			roleRepository.save(new Role("PROVIDER"));
			roleRepository.save(new Role("CLIENT"));

			userRepository.saveAll(
					List.of(
							User.builder()
									.address("Avenue New York")
									.email("carlos@gmail.com")
									.role(roleRepository.findByRoleId(1).orElse(null))
									.bankAccount(123456789)
									.firstName("Carlos")
									.lastName("Chacon")
									.phoneNumber(123456789)
									.build(),

							User.builder()
									.address("Avenue New York")
									.email("client@gmail.com")
									.role(roleRepository.findByRoleId(2).orElse(null))
									.bankAccount(123456789)
									.firstName("Client")
									.lastName("Client")
									.phoneNumber(123456789)
									.build()
					)
			);

			productRepository.saveAll(List.of(
					 Product.builder()
							 .price(11.59)
							 .name("Blanket")
							 .provider(userRepository.findByEmail("carlos@gmail.com").orElse(null))
							 .build(),

					 Product.builder()
							 .price(1.9)
							 .name("Bluse")
							 .provider(userRepository.findByEmail("carlos@gmail.com").orElse(null))
							 .build(),

					 Product.builder()
							 .price(2.9)
							 .name("Shoes")
							 .provider(userRepository.findByEmail("carlos@gmail.com").orElse(null))
							 .build(),

					 Product.builder()
							 .price(3.9)
							 .name("Pants")
							 .provider(userRepository.findByEmail("carlos@gmail.com").orElse(null))
							 .build(),

					 Product.builder()
							 .price(4.9)
							 .name("T-Shirt")
							 .provider(userRepository.findByEmail("carlos@gmail.com").orElse(null))
							 .build(),

					 Product.builder()
							 .price(5.9)
							 .name("Socks")
							 .provider(userRepository.findByEmail("carlos@gmail.com").orElse(null))
							 .build(),

					 Product.builder()
							 .price(6.9)
							 .name("Jacket")
							 .provider(userRepository.findByEmail("carlos@gmail.com").orElse(null))
							 .build(),

					 Product.builder()
							 .price(7.9)
							 .name("Hat")
							 .provider(userRepository.findByEmail("carlos@gmail.com").orElse(null))
							 .build(),

					 Product.builder()
							 .price(8.9)
							 .name("Gloves")
							 .provider(userRepository.findByEmail("carlos@gmail.com").orElse(null))
							 .build(),

					 Product.builder()
							 .price(9.9)
							 .name("Scarf")
							 .provider(userRepository.findByEmail("carlos@gmail.com").orElse(null))
							 .build()
			));
		};
	}


}
