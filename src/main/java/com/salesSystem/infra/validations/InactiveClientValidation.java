package com.salesSystem.infra.validations;

import com.salesSystem.domain.dtos.sale.SaleRegisterDto;
import com.salesSystem.infra.repositories.ClientRepository;
import com.salesSystem.infra.repositories.SellerRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class InactiveClientValidation implements Validation {

    private final ClientRepository clientRepository;

    public InactiveClientValidation(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @Override
    public void validate(SaleRegisterDto sale) {
        if (sale.buyerId() == null) {
            return;
        }

        boolean isInactiveClient = clientRepository.findIsActiveById(sale.buyerId());

        if (!isInactiveClient) {
            throw new ValidationException(" Não foi possível realizar venda! Cliente está inativo! ");
        }

    }
}
