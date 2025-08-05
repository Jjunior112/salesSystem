package com.salesSystem.domain.models;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    private Long productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer quantity;

    public CartItem(Product product, Integer quantity) {
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.productPrice = product.getPrice();
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }
}