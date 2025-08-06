package com.salesSystem.infra.validations;

import com.salesSystem.domain.dtos.sale.SaleRegisterDto;
import com.salesSystem.infra.repositories.SellerRepository;
import com.salesSystem.infra.repositories.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class InactiveSellerValidation implements Validation {

    private final UserRepository userRepository;

    public InactiveSellerValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void validate(SaleRegisterDto sale) {
        if (sale.sellerId() == null) {
            return;
        }

        boolean isInactiveSeller = userRepository.findIsActiveById(sale.sellerId());

        if (!isInactiveSeller) {
            throw new ValidationException(" Não foi possível realizar venda! Vendedor está inativo! ");
        }

    }
}
