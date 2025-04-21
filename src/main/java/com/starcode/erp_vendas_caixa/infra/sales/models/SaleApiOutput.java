package com.starcode.erp_vendas_caixa.infra.sales.models;

import com.starcode.erp_vendas_caixa.domain.dto.outputs.SaleOutputDTO;

public record SaleApiOutput(
        String message,
        SaleOutputDTO sale
) {
}
