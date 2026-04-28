package com.dbms.ecommerceplatform.repository;

import com.dbms.ecommerceplatform.assets.VendorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
    @Query("SELECT v FROM VendorEntity v WHERE v.userDetailsEntity = :userDetailsEntity")
    Optional<VendorEntity> findByUserDetailsEntity(UserDetailsEntity userDetailsEntity);

    @Query("SELECT new com.dbms.ecommerceplatform.assets.VendorDTO(v.vendorId, v.storeName, v.storeDescription) c FROM VendorEntity v WHERE v.userDetailsEntity = :userDetailsEntity")
    Optional<VendorDTO> findVendorEntityByUserDetailsEntity(UserDetailsEntity userDetailsEntity);
}
