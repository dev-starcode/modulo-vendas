package com.starcode.erp_vendas_caixa.domain.validation;

import java.util.List;

public interface ValidationHandler {
    ValidationHandler append(Error error);
//    ValidationHandler append(ValidationHandler handler);
    List<Error> getErrors();
    ValidationHandler validate(Validation validation);
    default boolean hasError(){
        return getErrors() != null && !getErrors().isEmpty();
    }

    public interface Validation {
        void validate();
    }

}
