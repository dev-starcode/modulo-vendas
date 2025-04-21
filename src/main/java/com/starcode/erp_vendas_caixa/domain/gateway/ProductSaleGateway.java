package com.starcode.erp_vendas_caixa.domain.gateway;

import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.entities.ProductSale.ProductSale;

import java.util.Optional;

public interface ProductSaleGateway {
    void save(ProductSale data);
    void update(ProductSale data);
    Optional<ProductSale> getProductSaleById(final String sale_product_id);
    Pagination<ProductSale> getAllProductsSaleByProductId(final SearchQuery search_query, final String product_id);
    Pagination<ProductSale> getAllProductsSaleBySale(final SearchQuery search_query , final String sale_id);
}
