package com.starcode.erp_vendas_caixa.infra.cashierMovements.persistence.database;

import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.entities.CashierMovements.CashierMovements;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierMovementsGateway;
import com.starcode.erp_vendas_caixa.infra.cashierMovements.persistence.CashierMovementsJPAEntity;
import com.starcode.erp_vendas_caixa.infra.cashierMovements.persistence.CashierMovementsRepositoryJPA;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.starcode.erp_vendas_caixa.infra.utils.SpecificationUtils.like;

@Repository
public class CashierMovementsPostgresqlRepository implements CashierMovementsGateway {

    private final CashierMovementsRepositoryJPA repository;

    public CashierMovementsPostgresqlRepository(CashierMovementsRepositoryJPA repository) {
        this.repository = repository;
    }

    @Override
    public void save(final CashierMovements data) {
        this.repository.save(CashierMovementsJPAEntity.create(data));
    }

    @Override
    public void update(final CashierMovements data) {
        this.repository.save(CashierMovementsJPAEntity.create(data));
    }

    @Override
    public Optional<CashierMovements> getCashierMovementById(final String cashier_movement_id) {
        return this.repository.findById(cashier_movement_id).map(CashierMovementsJPAEntity::toAggregate);
    }

    @Override
    public Pagination<CashierMovements> getCashierMovementByCashier(final SearchQuery search_query, final String cashier_id) {
        final var page = PageRequest.of(
                search_query.page(),
                search_query.perPage(),
                Sort.by(Sort.Direction.fromString(search_query.direction()), search_query.sort()));

        Specification<CashierMovementsJPAEntity> cashierSpec = (root, query, cb) ->
                cb.equal(root.get("cashier_id"), cashier_id);

        Specification<CashierMovementsJPAEntity> finalSpec = Optional.ofNullable(search_query.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .map(cashierSpec::and)
                .orElse(cashierSpec);


        final var result = this.repository.findAll(Specification.where(finalSpec), page);

        return new Pagination<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.map(CashierMovementsJPAEntity::toAggregate).toList());
    }

    @Override
    public Pagination<CashierMovements> getByPagination(final SearchQuery search_query) {
        final var page = PageRequest.of(
                search_query.page(),
                search_query.perPage(),
                Sort.by(Sort.Direction.fromString(search_query.direction()), search_query.sort()));

        Specification<CashierMovementsJPAEntity> finalSpec = Optional.ofNullable(search_query.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var result = this.repository.findAll(Specification.where(finalSpec), page);

        return new Pagination<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.map(CashierMovementsJPAEntity::toAggregate).toList());
    }

    @Override
    public List<CashierMovements> getAll() {
        return this.repository.findAll()
                .stream().map(CashierMovementsJPAEntity::toAggregate).toList();
    }

    private Specification<CashierMovementsJPAEntity> assembleSpecification(final String str) {
        return like("type", str);
    }
}
