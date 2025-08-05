package com.salesSystem.application.services;

import com.salesSystem.domain.dtos.cart.UpdateCartDto;
import com.salesSystem.domain.dtos.client.ClientListDto;
import com.salesSystem.domain.dtos.client.ClientRegisterDto;
import com.salesSystem.domain.models.Client;
import com.salesSystem.domain.models.Product;
import com.salesSystem.infra.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ClientService {

    private ClientRepository repository;
    private ProductService productService;

    public ClientService(ClientRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    @Transactional
    public Client createClient(ClientRegisterDto newClient) {
        Client client = new Client(newClient);

        repository.save(client);

        return client;
    }

    public Page<ClientListDto> findAllClients(Pageable pagination) {
        return repository.findAllByIsActiveTrue(pagination).map(ClientListDto::new);
    }

    public Client findClientById(UUID id) {

        Client client = repository.getReferenceById(id);

        return client;
    }

    @Transactional
    public void inactiveClient(UUID id) {
        Client client = repository.getReferenceById(id);

        client.inactiveClient();

    }


}



