package com.starcode.erp_vendas_caixa.infra.application.cashier;

import com.starcode.erp_vendas_caixa.application.usecases.Cashier.CreateCashierUseCase;
import com.starcode.erp_vendas_caixa.domain.dto.inputs.CreateCashierInputDTO;
import com.starcode.erp_vendas_caixa.infra.IntegrationTest;
import com.starcode.erp_vendas_caixa.infra.cashier.persistence.CashierRepositoryJPA;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@IntegrationTest
public class CreateCashierUseCaseIT {

    @Autowired
    private CreateCashierUseCase createCashierUseCase;

    @Autowired
    private CashierRepositoryJPA cashierRepository;

    @Test
    @DisplayName("Deve criar um caixa corretamente")
    public void deveCriarUmCaixaCorretamente() {
        final var userOpenedId = "user_opened_id";
        final var openingAmount = 100D;
        final var input = new CreateCashierInputDTO(userOpenedId, openingAmount);
        assertEquals(0, cashierRepository.count());
        final var output = this.createCashierUseCase.execute(input);
        assertNotNull(output.cashierId());
        assertEquals(userOpenedId, output.userOpenedId());
        assertEquals("opened", output.status().toString());
        assertEquals(1, cashierRepository.count());
        final var actualCashier =
                cashierRepository.findById(output.cashierId());
        assertEquals(output.cashierId(), actualCashier.get().getCashierId());
        assertEquals(output.status(), actualCashier.get().getStatus());
        assertEquals(output.openingAmount(), actualCashier.get().getOpeningAmount());
        assertEquals(
                output.openedAt().truncatedTo(ChronoUnit.MILLIS),
                actualCashier.get().getOpened_at().truncatedTo(ChronoUnit.MILLIS)
        );
    }
}