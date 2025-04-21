package com.starcode.erp_vendas_caixa.domain.gateway;

import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;

import java.util.List;
import java.util.Optional;

public interface CashierGateway {
    void save(Cashier data);
    void updateCashier(Cashier search_query);
    List<Cashier> getAll();
    Pagination<Cashier> getAllByPagination(SearchQuery search_query);
    Optional<Cashier> getCashierById(String cashier_id);
}
