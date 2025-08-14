package com.salesSystem.domain.dtos.sale;

import com.salesSystem.domain.models.CartItem;
import com.salesSystem.domain.models.Sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ListSalesDto(
        Long saleId,
        UUID buyerId,
        String buyerFirstName,
        String buyerLastName,
        UUID sellerId,
        String sellerFirstName,
        String sellerLastName,
        List<CartItem> cartItems,

        BigDecimal totalAmount,
        LocalDateTime timestamp
) {
    // Construtor para instanciar a partir da entidade Sale
    public ListSalesDto(Sale sale) {
        this(
                sale.getId(),
                sale.getBuyer().getId(),
                sale.getBuyer().getFirstName(),
                sale.getBuyer().getLastName(),
                sale.getSeller().getId(),
                sale.getSeller().getFirstName(),
                sale.getSeller().getLastName(),
                sale.getCartItems(),
                sale.getCartTotal(),
                sale.getTimestamp()
        );
    }
}