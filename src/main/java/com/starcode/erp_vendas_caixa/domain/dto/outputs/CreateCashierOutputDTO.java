package com.starcode.erp_vendas_caixa.domain.dto.outputs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;

import java.time.LocalDateTime;

public record CreateCashierOutputDTO(
        String cashierId,
        Double openingAmount,
        String status,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime openedAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime closedAt,
        String user_openedId,
        String user_closedId,
        Integer totalSales,
        Double closingAmount
) {

    public static CreateCashierOutputDTO create(final Cashier cashier){
        return new CreateCashierOutputDTO(
                cashier.getCashierId().getValue(),
                cashier.getOpeningAmount().getValue(),
                cashier.getStatus().name(),
                cashier.getOpenedAt(),
                cashier.getClosedAt(),
                cashier.getUserOpenedId(),
                cashier.getUserClosedId(),
                cashier.getTotalSales(),
                cashier.getClosingAmount().getValue()
        );
    }
}


