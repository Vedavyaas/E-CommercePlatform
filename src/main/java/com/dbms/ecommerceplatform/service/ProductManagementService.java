package com.dbms.ecommerceplatform.service;

import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.assets.ProductDetails;
import com.dbms.ecommerceplatform.assets.Products;
import com.dbms.ecommerceplatform.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductManagementService {
    private final UserDetailsRepository userDetailsRepository;
    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;
    private final VendorService vendorService;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public ProductManagementService(UserDetailsRepository userDetailsRepository, VendorRepository vendorRepository, ProductRepository productRepository, VendorService vendorService, CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
        this.vendorService = vendorService;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
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

    public List<Products> getProducts() {
        return productRepository.findEverything();
    }

    public List<Products> getProduct(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Transactional
    public String buyProduct(Long id, Integer quantity, String username) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if (productEntity.isEmpty()) throw new InvalidityException("Product does not exist.");

        if (productEntity.get().getStock() < quantity) return "Stock is lesser than your quantity to purchase";

        Optional<CustomerEntity> customerEntity = customerRepository.findByUserDetailsEntity_Username(username);
        if (customerEntity.isEmpty()) throw new InvalidityException("Customer id not registered!");

        BigDecimal price = productEntity.get().getPrice();
        productEntity.get().setStock(productEntity.get().getStock() - quantity);

        if (productEntity.get().getStock() == 0) productRepository.delete(productEntity.get());
        productRepository.save(productEntity.get());

        OrderEntity orderEntity = new OrderEntity(customerEntity.get(), LocalDateTime.now(), "PURCHASED", price.multiply(BigDecimal.valueOf(quantity)));
        orderRepository.save(orderEntity);

        return "Product purchased successfully";
    }
}
