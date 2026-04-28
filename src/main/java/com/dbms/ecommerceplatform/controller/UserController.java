package com.dbms.ecommerceplatform.controller;

import com.dbms.ecommerceplatform.assets.AccountDetails;
import com.dbms.ecommerceplatform.assets.InvalidityException;
import com.dbms.ecommerceplatform.assets.JWTToken;
import com.dbms.ecommerceplatform.assets.RequestLogin;
import com.dbms.ecommerceplatform.service.AccountService;
import com.dbms.ecommerceplatform.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final LoginService loginService;

    private final AccountService accountService;

    public UserController(LoginService loginService, AccountService accountService) {
        this.loginService = loginService;
        this.accountService = accountService;
    }

    @PostMapping("/api/user/create")
    public ResponseEntity<String> createAccount(@RequestBody AccountDetails accountDetails) {
        return ResponseEntity.ok(accountService.createAccount(accountDetails));
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<JWTToken> login(@RequestBody RequestLogin requestLogin) {
        return ResponseEntity.ok(loginService.login(requestLogin));
    }

    @PutMapping("/api/user/rest")
    public ResponseEntity<String> resetPassword(@RequestParam String username, @RequestParam String newPassword) {
        return ResponseEntity.ok(accountService.resetAccountPassword(username, newPassword));
    }

    @ExceptionHandler(InvalidityException.class)
    public ResponseEntity<String> handle(InvalidityException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
