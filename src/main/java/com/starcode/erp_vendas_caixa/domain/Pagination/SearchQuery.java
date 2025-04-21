package com.starcode.erp_vendas_caixa.domain.Pagination;

public record SearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}