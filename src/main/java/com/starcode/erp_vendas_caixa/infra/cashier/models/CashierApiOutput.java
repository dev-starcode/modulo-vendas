package com.starcode.erp_vendas_caixa.infra.cashier.models;


import com.starcode.erp_vendas_caixa.domain.dto.outputs.CashierOutputDTO;

public record CashierApiOutput(
        String message,
        CashierOutputDTO cashier) {
}
