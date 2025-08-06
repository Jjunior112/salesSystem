package com.salesSystem.infra.validations;

import com.salesSystem.domain.dtos.sale.SaleRegisterDto;

public interface Validation {
    void validate(SaleRegisterDto sale);
}
