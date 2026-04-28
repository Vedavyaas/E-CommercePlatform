package com.dbms.ecommerceplatform.controller;

import com.dbms.ecommerceplatform.assets.CustomerCredentials;
import com.dbms.ecommerceplatform.assets.CustomerDTO;
import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.service.CustomerDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    private final CustomerDetailsService customerDetailsService;

    public CustomerController(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
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

    @ExceptionHandler(InvalidityException.class)
    public ResponseEntity<String> handle(InvalidityException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}