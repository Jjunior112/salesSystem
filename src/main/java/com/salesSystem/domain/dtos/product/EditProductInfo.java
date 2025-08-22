package com.salesSystem.domain.dtos.product;

import com.salesSystem.domain.enums.ProductCategory;

import java.math.BigDecimal;

public record EditProductInfo(
        String productName,
        String productDescription,
        ProductCategory productCategory,
        String brand,
        Integer balance,
        BigDecimal price) {
}
