package com.dbms.ecommerceplatform.controller;

import com.dbms.ecommerceplatform.assets.CustomerCredentials;
import com.dbms.ecommerceplatform.assets.CustomerDTO;
import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.assets.Products;
import com.dbms.ecommerceplatform.service.CustomerDetailsService;
import com.dbms.ecommerceplatform.service.ProductManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerDetailsService customerDetailsService;
    private final ProductManagementService productManagementService;

    public CustomerController(CustomerDetailsService customerDetailsService, ProductManagementService productManagementService) {
        this.customerDetailsService = customerDetailsService;
        this.productManagementService = productManagementService;
    }

    @PostMapping("/customer/enter/details")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CUSTOMER')")
    public ResponseEntity<String> setCredentials(@RequestBody CustomerCredentials customerCredentials, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(customerDetailsService.setCredentials(customerCredentials, jwt.getSubject()));
    }

    @PutMapping("/customer/firstname/modify")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CUSTOMER')")
    public ResponseEntity<String> changeFirstname(@RequestParam String firstname, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(customerDetailsService.changeFirstname(firstname, jwt.getSubject()));
    }

    @PutMapping("/customer/lastname/modify")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CUSTOMER')")
    public ResponseEntity<String> changeLastname(@RequestParam String lastname, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(customerDetailsService.changeLastname(lastname, jwt.getSubject()));
    }

    @PutMapping("/customer/phone/modify")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CUSTOMER')")
    public ResponseEntity<String> changePhoneNumber(@RequestParam String phoneNumber, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(customerDetailsService.changePhoneNumber(phoneNumber, jwt.getSubject()));
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CUSTOMER')")
    public ResponseEntity<CustomerDTO> getSelf(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(customerDetailsService.getDetails(jwt.getSubject()));
    }

    @GetMapping("/products/info")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CUSTOMER')")
    public ResponseEntity<List<Products>> getProducts() {
        return ResponseEntity.ok(productManagementService.getProducts());
    }

    @GetMapping("/product/info")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CUSTOMER')")
    public ResponseEntity<List<Products>> getProduct(@RequestParam String name) {
        return ResponseEntity.ok(productManagementService.getProduct(name));
    }

    @PutMapping("/product/buy")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CUSTOMER')")
    public ResponseEntity<String> buyProduct(@RequestParam Long id, @RequestParam Integer quantity, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(productManagementService.buyProduct(id, quantity, jwt.getSubject()));
    }
    @ExceptionHandler(InvalidityException.class)
    public ResponseEntity<String> handle(InvalidityException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}