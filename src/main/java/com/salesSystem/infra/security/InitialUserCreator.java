package com.salesSystem.infra.security;

import com.salesSystem.domain.dtos.user.AdminRegisterDto;
import com.salesSystem.application.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitialUserCreator {

    @Value("${api.security.user}")
    private String username;
    @Value("${api.security.password}")
    private String password;

    private UserService userService;

    public InitialUserCreator(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createInitialUser() {
        if (!userService.existUser(username)) {
            AdminRegisterDto register = new AdminRegisterDto(
                    username,
                    password
            );
            userService.createUserAdmin(register);
        }

    }
}
