package com.starcode.erp_vendas_caixa.domain.dto.inputs;

import java.util.List;

public record CreateSaleInputDTO(
        String userId,
        String clientId,
        String cashierId,
        Double partialPrice,
        Double discount,
        String status,
        String reason,
        List<ProductSaleDTO> productsSale
) {
}
