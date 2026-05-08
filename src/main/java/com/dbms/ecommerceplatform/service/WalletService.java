package com.dbms.ecommerceplatform.service;

import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.repository.WalletEntity;
import com.dbms.ecommerceplatform.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public BigDecimal getBalance(String username) {
        Optional<WalletEntity> wallet = walletRepository.findByUsername(username);
        if (wallet.isEmpty()) {
            throw new InvalidityException("Wallet not found.");
        }
        return wallet.get().getBalance();
    }

    public String addBalance(String username, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidityException("Amount must be greater than zero.");
        }
        Optional<WalletEntity> wallet = walletRepository.findByUsername(username);
        if (wallet.isEmpty()) {
            throw new InvalidityException("Wallet not found.");
        }
        wallet.get().setBalance(wallet.get().getBalance().add(amount));
        walletRepository.save(wallet.get());
        return "Balance added successfully.";
    }
}
