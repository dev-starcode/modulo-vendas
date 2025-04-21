package com.starcode.erp_vendas_caixa.application.usecases.Cashier;

import com.starcode.erp_vendas_caixa.domain.dto.inputs.OpenCashierInputDTO;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.exceptions.NotFoundException;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenCashierUseCaseTest {

    @Mock
    private CashierGateway cashierRepository;

    @InjectMocks
    private OpenCashierUseCase useCase;

    private Cashier cashier;


    @Test
    @DisplayName("Deve abrir o caixa corretamente")
    void deveAbrirOCaixaCorretamente() {
        final var cashier = Cashier.restore("cashier_id",100D,"closed", LocalDateTime.now(),LocalDateTime.now().plusHours(1), "user_opened_id","user_closed_id", 10, 100D);

        when(cashierRepository.getCashierById("cashier_id")).thenReturn(Optional.of(cashier));
        doNothing().when(cashierRepository).updateCashier(any(Cashier.class));

        // Act
        final var input = new OpenCashierInputDTO("cashier_id", "user_opened_id");
        final var openedCashier = useCase.execute(input);

        // Assert
        assertNotNull(openedCashier);
        assertEquals("opened", openedCashier.status());
    }

    @Test
    @DisplayName("Deve lançar exceção se o caixa não for encontrado")
    void deveLancarExcecaoSeCaixaNaoForEncontrado() {
        // Arrange
        when(cashierRepository.getCashierById("nao_existe")).thenReturn(Optional.empty());

        // Act & Assert
        final var input = new OpenCashierInputDTO("nao_existe", "user_opened_id");
        NotFoundException exception = assertThrows(NotFoundException.class, () -> useCase.execute(input));

        assertEquals("Caixa não encontrado", exception.getErrors().getFirst().message());
    }

    @Test
    @DisplayName("Deve abrir o caixa corretamente")
    void deveLancarUmaExcpetionQuandoOCaixaJaEstiverAberto() {
        final var cashier = Cashier.restore
                ("cashier_id",100D,"opened",
                        LocalDateTime.now(),LocalDateTime.now().plusHours(1),
                        "user_opened_id","user_closed_id",
                        10, 100D);

        when(cashierRepository.getCashierById("cashier_id")).thenReturn(Optional.of(cashier));

        final var input = new OpenCashierInputDTO("cashier_id", "user_opened_id");
        final var output = assertThrows(DomainException.class,
                () -> useCase.execute(input));

        assertEquals("O caixa já está aberto.", output.getErrors().getFirst().message());
    }

}
