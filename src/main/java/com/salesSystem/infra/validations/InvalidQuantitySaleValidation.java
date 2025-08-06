package com.salesSystem.infra.validations;

import com.salesSystem.application.services.ProductService;
import com.salesSystem.domain.dtos.cart.CartRegisterDto;
import com.salesSystem.domain.dtos.sale.SaleRegisterDto;
import com.salesSystem.domain.models.Product;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class InvalidQuantitySaleValidation implements Validation {

    private final ProductService productService;

    public InvalidQuantitySaleValidation(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void validate(SaleRegisterDto sale) {

        for (CartRegisterDto item : sale.cart()) {
            Product product = productService.findById(item.productId());

            if (item.quantity() <= 0) {
                throw new ValidationException("Produto não pode ser 0!");
            }

        }


    }
}
