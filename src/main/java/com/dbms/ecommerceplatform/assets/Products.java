package com.dbms.ecommerceplatform.assets;

import java.math.BigDecimal;

public record Products(Long Id, String name, String description, BigDecimal price, Integer stock, String storeName, String storeDescription) {
}
