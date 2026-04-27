package com.dbms.ecommerceplatform.repository;

import jakarta.persistence.*;

@Entity
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;

    @OneToOne
    private UserDetailsEntity userDetailsEntity;

    private String firstname;

    private String lastname;

    private String phoneNumber;

    public CustomerEntity() { }

    public CustomerEntity(UserDetailsEntity userDetailsEntity, String firstname, String lastname, String phoneNumber) {
        this.userDetailsEntity = userDetailsEntity;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public UserDetailsEntity getUserDetailsEntity() {
        return userDetailsEntity;
    }

    public void setUserDetailsEntity(UserDetailsEntity userDetailsEntity) {
        this.userDetailsEntity = userDetailsEntity;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}