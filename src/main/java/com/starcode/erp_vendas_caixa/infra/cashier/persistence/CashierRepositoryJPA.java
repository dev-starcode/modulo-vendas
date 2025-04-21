package com.starcode.erp_vendas_caixa.infra.cashier.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashierRepositoryJPA extends JpaRepository<CashierJPAEntity, String> {

    Page<CashierJPAEntity> findAll(Specification<CashierJPAEntity> whereClause, Pageable page);
}
