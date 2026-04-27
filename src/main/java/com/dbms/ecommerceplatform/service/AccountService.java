package com.dbms.ecommerceplatform.service;

import com.dbms.ecommerceplatform.assets.AccountDetails;
import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.repository.UserDetailsEntity;
import com.dbms.ecommerceplatform.repository.UserDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final UserDetailsRepository userDetailsRepository;

    private final PasswordEncoder passwordEncoder;

    public AccountService(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String createAccount(AccountDetails accountDetails) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(accountDetails.username());

        if (userDetailsEntity.isPresent()) throw new InvalidityException("Username already exists.");

        Optional<UserDetailsEntity> userDetailsEntity1 = userDetailsRepository.findByEmail(accountDetails.email());

        if (userDetailsEntity1.isPresent()) throw new InvalidityException("Email already exists.");

        UserDetailsEntity userDetailsEntity2 = new UserDetailsEntity(accountDetails.username(), accountDetails.email(), passwordEncoder.encode(accountDetails.password()), accountDetails.role());
        userDetailsRepository.save(userDetailsEntity2);

        return "Account created successfully.";
    }

    public String deleteAccount(String username) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);

        if (userDetailsEntity.isEmpty()) throw new InvalidityException("Account does not exists.");

        userDetailsRepository.delete(userDetailsEntity.get());
        return "Account removed successfully.";
    }

    public String resetAccountPassword(String username, String newPassword) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);

        if (userDetailsEntity.isEmpty()) throw new InvalidityException("Account does not exists.");

        userDetailsEntity.get().setPassword(passwordEncoder.encode(newPassword));
        userDetailsRepository.save(userDetailsEntity.get());

        return "Password changed successfully.";
    }
}
