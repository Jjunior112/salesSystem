package com.salesSystem.domain.dtos.user;

import com.salesSystem.domain.enums.UserRole;
import com.salesSystem.domain.models.User;

import java.util.UUID;

public record UserListDto(UUID id, String email, UserRole role, boolean isActive) {
    public UserListDto(User user) {
        this(user.getId(), user.getEmail(), user.getRole(), user.isActive());
    }
}
