package com.starcode.erp_vendas_caixa.application.usecases;

public interface UseCase<INPUT, OUTPUT> {
    OUTPUT execute(final INPUT data);
}
