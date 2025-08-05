package com.salesSystem.domain.dtos.sale;

import com.salesSystem.domain.models.CartItem;
import com.salesSystem.domain.models.Client;
import com.salesSystem.domain.models.Sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ListSalesDto(Long saleId, UUID buyerId, UUID sellerId, List<CartItem> cart, BigDecimal totalAmount,
                           LocalDateTime timestamp) {

    public ListSalesDto(Sale sale) {
        this(sale.getId(), sale.getBuyer().getId(), sale.getSeller().getId(), sale.getCartItems(), sale.getCartTotal(), sale.getTimestamp());
    }


}
