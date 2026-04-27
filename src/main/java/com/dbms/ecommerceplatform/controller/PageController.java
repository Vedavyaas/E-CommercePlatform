package com.dbms.ecommerceplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping({"/", "/login"})
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/dashboard/customer")
    public String customerDashboard() {
        return "dashboard-customer";
    }

    @GetMapping("/dashboard/vendor")
    public String vendorDashboard() {
        return "dashboard-vendor";
    }

    @GetMapping("/dashboard/admin")
    public String adminDashboard() {
        return "dashboard-admin";
    }
}
