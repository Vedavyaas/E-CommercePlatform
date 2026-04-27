package com.dbms.ecommerceplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
    VendorEntity findByUserDetailsEntity(UserDetailsEntity userDetailsEntity);
}
