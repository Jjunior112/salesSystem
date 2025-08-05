package com.salesSystem.domain.dtos.user;


import com.salesSystem.domain.enums.UserRole;
import com.salesSystem.domain.models.User;

import java.util.UUID;

public record AdminListDto(UUID id, String email, UserRole role) {

    public AdminListDto(User user) {
        this(user.getId(), user.getEmail(), user.getRole());
    }
}
