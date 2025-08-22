package com.salesSystem.unit.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesSystem.application.controllers.ProductController;
import com.salesSystem.application.services.ProductService;
import com.salesSystem.application.services.TokenService;
import com.salesSystem.application.services.UserService;
import com.salesSystem.domain.dtos.product.ProductRegisterDto;
import com.salesSystem.domain.enums.ProductCategory;
import com.salesSystem.domain.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false) // remove filtros de segurança
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;

    // beans necessários para o Security Filter
    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserService userService;

    //--------------------------------------------
    private Product product;
    private ProductRegisterDto registerDto;

    @BeforeEach
    void setup() {
        registerDto = new ProductRegisterDto(
                "Tênis",
                "Tênis esportivo",
                ProductCategory.CLOTHES,
                "Nike",
                10,
                BigDecimal.valueOf(150)
        );

        product = new Product(registerDto);
        product.setId(1L);
    }

    @Test
    @DisplayName("Deve retornar 201 ao criar um produto")
    @WithMockUser
    void createProduct_success() throws Exception {
        // arrange

        when(productService.createProduct(any(ProductRegisterDto.class)))
                .thenReturn(product);

        // act

        var result = mvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDto)));

        //assert

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productName").value("Tênis"))
                .andExpect(jsonPath("$.brand").value("Nike"));
    }

    @Test
    @DisplayName("Deve retornar 400 ao tentar criar produto com dados inválidos")
    @WithMockUser
    void createProduct_invalidData() throws Exception {
        // arrange

        var invalidDto = new ProductRegisterDto(
                "",
                "desc",
                ProductCategory.CLOTHES,
                "Nike",
                -5,
                null
        );

        // act

        var result = mvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)));

        // assert

        result.andExpect(status().isBadRequest());
    }
}
