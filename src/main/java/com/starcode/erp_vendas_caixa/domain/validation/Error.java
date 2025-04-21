package com.starcode.erp_vendas_caixa.domain.validation;

public record Error(String error, String message) {
    public static Error create(final String error, final String message){
        return new Error(error, message);
    }
}
