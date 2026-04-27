package com.dbms.ecommerceplatform.security;

import com.dbms.ecommerceplatform.assets.Role;
import com.dbms.ecommerceplatform.repository.UserDetailsEntity;
import com.dbms.ecommerceplatform.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userDetailsRepository.count() == 0) {
            UserDetailsEntity userDetailsEntity1 = new UserDetailsEntity("customer", "customer@gmail.com", passwordEncoder.encode("123"), Role.CUSTOMER);
            UserDetailsEntity userDetailsEntity2 = new UserDetailsEntity("vendor", "vendor@gmail.com", passwordEncoder.encode("123"), Role.VENDOR);
            UserDetailsEntity userDetailsEntity3 = new UserDetailsEntity("admin", "admin@gmail.com", passwordEncoder.encode("123"), Role.ADMIN);

            userDetailsRepository.saveAll(List.of(userDetailsEntity1, userDetailsEntity2, userDetailsEntity3));
        }
    }
}
