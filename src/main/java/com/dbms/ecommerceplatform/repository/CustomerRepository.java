package com.dbms.ecommerceplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findByUserDetailsEntity(UserDetailsEntity userDetailsEntity);
}
