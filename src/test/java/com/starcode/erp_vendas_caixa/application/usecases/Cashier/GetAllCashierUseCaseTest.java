package com.starcode.erp_vendas_caixa.application.usecases.Cashier;

import com.starcode.erp_vendas_caixa.domain.Pagination.Pagination;
import com.starcode.erp_vendas_caixa.domain.Pagination.SearchQuery;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllCashierUseCaseTest {

    @InjectMocks
    private GetAllCashierUseCase useCase;

    @Mock
    private CashierGateway cashierRepository;

    private Cashier cashier1;
    private Cashier cashier2;

    @Test
    @DisplayName("Deve retornar uma pagina de caixas corretamente")
    void deveRetornarUmaPaginaDeCaixas() {
        final var cashier1 = Cashier.restore("cashier_id1",100D,"closed", LocalDateTime.now(),LocalDateTime.now().plusHours(1), "user_opened_id","user_closed_id", 10, 100D);
        final var cashier2 = Cashier.restore("cashier_id2",100D,"opened", LocalDateTime.now().plusHours(1),LocalDateTime.now().plusHours(2), "user_opened_id","user_closed_id", 10, 100D);

        var query = new SearchQuery(0, 10, "", "createdAt", "asc");
        var pagination = new Pagination<>(
                0,
                10,
                2,
                List.of(cashier1, cashier2)
        );

        when(cashierRepository.getAllByPagination(query)).thenReturn(pagination);

        var result = useCase.execute(query);

        assertNotNull(result);
        assertEquals(2, result.items().size());
        assertEquals(0, result.currentPage());
        assertEquals(2, result.size());
        assertEquals("cashier_id1", result.items().getFirst().cashierId());
        assertEquals("user_opened_id", result.items().getFirst().userOpenedId());

        verify(cashierRepository, times(1)).getAllByPagination(query);
    }

    @Test
    @DisplayName("Deve retornar uma página vazia se não houver caixas")
    void deveRetornarPaginaVazia() {
        var query = new SearchQuery(0, 10, "closed", "created_at", "asc");
        var emptyList = List.<Cashier>of();
        var pagination = new Pagination<>(0, 10, 0,emptyList);

        when(cashierRepository.getAllByPagination(query)).thenReturn(pagination);

        var result = useCase.execute(query);

        assertNotNull(result);
        assertTrue(result.items().isEmpty());
        assertEquals(0, result.size());

        verify(cashierRepository, times(1)).getAllByPagination(query);
    }
}
