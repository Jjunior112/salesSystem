package com.salesSystem.domain.dtos.user;

import com.salesSystem.domain.enums.UserRole;
import com.salesSystem.domain.models.Address;
import com.salesSystem.domain.models.Seller;
import com.salesSystem.domain.models.User;

import java.util.UUID;

public record SellerListDto(UUID id, String firstName, String lastName, Address address, UserRole role) {
    public SellerListDto(Seller seller) {
        this(seller.getId(), seller.getFirstName(), seller.getLastName(), seller.getAddress(), seller.getRole());
    }


}
