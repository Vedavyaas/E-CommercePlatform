package com.dbms.ecommerceplatform.service;

import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.assets.VendorDTO;
import com.dbms.ecommerceplatform.assets.VendorDetails;
import com.dbms.ecommerceplatform.repository.UserDetailsEntity;
import com.dbms.ecommerceplatform.repository.UserDetailsRepository;
import com.dbms.ecommerceplatform.repository.VendorEntity;
import com.dbms.ecommerceplatform.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorService {
    private final UserDetailsRepository userDetailsRepository;

    private final VendorRepository vendorRepository;

    public VendorService(UserDetailsRepository userDetailsRepository, VendorRepository vendorRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.vendorRepository = vendorRepository;
    }

    public String setCredentials(VendorDetails vendorDetails, String username) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);

        if (userDetailsEntity.isEmpty()) throw new InvalidityException("No user account found!");

        Optional<VendorEntity> vendorEntity = vendorRepository.findByUserDetailsEntity(userDetailsEntity.get());

        if (vendorEntity.isPresent()) throw new InvalidityException("Credentials already given.");

        VendorEntity vendorEntity1 = new VendorEntity(userDetailsEntity.get(), vendorDetails.storeName(), vendorDetails.storeDescription());
        vendorRepository.save(vendorEntity1);

        return "Credentials stored successfully";
    }

    public String changeStoreName(String storeName, String username) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);

        if (userDetailsEntity.isEmpty()) throw new InvalidityException("No user account found!");

        Optional<VendorEntity> vendorEntity = vendorRepository.findByUserDetailsEntity(userDetailsEntity.get());

        if (vendorEntity.isEmpty()) throw new InvalidityException("Credentials not given.");

        vendorEntity.get().setStoreName(storeName);
        vendorRepository.save(vendorEntity.get());

        return "Store name changed successfully";
    }

    public String changeStoreDescription(String storeDesc, String username) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);

        if (userDetailsEntity.isEmpty()) throw new InvalidityException("No user account found!");

        Optional<VendorEntity> vendorEntity = vendorRepository.findByUserDetailsEntity(userDetailsEntity.get());

        if (vendorEntity.isEmpty()) throw new InvalidityException("Credentials not given.");

        vendorEntity.get().setStoreDescription(storeDesc);
        vendorRepository.save(vendorEntity.get());

        return "Store description changed successfully";
    }

    public VendorDTO getCredentials(String username) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);

        if (userDetailsEntity.isEmpty()) throw new InvalidityException("No user account found!");

        Optional<VendorDTO> vendorEntity = vendorRepository.findVendorEntityByUserDetailsEntity(userDetailsEntity.get());

        if (vendorEntity.isEmpty()) throw new InvalidityException("Credentials not given.");

        return vendorEntity.get();
    }
}
