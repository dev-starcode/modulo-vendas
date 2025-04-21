package com.starcode.erp_vendas_caixa.domain.dto.outputs;

import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;

import java.time.LocalDateTime;

public record SaleOutputDTO(
        String saleId,
        String userId,
        String clientId,
        String cashierId,
        Double partialPrice,
        Double discount,
        Double total,
        String status,
        LocalDateTime saleDate
        ) {

        public static SaleOutputDTO create(final Sale sale){
                return new SaleOutputDTO(
                        sale.getSaleId().getValue(),
                        sale.getUserId(),
                        sale.getClientId(),
                        sale.getCashierId(),
                        sale.getPartialPrice().getValue(),
                        sale.getDiscount().getValue(),
                        sale.getTotal(),
                        sale.getStatus().name(),
                        sale.getSaleDate()
                );
        }
}
