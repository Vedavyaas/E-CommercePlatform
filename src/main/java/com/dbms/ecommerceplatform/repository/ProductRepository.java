package com.dbms.ecommerceplatform.repository;

import com.dbms.ecommerceplatform.assets.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByVendorEntityAndName(VendorEntity vendorEntity, String name);

    List<ProductDetails> findByVendorEntity_UserDetailsEntity_Username(String vendorEntityUserDetailsEntityUsername);

    Optional<ProductEntity> findByName(String name);
}
