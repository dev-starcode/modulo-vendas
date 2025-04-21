package com.starcode.erp_vendas_caixa.infra.productSale.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSaleRepositoryJPA extends JpaRepository<ProductSaleJPAEntity, String> {

    Page<ProductSaleJPAEntity> findAll(Specification<ProductSaleJPAEntity> whereClause, Pageable page);

}
