package com.starcode.erp_vendas_caixa.infra.cashier.persistence.database;

import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;
import com.starcode.erp_vendas_caixa.infra.cashier.persistence.CashierJPAEntity;
import com.starcode.erp_vendas_caixa.infra.cashier.persistence.CashierRepositoryJPA;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.starcode.erp_vendas_caixa.infra.utils.SpecificationUtils.like;

@Repository
public class CashierPostgresqlRepository implements CashierGateway {

    private final CashierRepositoryJPA repository;

    public CashierPostgresqlRepository(CashierRepositoryJPA repository) {
        this.repository = repository;
    }

    @Override
    public void save(final Cashier data) {
        final var cashierJpaEntity = CashierJPAEntity.create(data);
        this.repository.save(cashierJpaEntity);
    }

    @Override
    public void updateCashier(final Cashier data) {
        final var cashierJpaEntity = CashierJPAEntity.create(data);
        this.repository.save(cashierJpaEntity);
    }

    @Override
    public List<Cashier> getAll() {
        return this.repository.findAll().stream().map(CashierJPAEntity::toAggregate).toList();
    }

    @Override
    public Pagination<Cashier> getAllByPagination(final SearchQuery search_query) {
        final var page = PageRequest.of(
                search_query.page(),
                search_query.perPage(),
                Sort.by(Sort.Direction.fromString(search_query.direction()), search_query.sort())
        );

        final var specifications = Optional.ofNullable(search_query.terms())
                .filter(str -> {
                    System.out.println(str);
                    return !str.isBlank();
                })
                .map(this::assembleSpecification)
                .orElse(null);
        final var result = this.repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.map(CashierJPAEntity::toAggregate).toList()
        );
    }

    @Override
    public Optional<Cashier> getCashierById(final String cashier_id) {
        return this.repository.findById(cashier_id)
                .map(CashierJPAEntity::toAggregate);
    }

    private Specification<CashierJPAEntity> assembleSpecification(final String str) {
        return like("status", str);
    }
}
