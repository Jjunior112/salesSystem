package com.salesSystem.application.services;

import com.salesSystem.domain.dtos.user.AdminRegisterDto;
import com.salesSystem.domain.dtos.user.SellerListDto;
import com.salesSystem.domain.dtos.user.SellerRegisterDto;
import com.salesSystem.domain.dtos.user.UserListDto;
import com.salesSystem.domain.enums.UserRole;
import com.salesSystem.domain.models.Sale;
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

    public Page<UserListDto> findAllUsers(Pageable pagination) {

        return userRepository.findAllByIsActiveTrue(pagination).map(UserListDto::new);
    }

    public Seller findSellerById(UUID id) {

        Seller seller = sellerRepository.getReferenceById(id);

        return seller;
    }

    public void inactiveUser(UUID id) {
        User user = userRepository.getReferenceById(id);

        user.inactiveUser();

    }

    public boolean existUser(String email) {
        var user = userRepository.findByEmail(email);

        if (user == null) {
            return false;
        }

        return true;
    }
}
