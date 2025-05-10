package com.starcode.erp_vendas_caixa.domain.enums;

import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.Error;

public enum StatusSale {
    PAID("paid"),
    PENDING("pending"),
    CANCELED("canceled");

    private final String value;

    StatusSale(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static StatusSale fromString(final String text) {
        for (StatusSale status : StatusSale.values()) {
            if (status.value.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw DomainException.with(Error.create("UnprocessableEntity", "Status inválido: " + text));
    }
}
