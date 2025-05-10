package com.starcode.erp_vendas_caixa.domain.enums;

import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.Error;

public enum StatusCashier {
    OPENED("opened"),
    CLOSED("closed");

    private final String value;

    StatusCashier(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static StatusCashier fromString(final String text) {
        for (StatusCashier status : StatusCashier.values()) {
            if (status.value.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw DomainException.with(Error.create("UnprocessableEntity","Status inválido: "+text));
    }
}
