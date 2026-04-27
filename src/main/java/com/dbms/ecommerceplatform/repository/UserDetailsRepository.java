package com.dbms.ecommerceplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {

    @Query("SELECT u FROM UserDetailsEntity u WHERE u.username = :username")
    Optional<UserDetailsEntity> findByUsername(String username);

    @Query("SELECT u FROM UserDetailsEntity u WHERE u.email = :email")
    Optional<UserDetailsEntity> findByEmail(String email);
}
