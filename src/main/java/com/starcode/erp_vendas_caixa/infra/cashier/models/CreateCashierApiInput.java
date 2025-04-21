package com.starcode.erp_vendas_caixa.infra.cashier.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record CreateCashierApiInput(
        @NotNull @JsonProperty("user_opened_id") String userOpeningId,
        @NotNull @JsonProperty("opening_amount") Double openingAmount
) {
}
