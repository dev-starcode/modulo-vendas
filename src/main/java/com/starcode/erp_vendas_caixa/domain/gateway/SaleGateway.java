package com.starcode.erp_vendas_caixa.domain.gateway;

import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleGateway {
    void save(final Sale data);
    void updateSale(final Sale data);
    Optional<Sale> getSaleById(final String sale_id);
    List<Sale> getAll();
    Pagination<Sale> getAllByPagination(final SearchQuery search_query);
    Pagination<Sale> getAllSalesOfCashierPagination(final SearchQuery search_query, final String cashier_id);
    List<Sale> getAllSalesOfCashier(final String cashier_id);
}
