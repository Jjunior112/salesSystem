package com.salesSystem.domain.dtos.client;

import com.salesSystem.domain.dtos.address.AddressDto;
import com.salesSystem.domain.dtos.address.EditAddressDto;
import com.salesSystem.domain.models.Address;

public record EditClientInfo(String phone, EditAddressDto address) {
}
