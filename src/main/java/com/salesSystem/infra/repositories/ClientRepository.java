package com.salesSystem.infra.repositories;

import com.salesSystem.domain.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Page<Client> findAllByIsActiveTrue(Pageable pagination);

}
