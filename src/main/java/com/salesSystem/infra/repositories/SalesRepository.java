package com.salesSystem.infra.repositories;

import com.salesSystem.domain.models.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sale, Long> {

    @Override
    Page<Sale> findAll(Pageable pagination);

}
