package com.starcode.erp_vendas_caixa.infra.application.cashier;

import com.starcode.erp_vendas_caixa.application.usecases.Cashier.GetCashierById;
import com.starcode.erp_vendas_caixa.domain.entities.Cashier.Cashier;
import com.starcode.erp_vendas_caixa.domain.exceptions.DomainException;
import com.starcode.erp_vendas_caixa.infra.IntegrationTest;
import com.starcode.erp_vendas_caixa.infra.cashier.persistence.CashierJPAEntity;
import com.starcode.erp_vendas_caixa.infra.cashier.persistence.CashierRepositoryJPA;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


@IntegrationTest
public class GetCashierByIdUseCaseIT {
    @Autowired
    private GetCashierById getCashierById;

    @Autowired
    private CashierRepositoryJPA cashierRepository;

    @Test
    @DisplayName("Deve retornar o caixa quando encontrado")
    void deveRetornarOCaixaQuandoEncontrado() {
        final var cashier = Cashier.create("user123", 100.00);
        this.cashierRepository.save(CashierJPAEntity.create(cashier));
        final var result = getCashierById.execute(cashier.getCashierId().getValue());
       assertNotNull(result);
       assertEquals(cashier.getCashierId().getValue(), result.cashierId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando caixa não for encontrado")
    void deveLancarExcecaoQuandoCaixaNaoForEncontrado() {

        final var result = assertThrows(DomainException.class, () -> getCashierById.execute(""));
        assertEquals("Caixa não encontrado", result.getErrors().getFirst().message());
    }
}
