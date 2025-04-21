package com.starcode.erp_vendas_caixa.application.factory;

import com.starcode.erp_vendas_caixa.domain.entities.CashierMovements.CashierMovements;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.CreateSaleInputDTO;

public class CashierMovementFactory {

    public static CashierMovements create(CreateSaleInputDTO data, Double total) {
        return CashierMovements.create(
                data.cashierId(),
                data.userId(),
                "in",
                total,
                data.reason()
        );
    }
}