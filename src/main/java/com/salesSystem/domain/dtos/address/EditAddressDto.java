package com.salesSystem.domain.dtos.address;

public record EditAddressDto(
        String street,
        String neighborhood,
        String zipCode,
        String city,
        String state,
        String complement,
        String number
) {
}