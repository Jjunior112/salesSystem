package com.salesSystem.application.services;

import com.salesSystem.domain.dtos.product.ProductRegisterDto;
import com.salesSystem.domain.enums.ProductCategory;
import com.salesSystem.domain.models.Product;
import com.salesSystem.infra.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        ProductRegisterDto newProduct = new ProductRegisterDto("name", "description", ProductCategory.CLOTHES, "brand", 10, BigDecimal.valueOf(150));

        product = new Product(newProduct);

        product.setId(1L);
    }

    @Test
    @DisplayName("Deve retornar o produto quando informações forem enviadas corretamente")
    void createProductCase1() {

        //arrange

        ProductRegisterDto newProduct = new ProductRegisterDto("name", "description", ProductCategory.CLOTHES, "brand", 10, BigDecimal.valueOf(150));

        when(repository.save(any())).thenReturn(product);

        // act

        Product result = productService.createProduct(newProduct);

        //assert

        verify(repository, times(1)).save(any());


        assertEquals(newProduct.productName(), result.getProductName(),
                "O nome do produto retornado deve ser igual ao enviado");
    }

    @Test
    @DisplayName("Deveria retornar o produto corretamente")
    void findProductByIdCase1() {
        //arrange

        Long id = 1L;

        //act

        Product result = productService.findById(id);

        //assert

        verify(repository, times(1)).getReferenceById(any());

        assertEquals(id, product.getId());

    }

    @Test
    @DisplayName("Não deveria retornar o produto corretamente")
    void findProductByIdCase2() {
        //arrange

        Long id = 2L;

        //act

        Product result = productService.findById(id);

        //assert

        verify(repository, times(1)).getReferenceById(any());

        assertNotEquals(id, product.getId());

    }

    @Test
    @DisplayName("Deveria excluir o produto corretamente")
    void deleteProductCase1() {
        //arrange

        Long id = 1L;

        when(productService.findById(any())).thenReturn(product);

        //act

        productService.deleteProduct(id);

        //assert

        verify(repository, times(1)).delete(any(Product.class));

    }

    @Test
    @DisplayName("Não deveria excluir o produto corretamente")
    void deleteProductCase2() {
        //arrange

        Long id = 1L;

        //act

        productService.deleteProduct(id);

        //assert

        verify(repository, times(0)).delete(any(Product.class));

    }

}