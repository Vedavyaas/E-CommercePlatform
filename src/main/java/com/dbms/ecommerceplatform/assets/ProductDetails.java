package com.dbms.ecommerceplatform.assets;

import java.math.BigDecimal;

public record ProductDetails(Long id, String name, String description, BigDecimal price, Integer stock) {
}
