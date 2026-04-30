package com.dbms.ecommerceplatform.controller;

import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.assets.ProductDetails;
import com.dbms.ecommerceplatform.assets.VendorDTO;
import com.dbms.ecommerceplatform.assets.VendorDetails;
import com.dbms.ecommerceplatform.service.ProductManagementService;
import com.dbms.ecommerceplatform.service.VendorService;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.config.RepositoryConfigurationSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class VendorController {
    private final VendorService vendorService;
    private final ProductManagementService productManagementService;

    public VendorController(VendorService vendorService, ProductManagementService productManagementService) {
        this.vendorService = vendorService;
        this.productManagementService = productManagementService;
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

    @PostMapping("/enter/fresh/product")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_VENDOR')")
    public ResponseEntity<String> productAddition(@RequestBody ProductDetails productDetails, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(productManagementService.addProduct(productDetails, jwt.getSubject()));
    }

    @PutMapping("/update/product/price")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_VENDOR')")
    public ResponseEntity<String> productPriceChange(@RequestParam Long id, @RequestParam BigDecimal price, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(productManagementService.changePrice(id, price, jwt.getSubject()));
    }

    @PutMapping("/update/product/stock")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_VENDOR')")
    public ResponseEntity<String> productStockChange(@RequestParam Long id, @RequestParam Integer stock, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(productManagementService.changeStock(id, stock, jwt.getSubject()));
    }

    @GetMapping("/get/all/product")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_VENDOR')")
    public ResponseEntity<List<ProductDetails>> getAllProduct(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(productManagementService.productDetailsList(jwt.getSubject()));
    }

    @DeleteMapping("/delete/product/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_VENDOR')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(productManagementService.deleteProduct(id, jwt.getSubject()));
    }

    @GetMapping("/find/product/{name}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_VENDOR')")
    public ResponseEntity<ProductDetails> productByName(@PathVariable String name, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(productManagementService.findByName(name, jwt.getSubject()));
    }

    @ExceptionHandler(InvalidityException.class)
    public ResponseEntity<String> handle(InvalidityException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
