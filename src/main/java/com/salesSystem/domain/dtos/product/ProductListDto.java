package com.salesSystem.domain.dtos.product;

import com.salesSystem.domain.enums.ProductCategory;
import com.salesSystem.domain.models.Product;

import java.math.BigDecimal;

public record ProductListDto(Long id,

                             String productName,
                             ProductCategory productCategory,
                             String productDescription,
                             String brand,
                             Integer balance,
                             BigDecimal price) {

    public ProductListDto(Product product) {
        this(product.getId(), product.getProductName(), product.getProductCategory(), product.getProductDescription(), product.getBrand(), product.getBalance(), product.getPrice());
    }
}
