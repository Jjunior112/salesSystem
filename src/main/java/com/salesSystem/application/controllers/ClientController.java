package com.salesSystem.application.controllers;

import com.salesSystem.domain.dtos.cart.CartListDto;
import com.salesSystem.domain.dtos.cart.UpdateCartDto;
import com.salesSystem.domain.dtos.client.ClientListDto;
import com.salesSystem.domain.dtos.client.ClientRegisterDto;
import com.salesSystem.domain.dtos.client.EditClientInfo;
import com.salesSystem.domain.models.Client;
import com.salesSystem.application.services.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;


@RestController
@RequestMapping("/clients")
@SecurityRequirement(name = "bearer-key")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientListDto> postClient(@RequestBody @Valid ClientRegisterDto newClient, UriComponentsBuilder uriBuilder) {

        Client client = clientService.createClient(newClient);

        var uri = uriBuilder.path("/client/{id}").buildAndExpand(client.getId()).toUri();

        return ResponseEntity.created(uri).body(new ClientListDto(client));

    }

    @GetMapping

    public ResponseEntity<Page<ClientListDto>> getAll(@PageableDefault(sort = "firstName", size = 10) Pageable pagination) {

        var clients = clientService.findAllClients(pagination);

        return new ResponseEntity<>(clients, HttpStatus.OK);

    }

    @GetMapping("/{id}")

    public ResponseEntity<ClientListDto> getById(@PathVariable UUID id) {

        var client = clientService.findClientById(id);

        return new ResponseEntity<>(new ClientListDto(client), HttpStatus.OK);
    }

    @PutMapping("/{id}")

    public ResponseEntity<ClientListDto> editClientInfo(@PathVariable UUID id, @RequestBody EditClientInfo edit) {
        var response = clientService.editClientInfo(id, edit);

        return new ResponseEntity<>(new ClientListDto(response), HttpStatus.OK);
    }

    @PutMapping("/reactive/{id}")

    public ResponseEntity<ClientListDto> reactiveClient(@PathVariable UUID id) {

        var client = clientService.reactiveClient(id);

        return new ResponseEntity<>(new ClientListDto(client), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")

    public ResponseEntity deleteClient(@PathVariable UUID id) {

        clientService.inactiveClient(id);

        return ResponseEntity.noContent().build();
    }


}
