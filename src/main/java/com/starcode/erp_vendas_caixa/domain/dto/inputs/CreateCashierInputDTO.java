package com.starcode.erp_vendas_caixa.domain.dto.inputs;

public record CreateCashierInputDTO(
        String userOpenedId,
        Double openingAmount
) {
    public static CreateCashierInputDTO create(final String user_opened_id, final double opening_amount){
        return new CreateCashierInputDTO(user_opened_id, opening_amount);
    }
}
