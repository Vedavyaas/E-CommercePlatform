package com.dbms.ecommerceplatform.service;

import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.assets.ProductDetails;
import com.dbms.ecommerceplatform.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductManagementService {
    private final UserDetailsRepository userDetailsRepository;
    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;
    private final VendorService vendorService;

    public ProductManagementService(UserDetailsRepository userDetailsRepository, VendorRepository vendorRepository, ProductRepository productRepository, VendorService vendorService) {
        this.userDetailsRepository = userDetailsRepository;
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
        this.vendorService = vendorService;
    }

    public String addProduct(ProductDetails productDetails, String username) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUsername(username);
        if (userDetailsEntity.isEmpty()) throw new InvalidityException("Account does not exist.");

        Optional<VendorEntity> vendorEntity = vendorRepository.findByUserDetailsEntity(userDetailsEntity.get());
        if (vendorEntity.isEmpty()) throw new InvalidityException("Vendor id does not exist.");

        Optional<ProductEntity> productEntity = productRepository.findByVendorEntityAndName(vendorEntity.get(), productDetails.name());
        if (productEntity.isPresent()) throw new InvalidityException("Product with name " + productDetails.name() + " is already present");

        ProductEntity productEntity1 = new ProductEntity(vendorEntity.get(), productDetails.name(), productDetails.description(), productDetails.price(), productDetails.stock());
        productRepository.save(productEntity1);

        return "Product added successfully";
    }

    public String changePrice(Long id, BigDecimal price, String username) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if (productEntity.isEmpty()) throw new InvalidityException("Product does not exist.");

        if (!productEntity.get().getVendorEntity().getUserDetailsEntity().getUsername().equals(username))
            throw new InvalidityException("Product does not belong to you.");

        productEntity.get().setPrice(price);
        productRepository.save(productEntity.get());
        return "Price updated successfully!";
    }

    public String changeStock(Long id, Integer stock, String username) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if (productEntity.isEmpty()) throw new InvalidityException("Product does not exist.");

        if (!productEntity.get().getVendorEntity().getUserDetailsEntity().getUsername().equals(username))
            throw new InvalidityException("Product does not belong to you.");

        productEntity.get().setStock(stock);
        productRepository.save(productEntity.get());
        return "Stock updated successfully!";
    }

    public List<ProductDetails> productDetailsList(String username) {
        return productRepository.findByVendorEntity_UserDetailsEntity_Username(username);
    }

    public String deleteProduct(Long id, String username) {
        Optional<ProductEntity> productEntity =  productRepository.findById(id);
        if (productEntity.isEmpty()) throw new InvalidityException("Product does not exist.");

        if (!productEntity.get().getVendorEntity().getUserDetailsEntity().getUsername().equals(username)) throw new InvalidityException("Product does not belong to you!!");

        productRepository.delete(productEntity.get());
        return "Product removed successfully!!";
    }

    public ProductDetails findByName(String name, String username) {
        Optional<ProductEntity> productDetails = productRepository.findByName(name);
        if (productDetails.isEmpty()) throw new InvalidityException("Product does not exist!");

        if (!productDetails.get().getVendorEntity().getUserDetailsEntity().getUsername().equals(username)) throw new InvalidityException("Product does not belong to you!");

        return new ProductDetails(productDetails.get().getId(), productDetails.get().getName(), productDetails.get().getDescription(), productDetails.get().getPrice(), productDetails.get().getStock());
    }
}
