package com.starcode.erp_vendas_caixa.application.usecases.Sales;

import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.dto.outputs.SaleOutputDTO;
import com.starcode.erp_vendas_caixa.domain.gateway.SaleGateway;

public class GetAllSalesByCashierUseCase {
    private final SaleGateway salesRepository;

    public GetAllSalesByCashierUseCase(SaleGateway salesRepository) {
        this.salesRepository = salesRepository;
    }

    public Pagination<SaleOutputDTO> execute(final SearchQuery searchQuery, final String cashier_id){
        final var output = this.salesRepository.getAllSalesOfCashierPagination(searchQuery, cashier_id);
        final var items = output.items()
                .stream()
                .map(SaleOutputDTO::create)
                .toList();

        return new Pagination<>(
                output.currentPage(),
                output.perPage(),
                output.size(),
                items);
    }
}
