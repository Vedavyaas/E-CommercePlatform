package com.dbms.ecommerceplatform.controller;

import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/balance")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CUSTOMER') or hasAuthority('SCOPE_ROLE_VENDOR')")
    public ResponseEntity<BigDecimal> getBalance(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(walletService.getBalance(jwt.getSubject()));
    }

    @PutMapping("/add")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CUSTOMER') or hasAuthority('SCOPE_ROLE_VENDOR')")
    public ResponseEntity<String> addBalance(@RequestParam BigDecimal amount, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(walletService.addBalance(jwt.getSubject(), amount));
    }

    @ExceptionHandler(InvalidityException.class)
    public ResponseEntity<String> handle(InvalidityException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
