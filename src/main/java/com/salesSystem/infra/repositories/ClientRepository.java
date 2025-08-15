package com.salesSystem.infra.repositories;

import com.salesSystem.domain.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    @Override
    Page<Client> findAll(Pageable pagination);

    @Query(
            """
                    select c.isActive from Client c
                    where c.id = :id
                    """
    )
    Boolean findIsActiveById(UUID id);

}
