package com.salesSystem.domain.dtos.cart;

import com.salesSystem.domain.models.CartItem;
import com.salesSystem.domain.models.Client;

import java.util.List;

public record CartListDto(List<CartItem> items) {


}
