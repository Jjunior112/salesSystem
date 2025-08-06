package com.salesSystem.infra.repositories;

import com.salesSystem.domain.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {



}
