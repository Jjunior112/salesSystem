package com.salesSystem.application.controllers;

import com.salesSystem.domain.dtos.sale.ListSalesDto;
import com.salesSystem.domain.dtos.sale.SaleRegisterDto;
import com.salesSystem.application.services.SaleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/sales")
@SecurityRequirement(name = "bearer-key")
public class SaleController {

    private SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ListSalesDto> postSale(@RequestBody @Valid SaleRegisterDto sale, UriComponentsBuilder uriBuilder) {
        var response = service.createSale(sale);
        var uri = uriBuilder.path("sales/{id}").buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(new ListSalesDto(response));

    }

    @GetMapping

    public ResponseEntity<Page<ListSalesDto>> getAllSales(@PageableDefault(sort = "timestamp", size = 10) Pageable pagination) {
        var sales = service.findAllSales(pagination);

        return ResponseEntity.ok(sales);
    }

    @GetMapping("/{id}")

    public ResponseEntity<ListSalesDto> getSaleById(@PathVariable Long id) {
        var sale = service.findSaleById(id);

        return ResponseEntity.ok(new ListSalesDto(sale));

    }

    @DeleteMapping("/{id}")

    public ResponseEntity deleteSale(Long id) {
        service.DeleteFullSale(id);

        return ResponseEntity.noContent().build();
    }


}
