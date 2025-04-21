package com.starcode.erp_vendas_caixa.domain.entities;

import com.starcode.erp_vendas_caixa.domain.entities.CashierMovements.CashierMovements;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CashierMovimentsTest {

    @Test
    @DisplayName("Deve criar uma movimentação válida de entrada")
    void deveCriarUmaMovimentacaoDeEntraNoCaixa() {
        CashierMovements movement = CashierMovements.create(
                "cashier123", "user123", "in", 100.0, "Depósito inicial"
        );

        assertNotNull(movement);
        assertEquals("cashier123", movement.getCashierId());
        assertEquals("user123", movement.getUserId());
        assertEquals(CashierMovements.Type.in, movement.getType());
        assertEquals(100.0, movement.getAmount().getValue());
        assertEquals("Depósito inicial", movement.getReason());
    }

    @Test
    @DisplayName("Deve criar uma movimentação válida de saída")
    void deveCriarUmaMovimentacaoDeSaidaNoCaixa() {
        CashierMovements movement = CashierMovements.create(
                "cashier123", "user123", "out", 50.0, "Pagamento"
        );

        assertNotNull(movement);
        assertEquals(CashierMovements.Type.out, movement.getType());
        assertEquals(50.0, movement.getAmount().getValue());
        assertEquals("Pagamento", movement.getReason());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar movimentação com cashier_id nulo")
    void deveLancarUmaExceptionCasoOCashiserSejaNull() {
        final var exception = assertThrows(DomainException.class, () -> CashierMovements.create(null, "user123", "in", 100.0, "Depósito").validate(new ThrowsValidationHandler()));

        assertEquals("cashier_id não pode ser null", exception.getErrors().getFirst().message());
    }
    @Test
    @DisplayName("Deve lançar exceção ao criar movimentação com cashier_id nulo")
    void deveLancarUmaExceptionCasoOCashiserSejaMenorQueZero() {
        DomainException exception = assertThrows(DomainException.class, () -> CashierMovements.create(" ", "user123", "in", 100.0, "Depósito").validate(new ThrowsValidationHandler()));

        assertEquals("cashier_id deve ser maior que zero", exception.getErrors().getFirst().message());
    }
    @Test
    @DisplayName("Deve lançar exceção ao criar movimentação com user_id nulo")
    void deveLancarUmaExceptionCasoOUserIdSejaMenorQueZero() {
        DomainException exception = assertThrows(DomainException.class, () -> CashierMovements.create("cashier123", null,"in", 50.0, "Saque").validate(new ThrowsValidationHandler()));
        assertEquals("user_id não pode ser null", exception.getErrors().getFirst().message());
    }

    @Test
    @DisplayName("Deve restaurar uma movimentação válida")
    void deveRestaurarUmaMovimentacaoValida() {
        final var now = LocalDateTime.now();
        final var moviment = CashierMovements.restore(
                "mov123", "cashier123", "user123", "in", 200.0, "Restaurado", now
        );

        assertNotNull(moviment);
        assertEquals("mov123", moviment.getCashierMovementId().getValue());
        assertEquals(CashierMovements.Type.in, moviment.getType());
        assertEquals(200.0, moviment.getAmount().getValue());
        assertEquals("Restaurado", moviment.getReason());
    }

    @Test
    @DisplayName("Deve mudar tipo de movimentação para entrada")
    void deveMudarOTipoDeMovimentacao() {
        final var movement = CashierMovements.create(
                "cashier123", "user123", "out", 50.0, "Saque"
        );
        movement.validate(new ThrowsValidationHandler());
        movement.in();
        assertEquals(CashierMovements.Type.in, movement.getType());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar mudar para entrada quando já é entrada")
    void deveLancarUmaExcecaoQuandoTentarMudarParaOMesmoEstadoEntrada() {
        final var movement = CashierMovements.create(
                "cashier123", "user123", "in", 100.0, "Depósito"
        );
        movement.validate(new ThrowsValidationHandler());
        DomainException exception = assertThrows(DomainException.class, movement::in);
        assertEquals("O tipo de movimentação já é de entrada",
                exception.getErrors().getFirst().message());
    }

    @Test
    @DisplayName("Deve mudar tipo de movimentação para saída")
    void deveMudarOTipoParaSaida() {
        final var movement = CashierMovements.create(
                "cashier123", "user123", "in", 100.0, "Depósito"
        );
        movement.validate(new ThrowsValidationHandler());
        movement.out();
        assertEquals(CashierMovements.Type.out, movement.getType());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar mudar para saída quando já é saída")
    void deveLancarUmaExcecaoQuandoTentarMudarParaOMesmoEstadoSaida() {
        final var movement = CashierMovements.create(
                "cashier123", "user123", "out", 50.0, "Saque"
        );
        movement.validate(new ThrowsValidationHandler());
        DomainException exception = assertThrows(DomainException.class, movement::out);
        assertEquals("O tipo de movimentação já é de saída",
                exception.getErrors().getFirst().message());
    }
}