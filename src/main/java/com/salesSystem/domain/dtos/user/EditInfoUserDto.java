package com.salesSystem.domain.dtos.user;

import com.salesSystem.domain.models.Address;

public record EditInfoUserDto(String phone, Address address) {
}
