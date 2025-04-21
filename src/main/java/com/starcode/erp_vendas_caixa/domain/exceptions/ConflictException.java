package com.starcode.erp_vendas_caixa.domain.exceptions;

import com.starcode.erp_vendas_caixa.domain.validation.Error;
import java.util.List;

public class ConflictException extends DomainException {

    protected ConflictException(String message, List<Error> errors) {
        super(message, errors);
    }

    public static ConflictException with(final Error error) {
        return new ConflictException(error.message(), List.of(error));
    }
}
