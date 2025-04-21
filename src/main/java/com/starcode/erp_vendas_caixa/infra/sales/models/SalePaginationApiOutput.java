package com.starcode.erp_vendas_caixa.infra.sales.models;

import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;

public record SalePaginationApiOutput(
        String message,
        Pagination sales
) {
}
