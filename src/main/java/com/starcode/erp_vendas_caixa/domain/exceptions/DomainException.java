package com.starcode.erp_vendas_caixa.domain.exceptions;

import com.starcode.erp_vendas_caixa.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {

    private final List<Error> errors;
    protected  DomainException(final String error, final List<Error> errors) {
        super(error);
        this.errors = errors;
    }

    public static DomainException with(final Error errors) {
        return new DomainException(errors.error(), List.of(errors));
    }

    public static DomainException with(final List<Error> errors) {
        return new DomainException("", errors);
    }

    public List<Error> getErrors() {
        return errors;
    }

    public String getError() {
        return errors.getFirst().message();
    }

    public String getTypeError(){
        return this.errors.getFirst().error();
    }
}