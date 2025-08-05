package com.salesSystem.domain.dtos.product;

import com.salesSystem.domain.enums.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRegisterDto(
        @NotBlank
        String productName,
        String productDescription,

        @NotNull
        ProductCategory productCategory,

        @NotBlank
        String brand,

        @NotNull
        Integer balance,
        @NotNull
        BigDecimal price

) {

}
