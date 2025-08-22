package com.salesSystem.application.controllers;

import com.salesSystem.domain.dtos.client.ClientListDto;
import com.salesSystem.domain.dtos.user.*;
import com.salesSystem.domain.enums.UserRole;
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
    public ResponseEntity<JwtDto> login(@RequestBody @Valid LoginDto login) {

        var authToken = new UsernamePasswordAuthenticationToken(login.email(), login.password());

        var authentication = authenticationManager.authenticate(authToken);

        var user = (User) authentication.getPrincipal();

        var tokenJwt = tokenService.generateToken(user);

        return ResponseEntity.ok(
                new JwtDto(tokenJwt, user.getRole().name())
        );
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

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<UserListDto>> getAllUsers(
            @PageableDefault(sort = "email", size = 10) Pageable pagination,
            @RequestParam(name = "role", required = false) UserRole role) {

        Page<UserListDto> response = userService.findAllUsers(pagination, role);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/sellers")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<SellerListDto>> getAllSellers(
            @PageableDefault(sort = "firstName", size = 10) Pageable pagination
    ) {

        Page<SellerListDto> response = userService.findAllSellers(pagination);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/sellers/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<SellerListDto> getSellerById(@PathVariable UUID id) {

        var response = userService.findSellerById(id);

        return new ResponseEntity<>(new SellerListDto(response), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserListDto> getUserById(@PathVariable UUID id) {

        var response = userService.findUserById(id);

        return new ResponseEntity<>(new UserListDto(response), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<SellerListDto> editInfoUser(@PathVariable UUID id, @RequestBody EditInfoUserDto edit) {

        var response = userService.editUserInfo(id, edit);

        return new ResponseEntity<>(new SellerListDto(response), HttpStatus.OK);
    }

    @PutMapping("/reactive/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity reactiveUser(@PathVariable UUID id) {

        userService.reactiveUser(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")

    public ResponseEntity inactiveUser(@PathVariable UUID id) {
        userService.inactiveUser(id);

        return ResponseEntity.noContent().build();
    }


}
