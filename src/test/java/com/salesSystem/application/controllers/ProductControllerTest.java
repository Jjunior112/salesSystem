package com.salesSystem.application.controllers;

import com.salesSystem.application.services.ProductService;
import com.salesSystem.domain.dtos.product.ProductListDto;
import com.salesSystem.domain.dtos.product.ProductRegisterDto;
import com.salesSystem.domain.enums.ProductCategory;
import com.salesSystem.domain.models.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters

public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ProductRegisterDto> registerDtoJson;

    @Autowired
    private JacksonTester<ProductListDto> listDtoJson;

    @Mock
    private ProductService productService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        ProductService productServiceMock() {
            return Mockito.mock(ProductService.class);
        }
    }

    @Test
    @DisplayName("Deveria devolver response 400 quando informações inválidas")
    @WithMockUser
    void createProductCenario1() throws Exception {
        var response = mvc.perform(post("/products")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver response 200 quando informações corretas")
    @WithMockUser
    void createProductCenario2() throws Exception {

        var productCategory = ProductCategory.CLOTHES;

        var registerProduct = new ProductRegisterDto("name", "description", productCategory, "brand", 5, BigDecimal.valueOf(150));

        var product = new Product(registerProduct);

        var listProduct = new ProductListDto(26L, "name", productCategory, "brand", 5, BigDecimal.valueOf(150));

        when(productService.createProduct(any())).thenReturn(product);

        var response =
                mvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(registerDtoJson
                                        .write(registerProduct)
                                        .getJson()
                                )
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var expectedJson = listDtoJson.write(listProduct).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);

    }

    @Test
    @DisplayName("Deveria devolver response 200 quando informações forem requisitadas")
    @WithMockUser
    void getProductCenario1() throws Exception {

        var response = mvc.perform(get("/products")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deveria devolver response 404 quando produto não existe")
    @WithMockUser
    void getProductCenario2() throws Exception {

        var response = mvc.perform(get("/products/1")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("Deveria devolver response 204 quando informações deletadas")
    @WithMockUser
    void removeProductCenario1() throws Exception {

        var productCategory = ProductCategory.CLOTHES;

        var registerProduct = new ProductRegisterDto("name", "description", productCategory, "brand", 5, BigDecimal.valueOf(150));

        var product = new Product(registerProduct);

        var response = mvc.perform(delete("/products/1")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}




