package com.salesSystem.domain.enums;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    CLIENTE("ROLE_CLIENTE"),
    VENDEDOR("ROLE_VENDEDOR"),
    ENTREGADOR("ROLE_ENTREGADOR");
    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
