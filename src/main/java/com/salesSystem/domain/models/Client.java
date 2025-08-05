package com.salesSystem.domain.models;

import com.salesSystem.domain.dtos.client.ClientRegisterDto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private UUID id;

    @Column(unique = false, nullable = false)

    private String firstName;

    @Column(unique = false, nullable = false)

    private String lastName;

    @Column(unique = false, nullable = false)

    private String phone;

    @Column(unique = true, nullable = false)

    private String document;

    @Embedded
    private Address address;

    boolean isActive = true;

    public Client(ClientRegisterDto client) {
        this.firstName = client.firstName();
        this.lastName = client.lastName();
        this.phone = client.phone();
        this.document = client.document();
        this.address = client.address();

    }

    public void inactiveClient() {
        if (isActive) {
            this.isActive = false;
        }
    }

}
