package com.dbms.ecommerceplatform.security;

import com.dbms.ecommerceplatform.assets.Role;
import com.dbms.ecommerceplatform.repository.CustomerEntity;
import com.dbms.ecommerceplatform.repository.CustomerRepository;
import com.dbms.ecommerceplatform.repository.ProductEntity;
import com.dbms.ecommerceplatform.repository.ProductRepository;
import com.dbms.ecommerceplatform.repository.UserDetailsEntity;
import com.dbms.ecommerceplatform.repository.UserDetailsRepository;
import com.dbms.ecommerceplatform.repository.VendorEntity;
import com.dbms.ecommerceplatform.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;

    public DataSeeder(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder,
                      CustomerRepository customerRepository, VendorRepository vendorRepository, ProductRepository productRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userDetailsRepository.count() == 0) {
            UserDetailsEntity userDetailsEntity1 = new UserDetailsEntity("customer", "customer@gmail.com", passwordEncoder.encode("123"), Role.CUSTOMER);
            UserDetailsEntity userDetailsEntity2 = new UserDetailsEntity("vendor", "vendor@gmail.com", passwordEncoder.encode("123"), Role.VENDOR);
            UserDetailsEntity userDetailsEntity3 = new UserDetailsEntity("admin", "admin@gmail.com", passwordEncoder.encode("123"), Role.ADMIN);

            userDetailsRepository.saveAll(List.of(userDetailsEntity1, userDetailsEntity2, userDetailsEntity3));

            CustomerEntity customerEntity = new CustomerEntity(userDetailsEntity1, "John", "Doe", "+1234567890");
            customerRepository.save(customerEntity);

            VendorEntity vendorEntity = new VendorEntity(userDetailsEntity2, "TechNova Store", "Best tech products on the internet.");
            vendorRepository.save(vendorEntity);

            List<ProductEntity> products = List.of(
                    new ProductEntity(vendorEntity, "Wireless Mouse", "Ergonomic wireless mouse with USB-C charging", new BigDecimal("29.99"), 150),
                    new ProductEntity(vendorEntity, "Mechanical Keyboard", "RGB mechanical keyboard with blue switches", new BigDecimal("89.99"), 75),
                    new ProductEntity(vendorEntity, "4K Monitor", "32-inch 4K UHD monitor with HDR support", new BigDecimal("349.99"), 30),
                    new ProductEntity(vendorEntity, "Gaming Headset", "Noise-cancelling gaming headset with surround sound", new BigDecimal("59.99"), 100),
                    new ProductEntity(vendorEntity, "USB-C Hub", "7-in-1 USB-C hub with HDMI and card reader", new BigDecimal("39.99"), 200),
                    new ProductEntity(vendorEntity, "Laptop Stand", "Adjustable aluminum laptop stand", new BigDecimal("24.99"), 120),
                    new ProductEntity(vendorEntity, "Webcam 1080p", "HD webcam with built-in microphone", new BigDecimal("45.00"), 80),
                    new ProductEntity(vendorEntity, "External SSD 1TB", "Portable 1TB NVMe SSD", new BigDecimal("129.99"), 50),
                    new ProductEntity(vendorEntity, "Bluetooth Speaker", "Waterproof portable bluetooth speaker", new BigDecimal("49.99"), 90),
                    new ProductEntity(vendorEntity, "Smartwatch", "Fitness tracker smartwatch with heart rate monitor", new BigDecimal("99.99"), 60),
                    new ProductEntity(vendorEntity, "Wireless Earbuds", "True wireless earbuds with noise cancellation", new BigDecimal("79.99"), 110),
                    new ProductEntity(vendorEntity, "Smartphone Stand", "Foldable desk stand for smartphones", new BigDecimal("12.99"), 300),
                    new ProductEntity(vendorEntity, "Power Bank 20000mAh", "High capacity portable charger", new BigDecimal("34.99"), 140),
                    new ProductEntity(vendorEntity, "Desk Pad", "Large PU leather desk pad", new BigDecimal("19.99"), 160),
                    new ProductEntity(vendorEntity, "USB Microphone", "Studio condenser USB microphone for streaming", new BigDecimal("65.00"), 40),
                    new ProductEntity(vendorEntity, "Ergonomic Chair", "Mesh office chair with lumbar support", new BigDecimal("199.99"), 20),
                    new ProductEntity(vendorEntity, "Ring Light", "10-inch LED ring light with tripod stand", new BigDecimal("29.99"), 85),
                    new ProductEntity(vendorEntity, "Cable Organizer", "Magnetic cable clips for desk management", new BigDecimal("9.99"), 400),
                    new ProductEntity(vendorEntity, "Surge Protector", "8-outlet surge protector power strip", new BigDecimal("22.50"), 130),
                    new ProductEntity(vendorEntity, "Monitor Arm", "Single monitor desk mount", new BigDecimal("49.99"), 65),
                    new ProductEntity(vendorEntity, "Graphic Tablet", "Drawing tablet with battery-free stylus", new BigDecimal("59.99"), 45),
                    new ProductEntity(vendorEntity, "Wi-Fi Router", "Dual-band gigabit wireless router", new BigDecimal("89.99"), 55),
                    new ProductEntity(vendorEntity, "Ethernet Cable 50ft", "Cat 6 ethernet patch cable", new BigDecimal("15.99"), 250),
                    new ProductEntity(vendorEntity, "HDMI Cable 10ft", "4K HDR compatible HDMI cable", new BigDecimal("11.99"), 300),
                    new ProductEntity(vendorEntity, "MicroSD Card 128GB", "High speed memory card with adapter", new BigDecimal("24.99"), 180)
            );

            productRepository.saveAll(products);
        }
    }
}
