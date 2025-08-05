package com.salesSystem.domain.dtos.user;

import com.salesSystem.domain.enums.UserRole;
import com.salesSystem.domain.models.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;

public record SellerRegisterDto(

        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String phone,
        @NotBlank
        String document,
        @Email
        String email,
        @NotBlank
        // @Pattern(
        //        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
        //       message = "A senha deve conter no mínimo 8 caracteres, incluindo letra maiúscula, minúscula, número e símbolo."
        //)
        String password,
        @NotNull
        Address address

) {
}
