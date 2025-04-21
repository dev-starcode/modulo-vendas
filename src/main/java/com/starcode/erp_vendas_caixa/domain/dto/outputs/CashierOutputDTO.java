package com.starcode.erp_vendas_caixa.domain.dto.outputs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;

import java.time.LocalDateTime;

public record CashierOutputDTO(
        String cashierId,
        Double openingAmount,
        String status,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime openedAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime closedAt,
        String userOpenedId,
        String userClosedId,
        Integer totalSales,
        Double closingAmount
) {
    public static CashierOutputDTO create(final Cashier data){
        return new CashierOutputDTO(
                data.getCashierId().getValue(),
                data.getOpeningAmount().getValue(),
                data.getStatus().name(),
                data.getOpenedAt(),
                data.getClosedAt(),
                data.getUserOpenedId(),
                data.getUserClosedId(),
                data.getTotalSales(),
                data.getClosingAmount().getValue()
                );
    }
}
