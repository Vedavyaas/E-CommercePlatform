package com.dbms.ecommerceplatform.service;

import com.dbms.ecommerceplatform.assets.AccountDetails;
import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.repository.UserDetailsEntity;
import com.dbms.ecommerceplatform.repository.UserDetailsRepository;
import com.dbms.ecommerceplatform.repository.WalletEntity;
import com.dbms.ecommerceplatform.repository.WalletRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {

    private final UserDetailsRepository userDetailsRepository;

    private final PasswordEncoder passwordEncoder;

    private final WalletRepository walletRepository;

    public AccountService(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder, WalletRepository walletRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
        this.walletRepository = walletRepository;
    }

    public String createAccount(AccountDetails accountDetails) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(accountDetails.username());

        if (userDetailsEntity.isPresent()) throw new InvalidityException("Username already exists.");

        Optional<UserDetailsEntity> userDetailsEntity1 = userDetailsRepository.findByEmail(accountDetails.email());

        if (userDetailsEntity1.isPresent()) throw new InvalidityException("Email already exists.");

        UserDetailsEntity userDetailsEntity2 = new UserDetailsEntity(accountDetails.username(), accountDetails.email(), passwordEncoder.encode(accountDetails.password()), accountDetails.role());
        userDetailsRepository.save(userDetailsEntity2);

        walletRepository.save(new WalletEntity(userDetailsEntity2, new BigDecimal("1000")));

        return "Account created successfully.";
    }

    public String resetAccountPassword(String username, String newPassword) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);

        if (userDetailsEntity.isEmpty()) throw new InvalidityException("Account does not exists.");

        userDetailsEntity.get().setPassword(passwordEncoder.encode(newPassword));
        userDetailsRepository.save(userDetailsEntity.get());

        return "Password changed successfully.";
    }
}
