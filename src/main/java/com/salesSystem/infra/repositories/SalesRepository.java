package com.salesSystem.infra.repositories;

import com.salesSystem.domain.dtos.sale.ListSalesDto;
import com.salesSystem.domain.models.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


public interface SalesRepository extends JpaRepository<Sale, Long> {

    @Override
    Page<Sale> findAll(Pageable pagination);

    Page<Sale> findAllByTimestamp(LocalDateTime timestamp, Pageable pagination);
}

