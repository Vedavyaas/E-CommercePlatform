package com.dbms.ecommerceplatform.repository;

import jakarta.persistence.*;

@Entity
public class VendorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    @OneToOne
    private UserDetailsEntity userDetailsEntity;

    private String storeName;

    private String storeDescription;

    public VendorEntity() { }

    public VendorEntity(UserDetailsEntity userDetailsEntity, String storeName, String storeDescription) {
        this.userDetailsEntity = userDetailsEntity;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public UserDetailsEntity getUserDetailsEntity() {
        return userDetailsEntity;
    }

    public void setUserDetailsEntity(UserDetailsEntity userDetailsEntity) {
        this.userDetailsEntity = userDetailsEntity;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }
}
