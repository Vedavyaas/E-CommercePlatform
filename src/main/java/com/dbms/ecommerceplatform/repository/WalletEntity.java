package com.dbms.ecommerceplatform.repository;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDetailsEntity userDetailsEntity;

    private BigDecimal balance;

    public WalletEntity() {
    }

    public WalletEntity(UserDetailsEntity userDetailsEntity, BigDecimal balance) {
        this.userDetailsEntity = userDetailsEntity;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDetailsEntity getUserDetailsEntity() {
        return userDetailsEntity;
    }

    public void setUserDetailsEntity(UserDetailsEntity userDetailsEntity) {
        this.userDetailsEntity = userDetailsEntity;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
