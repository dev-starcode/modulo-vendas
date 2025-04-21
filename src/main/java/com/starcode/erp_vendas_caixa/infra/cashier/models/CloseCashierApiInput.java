package com.starcode.erp_vendas_caixa.infra.cashier.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record CloseCashierApiInput(
        @NotNull @JsonProperty("user_closed_id") String userClosedId) {
}
