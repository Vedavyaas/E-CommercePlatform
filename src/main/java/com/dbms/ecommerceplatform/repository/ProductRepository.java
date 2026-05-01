package com.dbms.ecommerceplatform.repository;

import com.dbms.ecommerceplatform.assets.ProductDetails;
import com.dbms.ecommerceplatform.assets.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByVendorEntityAndName(VendorEntity vendorEntity, String name);

    @Query("SELECT new com.dbms.ecommerceplatform.assets.ProductDetails(p.Id, p.name, p.description, p.price, p.stock) FROM ProductEntity p WHERE p.vendorEntity.userDetailsEntity.username = :vendorEntityUserDetailsEntityUsername")
    List<ProductDetails> findByVendorEntity_UserDetailsEntity_Username(String vendorEntityUserDetailsEntityUsername);

    @Query("SELECT p FROM ProductEntity p WHERE p.name = :name")
    Optional<ProductEntity> findByName(String name);

    @Query("SELECT new com.dbms.ecommerceplatform.assets.Products(p.Id, p.name, p.description, p.price, p.stock, p.vendorEntity.storeName, p.vendorEntity.storeDescription) FROM ProductEntity p")
    List<Products> findEverything();

    @Query("SELECT new com.dbms.ecommerceplatform.assets.Products(p.Id, p.name, p.description, p.price, p.stock, p.vendorEntity.storeName, p.vendorEntity.storeDescription) FROM ProductEntity p WHERE p.name LIKE :name")
    List<Products> findByNameContaining(String name);
}
