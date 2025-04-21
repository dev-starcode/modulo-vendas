package com.starcode.erp_vendas_caixa.infra.cashier.persistence.database;

import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.entities.CashierMovements.CashierMovements;
import com.starcode.erp_vendas_caixa.infra.cashierMovements.persistence.CashierMovementsRepositoryJPA;
import com.starcode.erp_vendas_caixa.infra.cashierMovements.persistence.database.CashierMovementsPostgresqlRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("development")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CashierMovementsPostegrsqlRepositoryTest {

    @Autowired
    private CashierMovementsPostgresqlRepository cashierMovementsPostgresqlRepository;

    @Autowired
    private CashierMovementsRepositoryJPA cashierMovementsRepositoryJPA;


    @BeforeAll
    void setup(){
        final var movement1 = CashierMovements.create(
                "cashier123", "user123", "out", 50.0, "Venda"
        );
        final var movement2 = CashierMovements.create(
                "cashier123", "user123", "out", 50.0, "Pagamento"
        );
        final var movement3 = CashierMovements.create(
                "cashier123", "user123", "in", 50.0, "Pagamento"
        );

        cashierMovementsPostgresqlRepository.save(movement1);
        cashierMovementsPostgresqlRepository.save(movement2);
        cashierMovementsPostgresqlRepository.save(movement3);
    }

    @Test
    @DisplayName("deve salvar um movimento de venda")
    public void deveSalvarUmMovimento(){
        Assertions.assertEquals(3, this.cashierMovementsRepositoryJPA.count());
        final var movement = CashierMovements.create(
                "cashier123", "user123", "out", 50.0, "Pagamento"
        );
        this.cashierMovementsPostgresqlRepository.save(movement);
        Assertions.assertEquals(4, this.cashierMovementsRepositoryJPA.count());
    }

    @Test
    @DisplayName("deve buscar um movimento de venda pelo id ")
    public void deveBuscarPeloId(){
        final var movement = CashierMovements.create(
                "cashier123", "user123", "out", 50.0, "Pagamento"
        );
        this.cashierMovementsPostgresqlRepository.save(movement);
        final var result = this.cashierMovementsPostgresqlRepository.getCashierMovementById(movement.getCashierMovementId().getValue());
        Assertions.assertNotNull(result.get());
    }

    @Test
    @DisplayName("deve buscar movimentos do tipo 'out' por paginaçã")
    public void deveBuscarPorPaginacao(){
       final var pagination = new SearchQuery(0,10,"out", "createdAt", "asc");
        final var result = this.cashierMovementsPostgresqlRepository.getByPagination(pagination);
        Assertions.assertEquals(2, result.size());
    }

}
