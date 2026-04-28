package com.dbms.ecommerceplatform.controller;

import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.assets.VendorDTO;
import com.dbms.ecommerceplatform.assets.VendorDetails;
import com.dbms.ecommerceplatform.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/vendor/enter/details")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_VENDOR')")
    public ResponseEntity<String> enterCredentials(@RequestBody VendorDetails vendorDetails, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(vendorService.setCredentials(vendorDetails, jwt.getSubject()));
    }

    @PutMapping("/vendor/storeName/change")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_VENDOR')")
    public ResponseEntity<String> changeName(@RequestParam String storeName, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(vendorService.changeStoreName(storeName, jwt.getSubject()));
    }

    @PutMapping("/vendor/storeDescription/change")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_VENDOR')")
    public ResponseEntity<String> changeDescription(@RequestParam String storeDesc, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(vendorService.changeStoreDescription(storeDesc, jwt.getSubject()));
    }

    @GetMapping("/vendor/get/credentials")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_VENDOR')")
    public ResponseEntity<VendorDTO> getCredentials(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(vendorService.getCredentials(jwt.getSubject()));
    }

    @ExceptionHandler(InvalidityException.class)
    public ResponseEntity<String> handle(InvalidityException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
