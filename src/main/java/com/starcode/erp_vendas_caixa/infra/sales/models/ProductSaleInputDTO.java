package com.starcode.erp_vendas_caixa.infra.sales.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record ProductSaleInputDTO(
        @NotNull @JsonProperty("product_id") String productId,
        @NotNull @JsonProperty("product_name") String productName,
        @NotNull @JsonProperty("price_cost") double priceCost,
        @NotNull @JsonProperty("price_sale") double priceSale,
        @NotNull @JsonProperty("quantity") int quantity
){
}
