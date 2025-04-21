package com.starcode.erp_vendas_caixa.infra.cashier.persistence.database;

import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.infra.cashier.persistence.CashierRepositoryJPA;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CashierPostgresqlRepositoryTest {
    @Autowired
    private CashierPostgresqlRepository cashierPostgresqlRepository;

    @Autowired
    private CashierRepositoryJPA cashierRepositoryJPA;

    @BeforeAll
    void setup() {
        final var cashier1 = Cashier.restore("cashier1", 100D, "closed",
                LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), "", "", 10, 400D);
        final var cashier2 = Cashier.restore("cashier2", 100D, "closed",
                LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(8), "", "", 10, 400D);
        final var cashier3 = Cashier.restore("cashier3", 100D, "opened",
                LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(10), "", "", 10, 400D);
        final var cashier4 = Cashier.restore("cashier4", 100D, "closed",
                LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4), "", "", 10, 400D);
        final var cashier5 = Cashier.restore("cashier5", 100D, "opened",
                LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(5), "", "", 10, 400D);

        cashierPostgresqlRepository.save(cashier1);
        cashierPostgresqlRepository.save(cashier2);
        cashierPostgresqlRepository.save(cashier3);
        cashierPostgresqlRepository.save(cashier4);
        cashierPostgresqlRepository.save(cashier5);
    }

    @Test
    @DisplayName("Deve salvar um caixa")
    void deveSalvarUmCaixa() {
        Cashier cashier = Cashier.create("user123", 100.00);
        long before = cashierRepositoryJPA.count();
        cashierPostgresqlRepository.save(cashier);
        long after = cashierRepositoryJPA.count();
        Assertions.assertEquals(before + 1, after);
    }
    @Test
    @DisplayName("Deve buscar um caixa")
    void deveBuscarUmCaixa() {
        final var cashier = Cashier.create("user123", 100.00);
        cashierPostgresqlRepository.save(cashier);


        final var result = cashierPostgresqlRepository
                .getCashierById(cashier.getCashierId().getValue());

        Assertions.assertTrue(result.isPresent());
        final var retrieved = result.get();
        Assertions.assertNotNull(retrieved.getCashierId().getValue());
        Assertions.assertEquals("user123", retrieved.getUserOpenedId());
        Assertions.assertEquals(100.00, retrieved.getOpeningAmount().getValue());
    }

    @DisplayName("deve buscar todos os caixas")
    @Test
    void deveBuscarTodosOsCaixas(){
        final var searchQuery = new SearchQuery(0, 10, "", "status", "asc");
        final var output = cashierPostgresqlRepository.getAllByPagination(searchQuery);
        Assertions.assertEquals(5, output.size());
    }

    @Test
    @DisplayName("Deve bucvar todos os caixas abertos")
    void devebUscarTodosOsCaixasAberto(){
        final var searchQuery = new SearchQuery(0, 10, "opened", "openedAt", "asc");

        final var output = cashierPostgresqlRepository.getAllByPagination(searchQuery);
        Assertions.assertEquals(2, output.size());
    }
    @Test
    @DisplayName("Deve bucvar todos os caixas fechados")
    void devebUscarTodosOsCaixasFechado(){
        final var searchQuery = new SearchQuery(0, 10, "closed", "openedAt", "asc");

        final var output = cashierPostgresqlRepository.getAllByPagination(searchQuery);
        Assertions.assertEquals(3, output.size());
    }
}