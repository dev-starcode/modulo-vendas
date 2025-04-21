package com.starcode.erp_vendas_caixa.domain.dto.inputs;

public record ProductSaleDTO(
        String productId,
        String productName,
        double priceCost,
        double priceSale,
        int quantity
) {
}
