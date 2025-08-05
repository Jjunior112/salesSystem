package com.salesSystem.application.controllers;

import com.salesSystem.domain.dtos.product.ProductListDto;
import com.salesSystem.domain.dtos.product.ProductRegisterDto;
import com.salesSystem.domain.dtos.product.UpdateProductBalanceDto;
import com.salesSystem.domain.models.Product;
import com.salesSystem.application.services.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/products")
@SecurityRequirement(name = "bearer-key")

public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductListDto> post(@RequestBody @Valid ProductRegisterDto product, UriComponentsBuilder uriBuilder) {

        Product response = productService.createProduct(product);

        var uri = uriBuilder.path("/products/{id}").buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(new ProductListDto(response));

    }

    @GetMapping
    public ResponseEntity<Page<ProductListDto>> getAll(@PageableDefault(size = 10, sort = {"productName"}) Pageable pagination) {

        Page<ProductListDto> products = productService.findAll(pagination);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductListDto> getById(@PathVariable Long id) {

        Product product = productService.findById(id);

        return new ResponseEntity<>(new ProductListDto(product), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductListDto> putProduct(@PathVariable Long id, UpdateProductBalanceDto update) {

        Product response = productService.updateProductBalance(id, update);

        return new ResponseEntity<>(new ProductListDto(response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

}
