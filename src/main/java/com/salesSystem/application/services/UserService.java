package com.salesSystem.application.services;

import com.salesSystem.domain.dtos.user.*;
import com.salesSystem.domain.enums.UserRole;
import com.salesSystem.domain.models.Seller;
import com.salesSystem.domain.models.User;
import com.salesSystem.infra.repositories.SellerRepository;
import com.salesSystem.infra.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service

public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private SellerRepository sellerRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, SellerRepository sellerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    @Transactional
    public User createUserAdmin(AdminRegisterDto register) {

        if (userRepository.findByEmail(register.email()) != null) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(register.password());

        User user = new User(
                register.email(),
                encryptedPassword,
                UserRole.ADMIN
        );

        return userRepository.save(user);

    }

    @Transactional
    public Seller createUserSeller(SellerRegisterDto register) {

        if (userRepository.findByEmail(register.email()) != null) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(register.password());

        Seller seller = new Seller(
                register.firstName(),
                register.lastName(),
                register.phone(),
                register.document(),
                register.address(),
                register.email(),
                encryptedPassword,
                UserRole.VENDEDOR
        );

        return userRepository.save(seller);
    }

    @Transactional
    public Seller editUserInfo(UUID id, EditInfoUserDto editInfo) {
        Seller seller = findSellerById(id);

        seller.editInfo(editInfo);

        return seller;
    }

    public Page<UserListDto> findAllUsers(Pageable pagination, UserRole role) {
        if (role == null) {
            return userRepository.findAll(pagination).map(UserListDto::new);
        } else {
            return userRepository.findByRole(role, pagination).map(UserListDto::new);
        }
    }

    public Page<SellerListDto> findAllSellers(Pageable pagination) {

        return sellerRepository.findAll(pagination).map(SellerListDto::new);
    }

    public Seller findSellerById(UUID id) {
        return sellerRepository.getReferenceById(id);
    }

    @Transactional
    public void inactiveUser(UUID id) {
        User user = userRepository.getReferenceById(id);

        user.inactiveUser();

    }

    @Transactional
    public void reactiveUser(UUID id) {
        User user = userRepository.getReferenceById(id);

        user.reactiveUser();

    }

    public boolean existUser(String email) {
        var user = userRepository.findByEmail(email);

        return user != null;
    }
}
