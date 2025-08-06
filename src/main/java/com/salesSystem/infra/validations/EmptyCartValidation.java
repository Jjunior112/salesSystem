package com.salesSystem.infra.validations;

import com.salesSystem.application.services.ProductService;
import com.salesSystem.domain.dtos.cart.CartRegisterDto;
import com.salesSystem.domain.dtos.sale.SaleRegisterDto;
import com.salesSystem.domain.models.Product;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmptyCartValidation implements Validation {

    private final ProductService productService;

    public EmptyCartValidation(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void validate(SaleRegisterDto sale) {

        List<CartRegisterDto> cart = sale.cart();

        List<String> errors = new ArrayList<>();

        if (cart == null || cart.isEmpty()) {

            throw new ValidationException("Carrinho vazio!");

        }
    }
}
