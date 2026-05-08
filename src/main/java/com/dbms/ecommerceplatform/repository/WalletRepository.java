package com.dbms.ecommerceplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
    @Query("SELECT w FROM WalletEntity w WHERE w.userDetailsEntity.username = :username")
    Optional<WalletEntity> findByUsername(String username);
}
