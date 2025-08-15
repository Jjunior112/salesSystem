package com.salesSystem.domain.dtos.user;

import com.salesSystem.domain.dtos.address.EditAddressDto;
import com.salesSystem.domain.models.Address;

public record EditInfoUserDto(String phone, EditAddressDto address) {
}
