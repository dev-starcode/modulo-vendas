package com.starcode.erp_vendas_caixa.domain.enums;

import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.Error;

public enum TypeCashierMovements {
    IN("in"),
    OUT("out");

    private final String value;

    TypeCashierMovements(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TypeCashierMovements fromString(final String text) {
        for (TypeCashierMovements status : TypeCashierMovements.values()) {
            if (status.value.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw DomainException.with(Error.create("UnprocessableEntity", "Type inválido: "+text));
    }
}
