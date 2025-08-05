package com.salesSystem.domain.dtos.cart;

import java.util.UUID;

public record UpdateCartDto(Long productId, Integer quantity) {
}
