package com.starcode.erp_vendas_caixa.infra.sales.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepositoryJPA extends JpaRepository<SaleJPAEntity, String> {

    Page<SaleJPAEntity> findAll(Specification<SaleJPAEntity> whereClause, Pageable page);

    @Query(value = "SELECT * FROM sales WHERE cashier_id = :cashierId", nativeQuery = true)
    List<SaleJPAEntity> getSaleByCashierId(@Param("cashierId") String cashier_id);
}


