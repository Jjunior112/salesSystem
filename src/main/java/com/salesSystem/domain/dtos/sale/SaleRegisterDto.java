package com.salesSystem.domain.dtos.sale;

import com.salesSystem.domain.dtos.cart.CartRegisterDto;
import com.salesSystem.domain.models.CartItem;

import java.util.List;
import java.util.UUID;

public record SaleRegisterDto(
        UUID sellerId,
        UUID buyerId,
        List<CartRegisterDto> cart
) {
}
