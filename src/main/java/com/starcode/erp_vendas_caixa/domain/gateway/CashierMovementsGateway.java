package com.starcode.erp_vendas_caixa.domain.gateway;

import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.entities.CashierMovements.CashierMovements;

import java.util.List;
import java.util.Optional;

public interface CashierMovementsGateway {
    void save(CashierMovements data);
    void update(CashierMovements data);
    Optional<CashierMovements> getCashierMovementById(String cashier_movement_id);
    Pagination<CashierMovements> getCashierMovementByCashier(SearchQuery search_query, String cashier_id);
    Pagination<CashierMovements> getByPagination(SearchQuery search_query);
    List<CashierMovements> getAll();

}
