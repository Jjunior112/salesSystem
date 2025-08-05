package com.salesSystem.domain.dtos.client;

import com.salesSystem.domain.models.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientRegisterDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String phone,
        @NotBlank
        String document,
        @NotNull
        Address address
) {
}
