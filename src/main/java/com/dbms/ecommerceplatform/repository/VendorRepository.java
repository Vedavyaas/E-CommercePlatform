package com.dbms.ecommerceplatform.repository;

import com.dbms.ecommerceplatform.assets.VendorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
    @Query("SELECT v FROM VendorEntity v WHERE v.userDetailsEntity = :userDetailsEntity")
    Optional<VendorEntity> findByUserDetailsEntity(@Param("userDetailsEntity") UserDetailsEntity userDetailsEntity);

    @Query("SELECT new com.dbms.ecommerceplatform.assets.VendorDTO(v.vendorId, v.storeName, v.storeDescription) FROM VendorEntity v WHERE v.userDetailsEntity = :userDetailsEntity")
    Optional<VendorDTO> findVendorEntityByUserDetailsEntity(@Param("userDetailsEntity") UserDetailsEntity userDetailsEntity);
}
