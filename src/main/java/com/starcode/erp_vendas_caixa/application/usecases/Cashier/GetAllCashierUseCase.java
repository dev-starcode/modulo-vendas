package com.starcode.erp_vendas_caixa.application.usecases.Cashier;

import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.dto.outputs.CashierOutputDTO;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;


public class GetAllCashierUseCase {
    private final CashierGateway cashierRepository;

    public GetAllCashierUseCase(final CashierGateway cashierRepository) {
        this.cashierRepository = cashierRepository;
    }
    public Pagination<CashierOutputDTO> execute(final SearchQuery data) {
        final var output = this.cashierRepository.getAllByPagination(data);
        final var items = output.items()
                .stream()
                .map(CashierOutputDTO::create)
                .toList();

        return new Pagination<>(
                output.currentPage(),
                output.perPage(),
                output.size(),
                items
        );
    }
}
