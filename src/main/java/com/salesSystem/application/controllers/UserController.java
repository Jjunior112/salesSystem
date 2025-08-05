package com.salesSystem.application.controllers;

import com.salesSystem.domain.dtos.user.*;
import com.salesSystem.domain.models.Seller;
import com.salesSystem.domain.models.User;
import com.salesSystem.application.services.TokenService;
import com.salesSystem.application.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {


    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    private UserService userService;

    public UserController(AuthenticationManager authenticationManager, TokenService tokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDto login) {

        var authToken = new UsernamePasswordAuthenticationToken(login.email(), login.password());

        var authentication = authenticationManager.authenticate(authToken);

        var tokenJwt = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new JwtDto(tokenJwt));

    }

    @PostMapping("/adminRegister")
    @SecurityRequirement(name = "bearer-key")

    public ResponseEntity<AdminListDto> adminRegister(@RequestBody @Valid AdminRegisterDto register) {

        User response = userService.createUserAdmin(register);

        return ResponseEntity.ok().body(new AdminListDto(response));
    }

    @PostMapping("/sellerRegister")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<SellerListDto> sellerRegister(@RequestBody @Valid SellerRegisterDto register) {

        Seller response = userService.createUserSeller(register);

        return ResponseEntity.ok().body(new SellerListDto(response));

    }

    @GetMapping()
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<UserListDto>> getAllUsers(@PageableDefault(sort = "firstName", size = 10) @RequestParam(name = "role", required = false) Pageable pagination) {

        Page<UserListDto> response = userService.findAllUsers(pagination);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")

    public ResponseEntity inactiveUser(@PathVariable UUID id) {
        userService.inactiveUser(id);

        return ResponseEntity.noContent().build();
    }


}
