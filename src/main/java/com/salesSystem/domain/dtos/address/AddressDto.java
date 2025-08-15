package com.salesSystem.domain.dtos.address;

import jakarta.validation.constraints.NotBlank;


public record AddressDto(
        @NotBlank
        String street,

        @NotBlank
        String neighborhood,

        @NotBlank
        String zipCode,

        @NotBlank
        String city,

        @NotBlank
        String state,

        String complement,

        String number
) {
}