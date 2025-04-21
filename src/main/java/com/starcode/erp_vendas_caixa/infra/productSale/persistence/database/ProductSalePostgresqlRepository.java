package com.starcode.erp_vendas_caixa.infra.productSale.persistence.database;

import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.entities.ProductSale.ProductSale;
import com.starcode.erp_vendas_caixa.domain.gateway.ProductSaleGateway;
import com.starcode.erp_vendas_caixa.infra.productSale.persistence.ProductSaleJPAEntity;
import com.starcode.erp_vendas_caixa.infra.productSale.persistence.ProductSaleRepositoryJPA;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class ProductSalePostgresqlRepository implements ProductSaleGateway {

    private final ProductSaleRepositoryJPA repository;

    public ProductSalePostgresqlRepository(ProductSaleRepositoryJPA repository) {
        this.repository = repository;
    }

    @Override
    public void save(final ProductSale data) {
        this.repository.save(ProductSaleJPAEntity.create(data));
    }

    @Override
    public void update(final ProductSale data) {
        this.repository.save(ProductSaleJPAEntity.create(data));
    }

    @Override
    public Optional<ProductSale> getProductSaleById(final String sale_product_id) {
        return this.repository.findById(sale_product_id).map(ProductSaleJPAEntity::toAggregate);
    }

    @Override
    public Pagination<ProductSale> getAllProductsSaleByProductId(final SearchQuery searchQuery, final String product_id) {
        final var page = PageRequest.of(
                searchQuery.page(),
                searchQuery.perPage(),
                Sort.by(Sort.Direction.fromString(searchQuery.direction()), searchQuery.sort())
        );
        Specification<ProductSaleJPAEntity> productSpec = (root, query, cb) ->
                cb.equal(root.get("product_id"), product_id);

        final var result = this.repository.findAll(productSpec, page);
        return new Pagination<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.map(ProductSaleJPAEntity::toAggregate).toList()
        );
    }

    @Override
    public Pagination<ProductSale> getAllProductsSaleBySale(final SearchQuery search_query, final String sale_id) {
        final var page = PageRequest.of(
                search_query.page(),
                search_query.perPage(),
                Sort.by(Sort.Direction.fromString(search_query.direction()), search_query.sort())
        );
        Specification<ProductSaleJPAEntity> productSpec = (root, query, cb) ->
                cb.equal(root.get("sale_id"), sale_id);

        final var result = this.repository.findAll(productSpec, page);
        return new Pagination<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.map(ProductSaleJPAEntity::toAggregate).toList()
        );
    }
}
