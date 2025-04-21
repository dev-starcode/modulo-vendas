package com.starcode.erp_vendas_caixa.application.usecases.Cashier;

import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCashierByIdTest {

    @Mock
    private CashierGateway cashierRepository;

    @InjectMocks
    private GetCashierById useCase;

    private String cashierId;
    private Cashier cashier;

    @BeforeEach
    void setUp() {
        cashierId = "cashier_123";
        cashier = Cashier.create("user_opened_id", 100.0); // ou new Cashier(...) dependendo do seu construtor
    }

    @Test
    @DisplayName("Deve retornar o caixa quando encontrado")
    void deveRetornarOCaixaQuandoEncontrado() {
        when(cashierRepository.getCashierById(cashierId)).thenReturn(Optional.of(cashier));

        final var result = useCase.execute(cashierId);

        assertNotNull(result);
        verify(cashierRepository, times(1)).getCashierById(cashierId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando caixa não for encontrado")
    void deveLancarExcecaoQuandoCaixaNaoForEncontrado() {
        when(cashierRepository.getCashierById(cashierId)).thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, () -> useCase.execute(cashierId));

        assertEquals("Caixa não encontrado", exception.getErrors().getFirst().message());
        verify(cashierRepository, times(1)).getCashierById(cashierId);
    }
}
