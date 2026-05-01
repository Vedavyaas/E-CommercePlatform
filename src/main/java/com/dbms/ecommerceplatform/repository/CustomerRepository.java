package com.dbms.ecommerceplatform.repository;

import com.dbms.ecommerceplatform.assets.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("SELECT c FROM CustomerEntity c WHERE c.userDetailsEntity = :userDetailsEntity")
    Optional<CustomerEntity> findByUserDetailsEntity(UserDetailsEntity userDetailsEntity);

    @Query("SELECT new com.dbms.ecommerceplatform.assets.CustomerDTO(c.customerId, c.firstname, c.lastname, c.phoneNumber) FROM CustomerEntity c WHERE c.userDetailsEntity = :userDetailsEntity")
    Optional<CustomerDTO> findCustomerEntityByUserDetailsEntity(UserDetailsEntity userDetailsEntity);

    Optional<CustomerEntity> findByUserDetailsEntity_Username(String userDetailsEntityUsername);
}
