package com.starcode.erp_vendas_caixa.application.usecases.Cashier;

import com.starcode.erp_vendas_caixa.domain.dto.inputs.CreateCashierInputDTO;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.gateway.CashierGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CreateCashierUseCaseTest {
    @InjectMocks
    private CreateCashierUseCase useCase;
    @Mock
    private CashierGateway cashierRepository;

    @Test
    @DisplayName("Deve criar um caixa corretamente")
    public void deveCriarUmCaixaCorretamente(){
        final var userOpenedId = "user_opened_id";
        final var openingAmount = 100D;
        final var input = CreateCashierInputDTO.create(userOpenedId, openingAmount);
        Mockito.doNothing().when(cashierRepository).save(any());

        final var outPut = this.useCase.execute(input);
        assertNotNull(outPut.cashierId());
        assertEquals(userOpenedId, outPut.userOpenedId());
        assertEquals("opened",
                outPut.status());
    }

    @Test
    @DisplayName("Deve lancar uma exceçao caso seja passado o userOpenedId null")
    public void deveLancarUmaExcecaoCasoSejaPassadoUmIdNull(){
        final var openingAmount = 100D;
        final var input = CreateCashierInputDTO.create(null, openingAmount);

        final var exception = assertThrows(DomainException.class, () -> this.useCase.execute(input));
        assertEquals("user_opened_id não pode ser null.", exception.getErrors().getFirst().message());
    }
}