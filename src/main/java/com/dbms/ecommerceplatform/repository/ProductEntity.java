package com.dbms.ecommerceplatform.repository;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private VendorEntity vendorEntity;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    public ProductEntity() { }

    public ProductEntity(VendorEntity vendorEntity, String name, String description, BigDecimal price, Integer stock) {
        this.vendorEntity = vendorEntity;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public void setId(Long productId) {
        this.Id = productId;
    }

    public Long getId() {
        return Id;
    }

    public VendorEntity getVendorEntity() {
        return vendorEntity;
    }

    public void setVendorEntity(VendorEntity vendorEntity) {
        this.vendorEntity = vendorEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
