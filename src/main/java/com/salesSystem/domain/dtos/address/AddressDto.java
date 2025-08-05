package com.salesSystem.domain.dtos.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
public record AddressDto(
        @NotBlank
        String street,

        @NotBlank
        String neighborhood,

        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String zipCode,

        @NotBlank
        String city,

        @NotBlank
        String state,

        String complement,

        String number
) {}