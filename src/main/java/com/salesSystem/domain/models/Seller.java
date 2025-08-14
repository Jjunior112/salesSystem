package com.salesSystem.domain.models;

import com.salesSystem.domain.dtos.user.EditInfoUserDto;
import com.salesSystem.domain.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sellers")
@Getter
@Setter

public class Seller extends User {

    public Seller() {
        super();
    }

    private String firstName;

    private String lastName;

    private String phone;

    @Column(unique = true, nullable = false)
    private String document;

    @Embedded
    private Address address;


    public Seller(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String phone, @NotBlank String document, @NotNull Address address, @Email String email, String password, UserRole role) {
        super(email, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.document = document;
        this.address = address;
    }

    public void editInfo(EditInfoUserDto edit) {
        if (edit.phone() != null) {
            this.phone = edit.phone();
        }
        if (edit.address() != null) {
            this.address = edit.address();
        }
    }


}
