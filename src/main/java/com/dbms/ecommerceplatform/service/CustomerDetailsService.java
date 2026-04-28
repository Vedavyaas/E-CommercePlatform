package com.dbms.ecommerceplatform.service;

import com.dbms.ecommerceplatform.assets.CustomerCredentials;
import com.dbms.ecommerceplatform.assets.CustomerDTO;
import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.repository.CustomerEntity;
import com.dbms.ecommerceplatform.repository.CustomerRepository;
import com.dbms.ecommerceplatform.repository.UserDetailsEntity;
import com.dbms.ecommerceplatform.repository.UserDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.OptionalInt;

@Service
public class CustomerDetailsService {
    private final CustomerRepository customerRepository;

    private final UserDetailsRepository userDetailsRepository;

    public CustomerDetailsService(CustomerRepository customerRepository, UserDetailsRepository userDetailsRepository) {
        this.customerRepository = customerRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public String setCredentials(CustomerCredentials customerCredentials, String username) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);

        if (userDetailsEntity.isEmpty()) throw new InvalidityException("No user account found!");

        Optional<CustomerEntity> customerEntity = customerRepository.findByUserDetailsEntity(userDetailsEntity.get());

        if (customerEntity.isPresent()) throw new InvalidityException("Customer credential already registered");

        CustomerEntity customerEntity1 = new CustomerEntity(userDetailsEntity.get(), customerCredentials.firstname(), customerCredentials.lastname(), customerCredentials.phoneNumber());
        customerRepository.save(customerEntity1);

        return "Customer credentials registered successfully";
    }

    public String changeFirstname(String firstname, String username) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);

        if (userDetailsEntity.isEmpty()) throw new InvalidityException("No user account found!");

        Optional<CustomerEntity> customerEntity = customerRepository.findByUserDetailsEntity(userDetailsEntity.get());

        if (customerEntity.isEmpty()) throw new InvalidityException("No customer credentials entered to modify!");

        customerEntity.get().setFirstname(firstname);
        customerRepository.save(customerEntity.get());

        return "First name changed successfully.";
    }

    public String changeLastname(String lastname, String username) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);

        if (userDetailsEntity.isEmpty()) throw new InvalidityException("No user account found!");

        Optional<CustomerEntity> customerEntity = customerRepository.findByUserDetailsEntity(userDetailsEntity.get());

        if (customerEntity.isEmpty()) throw new InvalidityException("No customer credentials entered to modify!");

        customerEntity.get().setLastname(lastname);
        customerRepository.save(customerEntity.get());

        return "Last name changed successfully.";
    }

    public String changePhoneNumber(String phoneNumber, String username) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);

        if (userDetailsEntity.isEmpty()) throw new InvalidityException("No user account found!");

        Optional<CustomerEntity> customerEntity = customerRepository.findByUserDetailsEntity(userDetailsEntity.get());

        if (customerEntity.isEmpty()) throw new InvalidityException("No customer credentials entered to modify!");

        customerEntity.get().setPhoneNumber(phoneNumber);
        customerRepository.save(customerEntity.get());

        return "Phone number changed successfully.";
    }

    public CustomerDTO getDetails(String username) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);

        if (userDetailsEntity.isEmpty()) throw new InvalidityException("Account does not exist.");

        Optional<CustomerDTO> customerEntity = customerRepository.findCustomerEntityByUserDetailsEntity(userDetailsEntity.get());

        if (customerEntity.isEmpty()) throw new InvalidityException("Customer credential is not given yet!");

        return customerEntity.get();
    }
}
