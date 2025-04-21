package com.starcode.erp_vendas_caixa.infra.sales.persistence.database;

import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;
import com.starcode.erp_vendas_caixa.domain.gateway.SaleGateway;
import com.starcode.erp_vendas_caixa.infra.cashierMovements.persistence.CashierMovementsJPAEntity;
import com.starcode.erp_vendas_caixa.infra.productSale.persistence.ProductSaleJPAEntity;
import com.starcode.erp_vendas_caixa.infra.sales.persistence.SaleJPAEntity;
import com.starcode.erp_vendas_caixa.infra.sales.persistence.SaleRepositoryJPA;
import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.starcode.erp_vendas_caixa.infra.utils.SpecificationUtils.like;

@Repository
public class SalePostgresqlRepository implements SaleGateway {

    private final SaleRepositoryJPA repository;

    public SalePostgresqlRepository(SaleRepositoryJPA repository) {
        this.repository = repository;
    }

    @Override
    public void save(final Sale data) {
        this.repository.save(SaleJPAEntity.create(data));
    }

    @Override
    public void updateSale(final Sale data) {
        this.repository.save(SaleJPAEntity.create(data));
    }

    @Override
    public Optional<Sale> getSaleById(final String sale_id) {
        return this.repository.findById(sale_id).map(SaleJPAEntity::toAggregate);
    }

    @Override
    public List<Sale> getAll() {
        return this.repository.findAll().stream().map(SaleJPAEntity::toAggregate).toList();
    }

    @Override
    public Pagination<Sale> getAllByPagination(final SearchQuery search_query) {
        final var page = PageRequest.of(
                search_query.page(),
                search_query.perPage(),
                Sort.by(Sort.Direction.fromString(search_query.direction()), search_query.sort())
        );

        final var specifications = Optional.ofNullable(search_query.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var result = this.repository.findAll(Specification.where(specifications), page);
        return new Pagination<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.map(SaleJPAEntity::toAggregate).toList()
        );
    }

    @Override
    public Pagination<Sale> getAllSalesOfCashierPagination(final SearchQuery search_query, final String cashier_id) {
        final var page = PageRequest.of(
                search_query.page(),
                search_query.perPage(),
                Sort.by(Sort.Direction.fromString(search_query.direction()), search_query.sort())
        );

        Specification<SaleJPAEntity> cashierSpec = (root, query, cb) ->
                cb.equal(root.get("cashierId"), cashier_id);

        Specification<SaleJPAEntity> finalSpec = Optional.ofNullable(search_query.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .map(cashierSpec::and)
                .orElse(cashierSpec);

        final var result = this.repository.findAll(Specification.where(finalSpec), page);
        return new Pagination<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.map(SaleJPAEntity::toAggregate).toList());
    }

    @Override
    public List<Sale> getAllSalesOfCashier(final String cashier_id) {
        return this.repository.getSaleByCashierId(cashier_id).stream().map(SaleJPAEntity::toAggregate).toList();
    }

    private Specification<SaleJPAEntity> assembleSpecification(final String str) {
        return like("status", str);
    }
}
