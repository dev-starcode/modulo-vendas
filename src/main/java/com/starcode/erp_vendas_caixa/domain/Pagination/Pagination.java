package com.starcode.erp_vendas_caixa.domain.Pagination;

import java.util.List;

public record Pagination <T>(
        int currentPage,
        int perPage,
        long size,
        List<T> items
) {
}
