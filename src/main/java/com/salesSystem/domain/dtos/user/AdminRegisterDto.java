package com.salesSystem.domain.dtos.user;

import com.salesSystem.domain.enums.UserRole;

public record AdminRegisterDto(String email, String password) {
}
