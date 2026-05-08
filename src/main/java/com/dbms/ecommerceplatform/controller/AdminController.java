package com.dbms.ecommerceplatform.controller;

import com.dbms.ecommerceplatform.repository.SystemLogEntity;
import com.dbms.ecommerceplatform.repository.SystemLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final SystemLogRepository systemLogRepository;

    public AdminController(SystemLogRepository systemLogRepository) {
        this.systemLogRepository = systemLogRepository;
    }

    @GetMapping("/logs")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<List<SystemLogEntity>> getLogs() {
        return ResponseEntity.ok(systemLogRepository.findAllByOrderByTimestampDesc());
    }
}
