package com.starcode.erp_vendas_caixa.domain.validation.handler;

import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.Error;
import com.starcode.erp_vendas_caixa.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error error) {
        throw DomainException.with(List.of(error));
    }

//    @Override
//    public ValidationHandler append(final ValidationHandler handler) {
//        throw DomainException.with(handler.getErrors());
//    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }

    @Override
    public ValidationHandler validate(final Validation validation) {
        try {
            validation.validate();
        }catch (final Exception e){
            throw DomainException.with(List.of(new Error("",e.getMessage())));
        }
        return this;
    }
}
