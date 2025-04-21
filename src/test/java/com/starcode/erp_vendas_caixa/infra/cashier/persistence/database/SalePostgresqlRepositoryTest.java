package com.starcode.erp_vendas_caixa.infra.cashier.persistence.database;


import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.entities.Sale.Sale;
import com.starcode.erp_vendas_caixa.infra.sales.persistence.SaleRepositoryJPA;
import com.starcode.erp_vendas_caixa.infra.sales.persistence.database.SalePostgresqlRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SalePostgresqlRepositoryTest {

    @Autowired
    private SalePostgresqlRepository salePostgresqlRepository;

    @Autowired
    private SaleRepositoryJPA saleRepositoryJPA;

    @BeforeAll
    void setup(){
            final var sale1 = Sale.restore("sale1", "user", "client", "cashier",
                    100D, 0D, 100D, "paid", LocalDateTime.now());
            final var sale2 = Sale.restore("sale2", "user", "client", "cashier",
                    300D, 20D, 280D, "pending", LocalDateTime.now().plusHours(5));
            final var sale3 = Sale.restore("sale3", "user", "client", "cashier",
                    50D, 0D, 50D, "paid", LocalDateTime.now().plusHours(2));
        salePostgresqlRepository.save(sale1);
        salePostgresqlRepository.save(sale2);
        salePostgresqlRepository.save(sale3);
    }

    @Test
    @DisplayName("deve salvar um venda")
    public void deveSalvarUmCaixa(){
        final var sale = Sale.restore("sale", "user", "client", "cashier",
                100D, 0D, 100D, "paid", LocalDateTime.now());
        this.salePostgresqlRepository.save(sale);
        Assertions.assertEquals(4, this.saleRepositoryJPA.count());
    }

    @Test
    @DisplayName("deve buscar uma venda")
    public void deveBuscarUmCaixa() {
        final var sale = this.salePostgresqlRepository.getSaleById("sale1");
        Assertions.assertNotNull(sale.get());
    }
    @Test
    @DisplayName("deve buscar todos os vendas do banco")
    public void deveBuscarTodosOsCaixasdoBanco(){
        final var result = this.salePostgresqlRepository.getAll();
        Assertions.assertEquals(3, result.size());
    }

    @Test
    @DisplayName("deve buscar todos os vendas por filtos")
    public void deveBuscarTodosOsCaixas(){
        final var search = new SearchQuery(0, 10, "paid", "saleDate", "asc");
        final var result = this.salePostgresqlRepository.getAllByPagination(search);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    @DisplayName("deve buscar todos os vendas pelo caixa")
    public void deveBuscarTodasAsVendasPeloCaixa(){
        final var search = new SearchQuery(0, 10, "paid", "saleDate", "asc");
        final var result = this.salePostgresqlRepository.getAllSalesOfCashierPagination(search, "cashier");
        Assertions.assertEquals(2, result.size());
    }
}
