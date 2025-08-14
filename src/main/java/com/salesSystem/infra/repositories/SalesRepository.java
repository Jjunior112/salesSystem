package com.salesSystem.infra.repositories;

import com.salesSystem.domain.dtos.sale.ListSalesDto;
import com.salesSystem.domain.models.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sale, Long> {

    @Override
    Page<Sale> findAll(Pageable pagination);
}

