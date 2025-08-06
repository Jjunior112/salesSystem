package com.salesSystem.domain.dtos.client;

import com.salesSystem.domain.models.Address;
import com.salesSystem.domain.models.CartItem;
import com.salesSystem.domain.models.Client;

import java.util.List;
import java.util.UUID;

public record ClientListDto(UUID id, String firstName, String lastName, String phone, Address address,
                            boolean isActive) {

    public ClientListDto(Client client) {
        this(client.getId(), client.getFirstName(), client.getLastName(), client.getPhone(), client.getAddress(), client.isActive());
    }
}
