package com.starcode.erp_vendas_caixa.infra.sales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateSaleInputAPI(
        @NotNull @JsonProperty("user_id") String userId,
        @JsonProperty("client_id")String clientId,
        @NotNull @JsonProperty("cashier_id")String cashierId,
        @NotNull @JsonProperty("partial_price")double partialPrice,
        @JsonProperty("discount") double discount,
        @NotNull @JsonProperty("status")String status,
        @JsonProperty("reason")String reason,
        @NotNull @JsonProperty("products")List<ProductSaleInputDTO> products
) {
}


