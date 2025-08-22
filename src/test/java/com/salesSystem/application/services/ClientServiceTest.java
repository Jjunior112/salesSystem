package com.salesSystem.application.services;

import com.salesSystem.domain.dtos.address.AddressDto;
import com.salesSystem.domain.dtos.client.ClientRegisterDto;
import com.salesSystem.domain.models.Address;
import com.salesSystem.domain.models.Client;
import com.salesSystem.infra.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @Autowired
    @InjectMocks
    private ClientService clientService;

    private Client client;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        AddressDto addressDto = new AddressDto("rua", "bairro", "12546888", "cidade", "estado", "complemento", "123");

        Address address = new Address(addressDto);

        ClientRegisterDto clientRegisterDto = new ClientRegisterDto("firstname", "lastname", "12123456789", "111.222.555-55", address);

        client = new Client(clientRegisterDto);

    }

    @Test
    @DisplayName("Deveria registrar o cliente corretamente")
    void createClientCase1() {
        //arrange

        AddressDto addressDto = new AddressDto("rua", "bairro", "12546888", "cidade", "estado", "complemento", "123");

        Address address = new Address(addressDto);

        ClientRegisterDto clientRegisterDto = new ClientRegisterDto("firstname", "lastname", "12123456789", "111.222.555-55", address);

        //act

        Client result = clientService.createClient(clientRegisterDto);

        //assert

        verify(repository, times(1)).save(any());

        assertEquals(clientRegisterDto.firstName(), result.getFirstName(),"O nome do cliente retornado deve ser igual ao enviado");
    }

    @Test
    @DisplayName("Deveria retornar cliente corretamente")
    void findClientByIdCase1() {
        //arrange

        UUID id = UUID.randomUUID();

        System.out.println(id);

        //act

        Client result = clientService.findClientById(id);

        verify(repository, times(1)).getReferenceById(any());

    }
}